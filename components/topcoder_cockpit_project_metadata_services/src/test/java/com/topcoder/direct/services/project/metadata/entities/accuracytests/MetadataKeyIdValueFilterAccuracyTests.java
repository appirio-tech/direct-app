/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyIdValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataValueOperator;
import com.topcoder.json.object.JSONObject;
import com.topcoder.json.object.io.JSONDecodingException;
import com.topcoder.json.object.io.StandardJSONDecoder;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>Tests {@link MetadataKeyIdValueFilter} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class MetadataKeyIdValueFilterAccuracyTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private MetadataKeyIdValueFilter instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(MetadataKeyIdValueFilterAccuracyTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new MetadataKeyIdValueFilter();
    }

    /**
     * <p>Tests if {@link MetadataKeyIdValueFilter} class implements {@link  java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {

        assertTrue("Class MetadataKeyIdValueFilter does not implement Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests {@link MetadataKeyIdValueFilter#MetadataKeyIdValueFilter()} constructor.</p>
     */
    @Test
    public void testCtor() {

        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests the <code>projectMetadataKeyId</code> property, this method tests both {@link
     * MetadataKeyIdValueFilter#getProjectMetadataKeyId()} and {@link MetadataKeyIdValueFilter#setProjectMetadataKeyId(long)}
     * methods.</p>
     */
    @Test
    public void testProjectMetadataKeyId() {
        assertEquals("Property projectMetadataKeyId should be 0.", 0L, instance.getProjectMetadataKeyId());

        long projectMetadataKeyId = 10L;
        instance.setProjectMetadataKeyId(projectMetadataKeyId);

        assertEquals("Invalid projectMetadataKeyId property value.", projectMetadataKeyId,
                instance.getProjectMetadataKeyId());
    }

    /**
     * <p>Tests the <code>metadataValue</code> property, this method tests both {@link
     * MetadataKeyIdValueFilter#getMetadataValue()} and {@link MetadataKeyIdValueFilter#setMetadataValue(String)}
     * methods.</p>
     */
    @Test
    public void testMetadataValue() {
        assertNull("Property metadataValue should be null.", instance.getMetadataValue());

        String metadataValue = "metadataValue";
        instance.setMetadataValue(metadataValue);

        assertEquals("Invalid metadataValue property value.", metadataValue, instance.getMetadataValue());
    }

    /**
     * <p>Tests the <code>metadataValueOperator</code> property, this method tests both {@link
     * MetadataKeyIdValueFilter#getMetadataValueOperator()} and {@link MetadataKeyIdValueFilter#setMetadataValueOperator(MetadataValueOperator)}
     * methods.</p>
     */
    @Test
    public void testMetadataValueOperator() {
        assertNull("Property metadataValueOperator should be null.", instance.getMetadataValueOperator());

        MetadataValueOperator metadataValueOperator = MetadataValueOperator.EQUALS;
        instance.setMetadataValueOperator(metadataValueOperator);

        assertEquals("Invalid metadataValueOperator property value.", metadataValueOperator,
                instance.getMetadataValueOperator());
    }

    /**
     * <p>Tests {@link MetadataKeyIdValueFilter#toJSONString()} method.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONString() throws JSONDecodingException {
        long projectMetadataKeyId = 1L;
        String metadataValue = "metadataValue";
        MetadataValueOperator metadataValueOperator = MetadataValueOperator.EQUALS;

        instance.setProjectMetadataKeyId(projectMetadataKeyId);
        instance.setMetadataValue(metadataValue);
        instance.setMetadataValueOperator(metadataValueOperator);

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("ProjectMetadataKeyId hasn't been correctly serialized.", projectMetadataKeyId,
                jsonObject.getLong("projectMetadataKeyId"));
        assertEquals("MetadataValue hasn't been correctly serialized.", metadataValue,
                jsonObject.getString("metadataValue"));
        assertEquals("MetadataValueOperator hasn't been correctly serialized.", metadataValueOperator.toString(),
                jsonObject.getString("metadataValueOperator"));
    }

    /**
     * <p>Tests {@link MetadataKeyIdValueFilter#toJSONString()} method when not property has been set.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONStringEmpty() throws JSONDecodingException {
        long projectMetadataKeyId = 0L;

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("ProjectMetadataKeyId hasn't been correctly serialized.", projectMetadataKeyId,
                jsonObject.getLong("projectMetadataKeyId"));
        assertTrue("MetadataValue hasn't been correctly serialized.", jsonObject.isNull("metadataValue"));
        assertTrue("MetadataValueOperator hasn't been correctly serialized.",
                jsonObject.isNull("metadataValueOperator"));
    }
}
