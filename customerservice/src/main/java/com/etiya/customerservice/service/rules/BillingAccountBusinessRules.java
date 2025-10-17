package com.etiya.customerservice.service.rules;

import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.localization.LocalizationService;
import com.etiya.customerservice.repository.BillingAccountRepository;
import com.etiya.customerservice.service.messages.Messages;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BillingAccountBusinessRules {

    private final BillingAccountRepository billingAccountRepository;
    private final LocalizationService localizationService;

    public BillingAccountBusinessRules(BillingAccountRepository billingAccountRepository, LocalizationService localizationService) {
        this.billingAccountRepository = billingAccountRepository;
        this.localizationService = localizationService;
    }

    public void checkAddressMatchWithCustomerInBillingAccount(int addressId, UUID customerId)
    {
        if(!billingAccountRepository.checkAddressContainsCustomer(addressId, customerId))
        {
            throw new BusinessException(localizationService.getMessage(Messages.BillingAccountBoundingNotExists));
        }
    }

    public void typeCannotBeChanged(int id, String type) {
        if(!billingAccountRepository.findById(id).get().getType().equals(type) && type != null){
            throw new BusinessException(localizationService.getMessage(Messages.TypeCannotChange));
        }
    }


    public void statusRules(int id, String status) {
        if(billingAccountRepository.findById(id).get().getStatus().equals("suspended")){

            // 90 gün kodu
        } else if(billingAccountRepository.findById(id).get().getStatus().equals("closed")){
            if(status.equals("active")){
                throw new BusinessException(localizationService.getMessage(Messages.AlreadyClosedAccount));
            }
        }
    }

    public void checkStatusBeforeDelete(int id) {
        if(billingAccountRepository.findById(id).get().getStatus().equals("active")){
            throw new BusinessException(localizationService.getMessage(Messages.ActiveAccountCannotDeleted));
        }
    }



}
