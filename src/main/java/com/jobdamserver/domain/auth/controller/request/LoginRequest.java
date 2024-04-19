package com.jobdamserver.domain.auth.controller.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String name;
    private String password;
}
