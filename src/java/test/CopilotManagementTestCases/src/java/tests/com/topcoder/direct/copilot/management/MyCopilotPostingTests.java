/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.copilot.management;

import com.thoughtworks.selenium.Selenium;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;


/**
 * Functional test for cockpit copilot management.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MyCopilotPostingTests extends TestCase {
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
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=My Copilot Postings");
        browser.waitForPageToLoad(TestHelper.getTimeout());
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
     * Verify the user can go to My Copilot Postings page by click "My Copilot Postings" tab .
     *
     * @throws Exception if any error
     */
    public void testFTC135() throws Exception {
        assertTrue("the page content is incorrect",
            browser.isTextPresent("Dashboard > Copilots > My Copilot Selection Contests"));
    }

    /**
     * Verify all data will be listed on the my copilot selection contests table .
     *
     * @throws Exception if any error
     */
    public void testFTC136() throws Exception {
        assertEquals("the page content is incorrect", "Copilot Posting",
            browser.getText("//table[@id='tableSorterUsed']/thead/tr/th[1]"));
        assertEquals("the page content is incorrect", "Project Name",
            browser.getText("//table[@id='tableSorterUsed']/thead/tr/th[2]"));
        assertEquals("the page content is incorrect", "Start Date",
            browser.getText("//table[@id='tableSorterUsed']/thead/tr/th[3]"));
        assertEquals("the page content is incorrect", "End Date",
            browser.getText("//table[@id='tableSorterUsed']/thead/tr/th[4]"));
        assertEquals("the page content is incorrect", "Registrants",
            browser.getText("//table[@id='tableSorterUsed']/thead/tr/th[5]"));
        assertEquals("the page content is incorrect", "Submissions",
            browser.getText("//table[@id='tableSorterUsed']/thead/tr/th[6]"));
        assertEquals("the page content is incorrect", "Forums",
            browser.getText("//table[@id='tableSorterUsed']/thead/tr/th[7]"));
        assertEquals("the page content is incorrect", "Status",
            browser.getText("//table[@id='tableSorterUsed']/thead/tr/th[8]"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=View/Edit"));
        assertTrue("the page content is incorrect",
                browser.isElementPresent("//table[@id='tableSorterUsed']/tbody/tr[1]/td[9]/a"));
        assertTrue("the page content is incorrect",
                browser.isElementPresent("//table[@id='tableSorterUsed']/tbody/tr[2]/td[9]/a"));
        assertTrue("the page content is incorrect",
                browser.isElementPresent("//table[@id='tableSorterUsed']/tbody/tr[3]/td[9]/a"));
        assertTrue("the page content is incorrect",
                browser.isElementPresent("//table[@id='tableSorterUsed']/tbody/tr[4]/td[9]/a"));
        assertTrue("the page content is incorrect",
                browser.isElementPresent("//table[@id='tableSorterUsed']/tbody/tr[5]/td[9]/a"));
    }

    /**
     * Verify the default value on show per page dropdown list is 10 .
     *
     * @throws Exception if any error
     */
    public void testFTC137() throws Exception {
        assertEquals("the page content is incorrect", "10", browser.getSelectedLabel("dataTableLength"));
    }

    /**
     * Verify all value can be listed on  show per page dropdown list .
     *
     * @throws Exception if any error
     */
    public void testFTC138() throws Exception {
        List<String> labels = Arrays.asList(browser.getSelectOptions("dataTableLength"));
        assertTrue("the page content is incorrect", labels.contains("5"));
        assertTrue("the page content is incorrect", labels.contains("10"));
        assertTrue("the page content is incorrect", labels.contains("25"));
        assertTrue("the page content is incorrect", labels.contains("All"));
        assertTrue("the page content is incorrect", labels.size() == 4);
    }

    /**
     * Verify pagination function on Active Contests page is working well(Change the number of items per page)
     * .
     *
     * @throws Exception if any error
     */
    public void testFTC139() throws Exception {
        browser.select("dataTableLength", "5");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 1 to 5 of"));
        browser.select("dataTableLength", "10");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 1 to 10 of"));
        browser.select("dataTableLength", "25");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 1 to 25 of"));
        browser.select("dataTableLength", "All");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 1 to 43 of"));
    }

    /**
     * Verify pagination features is working well(Change the current page which have 5 items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC140() throws Exception {
        browser.select("dataTableLength", "5");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("//div[@id='tableSorterUsed_paginate']/span[3]/span[2]");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 6 to 10"));
        browser.click("//div[@id='tableSorterUsed_paginate']/span[3]/span");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 1 to 5"));
        browser.click("id=tableSorterUsed_next");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 6 to 10"));
        browser.click("id=tableSorterUsed_previous");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 1 to 5"));
    }

    /**
     * Verify pagination features is working well(Change the current page which have 10 items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC141() throws Exception {
        browser.select("dataTableLength", "10");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("//div[@id='tableSorterUsed_paginate']/span[3]/span[2]");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 11 to 20"));
        browser.click("//div[@id='tableSorterUsed_paginate']/span[3]/span");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 1 to 10"));
        browser.click("id=tableSorterUsed_next");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 11 to 20"));
        browser.click("id=tableSorterUsed_previous");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 1 to 10"));
    }

    /**
     * Verify pagination features is working well(Change the current page which have 25 items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC142() throws Exception {
        browser.select("dataTableLength", "25");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("//div[@id='tableSorterUsed_paginate']/span[3]/span[2]");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 26 to"));
        browser.click("//div[@id='tableSorterUsed_paginate']/span[3]/span");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 1 to 25"));
        browser.click("id=tableSorterUsed_next");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 26 to"));
        browser.click("id=tableSorterUsed_previous");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Showing 1 to 25"));
    }

    /**
     * Verify pagination features is working well(Change the current page which have all items per page) .
     *
     * @throws Exception if any error
     */
    public void testFTC143() throws Exception {
        browser.select("dataTableLength", "All");
        Thread.sleep(TestHelper.SLEEP);
        assertFalse("the page content is incorrect",
            browser.isElementPresent("//div[@id='tableSorterUsed_paginate']/span[3]/span[2]"));
    }

    /**
     * Verify breadcrumb is correct on my copilot postings page .
     *
     * @throws Exception if any error
     */
    public void testFTC146() throws Exception {
        assertTrue("the page content is incorrect",
            browser.isTextPresent("Dashboard > Copilots > My Copilot Selection Contests"));
    }

    /**
     * Verify the user can go to view/edit posting page .
     *
     * @throws Exception if any error
     */
    public void testFTC147() throws Exception {
        browser.click("css=span.btnIcon");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect",
                browser.isElementPresent("//table[@class='projectStats contests']"));
        assertEquals("the page content is incorrect", "Start Date/Time",
            browser.getText("//table[@class='projectStats contests']/thead/tr/th[1]"));
        assertEquals("the page content is incorrect", "End Date/Time",
            browser.getText("//table[@class='projectStats contests']/thead/tr/th[2]"));
        assertEquals("the page content is incorrect", "Registrants",
            browser.getText("//table[@class='projectStats contests']/thead/tr/th[3]"));
        assertEquals("the page content is incorrect", "Forums",
            browser.getText("//table[@class='projectStats contests']/thead/tr/th[4]"));
        assertEquals("the page content is incorrect", "Submissions",
            browser.getText("//table[@class='projectStats contests']/thead/tr/th[5]"));
        assertEquals("the page content is incorrect", "Next Action",
            browser.getText("//table[@class='projectStats contests']/thead/tr/th[6]"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=Details"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=Registrants"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=Submissions"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=Receipt"));
    }

    /**
     * Verify all links on view/edit posting page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC148() throws Exception {
        browser.click("css=span.btnIcon");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=View Contest");
        assertEquals("the contest details should be displayed", 2, browser.getAllWindowIds().length);
        browser.click("link=Registration Page");
        assertEquals("the contest details should be displayed", 3, browser.getAllWindowIds().length);
        browser.click("link=Forum");
        assertEquals("the contest details should be displayed", 4, browser.getAllWindowIds().length);
    }

    /**
     * Verify all elements will be displayed on Details tab of view/edit postings page .
     *
     * @throws Exception if any error
     */
    public void testFTC149() throws Exception {
        browser.click("css=span.btnIcon");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Details");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isTextPresent("General Information"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Copilot Posting Name :"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Project Name :"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Billing Account :"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Copilot Posting Schedule"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Start Time:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Description that you want everyone to see"));
        assertTrue("the page content is incorrect",
            browser.isTextPresent("Description that is only viewable to copilots that register for this posting"));
    }

    /**
     * Verify the user activate the draft postings .
     *
     * @throws Exception if any error
     */
    public void testFTC150() throws Exception {
        browser.click("css=span.btnIcon");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Details");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect",
            browser.isTextPresent("Description that is only viewable to copilots that register for this posting "));
    }

    /**
     * Verify the user can edit general information on details tab .
     *
     * @throws Exception if any error
     */
    public void testFTC151() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isElementPresent("projects2"));
        assertTrue("the page content is incorrect", browser.isElementPresent("contestNameInput2"));
        assertTrue("the page content is incorrect", browser.isElementPresent("billingProjects2"));
    }

    /**
     * Verify general information form validation is working well(Invalid Project name) .
     *
     * @throws Exception if any error
     */
    public void testFTC152() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("contestNameInput2", TestHelper.getLongChars(1) + 1);
        browser.click("css=img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("You can only input max 200 characters."));
    }

    /**
     * Verify general information form validation is working well(Empty Project name) .
     *
     * @throws Exception if any error
     */
    public void testFTC153() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("contestNameInput2", "");
        browser.click("css=img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("Contest name is empty"));
    }

    /**
     * Verify the user can cancel add new project name on general information section .
     *
     * @throws Exception if any error
     */
    public void testFTC156() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=#addNewProject2 > span.left > span.right");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("//div[@id='addNewProjectModal']/div[2]/div[2]/a[2]/span/span");
        Thread.sleep(TestHelper.SLEEP);
    }

    /**
     * Verify general information form validation is working well(Empty Project) .
     *
     * @throws Exception if any error
     */
    public void testFTC157() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("projects2", "Please select an existing project");
        browser.click("css=img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("Project is not selected"));
    }

    /**
     * Verify general information form validation is working well(Empty Posting name) .
     *
     * @throws Exception if any error
     */
    public void testFTC158() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("contestNameInput2", "");
        browser.click("css=img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("Contest name is empty"));
    }

    /**
     * Verify general information form validation is working well(Invalid Posting name) .
     *
     * @throws Exception if any error
     */
    public void testFTC159() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("contestNameInput2", TestHelper.getLongChars(1) + 1);
        browser.click("css=img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("You can only input max 200 characters."));
    }

    /**
     * Verify general information form validation is working well(Empty Billing Account) .
     *
     * @throws Exception if any error
     */
    public void testFTC160() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("billingProjects2", "Please select an existing account");
        browser.click("css=img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("Billing account is not selected"));
    }

    /**
     * Verify the user can save general information successfully .
     *
     * @throws Exception if any error
     */
    public void testFTC161() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("contestNameInput2", "Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.click("css=img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the confirmation is incorrect", browser.isVisible("demoModal"));
    }

    /**
     * Verify the user can cancel update general information operation .
     *
     * @throws Exception if any error
     */
    public void testFTC162() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("contestNameInput2", "Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.click("id=cancelContestInfo");
        Thread.sleep(TestHelper.SLEEP);
        assertFalse("the confirmation is incorrect", browser.isVisible("contestNameInput2"));
    }

    /**
     * Verify the user can edit Contest Schedule on details tab .
     *
     * @throws Exception if any error
     */
    public void testFTC163() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.infoPanel.scheduleInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isElementPresent("start2DateInput"));
        assertTrue("the page content is incorrect", browser.isElementPresent("start2TimeInput"));
    }

    /**
     * Verify Contest Schedule form validation is working well(Start Date before current time) .
     *
     * @throws Exception if any error
     */
    public void testFTC164() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.infoPanel.scheduleInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("start2DateInput", "01/01/2011");
        browser.click("css=#saveDates > img.save_btn");
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed",
                browser.isTextPresent("The start date should after current time."));
    }

    /**
     * Verify the user can save the change for contest schedule .
     *
     * @throws Exception if any error
     */
    public void testFTC165() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.infoPanel.scheduleInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("start2DateInput", "01/01/2020");
        browser.click("css=#saveDates > img.save_btn");
        assertTrue("the confirmation is incorrect", browser.isVisible("demoModal"));
        assertTrue("the confirmation is incorrect",
            browser.isTextPresent("Your copilot posting has been saved successfully."));
    }

    /**
     * Verify the user can cancel the change for contest schedule .
     *
     * @throws Exception if any error
     */
    public void testFTC166() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.infoPanel.scheduleInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("start2DateInput", "01/01/2020");
        browser.click("id=cancelDates");
        assertFalse("the form is incorrect", browser.isVisible("start2DateInput"));
    }

    /**
     * Verify the user can edit "Description that you want everyone to see"(Public Description) on details tab
     * .
     *
     * @throws Exception if any error
     */
    public void testFTC167() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.htmlDescription.descriptionInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("page content is incorrect",
            browser.isElementPresent(
                "dom=document.getElementById('publicCopilotPostingDescription2_ifr').contentDocument.body"));
    }

    /**
     * Verify Public Description form validation is working well(Invalid Public Description) .
     *
     * @throws Exception if any error
     */
    public void testFTC168() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.htmlDescription.descriptionInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("dom=document.getElementById('publicCopilotPostingDescription2_ifr').contentDocument.body",
            TestHelper.getLongChars(60) + 1);
        browser.click("css=#savePublicDesc > img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed",
                browser.isTextPresent("You can only input max 12000 characters."));
    }

    /**
     * Verify Public Description form validation is working well(Empty Public Description) .
     *
     * @throws Exception if any error
     */
    public void testFTC169() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.htmlDescription.descriptionInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("dom=document.getElementById('publicCopilotPostingDescription2_ifr').contentDocument.body", "");
        browser.click("css=#savePublicDesc > img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("Project Description is empty."));
    }

    /**
     * Verify the user can save change for "Public Description"  .
     *
     * @throws Exception if any error
     */
    public void testFTC170() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.htmlDescription.descriptionInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("dom=document.getElementById('publicCopilotPostingDescription2_ifr').contentDocument.body",
            "abcdefg");
        browser.click("css=#savePublicDesc > img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the confirmation is incorrect", browser.isVisible("demoModal"));
        assertTrue("the confirmation is incorrect",
            browser.isTextPresent("Your copilot posting has been saved successfully."));
    }

    /**
     * Verify the user can cancel change for "Public Description"  .
     *
     * @throws Exception if any error
     */
    public void testFTC171() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.htmlDescription.descriptionInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("dom=document.getElementById('publicCopilotPostingDescription2_ifr').contentDocument.body",
            "abcdefg");
        browser.click("id=cancelPublicDesc");
        Thread.sleep(TestHelper.SLEEP);
        assertFalse("the form is incorrect",
            browser.isVisible(
                "dom=document.getElementById('publicCopilotPostingDescription2_ifr').contentDocument.body"));
    }

    /**
     * Verify the user can edit "Description that is only viewable to copilots that register for this
     * posting"(Specific Description) on details tab .
     *
     * @throws Exception if any error
     */
    public void testFTC172() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click(
            "css=div.editMask.greybg > div.htmlDescription.descriptionInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("page content is incorrect",
            browser.isElementPresent("dom=document.getElementById('privateDescription_ifr').contentDocument.body"));
    }

    /**
     * Verify Specific Description form validation is working well(Invalid Specific Description) .
     *
     * @throws Exception if any error
     */
    public void testFTC173() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click(
            "css=div.editMask.greybg > div.htmlDescription.descriptionInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            TestHelper.getLongChars(20)
            + "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567");
        browser.click("css=#savePrivateDesc > img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("You can only input max 4096 characters."));
    }

    /**
     * Verify Specific Description form validation is working well(Empty Specific Description) .
     *
     * @throws Exception if any error
     */
    public void testFTC174() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click(
            "css=div.editMask.greybg > div.htmlDescription.descriptionInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body", "");
        browser.click("css=#savePrivateDesc > img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("Specific Description is empty."));
    }

    /**
     * Verify the user can save change for "Specific Description"  .
     *
     * @throws Exception if any error
     */
    public void testFTC175() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click(
            "css=div.editMask.greybg > div.htmlDescription.descriptionInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body", "updated");
        browser.click("css=#savePrivateDesc > img.save_btn");
        assertTrue("the confirmation is incorrect", browser.isVisible("demoModal"));
        assertTrue("the confirmation is incorrect",
            browser.isTextPresent("Your copilot posting has been saved successfully."));
    }

    /**
     * Verify the user can cancel change for "Specific Description"  .
     *
     * @throws Exception if any error
     */
    public void testFTC176() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click(
            "css=div.editMask.greybg > div.htmlDescription.descriptionInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body", "updated");
        browser.click("id=cancelPrivateDesc");
        Thread.sleep(TestHelper.SLEEP);
        assertFalse("the form is incorrect",
            browser.isVisible("dom=document.getElementById('privateDescription_ifr').contentDocument.body"));
    }

    /**
     * Verify the user can edit "Files" section on details tab .
     *
     * @throws Exception if any error
     */
    public void testFTC177() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.infoPanel.fileUploadInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the edit layer should be displayed", browser.isElementPresent("fakeTextInput2"));
    }

    /**
     * Verify Files form validation is working well(Empty file) .
     *
     * @throws Exception if any error
     */
    public void testFTC178() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.infoPanel.fileUploadInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=#saveFiles > img.save_btn");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("No file is selected."));
    }

    /**
     * Verify "+" button on the "Files" section is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC179() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.infoPanel.fileUploadInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("id=fileUploadBtn2");
        Thread.sleep(TestHelper.SLEEP);
        assertFalse("error popup should not be displayed", browser.isVisible("errortModal"));
    }

    /**
     * Verify "-" button on the "Files" section is working well(Based on
     * https://www.topcoder.com/direct/copilot/launchCopilotContest.action) .
     *
     * @throws Exception if any error
     */
    public void testFTC180() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.infoPanel.fileUploadInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("id=fileUploadBtn2");
        Thread.sleep(TestHelper.SLEEP);
        assertFalse("error popup should not be displayed", browser.isVisible("errortModal"));
    }

    /**
     * Verify the user can upload the file .
     *
     * @throws Exception if any error
     */
    public void testFTC181() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.infoPanel.fileUploadInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("name=document", TestHelper.getUploadFile());
        browser.type("id=fileDescription2", "test");
        browser.click("id=fileUploadBtn2");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the file should be uploaded", browser.isTextPresent("test.rar"));
    }

    /**
     * Verify the user can cancel upload the file .
     *
     * @throws Exception if any error
     */
    public void testFTC182() throws Exception {
        browser.click("link=Client 2 Billing Account 1 Project 2 Copilot Posting Contest 109");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=div.infoPanel.fileUploadInfo  > h3 > a.editLink > img.edit_type");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("id=cancelFiles");
        Thread.sleep(TestHelper.SLEEP);
        assertFalse("the form is incorrect", browser.isVisible("name=document"));
    }
}
