package com.spring.wxshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.wxshop.entity.LoginStatus;
import com.spring.wxshop.generated.User;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

public class BaseIntegrationTest {
    @Autowired
    Environment environment;

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    static final String apiVersion = "/api/v1";
    static final ObjectMapper objectMapper = new ObjectMapper();
    static final TelAndCode telAndCode = new TelAndCode("13812345678", "000000");
    static final TelAndCode anyTel = new TelAndCode("13812345678", null);
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

    public HttpResponse doHttpRequest(String apiName, HttpMethod method, Map<String, String> params) throws IOException {
        HttpUriRequest uriRequest = createRequest(getUrl(apiName), method, params);
        try (CloseableHttpResponse closeableHttpResponse = client.execute(uriRequest)) {
            return new HttpResponse(closeableHttpResponse);
        }
    }

    public String getUrl(String apiName) {
        String port = environment.getProperty("local.server.port");
        return "http://localhost:" + port + apiVersion + apiName;
    }

    public LoginResult getLoginResult() throws IOException {
        /* status: not logged in */
        HttpResponse httpResponse = doHttpRequest("/status", HttpMethod.GET, null);
        LoginStatus loginStatus = objectMapper.readValue(httpResponse.body, LoginStatus.class);
        Assertions.assertFalse(loginStatus.isLogin());

        /* send code to login */
        httpResponse = doHttpRequest("/code", HttpMethod.POST, anyTel.toMap());
        Assertions.assertEquals(HTTP_OK, httpResponse.code);

        /* login with tel and code */
        doHttpRequest("/login", HttpMethod.POST, telAndCode.toMap());
        String cookie = cookieStore.getCookies().stream().findFirst().map(Cookie::getValue).get();

        /* status: logged in */
        httpResponse = doHttpRequest("/status", HttpMethod.GET, null);
        loginStatus = objectMapper.readValue(httpResponse.body, LoginStatus.class);
        return new LoginResult(loginStatus.getUser(), cookie, loginStatus.isLogin());
    }

    static class LoginResult {
        User user;
        String cookie;
        boolean isLogin;

        LoginResult(User user, String cookie, boolean isLogin) {
            this.user = user;
            this.cookie = cookie;
            this.isLogin = isLogin;
        }
    }

    static class TelAndCode {
        String tel;
        String code;

        TelAndCode(String tel, String code) {
            this.tel = tel;
            this.code = code;
        }

        Map<String, String> toMap() {
            Map<String, String> map = new HashMap<>();
            map.put("tel", tel);
            map.put("code", code);
            return map;
        }
    }

    static class HttpResponse {
        int code;
        String body;
        List<Header> headers;

        HttpResponse(CloseableHttpResponse response) throws IOException {
            this.code = response.getStatusLine().getStatusCode();
            this.body = EntityUtils.toString(response.getEntity());
            this.headers = Arrays.asList(response.getAllHeaders());
        }

        HttpResponse assertOkStatusCode() {
            Assertions.assertTrue(code >= 200 && code < 300, "" + code + ": " + body);
            return this;
        }

        <T> T asJsonObject(TypeReference<T> typeReference) throws JsonProcessingException {
            return objectMapper.readValue(body, typeReference);
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
