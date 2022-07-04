/*
 * Copyright (C) 2011-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.copilot.management;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;


/**
 * The Helper class for test.
 *
 * <p>
 * Version 1.1 Change notes:
 *   <ol>
 *     <li>Added {@link #getBrowserStopperScript()} method.</li>
 *     <li>Added {@link #tearDown()} method.</li>
 *     <li>Added {@link #getPort()} method.</li>
 *     <li>Added {@link #getDomain()} method.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER, isv
 * @version 1.1
 */
public class TestHelper {
    /** Represent the thread sleep time. */
    static final long SLEEP = 500;

    /** The test config file. */
    private static final String TEST_CONFIG_FILE = "test_files/config.properties";

    /**
     * <p>Represents the test properties.</p>
     */
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = loadProperties(TEST_CONFIG_FILE);
    }

    /**
     * <p>Default constructor.</p>
     */
    private TestHelper() {
    }

    /**
     * To get the base index URL.
     *
     * @return base index url
     *
     * @throws Exception if any error occurred
     */
    static String getIndex() throws Exception {
        return PROPERTIES.getProperty("index");
    }

    /**
     * To get the browser for testing.
     *
     * @return browser
     *
     * @throws Exception if any error occurred
     */
    static String getBrowser() throws Exception {
        return PROPERTIES.getProperty("browser");
    }

    /**
     * To get the timeout for page loading.
     *
     * @return the timeout
     *
     * @throws Exception if any error occurred
     */
    static String getTimeout() throws Exception {
        return PROPERTIES.getProperty("timeout");
    }

    /**
     * Get the index Page.
     *
     * @return the index page.
     *
     * @throws Exception if any error occurred
     */
    static Selenium getIndexPage() throws Exception {
        Selenium browser = new DefaultSelenium("localhost", getPort(), getBrowser(), getDomain());
        browser.start();
        browser.open(getIndex());
        browser.setTimeout(getTimeout());

        return browser;
    }

    /**
     * Login the user.
     *
     * @param browser the selenium browser
     *
     * @throws Exception if any error occurred
     */
    static void loginUser(Selenium browser) throws Exception {
        browser.type("id=LoginForm_formData_username", PROPERTIES.getProperty("user1.username"));
        browser.type("id=LoginForm_formData_password", PROPERTIES.getProperty("user1.password"));
        browser.click("//a[@href='javascript:document.LoginForm.submit();']");
        browser.waitForPageToLoad(getTimeout());
    }

    /**
     * Get the upload file path.
     *
     * @return the file path
     */
    static String getUploadFile() {
        return PROPERTIES.getProperty("uploadfile");
    }

    /**
     * Get the Copilot pool entrance page.
     *
     * @return the Copilot pool entrance page.
     *
     * @throws Exception if any error occurred
     */
    static Selenium getCopilotPoolPage() throws Exception {
        Selenium browser = new DefaultSelenium("localhost", getPort(), getBrowser(), getIndex());
        browser.start();
        browser.open(PROPERTIES.getProperty("copilot_pool"));

        return browser;
    }

    /**
     * Load the Properties object from the configuration object.
     *
     * @param configFilePath the file path of the configuration file
     *
     * @return the properties loaded
     */
    private static Properties loadProperties(String configFilePath) {
        InputStream in = null;

        try {
            Properties prop = new Properties();
            in = new FileInputStream(configFilePath);
            prop.load(in);

            return prop;
        } catch (IOException e) {
            // simply return null, it will never happen
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // ignore the exception
                }
            }
        }
    }

    /**
     * Return a long characters string. Based on 200 characters.
     *
     * @param times the times to 200 chars
     * @return the long characters string
     */
    static String getLongChars(int times) {
        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < times; i++) {
            sb.append(
                "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong"
                    + "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong"
                    + "longlonglonglonglonglonglonglonglong");
        }

        return sb.toString();
    }

    /**
     * Tears down the testing environment.
     *
     * @throws Exception if any error occurs.
     * @since 1.1
     */
    static void tearDown() throws Exception {
        String browserStopperScript = getBrowserStopperScript();
        if (browserStopperScript != null && browserStopperScript.trim().length() > 0) {
            Process stopperProcess = Runtime.getRuntime().exec(browserStopperScript);
            stopperProcess.waitFor();
        }
    }

    /**
     * To get the script for stopping the browser (optional).
     *
     * @return the path to script for stopping the browser.
     * @since 1.1
     */
    static String getBrowserStopperScript() {
        return PROPERTIES.getProperty("browserStopperScript");
    }

    /**
     * To get the port for base index URL.
     *
     * @return base index port
     * @since 1.1
     */
    static int getPort() {
        return Integer.parseInt(PROPERTIES.getProperty("seleniumPort"));
    }

    /**
     * To get the domain for Selenium server.
     *
     * @return the domai for Selenium server.
     * @since 1.1
     */
    static String getDomain() {
        return PROPERTIES.getProperty("seleniumDomain");
    }

    /**
     * To get the path to directory where to put files with content of browser pages for failed tests.
     *
     * @return the path to directory.
     * @since 1.1
     */
    static String getBrowserPageContentsDir() {
        return PROPERTIES.getProperty("browserPageContentsDir");
    }


}
