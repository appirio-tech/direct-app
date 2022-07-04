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
public class GetACopilotSummaryTests extends BaseTestCase {

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
     * Verify the user can go to summary page by pressing "Next Step" button after input valid value .
     *
     * @throws Exception if any error
     */
    public void testFTC66() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > Copilots > Post a Copilot"));
        assertEquals("the page content is incorrect", "Summary", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify breadcrumb is correct on Summary page .
     *
     * @throws Exception if any error
     */
    public void testFTC67() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > Copilots > Post a Copilot"));
        browser.click("//a[contains(text(),'Copilots')]");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the introduction must exist",
            browser.isTextPresent(
                "A Copilot is a TopCoder Member who manages the TopCoder process for a customer in order to"
                    + " deliver a requested asset. For example, a customer may ask to build a website. A Copilot"
                    + " will work with that customer to agree on a plan and pricing to build that website and"
                    + " then they would manage the process using the TopCoder Platform to deliver the website"
                    + " back to the customer."));
    }

    /**
     * Verify "Back to Dashboard" button on Summary page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC69() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > Copilots > Post a Copilot"));
        browser.click("link=Back to Dashboard");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isTextPresent("Dashboard > All Active Contests"));
        assertTrue("the page content is incorrect",
                browser.isElementPresent("//img[@src='/images/dashboard_logo.png']"));
    }

    /**
     * Verify user can go to Step 1 page by pressing "Step 1" link  .
     *
     * @throws Exception if any error
     */
    public void testFTC70() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("id=stepLink_1");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "1Basic", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify user can go to Step 2 page by pressing "Step 2" link  .
     *
     * @throws Exception if any error
     */
    public void testFTC71() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("id=stepLink_4");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "2Schedule", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify user can go to Step 3 page by pressing "Step 3" link  .
     *
     * @throws Exception if any error
     */
    public void testFTC72() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("id=stepLink_5");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "3Billing", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify all elements can be displayed on Summary page .
     *
     * @throws Exception if any error
     */
    public void testFTC73() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Project Name:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Copilot Opportunity Title:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Public Description:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Specific Description:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Uploaded Documents:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Action:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("1ST place:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("2ND place:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Total:"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Billing Account:"));
    }

    /**
     * Verify all data can be displayed on Summary page .
     *
     * @throws Exception if any error
     */
    public void testFTC74() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("the page content is incorrect", browser.isTextPresent("Client 1 Billing Account 1 Project 2"));
        assertTrue("the page content is incorrect", browser.isTextPresent(contest));
        assertTrue("the page content is incorrect", browser.isTextPresent("Post Now"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Test Public Description"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Test Specification Description"));
        assertTrue("the page content is incorrect", browser.isTextPresent("$150"));
        assertTrue("the page content is incorrect", browser.isTextPresent("$225"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Liquid (CA)"));
    }

    /**
     * Verify "Save As Draft" button on Summary page is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC75() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        assertTrue("the page content is incorrect", browser.isVisible("demoModal"));
        assertTrue("the page content is incorrect",
            browser.isTextPresent("Your Copilot Posting " + contest + " has been saved as draft successfully."));
        browser.click("css=span.btnC");
        assertFalse("the page content is incorrect", browser.isVisible("demoModal"));
    }

    /**
     * Verify the user can go to step 1 page after click "Edit" button beside Project Details section .
     *
     * @throws Exception if any error
     */
    public void testFTC76() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=editStep1");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "1Basic", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify the user can go to step 1 page after click "Edit" button beside Copilot Opportunity Details
     * section .
     *
     * @throws Exception if any error
     */
    public void testFTC77() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("//div[@id='stepContainer']/div[4]/div/div[7]/div/div[2]/div/div/div/a");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "1Basic", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify the user can go to step 1 page after click "Edit" button beside Document Upload section .
     *
     * @throws Exception if any error
     */
    public void testFTC78() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("//div[@id='stepContainer']/div[4]/div/div[7]/div/div[3]/div/div/div/a");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "1Basic", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify the user can go to step 2 page after click "Edit" button beside Project Schedule section .
     *
     * @throws Exception if any error
     */
    public void testFTC79() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=editStep4");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "2Schedule", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify the user can go to step 3 page after click "Edit" button beside Billing section .
     *
     * @throws Exception if any error
     */
    public void testFTC80() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=editStep5");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("the page content is incorrect", "3Billing", browser.getText("//div[@class='stepTitle']"));
    }

    /**
     * Verify there will have validation information when click "View Contest" link without click "Save As
     * Draft" first .
     *
     * @throws Exception if any error
     */
    public void testFTC81() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("previewButton");
        Thread.sleep(TestHelper.SLEEP);
        assertTrue("error popup should be displayed", browser.isVisible("errortModal"));
        assertTrue("error popup should be displayed",
            browser.isTextPresent("You must 'Save as Draft' before you can preview your contest."));
    }

    /**
     * Verify click "View Contest" link on Confirmation page will navigate to contest page   .
     *
     * @throws Exception if any error
     */
    public void testFTC82() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, POST NOW");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("name=amount");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("css=span.right");
        Thread.sleep(TestHelper.SLEEP * 10);
        assertTrue("the page content is incorrect", browser.isVisible("demoModal"));
        assertTrue("the page content is incorrect",
            browser.isTextPresent("Your Copilot Posting " + contest + " has been saved as draft successfully."));
        browser.click("css=span.btnC");
        assertFalse("the page content is incorrect", browser.isVisible("demoModal"));
        browser.click("previewButton");
        Thread.sleep(TestHelper.SLEEP);
        assertEquals("a preview window should be displayed", 2, browser.getAllWindowIds().length);
    }

    /**
     * Verify there will have no "Submit" button if user select "Yes, Do it later" button   .
     *
     * @throws Exception if any error
     */
    public void testFTC83() throws Exception {
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
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("link=YES, DO IT LATER");
        Thread.sleep(TestHelper.SLEEP);
        browser.click("//div[@id='stepContainer']/div[4]/div/div[5]/div/div[2]/div[3]/div[4]/div/ul/li/input");
        browser.type("startDate", "12/12/2020");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        browser.select("id=billingProjects", "label=Liquid (CA)");
        browser.click("css=div.BottomBar > a.nextStepButton");
        Thread.sleep(TestHelper.SLEEP);
        assertFalse("submit button should not be displayed", browser.isElementPresent("submitButton"));
    }
}
