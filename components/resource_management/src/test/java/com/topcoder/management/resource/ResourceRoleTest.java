/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

/**
 * Unit tests for the class: ResourceRole.
 *
 * @author kinfkong
 * @version 1.0
 */
public class ResourceRoleTest extends TestCase {

    /**
     * An instance of ResourceRole for tests.
     */
    private ResourceRole rr = null;

    /**
     * Sets up the environments.
     */
    protected void setUp() {
        rr = new ResourceRole();
    }

    /**
     * Tests method: ResourceRole(). Checks if the fields are set properly.
     */
    public void testResourceRole() {
        assertNotNull("The instance of ResourceRole cannot be created.", rr);
        // check the fields, they should be set to default
        assertEquals("The id should be unset.", ResourceRole.UNSET_ID, rr.getId());
        assertEquals("The name should be null.", null, rr.getName());
        assertEquals("The phaseType should be null.", null, rr.getPhaseType());
    }

    /**
     * Tests method: ResourceRole(long). With non-positive id, IllegalArgumentException should be thrown.
     */
    public void testResourceRoleLong_NonPositiveId() {
        try {
            rr = new ResourceRole(-1);
            fail("The id should be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: ResourceRole(long). Checks if the fields are properly set.
     */
    public void testResourceRoleLong_accuracy() {
        rr = new ResourceRole(100L);
        assertEquals("The id is not properly set.", 100L, rr.getId());
        assertEquals("The name should be null.", null, rr.getName());
        assertEquals("The phaseType should be null.", null, rr.getPhaseType());
    }

    /**
     * Tests method: ResourceRole(long, String, long). With non-positive id, IllegalArgumentException should be thrown.
     */
    public void testResourceRoleLongStringLong_NonPositiveId() {
        try {
            rr = new ResourceRole(-1, "name", 123);
            fail("The id should be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: ResourceRole(long, String, long). With null name, IllegalArgumentException should be thrown.
     */
    public void testResourceRoleLongStringLong_NullName() {
        try {
            rr = new ResourceRole(1, null, 123);
            fail("The name should be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: ResourceRole(long, String, long). With non-positive phaseType, IllegalArgumentException should be
     * thrown.
     */
    public void testResourceRoleLongStringLong_NonPositivePhaseType() {
        try {
            rr = new ResourceRole(1, "name", -1);
            fail("The phaseType should be positive.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: ResourceRole(long, String, long). Checks if the fields are properly set.
     */
    public void testResourceRoleLongStringLong_accuracy() {
        rr = new ResourceRole(1, "name", 123);
        assertEquals("The id is not set properly.", 1, rr.getId());
        assertEquals("The name is not set properly.", "name", rr.getName());
        assertEquals("The phaseType is not set properly.", 123, rr.getPhaseType().longValue());
    }

    /**
     * Tests ResourceRole.setId(long). With a non-positive value, IllegalArgumentException should be thrown.
     */
    public void testSetId_NonPositive() {
        ResourceRole rr1 = new ResourceRole();
        try {
            rr1.setId(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests ResourceRole.setId(long). Sets the id that already set, IdAlreadySetException should be thrown.
     */
    public void testSetId_ResetId() {
        ResourceRole rr1 = new ResourceRole(1);
        try {
            rr1.setId(2);
            fail("IdAlreadySetException should be thrown.");
        } catch (IdAlreadySetException e) {
            // success
        }
    }

    /**
     * Accuracy tests for the method: setId(long).
     */
    public void testSetId() {
        rr.setId(123L);
        assertEquals("The method does not work properly.", 123L, rr.getId());
    }

    /**
     * Accuracy tests for the method: getId(long).
     */
    public void testGetId() {
        rr.setId(123L);
        assertEquals("The method does not work properly.", 123L, rr.getId());
    }

    /**
     * Tests method setDescription(String). With parameter is null.
     */
    public void testSetDescription_Null() {
        ResourceRole rr1 = new ResourceRole();
        rr1.setDescription(null);
        assertEquals("The method does not work properly when the parameter is null.", null, rr1.getDescription());
    }

    /**
     * Tests method setDescription(String). With parameter is not null.
     */
    public void testSetDescription_NotNull() {
        ResourceRole rr1 = new ResourceRole();
        rr1.setDescription("Description");
        assertEquals("The method does not work properly when the parameter is null.", "Description", rr1
                .getDescription());
    }

    /**
     * Tests method getDescription(String). With parameter is null.
     */
    public void testGetDescription_Null() {
        ResourceRole rr1 = new ResourceRole();
        rr1.setDescription(null);
        assertEquals("The method does not work properly when the parameter is null.", null, rr1.getDescription());
    }

    /**
     * Tests method getDescription(String). With parameter is not null.
     */
    public void testGetDescription_NotNull() {
        ResourceRole rr1 = new ResourceRole();
        rr1.setDescription("Description");
        assertEquals("The method does not work properly when the parameter is null.", "Description", rr1
                .getDescription());
    }

    /**
     * Tests method setName(String). With parameter is null.
     */
    public void testSetName_Null() {
        ResourceRole rr1 = new ResourceRole();
        rr1.setName(null);
        assertEquals("The method does not work properly when the parameter is null.", null, rr1.getName());
    }

    /**
     * Tests method setName(String). With parameter is not null.
     */
    public void testSetName_NotNull() {
        ResourceRole rr1 = new ResourceRole();
        rr1.setName("Name");
        assertEquals("The method does not work properly when the parameter is null.", "Name", rr1.getName());
    }

    /**
     * Tests method getName(String). With parameter is null.
     */
    public void testGetName_Null() {
        ResourceRole rr1 = new ResourceRole();
        rr1.setName(null);
        assertEquals("The method does not work properly when the parameter is null.", null, rr1.getName());
    }

    /**
     * Tests method getName(String). With parameter is not null.
     */
    public void testGetName_NotNull() {
        ResourceRole rr1 = new ResourceRole();
        rr1.setName("Name");
        assertEquals("The method does not work properly when the parameter is null.", "Name", rr1.getName());
    }

    /**
     * Tests method setPhaseTypeType(Long). Sets it with null.
     */
    public void testSetPhaseTypeType_Null() {
        ResourceRole rr1 = new ResourceRole();
        rr1.setPhaseType(null);
        assertEquals("The method does not work properly.", null, rr.getPhaseType());
    }

    /**
     * Tests method setPhaseType(Long). Sets it with non-positive value, IllegalArgumentException should be thrown.
     */
    public void testSetPhaseType_NonPositive() {
        ResourceRole rr1 = new ResourceRole();
        try {
            rr1.setPhaseType(new Long(-1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method setPhaseType(Long). Sets it with a normal value.
     */
    public void testSetPhaseType_Normal() {
        ResourceRole rr1 = new ResourceRole();
        rr1.setPhaseType(new Long(3));
        assertEquals("The method does not work properly.", new Long(3), rr1.getPhaseType());
    }

    /**
     * Tests method getPhaseType(Long). Sets it with a normal value.
     */
    public void testGetPhaseType_Normal() {
        ResourceRole rr1 = new ResourceRole();
        rr1.setPhaseType(new Long(3));
        assertEquals("The method does not work properly.", new Long(3), rr1.getPhaseType());
    }

    /**
     * Tests method getPhaseType(Long). Sets it with null.
     */
    public void testGetPhaseType_Null() {
        ResourceRole rr1 = new ResourceRole();
        rr1.setPhaseType(null);
        assertEquals("The method does not work properly.", null, rr.getPhaseType());
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
        ResourceRole role = new ResourceRole(1);

        // Marshalling
        JAXBContext jaxbContext = JAXBContext.newInstance(AuditableResourceStructure.class, ResourceRole.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(role, os);

        // Unmarshalling
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ResourceRole unmarshalledRole = (ResourceRole) unmarshaller.unmarshal(new ByteArrayInputStream(os.toString()
                .getBytes()));

        // verification.
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
        ResourceRole role = new ResourceRole();
        role.setId(1);
        role.setName("UnitTest");
        role.setDescription("Desc");
        role.setCreationUser("Ivern");
        role.setCreationTimestamp(new Date());
        role.setModificationUser("FireIce");
        role.setModificationTimestamp(new Date());
        role.setPhaseType(2L);

        // Marshalling
        JAXBContext jaxbContext = JAXBContext.newInstance(AuditableResourceStructure.class, ResourceRole.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(role, os);

        // Unmarshalling
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ResourceRole unmarshalledRole = (ResourceRole) unmarshaller.unmarshal(new ByteArrayInputStream(os.toString()
                .getBytes()));

        // verification.
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
