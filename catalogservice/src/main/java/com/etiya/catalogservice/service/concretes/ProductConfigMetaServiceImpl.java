package com.etiya.catalogservice.service.concretes;

// package: com.etiya.catalogservice.service.concretes;

import com.etiya.catalogservice.domain.entities.*;
import com.etiya.catalogservice.repository.ProductOfferRepository;
import com.etiya.catalogservice.service.abstracts.ProductConfigMetaService;
import com.etiya.catalogservice.service.dtos.response.productConfig.ProductConfigCharacteristicResponse;
import com.etiya.catalogservice.service.dtos.response.productConfig.ProductConfigMetaResponse;
import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductConfigMetaServiceImpl implements ProductConfigMetaService {

    private final ProductOfferRepository productOfferRepository;

    public ProductConfigMetaServiceImpl(ProductOfferRepository productOfferRepository) {
        this.productOfferRepository = productOfferRepository;
    }

    @Override
    public ProductConfigMetaResponse getConfigMeta(String productOfferId) {

        ProductOffer offer = productOfferRepository.findById(productOfferId)
                .orElseThrow(() -> new BusinessException("ProductOffer not found: " + productOfferId));

        ProductSpecification spec = offer.getProductSpecification();
        if (spec == null || spec.getProductSpecCharacteristics() == null) {
            // Bu ürün için konfigürasyon yoksa boş liste döneriz
            return new ProductConfigMetaResponse(
                    offer.getId(),
                    offer.getName(),
                    Collections.emptyList()
            );
        }

        List<ProductSpecCharacteristic> pscList = spec.getProductSpecCharacteristics();

        List<ProductConfigCharacteristicResponse> charResponses = pscList.stream()
                .map(psc -> {
                    Characteristic ch = psc.getCharacteristic();

                    // Allowed values: CharValues tablosundan
                    List<String> allowed = ch.getCharacteristicValues() == null
                            ? Collections.emptyList()
                            : ch.getCharacteristicValues().stream()
                            .map(CharacteristicValue::getValue)
                            .collect(Collectors.toList());

                    // Default value: bu productOffer için ProdOfferCharValues üzerinden
                    String defaultValue = null;

                    if (offer.getProdOfferCharValues() != null) {
                        Optional<ProdOfferCharValues> pov = offer.getProdOfferCharValues().stream()
                                .filter(pv -> pv.getCharacteristicValue() != null
                                        && pv.getCharacteristicValue().getCharacteristic() != null
                                        && pv.getCharacteristicValue().getCharacteristic().getId() == ch.getId())
                                .findFirst();

                        if (pov.isPresent()) {
                            defaultValue = pov.get().getCharacteristicValue().getValue();
                        }
                    }

                    return new ProductConfigCharacteristicResponse(
                            ch.getName(),               // key -> "Pstn No", "XDSL User Name"...
                            ch.getDataType(),
                            ch.getUnitOfMeasure(),
                            psc.isRequired(),
                            allowed,
                            defaultValue
                    );
                })
                .collect(Collectors.toList());

        return new ProductConfigMetaResponse(
                offer.getId(),
                offer.getName(),
                charResponses
        );
    }
}

