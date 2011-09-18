/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

/**
 * This class is a <code>Domain</code> class for review table.
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
class Review {
    /**
     * Define the ORM relations.
     */
    static belongsTo = [scorecard:Scorecard]

    /**
     * Define the validation rules
     */
    static constraints = {
    }

    /**
     * Define the ORM mapping.
     */
    static mapping = {
        table 'review'
        id column:'review_id'
        version false
        autoTimestamp false
        columns { scorecard:'scorecard_id' }
    }
}
