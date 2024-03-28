package com.apollogix.demo.web.controller;

import com.apollogix.demo.service.security.AuthenticationService;
import com.apollogix.demo.web.model.AuthenticationRegister;
import com.apollogix.demo.web.model.AuthenticationResponse;
import com.apollogix.demo.web.model.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping()
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRegister request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
