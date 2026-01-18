package com.admin.iam.adapter.in.web.admin.dto;


public record LoginResponse(
        String accessToken,
        String tokenType
) {
    public static LoginResponse bearer(String accessToken) {
        return new LoginResponse(accessToken, "Bearer");
    }
}

