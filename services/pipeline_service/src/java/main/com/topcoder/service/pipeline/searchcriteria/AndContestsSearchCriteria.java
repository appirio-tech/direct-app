/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;

import java.util.List;


/**
 * <p>
 * The AND composite search criteria used for searching the contests.
 * </p>
 */
public class AndContestsSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = 5966525366810512762L;

    /** The list of ContestsSearchCriteria. */
    private List<ContestsSearchCriteria> searchCriterias = null;

    /**
     * Default ctor.
     */
    public AndContestsSearchCriteria() {
    }

    /**
     * Constructs with List of ContestsSearchCriteria.
     *
     * @param criterias could be null
     */
    public AndContestsSearchCriteria(List<ContestsSearchCriteria> criterias) {
        this.searchCriterias = criterias;
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
                sb.append(") AND ");
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
        StringBuffer sb = new StringBuffer("AndContestsSearchCriteria[");

        if (searchCriterias != null) {
            for (ContestsSearchCriteria c : this.searchCriterias) {
                sb.append(c.toString()).append(";");
            }
        }

        return sb.append("]").toString();
    }
}
