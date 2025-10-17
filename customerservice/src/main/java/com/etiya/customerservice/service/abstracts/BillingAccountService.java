package com.etiya.customerservice.service.abstracts;

import com.etiya.customerservice.service.requests.billingAccount.CreateBillingAccountRequest;
import com.etiya.customerservice.service.requests.billingAccount.UpdateBillingAccountRequest;
import com.etiya.customerservice.service.responses.billingAccount.CreatedBillingAccountResponse;
import com.etiya.customerservice.service.responses.billingAccount.GetBillingAccountResponse;
import com.etiya.customerservice.service.responses.billingAccount.GetListBillingAccountResponse;
import com.etiya.customerservice.service.responses.billingAccount.UpdatedBillingAccountResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillingAccountService {
    CreatedBillingAccountResponse add(CreateBillingAccountRequest request);
    List<GetListBillingAccountResponse> getAll();
    GetBillingAccountResponse getById(int id);
    UpdatedBillingAccountResponse update(UpdateBillingAccountRequest request);
    void delete(int id);
    void softDelete(int id);

    List<GetListBillingAccountResponse> findAllByOrderByAccountNameDesc();
    List<GetListBillingAccountResponse> findAllByTypeCorporate();
    List<GetListBillingAccountResponse> findAllByAccountNameStartingPrefix(String prefix);



}

