/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;

/**
 * <p>
 * The search criteria instance used for searching the contests.
 * </p>
 */
public class FirstPrizeSearchCriteria extends ContestsSearchCriteria {
	/** serial version UID. */
	private static final long serialVersionUID = 8046632295443701082L;

	/** The lower bound. */
	private double lowerBoundPrize;

	/** The upper bound. */
	private double upperBoundPrize;

	/**
	 * Creates a new FirstPrizeSearchCriteria object.
	 */
	public FirstPrizeSearchCriteria() {
	}

	/**
	 * Constructs the criteria with lower bound and upper bound.
	 * 
	 * @param loweBoundPrize
	 *            the lower bound prize
	 * @param upperBoundPrize
	 *            the upper bound prize
	 */
	public FirstPrizeSearchCriteria(double loweBoundPrize,
			double upperBoundPrize) {
		this.lowerBoundPrize = loweBoundPrize;
		this.upperBoundPrize = upperBoundPrize;
	}

	/**
	 * Constructs the where clause for the criteria.
	 * 
	 * @return where clause, could be empty, not null
	 */
	public String getWhereClause() {
		if (lowerBoundPrize > upperBoundPrize) {
			return "";
		}
		return "";
	}

	/**
	 * Overrides the toString to print the value.
	 * 
	 * @return the string for this criteria
	 */
	public String toString() {
		return "FirstPrizeSearchCriteria[upperBoundPrize=" + upperBoundPrize
				+ ", lowerBoundPrize" + lowerBoundPrize + "]";
	}
}
