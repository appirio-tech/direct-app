/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import com.topcoder.direct.services.copilot.CopilotServiceException;
import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectPlanDAO;
import com.topcoder.direct.services.copilot.dao.UtilityDAO;
import com.topcoder.direct.services.copilot.dto.ContestTypeStat;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;
import com.topcoder.direct.services.copilot.model.PlannedContest;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.link.ProjectLink;
import com.topcoder.management.project.link.ProjectLinkManager;
import com.topcoder.management.project.link.ProjectLinkType;

/**
 * <p>
 * This is a base class for CopilotProfileService and CopilotProjectService implementations provided in this
 * component. It holds injected DAOs and managers instances and provides functionality that is common for both
 * service implementations.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class has mutable attributes, thus it's not thread safe. But it's assumed that it will
 * be initialized via Spring IoC before calling any business method, this way it's always used in thread safe manner.
 * It uses thread safe DAOs and Log instances. Used instances of ProjectManager and ProjectLinkManager are not thread
 * safe, thus additional synchronization is used when accessing their methods.
 * </p>
 *
 * <p>
 * Version 1.0.1 (Release Assembly - TC Direct Select From Copilot Pool Assembly) Change notes:
 *   <ol>
 *     <li>Update {@link #checkContestIdList()} method to not throw IAE if contest list is empty.</li>
 *     <li>Update {@link #calculateContestsStat()} method to not count bug race number.</li>
 *   </ol>
 * </p>
 *
 * @param <T> the type of the entity to be managed by this service
 * @author saarixx, TCSDEVELOPER, tangzx
 * @version 1.0.1
 */
public abstract class BaseCopilotService<T extends IdentifiableEntity> extends GenericServiceImpl<T> {
    /**
     * Represents the 'REPOST_FOR' projectLinkType.
     */
    private static final long REPOST_FOR = 5;
    /**
     * The copilot project plan DAO to be used by subclasses. Cannot be null after initialization (validation is
     * performed in checkInit() method). Initialized by Spring setter injection. Has protected getter and public
     * setter. Is used in checkInit().
     */
    private CopilotProjectPlanDAO copilotProjectPlanDAO;
    /**
     * The utility DAO to be used by this class. Cannot be null after initialization (validation is performed in
     * checkInit() method). Initialized by Spring setter injection. Has protected getter and public setter. Is used
     * in checkInit() and calculateContestsStat().
     */
    private UtilityDAO utilityDAO;
    /**
     * <p>
     * The project manager to be used by this class. Cannot be null after initialization (validation is performed in
     * checkInit() method). Initialized by Spring setter injection. Has protected getter and public setter. Is used
     * in checkInit() and calculateContestsStat().
     * </p>
     * <p>
     * Implementations of ProjectManager are not required to be thread safe. Thus to make BaseCopilotService thread
     * safe, all calls to projectManager's methods must be synchronized on "this" instance.
     * </p>
     */
    private ProjectManager projectManager;
    /**
     * <p>
     * The project link manager to be used by this class. Cannot be null after initialization (validation is
     * performed in checkInit() method). Initialized by Spring setter injection. Has protected getter and public
     * setter. Is used in checkInit() and calculateContestsStat().
     * </p>
     * <p>
     * Implementations of ProjectLinkManager are not required to be thread safe. Thus to make BaseCopilotService
     * thread safe, all calls to projectLinkManager's methods must be synchronized on "this" instance.
     * </p>
     */
    private ProjectLinkManager projectLinkManager;
    /**
     * The ID of the active project status. Must be positive after initialization (validation is performed in
     * checkInit() method). Initialized by Spring setter injection. Has getter and setter. Is used in checkInit() and
     * calculateContestsStat().
     */
    private long activeProjectStatusId;
    /**
     * The IDs of the failed project statuses. Cannot be null/empty and must contain positive elements only after
     * initialization (validation is performed in checkInit() method). Initialized by Spring setter injection. Has
     * getter and setter. Is used in checkInit() and calculateContestsStat().
     */
    private List<Long> failedProjectStatusIds;
    /**
     * The ID of the active copilot project status. Must be positive after initialization (validation is performed in
     * checkInit() method). Initialized by Spring setter injection. Has getter and setter. Is used in checkInit().
     */
    private long activeCopilotProjectStatusId;
    /**
     * The mapping from project category ID to the corresponding ProjectCategory instance. Is initialized in
     * checkInit() with not null value and never changed after that. Cannot contain null key/value after
     * initialization. Is used in checkInit() and countPlannedContestInStats().
     */
    private Map<Long, ProjectCategory> projectCategories;

    /**
     * Creates an instance of BaseCopilotService.
     */
    protected BaseCopilotService() {
        // Do nothing
    }

