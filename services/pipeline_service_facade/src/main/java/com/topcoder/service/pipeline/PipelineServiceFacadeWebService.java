/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline;

import java.util.Date;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebService;
import com.topcoder.service.pipeline.entities.CompetitionChangeHistory;
import com.topcoder.service.pipeline.searchcriteria.ContestsSearchCriteria;
import com.topcoder.service.pipeline.searchcriteria.DateSearchCriteria;
import com.topcoder.service.project.Competition;

/**
 * <p>
 * An interface for the web service for contest pipeline services. It contains methods to find the contests pipeline
 * info and methods to search the competition change history info for both date and prize type.
 * </p>
 *
 * <p>
 * This interface is a copy of the old PipelineServiceFacade interface. PipelineServiceFacade is no longer a web service point.
 * The security part is covered in this new web-service component. This bean's methods create this TCSubject instance by
 * do the login with LoginBean class and simply call the corresponding PipelineServiceFacade method. This web service must
 * now be used instead of PipelineServiceFacade by web service clients.
 * </p>
 *
 * <p>
 * So the old PipelineServiceFacade will accepts a more parameter: TCSubject from this new-facade. AuthenticationException
 * and GeneralSecurityException from login now will be directly propagated.
 * </p>
 * <p>
 * Thread-safe: the Implementation is required to be thread-safe.
 * </p>
 *
 * @author TCSASSEMBLER
 * @since Cockpit Security Facade V1.0
 * @version 1.0
 */
@WebService(name = "PipelineServiceFacade")
public interface PipelineServiceFacadeWebService {
    /**
     * <p>
     * Search the contests by the given criteria.
     * </p>
     *
     * @param criteria the search criteria, not null
     * @return List of Competition, could be empty if nothing found
     * @throws ContestPipelineServiceException fail to do the search
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    List<Competition> getContests(ContestsSearchCriteria criteria) throws ContestPipelineServiceException;
    /**
     * <p>
     * Search the contests by the given date criteria.
     * </p>
     *
     * @param criteria the date search criteria, not null
     * @return List of Competition, could be empty if nothing found
     * @throws ContestPipelineServiceException fail to do the search
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    List<Competition> getContestsByDate(DateSearchCriteria criteria) throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the date competition change history for the given contest and competition type.
     * </p>
     *
     * @param contestId the contest id
     * @return List of CompetitionChangeHistory
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws ContestPipelineServiceException fail to do the query
     */
    List<CompetitionChangeHistory> getContestDateChangeHistory(long contestId)
            throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the prize competition change history for the given contest and competition type.
     * </p>
     *
     * @param contestId the contest id
     * @return List of CompetitionChangeHistory
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws ContestPipelineServiceException fail to do the query
     */
    List<CompetitionChangeHistory> getContestPrizeChangeHistory(long contestId)
            throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the date competition change history for the given contest ids and their competition types.
     * </p>
     *
     * @param contestIds the contest ids
     * @param competitionTypess competition types, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException fail to do the query
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestDateChangeHistories(long[] contestIds, String[] competitionTypes)
            throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the prize competition change history for the given contest ids and their competition types.
     * </p>
     *
     * @param contestIds the contest ids
     * @param competitionTypess competition types, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException fail to do the query
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestPrizeChangeHistories(long[] contestIds, String[] competitionTypes)
            throws ContestPipelineServiceException;

    /**
     * Gets the list of common pipeline data within between specified start and end date.
     *
     * @param startDate the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ContestManagementException if error during retrieval from database.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<CommonPipelineData> getCommonPipelineData(Date startDate, Date endDate, boolean overdueContests)
            throws ContestPipelineServiceException;

    /**
     * Gets the list of dates that have full capacity starting from tomorrow for the given contest type (for software or
     * studio contests) This method delegates to Pipeline Service layer.
     *
     * @param contestType the contest type
     * @param isStudio true of it is a studio competition, false otherwise
     * @return the list of dates that have full capacity.
     * @throws ContestPipelineServiceException if any error occurs during retrieval of information.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CapacityData> getCapacityFullDates(int contestType, boolean isStudio)
            throws ContestPipelineServiceException;
}
