/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.contest.management;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;


/**
 * The Helper class for test.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper {
    /** Represent the thread sleep time. */
    static final long SLEEP = 500;

    /** The test config file. */
    private static final String TEST_CONFIG_FILE = "test_files/config.properties";

    /**
     * <p>Represents the test properties.</p>
     */
    private static final Properties properties;

    /**
     * <p>Represents base context.</p>
     */
    private static final int PORT = 4444;

    /**
     * Load the default configurations.
     */
    static {
        properties = loadProperties(TEST_CONFIG_FILE);
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
        return properties.getProperty("index");
    }

    /**
     * To get the browser for testing.
     *
     * @return browser
     *
     * @throws Exception if any error occurred
     */
    static String getBrowser() throws Exception {
        return properties.getProperty("browser");
    }

    /**
     * To get the timeout for page loading.
     *
     * @return the timeout
     *
     * @throws Exception if any error occurred
     */
    static String getTimeout() throws Exception {
        return properties.getProperty("timeout");
    }

    /**
     * Get the index Page.
     *
     * @return the index page.
     *
     * @throws Exception if any error occurred
     */
    static Selenium getIndexPage() throws Exception {
        Selenium browser = new DefaultSelenium("localhost", PORT, getBrowser(), getIndex());
        browser.start();
        browser.open(getIndex());

        return browser;
    }

    /**
     * Login the user.
     *
     * @return the logged in page.
     *
     * @throws Exception if any error occurred
     */
    static void loginUser(Selenium browser) throws Exception {
    	browser.type("id=LoginForm_formData_username", properties.getProperty("user1.username"));
		browser.type("id=LoginForm_formData_password", properties.getProperty("user1.password"));
		browser.click("//a[@href='javascript:document.LoginForm.submit();']");
		browser.waitForPageToLoad(getTimeout());
    }

    /**
     * Get the upload file path.
     * @return the file path
     */
	static String getUploadFile() {
		return properties.getProperty("uploadfile");
	}

    /**
     * Load the Properties object from the configuration object.
     *
     * @param configFilePath the file path of the configuration file
     *
     * @return the properties loaded
     *
     * @throws Exception if any error
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
     * Perform the step 1 of creating new non-studio contest.
     * @throws Exception if any error.
     */
	static String performSoftwareStep1(Selenium browser) throws Exception {
    	browser.click("link=Launch New Contest");
    	browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.selectedTxt");
		browser.click("link=Software Conceptualization");
		browser.check("id=lccCheckBox");
		String projectName = "Project " + System.currentTimeMillis();
		browser.type("id=contestName", projectName);
		browser.select("projects", "Client 1 Billing Account 1 Project 2");
		browser.select("billingProjects", "Client 1 Billing Account 1");
		browser.select("contestCopilot", "Unassigned");
		browser.select("roundTypes", "Contest will be run in multi-rounds");
		browser.type("startDate", "11/22/2022");
		browser.click("//a[@href='javascript:continueContestSelection();']");
		Thread.sleep(TestHelper.SLEEP);
		return projectName;
	}

    /**
     * Perform the step 2 of creating new non-studio contest.
     * @throws Exception if any error.
     */
	static void performSoftwareStep2(Selenium browser) throws Exception {
		browser.type("dom=document.getElementById('swDetailedRequirements_ifr').contentDocument.body", "This is test contest.");
		browser.type("dom=document.getElementById('swGuidelines_ifr').contentDocument.body", "This is test contest.");
		browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
		browser.type("swFileDescription", "This is requirement doc.");
		browser.click("swFileUploadBtn");
		Thread.sleep(TestHelper.SLEEP);
	}

    /**
     * Perform the step 1 of creating new studio contest.
     * @throws Exception if any error.
     */
	static void performStudioStep1(Selenium browser) throws Exception {
    	browser.click("link=Launch New Contest");
    	browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.selectedTxt");
		browser.click("link=Web Design");
		browser.type("id=contestName", "Test Contest");
		browser.select("projects", "Client 1 Billing Account 1 Project 2");
		browser.select("billingProjects", "Client 1 Billing Account 1");
		browser.select("contestCopilot", "Unassigned");
		browser.select("roundTypes", "Contest will be run in multi-rounds");
		browser.type("startDate", "11/22/2022");
		browser.click("//a[@href='javascript:continueContestSelection();']");
		Thread.sleep(TestHelper.SLEEP);
	}
}
