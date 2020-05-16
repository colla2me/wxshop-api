package com.spring.wxshop.controller;

import com.spring.wxshop.entity.LoginStatus;
import com.spring.wxshop.service.AuthService;
import com.spring.wxshop.service.impl.UserContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/code")
    public void code(@RequestBody Map<String, String> body, HttpServletResponse response) {
        if (body != null && authService.isValidTel(body.get("tel"))) {
            authService.sendVerificationCode(body.get("tel"));
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    @PostMapping("/login")
    public void login(@RequestBody Map<String, String> body, HttpServletResponse response) {
        if (body == null || body.get("tel") == null || body.get("code") == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }
        if (!authService.isCorrectCode(body.get("tel"), body.get("code"))) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(body.get("tel"), body.get("code"));
        token.setRememberMe(true);
        SecurityUtils.getSubject().login(token);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        if (UserContext.getContext() == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else {
            SecurityUtils.getSubject().logout();
        }
    }

    @GetMapping("/status")
    public LoginStatus loginStatus(HttpServletResponse response) {
        if (UserContext.getContext() == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return LoginStatus.logout(response.getStatus());
        }
        return LoginStatus.login(UserContext.getContext());
    }
}
