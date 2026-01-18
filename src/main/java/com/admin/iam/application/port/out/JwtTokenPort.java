package com.admin.iam.application.port.out;

import com.admin.iam.application.model.TokenSubject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * [Port] JWT 토큰 기능의 "출구" 인터페이스.
 *
 * - application/usecase(AuthService 등)는 이 인터페이스만 의존한다.
 * - 실제 구현(JJWT 라이브러리 사용)은 adapter 레이어에서 담당한다.
 *
 * 목적:
 * 1) 도메인/유스케이스가 특정 JWT 라이브러리에 종속되지 않게 한다.
 * 2) 테스트 시 mock/stub으로 대체하기 쉽다.
 */
public interface JwtTokenPort {

    /**
     * [기능] Access Token 발급
     * - 짧은 만료(예: 15분)
     * - 매 요청 Authorization 헤더로 전달되어 인증/인가에 사용
     *
     */
    String createAccessToken(TokenSubject subject);

    /**
     * [기능] Refresh Token 발급
     * - 긴 만료(예: 14일)
     * - Access 만료 시 재발급에 사용
     * - 보통 HttpOnly 쿠키로 내려주고, 서버(DB)에 저장(해시)해서 통제
     *

     */
    String createRefreshToken(TokenSubject subject);

    /**
     * [기능] 토큰 파싱/검증
     * - 서명 검증
     * - 만료(exp) 검증
     * - issuer 검증(필요 시)
     *
     * @param token JWT 문자열
     * @return 서명 검증된 Claims
     */
    Jws<Claims> parse(String token);
}
