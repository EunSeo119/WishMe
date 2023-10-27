package com.wishme.myLetter.config;

import com.wishme.myLetter.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final String JwtSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization:{}", authorization);

        // Jwt 안보내면 block
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("authorization is error");
            filterChain.doFilter(request, response);
            return;
        }

        // Jwt 꺼내기
        JwtUtil jwtUtil = new JwtUtil();
        String token = authorization.split(" ")[1];

        // Token Expired 여부
        if (jwtUtil.isExpired(token, JwtSecretKey)) {
            log.error("Jwt is expired");
            filterChain.doFilter(request, response);
            return;
        }

        // Jwt에서 UserSeq 꺼내기
        String userSeq = jwtUtil.getUserSeq(token, JwtSecretKey);
        log.info("userSeq: {}", userSeq);

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userSeq, null, List.of(new SimpleGrantedAuthority("USER")));

        // Detail 넣기
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
