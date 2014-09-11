/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user.ejb;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;

import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.NamespaceConflictException;
import com.topcoder.configuration.persistence.UnrecognizedFileTypeException;
import com.topcoder.configuration.persistence.UnrecognizedNamespaceException;


/**
 * <p>
 * Copied from Cockpit Authentication Module Implementation. It queries security database to verify UserPasswordCredentials.
 * </p>
 *
 */
public class Util {
    /**
     * <p>
     * The configuration namespace in ConfigurationFileManager.properties.
     * </p>
     */
    private static final String CONFIG_FILE_NS = "com.topcoder.cockpit.security";

    /**
     * <p>
     * The configuration namespace for this class.
     * </p>
     */
    private static final String CONFIG_NS = "com.topcoder.cockpit.security";

    /**
     * <p>
     * DB connection factory key.
     * </p>
     */
    private static final String DB_CONNECTOIN_FACTORY_KEY = "DBConnectionFactoryKey";

    /**
     * <p>
     * DB connection factory key.
     * </p>
     */
    private static final String CONNECTION_NAME_KEY = "authenticationModuleConnectionName";

    /**
     * <p>
     * Key store file name key.
     * </p>
     */
    private static final String KEY_STORE_FILE_NAME_KEY = "keystore";

    /**
     * <p>
     * Key store password key.
     * </p>
     */
    private static final String KEY_STORE_PASSWORD_KEY = "kspassword";

    /**
     * <p>
     * Alias key.
     * </p>
     */
    private static final String ALIAS_KEY = "alias";


    /**
     * The key which will be used to encrypt password. This is initialized in the constructor.
     */
    private static final Key encryptionKey;

    /**
     * <p>
     * A String used for base64 encoding of byte arrays.
     * </p>
     */
    private static final String base64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    /**
     * <p>
     * Default Constructor and it will initialize connection factory and connection name (could be null).
     * </p>
     *
     * @throws ExceptionInInitializerError if any error occurs during construction
     */
    static {
        try {
            ConfigurationFileManager cfm = new ConfigurationFileManager();
            ConfigurationObject config = cfm.getConfiguration(CONFIG_FILE_NS).getChild(CONFIG_NS);
            encryptionKey = loadKey(getRequiredStringValue(config, KEY_STORE_FILE_NAME_KEY), 
                                   getRequiredStringValue(config, KEY_STORE_PASSWORD_KEY), getRequiredStringValue(config, ALIAS_KEY));
        } catch (ConfigurationParserException e) {
            throw new ExceptionInInitializerError(e);
        } catch (UnrecognizedFileTypeException e) {
            throw new ExceptionInInitializerError(e);
        } catch (NamespaceConflictException e) {
            throw new ExceptionInInitializerError(e);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        } catch (ConfigurationAccessException e) {
            throw new ExceptionInInitializerError(e);
        } catch (UnrecognizedNamespaceException e) {
            throw new ExceptionInInitializerError(e);
        } 
    }

