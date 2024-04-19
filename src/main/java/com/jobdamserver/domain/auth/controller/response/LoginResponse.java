package com.jobdamserver.domain.auth.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {

    private Long memberId;
    private String name;
    private String accessToken;
}
