package com.commerce_04.commerce.config.security;

import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.Repository.user.Entity.UserRepository;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipal;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipalRepository;
import com.commerce_04.commerce.Service.excpetions.NotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final UserPrincipalRepository userPrincipalRepository;
    private String secretKey = "Super-Coding";

    @PostConstruct
    public void setUp() {
        secretKey = Base64.getEncoder()
                .encodeToString(secretKey.getBytes());
    }


    final long tokenValidMillisecond = 1000L * 60 * 60; // 토큰 유효시간은 1시간으로 설정

    private final UserDetailsService userDetailsService;


    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    public String createToken(String userId, List<String> roles,String password) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);
        claims.put("userId", userId);
        claims.put("password",password);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken)
                    .getBody();
            Date now = new Date();
            return claims.getExpiration().after(now);
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserId(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserId(String jwtToken) {
        String userId = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
        return userId;
    }

    public String getUserRoles(String jwtToken) {
        List<String> roleList = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .get("roles", List.class);
        String roles = String.join(",", roleList);
        return roles;
    }
}
