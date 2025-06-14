package com.ameda.api.user;

import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Author: kev.Ameda
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Address {
    private String street;
    private String city;
    private String county;
}
