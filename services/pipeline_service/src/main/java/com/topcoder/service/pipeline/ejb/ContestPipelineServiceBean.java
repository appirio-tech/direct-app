/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.service.CatalogService;
import com.topcoder.catalog.service.EntityNotFoundException;
import com.topcoder.management.project.SoftwareCapacityData;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;

import com.topcoder.security.TCSubject;
import com.topcoder.service.pipeline.CommonPipelineData;
import com.topcoder.service.pipeline.CapacityData;
import com.topcoder.service.pipeline.ContestPipelineServiceException;
import com.topcoder.service.pipeline.entities.CompetitionChangeHistory;
import com.topcoder.service.pipeline.entities.CompetitionChangeType;
import com.topcoder.service.pipeline.entities.CompetitionPipelineInfo;
import com.topcoder.service.pipeline.entities.SoftwareCompetitionPipelineInfo;
import com.topcoder.service.pipeline.searchcriteria.ContestsSearchCriteria;
import com.topcoder.service.pipeline.searchcriteria.DateSearchCriteria;
import com.topcoder.service.project.Competition;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This is an implementation of <code>ContestPipelineService</code> web service in form of stateless session EJB.
 * </p>
 *
 * <p>
 * Updated for Pipeline Conversion Cockpit Integration Assembly 1 v1.0
 *      - Added method for getContestsByDate
 *      - Added method to retrieve change histories for array of contest ids and their types.
 * </p>
 *
 * <p>
 * Version 1.0.1 (Cockpit Pipeline Release Assembly 1 v1.0) Change Notes:
 *  - Introduced method to retrieve CommonPipelineData for given date range.
 * </p>
 * <p>
 * Version 1.1 (Cockpit Pipeline Release Assembly 2 - Capacity) changelog:
 * - added service that retrieves a list of dates that have full capacity starting from tomorrow for a given contest
 *   type (for software or studio contests)
 * - added configuration retrieval for capacity processing
 * </p>
 * <p>
 * Version 1.1.1(Cockpit Security Facade V1.0) change:
 *  -Add TCSubject as parameter for the method getCommonPipelineData.
 * </p>
 *  <p>
 *  Version 1.1.2 - Direct Pipeline Integration Assembly
 *  - updated {@link #getCommonPipelineData(TCSubject, Date, Date, boolean)} method to return details for copilots.
 *  </p>
 *
 * <p>
 * Thread-safty: This is an CMT bean, so it transaction is managed by the container.
 * </p>
 *
 * @author waits, snow01, pulky, isv
 * @version 1.1.2
 * @since Pipeline Conversion Service v1.0
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ContestPipelineServiceBean implements ContestPipelineServiceRemote, ContestPipelineServiceLocal {
    private static final long MILLIS_PER_HOUR = 1000 * 60 * 60;

    /** Private constant specifying project type info's version id key. */
    private static final String PROJECT_TYPE_INFO_EXTERNAL_REFERENCE_KEY = "External Reference ID";

    /** Base SQL Query to fetch the studio change history. */
    private static final String GET_STUDIO_CHANGE_HISTORY_QUERY = "SELECT ch FROM StudioCompetitionChangeHistory ch WHERE ch.pipelineInfo.contestId=:contestId AND ch.changeType=:changeType";

    /** Base SQL Query to fetch the software change history. */
    private static final String GET_SOFTWARE_CHANGE_HISTORY_QUERY = "SELECT ch FROM SoftwareCompetitionChangeHistory ch where ch.pipelineInfo.componentId=:contestId AND ch.changeType=:changeType";

    /**
     * private constant representing studio capacity default value
     *
     * @since 1.1
     */
    private static final int STUDIO_CAPACITY_DEFAULT_VALUE = 10;

    /**
     * private constant representing software capacity default value
     *
     * @since 1.1
     */
    private static final int SOFTWARE_CAPACITY_DEFAULT_VALUE = 10;

    /**
     * private constant representing software property prefix
     *
     * @since 1.1
     */
    private static final String SOFTWARE_PROP_PREFIX = "Software";

    /**
     * private constant representing studio property prefix
     *
     * @since 1.1
     */
    private static final String STUDIO_PROP_PREFIX = "Studio";


    /**
     * private constant representing days capacity property name
     *
     * @since 1.1
     */
    private static final String DAYS_CAPACITY_PROP_SUFFIX = "DaysCapacity";

    /**
     * private constant representing configuration management namespace for this class
     *
     * @since 1.1
     */
    private static final String CONTEST_PIPELINE_SERVICE_BEAN_NAMESPACE =
        "com.topcoder.service.pipeline.ejb.ContestPipelineServiceBean";

    /**
     * private constant with string representation of the days of a week
     *
     * @since 1.1
     */
    private static final String[] daysPropNames = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday"};

    /**
     * private static attribute representing configured software days capacity
     *
     * The key to the first map is the day of the week and the second map is the contest type, while the value is
     * the configured capacity for that day of the week and contest type id
     *
     * @since 1.1
     */
    private static Map<String, Map<Integer,Integer>> softwareDaysCapacity;

    /**
     * private static attribute representing configured studio days capacity
     *
     * The key to the first map is the day of the week and the second map is the contest type, while the value is
     * the configured capacity for that day of the week and contest type id
     *
     * @since 1.1
     */
    private static Map<String, Map<Integer,Integer>> studioDaysCapacity;

    /**
     * <p>
     * A <code>CatalogService</code> providing access to available <code>Category Services EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Category Services</code> component.
     * </p>
     */
    @EJB(name = "ejb/CatalogService")
    private CatalogService catalogService = null;

    /**
     * <p>
     * A <code>ProjectServices</code> providing access to available <code>Project Services EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Project Services</code> component.
     * </p>
     */
    @EJB(name = "ejb/ProjectServicesBean")
    private ProjectServices projectServices = null;

    /**
     * <p>
     * Represents the sessionContext of the EJB.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * This field represents the persistence unit name to lookup the <code>EntityManager</code> from the
     * <code>SessionContext</code>. It is initialized in the <code>initialize</code> method, and never changed
     * afterwards. It must be non-null, non-empty string.
     * </p>
     */
    @Resource(name = "softwareUnitName")
    private String softwareUnitName;

    /**
     * <p>
     * Represents the loggerName used to retrieve the logger.
     * </p>
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private Log logger;

    /**
     * A default empty constructor.
     */
    public ContestPipelineServiceBean() {
    }

    /**
     * <p>
     * This is method is performed after the construction of the bean, at this point all the bean's resources will be
     * ready.
     * </p>
     */
    @PostConstruct
    protected void init() {
        if (logName != null) {
            if (logName.trim().length() == 0) {
                throw new IllegalStateException("logName parameter not supposed to be empty.");
            }

            logger = LogManager.getLog(logName);
        }

         initCapacity();

        // first record in logger
        logExit("init");
    }

    /**
     * This method initializes capacity configurations
     *
     * Capacity configurations will be loaded using configuration manager.
     *
     * @since 1.1
     */
    private void initCapacity() {
        // Obtaining the instance of Configuration Manager
        ConfigManager cfgMgr = ConfigManager.getInstance();

        softwareDaysCapacity = getDaysCapacityConfiguration(cfgMgr, SOFTWARE_PROP_PREFIX);
        studioDaysCapacity = getDaysCapacityConfiguration(cfgMgr, STUDIO_PROP_PREFIX);
    }

    /**
     * <p>
     * Search the date competition change history for the given contest ids and their competition types.
     * </p>
     *
     * @param contestIds
     *            the contest ids
     * @param competitionTypess
     *            competition types, could be studio or software
     *
     * @return List of CompetitionChangeHistory
     *
     * @throws ContestPipelineServiceException
     *             fail to do the query
     *
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestDateChangeHistories(long[] contestIds,
            String[] competitionTypes) throws ContestPipelineServiceException {
        logger
                .log(
                        Level.DEBUG,
                        "Enter getContestDateChangeHistories(long[] contestIds, CompetitionType[] competitionTypes, CompetitionChangeType changeType) method");
        logger.log(Level.DEBUG, "The parameters[contestIds =" + Arrays.toString(contestIds) + ", competitionTypes = "
                + Arrays.toString(competitionTypes) + "].");

        try {
            return getContestChangeHistories(contestIds, competitionTypes, CompetitionChangeType.DATE);
        } finally {
            this.logExit("getContestDateChangeHistories");
        }
    }

    /**
     * <p>
     * Search the prize competition change history for the given contest ids and their competition types.
     * </p>
     *
     * @param contestIds
     *            the contest ids
     * @param competitionTypess
     *            competition types, could be studio or software
     *
     * @return List of CompetitionChangeHistory
     *
     * @throws ContestPipelineServiceException
     *             fail to do the query
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestPrizeChangeHistories(long[] contestIds,
            String[] competitionTypes) throws ContestPipelineServiceException {
        logger
                .log(
                        Level.DEBUG,
                        "Enter getContestPrizeChangeHistories()");
        /**logger.log(Level.DEBUG, "The parameters[contestIds =" + Arrays.toString(contestIds) + ", competitionTypes = "
                + Arrays.toString(competitionTypes) + "].");*/

        try {
            return getContestChangeHistories(contestIds, competitionTypes, CompetitionChangeType.PRIZE);
        } finally {
            this.logExit("getContestPrizeChangeHistories");
        }
    }

    /**
     * <p>
     * Search the competition change history for the given contest ids and their competition types.
     * </p>
     *
     * @param contestIds
     *            the contest ids
     * @param competitionTypess
     *            competition types, could be studio or software
     *
     * @return List of CompetitionChangeHistory
     *
     * @throws ContestPipelineServiceException
     *             fail to do the query
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<CompetitionChangeHistory> getContestChangeHistories(long[] contestIds,
            String[] competitionTypes, CompetitionChangeType changeType)
            throws ContestPipelineServiceException {
        logger
                .log(
                        Level.DEBUG,
                        "Enter getContestChangeHistories(long[] contestIds, CompetitionType[] competitionTypes, CompetitionChangeType changeType) method");
        logger.log(Level.DEBUG, "The parameters[contestIds =" + Arrays.toString(contestIds) + ", competitionTypes = "
                + Arrays.toString(competitionTypes) + "].");

        ExceptionUtils.checkNull(contestIds, null, null, "contestIds is null");
        ExceptionUtils.checkNull(competitionTypes, null, null, "competitionTypes is null");
        if (contestIds.length != competitionTypes.length) {
            throw wrapContestPipelineServiceException("Both array contestIds and competitionTypes should be of same length");
        }

        try {
            List<CompetitionChangeHistory> results = new LinkedList<CompetitionChangeHistory>();
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < contestIds.length; i++) {
                results.addAll(getHistory(contestIds[i], changeType));
            }

            long endTime = System.currentTimeMillis();
            logger.log(Level.DEBUG, "Executed " + (endTime - startTime) + " m-seconds and get results:");

            return results;
        } finally {
            this.logExit("getContestChangeHistories");
        }
    }

    /**
     * <p>
     * Search the date competition change history for the given contest and competition type.
     * </p>
     *
     * @param contestId
     *            the contest id
     * @param competitionType
     *            competition type, could be studio or software
     *
     * @return List of CompetitionChangeHistory
     *
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestDateChangeHistory(long contestId)
            throws ContestPipelineServiceException {
        logger.log(Level.DEBUG,
                "Enter getContestDateChangeHistory(long contestId, CompetitionType competitionType) method");
        logger.log(Level.DEBUG, "The parameters[contestId =" + contestId + " ].");

        try {
            long startTime = System.currentTimeMillis();
            List<CompetitionChangeHistory> results = getHistory(contestId, CompetitionChangeType.DATE);
            long endTime = System.currentTimeMillis();
            logger.log(Level.DEBUG, "Executed " + (endTime - startTime) + " m-seconds and get results:");

            return results;
        } finally {
            this.logExit("getContestDateChangeHistory");
        }
    }

    /**
     * <p>
     * Search the prize competition change history for the given contest and competition type.
     * </p>
     *
     * @param contestId
     *            the contest id
     * @param competitionType
     *            competition type, could be studio or software
     *
     * @return List of CompetitionChangeHistory
     *
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestPrizeChangeHistory(long contestId)
            throws ContestPipelineServiceException {
        logger.log(Level.DEBUG,
                "Enter getContestPrizeChangeHistory(long contestId, CompetitionType competitionType) method");
        logger.log(Level.DEBUG, "The parameters[contestId =" + contestId + " ].");

        try {
            long startTime = System.currentTimeMillis();
            List<CompetitionChangeHistory> results = getHistory(contestId, CompetitionChangeType.PRIZE);
            long endTime = System.currentTimeMillis();
            logger.log(Level.DEBUG, "Executed " + (endTime - startTime) + " m-seconds and get results:");

            return results;
        } finally {
            this.logExit("getContestPrizeChangeHistory");
        }
    }

    /**
     * Gets the specified type of competition change history for the specified contest id of the specified competition
     * type.
     *
     * @param contestId
     *            specified contest id.
     * @param competitionType
     *            specified competition type.
     * @param changeType
     *            specified change type.
     *
     * @return the specified type of competition change history for the specified contest id of the specified
     *         competition type.
     *
     * @throws ContestPipelineServiceException
     *             if any error occurs during persistence retrieval.
     */
    @SuppressWarnings("unchecked")
    private List<CompetitionChangeHistory> getHistory(long contestId, CompetitionChangeType changeType) throws ContestPipelineServiceException {

        try {
            Query query = null;

            EntityManager em = getSoftwareEntityManager();
            query = em.createQuery(GET_SOFTWARE_CHANGE_HISTORY_QUERY);

            query.setParameter("contestId", contestId);
            query.setParameter("changeType", changeType);

            List<CompetitionChangeHistory> results = (List<CompetitionChangeHistory>) query.getResultList();

            for (CompetitionChangeHistory r : results) {
                r.setContestId(contestId);
            }

            logDebug("The results are:" + results.size());

            return results;
        } catch (PersistenceException pe) {
            throw wrapContestPipelineServiceException(pe, "Fail to retrieve the change history.");
        } catch (RuntimeException re) {
            throw wrapContestPipelineServiceException(re, "Fail to retrieve the change history.");
        }
    }

    /**
     * Gets the list of competition for the specified date search criteria.
     *
     * @param searchCriteria
     *            the date search criteria
     *
     * @return the list of competition for the specified search criteria.
     *
     * @throws ContestPipelineServiceException
     *             if any error occurs during retrieval of competitions.
     *
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Competition> getContestsByDate(DateSearchCriteria criteria) throws ContestPipelineServiceException {
        logger.log(Level.DEBUG, "Enter getContestsByDate(ContestsSearchCriteria criteria) method.");
        logger.log(Level.DEBUG, "with parameter criteria:" + criteria);
        return getContests(criteria);
    }

    /**
     * Gets the list of competition for the specified search criteria.
     *
     * @param searchCriteria
     *            the search criteria
     *
     * @return the list of competition for the specified search criteria.
     *
     * @throws ContestPipelineServiceException
     *             if any error occurs during retrieval of competitions.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Competition> getContests(ContestsSearchCriteria criteria) throws ContestPipelineServiceException {
        logger.log(Level.DEBUG, "Enter getContests(ContestsSearchCriteria criteria) method.");
        logger.log(Level.DEBUG, "with parameter criteria:" + criteria);
        ExceptionUtils.checkNull(criteria, null, null, "The criteria is null.");

        long startTime = System.currentTimeMillis();
        List<Competition> ret = new ArrayList<Competition>();

        try {
            ret.addAll(getSoftwareContests(criteria.getWhereClause()));
            logDebug("The results are:" + ret.size());

            long endTime = System.currentTimeMillis();
            logger.log(Level.DEBUG, "Executed " + (endTime - startTime) + " m-seconds and get results:");

            return ret;
        } catch (PersistenceException pe) {
            throw wrapContestPipelineServiceException(pe, "Fail to retrieve the contests.");
        } catch (RuntimeException re) {
            throw wrapContestPipelineServiceException(re, "Fail to retrieve the contests.");
        } finally {
            logExit("getContests");
        }
    }


    /**
     * Gets the list of dates that have full capacity starting from tomorrow for the given contest type (for software
     * or studio contests). If there are no capacity constraints, return null.
     *
     * This method will get capacity information from the corresponding project / studio services layer and will
     * evaluate capacity according to the configured values.
     *
     * @param contestType the contest type
     * @param isStudio true of it is a studio competition, false otherwise
     * @return the list of dates that have full capacity.
     * @throws ContestPipelineServiceException if any error occurs during retrieval of information.
     *
     * @since 1.1
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CapacityData> getCapacityFullDates(int contestType, boolean isStudio)
        throws ContestPipelineServiceException {

        // this will make access easier and faster
        int[] capacity = new int[7];
        for (int i = 0; i < 7; i++) {
            capacity[i] = getCapacityForDay(daysPropNames[i], contestType, isStudio);
        }

        try {
            List<CapacityData> fullCapacityList = new ArrayList<CapacityData>();

            // get capacity data from underlying services
            List<CapacityData> capacityDataList;
            capacityDataList = convertSoftwareCapacityDataToCapacityData(projectServices.getCapacity(contestType));

            // use configuration to check full capacity
            for (CapacityData capacityData : capacityDataList) {
                Calendar cal = new GregorianCalendar();
                cal.setTime(getDate(capacityData.getDate()));

                if (capacity[cal.get(Calendar.DAY_OF_WEEK) - 1] <= capacityData.getNumScheduledContests()) {
                    fullCapacityList.add(capacityData);

                }
            }

            return fullCapacityList.size() > 0 ? fullCapacityList : null;
        } catch (Exception e) {
            throw wrapContestPipelineServiceException(e, "An error occured while calculating full capacity dates");
        }
    }

    /**
     * Gets the list of software competition for the specified where clause.
     *
     * @param whereClause
     *            the search criteria as where clause
     *
     * @return the list of software competition for the specified where clause.
     *
     * @throws ContestPipelineServiceException
     *             if any error occurs during retrieval of competitions.
     */
    @SuppressWarnings( { "unchecked" })
    private List<Competition> getSoftwareContests(String whereClause) throws ContestPipelineServiceException {
        List<Competition> competitions = new ArrayList<Competition>();

        String queryStr = constructQuery(whereClause);

        logDebug("Query: " + queryStr);

        EntityManager em = getSoftwareEntityManager();

        //for native query
        if (queryStr.indexOf("SELECT max(nvl(actual_end_time, scheduled_end_time)") != -1) {
            Query query = em.createNativeQuery(queryStr);
            List<Object[]> projectInfoIds = query.getResultList();

            for (Object[] ids : projectInfoIds) {
                Integer projectId = (Integer) ids[0];
                Integer infoId = (Integer) ids[1];
                SoftwareCompetitionPipelineInfo info = getSoftwareCompetitionPipelineInfoById(em, infoId);
                SoftwareCompetition c = getSoftwareContestByProjectId(projectId);
                if (c == null) {
                    throw wrapContestPipelineServiceException("No software competition found for pipeline info id: " +
                        infoId);
                }
                setSoftwarePipelineInfo(c, info);
                competitions.add(c);
            }
            return competitions;
        }


        //for jql query
        Query query = em.createQuery(queryStr);

        List<SoftwareCompetitionPipelineInfo> pipelineInfos = query.getResultList();

        for (SoftwareCompetitionPipelineInfo pipelineInfo : pipelineInfos) {
            SoftwareCompetition c = getSoftwareContestByProjectId(getProjectIdByInfo(em, pipelineInfo));
            if (c == null) {
                throw wrapContestPipelineServiceException("No software competition found for pipeline info id: "
                        + pipelineInfo.getPipelineInfoId());
            }

            setSoftwarePipelineInfo(c, pipelineInfo);
            competitions.add(c);
        }

        return competitions;
    }

    /**
     * Gets the project id by info id.
     *
     * @param em Entity manager.
     * @param pipelineInfo the specified sofware pipeline info
     * @return project id
     */
    private Integer getProjectIdByInfo(EntityManager em, SoftwareCompetitionPipelineInfo pipelineInfo) {
        String queryProjectIdByComponentId =
            "SELECT DISTINCT project_id FROM project_info p, software_competition_pipeline_info info " +
            "WHERE p.project_info_type_id = 2 and info.component_id = p.value AND info.component_id = " + pipelineInfo.getComponentId() +
            " AND info.id = " + pipelineInfo.getPipelineInfoId();
        Query query = em.createNativeQuery(queryProjectIdByComponentId);
        return (Integer) query.getSingleResult();
    }

    /**
     * Gets the software competition pipeline info by specified info id.
     *
     * @param em the Entity Manager
     * @param infoId the info id.
     * @return the instance of SoftwareCompetitionPipelineInfo
     * @throws ContestPipelineServiceException if some exception occurs
     */
    private SoftwareCompetitionPipelineInfo getSoftwareCompetitionPipelineInfoById(EntityManager em, Integer infoId)
        throws ContestPipelineServiceException {
        return (SoftwareCompetitionPipelineInfo) em.createQuery(
            "SELECT info FROM SoftwareCompetitionPipelineInfo info WHERE info.id = " + infoId).getSingleResult();
    }

    /**
     * <p>
     * BURG-1716: We need to add a method to get software contest by project id, the method wil get all OR project
     * related data, then from project property to get comp version id then to call getAssetByVersionId to get assetDTO,
     * please check create software contest to see what data need to be returned.
     * </p>
     *
     * @param projectId
     *            the OR Project Id
     *
     * @return SoftwareCompetition
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since BURG-1716
     */
    private SoftwareCompetition getSoftwareContestByProjectId(long projectId) throws ContestPipelineServiceException {
        SoftwareCompetition contest = new SoftwareCompetition();

        try {
            FullProjectData fullProjectData = this.projectServices.getFullProjectData(projectId);
            Long compVersionId = Long.parseLong(fullProjectData.getProjectHeader().getProperty(
                    PROJECT_TYPE_INFO_EXTERNAL_REFERENCE_KEY));
            contest.setAssetDTO(this.catalogService.getAssetByVersionId(compVersionId));
            contest.setProjectHeader(fullProjectData.getProjectHeader());
            contest.setProjectData(fullProjectData);
            contest.setProjectPhases(fullProjectData);
            contest.getProjectPhases().setId(fullProjectData.getProjectHeader().getId());
            contest.setId(projectId);
            contest.setProjectResources(fullProjectData.getResources());

            com.topcoder.project.phases.Phase[] allPhases = fullProjectData.getAllPhases();

            // this is to avoid cycle
            for (int i = 0; i < allPhases.length; i++) {
                allPhases[i].setProject(null);
                allPhases[i].clearDependencies();
            }

            // set project start date in production date
            contest.getAssetDTO().setProductionDate(getXMLGregorianCalendar(contest.getProjectPhases().getStartDate()));

            // set null to avoid cycle
            contest.getAssetDTO().setDependencies(null);

            if (contest.getAssetDTO().getForum() != null) {
                contest.getAssetDTO().getForum().setCompVersion(null);
            }

            if (contest.getAssetDTO().getLink() != null) {
                contest.getAssetDTO().getLink().setCompVersion(null);
            }

            if ((contest.getAssetDTO().getDocumentation() != null)
                    && (contest.getAssetDTO().getDocumentation().size() > 0)) {
                for (CompDocumentation doc : contest.getAssetDTO().getDocumentation()) {
                    doc.setCompVersion(null);
                }
            }

            return contest;
        } catch (ProjectServicesException pse) {
            throw wrapContestPipelineServiceException(pse, "Fail to get project data from project services.");
        } catch (NumberFormatException nfe) {
            throw wrapContestPipelineServiceException(nfe,
                    "the properites 'Version ID' is not of Long value in project.");
        } catch (EntityNotFoundException e) {
            throw wrapContestPipelineServiceException(e, "the version id does not exist.");
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            throw wrapContestPipelineServiceException(e, "Fail to get project asset.");
        }
    }

    /**
     * <p>
     * This methods sets the SoftwareCompetitionPipelineInfo to the SoftwareCompetition
     * </p>
     *
     * @param competition
     *            the SoftwareCompetition
     * @param pipelineInfo
     *            the SoftwareCompetitionPipelineInfo
     */
    private void setSoftwarePipelineInfo(SoftwareCompetition competition, SoftwareCompetitionPipelineInfo pipelineInfo) {
        setPipelineInfo(competition, pipelineInfo);
    }

    /**
     * <p>
     * This methods sets the CompetitionPipelineInfo to the Competition
     * </p>
     *
     * @param competition
     *            the Competition
     * @param pipelineInfo
     *            the CompetitionPipelineInfo
     */
    private void setPipelineInfo(Competition competition, CompetitionPipelineInfo pipelineInfo) {
        competition.setReviewPayment(pipelineInfo.getReviewPayment());
        competition.setSpecificationReviewPayment(pipelineInfo.getSpecificationReviewPayment());
        competition.setContestFee(pipelineInfo.getContestFee());
        competition.setClientName(pipelineInfo.getClientName());
        competition.setConfidence(pipelineInfo.getConfidence());
        competition.setClientApproval(pipelineInfo.getClientApproval());
        competition.setPricingApproval(pipelineInfo.getPricingApproval());
        competition.setHasWikiSpecification(pipelineInfo.getHasWikiSpecification());
        competition.setPassedSpecReview(pipelineInfo.getPassedSpecReview());
        competition.setHasDependentCompetitions(pipelineInfo.getHasDependentCompetitions());
        competition.setWasReposted(pipelineInfo.getWasReposted());
        competition.setNotes(pipelineInfo.getNotes());
    }

    /**
     * <p>
     * Converts specified <code>Date</code> instance into <code>XMLGregorianCalendar</code> instance.
     * </p>
     *
     * @param date
     *            a <code>Date</code> representing the date to be converted.
     *
     * @return a <code>XMLGregorianCalendar</code> providing the converted value of specified date or <code>null</code>
     *         if specified <code>date</code> is <code>null</code> or if it can't be converted to calendar.
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }

    /**
     * <p>
     * Converts specified <code>XMLGregorianCalendar</code> instance into <code>Date</code> instance.
     * </p>
     *
     * @param calendar
     *            an <code>XMLGregorianCalendar</code> representing the date to be converted.
     *
     * @return a <code>Date</code> providing the converted value of specified calendar or <code>null</code> if specified
     *         <code>calendar</code> is <code>null</code>.
     */
    private Date getDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }

        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * Constructs the SQL clause for searching contests.
     *
     * @param whereClause
     *            the ContestsSearchCriteria instance
     *
     * @return type CompetitionType instance
     */
    private String constructQuery(String whereClause) {
        StringBuffer sb = new StringBuffer("SELECT ");

        sb.append("pinfo FROM SoftwareCompetitionPipelineInfo pinfo ");

        if ((whereClause == null) || (whereClause.trim().length() == 0)) {
            return sb.toString();
        }

        sb.append(getSoftwareFromClause(whereClause));
        
        if (sb.indexOf("DateSearchCriteria") == -1) {
            sb.append(" WHERE ");

            return sb.append(whereClause).toString();
        } else {
            return whereClause;
        }
    }

    /**
     * Format from clause.
     *
     * @param whereClause
     *            the where clause from the criteria.
     *
     * @return the from clause
     */
    private String getSoftwareFromClause(String whereClause) {
        if (whereClause.indexOf("end_date >= to_date") != -1) {
            return "DateSearchCriteria";
        }

        return "";
    }

    /**
     * <p>
     * Creates a <code>ContestPipelineServiceException</code> with inner exception and message. It will log the
     * exception, and set the sessionContext to rollback only.
     * </p>
     *
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     *
     * @return the created exception
     */
    private ContestPipelineServiceException wrapContestPipelineServiceException(Exception e, String message) {
        ContestPipelineServiceException ce = new ContestPipelineServiceException(message, e);
        logException(ce, message);

        return ce;
    }

    /**
     * <p>
     * Creates a <code>ContestPipelineServiceException</code> with inner exception and message. It will log the
     * exception, and set the sessionContext to rollback only.
     * </p>
     *
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     *
     * @return the created exception
     */
    private ContestPipelineServiceException wrapContestPipelineServiceException(String message) {
        ContestPipelineServiceException ce = new ContestPipelineServiceException(message);
        logException(ce, message);

        return ce;
    }

    /**
     * <p>
     * Returns the <code>EntityManager</code> looked up from the session context.
     * </p>
     *
     * @return the EntityManager looked up from the session context
     *
     * @throws ContestManagementException
     *             if fail to get the EntityManager from the sessionContext.
     */
    private EntityManager getSoftwareEntityManager() throws ContestPipelineServiceException {
        try {
            Object obj = sessionContext.lookup(softwareUnitName);

            if (obj == null) {
                throw wrapContestPipelineServiceException("The object for jndi name '" + softwareUnitName
                        + "' doesn't exist.");
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
            throw wrapContestPipelineServiceException(e, "The jndi name for '" + softwareUnitName
                    + "' should be EntityManager instance.");
        }
    }

    /**
     * This method is used to log the debug message.
     *
     * @param msg
     *            the message string.
     */
    private void logDebug(String msg) {
        if (logger != null) {
            logger.log(Level.DEBUG, msg);
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     *
     * @param method
     *            name of the leaved method
     */
    private void logExit(String method) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Leave method {0}.", method);
        }
    }

    /**
     * <p>
     * Log the exception.
     * </p>
     *
     * @param e
     *            the exception to log
     * @param message
     *            the string message
     */
    private void logException(Throwable e, String message) {
        if (logger != null) {
            // This will log the message and StackTrace of the exception.
            logger.log(Level.ERROR, e, message);

            while (e != null) {
                logger.log(Level.ERROR, "INNER: " + e.getMessage());
                e = e.getCause();
            }
        }
    }

    /**
     * Gets the list of common pipeline data within between specified start and end date.
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param startDate
     *            the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate
     *            the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests
     *            whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ContestManagementException
     *             if error during retrieval from database.
     * @since 1.0.1
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CommonPipelineData> getCommonPipelineData(TCSubject tcSubject, Date startDate, Date endDate, boolean overdueContests)
            throws ContestPipelineServiceException {
        logger.log(Level.DEBUG, "Enter getCommonPipelineData(TCSubject tcSubject,Date startDate, Date endDate, boolean overdueContests) method.");
        logger.log(Level.DEBUG, "with parameter tcSubject:" + tcSubject.getUserId() + ",startDate:" + startDate + ", endDate: " + endDate + ", overdueContests: " + overdueContests);
        ExceptionUtils.checkNull(startDate, null, null, "The startDate is null.");
        ExceptionUtils.checkNull(endDate, null, null, "The endDate is null.");
        ExceptionUtils.checkNull(tcSubject, null, null, "The tcSubject is null.");

        long startTime = System.currentTimeMillis();
        List<CommonPipelineData> ret = new ArrayList<CommonPipelineData>();

        try {

            List<com.topcoder.management.project.SimplePipelineData> swPipelineDatas =
                this.projectServices.getSimplePipelineData(tcSubject, startDate, endDate, overdueContests);
            if (swPipelineDatas != null) {

                for (com.topcoder.management.project.SimplePipelineData p : swPipelineDatas) {
                    CommonPipelineData cp = new CommonPipelineData();
                    cp.setCpname(p.getCpname());
                    cp.setCname(p.getCname());
                    cp.setContestId(p.getContestId());
                    cp.setPname(p.getPname());
                    cp.setSname(p.getSname());
                    cp.setContestType(p.getContestType());
                    cp.setProjectId(p.getProjectId());
                    cp.setCversion(p.getCversion());
                    cp.setContestCategory(p.getContestCategory());
                    cp.setCreateTime(getXMLGregorianCalendar(p.getCreateTime()));
                    cp.setModifyTime(getXMLGregorianCalendar(p.getModifyTime()));
                    cp.setClientName(p.getClientName());
                    cp.setTotalPrize(p.getTotalPrize());
                    cp.setContestFee(p.getContestFee());
                    cp.setManager(p.getManager());
					cp.setWasReposted(p.getWasReposted());
                    cp.setCperm(p.getCperm());
                    cp.setPperm(p.getPperm());
                    cp.setDurationInHrs((p.getDurationEndTime().getTime() - p.getDurationStartTime().getTime())
                            / MILLIS_PER_HOUR);
                    cp.setStartDate(getXMLGregorianCalendar(p.getStartDate()));
                    cp.setEndDate(getXMLGregorianCalendar(p.getEndDate()));
                    cp.setDurationStartTime(getXMLGregorianCalendar(p.getDurationStartTime()));
                    cp.setDurationEndTime(getXMLGregorianCalendar(p.getDurationEndTime()));
                    cp.setCopilots(p.getCopilots());
                    cp.setIsStudio(false);
                    cp.setContestTypeId(p.getContestTypeId());
					cp.setClientId(p.getClientId());

                    ret.add(cp);
                }
            }

            logDebug("The results are:" + ret.size());

            Collections.sort(ret);

            long endTime = System.currentTimeMillis();
            logger.log(Level.DEBUG, "Executed " + (endTime - startTime) + " m-seconds and get results:");

            return ret;
        } catch (Exception e) {
            throw wrapContestPipelineServiceException(e, "Fail to retrieve the pipeline data.");
        }
        finally {
            logExit("getCommonPipelineData");
        }
    }

   /**
     * Private helper method that gets the configured capacity for a particular day, taking into consideration
     * the studio/software contest type.
     *
     * If there configuration can't be found, the default is returned.
     *
     * @param day the string representation of the day being inquired
     * @param contestType the contest type
     * @param isStudio true for studio competitions, false otherwise
     *
     * @return the configured capacity (or default) as corresponds
     *
     * @since 1.1
     */
    private int getCapacityForDay(String day, int contestType, boolean isStudio) {
        ExceptionUtils.checkNull(day, null, null, "day is null");

        Map<Integer,Integer> dayCapacity;
        int capacity;

        if (isStudio) {
            dayCapacity = studioDaysCapacity.get(day);
            capacity = STUDIO_CAPACITY_DEFAULT_VALUE;
        } else {
            dayCapacity = softwareDaysCapacity.get(day);
            capacity = SOFTWARE_CAPACITY_DEFAULT_VALUE;
        }

        if (dayCapacity != null) {
            if (dayCapacity.containsKey(contestType)) {
                capacity = dayCapacity.get(contestType);
            }
        }

        return capacity;
    }

    /**
     * Private helper method that converts a list of software capacity data objects into a list of capacity data
     * objects.
     *
     * @param capacity the list of software capacity data objects
     * @return the converted list of capacity data objects or an empty list if the argument is null
     *
     * @since 1.1
     */
    private List<CapacityData> convertSoftwareCapacityDataToCapacityData(List<SoftwareCapacityData> capacity) {
        List<CapacityData> capacityData = new ArrayList<CapacityData>();

        if (capacity != null) {
            for (SoftwareCapacityData softwareCapacitydata : capacity) {
                capacityData.add(new CapacityData(getXMLGregorianCalendar(softwareCapacitydata.getDate()),
                        softwareCapacitydata.getNumScheduledContests(), new ArrayList(softwareCapacitydata.getContests())));
            }
        }

        return capacityData;
    }

    /**
     * Private helper method that retrieves the capacity configuration for a given competition type
     *
     * @param cfgMgr the configuration manager instance used to retrieve the configurations
     * @param type the competition type property prefix
     * @return a <code>Map<String, Map<Integer, Integer>></code> where the key to the first map is the day of the
     *     week and the second map is the contest type, while the value is the configured capacity for that day of
     *     the week and contest type id.
     *
     * @since 1.1
     */
    private Map<String, Map<Integer, Integer>> getDaysCapacityConfiguration(ConfigManager cfgMgr, String type) {

        Map<String, Map<Integer, Integer>> daysCapacity =
            new HashMap<String, Map<Integer,Integer>>(daysPropNames.length);

        try {
            // iterate days
            for (String dayProp : daysPropNames) {
                Map<Integer,Integer> capacityMap = new HashMap<Integer,Integer>();

                Property propDaysCapacity = cfgMgr.getPropertyObject(CONTEST_PIPELINE_SERVICE_BEAN_NAMESPACE,
                    type + DAYS_CAPACITY_PROP_SUFFIX + "." + dayProp);
                Enumeration daysProps = propDaysCapacity.propertyNames();

                // iterate contest types
                while (daysProps.hasMoreElements()) {
                    String typePropName = (String) daysProps.nextElement();
                    Integer capacity = Integer.parseInt(propDaysCapacity.getValue(typePropName));
                    Integer typeId = Integer.parseInt(typePropName);

                    capacityMap.put(typeId, capacity);
                }

                daysCapacity.put(dayProp, capacityMap);
            }
        } catch (Exception e) {
            logException(e, "An error occurred while loading capacity configuration, using defaults");
        }

        return daysCapacity;
    }
}
