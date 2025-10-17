package com.etiya.customerservice.controller;

import com.etiya.customerservice.service.abstracts.DistrictService;
import com.etiya.customerservice.service.requests.district.CreateDistrictRequest;
import com.etiya.customerservice.service.responses.district.CreatedDistrictResponse;
import com.etiya.customerservice.service.responses.district.GetDistrictResponse;
import com.etiya.customerservice.service.responses.district.GetListDistrictResponse;
import com.etiya.customerservice.service.responses.district.GetListDistrictWithAddressesResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {

    private final DistrictService districtService;

    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedDistrictResponse add(@Valid @RequestBody CreateDistrictRequest request) {
        return districtService.add(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GetListDistrictResponse> getList() {return districtService.getAll();}

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetDistrictResponse getById(@PathVariable int id) {
        return districtService.getById(id);
    }

    @GetMapping("findDistrictWithAddresses")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListDistrictWithAddressesResponse> findDistrictWithAddresses() {
        return districtService.findDistrictWithAddresses();
    }

    @GetMapping("findDistrictByNamePrefix/{prefix}")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListDistrictResponse> findDistrictByNamePrefix(@PathVariable String prefix) {
        return districtService.findDistrictByNamePrefix(prefix);
    }

    @GetMapping("findDistrictByName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListDistrictResponse> findDistrictByName(@RequestParam String name) {
        return districtService.findDistrictByName(name);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {
        districtService.delete(id);
    }
}


