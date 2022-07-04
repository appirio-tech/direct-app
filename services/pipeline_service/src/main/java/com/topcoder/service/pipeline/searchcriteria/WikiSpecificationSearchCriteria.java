/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The search criteria instance for searching with 'has_wiki_specification' field.
 * </p>
 *
 * @since Pipeline Conversion Service Layer Assembly v1.0
 */
public class WikiSpecificationSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = 4587284447045836180L;

    /** The value for 'has_wiki_specification' search criteria. */
    private boolean wikiSpecificationExists;

    /**
     * Creates a new WikiSpecificationSearchCriteria object.
     */
    public WikiSpecificationSearchCriteria() {
    }

    /**
     * <p>
     * Constructor with the value for has_wiki_specification.
     * </p>
     *
     * @param wikiSpecificationExists the value to search
     */
    public WikiSpecificationSearchCriteria(boolean wikiSpecificationExists) {
        this.wikiSpecificationExists = wikiSpecificationExists;
    }

    /**
     * <p>
     * Returns the where clause for searching.
     * </p>
     *
     * @return the where clause
     */
    public String getWhereClause() {
        return new StringBuffer("pinfo.hasWikiSpecification=").append(wikiSpecificationExists).toString();
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "WikiSpecificationSearchCriteria[wikiSpecificationExists=" + wikiSpecificationExists + "]";
    }
}
