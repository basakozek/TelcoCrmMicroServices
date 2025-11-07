package com.etiya.catalogservice.service.dtos.response.catalogProductOffer;

import lombok.Getter; import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class CatalogProductOfferWithDetailResponse {
    private int catalogProductOfferId;

    private int catalogId;

    private int productOfferId;
    private String productOfferName;
    private String productOfferDescription;
    private double discountRate;       // normalize edilmiş: 0..1
    private String status;             // ACTIVE / INACTIVE / None
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String productId;
    private String productName;
    private double productPrice;
}
