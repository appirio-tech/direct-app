/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;

import java.util.List;


/**
 * <p>
 * The OR composite search criteria used for searching the contests.
 * </p>
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 */
public class OrContestsSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = -600295196896717870L;

    /** The list of ContestsSearchCriteria. */
    private List<ContestsSearchCriteria> searchCriterias;

    /**
     * Creates a new OrContestsSearchCriteria object.
     */
    public OrContestsSearchCriteria() {
    }

    /**
     * Constructs with List of ContestsSearchCriteria.
     *
     * @param criterias could be null
     */
    public OrContestsSearchCriteria(List<ContestsSearchCriteria> searchCriterias) {
        this.searchCriterias = searchCriterias;
    }

    /**
     * Returns the value of searchCriterias.
     *
     * @return the searchCriterias
     */
    public List<ContestsSearchCriteria> getSearchCriterias() {
        return searchCriterias;
    }

    /**
     * Constructs the where clause for the criteria.
     *
     * @return where clause, could be empty, not null
     */
    public String getWhereClause() {
        if ((searchCriterias == null) || searchCriterias.isEmpty()) {
            return "";
        }

        StringBuffer sb = new StringBuffer("(");

        for (int i = 0; i < searchCriterias.size(); i++) {
            //skip the invalid ones
            if ((searchCriterias.get(i) == null) || (searchCriterias.get(i).getWhereClause().trim().length() == 0)) {
                continue;
            }

            sb.append("(").append(searchCriterias.get(i).getWhereClause());

            if (i != (searchCriterias.size() - 1)) {
                sb.append(") OR ");
            }
        }

        sb.append(")");

        if (sb.length() > 2) {
            return sb.toString();
        }

        return "";
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("OrContestsSearchCriteria[");

        if (searchCriterias != null) {
            for (ContestsSearchCriteria c : this.searchCriterias) {
                sb.append(c.toString()).append(";");
            }
        }

        return sb.append("]").toString();
    }
}
