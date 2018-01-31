package com.topcoder.direct.services.view.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.UUID;
import java.util.Collection;
import java.util.Iterator;

/**
 * JwtSigner implementation based on the Ruby implementation from http://jwt.io
 * No support for RSA encryption at present
 *
 * Change on version 2.0
 * - Use JWTCreator.Builder to create token
 *
 * @version 2.0
 */
public class DirectJWTSigner {
    /**
     * Logging
     */
    private static final Logger logger = Logger.getLogger(DirectJWTSigner.class);
    /**
     * The base64 encoded secret.
     */
    private final String secret;

    /**
     * Secret encoder
     */
    private JWTToken.SecretEncoder secretEncoder = new JWTToken.SecretEncoder();

    /**
     * Create the JWT signer
     *
     * @param secret  secret.
     */
    public DirectJWTSigner(String secret) {
        this(secret, null);
    }

    /**
     * Create the JWT signer with specific encoder
     *
     * @param secret secret
     * @param secretEncoder secret encoder
     */
    public DirectJWTSigner(String secret, JWTToken.SecretEncoder secretEncoder) {
        this.secret = secret;
        if (secretEncoder != null) {
            this.secretEncoder = secretEncoder;
        }
    }

    /**
     * Generate a JSON Web Token.
     * using the default algorithm HMAC SHA-256 ("HS256")
     * and no claims automatically set.
     *
     * @param claims  A map of the JWT claims that form the payload. Registered claims
     *                must be of appropriate Java datatype as following:
     *                <ul>
     *                <li>iss, sub: String
     *                <li>exp, nbf, iat, jti: numeric, eg. Long
     *                <li>aud: String, or Collection&lt;String&gt;
     *                </ul>
     *                All claims with a null value are left out the JWT.
     *                Any claims set automatically as specified in
     *                the "options" parameter override claims in this map.
     * @param options Allow choosing the signing algorithm, and automatic setting of some registered claims.
     */
    public String sign(Map<String, Object> claims, Options options) throws Exception{
        Algorithm algorithm = Algorithm.HMAC256(secretEncoder.encode(secret));
        if (options != null && options.algorithm != null) {
            algorithm = options.algorithm;
        }
        JWTCreator.Builder builder = buildPayload(claims, options);
        return builder.sign(algorithm);
    }

    /**
     * Create JWTCreator.Builder from claims Map
     *
     * @param claims Key to use in signing. Used as-is without Base64 encoding.
     *               <p/>
     *               For details, see the two parameter variant of this method.
     */
    public String sign(Map<String, Object> claims) throws Exception{
        return sign(claims, null);
    }

    /**
     * Generate the JWTCreator.Builder payload string from the claims.
     *
     * @param options
     */
    private JWTCreator.Builder buildPayload(Map<String, Object> _claims, Options options) throws Exception {
        Map<String, Object> claims = new HashMap<String, Object>(_claims);
        enforceStringOrURI(claims, "iss");
        enforceStringOrURI(claims, "sub");
        enforceStringOrURICollection(claims, "aud");
        enforceIntDate(claims, "exp");
        enforceIntDate(claims, "nbf");
        enforceIntDate(claims, "iat");
        enforceString(claims, "jti");

        if (options != null)
            processPayloadOptions(claims, options);

        JWTCreator.Builder builder = JWT.create();
        String[] _dateTypeClaims = new String[]{"exp", "nbf", "iat"};
        List<String> dateTypeClaims = Arrays.asList(_dateTypeClaims);

        logger.info("Build jwt claims");
        for (String key : claims.keySet()) {
            if (!dateTypeClaims.contains(key)) {
                builder.withClaim(key, (String)claims.get(key));
            }
        }

        if (claims.get("exp") != null && (Long)claims.get("exp") > 0)
            builder.withExpiresAt(new Date((Long)claims.get("exp") * 1000));

        if (claims.get("nbf") != null && (Long)claims.get("nbf") > 0)
            builder.withNotBefore(new Date((Long)claims.get("nbf") * 1000));

        if (claims.get("iat") != null && (Long)claims.get("iat") > 0)
            builder.withIssuedAt(new Date((Long)claims.get("iat") * 1000));

        return builder;
    }

    private void processPayloadOptions(Map<String, Object> claims, Options options) {
        long now = System.currentTimeMillis() / 1000l;
        if (options.expirySeconds != null)
            claims.put("exp", now + options.expirySeconds);
        if (options.notValidBeforeLeeway != null)
            claims.put("nbf", now - options.notValidBeforeLeeway);
        if (options.isIssuedAt())
            claims.put("iat", now);
        if (options.isJwtId())
            claims.put("jti", UUID.randomUUID().toString());
    }

