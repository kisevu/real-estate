package com.ameda.jib.companies.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Author: kev.Ameda
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String companyId;
    private String companyName;
    private String kraPin;
    private String contactPerson;
    private int count;
    @Embedded
    private Address address;
    private String phoneNumber;
}
