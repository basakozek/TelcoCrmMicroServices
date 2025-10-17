package com.etiya.customerservice.service.rules;

import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.localization.LocalizationService;
import com.etiya.customerservice.domain.entities.Customer;
import com.etiya.customerservice.repository.CustomerRepository;
import com.etiya.customerservice.service.messages.Messages;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public abstract class CustomerBusinessRules<T extends Customer> {
    private final CustomerRepository<T> customerRepository;
    private final LocalizationService localizationService;

    public CustomerBusinessRules(CustomerRepository<T> customerRepository, LocalizationService localizationService) {
        this.customerRepository = customerRepository;
        this.localizationService = localizationService;
    }

    public void checkIfCustomerId(UUID id) {
        if(!customerRepository.existsById(id) ){
            throw new BusinessException(localizationService.getMessage(Messages.CustomerIdNotExists));
        }
    }
}
