/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.stresstests;

import java.math.BigInteger;

import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;

/**
 * <p>
 * Mock class for IDGenerator.
 * </p>
 * @author still
 * @version 1.0
 */
public final class StressMockIDGenerator implements IDGenerator {
    /** the static DEFAULT StressMockIDGenerator. */
    public static final  StressMockIDGenerator DEFAULT = new StressMockIDGenerator("");
    /** the long variable id starting from 100. */
    private long id = 100;

    /**
     * The default constructor.
     *
     * @param seqName the unused param
     */
    public StressMockIDGenerator(String seqName) {
    }

    /**
     * Returns "stress_test_id_generator" always.
     *
     * @return "stress_test_id_generator".
     */
    public String getIDName() {
        return "stress_test_id_generator";
    }

    /**
     * Returns the next id.
     * @return the next id.
     * @throws IDGenerationException in no circumstance
     */
    public long getNextID() throws IDGenerationException {
        return id++;
    }

    /**
     * Returns the next id in BigInteger.
     * @return the next id.
     * @throws IDGenerationException in no circumstance
     */
    public BigInteger getNextBigID() throws IDGenerationException {
        return new BigInteger("" + (id++));
    }

    /**
     * Disposes the IDGenerator.
     */
    public void dispose() {
        // simply empty.
    }
}