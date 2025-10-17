package com.etiya.customerservice.service.rules;

import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.localization.LocalizationService;
import com.etiya.customerservice.repository.DistrictRepository;
import com.etiya.customerservice.service.messages.Messages;
import org.springframework.stereotype.Service;

@Service
public class DistrictBusinessRules {
    private final DistrictRepository districtRepository;
    private final LocalizationService localizationService;

    public DistrictBusinessRules(DistrictRepository districtRepository, LocalizationService localizationService) {
        this.districtRepository = districtRepository;
        this.localizationService = localizationService;
    }

    public void checkAddressExistenceBeforeDelete(int id){
        if (districtRepository.existsAddress(id)){
            throw new BusinessException(localizationService.getMessage(Messages.AddressExistsInDistrict));
        }

    }

    public void checkCityExistence(int id){
        if(districtRepository.existCity(id)){
            throw new BusinessException(localizationService.getMessage(Messages.CityNotExistsInDistrict));
        }
    }
}
