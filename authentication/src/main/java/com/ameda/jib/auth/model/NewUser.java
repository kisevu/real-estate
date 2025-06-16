package com.ameda.jib.auth.model;

/**
 * Author: kev.Ameda
 */
public record NewUser(String username,
                      String password,
                      String firstName,
                      String lastName) {
}
