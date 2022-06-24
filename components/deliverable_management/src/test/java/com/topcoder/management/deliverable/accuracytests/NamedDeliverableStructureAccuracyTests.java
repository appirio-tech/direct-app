/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.NamedDeliverableStructure;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Accuracy test cases for <code>NamedDeliverableStructure</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class NamedDeliverableStructureAccuracyTests extends TestCase {
    /** The <code>NamedDeliverableStructure</code> instance to be test. */
    private NamedDeliverableStructure structure = null;

    /**
     * Sets up the test environment. New <code>NamedDeliverableStructure</code> instance is created with default id and
     * default name. Fields in super class are set to valid values.
     */
    protected void setUp() {
        structure = new EmptyDeliverableStructure();
        structure.setCreationUser("create");
        structure.setCreationTimestamp(new Date());
        structure.setModificationUser("modify");
        structure.setModificationTimestamp(new Date());
    }

    /**
     * Tests default constructor.
     */
    public void testDefaultConstructor() {
        structure = new EmptyDeliverableStructure();
        assertNotNull("Unable to instantiate NamedDeliverableStructure", structure);
        assertEquals("Id not set correctly", NamedDeliverableStructure.UNSET_ID, structure.getId());
        assertEquals("Creation user not set correctly", null, structure.getCreationUser());
        assertEquals("Creation timestamp not set correctly", null, structure.getCreationTimestamp());
        assertEquals("Modification user not set correctly", null, structure.getModificationUser());
        assertEquals("Modification timestamp not set correctly", null, structure.getModificationTimestamp());
        assertEquals("Name not set correctly", null, structure.getName());
        assertEquals("Description not set correctly", null, structure.getDescription());
    }

    /**
     * Tests constructor with id.
     */
    public void testDefaultConstructorWithId() {
        long id = 1024;
        structure = new EmptyDeliverableStructure(id);
        assertNotNull("Unable to instantiate NamedDeliverableStructure", structure);
        assertEquals("Id not set correctly", id, structure.getId());
        assertEquals("Creation user not set correctly", null, structure.getCreationUser());
        assertEquals("Creation timestamp not set correctly", null, structure.getCreationTimestamp());
        assertEquals("Modification user not set correctly", null, structure.getModificationUser());
        assertEquals("Modification timestamp not set correctly", null, structure.getModificationTimestamp());
        assertEquals("Name not set correctly", null, structure.getName());
        assertEquals("Description not set correctly", null, structure.getDescription());
    }

    /**
     * Tests constructor with id and name.
     */
    public void testDefaultConstructorWithIdAndName() {
        long id = 1024;
        String name = "good boy";
        structure = new EmptyDeliverableStructure(id, name);
        assertNotNull("Unable to instantiate NamedDeliverableStructure", structure);
        assertEquals("Id not set correctly", id, structure.getId());
        assertEquals("Creation user not set correctly", null, structure.getCreationUser());
        assertEquals("Creation timestamp not set correctly", null, structure.getCreationTimestamp());
        assertEquals("Modification user not set correctly", null, structure.getModificationUser());
        assertEquals("Modification timestamp not set correctly", null, structure.getModificationTimestamp());
        assertEquals("Name not set correctly", name, structure.getName());
        assertEquals("Description not set correctly", null, structure.getDescription());
    }

    /**
     * Tests constructor with id and empty name.
     */
    public void testDefaultConstructorWithIdAndEmptyName() {
        long id = 1024;
        structure = new EmptyDeliverableStructure(id, "");
        assertNotNull("Unable to instantiate NamedDeliverableStructure", structure);
        assertEquals("Id not set correctly", id, structure.getId());
        assertEquals("Creation user not set correctly", null, structure.getCreationUser());
        assertEquals("Creation timestamp not set correctly", null, structure.getCreationTimestamp());
        assertEquals("Modification user not set correctly", null, structure.getModificationUser());
        assertEquals("Modification timestamp not set correctly", null, structure.getModificationTimestamp());
        assertEquals("Name not set correctly", "", structure.getName());
        assertEquals("Description not set correctly", null, structure.getDescription());
    }

    /**
     * Tests setName and getName methods.
     */
    public void testSetAndGetName() {
        String name = "new name";
        structure.setName(name);
        assertEquals("SetName or getName not functions correctly", name, structure.getName());
        structure.setName(null);
        assertEquals("SetName or getName not functions correctly", null, structure.getName());
        structure.setName("");
        assertEquals("SetName or getName not functions correctly", "", structure.getName());
    }

    /**
     * Tests setDescription and getDescription methods.
     */
    public void testSetAndGetDescription() {
        String description = "This is a description, en";
        structure.setDescription(description);
        assertEquals("SetDescription or getDescription not functions correctly", description, structure.getDescription());
        structure.setDescription(null);
        assertEquals("SetDescription or getDescription not functions correctly", null, structure.getDescription());
        structure.setDescription("");
        assertEquals("SetDescription or getDescription not functions correctly", "", structure.getDescription());
    }

    /**
     * Tests isValidToPersist method with null name.
     */
    public void testIsValidToPersistNullName() {
        structure.setId(256);
        structure.setDescription("description");
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            ((EmptyDeliverableStructure) structure).isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with null description.
     */
    public void testIsValidToPersistNullDescription() {
        structure.setId(256);
        structure.setName("name");
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            ((EmptyDeliverableStructure) structure).isValidToPersist());
    }

    /**
     * Tests isValidToPersist method with unset id.
     */
    public void testIsValidToPersistUnsetId() {
        structure.setName("name");
        structure.setDescription("description");
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            ((EmptyDeliverableStructure) structure).isValidToPersist());
    }

    /**
     * Tests isValidToPersist method when super.isValidToPersist() returns false.
     */
    public void testIsValidToPersistInvalidBase() {
        structure.setId(256);
        structure.setName("name");
        structure.setDescription("description");
        structure.setCreationTimestamp(null);
        assertEquals("IsValidToPersist not functions correctly, false should be returned.", false,
            ((EmptyDeliverableStructure) structure).isValidToPersist());
    }

    /**
     * Tests isValidToPersist method when the structure is valid. True should be returned.
     */
    public void testIsValidToPersistValid() {
        structure.setId(256);
        structure.setName("name");
        structure.setDescription("description");
        assertEquals("IsValidToPersist not functions correctly, true should be returned.", true,
            ((EmptyDeliverableStructure) structure).isValidToPersist());
    }

    /**
     * This class extends <code>NamedDeliverableStructure</code>, making it possible to instantiate
     * <code>NamedDeliverableStructure</code> and test it.
     */
    private class EmptyDeliverableStructure extends NamedDeliverableStructure {
        /**
         * Default constructor. Simply call super().
         */
        public EmptyDeliverableStructure() {
            super();
        }

        /**
         * This constructor simply call super(id).
         *
         * @param id the id of this structure.
         */
        public EmptyDeliverableStructure(long id) {
            super(id);
        }

        /**
         * This constructor simply call super(id, name).
         *
         * @param id the id of this structure.
         * @param name the name of this structure.
         */
        public EmptyDeliverableStructure(long id, String name) {
            super(id, name);
        }

        /**
         * This method simply call super.isValidToPersist().
         *
         * @return true if the structure is valid to persist, false otherwise.
         */
        public boolean isValidToPersist() {
            return super.isValidToPersist();
        }
    }
}
