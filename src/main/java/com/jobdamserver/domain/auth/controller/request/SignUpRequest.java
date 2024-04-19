package com.jobdamserver.domain.auth.controller.request;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String name;
    private String password;
    private String confirmPassword;
}
