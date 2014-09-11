/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyNameValueFilter;
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
 * <p>Tests {@link MetadataKeyNameValueFilter} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class MetadataKeyNameValueFilterAccuracyTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private MetadataKeyNameValueFilter instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(MetadataKeyNameValueFilterAccuracyTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new MetadataKeyNameValueFilter();
    }

    /**
     * <p>Tests if {@link MetadataKeyNameValueFilter} class implements {@link  java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {

        assertTrue("Class MetadataKeyNameValueFilter does not implement Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests {@link MetadataKeyNameValueFilter#MetadataKeyNameValueFilter()} constructor.</p>
     */
    @Test
    public void testCtor() {

        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests the <code>projectMetadataKeyName</code> property, this method tests both {@link
     * MetadataKeyNameValueFilter#getProjectMetadataKeyName()} and {@link MetadataKeyNameValueFilter#setProjectMetadataKeyName(String)}
     * methods.</p>
     */
    @Test
    public void testProjectMetadataKeyName() {
        assertNull("Property projectMetadataKeyName should be null.", instance.getProjectMetadataKeyName());

        String projectMetadataKeyName = "projectMetadataKeyName";
        instance.setProjectMetadataKeyName(projectMetadataKeyName);

        assertEquals("Invalid projectMetadataKeyName property value.", projectMetadataKeyName,
                instance.getProjectMetadataKeyName());
    }

    /**
     * <p>Tests the <code>metadataValue</code> property, this method tests both {@link
     * MetadataKeyNameValueFilter#getMetadataValue()} and {@link MetadataKeyNameValueFilter#setMetadataValue(String)}
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
     * MetadataKeyNameValueFilter#getMetadataValueOperator()} and {@link MetadataKeyNameValueFilter#setMetadataValueOperator(MetadataValueOperator)}
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
     * <p>Tests {@link MetadataKeyNameValueFilter#toJSONString()} method.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONString() throws JSONDecodingException {
        String projectMetadataKeyName = "projectMetadataKeyName";
        String metadataValue = "metadataValue";
        MetadataValueOperator metadataValueOperator = MetadataValueOperator.EQUALS;

        instance.setProjectMetadataKeyName(projectMetadataKeyName);
        instance.setMetadataValue(metadataValue);
        instance.setMetadataValueOperator(metadataValueOperator);

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("ProjectMetadataKeyName hasn't been correctly serialized.", projectMetadataKeyName,
                jsonObject.getString("projectMetadataKeyName"));
        assertEquals("MetadataValue hasn't been correctly serialized.", metadataValue,
                jsonObject.getString("metadataValue"));
        assertEquals("MetadataValueOperator hasn't been correctly serialized.", metadataValueOperator.toString(),
                jsonObject.getString("metadataValueOperator"));
    }

    /**
     * <p>Tests {@link MetadataKeyNameValueFilter#toJSONString()} method when not property has been set.</p>
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
        assertTrue("ProjectMetadataKeyName hasn't been correctly serialized.",
                jsonObject.isNull("projectMetadataKeyName"));
        assertTrue("MetadataValue hasn't been correctly serialized.", jsonObject.isNull("metadataValue"));
        assertTrue("MetadataValueOperator hasn't been correctly serialized.",
                jsonObject.isNull("metadataValueOperator"));
    }
}
