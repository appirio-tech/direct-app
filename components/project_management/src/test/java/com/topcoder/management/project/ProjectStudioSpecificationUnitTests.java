/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for {@link ProjectStudioSpecification}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class ProjectStudioSpecificationUnitTests extends TestCase {
    /**
     * Represents {@link ProjectStudioSpecification} instance for tests.
     */
    private ProjectStudioSpecification instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new ProjectStudioSpecification();
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#ProjectStudioSpecification()}.
     */
    @Test
    public void test_ctor() {
        assertNotNull("Unable to instantiate this object", instance);
        assertTrue("Should be true", instance instanceof AuditableObject);
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#getGoals()} method. The method should get the field value
     * correctly.
     */
    @Test
    public void test_getGoals() {
        assertEquals("Should be null", null, instance.getGoals());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setGoals(String)} method. The method should set the field
     * value correctly.
     */
    @Test
    public void test_setGoals() {
        String value = " newValue";
        instance.setGoals(value);
        assertEquals("Should be the same", value, instance.getGoals());
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setGoals(String)} method. If the argument is null,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setGoals_failure1() {
        try {
            instance.setGoals(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setGoals(String)} method. If the argument is empty,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setGoals_failure2() {
        try {
            instance.setGoals(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#getTargetAudience()} method. The method should get the field
     * value correctly.
     */
    @Test
    public void test_getTargetAudience() {
        assertEquals("Should be null", null, instance.getTargetAudience());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setTargetAudience(String)} method. The method should set the
     * field value correctly.
     */
    @Test
    public void test_setTargetAudience() {
        String value = " newValue";
        instance.setTargetAudience(value);
        assertEquals("Should be the same", value, instance.getTargetAudience());
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setTargetAudience(String)} method. If the argument is null,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setTargetAudience_failure1() {
        try {
            instance.setTargetAudience(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setTargetAudience(String)} method. If the argument is empty,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setTargetAudience_failure2() {
        try {
            instance.setTargetAudience(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#getBrandingGuidelines()} method. The method should get the
     * field value correctly.
     */
    @Test
    public void test_getBrandingGuidelines() {
        assertEquals("Should be null", null, instance.getBrandingGuidelines());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setBrandingGuidelines(String)} method. The method should set
     * the field value correctly.
     */
    @Test
    public void test_setBrandingGuidelines() {
        String value = " newValue";
        instance.setBrandingGuidelines(value);
        assertEquals("Should be the same", value, instance.getBrandingGuidelines());
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setBrandingGuidelines(String)} method. If the argument is
     * null, IllegalArgumentException is expected.
     */
    @Test
    public void test_setBrandingGuidelines_failure1() {
        try {
            instance.setBrandingGuidelines(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setBrandingGuidelines(String)} method. If the argument is
     * empty, IllegalArgumentException is expected.
     */
    @Test
    public void test_setBrandingGuidelines_failure2() {
        try {
            instance.setBrandingGuidelines(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#getDislikedDesignWebSites()} method. The method should get
     * the field value correctly.
     */
    @Test
    public void test_getDislikedDesignWebSites() {
        assertEquals("Should be null", null, instance.getDislikedDesignWebSites());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setDislikedDesignWebSites(String)} method. The method should
     * set the field value correctly.
     */
    @Test
    public void test_setDislikedDesignWebSites() {
        String value = " newValue";
        instance.setDislikedDesignWebSites(value);
        assertEquals("Should be the same", value, instance.getDislikedDesignWebSites());
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setDislikedDesignWebSites(String)} method. If the argument is
     * null, IllegalArgumentException is expected.
     */
    @Test
    public void test_setDislikedDesignWebSites_failure1() {
        try {
            instance.setDislikedDesignWebSites(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setDislikedDesignWebSites(String)} method. If the argument is
     * empty, IllegalArgumentException is expected.
     */
    @Test
    public void test_setDislikedDesignWebSites_failure2() {
        try {
            instance.setDislikedDesignWebSites(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#getOtherInstructions()} method. The method should get the
     * field value correctly.
     */
    @Test
    public void test_getOtherInstructions() {
        assertEquals("Should be null", null, instance.getOtherInstructions());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setOtherInstructions(String)} method. The method should set
     * the field value correctly.
     */
    @Test
    public void test_setOtherInstructions() {
        String value = " newValue";
        instance.setOtherInstructions(value);
        assertEquals("Should be the same", value, instance.getOtherInstructions());
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setOtherInstructions(String)} method. If the argument is null,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setOtherInstructions_failure1() {
        try {
            instance.setOtherInstructions(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setOtherInstructions(String)} method. If the argument is
     * empty, IllegalArgumentException is expected.
     */
    @Test
    public void test_setOtherInstructions_failure2() {
        try {
            instance.setOtherInstructions(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#getWinningCriteria()} method. The method should get the field
     * value correctly.
     */
    @Test
    public void test_getWinningCriteria() {
        assertEquals("Should be null", null, instance.getWinningCriteria());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setWinningCriteria(String)} method. The method should set the
     * field value correctly.
     */
    @Test
    public void test_setWinningCriteria() {
        String value = " newValue";
        instance.setWinningCriteria(value);
        assertEquals("Should be the same", value, instance.getWinningCriteria());
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setWinningCriteria(String)} method. If the argument is null,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setWinningCriteria_failure1() {
        try {
            instance.setWinningCriteria(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setWinningCriteria(String)} method. If the argument is empty,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setWinningCriteria_failure2() {
        try {
            instance.setWinningCriteria(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#isSubmittersLockedBetweenRounds()} method. The method should
     * get the field value correctly.
     */
    @Test
    public void test_isSubmittersLockedBetweenRounds() {
        assertEquals("Should be false", false, instance.isSubmittersLockedBetweenRounds());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setSubmittersLockedBetweenRounds(String)} method. The method
     * should set the field value correctly.
     */
    @Test
    public void test_setSubmittersLockedBetweenRounds() {
        boolean value = true;
        instance.setSubmittersLockedBetweenRounds(value);
        assertEquals("Should be the same", value, instance.isSubmittersLockedBetweenRounds());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#getRoundOneIntroduction()} method. The method should get the
     * field value correctly.
     */
    @Test
    public void test_getRoundOneIntroduction() {
        assertEquals("Should be null", null, instance.getRoundOneIntroduction());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setRoundOneIntroduction(String)} method. The method should
     * set the field value correctly.
     */
    @Test
    public void test_setRoundOneIntroduction() {
        String value = " newValue";
        instance.setRoundOneIntroduction(value);
        assertEquals("Should be the same", value, instance.getRoundOneIntroduction());
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setRoundOneIntroduction(String)} method. If the argument is
     * null, IllegalArgumentException is expected.
     */
    @Test
    public void test_setRoundOneIntroduction_failure1() {
        try {
            instance.setRoundOneIntroduction(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setRoundOneIntroduction(String)} method. If the argument is
     * empty, IllegalArgumentException is expected.
     */
    @Test
    public void test_setRoundOneIntroduction_failure2() {
        try {
            instance.setRoundOneIntroduction(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#getRoundTwoIntroduction()} method. The method should get the
     * field value correctly.
     */
    @Test
    public void test_getRoundTwoIntroduction() {
        assertEquals("Should be null", null, instance.getRoundTwoIntroduction());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setRoundTwoIntroduction(String)} method. The method should
     * set the field value correctly.
     */
    @Test
    public void test_setRoundTwoIntroduction() {
        String value = " newValue";
        instance.setRoundTwoIntroduction(value);
        assertEquals("Should be the same", value, instance.getRoundTwoIntroduction());
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setRoundTwoIntroduction(String)} method. If the argument is
     * null, IllegalArgumentException is expected.
     */
    @Test
    public void test_setRoundTwoIntroduction_failure1() {
        try {
            instance.setRoundTwoIntroduction(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setRoundTwoIntroduction(String)} method. If the argument is
     * empty, IllegalArgumentException is expected.
     */
    @Test
    public void test_setRoundTwoIntroduction_failure2() {
        try {
            instance.setRoundTwoIntroduction(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#getColors()} method. The method should get the field value
     * correctly.
     */
    @Test
    public void test_getColors() {
        assertEquals("Should be null", null, instance.getColors());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setColors(String)} method. The method should set the field
     * value correctly.
     */
    @Test
    public void test_setColors() {
        String value = " newValue";
        instance.setColors(value);
        assertEquals("Should be the same", value, instance.getColors());
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setColors(String)} method. If the argument is null,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setColors_failure1() {
        try {
            instance.setColors(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setColors(String)} method. If the argument is empty,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setColors_failure2() {
        try {
            instance.setColors(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#getFonts()} method. The method should get the field value
     * correctly.
     */
    @Test
    public void test_getFonts() {
        assertEquals("Should be null", null, instance.getFonts());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setFonts(String)} method. The method should set the field
     * value correctly.
     */
    @Test
    public void test_setFonts() {
        String value = " newValue";
        instance.setFonts(value);
        assertEquals("Should be the same", value, instance.getFonts());
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setFonts(String)} method. If the argument is null,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setFonts_failure1() {
        try {
            instance.setFonts(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setFonts(String)} method. If the argument is empty,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setFonts_failure2() {
        try {
            instance.setFonts(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#getLayoutAndSize()} method. The method should get the field
     * value correctly.
     */
    @Test
    public void test_getLayoutAndSize() {
        assertEquals("Should be null", null, instance.getLayoutAndSize());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setLayoutAndSize(String)} method. The method should set the
     * field value correctly.
     */
    @Test
    public void test_setLayoutAndSize() {
        String value = " newValue";
        instance.setLayoutAndSize(value);
        assertEquals("Should be the same", value, instance.getLayoutAndSize());
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setLayoutAndSize(String)} method. If the argument is null,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setLayoutAndSize_failure1() {
        try {
            instance.setLayoutAndSize(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setLayoutAndSize(String)} method. If the argument is empty,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setLayoutAndSize_failure2() {
        try {
            instance.setLayoutAndSize(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#getId()} method. The method should get the field value
     * correctly.
     */
    @Test
    public void test_getId() {
        assertEquals("Should be 0", 0, instance.getId());
    }

    /**
     * Accuracy test for {@link ProjectStudioSpecification#setId(String)} method. The method should set the field value
     * correctly.
     */
    @Test
    public void test_setId() {
        int value = 2;
        instance.setId(value);
        assertEquals("Should be the same", value, instance.getId());
    }

    /**
     * Failure test for {@link ProjectStudioSpecification#setId(String)} method. If the argument is non positive,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setId_failure1() {
        try {
            instance.setId(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
        try {
            instance.setId(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

}
