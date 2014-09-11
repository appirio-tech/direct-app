/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKeyAudit;
import com.topcoder.json.object.JSONObject;
import com.topcoder.json.object.io.JSONDecodingException;
import com.topcoder.json.object.io.StandardJSONDecoder;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>Tests {@link DirectProjectMetadataKeyAudit} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class DirectProjectMetadataKeyAuditAccuracyTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private DirectProjectMetadataKeyAudit instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProjectMetadataKeyAuditAccuracyTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new DirectProjectMetadataKeyAudit();
    }

    /**
     * <p>Tests if {@link DirectProjectMetadataKeyAudit} class implements {@link  java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {

        assertTrue("Class DirectProjectMetadataKeyAudit does not implement Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests {@link DirectProjectMetadataKeyAudit#DirectProjectMetadataKeyAudit()} constructor.</p>
     */
    @Test
    public void testCtor() {

        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests the <code>id</code> property, this method tests both {@link DirectProjectMetadataKeyAudit#getId()} and
     * {@link DirectProjectMetadataKeyAudit#setId(long)} methods.</p>
     */
    @Test
    public void testId() {
        assertEquals("Property id should be 0.", 0L, instance.getId());

        long id = 10L;
        instance.setId(id);

        assertEquals("Invalid id property value.", id, instance.getId());
    }

    /**
     * <p>Tests the <code>auditActionTypeId</code> property, this method tests both {@link
     * DirectProjectMetadataKeyAudit#getAuditActionTypeId()} and {@link DirectProjectMetadataKeyAudit#setAuditActionTypeId(int)}
     * methods.</p>
     */
    @Test
    public void testAuditActionTypeId() {
        assertEquals("Property auditActionTypeId should be 0.", 0, instance.getAuditActionTypeId());

        int auditActionTypeId = 10;
        instance.setAuditActionTypeId(auditActionTypeId);

        assertEquals("Invalid auditActionTypeId property value.", auditActionTypeId,
                instance.getAuditActionTypeId());
    }

    /**
     * <p>Tests the <code>actionDate</code> property, this method tests both {@link
     * DirectProjectMetadataKeyAudit#getActionDate()} and {@link DirectProjectMetadataKeyAudit#setActionDate(java.util.Date)}
     * methods.</p>
     */
    @Test
    public void testActionDate() {
        assertNull("Property actionDate should be null.", instance.getActionDate());

        Date actionDate = new Date();
        instance.setActionDate(actionDate);

        assertEquals("Invalid actionDate value.", actionDate, instance.getActionDate());
    }

    /**
     * <p>Tests the <code>actionUserId</code> property, this method tests both {@link
     * DirectProjectMetadataKeyAudit#getActionUserId()} and {@link DirectProjectMetadataKeyAudit#setActionUserId(long)}
     * methods.</p>
     */
    @Test
    public void testActionUserId() {
        assertEquals("Property actionUserId should be 0.", 0L, instance.getActionUserId());

        long actionUserId = 10L;
        instance.setActionUserId(actionUserId);

        assertEquals("Invalid actionUserId property value.", actionUserId, instance.getActionUserId());
    }

    /**
     * <p>Tests the <code>projectMetadataKeyId</code> property, this method tests both {@link
     * DirectProjectMetadataKeyAudit#getProjectMetadataKeyId()} and {@link DirectProjectMetadataKeyAudit#setProjectMetadataKeyId(long)}
     * methods.</p>
     */
    @Test
    public void testProjectMetadataKeyId() {
        assertEquals("Property projectMetadataKeyId should be 0.", 0L, instance.getProjectMetadataKeyId());

        long projectMetadataKeyId = 10L;
        instance.setProjectMetadataKeyId(projectMetadataKeyId);

        assertEquals("Invalid projectMetadataKeyId property value.", projectMetadataKeyId, instance.getProjectMetadataKeyId());
    }

    /**
     * <p>Tests the <code>name</code> property, this method tests both {@link DirectProjectMetadataKeyAudit#getName()}
     * and {@link DirectProjectMetadataKeyAudit#setName(String)} methods.</p>
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
     * DirectProjectMetadataKeyAudit#getDescription()} and {@link DirectProjectMetadataKeyAudit#setDescription(String)}
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
     * <p>Tests the <code>grouping</code> property, this method tests both {@link DirectProjectMetadataKeyAudit#getGrouping()}
     * and {@link DirectProjectMetadataKeyAudit#setGrouping(Boolean)} methods.</p>
     */
    @Test
    public void testGrouping() {
        assertNull("Property grouping should be null.", instance.getGrouping());

        Boolean grouping = true;
        instance.setGrouping(grouping);

        assertEquals("Invalid grouping property value.", grouping, instance.getGrouping());
    }

    /**
     * <p>Tests the <code>clientId</code> property, this method tests both {@link DirectProjectMetadataKeyAudit#getClientId()}
     * and {@link DirectProjectMetadataKeyAudit#setClientId(Long)} methods.</p>
     */
    @Test
    public void testClientId() {
        assertNull("Property clientId should be null.", instance.getClientId());

        Long clientId = 10L;
        instance.setClientId(clientId);

        assertEquals("Invalid clientId property value.", clientId, instance.getClientId());
    }

    /**
     * <p>Tests the <code>single</code> property, this method tests both {@link DirectProjectMetadataKeyAudit#isSingle()}
     * and {@link DirectProjectMetadataKeyAudit#setSingle(boolean)} methods.</p>
     */
    @Test
    public void testSingle() {
        assertFalse("Property single should be false.", instance.isSingle());

        Boolean single = true;
        instance.setSingle(single);

        assertEquals("Invalid single property value.", single, instance.isSingle());
    }

    /**
     * <p>Tests {@link DirectProjectMetadataKeyAudit#toJSONString()} method.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONString() throws JSONDecodingException {
        long id = 10L;
        int auditActionTypeId = 1;
        Date actionDate = new Date();
        int actionUserId = 2;

        long projectMetadataKeyId = 16L;
        String name = "name";
        String description = "description";
        Boolean grouping = Boolean.TRUE;
        Long clientId = 20L;
        boolean single = true;

        instance.setId(id);
        instance.setAuditActionTypeId(auditActionTypeId);
        instance.setActionDate(actionDate);
        instance.setActionUserId(actionUserId);

        instance.setProjectMetadataKeyId(projectMetadataKeyId);
        instance.setName(name);
        instance.setDescription(description);
        instance.setGrouping(grouping);
        instance.setClientId(clientId);
        instance.setSingle(single);

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("Id hasn't been correctly serialized.", id, jsonObject.getLong("id"));
        assertEquals("AuditActionTypeId hasn't been correctly serialized.", auditActionTypeId,
                jsonObject.getInt("auditActionTypeId"));
        assertEquals("ActionDate hasn't been correctly serialized.", AccuracyTestsHelper.formatDate(actionDate),
                jsonObject.getString("actionDate"));
        assertEquals("ActionUserId hasn't been correctly serialized.", actionUserId,
                jsonObject.getInt("actionUserId"));

        assertEquals("ProjectMetadataKeyId hasn't been correctly serialized.", projectMetadataKeyId,
                jsonObject.getLong("projectMetadataKeyId"));
        assertEquals("Name hasn't been correctly serialized.", name, jsonObject.getString("name"));
        assertEquals("Description hasn't been correctly serialized.", description, jsonObject.getString("description"));
        assertEquals("Grouping hasn't been correctly serialized.", grouping, jsonObject.getBoolean("grouping"));
        assertEquals("ClientId hasn't been correctly serialized.", clientId, (Long) jsonObject.getLong("clientId"));
        assertEquals("Single hasn't been correctly serialized.", single, jsonObject.getBoolean("single"));
    }

    /**
     * <p>Tests {@link DirectProjectMetadataKeyAudit#toJSONString()} method when not property has been set.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONStringEmpty() throws JSONDecodingException {
        long id = 0L;
        int auditActionTypeId = 0;
        int actionUserId = 0;
        long projectMetadataKeyId = 0L;

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("Id hasn't been correctly serialized.", id, jsonObject.getLong("id"));
        assertEquals("AuditActionTypeId hasn't been correctly serialized.", auditActionTypeId,
                jsonObject.getInt("auditActionTypeId"));
        assertTrue("ActionDate hasn't been correctly serialized.", jsonObject.isNull("actionDate"));
        assertEquals("ActionUserId hasn't been correctly serialized.", actionUserId,
                jsonObject.getInt("actionUserId"));

        assertEquals("ProjectMetadataKeyId hasn't been correctly serialized.", projectMetadataKeyId,
                jsonObject.getLong("projectMetadataKeyId"));
        assertTrue("Name hasn't been correctly serialized.", jsonObject.isNull("name"));
        assertTrue("Description hasn't been correctly serialized.", jsonObject.isNull("description"));
        assertTrue("Grouping hasn't been correctly serialized.", jsonObject.isNull("grouping"));
        assertTrue("ClientId hasn't been correctly serialized.", jsonObject.isNull("clientId"));
        assertFalse("Single hasn't been correctly serialized.", jsonObject.getBoolean("single"));
    }
}
