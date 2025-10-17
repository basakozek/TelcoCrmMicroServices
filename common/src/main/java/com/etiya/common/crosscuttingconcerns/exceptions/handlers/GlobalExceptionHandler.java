package com.etiya.common.crosscuttingconcerns.exceptions.handlers;


import com.etiya.common.crosscuttingconcerns.exceptions.constants.ExceptionMessages;
import com.etiya.common.crosscuttingconcerns.exceptions.problemdetails.BusinessProblemDetails;
import com.etiya.common.crosscuttingconcerns.exceptions.problemdetails.InternalServerProblemDetails;
import com.etiya.common.crosscuttingconcerns.exceptions.problemdetails.ProblemDetails;
import com.etiya.common.crosscuttingconcerns.exceptions.problemdetails.ValidationProblemDetails;
import com.etiya.common.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.common.crosscuttingconcerns.exceptions.types.InternalServerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetails handleBusinessException(BusinessException businessException) {
        BusinessProblemDetails businessProblemDetails = new BusinessProblemDetails();
        businessProblemDetails.setDetail(businessException.getMessage());
        return businessProblemDetails;
    }


    @ExceptionHandler({InternalServerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetails handleInternalServerException(InternalServerException internalServerException) {
        InternalServerProblemDetails internalServerProblemDetails = new InternalServerProblemDetails();
        internalServerProblemDetails.setDetail(internalServerException.getMessage());
        return internalServerProblemDetails;
    }



    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetails handleValidationException(MethodArgumentNotValidException exception) {
        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setDetail(ExceptionMessages.VALIDATION_ERRORS);


        Map<String, String> validationErrors = new HashMap<>();
        // döndürülecek hatayı yakalamak için
        // exception fırlatıldığında bana hata dönecek  -->  exception.getBindingResult()
        // normal hatada bir sürü hata mesajı var ya orada field alanlarını yakalamak istiyoruz. ;
        // tüm exceptionlarda alanlar için dönen hatayı yakalıyorum -->  .getFieldErrors()
        // list yapısı olduğu için tek tek alanlara özgü hatayı for each ile dönmem gerekeiyor.  -->  .forEach
        // oluşturduğum map e hataları gönderiyorum. error un içindeki get field ve karşılığı default message ı yakalayıp listeye ekliyoruz
        exception.getBindingResult().getFieldErrors().forEach(error->validationErrors.put(error.getField(),error.getDefaultMessage()));
        // böyle tüm errorları buraya gönderdik.
        validationProblemDetails.setValidationErrors(validationErrors);
        return validationProblemDetails;
    }



}
