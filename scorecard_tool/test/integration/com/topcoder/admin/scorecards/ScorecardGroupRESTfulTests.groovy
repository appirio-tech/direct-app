/*
* Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
*/
package com.topcoder.admin.scorecards

import grails.test.*
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient


/**
* This class is unit test class for the RESTful service of <code>ScorecardGroup</code>
* domain.
*
* @author TCSASSEMBER
* @version 1.0
*/
class ScorecardGroupRESTfulTests extends GroovyTestCase {
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
        
        // first, we create a project type for test
        response = client.post(
            path: "project-type",
            body: [ data: [ name: "test project type", description: "test project type", isGeneric: false, dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        def projectTypeId = response.data.data.id
        
        // create a project category for test
        response = client.post(
            path: "project-category",
            body: [ data: [ name: "test project category", description: "test project category", display: true, displayOrder: 1, projectType: projectTypeId, dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        def projectCategoryId = response.data.data.id
        
        // create a scorecard type first
        response = client.post(
            path: "scorecard-type",
            body: [ data: [ name: "test scorecard type", description: "test scorecard type", dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        def scorecardTypeId = response.data.data.id 
        
        // create a socrecard status first
        response = client.post(
            path: "scorecard-status",
            body: [ data: [ name: "test scorecard status", description: "test scorecard status", dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        def scorecardStatusId = response.data.data.id
        
        // create a scorecard first
        response = client.post(
            path: "scorecard",
            body: [ data: [scorecardName: "test scorecard", scorecardVersion: '1.0.0', minimumScore:75.0, maximumScore: 100.0, scorecardType: scorecardTypeId, scorecardStatus: scorecardStatusId, projectCategory: projectCategoryId, dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        def scorecardId = response.data.data.id
        
        // POST, create a new instance
        response = client.post(
            path: "scorecard-group",
            body: [ data: [groupName: "test scorecard group", weight: 100.0, sort: 1, scorecard: scorecardId, dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        assertEquals "test scorecard group", response.data.data.groupName
        assertEquals 100.0, response.data.data.weight
        assertEquals 1, response.data.data.sort
        
        def id = response.data.data.id
        
        // GET (list)
        response = client.get(path: "scorecard-group")
        assertTrue response.data.success
        
        // GET (single item)
        response = client.get(path: "scorecard-group/${id}")
        assertTrue response.data.success
        assertEquals "test scorecard group", response.data.data.groupName
        assertEquals 100.0, response.data.data.weight
        assertEquals 1, response.data.data.sort
        
        // PUT
        response = client.put(
            path: "scorecard-group/${id}",
            body: [ data: [groupName: "test scorecard group2", weight: 100.0, sort: 2, scorecard: scorecardId, dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        assertEquals "test scorecard group2", response.data.data.groupName
        assertEquals 100.0, response.data.data.weight
        assertEquals 2, response.data.data.sort
        
        // DELETE
        response = client.delete(path: "scorecard-group/${id}")
        assertTrue response.data.success
        assertEquals "test scorecard group2", response.data.data.groupName
        assertEquals 100.0, response.data.data.weight
        assertEquals 2, response.data.data.sort
        
        // verify the item is deleted
        try {
            response = client.get(path: "scorecard-group/${id}")
            fail "the item should be deleted"
        } catch (HttpResponseException e) {
            assert e.response.status == 404
            assert e.response.data.success == false
            assert e.response.data.message.contains("not found")
        }
        
        response = client.delete(path: "scorecard/${scorecardId}")
        assertTrue response.data.success
        
        response = client.delete(path: "project-category/${projectCategoryId}")
        assertTrue response.data.success
        
        response = client.delete(path: "project-type/${projectTypeId}")
        assertTrue response.data.success
        
        response = client.delete(path: "scorecard-type/${scorecardTypeId}")
        assertTrue response.data.success
        
        response = client.delete(path: "scorecard-status/${scorecardStatusId}")
        assertTrue response.data.success
    }
    
    /**
    * Error will occurs when saving the instance.
    */
    void testRESTfulSavingFail() {
        def client = new RESTClient("http://localhost:8080/scorecard_tool/json/")
        try {
            client.post(
                path: "scorecard-group",
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
