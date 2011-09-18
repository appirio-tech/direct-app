/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import java.util.Date

/**
 * This class is a <code>Domain</code> class for project_category_lu table.
 * 
 * <p>
 * Version 1.1 change log: Add expose field to be used by JSON RESTful API.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.1
 */
class ProjectCategory extends Audit {
    /**
     * The domain class name used by JSON RESTful API plugin.
     * 
     * @since 1.1
     */
    static expose = 'project-category'
    
    /**
     * Represents the name of the project category.
     */
    String name

    /**
     * Represents the description of the project category.
     */
    String description

    /**
     * A flag indicates whether to display this project category.
     */
    Boolean display

    /**
     * Represents the display order of the project category.
     */
    Integer displayOrder

    /**
     * Represents the project type reference.
     */
    ProjectType projectType

    /**
     * Define the validation rules
     */
    static constraints = {
        name(blank:false, maxSize:64, unique:'projectType')
        description(blank:false, maxSize:254)
        display(blank:false)
        displayOrder(min:0)
        projectType(blank:false)
        createUser(blank:false, maxSize:64, display:false, editable:false)
        modifyUser(blank:false, maxSize:64, display:false, editable:false)
    }

    /**
     * Define the ORM mapping.
     */
    static mapping = {
        table 'project_category_lu'
        id column:'project_category_id'
        id generator:'increment'
        projectType cascade:'save-update'
        columns {
            dateCreated column:'create_date'
            lastUpdated column:'modify_date'
            projectType column:'project_type_id'
        }
    }

    /**
     * Override the toString method to provide the meaningful name.
     * 
     * @return the name of the project category
     */
    String toString() {
        name
    }
}
