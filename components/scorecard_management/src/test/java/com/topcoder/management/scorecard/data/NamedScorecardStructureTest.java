/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;

/**
 * Abstract base class that contains the unit tests for the {@link NamedScorecardStructure} abstract base class.
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
public abstract class NamedScorecardStructureTest extends TestCase {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Fields

    /**
     * The identifier to be used in unit testing.
     */
    protected static final long ID = 1;

    /**
     * The name to be used in unit testing.
     */
    protected static final String NAME = "TopCoder";

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Fields

    /**
     * The NamedScorecardStructure instance to be used in unit testing.
     */
    private NamedScorecardStructure instance = null;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Abstract Methods

    /**
     * Creates a new NamedScorecardStructure instance of the class being unit testing using the zero
     * argument constructor.
     *
     * @return  A NamedScorecardStructure instance of the class being unit testing created using the zero
     *          argument constructor.
     */
    protected abstract NamedScorecardStructure createInstance();

    /**
     * Creates a new NamedScorecardStructure instance of the class being unit testing using the one
     * argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     *
     * @return  A NamedScorecardStructure instance of the class being unit testing created using the one
     *          argument constructor.
     */
    protected abstract NamedScorecardStructure createInstance(long id);

    /**
     * Creates a new NamedScorecardStructure instance of the class being unit testing using the two
     * argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   name
     *          The name to initialize with.
     *
     * @return  A NamedScorecardStructure instance of the class being unit testing created using the two
     *          argument constructor.
     */
    protected abstract NamedScorecardStructure createInstance(long id, String name);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods

    /**
     * Creates a new NamedScorecardStructure instance of the class being unit testing using the one
     * argument constructor.
     *
     * @return  A NamedScorecardStructure instance of the class being unit testing created using the one
     *          argument constructor.
     */
    protected NamedScorecardStructure createInstance1() {
        return createInstance(ID);
    }

    /**
     * Creates a new NamedScorecardStructure instance of the class being unit testing using the two
     * argument constructor.
     *
     * @return  A NamedScorecardStructure instance of the class being unit testing created using the two
     *          argument constructor.
     */
    protected NamedScorecardStructure createInstance2() {
        return createInstance(ID, NAME);
    }

    /**
     * Returns the instance currently being unit tested.
     *
     * @return  The instance currently being unit tested.
     */
    protected NamedScorecardStructure getInstance() {
        return instance;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SetUp

    /**
     * Recreates the instance being unit tested before each unit test.
     */
    protected void setUp() {
        instance = createInstance();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Field Checkers

    /**
     * Helper method to check the id field.
     *
     * @param   structure
     *          The NamedScorecardStructure instance to check.
     * @param   expected
     *          The expected value of the id field.
     * @param   message
     *          The error message if the field values don't match.
     */
    protected void checkId(NamedScorecardStructure structure, long expected, String message) {
        assertEquals(message, expected, structure.getId());
    }

    /**
     * Helper method to check the name field.
     *
     * @param   structure
     *          The NamedScorecardStructure instance to check.
     * @param   expected
     *          The expected value of the name field.
     * @param   message
     *          The error message if the field values don't match.
     */
    protected void checkName(NamedScorecardStructure structure, String expected, String message) {
        assertEquals(message, expected, structure.getName());
    }

    /**
     * Helper method to check the default value of id field which throws an IllegalStateException.
     *
     * @param   structure
     *          The NamedScorecardStructure instance to check.
     */
    private void checkDefaultId(NamedScorecardStructure structure) {
        assertEquals("Incorrect default value.", NamedScorecardStructure.SENTINEL_ID, structure.getId());
    }

    /**
     * Helper method to check the default value of name field, which is null.
     *
     * @param   structure
     *          The NamedScorecardStructure instance to check.
     */
    protected void checkDefaultName(NamedScorecardStructure structure) {
        checkName(structure, null, "A null is expected when the name has not yet been initialized.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor() Tests

    /**
     * Ensures that the zero argument constructor sets the identifier properly, by checking that the getId
     * method throws an IllegalStateException.
     */
    public void testCtor0SetsId() {
        checkDefaultId(createInstance());
    }

    /**
     * Ensures that the zero argument constructor sets the identifier properly, by checking that the
     * getName method returns a null.
     */
    public void testCtor0SetsName() {
        checkDefaultName(createInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(long) Tests

    /**
     * Ensures that the one argument constructor throws an IllegalArgumentException when given a negative
     * identifier.
     */
    public void testCtor1ThrowsOnNegativeId() {
        try {
            createInstance(-1);
            fail("An IllegalArgumentException is expected when the id is negative.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the one argument constructor throws an IllegalArgumentException when given a zero
     * identifier.
     */
    public void testCtor1ThrowsOnZeroId() {
        try {
            createInstance(0);
            fail("An IllegalArgumentException is expected when the id is zero.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the one argument constructor sets the identifier properly, by checking that the getId
     * method returns the original identifier.
     */
    public void testCtor1SetsId() {
        checkId(createInstance1(), ID, "The identifiers did not match.");
    }

    /**
     * Ensures that the one argument constructor sets the identifier properly, by checking that the
     * getName method returns a null.
     */
    public void testCtor1SetsName() {
        checkDefaultName(createInstance1());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(long, String) Tests

    /**
     * Ensures that the two argument constructor throws an IllegalArgumentException when given a negative
     * identifier.
     */
    public void testCtor2ThrowsOnNonNegativeId() {
        try {
            createInstance(-1, NAME);
            fail("An IllegalArgumentException is expected when the id is negative.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the two argument constructor throws an IllegalArgumentException when given a zero
     * identifier.
     */
    public void testCtor2ThrowsOnNonZeroId() {
        try {
            createInstance(0, NAME);
            fail("An IllegalArgumentException is expected when the id is zero.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the two argument constructor throws an IllegalArgumentException when given a name that
     * is null.
     */
    public void testCtor2ThrowsOnNullName() {
        try {
            createInstance(ID, null);
            fail("An IllegalArgumentException is expected when the name is null.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the two argument constructor sets the identifier properly, by checking that the getId
     * method returns the original identifier.
     */
    public void testCtor2SetsId() {
        checkId(createInstance2(), ID, "The identifiers did not match.");
    }

    /**
     * Ensures that the two argument constructor sets the name properly, by checking that the getName
     * method returns the original name.
     */
    public void testCtor2SetsName() {
        checkName(createInstance2(), NAME, "The names did not match.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // id Mutator Tests

    /**
     * Ensures that the setId method throws an IllegalArgumentException when given a negative
     * identifier.
     */
    public void testSetIdThrowsOnNegativeId() {
        try {
            instance.setId(-1);
            fail("An IllegalArgumentException is expected when the id is negative.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setId method throws an IllegalArgumentException when given a zero
     * identifier.
     */
    public void testSetIdThrowsOnZeroId() {
        try {
            instance.setId(0);
            fail("An IllegalArgumentException is expected when the id is zero.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the setId method sets the identifier properly, by checking that the getId
     * method returns the original identifier.
     */
    public void testSetIdWorks() {
        instance.setId(ID);
        checkId(instance, ID, "The identifiers did not match.");
    }

    /**
     * Ensures that the resetId method resets the identifier properly, by checking that the getId
     * method throws an IllegalStateException.
     */
    public void testResetIdWorks() {
        instance.setId(ID);
        instance.resetId();
        checkDefaultId(instance);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // name Mutator Tests

    /**
     * Ensures that the setName method sets the name properly, by checking that the getName
     * method returns the original name.
     */
    public void testSetNameWorks() {
        instance.setName(NAME);
        checkName(instance, NAME, "The names did not match.");
    }

    /**
     * Ensures that the resetName method resets the name properly, by checking that the getName
     * method returns a null.
     */
    public void testResetNameWorks() {
        instance.setName(NAME);
        instance.resetName();
        checkDefaultName(instance);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serialization Tests

    /**
     * Helper method that serializes the internal NamedScorecardStructure, deserializes the serialized form
     * and returns the deserialized NamedScorecardStructure.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     *
     * @return  A copy of the original structure after a serialization and deserialization round trip.
     */
    protected NamedScorecardStructure serializeAndDeserialize() throws ClassNotFoundException, IOException {
        // Serialize the object.
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(instance);
        oos.close();

        // Deserialize the object.
        byte[] bytes = out.toByteArray();
        InputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(in);
        return (NamedScorecardStructure) ois.readObject();
    }

    /**
     * Ensures that serialization of the id field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeIdWorks() throws ClassNotFoundException, IOException {
        instance.setId(ID);
        NamedScorecardStructure copy = serializeAndDeserialize();
        checkId(copy, ID, "The identifiers did not match.");
    }

    /**
     * Ensures that serialization of the name field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeNameWorks() throws ClassNotFoundException, IOException {
        instance.setName(NAME);
        NamedScorecardStructure copy = serializeAndDeserialize();
        checkName(copy, NAME, "The names did not match.");
    }
}
