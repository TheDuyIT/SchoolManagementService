package com.apollogix.demo.service.impl;

import com.apollogix.demo.domain.Role;
import com.apollogix.demo.domain.UserInfo;
import com.apollogix.demo.mapper.RegisterRequestMapper;
import com.apollogix.demo.repository.UserInfoRepository;
import com.apollogix.demo.service.UserService;
import com.apollogix.demo.web.errors.BadRequestException;
import com.apollogix.web.rest.model.AuthenticationRequest;
import com.apollogix.web.rest.model.RoleAssignmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RegisterRequestMapper registerRequestMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;

    @Override
    public UserInfo register(AuthenticationRequest request) {
        var userInfoOptional = userInfoRepository.findByEmail(request.getEmail());
        if (userInfoOptional.isPresent()) {
            throw new BadRequestException("User already exists!");
        }

        var user = registerRequestMapper.toUserInfo(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.STUDENT); // default user role
        user = userInfoRepository.save(user);
        return user;
    }

    @Override
    public UserInfo assignRole(RoleAssignmentRequest request) {
        var userInfoOptional = userInfoRepository.findById(request.getUserId());
        var user = userInfoOptional.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        user.setRole(Enum.valueOf(Role.class, request.getRole().getValue()));
        user = userInfoRepository.save(user);
        return user;
    }
}
