/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests.persistence;

import java.math.BigInteger;

import com.topcoder.util.idgenerator.IDGenerator;

/**
 * A <code>IDGenerator</code> implementation used for failure tests.
 * <p>
 * It keeps a internal counter, and increases the counter when you retrieve id from it.
 * </p>
 *
 * @author mayi
 * @version 1.1
 * @since 1.0
 */
public class FailureIDGenerator implements IDGenerator {
    /**
     * Represents the current id counter.
     */
    private long id = 0;

    /**
     * Default constructor.
     */
    public FailureIDGenerator() {
    }

    /**
     * Get this id genrator's name.
     *
     * @return the id name.
     */
    public String getIDName() {
        return "FailureIDGenerator";
    }

    /**
     * Get next id.
     *
     * @return the next id generated.
     */
    public long getNextID() {
        return ++id;
    }

    /**
     * Get next id as a <code>BigInteger</code>.
     *
     * @return the next id generated.
     */
    public BigInteger getNextBigID() {
        return new BigInteger(Long.toString(++id));
    }

}
