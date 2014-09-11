/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import grails.test.*

import org.hibernate.SessionFactory

/**
 * This class is integration test cases for <code>Scorecard</code> domain.
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
class ScorecardIntegrationTests extends GroovyTestCase {
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
        projectType.save(flush:true)

        projectCategory = new ProjectCategory(
                name:'Assembly',
                description:'Assembly',
                display:true,
                displayOrder:1,
                projectType:projectType,
                createUser:'root',
                modifyUser:'root'
                );
        projectCategory.save(flush:true)

        scorecardType = new ScorecardType(
                name:'Screening',
                description:'Screening',
                createUser:'root',
                modifyUser:'root'
                );
        scorecardType.save(flush:true)

        scorecardStatus = new ScorecardStatus(
                name:'Active',
                description:'Active',
                createUser:'root',
                modifyUser:'root');
        scorecardStatus.save(flush:true)
    }

    /**
     * Tear down the test environment
     */
    protected void tearDown() {
        super.tearDown()

        projectCategory.delete(flush:true)
        projectType.delete(flush:true)
        scorecardType.delete(flush:true)
        scorecardStatus.delete(flush:true)
    }

    /**
     * Test that the scorecard name cannot be null.
     */
    void testScorecardNameNull() {
        def scorecard = new Scorecard(
                scorecardVersion:'1.0.0',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('scorecardName')
        assertNotNull "Expecting to find an error on the scorecardName field", badField
    }

    /**
     * Test that the scorecard name cannot be blank.
     */
    void testScorecardNameBlank() {
        def scorecard = new Scorecard(
                scorecardName:'   ',
                scorecardVersion:'1.0.0',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('scorecardName')
        assertNotNull "Expecting to find an error on the scorecardName field", badField
    }

    /**
     * Test the max size of the scorecard name.
     */
    void testScorecardNameMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 65; i++) {
            sb.append 'a'
        }
        def scorecard = new Scorecard(
                scorecardName:sb,
                scorecardVersion:'1.0.0',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('scorecardName')
        assertNotNull "Expecting to find an error on the scorecardName field", badField
    }

    /**
     * Test that the scorecard name and scorecard version pair must be unique.
     */
    void testScorecardNameAndScorecardVersionUnique() {
        // Create a new scorecard.
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.0',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )
        scorecard.save(flush:true)

        // try to create another scorecard with the same scorecard name and scorecard version.
        def scorecard2 = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.0',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )
        assertFalse "Validation should not succeed", scorecard2.validate()
        assertEquals "There should be only 1 error", 1, scorecard2.errors.getAllErrors().size()
        def badField = scorecard2.errors.getFieldError('scorecardName')
        assertNotNull "Expecting to find an error on the scorecardName field", badField

        scorecard.delete(flush:true)
    }

    /**
     * Test that the scorecard version cannot be null.
     */
    void testScorecardVersionNull() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('scorecardVersion')
        assertNotNull "Expecting to find an error on the scorecardVersion field", badField
    }

    /**
     * Test that the scorecard version cannot be blank.
     */
    void testScorecardVersionBlank() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'   ',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('scorecardVersion')
        assertNotNull "Expecting to find an error on the scorecardVersion field", badField
    }

    /**
     * Test that the scorecard version must be format of x.x.x. Here x is a number. 
     */
    void testScorecardVersionFormat1() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'d.x.e',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('scorecardVersion')
        assertNotNull "Expecting to find an error on the scorecardVersion field", badField
    }

    /**
     * Test that the scorecard version must be format of x.x.x. Here x is a number.
     */
    void testScorecardVersionFormat2() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.1.e',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('scorecardVersion')
        assertNotNull "Expecting to find an error on the scorecardVersion field", badField
    }

    /**
     * Test that the scorecard version must be format of x.x.x. Here x is a number.
     */
    void testScorecardVersionFormat3() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('scorecardVersion')
        assertNotNull "Expecting to find an error on the scorecardVersion field", badField
    }

    /**
     * Test the max size of the scorecard version field.
     */
    void testScorecardVersionMaxSize() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'11111.11111.11111',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('scorecardVersion')
        assertNotNull "Expecting to find an error on the scorecardVersion field", badField
    }

    /**
     * Test that the minimum score must be grater or equal to 0.
     */
    void testMinimumScoreMin() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:-10.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('minimumScore')
        assertNotNull "Expecting to find an error on the minimumScore field", badField
    }

    /**
     * Test that the minimum score must be less or equal to 100.
     */
    void testMinimumScoreMax() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:110.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        def badField = scorecard.errors.getFieldError('minimumScore')
        assertNotNull "Expecting to find an error on the minimumScore field", badField
    }

    /**
     * Test that the maximum score must be grater or equal to 0.
     */
    void testMaximumScoreMin() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:75.0,
                maximumScore:-10.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        def badField = scorecard.errors.getFieldError('maximumScore')
        assertNotNull "Expecting to find an error on the maximumScore field", badField
    }

    /**
     * Test that the maximum score must be less or equal to 100.
     */
    void testMaximumScoreMax() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:75.0,
                maximumScore:110.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('maximumScore')
        assertNotNull "Expecting to find an error on the maximumScore field", badField
    }

    /**
     * Test that the minimum score must be less then maximum score.
     */
    void testMaximumScoreGreaterThanMinimumScore1() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:75.0,
                maximumScore:75.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('minimumScore')
        assertNotNull "Expecting to find an error on the minimumScore field", badField
    }

    /**
     * Test that the minimum score must be less then maximum score.
     */
    void testMaximumScoreGreaterThanMinimumScore2() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:75.0,
                maximumScore:73.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('minimumScore')
        assertNotNull "Expecting to find an error on the minimumScore field", badField
    }

    /**
     * Test that the scorecard type field cannot be null.
     */
    void testScorecardTypeNull() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('scorecardType')
        assertNotNull "Expecting to find an error on the scorecardType field", badField
    }

    /**
     * Test that the scorecard status field cannot be null.
     */
    void testScorecardStatusNull() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('scorecardStatus')
        assertNotNull "Expecting to find an error on the scorecardStatus field", badField
    }

    /**
     * Test that the create user field cannot be blank.
     */
    void testCreateUserBlank() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'    ',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('createUser')
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
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:sb,
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('createUser')
        assertNotNull "Expecting to find an error on the createUser field", badField
    }

    /**
     * Test that the modify user field cannot be blank.
     */
    void testModifyUserBlank() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'    '
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('modifyUser')
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
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.1',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:sb
                )

        assertFalse "Validation should not succeed", scorecard.validate()
        assertEquals "There should be only 1 error", 1, scorecard.errors.getAllErrors().size()
        def badField = scorecard.errors.getFieldError('modifyUser')
        assertNotNull "Expecting to find an error on the modifyUser field", badField
    }

    /**
     * Test that the scorecard name can be the same if the scorecard version is different.
     */
    void testSameNameWithDifferentVersion() {
        def scorecard = new Scorecard(
                scorecardName:'name',
                scorecardVersion:'1.0.0',
                minimumScore:75.0,
                maximumScore:100.0,
                scorecardType:scorecardType,
                scorecardStatus:scorecardStatus,
                projectCategory:projectCategory,
                createUser:'root',
                modifyUser:'root'
                )
        scorecard.save(flush:true)

        def scorecard2 = new Scorecard(
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
        scorecard2.save(flush:true)
        assertNotNull "The object should be persisted to the database", Scorecard.findByScorecardNameAndScorecardVersion('name', '1.0.1')
        scorecard2.delete(flush:true)
        scorecard.delete(flush:true)
    }

    /**
     * Test that the scorecard can't be deleted when the scorecard has been used.
     */
    void testCannotDeleteScorecardWhenUsed() {
        // create a new scorecard
        def scorecard = new Scorecard(
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
        scorecard.save(flush:true)
        assertNotNull "The scorecard shoud be persisted to the database", Scorecard.findByScorecardNameAndScorecardVersion('name', '1.0.1')
        // create a review for this scorecard so that the scorecard is in used
        def review = new Review(scorecard:scorecard)
        review.save(flush:true)
        assertNotNull "The review shoud be persisted to the database", Review.findByScorecard(scorecard)
        scorecard.delete(flush:true)
        assertTrue "There should be errors when delete the scorecard", scorecard.hasErrors()

        sessionFactory.getCurrentSession().clear()
        assertNotNull "The scorecard should not be deleted", Scorecard.findByScorecardNameAndScorecardVersion('name', '1.0.1')

        review.delete(flush:true)
        scorecard = Scorecard.findByScorecardNameAndScorecardVersion('name', '1.0.1')
        scorecard.delete(flush:true)
        assertNull "The scorecard should be deleted", Scorecard.findByScorecardNameAndScorecardVersion('name', '1.0.1')
    }

    /**
     * Test that the scorecard can't be updated when the scorecard has been used.
     */
    void testCannotUpdateScorecardWhenUsed() {
        // create a new scorecard
        def scorecard = new Scorecard(
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
        scorecard.save(flush:true)
        assertNotNull "The scorecard shoud be persisted to the database", Scorecard.findByScorecardNameAndScorecardVersion('name', '1.0.1')
        // create a review for this scorecard so that the scorecard is in used
        def review = new Review(scorecard:scorecard)
        review.save(flush:true)
        assertNotNull "The review shoud be persisted to the database", Review.findByScorecard(scorecard)

        scorecard.minimumScore = 80.0
        scorecard.save(flush:true)

        sessionFactory.getCurrentSession().clear()
        assertTrue "There should be errors when update the scorecard", scorecard.hasErrors()
        assertEquals "The scorecard should not be updated", 75.0, Scorecard.get(scorecard.id).minimumScore

        review.delete(flush:true)
        Scorecard.get(scorecard.id).delete(flush:true)
    }

    /**
     * Test that the the sum wieght of groups/sections/question must be 100.0 when updating the scorecard.
     */
    void testSumWeight() {
        // create a new scorecard
        def scorecard = new Scorecard(
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
        scorecard.save(flush:true)

        // create a new scorecard group
        def scorecardGroup1 = new ScorecardGroup(
                groupName:'group1',
                weight:70.0,
                sort:1,
                scorecard:scorecard
                )
        scorecardGroup1.save(flush:true)

        // create a new scorecard group
        def scorecardGroup2 = new ScorecardGroup(
                groupName:'group2',
                weight:30.0,
                sort:1,
                scorecard:scorecard
                )
        scorecardGroup2.save(flush:true)

        sessionFactory.getCurrentSession().clear()

        // try to update the scorecard
        scorecard = Scorecard.get(scorecard.id)
        scorecard.minimumScore = 80.0
        scorecard.save(flush:true)

        sessionFactory.getCurrentSession().clear()

        assertFalse "There should be no errors when update the scorecard", scorecard.hasErrors()
        assertEquals "The scorecard should be updated", 80.0, Scorecard.get(scorecard.id).minimumScore

        ScorecardGroup.get(scorecardGroup1.id).delete(flush:true)
        ScorecardGroup.get(scorecardGroup2.id).delete(flush:true)
        Scorecard.get(scorecard.id).delete(flush:true)
    }

    /**
     * Test that the ORM mapping can work.
     */
    void testMapping() {
        def scorecard = new Scorecard(
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
        scorecard.save(flush:true)

        def retrieveScorecard = Scorecard.findByScorecardName('name')
        assertNotNull 'The object should be persistence to the database', retrieveScorecard
        assertEquals retrieveScorecard.scorecardName, scorecard.scorecardName
        assertEquals retrieveScorecard.scorecardVersion, scorecard.scorecardVersion
        assertEquals retrieveScorecard.minimumScore, scorecard.minimumScore
        assertEquals retrieveScorecard.maximumScore, scorecard.maximumScore
        assertEquals retrieveScorecard.createUser, scorecard.createUser
        assertEquals retrieveScorecard.modifyUser, scorecard.modifyUser
        assertEquals retrieveScorecard.scorecardType.id, scorecard.scorecardType.id
        assertEquals retrieveScorecard.scorecardStatus.id, scorecard.scorecardStatus.id
        assertEquals retrieveScorecard.projectCategory.id, scorecard.projectCategory.id
        assertNotNull retrieveScorecard.dateCreated
        assertNotNull retrieveScorecard.lastUpdated
        scorecard.delete(flush:true)
    }
}
