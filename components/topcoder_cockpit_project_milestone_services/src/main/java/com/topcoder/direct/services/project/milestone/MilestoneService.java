/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.SortOrder;

/**
 * <p>
 * This interface defines the service contract for the management of milestones.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Enterprise Calendar Revamp) change notes:
 * <ul>
 *     <li>Add method {@link #getAllForProjects(java.util.List, java.util.List,
 *     com.topcoder.direct.services.project.milestone.model.SortOrder)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (Module Assembly - TC Cockpit Public Page for TopCoder Road map and RSS syndication) change notes:
 * <ul>
 *     <li>Add method {@link #getAllForProjectsGroupedByStatus(java.util.List, java.util.Map)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.3 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Roadmap part) change notes:
 * <ul>
 *     <li>Add method {@link #getAllForProjectsGroupedByStatus(java.util.List, java.util.Map, java.util.Date, java.util.Date)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> Implementations are expected to be effectively thread-safe.
 * </p>
 *
 * @author argolite, GreatKevin
 * @version 1.3
 */
public interface MilestoneService {
    /**
     * This method adds the given milestone.
     *
     * @param milestone
     *            the milestone to add
     * @return the ID of the milestone
     * @throws IllegalArgumentException
     *             If milestone is null
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public long add(Milestone milestone) throws ProjectMilestoneManagementException;

    /**
     * This method adds the given milestones.
     *
     * @param milestones
     *            the milestones to add
     * @throws IllegalArgumentException
     *             If milestones is null/empty, or has null elements
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public void add(List<Milestone> milestones) throws ProjectMilestoneManagementException;

    /**
     * This method updates the given milestone.
     *
     * @param milestone
     *            the milestone to update
     * @throws IllegalArgumentException
     *             If milestone is null
     * @throws EntityNotFoundException
     *             If the milestone is not found
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public void update(Milestone milestone) throws ProjectMilestoneManagementException;

    /**
     * This method updates the given milestones.
     *
     * @param milestones
     *            the milestones to update
     * @throws IllegalArgumentException
     *             If milestones is null/empty, or has null elements
     * @throws EntityNotFoundException
     *             If any milestone is not found
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public void update(List<Milestone> milestones) throws ProjectMilestoneManagementException;

    /**
     * This method deletes the given milestone.
     *
     * @param milestoneId
     *            the milestone's id to delete
     * @throws EntityNotFoundException
     *             If the milestone is not found
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public void delete(long milestoneId) throws ProjectMilestoneManagementException;

    /**
     * This method deletes the given milestones.
     *
     * @param milestoneIds
     *            the ids of milestones to delete
     * @throws IllegalArgumentException
     *             If milestoneIds is null/empty, or has null elements
     * @throws EntityNotFoundException
     *             If any milestone is not found
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public void delete(List<Long> milestoneIds) throws ProjectMilestoneManagementException;

    /**
     * This method gets the milestone with the given ID. If not found, returns null.
     *
     * @param milestoneId
     *            the ID of the milestone to get
     * @return the milestone
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public Milestone get(long milestoneId) throws ProjectMilestoneManagementException;

    /**
     * This method gets the milestones with the given IDs. If any given milestone is not found, in its index a null
     * is returned, so the return list has the same size as the input list.
     *
     * @param milestoneIds
     *            the IDs of the milestones to get
     * @return the milestones
     * @throws IllegalArgumentException
     *             If milestoneIds is null/empty, or has null elements
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public List<Milestone> get(List<Long> milestoneIds) throws ProjectMilestoneManagementException;

    /**
     * This method gets the milestones for the given project, for the given statuses, and sorted in the given
     * order. If none found, returns an empty list.
     *
     * @param sortOrder
     *            the order of the milestones grouped by status - default is ascending
     * @param requestedStatuses
     *            the statuses whose milestones are to be filtered by
     * @param projectId
     *            the ID of the project
     * @return the milestones
     * @throws IllegalArgumentException
     *             If requestedStatuses contains null or duplicate elements
     * @throws EntityNotFoundException
     *             If the project with projectId is not found
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public List<Milestone> getAll(long projectId, List<MilestoneStatus> requestedStatuses, SortOrder sortOrder)
        throws ProjectMilestoneManagementException;

    /**
     * This method gets the milestones for the given project, for the given statuses in the given month. If none
     * found, returns an empty list.
     *
     * @param month
     *            the 1-based index of the month
     * @param requestedStatuses
     *            the statuses whose milestones are to be filtered by
     * @param year
     *            the year number
     * @param projectId
     *            the ID of the project
     * @return the milestones
     * @throws IllegalArgumentException
     *             If requestedStatuses contains null or duplicate elements, or month is not in [1,12]
     * @throws EntityNotFoundException
     *             If the project with projectId is not found
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public List<Milestone> getAllInMonth(long projectId, int month, int year,
        List<MilestoneStatus> requestedStatuses) throws ProjectMilestoneManagementException;


    /**
     * Gets all the milestones for the given list of projects and sorted in the given
     * order. If none found, returns an empty list.
     *
     * @param projectIds        the list of project ids.
     * @param requestedStatuses the list of requested status.
     * @param sortOrder         the sort order
     * @return the list of milestones.
     * @throws ProjectMilestoneManagementException
     *          If there are any errors during the execution of this method
     * @since 1.1
     */
    public List<Milestone> getAllForProjects(List<Long> projectIds, List<MilestoneStatus> requestedStatuses,
                                             SortOrder sortOrder) throws ProjectMilestoneManagementException;

    /**
     * Gets milestones of a list projects and groups them by the milestone status.
     *
     * @param projectIds the list of project ids to get milestones for
     * @param filters the status filter with sort orders
     * @return the grouped milestones by status store in a map, the key is milestone status, the value is the milestone list.
     * @throws ProjectMilestoneManagementException if there is any error when retrieving the milestones.
     * @since 1.2
     */
    public Map<MilestoneStatus, List<Milestone>> getAllForProjectsGroupedByStatus(List<Long> projectIds,
                                Map<MilestoneStatus, SortOrder> filters) throws ProjectMilestoneManagementException;

    /**
     * Gets the milestones of list projects in the specified date range and groups them by the milestone status.
     *
     * @param projectIds the list of project ids to get milestones for
     * @param filters the status filter with sort orders
     * @param startDate the start date used to filter milestone
     * @param endDate the end date used to filter milestone
     * @return the grouped milestones by status store in a map, the key is milestone status, the value is the milestone list.
     * @throws ProjectMilestoneManagementException if there is any error when retrieving the milestones.
     * @since 1.3
     */
    public Map<MilestoneStatus, List<Milestone>> getAllForProjectsGroupedByStatus(List<Long> projectIds,
                                Map<MilestoneStatus, SortOrder> filters, Date startDate, Date endDate)
            throws ProjectMilestoneManagementException;
}
