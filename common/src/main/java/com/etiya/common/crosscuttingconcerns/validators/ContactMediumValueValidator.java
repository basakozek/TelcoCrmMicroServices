package com.etiya.common.crosscuttingconcerns.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/*public class ContactMediumValueValidator implements ConstraintValidator<ContactMediumValueConstraint, CreateContactMediumRequest> {

    private static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$";
    private static final String PHONE_REGEX = "^\\+?\\d{10,13}$";
    private static final String URL_REGEX = "^(https?://)([\\w.-]+)(:[0-9]+)?(/.*)?$";

    @Override
    public boolean isValid(CreateContactMediumRequest request, ConstraintValidatorContext context) {
        if (request == null || request.getType() == null || request.getValue() == null) {
            return true; // diğer validasyonlar bunu yakalayacak
        }

        String type = request.getType().toUpperCase();
        String value = request.getValue();

        boolean valid = switch (type) {
            case "EMAIL" -> value.matches(EMAIL_REGEX);
            case "PHONE_NUMBER" -> value.matches(PHONE_REGEX);
            case "SOCIAL_MEDIA" -> value.matches(URL_REGEX);
            case "ADDRESS" -> value.length() >= 10;
            default -> false;
        };

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage(type)).addPropertyNode("value").addConstraintViolation();
        }


        return valid;
    }

    private String errorMessage(String type) {
        return switch (type) {
            case "EMAIL" -> "Invalid email format";
            case "PHONE_NUMBER" -> "Phone number must contain only digits and '+' and be 10–13 characters long";
            case "SOCIAL_MEDIA" -> "Invalid URL format";
            case "ADDRESS" -> "Address must contain at least 10 characters";
            default -> "Invalid value for given type";
        };
    }
}*/
