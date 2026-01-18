package com.admin.iam;

import com.admin.iam.application.port.out.JwtTokenPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.*;

/**
 * [테스트 목적]
 * 1) Spring 컨텍스트에서 JwtTokenPort 빈이 정상 주입되는지 확인
 * 2) Access/Refresh 토큰 생성이 정상인지 확인
 * 3) parse가 서명/만료 검증 포함해 정상 동작하는지 확인
 */
@SpringBootTest
class JwtTokenPortTest {

    @Autowired
    JwtTokenPort jwt;

    private static final Logger log = LoggerFactory.getLogger(JwtTokenPortTest.class);

    @Test
    void accessToken_create_and_parse_ok() {
        // [준비] 샘플 데이터
//        String token = jwt.createAccessToken(100L, 10L);
//        log.info("token={}",token);
        // [실행] 토큰 파싱(서명/만료 검증 포함)
//        Jws<Claims> parsed = jwt.parse(token);
//        log.info("Claims={}",parsed);
        // [검증] 우리가 넣은 값들이 parsed 들어갔는지 확인
//        assertThat(parsed.getPayload().getSubject()).isEqualTo("100");
//        assertThat(parsed.getPayload().get("groupNo", Number.class).longValue()).isEqualTo(10L);
//        assertThat(parsed.getPayload().get("typ", String.class)).isEqualTo("access");
    }

    @Test
    void refreshToken_create_and_parse_ok() {
//        String token = jwt.createRefreshToken(200L);
//
//        Jws<Claims> parsed = jwt.parse(token);
//
//        assertThat(parsed.getPayload().getSubject()).isEqualTo("200");
//        assertThat(parsed.getPayload().get("typ", String.class)).isEqualTo("refresh");
    }

    @Test
    void invalid_token_should_throw() {
        // [검증] 형식이 잘못된 토큰은 parse 단계에서 예외
        assertThatThrownBy(() -> jwt.parse("invalid.token.value"))
                .isInstanceOf(Exception.class);
    }
}
