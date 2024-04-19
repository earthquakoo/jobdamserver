package com.jobdamserver.core.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtUserInfo {
    private final Long memberId;
}
