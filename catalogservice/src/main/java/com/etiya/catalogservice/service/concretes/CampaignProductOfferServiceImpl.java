package com.etiya.catalogservice.service.concretes;

import com.etiya.catalogservice.domain.entities.Campaign;
import com.etiya.catalogservice.domain.entities.CampaignProductOffer;
import com.etiya.catalogservice.domain.entities.ProductOffer;
import com.etiya.catalogservice.repository.CampaignProductRepository;
import com.etiya.catalogservice.repository.CampaignRepository;
import com.etiya.catalogservice.repository.ProductOfferRepository;
import com.etiya.catalogservice.service.abstracts.CampaignProductOfferService;
import com.etiya.catalogservice.service.dtos.request.campaignProduct.CreateCampaignProductRequest;
import com.etiya.catalogservice.service.dtos.response.campaignProduct.CreatedCampaignProductResponse;
import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.responses.ActiveCampaignProductResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignProductOfferServiceImpl implements CampaignProductOfferService {
    private final CampaignProductRepository repository;
    private final CampaignRepository campaignRepository;
    private final ProductOfferRepository productOfferRepository; // GÜNCELLENDİ

    public CampaignProductOfferServiceImpl(CampaignProductRepository repository, CampaignRepository campaignRepository, ProductOfferRepository productOfferRepository) {
        this.repository = repository;
        this.campaignRepository = campaignRepository;
        this.productOfferRepository = productOfferRepository;
    }

    @Override
    public Optional<ActiveCampaignProductResponse> getBestActiveForProduct(String productId) {
        var list = repository.findActiveByProduct(productId);
        return list.stream()
                .max(Comparator.comparingDouble(cp -> normalize(cp.getCampaign().getDiscountRate())))
                .map(this::map);
    }

    @Override
    public List<ActiveCampaignProductResponse> getAllActive() {
        return repository.findAllActive().stream().map(this::map).toList();
    }

    @Override
    public CreatedCampaignProductResponse add(CreateCampaignProductRequest r) {
        // 1) Ürün & kampanya kontrolü
        ProductOffer productOffer = productOfferRepository.findById(r.getProductId())
                .orElseThrow(() -> new BusinessException("ProductOffer not found: " + r.getProductId()));
        Campaign campaign = campaignRepository.findById(r.getCampaignId())
                .orElseThrow(() -> new BusinessException("Campaign not found: " + r.getCampaignId()));

        // 2) Duplicate engelleme (unique constraint var; yine de önkontrol)
        boolean exists = repository.findAll().stream()
                .anyMatch(cp -> cp.getProductOffer().getId().equals(productOffer.getId())
                        && cp.getCampaign().getId() == campaign.getId());
        if (exists) {
            throw new BusinessException("This product is already added to the campaign");
        }

        // 3) Kaydet
        CampaignProductOffer cp = new CampaignProductOffer();
        cp.setProductOffer(productOffer);
        cp.setCampaign(campaign);

        try {
            cp = repository.save(cp);
        } catch (DataIntegrityViolationException ex) {
            // DB seviyesindeki unique constraint’e yakalanırsa yine anlamlı mesaj
            throw new BusinessException("This product is already added to the campaign");
        }

        // 4) Response
        CreatedCampaignProductResponse resp = new CreatedCampaignProductResponse();
        resp.setId(cp.getId());
        resp.setProductId(productOffer.getId());
        resp.setCampaignId(campaign.getId());
        return resp;
    }

    private ActiveCampaignProductResponse map(CampaignProductOffer cp) {
        var c = cp.getCampaign();
        ActiveCampaignProductResponse r = new ActiveCampaignProductResponse();
        r.setCampaignProductId(cp.getId());
        r.setCampaignId(c.getId());
        r.setCampaignName(c.getName());
        r.setProductId(cp.getProductOffer().getId());
        r.setDiscountRate(normalize(c.getDiscountRate()));
        return r;
    }

    private double normalize(double rate) {
        return rate > 1.0 ? rate / 100.0 : rate;
    }
}
