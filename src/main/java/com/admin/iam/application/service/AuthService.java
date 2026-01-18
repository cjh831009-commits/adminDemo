package com.admin.iam.application.service;

import com.admin.iam.adapter.out.persistence.entity.AdminUser;
import com.admin.iam.adapter.out.persistence.repository.AdminUserJpaRepository;
import com.admin.iam.application.model.TokenSubject;
import com.admin.iam.application.port.out.JwtTokenPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AdminUserJpaRepository adminUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenPort jwt;

    public AuthService(AdminUserJpaRepository adminUserRepo, PasswordEncoder passwordEncoder, JwtTokenPort jwt) {
        this.adminUserRepo = adminUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }


    public Tokens login(String adminId, String rawPassword) {
        AdminUser user = adminUserRepo.findByAdminId(adminId)
                .orElseThrow(() -> new IllegalArgumentException("INVALID_CREDENTIALS"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("INVALID_CREDENTIALS");
        }

        TokenSubject tokenSubject = new TokenSubject(user.getAdminNo(),user.getAdminId(),user.getStatus());
        String access = jwt.createAccessToken(tokenSubject);
        String refresh = jwt.createRefreshToken(tokenSubject);

        return new Tokens(access, refresh);
    }

    public record Tokens(String accessToken, String refreshToken) {}
}
