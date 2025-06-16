package com.ameda.jib.auth.services.impl;


import com.ameda.jib.auth.model.NewUser;
import com.ameda.jib.auth.services.UserService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Author: kev.Ameda
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;

    @Value("${app.keycloak.realm}")
    private String realm;

    @Override
    public void createUser(NewUser newUser) {

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(newUser.firstName());
        userRepresentation.setLastName(newUser.lastName());
        userRepresentation.setUsername(newUser.username());
        userRepresentation.setEmail(newUser.username());
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(newUser.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        UsersResource usersResource = this.getUsersResource();

        Response response = usersResource.create(userRepresentation);

        log.info("Status code:{}",response.getStatus());

        if(!Objects.equals(201,response.getStatus())){
            throw new RuntimeException("Status code " + response.getStatus());
        }
        log.info("New User has been created:{}", newUser.firstName());

        /*
        * send verification email on account creation
        * */
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(newUser.username(), true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
        sendVerificationEmail(userRepresentation1.getId());
    }

    @Override
    public void sendVerificationEmail(String userId ){
        UsersResource usersResource = this.getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }


    @Override
    public void deleteUser(String userId) {
        UsersResource usersResource = this.getUsersResource();
        usersResource.delete(userId);
    }

    @Override
    public void forgotPassword(String username) {
        UsersResource usersResource = this.getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
        UserRepresentation userRepresentation = userRepresentations.get(0);
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    @Override
    public UserResource getUser(String userId) {
        UsersResource usersResource = this.getUsersResource();
        return usersResource.get(userId);
    }

    @Override
    public List<RoleRepresentation> getUserRoles(String userId) {
        return this.getUser(userId).roles().realmLevel().listAll();
    }

    @Override
    public List<GroupRepresentation> getUserGroups(String userId) {
        return this.getUser(userId).groups();
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }
}
