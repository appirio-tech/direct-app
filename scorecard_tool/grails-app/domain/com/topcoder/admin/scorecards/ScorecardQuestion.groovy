/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import java.util.Date

/**
 * This class is a <code>Domain</code> class for scorecard_question table.
 *
 * <p>
 * Version 1.1 change log: Add expose field to be used by JSON RESTful API.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.1
 */
class ScorecardQuestion extends Audit {
    /**
     * The domain class name used by JSON RESTful API plugin.
     * 
     * @since 1.1
     */
    static expose = 'scorecard-question'
   
    /**
     * Represents the description of this score question.
     */
    String description

    /**
     * Represents the guideline of this score card question.
     */
    String guideline

    /**
     * Represents the weight of this score card question.
     */
    BigDecimal weight

    /**
     * Represents the sort order of this score card question.
     */
    Integer sort

    /**
     * A flag indicates whether we can upload document for this score card question.
     */
    Boolean uploadDocument

    /**
     * A flag indicates whether a upload document must be uploaded for this score card question.
     */
    Boolean uploadDocumentRequired

    /**
     * Represents the scorecard question type reference.
     */
    ScorecardQuestionType questionType

    /**
     * Define the ORM relations.
     */
    static belongsTo = [scorecardSection:ScorecardSection]

    /**
     * Define the validation rules
     */
    static constraints = {
        description(blank:false, maxSize:4096)
        guideline(blank:false, maxSize:4096)
        weight(blank:false, validator:{
            it > 0.0 && it <= 100.0
        })
        sort(blank:false, min:0)
        uploadDocument(blank:false)
        uploadDocumentRequired(blank:false)
        questionType(blank:false)
        scorecardSection(blank:false, validator:{
            // checks whether the scorecard is editable
            Util.checkScorecardEditable(it.scorecardGroup.scorecard)
        })
        createUser(blank:false, maxSize:64, display:false, editable:false)
        modifyUser(blank:false, maxSize:64, display:false, editable:false)
    }

    /**
     * Define the ORM mapping.
     */
    static mapping = {
        table 'scorecard_question'
        id column:'scorecard_question_id'
		id generator:'com.topcoder.database.utilities.TopCoderTableHiLoGenerator', params:[seq_name:'scorecard_question_id_seq']
        scorecardSection cascade:'save-update'
        columns {
            dateCreated column:'create_date'
            lastUpdated column:'modify_date'
            scorecardSection column:'scorecard_section_id'
            questionType column:'scorecard_question_type_id'
        }
    }

    /**
     * Override the toString method to provide the meaningful name.
     *
     * @return the name of the score card question
     */
    String toString() {
        "Question ${id}"
    }

    /**
     * This closure will be executed before deleting a record. We will check whether the scorecard is editable.
     */
    def beforeDelete = {
        Util.checkScorecardEditable(this.scorecardSection.scorecardGroup.scorecard)
    }

    /**
     * This closure will be executed after deleting a record. We will check whether the scorecard is editable, if not,
     * error message will be added
     */
    def afterDelete = {
        if (!Util.checkScorecardEditable(this.scorecardSection.scorecardGroup.scorecard)) {
            this.errors.reject('com.topcoder.admin.scorecards.scorecardInUsed')
        }
    }
}
