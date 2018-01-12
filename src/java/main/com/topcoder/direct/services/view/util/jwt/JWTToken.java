/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util.jwt;

import java.net.URI;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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

import com.auth0.jwk.GuavaCachedJwkProvider;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import static sun.security.krb5.internal.Krb5.getErrorMessage;

/**
 * Jwt token. Main purpose is to verify token.
 * Verification based on token signature, issuers, and time validation
 *
 */
public class JWTToken {

    private static final Logger logger = Logger.getLogger(JWTToken.class);

    public static final String CLAIM_USER_ID = "userId";
    public static final String CLAIM_HANDLE = "handle";
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_ROLES = "roles";
    public static final String CLAIM_SUBJECT = "sub";

    public static final String CLAIM_ISSUER = "iss";
    public static final String CLAIM_ISSUED_TIME = "iat";
    public static final String CLAIM_EXPIRATION_TIME = "exp";

    public static final int DEFAULT_EXP_SECONDS = 10 * 60; // 10 mins

    private String userId;

    private String handle;

    private String email;

    private String issuer;

    private String subject;

    private String token;

    private String secret;

    private List<String> roles = new ArrayList<String>();

    private Integer expirySeconds = DEFAULT_EXP_SECONDS;

    private List<String> knownIssuers = new ArrayList<String>();

    private String algorithmName = "HS256";

    protected SecretEncoder encoder = new Base64SecretEncoder();

    private boolean valid = false;

    /**
     * authorizationUrl
     */
    private String authorizationURL;

    protected static final String ERROR_MESSAGE_FORMAT = "Service URL:%s, HTTP Status Code:%d, Error Message:%s";

    private static final String AUTHORIZATION_PARAMS = "{\"param\": {\"externalToken\": \"%s\"}}";

