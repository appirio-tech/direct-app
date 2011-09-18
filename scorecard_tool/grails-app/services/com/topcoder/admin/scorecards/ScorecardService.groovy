/*
* Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
*/
package com.topcoder.admin.scorecards

import groovy.sql.Sql

import org.springframework.web.servlet.support.RequestContextUtils as RCU

/**
* This class is the service for <code>Scorecard</code> domain. Grails will handle transaction management
* in service classes.
*
* @author TCSASSEMBER
* @version 1.1
*/
class ScorecardService {
    /**
     * Use transaction management.
     */
    static transactional = true

    /**
     * The <code>SessionFactory</code> instance which will be injected by grails framework.
     */
    def sessionFactory
    
    /**
     * Save the scorecard data to database.
     * 
     * @param messageSource the message source instance used to retrieve message
     * @param scorecardData the scorecard data
     * @param request the http request instance
     * @return the scorecard id
     */
    def saveScorecard(messageSource, scorecardData, request) {
        try {
            // save scorecard
            def scorecardInstance
            def toCreate = false
            if (scorecardData.id && scorecardData.id > 0) {
                scorecardInstance = Scorecard.get(scorecardData.id)
            } else {
                toCreate = true
                scorecardInstance = new Scorecard()
            }
            
            scorecardInstance.properties = [
                'projectCategory.id': scorecardData.projectCategory,
                'scorecardName': scorecardData.scorecardName,
                'scorecardVersion': scorecardData.scorecardVersion,
                'scorecardType.id': scorecardData.scorecardType,
                'minimumScore': scorecardData.minimumScore,
                'maximumScore': scorecardData.maximumScore,
                'scorecardStatus.id': scorecardData.scorecardStatus
            ]
            
            if (!scorecardInstance.hasErrors() && scorecardInstance.save(flush:true)) {
                if (scorecardInstance.hasErrors()) {
                    throw new Exception(extractErrors(messageSource, scorecardInstance, request).join(";"));
                }
            } else {
                throw new Exception(extractErrors(messageSource, scorecardInstance, request).join(";"));
            }
            
            def groupIds = [] as Set;
            def sectionIds = [] as Set;
            def questionIds = [] as Set;
    
            // save scorecard groups
            for (int i = 0; i < scorecardData.scorecardGroups.size(); i++) {
                def group = scorecardData.scorecardGroups.getJSONObject(i)
                def groupDomain
                if (group.containsKey("id")) {
                    groupDomain = ScorecardGroup.get(group.id)
                } else {
                    groupDomain = new ScorecardGroup()
                }
                groupDomain.properties = [
                    'groupName': group.groupName,
                    'weight': group.weight,
                    'sort': group.sort,
                    'scorecard.id': scorecardInstance.id
                ]
                
                if (!groupDomain.hasErrors() && groupDomain.save(flush:true)) {
                    if (groupDomain.hasErrors()) {
                        throw new Exception(extractErrors(messageSource, groupDomain, request).join(";"));
                    }
                } else {
                    throw new Exception(extractErrors(messageSource, groupDomain, request).join(";"));
                }
                group.put("id", groupDomain.id)
                groupIds << groupDomain.id
                
                // save group sections
                for (int j = 0; j < group.scorecardSections.size(); j++) {
                    def section = group.scorecardSections.getJSONObject(j)
                    def sectionDomain
                    if (section.containsKey("id")) {
                        sectionDomain = ScorecardSection.get(section.id)
                    } else {
                        sectionDomain = new ScorecardSection()
                    }
                    sectionDomain.properties = [
                        'sectionName': section.sectionName,
                        'weight': section.weight,
                        'sort': section.sort,
                        'scorecardGroup.id': groupDomain.id
                    ]

                    if (!sectionDomain.hasErrors() && sectionDomain.save(flush:true)) {
                        if (sectionDomain.hasErrors()) {
                            throw new Exception(extractErrors(messageSource, sectionDomain, request).join(";"));
                        }
                    } else {
                        throw new Exception(extractErrors(messageSource, sectionDomain, request).join(";"));
                    }
                    section.put("id", sectionDomain.id)
                    sectionIds << sectionDomain.id
                    
                    // save section questions
                    for (int k = 0; k < section.scorecardQuestions.size(); k++) {
                        def question = section.scorecardQuestions.getJSONObject(k)
                        def questionDomain
                        def uploadDocumentRequired = false
                        if (question.containsKey("id")) {
                            questionDomain = ScorecardQuestion.get(question.id)
                            uploadDocumentRequired = questionDomain.uploadDocumentRequired
                        } else {
                            questionDomain = new ScorecardQuestion()
                        }
                        
                        questionDomain.properties = [
                            'questionType.id' : question.questionType,
                            'description': question.description,
                            'guideline': question.guideline,
                            'weight': question.weight,
                            'uploadDocument': question.uploadDocument,
                            'scorecardSection.id': sectionDomain.id,
                            'uploadDocumentRequired': uploadDocumentRequired,
                            sort: question.sort
                        ]

                        if (!questionDomain.hasErrors() && questionDomain.save(flush:true)) {
                            if (questionDomain.hasErrors()) {
                                throw new Exception(extractErrors(messageSource, questionDomain, request).join(";"));
                            }
                        } else {
                            throw new Exception(extractErrors(messageSource, questionDomain, request).join(";"));
                        }
                        question.put("id", questionDomain.id)
                        questionIds << questionDomain.id
                    }
                }
            }
            
            // delete the unused data
            if (!toCreate) {
                scorecardInstance.scorecardGroups.each {group ->
                    group.scorecardSections.each {section ->
                        section.scorecardQuestions.each {question ->
                            if (!questionIds.contains(question.id)) {
                                def sql = new Sql(sessionFactory.currentSession.connection())
                                sql.executeUpdate("delete from scorecard_question where scorecard_question_id=?", [question.id])
                            }
                        }
                    }
                }
                scorecardInstance.scorecardGroups.each {group ->
                    group.scorecardSections.each {section ->
                        if (!sectionIds.contains(section.id)) {
                            def sql = new Sql(sessionFactory.currentSession.connection())
                            sql.executeUpdate("delete from scorecard_section where scorecard_section_id=?", [section.id])
                        }
                    }
                }
                scorecardInstance = Scorecard.get(scorecardData.id)
                scorecardInstance.scorecardGroups.each {group ->
                    if (!groupIds.contains(group.id)) {
                        def sql = new Sql(sessionFactory.currentSession.connection())
                        sql.executeUpdate("delete from scorecard_group where scorecard_group_id=?", [group.id])
                    }
                }
            }
            return scorecardInstance.id
        } catch (Exception e) {
            // error occurs, throw RuntimeException so that the transaction can be rollback
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    /**
     * Gets the error message from domain class
     * 
     * @param messageSource the message source instance used to retrieve message
     * @param model the domain class instance
     * @param request the http request instance
     * @return the error message
     */
    private extractErrors(messageSource, model, request) {
        def locale = RCU.getLocale(request)
        model.errors.fieldErrors.collect { error ->
            messageSource.getMessage(error, locale)
        }
    }
}
