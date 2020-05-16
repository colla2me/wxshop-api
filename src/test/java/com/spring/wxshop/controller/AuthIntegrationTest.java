package com.spring.wxshop.controller;

import com.spring.wxshop.WxshopApplication;
import com.spring.wxshop.entity.LoginStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WxshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.config.location=classpath:test-application.yml"})
class AuthIntegrationTest extends BaseIntegrationTest {

    @Test
    public void testLogout() throws IOException {
        LoginResult loginResult = getLoginResult();

        Assertions.assertTrue(loginResult.isLogin);
        Assertions.assertEquals(BaseIntegrationTest.onlyTel.tel, loginResult.user.getTel());

        doHttpRequest("/api/v1/logout", HttpMethod.POST, null);

        CloseableHttpResponse httpResponse = doHttpRequest("/api/v1/status", HttpMethod.GET, null);
        LoginStatus loginStatus = objectMapper.readValue(httpResponse.getEntity().getContent(), LoginStatus.class);
        Assertions.assertFalse(loginStatus.isLogin());
    }
}