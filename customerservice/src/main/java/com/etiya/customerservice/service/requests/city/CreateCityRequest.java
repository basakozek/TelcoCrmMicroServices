package com.etiya.customerservice.service.requests.city;

import com.etiya.customerservice.service.messages.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCityRequest {
    @NotBlank(message = Messages.CityNameNotBlank)
    @Pattern(regexp = "^[A-Za-zÇçĞğİıÖöŞşÜü\\s]+$", message = Messages.CityNamePattern)
    private String name;
}
