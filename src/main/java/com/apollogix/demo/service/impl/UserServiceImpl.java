package com.apollogix.demo.service.impl;

import com.apollogix.demo.domain.Role;
import com.apollogix.demo.mapper.RegisterRequestMapper;
import com.apollogix.demo.repository.UserInfoRepository;
import com.apollogix.demo.service.UserService;
import com.apollogix.demo.web.model.RegisterRequest;
import com.apollogix.demo.web.model.RoleAssignmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RegisterRequestMapper registerRequestMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails register(RegisterRequest request) {
        var user = registerRequestMapper.toUserInfo(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.STUDENT); // default user role
        user = userInfoRepository.save(user);
        return user;
    }

    @Override
    public UserDetails assignRole(RoleAssignmentRequest request) {
        var userInfoOptional = userInfoRepository.findById(request.getUserId());
        var user = userInfoOptional.orElseThrow();
        user.setRole(request.getRole());
        user = userInfoRepository.save(user);
        return user;
    }
}