    /**
     * Checks whether this class was initialized by Spring properly. Additionally finishes the initialization of this
     * class.
     *
     * @throws CopilotServiceInitializationException if the class was not initialized properly (e.g. when required
     *             properly is not specified or property value has invalid format)
     */
    @PostConstruct
    protected void checkInit() {
        super.checkInit();
        Helper.checkNullForInjectedValue(copilotProjectPlanDAO, "copilotProjectPlanDAO");
        Helper.checkNullForInjectedValue(utilityDAO, "utilityDAO");
        Helper.checkNullForInjectedValue(projectManager, "projectManager");
        Helper.checkNullForInjectedValue(projectLinkManager, "projectLinkManager");
        checkPositive(activeProjectStatusId, "activeProjectStatusId");
        if (failedProjectStatusIds == null || failedProjectStatusIds.isEmpty()) {
            throw new CopilotServiceInitializationException("failedProjectStatusIds should not be null or empty.");
        }
        for (Long id : failedProjectStatusIds) {
            Helper.checkNullForInjectedValue(id, "Any element in failedProjectStatusIds");
            checkPositive(id, "Any element in failedProjectStatusIds");
        }
        checkPositive(activeCopilotProjectStatusId, "activeCopilotProjectStatusId");
        try {
            projectCategories = new HashMap<Long, ProjectCategory>();
            ProjectCategory[] projectCategoryArray;
            synchronized (this) {
                projectCategoryArray = projectManager.getAllProjectCategories();
            }
            for (ProjectCategory projectCategory : projectCategoryArray) {
                projectCategories.put(projectCategory.getId(), projectCategory);
            }
        } catch (PersistenceException e) {
            throw new CopilotServiceInitializationException(
                "Error occurred when executing projectManager.getAllProjectCategories method.", e);
        }
    }

    /**
     * Checks whether the given argument is positive.
     *
     * @param arg the argument to check
     * @param name the name of the argument to check
     * @throws CopilotServiceInitializationException if the given argument is not positive
     */
    private static void checkPositive(long arg, String name) {
        if (arg <= 0) {
            throw new CopilotServiceInitializationException(name + " should be positive.");
        }
    }

    /**
     * Retrieves the statistics container for the specified project category. If statistics doesn't exist, creates a
     * new one and puts it to the map.
     *
     * @param contestTypeStats the mapping from category name to the corresponding statistics container
     * @param projectCategory the project category
     * @return the retrieved statistics for project category (not null)
     * @throws IllegalArgumentException if contestTypeStats is null, contains null key or value, projectCategory is
     *             null
     */
    protected ContestTypeStat getStatForProjectCategory(Map<String, ContestTypeStat> contestTypeStats,
        ProjectCategory projectCategory) {
        Helper.checkNull(projectCategory, "projectCategory");
        Helper.checkNull(contestTypeStats, "contestTypeStats");
        checkContestTypeStats(contestTypeStats);
        // Get name of the project category
        String projectCategoryName = projectCategory.getName();
        // Get stat for this category from the map
        ContestTypeStat stat = contestTypeStats.get(projectCategoryName);
        if (stat == null) {
            // Create new stat
            stat = new ContestTypeStat();
            // Set project category ID to the stat
            stat.setProjectCategoryId(projectCategory.getId());
            // Set project category name to the stat
            stat.setProjectCategoryName(projectCategoryName);
            // Put created stat to the map
            contestTypeStats.put(projectCategoryName, stat);
        }
        return stat;
    }

    /**
     * Check whether the contestTypeStats map is valid.
     *
     * @param contestTypeStats the contestTypeStats to check
     * @throws IllegalArgumentException if contestTypeStats contains null key or value
     */
    private static void checkContestTypeStats(Map<String, ContestTypeStat> contestTypeStats) {
        if (contestTypeStats != null) {
            for (Entry<String, ContestTypeStat> entry : contestTypeStats.entrySet()) {
                Helper.checkNull(entry.getKey(), "Any key in contestTypeStats");
                Helper.checkNull(entry.getValue(), "Any value in contestTypeStats");
            }
        }
    }

    /**
     * Counts the given planned contest in the statistics.
     *
     * @param contestTypeStats the mapping from project category name to the corresponding statistics
     * @param plannedContest the planned contest
     * @throws IllegalArgumentException if contestTypeStats is null, contains null key or value, plannedContest is
     *             null
     * @throws CopilotServiceException if some other error occurred
     */
    protected void countPlannedContestInStats(Map<String, ContestTypeStat> contestTypeStats,
        PlannedContest plannedContest) throws CopilotServiceException {
        // contestTypeStats will be checked in getStatForProjectCategory method
        Helper.checkNull(plannedContest, "plannedContest");
        // Get project category ID
        long projectCategoryId = plannedContest.getProjectCategoryId();
        // Get ProjectCategory by its ID from the map
        ProjectCategory projectCategory = projectCategories.get(projectCategoryId);
        if (projectCategory == null) {
            throw new CopilotServiceException("projectCategory with id " + projectCategoryId
                + " should exist in projectCategories map.");
        }
        // Get stat for this project category
        ContestTypeStat stat = getStatForProjectCategory(contestTypeStats, projectCategory);
        // Increase the planned contests number in the stat
        stat.setPlannedContests(stat.getPlannedContests() + 1);
    }

