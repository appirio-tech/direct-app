/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * <p>
 * The search criteria interface used for searching the contests.
 * </p>
 * @author TCSASSEMBLER
 * @version 1.0
 */
@XmlSeeAlso( { AndContestsSearchCriteria.class, ArchitectContestsSearchCriteria.class, ClientApprovalSearchCriteria.class,
	ClientContestsSearchCriteria.class, DateSearchCriteria.class, ContestFeeSearchCriteria.class, ConfidenceSearchCriteria.class,
	DRPointsSearchCriteria.class, DurationSearchCriteria.class, ExcludeClientsSearchCriteria.class,
	FirstPrizeSearchCriteria.class, ManagerContestsSearchCriteria.class, NotContestsSearchCriteria.class,
	OrContestsSearchCriteria.class, PassedSpecReviewSearchCriteria.class, PricingApprovalSearchCriteria.class,
	RepostSearchCriteria.class, ReviewCostSearchCriteria.class,SalespersonSearchCriteria.class,SecondPrizeSearchCriteria.class,
	SpecificationReviewCostSearchCriteria.class, UserContestsSearchCriteria.class, WikiSpecificationSearchCriteria.class})
public abstract class ContestsSearchCriteria implements Serializable {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 7689468788988700834L;
	/**
	 * The default constructor.
	 */
	public ContestsSearchCriteria() {}
	/**
	 * Constructs the where clause for the criteria.
	 *
	 * @return where clause, could be empty, not null
	 */
	public abstract String getWhereClause();
}
