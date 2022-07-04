/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.TestsHelper;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;

/**
 * <p>
 * Unit tests for {@link Helper} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class HelperUnitTests {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(HelperUnitTests.class);
    }

    /**
     * <p>
     * Tests accuracy of <code>toString(Object obj)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_toString_1() {
        assertEquals("'toString' should be correct.", "null", Helper.toString(null));
    }

    /**
     * <p>
     * Tests accuracy of <code>toString(Object obj)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_toString_2() {
        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setName("name3");
        key.setDescription("some text");
        key.setGrouping(null);
        key.setClientId(null);
        key.setSingle(true);

        assertTrue("'toString' should be correct.", Helper.toString(key).contains("\"name\":\"name3\""));
    }

    /**
     * <p>
     * Tests accuracy of <code>checkMap(Map&lt;T1, T2&gt; map, String name)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_checkMap() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("create", 1);
        map.put("update", 2);
        map.put("delete", 3);

        Helper.checkMap(map, "name");
    }

    /**
     * <p>
     * Tests failure of <code>checkMap(Map&lt;T1, T2&gt; map, String name)</code> method with map is
     * <code>null</code>.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkMap_mapNull() {
        Map<String, Integer> map = null;

        Helper.checkMap(map, "name");
    }

    /**
     * <p>
     * Tests failure of <code>checkMap(Map&lt;T1, T2&gt; map, String name)</code> method with map contains
     * <code>null</code> key.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkMap_mapContainsNullKey() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("create", 1);
        map.put("update", 2);
        map.put(null, 3);

        Helper.checkMap(map, "name");
    }

    /**
     * <p>
     * Tests failure of <code>checkMap(Map&lt;T1, T2&gt; map, String name)</code> method with map contains
     * empty key.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkMap_mapContainsEmptyKey() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("create", 1);
        map.put("update", 2);
        map.put(TestsHelper.EMPTY_STRING, 3);

        Helper.checkMap(map, "name");
    }

    /**
     * <p>
     * Tests failure of <code>checkMap(Map&lt;T1, T2&gt; map, String name)</code> method with map contains
     * <code>null</code> value.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkMap_mapContainsNullValue() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("create", 1);
        map.put("update", 2);
        map.put("delete", null);

        Helper.checkMap(map, "name");
    }
}
