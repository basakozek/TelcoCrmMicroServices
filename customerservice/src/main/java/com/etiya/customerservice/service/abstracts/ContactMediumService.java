package com.etiya.customerservice.service.abstracts;


import com.etiya.customerservice.service.requests.contactMedium.CreateContactMediumRequest;
import com.etiya.customerservice.service.requests.contactMedium.UpdateContactMediumRequest;
import com.etiya.customerservice.service.responses.contactMedium.CreatedContactMediumResponse;
import com.etiya.customerservice.service.responses.contactMedium.GetContactMediumResponse;
import com.etiya.customerservice.service.responses.contactMedium.GetListContactMediumResponse;
import com.etiya.customerservice.service.responses.contactMedium.UpdatedContactMediumResponse;

import java.util.List;

public interface ContactMediumService {

    CreatedContactMediumResponse add(CreateContactMediumRequest request);
    List<GetListContactMediumResponse> getAll();
    GetContactMediumResponse getById(int id);
    UpdatedContactMediumResponse update(UpdateContactMediumRequest request);
    void delete(int id);
    void softDelete(int id);

    List<GetListContactMediumResponse> findAllByTypeMail();
    List<GetListContactMediumResponse> findAllByValueStartingPrefix(String prefix);
    List<GetListContactMediumResponse> findAllOrderByValueAsc();

}
