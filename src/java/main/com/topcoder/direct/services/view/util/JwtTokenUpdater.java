/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.direct.services.configs.ServerConfiguration;
import com.topcoder.direct.services.view.dto.my.SingleRestResult;
import com.topcoder.direct.services.view.dto.my.Token;
import com.topcoder.direct.services.view.exception.JwtAuthenticationException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.Cookie;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import static sun.security.krb5.internal.Krb5.getErrorMessage;

/**
 * Utility class to check and update token from  cookie
 *
 */
public class JwtTokenUpdater {

    private static final Logger logger = Logger.getLogger(JwtTokenUpdater.class);

    /**
     * authorizationUrl
     */
    private String authorizationURL;

    /**
     * v3 token
     */
    private String token;

    private String v2Token = null;

    /**
     * ssoLogin Url
     */
    private String ssoLoginUrl;

    protected static final String ERROR_MESSAGE_FORMAT = "Service URL:%s, HTTP Status Code:%d, Error Message:%s";

    private static final String AUTHORIZATION_PARAMS = "{\"param\": {\"externalToken\": \"%s\"}}";

    protected static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy HH:mm"));
    }

    public JwtTokenUpdater() {

    }

    /**
     * Check token from cookie
     *
     * @return this class instance
     * @throws Exception
     */
    public JwtTokenUpdater check() throws Exception {
        Cookie jwtCookieV3 = DirectUtils.getCookieFromRequest(ServletActionContext.getRequest(),
                ServerConfiguration.JWT_V3_COOKIE_KEY);
        Cookie jwtCookieV2 = DirectUtils.getCookieFromRequest(ServletActionContext.getRequest(),
                ServerConfiguration.JWT_COOOKIE_KEY);

//        Cookie jwtCookieV2 = new Cookie(ServerConfiguration.JWT_COOOKIE_KEY,
//                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuaWNrbmFtZSI6IkZpcmVJY2UiLCJlbWFpbCI6ImVtYWlsQGRvbWFpbi5jb20ueiIsIm5hbWUiOiJlbWFpbEBkb21haW4uY29tLnoiLCJwaWN0dXJlIjoiaHR0cHM6Ly9zLmdyYXZhdGFyLmNvbS9hdmF0YXIvZGFjNGIwMjljNWRiZDliYjBkOTJkYzU4MGYwNTU4OWQ_cz00ODAmcj1wZyZkPWh0dHBzJTNBJTJGJTJGY2RuLmF1dGgwLmNvbSUyRmF2YXRhcnMlMkZlbS5wbmciLCJhcHBfbWV0YWRhdGEiOnsicm9sZXMiOlsidXNlciJdfSwicm9sZXMiOlsidXNlciJdLCJjbGllbnRJRCI6IkpGRG83SE1rZjBxMkNrVkZIb2p5M3pIV2FmemlwcmhUIiwidXBkYXRlZF9hdCI6IjIwMTctMDgtMjJUMTM6NDU6MTQuNjk4WiIsInVzZXJfaWQiOiJhdXRoMHwxNTA1MDQzNCIsImlkZW50aXRpZXMiOlt7InVzZXJfaWQiOiIxNTA1MDQzNCIsInByb3ZpZGVyIjoiYXV0aDAiLCJjb25uZWN0aW9uIjoiVEMtVXNlci1EYXRhYmFzZSIsImlzU29jaWFsIjpmYWxzZX1dLCJjcmVhdGVkX2F0IjoiMjAxNy0wMy0wN1QwMjo1ODowNC4xMDRaIiwiaXNzIjoiaHR0cHM6Ly90b3Bjb2Rlci1kZXYuYXV0aDAuY29tLyIsInN1YiI6ImF1dGgwfDE1MDUwNDM0IiwiYXVkIjoiSkZEbzdITWtmMHEyQ2tWRkhvankzekhXYWZ6aXByaFQiLCJleHAiOjE4NjM0MDk1MTQsImlhdCI6MTUwMzQwOTUxNH0.xktT_g2qRldBGhSbpwZR_82DZMxKzTHaCx4PTcYI2pI");

        if (jwtCookieV2 == null) {
            throw new JwtAuthenticationException("Please re-login");
        }

        validateCookieV2V3(jwtCookieV2,jwtCookieV3);
        v2Token = jwtCookieV2.getValue();
        return this;
    }


    private Token getRefreshTokenFromApi(String oldToken) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        SingleRestResult<Token> resultToken = null;
        try {
            URI authorizationUri = new URI(getAuthorizationURL());
            HttpPost httpPost = new HttpPost(authorizationUri);
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            StringEntity body = new StringEntity(String.format(AUTHORIZATION_PARAMS, oldToken));
            httpPost.setEntity(body);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new JwtAuthenticationException(String.format(ERROR_MESSAGE_FORMAT, authorizationUri,
                        response.getStatusLine().getStatusCode(),
                        getErrorMessage(response.getStatusLine().getStatusCode())));
            }

            JsonNode result = objectMapper.readTree(entity.getContent());
            resultToken = objectMapper.readValue(result.get("result"),
                    objectMapper.getTypeFactory().constructParametricType(SingleRestResult.class, Token.class));
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return resultToken.getContent();
    }

    /**
     * Verify token.If token expired: refresh it
     *
     * @param tokenV3
     * @param tokenV2
     * @return
     * @throws JwtAuthenticationException
     */
    private String getValidJwtToken(String tokenV3, String tokenV2) throws JwtAuthenticationException {
        String[] tokenSplit = tokenV3.split("\\.");
        boolean valid = true;
        if (tokenSplit.length < 2) valid = false;

        JsonNode jsonNode = null;

        try {
            if (valid) {
                StringBuffer payloadStr = new StringBuffer(tokenSplit[1]);
                while (payloadStr.length() % 4 != 0) payloadStr.append('=');
                String payload = new String(Base64.decodeBase64(payloadStr.toString().getBytes(StandardCharsets.UTF_8)));

                jsonNode = objectMapper.readValue(payload.toString(), JsonNode.class);

                long exp = jsonNode.get("exp").getLongValue();
                Date expDate = new Date(exp * 1000);
                logger.info("token expire at: " + expDate);
                if (expDate.before(new Date())) valid = false;
            }

            if (!valid) {
                logger.info("refresh new token for : " + tokenV2);
                Token newToken = getRefreshTokenFromApi(tokenV2);
                if (newToken == null || newToken.getToken().isEmpty()) {
                    throw new JwtAuthenticationException("Invalid refresh token");
                }

                return newToken.getToken();
            }
        } catch (Exception e) {
            throw new JwtAuthenticationException("Failed to refresh toke through api, Please go to sso login page : " +
                    getSsoLoginUrl());
        }
        return tokenV3;
    }

    /**
     * Validate cookie v2 and v3
     *
     * @param v2 cookie v2
     * @param v3 cookie v3
     * @throws Exception
     */
    private void validateCookieV2V3(Cookie v2, Cookie v3) throws Exception{
        String validToken = null;
        String v3Token = null;
        if (v3 == null) {
            validToken = getRefreshTokenFromApi(v2.getValue()).getToken();
        } else {
            validToken = getValidJwtToken(v3.getValue(), v2.getValue());
            v3Token = v3.getValue();
        }

        if (!validToken.equals(v3Token)) {
            DirectUtils.addDirectCookie(ServletActionContext.getResponse(), ServerConfiguration.JWT_V3_COOKIE_KEY,  validToken, -1);
            token = validToken;
        }
    }

    /**
     * True if user has logge-in and has v2token
     * Must be called after {@link #check()}
     * @return
     */
    public boolean isLoggedIn() {
        return v2Token != null && !v2Token.isEmpty();
    }

    public String getAuthorizationURL() {
        return authorizationURL;
    }

    public void setAuthorizationURL(String authorizationURL) {
        this.authorizationURL = authorizationURL;
    }

    public String getSsoLoginUrl() {
        return ssoLoginUrl;
    }

    public void setSsoLoginUrl(String ssoLoginUrl) {
        this.ssoLoginUrl = ssoLoginUrl;
    }

    /**
     * Get v3 token
     * Must be called after {@link #check()}
     * @return
     */
    public String getToken() {
        return token;
    }
}
