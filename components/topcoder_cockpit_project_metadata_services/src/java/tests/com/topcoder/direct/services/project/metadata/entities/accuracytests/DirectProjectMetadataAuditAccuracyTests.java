/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataAudit;
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
 * <p>Tests {@link DirectProjectMetadataAudit} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class DirectProjectMetadataAuditAccuracyTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private DirectProjectMetadataAudit instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProjectMetadataAuditAccuracyTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new DirectProjectMetadataAudit();
    }

    /**
     * <p>Tests if {@link DirectProjectMetadataAudit} class implements {@link  java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {

        assertTrue("Class DirectProjectMetadataAudit does not implement Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests {@link DirectProjectMetadataAudit#DirectProjectMetadataAudit()} constructor.</p>
     */
    @Test
    public void testCtor() {

        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests the <code>id</code> property, this method tests both {@link DirectProjectMetadataAudit#getId()} and
     * {@link DirectProjectMetadataAudit#setId(long)} methods.</p>
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
     * DirectProjectMetadataAudit#getAuditActionTypeId()} and {@link DirectProjectMetadataAudit#setAuditActionTypeId(int)}
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
     * DirectProjectMetadataAudit#getActionDate()} and {@link DirectProjectMetadataAudit#setActionDate(java.util.Date)}
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
     * DirectProjectMetadataAudit#getActionUserId()} and {@link DirectProjectMetadataAudit#setActionUserId(long)}
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
     * <p>Tests the <code>projectMetadataId</code> property, this method tests both {@link
     * DirectProjectMetadataAudit#getProjectMetadataId()} and {@link DirectProjectMetadataAudit#setProjectMetadataId(long)}
     * methods.</p>
     */
    @Test
    public void testProjectMetadataId() {
        assertEquals("Property projectMetadataId should be 0.", 0L, instance.getProjectMetadataId());

        long projectMetadataId = 10L;
        instance.setProjectMetadataId(projectMetadataId);

        assertEquals("Invalid projectMetadataId property value.", projectMetadataId, instance.getProjectMetadataId());
    }

    /**
     * <p>Tests the <code>tcDirectProjectId</code> property, this method tests both {@link
     * DirectProjectMetadataAudit#getTcDirectProjectId()} and {@link DirectProjectMetadataAudit#setTcDirectProjectId(long)}
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
     * <p>Tests the <code>projectMetadataKeyId</code> property, this method tests both {@link
     * DirectProjectMetadataAudit#getProjectMetadataKeyId()} and {@link DirectProjectMetadataAudit#setProjectMetadataKeyId(long)}
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
     * <p>Tests the <code>metadataValue</code> property, this method tests both {@link
     * DirectProjectMetadataAudit#getMetadataValue()} and {@link DirectProjectMetadataAudit#setMetadataValue(String)}
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
     * <p>Tests {@link DirectProjectMetadataAudit#toJSONString()} method.</p>
     *
     * @throws com.topcoder.json.object.io.JSONDecodingException
     *          if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONString() throws JSONDecodingException {
        long id = 10L;
        int auditActionTypeId = 1;
        Date actionDate = new Date();
        int actionUserId = 2;
        long projectMetadataId = 3L;
        long projectMetadataKeyId = 4L;
        long tcDirectProjectId = 20L;
        String metadataValue = "metadataValue";

        instance.setId(id);
        instance.setAuditActionTypeId(auditActionTypeId);
        instance.setActionDate(actionDate);
        instance.setActionUserId(actionUserId);

        instance.setTcDirectProjectId(tcDirectProjectId);
        instance.setProjectMetadataId(projectMetadataId);
        instance.setProjectMetadataKeyId(projectMetadataKeyId);
        instance.setMetadataValue(metadataValue);

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

        assertEquals("TcDirectProjectId hasn't been correctly serialized.", tcDirectProjectId,
                jsonObject.getLong("tcDirectProjectId"));
        assertEquals("ProjectMetadataId hasn't been correctly serialized.", projectMetadataId,
                jsonObject.getLong("projectMetadataId"));
        assertEquals("ProjectMetadataKeyId hasn't been correctly serialized.", projectMetadataKeyId,
                jsonObject.getLong("projectMetadataKeyId"));
        assertEquals("MetadataValue hasn't been correctly serialized.", metadataValue,
                jsonObject.getString("metadataValue"));
    }

    /**
     * <p>Tests {@link DirectProjectMetadataAudit#toJSONString()} method when not property has been set.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONStringEmpty() throws JSONDecodingException {
        long id = 0L;
        int auditActionTypeId = 0;
        Date actionDate = new Date();
        int actionUserId = 0;
        long projectMetadataId = 0L;
        long projectMetadataKeyId = 0L;
        long tcDirectProjectId = 0L;

        instance.setId(id);
        instance.setAuditActionTypeId(auditActionTypeId);
        instance.setActionDate(actionDate);
        instance.setActionUserId(actionUserId);

        instance.setTcDirectProjectId(tcDirectProjectId);
        instance.setProjectMetadataId(projectMetadataId);
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

        assertEquals("TcDirectProjectId hasn't been correctly serialized.", tcDirectProjectId,
                jsonObject.getLong("tcDirectProjectId"));
        assertEquals("ProjectMetadataId hasn't been correctly serialized.", projectMetadataId,
                jsonObject.getLong("projectMetadataId"));
        assertEquals("ProjectMetadataKeyId hasn't been correctly serialized.", projectMetadataKeyId,
                jsonObject.getLong("projectMetadataKeyId"));
        assertTrue("MetadataValue hasn't been correctly serialized.",
                jsonObject.isNull("metadataValue"));
    }
}
