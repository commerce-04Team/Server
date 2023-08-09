package com.commerce_04.commerce.Service.user;

import com.commerce_04.commerce.Repository.roles.Roles;
import com.commerce_04.commerce.Repository.roles.RolesRepository;
import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.Repository.user.Entity.UserRepository;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipal;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipalRepository;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipalRoles;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipalRolesRepository;
import com.commerce_04.commerce.Service.excpetions.NotAcceptException;
import com.commerce_04.commerce.Service.excpetions.NotFoundException;
import com.commerce_04.commerce.config.security.JwtTokenProvider;
import com.commerce_04.commerce.web.dto.user.Login;
import com.commerce_04.commerce.web.dto.user.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserPrincipalRepository userPrincipalRepository;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserPrincipalRolesRepository userPrincipalRolesRepository;

    //    1. 아이디 동일 체크
//    public boolean signUp(SignUp singUpRequest) {
//        String email = singUpRequest.getEmail();
//        String password = singUpRequest.getPassword();
//        String username =singUpRequest.getName();
//
//        if (userPrincipalRepository.existsByEmail(email)){
//            return false;
//        }
////    2.유저가 있으면 ID 만 등록아니면 유저도 만들기
//        User userFound = userRepository.findByUsername(username)
//                .orElseGet(() -> userRepository.save(User.builder()
//                        .username(username)
//                        .build()));
//
////    3. UserName Password 등록, 기본 ROLE_USER
//        Roles roles = rolesRepository.findByName("ROLE_USER").orElseThrow(() -> new NotFoundException("Role User 를 찾을 수 없습니다."));
//        UserPrincipal userPrincipal = UserPrincipal.builder()
//                .email(email)
//                .user(userFound)
//                .password(passwordEncoder.encode(password))
//                .build();
//        userPrincipalRepository.save(userPrincipal);
//        userPrincipalRolesRepository.save(UserPrincipalRoles.builder()
//                .roles(roles)
//                .userPrincipal(userPrincipal)
//                .build()
//        );
//        return true;
//    }

    public String login(Login loginRequest) {
        try {
            String userId = loginRequest.getUser_id();
            String password = loginRequest.getPassword();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userId, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userPrincipal = userPrincipalRepository.findByEmailFetchJoin(userId)
                    .orElseThrow(() -> new NotFoundException("UserPrincipal을 찾을 수 없습니다"));

            List<String> roles = userPrincipal.getUserPrincipalRoles()
                    .stream().map(UserPrincipalRoles::getRoles).map(Roles::getName).collect(Collectors.toList());


            return jwtTokenProvider.createToken(userId, roles);
        }catch (Exception e){
            e.printStackTrace();
            throw new NotAcceptException("로그인 할 수 없습니다");
        }
    }
    // 회원 가입 메서드
    @Transactional
    public boolean registerUser(User user) {
        String userId = user.getId();
        String password = user.getPassword();
        String username = user.getUserName();

        if (userPrincipalRepository.existsByEmail(userId)) {
            return false;
        }

        // 1. 유저가 있으면 ID 만 등록, 없으면 유저도 만들기
        User userFound = userRepository.findByUserName(username)
                .orElseGet(() -> userRepository.save(User.builder()
                        .id(userId)
                        .address(user.getAddress())
                        .nickName(user.getNickName())
                        .email(user.getEmail())
                        .password(passwordEncoder.encode(password))
                        .userName(username)
                        .build()));


        // 2. UserName과 Password 등록, 기본 ROLE_USER
        Roles roles = rolesRepository.findByName("ROLE_USER")
                .orElseGet(() -> rolesRepository.save(new Roles("ROLE_USER")));
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .userPrincipalId(userId)
                .user(userFound)
                .password(passwordEncoder.encode(password)) // 비밀번호 인코딩하여 저장
                .email(user.getEmail())
                .build();

        userRepository.save(User.builder()
                .id(userId)
                .address(user.getAddress())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(password))
                .userName(username)
                .build());

        userPrincipalRepository.save(userPrincipal);
        userPrincipalRolesRepository.save(UserPrincipalRoles.builder()
                .roles(roles)
                .userPrincipal(userPrincipal)
                .build()
        );
        return true;
    }
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        // SecurityContextHolder에서 인증 정보 제거
        SecurityContextHolder.clearContext();

        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // JSESSIONID 쿠키 삭제
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        return ResponseEntity.ok("로그아웃이 성공하였습니다.");
    }
}

