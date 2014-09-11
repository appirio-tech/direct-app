/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValue;
import com.topcoder.json.object.JSONObject;
import com.topcoder.json.object.io.JSONDecodingException;
import com.topcoder.json.object.io.StandardJSONDecoder;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>Tests {@link DirectProjectMetadataPredefinedValue} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class DirectProjectMetadataPredefinedValueAccuracyTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private DirectProjectMetadataPredefinedValue instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProjectMetadataPredefinedValueAccuracyTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new DirectProjectMetadataPredefinedValue();
    }

    /**
     * <p>Tests if {@link DirectProjectMetadataPredefinedValue} class implements {@link  java.io.Serializable}
     * interface.</p>
     */
    @Test
    public void testSerializable() {

        assertTrue("Class DirectProjectMetadataPredefinedValue does not implement Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests {@link DirectProjectMetadataPredefinedValue#DirectProjectMetadataPredefinedValue()} constructor.</p>
     */
    @Test
    public void testCtor() {

        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests the <code>id</code> property, this method tests both {@link DirectProjectMetadataPredefinedValue#getId()}
     * and {@link DirectProjectMetadataPredefinedValue#setId(long)} methods.</p>
     */
    @Test
    public void testId() {
        assertEquals("Property id should be 0.", 0L, instance.getId());

        long id = 10L;
        instance.setId(id);

        assertEquals("Invalid id property value.", id, instance.getId());
    }

    /**
     * <p>Tests the <code>predefinedMetadataValue</code> property, this method tests both {@link
     * DirectProjectMetadataPredefinedValue#getPredefinedMetadataValue()} and {@link
     * DirectProjectMetadataPredefinedValue#setPredefinedMetadataValue(String)} methods.</p>
     */
    @Test
    public void testPredefinedMetadataValue() {
        assertNull("Property predefinedMetadataValue should be null.", instance.getPredefinedMetadataValue());

        String predefinedMetadataValue = "predefinedMetadataValue";
        instance.setPredefinedMetadataValue(predefinedMetadataValue);

        assertEquals("Invalid predefinedMetadataValue property value.", predefinedMetadataValue,
                instance.getPredefinedMetadataValue());
    }

    /**
     * <p>Tests the <code>position</code> property, this method tests both {@link DirectProjectMetadataPredefinedValue#getPosition()}
     * and {@link DirectProjectMetadataPredefinedValue#setPosition(int)}methods.</p>
     */
    @Test
    public void testPosition() {
        assertEquals("Property position should be 0.", 0, instance.getPosition());

        int position = 10;
        instance.setPosition(position);

        assertEquals("Invalid position property value.", position, instance.getPosition());
    }

    /**
     * <p>Tests the <code>projectMetadataKey</code> property, this method tests both {@link
     * DirectProjectMetadataPredefinedValue#getProjectMetadataKey()} and {@link DirectProjectMetadataPredefinedValue#setProjectMetadataKey(DirectProjectMetadataKey)}
     * methods.</p>
     */
    @Test
    public void testProjectMetadataKey() {
        assertNull("Property projectMetadataKey should be null.", instance.getProjectMetadataKey());

        DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();
        instance.setProjectMetadataKey(projectMetadataKey);

        assertEquals("Invalid projectMetadataKey property value.", projectMetadataKey,
                instance.getProjectMetadataKey());
    }

    /**
     * <p>Tests {@link DirectProjectMetadataPredefinedValue#toJSONString()} method.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONString() throws JSONDecodingException {
        long id = 10L;
        String predefinedMetadataValue = "predefinedMetadataValue";
        int position = 2;
        DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();

        instance.setId(id);
        instance.setPredefinedMetadataValue(predefinedMetadataValue);
        instance.setPosition(position);
        instance.setProjectMetadataKey(projectMetadataKey);

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("Id hasn't been correctly serialized.", id, jsonObject.getLong("id"));
        assertEquals("PredefinedMetadataValue hasn't been correctly serialized.", predefinedMetadataValue,
                jsonObject.getString("predefinedMetadataValue"));
        assertEquals("Position hasn't been correctly serialized.", position, jsonObject.getInt("position"));
        assertFalse("ProjectMetadataKey hasn't been correctly serialized.", jsonObject.isNull("projectMetadataKey"));
    }

    /**
     * <p>Tests {@link DirectProjectMetadataPredefinedValue#toJSONString()} method when not property has been set.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONStringEmpty() throws JSONDecodingException {
        long id = 0L;
        int position = 0;

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("Id hasn't been correctly serialized.", id, jsonObject.getLong("id"));
        assertTrue("PredefinedMetadataValue hasn't been correctly serialized.",
                jsonObject.isNull("predefinedMetadataValue"));
        assertEquals("Position hasn't been correctly serialized.", position, jsonObject.getInt("position"));
        assertTrue("ProjectMetadataKey hasn't been correctly serialized.", jsonObject.isNull("projectMetadataKey"));
    }
}
