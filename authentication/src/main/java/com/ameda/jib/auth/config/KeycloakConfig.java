package com.ameda.jib.auth.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: kev.Ameda
 */
@Configuration
public class KeycloakConfig {

    @Value("${app.keycloak.admin.clientId}")
    private String clientId;

    @Value("${app.keycloak.admin.clientSecret}")
    private String clientSecret;

    @Value("${app.keycloak.realm}")
    private String realm;

    @Value("${app.keycloak.serverUrl}")
    private String serverUrl;

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .clientSecret(clientSecret)
                .clientId(clientId)
                .realm(realm)
                .grantType("client_credentials")
                .serverUrl(serverUrl)
                .build();
    }

}
