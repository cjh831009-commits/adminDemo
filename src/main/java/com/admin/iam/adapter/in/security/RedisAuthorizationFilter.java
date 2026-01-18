package com.admin.iam.adapter.in.security;

import com.admin.iam.adapter.out.persistence.repository.AdminUserJpaRepository;
import com.admin.iam.adapter.out.redis.GroupAuthCacheService;
import com.admin.iam.adapter.out.redis.dto.GroupAuthCachePayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

public class RedisAuthorizationFilter extends OncePerRequestFilter {

    private final GroupAuthCacheService cacheService;
    private final AdminUserJpaRepository adminUserRepo;

    public RedisAuthorizationFilter(GroupAuthCacheService cacheService, AdminUserJpaRepository adminUserRepo) {
        this.cacheService = cacheService;
        this.adminUserRepo = adminUserRepo;
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // JwtAuthenticationFilter에서 subject를 adminNo로 넣었다고 가정
        Long adminNo;
        try {
            adminNo = Long.parseLong(String.valueOf(auth.getPrincipal()));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Long groupNo = adminUserRepo.findById(adminNo)
                .map(u -> u.getGroupNo())
                .orElse(null);

        if (groupNo == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // /adminApi/{menuId}/{actionKey}
        String[] parts = request.getRequestURI().split("/");
        // ["", "adminApi", "{menuId}", "{actionKey}", ...]
        if (parts.length < 4) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String menuId = parts[2];
        String actionKey = parts[3];

        GroupAuthCachePayload payload = cacheService.getOrLoadPayload(groupNo);

        Set<String> actions = payload.perm().get(menuId);
        boolean allowed = (actions != null && actions.contains(actionKey));

        if (!allowed) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
