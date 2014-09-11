/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests;

import com.topcoder.management.resource.IdAlreadySetException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

import junit.framework.TestCase;

/**
 * Failure test for {@link Resource} class.
 *
 * @author mayi
 * @author liuliquan
 * @version 1.1
 * @since 1.0
 */
public class ResourceFailureTest extends TestCase {
    /**
     * A <code>Resource</code> instance to test against.
     */
    private Resource resource = null;

    /**
     * Set up.
     * <p>Create the <code>resource</code> instance to test.</p>
     */
    protected void setUp() {
        resource = new Resource();
    }

    /**
     * Test <code>Resource(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_Long_ZeroId() {
        try {
            new Resource(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>Resource(long)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_Long_NegativeId() {
        try {
            new Resource(-1);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>Resource(long, ResourceRole)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongResourceRole_ZeroId() {
        try {
            new Resource(0, new ResourceRole());
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>Resource(long, ResourceRole)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongResourceRole_NegativeId() {
        try {
            new Resource(-1, new ResourceRole());
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>Resource(long, ResourceRole)</code> with null ResourceRole.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongResourceRole_NullName() {
        try {
            new Resource(1L, null);
            fail("Should throw IllegalArgumentException for null ResourceRole.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }


    /**
     * Test <code>setId(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetId_ZeroId() {
        try {
            resource.setId(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setId(long)</code> with negative id after id is set.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetId_NegativeId() {
        resource.setId(1);

        try {
            resource.setId(-1);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setId(long)</code> after id is set.
     * <p>IdAlreadySetException should be thrown.</p>
     */
    public void testSetId_AlreadySet() {
        resource.setId(1);

        try {
            resource.setId(1);
            fail("Should throw IdAlreadySetException for negative id.");
        } catch (IdAlreadySetException e) {
            // pass
        }
    }

    /**
     * Test <code>setProject(long)</code> with zero Project value.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetProject_ZeroId() {
        try {
            resource.setProject(new Long(0));
            fail("Should throw IllegalArgumentException for zero Project.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setProject(long)</code> with negative Project value.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetProject_NegativeId() {
        try {
            resource.setProject(new Long(-1));
            fail("Should throw IllegalArgumentException for negative Project.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setPhase(long)</code> with zero Phase value.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetPhase_ZeroId() {
        try {
            resource.setPhase(new Long(0));
            fail("Should throw IllegalArgumentException for zero Phase.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setPhase(long)</code> with negative Phase value.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetPhase_NegativeId() {
        try {
            resource.setPhase(new Long(-1));
            fail("Should throw IllegalArgumentException for negative Phase.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Set <code>setProperty(String, Object)</code> with null name.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetProperty_NullName() {
        try {
            resource.setProperty(null, new Object());
            fail("Should throw IllegalArgumentException for null name.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Set <code>hasProperty(String)</code> with null name.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testHasProperty_NullName() {
        try {
            resource.hasProperty(null);
            fail("Should throw IllegalArgumentException for null name.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Set <code>getProperty(String)</code> with null name.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testGetProperty_NullName() {
        try {
            resource.getProperty(null);
            fail("Should throw IllegalArgumentException for null name.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }


    /**
     * Failure test for removeSubmission(Long).
     */
    public void testRemoveSubmission_Failure1() {
        Resource resource = new Resource();
        try {
            resource.removeSubmission(null);
            fail("IllegalArgumentException is expected since parameter is null.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Failure test for removeSubmission(Long).
     */
    public void testRemoveSubmission_Failure2() {
        Resource resource = new Resource();
        try {
            resource.removeSubmission(new Long(-1L));
            fail("IllegalArgumentException is expected since parameter is not positive.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Failure test for removeSubmission(Long).
     */
    public void testRemoveSubmission_Failure3() {
        Resource resource = new Resource();
        try {
            resource.removeSubmission(new Long(0L));
            fail("IllegalArgumentException is expected since parameter is not positive.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Failure test for addSubmission(Long).
     *
     * Set it with null argument.
     */
    public void testAddSubmission_Failure1() {
        Resource resource = new Resource();
        try {
            resource.addSubmission(null);
            fail("IllegalArgumentException is expected since parameter is null.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Failure test for addSubmission(Long).
     */
    public void testAddSubmission_Failure2() {
        Resource resource = new Resource();
        try {
            resource.addSubmission(new Long(-1L));
            fail("IllegalArgumentException is expected since parameter is not positive.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Failure test for addSubmission(Long).
     */
    public void testAddSubmission_Failure3() {
        Resource resource = new Resource();
        try {
            resource.addSubmission(new Long(0L));
            fail("IllegalArgumentException is expected since parameter is not positive.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Failure test for method setSubmissions(Long[])
     * Sets it with null.
     */
    public void testSetSubmissions_failure1() {
        Resource resource = new Resource();

        try {
            resource.setSubmissions(null);
            fail("IllegalArgumentException is expected since argument is null");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Failure test for method setSubmissions(Long[])
     *
     * Sets it with null element.
     */
    public void testSetSubmissions_failure2() {
        Resource resource = new Resource();
        Long[] subs = new Long[]{null, new Long(1)};
        try {
            resource.setSubmissions(subs);
            fail("IllegalArgumentException is expected since argument contains null element");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Failure test for method setSubmissions(Long[])
     *
     * Sets it with null element.
     */
    public void testSetSubmissions_failure3() {
        Resource resource = new Resource();
        Long[] subs = new Long[]{new Long(1), new Long(-1L)};
        try {
            resource.setSubmissions(subs);
            fail("IllegalArgumentException is expected since argument contains non positive element");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Failure test for method setSubmissions(Long[])
     *
     * Sets it with null element.
     */
    public void testSetSubmissions_failure4() {
        Resource resource = new Resource();
        Long[] subs = new Long[]{new Long(1), new Long(0L)};
        try {
            resource.setSubmissions(subs);
            fail("IllegalArgumentException is expected since argument contains non positive element");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Failure test for containsSubmission(Long).
     */
    public void testContainsSubmission_Failure1() {
        Resource resource = new Resource();
        try {
            resource.containsSubmission(null);
            fail("IllegalArgumentException is expected since parameter is null.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Failure test for containsSubmission(Long).
     */
    public void testContainsSubmission_Failure2() {
        Resource resource = new Resource();
        try {
            resource.containsSubmission(new Long(-1));
            fail("IllegalArgumentException is expected since parameter is not positive.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Failure test for containsSubmission(Long).
     */
    public void testContainsSubmission_Failure3() {
        Resource resource = new Resource();
        try {
            resource.containsSubmission(new Long(0));
            fail("IllegalArgumentException is expected since parameter is not positive.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }
}
