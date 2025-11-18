package com.etiya.customerservice.service.concretes;

import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.events.billingaccount.CreateBillingAccountEvent;
import com.etiya.common.events.billingaccount.DeleteBillingAccountEvent;
import com.etiya.common.events.billingaccount.SoftDeleteBillingAccountEvent;
import com.etiya.common.events.billingaccount.UpdateBillingAccountEvent;
import com.etiya.common.responses.BillingAccountResponse;
import com.etiya.customerservice.domain.entities.BillingAccount;
import com.etiya.customerservice.repository.BillingAccountRepository;
import com.etiya.customerservice.service.abstracts.BillingAccountService;
import com.etiya.customerservice.service.mappings.BillingAccountMapper;
import com.etiya.customerservice.service.requests.billingAccount.CreateBillingAccountRequest;
import com.etiya.customerservice.service.requests.billingAccount.UpdateBillingAccountRequest;
import com.etiya.customerservice.service.responses.billingAccount.CreatedBillingAccountResponse;
import com.etiya.customerservice.service.responses.billingAccount.GetBillingAccountResponse;
import com.etiya.customerservice.service.responses.billingAccount.GetListBillingAccountResponse;
import com.etiya.customerservice.service.responses.billingAccount.UpdatedBillingAccountResponse;
import com.etiya.customerservice.service.rules.BillingAccountBusinessRules;
import com.etiya.customerservice.transport.kafka.producer.billingaccount.CreateBillingAccountProducer;
import com.etiya.customerservice.transport.kafka.producer.billingaccount.DeleteBillingAccountProducer;
import com.etiya.customerservice.transport.kafka.producer.billingaccount.SoftDeleteBillingAccountProducer;
import com.etiya.customerservice.transport.kafka.producer.billingaccount.UpdateBillingAccountProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class  BillingAccountServiceImpl implements BillingAccountService {

    public final BillingAccountRepository billingAccountRepository;
    public final BillingAccountBusinessRules billingAccountBusinessRules;
    public final CreateBillingAccountProducer createBillingAccountProducer;
    public final UpdateBillingAccountProducer updateBillingAccountProducer;
    public final DeleteBillingAccountProducer deleteBillingAccountProducer;
    public final SoftDeleteBillingAccountProducer  softDeleteBillingAccountProducer;

    public BillingAccountServiceImpl(BillingAccountRepository billingAccountRepository, BillingAccountBusinessRules billingAccountBusinessRules, CreateBillingAccountProducer createBillingAccountProducer, UpdateBillingAccountProducer updateBillingAccountProducer, DeleteBillingAccountProducer deleteBillingAccountProducer, SoftDeleteBillingAccountProducer softDeleteBillingAccountProducer) {
        this.billingAccountRepository = billingAccountRepository;
        this.billingAccountBusinessRules = billingAccountBusinessRules;
        this.createBillingAccountProducer = createBillingAccountProducer;
        this.updateBillingAccountProducer = updateBillingAccountProducer;
        this.deleteBillingAccountProducer = deleteBillingAccountProducer;
        this.softDeleteBillingAccountProducer = softDeleteBillingAccountProducer;
    }

    @Override
    public CreatedBillingAccountResponse add(CreateBillingAccountRequest request) {
        billingAccountBusinessRules.checkAddressMatchWithCustomerInBillingAccount(request.getAddressId(), request.getCustomerId());
        //billingAccountBusinessRules.checkTypesBetweenBillingAccountAndCustomer(request.getType());
        BillingAccount billingAccount = BillingAccountMapper.INSTANCE.billingAccountFromBillingAccountRequest(request);
        billingAccount.setStatus("Active");
        BillingAccount createdBillingAccount = billingAccountRepository.save(billingAccount);

        CreateBillingAccountEvent event = new CreateBillingAccountEvent(
                createdBillingAccount.getCustomer().getId().toString(),
                createdBillingAccount.getAddress().getId(),
                createdBillingAccount.getId(),
                createdBillingAccount.getType(),
                createdBillingAccount.getStatus(),
                createdBillingAccount.getAccountNumber(),
                createdBillingAccount.getAccountName()
        );

        createBillingAccountProducer.produceBillingAccountCreated(event);

        CreatedBillingAccountResponse response = BillingAccountMapper.INSTANCE.createdBillingAccountFromBillingAccount(createdBillingAccount);
        return response;
    }


    @Override
    public List<GetListBillingAccountResponse> getAll() {
        List<BillingAccount> billingAccounts = billingAccountRepository.findAll();
        List<GetListBillingAccountResponse> responseList = BillingAccountMapper.INSTANCE.getListBillingAccountResponseFromBillingAccount(billingAccounts);
        return responseList;
    }

    @Override
    public GetBillingAccountResponse getById(int id) {
        BillingAccount billingAccount = billingAccountRepository.findById(id).orElseThrow(() -> new RuntimeException("billing account not found"));
        GetBillingAccountResponse response = BillingAccountMapper.INSTANCE.getBillingAccountResponseFromBillingAccount(billingAccount);
        return response;

    }

    @Override
    public UpdatedBillingAccountResponse update(int id,UpdateBillingAccountRequest request) {
        BillingAccount existingBillingAccount = billingAccountRepository.findById(id).orElseThrow(() -> new RuntimeException("billing account not found"));

        BillingAccountMapper.INSTANCE.billingAccountFromUpdateBillingAccountRequest(request, existingBillingAccount);
        BillingAccount saved = billingAccountRepository.save(existingBillingAccount);


        UpdateBillingAccountEvent event = new UpdateBillingAccountEvent(
                saved.getId(),
                saved.getCustomer().getId().toString(),
                saved.getAddress().getId(),
                saved.getType(),
                saved.getStatus(),
                saved.getAccountNumber(),
                saved.getAccountName()
        );

        updateBillingAccountProducer.produceBillingAccountUpdated(event);
        return BillingAccountMapper.INSTANCE.updatedBillingAccountResponseFromBillingAccount(saved);
    }


    @Override
    public void delete(int id) {
        BillingAccount billingAccount = billingAccountRepository.findById(id).orElseThrow(()-> new RuntimeException("Billing account not found"));

        DeleteBillingAccountEvent event = new DeleteBillingAccountEvent(
                billingAccount.getCustomer().getId().toString(),
                id
        );

        deleteBillingAccountProducer.produceBillingAccountDeleted(event);

        billingAccountRepository.delete(billingAccount);
    }

    @Override
    public void softDelete(int id) {
        billingAccountBusinessRules.checkStatusBeforeDelete(id);
        BillingAccount billingAccount = billingAccountRepository.findById(id).orElseThrow(()-> new RuntimeException("Billing account not found"));
        billingAccount.setStatus("Inactive");
        billingAccount.setDeletedDate(LocalDateTime.now());
        BillingAccount savedBillingAccount = billingAccountRepository.save(billingAccount);
        SoftDeleteBillingAccountEvent event = new SoftDeleteBillingAccountEvent(
                savedBillingAccount.getCustomer().getId().toString(),
                savedBillingAccount.getId(),
                savedBillingAccount.getDeletedDate().toString()
        );
        softDeleteBillingAccountProducer.produceBillingAccountSoftDeleted(event);
    }



    @Override
    public List<GetListBillingAccountResponse> findAllByOrderByAccountNameDesc() {
        List<BillingAccount> billingAccounts = billingAccountRepository.findAllByOrderByAccountNameDesc();
        List<GetListBillingAccountResponse> responseList = BillingAccountMapper.INSTANCE.getListBillingAccountResponseFromBillingAccount(billingAccounts);
        return responseList;
    }

    @Override
    public List<GetListBillingAccountResponse> findAllByTypeCorporate() {
        List<BillingAccount> billingAccounts = billingAccountRepository.findAllByTypeCorporate();
        List<GetListBillingAccountResponse> responseList = BillingAccountMapper.INSTANCE.getListBillingAccountResponseFromBillingAccount(billingAccounts);
        return responseList;
    }

    @Override
    public List<GetListBillingAccountResponse> findAllByAccountNameStartingPrefix(String prefix) {
        List<BillingAccount> billingAccounts = billingAccountRepository.findAllByAccountNameStartingPrefix(prefix);
        List<GetListBillingAccountResponse> responseList = BillingAccountMapper.INSTANCE.getListBillingAccountResponseFromBillingAccount(billingAccounts);
        return responseList;
    }
    @Override
    public List<GetListBillingAccountResponse> findActiveByCustomerId(UUID customerId) {
        List<BillingAccount> billingAccounts = billingAccountRepository.findActiveByCustomerId(customerId);
        return BillingAccountMapper.INSTANCE.getListBillingAccountResponseFromBillingAccount(billingAccounts);
    }



    @Override
    public BillingAccountResponse getByIdForBasket(int id) {
        return billingAccountRepository.findById(id).stream().map(this::mapToResponse).findFirst().orElseThrow(() -> new BusinessException("Billing account not found"));
    }

    private BillingAccountResponse mapToResponse(BillingAccount billingAccount) {
        BillingAccountResponse response = new BillingAccountResponse();
        response.setId(billingAccount.getId());
        return response;
    };
}
