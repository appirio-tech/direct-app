package com.topcoder.service.contest.eligibilityvalidation;

import com.topcoder.service.contest.eligibility.GroupContestEligibility;
import com.topcoder.service.contest.eligibilityvalidation.GroupEligibilityValidator;
import com.topcoder.service.contest.eligibilityvalidation.TestHelper;

import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Stress Tests for {@link GroupEligibilityValidator}.
 * 
 * @author assistant
 * @version 1.0
 */
public class GroupEligibilityValidatorStressTests extends TestCase {

    /**
     * The instance to test.
     */
    private GroupEligibilityValidator validator;

    /**
     * Sets up the environment.
     * 
     * @throws Exception
     *             if anything goes wrong
     */
    public void setUp() throws Exception {
        EntityManager entityManager =
            Persistence.createEntityManagerFactory("persistence-unit").createEntityManager();
        TestHelper.runSQL("drop.sql", entityManager);
        TestHelper.runSQL("setup.sql", entityManager);
        validator = new GroupEligibilityValidator("persistence-unit");
    }

    /**
     * Cleans up the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void tearDown() throws Exception {
        EntityManager entityManager =
            Persistence.createEntityManagerFactory("persistence-unit").createEntityManager();
        TestHelper.runSQL("drop.sql", entityManager);
    }

    /**
     * Test the validation with the same user.
     */
    public void testValidate_1() throws Exception {
        setUp();
        GroupContestEligibility eli = new GroupContestEligibility();
        eli.setGroupId(1);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            assertFalse("Validation failed.", validator.validate(15, eli));
        }
        System.out.println("Run validation for user took " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * Test the validation with the different users.
     */
    public void testValidate_2() {
        GroupContestEligibility eli = new GroupContestEligibility();
        eli.setGroupId(1);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            assertFalse("Validation failed.", validator.validate(5, eli));
        }
        System.out.println("Run validation for users took " + (System.currentTimeMillis() - start) + "ms");
    }
}
