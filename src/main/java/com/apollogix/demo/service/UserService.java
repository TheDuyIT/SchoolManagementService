package com.apollogix.demo.service;

import com.apollogix.demo.domain.UserInfo;
import com.apollogix.web.rest.model.AuthenticationRequest;
import com.apollogix.web.rest.model.RoleAssignmentRequest;
import com.apollogix.web.rest.model.UserCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    UserInfo register(AuthenticationRequest request);

    UserInfo assignRole(RoleAssignmentRequest request);

    Page<UserInfo> findByCriteria(UserCriteria criteria, Pageable pageable);
}
