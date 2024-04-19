package com.jobdamserver.core.jwt;

import com.jobdamserver.core.exception.CustomException;
import com.jobdamserver.core.jwt.dto.JwtTokenDto;
import com.jobdamserver.core.jwt.dto.JwtUserInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.jobdamserver.core.exception.ErrorInfo.EXPIRED_JWT_TOKEN;
import static com.jobdamserver.core.exception.ErrorInfo.INVALID_JWT_TOKEN;


@Component
public class JwtTokenProvider {

    private final Key key;

    @Value("${jwt.access_token_expiration_ms}")
    private long accessTokenExpirationTimeMs;


    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtTokenDto generateToken(Long memberId) {
        Date accessTokenExpiration = getTokenExpiration(accessTokenExpirationTimeMs);

        String accessToken = Jwts.builder()
                .setSubject(memberId.toString())
                .setExpiration(accessTokenExpiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    public Date getTokenExpiration(long expirationMs) {
        long now = (new Date()).getTime();
        return new Date(now + expirationMs);
    }

    public JwtUserInfo validateAndExtractUserInfo(String token) {
        try {
            Jws<Claims> parsedToken = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return new JwtUserInfo(Long.parseLong(parsedToken.getBody().getSubject()));
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new CustomException(INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new CustomException(EXPIRED_JWT_TOKEN);
        }
    }

    public JwtUserInfo getCurrentUserInfo() {
        return (JwtUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
