package com.ameda.jib.companies.api;

import com.ameda.jib.companies.DTO.CompanyRequest;
import com.ameda.jib.companies.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Author: kev.Ameda
 */

@RestController
@RequestMapping("/api/companies")
public class CompanyResource {

    private final CompanyService companyService;

    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody @Valid CompanyRequest request ){
        String result = this.companyService.addCompany(request);
        if (Objects.equals(result,"success")){
            return ResponseEntity.status(HttpStatus.CREATED).body("Company Added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Company could not be added");
        }
    }

    @GetMapping("/id/{companyId}")
    public  ResponseEntity<?> getCompanyById(@PathVariable String companyId ){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyById(companyId));
    }

    @GetMapping("/name/{companyName}")
    public ResponseEntity<?> getCompanyByName(@PathVariable String companyName ){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyByName(companyName));
    }
}
