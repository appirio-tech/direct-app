/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards


/**
 * This class is a <code>Domain</code> class for scorecard table.
 *
 * <p>
 * Version 1.1 change log: Add expose field to be used by JSON RESTful API.
 * </p>
 *
 * @author TCSASSEMBER
 * @version 1.1
 */
class Scorecard extends Audit {
    /**
     * The domain class name used by JSON RESTful API plugin.
     * 
     * @since 1.1
     */
    static expose = 'scorecard'
    
    static api = [
        excludedFields: ['beforeDelete', 'afterDelete', 'beforeUpdate', 'afterUpdate', 'checkScorecardEditableWithErrorMessage'],
        list: { params -> Scorecard.list(params) },
        count: { params -> Scorecard.count() }
    ]
    
    /**
     * Represents the name of this score card
     */
    String scorecardName

    /**
     * Represents the version of this score card. 
     */
    String scorecardVersion

    /**
     * Represents the minimum score of this score card.
     */
    BigDecimal minimumScore

    /**
     * Represents the maximum score of this score card.
     */
    BigDecimal maximumScore

    /**
     * Represents the scorecard type reference.
     */
    ScorecardType scorecardType

    /**
     * Represents the scoreacrd status reference.
     */
    ScorecardStatus scorecardStatus

    /**
     * Represents the project category reference.
     */
    ProjectCategory projectCategory
	
    /**
     * Define the ORM relations.
     */
    static hasMany = [scorecardGroups:ScorecardGroup]

    /**
     * Define the validation rules
     */
    static constraints = {
        scorecardName(blank:false, maxSize:64, unique:'scorecardVersion')
        scorecardVersion(blank:false, matches:"[0-9]+\\.[0-9]+\\.[0-9]+", maxSize:16)
        minimumScore(min:0.0, max:100.0, validator:{ val, obj ->
            val < obj.properties['maximumScore']
        })
        maximumScore(min:0.0, max:100.0)
        scorecardType(blank:false)
        scorecardStatus(blank:false)
        projectCategory(blank:false)
        scorecardGroups(blank:false, validator:{val,  obj ->
            // checks whether the sum weight of groups is 100.0
            // TODO: we allow the sum weight of groups be 0 currently, will be fixed later
            val == null || val.size() == 0 || val.inject(0) { result, i -> result += i.weight  } == 100.0
        })
        createUser(blank:false, maxSize:64, display:false, editable:false)
        modifyUser(blank:false, maxSize:64, display:false, editable:false)
    }

    /**
     * Define the ORM mapping.
     */
    static mapping = {
        table 'scorecard'
        id column:'scorecard_id'
        scorecardGroups cascade:'all-delete-orphan', sort: 'sort', order: 'asc'
        id generator:'com.topcoder.database.utilities.TopCoderTableHiLoGenerator', params:[seq_name:'scorecard_id_seq']
        version 'version_number'
        columns {
            scorecardName column:'name'
            scorecardVersion column:'version'
            minimumScore column:'min_score'
            maximumScore column:'max_score'
            dateCreated column:'create_date'
            lastUpdated column:'modify_date'
            scorecardType column:'scorecard_type_id'
            scorecardStatus column:'scorecard_status_id'
            projectCategory column:'project_category_id'
        }
    }

    /**
     * Override the toString method to provide the meaningful name.
     *
     * @return the name of the score card
     */
    String toString() {
        scorecardName
    }

    /**
     * Checks whether the scorecard is editable, it not, error message will be added.
     */
    def checkScorecardEditableWithErrorMessage = {
        if (!Util.checkScorecardEditable(this)) {
            this.errors.reject('com.topcoder.admin.scorecards.scorecardInUsed')
        }
    }

    /**
     * This closure will be executed before deleting a record. We will check whether the scorecard is editable.
     */
    def beforeDelete = { Util.checkScorecardEditable(this) }

    /**
     * This closure will be executed after deleting a record. We will check whether the scorecard is editable, if not,
     * error message will be added.
     */
    def afterDelete = checkScorecardEditableWithErrorMessage

    /**
     * This closure will be executed before updating the record. We will check whether the scorecard is editable.
     */
    def beforeUpdate = {Util.checkScorecardEditable(this) }

    /**
     * This closure will be executed before updating the record. We will check whether the scorecard is editable, if not,
     * error message will be added.
     */
    def afterUpdate = checkScorecardEditableWithErrorMessage
}
