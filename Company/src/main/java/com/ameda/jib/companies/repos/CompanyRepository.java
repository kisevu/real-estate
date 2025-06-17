package com.ameda.jib.companies.repos;

import com.ameda.jib.companies.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: kev.Ameda
 */

@Repository
public interface CompanyRepository extends JpaRepository<Company,String> {

    @Query(value = "select * from tbl_companies where company_name=: companyName",nativeQuery = true)
    Optional<Company> getCompanyByName(@Param("companyName") String companyName);

    @Query(value = "select * from tbl_companies where company_id=: companyId",nativeQuery = true)
    Optional<Company> getCompanyById(@Param("companyId") String companyId);


}
