package com.commerce_04.commerce.web.controller.user;


import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.Service.user.AuthService;
import com.commerce_04.commerce.web.dto.user.Login;
import com.commerce_04.commerce.web.dto.user.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class UserController {
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

//    @DeleteMapping("/users/{userId}")
//    public ResponseEntity<String> deleteUser(@PathVariable String userId,String password) {
//        boolean isSuccess = authService.deleteUser(userId,password);
//        if (isSuccess) {
//            return ResponseEntity.ok("회원탈퇴 성공하였습니다.");
//        } else {
//            return ResponseEntity.badRequest().body("회원탈퇴 실패하였습니다.");
//        }
//    }
    }
