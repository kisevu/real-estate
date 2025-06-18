package com.ameda.jib.companies.service.impl;

import com.ameda.jib.companies.DTO.CompanyRequest;
import com.ameda.jib.companies.entities.Address;
import com.ameda.jib.companies.entities.Company;
import com.ameda.jib.companies.exceptions.CompanyNotFoundException;
import com.ameda.jib.companies.repos.CompanyRepository;
import com.ameda.jib.companies.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Author: kev.Ameda
 */

@Service
@Transactional
@Slf4j
public class CompanyServiceImpl  implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public String addCompany(CompanyRequest request) {
        Address addressReq = request.getAddress();
        Address address = Address.builder()
                .street(addressReq.getStreet())
                .constituency(addressReq.getConstituency())
                .city(addressReq.getCity())
                .building(addressReq.getBuilding())
                .build();
        Company company = Company.builder()
                .companyName(request.getCompanyName())
                .address(address)
                .count(request.getCount())
                .contactPerson(request.getContactPerson())
                .phoneNumber(request.getPhoneNumber())
                .kraPin(request.getKraPin())
                .email(request.getEmail())
                .build();
        Company saved = companyRepository.save(company);
        if(!Objects.isNull(saved)){
            return "success";
        } else {
            return "unsuccess";
        }
    }

    @Override
    public Company getCompanyById(String companyId) {
        return companyRepository.getCompanyById(companyId)
                .orElseThrow(()-> new CompanyNotFoundException("Company could not be found."));
    }

    @Override
    public Company getCompanyByName(String companyName) {
        return companyRepository.getCompanyByName(companyName)
                .orElseThrow(()-> new CompanyNotFoundException("Company could not be found."));
    }
}
