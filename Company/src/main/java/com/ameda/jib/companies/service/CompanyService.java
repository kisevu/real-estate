package com.ameda.jib.companies.service;


import com.ameda.jib.companies.DTO.CompanyRequest;
import com.ameda.jib.companies.entities.Company;

/**
 * Author: kev.Ameda
 */


public interface CompanyService {
    String addCompany(CompanyRequest request);
    Company getCompanyById(String companyId);
    Company getCompanyByName(String companyName);
}
