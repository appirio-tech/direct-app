/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import java.util.Date

/**
 * This class is a <code>Domain</code> class for scorecard_status_lu table.
 *
 * <p>
 * Version 1.1 change log: Add expose field to be used by JSON RESTful API.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.1
 */
class ScorecardStatus extends Audit {
    /**
     * The domain class name used by JSON RESTful API plugin.
     * 
     * @since 1.1
     */
    static expose = 'scorecard-status'
   
    /**
     * Represents the name of this score card status.
     */
    String name

    /**
     * Represents the description of this score card staus.
     */
    String description

    /**
     * Define the validation rules
     */
    static constraints = {
        name(blank:false, unique:true, maxSize:64)
        description(blank:false, maxSize:254)
        createUser(blank:false, maxSize:64, display:false, editable:false)
        modifyUser(blank:false, maxSize:64, display:false, editable:false)
    }

    /**
     * Define the ORM mapping.
     */
    static mapping = {
        table 'scorecard_status_lu'
        id column:'scorecard_status_id'
        id generator:'increment'
        columns {
            dateCreated column:'create_date'
            lastUpdated column:'modify_date'
        }
    }

    /**
     * Override the toString method to provide the meaningful name.
     *
     * @return the name of the scorecard status
     */
    String toString() {
        name
    }
}
