package com.etiya.customerservice.service.concretes;

import com.etiya.customerservice.domain.entities.Address;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillingAccountServiceImpl implements BillingAccountService {

    public final BillingAccountRepository billingAccountRepository;
    public final BillingAccountBusinessRules billingAccountBusinessRules;

    public BillingAccountServiceImpl(BillingAccountRepository billingAccountRepository, BillingAccountBusinessRules billingAccountBusinessRules) {
        this.billingAccountRepository = billingAccountRepository;
        this.billingAccountBusinessRules = billingAccountBusinessRules;
    }

    @Override
    public CreatedBillingAccountResponse add(CreateBillingAccountRequest request) {
        billingAccountBusinessRules.checkAddressMatchWithCustomerInBillingAccount(request.getAddressId(), request.getCustomerId());
        //billingAccountBusinessRules.checkTypesBetweenBillingAccountAndCustomer(request.getType());
        BillingAccount billingAccount = BillingAccountMapper.INSTANCE.billingAccountFromBillingAccountRequest(request);

        BillingAccount createdBillingAccount = billingAccountRepository.save(billingAccount);

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
    public UpdatedBillingAccountResponse update(UpdateBillingAccountRequest request) {
        billingAccountBusinessRules.typeCannotBeChanged(request.getId(), request.getType());
        billingAccountBusinessRules.statusRules(request.getId(), request.getStatus());
        BillingAccount billingAccount = billingAccountRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("billing account not found"));

        if (request.getAccountName() != null) {
            billingAccount.setAccountName(request.getAccountName());
        }
        if (request.getStatus() != null) {
            billingAccount.setStatus(request.getStatus());
        }
        if (request.getType() != null) {
            billingAccount.setType(request.getType());
        }
        if (request.getAddressId() != null) {
            Address address = new Address();
            address.setId(request.getAddressId());
            billingAccount.setAddress(address);
        }

        BillingAccount saved = billingAccountRepository.save(billingAccount);

        UpdatedBillingAccountResponse resp = new UpdatedBillingAccountResponse();
        resp.setId(saved.getId());
        resp.setAccountName(saved.getAccountName());
        resp.setAccountNumber(saved.getAccountNumber());
        resp.setStatus(saved.getStatus());
        resp.setType(saved.getType());
        resp.setCustomerId(saved.getCustomer().getId());
        resp.setAddressId(saved.getAddress().getId());
        resp.setCityName(saved.getAddress().getDistrict().getCity().getName());
        resp.setDistrictName(saved.getAddress().getDistrict().getName());
        return resp;
    }


    @Override
    public void delete(int id) {
        BillingAccount billingAccount = billingAccountRepository.findById(id).orElseThrow(()-> new RuntimeException("Billing account not found"));
        billingAccountRepository.delete(billingAccount);
    }

    @Override
    public void softDelete(int id) {
        billingAccountBusinessRules.checkStatusBeforeDelete(id);
        BillingAccount billingAccount = billingAccountRepository.findById(id).orElseThrow(()-> new RuntimeException("Billing account not found"));
        billingAccount.setDeletedDate(LocalDateTime.now());
        billingAccountRepository.save(billingAccount);
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
}