    /**
     * Loads a encryption key.
     *
     * @param keyStoreFileName key store file name
     * @param keyStorePassword key store password
     * @param alias key alias
     * @return Key object
     */
    private static Key loadKey(String keyStoreFileName, String keyStorePassword, String alias) {
        InputStream is = null;

        try {
            is = new BufferedInputStream(new FileInputStream(keyStoreFileName));
            KeyStore ks = KeyStore.getInstance("JCEKS", "SunJCE");
            ks.load(is, keyStorePassword.toCharArray());
            return ks.getKey(alias, keyStorePassword.toCharArray());
        } catch (FileNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        } catch (KeyStoreException e) {
            throw new ExceptionInInitializerError(e);
        } catch (NoSuchProviderException e) {
            throw new ExceptionInInitializerError(e);
        } catch (NoSuchAlgorithmException e) {
            throw new ExceptionInInitializerError(e);
        } catch (CertificateException e) {
            throw new ExceptionInInitializerError(e);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        } catch (UnrecoverableKeyException e) {
            throw new ExceptionInInitializerError(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

  
    

    /**
     * Encodes the password before comparison.
     *
     * @param password string
     * @return encoded password string
     *
     * @throws NoSuchAlgorithmException error occurs
     * @throws NoSuchPaddingException error occurs
     * @throws InvalidKeyException error occurs
     * @throws IllegalBlockSizeException error occurs
     * @throws BadPaddingException error occurs
     */
    public static String encodePassword(String password) throws NoSuchAlgorithmException, NoSuchPaddingException,
        InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] encPasswd = new byte[0];

        Cipher c = Cipher.getInstance("Blowfish");
        c.init(Cipher.ENCRYPT_MODE, encryptionKey);
        encPasswd = c.doFinal(password.getBytes());
        return Util.encode64(encPasswd);
    }


    /**
     * <p>
     * Encode a byte array into a String using base 64 encoding.
     * </p>
     *
     * @param b a byte array to encode.
     *
     * @return the encoded String
     */
    public static String encode64(byte[] b) {
        int outputlength = ((b.length + 2) / 3) * 4;
        StringBuffer sb = new StringBuffer(outputlength);

        int len = (b.length / 3) * 3;
        int leftover = b.length - len;

        for (int i = 0; i < len; i += 3) {
            // get next three bytes in unsigned form lined up
            int combined = b[i] & 0xff;

            combined <<= 8;
            combined |= (b[i + 1] & 0xff);
            combined <<= 8;
            combined |= (b[i + 2] & 0xff);

            // break those 24 bits into 4 groups of 6 bits
            int c3 = combined & 0x3f;

            combined >>>= 6;

            int c2 = combined & 0x3f;

            combined >>>= 6;

            int c1 = combined & 0x3f;

            combined >>>= 6;

            int c0 = combined & 0x3f;

            // Translate them to equivalent alphanumeric char
            sb.append(base64.charAt(c0));
            sb.append(base64.charAt(c1));
            sb.append(base64.charAt(c2));
            sb.append(base64.charAt(c3));
        }

        if (leftover == 1) {
            sb.append(encode64(new byte[] {b[len], 0, 0}).substring(0, 2));
            sb.append("==");
        } else if (leftover == 2) {
            sb.append(encode64(new byte[] {b[len], b[len + 1], 0}).substring(0, 3));
            sb.append("=");
        }
        return sb.toString();
    }


    /**
     * <p>
     * Checks to see if object is null.
     * </p>
     *
     * @param object object value
     * @param objectName object name
     *
     * @throws IllegalArgumentException if object is null
     */
    static void checkNull(Object object, String objectName) {
        if (object == null) {
            throw new IllegalArgumentException(objectName + " should not be null.");
        }
    }

    /**
     * <p>
     * Checks if string value is null or empty.
     * </p>
     *
     * @param stringValue string value
     * @param stringName string name
     *
     * @throws IllegalArgumentException if string value is null or empty
     */
    static void checkStringNullOrEmpty(String stringValue, String stringName) {
        checkNull(stringValue, stringName);

        if (stringValue.trim().length() == 0) {
            throw new IllegalArgumentException(stringName + " should not be empty.");
        }
    }

    /**
     * Gets a required String value under specified key.
     *
     * @param config object
     * @param key to be used to get String value
     * @return String value
     *
     * @throws ExceptionInInitializerError if any error occurs or it is not a String or String value is null or empty
     */
    static String getRequiredStringValue(ConfigurationObject config, String key) {
        return getStringValue(config, key, true);
    }

    /**
     * Gets a String value under specified key.
     *
     * @param config object
     * @param key to be used to get String value
     * @param bRequired the key value is required or not?
     * @return String value
     *
     * @throws ExceptionInInitializerError if any error occurs or it is not a String or String value is null or empty
     *             when it is required
     */
    static String getStringValue(ConfigurationObject config, String key, boolean bRequired) {
        try {
            Object value = config.getPropertyValue(key);
            if (value instanceof String) {
                if (bRequired) {
                    configurationCheckRequiredNonEmptyStringValue((String) value, key);
                }
                return (String) value;
            } else {
                throw new ExceptionInInitializerError("Value of key '" + key + "' is not a String value.");
            }
        } catch (ConfigurationAccessException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * <p>
     * Checks to see if string value is not null and also non-empty value. It is used in
     * <code>CatalogManagerHelper</code> and <code>AssetManagerHelper</code>.
     * </p>
     *
     * @param strValue string value to be checked
     * @param propertyName property name to be used to construct detailed error message
     *
     * @throws ExceptionInInitializerError if any validation fails
     */
    private static void configurationCheckRequiredNonEmptyStringValue(String strValue, String propertyName) {
        if (strValue == null) {
            throw new ExceptionInInitializerError(propertyName + " is missing in configuration.");
        }

        if (strValue.trim().equals("")) {
            throw new ExceptionInInitializerError(propertyName + " should not be empty value.");
        }
    }


}
