/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dto.CompositeFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.CompositeOperator;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectFilter;
import com.topcoder.json.object.JSONObject;
import com.topcoder.json.object.io.JSONDecodingException;
import com.topcoder.json.object.io.StandardJSONDecoder;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>Tests {@link CompositeFilter} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class CompositeFilterAccuracyTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private CompositeFilter instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CompositeFilterAccuracyTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new CompositeFilter();
    }

    /**
     * <p>Tests if {@link CompositeFilter} class implements {@link  java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {

        assertTrue("Class CompositeFilter does not implement Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests {@link CompositeFilter#CompositeFilter()} constructor.</p>
     */
    @Test
    public void testCtor() {

        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests the <code>projectFilters</code> property, this method tests both {@link
     * CompositeFilter#getProjectFilters()} and {@link CompositeFilter#setProjectFilters(List)} methods.</p>
     */
    @Test
    public void testProjectFilters() {
        assertNull("Property projectFilters should be null.", instance.getProjectFilters());

        List<DirectProjectFilter> projectFilters =
                Arrays.<DirectProjectFilter>asList(new MockDirectProjectFilter(), new MockDirectProjectFilter());
        instance.setProjectFilters(projectFilters);

        assertEquals("Invalid projectFilters property value.", projectFilters, instance.getProjectFilters());
        assertEquals("Invalid projectFilters property value.", projectFilters, instance.getProjectFilters());
    }

    /**
     * <p>Tests the <code>compositeOperator</code> property, this method tests both {@link
     * CompositeFilter#getCompositeOperator()} and {@link CompositeFilter#setCompositeOperator(CompositeOperator)}
     * methods.</p>
     */
    @Test
    public void testId() {
        assertNull("Property compositeOperator should be null.", instance.getCompositeOperator());

        CompositeOperator compositeOperator = CompositeOperator.AND;
        instance.setCompositeOperator(compositeOperator);

        assertEquals("Invalid compositeOperator property value.", compositeOperator, instance.getCompositeOperator());
    }

    /**
     * <p>Tests {@link CompositeFilter#toJSONString()} method.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONString() throws JSONDecodingException {
        CompositeOperator compositeOperator = CompositeOperator.AND;
        List<DirectProjectFilter> projectFilters =
                Arrays.<DirectProjectFilter>asList(new MockDirectProjectFilter(),
                        new MockDirectProjectFilter());

        instance.setCompositeOperator(compositeOperator);
        instance.setProjectFilters(projectFilters);

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("CompositeOperator hasn't been correctly serialized.", compositeOperator.toString(),
                jsonObject.getString("compositeOperator"));
        assertEquals("ProjectFilters hasn't been correctly serialized.", 2,
                jsonObject.getArray("projectFilters").getSize());
    }

    /**
     * <p>Tests {@link CompositeFilter#toJSONString()} method when not property has been set.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONStringEmpty() throws JSONDecodingException {
        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertTrue("CompositeOperator hasn't been correctly serialized.",
                jsonObject.isNull("compositeOperator"));
        assertTrue("ProjectFilters hasn't been correctly serialized.",
                jsonObject.isNull("projectFilters"));
    }
}
