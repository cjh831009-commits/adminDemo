package com.admin.iam.adapter.in.security;

import com.admin.common.config.properties.JwtProperties;
import com.admin.iam.application.model.TokenSubject;
import com.admin.iam.application.port.out.JwtTokenPort;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * [Adapter - In(Security)]
 * JJWT 라이브러리를 사용해 JwtTokenPort를 구현하는 클래스.
 *
 * 역할:
 * 1) Access/Refresh 토큰 생성
 * 2) 토큰 파싱/검증(서명, 만료 등)
 *
 * 왜 adapter/in/security인가?
 * - JWT는 "웹 요청 인증" 과정에 쓰이는 기술요소라서 in-bound(요청 처리) 쪽에 배치.
 * - 핵심 유스케이스는 이 구현체(JJWT)를 모르고 Port만 알게 된다.
 */
@Component
public class JwtTokenServiceImpl implements JwtTokenPort {

    // [기능] 서명 검증/생성에 사용하는 HMAC 키
    private final SecretKey key;

    // [기능] application.yml의 app.jwt.* 설정값 모음
    private final JwtProperties props;

    /**
     * [초기화]
     * - 설정값(secret)을 읽어서 서명키 생성
     * - secret은 최소 32바이트 이상 권장(HMAC-SHA)
     */
    public JwtTokenServiceImpl(JwtProperties props) {
        this.props = props;
        this.key = Keys.hmacShaKeyFor(props.secret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * [기능] Access Token 발급
     *
     * 토큰에 담는 정보(Claims):
     * - sub(subject): adminNo (사용자 식별자)
     * - groupNo: 그룹 식별자(권한 캐시/인가에 필요)
     * - typ: "access" (access/refresh 구분용)
     *
     * 만료:
     * - app.jwt.access-minutes 기반 (예: 15분)
     */
    @Override
    public String createAccessToken(TokenSubject tokenSubject) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(props.accessMinutes() * 60);

        return Jwts.builder()
                .issuer(props.issuer())
                .subject(String.valueOf(tokenSubject.adminNo()))
                .claims(Map.of(
                        "adminId", tokenSubject.adminId(),
                        "typ", "access"
                ))
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    /**
     * [기능] Refresh Token 발급
     *
     * 토큰에 담는 정보(Claims):
     * - sub(subject): adminNo
     * - typ: "refresh"
     *
     * 만료:
     * - app.jwt.refresh-days 기반 (예: 14일)
     */
    @Override
    public String createRefreshToken(TokenSubject tokenSubject) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(props.refreshDays() * 24 * 60 * 60);

        return Jwts.builder()
                .issuer(props.issuer())
                .subject(String.valueOf(tokenSubject.adminNo()))
                .claims(Map.of("typ", "refresh"))
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    /**
     * [기능] 토큰 파싱/검증
     *
     * 여기서 수행되는 검증:
     * 1) 서명 검증 (secret key로 verify)
     * 2) 만료(exp) 검증 (만료된 토큰이면 예외)
     * 3) 구조 검증 (형식이 이상하면 예외)
     *
     * 주의:
     * - issuer 체크를 강하게 하려면 parse 후 claims.getIssuer() 비교로 추가 검증 가능
     */
    @Override
    public Jws<Claims> parse(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }
}
