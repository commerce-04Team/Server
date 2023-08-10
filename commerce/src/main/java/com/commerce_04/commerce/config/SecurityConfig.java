package com.commerce_04.commerce.config;

import com.commerce_04.commerce.config.security.CustomAuthenticationEntryPoint;
import com.commerce_04.commerce.config.security.CustomerAccessDeniedHandler;
import com.commerce_04.commerce.config.security.JwtAuthenticationFilter;
import com.commerce_04.commerce.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

        private final JwtTokenProvider jwtTokenProvider;
        private final CustomerAccessDeniedHandler customerAccessDeniedHandler;
        private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
                http.headers().frameOptions().sameOrigin()
                        .and().formLogin().disable()
                        .csrf().disable()
                        .httpBasic().disable()
                        .rememberMe().disable()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .authorizeRequests().antMatchers("/resources/static/**","/v1/api/*").permitAll()
//                        .antMatchers("/v1/api/*").hasRole("USER") 미구현
                        .and()
                        .exceptionHandling()
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomerAccessDeniedHandler())
                        .and()
                        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }
}
