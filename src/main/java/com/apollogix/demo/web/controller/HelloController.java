package com.apollogix.demo.web.controller;

import com.apollogix.demo.config.AuthoritiesConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/teacher")
    @Secured({
            AuthoritiesConstants.TEACHER
    })
    public ResponseEntity<String> sayHelloTeacher(){
        return ResponseEntity.ok("Hello from secured endpoint for teacher!");
    }

    @GetMapping("/student")
    @Secured({
            AuthoritiesConstants.STUDENT
    })
    public ResponseEntity<String> sayHelloStudent(){
        return ResponseEntity.ok("Hello from secured endpoint for student!");
    }
}
