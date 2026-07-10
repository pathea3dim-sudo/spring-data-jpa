package com.example.ecommerceiteapp.feature.auth;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class RoleService {


    private final Keycloak keycloak;


    public void assignRole(
            UserResource user,
            List<RoleRepresentation> roles
    ){

        user.roles()
                .realmLevel()
                .add(roles);

    }

}