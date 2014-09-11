/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.io.Serializable;
import java.util.Date;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases of ScreeningTask. Tests the class for proper behavior.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class ScreeningTaskTests extends TestCase {
    /**
     * <p>
     * The id used for testing.
     * </p>
     */
    private static final long ID = 123;

    /**
     * <p>
     * The ScreeningStatus used for testing.
     * </p>
     */
    private static final ScreeningStatus SCREENINGSTATUS = new ScreeningStatus();

    /**
     * <p>
     * The upload used for testing.
     * </p>
     */
    private static final long UPLOAD = 555;

    /**
     * <p>
     * The screener used for testing.
     * </p>
     */
    private static final long SCREENER = 666;

    /**
     * <p>
     * The startTimestamp used for testing.
     * </p>
     */
    private static final Date STARTTIMESTAMP = new Date(System.currentTimeMillis() + 10000000);

    /**
     * <p>
     * The creationUser used for testing.
     * </p>
     */
    private static final String CREATIONUSER = "test create user";

    /**
     * <p>
     * The creationTimestamp used for testing.
     * </p>
     */
    private static final Date CREATIONTIMESTAMP = new Date(System.currentTimeMillis() - 10000000);

    /**
     * <p>
     * The modificationUser used for testing.
     * </p>
     */
    private static final String MODIFICATIONUSER = "test modify user";

    /**
     * <p>
     * The modificationTimestamp used for testing.
     * </p>
     */
    private static final Date MODIFICATIONTIMESTAMP = new Date();

    /**
     * <p>
     * The ScreeningResult used for testing.
     * </p>
     */
    private static final ScreeningResult SCREENINGRESULT1 = new ScreeningResult();

    /**
     * <p>
     * The ScreeningResult used for testing.
     * </p>
     */
    private static final ScreeningResult SCREENINGRESULT2 = new ScreeningResult();

    /**
     * <p>
     * An instance of <code>ScreeningTask</code> which is tested.
     * </p>
     */
    private ScreeningTask target = null;

    /**
     * <p>
     * setUp() routine. Creates test ScreeningTask instance.
     * </p>
     */
    protected void setUp() {
        this.target = new ScreeningTask();
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ScreeningTask implements Serializable.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ScreeningTask does not implement Serializable.", target instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningTask()</code> Create for proper behavior.
     * </p>
     */
    public void testCreate_accuracy() {
        assertNotNull("Failed to initialize ScreeningTask object.", target);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setId(long)</code> for proper behavior. Verify that id is
     * saved as is.
     * </p>
     */
    public void testSetId_accuracy() {
        target.setId(ID);
        assertEquals("id", ID, target.getId());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setId(long)</code> for proper behavior. Verify that
     * IllegalArgumentException is thrown for invalid id.
     * </p>
     */
    public void testSetId_1_failure() {
        try {
            target.setId(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>setId(long)</code> for proper behavior. Verify that
     * IllegalArgumentException is thrown for invalid id.
     * </p>
     */
    public void testSetId_2_failure() {
        try {
            target.setId(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getId()</code> for proper behavior. Verify that id is
     * returned as is.
     * </p>
     */
    public void testGetId_accuracy() {
        target.setId(ID);
        assertEquals("id", ID, target.getId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setScreeningStatus(ScreeningStatus)</code> for proper
     * behavior. Verify that ScreeningStatus is saved as is.
     * </p>
     */
    public void testSetScreeningStatus_accuracy() {
        target.setScreeningStatus(SCREENINGSTATUS);
        assertEquals("ScreeningStatus", SCREENINGSTATUS, target.getScreeningStatus());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setScreeningStatus(ScreeningStatus)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for invalid ScreeningStatus.
     * </p>
     */
    public void testSetScreeningStatus_1_failure() {
        try {
            target.setScreeningStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreeningStatus()</code> for proper behavior. Verify that
     * ScreeningStatus is returned as is.
     * </p>
     */
    public void testGetScreeningStatus_accuracy() {
        target.setScreeningStatus(SCREENINGSTATUS);
        assertEquals("ScreeningStatus", SCREENINGSTATUS, target.getScreeningStatus());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setUpload(long)</code> for proper behavior. Verify that
     * Upload is saved as is.
     * </p>
     */
    public void testUpload_accuracy() {
        target.setUpload(UPLOAD);
        assertEquals("Upload", UPLOAD, target.getUpload());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setUpload(long)</code> for proper behavior. Verify that
     * IllegalArgumentException is thrown for invalid Upload.
     * </p>
     */
    public void testSetUpload_1_failure() {
        try {
            target.setUpload(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>setUpload(long)</code> for proper behavior. Verify that
     * IllegalArgumentException is thrown for invalid Upload.
     * </p>
     */
    public void testSetUpload_2_failure() {
        try {
            target.setUpload(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getUpload()</code> for proper behavior. Verify that Upload
     * is returned as is.
     * </p>
     */
    public void testGetUpload_accuracy() {
        target.setUpload(UPLOAD);
        assertEquals("Upload", UPLOAD, target.getUpload());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setScreener(long)</code> for proper behavior. Verify that
     * Screener is saved as is.
     * </p>
     */
    public void testSetScreener_accuracy() {
        target.setScreener(SCREENER);
        assertEquals("Screener", SCREENER, target.getScreener());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setScreener(long)</code> for proper behavior. Verify that
     * IllegalArgumentException is thrown for invalid Screener.
     * </p>
     */
    public void testSetScreener_1_failure() {
        try {
            target.setScreener(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>setScreener(long)</code> for proper behavior. Verify that
     * IllegalArgumentException is thrown for invalid Screener.
     * </p>
     */
    public void testSetScreener_2_failure() {
        try {
            target.setScreener(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreener()</code> for proper behavior. Verify that
     * Screener is returned as is.
     * </p>
     */
    public void testGetScreener_accuracy() {
        target.setScreener(SCREENER);
        assertEquals("Screener", SCREENER, target.getScreener());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setStartTimestamp(Date)</code> for proper behavior. Verify
     * that StartTimestamp is saved as is.
     * </p>
     */
    public void testSetStartTimestamp_accuracy() {
        target.setStartTimestamp(STARTTIMESTAMP);
        assertEquals("StartTimestamp", STARTTIMESTAMP, target.getStartTimestamp());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setStartTimestamp(Date)</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid StartTimestamp.
     * </p>
     */
    public void testSetStartTimestamp_1_failure() {
        try {
            target.setStartTimestamp(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getStartTimestamp()</code> for proper behavior. Verify that
     * StartTimestamp is returned as is.
     * </p>
     */
    public void testGetStartTimestamp_accuracy() {
        target.setStartTimestamp(STARTTIMESTAMP);
        assertEquals("StartTimestamp", STARTTIMESTAMP, target.getStartTimestamp());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>addScreeningResult(ScreeningResult)</code> for proper
     * behavior. Verify that ScreeningResult is saved as is.
     * </p>
     */
    public void testAddScreeningResult_accuracy() {
        target.addScreeningResult(SCREENINGRESULT1);
        assertEquals("ScreeningResult", SCREENINGRESULT1, target.getAllScreeningResults()[0]);
    }

    /**
     * <p>
     * Failure test. Tests the <code>addScreeningResult(ScreeningResult)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for invalid ScreeningResult.
     * </p>
     */
    public void testAddScreeningResult_1_failure() {
        try {
            target.addScreeningResult(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>addScreeningResult(ScreeningResult)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for invalid ScreeningResult.
     * </p>
     */
    public void testAddScreeningResult_2_failure() {
        try {
            target.addScreeningResult(SCREENINGRESULT1);
            target.addScreeningResult(SCREENINGRESULT1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>removeScreeningResult(ScreeningResult)</code> for proper
     * behavior.
     * </p>
     */
    public void testRemoveScreeningResult_accuracy() {
        target.addScreeningResult(SCREENINGRESULT1);
        target.addScreeningResult(SCREENINGRESULT2);
        target.removeScreeningResult(SCREENINGRESULT1);
        assertEquals("There should be only one ScreeningResult in the task.", 1, target
                .getAllScreeningResults().length);
    }

    /**
     * <p>
     * Failure test. Tests the <code>removeScreeningResult(ScreeningResult)</code> for proper
     * behavior.
     * </p>
     */
    public void testRemoveScreeningResult_failure() {
        try {
            target.addScreeningResult(SCREENINGRESULT1);
            target.removeScreeningResult(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getAllScreeningResults()</code> for proper behavior.
     * </p>
     */
    public void testGetAllScreeningResults_accuracy() {
        target.addScreeningResult(SCREENINGRESULT1);
        target.addScreeningResult(SCREENINGRESULT2);
        assertEquals("There should be two ScreeningResult in the task.", 2, target
                .getAllScreeningResults().length);
        assertEquals("Failed to get all screening results.", SCREENINGRESULT1, target
                .getAllScreeningResults()[0]);
        assertEquals("Failed to get all screening results.", SCREENINGRESULT2, target
                .getAllScreeningResults()[1]);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>clearAllScreeningResults()</code> for proper behavior.
     * </p>
     */
    public void testClearAllScreeningResults_accuracy() {
        target.addScreeningResult(SCREENINGRESULT1);
        target.addScreeningResult(SCREENINGRESULT2);
        target.clearAllScreeningResults();
        assertEquals("There should be no ScreeningResult in the task.", 0, target
                .getAllScreeningResults().length);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setCreationUser(String)</code> for proper behavior. Verify
     * that CreationUser is saved as is.
     * </p>
     */
    public void testSetCreationUser_accuracy() {
        target.setCreationUser(CREATIONUSER);
        assertEquals("CreationUser", CREATIONUSER, target.getCreationUser());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setCreationUser(String)</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid CreationUser.
     * </p>
     */
    public void testSetCreationUser_1_failure() {
        try {
            target.setCreationUser(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>setCreationUser(String)</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid CreationUser.
     * </p>
     */
    public void testSetCreationUser_2_failure() {
        try {
            target.setCreationUser("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getCreationUser()</code> for proper behavior. Verify that
     * CreationUser is returned as is.
     * </p>
     */
    public void testGetCreationUser_accuracy() {
        target.setCreationUser(CREATIONUSER);
        assertEquals("CreationUser", CREATIONUSER, target.getCreationUser());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setCreationTimestamp(String)</code> for proper behavior.
     * Verify that CreationTimestamp is saved as is.
     * </p>
     */
    public void testSetCreationTimestamp_accuracy() {
        target.setCreationTimestamp(CREATIONTIMESTAMP);
        assertEquals("CreationTimestamp", CREATIONTIMESTAMP, target.getCreationTimestamp());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setCreationTimestamp(String)</code> for proper behavior.
     * Verify that IllegalArgumentException is thrown for invalid CreationTimestamp.
     * </p>
     */
    public void testSetCreationTimestamp_1_failure() {
        try {
            target.setCreationTimestamp(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getCreationTimestamp()</code> for proper behavior. Verify
     * that CreationTimestamp is returned as is.
     * </p>
     */
    public void testGetCreationTimestamp_accuracy() {
        target.setCreationTimestamp(CREATIONTIMESTAMP);
        assertEquals("CreationTimestamp", CREATIONTIMESTAMP, target.getCreationTimestamp());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setModificationUser(String)</code> for proper behavior.
     * Verify that ModificationUser is saved as is.
     * </p>
     */
    public void testSetModificationUser_accuracy() {
        target.setModificationUser(MODIFICATIONUSER);
        assertEquals("ModificationUser", MODIFICATIONUSER, target.getModificationUser());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setModificationUser(String)</code> for proper behavior.
     * Verify that IllegalArgumentException is thrown for invalid ModificationUser.
     * </p>
     */
    public void testSetModificationUser_1_failure() {
        try {
            target.setModificationUser(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>setModificationUser(String)</code> for proper behavior.
     * Verify that IllegalArgumentException is thrown for invalid ModificationUser.
     * </p>
     */
    public void testSetModificationUser_2_failure() {
        try {
            target.setModificationUser("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getModificationUser()</code> for proper behavior. Verify
     * that ModificationUser is returned as is.
     * </p>
     */
    public void testGetModificationUser_accuracy() {
        target.setModificationUser(MODIFICATIONUSER);
        assertEquals("ModificationUser", MODIFICATIONUSER, target.getModificationUser());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setModificationTimestamp(String)</code> for proper behavior.
     * Verify that ModificationTimestamp is saved as is.
     * </p>
     */
    public void testSetModificationTimestamp_accuracy() {
        target.setModificationTimestamp(MODIFICATIONTIMESTAMP);
        assertEquals("ModificationTimestamp", MODIFICATIONTIMESTAMP, target
                .getModificationTimestamp());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setModificationTimestamp(String)</code> for proper behavior.
     * Verify that IllegalArgumentException is thrown for invalid ModificationTimestamp.
     * </p>
     */
    public void testSetModificationTimestamp_1_failure() {
        try {
            target.setModificationTimestamp(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getModificationTimestamp()</code> for proper behavior.
     * Verify that ModificationTimestamp is returned as is.
     * </p>
     */
    public void testGetModificationTimestamp_accuracy() {
        target.setModificationTimestamp(MODIFICATIONTIMESTAMP);
        assertEquals("ModificationTimestamp", MODIFICATIONTIMESTAMP, target
                .getModificationTimestamp());
    }
}
