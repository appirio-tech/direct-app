/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.management.project.Prize;

/**
 * <p>
 * Unit test for Submission.
 * </p>
 * <p>
 * <em>Changes in 1.1: </em>
 * <ul>
 * <li>Added test cases for getter and setter of submissionType attribute.</li>
 * <li>Added test case testIsValidToPersist_Accuracy6.</li>
 * <li>Updated test case testIsValidToPersist_Accuracy5.</li>
 * </ul>
 * </p>
 * <p>
 * <em>Changes in 1.2: </em>
 * <ul>
 * <li>Modified test cases for getUploads and setUploads methods.</li>
 * <li>Added test cases for all public setter and getter methods.</li>
 * </ul>
 * </p>
 *
 * @author singlewood, sparemax
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class SubmissionTests extends TestCase {
    /**
     * <p>
     * The test Submission instance.
     * </p>
     */
    private Submission submission;

    /**
     * <p>
     * The uploads list for submission.
     * </p>
     */
    private List<Upload> uploads;

    /**
     * <p>
     * The images list for submission.
     * </p>
     *
     * @since 1.2
     */
    private List<SubmissionImage> images;

    /**
     * <p>
     * The test SubmissionStatus instance.
     * </p>
     */
    private SubmissionStatus submissionStatus;

    /**
     * <p>
     * The test SubmissionType instance.
     * </p>
     *
     * @since 1.1
     */
    private SubmissionType submissionType;

    /**
     * Create the test instance.
     *
     * @throws Exception
     *             exception to JUnit.
     */
    public void setUp() throws Exception {
        Upload upload = DeliverableTestHelper.getValidToPersistUpload();
        upload.setId(1);

        uploads = Arrays.asList(upload);

        SubmissionImage image = DeliverableTestHelper.getValidToPersistSubmissionImage();

        images = Arrays.asList(image);

        submissionStatus = DeliverableTestHelper.getValidToPersistSubmissionStatus();
        submissionStatus.setId(1);
        submissionType = DeliverableTestHelper.getValidToPersistSubmissionType();
        submissionType.setId(1);

        submission = DeliverableTestHelper.getValidToPersistSubmission();
    }

    /**
     * Clean the config.
     *
     * @throws Exception
     *             exception to JUnit.
     */
    public void tearDown() throws Exception {
        uploads = null;
        submissionStatus = null;
        submission = null;
    }

    /**
     * The default constructor should set id to UNSET_ID. So check if id is set properly. No exception should be thrown.
     */
    public void testConstructor1_Accuracy1() {
        assertEquals("the constructor doesn't set id properly", Submission.UNSET_ID, submission.getId());
    }

    /**
     * Test constructor2 with invalid parameters. The argument will be set to 0. IllegalArgumentException should be
     * thrown.
     */
    public void testConstructor2_InvalidLong1() {
        try {
            new Submission(0);
            fail("IllegalArgumentException should be thrown because of the null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test constructor2 with invalid parameters. The argument will be set to -2. IllegalArgumentException should be
     * thrown.
     */
    public void testConstructor2_InvalidLong2() {
        try {
            new Submission(-2);
            fail("IllegalArgumentException should be thrown because of invalid parameters.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test constructor2 with valid parameter. Check if the constructor set the id fields properly. No exception should
     * be thrown.
     */
    public void testConstructor2_Accuracy1() {
        submission = new Submission(123);
        assertEquals("constructor doesn't work properly.", 123, submission.getId());
    }

    /**
     * Test the behavior of setUploads. Set the uploads field, see if the getUploads method can get the correct value.
     * No exception should be thrown.
     */
    public void testSetUploads_Accuracy1() {
        submission.setUploads(uploads);
        assertEquals("uploads is not set properly.", uploads, submission.getUploads());
    }

    /**
     * Test the behavior of getUploads. Set the upload field, see if the getUploads method can get the correct value. No
     * exception should be thrown.
     */
    public void testGetUploads_Accuracy1() {
        submission.setUploads(uploads);
        assertEquals("getUploads doesn't work properly.", uploads, submission.getUploads());
    }

    /**
     * Test the behavior of setSubmissionStatus. Set the submissionStatus field, see if the getSubmissionStatus method
     * can get the correct value. No exception should be thrown.
     */
    public void testSetSubmissionStatus_Accuracy1() {
        submission.setSubmissionStatus(submissionStatus);
        assertEquals("submissionStatus is not set properly.", submissionStatus, submission.getSubmissionStatus());
    }

    /**
     * Test the behavior of getSubmissionStatus. Set the submissionStatus field, see if the getSubmissionStatus method
     * can get the correct value. No exception should be thrown.
     */
    public void testGetSubmissionStatus_Accuracy1() {
        submission.setSubmissionStatus(submissionStatus);
        assertEquals("getSubmissionStatus doesn't work properly.", submissionStatus, submission.getSubmissionStatus());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSubmissionType()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     *
     * @since 1.1
     */
    public void test_getSubmissionType() {
        SubmissionType value = new SubmissionType();
        submission.setSubmissionType(value);

        assertSame("'submissionType' value should be properly retrieved.", value, submission.getSubmissionType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmissionType(SubmissionType submissionType)</code>.<br>
     * The value should be properly set.
     * </p>
     *
     * @since 1.1
     */
    public void test_setSubmissionType() {
        SubmissionType value = new SubmissionType();
        submission.setSubmissionType(value);

        assertSame("'submissionType' value should be properly set.", value, submission.getSubmissionType());
    }

    /**
     * <p>
     * Tests the <code>getFinalScore()</code> method.
     * </p>
     * <p>
     * The default value should be null.
     * </p>
     *
     * @since 1.2
     */
    public void testGetFinalScore() {
        assertNull("the default value should be null", submission.getFinalScore());
    }

    /**
     * <p>
     * Tests the <code>setFinalScore(Double)</code> method.
     * </p>
     * <p>
     * The final score should be set properly.
     * </p>
     *
     * @since 1.2
     */
    public void testSetFinalScore() {
        Double finalScore = new Double(80.0);

        submission.setFinalScore(finalScore);

        assertEquals("The final score should be set properly.", finalScore, submission.getFinalScore());
    }

    /**
     * <p>
     * Tests the <code>getInitialScore()</code> method.
     * </p>
     * <p>
     * The default value should be null.
     * </p>
     *
     * @since 1.2
     */
    public void testGetInitialScore() {
        assertNull("the default value should be null", submission.getInitialScore());
    }

    /**
     * <p>
     * Tests the <code>setInitialScore(Double)</code> method.
     * </p>
     * <p>
     * The initial score should be set properly.
     * </p>
     *
     * @since 1.2
     */
    public void testSetInitialScore() {
        Double initialScore = new Double(80.0);

        submission.setInitialScore(initialScore);

        assertEquals("The initial score should be set properly.", initialScore, submission.getInitialScore());
    }

    /**
     * <p>
     * Tests the <code>getScreeningScore()</code> method.
     * </p>
     * <p>
     * The default value should be null.
     * </p>
     *
     * @since 1.2
     */
    public void testGetScreeningScore() {
        assertNull("the default value should be null", submission.getScreeningScore());
    }

    /**
     * <p>
     * Tests the <code>setScreeningScore(Double)</code> method.
     * </p>
     * <p>
     * The screening score should be set properly.
     * </p>
     *
     * @since 1.2
     */
    public void testSetScreeningScore() {
        Double screeningScore = new Double(80.0);

        submission.setScreeningScore(screeningScore);

        assertEquals("The screening score should be set properly.", screeningScore, submission.getScreeningScore());
    }

    /**
     * <p>
     * Tests the <code>getPlacement()</code> method.
     * </p>
     * <p>
     * The default value should be null.
     * </p>
     *
     * @since 1.2
     */
    public void testGetPlacement() {
        assertNull("the default value should be null", submission.getPlacement());
    }

    /**
     * <p>
     * Tests the <code>setPlacement(Long)</code> method.
     * </p>
     * <p>
     * The placement should be set properly.
     * </p>
     *
     * @since 1.2
     */
    public void testSetPlacement() {
        Long placement = new Long(1);

        submission.setPlacement(placement);

        assertEquals("The placement should be set properly.", placement, submission.getPlacement());
    }

    /**
     * <p>
     * Tests the <code>isThumb()</code> method.
     * </p>
     * <p>
     * The default value should be false.
     * </p>
     *
     * @since 1.2
     */
    public void testIsThumb() {
        assertFalse("The default value should be false.", submission.isThumb());
    }

    /**
     * <p>
     * Tests the <code>setThumb(boolean)</code> method.
     * </p>
     * <p>
     * The thumb field should be set properly.
     * </p>
     *
     * @since 1.2
     */
    public void testSetThumb() {
        submission.setThumb(true);

        assertTrue("the thumb field should be set.", submission.isThumb());
    }

    /**
     * <p>
     * Tests the <code>getUserRank()</code> method.
     * </p>
     * <p>
     * The default value should be 1.
     * </p>
     *
     * @since 1.2
     */
    public void testGetUserRank() {
        assertEquals("The default value should be 1.", 1, submission.getUserRank());
    }

    /**
     * <p>
     * Tests the <code>setUserRank()</code> method.
     * </p>
     * <p>
     * The user rank should be set properly.
     * </p>
     *
     * @since 1.2
     */
    public void testSetUserRank() {
        int[] userRanks = new int[] {-1, 0, 1};

        for (int userRank : userRanks) {
            submission.setUserRank(userRank);

            assertEquals("The user rank is not set properly.", userRank, submission.getUserRank());
        }
    }

    /**
     * <p>
     * Tests the <code>isExtra()</code> method.
     * </p>
     * <p>
     * The default value should be false.
     * </p>
     *
     * @since 1.2
     */
    public void testIsExtra() {
        assertFalse("The default value should be false.", submission.isExtra());
    }

    /**
     * <p>
     * Tests the <code>setExtra(boolean)</code> method.
     * </p>
     * <p>
     * The extra field should be set properly.
     * </p>
     *
     * @since 1.2
     */
    public void testSetExtra() {
        submission.setExtra(true);

        assertTrue("the extra field should be set.", submission.isExtra());
    }

    /**
     * <p>
     * Test the <code>setImages(List)</code> method.
     * </p>
     * <p>
     * Set the images field, see if the getImages method can get the correct value. No exception should be thrown.
     * </p>
     *
     * @since 1.2
     */
    public void testSetImages() {
        submission.setImages(images);
        assertEquals("images is not set properly.", images, submission.getImages());
    }

    /**
     * <p>
     * Test the <code>getImages()</code> method.
     * </p>
     * <p>
     * The default value should be null.
     * </p>
     *
     * @since 1.2
     */
    public void testGetImages() {
        submission = new Submission();
        assertNull("getImages should return null.", submission.getImages());
    }

    /**
     * <p>
     * Tests the <code>getPrize()</code> method.
     * </p>
     * <p>
     * The default value should be null.
     * </p>
     *
     * @since 1.2
     */
    public void testGetPrize() {
        assertNull("the default value should be null.", submission.getPrize());
    }

    /**
     * <p>
     * Tests the <code>setPrize()</code> method.
     * </p>
     * <p>
     * The prize field should be set properly.
     * </p>
     *
     * @since 1.2
     */
    public void testSetPrize() {
        Prize prize = new Prize();

        submission.setPrize(prize);

        assertSame("the prize field should be set properly.", prize, submission.getPrize());
    }

    /**
     * Test the behavior of isValidToPersist. Set the uploads field to null, see if the isValidToPersist returns false.
     * No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy1() {
        submission.setUploads(null);
        submission.setSubmissionStatus(submissionStatus);
        submission.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", false, submission.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set the submissionStatus field to null, see if the isValidToPersist
     * returns false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy2() {
        submission.setUploads(uploads);
        submission.setSubmissionStatus(null);
        submission.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", false, submission.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Do not set id field, see if the isValidToPersist returns false. No
     * exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy3() {
        submission.setUploads(uploads);
        submission.setSubmissionStatus(submissionStatus);
        assertEquals("isValidToPersist doesn't work properly.", false, submission.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set super.isValidToPersist() to false, see if the isValidToPersist returns
     * false. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy4() {
        submission.setUploads(uploads);
        submission.setSubmissionStatus(submissionStatus);
        submission.setId(1);
        submission.setCreationUser(null);
        assertEquals("isValidToPersist doesn't work properly.", false, submission.isValidToPersist());
    }

    /**
     * Test the behavior of isValidToPersist. Set all the field with non-null values, see if the isValidToPersist
     * returns true. No exception should be thrown.
     */
    public void testIsValidToPersist_Accuracy5() {
        submission.setUploads(uploads);
        submission.setSubmissionStatus(submissionStatus);
        submission.setSubmissionType(submissionType);
        submission.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", true, submission.isValidToPersist());
    }

    /**
     * <p>
     * Test the behavior of isValidToPersist. Set super.isValidToPersist() to false, see if the isValidToPersist returns
     * false. No exception should be thrown.
     * </p>
     *
     * @since 1.1
     */
    public void testIsValidToPersist_Accuracy6() {
        submission.setUploads(uploads);
        submission.setSubmissionStatus(submissionStatus);
        submission.setSubmissionType(null);
        submission.setId(1);
        assertEquals("isValidToPersist doesn't work properly.", false, submission.isValidToPersist());
    }

    /**
     * <p>
     * Test the <code>isValidToPersist()</code> method.
     * </p>
     * <p>
     * If any Upload.isValidToPersist() returns false, should return false.
     * </p>
     *
     * @since 1.2
     */
    public void testIsValidToPersist_Accuracy7() {
        submission.setUploads(Arrays.asList(new Upload()));
        submission.setSubmissionStatus(submissionStatus);
        submission.setSubmissionType(submissionType);
        submission.setId(1);
        assertFalse("isValidToPersist doesn't work properly.", submission.isValidToPersist());
    }
}
