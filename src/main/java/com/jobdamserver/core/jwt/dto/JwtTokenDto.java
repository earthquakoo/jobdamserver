package com.jobdamserver.core.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtTokenDto {

    private String accessToken;
}