    private void enforceIntDate(Map<String, Object> claims, String claimName) {
        Object value = handleNullValue(claims, claimName);
        if (value == null)
            return;
        if (!(value instanceof Number)) {
            throw new RuntimeException(String.format("Claim '%s' is invalid: must be an instance of Number", claimName));
        }
        long longValue = ((Number) value).longValue();
        if (longValue < 0)
            throw new RuntimeException(String.format("Claim '%s' is invalid: must be non-negative", claimName));
        claims.put(claimName, longValue);
    }

    private void enforceStringOrURICollection(Map<String, Object> claims, String claimName) {
        Object values = handleNullValue(claims, claimName);
        if (values == null)
            return;
        if (values instanceof Collection) {
            @SuppressWarnings({"unchecked"})
            Iterator<Object> iterator = ((Collection<Object>) values).iterator();
            while (iterator.hasNext()) {
                Object value = iterator.next();
                String error = checkStringOrURI(value);
                if (error != null)
                    throw new RuntimeException(String.format("Claim 'aud' element is invalid: %s", error));
            }
        } else {
            enforceStringOrURI(claims, "aud");
        }
    }

    private void enforceStringOrURI(Map<String, Object> claims, String claimName) {
        Object value = handleNullValue(claims, claimName);
        if (value == null)
            return;
        String error = checkStringOrURI(value);
        if (error != null)
            throw new RuntimeException(String.format("Claim '%s' is invalid: %s", claimName, error));
    }

    private void enforceString(Map<String, Object> claims, String claimName) {
        Object value = handleNullValue(claims, claimName);
        if (value == null)
            return;
        if (!(value instanceof String))
            throw new RuntimeException(String.format("Claim '%s' is invalid: not a string", claimName));
    }

    private Object handleNullValue(Map<String, Object> claims, String claimName) {
        if (!claims.containsKey(claimName))
            return null;
        Object value = claims.get(claimName);
        if (value == null) {
            claims.remove(claimName);
            return null;
        }
        return value;
    }

    private String checkStringOrURI(Object value) {
        if (!(value instanceof String))
            return "not a string";
        String stringOrUri = (String) value;
        if (!stringOrUri.contains(":"))
            return null;
        try {
            new URI(stringOrUri);
        } catch (URISyntaxException e) {
            return "not a valid URI";
        }
        return null;
    }

    /**
     * An option object for JWT signing operation. Allow choosing the algorithm, and/or specifying
     * claims to be automatically set.
     */
    public static class Options {
        private Algorithm algorithm;
        private Integer expirySeconds;
        private Integer notValidBeforeLeeway;
        private boolean issuedAt;
        private boolean jwtId;

        public Algorithm getAlgorithm() {
            return algorithm;
        }

        /**
         * Algorithm to sign JWT with. Default is <code>HS256</code>.
         */
        public Options setAlgorithm(Algorithm algorithm) {
            this.algorithm = algorithm;
            return this;
        }


        public Integer getExpirySeconds() {
            return expirySeconds;
        }

        /**
         * Set JWT claim "exp" to current timestamp plus this value.
         * Overrides content of <code>claims</code> in <code>sign()</code>.
         */
        public Options setExpirySeconds(Integer expirySeconds) {
            this.expirySeconds = expirySeconds;
            return this;
        }

        public Integer getNotValidBeforeLeeway() {
            return notValidBeforeLeeway;
        }

        /**
         * Set JWT claim "nbf" to current timestamp minus this value.
         * Overrides content of <code>claims</code> in <code>sign()</code>.
         */
        public Options setNotValidBeforeLeeway(Integer notValidBeforeLeeway) {
            this.notValidBeforeLeeway = notValidBeforeLeeway;
            return this;
        }

        public boolean isIssuedAt() {
            return issuedAt;
        }

        /**
         * Set JWT claim "iat" to current timestamp. Defaults to false.
         * Overrides content of <code>claims</code> in <code>sign()</code>.
         */
        public Options setIssuedAt(boolean issuedAt) {
            this.issuedAt = issuedAt;
            return this;
        }

        public boolean isJwtId() {
            return jwtId;
        }

        /**
         * Set JWT claim "jti" to a pseudo random unique value (type 4 UUID). Defaults to false.
         * Overrides content of <code>claims</code> in <code>sign()</code>.
         */
        public Options setJwtId(boolean jwtId) {
            this.jwtId = jwtId;
            return this;
        }
    }
}