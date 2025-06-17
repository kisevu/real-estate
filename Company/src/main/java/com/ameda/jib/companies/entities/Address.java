package com.ameda.jib.companies.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Author: kev.Ameda
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {
    private String street;
    private String city;
    private String constituency;
    private String building;
}
