/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import grails.test.*
import groovyx.net.http.RESTClient

/**
 * This class is integration test cases for <code>ProjectCategory</code> domain.
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
class ProjectCategoryIntegrationTests extends GroovyTestCase {
    /**
     * A <code>ProjectType</code> instance to persists to database for test.
     */
    ProjectType aProjectType

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        super.setUp()

        aProjectType = new ProjectType(
                name:'type',
                description:'desc',
                isGeneric:true,
                createUser:'root',
                modifyUser:'root'
                )

        aProjectType.save(flush:true)
    }

    /**
     * Tear down the test environment
     */
    protected void tearDown() {
        super.tearDown()

        ProjectType.findByName('type').delete(flush:true)
    }

    /**
     * Test the the project category name cannot be null.
     */
    void testNameNull() {
        // project category name is null
        def projectCategory = new ProjectCategory(
                description:'description',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField
    }

    /**
     * Test the project category name cannot be blank.
     */
    void testNameBlank() {
        // project category name is blank
        def projectCategory = new ProjectCategory(
                name:'   ',
                description:'description',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField
    }

    /**
     * Test the project category name under a specific project type must be unique. 
     */
    void testNameUnique() {
        // create a project category instance
        def p = new ProjectCategory(
                name:'type',
                description:'description',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )
        p.save(flush:true)

        // try to create another category instance with the same name and project type
        def projectCategory = new ProjectCategory(
                name:'type',
                description:'description',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField

        p.delete()
    }

    /**
     * Test the max size of the project category name.
     */
    void testNameMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 65; i++) {
            sb.append 'a'
        }
        def projectCategory = new ProjectCategory(
                name:sb,
                description:'description',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('name')
        assertNotNull "Expecting to find an error on the name field", badField
    }

    /**
     * Test that the project category description cannot be null.
     */
    void testDescriptionNull() {
        // description is null
        def projectCategory = new ProjectCategory(
                name:'type',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test that the project category description cannot be blank.
     */
    void testDescriptionBlank() {
        // description is blank
        def projectCategory = new ProjectCategory(
                name:'type',
                description:'   ',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test the max size of project category description.
     */
    void testDescriptionMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 255; i++) {
            sb.append 'a'
        }
        def projectCategory = new ProjectCategory(
                name:'type',
                description:sb,
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test the display field cannot be null.
     */
    void testDisplayNull() {
        // display field is null
        def projectCategory = new ProjectCategory(
                name:'type',
                description:'desc',
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('display')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test the display order field cannot be null.
     */
    void testDisplayOrderNull() {
        // display order field is null
        def projectCategory = new ProjectCategory(
                name:'type',
                description:'desc',
                display:true,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('displayOrder')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test that the display order field must be non-negative.
     */
    void testDisplayOrderNegative() {
        def projectCategory = new ProjectCategory(
                name:'type',
                description:'desc',
                display:true,
                displayOrder:-1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('displayOrder')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test that the project type field cannot be null.
     */
    void testProjectTypeNull() {
        def projectCategory = new ProjectCategory(
                name:'type',
                description:'desc',
                display:true,
                displayOrder:1,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('projectType')
        assertNotNull "Expecting to find an error on the description field", badField
    }

    /**
     * Test that the create user field cannot be blank.
     */
    void testCreateUserBlank() {
        def projectCategory = new ProjectCategory(
                name:'name',
                description:'description',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'    ',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('createUser')
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
        def projectCategory = new ProjectCategory(
                name:'name',
                description:'description',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:sb,
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('createUser')
        assertNotNull "Expecting to find an error on the createUser field", badField
    }

    /**
     * Test that the modify user field cannot be blank.
     */
    void testModifyUserBlank() {
        def projectCategory = new ProjectCategory(
                name:'name',
                description:'description',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'  '
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('modifyUser')
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
        def projectCategory = new ProjectCategory(
                name:'name',
                description:'description',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:sb
                )

        assertFalse "Validation should not succeed", projectCategory.validate()
        assertEquals "There should be only 1 error", 1, projectCategory.errors.getAllErrors().size()
        def badField = projectCategory.errors.getFieldError('modifyUser')
        assertNotNull "Expecting to find an error on the modifyUser field", badField
    }

    /**
     * Test the project category name can be the same if the project type is different.
     */
    void testSameNameWithDifferentProjectType() {
        // create a different project type
        def projectType = new ProjectType(
                name:'type2',
                description:'desc',
                isGeneric:true,
                createUser:'root',
                modifyUser:'root'
                )
        projectType.save(flush:true)

        // create a project category whose project type is the first project type
        def projectCategory = new ProjectCategory(
                name:'name',
                description:'description',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )
        projectCategory.save(flush:true)

        // try to create a project category with the same name whose project type is the second project type
        def projectCategory2 = new ProjectCategory(
                name:'name',
                description:'description',
                display:true,
                displayOrder:1,
                projectType:projectType,
                createUser:'root',
                modifyUser:'root'
                )
        assertTrue "Validation should succeed", projectCategory2.validate()

        projectCategory.delete(flush:true)
        projectType.delete(flush:true)
    }

    /**
     * Test that the ORM mapping can work.
     */
    void testMapping() {
        def projectCategory = new ProjectCategory(
                name:'name',
                description:'description',
                display:true,
                displayOrder:1,
                projectType:aProjectType,
                createUser:'root',
                modifyUser:'root'
                )
        projectCategory.save(flush:true)

        def reteiveProjectCategory = ProjectCategory.findByName('name')
        assertNotNull 'The object should be persistence to the database', reteiveProjectCategory
        assertEquals reteiveProjectCategory.name, projectCategory.name
        assertEquals reteiveProjectCategory.description, projectCategory.description
        assertEquals reteiveProjectCategory.display, projectCategory.display
        assertEquals reteiveProjectCategory.displayOrder, projectCategory.displayOrder
        assertEquals reteiveProjectCategory.projectType.id, projectCategory.projectType.id
        assertEquals reteiveProjectCategory.createUser, projectCategory.createUser
        assertEquals reteiveProjectCategory.modifyUser, projectCategory.modifyUser
        assertNotNull reteiveProjectCategory.dateCreated
        assertNotNull reteiveProjectCategory.lastUpdated
        projectCategory.delete(flush:true)
    }
}
