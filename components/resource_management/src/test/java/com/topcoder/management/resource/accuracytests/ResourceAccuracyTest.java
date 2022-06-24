/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.management.resource.AuditableResourceStructure;
import com.topcoder.management.resource.IdAlreadySetException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Map;

/**
 * <p>
 * This accuracy tests addresses the functionality provided by the
 * <code>Resource</code> class. It tests the Resource(), Resource(long) and
 * Resource(long, ResourceRole) constructors; getAllProperties(), getId(),
 * getPhase(), getProject(), getProperty(String), getResourceRole(),
 * getSubmission(), hasProperty(String), setId(long), setPhase(Long),
 * setProject(Long), setProperty(String, Object), setResourceRole(ResourceRole)
 * and setSubmission(Long) methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceAccuracyTest extends TestCase {
    /**
     * <p>
     * The id of <code>Resource</code> instance for test.
     * </p>
     */
    private long id = 1;

    /**
     * <p>
     * The instance of <code>ResourceRole</code> for test.
     * </p>
     */
    private ResourceRole role = new ResourceRole(5);

    /**
     * <p>
     * The instance of <code>Resource</code> for test.
     * </p>
     */
    private Resource resource = null;

    /**
     * <p>
     * Test suite of <code>ResourceAccuracyTest</code>.
     * </p>
     * @return Test suite of <code>ResourceAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ResourceAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>Resource()</code> constructor.
     * </p>
     */
    public void testResourceCtor1() {
        resource = new Resource();

        // check null here.
        assertNotNull("Create Resource failed.", resource);

        // check the type here.
        assertTrue("Resource should extend AuditableResourceStructure.",
                resource instanceof AuditableResourceStructure);

        // check the fields here.
        assertEquals("The id should be: -1.", -1, resource.getId());
        assertNull("The resource phase should be: null.", resource.getPhase());
        assertNull("The resource project should be: null.", resource.getProject());
        assertNull("The resource role should be: null.", resource.getResourceRole());
        // assertNull("The resource submission should be: null.",
        // resource.getSubmission());
        assertTrue("The resource submissions should be: submission[].",
                resource.getSubmissions() instanceof Long[]);
        assertEquals("The 'UNSET_ID' should be: -1.", -1, Resource.UNSET_ID);
    }

    /**
     * <p>
     * Accuracy test of <code>Resource(long)</code> constructor.
     * </p>
     */
    public void testResourceCtor2() {
        resource = new Resource(id);

        // check null here.
        assertNotNull("Create Resource failed.", resource);

        // check the type here.
        assertTrue("Resource should extend AuditableResourceStructure.",
                resource instanceof AuditableResourceStructure);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, resource.getId());
        assertNull("The resource phase should be: null.", resource.getPhase());
        assertNull("The resource project should be: null.", resource.getProject());
        assertNull("The resource role should be: null.", resource.getResourceRole());
        // assertNull("The resource submission should be: null.",
        // resource.getSubmission());
        assertTrue("The resource submissions should be: submission[].",
                resource.getSubmissions() instanceof Long[]);
        assertTrue("The resource submissions's size should be: 0.", resource.getSubmissions().length == 0);
        assertEquals("The 'UNSET_ID' should be: -1.", -1, Resource.UNSET_ID);
    }

    /**
     * <p>
     * Accuracy test of <code>Resource(long, ResourceRole)</code> constructor.
     * </p>
     */
    public void testResourceCtor3() {
        resource = new Resource(id, role);

        // check null here.
        assertNotNull("Create Resource failed.", resource);

        // check the type here.
        assertTrue("Resource should extend AuditableResourceStructure.",
                resource instanceof AuditableResourceStructure);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, resource.getId());
        assertNull("The resource phase should be: null.", resource.getPhase());
        assertNull("The resource project should be: null.", resource.getProject());
        assertNotNull("The resource role should not be null.", resource.getResourceRole());
        assertTrue("The resource submissions should be: submission[].",
                resource.getSubmissions() instanceof Long[]);
        assertTrue("The resource submissions's size should be: 0.", resource.getSubmissions().length == 0);
        assertEquals("The 'UNSET_ID' should be: -1.", -1, Resource.UNSET_ID);
    }

    /**
     * <p>
     * Accuracy test of the <code>getAllProperties()</code> method.
     * </p>
     */
    public void testMethod_getAllProperties() {
        resource = new Resource(id);

        Map properties = resource.getAllProperties();
        assertEquals("The properties map should be empty.", 0, properties.size());

        // add some properties here.
        resource.setProperty("name1", "value1");
        resource.setProperty("name2", new Long(2));
        resource.setProperty("name3", new Exception());

        properties = resource.getAllProperties();
        assertEquals("The properties map's size should be: 3.", 3, properties.size());
    }

    /**
     * <p>
     * Accuracy test of the <code>getId()</code> method.
     * </p>
     */
    public void testMethod_getId() {
        resource = new Resource();

        assertEquals("The id should be: -1.", -1, resource.getId());

        // set and check the id here.
        resource.setId(id);
        assertEquals("The id should be: " + id + ".", id, resource.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>setId(long)</code> method.
     * </p>
     */
    public void testMethod_setId() {
        resource = new Resource();

        // check the id here.
        resource.setId(id);
        assertEquals("The id should be: " + id + ".", id, resource.getId());

        // set the id again.
        try {
            resource.setId(100);
            fail("IdAlreadySetException should be thrown here.");
        } catch (IdAlreadySetException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test of the <code>getPhase()</code> method.
     * </p>
     */
    public void testMethod_getPhase() {
        resource = new Resource(id, role);
        assertNull("The resource phase should be: null.", resource.getPhase());

        // set the resource phase here.
        resource.setPhase(new Long(2));
        assertEquals("The phase type should be: 2.", 2, resource.getPhase().longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>setPhase(Long)</code> method.
     * </p>
     */
    public void testMethod_setPhase() {
        resource = new Resource(id);

        // set the resource phase here.
        resource.setPhase(new Long(2));
        assertEquals("The resource phase should be: 2.", 2, resource.getPhase().longValue());

        // set the resource phase again here.
        resource.setPhase(new Long(10));
        assertEquals("The resource phase should be: 10.", 10, resource.getPhase().longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>getProject()</code> method.
     * </p>
     */
    public void testMethod_getProject() {
        resource = new Resource(id, role);
        assertNull("The resource project should be: null.", resource.getProject());

        // set the resource project here.
        resource.setProject(new Long(2));
        assertEquals("The resource project should be: 2.", 2, resource.getProject().longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>setProject(Long)</code> method.
     * </p>
     */
    public void testMethod_setProject() {
        resource = new Resource();

        // set the resource project here.
        resource.setProject(new Long(2));
        assertEquals("The resource project should be: 2.", 2, resource.getProject().longValue());

        // set the resource phase again here.
        resource.setProject(new Long(10));
        assertEquals("The resource project should be: 10.", 10, resource.getProject().longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>getProperty(String)</code> method.
     * </p>
     */
    public void testMethod_getProperty() {
        resource = new Resource(id, role);
        assertNull("This property: 'name1' should be inexistent.", resource.getProperty("name1"));

        // add some properties here.
        resource.setProperty("name1", "value1");
        resource.setProperty("name2", new Long(2));
        resource.setProperty("name3", new Exception());

        // get property and test it.
        assertEquals("This property: 'name1' is incorrect.", "value1", resource.getProperty("name1"));
        assertEquals("This property: 'name2' is incorrect.", 2, ((Long) resource.getProperty("name2"))
                .longValue());
        assertNull("This property: 'name4' should be inexistent.", resource.getProperty("name4"));
    }

    /**
     * <p>
     * Accuracy test of the <code>hasProperty(String)</code> method.
     * </p>
     */
    public void testMethod_hasProperty() {
        resource = new Resource(id, role);
        assertFalse("This property: 'name1' should be inexistent.", resource.hasProperty("name1"));

        // add some properties here.
        resource.setProperty("name1", "value1");
        resource.setProperty("name2", new Long(2));
        resource.setProperty("name3", new Exception());

        assertTrue("This property: 'name1' is incorrect.", resource.hasProperty("name1"));
        assertTrue("This property: 'name2' is incorrect.", resource.hasProperty("name2"));
        assertFalse("This property: 'name4' should be inexistent.", resource.hasProperty("name4"));
    }

    /**
     * <p>
     * Accuracy test of the <code>setProperty(String, Object)</code> method.
     * </p>
     */
    public void testMethod_setProperty() {
        resource = new Resource();

        // add one property here.
        resource.setProperty("name1", "value1");

        // get property and test it.
        assertEquals("The properties map's size should be: 1.", 1, resource.getAllProperties().size());
        assertEquals("This property: 'name1' is incorrect.", "value1", resource.getProperty("name1"));

        // add some properties again here.
        resource.setProperty("name1", "newValue1");
        resource.setProperty("name2", new Long(2));
        resource.setProperty("name3", new Exception());

        // get property and test it.
        assertEquals("The properties map's size should be: 3.", 3, resource.getAllProperties().size());
        assertEquals("This property: 'name1' is incorrect.", "newValue1", resource.getProperty("name1"));
        assertEquals("This property: 'name2' is incorrect.", 2, ((Long) resource.getProperty("name2"))
                .longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>getResourceRole()</code> method.
     * </p>
     */
    public void testMethod_getResourceRole() {
        resource = new Resource();
        assertNull("The resource role should be: null.", resource.getResourceRole());

        // set the resource role here.
        resource.setResourceRole(role);
        assertEquals("This resource role's id should be: 5.", 5, resource.getResourceRole().getId());

        resource = new Resource(id, role);
        assertNotNull("The resource role should not be null.", resource.getResourceRole());
    }

    /**
     * <p>
     * Accuracy test of the <code>setResourceRole(ResourceRole)</code> method.
     * </p>
     */
    public void testMethod_setResourceRole() {
        resource = new Resource();

        // set the resource role here.
        resource.setResourceRole(role);
        assertEquals("This resource role's id should be: 5.", 5, resource.getResourceRole().getId());

        // reset the resource role here.
        resource.setResourceRole(new ResourceRole());
        assertEquals("This resource role's id should be: -1.", -1, resource.getResourceRole().getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>getSubmissions()</code> method.
     * </p>
     */
    public void testMethod_getSubmissions() {
        resource = new Resource();
        assertTrue("The resource submissions's size should be: 0.", resource.getSubmissions().length == 0);

        // set resource submission here.
        Long[] test1 = {new Long(3) };

        resource.setSubmissions(test1);
        assertEquals("The resource phase should be: 3.", 3, resource.getSubmissions()[0].intValue());

        Long[] test2 = {new Long(3), new Long(2), new Long(1) };

        resource.setSubmissions(test2);

        assertEquals("The resource submission length should be: 3.", 3, resource.getSubmissions().length);
    }

    /**
     * <p>
     * Accuracy test of the <code>setSubmissions(Long[])</code> method.
     * </p>
     */
    public void testMethod_setSubmissions() {
        resource = new Resource(id, role);

        // set resource submission here.
        Long[] test1 = {new Long(3) };

        resource.setSubmissions(test1);
        assertEquals("The resource phase should be: 3.", 3, resource.getSubmissions()[0].intValue());

        // reset resource submission here.
        Long[] test2 = {new Long(Long.MAX_VALUE) };

        resource.setSubmissions(test2);
        assertEquals("The resource phase should be: 3.", Long.MAX_VALUE, resource.getSubmissions()[0]
                .longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>addSubmission()</code> method.
     * </p>
     */
    public void testMethod_addSubmission() {
        resource = new Resource(id, role);

        // set resource submission here.
        Long[] test1 = {new Long(3) };

        resource.setSubmissions(test1);
        assertEquals("The resource first submission should be: 3.", 3, resource.getSubmissions()[0]
                .intValue());

        resource.addSubmission(new Long(2));
        assertEquals("The resource submission length should be: 2.", 2, resource.getSubmissions().length);
    }

    /**
     * <p>
     * Accuracy test of the <code>clearSubmission()</code> method.
     * </p>
     */
    public void testMethod_clearSubmissions() {
        resource = new Resource(id, role);

        // set resource submission here.
        Long[] test1 = {new Long(3), new Long(2), new Long(1) };

        resource.setSubmissions(test1);

        resource.clearSubmissions();

        assertTrue("The resource submission length should be: 0.", resource.countSubmissions() == 0);
    }

    /**
     * <p>
     * Accuracy test of the <code>containSubmission()</code> method.
     * </p>
     */
    public void testMethod_containSubmissions() {
        resource = new Resource(id, role);

        // set resource submission here.
        Long[] test1 = {new Long(3), new Long(2), new Long(1) };

        resource.setSubmissions(test1);

        // contain 1,2,3
        assertTrue("The resource submission  not contain 1.", resource.containsSubmission(new Long(1)));
        assertTrue("The resource submission  not contain 2.", resource.containsSubmission(new Long(2)));
        assertTrue("The resource submission  not contain 3.", resource.containsSubmission(new Long(3)));

        assertFalse("The resource submission do not contain 4.", resource.containsSubmission(new Long(4)));
    }

    /**
     * <p>
     * Accuracy test of the <code>countSubmissions()</code> method.
     * </p>
     */
    public void testMethod_countSubmissions() {
        resource = new Resource(id, role);

        // set resource submission here.
        Long[] test1 = {new Long(3), new Long(2), new Long(1) };

        resource.setSubmissions(test1);

        assertTrue("The resource submission count should be: 3.", resource.countSubmissions() == 3);

        resource.addSubmission(new Long(4));
        assertTrue("The resource submission count should be: 4.", resource.countSubmissions() == 4);
    }

    /**
     * <p>
     * Accuracy test of the <code>hasSubmissions()</code> method.
     * </p>
     */
    public void testMethod_hasSubmissions() {
        resource = new Resource(id, role);

        // set resource submission here.
        Long[] test1 = {new Long(3), new Long(2), new Long(1) };

        resource.setSubmissions(test1);

        assertTrue("The resource submission should not be empty.", resource.hasSubmissions());

        resource.clearSubmissions();
        assertFalse("The resource submission should be empty.", resource.hasSubmissions());
    }

    /**
     * <p>
     * Accuracy test of the <code>removeSubmission()</code> method.
     * </p>
     */
    public void testMethod_removeSubmission() {
        resource = new Resource(id, role);

        // set resource submission here.
        Long[] test1 = {new Long(3), new Long(2), new Long(1) };

        resource.setSubmissions(test1);

        // remove 1
        resource.removeSubmission(new Long(1));

        assertFalse("The resource submission should not contain 1.", resource.containsSubmission(new Long(1)));

        // remove all elements
        resource.removeSubmission(new Long(2));
        resource.removeSubmission(new Long(3));
        assertFalse("The resource submission should be empty.", resource.hasSubmissions());
    }
}
