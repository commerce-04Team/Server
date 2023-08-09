package com.commerce_04.commerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


//    각 권한에 맞는 기능 설정
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.httpBasic()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/user/**").hasRole("USER")
                    .antMatchers("/v1/api/sign/registers").permitAll()
                    .antMatchers("/**").permitAll()
                    .and()
                    .logout() // 로그아웃 설정 시작
                    .logoutUrl("/logout") // 로그아웃 URL
                    .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL
                    .invalidateHttpSession(true) // 세션 무효화
                    .deleteCookies("JSESSIONID") // 로그아웃 시 쿠키 삭제
                    .and()
                    .csrf().disable();
        }
}
