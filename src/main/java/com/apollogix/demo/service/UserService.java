package com.apollogix.demo.service;

import com.apollogix.demo.domain.UserInfo;
import com.apollogix.web.rest.model.AuthenticationRequest;
import com.apollogix.web.rest.model.RoleAssignmentRequest;


public interface UserService {
    UserInfo register(AuthenticationRequest request);

    UserInfo assignRole(RoleAssignmentRequest request);
}
