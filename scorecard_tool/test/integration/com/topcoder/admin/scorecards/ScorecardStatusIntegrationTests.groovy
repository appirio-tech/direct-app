/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import grails.test.*

/**
 * This class is integration test cases for <code>ScorecardStatus</code> domain.
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
class ScorecardStatusIntegrationTests extends GroovyTestCase {
    /**
     * Set up the test environment.
     */
    protected void setUp() {
        super.setUp()
    }

    /**
     * Tear down the test environment
     */
    protected void tearDown() {
        super.tearDown()
    }

    /**
     * Test that the name cannot be null.
     */
    void testNameNull() {
        def scorecardStatus = new ScorecardStatus(
                description:'description',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardStatus.validate()
        assertEquals "There should be only 1 error", 1, scorecardStatus.errors.getAllErrors().size()
        def badField = scorecardStatus.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField
    }

    /**
     * Test that the name cannot be blank.
     */
    void testNameBlank() {
        def scorecardStatus = new ScorecardStatus(
                name:'   ',
                description:'description',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardStatus.validate()
        assertEquals "There should be only 1 error", 1, scorecardStatus.errors.getAllErrors().size()
        def badField = scorecardStatus.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField
    }

    /**
     * Test that the name must be unique.
     */
    void testNameUnique() {
        def p = new ScorecardStatus(
                name:'type',
                description:'description',
                createUser:'root',
                modifyUser:'root'
                )
        p.save(flush:true)

        def scorecardStatus = new ScorecardStatus(
                name:'type',
                description:'description',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardStatus.validate()
        assertEquals "There should be only 1 error", 1, scorecardStatus.errors.getAllErrors().size()
        def badField = scorecardStatus.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField

        p.delete()
    }

    /**
     * Test the max size of the name.
     */
    void testNameMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 65; i++) {
            sb.append 'a'
        }
        def scorecardStatus = new ScorecardStatus(
                name:sb,
                description:'description',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardStatus.validate()
        assertEquals "There should be only 1 error", 1, scorecardStatus.errors.getAllErrors().size()
        def badField = scorecardStatus.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField
    }

    /**
     * Test that the description cannot be null.
     */
    void testDescriptionNull() {
        def scorecardStatus = new ScorecardStatus(
                name:'type',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardStatus.validate()
        assertEquals "There should be only 1 error", 1, scorecardStatus.errors.getAllErrors().size()
        def badField = scorecardStatus.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test that the description cannot be blank.
     */
    void testDescriptionBlank() {
        def scorecardStatus = new ScorecardStatus(
                name:'type',
                description:'   ',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardStatus.validate()
        assertEquals "There should be only 1 error", 1, scorecardStatus.errors.getAllErrors().size()
        def badField = scorecardStatus.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test the max size the description.
     */
    void testDescriptionMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 255; i++) {
            sb.append 'a'
        }
        def scorecardStatus = new ScorecardStatus(
                name:'type',
                description:sb,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardStatus.validate()
        assertEquals "There should be only 1 error", 1, scorecardStatus.errors.getAllErrors().size()
        def badField = scorecardStatus.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test that the create user field cannot be blank.
     */
    void testCreateUserBlank() {
        def scorecardStatus = new ScorecardStatus(
                name:'type',
                description:'desc',
                createUser:'   ',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardStatus.validate()
        assertEquals "There should be only 1 error", 1, scorecardStatus.errors.getAllErrors().size()
        def badField = scorecardStatus.errors.getFieldError('createUser')
        assertNotNull "Expecting to find an error on the createUser field", badField
    }

    /**
     * Test the max size of create user field.
     */
    void testCreateUserMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 65; i++) {
            sb.append 'a'
        }
        def scorecardStatus = new ScorecardStatus(
                name:'type',
                description:'desc',
                createUser:sb,
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardStatus.validate()
        assertEquals "There should be only 1 error", 1, scorecardStatus.errors.getAllErrors().size()
        def badField = scorecardStatus.errors.getFieldError('createUser')
        assertNotNull "Expecting to find an error on the createUser field", badField
    }

    /**
     * Test that the modify user field cannot be blank.
     */
    void testModifyUserBlank() {
        def scorecardStatus = new ScorecardStatus(
                name:'type',
                description:'desc',
                createUser:'root',
                modifyUser:'    '
                )

        assertFalse "Validation should not succeed", scorecardStatus.validate()
        assertEquals "There should be only 1 error", 1, scorecardStatus.errors.getAllErrors().size()
        def badField = scorecardStatus.errors.getFieldError('modifyUser')
        assertNotNull "Expecting to find an error on the modifyUser field", badField
    }

    /**
     * Test the max size of the modify user field.
     */
    void testModifyUserMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 65; i++) {
            sb.append 'a'
        }
        def scorecardStatus = new ScorecardStatus(
                name:'type',
                description:'desc',
                createUser:'root',
                modifyUser:sb
                )

        assertFalse "Validation should not succeed", scorecardStatus.validate()
        assertEquals "There should be only 1 error", 1, scorecardStatus.errors.getAllErrors().size()
        def badField = scorecardStatus.errors.getFieldError('modifyUser')
        assertNotNull "Expecting to find an error on the modifyUser field", badField
    }

    /**
     * Test that the ORM mapping can work.
     */
    void testMapping() {
        def scorecardStatus = new ScorecardStatus(
                name:'type',
                description:'desc',
                createUser:'root',
                modifyUser:'root'
                )

        scorecardStatus.save(flush:true)
        def retrieveScorecardStatus = ScorecardStatus.findByName('type')
        assertNotNull 'The object should be persistence to the database', retrieveScorecardStatus
        assertEquals retrieveScorecardStatus.name, scorecardStatus.name
        assertEquals retrieveScorecardStatus.description, scorecardStatus.description
        assertEquals retrieveScorecardStatus.createUser, scorecardStatus.createUser
        assertEquals retrieveScorecardStatus.modifyUser, scorecardStatus.modifyUser
        assertNotNull retrieveScorecardStatus.dateCreated
        assertNotNull retrieveScorecardStatus.lastUpdated
        scorecardStatus.delete(flush:true)
    }
}
