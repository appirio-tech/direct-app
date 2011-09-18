/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import org.hibernate.SessionFactory;

import grails.test.*

/**
 * This class is integration test cases for <code>ScorecardSection</code> domain.
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
class ScorecardSectionIntegrationTests extends GroovyTestCase {
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
     * A <code>ScorecardGroup</code> instance to persists to database for test.
     */
    ScorecardGroup scorecardGroup

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

        scorecardGroup = new ScorecardGroup(
                groupName:'name',
                weight:70.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )
        scorecardGroup.save()
    }

    /**
     * Tear down the test environment
     */
    protected void tearDown() {
        super.tearDown()

        ScorecardGroup.get(scorecardGroup.id).delete()
        Scorecard.get(scorecard.id).delete()
        ProjectCategory.get(projectCategory.id).delete()
        ProjectType.get(projectType.id).delete()
        ScorecardType.get(scorecardType.id).delete()
        ScorecardStatus.get(scorecardStatus.id).delete()
    }

    /**
     * Test that the section name cannot be null.
     */
    void testSectionNameNull() {
        def scorecardSection = new ScorecardSection(
                weight:100.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('sectionName')
        assertNotNull "Expecting to find an error on the sectionName field", badField
    }

    /**
     * Test that the section name cannot be blank.
     */
    void testSectionNameBlank() {
        def scorecardSection = new ScorecardSection(
                sectionName:'   ',
                weight:100.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('sectionName')
        assertNotNull "Expecting to find an error on the sectionName field", badField
    }

    /**
     * Test the max size of the section name.
     */
    void testSectionNameMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 1025; i++) {
            sb.append 'a'
        }
        def scorecardSection = new ScorecardSection(
                sectionName:sb,
                weight:100.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('sectionName')
        assertNotNull "Expecting to find an error on the sectionName field", badField
    }

    /**
     * Test that the section name can be the same if the group is different.
     */
    void testSectionNameUnique2() {
        // create a new ScorecardGroup
        def scorecardGroup2 = new ScorecardGroup(
                groupName:'name2',
                weight:70.0,
                sort:1,
                scorecard:scorecard,
                createUser:'root',
                modifyUser:'root'
                )
        scorecardGroup2.save(flush:true)

        // create a new section whose group is the first scorecard group
        def scorecardSection = new ScorecardSection(
                sectionName:'group',
                weight:100.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )
        scorecardSection.save(flush:true)

        // try to create another new section with the same name whose group is the second scorecard group
        def scorecardSection2 = new ScorecardSection(
                sectionName:'group',
                weight:100.0,
                sort:1,
                scorecardGroup:scorecardGroup2,
                createUser:'root',
                modifyUser:'root'
                )
        assertTrue "Validation should succeed", scorecardSection2.validate()
        scorecardSection.delete(flush:true)
        scorecardGroup2.delete(flush:true)
    }

    /**
     * Test that the weight field cannot be null.
     */
    void testWeightNull() {
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test that the weight field must be a number.
     */
    void testWeightNotNumber() {
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:'asdf',
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test that the weight field cannot be zero.
     */
    void testWeightZero() {
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:0.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test that the weight field cannot be negative.
     */
    void testWeightNegative() {
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:-10.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test the max value of the weight field.
     */
    void testWeightMax() {
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:101.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test that the sort field cannot be null.
     */
    void testSortNull() {
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:70.0,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('sort')
        assertNotNull "Expecting to find an error on the sort field", badField
    }

    /**
     * Test that the sort field must be a number.
     */
    void testSortNotNumber() {
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:70.0,
                sort:'asdf',
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('sort')
        assertNotNull "Expecting to find an error on the sort field", badField
    }

    /**
     * Test that the sort field cannot be negative.
     */
    void testSortNegative() {
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:70.0,
                sort:-1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('sort')
        assertNotNull "Expecting to find an error on the sort field", badField
    }

    /**
     * Test that the scorecard group field cannot be null.
     */
    void testScorecardGroupNull() {
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:70.0,
                sort:1,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('scorecardGroup')
        assertNotNull "Expecting to find an error on the scorecardGroup field", badField
    }

    /**
     * Test that the create user field cannot be blank.
     */
    void testCreateUserBlank() {
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:70.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'    ',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('createUser')
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
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:70.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:sb,
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('createUser')
        assertNotNull "Expecting to find an error on the createUser field", badField
    }

    /**
     * Test that the modify user field cannot be blank.
     */
    void testModifyUserBlank() {
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:70.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'    '
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('modifyUser')
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
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:70.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:sb
                )

        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('modifyUser')
        assertNotNull "Expecting to find an error on the modifyUser field", badField
    }

    /**
     * Test that the scorecard must not be in used when updating/inserting the scorecard section.
     */
    void testScorecardInUsed1() {
        // create a review for this scorecard so that the scorecard is in used
        def review = new Review(scorecard:scorecard)
        review.save(flush:true)

        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:100.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )
        assertFalse "Validation should not succeed", scorecardSection.validate()
        assertEquals "There should be only 1 error", 1, scorecardSection.errors.getAllErrors().size()
        def badField = scorecardSection.errors.getFieldError('scorecardGroup')
        assertNotNull "Expecting to find an error on the scorecardGroup field", badField

        review.delete(flush:true)
    }

    /**
     * Test that the scorecard must not be in used when deleting the scorecard section.
     */
    void testScorecardInUsed2() {
        // create a new scorecard section
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:100.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )
        scorecardSection.save(flush:true)
        assertFalse "The object should have persisted to database", scorecardSection.hasErrors()

        // create a review for this scorecard so that the scorecard is in used
        def review = new Review(scorecard:scorecard)
        review.save(flush:true)

        sessionFactory.getCurrentSession().clear()

        // try to delete the scorecard section
        scorecardSection.delete(flush:true)
        assertTrue "There should be errors when delete the scorecard section", scorecardSection.hasErrors()

        sessionFactory.getCurrentSession().clear()
        assertNotNull "The scorecard section should not be deleted", ScorecardSection.get(scorecardSection.id)
        Review.get(review.id).delete(flush:true)
        ScorecardSection.get(scorecardSection.id).delete(flush:true)
    }

    /**
     * Test that the ORM mapping can work.
     */
    void testMapping() {
        def scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:70.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )
        scorecardSection.save(flush:true)
        def retrieveScorecardSection = ScorecardSection.findBySectionNameAndScorecardGroup('name', scorecardGroup)
        assertNotNull "The object should be persisted to the database", retrieveScorecardSection
        assertEquals retrieveScorecardSection.sectionName, scorecardSection.sectionName
        assertEquals retrieveScorecardSection.weight, scorecardSection.weight
        assertEquals retrieveScorecardSection.sort, scorecardSection.sort
        assertEquals retrieveScorecardSection.scorecardGroup.id, scorecardSection.scorecardGroup.id
        assertEquals retrieveScorecardSection.createUser, scorecardSection.createUser
        assertEquals retrieveScorecardSection.modifyUser, scorecardSection.modifyUser
        assertNotNull retrieveScorecardSection.dateCreated
        assertNotNull retrieveScorecardSection.lastUpdated
        scorecardSection.delete(flush:true)
    }
}