    /**
     * Check whether the contestIdList is valid.
     *
     * @param contestIdList the contestIdList to check
     * @throws IllegalArgumentException if contestIdList is null/empty, contains null
     */
    private static void checkContestIdList(List<Long> contestIdList) {
        Helper.checkNull(contestIdList, "contestIdList");
        // update to not throw IAE when it's empty
        /*
        if (contestIdList.isEmpty()) {
            throw new IllegalArgumentException("contestIdList should not be empty.");
        }
        */
        for (Long contestId : contestIdList) {
            Helper.checkNull(contestId, "Any element in contestIdList");
            if (contestId <= 0) {
                throw new IllegalArgumentException("Any element in contestIdList should be positive.");
            }
        }
    }

    /**
     * Obtain the projectIds from given contestIdList.
     *
     * @param contestIdList the contestIdList
     * @return an array of projectIds
     */
    private static long[] obtainProjectIds(List<Long> contestIdList) {
        long[] projectIds = new long[contestIdList.size()];
        for (int i = 0; i < projectIds.length; i++) {
            projectIds[i] = contestIdList.get(i);
        }
        return projectIds;
    }

    /**
     * Handles the totalReposted.
     *
     * @param contestId the contest id
     * @param totalReposted the totalReposted
     * @param stat the ContestTypeStat instance
     * @return the totalReposted
     * @throws PersistenceException if any error occurs while executing the method
     *             projectLinkManager.getSourceProjectLinks
     */
    private int handleTotalReposted(long contestId, int totalReposted, ContestTypeStat stat)
        throws PersistenceException {
        // Get all links from this project
        ProjectLink[] sourceProjectLinks = null;
        synchronized (this) {
            sourceProjectLinks = projectLinkManager.getSourceProjectLinks(contestId);
        }
        boolean isRepost = false;
        for (ProjectLink sourceProjectLink : sourceProjectLinks) {
            // Get project link type
            ProjectLinkType projectLinkType = sourceProjectLink.getType();
            if (projectLinkType.getId() == REPOST_FOR) {
                isRepost = true;
                break;
            }
        }
        if (isRepost) {
            totalReposted++;
            if (stat != null) {
                // Increase the number of reposted contests in the stat
                stat.setRepostedContests(stat.getRepostedContests() + 1);
            }
        } else if (stat != null) {
            // Increase the number of real contests in the stat
            stat.setRealContests(stat.getRealContests() + 1);
        }
        return totalReposted;
    }

    /**
     * Calculates the statistics for contests with the specified IDs.
     *
     * @param contestTypeStats the mapping from project category name to the corresponding statistics
     * @param contestIdList the list with contest IDs
     * @return the contests statistics instance (not null)
     * @throws IllegalArgumentException if contestTypeStats contains null key or value, contestIdList is null/empty,
     *             contains null
     * @throws CopilotServiceException if some other error occurred
     */
    protected ContestsStat calculateContestsStat(List<Long> contestIdList,
        Map<String, ContestTypeStat> contestTypeStats) throws CopilotServiceException {
        checkContestTypeStats(contestTypeStats);
        checkContestIdList(contestIdList);
        try {
            // Create contests stat instance
            ContestsStat contestsStat = new ContestsStat();
            if (contestIdList.isEmpty()) {
                return contestsStat;
            }
            
            // Get projects for the given contest IDs
            long[] projectIds = obtainProjectIds(contestIdList);
            Project[] projects = null;
            synchronized (this) {
                projects = projectManager.getProjects(projectIds);
            }
            int currentContests = 0;
            int totalFailedContests = 0;
            int totalReposted = 0;
            int totalBugs = 0;
            for (Project project : projects) {
                ContestTypeStat stat = null;
                if (contestTypeStats != null) {
                    // Get project category for this project
                    ProjectCategory projectCategory = project.getProjectCategory();
                    // Get stat for this project category
                    stat = getStatForProjectCategory(contestTypeStats, projectCategory);
                }
                long contestId = project.getId();
                totalReposted = handleTotalReposted(contestId, totalReposted, stat);
                // Get ID of the project status
                long projectStatusId = project.getProjectStatus().getId();
                if (projectStatusId == activeProjectStatusId) {
                    currentContests++;
                } else if (failedProjectStatusIds.contains(projectStatusId)) {
                    totalFailedContests++;
                    if (stat != null) {
                        // Increase the number of failed contests in the stat
                        stat.setFailedContests(stat.getFailedContests() + 1);
                    }
                }
                // update to not retrieve bug count since the implementation is incorrect for now
                // Get the number of bugs for this contest
                // totalBugs += utilityDAO.getContestBugCount(contestId);
            }
            // Set total contests number to the result
            contestsStat.setTotalContests(projects.length - totalReposted);
            // Set total reposted contests number to the result
            contestsStat.setTotalRepostedContests(totalReposted);
            // Set total failed contests number to the result
            contestsStat.setTotalFailedContests(totalFailedContests);
            // Set total bug races number to the result
            contestsStat.setTotalBugRaces(totalBugs);
            // Set the number of current (active) contests to the result
            contestsStat.setCurrentContests(currentContests);
            return contestsStat;
        /*    
        } catch (CopilotDAOException e) {
            // should not do logging here because only public methods should do logging.
            throw new CopilotServiceException("Error occurred when executing utilityDAO.getContestBugCount method.",
                e);
        */        
        } catch (PersistenceException e) {
            // should not do logging here because only public methods should do logging.
            throw new CopilotServiceException(
                "Error occurred when executing projectManager.getProjects or projectLinkManager#getSourceProjectLinks.",
                e);
        }
    }

