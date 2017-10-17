/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.direct.services.configs.ServerConfiguration;
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
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.Cookie;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        if (jwtCookieV2 == null) {
            throw new JwtAuthenticationException("Please re-login");
        }

        validateCookieV2V3(jwtCookieV2,jwtCookieV3);
        v2Token = jwtCookieV2.getValue();
        return this;
    }


    private String getRefreshTokenFromApi(String oldToken) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
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

            return result.path("result").path("content").path("token").asText();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    /**
     * Verify token. If token expired: refresh it
     *
     * @param v3token  the v3 jwt token
     * @param v2token the v2 jwt token
     * @return
     * @throws JwtAuthenticationException
     */
    private String getValidJwtToken(String v3token, String v2token) throws JwtAuthenticationException {
        String[] tokenSplit = v3token.split("\\.");
        boolean valid = tokenSplit.length >= 2;

        try {
            if (valid) {
                StringBuilder payloadStr = new StringBuilder(tokenSplit[1]);
                while (payloadStr.length() % 4 != 0) payloadStr.append('=');
                String payload = new String(Base64.decodeBase64(payloadStr.toString().getBytes(StandardCharsets.UTF_8)));

                JsonNode jsonNode = objectMapper.readValue(payload, JsonNode.class);

                long exp = jsonNode.get("exp").getLongValue();
                Date expDate = new Date(exp * 1000);
                logger.info("token expire at: " + expDate);
                if (expDate.after(new Date())) {
                    return v3token;
                }
            }

            logger.info("refresh v3 token for : " + v2token);
            String newToken = getRefreshTokenFromApi(v2token);
            if (newToken == null || newToken.isEmpty()) {
                throw new JwtAuthenticationException("Invalid refreshed token - " + newToken);
            }

            return newToken;
        } catch (JwtAuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new JwtAuthenticationException("Failed to refresh toke through api, Please go to sso login page : " +
                    getSsoLoginUrl());
        }
    }

    /**
     * Validate cookie v2 and v3
     *
     * @param v2 cookie v2
     * @param v3 cookie v3
     * @throws Exception
     */
    private void validateCookieV2V3(Cookie v2, Cookie v3) throws Exception {
        String validToken;
        String v3Token = null;
        if (v3 == null) {
            validToken = getRefreshTokenFromApi(v2.getValue());
        } else {
            validToken = getValidJwtToken(v3.getValue(), v2.getValue());
            v3Token = v3.getValue();
        }

        if (!validToken.equals(v3Token)) {
            DirectUtils.addDirectCookie(ServletActionContext.getResponse(), ServerConfiguration.JWT_V3_COOKIE_KEY,  validToken, -1);
        }

        token = validToken;
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
