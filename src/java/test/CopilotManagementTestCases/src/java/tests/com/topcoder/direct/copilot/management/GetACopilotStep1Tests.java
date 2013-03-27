/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.copilot.management;

/**
 * Functional test for cockpit copilot management.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetACopilotStep1Tests extends BaseTestCase {

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
     * Verify the user can go to get a copilot step 1 page by pressing "Work with a Copilot" button .
     *
     * @throws Exception if any error
     */
    public void testFTC14() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > Copilots > Post a Copilot"));
        assertEquals("the page content is incorrect", "1Basic", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify breadcrumb is correct on Get a copilot step 1 page .
     *
     * @throws Exception if any error
     */
    public void testFTC15() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > Copilots > Post a Copilot"));
        browser.click("//a[contains(text(),'Copilots')]");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the introduction must exist",
            browser.isTextPresent(
                "A Copilot is a TopCoder Member who manages the TopCoder process for a customer in order to"
                    + " deliver a requested asset. For example, a customer may ask to build a website. A Copilot"
                    + " will work with that customer to agree on a plan and pricing to build that website and then"
                    + " they would manage the process using the TopCoder Platform to deliver the website back to the"
                    + " customer."));
    }

    /**
     * Verify "Back to Dashboard" button on Get a copilot step 1 page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC17() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Back to Dashboard");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect",
                browser.isTextPresent("Dashboard > All Active Contests"));
        assertTrue("the page content is incorrect",
                browser.isElementPresent("//img[@src='/images/dashboard_logo.png']"));
    }

    /**
     * Verify all fields can be displayed on Get a copilot step 1 page .
     *
     * @throws Exception if any error
     */
    public void testFTC18() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "Project Name*",
            browser.getText("//div[@class='rowItem rowItemFirst']/label"));
        assertTrue("the page content is incorrect", browser.isElementPresent("//select[@id='projects']"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Copilot Opportunity Title*"));
        assertTrue("the page content is incorrect", browser.isElementPresent("contestName"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Public Description"));
        assertTrue("the page content is incorrect", browser.isElementPresent("allDescription"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Specific Description"));
        assertTrue("the page content is incorrect", browser.isElementPresent("privateDescription"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Select Document"));
        assertTrue("the page content is incorrect", browser.isElementPresent("fileUpload"));
    }

    /**
     * Verify all values can be listed on project name dropdown list .
     *
     * @throws Exception if any error
     */
    public void testFTC19() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());

        String[] options = browser.getSelectOptions("projects");
        assertTrue("the page content is incorrect", options.length > 1);

        boolean exist = false;

        for (String project : options) {
            if (project.indexOf("Client 1") > -1) {
                exist = true;

                break;
            }
        }

        assertTrue("the page content is incorrect", exist);
    }

    /**
     * Verify Browse and Upload button is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC20() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=span.customUploadWrap > a.draft.button6 > span.left > span.right");
        fail("file upload logic is wrong");
    }

    /**
     * Verify line indicator on Public Description field is working well(Based on
     * https://www.topcoder.com/direct/copilot/launchCopilotContest.action) .
     *
     * @throws Exception if any error
     */
    public void testFTC21() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());

        String newLine = System.getProperty("line.separator");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
            "test" + newLine + "test" + newLine + "test");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", 3, browser.getText("allDescription-word-count"));
    }

    /**
     * Verify line indicator on Specific Description field is working well(Based on
     * https://www.topcoder.com/direct/copilot/launchCopilotContest.action) .
     *
     * @throws Exception if any error
     */
    public void testFTC22() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());

        String newLine = System.getProperty("line.separator");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "test" + newLine + "test" + newLine + "test");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", 3, browser.getText("privateDescription-word-count"));
    }

    /**
     * Verify "+" button on the Get a Copilot Step 1 page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC23() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=a.addButton");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "Select Document",
            browser.getText("//div[@class='row uploadContent copilotFileUploadDiv']/div[2]/p[3]/label"));
        assertTrue("the page content is incorrect",
            browser.isElementPresent(
                    "//div[@class='row uploadContent copilotFileUploadDiv']/div[2]/p[3]/span/input[1]"));
        assertTrue("the page content is incorrect",
            browser.isElementPresent(
                    "//div[@class='row uploadContent copilotFileUploadDiv']/div[2]/p[3]/span/input[2]"));
        assertEquals("the page content is incorrect", "Description",
            browser.getText("//div[@class='row uploadContent copilotFileUploadDiv']/div[2]/p[4]/label"));
    }

    /**
     * Verify "-" button on the Get a Copilot Step 1 page is working well(Based on
     * https://www.topcoder.com/direct/copilot/launchCopilotContest.action) .
     *
     * @throws Exception if any error
     */
    public void testFTC24() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=a.addButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("//div[@id='stepContainer']/div[4]/div/div[2]/div/div[4]/div[2]/p[4]/a[3]");
        Thread.sleep(TestHelper.SLEEP);
        assertFalse("the row should be removed",
            browser.isElementPresent("//div[@class='row uploadContent copilotFileUploadDiv']/div[2]/p[3]/label"));
    }

    /**
     * Verify "Add New" button on the Get a Copilot Step 1 page is working well(Based on
     * https://www.topcoder.com/direct/copilot/launchCopilotContest.action) .
     *
     * @throws Exception if any error
     */
    public void testFTC25() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=#addNewProject > span.left > span.right");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("popup window should be displayed", browser.isVisible("addNewProjectModal"));
        assertTrue("the page content is incorrect", browser.isElementPresent("newProjectName"));
        assertTrue("the page content is incorrect", browser.isElementPresent("newProjectDescription"));
    }

    /**
     * Verify the user can successful add new project name on step 1 page .
     *
     * @throws Exception if any error
     */
    public void testFTC26() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=#addNewProject > span.left > span.right");
        Thread.sleep(TestHelper.SLEEP);

        String projectName = "Project " + System.currentTimeMillis();
        browser.type("newProjectName", projectName);
        browser.type("newProjectDescription", "test description");
        browser.click(
            "css=#addNewProjectModal > div.modalBody > div.modalCommandBox > a.newButton1 > span.btnR > span.btnC");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the confirmation is incorrect", browser.isVisible("demoModal"));
        assertTrue("the confirmation is incorrect", browser.isTextPresent(projectName + " is created successfully."));
        browser.click("css=span.btnC");
        Thread.sleep(TestHelper.SLEEP);
        assertFalse("the confirmation is not closed", browser.isVisible("demoModal"));
    }

    /**
     * Verify the user can cancel add new project name on step 1 page .
     *
     * @throws Exception if any error
     */
    public void testFTC27() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=#addNewProject > span.left > span.right");
        Thread.sleep(TestHelper.SLEEP);

        String projectName = "Project " + System.currentTimeMillis();
        browser.type("newProjectName", projectName);
        browser.type("newProjectDescription", "test description");
        browser.click("//div[@id='addNewProjectModal']/div[2]/div[2]/a[2]/span/span");
        Thread.sleep(TestHelper.SLEEP);
        assertFalse("the confirmation is not closed", browser.isVisible("demoModal"));
        assertFalse("the project name is not displayed", browser.isTextPresent(projectName));
    }

    /**
     * Verify step 1 form validation is working well(Empty Project Name) .
     *
     * @throws Exception if any error
     */
    public void testFTC28() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specific Description");
        browser.type("id=contestName", "Description");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("Project name could not be empty."));
    }

    /**
     * Verify step 1 form validation is working well(Empty Project Name on add new popup window) .
     *
     * @throws Exception if any error
     */
    public void testFTC29() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=#addNewProject > span.left > span.right");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("newProjectName", "");
        browser.type("newProjectDescription", "test description");
        browser.click(
            "css=#addNewProjectModal > div.modalBody > div.modalCommandBox > a.newButton1 > span.btnR > span.btnC");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("Project name is empty."));
    }

    /**
     * Verify step 1 form validation is working well(Empty Description on add new popup window) .
     *
     * @throws Exception if any error
     */
    public void testFTC30() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=#addNewProject > span.left > span.right");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("newProjectName", "Project Name");
        browser.type("newProjectDescription", "");
        browser.click(
            "css=#addNewProjectModal > div.modalBody > div.modalCommandBox > a.newButton1 > span.btnR > span.btnC");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("Project description is empty."));
    }

    /**
     * Verify step 1 form validation is working well(Invalid Project Name on add new popup window) .
     *
     * @throws Exception if any error
     */
    public void testFTC31() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=#addNewProject > span.left > span.right");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("newProjectName", TestHelper.getLongChars(1) + "1");
        browser.type("newProjectDescription", "test description");
        browser.click(
            "css=#addNewProjectModal > div.modalBody > div.modalCommandBox > a.newButton1 > span.btnR > span.btnC");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("You can only input max 200 characters."));
    }

    /**
     * Verify step 1 form validation is working well(Invalid Description on add new popup window) .
     *
     * @throws Exception if any error
     */
    public void testFTC32() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("css=#addNewProject > span.left > span.right");
        Thread.sleep(TestHelper.SLEEP);
        browser.type("newProjectName", "Project " + System.currentTimeMillis());
        browser.type("newProjectDescription", TestHelper.getLongChars(5) + "1");
        browser.click(
            "css=#addNewProjectModal > div.modalBody > div.modalCommandBox > a.newButton1 > span.btnR > span.btnC");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("You can only input max 1000 characters."));
    }

    /**
     * Verify step 1 form validation is working well(Empty Copilot Opportunity Title) .
     *
     * @throws Exception if any error
     */
    public void testFTC33() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specific Description");
        browser.type("id=contestName", "");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed",
            browser.isTextPresent("Copilot Opportunity Title could not be empty."));
    }

    /**
     * Verify step 1 form validation is working well(Empty Public Description) .
     *
     * @throws Exception if any error
     */
    public void testFTC34() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body", "");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specific Description");
        browser.type("id=contestName", "test name");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("Public Description could not be empty."));
    }

    /**
     * Verify step 1 form validation is working well(Empty Specific Description) .
     *
     * @throws Exception if any error
     */
    public void testFTC35() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body", "");
        browser.type("id=contestName", "test name");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed",
                browser.isTextPresent("Specific Description could not be empty."));
    }

    /**
     * Verify step 1 form validation is working well(Invalid Copilot Opportunity Title) .
     *
     * @throws Exception if any error
     */
    public void testFTC36() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specific Description");
        browser.type("id=contestName", TestHelper.getLongChars(1) + "1");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("You can only input max 200 characters."));
    }

    /**
     * Verify step 1 form validation is working well(Invalid Public Description) .
     *
     * @throws Exception if any error
     */
    public void testFTC37() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
            TestHelper.getLongChars(60) + 1);
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specific Description");
        browser.type("id=contestName", "test contest");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed",
                browser.isTextPresent("You can only input max 12000 characters."));
    }

    /**
     * Verify step 1 form validation is working well(Invalid Specific Description) .
     *
     * @throws Exception if any error
     */
    public void testFTC38() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            TestHelper.getLongChars(20)
            + "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567");
        browser.type("id=contestName", "test contest");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed", browser.isTextPresent("You can only input max 4096 characters."));
    }

    /**
     * Verify step 1 form validation is working well(Invalid Description on upload section) .
     *
     * @throws Exception if any error
     */
    public void testFTC39() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
    }

    /**
     * Verify "Save As Draft" button on get a copilot step 1 page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC40() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=Work with a Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.select("id=projects", "label=Client 1 Billing Account 1 Project 2");
        browser.type("dom=document.getElementById('allDescription_ifr').contentDocument.body",
                "Test Public Description");
        browser.type("dom=document.getElementById('privateDescription_ifr').contentDocument.body",
            "Test Specification Description");

        String contest = "Contest " + System.currentTimeMillis();
        browser.type("id=contestName", contest);
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        assertTrue("the page content is incorrect", browser.isVisible("demoModal"));
        assertTrue("the page content is incorrect",
            browser.isTextPresent("Your Copilot Posting " + contest + " has been saved as draft successfully."));
        browser.click("css=span.btnC");
        assertFalse("the page content is incorrect", browser.isVisible("demoModal"));
    }
}
