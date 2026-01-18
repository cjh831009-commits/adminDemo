package com.admin.iam.adapter.in.web.admin.dto;

public record LoginRequest(
        String adminId,
        String password
) {}
