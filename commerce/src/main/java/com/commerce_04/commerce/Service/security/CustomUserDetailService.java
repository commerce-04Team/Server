package com.commerce_04.commerce.Service.security;

import com.commerce_04.commerce.Repository.roles.Roles;
import com.commerce_04.commerce.Repository.userDetails.CustomUserDetails;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipal;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipalRepository;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipalRoles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Primary
@RequiredArgsConstructor
@Configuration
@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserPrincipalRepository userPrincipalRepository;
    private String inputPassword;

    public void setInputPassword(String inputPassword) {
        this.inputPassword = inputPassword;
    }


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        UserPrincipal userPrincipal = userPrincipalRepository.findByEmailFetchJoin(id).orElseThrow( () -> new UsernameNotFoundException("아이디가 틀렸습니다."));

        log.info("inputPassword : {} ",inputPassword);

        if (!passwordEncoder().matches(inputPassword, userPrincipal.getPassword())) {
            throw new UsernameNotFoundException("비밀번호가 틀렸습니다.");
        }

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                    .userId(userPrincipal.getUser().getId())
                    .email(userPrincipal.getEmail())
                    .password(userPrincipal.getPassword())
                    .authority(userPrincipal.getUserPrincipalRoles().stream().map(UserPrincipalRoles::getRoles).map(Roles::getName).collect(Collectors.toList()))
                    .build();
            return customUserDetails;
        }
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    }

