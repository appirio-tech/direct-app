package com.topcoder.service.facade.contest;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.ConfigManagerInterface;

import org.jboss.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Vector;

/**
 * A bunch of static methods used in various com.topcoder.security (and subpackage)
 * classes.  They do things like execute queries in the database and encoding
 * and decoding codenames (so plain text codenames aren't stored in the db).  Requires
 * ConfigManager properties file.
 *
 * The methods in this class are only intended to be used by classes within
 * the Security Manager component.  It is only public because the package
 * heirarchy requires it to be public in order for classes in subpackages of
 * com.topcoder.security to access it.
 */
public class CodeNameUtil implements ConfigManagerInterface {

    private static String PROPERTIES_NAMESPACE = "com.topcoder.security.Util";
    private static String PROPERTIES_FORMAT = ConfigManager.CONFIG_PROPERTIES_FORMAT;
    private Logger logger = Logger.getLogger(this.getClass());
    private static String base64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    //Supplies encryption provider
    static {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
    }

    /**
     * @return The ConfigManager for this namespace
     */
    public static ConfigManager getConfigManager()
            throws ConfigManagerException {

        //logger.debug("in getConfigManager");
        ConfigManager cm = ConfigManager.getInstance();
        //logger.debug("got ConfigManager");
        try {
/*
            if (cm.existsNamespace(PROPERTIES_NAMESPACE)) {
                //logger.debug("getConfigManager if");
                cm.refresh(PROPERTIES_NAMESPACE);
            } else {
                //logger.debug("getConfigManager else");
                cm.add(PROPERTIES_NAMESPACE, PROPERTIES_FORMAT);
            }
*/
            if (!cm.existsNamespace(PROPERTIES_NAMESPACE)) {
                cm.add(PROPERTIES_NAMESPACE, PROPERTIES_FORMAT);
            }
        } catch (ConfigManagerException e) {
            e.fillInStackTrace();
            throw e;
        }
        //logger.debug("getConfigManager - got cm");
        return cm;
    }

    /**
     * Get the particular property from the config manager
     *
     * @param property
     * @return The string property value
     */
    public static String getProperty(String property)
            throws Exception {

        //logger.debug("Util - getProperty");
        try {
            ConfigManager cm = getConfigManager();
            String prop = (String) cm.getProperty(PROPERTIES_NAMESPACE, property);
            return prop;
        } catch (ConfigManagerException e) {
            throw new Exception("" + e);
        }
    }

    /**
     * Part of <code>ConfigManagerInterface</code>
     *
     * @return current namespace
     */
    public String getNamespace() {
        return PROPERTIES_NAMESPACE;
    }

    /**
     * art of <code>ConfigManagerInterface</code>
     *
     * @return all known property keys in this namespace
     */
    public Enumeration getConfigPropNames() {
        Vector<String> propNames = new Vector<String>();
        propNames.add("keystore");
        propNames.add("kspassword");
        propNames.add("keystring");
        return propNames.elements();
    }

    /**
     * Gets the key tied to the given alias.  If the key does not already
     * exist, create and store it.  The alias is used to look up the key
     * in the keystore.  The location of the keystore is dependent on
     * configuration settings in this class's .properties file.
     *
     * @param alias Used to store and lookup a particular key within a
     *              keystore
     * @return Returns a Key
     */
    private static Key loadKey(String alias)
            throws ConfigManagerException {

        KeyStore ks;
        Key key;
        //logger.debug("Util - loadKey");
        //ConfigManager cm = getConfigManager();
        //logger.debug("got ConfigManager from getConfigManager");
        
        try {
		
			String keyStoreFileName = getProperty("keystore");
			String cryptPswd = getProperty("kspassword");
		
            ks = KeyStore.getInstance("JCEKS", "SunJCE");
            //logger.debug("got KeyStore");
            File f = new File(keyStoreFileName);  System.out.println("-------eeeeeeeeeeeeeeeeeexxx-------------"+f.exists());
            //f.delete();
            f.createNewFile();
            //logger.debug(Boolean.toString(f.exists()));
            try {
                ks.load(new FileInputStream(keyStoreFileName), cryptPswd.toCharArray());
                //logger.debug("ks.load");
                key = ks.getKey(alias, cryptPswd.toCharArray());
                //logger.debug("got key");
            } catch (java.io.EOFException e) {
                //logger.debug("in catch");
                key = storeKeyStore(ks, alias);
            }
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConfigManagerException("codename encryption error in com.topcoder.security.Util.loadKey: " + e);
        }
    }

