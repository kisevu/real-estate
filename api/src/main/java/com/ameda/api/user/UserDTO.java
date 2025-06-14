package com.ameda.api.user;

import lombok.*;

/**
 * Author: kev.Ameda
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private Address address;
}
