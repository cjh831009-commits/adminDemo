package com.admin.iam.adapter.in.web.admin;

import com.admin.iam.adapter.in.web.admin.dto.LoginRequest;
import com.admin.iam.adapter.in.web.admin.dto.LoginResponse;
import com.admin.iam.application.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Value("${app.jwt.refresh-cookie-name:refreshToken}")
    private String refreshCookieName;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req, HttpServletResponse response) {
        var tokens = authService.login(req.adminId(), req.password());

        // refresh token -> HttpOnly cookie
        ResponseCookie cookie = ResponseCookie.from(refreshCookieName, tokens.refreshToken())
                .httpOnly(true)
                .secure(false)   // 로컬은 false, 운영은 true(HTTPS)
                .path("/adminApi/auth") // refresh 요청에만 붙게(원하면 "/"로)
                .sameSite("Lax")
                .maxAge(14 * 24 * 60 * 60L) // 14 days (데모)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return LoginResponse.bearer(tokens.accessToken());
    }
}
