/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.math.BigInteger;

import com.topcoder.util.idgenerator.IDGenerator;

/**
 * A IDGenerator generating increasing integer values. It is used for test.
 *
 * @author singlewood
 * @version 1.0
 */
public class IDGeneratorIncrease implements IDGenerator {

    /**
     * static int value for generating id.
     */
    private long count = 1;

    /**
     * Return the name of the ID sequence which this instance encapsulates.
     *
     * @return the name of the ID sequence which this instance encapsulates
     */
    public String getIDName() {
        return "count";
    }

    /**
     * Returns the next ID in the ID sequence encapsulated by this instance. Internal state is
     * updated so that this ID is not returned again from this method.
     *
     * @return the next ID in the ID sequence
     */
    public long getNextID() {
        return count++;
    }

    /**
     * <p>
     * Returns the next ID in the ID sequence encapsulated by this instance in the form of a
     * BigInteger, rather than a long.
     * </p>
     *
     * @return the next ID in the ID sequence
     */
    public BigInteger getNextBigID() {
        return BigInteger.valueOf(count++);
    }

}
