/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dao.AuditEntity;
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
 * <p>Tests {@link AuditEntity} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class AuditEntityAccuracyTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private AuditEntity instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AuditEntityAccuracyTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new MockAuditEntity();
    }

    /**
     * <p>Tests if {@link AuditEntity} class implements {@link  java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {

        assertTrue("Class AuditEntity does not implement Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests {@link AuditEntity#AuditEntity()} constructor.</p>
     */
    @Test
    public void testCtor() {

        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests the <code>id</code> property, this method tests both {@link AuditEntity#getId()} and {@link
     * AuditEntity#setId(long)} methods.</p>
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
     * AuditEntity#getAuditActionTypeId()} and {@link AuditEntity#setAuditActionTypeId(int)} methods.</p>
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
     * <p>Tests the <code>actionDate</code> property, this method tests both {@link AuditEntity#getActionDate()} and
     * {@link AuditEntity#setActionDate(Date)} methods.</p>
     */
    @Test
    public void testActionDate() {
        assertNull("Property actionDate should be null.", instance.getActionDate());

        Date actionDate = new Date();
        instance.setActionDate(actionDate);

        assertEquals("Invalid actionDate value.", actionDate, instance.getActionDate());
    }

    /**
     * <p>Tests the <code>actionUserId</code> property, this method tests both {@link AuditEntity#getActionUserId()} and
     * {@link AuditEntity#setActionUserId(long)} methods.</p>
     */
    @Test
    public void testActionUserId() {
        assertEquals("Property actionUserId should be 0.", 0L, instance.getActionUserId());

        long actionUserId = 10L;
        instance.setActionUserId(actionUserId);

        assertEquals("Invalid actionUserId property value.", actionUserId, instance.getActionUserId());
    }

    /**
     * <p>Tests {@link AuditEntity#toJSONString()} method.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONString() throws JSONDecodingException {
        long id = 10L;
        int auditActionTypeId = 1;
        Date actionDate = new Date();
        int actionUserId = 2;
        instance.setId(id);
        instance.setAuditActionTypeId(auditActionTypeId);
        instance.setActionDate(actionDate);
        instance.setActionUserId(actionUserId);

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
    }

    /**
     * <p>Tests {@link AuditEntity#toJSONString()} method when not property has been set.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONStringEmpty() throws JSONDecodingException {
        long id = 0L;
        int auditActionTypeId = 0;
        int actionUserId = 0;

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
    }
}
