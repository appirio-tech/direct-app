/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
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
 * <p>Tests {@link DirectProjectMetadata} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class DirectProjectMetadataAccuracyTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private DirectProjectMetadata instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProjectMetadataAccuracyTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new DirectProjectMetadata();
    }

    /**
     * <p>Tests if {@link DirectProjectMetadata} class implements {@link  java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {

        assertTrue("Class DirectProjectMetadata does not implement Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests {@link DirectProjectMetadata#DirectProjectMetadata()} constructor.</p>
     */
    @Test
    public void testCtor() {

        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests the <code>id</code> property, this method tests both {@link DirectProjectMetadata#getId()} and {@link
     * DirectProjectMetadata#setId(long)} methods.</p>
     */
    @Test
    public void testId() {
        assertEquals("Property id should be 0.", 0L, instance.getId());

        long id = 10L;
        instance.setId(id);

        assertEquals("Invalid id property value.", id, instance.getId());
    }

    /**
     * <p>Tests the <code>tcDirectProjectId</code> property, this method tests both {@link
     * DirectProjectMetadata#getTcDirectProjectId()} and {@link DirectProjectMetadata#setTcDirectProjectId(long)}
     * methods.</p>
     */
    @Test
    public void testTcDirectProjectId() {
        assertEquals("Property tcDirectProjectId should be 0.", 0L, instance.getTcDirectProjectId());

        long tcDirectProjectId = 10L;
        instance.setTcDirectProjectId(tcDirectProjectId);

        assertEquals("Invalid tcDirectProjectId property value.", tcDirectProjectId, instance.getTcDirectProjectId());
    }


    /**
     * <p>Tests the <code>projectMetadataKey</code> property, this method tests both {@link
     * DirectProjectMetadata#getProjectMetadataKey()} and {@link DirectProjectMetadata#setProjectMetadataKey(DirectProjectMetadataKey)}
     * methods.</p>
     */
    @Test
    public void testProjectMetadataKey() {
        assertNull("Property projectMetadataKey should be null.", instance.getProjectMetadataKey());

        DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();
        instance.setProjectMetadataKey(projectMetadataKey);

        assertEquals("Invalid projectMetadataKey property value.", projectMetadataKey, instance.getProjectMetadataKey());
    }

    /**
     * <p>Tests the <code>metadataValue</code> property, this method tests both {@link
     * DirectProjectMetadata#getMetadataValue()} and {@link DirectProjectMetadata#setMetadataValue(String)}
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
     * <p>Tests {@link DirectProjectMetadata#toJSONString()} method.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONString() throws JSONDecodingException {
        long id = 10L;
        long tcDirectProjectId = 20L;
        String metadataValue = "metadataValue";
        DirectProjectMetadataKey directProjectMetadataKey = new DirectProjectMetadataKey();
        instance.setId(id);
        instance.setTcDirectProjectId(tcDirectProjectId);
        instance.setMetadataValue(metadataValue);
        instance.setProjectMetadataKey(directProjectMetadataKey);

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("Id hasn't been correctly serialized.", id, jsonObject.getLong("id"));
        assertEquals("TcDirectProjectId hasn't been correctly serialized.", tcDirectProjectId,
                jsonObject.getLong("tcDirectProjectId"));
        assertEquals("MetadataValue hasn't been correctly serialized.", metadataValue,
                jsonObject.getString("metadataValue"));
        assertFalse("DirectProjectMetadataKey hasn't been correctly serialized.",
                jsonObject.isNull("projectMetadataKey"));
    }

    /**
     * <p>Tests {@link DirectProjectMetadata#toJSONString()} method when not property has been set.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONStringEmpty() throws JSONDecodingException {
        long id = 0L;
        long tcDirectProjectId = 0L;

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("Id hasn't been correctly serialized.", id, jsonObject.getLong("id"));
        assertEquals("TcDirectProjectId hasn't been correctly serialized.", tcDirectProjectId,
                jsonObject.getLong("tcDirectProjectId"));
        assertTrue("MetadataValue hasn't been correctly serialized.",
                jsonObject.isNull("metadataValue"));
        assertTrue("DirectProjectMetadataKey hasn't been correctly serialized.",
                jsonObject.isNull("projectMetadataKey"));
    }
}
