/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit test for <code>{@link SubmissionImage}</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.2
 * @since 1.2
 */
public class SubmissionImageTests extends TestCase {

    /**
     * Represents the SubmissionImage instance for testing.
     */
    private SubmissionImage submissionImage;

    /**
     * Set up the testing environment.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    protected void setUp() throws Exception {
        super.setUp();

        submissionImage = new SubmissionImage();
    }

    /**
     * Tear down the testing environment.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        submissionImage = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>SubmissionImage()</code> constructor.
     * </p>
     * <p>
     * Instance should be created all the time.
     * </p>
     */
    public void testCtor() {
        submissionImage = new SubmissionImage();

        assertNotNull("Instance should be created", submissionImage);
    }

    /**
     * <p>
     * Tests the <code>getSubmissionId()</code> method.
     * </p>
     * <p>
     * The default submission id should be 0.
     * </p>
     */
    public void testGetSubmissionId() {
        assertEquals("The default submission id should be 0.", 0, submissionImage.getSubmissionId());
    }

    /**
     * <p>
     * Tests the <code>setSubmissionId(long)</code> method.
     * </p>
     * <p>
     * The submission id should be set internally for any value.
     * </p>
     */
    public void testSetSubmissionId() {
        long[] submissionIds = new long[] {-1, 0, 1};
        for (long submissionId : submissionIds) {
            submissionImage.setSubmissionId(submissionId);

            assertEquals("The submission id is not set correctly.", submissionId, submissionImage.getSubmissionId());
        }
    }

    /**
     * <p>
     * Tests the <code>getImageId()</code> method.
     * </p>
     * <p>
     * The default image id should be 0.
     * </p>
     */
    public void testGetImageId() {
        assertEquals("The default image id should be 0.", 0, submissionImage.getImageId());
    }

    /**
     * <p>
     * Tests the <code>setImageId(int)</code> method.
     * </p>
     * <p>
     * The image id should be set internally for any value.
     * </p>
     */
    public void testSetImageId() {
        int[] imageIds = new int[] {-1, 0, 1};
        for (int imageId : imageIds) {
            submissionImage.setImageId(imageId);

            assertEquals("The image id is not set correctly.", imageId, submissionImage.getImageId());
        }
    }

    /**
     * <p>
     * Tests the <code>getSortOrder()</code> method.
     * </p>
     * <p>
     * The default sort order should be 0.
     * </p>
     */
    public void testGetSortOrder() {
        assertEquals("The default sort order should be 0.", 0, submissionImage.getSortOrder());
    }

    /**
     * <p>
     * Tests the <code>setSortOrder(int)</code> method.
     * </p>
     * <p>
     * The sort order should be set internally for any value.
     * </p>
     */
    public void testSetSortOrder() {
        int[] sortOrders = new int[] {-1, 0, 1};
        for (int sortOrder : sortOrders) {
            submissionImage.setSortOrder(sortOrder);

            assertEquals("The sort order is not set correctly.", sortOrder, submissionImage.getSortOrder());
        }
    }

    /**
     * <p>
     * Tests <code>getModifyDate()</code> method.
     * </p>
     * <p>
     * The default modify date should be null.
     * </p>
     */
    public void testGetModifyDate() {
        assertNull("the default value should be null", submissionImage.getModifyDate());
    }

    /**
     * <p>
     * Tests <code>setModifyDate(Date)</code> method.
     * </p>
     * <p>
     * The modify date should be set internally.
     * </p>
     */
    public void testSetModifyDate() {
        Date modifyDate = new Date();
        submissionImage.setModifyDate(modifyDate);

        assertSame("the modify date is not set properly.", modifyDate, submissionImage.getModifyDate());
    }

    /**
     * <p>
     * Tests <code>getCreateDate()</code> method.
     * </p>
     * <p>
     * The default create date should be null.
     * </p>
     */
    public void testGetCreateDate() {
        assertNull("the default value should be null", submissionImage.getCreateDate());
    }

    /**
     * <p>
     * Tests <code>setCreateDate(Date)</code> method.
     * </p>
     * <p>
     * The create date should be set internally.
     * </p>
     */
    public void testSetCreateDate() {
        Date createDate = new Date();
        submissionImage.setCreateDate(createDate);

        assertSame("the create date is not set properly.", createDate, submissionImage.getCreateDate());
    }

    /**
     * <p>
     * Tests <code>isValidToPersist()</code> method.
     * </p>
     * <p>
     * If both submission id and image id are not positive, should return false.
     * </p>
     */
    public void testIsValidToPersist1() {
        assertFalse("the entity is not valid to persist", submissionImage.isValidToPersist());
    }

    /**
     * <p>
     * Tests <code>isValidToPersist()</code> method.
     * </p>
     * <p>
     * If submission id is not positive, should return false.
     * </p>
     */
    public void testIsValidToPersist2() {
        submissionImage.setImageId(1);
        assertFalse("the entity is not valid to persist", submissionImage.isValidToPersist());
    }

    /**
     * <p>
     * Tests <code>isValidToPersist()</code> method.
     * </p>
     * <p>
     * If image id is not positive, should return false.
     * </p>
     */
    public void testIsValidToPersist3() {
        submissionImage.setSubmissionId(1);
        assertFalse("the entity is not valid to persist", submissionImage.isValidToPersist());
    }

    /**
     * <p>
     * Tests <code>isValidToPersist()</code> method.
     * </p>
     * <p>
     * If both submission id and image id are positive, should return true.
     * </p>
     */
    public void testIsValidToPersist4() {
        submissionImage.setImageId(1);
        submissionImage.setSubmissionId(1);
        assertTrue("the entity is valid to persist", submissionImage.isValidToPersist());
    }
}
