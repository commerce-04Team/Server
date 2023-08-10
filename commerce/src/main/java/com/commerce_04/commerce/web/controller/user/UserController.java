package com.commerce_04.commerce.web.controller.user;


import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.Service.user.AuthService;
import com.commerce_04.commerce.config.security.JwtTokenProvider;
import com.commerce_04.commerce.web.dto.user.Login;
import com.commerce_04.commerce.web.dto.user.PublicInformation;
import com.commerce_04.commerce.web.dto.user.UpdateUserRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class UserController {


    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;


    @PostMapping(value = "/login")
    public String login(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse) {
        String token = authService.login(loginRequest);
        httpServletResponse.setHeader("X-AUTH-TOKEN", token);
        return "로그인이 성공하였습니다.";
    }


    @PostMapping("/registers")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        boolean isSuccess = authService.registerUser(user);
        if (isSuccess) {
            return ResponseEntity.ok("회원가입 성공 하였습니다.");
        } else {
            return ResponseEntity.badRequest().body("회원가입 실패 하였습니다.");
        }
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestHeader("X-AUTH-TOKEN") String jwtToken) {

        if (jwtToken != null && jwtTokenProvider.validateToken(jwtToken)) {
            String userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
            boolean isSuccess = authService.deleteUser(userId);

            if (isSuccess) {
                return ResponseEntity.ok("회원탈퇴 성공하였습니다.");
            } else {
                return ResponseEntity.badRequest().body("회원탈퇴 실패하였습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestHeader("X-AUTH-TOKEN") String jwtToken, @RequestBody Login request) {
        if (jwtToken != null && jwtTokenProvider.validateToken(jwtToken)) {

            log.info("Controller Request Password : {} ", request.getPassword());


            boolean isSuccess = authService.changePassword(jwtToken, request.getPassword());

            if (isSuccess) {
                return ResponseEntity.ok("비밀번호 변경에 성공하였습니다.");
            } else {
                return ResponseEntity.badRequest().body("비밀번호 변경에 실패하였습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestHeader("X-AUTH-TOKEN") String jwtToken,
                                             @RequestBody UpdateUserRequest updateUserRequest) {
        if (jwtToken != null && jwtTokenProvider.validateToken(jwtToken)) {
            String userId = jwtTokenProvider.getUserIdFromToken(jwtToken);

            boolean isSuccess = authService.updateUser(userId, updateUserRequest);

            if (isSuccess) {
                return ResponseEntity.ok("회원 정보 수정에 성공하였습니다.");
            } else {
                return ResponseEntity.badRequest().body("회원 정보 수정에 실패하였습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
    }

    @GetMapping("/getUser")
    public PublicInformation getUserById(@RequestParam String userId) {
        User user = authService.getUserById(userId);
        return new PublicInformation(user.getNickName(),user.getEmail());
    }
    @PostMapping("/changeUserRole")
    public ResponseEntity<String> changeUserRole(@RequestParam String userId, @RequestParam String roles) {
        boolean isSuccess = authService.changeUserRole(userId,roles);
        if (isSuccess) {
            return ResponseEntity.ok("사용자 권한 변경에 성공하였습니다.");
        } else {
            return ResponseEntity.badRequest().body("사용자 권한 변경에 실패하였습니다.");
        }
    }
}

