package com.etiya.customerservice.service.orchestrators;

import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.events.address.CreateAddressEvent;
import com.etiya.common.events.contactmedium.CreateContactMediumEvent;
import com.etiya.common.events.customer.CreateCustomerEvent;
import com.etiya.customerservice.domain.entities.Address;
import com.etiya.customerservice.domain.entities.ContactMedium;
import com.etiya.customerservice.domain.entities.District;
import com.etiya.customerservice.domain.entities.IndividualCustomer;
import com.etiya.customerservice.repository.AddressRepository;
import com.etiya.customerservice.repository.ContactMediumRepository;
import com.etiya.customerservice.repository.IndividualCustomerRepository;
import com.etiya.customerservice.service.requests.CreateFullCustomerRequest;
import com.etiya.customerservice.service.requests.address.CreateAddressItem;
import com.etiya.customerservice.service.requests.contactMedium.CreateContactMediumItem;
import com.etiya.customerservice.service.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.etiya.customerservice.service.responses.CreateFullCustomerResponse;
import com.etiya.customerservice.transport.kafka.producer.address.CreateAddressProducer;
import com.etiya.customerservice.transport.kafka.producer.contactmedium.CreateContactMediumProducer;
import com.etiya.customerservice.transport.kafka.producer.customer.CreateCustomerProducer;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerOnboardingService {

    private final IndividualCustomerRepository individualRepo;
    private final AddressRepository addressRepo;
    private final ContactMediumRepository contactRepo;
    private final EntityManager em;

    private final CreateCustomerProducer createCustomerProducer;
    private final CreateAddressProducer createAddressProducer;
    private final CreateContactMediumProducer createContactProducer;

    public CustomerOnboardingService(IndividualCustomerRepository individualRepo, AddressRepository addressRepo, ContactMediumRepository contactRepo, EntityManager em, CreateCustomerProducer createCustomerProducer, CreateAddressProducer createAddressProducer, CreateContactMediumProducer createContactProducer) {
        this.individualRepo = individualRepo;
        this.addressRepo = addressRepo;
        this.contactRepo = contactRepo;
        this.em = em;
        this.createCustomerProducer = createCustomerProducer;
        this.createAddressProducer = createAddressProducer;
        this.createContactProducer = createContactProducer;
    }

    @Transactional
    public CreateFullCustomerResponse createFull(CreateFullCustomerRequest req) {
        // 1) Customer
        CreateIndividualCustomerRequest ic = req.getIndividualCustomer();

        IndividualCustomer customer = new IndividualCustomer();
        customer.setFirstName(ic.getFirstName());
        customer.setMiddleName(ic.getMiddleName());
        customer.setLastName(ic.getLastName());
        customer.setNationalId(ic.getNationalId());
        customer.setMotherName(ic.getMotherName());
        customer.setFatherName(ic.getFatherName());
        customer.setGender(ic.getGender());
        customer.setDateOfBirth(ic.getDateOfBirth()); // "YYYY-MM-DD"

        IndividualCustomer created = individualRepo.save(customer);

        // event
        CreateCustomerEvent custEvent = new CreateCustomerEvent(
                created.getId().toString(),
                created.getCustomerNumber(),
                created.getFirstName(),
                created.getLastName(),
                created.getNationalId(),
                created.getDateOfBirth().toString(),
                created.getMotherName(),
                created.getFatherName(),
                created.getGender()
        );
        createCustomerProducer.produceCustomerCreated(custEvent);

        // 2) Addresses
        List<CreateFullCustomerResponse.CreatedAddressMini> addrMinis = new ArrayList<>();
        for (CreateAddressItem a : req.getAddresses()) {
            Address address = new Address();
            address.setTitle(a.getTitle());
            address.setStreet(a.getStreet());
            address.setHouseNumber(a.getHouseNumber());
            address.setDescription(a.getDescription());
            address.setDefault(a.isDefault());

            address.setCustomer(created);
            address.setDistrict(em.getReference(District.class, a.getDistrictId()));

            Address saved = addressRepo.save(address);

            // event
            CreateAddressEvent addrEvent = new CreateAddressEvent(
                    created.getId().toString(),
                    saved.getId(),
                    saved.getTitle(),
                    saved.getStreet(),
                    saved.getHouseNumber(),
                    saved.getDescription(),
                    saved.isDefault(),
                    saved.getDistrict().getId(),
                    saved.getDistrict().getName(),
                    saved.getDistrict().getCity().getId(),
                    saved.getDistrict().getCity().getName(),
                    saved.getCreatedDate().toString(),
                    "", ""
            );
            createAddressProducer.produceAddressCreated(addrEvent);

            CreateFullCustomerResponse.CreatedAddressMini mini = new CreateFullCustomerResponse.CreatedAddressMini();
            mini.setId(saved.getId());
            mini.setDefault(saved.isDefault());
            addrMinis.add(mini);
        }

        // 3) Contact Mediums
        List<CreateFullCustomerResponse.CreatedContactMini> cmMinis = new ArrayList<>();
        boolean hasEmail = req.getContactMediums().stream().anyMatch(m -> "email".equalsIgnoreCase(m.getType()));
        boolean hasMobile = req.getContactMediums().stream().anyMatch(m -> "mobile_phone".equalsIgnoreCase(m.getType()));
        if (!hasEmail || !hasMobile) {
            throw new BusinessException("Both email and mobile_phone contact mediums are required.");
        }

        for (CreateContactMediumItem m : req.getContactMediums()) {
            // contactMediumBusinessRules.checkPrimaryStatus(created.getId(), m.isPrimary());

            ContactMedium cm = new ContactMedium();
            cm.setCustomer(created);
            cm.setType(m.getType());
            cm.setValue(m.getValue());
            cm.setPrimary(m.isPrimary());

            ContactMedium saved = contactRepo.save(cm);

            // event
            CreateContactMediumEvent cmEvent = new CreateContactMediumEvent(
                    created.getId().toString(),
                    saved.getId(),
                    saved.getType(),
                    saved.getValue(),
                    saved.isPrimary()
            );
            createContactProducer.produceContactMediumCreated(cmEvent);

            CreateFullCustomerResponse.CreatedContactMini mini = new CreateFullCustomerResponse.CreatedContactMini();
            mini.setId(saved.getId());
            mini.setType(saved.getType());
            mini.setPrimary(saved.isPrimary());
            cmMinis.add(mini);
        }

        // 4) Response
        CreateFullCustomerResponse resp = new CreateFullCustomerResponse();
        resp.setCustomerId(created.getId().toString());
        resp.setCustomerNumber(created.getCustomerNumber());
        resp.setAddresses(addrMinis);
        resp.setContactMediums(cmMinis);
        return resp;
    }
}
