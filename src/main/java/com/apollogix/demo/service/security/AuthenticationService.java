package com.apollogix.demo.service.security;

import com.apollogix.demo.mapper.RegisterRequestMapper;
import com.apollogix.demo.repository.UserInfoRepository;
import com.apollogix.demo.service.UserService;
import com.apollogix.demo.web.model.AuthenticationRegister;
import com.apollogix.demo.web.model.AuthenticationResponse;
import com.apollogix.demo.web.model.RegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserInfoRepository userInfoRepository;
    private final RegisterRequestMapper registerRequestMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    public AuthenticationResponse authenticate(AuthenticationRegister request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userInfoRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = userService.register(request);
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}
