package com.apollogix.demo.web.rest;

import com.apollogix.demo.config.AuthoritiesConstants;
import com.apollogix.demo.service.security.AuthenticationService;
import com.apollogix.web.rest.api.AuthV1ApiDelegate;
import com.apollogix.web.rest.model.AuthenticationRequest;
import com.apollogix.web.rest.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthApiDelegateApiV1Impl implements AuthV1ApiDelegate {
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthenticationResponse> doAuthenticate(AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @Secured({
            AuthoritiesConstants.TEACHER
    })
    @Override
    public ResponseEntity<AuthenticationResponse> doRegister(AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
}
