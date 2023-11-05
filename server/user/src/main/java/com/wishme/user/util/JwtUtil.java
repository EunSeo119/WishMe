package com.wishme.user.util;

import com.wishme.user.domain.User;
import com.wishme.user.user.model.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Autowired
    private UserRepository userRepository;

    // access token valid time : 14 days
    private final long ACCESS_TOKEN_VALID_TIME = 14 * 24 * 60 * 60 * 1000L;

    // refresh token valid time : 3 weeks
    private final long REFRESH_TOKEN_VALID_TIME = 21 * 24 * 60 * 60 * 1000L;

    public String createJwt(String userSeq, String secretKey) {
        Claims claims = Jwts.claims();
        claims.put("userSeq", userSeq);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("access-token")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() + ACCESS_TOKEN_VALID_TIME)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(String userSeq, String secretKey) {
        Claims claims = Jwts.claims();
        claims.put("userSeq", userSeq);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("refresh-token")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() + REFRESH_TOKEN_VALID_TIME)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean isExpired(String token, String secretKey) {

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }

//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration()
//                .before(new Date());
    }

    public boolean isValidationRefreshToken(String token, String secretKey) {

        if (!isExpired(token, secretKey)) return false;

        User user = userRepository.findByUserSeq(Long.parseLong(getUserSeq(token, secretKey)));
        String refreshToken = user.getRefreshToken();

        if (refreshToken != null && refreshToken.equals(token)) {
            return true;
        } else {
            return false;
        }
    }

    public String getUserSeq(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("userSeq", String.class);
    }
}
