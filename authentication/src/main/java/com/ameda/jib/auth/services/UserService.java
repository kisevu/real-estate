package com.ameda.jib.auth.services;

import com.ameda.jib.auth.model.NewUser;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;


/**
 * Author: kev.Ameda
 */

public interface UserService {
    void createUser(NewUser newUser);
    void sendVerificationEmail(String userId);
    void deleteUser(String userId);
    void forgotPassword(String username);
    UserResource getUser(String userId);
    List<RoleRepresentation>  getUserRoles(String userId);
    List<GroupRepresentation> getUserGroups(String userId);
 }
