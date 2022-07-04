/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import java.io.IOException;

/**
 * Abstract base class that contains the unit tests for the {@link WeightedScorecardStructure} abstract base
 * class.
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
public abstract class WeightedScorecardStructureTest extends NamedScorecardStructureTest {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Fields

    /**
     * The weight to be used in unit testing.
     */
    protected static final float WEIGHT = 50.0f;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Abstract Methods

    /**
     * Creates a new WeightedScorecardStructure instance of the class being unit testing using the two
     * argument constructor that takes a weight.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   weight
     *          The weight to initialize with.
     *
     * @return  A WeightedScorecardStructure instance of the class being unit testing created using the two
     *          argument constructor that takes a weight.
     */
    protected abstract WeightedScorecardStructure createInstance(long id, float weight);

    /**
     * Creates a new WeightedScorecardStructure instance of the class being unit testing using the three
     * argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   name
     *          The name to initialize with.
     * @param   weight
     *          The weight to initialize with.
     *
     * @return  A WeightedScorecardStructure instance of the class being unit testing created using the
     *          three argument constructor.
     */
    protected abstract WeightedScorecardStructure createInstance(long id, String name, float weight);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods

    /**
     * Creates a new WeightedScorecardStructure instance of the class being unit testing using the two
     * argument constructor that takes a weight.
     *
     * @return  A WeightedScorecardStructure instance of the class being unit testing created using the two
     *          argument constructor that takes a weight.
     */
    protected WeightedScorecardStructure createInstance2Weight() {
        return createInstance(ID, WEIGHT);
    }

    /**
     * Creates a new WeightedScorecardStructure instance of the class being unit testing using the three
     * argument constructor.
     *
     * @return  A WeightedScorecardStructure instance of the class being unit testing created using the
     *          three argument constructor.
     */
    protected WeightedScorecardStructure createInstance3() {
        return createInstance(ID, NAME, WEIGHT);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Field Checkers

    /**
     * Helper method to check the weight field.
     *
     * @param   structure
     *          The WeightedScorecardStructure instance to check (will be down casted).
     * @param   expected
     *          The expected value of the weight field.
     * @param   message
     *          The error message if the field values don't match.
     */
    private void checkWeight(NamedScorecardStructure structure, float expected, String message) {
        assertEquals(message, expected, ((WeightedScorecardStructure) structure).getWeight(), 1e-9f);
    }

    /**
     * Helper method to check the default value of weight field, which is zero.
     *
     * @param   structure
     *          The WeightedScorecardStructure instance to check (will be down casted).
     */
    private void checkDefaultWeight(NamedScorecardStructure structure) {
        checkWeight(structure, 0.0f, "A 0 is expected when the weight has not yet been initialized.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor() Tests

    /**
     * Ensures that the zero argument constructor sets the weight properly, by checking that the getWeight
     * method returns 0.
     */
    public void test0CtorSetsWeight() {
        checkDefaultWeight(createInstance());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(long) Tests

    /**
     * Ensures that the one argument constructor sets the weight properly, by checking that the getWeight
     * method returns 0.
     */
    public void test1CtorSetsWeight() {
        checkDefaultWeight(createInstance1());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(long, String) Tests

    /**
     * Ensures that the two argument constructor sets the weight properly, by checking that the getWeight
     * method returns 0.
     */
    public void test2CtorSetsWeight() {
        checkDefaultWeight(createInstance2());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(long, float) Tests

    /**
     * Ensures that the two argument constructor that takes a weight throws an IllegalArgumentException when
     * given a negative identifier.
     */
    public void test2WeightCtorThrowsOnNegativeId() {
        check2WeightCtorThrowsIAE(-1, WEIGHT, "the id is negative.");
    }

    /**
     * Ensures that the two argument constructor that takes a weight throws an IllegalArgumentException when
     * given a zero identifier.
     */
    public void test2WeightCtorThrowsOnZeroId() {
        check2WeightCtorThrowsIAE(0, WEIGHT, "the id is zero.");
    }

    /**
     * Ensures that the two argument constructor that takes a weight throws an IllegalArgumentException when
     * given a weight that is less than 0.
     */
    public void test2WeightCtorThrowsOnWeightLessThanZero() {
        check2WeightCtorThrowsIAE(ID, -0.01f, "the weight is less than 0.");
    }

    /**
     * Ensures that the two argument constructor that takes a weight throws an IllegalArgumentException when
     * given a weight that is greater than 100.
     */
    public void test2WeightCtorThrowsOnWeightGreaterThanOneHundred() {
        check2WeightCtorThrowsIAE(ID, 100.01f, "the weight is greater than 100.");
    }

    /**
     * Ensures that the two argument constructor that takes a weight throws an IllegalArgumentException when
     * given a weight that is not a number (Float.NaN).
     */
    public void test2WeightCtorThrowsOnWeightNaN() {
        check2WeightCtorThrowsIAE(ID, Float.NaN, "the weight is Float.NaN.");
    }

    /**
     * Ensures that the two argument constructor that takes a weight sets the identifier properly, by checking
     * that the getId method returns the original identifier.
     */
    public void test2WeightCtorSetsId() {
        checkId(createInstance2Weight(), ID, "The identifiers did not match.");
    }

    /**
     * Ensures that the two argument constructor that takes a weight sets the name properly, by checking that
     * the getName method returns a null.
     */
    public void test2WeightCtorSetsName() {
        checkDefaultName(createInstance2Weight());
    }

    /**
     * Ensures that the two argument constructor that takes a weight sets the weight properly, by checking that
     * the getWeight method returns the original weight.
     */
    public void test2WeightCtorSetsWeight() {
        checkWeight(createInstance2Weight(), WEIGHT, "The weights did not match.");
    }

    /**
     * Helper method to ensure that the two argument constructor that takes a weight throws an
     * IllegalArgumentException with the specified arguments.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   weight
     *          The weight to initialize with.
     * @param   message
     *          The message to use in the error message.
     */
    private void check2WeightCtorThrowsIAE(long id, float weight, String message) {
        try {
            createInstance(id, weight);
            fail("An IllegalArgumentException is expected when " + message);
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor(long, String, float) Tests

    /**
     * Ensures that the three argument constructor throws an IllegalArgumentException when given a
     * negative identifier.
     */
    public void test3CtorThrowsOnNegativeId() {
        check3CtorThrowsIAE(-1, NAME, WEIGHT, "the id is negative.");
    }

    /**
     * Ensures that the three argument constructor throws an IllegalArgumentException when given a
     * zero identifier.
     */
    public void test3CtorThrowsOnZeroId() {
        check3CtorThrowsIAE(-1, NAME, WEIGHT, "the id is zero.");
    }

    /**
     * Ensures that the three argument constructor throws an IllegalArgumentException when given a name
     * that is null.
     */
    public void test3CtorThrowsOnNullName() {
        check3CtorThrowsIAE(ID, null, WEIGHT, "the name is null.");
    }

    /**
     * Ensures that the three argument constructor throws an IllegalArgumentException when given a weight
     * that is less than 0.
     */
    public void test3CtorThrowsOnWeightLessThanZero() {
        check3CtorThrowsIAE(ID, NAME, -0.01f, "the weight is less than 0.");
    }

    /**
     * Ensures that the three argument constructor throws an IllegalArgumentException when given a weight
     * that is greater than 100.
     */
    public void test3CtorThrowsOnWeightGreaterThanOneHundred() {
        check3CtorThrowsIAE(ID, NAME, 100.01f, "the weight is greater than 100.");
    }

    /**
     * Ensures that the three argument constructor throws an IllegalArgumentException when given a weight
     * that is not a number (Float.NaN).
     */
    public void test3CtorThrowsOnWeightNaN() {
        check3CtorThrowsIAE(ID, NAME, Float.NaN, "the weight is Float.NaN.");
    }

    /**
     * Ensures that the three argument constructor sets the identifier properly, by checking that the
     * getId method returns the original identifier.
     */
    public void test3CtorSetsId() {
        checkId(createInstance3(), ID, "The identifiers did not match.");
    }

    /**
     * Ensures that the three argument constructor sets the name properly, by checking that the getName
     * method returns the original name.
     */
    public void test3CtorSetsName() {
        checkName(createInstance3(), NAME, "The names did not match.");
    }

    /**
     * Ensures that the three argument constructor sets the weight properly, by checking that the
     * getWeight method returns the original weight.
     */
    public void test3CtorSetsWeight() {
        checkWeight(createInstance3(), WEIGHT, "The weights did not match.");
    }

    /**
     * Helper method to ensure that the three argument constructor throws an IllegalArgumentException with
     * the specified arguments.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   name
     *          The name to initialize with.
     * @param   weight
     *          The weight to initialize with.
     * @param   message
     *          The message to use in the error message.
     */
    private void check3CtorThrowsIAE(long id, String name, float weight, String message) {
        try {
            createInstance(id, name, weight);
            fail("An IllegalArgumentException is expected when " + message);
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper Methods

    /**
     * Down casts the NamedScorecardStructure back to a WeightedScorecardStructure.
     *
     * @return  The WeightedScorecardStructure instance created in the setUp method.
     */
    private WeightedScorecardStructure getWeightInstance() {
        return (WeightedScorecardStructure) getInstance();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // weight Mutator Tests

    /**
     * Ensures that the setWeight method throws an IllegalArgumentException when given a weight that
     * is less than zero.
     */
    public void testSetWeightThrowsOnWeightLessThanZero() {
        checkSetWeightThrowsIAE(-0.01f, "the weight is less than 0.");
    }

    /**
     * Ensures that the setWeight method throws an IllegalArgumentException when given a weight that
     * is greater than a hundred.
     */
    public void testSetWeightThrowsOnWeightGreaterThanOneHundred() {
        checkSetWeightThrowsIAE(100.01f, "the weight is greater than 100.");
    }

    /**
     * Ensures that the setWeight method throws an IllegalArgumentException when given a weight that
     * is not a number (Float.NaN).
     */
    public void testSetWeightThrowsOnWieghtNaN() {
        checkSetWeightThrowsIAE(Float.NaN, "the weight is Float.NaN.");
    }

    /**
     * Ensures that the setWeight method sets the weight properly, by checking that the
     * getWeight method returns the original weight.
     */
    public void testSetWeightWorks() {
        getWeightInstance().setWeight(WEIGHT);
        checkWeight(getInstance(), WEIGHT, "The weights did not match.");
    }

    /**
     * Ensures that the resetWeight method resets the weight properly, by checking that the
     * getWeight method returns zero.
     */
    public void testResetWeightWorks() {
        getWeightInstance().setWeight(WEIGHT);
        getWeightInstance().resetWeight();
        checkDefaultWeight(getInstance());
    }

    /**
     * Helper method used to ensure that the setWeight method throws an IllegalArgumentException
     * when given the specified weight.
     *
     * @param   weight
     *          The weight to use.
     * @param   message
     *          The message to use in the error message.
     */
    private void checkSetWeightThrowsIAE(float weight, String message) {
        try {
            getWeightInstance().setWeight(weight);
            fail("An IllegalArgumentException is expected when " + message);
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Serialization Tests

    /**
     * Ensures that serialization of the weight field works properly.
     *
     * @throws  ClassNotFoundException
     *          Couldn't deserialize properly.
     * @throws  IOException
     *          Couldn't serialize or deserialize properly.
     */
    public void testSerializeWeightWorks() throws ClassNotFoundException, IOException {
        getWeightInstance().setWeight(WEIGHT);
        WeightedScorecardStructure copy = (WeightedScorecardStructure) serializeAndDeserialize();
        checkWeight(copy, WEIGHT, "The weights did not match.");
    }
}
