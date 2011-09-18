/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import java.util.Date

/**
 * This class is a <code>Domain</code> class for scorecard_section table.
 *
 * <p>
 * Version 1.1 change log: Add expose field to be used by JSON RESTful API.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.1
 */
class ScorecardSection extends Audit {
    /**
     * The domain class name used by JSON RESTful API plugin.
     * 
     * @since 1.1
     */
    static expose = 'scorecard-section'
   
    /**
     * Represents the name of this score card section.
     */
    String sectionName

    /**
     * Represents the weight of this score card section.
     */
    BigDecimal weight

    /**
     * Represents the sort order of this score card section.
     */
    Integer sort

    /**
     * Define the ORM relations.
     */
    static belongsTo = [scorecardGroup:ScorecardGroup]
    static hasMany = [scorecardQuestions:ScorecardQuestion]

    /**
     * Define the validation rules
     */
    static constraints = {
        sectionName(blank:false, maxSize:1024)
        weight(blank:false, validator:{it > 0.0 && it <= 100})
        sort(blank:false, min:0)
        scorecardGroup(blank:false, validator:{
            // checks whether the scorecard is editable
            Util.checkScorecardEditable(it.scorecard)
        })
        scorecardQuestions(blank:false, validator:{val,  obj ->
            // checks whether the sum weight of questions is 100.0
            // TODO: we allow the sum weight of questions be 0 currently, will be fixed later
            val == null || val.size() == 0 || val.inject(0) { result, i -> result += i.weight  } == 100.0
        })
        createUser(blank:false, maxSize:64, display:false, editable:false)
        modifyUser(blank:false, maxSize:64, display:false, editable:false)
    }

    /**
     * Define the ORM mapping.
     */
    static mapping = {
        table 'scorecard_section'
        id column:'scorecard_section_id'
        scorecardQuestions cascade:'all-delete-orphan', sort: 'sort', order: 'asc'
        scorecardGroup cascade:'save-update'
		id generator:'com.topcoder.database.utilities.TopCoderTableHiLoGenerator', params:[seq_name:'scorecard_section_id_seq']
        columns {
            sectionName column:'name'
            scorecardGroup column:'scorecard_group_id'
            dateCreated column:'create_date'
            lastUpdated column:'modify_date'
        }
    }

    /**
     * Override the toString method to provide the meaningful name.
     *
     * @return the name of the score card section
     */
    String toString() {
        "${scorecardGroup.scorecard.scorecardName} - ${scorecardGroup.groupName} - ${sectionName}"
    }

    /**
     * This closure will be executed before deleting a record. We will check whether the scorecard is editable.
     */
    def beforeDelete = {
        Util.checkScorecardEditable(this.scorecardGroup.scorecard)
    }

    /**
     * This closure will be executed after deleting a record. We will check whether the scorecard is editable, if not,
     * error message will be added
     */
    def afterDelete = {
        if (!Util.checkScorecardEditable(this.scorecardGroup.scorecard)) {
            this.errors.reject('com.topcoder.admin.scorecards.scorecardInUsed')
        }
    }
}
