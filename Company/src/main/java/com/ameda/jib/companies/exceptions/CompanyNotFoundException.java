package com.ameda.jib.companies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: kev.Ameda
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompanyNotFoundException  extends RuntimeException{

    public CompanyNotFoundException() {
    }

    public CompanyNotFoundException(String message) {
        super(message);
    }
}
