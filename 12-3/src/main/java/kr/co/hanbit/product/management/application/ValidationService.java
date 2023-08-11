package kr.co.hanbit.product.management.application;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

@Service
@Validated
public class ValidationService {
    public  <T> void checkValid(@Valid T validationTarget) {
        // do nothing
    }
}
