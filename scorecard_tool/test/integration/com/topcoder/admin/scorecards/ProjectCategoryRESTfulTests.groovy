/*
* Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
*/
package com.topcoder.admin.scorecards;

import static org.junit.Assert.*
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient

import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * This class is unit test class for the RESTful service of <code>ProjectCategory</code>
 * domain.
 * 
 * @author TCSASSEMBER
 * @version 1.0
 */
class ProjectCategoryRESTfulTests extends GroovyTestCase {
    /**
     * Set up the environment.
     */
    public void setUp() throws Exception {
    }

    /**
     * Tear down the environment.
     */
    public void tearDown() throws Exception {
    }

    /**
     * Test the RESTful functionality.
     */
    void testRESTfulCURD() {
        def client = new RESTClient("http://localhost:8080/scorecard_tool/json/")
        def response
        
        // first, we create a project type for test
        response = client.post(
            path: "project-type",
            body: [ data: [ name: "test project type", description: "test project type", isGeneric: false, dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        def projectTypeId = response.data.data.id
        
        // POST, create a new instance
        response = client.post(
            path: "project-category",
            body: [ data: [ name: "test project category", description: "test project category", display: true, displayOrder: 1, projectType: projectTypeId, dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        assertEquals "test project category", response.data.data.name
        assertEquals "test project category", response.data.data.description
        assertTrue response.data.data.display
        assertEquals 1, response.data.data.displayOrder
        assertEquals projectTypeId, response.data.data.projectType
        
        def id = response.data.data.id
        
        // GET (list)
        response = client.get(path: "project-category")
        assertTrue response.data.success
        
        // GET (single item)
        response = client.get(path: "project-category/${id}")
        assertTrue response.data.success
        assertEquals "test project category", response.data.data.name
        assertEquals "test project category", response.data.data.description
        assertTrue response.data.data.display
        assertEquals 1, response.data.data.displayOrder
        assertEquals projectTypeId, response.data.data.projectType
        
        // PUT
        response = client.put(
            path: "project-category/${id}",
            body: [ data: [ name: "test project category2", description: "test project category2", display: false, displayOrder: 2, projectType: projectTypeId, dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        assertEquals "test project category2", response.data.data.name
        assertEquals "test project category2", response.data.data.description
        assertFalse response.data.data.display
        assertEquals 2, response.data.data.displayOrder
        assertEquals projectTypeId, response.data.data.projectType
        
        // DELETE
        response = client.delete(path: "project-category/${id}")
        assertTrue response.data.success
        assertEquals "test project category2", response.data.data.name
        assertEquals "test project category2", response.data.data.description
        
        // verify the item is deleted
        try {
            response = client.get(path: "project-category/${id}")
            fail "the item should be deleted"
        } catch (HttpResponseException e) {
            assert e.response.status == 404
            assert e.response.data.success == false
            assert e.response.data.message.contains("not found")
        }
        
        response = client.delete(path: "project-type/${projectTypeId}")
        assertTrue response.data.success
    }
    
    /**
     * Error will occurs when saving the instance.
     */
    void testRESTfulSavingFail() {
        def client = new RESTClient("http://localhost:8080/scorecard_tool/json/")
        try {
            client.post(
                path: "project-category",
                body: [ data: [ : ] ],
                requestContentType : ContentType.JSON.toString()
            )
            fail "Should throw HttpResponseException"
        } catch (HttpResponseException e) {
            assert e.response.status == 500
            assert e.response.data.success == false
            assert e.response.data.message.contains("null")
        }
    }
}
