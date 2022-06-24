/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dto.MetadataValueOperator;
import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Tests {@link MetadataValueOperator} enum.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MetadataValueOperatorAccuracyTests {

    /**
     * <p>Returns test suite for this enum.</p>
     *
     * @return test suite for this enum
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(MetadataValueOperatorAccuracyTests.class);
    }

    /**
     * <p>Tests if {@link MetadataValueOperator} is enum.</p>
     */
    @Test
    public void testEnum() {

        Assert.assertTrue("MetadataValueOperator is not declared as a enum",
                MetadataValueOperator.class.isEnum());
    }

    /**
     * <p>Tests {@link MetadataValueOperator#EQUALS} enum.</p>
     */
    @Test
    public void testEQUALS() {

        Assert.assertNotNull("Enum EQUALS is null.", MetadataValueOperator.EQUALS);
    }

    /**
     * <p>Tests {@link MetadataValueOperator#LIKE} enum.</p>
     */
    @Test
    public void testLIKE() {

        Assert.assertNotNull("Enum LIKE is null.", MetadataValueOperator.LIKE);
    }
}
