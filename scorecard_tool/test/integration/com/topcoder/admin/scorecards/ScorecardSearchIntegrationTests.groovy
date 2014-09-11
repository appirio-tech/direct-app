/*
* Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
*/
package com.topcoder.admin.scorecards

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.GET
import grails.test.*
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.RESTClient


/**
* This class is unit test class for the search service of <code>ScorecardController</code>
* controller.
*
* @author TCSASSEMBER
* @version 1.0
*/
class ScorecardSearchIntegrationTests extends GroovyTestCase {
    /**
     * The <code>RESTClient</code> instance used to send rest request to server.
     */
    def client
    
    /**
     * The id of new created project type entity.
     */
    def projectTypeId
    
    /**
     * The id of new created project category entity.
     */
    def projectCategoryId
    
    /**
     * The id of new created scorecard type entity.
     */
    def scorecardTypeId
    
    /**
     * The id of new created scorecard status entity.
     */
    def scorecardStatusId
    
    /**
     * The id of new created scorecard entity.
     */
    def scorecardId
    
    /**
    * Set up the environment.
    */
    protected void setUp() {
        super.setUp()

        client = new RESTClient("http://localhost:8080/scorecard_tool/json/")
        def response
        
        // first, we create a project type for test
        response = client.post(
            path: "project-type",
            body: [ data: [ name: "test project type", description: "test project type", isGeneric: false, dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        projectTypeId = response.data.data.id
        
        // create a project category for test
        response = client.post(
            path: "project-category",
            body: [ data: [ name: "test project category", description: "test project category", display: true, displayOrder: 1, projectType: projectTypeId, dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        projectCategoryId = response.data.data.id
        
        // create a scorecard type first
        response = client.post(
            path: "scorecard-type",
            body: [ data: [ name: "test scorecard type", description: "test scorecard type", dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        scorecardTypeId = response.data.data.id
        
        // create a socrecard status first
        response = client.post(
            path: "scorecard-status",
            body: [ data: [ name: "test scorecard status", description: "test scorecard status", dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        assertTrue response.data.success
        scorecardStatusId = response.data.data.id
        
        // create a scorecard first
        response = client.post(
            path: "scorecard",
            body: [ data: [scorecardName: "test scorecard", scorecardVersion: '1.0.0', minimumScore:75.0, maximumScore: 100.0, scorecardType: scorecardTypeId, scorecardStatus: scorecardStatusId, projectCategory: projectCategoryId, dateCreated: new Date(), lastUpdated: new Date() ] ],
            requestContentType : ContentType.JSON.toString()
        )
        scorecardId = response.data.data.id
    }

    /**
    * Tear down the environment.
    */
    protected void tearDown() {
        super.tearDown()
        
        def response 
        
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
     * Checks whether the scorecards json data returned by server contains a specified scorecard.
     */
    def checkJSONScorecards = {scorecards, needsId ->
        for (int i = 0; i < scorecards.size(); i++) {
            if (scorecards.getAt(i).id == needsId) {
                return true
            }
        }
        false
    }
    
    /**
    * Test the search functionality. No filter parameters.
    */
    void testSearchNoParams() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ : ]
            
            response.success = { resp, json ->
                assertTrue json.success
                assertTrue json.totalCount > 0
                assertTrue json.count > 0
                assertTrue json.data.size() == json.count
                assertTrue checkJSONScorecards(json.data, scorecardId)
            }
        }
    }
    
    /**
    * Test the search functionality. Search by scorecard name.
    */
    void testSearchBySN1() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ sn : 'teSt' ]
            
            response.success = { resp, json ->
                assertTrue json.success
                assertTrue json.totalCount > 0
                assertTrue json.count > 0
                assertTrue json.data.size() == json.count
                assertTrue checkJSONScorecards(json.data, scorecardId)
            }
        }
    }
    
    /**
    * Test the search functionality. Search by scorecard name.
    */
    void testSearchBySN2() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ sn : 'teStxxxx' ]
            
            response.success = { resp, json ->
                assertTrue json.success
                assertTrue json.data.size() == json.count
                assertFalse checkJSONScorecards(json.data, scorecardId)
            }
        }
    }
    
    /**
    * Test the search functionality. Search by project type.
    */
    void testSearchBySPT1() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ spt : projectTypeId ]
            
            response.success = { resp, json ->
                assertTrue json.success
                assertTrue json.totalCount > 0
                assertTrue json.count > 0
                assertTrue json.data.size() == json.count
                assertTrue checkJSONScorecards(json.data, scorecardId)
            }
        }
    }
    
    /**
    * Test the search functionality. Search by project type.
    */
    void testSearchBySPT2() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ spt : -1 ]
            
