package com.etiya.customerservice.service.rules;

import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.localization.LocalizationService;
import com.etiya.customerservice.repository.AddressRepository;
import com.etiya.customerservice.service.messages.Messages;
import org.springframework.stereotype.Service;

@Service
public class AddressBusinessRules {
    private final AddressRepository addressRepository;
    private final LocalizationService localizationService;

    public AddressBusinessRules(AddressRepository addressRepository, LocalizationService localizationService) {
        this.addressRepository = addressRepository;
        this.localizationService = localizationService;
    }

    public void checkBillingAccountExistenceBeforeDelete(int id){
        if (addressRepository.existsActiveBA(id)){
            throw new BusinessException(localizationService.getMessage(Messages.BillingAccountExistsInAddress));
        }

    }
}
