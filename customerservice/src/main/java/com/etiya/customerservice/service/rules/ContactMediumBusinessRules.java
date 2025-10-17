package com.etiya.customerservice.service.rules;

import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.localization.LocalizationService;
import com.etiya.customerservice.domain.entities.ContactMedium;
import com.etiya.customerservice.repository.ContactMediumRepository;
import com.etiya.customerservice.service.messages.Messages;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactMediumBusinessRules {
    private final ContactMediumRepository contactMediumRepository;
    private final LocalizationService localizationService;

    public ContactMediumBusinessRules(ContactMediumRepository contactMediumRepository, LocalizationService localizationService) {
        this.contactMediumRepository = contactMediumRepository;
        this.localizationService = localizationService;
    }

    public void checkPrimaryStatus(UUID customerId, boolean isPrimary) {
        if (isPrimary) {
            List<ContactMedium> primaries = contactMediumRepository.findAllByCustomerIdAndIsPrimaryTrue(customerId);
            for (ContactMedium cm : primaries) {
                cm.setPrimary(false);
                contactMediumRepository.save(cm);
            }
        }
    }

    public void checkCustomerBeforeAdd(UUID customerId) {
        if(!contactMediumRepository.existsByCustomerId(customerId)) {
            throw new BusinessException(localizationService.getMessage(Messages.CustomerNotExists));
        }

        if (!contactMediumRepository.isCustomerActive(customerId)) {
            throw new BusinessException(localizationService.getMessage(Messages.InactiveCustomer));
        }
    }

    public void checkPrimaryStatusBeforeDelete(int id) {
        if(contactMediumRepository.findById(id).get().isPrimary()) {
            throw new BusinessException(localizationService.getMessage(Messages.CantDeletePrimaryContact));
        }
    }
}
