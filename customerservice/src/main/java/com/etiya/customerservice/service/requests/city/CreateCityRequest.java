package com.etiya.customerservice.service.requests.city;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCityRequest {
    @NotBlank(message = "{city.name.notBlank}")
    @Pattern(regexp = "^[A-Za-zÇçĞğİıÖöŞşÜü\\s]+$", message = "{city.name.pattern}")
    private String name;
}
