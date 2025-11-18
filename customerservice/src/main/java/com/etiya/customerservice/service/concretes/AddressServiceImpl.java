package com.etiya.customerservice.service.concretes;

import com.etiya.common.events.address.CreateAddressEvent;
import com.etiya.common.events.address.DeleteAddressEvent;
import com.etiya.common.events.address.SoftDeleteAddressEvent;
import com.etiya.common.events.address.UpdateAddressEvent;
import com.etiya.customerservice.domain.entities.Address;
import com.etiya.customerservice.domain.entities.Customer;
import com.etiya.customerservice.domain.entities.District;
import com.etiya.customerservice.repository.AddressRepository;
import com.etiya.customerservice.service.abstracts.AddressService;
import com.etiya.customerservice.service.mappings.AddressMapper;
import com.etiya.customerservice.service.requests.address.CreateAddressRequest;
import com.etiya.customerservice.service.requests.address.UpdateAddressRequest;
import com.etiya.customerservice.service.responses.address.CreatedAddressResponse;
import com.etiya.customerservice.service.responses.address.GetAddressResponse;
import com.etiya.customerservice.service.responses.address.GetListAddressResponse;
import com.etiya.customerservice.service.responses.address.UpdatedAddressResponse;
import com.etiya.customerservice.service.rules.AddressBusinessRules;
import com.etiya.customerservice.transport.kafka.producer.address.CreateAddressProducer;
import com.etiya.customerservice.transport.kafka.producer.address.DeleteAddressProducer;
import com.etiya.customerservice.transport.kafka.producer.address.SoftDeleteAddressProducer;
import com.etiya.customerservice.transport.kafka.producer.address.UpdateAddressProducer;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    private final EntityManager em;
    public final AddressRepository addressRepository;
    public final AddressBusinessRules addressBusinessRules;
    public final CreateAddressProducer createAddressProducer;
    public final UpdateAddressProducer updateAddressProducer;
    public final DeleteAddressProducer deleteAddressProducer;
    public final SoftDeleteAddressProducer softDeleteAddressProducer;

    public AddressServiceImpl(EntityManager em, AddressRepository addressRepository, AddressBusinessRules addressBusinessRules, CreateAddressProducer createAddressProducer, UpdateAddressProducer updateAddressProducer, DeleteAddressProducer deleteAddressProducer, SoftDeleteAddressProducer softDeleteAddressProducer) {
        this.em = em;
        this.addressRepository = addressRepository;
        this.addressBusinessRules = addressBusinessRules;
        this.createAddressProducer = createAddressProducer;
        this.updateAddressProducer = updateAddressProducer;
        this.deleteAddressProducer = deleteAddressProducer;
        this.softDeleteAddressProducer = softDeleteAddressProducer;
    }

    @Override
    public CreatedAddressResponse add(CreateAddressRequest request) {
        Address address = AddressMapper.INSTANCE.addressFromCreateAddressRequest(request);

        address.setDistrict(em.getReference(District.class, request.getDistrictId()));
        address.setCustomer(em.getReference(Customer.class, request.getCustomerId()));

        Address createdAddress = addressRepository.save(address);

        CreateAddressEvent event = new CreateAddressEvent(
                createdAddress.getCustomer().getId().toString(),
                createdAddress.getId(),
                createdAddress.getTitle(),
                createdAddress.getStreet(),
                createdAddress.getHouseNumber(),
                createdAddress.getDescription(),
                createdAddress.isDefault(),
                createdAddress.getDistrict().getId(),
                createdAddress.getDistrict().getName(),
                createdAddress.getDistrict().getCity().getId(),
                createdAddress.getDistrict().getCity().getName(),
                createdAddress.getCreatedDate().toString(),
                "",""
        );

        createAddressProducer.produceAddressCreated(event);

        CreatedAddressResponse response = AddressMapper.INSTANCE.createdAddressResponseFromAddress(createdAddress);
        return response;
    }

    @Override
    public List<GetListAddressResponse> getList() {
        List<Address> addresses = addressRepository.findAll();
        List<GetListAddressResponse> response = AddressMapper.INSTANCE.getListAddressResponsesFromAddresses(addresses);
        return response;
    }

    @Override
    public UpdatedAddressResponse update(int id,UpdateAddressRequest request) {
        Address existingAddress = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
        AddressMapper.INSTANCE.addressFromUpdateAddressRequest(request, existingAddress);
        Address saved= addressRepository.save(existingAddress);
        UpdateAddressEvent event  = new UpdateAddressEvent(
                saved.getCustomer().getId().toString(),
                saved.getId(),
                saved.getTitle(),
                saved.getStreet(),
                saved.getHouseNumber(),
                saved.getDescription(),
                saved.isDefault(),
                saved.getDistrict().getId(),
                saved.getDistrict().getName(),
                saved.getDistrict().getCity().getId(),
                saved.getDistrict().getCity().getName()
        );
        updateAddressProducer.produceCustomerUpdated(event);
        return AddressMapper.INSTANCE.updatedAddressResponseFromAddress(saved);
    }

    @Override
    public GetAddressResponse getById(int id) {
        Address addresses = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("address not found"));
        GetAddressResponse response = AddressMapper.INSTANCE.getAddressResponseFromAddress(addresses);
        return response;
    }

    @Override
    public List<GetListAddressResponse> findDefaultAddresses() {
        List<Address> addresses = addressRepository.findDefaultAddresses();
        List<GetListAddressResponse> response = AddressMapper.INSTANCE.getListAddressResponsesFromAddresses(addresses);
        return response;
    }

    @Override
    public List<GetListAddressResponse> findByDescriptionNative(String keyword) {
        List<Address> addresses = addressRepository.findByDescriptionNative(keyword);
        List<GetListAddressResponse> response = AddressMapper.INSTANCE.getListAddressResponsesFromAddresses(addresses);
        return response;
    }

    @Override
    public List<GetListAddressResponse> findByStreetContaining(String streetPart) {
        List<Address> addresses = addressRepository.findByStreetContaining(streetPart);
        List<GetListAddressResponse> response = AddressMapper.INSTANCE.getListAddressResponsesFromAddresses(addresses);
        return response;
    }

    @Override
    public void delete(int id) {
        addressBusinessRules.checkBillingAccountExistenceBeforeDelete(id);
        Address address = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));

        DeleteAddressEvent event = new DeleteAddressEvent(
                address.getCustomer().getId().toString(),
                id
        );

        deleteAddressProducer.produceAddressDeleted(event);

        addressRepository.delete(address);
    }

    @Override
    public void softDelete(int id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
        address.setDeletedDate(LocalDateTime.now());
        addressRepository.save(address);
        SoftDeleteAddressEvent event = new SoftDeleteAddressEvent(
                address.getCustomer().getId().toString(),
                id,
                address.getUpdatedDate().toString(),
                address.getDeletedDate().toString()
        );
      softDeleteAddressProducer.produceAddressSoftDeleted(event);
    }

    @Override
    public List<GetListAddressResponse> findByCustomerId(UUID customerId) {
        List<Address> addresses = addressRepository.findByCustomerId(customerId);
        List<GetListAddressResponse> response = AddressMapper.INSTANCE.getListAddressResponsesFromAddresses(addresses);
        return response;
    }


}
