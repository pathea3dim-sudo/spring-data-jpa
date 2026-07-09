package com.example.ecommerceiteapp.feature.auth;

import com.example.ecommerceiteapp.feature.auth.dto.RegisterResponse;
import org.mapstruct.Mapper;
import org.keycloak.representations.idm.UserRepresentation;

@Mapper(componentModel = "spring")
public abstract class AuthMapper {

    public RegisterResponse mapUserRepresentaionToRegisterResponse(
            UserRepresentation userRepresentation
    ) {

        return RegisterResponse.builder()
                .userId(userRepresentation.getId())
                .username(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .phoneNumber(
                        userRepresentation.firstAttribute("phoneNumber")
                )
                .build();
    }
}