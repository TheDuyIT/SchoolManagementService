package com.apollogix.demo.web.rest;

import com.apollogix.demo.config.AuthoritiesConstants;
import com.apollogix.demo.mapper.UserResponseDTOMapper;
import com.apollogix.demo.service.UserService;
import com.apollogix.demo.util.RestResponseUtil;
import com.apollogix.web.rest.api.UserV1ApiDelegate;
import com.apollogix.web.rest.model.RoleAssignmentRequest;
import com.apollogix.web.rest.model.UserCriteria;
import com.apollogix.web.rest.model.UserResponseDTO;
import com.apollogix.web.rest.model.UserResponsePaginatedDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserApiDelegateApiV1Impl implements UserV1ApiDelegate {
    private final UserService userService;
    private final UserResponseDTOMapper userResponseDTOMapper;


    @Secured({
            AuthoritiesConstants.TEACHER
    })
    @Override
    public ResponseEntity<UserResponseDTO> assignRoleUsingPost(RoleAssignmentRequest roleAssignmentRequest) {
        var userDetails = userService.assignRole(roleAssignmentRequest);
        return ResponseEntity.ok(
                userResponseDTOMapper.toDTO(userDetails)
        );
    }


    @Secured({
            AuthoritiesConstants.TEACHER
    })
    @Override
    public ResponseEntity<UserResponsePaginatedDTO> getUserByCriteria(UserCriteria criteria, Pageable pageable) {
        var userResponseDTOS = userService.findByCriteria(criteria, pageable)
                .map(userResponseDTOMapper::toDTO);
        return ResponseEntity.ok(
                UserResponsePaginatedDTO.builder()
                        .payload(userResponseDTOS.getContent())
                        .pagination(RestResponseUtil.fromPage(userResponseDTOS))
                        .build()
        );
    }


}
