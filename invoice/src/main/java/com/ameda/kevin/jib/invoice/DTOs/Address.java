package com.ameda.kevin.jib.invoice.DTOs;


import lombok.*;

/**
 * Author: kev.Ameda
 */

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
