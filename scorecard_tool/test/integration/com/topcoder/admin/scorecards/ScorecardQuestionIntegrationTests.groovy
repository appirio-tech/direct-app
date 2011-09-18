/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import org.hibernate.SessionFactory;

import grails.test.*

/**
 * This class is integration test cases for <code>ScorecardQuestion</code> domain.
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
class ScorecardQuestionIntegrationTests extends GroovyTestCase {
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
     * A <code>ScorecardQuestionType</code> instance to persists to database for test.
     */
    ScorecardQuestionType questionType

    /**
     * A <code>Scorecard</code> instance to persists to database for test.
     */
    Scorecard scorecard

    /**
     * A <code>ScorecardGroup</code> instance to persists to database for test.
     */
    ScorecardGroup scorecardGroup

    /**
     * A <code>ScorecardSection</code> instance to persists to database for test.
     */
    ScorecardSection scorecardSection

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

        questionType = new ScorecardQuestionType(
                name:'Scale (1-4)',
                description:'Scale (1-4)',
                createUser:'root',
                modifyUser:'root'
                )
        questionType.save()

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

        scorecardSection = new ScorecardSection(
                sectionName:'name',
                weight:70.0,
                sort:1,
                scorecardGroup:scorecardGroup,
                createUser:'root',
                modifyUser:'root'
                )
        scorecardSection.save()
    }

    /**
     * Tear down the test environment
     */
    protected void tearDown() {
        super.tearDown()

        ScorecardSection.get(scorecardSection.id).delete()
        ScorecardGroup.get(scorecardGroup.id).delete()
        Scorecard.get(scorecard.id).delete()
        ProjectCategory.get(projectCategory.id).delete()
        ProjectType.get(projectType.id).delete()
        ScorecardType.get(scorecardType.id).delete()
        ScorecardStatus.get(scorecardStatus.id).delete()
        ScorecardQuestionType.get(questionType.id).delete()
    }

    /**
     * Test that the description cannot be null.
     */
    void testDescriptionNull() {
        def scorecardQuestion = new ScorecardQuestion(
                guideline:'guideline',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the guide field", badField
    }

    /**
     * Test that the description cannot be blank.
     */
    void testDescriptionBlank() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'    ',
                guideline:'guideline',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the guide field", badField
    }

    /**
     * Test the max size the description.
     */
    void testDescriptionMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 4097; i++) {
            sb.append 'a'
        }
        def scorecardQuestion = new ScorecardQuestion(
                description:sb,
                guideline:'guideline',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('description')
        assertNotNull "Expecting to find an error on the guide field", badField
    }

    /**
     * Test that the guideline cannot be null.
     */
    void testGuidelineNull() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('guideline')
        assertNotNull "Expecting to find an error on the guide field", badField
    }

    /**
     * Test that the guideline cannot be blank.
     */
    void testGuidelineBlank() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'    ',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('guideline')
        assertNotNull "Expecting to find an error on the guide field", badField
    }

    /**
     * Test the max size of the guideline.
     */
    void testGuidelineMaxSize() {
        def sb = new StringBuffer()
        for (int i = 0; i < 4097; i++) {
            sb.append 'a'
        }
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:sb,
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('guideline')
        assertNotNull "Expecting to find an error on the guide field", badField
    }

    /**
     * Test that the weight field cannot be null.
     */
    void testWeightNull() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test that the weight field must be a number.
     */
    void testWeightNotNumber() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:'asdf',
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test that the weight field cannot be zero.
     */
    void testWeightZero() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:0.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test that the weight field cannot be negative.
     */
    void testWeightNegative() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:-1.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test the max value of the weight field.
     */
    void testWeightMax() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:101.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('weight')
        assertNotNull "Expecting to find an error on the weight field", badField
    }

    /**
     * Test that the sort field cannot be null.
     */
    void testSortNull() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('sort')
        assertNotNull "Expecting to find an error on the sort field", badField
    }

    /**
     * Test that the sort field must be a number.
     */
    void testSortNotNumber() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:'asdf',
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('sort')
        assertNotNull "Expecting to find an error on the sort field", badField
    }

    /**
     * Test that the sort field cannot be negative.
     */
    void testSortNegative() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:-1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('sort')
        assertNotNull "Expecting to find an error on the sort field", badField
    }

    /**
     * Test that the uploadDocument field cannot be null.
     */
    void testUploadDocumentNull() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:1,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('uploadDocument')
        assertNotNull "Expecting to find an error on the uploadDocument field", badField
    }

    /**
     * Test that the uploadDocumentRequired field cannot be null.
     */
    void testUploadDocumentRequiredNull() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('uploadDocumentRequired')
        assertNotNull "Expecting to find an error on the uploadDocumentRequired field", badField
    }

    /**
     * Test that the questionType field cannot be null.
     */
    void testQuestionTypeNull() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('questionType')
        assertNotNull "Expecting to find an error on the questionType field", badField
    }

    /**
     * Test that the scorecardSection field cannot be null.
     */
    void testScorecardSectionNull() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                createUser:'root',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('scorecardSection')
        assertNotNull "Expecting to find an error on the scorecardSection field", badField
    }

    /**
     * Test that the create user field cannot be blank.
     */
    void testCreateUserBlank() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'    ',
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('createUser')
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
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:sb,
                modifyUser:'root'
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('createUser')
        assertNotNull "Expecting to find an error on the createUser field", badField
    }

    /**
     * Test that the modify user field cannot be blank.
     */
    void testModifyUserBlank() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'    '
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('modifyUser')
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
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:sb
                )

        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('modifyUser')
        assertNotNull "Expecting to find an error on the modifyUser field", badField
    }

    /**
     * Test that the scorecard must not be in used when updating/inserting the scorecard question.
     */
    void testScorecardInUsed1() {
        // create a review for this scorecard so that the scorecard is in used
        def review = new Review(scorecard:scorecard)
        review.save(flush:true)

        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )
        assertFalse "Validation should not succeed", scorecardQuestion.validate()
        assertEquals "There should be only 1 error", 1, scorecardQuestion.errors.getAllErrors().size()
        def badField = scorecardQuestion.errors.getFieldError('scorecardSection')
        assertNotNull "Expecting to find an error on the scorecardSection field", badField

        review.delete(flush:true)
    }

    /**
     * Test that the scorecard must not be in used when deleting the scorecard question.
     */
    void testScorecardInUsed2() {
        // create a new scorecard question
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )
        scorecardQuestion.save(flush:true)
        assertFalse "The object should have persisted to database", scorecardQuestion.hasErrors()

        // create a review for this scorecard so that the scorecard is in used
        def review = new Review(scorecard:scorecard)
        review.save(flush:true)

        sessionFactory.getCurrentSession().clear()

        // try to delete the scorecard question
        scorecardQuestion.delete(flush:true)
        assertTrue "There should be errors when delete the scorecard question", scorecardQuestion.hasErrors()

        sessionFactory.getCurrentSession().clear()
        assertNotNull "The scorecard question should not be deleted", ScorecardQuestion.get(scorecardQuestion.id)
        Review.get(review.id).delete(flush:true)
        ScorecardQuestion.get(scorecardQuestion.id).delete(flush:true)
    }

    /**
     * Test that the ORM mapping can work.
     */
    void testMapping() {
        def scorecardQuestion = new ScorecardQuestion(
                description:'description',
                guideline:'guide',
                weight:100.0,
                sort:1,
                uploadDocument:true,
                uploadDocumentRequired:true,
                questionType:questionType,
                scorecardSection:scorecardSection,
                createUser:'root',
                modifyUser:'root'
                )
        assertNull ScorecardQuestion.findByScorecardSection(scorecardSection)
        scorecardQuestion.save(flush:true)
        def retrieveScorecardQuestion = ScorecardQuestion.findByScorecardSection(scorecardSection)
        assertNotNull "The object should be persisted to the database", retrieveScorecardQuestion
        assertEquals retrieveScorecardQuestion.description, scorecardQuestion.description
        assertEquals retrieveScorecardQuestion.guideline, scorecardQuestion.guideline
        assertEquals retrieveScorecardQuestion.weight, scorecardQuestion.weight
        assertEquals retrieveScorecardQuestion.sort, scorecardQuestion.sort
        assertEquals retrieveScorecardQuestion.uploadDocument, scorecardQuestion.uploadDocument
        assertEquals retrieveScorecardQuestion.uploadDocumentRequired, scorecardQuestion.uploadDocumentRequired
        assertEquals retrieveScorecardQuestion.questionType.id, scorecardQuestion.questionType.id
        assertEquals retrieveScorecardQuestion.scorecardSection.id, scorecardQuestion.scorecardSection.id
        assertEquals retrieveScorecardQuestion.createUser, scorecardQuestion.createUser
        assertEquals retrieveScorecardQuestion.modifyUser, scorecardQuestion.modifyUser
        assertNotNull retrieveScorecardQuestion.dateCreated
        assertNotNull retrieveScorecardQuestion.lastUpdated
        retrieveScorecardQuestion.delete(flush:true)
    }
}
