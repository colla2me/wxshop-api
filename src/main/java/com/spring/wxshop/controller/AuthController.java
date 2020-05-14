package com.spring.wxshop.controller;

import com.spring.wxshop.entity.LoginResult;
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
@RequestMapping("/api")
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
        if (body != null && authService.isValidCodeForTel(body.get("tel"), body.get("code"))) {
            UsernamePasswordToken token = new UsernamePasswordToken(body.get("tel"), body.get("code"));
            token.setRememberMe(true);
            SecurityUtils.getSubject().login(token);
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    @PostMapping("/logout")
    public void logout() {
        UserContext.clear();
        SecurityUtils.getSubject().logout();
    }

    @GetMapping("/status")
    @ResponseBody
    public Object loginStatus() {
        if (UserContext.getUser() == null) {
            return LoginResult.notLoggedIn();
        }
        return LoginResult.loggedIn(UserContext.getUser());
    }
}
