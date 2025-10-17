package com.etiya.customerservice.service.concretes;

import com.etiya.common.events.CreateContactMediumEvent;
import com.etiya.common.events.DeleteAddressEvent;
import com.etiya.common.events.DeleteContactMediumEvent;
import com.etiya.common.events.UpdateContactMediumEvent;
import com.etiya.customerservice.domain.entities.ContactMedium;
import com.etiya.customerservice.repository.ContactMediumRepository;
import com.etiya.customerservice.service.abstracts.ContactMediumService;
import com.etiya.customerservice.service.mappings.ContactMediumMapper;
import com.etiya.customerservice.service.requests.contactMedium.CreateContactMediumRequest;
import com.etiya.customerservice.service.requests.contactMedium.UpdateContactMediumRequest;
import com.etiya.customerservice.service.responses.contactMedium.CreatedContactMediumResponse;
import com.etiya.customerservice.service.responses.contactMedium.GetContactMediumResponse;
import com.etiya.customerservice.service.responses.contactMedium.GetListContactMediumResponse;
import com.etiya.customerservice.service.responses.contactMedium.UpdatedContactMediumResponse;
import com.etiya.customerservice.service.rules.ContactMediumBusinessRules;
import com.etiya.customerservice.transport.kafka.producer.customer.CreateContactMediumProducer;
import com.etiya.customerservice.transport.kafka.producer.customer.DeleteContactMediumProducer;
import com.etiya.customerservice.transport.kafka.producer.customer.UpdateContactMediumProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactMediumServiceImpl implements ContactMediumService {

    private final ContactMediumRepository contactMediumRepository;
    private final ContactMediumBusinessRules contactMediumBusinessRules;
    private final CreateContactMediumProducer createContactMediumProducer;
    private final UpdateContactMediumProducer updateContactMediumProducer;
    private final DeleteContactMediumProducer deleteContactMediumProducer;


    public ContactMediumServiceImpl(ContactMediumRepository contactMediumRepository, ContactMediumBusinessRules contactMediumBusinessRules, CreateContactMediumProducer createContactMediumProducer, UpdateContactMediumProducer updateContactMediumProducer, DeleteContactMediumProducer deleteContactMediumProducer) {
        this.contactMediumRepository = contactMediumRepository;
        this.contactMediumBusinessRules = contactMediumBusinessRules;
        this.createContactMediumProducer = createContactMediumProducer;
        this.updateContactMediumProducer = updateContactMediumProducer;
        this.deleteContactMediumProducer = deleteContactMediumProducer;
    }

    @Override
    public CreatedContactMediumResponse add(CreateContactMediumRequest request) {
        contactMediumBusinessRules.checkPrimaryStatus(request.getCustomerId(), request.isPrimary());
        contactMediumBusinessRules.checkCustomerBeforeAdd(request.getCustomerId());
        ContactMedium contactMedium = ContactMediumMapper.INSTANCE.contactMediumFromContactMediumRequest(request);

        ContactMedium createdContactMedium = contactMediumRepository.save(contactMedium);

        CreateContactMediumEvent event = new CreateContactMediumEvent(
                createdContactMedium.getCustomer().getId().toString(),
                createdContactMedium.getId(),
                createdContactMedium.getType(),
                createdContactMedium.getValue(),
                createdContactMedium.isPrimary()
        );

        createContactMediumProducer.produceContactMediumCreated(event);

        CreatedContactMediumResponse createdContactMediumResponse = ContactMediumMapper.INSTANCE.createdContactMediumResponseFromContactMedium(createdContactMedium);
        return createdContactMediumResponse;
    }

    @Override
    public List<GetListContactMediumResponse> getAll() {
        List<ContactMedium> contactMediums = contactMediumRepository.findAll();
        List<GetListContactMediumResponse> response = ContactMediumMapper.INSTANCE.getListContactMediumResponseFromContactMedium(contactMediums);
                return response;
    }

    @Override
    public GetContactMediumResponse getById(int id) {
        ContactMedium contactMedium = contactMediumRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact Medium with id " + id + " not found"));
        GetContactMediumResponse response = ContactMediumMapper.INSTANCE.getContactMediumResponseFromContactMedium(contactMedium);
        return response;
    }

    @Override
    public UpdatedContactMediumResponse update(UpdateContactMediumRequest request) {
        ContactMedium contactMedium = contactMediumRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Contact Medium Not Found"));

        if(request.getValue() != null) {
            contactMedium.setValue(request.getValue());
        }

        if(request.getType() != null) {
            contactMedium.setType(request.getType());
        }

        if(request.getIsPrimary() != null) {
            contactMedium.setPrimary(request.getIsPrimary());
        }

        ContactMedium updatedContactMedium = contactMediumRepository.save(contactMedium);

        UpdateContactMediumEvent event = new UpdateContactMediumEvent(
                updatedContactMedium.getCustomer().getId().toString(),
                updatedContactMedium.getId(),
                updatedContactMedium.getType(),
                updatedContactMedium.getValue(),
                updatedContactMedium.isPrimary()
        );

        updateContactMediumProducer.produceContactMediumUpdated(event);


        UpdatedContactMediumResponse response = new UpdatedContactMediumResponse();
        response.setId(updatedContactMedium.getId());
        response.setCustomerId(updatedContactMedium.getCustomer().getId());
        response.setType(updatedContactMedium.getType());
        response.setValue(updatedContactMedium.getValue());
        response.setIsPrimary(updatedContactMedium.isPrimary());
        return response;
    }

    @Override
    public void delete(int id) {
        contactMediumBusinessRules.checkPrimaryStatusBeforeDelete(id);
        ContactMedium contactMedium = contactMediumRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact Medium Not Found"));

        DeleteContactMediumEvent event = new DeleteContactMediumEvent(
                contactMedium.getCustomer().getId().toString(),
                id
        );

        deleteContactMediumProducer.produceContactMediumDeleted(event);

        contactMediumRepository.delete(contactMedium);
    }

    @Override
    public void softDelete(int id) {
        contactMediumBusinessRules.checkPrimaryStatusBeforeDelete(id);
        ContactMedium contactMedium = contactMediumRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact Medium Not Found"));
        contactMedium.setDeletedDate(LocalDateTime.now());
        contactMediumRepository.save(contactMedium);
    }

    @Override
    public List<GetListContactMediumResponse> findAllByTypeMail() {
        List<ContactMedium> contactMediums = contactMediumRepository.findAllByTypeMail();
        List<GetListContactMediumResponse> response = ContactMediumMapper.INSTANCE.getListContactMediumResponseFromContactMedium(contactMediums);
        return response;
    }

    @Override
    public List<GetListContactMediumResponse> findAllByValueStartingPrefix(String prefix) {
        List<ContactMedium> contactMediums = contactMediumRepository.findAllByValueStartingPrefix(prefix);
        List<GetListContactMediumResponse> response = ContactMediumMapper.INSTANCE.getListContactMediumResponseFromContactMedium(contactMediums);
        return response;
    }

    @Override
    public List<GetListContactMediumResponse> findAllOrderByValueAsc() {
        List<ContactMedium> contactMediums = contactMediumRepository.findAllByOrderByValueAsc();
        List<GetListContactMediumResponse> response = ContactMediumMapper.INSTANCE.getListContactMediumResponseFromContactMedium(contactMediums);
        return response;
    }
}
