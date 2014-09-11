/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import grails.test.*

/**
 * This class is integration test cases for <code>ScorecardType</code> domain.
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
class ScorecardTypeIntegrationTests extends GroovyTestCase {
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
        def scorecardType = new ScorecardType(
                description:'description',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardType.validate()
        assertEquals "There should be only 1 error", 1, scorecardType.errors.getAllErrors().size()
        def badField = scorecardType.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField
    }

    /**
     * Test that the name cannot be blank.
     */
    void testNameBlank() {
        def scorecardType = new ScorecardType(
                name:'   ',
                description:'description',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardType.validate()
        assertEquals "There should be only 1 error", 1, scorecardType.errors.getAllErrors().size()
        def badField = scorecardType.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField
    }

    /**
     * Test that the name must be unique.
     */
    void testNameUnique() {
        def p = new ScorecardType(
                name:'type',
                description:'description',
                createUser:'root',
                modifyUser:'root'
                )
        p.save(flush:true)

        def scorecardType = new ScorecardType(
                name:'type',
                description:'description',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardType.validate()
        assertEquals "There should be only 1 error", 1, scorecardType.errors.getAllErrors().size()
        def badField = scorecardType.errors.getFieldError('name')
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
        def scorecardType = new ScorecardType(
                name:sb,
                description:'description',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardType.validate()
        assertEquals "There should be only 1 error", 1, scorecardType.errors.getAllErrors().size()
        def badField = scorecardType.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField
    }

    /**
     * Test that the description cannot be null.
     */
    void testDescriptionNull() {
        def scorecardType = new ScorecardType(
                name:'type',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardType.validate()
        assertEquals "There should be only 1 error", 1, scorecardType.errors.getAllErrors().size()
        def badField = scorecardType.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test that the description cannot be blank.
     */
    void testDescriptionBlank() {
        def scorecardType = new ScorecardType(
                name:'type',
                description:'   ',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardType.validate()
        assertEquals "There should be only 1 error", 1, scorecardType.errors.getAllErrors().size()
        def badField = scorecardType.errors.getFieldError('description')
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
        def scorecardType = new ScorecardType(
                name:'type',
                description:sb,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardType.validate()
        assertEquals "There should be only 1 error", 1, scorecardType.errors.getAllErrors().size()
        def badField = scorecardType.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test that the create user field cannot be blank.
     */
    void testCreateUserBlank() {
        def scorecardType = new ScorecardType(
                name:'type',
                description:'desc',
                createUser:'   ',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardType.validate()
        assertEquals "There should be only 1 error", 1, scorecardType.errors.getAllErrors().size()
        def badField = scorecardType.errors.getFieldError('createUser')
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
        def scorecardType = new ScorecardType(
                name:'type',
                description:'desc',
                createUser:sb,
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardType.validate()
        assertEquals "There should be only 1 error", 1, scorecardType.errors.getAllErrors().size()
        def badField = scorecardType.errors.getFieldError('createUser')
        assertNotNull "Expecting to find an error on the createUser field", badField
    }

    /**
     * Test that the modify user field cannot be blank.
     */
    void testModifyUserBlank() {
        def scorecardType = new ScorecardType(
                name:'type',
                description:'desc',
                createUser:'root',
                modifyUser:'    '
                )

        assertFalse "Validation should not succeed", scorecardType.validate()
        assertEquals "There should be only 1 error", 1, scorecardType.errors.getAllErrors().size()
        def badField = scorecardType.errors.getFieldError('modifyUser')
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
        def scorecardType = new ScorecardType(
                name:'type',
                description:'desc',
                createUser:'root',
                modifyUser:sb
                )

        assertFalse "Validation should not succeed", scorecardType.validate()
        assertEquals "There should be only 1 error", 1, scorecardType.errors.getAllErrors().size()
        def badField = scorecardType.errors.getFieldError('modifyUser')
        assertNotNull "Expecting to find an error on the modifyUser field", badField
    }

    /**
     * Test that the ORM mapping can work.
     */
    void testMapping() {
        def scorecardType = new ScorecardType(
                name:'type',
                description:'desc',
                createUser:'root',
                modifyUser:'root'
                )

        scorecardType.save(flush:true)
        def retrieveScorecardType = ScorecardType.findByName('type')
        assertNotNull 'The object should be persistence to the database', retrieveScorecardType
        assertEquals retrieveScorecardType.name, scorecardType.name
        assertEquals retrieveScorecardType.description, scorecardType.description
        assertEquals retrieveScorecardType.createUser, scorecardType.createUser
        assertEquals retrieveScorecardType.modifyUser, scorecardType.modifyUser
        assertNotNull retrieveScorecardType.dateCreated
        assertNotNull retrieveScorecardType.lastUpdated
        scorecardType.delete(flush:true)
    }
}
