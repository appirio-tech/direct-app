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
public class GetACopilotTests extends BaseTestCase {

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
     * Verify the user can go to introduction get a copilot page by pressing "Get a Copilot" button.
     *
     * @throws Exception if any error
     */
    public void testFTC1() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the copilot page must display", "TopCoder Copilots", browser.getText("//a[@class='logo']"));
        assertTrue("the introduction must exist",
            browser.isTextPresent(
                "A Copilot is a TopCoder Member who manages the TopCoder process for a customer in order to deliver"
                    + " a requested asset. For example, a customer may ask to build a website. A Copilot will work"
                    + " with that customer to agree on a plan and pricing to build that website and then they would"
                    + " manage the process using the TopCoder Platform to deliver the website back to the customer."));
    }

    /**
     * Verify the user can go to introduction get a copilot page by pressing "Copilots" tab.
     *
     * @throws Exception if any error
     */
    public void testFTC2() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the copilot page must display", "TopCoder Copilots", browser.getText("//a[@class='logo']"));
        assertTrue("the introduction must exist",
            browser.isTextPresent(
                "A Copilot is a TopCoder Member who manages the TopCoder process for a customer in order to deliver"
                    + " a requested asset. For example, a customer may ask to build a website. A Copilot will work"
                    + " with that customer to agree on a plan and pricing to build that website and then they would"
                    + " manage the process using the TopCoder Platform to deliver the website back to the customer."));
    }

    /**
     * Verify all elements are displayed on content section of Get a Copilot page  .
     *
     * @throws Exception if any error
     */
    public void testFTC3() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isTextPresent("Step 1"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Step 2"));
        assertTrue("the page content is incorrect", browser.isTextPresent("Step 3"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=Work with a Copilot"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=Select from Copilot Pool"));
    }

    /**
     * Verify all 3 tabs will be listed on get a copilot page .
     *
     * @throws Exception if any error
     */
    public void testFTC4() throws Exception {
        browser.click("link=Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isElementPresent("link=Get a Copilot"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=My Copilot Postings"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=Manage Copilots"));
    }

    /**
     * Verify breadcrumb is correct after get a copilot page .
     *
     * @throws Exception if any error
     */
    public void testFTC5() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect",
            browser.isTextPresent("Dashboard > Copilots > Introduction Get a Copilot"));
        browser.click("//a[contains(text(),'Copilots')]");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the introduction must exist",
            browser.isTextPresent(
                  "A Copilot is a TopCoder Member who manages the TopCoder process for a customer in order to deliver"
                     + " a requested asset. For example, a customer may ask to build a website. A Copilot will work"
                     + " with that customer to agree on a plan and pricing to build that website and then they would"
                     + " manage the process using the TopCoder Platform to deliver the website back to the customer."));
    }

    /**
     * Verify top 8 of copilots will be listed on the introduction page .
     *
     * @throws Exception if any error
     */
    public void testFTC6() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertTrue("the page content is incorrect", browser.isElementPresent("//div[@class='copilot-carousel']"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=>"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=<"));
        assertTrue("top 8 of copilots will be listed", browser.isTextPresent("3 / 8"));
    }

    /**
     * Verify &gt; button on copilots content section is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC7() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());

        String before = browser.getText("//div[@class='foot-bar']");
        browser.click("link=>");
        Thread.sleep(TestHelper.SLEEP);

        String after = browser.getText("//div[@class='foot-bar']");
        assertNotSame("the page content is incorrect", before, after);
    }

    /**
     * Verify &lt; button on copilots content section is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC8() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());

        String before = browser.getText("//div[@class='foot-bar']");
        browser.click("link=<");
        Thread.sleep(TestHelper.SLEEP);

        String after = browser.getText("//div[@class='foot-bar']");
        assertNotSame("the page content is incorrect", before, after);
    }

    /**
     * Verify the application can go to "View All Copilots" page by pressing "View All Copilots" link .
     *
     * @throws Exception if any error
     */
    public void testFTC9() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=View All Copilots");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "TopCoder :: Copilot Pool", browser.getTitle());
    }

    /**
     * Verify all the links on the view copilot section is working well .
     *
     * @throws Exception if any error
     */
    public void testFTC10() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=lmmortal");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "TopCoder Member Profile", browser.getTitle());
        browser.goBack();
        browser.click("link=3");
        browser.click("//div[@id='stepContainer']/div[3]/div[2]/div/div/ul/li[3]/div/div[2]/div/p[2]/a");
    }

    /**
     * Verify there have VIDEO TUTORIAL section on the page .
     *
     * @throws Exception if any error
     */
    public void testFTC11() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "Video Tutorial", browser.getText("//div[@class='titleRight']"));
        assertTrue("the page content is incorrect", browser.isElementPresent("link=View All Videos"));
    }

    /**
     * Verify the application can navigate to right page when click "View More" link on the page  .
     *
     * @throws Exception if any error
     */
    public void testFTC12() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=View More");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "Copilot Overview - TopCoder Wiki", browser.getTitle());
    }

    /**
     * Verify the application can navigate to right page when click "View All Videos" link on the page  .
     *
     * @throws Exception if any error
     */
    public void testFTC13() throws Exception {
        browser.click("link=Get A Copilot");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        browser.click("link=View All Videos");
        browser.waitForPageToLoad(TestHelper.getTimeout());
        assertEquals("the page content is incorrect", "TopCoder Help Center", browser.getTitle());
    }
}
