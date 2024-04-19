package com.jobdamserver.domain.auth.controller;

import com.jobdamserver.core.jwt.dto.JwtTokenDto;
import com.jobdamserver.domain.auth.controller.request.LoginRequest;
import com.jobdamserver.domain.auth.controller.request.SignUpRequest;
import com.jobdamserver.domain.auth.controller.response.LoginResponse;
import com.jobdamserver.domain.auth.controller.response.SignUpResponse;
import com.jobdamserver.domain.auth.controller.response.ValidateAccessTokenResponse;
import com.jobdamserver.domain.auth.facade.AuthFacade;
import com.jobdamserver.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResponse response = authFacade.signUp(request.getName(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authFacade.login(request.getName(), request.getPassword());
        System.out.println("response.getAccessToken() = " + response.getAccessToken());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/auth/verify/access-token")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Validate access toke")
    public ValidateAccessTokenResponse validateAccessToken() {
        return new ValidateAccessTokenResponse(true);
    }
}
