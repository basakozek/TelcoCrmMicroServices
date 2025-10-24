package com.etiya.customerservice.service.abstracts;



import com.etiya.customerservice.service.requests.address.CreateAddressRequest;
import com.etiya.customerservice.service.requests.address.UpdateAddressRequest;
import com.etiya.customerservice.service.responses.address.CreatedAddressResponse;
import com.etiya.customerservice.service.responses.address.GetAddressResponse;
import com.etiya.customerservice.service.responses.address.GetListAddressResponse;
import com.etiya.customerservice.service.responses.address.UpdatedAddressResponse;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    CreatedAddressResponse add(CreateAddressRequest CreateAddressRequest);
    List<GetListAddressResponse> getList();
    UpdatedAddressResponse update(UpdateAddressRequest UpdateAddressRequest);
    GetAddressResponse getById(int id);
    List<GetListAddressResponse> findDefaultAddresses();

    List<GetListAddressResponse> findByDescriptionNative(String keyword);

    List<GetListAddressResponse> findByStreetContaining(String streetPart);

    void delete(int id);
    void softDelete(int id);
    List<GetListAddressResponse> findByCustomerId(UUID customerId);
}
