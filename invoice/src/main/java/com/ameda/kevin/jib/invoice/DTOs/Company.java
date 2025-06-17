package com.ameda.kevin.jib.invoice.DTOs;


import lombok.*;

/**
 * Author: kev.Ameda
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {

    private String companyId;
    private String companyName;
    private String kraPin;
    private String contactPerson;
    private int count;
    private Address address;
    private String phoneNumber;
}
