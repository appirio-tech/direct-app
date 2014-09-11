/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import grails.test.*

/**
 * This class is integration test cases for <code>ProjectType</code> domain.
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
class ProjectTypeIntegrationTests extends GroovyTestCase {
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
     * Test that the project type name cannot be null. 
     */
    void testNameNull() {
        def projectType = new ProjectType(
                description:'description',
                isGeneric:true,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectType.validate()
        assertEquals "There should be only 1 error", 1, projectType.errors.getAllErrors().size()
        def badField = projectType.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField
    }

    /**
     * Test that the project type name cannot be blank.
     */
    void testNameBlank() {
        def projectType = new ProjectType(
                name:'   ',
                description:'description',
                isGeneric:true,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectType.validate()
        assertEquals "There should be only 1 error", 1, projectType.errors.getAllErrors().size()
        def badField = projectType.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField
    }

    /**
     * Test that the project type name must be unique.
     */
    void testNameUnique() {
        // create a project type first
        def p = new ProjectType(
                name:'type',
                description:'description',
                isGeneric:true,
                createUser:'root',
                modifyUser:'root'
                )
        p.save(flush:true)

        // try to create another project type with the same name
        def projectType = new ProjectType(
                name:'type',
                description:'description',
                isGeneric:true,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectType.validate()
        assertEquals "There should be only 1 error", 1, projectType.errors.getAllErrors().size()
        def badField = projectType.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField

        p.delete()
    }

    /**
     * Test the max size of the project type name.
     */
    void testNameMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 65; i++) {
            sb.append 'a'
        }
        def projectType = new ProjectType(
                name:sb,
                description:'description',
                isGeneric:true,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectType.validate()
        assertEquals "There should be only 1 error", 1, projectType.errors.getAllErrors().size()
        def badField = projectType.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField
    }

    /**
     * Test that the project type description cannot be null.
     */
    void testDescriptionNull() {
        def projectType = new ProjectType(
                name:'type',
                isGeneric:true,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectType.validate()
        assertEquals "There should be only 1 error", 1, projectType.errors.getAllErrors().size()
        def badField = projectType.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test that the project type description cannot be blank.
     */
    void testDescriptionBlank() {
        def projectType = new ProjectType(
                name:'type',
                description:'   ',
                isGeneric:true,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectType.validate()
        assertEquals "There should be only 1 error", 1, projectType.errors.getAllErrors().size()
        def badField = projectType.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test the max size of the project type description.
     */
    void testDescriptionMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 255; i++) {
            sb.append 'a'
        }
        def projectType = new ProjectType(
                name:'type',
                description:sb,
                isGeneric:true,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectType.validate()
        assertEquals "There should be only 1 error", 1, projectType.errors.getAllErrors().size()
        def badField = projectType.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test that the isGeneric field cannot be null.
     */
    void testIsGenericNull() {
        def projectType = new ProjectType(
                name:'type',
                description:'desc',
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectType.validate()
        assertEquals "There should be only 1 error", 1, projectType.errors.getAllErrors().size()
        def badField = projectType.errors.getFieldError('isGeneric')
        assertNotNull "Expecting to find an error on the isGeneric field", badField
    }

    /**
     * Test that the create user field cannot be blank.
     */
    void testCreateUserBlank() {
        def projectType = new ProjectType(
                name:'type',
                description:'desc',
                isGeneric:true,
                createUser:'   ',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectType.validate()
        assertEquals "There should be only 1 error", 1, projectType.errors.getAllErrors().size()
        def badField = projectType.errors.getFieldError('createUser')
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
        def projectType = new ProjectType(
                name:'type',
                description:'desc',
                isGeneric:true,
                createUser:sb,
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectType.validate()
        assertEquals "There should be only 1 error", 1, projectType.errors.getAllErrors().size()
        def badField = projectType.errors.getFieldError('createUser')
        assertNotNull "Expecting to find an error on the createUser field", badField
    }

    /**
     * Test that the modify user field cannot be blank.
     */
    void testModifyUserBlank() {
        def projectType = new ProjectType(
                name:'type',
                description:'desc',
                isGeneric:true,
                createUser:'root',
                modifyUser:'    '
                )

        assertFalse "Validation should not succeed", projectType.validate()
        assertEquals "There should be only 1 error", 1, projectType.errors.getAllErrors().size()
        def badField = projectType.errors.getFieldError('modifyUser')
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
        def projectType = new ProjectType(
                name:'type',
                description:'desc',
                isGeneric:true,
                createUser:'root',
                modifyUser:sb
                )

        assertFalse "Validation should not succeed", projectType.validate()
        assertEquals "There should be only 1 error", 1, projectType.errors.getAllErrors().size()
        def badField = projectType.errors.getFieldError('modifyUser')
        assertNotNull "Expecting to find an error on the modifyUser field", badField
    }

    /**
     * Test that the ORM mapping can work.
     */
    void testMapping() {
        def projectType = new ProjectType(
                name:'type',
                description:'desc',
                isGeneric:true,
                createUser:'root',
                modifyUser:'root'
                )

        projectType.save(flush:true)

        def retrieveProjectType = ProjectType.findByName('type')
        assertNotNull 'The object should be persistence to the database', retrieveProjectType
        assertEquals retrieveProjectType.name, projectType.name
        assertEquals retrieveProjectType.description, projectType.description
        assertEquals retrieveProjectType.isGeneric, projectType.isGeneric
        assertEquals retrieveProjectType.createUser, projectType.createUser
        assertEquals retrieveProjectType.modifyUser, projectType.modifyUser
        assertNotNull retrieveProjectType.dateCreated
        assertNotNull retrieveProjectType.lastUpdated

        retrieveProjectType.delete()
    }
}
