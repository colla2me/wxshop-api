package com.spring.wxshop.controller;

import com.spring.wxshop.WxshopApplication;
import com.spring.wxshop.entity.LoginStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static java.net.HttpURLConnection.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WxshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.config.location=classpath:test-application.yml"})
class AuthIntegrationTest extends BaseIntegrationTest {

    @Test
    public void testLoginThenLogout() throws IOException {
        LoginResult loginResult = getLoginResult();

        Assertions.assertTrue(loginResult.isLogin);
        Assertions.assertEquals(anyTel.tel, loginResult.user.getTel());

        doHttpRequest("/api/v1/logout", HttpMethod.POST, null);

        HttpResponse httpResponse = doHttpRequest("/api/v1/status", HttpMethod.GET, null);
        LoginStatus loginStatus = objectMapper.readValue(httpResponse.body, LoginStatus.class);
        Assertions.assertFalse(loginStatus.isLogin());
    }

    @Test
    public void testLoginWhenCodeIsWrong() throws IOException {
        HttpResponse response = doHttpRequest("/api/v1/login", HttpMethod.POST, wrongCode.toMap());
        Assertions.assertEquals(HTTP_FORBIDDEN, response.code);
    }

    @Test
    public void testSmsCodeWhenParamsIsCorrect() throws IOException {
        HttpResponse response = doHttpRequest("/api/v1/code", HttpMethod.POST, telAndCode.toMap());
        Assertions.assertEquals(HTTP_OK, response.code);
    }

    @Test
    public void testSmsCodeWhenParamsIsEmpty() throws IOException {
        HttpResponse response = doHttpRequest("/api/v1/code", HttpMethod.POST, allEmpty.toMap());
        Assertions.assertEquals(HTTP_BAD_REQUEST, response.code);
    }
}
