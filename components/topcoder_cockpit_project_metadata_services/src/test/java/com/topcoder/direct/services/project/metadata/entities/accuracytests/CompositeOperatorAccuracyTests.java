/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dto.CompositeOperator;
import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Tests {@link CompositeOperator} enum.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompositeOperatorAccuracyTests {

    /**
     * <p>Returns test suite for this enum.</p>
     *
     * @return test suite for this enum
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CompositeOperatorAccuracyTests.class);
    }

    /**
     * <p>Tests if {@link CompositeOperator} is enum.</p>
     */
    @Test
    public void testEnum() {

        Assert.assertTrue("CompositeOperator is not declared as a enum",
                CompositeOperator.class.isEnum());
    }

    /**
     * <p>Tests {@link CompositeOperator#AND} enum.</p>
     */
    @Test
    public void testAND() {

        Assert.assertNotNull("Enum AND is null.", CompositeOperator.AND);
    }

    /**
     * <p>Tests {@link CompositeOperator#OR} enum.</p>
     */
    @Test
    public void testOR() {

        Assert.assertNotNull("Enum OR is null.", CompositeOperator.OR);
    }
}
