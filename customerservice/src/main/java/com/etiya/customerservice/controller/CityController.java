package com.etiya.customerservice.controller;

import com.etiya.customerservice.service.abstracts.CityService;
import com.etiya.customerservice.service.requests.city.CreateCityRequest;
import com.etiya.customerservice.service.requests.city.UpdateCityRequest;
import com.etiya.customerservice.service.responses.city.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedCityResponse add(@Valid @RequestBody CreateCityRequest request) {
        return cityService.add(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GetListCityResponse> getAll() {
        return cityService.getAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetCityResponse getById(@PathVariable int id) {
        return cityService.getById(id);
    }

    @GetMapping("findAllWithDistricts")
    @ResponseStatus(HttpStatus.OK)
    public List<GetCityWithDistrictsResponse> findAllWithDistricts() {
        return cityService.findAllWithDistricts();
    }

    @GetMapping("findAllByOrderByNameAsc")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListCityResponse> findAllByOrderByNameAsc() {
        return cityService.findAllByOrderByNameAsc();
    }

    @GetMapping("findAllByNameStartingPrefix/{prefix}")
    @ResponseStatus(HttpStatus.OK)
    public List<GetListCityResponse> findAllByNameStartingPrefix(@PathVariable String prefix) {
        return cityService.findAllByNameStartingPrefix(prefix);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UpdatedCityResponse update(@RequestBody UpdateCityRequest request) {
        return cityService.update(request);
    }
}
