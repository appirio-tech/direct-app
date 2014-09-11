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

import com.topcoder.direct.services.copilot.CopilotProfileService;
import com.topcoder.direct.services.copilot.CopilotServiceException;
import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.dao.CopilotProjectPlanDAO;
import com.topcoder.direct.services.copilot.dao.UtilityDAO;
import com.topcoder.direct.services.copilot.dto.ContestTypeStat;
import com.topcoder.direct.services.copilot.dto.CopilotPoolMember;
import com.topcoder.direct.services.copilot.dto.CopilotProfileDTO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.PlannedContest;

/**
 * <p>
 * This class is an implementation of CopilotProfileService that uses pluggable managers and DAOs to perform its
 * functionality. It extends BaseCopilotService&lt;CopilotProfile&gt; that provides common functionality for
 * CopilotProfileService and CopilotProjectService implementations provided in this component. This class uses
 * Logging Wrapper logger to log errors and debug information.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 *
 * ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {&quot;applicationContext.xml&quot;});
 * CopilotProfileService service = (CopilotProfileService) context.getBean(&quot;copilotProfileService&quot;);
 * // get copilot pool members
 * service.getCopilotPoolMembers();
 * // get copilot profile
 * service.getCopilotProfile(1l);
 * // get copilot profileDTO
 * service.getCopilotProfileDTO(1l);
 *
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
public class CopilotProfileServiceImpl extends BaseCopilotService<CopilotProfile> implements CopilotProfileService {
    /**
     * The copilot project DAO to be used by this class. Cannot be null after initialization (validation is performed
     * in checkInit() method). Initialized by Spring setter injection. Has protected getter and public setter. Is
     * used in checkInit() and populateCopilotPoolMember().
     */
    private CopilotProjectDAO copilotProjectDAO;

    /**
     * Creates an instance of CopilotProfileServiceImpl.
     */
    public CopilotProfileServiceImpl() {
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
        if (!(getGenericDAO() instanceof CopilotProfileDAO)) {
            throw new CopilotServiceInitializationException("genericDAO should be an instance of CopilotProfileDAO.");
        }
        Helper.checkNullForInjectedValue(copilotProjectDAO, "copilotProjectDAO");
    }

    /**
     * Retrieves all copilot pool members from persistence. Returns an empty list if none are found.
     *
     * @return the retrieved copilot pool members (not null, doesn't contain null)
     * @throws CopilotServiceException if any error occurred
     */
    public List<CopilotPoolMember> getCopilotPoolMembers() throws CopilotServiceException {
        final String method = "CopilotProfileServiceImpl#getCopilotPoolMembers";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        try {
            CopilotProfileDAO copilotProfileDAO = (CopilotProfileDAO) getGenericDAO();
            // Retrieve all copilot profiles
            List<CopilotProfile> copilotProfiles = copilotProfileDAO.retrieveAll();
            List<CopilotPoolMember> result = new ArrayList<CopilotPoolMember>();
            for (CopilotProfile copilotProfile : copilotProfiles) {
                // Create copilot pool member instance
                CopilotPoolMember copilotPoolMember = new CopilotPoolMember();
                // Populate this copilot pool member instance
                populateCopilotPoolMember(copilotPoolMember, copilotProfile, method);
                // Add copilot pool member to the result list
                result.add(copilotPoolMember);
            }
            Helper.logReturnValue(getLog(), result);
            Helper.logMethodExit(method, getLog(), date);
            return result;
        } catch (CopilotDAOException e) {
            throw Helper.logError(new CopilotServiceException(
                "Error occurred when executing copilotProfileDAO#retrieveAll.", e), method, getLog());
        }
    }

    /**
     * Retrieves the copilot profile details.
     *
     * @param userId the user ID of the copilot
     * @return the retrieved copilot profile details or null if copilot user with the given ID doesn't exist
     * @throws IllegalArgumentException if userId <= 0
     * @throws CopilotServiceException if some other error occurred
     */
    public CopilotProfileDTO getCopilotProfileDTO(long userId) throws CopilotServiceException {
        final String method = "CopilotProfileServiceImpl#getCopilotProfileDTO";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        Helper.logInputArguments(getLog(), new String[] {"userId"}, new Object[] {userId});
        Helper.checkPositive(userId, "userId", method, getLog());
        try {
            CopilotProfileDAO copilotProfileDAO = (CopilotProfileDAO) getGenericDAO();
            // Get copilot profile by the user ID
            CopilotProfile copilotProfile = copilotProfileDAO.getCopilotProfile(userId);
            if (copilotProfile == null) {
                Helper.logReturnValue(getLog(), null);
                Helper.logMethodExit(method, getLog(), date);
                return null;
            }
            // Create copilot profile DTO instance
            CopilotProfileDTO result = new CopilotProfileDTO();
            // Populate this copilot profile DTO instance
            populateCopilotPoolMember(result, copilotProfile, method);
            Helper.logReturnValue(getLog(), result);
            Helper.logMethodExit(method, getLog(), date);
            return result;
        } catch (CopilotDAOException e) {
            throw Helper.logError(new CopilotServiceException(
                "Error occurred when executing copilotProfileDAO#getCopilotProfile.", e), method, getLog());
        }
    }

    /**
     * Retrieves the copilot profile of the user with the given ID.
     *
     * @param userId the user ID of the copilot
     * @return the retrieved copilot profile or null if copilot user with the given ID doesn't exist
     * @throws IllegalArgumentException if userId <= 0
     * @throws CopilotServiceException if some other error occurred
     */
    public CopilotProfile getCopilotProfile(long userId) throws CopilotServiceException {
        final String method = "CopilotProfileServiceImpl#getCopilotProfile";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        Helper.logInputArguments(getLog(), new String[] {"userId"}, new Object[] {userId});
        Helper.checkPositive(userId, "userId", method, getLog());
        try {
            CopilotProfileDAO copilotProfileDAO = (CopilotProfileDAO) getGenericDAO();
            CopilotProfile result = copilotProfileDAO.getCopilotProfile(userId);
            Helper.logReturnValue(getLog(), result);
            Helper.logMethodExit(method, getLog(), date);
            return result;
        } catch (CopilotDAOException e) {
            throw Helper.logError(new CopilotServiceException(
                "Error occurred when executing copilotProfileDAO#getCopilotProfile.", e), method, getLog());
        }
    }

    /**
     * Acquires contest id list.
     *
     * @param copilotPoolMember the CopilotPoolMember instance
     * @param copilotProjects a list of CopilotProject
     * @param copilotUserId copilot user id
     * @return a list of contest id
     * @throws CopilotServiceException if status of CopilotProjectStatus is null
     * @throws CopilotDAOException if any error occurs while calling utilityDAO#getCopilotProjectContests
     */
    private List<Long> acquireContestIdList(CopilotPoolMember copilotPoolMember,
        List<CopilotProject> copilotProjects, long copilotUserId) throws CopilotServiceException,
        CopilotDAOException {
        int currentProjects = 0;
        // Create a list for IDs of contests managed by this copilot
        List<Long> contestIdList = new ArrayList<Long>();
        for (CopilotProject copilotProject : copilotProjects) {
            // Get contest project status
            CopilotProjectStatus status = copilotProject.getStatus();
            if (status == null) {
                // log it in calling method
                throw new CopilotServiceException("status should not be null.");
            }
            // Get ID of the contest project status
            long copilotProjectStatusId = status.getId();
            if (copilotProjectStatusId == getActiveCopilotProjectStatusId()) {
                currentProjects++;
            }
            // Get TC direct project ID of this copilot project
            long tcDirectProjectId = copilotProject.getTcDirectProjectId();
            // Get IDs of all contests for this copilot project
            long[] contestIds = getUtilityDAO().getCopilotProjectContests(copilotUserId, tcDirectProjectId);
            for (long contestId : contestIds) {
                // Add ID to the list
                contestIdList.add(contestId);
            }
        }
        // Set the number of current projects to copilotPoolMember
        copilotPoolMember.setCurrentProjects(currentProjects);
        return contestIdList;
    }

    /**
     * Handle planned contest in stats.
     *
     * @param copilotProjects a list of CopilotProject
     * @param contestTypeStats contestTypeStats map
     * @throws CopilotDAOException any error occurs while calling copilotProjectPlanDAO#getCopilotProjectPlan
     * @throws CopilotServiceException if any error occurs while executing
     *             countPlannedContestInStats(contestTypeStats, plannedContest)
     */
    private void handlePlannedContestInStats(List<CopilotProject> copilotProjects,
        Map<String, ContestTypeStat> contestTypeStats) throws CopilotDAOException, CopilotServiceException {
        for (CopilotProject copilotProject : copilotProjects) {
            // Get copilot project ID
            long copilotProjectId = copilotProject.getId();
            CopilotProjectPlanDAO copilotProjectPlanDAO = getCopilotProjectPlanDAO();
            // Get copilot project plan for this copilot project
            CopilotProjectPlan copilotProjectPlan = copilotProjectPlanDAO.getCopilotProjectPlan(copilotProjectId);
            // Get all planned contests for this copilot project
            if (copilotProjectPlan.getPlannedContests() != null) {
                Set<PlannedContest> plannedContests = copilotProjectPlan.getPlannedContests();
                for (PlannedContest plannedContest : plannedContests) {
                    // Count planned contest in stats
                    countPlannedContestInStats(contestTypeStats, plannedContest);
                }
            }
        }
    }

    /**
     * Populates the given copilot pool member instance using the specified copilot profile.
     *
     * @param copilotProfile the copilot profile to be used as an input
     * @param copilotPoolMember the copilot pool member to be populated
     * @param method the method name
     * @throws CopilotServiceException if any error occurred
     */
    private void populateCopilotPoolMember(CopilotPoolMember copilotPoolMember, CopilotProfile copilotProfile,
        String method) throws CopilotServiceException {
        try {
            CopilotProfileDTO copilotProfileDTO = null;
            if (copilotPoolMember instanceof CopilotProfileDTO) {
                copilotProfileDTO = (CopilotProfileDTO) copilotPoolMember;
            }
            copilotPoolMember.setCopilotProfile(copilotProfile);
            // Get copilot profile ID
            long copilotProfileId = copilotProfile.getId();
            // Get all copilot projects for this copilot
            List<CopilotProject> copilotProjects = copilotProjectDAO.getCopilotProjects(copilotProfileId);
            // Get the number of copilot projects
            int totalProjects = copilotProjects.size();
            // Set the total projects number to copilotPoolMember
            copilotPoolMember.setTotalProjects(totalProjects);
            // Get user ID of the copilot
            long copilotUserId = copilotProfile.getUserId();
            List<Long> contestIdList = acquireContestIdList(copilotPoolMember, copilotProjects, copilotUserId);
            Map<String, ContestTypeStat> contestTypeStats = null;
            if (copilotProfileDTO != null) {
                // Create map for stats calculated by contest types
                contestTypeStats = new HashMap<String, ContestTypeStat>();
            }
            // Calculate stat by the retrieved contest IDs
            ContestsStat contestsStat = calculateContestsStat(contestIdList, contestTypeStats);
            // Get the number of current contests from the stat
            int currentContests = contestsStat.getCurrentContests();
            // Set this number to copilotPoolMember
            copilotPoolMember.setCurrentContests(currentContests);
            // Similarly copy the total failed contests number from contestsStat to copilotPoolMember
            copilotPoolMember.setTotalFailedContests(contestsStat.getTotalFailedContests());
            // Similarly copy the total reposted contests number from contestsStat to copilotPoolMember
            copilotPoolMember.setTotalRepostedContests(contestsStat.getTotalRepostedContests());
            // Similarly copy the total contests number from contestsStat to copilotPoolMember
            copilotPoolMember.setTotalContests(contestsStat.getTotalContests());
            // Similarly copy the total bug races number from contestsStat to copilotPoolMember
            copilotPoolMember.setTotalBugRaces(contestsStat.getTotalBugRaces());
            if (copilotProfileDTO != null) {
                handlePlannedContestInStats(copilotProjects, contestTypeStats);
                // Set stats for each contest type to copilotProfileDTO
                copilotProfileDTO.setContestTypeStats(contestTypeStats);
                UtilityDAO utilityDAO = getUtilityDAO();
                // Get copilot earnings
                double earnings = utilityDAO.getCopilotEarnings(copilotUserId);
                // Set copilot earnings to copilotProfileDTO
                copilotProfileDTO.setEarnings(earnings);
            }
        } catch (CopilotDAOException e) {
            throw Helper.logError(new CopilotServiceException("Error occurred when calling underlying DAO methods.",
                e), method, getLog());
        } catch (CopilotServiceException e) {
            // log the exception thrown from protected method and acquireContestIdList method
            throw Helper.logError(e, method, getLog());
        } catch (IllegalArgumentException e) {
            throw Helper.logError(new CopilotServiceException("contestIdList should not be empty.", e), method,
                getLog());
        }
    }

    /**
     * Retrieves the copilot project DAO to be used by this class.
     *
     * @return the copilot project DAO to be used by this class
     */
    protected CopilotProjectDAO getCopilotProjectDAO() {
        return copilotProjectDAO;
    }

    /**
     * Sets the copilot project DAO to be used by this class.
     *
     * @param copilotProjectDAO the copilot project DAO to be used by this class
     */
    public void setCopilotProjectDAO(CopilotProjectDAO copilotProjectDAO) {
        this.copilotProjectDAO = copilotProjectDAO;
    }
}
