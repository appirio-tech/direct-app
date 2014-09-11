/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.topcoder.direct.services.copilot.CopilotProjectService;
import com.topcoder.direct.services.copilot.CopilotServiceException;
import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.dto.ContestTypeStat;
import com.topcoder.direct.services.copilot.dto.CopilotProjectDTO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.PlannedContest;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * This class is an implementation of CopilotProjectService that uses pluggable managers and DAOs to perform its
 * functionality. It extends BaseCopilotService&lt;CopilotProject&gt; that provides common functionality for
 * CopilotProfileService and CopilotProjectService implementations provided in this component. This class uses
 * Logging Wrapper logger to log errors and debug information.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 *
 * ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {&quot;applicationContext.xml&quot;});
 * CopilotProjectService service = (CopilotProjectService) context.getBean(&quot;copilotProjectService&quot;);
 * // get copilot ProjectDTO
 * service.getCopilotProjectDTO(1l);
 * // get copilot projects
 * service.getCopilotProjects(1l);
 *
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class has mutable attributes, thus it's not thread safe. But it's assumed that it will
 * be initialized via Spring IoC before calling any business method, this way it's always used in thread safe manner.
 * It uses thread safe DAOs and Log instances. Used instances of ProjectManager and ProjectLinkManager are not thread
 * safe, thus additional synchronization is used when accessing their methods.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectServiceImpl extends BaseCopilotService<CopilotProject> implements CopilotProjectService {
    /**
     * The copilot profile DAO to be used by this class. Cannot be null after initialization (validation is performed
     * in checkInit() method). Initialized by Spring setter injection. Has protected getter and public setter. Is
     * used in checkInit() and createCopilotProjectDTO().
     */
    private CopilotProfileDAO copilotProfileDAO;
    /**
     * The phase manager to be used by this class. Cannot be null after initialization (validation is performed in
     * checkInit() method). Initialized by Spring setter injection. Has protected getter and public setter. Is used
     * in checkInit() and createCopilotProjectDTO().
     */
    private PhaseManager phaseManager;

    /**
     * Creates an instance of CopilotProjectServiceImpl.
     */
    public CopilotProjectServiceImpl() {
        // Do nothing
    }

    /**
     * Checks whether this class was initialized by Spring properly.
     *
     * @throws CopilotServiceInitializationException if the class was not initialized properly (e.g. when required
     *             properly is not specified or property value has invalid format)
     */
    @PostConstruct
    protected void checkInit() {
        super.checkInit();
        if (!(getGenericDAO() instanceof CopilotProjectDAO)) {
            throw new CopilotServiceInitializationException("genericDAO should be an instance of CopilotProjectDAO.");
        }
        Helper.checkNullForInjectedValue(copilotProfileDAO, "copilotProfileDAO");
        Helper.checkNullForInjectedValue(phaseManager, "phaseManager");
    }

    /**
     * Retrieves the copilot projects for the copilot with the specified profile ID. Returns an empty list if the
     * copilot has no associated projects.
     *
     * @param copilotProfileId the ID of the copilot profile
     * @return the copilot projects for the specified copilot (not null, doesn't contain null)
     * @throws IllegalArgumentException if copilotProfileId <= 0
     * @throws CopilotServiceException if some other error occurred
     */
    public List<CopilotProjectDTO> getCopilotProjects(long copilotProfileId) throws CopilotServiceException {
        final String method = "CopilotProjectServiceImpl#getCopilotProjects";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        Helper.logInputArguments(getLog(), new String[] {"copilotProfileId"}, new Object[] {copilotProfileId});
        Helper.checkPositive(copilotProfileId, "copilotProfileId", method, getLog());
        try {
            CopilotProjectDAO copilotProjectDAO = (CopilotProjectDAO) getGenericDAO();
            // Get all copilot projects for this copilot
            List<CopilotProject> copilotProjects = copilotProjectDAO.getCopilotProjects(copilotProfileId);
            // wrap EntityNotFoundException to CopilotServiceEntityNotFoundException
            // Create a result list
            List<CopilotProjectDTO> result = new ArrayList<CopilotProjectDTO>();
            for (CopilotProject copilotProject : copilotProjects) {
                // Create and populate copilot project DTO
                CopilotProjectDTO copilotProjectDTO = createCopilotProjectDTO(copilotProject, method);
                // Add copilot project DTO to the result list
                result.add(copilotProjectDTO);
            }
            Helper.logReturnValue(getLog(), result);
            Helper.logMethodExit(method, getLog(), date);
            return result;
        } catch (CopilotDAOException e) {
            throw Helper.logError(new CopilotServiceException(
                "Error occurred when executing copilotProjectDAO#getCopilotProjects.", e), method, getLog());
        }
    }

    /**
     * Retrieves the details of the copilot project with the specified ID.
     *
     * @param copilotProjectId the ID of the copilot project
     * @return the retrieved copilot project details or null if copilot project with the given ID doesn't exist
     * @throws IllegalArgumentException if copilotProjectId <= 0
     * @throws CopilotServiceException if some other error occurred
     */
    public CopilotProjectDTO getCopilotProjectDTO(long copilotProjectId) throws CopilotServiceException {
        final String method = "CopilotProjectServiceImpl#getCopilotProjectDTO";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        Helper.logInputArguments(getLog(), new String[] {"copilotProjectId"}, new Object[] {copilotProjectId});
        Helper.checkPositive(copilotProjectId, "copilotProjectId", method, getLog());
        try {
            CopilotProjectDAO copilotProjectDAO = (CopilotProjectDAO) getGenericDAO();
            // Retrieve copilot project by its ID
            CopilotProject copilotProject = copilotProjectDAO.retrieve(copilotProjectId);
            if (copilotProject == null) {
                Helper.logReturnValue(getLog(), null);
                Helper.logMethodExit(method, getLog(), date);
                return null;
            }
            // Create and populate copilot project DTO
            CopilotProjectDTO result = createCopilotProjectDTO(copilotProject, method);
            Helper.logReturnValue(getLog(), result);
            Helper.logMethodExit(method, getLog(), date);
            return result;
        } catch (CopilotDAOException e) {
            throw Helper.logError(new CopilotServiceException(
                "Error occurred when executing copilotProjectDAO#getCopilotProjects.", e), method, getLog());
        }
    }

    /**
     * Populate CopilotProjectDTO instance.
     *
     * @param result the CopilotProjectDTO instance
     * @param copilotProjectPlan the CopilotProjectPlan instance
     * @param contestsStat the ContestsStat instance
     */
    private void populateCopilotProjectDTO(CopilotProjectDTO result, CopilotProjectPlan copilotProjectPlan,
        ContestsStat contestsStat) {
        // Get total number of contests from the stat
        int totalContests = contestsStat.getTotalContests();
        // Set this total real contests number to the result
        result.setTotalRealContests(totalContests);
        // Similarly copy total reposted contests number from contestsStat to result
        result.setTotalRepostedContests(contestsStat.getTotalRepostedContests());
        // Similarly copy total failed contests number from contestsStat to result
        result.setTotalFailedContests(contestsStat.getTotalFailedContests());
        // Similarly copy total real bug races number from contestsStat to result
        result.setTotalRealBugRaces(contestsStat.getTotalBugRaces());

        // Get planned duration of the copilot project
        int plannedDuration = copilotProjectPlan.getPlannedDuration();
        // Set planned duration to the result
        result.setPlannedDuration(plannedDuration);
        // Get planned number of bug races
        int plannedBugRaces = copilotProjectPlan.getPlannedBugRaces();
        // Set planned number of bug races to the result
        result.setPlannedBugRaces(plannedBugRaces);
    }

    /**
     * Creates and fills copilot project DTO instance using the given copilot project as an input.
     *
     * @param copilotProject the copilot project
     * @param method the method name
     * @return the filled copilot project DTO (not null)
     * @throws CopilotServiceException if any error occurred
     */
    private CopilotProjectDTO createCopilotProjectDTO(CopilotProject copilotProject, String method)
        throws CopilotServiceException {
        try {
            // Create result instance
            CopilotProjectDTO result = new CopilotProjectDTO();
            // Set copilot project to the result
            result.setCopilotProject(copilotProject);
            // Get copilot project plan for this copilot project
            CopilotProjectPlan copilotProjectPlan =
                getCopilotProjectPlanDAO().getCopilotProjectPlan(copilotProject.getId());
            if (copilotProjectPlan == null || copilotProjectPlan.getPlannedContests() == null) {
                return result;
            }
            // Get all planned contests
            Set<PlannedContest> plannedContests = copilotProjectPlan.getPlannedContests();
            // Get the total number of planned contests
            int totalPlannedContests = plannedContests.size();
            // Set total number of planned contests to the result
            result.setTotalPlannedContests(totalPlannedContests);
            // Create map for stats by each contest type
            Map<String, ContestTypeStat> contestTypeStats = new HashMap<String, ContestTypeStat>();
            for (PlannedContest plannedContest : plannedContests) {
                // Count this planned contest in stats
                countPlannedContestInStats(contestTypeStats, plannedContest);
            }
            // Get copilot profile ID for this copilot project
            long copilotProfileId = copilotProject.getCopilotProfileId();
            // Retrieve copilot profile by its ID
            CopilotProfile copilotProfile = copilotProfileDAO.retrieve(copilotProfileId);
            if (copilotProfile == null) {
                return result;
            }
            // Get copilot user ID
            long copilotUserId = copilotProfile.getUserId();

            // Get TC direct project ID for this copilot project
            long tcDirectProjectId = copilotProject.getTcDirectProjectId();
            // Get all contests for this copilot project
            long[] contestIds = getUtilityDAO().getCopilotProjectContests(copilotUserId, tcDirectProjectId);
            // Create a list for IDs of all contests for this copilot project
            List<Long> contestIdList = new ArrayList<Long>();
            for (long contestId : contestIds) {
                contestIdList.add(contestId);
            }

            // Calculate contests stat
            ContestsStat contestsStat = calculateContestsStat(contestIdList, contestTypeStats);
            populateCopilotProjectDTO(result, copilotProjectPlan, contestsStat);
            handleRealDuration(copilotProject, contestIds, result);
            // Set stats by contest types to the result
            result.setContestTypeStats(contestTypeStats);
            return result;
        } catch (CopilotDAOException e) {
            throw Helper.logError(new CopilotServiceException("Error occurred when calling underlying DAO methods.",
                e), method, getLog());
        } catch (PhaseManagementException e) {
            throw Helper.logError(new CopilotServiceException(
                "Error occurred when calling phaseManager.getPhases method.", e), method, getLog());
        } catch (CopilotServiceException e) {
            // log the exception thrown from protected method
            throw Helper.logError(e, method, getLog());
        }
    }

    /**
     * Handle the real duration of copilotProjectDTO.
     *
     * @param copilotProject the CopilotProject instance
     * @param contestIds an array of contest ids
     * @param copilotProjectDTO the CopilotProjectDTO instance
     * @throws CopilotDAOException if error occurs while executing getUtilityDAO().getContestLatestBugResolutionDate
     * @throws PhaseManagementException if error occurs while executing phaseManager.getPhases
     * @throws CopilotServiceException if phaseEndDate or phaseStartDate is null
     */
    private void handleRealDuration(CopilotProject copilotProject, long[] contestIds,
        CopilotProjectDTO copilotProjectDTO) throws CopilotDAOException, PhaseManagementException,
        CopilotServiceException {
        Date minDate = null;
        Date maxDate = null;
        // Get copilot project status
        CopilotProjectStatus copilotProjectStatus = copilotProject.getStatus();
        boolean active =
            (copilotProjectStatus == null ? false
                : (copilotProjectStatus.getId() == getActiveCopilotProjectStatusId()));
        if (!active) {
            maxDate = copilotProject.getCompletionDate();
        }

        // Get phase projects for all contests
        Project[] phaseProjects = phaseManager.getPhases(contestIds);
        for (Project phaseProject : phaseProjects) {
            // Get all phases for this project
            Phase[] phases = phaseProject.getAllPhases();
            if (phases.length == 0) {
                continue;
            }
            Phase startPhase = phases[0];
            // Get the start date of the first phase
            Date phaseStartDate = startPhase.getActualStartDate();
            if (phaseStartDate == null) {
                throw new CopilotServiceException("phaseStartDate should not be null.");
            }
            if (minDate == null || phaseStartDate.before(minDate)) {
                minDate = phaseStartDate;
            }
            if (active) {
                Phase endPhase = phases[phases.length - 1];
                // Get the end date of the last phase
                Date phaseEndDate = endPhase.getActualEndDate();
                if (phaseEndDate == null) {
                    throw new CopilotServiceException("phaseEndDate should not be null.");
                }
                if (maxDate == null || phaseEndDate.after(maxDate)) {
                    maxDate = phaseEndDate;
                }
                // Get ID of the contest
                long contestId = phaseProject.getId();
                // Get latest bug resolution date for this contest
                Date contestLatestBugResolutionDate = getUtilityDAO().getContestLatestBugResolutionDate(contestId);
                if (contestLatestBugResolutionDate != null
                    && (maxDate == null || contestLatestBugResolutionDate.after(maxDate))) {
                    maxDate = contestLatestBugResolutionDate;
                }
            }
        }
        // Get the duration of copilot project in days (only full days are counted)
        if (maxDate == null || minDate == null) {
            copilotProjectDTO.setRealDuration(CopilotProjectDTO.UNDEFINED_STAT);
            return;
        }
        copilotProjectDTO.setRealDuration(Long.valueOf(
            (maxDate.getTime() - minDate.getTime()) / (24 * 60 * 60 * 1000)).intValue());
    }

    /**
     * Retrieves the copilot profile DAO to be used by this class.
     *
     * @return the copilot profile DAO to be used by this class
     */
    protected CopilotProfileDAO getCopilotProfileDAO() {
        return copilotProfileDAO;
    }

    /**
     * Sets the copilot profile DAO to be used by this class.
     *
     * @param copilotProfileDAO the copilot profile DAO to be used by this class
     */
    public void setCopilotProfileDAO(CopilotProfileDAO copilotProfileDAO) {
        this.copilotProfileDAO = copilotProfileDAO;
    }

    /**
     * Retrieves the phase manager to be used by this class.
     *
     * @return the phase manager to be used by this class
     */
    protected PhaseManager getPhaseManager() {
        return phaseManager;
    }

    /**
     * Sets the phase manager to be used by this class.
     *
     * @param phaseManager the phase manager to be used by this class
     */
    public void setPhaseManager(PhaseManager phaseManager) {
        this.phaseManager = phaseManager;
    }
}
