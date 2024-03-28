package com.apollogix.demo.web.rest;

import com.apollogix.demo.config.AuthoritiesConstants;
import com.apollogix.web.rest.api.HelloV1ApiDelegate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelloApiDelegateApiV1Impl implements HelloV1ApiDelegate {
    @Secured({
            AuthoritiesConstants.STUDENT
    })
    @Override
    public ResponseEntity<String> sayHelloStudent() {
        return ResponseEntity.ok("Hello from secured endpoint for student!");
    }

    @Secured({
            AuthoritiesConstants.TEACHER
    })
    @Override
    public ResponseEntity<String> sayHelloTeacher() {
        return ResponseEntity.ok("Hello from secured endpoint for teacher!");
    }
}