    /**
     * Retrieves the copilot project plan DAO to be used by subclasses.
     *
     * @return the copilot project plan DAO to be used by subclasses
     */
    protected CopilotProjectPlanDAO getCopilotProjectPlanDAO() {
        return copilotProjectPlanDAO;
    }

    /**
     * Sets the copilot project plan DAO to be used by subclasses.
     *
     * @param copilotProjectPlanDAO the copilot project plan DAO to be used by subclasses
     */
    public void setCopilotProjectPlanDAO(CopilotProjectPlanDAO copilotProjectPlanDAO) {
        this.copilotProjectPlanDAO = copilotProjectPlanDAO;
    }

    /**
     * Retrieves the utility DAO to be used by this class.
     *
     * @return the utility DAO to be used by this class
     */
    protected UtilityDAO getUtilityDAO() {
        return utilityDAO;
    }

    /**
     * Sets the utility DAO to be used by this class.
     *
     * @param utilityDAO the utility DAO to be used by this class
     */
    public void setUtilityDAO(UtilityDAO utilityDAO) {
        this.utilityDAO = utilityDAO;
    }

    /**
     * Retrieves the project manager to be used by this class.
     *
     * @return the project manager to be used by this class
     */
    protected ProjectManager getProjectManager() {
        return projectManager;
    }

    /**
     * Sets the project manager to be used by this class.
     *
     * @param projectManager the project manager to be used by this class
     */
    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    /**
     * Retrieves the project link manager to be used by this class.
     *
     * @return the project link manager to be used by this class
     */
    protected ProjectLinkManager getProjectLinkManager() {
        return projectLinkManager;
    }

    /**
     * Sets the project link manager to be used by this class.
     *
     * @param projectLinkManager the project link manager to be used by this class
     */
    public void setProjectLinkManager(ProjectLinkManager projectLinkManager) {
        this.projectLinkManager = projectLinkManager;
    }

    /**
     * Retrieves the ID of the active project status.
     *
     * @return the ID of the active project status
     */
    public long getActiveProjectStatusId() {
        return activeProjectStatusId;
    }

    /**
     * Sets the ID of the active project status.
     *
     * @param activeProjectStatusId the ID of the active project status
     */
    public void setActiveProjectStatusId(long activeProjectStatusId) {
        this.activeProjectStatusId = activeProjectStatusId;
    }

    /**
     * Retrieves the IDs of the failed project statuses.
     *
     * @return the IDs of the failed project statuses
     */
    public List<Long> getFailedProjectStatusIds() {
        // Return a shallow copy of the failedProjectStatusIds attribute
        return new ArrayList<Long>(failedProjectStatusIds);
    }

    /**
     * Sets the IDs of the failed project statuses.
     *
     * @param failedProjectStatusIds the IDs of the failed project statuses
     */
    public void setFailedProjectStatusIds(List<Long> failedProjectStatusIds) {
        // Set a shallow copy of the array to the failedProjectStatusIds attribute
        this.failedProjectStatusIds = new ArrayList<Long>(failedProjectStatusIds);
    }

    /**
     * Retrieves the ID of the active copilot project status.
     *
     * @return the ID of the active copilot project status
     */
    public long getActiveCopilotProjectStatusId() {
        return activeCopilotProjectStatusId;
    }

    /**
     * Sets the ID of the active copilot project status.
     *
     * @param activeCopilotProjectStatusId the ID of the active copilot project status
     */
    public void setActiveCopilotProjectStatusId(long activeCopilotProjectStatusId) {
        this.activeCopilotProjectStatusId = activeCopilotProjectStatusId;
    }
}
