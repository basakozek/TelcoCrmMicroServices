package com.etiya.catalogservice.service.concretes;

import com.etiya.catalogservice.domain.entities.Campaign;
import com.etiya.catalogservice.repository.CampaignRepository;
import com.etiya.catalogservice.service.abstracts.CampaignService;
import com.etiya.catalogservice.service.dtos.request.campaign.CreateCampaignRequest;
import com.etiya.catalogservice.service.dtos.response.campaign.CreatedCampaignResponse;
import com.etiya.catalogservice.service.mappers.CampaignMapper;
import org.springframework.stereotype.Service;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public CreatedCampaignResponse createCampaign(CreateCampaignRequest request) {
        Campaign campaign = CampaignMapper.INSTANCE.campaignFromCreateCampaignRequest(request);

        Campaign savedCampaign = campaignRepository.save(campaign);


        CreatedCampaignResponse response = CampaignMapper.INSTANCE.createdCampaignResponseFromCampaign(savedCampaign);
        return response;
    }
}
