/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import java.util.Date

/**
 * This class is a abstract class providing the basic fields for audit. The <code>Domain</code> classes
 * will extend this class.
 * 
 * @author TCSASSEMBER
 * @version 1.0
 */
abstract class Audit {
    /**
     * Represents the create user of a database record.
     * 
     * TODO:We use hard coded values currently, will be fixed later
     */
    String createUser = "System"

    /**
     * Represents the creation time of this project category.
     */
    Date dateCreated

    /**
     * Represents the modify user of a database record.
     * 
     * TODO:We use hard coded values currently, will be fixed later
     */
    String modifyUser = "System"

    /**
     * Represents the last updated time of this project category.
     */
    Date lastUpdated
}