    protected static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy HH:mm"));
    }

    /**
     * Constructor
     *
     * @param token token
     * @param secret secret, if algorithm required it
     * @param knownIssuers  comma separate known issuers
     * @param secretEncoder encoder of secret
     * @throws JWTException
     */
    public JWTToken(String token, String secret, String knownIssuers, String authorizationURL, SecretEncoder secretEncoder) throws JWTException{
        if (token == null) {
            logger.error("token can not be null");
            throw new IllegalArgumentException("token can not be null");
        }
        if (knownIssuers == null) {
            logger.error("issuers can not be null");
            throw new IllegalArgumentException("issuers can not be null");
        }

        if (secretEncoder != null)
            this.encoder = secretEncoder;

        for (String issuer : knownIssuers.split("\\s*,\\s*")) {
            this.knownIssuers.add(issuer.trim());
        }

        this.authorizationURL = authorizationURL;
        this.token = token;
        this.secret = secret;
    }

    /**
     * Verify this JWT token
     *
     * @param algorithm  algorithm to be used
     * @throws JWTException
     */
    protected void verifyToken(Algorithm algorithm) throws JWTException {
        try {
            Verification verification = JWT.require(algorithm);

            JWTVerifier verifier = verification.build();
            DecodedJWT decodedJWT = verifier.verify(token);

            // Validate the issuer
            if (decodedJWT.getIssuer() == null || !knownIssuers.contains(decodedJWT.getIssuer())) {
                logger.error("Invalid issuer:" + decodedJWT.getIssuer() + ", token: " + token);
                throw new InvalidTokenException("Invalid issuer: " + decodedJWT.getIssuer() + ", token: " + token);
            }
            logger.info("This JWT Token was issued by " + decodedJWT.getIssuer() + " is valid");
        } catch (com.auth0.jwt.exceptions.TokenExpiredException e) {
            logger.error("Token is expired. " + token);
            throw new TokenExpiredException(token + "Token is expired.", e);
        } catch (SignatureVerificationException e) {
            logger.error("Token is invalid. " + token);
            throw new InvalidTokenException(token + "Token is invalid. " + e.getLocalizedMessage(), e);
        } catch (IllegalStateException e) {
            logger.error("Token is invalid. " + token);
            throw new InvalidTokenException(token + "Token is invalid. " + e.getLocalizedMessage(), e);
        } catch (Exception e) {
            logger.error("Error occurred in verifying token. " + token);
            throw new JWTException(token + "Error occurred in verifying token. " + e.getLocalizedMessage(),
                    e);
        }
    }

    /**
     * Verify this JWT class besed on token string
     *
     * @throws JWTException
     */
    public JWTToken verify() throws JWTException {
        return verify(this.encoder);
    }

    /**
     * Verify this JWT class based on token string
     *
     * @param enc secret encoder
     * @throws JWTException
     */
    public JWTToken verify(SecretEncoder enc) throws JWTException {
        valid = false;
        if (token == null)
            throw new IllegalArgumentException("token must be specified.");

        DecodedJWT decodedJWT = null;

        // Decode only first to get the algorithm
        try {
            decodedJWT = JWT.decode(token);
            logger.info("Jwt decoded payload: " + decodedJWT.getPayload());
        } catch (JWTDecodeException e) {
            throw new InvalidTokenException("Error occurred in decoding token: " + token + " : " + e.getLocalizedMessage(), e);
        }

        algorithmName = decodedJWT.getAlgorithm();

        // Create the algorithm
        logger.info("using algorithm: " + algorithmName);

        Algorithm algorithm;

        if ("RS256".equals(algorithmName)) {
            // Create the JWK provider with caching
            JwkProvider urlJwkProvider = new UrlJwkProvider(decodedJWT.getIssuer());
            JwkProvider jwkProvider = new GuavaCachedJwkProvider(urlJwkProvider);

            // Get the public key and create the algorithm
            try {
                logger.info("Getting pub key from " + decodedJWT.getIssuer());
                Jwk jwk = jwkProvider.get(decodedJWT.getKeyId());
                RSAPublicKey publicKey = (RSAPublicKey) jwk.getPublicKey();

                algorithm = Algorithm.RSA256(publicKey, null);
            } catch (Exception e) {
                logger.error("Error occurred in creating algorithm. token: "  + token);
                throw new JWTException("Error occurred in creating algorithm. token: "  + token + " . " + e.getLocalizedMessage(), e);
            }
        } else if ("HS256".equals(algorithmName)) {
            if (secret == null || secret.length() == 0) {
                logger.error("secret must be specified.");
                throw new IllegalArgumentException("secret must be specified.");
            }
            if (enc == null)
                enc = new SecretEncoder();

            // Create the algorithm
            try {
                algorithm = Algorithm.HMAC256(enc.encode(secret));
            } catch (Exception e) {
                throw new JWTException("Error occurred in creating algorithm. token: " + token + " . " + e.getLocalizedMessage(), e);
            }
        } else {
            throw new JWTException("Algorithm not supported: " + algorithmName);
        }
        verifyToken(algorithm);
        decodedJWTApply(decodedJWT);
        valid = true;
        return this;
    }

    /**
     * Set up fields from decodedJWT
     *
     * @param decodedJWT
     */
    @SuppressWarnings("unchecked")
    protected void decodedJWTApply(DecodedJWT decodedJWT) {
        // Search for the custom claims
        for (Entry<String, Claim> entry : decodedJWT.getClaims().entrySet()) {
            String key = entry.getKey();
            Claim claim = entry.getValue();

            if (key.contains(CLAIM_USER_ID)) {
                setUserId(claim.asString());
            } else if (key.contains(CLAIM_EMAIL)) {
                setEmail(claim.asString());
            } else if (key.contains(CLAIM_HANDLE)) {
                setHandle(claim.asString());
            } else if (key.contains(CLAIM_ROLES)) {
                setRoles(claim.as(List.class));
            } else if (key.contains(CLAIM_SUBJECT)) {
                setSubject(claim.asString());
            }
        }

        setIssuer(decodedJWT.getClaim(CLAIM_ISSUER).asString());
        Integer iat = decodedJWT.getClaim(CLAIM_ISSUED_TIME).asInt();
        Integer exp = decodedJWT.getClaim(CLAIM_EXPIRATION_TIME).asInt();
        if (exp != null) {
            setExpirySeconds(calcExpirySeconds(exp, iat));
        }
    }

    /**
     * Calculate expired time
     *
     * @param exp
     * @param iat
     * @return
     */
    protected Integer calcExpirySeconds(Integer exp, Integer iat) {
        if (exp == null)
            return null;
        int issuedAt = iat != null ? iat : (int) (System.currentTimeMillis() / 1000L);
        return exp - issuedAt;
    }

    /**
     * Refresh jwt from authorizationUrl
     *
     * @return this instance
     * @throws Exception if any error occurs
     */
    public JWTToken refresh() throws Exception {
        if (authorizationURL == null || "".equals(authorizationURL))
            throw new JWTException("Please set authorizationUrl");

        logger.info("Refresh token from this url: " + authorizationURL);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String newToken;
        try {
            URI authorizationUri = new URI(this.authorizationURL);
            HttpPost httpPost = new HttpPost(authorizationUri);
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            StringEntity body = new StringEntity(String.format(AUTHORIZATION_PARAMS, token));
            httpPost.setEntity(body);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new JWTEndpointException(String.format(ERROR_MESSAGE_FORMAT, authorizationUri,
                        response.getStatusLine().getStatusCode(),
                        getErrorMessage(response.getStatusLine().getStatusCode())));
            }

            JsonNode result = objectMapper.readTree(entity.getContent());
            newToken =  result.path("result").path("content").path("token").asText();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        setTokenAndSecret(newToken, secret);
        return this;
    }

    public String getUserId() {
        return userId;
    }

    /**
     * Set JWT claim "userId" (private).
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHandle() {
        return handle;
    }

    /**
     * Set JWT claim "handle" (private).
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Set JWT claim "email" (private).
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getIssuer() {
        return issuer;
    }

    /**
     * Set JWT claim "roles" (private).
     *
     * @param roles
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    /**
     * Set JWT claim "iss".
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Integer getExpirySeconds() {
        return expirySeconds;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Set JWT claim "exp" to current timestamp plus this value.
     */
    public void setExpirySeconds(Integer expirySeconds) {
        this.expirySeconds = expirySeconds;
    }

    public String getAlgorithm() {
        return algorithmName;
    }

    /**
     * Set algorithm (default: HS256 [HMAC SHA-256])
     */
    public void setAlgorithm(String algorithm) {
        this.algorithmName = algorithm;
    }

    public String getToken() {
        return token;
    }

    public String getAuthorizationURL() {
        return authorizationURL;
    }

    public void setAuthorizationURL(String authorizationURL) {
        this.authorizationURL = authorizationURL;
    }

    public boolean isValid() {
        return valid;
    }

    /**
     * Set new token
     *
     * @param token
     * @param secret
     * @throws JWTException
     */
    public void setTokenAndSecret(String token, String secret) throws JWTException {
        if (token == null) {
            throw new IllegalArgumentException("token can not be null");
        }
        this.token = token;
        this.secret = secret;
        verify();
    }

    public static class SecretEncoder {
        public byte[] encode(String secret) {
            return secret != null ? secret.getBytes() : null;
        }
    }

    public static class Base64SecretEncoder extends SecretEncoder {
        public byte[] encode(String secret) {
            return secret != null ? Base64.decodeBase64(secret) : null;
        }
    }
}