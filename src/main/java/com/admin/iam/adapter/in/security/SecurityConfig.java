package com.admin.iam.adapter.in.security;

import com.admin.iam.adapter.out.persistence.repository.AdminUserJpaRepository;
import com.admin.iam.adapter.out.redis.GroupAuthCacheService;
import com.admin.iam.application.port.out.JwtTokenPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * - /openApi/** : 공개
 * - /adminApi/auth/** : 로그인/리프레시 등 공개
 * - /adminApi/** : 인증 필요
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtTokenPort jwtTokenPort,
            GroupAuthCacheService cacheService,
            AdminUserJpaRepository adminUserRepo
    ) throws Exception {

        var jwtFilter = new JwtAuthenticationFilter(jwtTokenPort);
        var authzFilter = new RedisAuthorizationFilter(cacheService, adminUserRepo);

        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/openApi/**").permitAll()
                        .requestMatchers("/adminApi/auth/**").permitAll()
                        .requestMatchers("/adminApi/**").authenticated()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().denyAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(authzFilter, JwtAuthenticationFilter.class) // ✅ Jwt 다음
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
