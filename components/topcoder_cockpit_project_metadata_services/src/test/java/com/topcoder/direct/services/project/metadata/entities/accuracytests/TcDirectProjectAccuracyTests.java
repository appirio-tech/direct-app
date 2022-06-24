/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
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
 * <p>Tests {@link TcDirectProject} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class TcDirectProjectAccuracyTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private TcDirectProject instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TcDirectProjectAccuracyTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new TcDirectProject();
    }

    /**
     * <p>Tests if {@link TcDirectProject} class implements {@link  java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {

        assertTrue("Class TcDirectProject does not implement Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests {@link TcDirectProject#TcDirectProject()} constructor.</p>
     */
    @Test
    public void testCtor() {

        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests the <code>projectId</code> property, this method tests both {@link TcDirectProject#getProjectId()} and
     * {@link TcDirectProject#setProjectId(long)} methods.</p>
     */
    @Test
    public void testProjectId() {
        assertEquals("Property projectId should be 0.", 0L, instance.getProjectId());

        long projectId = 10L;
        instance.setProjectId(projectId);

        assertEquals("Invalid projectId property value.", projectId, instance.getProjectId());
    }

    /**
     * <p>Tests the <code>name</code> property, this method tests both {@link TcDirectProject#getName()} and {@link
     * TcDirectProject#setName(String)} methods.</p>
     */
    @Test
    public void testName() {
        assertNull("Property name should be null.", instance.getName());

        String name = "name";
        instance.setName(name);

        assertEquals("Invalid name property value.", name, instance.getName());
    }

    /**
     * <p>Tests the <code>description</code> property, this method tests both {@link TcDirectProject#getDescription()}
     * and {@link TcDirectProject#setDescription(String)} methods.</p>
     */
    @Test
    public void testDescription() {
        assertNull("Property description should be null.", instance.getDescription());

        String description = "description";
        instance.setDescription(description);

        assertEquals("Invalid description property value.", description, instance.getDescription());
    }

    /**
     * <p>Tests the <code>projectStatusId</code> property, this method tests both {@link
     * TcDirectProject#getProjectStatusId()} and {@link TcDirectProject#setProjectStatusId(int)} methods.</p>
     */
    @Test
    public void testProjectStatusId() {
        assertEquals("Property projectStatusId should be 0.", 0, instance.getProjectStatusId());

        int projectStatusId = 10;
        instance.setProjectStatusId(projectStatusId);

        assertEquals("Invalid projectStatusId property value.", projectStatusId, instance.getProjectStatusId());
    }

    /**
     * <p>Tests the <code>userId</code> property, this method tests both {@link TcDirectProject#getUserId()} and {@link
     * TcDirectProject#setUserId(int)} methods.</p>
     */
    @Test
    public void testUserId() {
        assertEquals("Property userId should be 0.", 0, instance.getUserId());

        int userId = 10;
        instance.setUserId(userId);

        assertEquals("Invalid userId property value.", userId, instance.getUserId());
    }

    /**
     * <p>Tests the <code>createDate</code> property, this method tests both {@link TcDirectProject#getCreateDate()} and
     * {@link TcDirectProject#setCreateDate(Date)} methods.</p>
     */
    @Test
    public void testCreateDate() {
        assertNull("Property createDate should be null.", instance.getCreateDate());

        Date createDate = new Date();
        instance.setCreateDate(createDate);

        assertEquals("Invalid createDate property value.", createDate, instance.getCreateDate());
    }

    /**
     * <p>Tests the <code>modifyDate</code> property, this method tests both {@link TcDirectProject#getModifyDate()} and
     * {@link TcDirectProject#setModifyDate(Date)} methods.</p>
     */
    @Test
    public void testModifyDate() {
        assertNull("Property modifyDate should be null.", instance.getModifyDate());

        Date modifyDate = new Date();
        instance.setModifyDate(modifyDate);

        assertEquals("Invalid modifyDate property value.", modifyDate, instance.getModifyDate());
    }

    /**
     * <p>Tests {@link TcDirectProject#toJSONString()} method.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONString() throws JSONDecodingException {
        long projectId = 10L;
        String name = "name";
        String description = "description";
        int projectStatusId = 20;
        int userId = 30;
        Date createDate = new Date();
        Date modifyDate = new Date();

        instance.setProjectId(projectId);
        instance.setName(name);
        instance.setDescription(description);
        instance.setProjectStatusId(projectStatusId);
        instance.setUserId(userId);
        instance.setCreateDate(createDate);
        instance.setModifyDate(modifyDate);

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("ProjectId hasn't been correctly serialized.", projectId,
                jsonObject.getLong("projectId"));
        assertEquals("Name hasn't been correctly serialized.", name,
                jsonObject.getString("name"));
        assertEquals("Description hasn't been correctly serialized.", description,
                jsonObject.getString("description"));
        assertEquals("ProjectStatusId hasn't been correctly serialized.", projectStatusId,
                jsonObject.getInt("projectStatusId"));
        assertEquals("UserId hasn't been correctly serialized.", userId,
                jsonObject.getInt("userId"));
        assertEquals("CreateDate hasn't been correctly serialized.", AccuracyTestsHelper.formatDate(createDate),
                jsonObject.getString("createDate"));
        assertEquals("ModifyDate hasn't been correctly serialized.", AccuracyTestsHelper.formatDate(modifyDate),
                jsonObject.getString("modifyDate"));
    }

    /**
     * <p>Tests {@link TcDirectProject#toJSONString()} method when not property has been set.</p>
     *
     * @throws JSONDecodingException if any error occurs when parsing the JSON
     */
    @Test
    public void testToJSONStringEmpty() throws JSONDecodingException {
        long projectId = 0L;
        int projectStatusId = 0;
        int userId = 0;

        // generates the json
        String json = instance.toJSONString();
        assertNotNull("Method toJSONString returned null.", json);
        assertTrue("Method toJSONString returned empty string.", json.length() > 0);

        // parsers the json
        JSONObject jsonObject = new StandardJSONDecoder().decodeObject(json);
        assertEquals("ProjectId hasn't been correctly serialized.", projectId,
                jsonObject.getLong("projectId"));
        assertTrue("Name hasn't been correctly serialized.", jsonObject.isNull("name"));
        assertTrue("Description hasn't been correctly serialized.", jsonObject.isNull("description"));
        assertEquals("ProjectStatusId hasn't been correctly serialized.", projectStatusId,
                jsonObject.getInt("projectStatusId"));
        assertEquals("UserId hasn't been correctly serialized.", userId,
                jsonObject.getInt("userId"));
        assertTrue("CreateDate hasn't been correctly serialized.", jsonObject.isNull("createDate"));
        assertTrue("ModifyDate hasn't been correctly serialized.", jsonObject.isNull("modifyDate"));
    }
}
