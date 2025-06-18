package com.ameda.jib.companies.DTO;

import com.ameda.jib.companies.entities.Address;
import lombok.*;

/**
 * Author: kev.Ameda
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequest {

    private String companyName;
    private String kraPin;
    private String contactPerson;
    private int count;
    private Address address;
    private String phoneNumber;
    private String email;
}
