package com.spring.wxshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.wxshop.entity.LoginStatus;
import com.spring.wxshop.generated.User;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class BaseIntegrationTest {
    public enum HttpMethod {
        GET("GET"),
        POST("POST"),
        PATCH("PATCH");

        final String method;
        HttpMethod(String method) {
            this.method = method;
        }

        public String value() {
            return method;
        }
    }

    @Autowired
    Environment environment;

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    static final ObjectMapper objectMapper = new ObjectMapper();
    static final TelAndCode telAndCode = new TelAndCode("13812345678", "000000");
    static final TelAndCode onlyTel = new TelAndCode("13812345678", null);
    static final TelAndCode wrongCode = new TelAndCode("13812345678", "123456");
    static final TelAndCode allEmpty = new TelAndCode(null, null);

    final CookieStore cookieStore = new BasicCookieStore();
    final CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

    @BeforeEach
    public void setUp() {
        ClassicConfiguration conf = new ClassicConfiguration();
        conf.setDataSource(databaseUrl, databaseUsername, databasePassword);
        Flyway flyway = new Flyway(conf);
        flyway.clean();
        flyway.migrate();
    }

    @AfterEach
    public void tearDown() throws IOException {
        cookieStore.clear();
        client.close();
    }

    public CloseableHttpResponse doHttpRequest(String apiName, HttpMethod method, Map<String, String> params) throws IOException {
        HttpUriRequest uriRequest = createRequest(getUrl(apiName), method, params);
        return client.execute(uriRequest);
    }

    public String getUrl(String apiName) {
        return "http://localhost:" + environment.getProperty("local.server.port") + apiName;
    }

    public LoginResult getLoginResult() throws IOException {
        /* status: not logged in */
        CloseableHttpResponse httpResponse = doHttpRequest("/api/v1/status", HttpMethod.GET, null);
        LoginStatus loginStatus = objectMapper.readValue(httpResponse.getEntity().getContent(), LoginStatus.class);
        Assertions.assertFalse(loginStatus.isLogin());

        /* send code to login */
        httpResponse = doHttpRequest("/api/v1/code", HttpMethod.POST, onlyTel.toMap());
        Assertions.assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());

        /* login with tel and code */
        doHttpRequest("/api/v1/login", HttpMethod.POST, telAndCode.toMap());
        String cookie = cookieStore.getCookies().stream().findFirst().map(Cookie::getValue).get();

        /* status: logged in */
        httpResponse = doHttpRequest("/api/v1/status", HttpMethod.GET, null);
        loginStatus = objectMapper.readValue(httpResponse.getEntity().getContent(), LoginStatus.class);
        return new LoginResult(loginStatus.getUser(), cookie, loginStatus.isLogin());
    }

    public static class LoginResult {
        User user;
        String cookie;
        boolean isLogin;

        public LoginResult(User user, String cookie, boolean isLogin) {
            this.user = user;
            this.cookie = cookie;
            this.isLogin = isLogin;
        }
    }

    public static class TelAndCode {
        String tel;
        String code;

        public TelAndCode(String tel, String code) {
            this.tel = tel;
            this.code = code;
        }

        public Map<String, String> toMap() {
            Map<String, String> map = new HashMap<>();
            map.put("tel", tel);
            map.put("code", code);
            return map;
        }
    }

    private HttpUriRequest createRequest(String url, HttpMethod method, Map<String, String> params) {
        switch (method) {
            case GET:
                try {
                    URIBuilder builder = new URIBuilder(url);
                    if (params != null) {
                        params.forEach(builder::setParameter);
                    }
                    return new HttpGet(builder.build());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            case POST:
                try {
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setHeader("Content-type", "application/json");
                    httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(params), ContentType.APPLICATION_JSON));
                    return httpPost;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            case PATCH:
                try {
                    HttpPatch httpPatch = new HttpPatch();
                    httpPatch.setHeader("Content-type", "application/json");
                    httpPatch.setEntity(new StringEntity(objectMapper.writeValueAsString(params), ContentType.APPLICATION_JSON));
                    return httpPatch;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            default:
                return null;
        }
    }
}
