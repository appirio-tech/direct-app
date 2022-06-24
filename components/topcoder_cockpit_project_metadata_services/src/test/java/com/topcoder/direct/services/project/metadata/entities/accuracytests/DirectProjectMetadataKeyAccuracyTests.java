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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>Tests {@link DirectProjectMetadataKey} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class DirectProjectMetadataKeyAccuracyTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private DirectProjectMetadataKey instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProjectMetadataKeyAccuracyTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new DirectProjectMetadataKey();
    }

    /**
     * <p>Tests if {@link DirectProjectMetadataKey} class implements {@link  java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {

        assertTrue("Class DirectProjectMetadataKey does not implement Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests {@link DirectProjectMetadataKey#DirectProjectMetadataKey()} constructor.</p>
     */
    @Test
    public void testCtor() {

        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests the <code>id</code> property, this method tests both {@link DirectProjectMetadataKey#getId()} and {@link
     * DirectProjectMetadataKey#setId(long)} methods.</p>
     */
    @Test
    public void testId() {
        assertEquals("Property id should be 0.", 0L, instance.getId());

        long id = 10L;
        instance.setId(id);

        assertEquals("Invalid id property value.", id, instance.getId());
    }

    /**
     * <p>Tests the <code>name</code> property, this method tests both {@link DirectProjectMetadataKey#getName()} and
     * {@link DirectProjectMetadataKey#setName(String)} methods.</p>
     */
    @Test
    public void testName() {
        assertNull("Property name should be null.", instance.getName());

        String name = "name";
        instance.setName(name);

        assertEquals("Invalid name property value.", name, instance.getName());
    }

    /**
     * <p>Tests the <code>description</code> property, this method tests both {@link
     * DirectProjectMetadataKey#getDescription()} and {@link DirectProjectMetadataKey#setDescription(String)}
     * methods.</p>
     */
    @Test
    public void testDescription() {
        assertNull("Property description should be null.", instance.getDescription());

        String description = "description";
        instance.setDescription(description);

        assertEquals("Invalid description property value.", description, instance.getDescription());
    }

    /**
     * <p>Tests the <code>grouping</code> property, this method tests both {@link DirectProjectMetadataKey#getGrouping()}
     * and {@link DirectProjectMetadataKey#setGrouping(Boolean)} methods.</p>
     */
    @Test
    public void testGrouping() {
        assertNull("Property grouping should be null.", instance.getGrouping());

        Boolean grouping = true;
        instance.setGrouping(grouping);

        assertEquals("Invalid grouping property value.", grouping, instance.getGrouping());
    }

    /**
     * <p>Tests the <code>clientId</code> property, this method tests both {@link DirectProjectMetadataKey#getClientId()}
     * and {@link DirectProjectMetadataKey#setClientId(Long)} methods.</p>
     */
    @Test
    public void testClientId() {
        assertNull("Property clientId should be null.", instance.getClientId());

        Long clientId = 10L;
        instance.setClientId(clientId);

        assertEquals("Invalid clientId property value.", clientId, (Long) instance.getClientId());
    }

    /**
     * <p>Tests the <code>predefinedValues</code> property, this method tests both {@link
     * DirectProjectMetadataKey#getPredefinedValues()} and {@link DirectProjectMetadataKey#setPredefinedValues(List)}
     * methods.</p>
     */
    @Test
    public void testPredefinedValues() {
        assertNull("Property predefinedValues should be null.", instance.getPredefinedValues());

        List<DirectProjectMetadataPredefinedValue> predefinedValues =
                Arrays.asList(new DirectProjectMetadataPredefinedValue(), new DirectProjectMetadataPredefinedValue());
        instance.setPredefinedValues(predefinedValues);

        assertEquals("Invalid predefinedValues property value.", predefinedValues, instance.getPredefinedValues());
        assertEquals("Invalid predefinedValues property size.", 2, instance.getPredefinedValues().size());
    }

    /**
     * <p>Tests the <code>single</code> property, this method tests both {@link DirectProjectMetadataKey#isSingle()} and
     * {@link DirectProjectMetadataKey#setSingle(boolean)} methods.</p>
     */
    @Test
    public void testSingle() {
        assertFalse("Property single should be false.", instance.isSingle());

        Boolean single = true;
        instance.setSingle(single);

        assertEquals("Invalid single property value.", single, instance.isSingle());
    }

    /**
     * <p>Tests {@link DirectProjectMetadataKey#toJSONString()} method.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONString() throws JSONDecodingException {
        long id = 10L;
        String name = "name";
        String description = "description";
        Boolean grouping = Boolean.TRUE;
        Long clientId = 20L;
        List<DirectProjectMetadataPredefinedValue> predefinedValues =
                Arrays.asList(new DirectProjectMetadataPredefinedValue(), new DirectProjectMetadataPredefinedValue());
        boolean single = true;
        instance.setId(id);
        instance.setName(name);
        instance.setDescription(description);
        instance.setGrouping(grouping);
        instance.setClientId(clientId);
        instance.setPredefinedValues(predefinedValues);
        instance.setSingle(single);

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("Id hasn't been correctly serialized.", id, jsonObject.getLong("id"));
        assertEquals("Name hasn't been correctly serialized.", name, jsonObject.getString("name"));
        assertEquals("Description hasn't been correctly serialized.", description, jsonObject.getString("description"));
        assertEquals("Grouping hasn't been correctly serialized.", grouping, jsonObject.getBoolean("grouping"));
        assertEquals("ClientId hasn't been correctly serialized.", clientId, (Long) jsonObject.getLong("clientId"));
        assertEquals("PredefinedValues hasn't been correctly serialized.", 2,
                jsonObject.getArray("predefinedValues").getSize());
        assertEquals("Single hasn't been correctly serialized.", single, jsonObject.getBoolean("single"));
    }

    /**
     * <p>Tests {@link DirectProjectMetadataKey#toJSONString()} method when not property has been set.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONStringEmpty() throws JSONDecodingException {
        long id = 0L;

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("Id hasn't been correctly serialized.", id, jsonObject.getLong("id"));
        assertTrue("Name hasn't been correctly serialized.", jsonObject.isNull("name"));
        assertTrue("Description hasn't been correctly serialized.", jsonObject.isNull("description"));
        assertTrue("Grouping hasn't been correctly serialized.", jsonObject.isNull("grouping"));
        assertTrue("ClientId hasn't been correctly serialized.", jsonObject.isNull("clientId"));
        assertTrue("PredefinedValues hasn't been correctly serialized.", jsonObject.isNull("predefinedValues"));
        assertFalse("Single hasn't been correctly serialized.", jsonObject.getBoolean("single"));
    }
}
