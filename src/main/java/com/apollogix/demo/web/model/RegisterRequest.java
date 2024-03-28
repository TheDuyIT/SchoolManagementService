package com.apollogix.demo.web.model;

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
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