    /**
     * Store a KeyStore.  This is called if a new key has to be created and
     * stored.  The location of the keystore is dependent on
     * configuration settings in this class's .properties file.  Uses the
     * Blowfish algorithm and its default initialization to generate a key.
     *
     * @param ks
     * @param alias Used to store and lookup a particular key within a
     *              keystore
     */
    private static Key storeKeyStore(KeyStore ks, String alias)
            throws ConfigManagerException {


        try {
		
			 String keyStoreFileName = getProperty("keystore");
			String cryptPswd = getProperty("kscodename");
		
            KeyGenerator kgen = KeyGenerator.getInstance("Blowfish");
            Key skey = kgen.generateKey();
            byte[] raw = skey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");

            //logger.debug("storeKeyStore key");
            Certificate[] chain = new Certificate[0];
            //logger.debug("in storeKeyStore try");
            ks.load(null, cryptPswd.toCharArray());
            ks.setKeyEntry(alias, skeySpec, cryptPswd.toCharArray(), chain);
            //logger.debug("setKeyEntry");
            ks.store(new FileOutputStream(keyStoreFileName), cryptPswd.toCharArray());
            //logger.debug("ks.store");
            return skeySpec;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConfigManagerException("codename encryption error in com.topcoder.security.Util.storeKeyStore: " + e.getMessage());
        }
    }

    /**
     * Depending on cipherMode, encrypt or decrypt codename using the key
     * retrieved or created with alias
     *
     * @param codenm
     * @param alias Used to look up a particular key within the keystore.
     * @param cipherMode
     * @return Returns the encrypted or decrypted codename string
     */
    private static byte[] encdec(byte[] codenm, String alias, int cipherMode)
            throws ConfigManagerException {

        //logger.debug("in encdec");
        Key key = loadKey(alias);
        //logger.debug("encdec - got key");
        byte[] enccodenm = new byte[0];
        try {
            //logger.debug("encdec - try");
            Cipher c = Cipher.getInstance("Blowfish");
            //logger.debug("encdec - got cipher");
            c.init(cipherMode, key);
            //logger.debug("encdec - init cipher");
            //logger.debug("codenm.length: " + codenm.length + " *" + new String(codenm) + "*");
            enccodenm = c.doFinal(codenm);
            //logger.debug("encdec - doFinal");
            //logger.debug("enccodenm.length: " + enccodenm.length);
            return enccodenm;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConfigManagerException("codename encrption error in com.topcoder.security.Util.encdec: " + e.getMessage());
        }
    }

    /**
     * Encrypt the codename using the key tied to alias.  After being
     * encrypted with a Blowfish key. the encrypted byte array is then
     * encoded with a base 64 encoding, resulting in the String that is
     * returned.
     *
     * @param codename
     * @param alias Used to look up a particular key within a keystore.  The
     *              location of the keystore is specified in a configuration
     *              file.
     * @return the encrypted and encoded codename
     */
    public static String encodeCodeName(String codename, String alias)
            throws ConfigManagerException {
        //logger.debug("in encodecodename");

        byte[] codenm = codename.getBytes();
        try {
            byte[] encCodeName = encdec(codenm, alias, Cipher.ENCRYPT_MODE);
            return encode64(encCodeName);
        } catch (Exception e) {
            throw new ConfigManagerException("ConfigManagerException in com.topcoder.security.Util.encodecodename: " + e.getMessage());
        }
    }

