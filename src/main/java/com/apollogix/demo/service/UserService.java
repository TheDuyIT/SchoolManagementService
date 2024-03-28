package com.apollogix.demo.service;

import com.apollogix.web.rest.model.AuthenticationRequest;
import com.apollogix.web.rest.model.RoleAssignmentRequest;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService {
    UserDetails register(AuthenticationRequest request);

    UserDetails assignRole(RoleAssignmentRequest request);
}
