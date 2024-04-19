package com.jobdamserver.domain.auth.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidateAccessTokenResponse {
    private boolean isVerified;
}
