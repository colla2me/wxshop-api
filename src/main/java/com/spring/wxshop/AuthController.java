package com.spring.wxshop;

import com.spring.wxshop.service.AuthService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void code(@RequestBody Map<String, String> body) {
        authService.sendVerificationCode(body.get("tel"));
    }

    @PostMapping("/login")
    public void login(@RequestBody Map<String, String> body) {
        UsernamePasswordToken token = new UsernamePasswordToken(body.get("tel"), body.get("code"));
        token.setRememberMe(true);
        SecurityUtils.getSubject().login(token);
    }
}
