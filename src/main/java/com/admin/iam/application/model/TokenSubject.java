package com.admin.iam.application.model;

public record TokenSubject(
        long adminNo,
        String adminId,
        String status // optional
) {}

