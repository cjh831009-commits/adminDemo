package com.admin.iam.adapter.in.security;

import com.admin.iam.application.port.out.JwtTokenPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenPort jwtTokenPort;

    public JwtAuthenticationFilter(JwtTokenPort jwtTokenPort) {
        this.jwtTokenPort = jwtTokenPort;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/openApi/")
                || uri.startsWith("/adminApi/auth/")
                || uri.equals("/error");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 1) 토큰 없으면 그냥 통과
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring("Bearer ".length()).trim();

        try {
            // 2) 토큰 파싱/검증
            Jws<Claims> parsed = jwtTokenPort.parse(token);
            Claims claims = parsed.getPayload();

            // 3) access 토큰만 처리(typ 클레임을 쓰는 경우)
            String typ = claims.get("typ", String.class);
            if (typ != null && !"access".equals(typ)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 4) subject(사용자 식별자)
            String subject = claims.getSubject(); // 예: adminId 또는 adminNo

            // 예: roles: ["ROLE_ADMIN","ROLE_MANAGER"]
            List<String> roles = claims.get("roles", List.class);
            var authorities = (roles == null)
                    ? List.<SimpleGrantedAuthority>of()
                    : roles.stream().map(SimpleGrantedAuthority::new).toList();

            var auth = new UsernamePasswordAuthenticationToken(subject, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // 정책: 401로 막기(권장)
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
