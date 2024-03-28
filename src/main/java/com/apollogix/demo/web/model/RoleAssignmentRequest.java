package com.apollogix.demo.web.model;

import com.apollogix.demo.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NonNull
public class RoleAssignmentRequest {
    private Long userId;
    private Role role;
}