    /**
     * Decrypt the codename using the key tied to alias.  Takes a codename
     * that has been ecrypted and encoded, uses base 64 decoding to return
     * an ecypted byte array.  That byte array is then decrypted using a
     * Blowfish key into the original string.
     *
     * @param codename base 64 encoded String.
     * @param alias Used to look up a particular key within a keystore.  The
     *              location of the keystore is specified in a configuration
     *              file.
     * @return the decypted codename
     */
    public static String decodeCodeName(String codename, String alias)
            throws ConfigManagerException {


        byte[] codenm = decode64(codename);
        try {
            byte[] encCodeName = encdec(codenm, alias, Cipher.DECRYPT_MODE);
            return new String(encCodeName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConfigManagerException("ConfigManagerException in com.topcoder.security.Util.encodecodename: " + e.getMessage());
        }
    }

    /**
     * Decode a string that was encoded using a base 64 encoding into its
     * original bytes.
     *
     * @param s The String to be decoded
     * @return a byte[]
     */
    private static byte[] decode64(String s) {
        int len = s.length();
        //System.out.println("s = " + s);
        byte[] b = new byte[(s.length() / 4) * 3];
        int cycle = 0;
        int combined = 0;
        int j = 0;
        int dummies = 0;
        for (int i = 0; i < len; i++) {
            int c = s.charAt(i);
            int value = (c == (int) '=') ? -2 : ((c <= 255) ? base64.indexOf(c) : -1);
            if (value == -2) {
                value = 0;
                dummies++;
            }
            if (value != -1) {
                if (cycle == 0) {
                    combined = value;
                    cycle++;
                } else {
                    combined <<= 6;
                    combined |= value;
                    cycle++;
                }
                if (cycle == 4) {
                    b[j + 2] = (byte) combined;
                    combined >>>= 8;
                    b[j + 1] = (byte) combined;
                    combined >>>= 8;
                    b[j] = (byte) combined;
                    j += 3;
                    cycle = 0;
                }
            }
        }
        if (dummies > 0) {
            j -= dummies;
            byte[] b2 = new byte[j];
            System.arraycopy(b, 0, b2, 0, j);
            b = b2;
        }
        return b;
    }

    /**
     * Encode a byte array into a String using base 64 encoding.
     *
     * @param b
     * @return the encoded String
     */
    private static String encode64(byte[] b) {

        int outputlength = ((b.length + 2) / 3) * 4;
        StringBuffer sb = new StringBuffer(outputlength);

        int len = (b.length / 3) * 3;
        int leftover = b.length - len;

        for (int i = 0; i < len; i += 3) {
            //get next three bytes in unsigned form lined up
            int combined = b[i] & 0xff;
            combined <<= 8;
            combined |= b[i + 1] & 0xff;
            combined <<= 8;
            combined |= b[i + 2] & 0xff;

            //break those 24 bits into 4 groups of 6 bits
            int c3 = combined & 0x3f;
            combined >>>= 6;
            int c2 = combined & 0x3f;
            combined >>>= 6;
            int c1 = combined & 0x3f;
            combined >>>= 6;
            int c0 = combined & 0x3f;

            //Translate them to equivalent alphanumeric char
            sb.append(base64.charAt(c0));
            sb.append(base64.charAt(c1));
            sb.append(base64.charAt(c2));
            sb.append(base64.charAt(c3));
        }
        if (leftover == 1) {
            sb.append(encode64(new byte[]{b[len], 0, 0}
            ).substring(0, 2));
            sb.append("==");
        } else if (leftover == 2) {
            sb.append(encode64(new byte[]{b[len], b[len + 1], 0}
            ).substring(0, 3));
            sb.append("=");
        }
        return sb.toString();
    }
	



}
