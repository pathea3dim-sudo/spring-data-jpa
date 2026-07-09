package com.example.ecommerceiteapp.feature.auth;


import com.example.ecommerceiteapp.feature.auth.dto.RegisterRequest;
import com.example.ecommerceiteapp.feature.auth.dto.RegisterResponse;
import com.example.ecommerceiteapp.feature.userprofile.UserProfile;
import com.example.ecommerceiteapp.feature.userprofile.UserProfileRepository;
import com.example.ecommerceiteapp.security.KeycloakAdminClientProps;

import jakarta.ws.rs.core.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {


    private final Keycloak keycloak;

    private final KeycloakAdminClientProps props;

    private final AuthMapper authMapper;

    private final UserProfileRepository userProfileRepository;



    @Override
    public RegisterResponse register(
            RegisterRequest request
    ) {


        if (!request.password()
                .equals(request.confirmedPassword())) {


            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match"
            );
        }



        UsersResource users =
                keycloak
                        .realm(props.getTargetRealm())
                        .users();


        UserRepresentation user =
                new UserRepresentation();


        user.setUsername(
                request.username()
        );


        user.setEmail(
                request.email()
        );


        user.setFirstName(
                request.firstName()
        );


        user.setLastName(
                request.lastName()
        );


        user.setEnabled(true);


        user.setEmailVerified(false);



        Map<String, List<String>> attributes =
                new HashMap<>();


        attributes.put(
                "phoneNumber",
                List.of(request.phoneNumber())
        );


        user.setAttributes(attributes);



        CredentialRepresentation credential =
                new CredentialRepresentation();


        credential.setType(
                CredentialRepresentation.PASSWORD
        );


        credential.setValue(
                request.password()
        );


        credential.setTemporary(false);



        user.setCredentials(
                List.of(credential)
        );



        try(Response response =
                    users.create(user)) {



            log.info(
                    "Keycloak create user status : {}",
                    response.getStatus()
            );



            if(response.getStatus()
                    == HttpStatus.CONFLICT.value()) {


                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Username or Email already exists"
                );
            }



            if(response.getStatus()
                    != HttpStatus.CREATED.value()) {


                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Cannot create user"
                );
            }



            String location =
                    response.getHeaderString(
                            "Location"
                    );


            String userId =
                    location.substring(
                            location.lastIndexOf("/") + 1
                    );



            log.info(
                    "Created Keycloak user id : {}",
                    userId
            );



            UserRepresentation createdUser =
                    users.get(userId)
                            .toRepresentation();




            UserProfile profile =
                    new UserProfile();


            profile.setUserId(
                    userId
            );


            userProfileRepository.save(profile);



            RealmResource realm =
                    keycloak
                            .realm(
                                    props.getTargetRealm()
                            );



            RoleRepresentation userRole =
                    realm.roles()
                            .get("USER")
                            .toRepresentation();



            RoleRepresentation customerRole =
                    realm.roles()
                            .get("CUSTOMER")
                            .toRepresentation();



            realm.users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .add(
                            List.of(
                                    userRole,
                                    customerRole
                            )
                    );



            log.info(
                    "Assigned USER and CUSTOMER roles"
            );

            try {

                realm.users()
                        .get(userId)
                        .sendVerifyEmail();


                log.info(
                        "Verification email sent"
                );


            } catch(Exception e) {


                log.warn(
                        "Cannot send verification email: {}",
                        e.getMessage()
                );
            }





            return authMapper
                    .mapUserRepresentaionToRegisterResponse(createdUser);



        }


        catch(ResponseStatusException e) {


            throw e;


        }


        catch(Exception e) {


            log.error(
                    "Register error",
                    e
            );


            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Service exception errored"
            );

        }

    }

}