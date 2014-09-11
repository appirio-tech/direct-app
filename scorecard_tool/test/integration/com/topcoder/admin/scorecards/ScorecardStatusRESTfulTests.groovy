/*
* Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
*/
package com.topcoder.admin.scorecards

import grails.test.*
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient


/**
* This class is unit test class for the RESTful service of <code>ScorecardStatus</code>
* domain.
*
* @author TCSASSEMBER
* @version 1.0
*/
class ScorecardStatusRESTfulTests extends GroovyTestCase {
    /**
    * Set up the environment.
    */
    protected void setUp() {
        super.setUp()
    }

    /**
    * Tear down the environment.
    */
    protected void tearDown() {
        super.tearDown()
    }

    /**
    * Test the RESTful functionality.
    */
    void testRESTfulCURD() {
        def client = new RESTClient("http://localhost:8080/scorecard_tool/json/")
        def response
        
        // POST, create a new instance
        response = client.post(
            path: "scorecard-status",
            body: [ data: [ name: "test scorecard status", description: "test scorecard status", dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        assertEquals "test scorecard status", response.data.data.name
        assertEquals "test scorecard status", response.data.data.description
        
        def id = response.data.data.id
        
        // GET (list)
        response = client.get(path: "scorecard-status")
        assertTrue response.data.success
        
        // GET (single item)
        response = client.get(path: "scorecard-status/${id}")
        assertTrue response.data.success
        assertEquals "test scorecard status", response.data.data.name
        assertEquals "test scorecard status", response.data.data.description
        
        // PUT
        response = client.put(
            path: "scorecard-status/${id}",
            body: [ data: [ name: "test scorecard status2", description: "test scorecard status2", dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        assertEquals "test scorecard status2", response.data.data.name
        assertEquals "test scorecard status2", response.data.data.description
        
        // DELETE
        response = client.delete(path: "scorecard-status/${id}")
        assertTrue response.data.success
        assertEquals "test scorecard status2", response.data.data.name
        assertEquals "test scorecard status2", response.data.data.description
        
        // verify the item is deleted
        try {
            response = client.get(path: "scorecard-status/${id}")
            fail "the item should be deleted"
        } catch (HttpResponseException e) {
            assert e.response.status == 404
            assert e.response.data.success == false
            assert e.response.data.message.contains("not found")
        }
    }
    
    /**
    * Error will occurs when saving the instance.
    */
    void testRESTfulSavingFail() {
        def client = new RESTClient("http://localhost:8080/scorecard_tool/json/")
        try {
            client.post(
                path: "scorecard-status",
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
