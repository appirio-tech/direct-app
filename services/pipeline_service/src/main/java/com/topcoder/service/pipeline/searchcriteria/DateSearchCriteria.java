/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * The search criteria instance for searching with studio#contest#'start_time'/'end_time' fields.
 * </p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dateSearchCriteria", propOrder = { "startDate",
        "endDate", "overdueContests"})
public class DateSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -4740502403708940727L;

    /** The start date value. */
    private Date startDate;

    /** The end date value. */
    private Date endDate;

    /** Overdue means that the launch date of a contest is earlier than today date. */
    private boolean overdueContests;

    /**
     * Creates a new DateSearchCriteria object.
     */
    public DateSearchCriteria() {
    }

    /**
     * Constructs the criteria with start date, end date and whether shows overdue instances.
     *
     * @param startDate the start time
     * @param endDate the end time
     * @param overdueContests whether supports show overdue or not
     */
    public DateSearchCriteria(Date startDate, Date endDate, boolean overdueContests) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.overdueContests = overdueContests;
    }

    /**
     * Constructs the where clause for the criteria.
     *
     * @return where clause, could be empty, not null
     */
    public String getWhereClause() {
        if (startDate == null || endDate == null || startDate.after(endDate)) {        	
            return "";
        }        
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        

        StringBuffer sb = new StringBuffer("SELECT DISTINCT project_id, info_id FROM ")
        		.append("(SELECT p.project_id as project_id, info.id AS info_id,(SELECT max(nvl(actual_end_time, scheduled_end_time))  ")
        		.append("						              FROM project_phase ph WHERE ph.project_id=p.project_id) AS end_date, ")
        		.append("                                    (SELECT min(nvl(actual_start_time, scheduled_start_time)) ")
        		.append("                                     FROM project_phase ph WHERE ph.project_id=p.project_id) AS start_date ")
        		.append("FROM project_info p, software_competition_pipeline_info info ")
        		.append("WHERE p.project_info_type_id = 2 AND info.component_id = p.value) ")
        		.append("AS b WHERE ((start_date >= to_date('")
        		.append(formatter.format(startDate))
        		.append("', '%Y-%m-%d') AND start_date <= to_date('")
        		.append(formatter.format(endDate))
        		.append("', '%Y-%m-%d')) OR (end_date >=")
        		.append(" to_date('")
        		.append(formatter.format(startDate)).append("', '%Y-%m-%d') AND end_date <= to_date('")
        		.append(formatter.format(endDate)).append("', '%Y-%m-%d')))");
        			        
        if (!overdueContests) {
            sb.append(" AND start_date >= TODAY");
        }
        sb.append(" ORDER BY info_id");	        
        return sb.toString();        	
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        DateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        return "DateSearchCriteria[overdueContests=" + overdueContests + ", startDate =" +
        	((startDate != null) ? ft.format(startDate) : null) + ", endDate=" +
        	((endDate != null) ? ft.format(endDate) : null) + "]";
    }
}
