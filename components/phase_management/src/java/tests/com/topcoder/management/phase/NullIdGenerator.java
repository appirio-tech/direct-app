/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.util.idgenerator.IDGenerator;
import java.math.BigInteger;

/**
 * A sample implementation of IDGenerator used by some test cases.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class NullIdGenerator implements IDGenerator {
    /**
     * The next ID to assign.
     */
    private long next = 2;

    /**
     * Does nothing.
     */
    public NullIdGenerator() { }

    /**
     * Initializes a new generator for the specified sequence.
     *
     * @param sequence the sequence
     */
    public NullIdGenerator(String sequence) {
    }

    /**
     * Returns the next long in sequence.
     *
     * @return the next long in sequence
     */
    long getNext() {
        return next++;
    }

    /**
     * Raises a RuntimeException; if this method is called that means that something unexpected has happened in a
     * test.
     *
     * @return the name of the sequence
     * @throws RuntimeException always thrown
     */
    public String getIDName() {
        throw new RuntimeException("unexpected call to getIDName");
    }

    /**
     * Raises a RuntimeException; if this method is called that means that something unexpected has happened in a
     * test.
     *
     * @return the next available ID
     * @throws RuntimeException always thrown
     */
    public long getNextID() {
        throw new RuntimeException("unexpected call to getNextID");
    }

    /**
     * Raises a RuntimeException; if this method is called that means that something unexpected has happened in a
     * test.
     *
     * @return the next available ID as a <code>BigInteger</code>
     * @throws RuntimeException always thrown
     */
    public BigInteger getNextBigID() {
        throw new RuntimeException("unexpected call to getNextBigID");
    }

    /**
     * Disposes the IDGenerator.
     */
    public void dispose() {
        // simply empty.
    }
}
