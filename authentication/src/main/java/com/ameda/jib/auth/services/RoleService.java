package com.ameda.jib.auth.services;

/**
 * Author: kev.Ameda
 */
public interface RoleService {

    void assignRole(String userId, String roleName);
    void deleteRoleFromUser(String userId, String roleName);
}
