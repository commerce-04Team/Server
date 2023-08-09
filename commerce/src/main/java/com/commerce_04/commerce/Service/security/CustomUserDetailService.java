package com.commerce_04.commerce.Service.security;

import com.commerce_04.commerce.Repository.roles.Roles;
import com.commerce_04.commerce.Repository.userDetails.CustomUserDetails;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipal;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipalRepository;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipalRoles;
import com.commerce_04.commerce.Service.excpetions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Primary
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserPrincipalRepository userPrincipalRepository;



    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserPrincipal userPrincipal = userPrincipalRepository.findByEmailFetchJoin(id).orElseThrow(() -> new NotFoundException("id에 해당하는 " + id + " 이 없습니다."));

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(userPrincipal.getUser().getId())
                .email(userPrincipal.getEmail())
                .password(userPrincipal.getPassword())
                .authority(userPrincipal.getUserPrincipalRoles().stream().map(UserPrincipalRoles::getRoles).map(Roles::getName).collect(Collectors.toList()))
                .build();

        return customUserDetails;
    }
}
