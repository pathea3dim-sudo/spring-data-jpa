package com.example.ecommerceiteapp.security;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class KeycloakAdminClientConfig {
    private final KeycloakAdminClientProps props;
    @Bean
    public Keycloak keycloakAdminClient(){
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:9999")
                .realm("ecommerce-ite2")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId("spring-api-client3")
                .clientSecret("Mxhy9xAjyAgNlWG9m1l3UkjaCikSZ1wF")
                .build();

    }


}
