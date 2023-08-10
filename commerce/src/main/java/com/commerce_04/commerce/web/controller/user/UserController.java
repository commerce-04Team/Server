package com.commerce_04.commerce.web.controller.user;


import com.commerce_04.commerce.Repository.user.Entity.User;
import com.commerce_04.commerce.Service.user.AuthService;
import com.commerce_04.commerce.config.security.JwtTokenProvider;
import com.commerce_04.commerce.web.dto.user.Login;
import com.commerce_04.commerce.web.dto.user.SignUp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

//    @DeleteMapping("/users/{userId}")
//    public ResponseEntity<String> deleteUser(@PathVariable String userId, @RequestParam String password) {
//        boolean isSuccess = authService.deleteUser(userId, password);
//        if (isSuccess) {
//            return ResponseEntity.ok("회원탈퇴 성공하였습니다.");
//        } else {
//            return ResponseEntity.badRequest().body("회원탈퇴 실패하였습니다.");
//        }
//    }
@DeleteMapping("/deleteUser")
public ResponseEntity<String> deleteUser(@RequestHeader("X-AUTH-TOKEN") String jwtToken) {

            log.info("Token : {}" , jwtToken);
    if (jwtToken != null && jwtTokenProvider.validateToken(jwtToken)) {
        String userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        log.info("userId : {}", userId);
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
}
