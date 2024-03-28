package com.apollogix.demo.service;

import com.apollogix.demo.web.model.RegisterRequest;
import com.apollogix.demo.web.model.RoleAssignmentRequest;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService {
    UserDetails register(RegisterRequest request);

    UserDetails assignRole(RoleAssignmentRequest request);
}
