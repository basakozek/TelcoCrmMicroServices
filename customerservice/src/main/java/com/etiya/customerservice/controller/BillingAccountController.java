package com.etiya.customerservice.controller;

import com.etiya.customerservice.service.abstracts.BillingAccountService;
import com.etiya.customerservice.service.requests.billingAccount.CreateBillingAccountRequest;
import com.etiya.customerservice.service.requests.billingAccount.UpdateBillingAccountRequest;
import com.etiya.customerservice.service.responses.billingAccount.CreatedBillingAccountResponse;
import com.etiya.customerservice.service.responses.billingAccount.GetBillingAccountResponse;
import com.etiya.customerservice.service.responses.billingAccount.GetListBillingAccountResponse;
import com.etiya.customerservice.service.responses.billingAccount.UpdatedBillingAccountResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing-accounts")
public class BillingAccountController {
    private final BillingAccountService billingAccountService;

    public BillingAccountController(BillingAccountService billingAccountService) {
        this.billingAccountService = billingAccountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedBillingAccountResponse add(@Valid @RequestBody CreateBillingAccountRequest request) {
        return billingAccountService.add(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GetListBillingAccountResponse> getAll() {
        return billingAccountService.getAll();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {
        billingAccountService.delete(id);
    }

    @DeleteMapping("{id}/soft")
    @ResponseStatus(HttpStatus.OK)
    public void softDelete(@PathVariable int id) {
        billingAccountService.softDelete(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetBillingAccountResponse getByIdBillingAccount(@PathVariable int id) {
        return billingAccountService.getById(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UpdatedBillingAccountResponse update(@PathVariable int id, @RequestBody UpdateBillingAccountRequest request) {
        request.setId(id);
        return billingAccountService.update(request);
    }


    @GetMapping("findAllByOrderByAccountNameDesc")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListBillingAccountResponse> findAllByOrderByAccountNameDesc() {
        return billingAccountService.findAllByOrderByAccountNameDesc();
    }

    @GetMapping("findAllByTypeCreated")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListBillingAccountResponse> findAllByTypeCreated() {
        return billingAccountService.findAllByTypeCorporate();
    }

    @GetMapping("findAllByAccountNameStartingPrefix/{prefix}")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListBillingAccountResponse> findAllByAccountNameStartingPrefix(String prefix) {
        return billingAccountService.findAllByAccountNameStartingPrefix(prefix);
    }
}