            response.success = { resp, json ->
                assertTrue json.success
                assertTrue json.totalCount == 0
                assertTrue json.count == 0
                assertTrue json.data.size() == json.count
                assertFalse checkJSONScorecards(json.data, scorecardId)
            }
        }
    }
    
    /**
    * Test the search functionality. Search by project type (Invalid parameters).
    */
    void testSearchBySPT3() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ spt : 'ddd' ]
            
            response.success = { resp, json ->
                assertFalse json.success
                assertEquals json.message, 'spt must be numbers.'
            }
        }
    }
    
    /**
    * Test the search functionality. Search by project category.
    */
    void testSearchBySC1() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ sc : projectCategoryId ]
            
            response.success = { resp, json ->
                assertTrue json.success
                assertTrue json.totalCount > 0
                assertTrue json.count > 0
                assertTrue json.data.size() == json.count
                assertTrue checkJSONScorecards(json.data, scorecardId)
            }
        }
    }
    
    /**
    * Test the search functionality. Search by project category.
    */
    void testSearchBySC2() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ sc : -1 ]
            
            response.success = { resp, json ->
                assertTrue json.success
                assertTrue json.totalCount == 0
                assertTrue json.count == 0
                assertTrue json.data.size() == json.count
                assertFalse checkJSONScorecards(json.data, scorecardId)
            }
        }
    }
    
    /**
    * Test the search functionality. Search by project category (Invalid parameters).
    */
    void testSearchBySC3() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ sc : 'ddd' ]
            
            response.success = { resp, json ->
                assertFalse json.success
                assertEquals json.message, 'sc must be numbers.'
            }
        }
    }
    
    /**
    * Test the search functionality. Search by scorecard type.
    */
    void testSearchByST1() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ st : scorecardTypeId ]
            
            response.success = { resp, json ->
                assertTrue json.success
                assertTrue json.totalCount > 0
                assertTrue json.count > 0
                assertTrue json.data.size() == json.count
                assertTrue checkJSONScorecards(json.data, scorecardId)
            }
        }
    }
    
    /**
    * Test the search functionality. Search by scorecard type.
    */
    void testSearchByST2() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ st : -1 ]
            
            response.success = { resp, json ->
                assertTrue json.success
                assertTrue json.totalCount == 0
                assertTrue json.count == 0
                assertTrue json.data.size() == json.count
                assertFalse checkJSONScorecards(json.data, scorecardId)
            }
        }
    }
    
    /**
    * Test the search functionality. Search by scorecard type (Invalid parameters).
    */
    void testSearchByST3() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ st : 'ddd' ]
            
            response.success = { resp, json ->
                assertFalse json.success
                assertEquals json.message, 'st must be numbers.'
            }
        }
    }
    
    /**
    * Test the search functionality. Search by scorecard status.
    */
    void testSearchBySS1() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ ss : scorecardStatusId ]
            
            response.success = { resp, json ->
                assertTrue json.success
                assertTrue json.totalCount > 0
                assertTrue json.count > 0
                assertTrue json.data.size() == json.count
                assertTrue checkJSONScorecards(json.data, scorecardId)
            }
        }
    }
    
    /**
    * Test the search functionality. Search by scorecard status.
    */
    void testSearchBySS2() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ ss : -1 ]
            
            response.success = { resp, json ->
                assertTrue json.success
                assertTrue json.totalCount == 0
                assertTrue json.count == 0
                assertTrue json.data.size() == json.count
                assertFalse checkJSONScorecards(json.data, scorecardId)
            }
        }
    }
    
    /**
    * Test the search functionality. Search by scorecard status (Invalid parameters).
    */
    void testSearchBySS3() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ ss : 'ddd' ]
            
            response.success = { resp, json ->
                assertFalse json.success
                assertEquals json.message, 'ss must be a number.'
            }
        }
    }
    
    /**
     * Test the search functionality. Specified the parameters related to paging and sort functinality.
     */
    void testSortAndPaging() {
        def http = new HTTPBuilder('http://localhost:8080')
        http.request(GET, JSON) {
            uri.path = '/scorecard_tool/scorecard/search'
            uri.query = [ offset : 0, max : 1, sort : 'scorecardName', asc : true ]
            
            response.success = { resp, json ->
                assertTrue json.success
                assertTrue json.totalCount > 0
                assertTrue json.count == 1
                assertTrue json.data.size() == json.count
            }
        }
    }
}
