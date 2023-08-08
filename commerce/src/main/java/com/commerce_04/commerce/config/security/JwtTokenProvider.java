package com.commerce_04.commerce.config.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    public final String secretKey = Base64.getEncoder()
            .encodeToString("super-coding".getBytes()); // 비밀키 설정

    private long tokenValidMillisecond = 1000L  * 60 *60 ; // 토큰 유효시간은 1시간으로 설정

    private UserDetailsService userDetailsService;

    public String resolveToken(HttpServletRequest request) {
        // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
        return request.getHeader("X-AUTH-TOKEN");
    }

    public String createToken(String email, List<String> roles){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles",roles);
        Date now = new Date();
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(new Date(now.getTime()+tokenValidMillisecond)).signWith(SignatureAlgorithm.HS256,secretKey).compact();
    }
    public boolean validateToken(String jwtToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
            Date now = new Date();
            return !claims.getExpiration().after(now);
        }catch (Exception e){
            return false;
        }
    }

    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserEmail(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    private String getUserEmail(String jwtToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody().getSubject();
    }
}
