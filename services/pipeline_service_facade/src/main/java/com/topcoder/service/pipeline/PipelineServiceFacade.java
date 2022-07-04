/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline;

import com.topcoder.security.TCSubject;
import com.topcoder.service.pipeline.entities.CompetitionChangeHistory;
import com.topcoder.service.pipeline.CapacityData;
import com.topcoder.service.pipeline.searchcriteria.ContestsSearchCriteria;
import com.topcoder.service.pipeline.searchcriteria.DateSearchCriteria;
import com.topcoder.service.project.Competition;

import java.util.Date;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * <p>
 * An interface for the web service for contest pipeline services. It contains methods to find the contests pipeline
 * info and methods to search the competition change history info for both date and prize type.
 * </p>
 * <p>
 * Updated for Pipeline Conversion Cockpit Integration Assembly 1 v1.0 - Added method for getContestsByDate - Added
 * method to retrieve change histories for array of contest ids and their types.
 * </p>
 * <p>
 * Version 1.0.1 (Cockpit Pipeline Release Assembly 1 v1.0) Change Notes: - Introduced method to retrieve
 * CommonPipelineData for given date range.
 * </p>
 * <p>
 * Version 1.1 (Cockpit Pipeline Release Assembly 2 - Capacity) changelog: - added service that retrieves a list of
 * dates that have full capacity starting from tomorrow for a given contest type (for software or studio contests)
 * </p>
 * <p>
 * Changes in v1.1.1(Cockpit Security Facade V1.0): - It is not a web-service facade any more. - All the methods accepts
 * a parameter TCSubject which contains all the security info for current user. The implementation EJB should use
 * TCSubject and now get these info from the sessionContext. - Please use the new UserServiceFacadeWebService as the
 * facade now. That interface will delegates all the methods to this interface.
 * </p>
 *
 * @author snow01, pulky
 * @since Pipeline Conversion Service Layer Assembly 2 v1.0
 * @version 1.1.1
 */
public interface PipelineServiceFacade {
    /**
     * <p>
     * Search the contests by the given criteria.
     * </p>
     * *
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param criteria the search criteria, not null
     * @return List of Competition, could be empty if nothing found
     * @throws ContestPipelineServiceException fail to do the search
     */
    List<Competition> getContests(TCSubject tcSubject, ContestsSearchCriteria criteria)
            throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the contests by the given date criteria.
     * </p>
     * *
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param criteria the date search criteria, not null
     * @return List of Competition, could be empty if nothing found
     * @throws ContestPipelineServiceException fail to do the search
     */
    List<Competition> getContestsByDate(TCSubject tcSubject, DateSearchCriteria criteria)
            throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the date competition change history for the given contest and competition type.
     * </p>
     * *
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the contest id
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException fail to do the query
     */
    List<CompetitionChangeHistory> getContestDateChangeHistory(TCSubject tcSubject, long contestId) throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the prize competition change history for the given contest and competition type.
     * </p>
     * *
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the contest id
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException fail to do the query
     */
    List<CompetitionChangeHistory> getContestPrizeChangeHistory(TCSubject tcSubject, long contestId) throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the date competition change history for the given contest ids and their competition types.
     * </p>
     *
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestIds the contest ids
     * @param competitionTypess competition types, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException fail to do the query
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestDateChangeHistories(TCSubject tcSubject, long[] contestIds,
            String[] competitionTypes) throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the prize competition change history for the given contest ids and their competition types.
     * </p>
     *
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestIds the contest ids
     * @param competitionTypess competition types, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException fail to do the query
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestPrizeChangeHistories(TCSubject tcSubject, long[] contestIds,
            String[] competitionTypes) throws ContestPipelineServiceException;

    /**
     * Gets the list of common pipeline data within between specified start and end date.
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param startDate the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ContestManagementException if error during retrieval from database.
     * @since 1.0.1
     */
    public List<CommonPipelineData> getCommonPipelineData(TCSubject tcSubject, Date startDate, Date endDate,
            boolean overdueContests) throws ContestPipelineServiceException;

    /**
     * Gets the list of dates that have full capacity starting from tomorrow for the given contest type (for software or
     * studio contests) This method delegates to Pipeline Service layer. *
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestType the contest type
     * @param isStudio true of it is a studio competition, false otherwise
     * @return the list of dates that have full capacity.
     * @throws ContestPipelineServiceException if any error occurs during retrieval of information.
     * @since 1.1
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CapacityData> getCapacityFullDates(TCSubject tcSubject, int contestType, boolean isStudio)
            throws ContestPipelineServiceException;

}
