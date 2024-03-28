package com.apollogix.demo.service.security;

import com.apollogix.demo.repository.UserInfoRepository;
import com.apollogix.demo.service.UserService;
import com.apollogix.web.rest.model.AuthenticationRequest;
import com.apollogix.web.rest.model.AuthenticationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserInfoRepository userInfoRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userInfoRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponse register(AuthenticationRequest request) {
        var user = (UserDetails) userService.register(request);
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}
