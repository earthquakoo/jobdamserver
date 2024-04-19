package com.jobdamserver.domain.auth.facade;

import com.jobdamserver.core.jwt.dto.JwtTokenDto;
import com.jobdamserver.domain.auth.controller.response.LoginResponse;
import com.jobdamserver.domain.auth.controller.response.SignUpResponse;
import com.jobdamserver.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthFacade {

    private final AuthService authService;

    public SignUpResponse signUp(String name, String password) {
        return authService.signUp(name, password);
    }

    public LoginResponse login(String name, String password) {
        return authService.login(name, password);
    }
}
