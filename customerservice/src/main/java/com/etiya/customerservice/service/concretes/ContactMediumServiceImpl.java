package com.etiya.customerservice.service.concretes;

import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.events.contactmedium.CreateContactMediumEvent;
import com.etiya.common.events.contactmedium.DeleteContactMediumEvent;
import com.etiya.common.events.contactmedium.SoftDeleteContactMediumEvent;
import com.etiya.common.events.contactmedium.UpdateContactMediumEvent;
import com.etiya.customerservice.domain.entities.ContactMedium;
import com.etiya.customerservice.domain.entities.Customer;
import com.etiya.customerservice.repository.ContactMediumRepository;
import com.etiya.customerservice.service.abstracts.ContactMediumService;
import com.etiya.customerservice.service.mappings.ContactMediumMapper;
import com.etiya.customerservice.service.requests.contactMedium.*;
import com.etiya.customerservice.service.responses.contactMedium.*;
import com.etiya.customerservice.service.rules.ContactMediumBusinessRules;
import com.etiya.customerservice.transport.kafka.producer.contactmedium.CreateContactMediumProducer;
import com.etiya.customerservice.transport.kafka.producer.contactmedium.DeleteContactMediumProducer;
import com.etiya.customerservice.transport.kafka.producer.contactmedium.SoftDeleteContactMediumProducer;
import com.etiya.customerservice.transport.kafka.producer.contactmedium.UpdateContactMediumProducer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContactMediumServiceImpl implements ContactMediumService {

    private final ContactMediumRepository contactMediumRepository;
    private final ContactMediumBusinessRules contactMediumBusinessRules;
    private final CreateContactMediumProducer createContactMediumProducer;
    private final UpdateContactMediumProducer updateContactMediumProducer;
    private final DeleteContactMediumProducer deleteContactMediumProducer;
    private final SoftDeleteContactMediumProducer softDeleteContactMediumProducer;


    public ContactMediumServiceImpl(ContactMediumRepository contactMediumRepository, ContactMediumBusinessRules contactMediumBusinessRules, CreateContactMediumProducer createContactMediumProducer, UpdateContactMediumProducer updateContactMediumProducer, DeleteContactMediumProducer deleteContactMediumProducer, SoftDeleteContactMediumProducer softDeleteContactMediumProducer) {
        this.contactMediumRepository = contactMediumRepository;
        this.contactMediumBusinessRules = contactMediumBusinessRules;
        this.createContactMediumProducer = createContactMediumProducer;
        this.updateContactMediumProducer = updateContactMediumProducer;
        this.deleteContactMediumProducer = deleteContactMediumProducer;
        this.softDeleteContactMediumProducer = softDeleteContactMediumProducer;
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
    public List<CreatedContactMediumResponse> addAsList(CreateContactMediumListRequest request) {
        UUID customerId = request.getCustomerId();

        // Business rules: müşteri aktif mi, mevcut mu
        contactMediumBusinessRules.checkCustomerBeforeAdd(customerId);

        // Liste kontrolü: email ve mobile_phone zorunlu
        boolean hasEmail = request.getContactMediums().stream()
                .anyMatch(m -> m.getType().equalsIgnoreCase("email"));
        boolean hasMobilePhone = request.getContactMediums().stream()
                .anyMatch(m -> m.getType().equalsIgnoreCase("mobile_phone"));

        if (!hasEmail || !hasMobilePhone) {
            throw new BusinessException("Both email and mobile_phone contact mediums are required.");
        }

        List<CreatedContactMediumResponse> responses = new ArrayList<>();

        for (CreateContactMediumItem item : request.getContactMediums()) {
            // isPrimary kontrolü (her bir item için)
            contactMediumBusinessRules.checkPrimaryStatus(customerId, item.isPrimary());

            // DTO’dan entity oluştur
            CreateContactMediumRequest singleRequest = new CreateContactMediumRequest();
            singleRequest.setCustomerId(customerId);
            singleRequest.setType(item.getType());
            singleRequest.setValue(item.getValue());
            singleRequest.setPrimary(item.isPrimary());

            ContactMedium contactMedium = ContactMediumMapper.INSTANCE.contactMediumFromContactMediumRequest(singleRequest);
            ContactMedium created = contactMediumRepository.save(contactMedium);

            // Event
            CreateContactMediumEvent event = new CreateContactMediumEvent(
                    created.getCustomer().getId().toString(),
                    created.getId(),
                    created.getType(),
                    created.getValue(),
                    created.isPrimary()
            );
            createContactMediumProducer.produceContactMediumCreated(event);

            // Response
            responses.add(ContactMediumMapper.INSTANCE.createdContactMediumResponseFromContactMedium(created));
        }
        return responses;
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
    public UpdatedContactMediumResponse update(int id, UpdateContactMediumRequest request) {
        ContactMedium existingContactMedium = contactMediumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact Medium Not Found"));

        ContactMediumMapper.INSTANCE.contactMediumFromUpdateRequest(request, existingContactMedium);
        ContactMedium saved = contactMediumRepository.save(existingContactMedium);

        UpdateContactMediumEvent event = new UpdateContactMediumEvent(
                saved.getCustomer().getId().toString(),
                saved.getId(),
                saved.getType(),
                saved.getValue(),
                saved.isPrimary()
        );
        updateContactMediumProducer.produceContactMediumUpdated(event);
        return ContactMediumMapper.INSTANCE.updatedContactMediumResponseFromContactMedium(saved);
    }

    @Override
    @Transactional
    public UpdatedContactMediumListResponse updateAsList(UpdateContactMediumListRequest request) {
        List<UpdatedContactMediumResponse> updatedList = new ArrayList<>();

        for (UpdateContactMediumItem item : request.getContactMediums()) {

            ContactMedium existing;

            if (item.getId() != null) {
                // --- MEVCUT GÜNCELLEME YOLU (AYNEN KALSIN) ---
                existing = contactMediumRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Contact Medium with ID " + item.getId() + " not found"));

                if (!existing.getCustomer().getId().equals(request.getCustomerId())) {
                    throw new RuntimeException("Customer ID mismatch for contact medium id " + item.getId());
                }

            } else {
                // --- MINIMAL UPSERT EKİ ---
                existing = contactMediumRepository
                        .findByCustomerIdAndTypeIgnoreCase(request.getCustomerId(), item.getType())
                        .orElse(null);

                if (existing == null) {
                    // kayıt yoksa: küçük bir create (addAsList’i çağırmadan, inline)
                    ContactMedium cm = new ContactMedium();
                    // müşteri referansı: lazy referans yeterli
                    Customer customerRef = new Customer();
                    customerRef.setId(request.getCustomerId());
                    cm.setCustomer(customerRef);
                    cm.setType(item.getType());
                    cm.setValue(item.getValue());
                    cm.setPrimary(item.isPrimary());

                    // varsa mevcut primary kuralını yine işletelim
                    contactMediumBusinessRules.checkPrimaryStatus(request.getCustomerId(), item.isPrimary());

                    ContactMedium created = contactMediumRepository.save(cm);

                    // created event (mevcut producer’ını kullan)
                    createContactMediumProducer.produceContactMediumCreated(
                            new CreateContactMediumEvent(
                                    created.getCustomer().getId().toString(),
                                    created.getId(),
                                    created.getType(),
                                    created.getValue(),
                                    created.isPrimary()
                            )
                    );

                    updatedList.add(ContactMediumMapper.INSTANCE.updatedContactMediumResponseFromContactMedium(created));
                    // bu item tamam; sonraki item’a geç
                    continue;
                }
                // buradan sonra "existing" var → aşağıdaki update path’e düşecek
            }

            // --- MEVCUT MAPPER + SAVE (AYNEN KALSIN) ---
            ContactMediumMapper.INSTANCE.contactMediumFromUpdateRequest(
                    new UpdateContactMediumRequest(
                            request.getCustomerId(),
                            item.getType(),
                            item.getValue(),
                            item.isPrimary()
                    ),
                    existing
            );

            ContactMedium saved = contactMediumRepository.save(existing);

            // update event (aynen)
            updateContactMediumProducer.produceContactMediumUpdated(
                    new UpdateContactMediumEvent(
                            saved.getCustomer().getId().toString(),
                            saved.getId(),
                            saved.getType(),
                            saved.getValue(),
                            saved.isPrimary()
                    )
            );

            updatedList.add(ContactMediumMapper.INSTANCE.updatedContactMediumResponseFromContactMedium(saved));
        }

        return new UpdatedContactMediumListResponse(request.getCustomerId(), updatedList);
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
        ContactMedium savedContactMedium = contactMediumRepository.save(contactMedium);

        SoftDeleteContactMediumEvent event = new SoftDeleteContactMediumEvent(
                savedContactMedium.getCustomer().getId().toString(),
                savedContactMedium.getId(),
                savedContactMedium.getDeletedDate().toString()
        );

        softDeleteContactMediumProducer.produceContactMediumSoftDeleted(event);
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
    @Override
    public List<GetListContactMediumResponse> getByCustomerId(UUID customerId) {
        List<ContactMedium> contactMediums = contactMediumRepository.findAllByCustomerId(customerId);
        return ContactMediumMapper.INSTANCE.getListContactMediumResponseFromContactMedium(contactMediums);
    }

}
