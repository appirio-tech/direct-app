/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import grails.test.*

import org.hibernate.SessionFactory

/**
 * This class is integration test cases for <code>ScorecardGroup</code> domain.
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
class ScorecardGroupIntegrationTests extends GroovyTestCase {
    /**
     * Represents the Hibernate session factory. It will be injected by grails framework.
     */
    SessionFactory sessionFactory

    /**
     * A <code>ProjectType</code> instance to persists to database for test.
     */
    ProjectType projectType

    /**
     * A <code>ProjectCategory</code> instance to persists to database for test.
     */
    ProjectCategory projectCategory

    /**
     * A <code>ScorecardType</code> instance to persists to database for test.
     */
    ScorecardType scorecardType

    /**
     * A <code>ScorecardStatus</code> instance to persists to database for test.
     */
    ScorecardStatus scorecardStatus

    /**
     * A <code>Scorecard</code> instance to persists to database for test.
     */
    Scorecard scorecard

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        super.setUp()

        projectType = new ProjectType(
                name:'Application',
                description:'Application',
                isGeneric:false,
                createUser:'root',
                modifyUser:'root'
                );
        projectType.save()

        projectCategory = new ProjectCategory(
                name:'Assembly',
                description:'Assembly',
                display:true,
                displayOrder:1,
                projectType:projectType,
                createUser:'root',
                modifyUser:'root'
                );
        projectCategory.save()

        scorecardType = new ScorecardType(
                name:'Screening',
                description:'Screening',
                createUser:'root',
                modifyUser:'root'
                );
        scorecardType.save()

        scorecardStatus = new ScorecardStatus(
                name:'Active',
                description:'Active',
                createUser:'root',
                modifyUser:'root');
        scorecardStatus.save()

        scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )
        scorecard.save()
    }

    /**
     * Tear down the test environment
     */
    protected void tearDown() {
        super.tearDown()

        scorecard.delete()
        projectCategory.delete()
        projectType.delete()
        scorecardType.delete()
        scorecardStatus.delete()
    }

    /**
     * Test that the group name cannot be null.
     */
    void testGroupNameNull() {
        def scorecardGroup = new ScorecardGroup(
                weight:100.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('groupName')
        assertNotNull "Expecting to find an error on the groupName field", badField
    }

    /**
     * Test that the group name cannot be blank.
     */
    void testGroupNameBlank() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'   ',
                weight:100.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('groupName')
        assertNotNull "Expecting to find an error on the groupName field", badField
    }

    /**
     * Test the max size of the group name.
     */
    void testGroupNameMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 65; i++) {
            sb.append 'a'
        }
        def scorecardGroup = new ScorecardGroup(
                groupName:sb,
                weight:100.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('groupName')
        assertNotNull "Expecting to find an error on the groupName field", badField
    }

    /**
     * Test that the group name under the same scorecard must be unique.
     */
    void testGroupNameUnique() {
        // create a scorecard group first
        def scorecardGroup = new ScorecardGroup(
                groupName:'group',
                weight:100.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )
        scorecardGroup.save(flush:true)

        // try to create another scorecard with the same group name and scorecard
        def scorecardGroup2 = new ScorecardGroup(
                groupName:'group',
                weight:100.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )
        assertFalse "Validation should not succeed", scorecardGroup2.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup2.errors.getAllErrors().size()
        def badField = scorecardGroup2.errors.getFieldError('groupName')
        assertNotNull "Expecting to find an error on the groupName field", badField

        scorecardGroup.delete(flush:true)
    }

    /**
     * Test that the group name can be the same if the scorecard is different
     */
    void testGroupNameUnique2() {
        // create a new Scorecard
        def scorecard2 = new Scorecard(
                scorecardName:'name2',
                scorecardVersion:'1.0.1',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )
        scorecard2.save(flush:true)

        // create a scorecard group whose scorecard is the first scorecard
        def scorecardGroup = new ScorecardGroup(
                groupName:'group',
                weight:100.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )
        scorecardGroup.save(flush:true)

        // try to create another scorecard group with the same group name whose scorecard is the second scorecard
        def scorecardGroup2 = new ScorecardGroup(
                groupName:'group',
                weight:100.0,
                sort:1,
                scorecard:scorecard2,
                createUser:'root',
                modifyUser:'root'
                )
        assertTrue "Validation should succeed", scorecardGroup2.validate()
        scorecardGroup.delete(flush:true)
        scorecard2.delete(flush:true)
    }

    /**
     * Test that the weight field cannot be null.
     */
    void testWeightNull() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test that the weight field must be a number.
     */
    void testWeightNotNumber() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:'asdf',
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test that the weight field cannot be zero.
     */
    void testWeightZero() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:0.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test that the weight field cannot be negative.
     */
    void testWeightNegative() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:-10.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test the max value of the weight field.
     */
    void testWeightMax() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:101.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test that the sort field cannot be null.
     */
    void testSortNull() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:70.0,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('sort')
        assertNotNull "Expecting to find an error on the sort field", badField
    }

    /**
     * Test that the sort field must be a number.
     */
    void testSortNotNumber() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:70.0,
                sort:'asdf',
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('sort')
        assertNotNull "Expecting to find an error on the sort field", badField
    }

    /**
     * Test that the sort field cannot be negative.
     */
    void testSortNegative() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:70.0,
                sort:-1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('sort')
        assertNotNull "Expecting to find an error on the sort field", badField
    }

    /**
     * Test that the scorecard field cannot be null.
     */
    void testScorecardNull() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:70.0,
                sort:1,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('scorecard')
        assertNotNull "Expecting to find an error on the scorecard field", badField
    }

    /**
     * Test that the create user field cannot be blank.
     */
    void testCreateUserBlank() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:70.0,
                sort:1,
                scorecard:scorecard,
                createUser:'    ',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('createUser')
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
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:70.0,
                sort:1,
                scorecard:scorecard,
                createUser:sb,
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('createUser')
        assertNotNull "Expecting to find an error on the createUser field", badField
    }

    /**
     * Test that the modify user field cannot be blank.
     */
    void testModifyUserBlank() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:70.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'    '
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('modifyUser')
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
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:70.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:sb
                )

        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('modifyUser')
        assertNotNull "Expecting to find an error on the modifyUser field", badField
    }

    /**
     * Test that the scorecard must not be in used when updating/inserting the scorecard group.
     */
    void testScorecardInUsed1() {
        // create a review for this scorecard so that the scorecard is in used
        def review = new Review(scorecard:scorecard)
        review.save(flush:true)

        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:100.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )
        assertFalse "Validation should not succeed", scorecardGroup.validate()
        assertEquals "There should be only 1 error", 1, scorecardGroup.errors.getAllErrors().size()
        def badField = scorecardGroup.errors.getFieldError('scorecard')
        assertNotNull "Expecting to find an error on the scorecard field", badField

        review.delete(flush:true)
    }

    /**
     * Test that the scorecard must not be in used when deleting the scorecard group.
     */
    void testScorecardInUsed2() {
        // create a new scorecard group
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:100.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )
        scorecardGroup.save(flush:true)
        assertFalse "The object should have persisted to database", scorecardGroup.hasErrors()

        // create a review for this scorecard so that the scorecard is in used
        def review = new Review(scorecard:scorecard)
        scorecard = Scorecard.get(scorecard.id)
        review.save(flush:true)

        sessionFactory.getCurrentSession().clear()

        // try to delete the scorecard group
        scorecardGroup.delete(flush:true)
        assertTrue "There should be errors when delete the scorecard group", scorecardGroup.hasErrors()

        sessionFactory.getCurrentSession().clear()
        assertNotNull "The scorecard group should not be deleted", ScorecardGroup.get(scorecardGroup.id)
        Review.get(review.id).delete(flush:true)
        ScorecardGroup.get(scorecardGroup.id).delete(flush:true)
        scorecard = Scorecard.get(scorecard.id)
    }

    /**
     * Test that the ORM mapping can work.
     */
    void testMapping() {
        def scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:70.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )
        scorecardGroup.save(flush:true)
        def retrieveScorecardGroup = ScorecardGroup.findByGroupNameAndScorecard('name', scorecard)
        assertNotNull "The object should be persisted to the database", retrieveScorecardGroup
        assertEquals retrieveScorecardGroup.groupName, scorecardGroup.groupName
        assertEquals retrieveScorecardGroup.weight, scorecardGroup.weight
        assertEquals retrieveScorecardGroup.sort, scorecardGroup.sort
        assertEquals retrieveScorecardGroup.scorecard.id, scorecardGroup.scorecard.id
        assertEquals retrieveScorecardGroup.createUser, scorecardGroup.createUser
        assertEquals retrieveScorecardGroup.modifyUser, scorecardGroup.modifyUser
        assertNotNull retrieveScorecardGroup.dateCreated
        assertNotNull retrieveScorecardGroup.lastUpdated
        scorecardGroup.delete(flush:true)
    }
}
