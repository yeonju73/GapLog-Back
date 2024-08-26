package com.gaplog.server.domain.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTToken {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    public static JWTToken of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new JWTToken(accessToken, refreshToken, grantType, expiresIn);
    }
}
