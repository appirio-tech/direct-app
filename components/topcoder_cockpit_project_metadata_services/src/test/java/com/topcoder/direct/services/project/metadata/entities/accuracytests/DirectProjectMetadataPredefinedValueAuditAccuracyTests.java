/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValueAudit;
import com.topcoder.json.object.JSONObject;
import com.topcoder.json.object.io.JSONDecodingException;
import com.topcoder.json.object.io.StandardJSONDecoder;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>Tests {@link DirectProjectMetadataPredefinedValueAudit} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class DirectProjectMetadataPredefinedValueAuditAccuracyTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private DirectProjectMetadataPredefinedValueAudit instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProjectMetadataPredefinedValueAuditAccuracyTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new DirectProjectMetadataPredefinedValueAudit();
    }

    /**
     * <p>Tests if {@link DirectProjectMetadataPredefinedValueAudit} class implements {@link  java.io.Serializable}
     * interface.</p>
     */
    @Test
    public void testSerializable() {

        assertTrue("Class DirectProjectMetadataPredefinedValueAudit does not implement Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests {@link DirectProjectMetadataPredefinedValueAudit#DirectProjectMetadataPredefinedValueAudit()}
     * constructor.</p>
     */
    @Test
    public void testCtor() {

        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests the <code>id</code> property, this method tests both {@link DirectProjectMetadataPredefinedValueAudit#getId()}
     * and {@link DirectProjectMetadataPredefinedValueAudit#setId(long)} methods.</p>
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
     * DirectProjectMetadataPredefinedValueAudit#getAuditActionTypeId()} and {@link
     * DirectProjectMetadataPredefinedValueAudit#setAuditActionTypeId(int)} methods.</p>
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
     * DirectProjectMetadataPredefinedValueAudit#getActionDate()} and {@link DirectProjectMetadataPredefinedValueAudit#setActionDate(java.util.Date)}
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
     * DirectProjectMetadataPredefinedValueAudit#getActionUserId()} and {@link DirectProjectMetadataPredefinedValueAudit#setActionUserId(long)}
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
     * <p>Tests the <code>projectMetadataPredefinedValueId</code> property, this method tests both {@link
     * DirectProjectMetadataPredefinedValueAudit#getProjectMetadataPredefinedValueId()} ()} and {@link
     * DirectProjectMetadataPredefinedValueAudit#setProjectMetadataPredefinedValueId(long)} methods.</p>
     */
    @Test
    public void testProjectMetadataPredefinedValueId() {
        assertEquals("Property projectMetadataPredefinedValueId should be 0.", 0L,
                instance.getProjectMetadataPredefinedValueId());

        long projectMetadataPredefinedValueId = 10L;
        instance.setProjectMetadataPredefinedValueId(projectMetadataPredefinedValueId);

        assertEquals("Invalid projectMetadataPredefinedValueId property value.", projectMetadataPredefinedValueId,
                instance.getProjectMetadataPredefinedValueId());
    }

    /**
     * <p>Tests the <code>predefinedMetadataValue</code> property, this method tests both {@link
     * DirectProjectMetadataPredefinedValueAudit#getPredefinedMetadataValue()} and {@link
     * DirectProjectMetadataPredefinedValueAudit#setPredefinedMetadataValue(String)} methods.</p>
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
     * <p>Tests the <code>position</code> property, this method tests both {@link DirectProjectMetadataPredefinedValueAudit#getPosition()}
     * ()} and {@link DirectProjectMetadataPredefinedValueAudit#setPosition(int)}methods.</p>
     */
    @Test
    public void testPosition() {
        assertEquals("Property position should be 0.", 0, instance.getPosition());

        int position = 10;
        instance.setPosition(position);

        assertEquals("Invalid position property value.", position, instance.getPosition());
    }

    /**
     * <p>Tests the <code>projectMetadataKeyId</code> property, this method tests both {@link
     * DirectProjectMetadataPredefinedValueAudit#getProjectMetadataKeyId()} ()} and {@link
     * DirectProjectMetadataPredefinedValueAudit#setProjectMetadataKeyId(long)} methods.</p>
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
     * <p>Tests {@link DirectProjectMetadataPredefinedValueAudit#toJSONString()} method.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONString() throws JSONDecodingException {
        long id = 10L;
        int auditActionTypeId = 1;
        Date actionDate = new Date();
        int actionUserId = 2;
        long projectMetadataPredefinedValueId = 4L;
        String predefinedMetadataValue = "predefinedMetadataValue";
        int position = 5;
        long projectMetadataKeyId = 6L;

        instance.setId(id);
        instance.setAuditActionTypeId(auditActionTypeId);
        instance.setActionDate(actionDate);
        instance.setActionUserId(actionUserId);

        instance.setProjectMetadataPredefinedValueId(projectMetadataPredefinedValueId);
        instance.setPredefinedMetadataValue(predefinedMetadataValue);
        instance.setPosition(position);
        instance.setProjectMetadataKeyId(projectMetadataKeyId);

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
        assertEquals("ProjectMetadataPredefinedValueId hasn't been correctly serialized.",
                projectMetadataPredefinedValueId, jsonObject.getLong("projectMetadataPredefinedValueId"));
        assertEquals("PredefinedMetadataValue hasn't been correctly serialized.", predefinedMetadataValue,
                jsonObject.getString("predefinedMetadataValue"));
        assertEquals("Position hasn't been correctly serialized.", position, jsonObject.getInt("position"));
        assertEquals("ProjectMetadataKeyId hasn't been correctly serialized.", projectMetadataKeyId,
                jsonObject.getLong("projectMetadataKeyId"));
    }

    /**
     * <p>Tests {@link DirectProjectMetadataPredefinedValueAudit#toJSONString()} method when not property has been
     * set.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONStringEmpty() throws JSONDecodingException {
        long id = 0L;
        int auditActionTypeId = 0;
        int actionUserId = 0;
        long projectMetadataPredefinedValueId = 0L;
        int position = 0;
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
        assertEquals("ProjectMetadataPredefinedValueId hasn't been correctly serialized.",
                projectMetadataPredefinedValueId, jsonObject.getLong("projectMetadataPredefinedValueId"));
        assertTrue("PredefinedMetadataValue hasn't been correctly serialized.",
                jsonObject.isNull("predefinedMetadataValue"));
        assertEquals("Position hasn't been correctly serialized.", position, jsonObject.getInt("position"));
        assertEquals("ProjectMetadataKeyId hasn't been correctly serialized.", projectMetadataKeyId,
                jsonObject.getLong("projectMetadataKeyId"));
    }
}


