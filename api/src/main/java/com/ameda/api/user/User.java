package com.ameda.api.user;

import jakarta.persistence.*;
import lombok.*;

/**
 * Author: kev.Ameda
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    private String name;
    @Embedded
    private Address address;
}
