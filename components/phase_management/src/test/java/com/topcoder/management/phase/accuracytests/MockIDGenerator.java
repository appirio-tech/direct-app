/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.accuracytests;

import com.topcoder.util.idgenerator.IDGenerator;
import java.math.BigInteger;

/**
 * <p>
 * A mock implementation of IDGenerator used only for test purpose.
 * </p>
 * @author Thinfox
 * @version 1.0
 */

public class MockIDGenerator implements IDGenerator {
    /**
     * The ID name.
     */
    private final String idName;

    /**
     * The next ID.
     */
    private long nextId = 1;

    /**
     * Initializes a new generator for the specified id name.
     * @param idName the ID name
     */
    public MockIDGenerator(String idName) {
        this.idName = idName;
    }

    /**
     * Returns the name of the sequence.
     * @return the name of the sequence
     * @throws RuntimeException always thrown
     */
    public String getIDName() {
        return idName;
    }

    /**
     * Returns the next id.
     * test.
     * @return the next ID.
     * @throws RuntimeException always thrown
     */
    public long getNextID() {
        return nextId++;
    }

    /**
     * Wraps the value that would be returned by getNextID() in a BigInteger instance and returns it.
     * @return next value that would be returned by getNextID() as a BigInteger
     * @throws RuntimeException always thrown
     */
    public BigInteger getNextBigID() {
        return BigInteger.valueOf(getNextID());
    }

    /**
     * Disposes the IDGenerator.
     */
    public void dispose() {
        // simply empty.
    }
}