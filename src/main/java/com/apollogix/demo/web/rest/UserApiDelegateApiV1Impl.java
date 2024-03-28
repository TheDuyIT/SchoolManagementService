package com.apollogix.demo.web.rest;

import com.apollogix.demo.config.AuthoritiesConstants;
import com.apollogix.demo.mapper.UserResponseMapper;
import com.apollogix.demo.service.UserService;
import com.apollogix.web.rest.api.UserV1ApiDelegate;
import com.apollogix.web.rest.model.RoleAssignmentRequest;
import com.apollogix.web.rest.model.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserApiDelegateApiV1Impl implements UserV1ApiDelegate {
    private final UserService userService;
    private final UserResponseMapper userResponseMapper;

    
    @Secured({
            AuthoritiesConstants.TEACHER
    })
    @Override
    public ResponseEntity<UserResponseDTO> assignRoleUsingPost(RoleAssignmentRequest roleAssignmentRequest) {
        var userDetails = userService.assignRole(roleAssignmentRequest);
        return ResponseEntity.ok(
                userResponseMapper.toUserResponse(userDetails)
        );
    }
}
