/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

/**
 * Unit tests for the class: Resource.
 *
 * @author kinfkong, Xuchen
 * @version 1.1
 * @since 1.0
 */
public class ResourceTest extends TestCase {

    /**
     * The Resource instance used for testing.
     */
    private Resource resource = null;

    /**
     * Set up testing environment.
     */
    protected void setUp() {
        resource = new Resource();
    }

    /**
     * Tests constructor: Resource(). Checks if the fields are properly set.
     */
    public void testResource() {
        resource = new Resource();
        // the instance can be created
        assertNotNull("The instance cannot be created.", resource);
        // check the fields
        assertEquals("The id is not set properly.", Resource.UNSET_ID, resource.getId());
        assertEquals("The resourceRole is not set properly.", null, resource.getResourceRole());
    }

    /**
     * Tests constructor Resource(long). Tests with non-positive id value, IllegalArgumentException should be thrown.
     */
    public void testResourceLong_NonPositvieId() {
        try {
            new Resource(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor Resource(long). Checks if the fields are properly set.
     */
    public void testResourceLong_accuracy() {
        resource = new Resource(10);
        // the instance can be created
        assertNotNull("The instance cannot be created.", resource);
        // check the fields
        assertEquals("The id is not set properly.", 10, resource.getId());
        assertEquals("The resourceRole is not set properly.", null, resource.getResourceRole());
    }

    /**
     * Tests constructor Resource(long, ResourceRole). With non-positive id value, IllegalArgumentException should be
     * thrown.
     */
    public void testResourceLongResourceRole_NonPositiveId() {
        try {
            new Resource(-1, new ResourceRole());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor Resource(long, ResourceRole). With null resouceRole, IllegalArgumentException should be thrown.
     */
    public void testResourceLongResourceRole_NullResourceRole() {
        try {
            new Resource(1, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests constructor Resource(long, ResourceRole). Checks if the fields are properly set.
     */
    public void testResourceLongResourceRole_accuracy() {
        ResourceRole resourceRole = new ResourceRole();
        resource = new Resource(10, resourceRole);
        // the instance can be created
        assertNotNull("The instance cannot be created.", resource);
        // check the fields
        assertEquals("The id is not set properly.", 10, resource.getId());
        assertEquals("The resourceRole is not set properly.", resourceRole, resource.getResourceRole());
    }

    /**
     * Tests method: setId(long). With non-positive id, IllegalArgumentException should be thrown.
     */
    public void testSetId_NonPositive() {
        try {
            resource.setId(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: setId(long). Reset an already set id, IdAlreadySetException should be thrown.
     */
    public void testSetId_ResetId() {
        resource = new Resource(1);
        try {
            resource.setId(31);
            fail("IdAlreadySetException should be thrown.");
        } catch (IdAlreadySetException e) {
            // success
        }
    }

    /**
     * Tests method: setId(long). Checks if the id is properly set.
     */
    public void testSetId_accuracy() {
        resource.setId(1);
        assertEquals("The id is not properly set.", 1, resource.getId());
    }

    /**
     * Tests method: getId(). Checks if the id is properly returned.
     */
    public void testGetId() {
        resource = new Resource(2);
        assertEquals("The id is not properly returned.", 2, resource.getId());
    }

    /**
     * Tests method: setResourceRole(ResourceRole). Sets with null.
     */
    public void testSetResourceRole_Null() {
        resource.setResourceRole(null);
        assertEquals("The method does not work properly.", null, resource.getResourceRole());
    }

    /**
     * Tests method: setResourceRole(ResourceRole). Sets with non-null value.
     */
    public void testSetResourceRole_NotNull() {
        ResourceRole rr = new ResourceRole();
        resource.setResourceRole(rr);
        assertEquals("The method does not work properly.", rr, resource.getResourceRole());
    }

    /**
     * Tests method: getResourceRole(ResourceRole). Sets with null.
     */
    public void testGetResourceRole_Null() {
        resource.setResourceRole(null);
        assertEquals("The method does not work properly.", null, resource.getResourceRole());
    }

    /**
     * Tests method: getResourceRole(ResourceRole). Sets with non-null value.
     */
    public void testGetResourceRole_NotNull() {
        ResourceRole rr = new ResourceRole();
        resource.setResourceRole(rr);
        assertEquals("The method does not work properly.", rr, resource.getResourceRole());
    }

    /**
     * Tests method setProject(Long). Sets it with null.
     */
    public void testSetProject_Null() {
        resource.setProject(null);
        assertEquals("The method does not work properly.", null, resource.getProject());
    }

    /**
     * Tests method setProject(Long). Sets it with non-positive value, IllegalArgumentException should be thrown.
     */
    public void testSetProject_NonPositive() {
        try {
            resource.setProject(new Long(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method setProject(Long). Sets it with a normal value.
     */
    public void testSetProject_Normal() {
        resource.setProject(new Long(3));
        assertEquals("The method does not work properly.", new Long(3), resource.getProject());
    }

    /**
     * Tests method getProject(Long). Sets it with a normal value.
     */
    public void testGetProject_Normal() {
        resource.setProject(new Long(3));
        assertEquals("The method does not work properly.", new Long(3), resource.getProject());
    }

    /**
     * Tests method getProject(Long). Sets it with null.
     */
    public void testGetProject_Null() {
        resource.setProject(null);
        assertEquals("The method does not work properly.", null, resource.getProject());
    }

    /**
     * Tests method setPhase(Long). Sets it with null.
     */
    public void testSetPhase_Null() {
        resource.setPhase(null);
        assertEquals("The method does not work properly.", null, resource.getPhase());
    }

    /**
     * Tests method setPhase(Long). Sets it with non-positive value, IllegalArgumentException should be thrown.
     */
    public void testSetPhase_NonPositive() {
        try {
            resource.setPhase(new Long(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method setPhase(Long). Sets it with a normal value.
     */
    public void testSetPhase_Normal() {
        resource.setPhase(new Long(3));
        assertEquals("The method does not work properly.", new Long(3), resource.getPhase());
    }

    /**
     * Tests method getPhase(Long). Sets it with a normal value.
     */
    public void testGetPhase_Normal() {
        resource.setPhase(new Long(3));
        assertEquals("The method does not work properly.", new Long(3), resource.getPhase());
    }

    /**
     * Tests method getPhase(Long). Sets it with null.
     */
    public void testGetPhase_Null() {
        resource.setPhase(null);
        assertEquals("The method does not work properly.", null, resource.getPhase());
    }

    /**
     * Tests method: setProperty(String, Object). With null name, IllegalArgumentException should be thrown.
     */
    public void testSetProperty_NullName() {
        try {
            resource.setProperty(null, "value 1");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: setProperty(String, object). Checks if the property is properly set.
     */
    public void testSetProperty_Accuracy() {
        resource.setProperty("name 1", "value 1");
        assertEquals("The property is not set properly.", "value 1", resource.getProperty("name 1"));
    }

    /**
     * Tests method: setProperty(String, Object). Set the value with null.
     *
     * @since 1.1
     */
    public void testSetProperty_NullValue() {
        resource.setProperty("name 1", null);
        assertNull("The property is not set properly.", resource.getProperty("name 1"));
    }

    /**
     * Tests method: hasProperty(String). Tests it with null name, IllegalArgumentException should be thrown.
     */
    public void testHasProperty_NullName() {
        try {
            resource.hasProperty(null);
            fail("The parameter name cannot be null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: hasProperty(String). Tests it with a name that does not contain in the properties, false is
     * expected.
     */
    public void testHasProperty_NotExist() {
        resource.setProperty("name 1", "value 1");
        resource.setProperty("name 2", "value 2");
        assertFalse("The property should not exist.", resource.hasProperty("NotExist"));
    }

    /**
     * Tests method: hasProperty(String). Tests it with a name that contains in the properties, true is expected.
     */
    public void testHasProperty_Exist() {
        resource.setProperty("name 1", "value 1");
        resource.setProperty("name 2", "value 2");
        assertTrue("The property should exist.", resource.hasProperty("name 1"));
    }

    /**
     * Tests method: getProperty(String). With a name that does not exist in the properties, null should be returned.
     */
    public void testGetProperty_NotExist() {
        resource.setProperty("name 1", "value 1");
        resource.setProperty("name 2", "value 2");
        assertNull("The property should not exist, null should be returned.", resource.getProperty("name 3"));
    }

    /**
     * Tests method: getProperty(String). With a name that exists in the properties, null should be returned.
     */
    public void testGetProperty_Exist() {
        resource.setProperty("name 1", "value 1");
        resource.setProperty("name 2", "value 2");
        assertEquals("The property should exist, the value should be returned.", "value 1", resource
                .getProperty("name 1"));
    }

    /**
     * Tests method: getAllProperties(). With no properties, empty should be expected.
     */
    public void testGetAllProperties_empty() {
        Map map = resource.getAllProperties();
        assertEquals("The map should be empty.", 0, map.size());
    }

    /**
     * Tests method: getAllProperties(). With three properties, check if the returned map is correct.
     */
    public void testGetAllProperties() {
        resource.setProperty("name 1", "value 1");
        resource.setProperty("name 2", "value 2");
        resource.setProperty("name 3", "value 3");
        Map map = resource.getAllProperties();
        assertEquals("The map should contain 3 elements.", 3, map.size());
        assertEquals("The value is incorrect.", "value 1", map.get("name 1"));
        assertEquals("The value is incorrect.", "value 2", map.get("name 2"));
        assertEquals("The value is incorrect.", "value 3", map.get("name 3"));
    }

    /**
     * Tests method: getAllProperties(). Change the data in the returned map, check if the properties underlying the
     * class changed.
     */
    public void testGetAllProperties_copy() {
        resource.setProperty("name 1", "value 1");
        resource.setProperty("name 2", "value 2");
        resource.setProperty("name 3", "value 3");
        Map map = resource.getAllProperties();
        // change the map
        map.put("name 1", "new value 1");
        // the Adas still not changed
        map = resource.getAllProperties();
        assertEquals("The map should contain 3 elements.", 3, map.size());
        assertEquals("The value is incorrect.", "value 1", map.get("name 1"));
        assertEquals("The value is incorrect.", "value 2", map.get("name 2"));
        assertEquals("The value is incorrect.", "value 3", map.get("name 3"));
    }

    /**
     * Test setSubmissions method with null argument. It should throw IllegalArgumentException.
     *
     * @since 1.1
     */
    public void testSetSubmissions_Null() {
        try {
            resource.setSubmissions(null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test setSubmissions method with array which contains null element. It should throw IllegalArgumentException.
     *
     * @since 1.1
     */
    public void testSetSubmissions_NullElement() {
        try {
            resource.setSubmissions(new Long[] {new Long(1), null});
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test setSubmissions method with array which contains non-positive element. It should throw
     * IllegalArgumentException.
     *
     * @since 1.1
     */
    public void testSetSubmissions_NonPositiveElement() {
        try {
            resource.setSubmissions(new Long[] {new Long(1), new Long(-1)});
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test setSubmissions method with proper argument which does not contain duplicated elements.
     *
     * @since 1.1
     */
    public void testSetSubmissions_Accuracy1() {
        resource.setSubmissions(new Long[] {new Long(1), new Long(2)});

        Long[] submissions = resource.getSubmissions();
        assertEquals("The number of submissions is not expected.", 2, submissions.length);
        assertTrue("The id 1 should exist.", submissions[0].equals(new Long(1)) || submissions[1].equals(new Long(1)));
        assertTrue("The id 2 should exist.", submissions[0].equals(new Long(2)) || submissions[1].equals(new Long(2)));
    }

    /**
     * Test setSubmissions method with proper argument which contains duplicated elements.
     *
     * @since 1.1
     */
    public void testSetSubmissions_Accuracy2() {
        resource.setSubmissions(new Long[] {new Long(1), new Long(2), new Long(1)});

        Long[] submissions = resource.getSubmissions();
        assertEquals("The number of submissions is not expected.", 2, submissions.length);
        assertTrue("The id 1 should exist.", submissions[0].equals(new Long(1)) || submissions[1].equals(new Long(1)));
        assertTrue("The id 2 should exist.", submissions[0].equals(new Long(2)) || submissions[1].equals(new Long(2)));
    }

    /**
     * Test setSubmissions method with proper argument in the following case: the submissions are set before, and it
     * tries to set with different submissions.
     *
     * @since 1.1
     */
    public void testSetSubmissions_Accuracy3() {
        resource.setSubmissions(new Long[] {new Long(1), new Long(2)});

        Long[] submissions = resource.getSubmissions();
        assertEquals("The number of submissions is not expected.", 2, submissions.length);
        assertTrue("The id 1 should exist.", submissions[0].equals(new Long(1)) || submissions[1].equals(new Long(1)));
        assertTrue("The id 2 should exist.", submissions[0].equals(new Long(2)) || submissions[1].equals(new Long(2)));

        resource.setSubmissions(new Long[] {new Long(3)});

        submissions = resource.getSubmissions();
        assertEquals("The number of submissions is not expected.", 1, submissions.length);
        assertTrue("The id: 3 should exist.", submissions[0].equals(new Long(3)));
    }

    /**
     * Test getSubmissions method accuracy when no submissions are set yet.
     *
     * @since 1.1
     */
    public void testGetSubmissions_Accuracy1() {
        Long[] submissions = resource.getSubmissions();

        assertEquals("The number of submissions is not expected.", 0, submissions.length);
    }

    /**
     * Test getSubmissions method accuracy when some submissions are set.
     *
     * @since 1.1
     */
    public void testGetSubmissions_Accuracy2() {
        resource.setSubmissions(new Long[] {new Long(1), new Long(2)});

        Long[] submissions = resource.getSubmissions();
        assertEquals("The number of submissions is not expected.", 2, submissions.length);
        assertTrue("The id 1 should exist.", submissions[0].equals(new Long(1)) || submissions[1].equals(new Long(1)));
        assertTrue("The id 2 should exist.", submissions[0].equals(new Long(2)) || submissions[1].equals(new Long(2)));
    }

    /**
     * Test addSubmission method with null argument. It should throw IllegalArgumentException.
     *
     * @since 1.1
     */
    public void testAddSubmission_Null() {
        try {
            resource.addSubmission(null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test addSubmission method with non-positive argument. It should throw IllegalArgumentException.
     *
     * @since 1.1
     */
    public void testAddSubmission_NonPositive() {
        try {
            resource.addSubmission(new Long(-1));
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test addSubmission method with proper argument.
     *
     * @since 1.1
     */
    public void testAddSubmission_Accuracy() {
        resource.addSubmission(new Long(1));

        assertTrue("The submission is not added successfully.", resource.containsSubmission(new Long(1)));
    }

    /**
     * Test removeSubmission method with null argument. It should throw IllegalArgumentException.
     *
     * @since 1.1
     */
    public void testRemoveSubmission_Null() {
        try {
            resource.removeSubmission(null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test removeSubmission method with non-positive argument. It should throw IllegalArgumentException.
     *
     * @since 1.1
     */
    public void testRemoveSubmission_NonPositive() {
        try {
            resource.removeSubmission(new Long(-1));
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test removeSubmission method with proper argument.
     *
     * @since 1.1
     */
    public void testRemoveSubmission_Accuracy() {
        resource.addSubmission(new Long(1));
        assertTrue("The submission is not added successfully.", resource.containsSubmission(new Long(1)));

        resource.removeSubmission(new Long(1));
        assertFalse("The submission is not removed successfully.", resource.containsSubmission(new Long(1)));
    }

    /**
     * Test removeSubmission method with submission id which is not associated with the resource. It should not throw
     * any exception.
     *
     * @since 1.1
     */
    public void testRemoveSubmission_NonExist() {
        resource.removeSubmission(new Long(100));
    }

    /**
     * Test containsSubmission method with null argument. It should throw IllegalArgumentException.
     *
     * @since 1.1
     */
    public void testContainsSubmission_Null() {
        try {
            resource.containsSubmission(null);
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test containsSubmission method with non-positive argument. It should throw IllegalArgumentException.
     *
     * @since 1.1
     */
    public void testContainsSubmission_NonPositive() {
        try {
            resource.containsSubmission(new Long(-1));
            fail("It should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Test containsSubmission method with submission id that is associated with the resource.
     *
     * @since 1.1
     */
    public void testContainsSubmission_Accuracy1() {
        resource.addSubmission(new Long(1));
        assertTrue("The returned value is not expected.", resource.containsSubmission(new Long(1)));
    }

    /**
     * Test containsSubmission method with submission id that is not associated with the resource.
     *
     * @since 1.1
     */
    public void testContainsSubmission_Accuracy2() {
        assertFalse("The returned value is not expected.", resource.containsSubmission(new Long(1)));
    }

    /**
     * Test hasSubmissions method accuracy when there is some submission associated with the resource.
     *
     * @since 1.1
     */
    public void testHasSubmissions_Accuracy1() {
        resource.addSubmission(new Long(1));

        assertTrue("The returned value is not expected.", resource.hasSubmissions());
    }

    /**
     * Test hasSubmissions method accuracy when there is not any submission associated with the resource.
     *
     * @since 1.1
     */
    public void testHasSubmissions_Accuracy2() {
        assertFalse("The returned value is not expected.", resource.hasSubmissions());
    }

    /**
     * Test countSubmissions method accuracy when there is some submission associated with the resource.
     *
     * @since 1.1
     */
    public void testCountSubmissions_Accuracy1() {
        resource.addSubmission(new Long(1));
        resource.addSubmission(new Long(2));

        assertEquals("The returned value is not expected.", 2, resource.countSubmissions());
    }

    /**
     * Test countSubmissions method accuracy when there is not any submission associated with the resource.
     *
     * @since 1.1
     */
    public void testCountSubmissions_Accuracy2() {
        assertEquals("The returned value is not expected.", 0, resource.countSubmissions());
    }

    /**
     * Test clearSubmissions method accuracy when there is some submission associated with the resource.
     *
     * @since 1.1
     */
    public void testClearSubmissions_Accuracy() {
        resource.addSubmission(new Long(1));
        resource.addSubmission(new Long(2));

        assertEquals("The submissions are not added successfully.", 2, resource.countSubmissions());

        // clear all submissions
        resource.clearSubmissions();
        assertEquals("The submissions are not cleared successfully.", 0, resource.countSubmissions());
    }

    /**
     * Test clearSubmissions method when there is not any submission associated with the resource. It should not throw
     * any exception.
     *
     * @since 1.1
     */
    public void testClearSubmissions_NonExist() {
        resource.clearSubmissions();
    }

    /**
     * <p>
     * Tests the functionality of XML Serialization of JAXB.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testMarshalAndUnmarshal() throws Exception {
        // create the entity object to serialize
        Resource resource = new Resource();
        resource.setId(1);
        resource.setCreationUser("Ivern");
        resource.setCreationTimestamp(new Date());
        resource.setModificationUser("FireIce");
        resource.setModificationTimestamp(new Date());

        // Marshalling
        JAXBContext jaxbContext = JAXBContext.newInstance(AuditableResourceStructure.class, ResourceRole.class,
                Resource.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(resource, os);

        // Unmarshalling
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Resource unmarshalledResource = (Resource) unmarshaller.unmarshal(new ByteArrayInputStream(os.toString()
                .getBytes()));

        // verification.
        assertNotNull("The object is not unmarshalled.", unmarshalledResource);
        assertEquals("Incorrect id.", resource.getId(), unmarshalledResource.getId());
        assertEquals("Incorrect CreationUser.", resource.getCreationUser(), unmarshalledResource.getCreationUser());
        assertEquals("Incorrect Creation Timestamp.", resource.getCreationTimestamp(), unmarshalledResource
                .getCreationTimestamp());
        assertEquals("Incorrect ModificationUser.", resource.getModificationUser(), unmarshalledResource
                .getModificationUser());
        assertEquals("Incorrect ModificationTimestamp.", resource.getModificationTimestamp(), unmarshalledResource
                .getModificationTimestamp());
    }

    /**
     * <p>
     * Tests the functionality of XML Serialization of JAXB.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testMarshalAndUnmarshal2() throws Exception {
        // create the entity object to serialize
        Resource resource = new Resource();
        resource.setId(1);
        resource.setCreationUser("Ivern");
        resource.setCreationTimestamp(new Date());
        resource.setModificationUser("FireIce");
        resource.setModificationTimestamp(new Date());

        resource.setPhase(2L);
        resource.setProject(3L);

        ResourceRole role = new ResourceRole();
        role.setId(1);
        role.setName("UnitTest");
        role.setDescription("Desc");
        role.setPhaseType(2L);

        resource.setResourceRole(role);

        resource.addSubmission(1L);
        resource.addSubmission(2L);

        resource.setProperty("Key1", "Value1");
        resource.setProperty("Key2", "Value2");

        // Marshalling
        JAXBContext jaxbContext = JAXBContext.newInstance(AuditableResourceStructure.class, ResourceRole.class,
                Resource.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(resource, os);

        // Unmarshalling
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Resource unmarshalledResource = (Resource) unmarshaller.unmarshal(new ByteArrayInputStream(os.toString()
                .getBytes()));

        // verification.
        assertNotNull("The object is not unmarshalled.", unmarshalledResource);
        assertEquals("Incorrect id.", resource.getId(), unmarshalledResource.getId());
        assertEquals("Incorrect Phase.", resource.getPhase(), unmarshalledResource.getPhase());
        assertEquals("Incorrect Project.", resource.getProject(), unmarshalledResource.getProject());
        assertEquals("Incorrect CreationUser.", resource.getCreationUser(), unmarshalledResource.getCreationUser());
        assertEquals("Incorrect Creation Timestamp.", resource.getCreationTimestamp(), unmarshalledResource
                .getCreationTimestamp());
        assertEquals("Incorrect ModificationUser.", resource.getModificationUser(), unmarshalledResource
                .getModificationUser());
        assertEquals("Incorrect ModificationTimestamp.", resource.getModificationTimestamp(), unmarshalledResource
                .getModificationTimestamp());
        assertEquals("Incorrect submissions length.", 2, unmarshalledResource.getSubmissions().length);
        Set<Long> submissions = new HashSet<Long>();
        submissions.add(1L);
        submissions.add(2L);
        for (Long submission : unmarshalledResource.getSubmissions()) {
            assertTrue("Incorrect submission id.", submissions.remove(submission));
        }
        assertEquals("Incorrect property", "Value1", unmarshalledResource.getProperty("Key1"));
        assertEquals("Incorrect property", "Value2", unmarshalledResource.getProperty("Key2"));

        // verify resource role.
        ResourceRole unmarshalledRole = unmarshalledResource.getResourceRole();
        assertNotNull("The object is not unmarshalled.", unmarshalledRole);
        assertEquals("Incorrect id.", role.getId(), unmarshalledRole.getId());
        assertEquals("Incorrect Name.", role.getName(), unmarshalledRole.getName());
        assertEquals("Incorrect Description.", role.getDescription(), unmarshalledRole.getDescription());
        assertEquals("Incorrect PhaseType.", role.getPhaseType(), unmarshalledRole.getPhaseType());
        assertEquals("Incorrect CreationUser.", role.getCreationUser(), unmarshalledRole.getCreationUser());
        assertEquals("Incorrect Creation Timestamp.", role.getCreationTimestamp(), unmarshalledRole
                .getCreationTimestamp());
        assertEquals("Incorrect ModificationUser.", role.getModificationUser(), unmarshalledRole.getModificationUser());
        assertEquals("Incorrect ModificationTimestamp.", role.getModificationTimestamp(), unmarshalledRole
                .getModificationTimestamp());
    }
}
