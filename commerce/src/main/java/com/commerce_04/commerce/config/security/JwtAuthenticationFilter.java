package com.commerce_04.commerce.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpHeaders;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

//        log.info("Header : {}" ,authorization);
//
        String jwtToken = jwtTokenProvider.resolveToken(request);

        log.info("Token : {}," ,jwtToken);

//        if (authorization == null || !authorization.startsWith("Bearer ")) {
//            log.info("authorization을 잘못 보냈습니다.");
//            filterChain.doFilter(request, response);
//            return;
//        }
        if(jwtToken != null && jwtTokenProvider.validateToken(jwtToken)){
            Authentication auth = jwtTokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }
}