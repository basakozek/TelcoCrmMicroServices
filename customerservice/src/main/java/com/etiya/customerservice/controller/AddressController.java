package com.etiya.customerservice.controller;

import com.etiya.customerservice.service.abstracts.AddressService;
import com.etiya.customerservice.service.requests.address.CreateAddressRequest;
import com.etiya.customerservice.service.requests.address.UpdateAddressRequest;
import com.etiya.customerservice.service.responses.address.CreatedAddressResponse;
import com.etiya.customerservice.service.responses.address.GetAddressResponse;
import com.etiya.customerservice.service.responses.address.GetListAddressResponse;
import com.etiya.customerservice.service.responses.address.UpdatedAddressResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController( AddressService addressService) {
        this.addressService = addressService;
    }


    /*
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Address address) {
        addressService.add(address);
    }*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedAddressResponse add(@Valid @RequestBody CreateAddressRequest request) {
        return addressService.add(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GetListAddressResponse> getAll() { return addressService.getList();}

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetAddressResponse getById(@PathVariable Integer id){
        return addressService.getById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {
        addressService.delete(id);
    }

    @DeleteMapping("{id}/soft")
    @ResponseStatus(HttpStatus.OK)
    public void softDelete(@PathVariable int id) {
        addressService.softDelete(id);
    }

    @GetMapping("findDefaultAddresses")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListAddressResponse> findDefaultAddresses(){
        return addressService.findDefaultAddresses();
    }

    @GetMapping("findByDescriptionNative/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListAddressResponse> findByDescriptionNative(@PathVariable String keyword){
        return addressService.findByDescriptionNative(keyword);
    }

    @GetMapping("findByStreetContaining/{streetPart}")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListAddressResponse> findByStreetContaining(@PathVariable String streetPart){
        return addressService.findByStreetContaining(streetPart);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UpdatedAddressResponse update(@Valid @RequestBody UpdateAddressRequest request) {
        return addressService.update(request);
    }
}
