package com.apollogix.demo.service.impl;

import com.apollogix.demo.domain.enums.Role;
import com.apollogix.demo.domain.UserInfo;
import com.apollogix.demo.mapper.RegisterRequestMapper;
import com.apollogix.demo.mapper.UserResponseDTOMapper;
import com.apollogix.demo.repository.UserInfoRepository;
import com.apollogix.demo.repository.predicate.UserInfoPredicates;
import com.apollogix.demo.service.UserService;
import com.apollogix.demo.web.errors.BadRequestException;
import com.apollogix.web.rest.model.AuthenticationRequest;
import com.apollogix.web.rest.model.RoleAssignmentRequest;
import com.apollogix.web.rest.model.UserCriteria;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RegisterRequestMapper registerRequestMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;
    private final UserResponseDTOMapper userResponseDTOMapper;

    @Override
    public UserInfo register(AuthenticationRequest request) {
        var userInfoOptional = userInfoRepository.findByEmail(request.getEmail());
        if (userInfoOptional.isPresent()) {
            throw new BadRequestException("User already exists!");
        }

        var user = registerRequestMapper.toUserInfo(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Enum.valueOf(Role.class, request.getRole().getValue()));
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

    @Override
    public Page<UserInfo> findByCriteria(UserCriteria criteria, Pageable pageable) {
        var predicate = UserInfoPredicates.byCriteria(criteria);
        return userInfoRepository.findAll(predicate, pageable);
    }
}
