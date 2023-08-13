package com.commerce_04.commerce.Service.user;

import com.commerce_04.commerce.Repository.roles.Roles;
import com.commerce_04.commerce.Repository.roles.RolesRepository;
import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.Repository.user.Entity.UserRepository;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipal;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipalRepository;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipalRoles;
import com.commerce_04.commerce.Repository.userPrincipal.UserPrincipalRolesRepository;
import com.commerce_04.commerce.Service.excpetions.NotFoundException;
import com.commerce_04.commerce.Service.excpetions.UserRegistrationException;
import com.commerce_04.commerce.Service.security.CustomUserDetailService;
import com.commerce_04.commerce.config.security.JwtTokenProvider;
import com.commerce_04.commerce.web.dto.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserPrincipalRepository userPrincipalRepository;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserPrincipalRolesRepository userPrincipalRolesRepository;
    private final CustomUserDetailService customUserDetailService;


    public String login(Login loginRequest) {
        String userId = loginRequest.getUser_id();
        String password = loginRequest.getPassword();


        UserPrincipal user = userPrincipalRepository.findByUserPrincipalId(userId)
                .orElseThrow(() -> new NotFoundException("해당 아이디를 찾을 수 없습니다"));
        UserPrincipalRoles userPrincipalRoles = userPrincipalRolesRepository.findByUserPrincipal(user)
                .orElseThrow(() -> new NotFoundException("해당 아이디를 찾을 수 없습니다"));

        if(userPrincipalRoles.getRoles().getName().matches("SUSPENDED_USER")){
         throw new UserRegistrationException("해당 유저는 정지된 유저 입니다.");
        }

        customUserDetailService.setInputPassword(password);
        customUserDetailService.loadUserByUsername(userId);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userId, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = userPrincipalRepository.findByEmailFetchJoin(userId)
                .orElseThrow(() -> new NotFoundException("UserPrincipal을 찾을 수 없습니다"));
        List<String> roles = userPrincipal.getUserPrincipalRoles()
                .stream().map(UserPrincipalRoles::getRoles).map(Roles::getName).collect(Collectors.toList());

        return jwtTokenProvider.createToken(userId, roles,passwordEncoder.encode(password));
    }


    // 회원 가입 메서드
    @Transactional
    public boolean registerUser(User user) {
        String userId = user.getId();
        String password = user.getPassword();
        String username = user.getUserName();
        String email = user.getEmail();
        String nickName = user.getNickName();
        if (userPrincipalRepository.existsByEmail(email)) {
            throw new UserRegistrationException("이미 등록된 이메일입니다.");
        }
        if (userRepository.existsUserById(userId)) {
            throw new UserRegistrationException("이미 등록된 아이디 입니다.");
        }
        if (userRepository.existsUserByNickName(nickName)) {
            throw new UserRegistrationException("이미 등록된 닉네임 입니다.");
        }
        if (username.length() < 3) {
            throw new UserRegistrationException("사용자 이름은 최소 3자 이상이어야 합니다.");
        }
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$")) {
            throw new UserRegistrationException("비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.");
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


        // 2. UserName과 Password 등록, 기본 ROLE_USER , ADMIN_ROLE, SUSPENDED_USER
        Roles roles = rolesRepository.findByName("ROLE_USER")
                .orElseGet(() -> rolesRepository.save(new Roles("ROLE_USER")));

        Roles roles1 = rolesRepository.findByName("ADMIN_USER")
                .orElseGet(() -> rolesRepository.save(new Roles("ADMIN_USER")));

        Roles roles2 = rolesRepository.findByName("SUSPENDED_USER")
                .orElseGet(() -> rolesRepository.save(new Roles("SUSPENDED_USER")));


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

    @Transactional
    public boolean deleteUser(String userId) {
        try {

            UserPrincipal userPrincipal = userPrincipalRepository.findByUserPrincipalId(userId)
                    .orElseThrow(() -> new NotFoundException("해당 아이디를 찾을 수 없습니다."));


            userRepository.delete(userPrincipal.getUser());
            userPrincipalRolesRepository.deleteByUserPrincipal(userPrincipal);
            userPrincipalRepository.delete(userPrincipal);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean changePassword(String jwtToken, String newPassword) {
        String userId = jwtTokenProvider.getUserId(jwtToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));

        if (!newPassword.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$")) {
            throw new UserRegistrationException("비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.");
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new UserRegistrationException("이전과 동일한 패스워드는 안됩니다.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }

    @Transactional
    public boolean updateUser(String userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));

        if (updateUserRequest.getEmail() == null || userPrincipalRepository.existsByEmail(updateUserRequest.getEmail())) {
            throw new UserRegistrationException("이미 등록된 이메일이거나 공백입니다.");
        }
        user.setEmail(updateUserRequest.getEmail());

        if (updateUserRequest.getNickName() == null || userRepository.existsUserByNickName(updateUserRequest.getNickName())) {
            throw new UserRegistrationException("이미 등록된 닉네임이거나 공백입니다.");
        }
        user.setNickName(updateUserRequest.getNickName());

        if (updateUserRequest.getName() == null) {
            throw new UserRegistrationException("이름이 공백입니다.");
        }
        user.setUserName(updateUserRequest.getName());
        if (updateUserRequest.getAddress() == null) {
            throw new UserRegistrationException("주소가 공백입니다.");
        }
        user.setAddress(updateUserRequest.getAddress());
        userRepository.save(user);
        return true;
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public boolean changeUserRole(String userId,String roles,String jwtToken) {
        Roles role;
        String UserRoles = jwtTokenProvider.getUserRoles(jwtToken);
        log.info("UserRoles : {}",UserRoles);
        if(!UserRoles.matches("ADMIN_USER")) throw new UserRegistrationException("해당 유저는 관리자가 아닙니다. 그러므로 권한을 변경할 수 없습니다");
        if (roles.matches("Ban")) {
            role = rolesRepository.findByName("SUSPENDED_USER")
                    .orElseThrow(() -> new NotFoundException("해당 역할이 없습니다."));
        } else if (roles.equalsIgnoreCase("Commit")) {
            role = rolesRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new NotFoundException("해당 역할이 없습니다."));
        } else {
            throw new NotFoundException("역할에 'Ban' 혹은 'Commit' 적어주세요.");
        }
        UserPrincipal user = userPrincipalRepository.findByUserPrincipalId(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        UserPrincipalRoles userPrincipalRoles = userPrincipalRolesRepository.findByUserPrincipal(user)
                .orElseThrow(() -> new NotFoundException("유저의 역할을 찾을 수 없습니다."));

        userPrincipalRoles.setRoles(role);
        userPrincipalRolesRepository.save(userPrincipalRoles);

        return true;
    }
    }

