/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.contest.management;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.thoughtworks.selenium.Selenium;


/**
 * Functional test for cockpit contest management.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CreateContestsTests extends TestCase {
  /**
   * Represents a 200 length string.
   */
  private static final String TWO_HUNDRED = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
    /** Represents the Selenium Instance. */
    private Selenium browser;

    /**
     * Sets up the testing environment.
     *
     * @throws Exception if any error occurs.
     */
    public void setUp() throws Exception {
        browser = TestHelper.getIndexPage();
        TestHelper.loginUser(browser);
        super.setUp();
    }

    /**
     * Tears down the testing environment.
     *
     * @throws Exception if any error occurs.
     */
    public void tearDown() throws Exception {
        browser.stop();
        TestHelper.tearDown();
        super.tearDown();
    }

    /**
     * FTC 1: Verify contest can be created successfully (Conceptualization contest, multi-rounds).
     *
     * @throws Exception if any error
     */
    public void testFTC1() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    //browser.select("billingProjects", "Client 1 Billing Account 1");
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("contest information is incorrect", "Test Contest", browser.getText("rswContestName"));
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("rswProjectName"));
    
    assertEquals("contest information is incorrect", "Contest will be run in multi-rounds", browser.getText("rswRoundType"));
    assertEquals("contest information is incorrect", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rswStartDate"));
    assertEquals("contest information is incorrect", "11/25/2022 at 00:00 EST (UTC-05)", browser.getText("rswCheckpointDate"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswDetailedRequirements"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswSoftwareGuidelines"));
    assertEquals("contest information is incorrect", "test.rar", browser.getText("//dl[@id='swDocUploadList']/dt"));
    assertEquals("contest information is incorrect", "1,000.00", browser.getText("rswFirstPlaceCost"));
    assertEquals("contest information is incorrect", "500.00", browser.getText("rswSecondPlaceCost"));
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:activateContest();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("confirmation must be displayed", browser.isTextPresent("We have scheduled your competition and processed payment."));
    }

    /**
     * FTC 2: Verify contest can not be created successfully if pressing "NO" at last step (Conceptualization contest, multi-rounds).
     *
     * @throws Exception if any error
     */
    public void testFTC2() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    // browser.select("billingProjects", "Client 1 Billing Account 1");
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("contest information is incorrect", "Test Contest", browser.getText("rswContestName"));
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("rswProjectName"));
    
    assertEquals("contest information is incorrect", "Contest will be run in multi-rounds", browser.getText("rswRoundType"));
    assertEquals("contest information is incorrect", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rswStartDate"));
    assertEquals("contest information is incorrect", "11/25/2022 at 00:00 EST (UTC-05)", browser.getText("rswCheckpointDate"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswDetailedRequirements"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswSoftwareGuidelines"));
    assertEquals("contest information is incorrect", "test.rar", browser.getText("//dl[@id='swDocUploadList']/dt"));
    assertEquals("contest information is incorrect", "1,000.00", browser.getText("rswFirstPlaceCost"));
    assertEquals("contest information is incorrect", "500.00", browser.getText("rswSecondPlaceCost"));
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:activateContest();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=a.newButton1.noBtn > span.btnR > span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("creation should be cancelled", browser.isTextPresent("Dashboard > Launch New Contest"));
    }

    /**
     * FTC 3: Verify contest can be drafted successfully (Conceptualization contest, multi-rounds).
     *
     * @throws Exception if any error
     */
    public void testFTC3() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    String contestName = "Project " + System.currentTimeMillis();
    browser.type("id=contestName", contestName);
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");

    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:saveAsDraftOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    browser.click("link=Client 30010001 Billing Account 1 Project 1");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("//div[@class='projectDescDetails']/p"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='projectInforDiv']"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='dashboardTable']"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='areaHeader padding2 titleProjectStats']"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='areaHeader padding2 titleProjectForum']"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='areaHeader padding2 activityHeader']"));
    }

    /**
     * FTC 4: Verify contest can be created successfully (Conceptualization contest, single-round).
     *
     * @throws Exception if any error
     */
    public void testFTC4() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in single-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("contest information is incorrect", "Test Contest", browser.getText("rswContestName"));
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("rswProjectName"));
    
    assertEquals("contest information is incorrect", "Contest will be run in single-round", browser.getText("rswRoundType"));
    assertEquals("contest information is incorrect", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rswStartDate"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswDetailedRequirements"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswSoftwareGuidelines"));
    assertEquals("contest information is incorrect", "test.rar", browser.getText("//dl[@id='swDocUploadList']/dt"));
    assertEquals("contest information is incorrect", "1,000.00", browser.getText("rswFirstPlaceCost"));
    assertEquals("contest information is incorrect", "500.00", browser.getText("rswSecondPlaceCost"));
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:activateContest();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("confirmation must be displayed", browser.isTextPresent("We have scheduled your competition and processed payment."));
    }

    /**
     * FTC 5: Verify contest can be drafted successfully (Conceptualization contest, single-round).
     *
     * @throws Exception if any error
     */
    public void testFTC5() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in single-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:saveAsDraftOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    browser.click("link=Client 30010001 Billing Account 1 Project 1");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    }

    /**
     * FTC 6: Verify contest can be created successfully (Assembly contest).
     *
     * @throws Exception if any error
     */
    public void testFTC6() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Assembly");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.addSelection("id=masterTechnologiesSelect", "label=.NET 3.5");
    browser.click("id=addTechnologies");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("contest information is incorrect", "Test Contest", browser.getText("rswContestName"));
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("rswProjectName"));
    
    assertEquals("contest information is incorrect", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rswStartDate"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswDetailedRequirements"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswSoftwareGuidelines"));
    assertEquals("contest information is incorrect", "test.rar", browser.getText("//dl[@id='swDocUploadList']/dt"));
    assertEquals("contest information is incorrect", "1,000.00", browser.getText("rswFirstPlaceCost"));
    assertEquals("contest information is incorrect", "500.00", browser.getText("rswSecondPlaceCost"));
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:activateContest();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("confirmation must be displayed", browser.isTextPresent("We have scheduled your competition and processed payment."));
    }

    /**
     * FTC 7: Verify contest can be drafted successfully (Assembly contest).
     *
     * @throws Exception if any error
     */
    public void testFTC7() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Assembly");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.addSelection("id=masterTechnologiesSelect", "label=.NET 3.5");
    browser.click("id=addTechnologies");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:saveAsDraftOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    browser.click("link=Client 30010001 Billing Account 1 Project 1");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    }

    /**
     * FTC 8: Verify contest can be created successfully (Development contest, Dev-Only).
     *
     * @throws Exception if any error
     */
    public void testFTC8() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Component Development");
    browser.click("id=devOnlyCheckBox");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.addSelection("id=masterTechnologiesSelect", "label=.NET 3.5");
    browser.click("id=addTechnologies");
    browser.click("//div[@id='swCatalogDiv']/div/div/div");
    browser.click("link=.NET Custom");
    browser.addSelection("id=select1_categories", "label=.NET Custom");
    browser.click("id=addCategories");
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("contest information is incorrect", "Test Contest", browser.getText("rswContestName"));
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("rswProjectName"));
    
    assertEquals("contest information is incorrect", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rswStartDate"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswDetailedRequirements"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswSoftwareGuidelines"));
    assertEquals("contest information is incorrect", "test.rar", browser.getText("//dl[@id='swDocUploadList']/dt"));
    assertEquals("contest information is incorrect", "500.00", browser.getText("rswFirstPlaceCost"));
    assertEquals("contest information is incorrect", "250.00", browser.getText("rswSecondPlaceCost"));
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:activateContest();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("confirmation must be displayed", browser.isTextPresent("We have scheduled your competition and processed payment."));
    }

    /**
     * FTC 9: Verify contest can be drafted successfully (Development contest, Dev-Only).
     *
     * @throws Exception if any error
     */
    public void testFTC9() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Component Development");
    browser.click("id=devOnlyCheckBox");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.addSelection("id=masterTechnologiesSelect", "label=.NET 3.5");
    browser.click("id=addTechnologies");
    browser.click("//div[@id='swCatalogDiv']/div/div/div");
    browser.click("link=.NET Custom");
    browser.addSelection("id=select1_categories", "label=.NET Custom");
    browser.click("id=addCategories");
    browser.click("//a[@href='javascript:saveAsDraftOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    browser.click("link=Client 30010001 Billing Account 1 Project 1");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    }

    /**
     * FTC 10: Verify contest can be created successfully (Development contest).
     *
     * @throws Exception if any error
     */
    public void testFTC10() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Component Development");
    browser.type("id=contestName", "Test Contest");
    browser.click("id=devOnlyCheckBox");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.addSelection("id=masterTechnologiesSelect", "label=.NET 3.5");
    browser.click("id=addTechnologies");
    browser.click("//div[@id='swCatalogDiv']/div/div/div");
    browser.click("link=.NET Custom");
    browser.addSelection("id=select1_categories", "label=.NET Custom");
    browser.click("id=addCategories");
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("contest information is incorrect", "Test Contest", browser.getText("rswContestName"));
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("rswProjectName"));
    
    assertEquals("contest information is incorrect", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rswStartDate"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswDetailedRequirements"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswSoftwareGuidelines"));
    assertEquals("contest information is incorrect", "test.rar", browser.getText("//dl[@id='swDocUploadList']/dt"));
    assertEquals("contest information is incorrect", "500.00", browser.getText("rswFirstPlaceCost"));
    assertEquals("contest information is incorrect", "250.00", browser.getText("rswSecondPlaceCost"));
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:activateContest();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("confirmation must be displayed", browser.isTextPresent("We have scheduled your competition and processed payment."));
    }

    /**
     * FTC 11: Verify contest can be drafted successfully (Development contest).
     *
     * @throws Exception if any error
     */
    public void testFTC11() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Component Development");
    browser.type("id=contestName", "Test Contest");
    browser.click("id=devOnlyCheckBox");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.addSelection("id=masterTechnologiesSelect", "label=.NET 3.5");
    browser.click("id=addTechnologies");
    browser.click("//div[@id='swCatalogDiv']/div/div/div");
    browser.click("link=.NET Custom");
    browser.addSelection("id=select1_categories", "label=.NET Custom");
    browser.click("id=addCategories");
    browser.click("//a[@href='javascript:saveAsDraftOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    browser.click("link=Client 30010001 Billing Account 1 Project 1");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("//div[@class='projectDescDetails']/p"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='projectInforDiv']"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='dashboardTable']"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='areaHeader padding2 titleProjectStats']"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='areaHeader padding2 titleProjectForum']"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='areaHeader padding2 activityHeader']"));
    }

    /**
     * FTC 12: Verify contest can be created successfully (Design contest, create corresponding Development contest).
     *
     * @throws Exception if any error
     */
    public void testFTC12() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Component Design");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.addSelection("id=masterTechnologiesSelect", "label=.NET 3.5");
    browser.click("id=addTechnologies");
    browser.click("//div[@id='swCatalogDiv']/div/div/div");
    browser.click("link=.NET Custom");
    browser.addSelection("id=select1_categories", "label=.NET Custom");
    browser.click("id=addCategories");
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("contest information is incorrect", "Test Contest", browser.getText("rswContestName"));
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("rswProjectName"));
    
    assertEquals("contest information is incorrect", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rswStartDate"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswDetailedRequirements"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswSoftwareGuidelines"));
    assertEquals("contest information is incorrect", "test.rar", browser.getText("//dl[@id='swDocUploadList']/dt"));
    assertEquals("contest information is incorrect", "500.00", browser.getText("rswFirstPlaceCost"));
    assertEquals("contest information is incorrect", "250.00", browser.getText("rswSecondPlaceCost"));
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:activateContest();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("confirmation must be displayed", browser.isTextPresent("We have scheduled your competition and processed payment."));
    }

    /**
     * FTC 13: Verify contest can be created successfully (Design contest, does not create corresponding Development contest).
     *
     * @throws Exception if any error
     */
    public void testFTC13() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Component Design");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.addSelection("id=masterTechnologiesSelect", "label=.NET 3.5");
    browser.click("id=addTechnologies");
    browser.click("//div[@id='swCatalogDiv']/div/div/div");
    browser.click("link=.NET Custom");
    browser.addSelection("id=select1_categories", "label=.NET Custom");
    browser.click("id=addCategories");
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("contest information is incorrect", "Test Contest", browser.getText("rswContestName"));
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("rswProjectName"));
    
    assertEquals("contest information is incorrect", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rswStartDate"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswDetailedRequirements"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rswSoftwareGuidelines"));
    assertEquals("contest information is incorrect", "test.rar", browser.getText("//dl[@id='swDocUploadList']/dt"));
    assertEquals("contest information is incorrect", "500.00", browser.getText("rswFirstPlaceCost"));
    assertEquals("contest information is incorrect", "250.00", browser.getText("rswSecondPlaceCost"));
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:activateContest();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=a.newButton1.noBtn > span.btnR > span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("creation should be cancelled", browser.isTextPresent("Dashboard > Launch New Contest"));
    }

    /**
     * FTC 14: Verify contest can be drafted successfully (Design contest).
     *
     * @throws Exception if any error
     */
    public void testFTC14() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Component Design");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.addSelection("id=masterTechnologiesSelect", "label=.NET 3.5");
    browser.click("id=addTechnologies");
    browser.click("//div[@id='swCatalogDiv']/div/div/div");
    browser.click("link=.NET Custom");
    browser.addSelection("id=select1_categories", "label=.NET Custom");
    browser.click("id=addCategories");
    browser.click("//a[@href='javascript:saveAsDraftOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.chooseCancelOnNextConfirmation();
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//div[@class='modalBody operationSuccess']/div/a/span");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("//div[@class='projectDescDetails']/p"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='projectInforDiv']"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='dashboardTable']"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='areaHeader padding2 titleProjectStats']"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='areaHeader padding2 titleProjectForum']"));
    assertTrue("contest information is incorrect", browser.isElementPresent("//div[@class='areaHeader padding2 activityHeader']"));
    }

    /**
     * FTC 15: Verify contest can be created successfully (Web Design contest, multi-rounds).
     *
     * @throws Exception if any error
     */
    public void testFTC15() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Web Design");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    
    browser.runScript("CKEDITOR.instances['contestIntroduction'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['contestDescription'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round1Info'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round2Info'].setData('This is test contest.');");

    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=input.defaultFileType");
    browser.click("//input[@value='34']");
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("contest information is incorrect", "Test Contest", browser.getText("rContestName"));
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("rProjectName"));
    
    assertEquals("contest information is incorrect", "Contest will be run in multi-rounds", browser.getText("rRoundType"));
    assertEquals("contest information is incorrect", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rStartDate"));
    assertEquals("contest information is incorrect", "11/25/2022 at 00:00 EST (UTC-05)", browser.getText("rCheckpointDate"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rContestIntroduction"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rContestDescription"));
    assertEquals("contest information is incorrect", "test.rar", browser.getText("//dl[@id='docUploadList']/dt"));
    assertTrue("contest information is incorrect", browser.isTextPresent("1250"));
    assertTrue("contest information is incorrect", browser.isTextPresent("250"));
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:activateContest();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("confirmation must be displayed", browser.isTextPresent("We have scheduled your competition and processed payment."));
    }

    /**
     * FTC 16: Verify contest can be drafted successfully (Web Design contest, multi-rounds).
     *
     * @throws Exception if any error
     */
    public void testFTC16() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Web Design");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['contestIntroduction'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['contestDescription'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round1Info'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round2Info'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:saveAsDraftOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    browser.click("link=Client 30010001 Billing Account 1 Project 1");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    }

    /**
     * FTC 15: Verify contest can be created successfully (Web Design contest, single-rounds).
     *
     * @throws Exception if any error
     */
    public void testFTC17() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Web Design");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in single-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['contestIntroduction'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['contestDescription'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=input.defaultFileType");
    browser.click("//input[@value='34']");
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("contest information is incorrect", "Test Contest", browser.getText("rContestName"));
    assertEquals("contest information is incorrect", "Client 30010001 Billing Account 1 Project 1", browser.getText("rProjectName"));
    
    assertEquals("contest information is incorrect", "Contest will be run in single-round", browser.getText("rRoundType"));
    assertEquals("contest information is incorrect", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rStartDate"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rContestIntroduction"));
    assertEquals("contest information is incorrect", "This is test contest.", browser.getText("rContestDescription"));
    assertEquals("contest information is incorrect", "test.rar", browser.getText("//dl[@id='docUploadList']/dt"));
    assertTrue("contest information is incorrect", browser.isTextPresent("1250"));
    assertTrue("contest information is incorrect", browser.isTextPresent("250"));
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:activateContest();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("confirmation must be displayed", browser.isTextPresent("We have scheduled your competition and processed payment."));
    }

    /**
     * FTC 18: Verify contest can be drafted successfully (Web Design contest, single-rounds).
     *
     * @throws Exception if any error
     */
    public void testFTC18() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Web Design");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in single-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.runScript("CKEDITOR.instances['contestIntroduction'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['contestDescription'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:saveAsDraftOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//div[@id='tabs0']/ul/li[2]/a/span");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    browser.click("link=Client 30010001 Billing Account 1 Project 1");
    browser.waitForPageToLoad(TestHelper.getTimeout());
    }

    /**
     * FTC 25: Verify sql injection is handled while launching contest (step 1).
     *
     * @throws Exception if any error
     */
    public void testFTC25() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "select project_id from project");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("should go to step 2", browser.isElementPresent("contestIntroduction"));
    assertTrue("should go to step 2", browser.isElementPresent("contestIntroduction"));
    assertTrue("should go to step 2", browser.isElementPresent("css=#swUploadButtonDiv > input[name='document']"));
    assertTrue("should go to step 2", browser.isElementPresent("swFileDescription"));
    }

    /**
     * FTC 26: Verify validation works well while launching contest (step 1, empty contest type).
     *
     * @throws Exception if any error
     */
    public void testFTC26() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        // browser.click("css=div.selectedTxt");
    // browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("No contest type is selected."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 27: Verify validation works well while launching contest (step 1, empty contest name).
     *
     * @throws Exception if any error
     */
    public void testFTC27() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    // browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Contest name is empty."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 29: Verify validation works well while launching contest (step 1, contest name max length exceeded).
     *
     * @throws Exception if any error
     */
    public void testFTC29() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("You can only input max 200 characters."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 30: Verify validation works well while launching contest (step 1, empty project name).
     *
     * @throws Exception if any error
     */
    public void testFTC30() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    // browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:continueContestSelection();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("No project is selected."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 31: Verify "Terms & Conditions" link works well while launching contest on step 1.
     *
     * @throws Exception if any error
     */
    public void testFTC31() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("link=Terms and Conditions");
    assertTrue("popup should be presented", browser.isVisible("//div[@class='helpArea']"));
    assertTrue("popup should be presented", browser.isTextPresent("TopCoder Direct Terms"));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("popup should be closed", browser.isVisible("//div[@class='helpArea']"));
    }

    /**
     * FTC 32: Verify project name can be created dynamically while launching contest on step 1.
     *
     * @throws Exception if any error
     */
    public void testFTC32() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("css=span.left > span.right");
    String projectName = "Project " + System.currentTimeMillis();
    browser.type("id=newProjectName", projectName);
    browser.type("id=newProjectDescription", "desc");
    browser.click("css=#addNewProjectModal > div.modalBody > div.modalCommandBox > a.newButton1 > span.btnR > span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP * 5);
    assertEquals("the project should be selected", projectName, browser.getSelectedLabel("projects"));
    assertFalse("popup box should be closed", browser.isVisible("addNewProjectModal"));
    }

    /**
     * FTC 33: Verify project name can be created dynamically while launching contest on step 1.
     *
     * @throws Exception if any error
     */
    public void testFTC33() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("css=a.button6.preview > span.left > span.right");
    assertTrue("confirmation is not displayed", browser.getConfirmation().matches("^Are you sure you want to cancel[\\s\\S] Please save your work first if you want to keep this contest\\.$"));
    browser.chooseOkOnNextConfirmation();
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("the page should be restored to default value", "Please select an existing project", browser.getSelectedLabel("projects"));
    }

    /**
     * FTC 34: Verify CANCEL button works well while launching contest on step 1 (cancel Cancel action).
     *
     * @throws Exception if any error
     */
    public void testFTC34() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.chooseCancelOnNextConfirmation();
    browser.click("//a[@href='javascript:cancelContest();']");
    assertTrue(browser.getConfirmation().matches("^Are you sure you want to cancel[\\s\\S] Please save your work first if you want to keep this contest\\.$"));
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("the page should not be restored to default value", "Client 30010001 Billing Account 1 Project 1", browser.getSelectedLabel("projects"));
    }

    /**
     * FTC 35: Verify "SAVE AS DRAFT" button works well while launching contest on step 1.
     *
     * @throws Exception if any error
     */
    public void testFTC35() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    browser.select("projects", "Client 30010001 Billing Account 1 Project 1");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("//a[@href='javascript:saveAsDraftContestSelection();']");
    Thread.sleep(TestHelper.SLEEP * 10);
    assertTrue("confirmation dialog should be displayed", browser.isVisible("//div[@class='modalBody operationSuccess']"));
    assertTrue("confirmation dialog should be displayed", browser.isTextPresent("Software Contest Test Contest has been saved successfully."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("the project is not saved", "Client 30010001 Billing Account 1 Project 1", browser.getSelectedLabel("projects"));
    }

    /**
     * FTC 36: Verify project name can not be created dynamically while launching contest on step 1 if pressing Cancel.
     *
     * @throws Exception if any error
     */
    public void testFTC36() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("css=span.left > span.right");
    String projectName = "Project " + System.currentTimeMillis();
    browser.type("id=newProjectName", projectName);
    browser.type("id=newProjectDescription", "desc");
    browser.click("//div[@id='addNewProjectModal']/div[2]/div[2]/a[2]/span/span");
    Thread.sleep(TestHelper.SLEEP);
    List<String> labels = Arrays.asList(browser.getSelectedLabels("projects"));
    assertFalse("the project option should exist", labels.contains(projectName));
    assertFalse("popup box should be closed", browser.isVisible("addNewProjectModal"));
    }

    /**
     * FTC 37: Verify validation works well while dynamically creating project name on step 1 (empty Name).
     *
     * @throws Exception if any error
     */
    public void testFTC37() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("css=span.left > span.right");
    browser.type("id=newProjectDescription", "desc");
    browser.click("css=#addNewProjectModal > div.modalBody > div.modalCommandBox > a.newButton1 > span.btnR > span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Project name is empty."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 38: Verify validation works well while dynamically creating project name on step 1 (empty Name).
     *
     * @throws Exception if any error
     */
    public void testFTC38() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("css=span.left > span.right");
    browser.type("id=newProjectDescription", "desc");
    browser.click("css=#addNewProjectModal > div.modalBody > div.modalCommandBox > a.newButton1 > span.btnR > span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("You can only input max 200 characters."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 39: Verify validation works well while dynamically creating project name on step 1 (empty Description).
     *
     * @throws Exception if any error
     */
    public void testFTC39() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("css=span.left > span.right");
    String projectName = "Project " + System.currentTimeMillis();
    browser.type("id=newProjectName", projectName);
    browser.click("css=#addNewProjectModal > div.modalBody > div.modalCommandBox > a.newButton1 > span.btnR > span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Project description is empty."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 40: Verify validation works well while dynamically creating project name on step 1 (Description max length exceeded).
     *
     * @throws Exception if any error
     */
    public void testFTC40() throws Exception {
      browser.click("link=Launch Contest");
      browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the create contest page should be displayed",
            browser.isTextPresent("Please Select your Contest Type"));
        browser.click("css=div.selectedTxt");
    browser.click("link=Software Conceptualization");
    browser.check("id=lccCheckBox");
    browser.type("id=contestName", "Test Contest");
    
    browser.select("contestCopilot", "Unassigned");
    browser.select("roundTypes", "Contest will be run in multi-rounds");
    browser.type("startDate", "11/22/2022");
    browser.click("css=span.left > span.right");
    browser.type("id=newProjectName", "Project " + System.currentTimeMillis());
    browser.click("css=#addNewProjectModal > div.modalBody > div.modalCommandBox > a.newButton1 > span.btnR > span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("You can only input max 12000 characters."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 41: Verify contest can be created successfully (Conceptualization contest, multi-rounds).
     *
     * @throws Exception if any error
     */
    public void testFTC41() throws Exception {
      TestHelper.performSoftwareStep1(browser);
    // browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Detailed requirements is empty."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 42: Verify validation works well while launching contest on step 2 (Detailed requirements max length exceeded).
     *
     * @throws Exception if any error
     */
    public void testFTC42() throws Exception {
      TestHelper.performSoftwareStep1(browser);
    // browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Detailed requirements is empty."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 43: Verify validation works well while launching contest on step 2 (empty Software guidelines).
     *
     * @throws Exception if any error
     */
    public void testFTC43() throws Exception {
      TestHelper.performSoftwareStep1(browser);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    // browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Software guidelines is empty."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 44: Verify validation works well while launching contest on step 2 (Software guidelines max length exceeded).
     *
     * @throws Exception if any error
     */
    public void testFTC44() throws Exception {
      TestHelper.performSoftwareStep1(browser);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    // browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Software guidelines is empty."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 45: Verify validation works well while launching contest on step 2 (empty contest introduction).
     *
     * @throws Exception if any error
     */
    public void testFTC45() throws Exception {
      TestHelper.performStudioStep1(browser);
    // browser.runScript("CKEDITOR.instances['contestIntroduction'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['contestDescription'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round1Info'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round2Info'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Contest introduction is empty."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 46: Verify validation works well while launching contest on step 2 (Contest  Introduction max length exceeded).
     *
     * @throws Exception if any error
     */
    public void testFTC46() throws Exception {
      TestHelper.performStudioStep1(browser);
    // browser.runScript("CKEDITOR.instances['contestIntroduction'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['contestDescription'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round1Info'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round2Info'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Contest introduction is empty."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 47: Verify validation works well while launching contest on step 2 (empty contest description).
     *
     * @throws Exception if any error
     */
    public void testFTC47() throws Exception {
      TestHelper.performStudioStep1(browser);
    browser.runScript("CKEDITOR.instances['contestIntroduction'].setData('This is test contest.');");
    // browser.runScript("CKEDITOR.instances['contestDescription'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round1Info'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round2Info'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Contest description is empty."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 48: Verify validation works well while launching contest on step 2 (Contest  Description max length exceeded).
     *
     * @throws Exception if any error
     */
    public void testFTC48() throws Exception {
      TestHelper.performStudioStep1(browser);
    browser.runScript("CKEDITOR.instances['contestIntroduction'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round1Info'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round2Info'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("You can only input max 10000 characters."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 49: Verify validation works well while launching contest on step 2 (empty Round 1 Information).
     *
     * @throws Exception if any error
     */
    public void testFTC49() throws Exception {
      TestHelper.performStudioStep1(browser);
    browser.runScript("CKEDITOR.instances['contestIntroduction'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['contestDescription'].setData('This is test contest.');");
    // browser.runScript("CKEDITOR.instances['round1Info'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round2Info'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Round 1 information is empty."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 50: Verify validation works well while launching contest on step 2 (empty Round 1 Information).
     *
     * @throws Exception if any error
     */
    public void testFTC50() throws Exception {
      TestHelper.performStudioStep1(browser);
    browser.runScript("CKEDITOR.instances['contestIntroduction'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['contestDescription'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round2Info'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("You can only input max 2000 characters."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 51: Verify validation works well while launching contest on step 2 (empty Round 2 Information).
     *
     * @throws Exception if any error
     */
    public void testFTC51() throws Exception {
      TestHelper.performStudioStep1(browser);
    browser.runScript("CKEDITOR.instances['contestIntroduction'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['contestDescription'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round1Info'].setData('This is test contest.');");
    // browser.runScript("CKEDITOR.instances['round2Info'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Round 2 information is empty."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 52: Verify validation works well while launching contest on step 2 (empty Round 1 Information).
     *
     * @throws Exception if any error
     */
    public void testFTC52() throws Exception {
      TestHelper.performStudioStep1(browser);
    browser.runScript("CKEDITOR.instances['contestIntroduction'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['contestDescription'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['round1Info'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    browser.type("swFileDescription", "This is requirement doc.");
    browser.click("swFileUploadBtn");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("You can only input max 2000 characters."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * FTC 56: Verify validation works well while launching contest on step 2 (requirement specification not attached).
     *
     * @throws Exception if any error
     */
    public void testFTC56() throws Exception {
      String projectName = TestHelper.performSoftwareStep1(browser);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    // browser.type("swFileDescription", "This is requirement doc.");
    // browser.click("swFileUploadBtn");
    // Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Requirements Specification Document was not attached, continue?"));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be closed", browser.isVisible("demoModal"));
    assertEquals("contest information is incorrect for step 3", projectName, browser.getText("rswContestName"));
    assertEquals("contest information is incorrect for step 3", "Client 30010001 Billing Account 1 Project 1", browser.getText("rswProjectName"));
    assertEquals("contest information is incorrect for step 3", "Contest will be run in multi-rounds", browser.getText("rswRoundType"));
    assertEquals("contest information is incorrect for step 3", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rswStartDate"));
    assertEquals("contest information is incorrect for step 3", "This is test contest.", browser.getText("rswDetailedRequirements"));
    assertEquals("contest information is incorrect for step 3", "This is test contest.", browser.getText("rswSoftwareGuidelines"));
    }

    /**
     * FTC 57: Verify validation works well while launching contest on step 2 (requirement specification not attached and cancelled continuing to next step).
     *
     * @throws Exception if any error
     */
    public void testFTC57() throws Exception {
      TestHelper.performSoftwareStep1(browser);
    browser.runScript("CKEDITOR.instances['swDetailedRequirements'].setData('This is test contest.');");
    browser.runScript("CKEDITOR.instances['swGuidelines'].setData('This is test contest.');");
    browser.type("css=#swUploadButtonDiv > input[name='document']", TestHelper.getUploadFile());
    // browser.type("swFileDescription", "This is requirement doc.");
    // browser.click("swFileUploadBtn");
    // Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("Requirements Specification Document was not attached, continue?"));
    browser.click("css=a.newButton1.noBtn > span.btnR > span.btnC");
    assertFalse("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("should stay in step 2", browser.isElementPresent("swDetailedRequirements"));
    }

    /**
     * FTC 58: Verify BACK button works well while launching contest on step 2.
     *
     * @throws Exception if any error
     */
    public void testFTC58() throws Exception {
      TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:backOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("should returned back to step 1", browser.isTextPresent("Please Select your Contest Type"));
    }

    /**
     * FTC 59: Verify CANCEL button works well while launching contest on step 2(confirm Cancel action).
     *
     * @throws Exception if any error
     */
    public void testFTC59() throws Exception {
      TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:cancelContest();']");
    assertTrue(browser.getConfirmation().matches("^Are you sure you want to cancel[\\s\\S] Please save your work first if you want to keep this contest\\.$"));
    browser.chooseOkOnNextConfirmation();
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("the page should be restored to default value", "Please select an existing project", browser.getSelectedLabel("projects"));
    }

    /**
     * FTC 60: Verify CANCEL button works well while launching contest on step 2 (cancel Cancel action).
     *
     * @throws Exception if any error
     */
    public void testFTC60() throws Exception {
      TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.chooseCancelOnNextConfirmation();
    browser.click("//a[@href='javascript:cancelContest();']");
    assertTrue(browser.getConfirmation().matches("^Are you sure you want to cancel[\\s\\S] Please save your work first if you want to keep this contest\\.$"));
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("should stay in step 2", browser.isElementPresent("swDetailedRequirements"));
    }

    /**
     * FTC 61: Verify PREVIEW button works well while launching contest on step 2 (contest is not saved as draft).
     *
     * @throws Exception if any error
     */
    public void testFTC61() throws Exception {
      TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:previewContest();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("You must 'Save as Draft' before you can preview your contest."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be presented", browser.isVisible("demoModal"));
    }

    /**
     * FTC 62: Verify PREVIEW button works well while launching contest on step 2 (contest is saved as draft).
     *
     * @throws Exception if any error
     */
    public void testFTC62() throws Exception {
      TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:saveAsDraftOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:previewContest();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("the preview page should be open", browser.getAllWindowIds().length == 2);
    }

    /**
     * FTC 63: Verify BACK button works well while launching contest on step 3.
     *
     * @throws Exception if any error
     */
    public void testFTC63() throws Exception {
      TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:backReview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("should stay in step 2", browser.isElementPresent("swDetailedRequirements"));
    }

    /**
     * FTC 64: Verify CANCEL button works well while launching contest on step 3(confirm Cancel action).
     *
     * @throws Exception if any error
     */
    public void testFTC64() throws Exception {
      TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:cancelContest();']");
    assertTrue(browser.getConfirmation().matches("^Are you sure you want to cancel[\\s\\S] Please save your work first if you want to keep this contest\\.$"));
    browser.chooseOkOnNextConfirmation();
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("the page should be restored to default value", "Please select an existing project", browser.getSelectedLabel("projects"));
    }

    /**
     * FTC 65: Verify CANCEL button works well while launching contest on step 3 (cancel Cancel action).
     *
     * @throws Exception if any error
     */
    public void testFTC65() throws Exception {
      String projectName = TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.chooseCancelOnNextConfirmation();
    browser.click("//a[@href='javascript:cancelContest();']");
    assertTrue(browser.getConfirmation().matches("^Are you sure you want to cancel[\\s\\S] Please save your work first if you want to keep this contest\\.$"));
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("contest information is incorrect for step 3", projectName, browser.getText("rswContestName"));
    assertEquals("contest information is incorrect for step 3", "Client 30010001 Billing Account 1 Project 1", browser.getText("rswProjectName"));
    assertEquals("contest information is incorrect for step 3", "Contest will be run in multi-rounds", browser.getText("rswRoundType"));
    assertEquals("contest information is incorrect for step 3", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rswStartDate"));
    assertEquals("contest information is incorrect for step 3", "This is test contest.", browser.getText("rswDetailedRequirements"));
    assertEquals("contest information is incorrect for step 3", "This is test contest.", browser.getText("rswSoftwareGuidelines"));
    }

    /**
     * FTC 66: Verify PREVIEW button works well while launching contest on step 3 (contest is not saved as draft).
     *
     * @throws Exception if any error
     */
    public void testFTC66() throws Exception {
      TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:previewContest();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("You must 'Save as Draft' before you can preview your contest."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be presented", browser.isVisible("demoModal"));
    }

    /**
     * FTC 67: Verify PREVIEW button works well while launching contest on step 3 (contest is saved as draft).
     *
     * @throws Exception if any error
     */
    public void testFTC67() throws Exception {
      TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:saveAsDraftReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:previewContest();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("the preview page should be open", browser.getAllWindowIds().length == 2);
    }

    /**
     * FTC 68: Verify BACK button works well while launching contest on step 4.
     *
     * @throws Exception if any error
     */
    public void testFTC68() throws Exception {
      String projectName = TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:backOrderReview();']");
    Thread.sleep(TestHelper.SLEEP);
    assertEquals("contest information is incorrect for step 3", projectName, browser.getText("rswContestName"));
    assertEquals("contest information is incorrect for step 3", "Client 30010001 Billing Account 1 Project 1", browser.getText("rswProjectName"));
    assertEquals("contest information is incorrect for step 3", "Contest will be run in multi-rounds", browser.getText("rswRoundType"));
    assertEquals("contest information is incorrect for step 3", "11/22/2022 at 00:00 EST (UTC-05)", browser.getText("rswStartDate"));
    assertEquals("contest information is incorrect for step 3", "This is test contest.", browser.getText("rswDetailedRequirements"));
    assertEquals("contest information is incorrect for step 3", "This is test contest.", browser.getText("rswSoftwareGuidelines"));
    }

    /**
     * FTC 69: Verify PREVIEW button works well while launching contest on step 4 (contest is saved as draft).
     *
     * @throws Exception if any error
     */
    public void testFTC69() throws Exception {
      TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:saveAsDraftOrderReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    }

    /**
     * FTC 70: Verify PREVIEW button works well while launching contest on step 4 (contest is not saved as draft).
     *
     * @throws Exception if any error
     */
    public void testFTC70() throws Exception {
      TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:previewContest();']");
    Thread.sleep(TestHelper.SLEEP);
    assertTrue("error box should be presented", browser.isVisible("demoModal"));
    assertTrue("error box should be presented", browser.isTextPresent("You must 'Save as Draft' before you can preview your contest."));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP);
    assertFalse("error box should be presented", browser.isVisible("demoModal"));
    }

    /**
     * FTC 71: Verify SAVE AS DRAFT button works well while launching contest on step 4.
     *
     * @throws Exception if any error
     */
    public void testFTC71() throws Exception {
      String project = TestHelper.performSoftwareStep1(browser);
      TestHelper.performSoftwareStep2(browser);
    browser.click("//a[@href='javascript:continueOverview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:continueReview();']");
    Thread.sleep(TestHelper.SLEEP);
    browser.click("//a[@href='javascript:saveAsDraftOrderReview();']");
    Thread.sleep(TestHelper.SLEEP * 10);
    assertTrue("saving box should be presented", browser.isVisible("demoModal"));
    assertTrue("saving box should be presented", browser.isTextPresent("Software Contest " + project + " has been saved successfully"));
    browser.click("css=span.btnC");
    Thread.sleep(TestHelper.SLEEP * 5);
    assertFalse("saving box should be closed", browser.isVisible("demoModal"));
    }

    /**
     * Create a 12001 length's string.
     * @return a 12001 length string
     */
    private String get12001String() {
    StringBuilder sb = new StringBuilder(TWO_HUNDRED);
    for (int i = 1; i < 60; i++) {
      sb.append(TWO_HUNDRED);
    }
    sb.append("1");
    return sb.toString();
    }
}
