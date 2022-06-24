/*
 * Copyright (C) 2006 - 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

import java.util.Date;
import java.util.List;

/**
 * This interface defines the contract that any project persistence must
 * implement. The implementation classes will be used by ProjectManagerImpl to
 * perform project persistence operations. The implementation classes should
 * have a constructor that receives a namespace string parameter so that they're
 * exchangeable with each other by changing configuration settings in the
 * manager.
 * 
 * <p>
 * Module Contest Service Software Contest Sales Assembly change: new methods added to support creating/updating/query contest
 * sale for software contest.
 * </p>
 *
 * <p>
 * Updated for Cockpit Project Admin Release Assembly v1.0: new methods added to support retrieval of project and their permissions.
 * </p>
 * 
 * <p>
 * Version 1.0.1 (Cockpit Pipeline Release Assembly 1 v1.0) Change Notes:
 *  - Introduced method to retrieve SimplePipelineData for given date range.
 * </p>
  * <p>
 * Version 1.1 (Cockpit Pipeline Release Assembly 2 - Capacity) changelog:
 *     - added service that retrieves a list of capacity data (date, number of scheduled contests) starting from
 *       tomorrow for a given contest type
 * </p>
 * <p>
 * Changes in v1.1.1 - Cockpit Release Assembly 11
 * Add method getDesignComponents to get design components.
 * </p>
 * <p>
 * Version 1.2 (Cockpit Contest Eligibility version 1.0) changelog:
 *     - add method for creating private contest's term of use.
 * </p>
 *
 * <p>
 * Version 1.2.1 (Release Assembly - TopCoder Bug Hunt Assembly Integration 2) change log:
 *     - Add method {@link #getContestId(long)} to get the corresponding contest which has the same asset setting
 *     as the give contest id.
 * </p>
 *
 * <p>
 * Version 1.2.2 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) change log:
 *     - Added method {@link #saveSoftwareCheckpointSubmissionsGeneralFeedback(long, String)},
 *     {@link #getSoftwareCheckpointSubmissionsGeneralFeedback(long)} to support
 *     software checkpoint management.
 * </p>
 * 
 * <p>
 * Version 1.2.3 (Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match) change log:
 *     - Added method {@link #createProjectMMSpecification(ProjectMMSpecification, String)} to creates the given 
 *     ProjectMMSpecification entity.
 *     - Added method {@link #updateProjectMMSpecification(ProjectMMSpecification, String)} to updates the given 
 *     ProjectMMSpecification entity.
 *     - Added method {@link #getProjectMMSpecification(long)} to get the ProjectMMSpecification entity by given projectId.
 * </p>
 *
 * <p>
 * Version 1.3 (Module Assembly - TC Cockpit Contest Milestone Association 1) change notes:
 *  <ul>
 *      <li> Add method {@link #createProjectMilestoneRelation(long, long, String)}  to add contest milestone association</li>
 *      <li> Add method {@link #updateProjectMilestoneRelation(long, long, String)}  to update contest milestone association</li>
 *      <li> Add method {@link #deleteProjectMilestoneRelation(long, String)}   to remove contest milestone association</li>
 *      <li> Add method {@link #getProjectMilestoneRelation(long)}  to retrieve contest milestone association</li>
 * </ul>
 * <p>
 *
 * <p>
 * Version 1.4 (Module Assembly - TC Cockpit Contest Milestone Association Milestone Page Update)
 * <ul>
 *     <li>Add method {@link #deleteMilestoneProjectRelations(long, String)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.5 (Module Assembly - TC Cockpit Launch F2F contest)
 * <ul>
 *     <li>Added method {@link #getAllProjectPlatforms()}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6 (TOPCODER - SUPPORT GROUPS CONCEPT FOR CHALLENGES):
 * <ul>
 *     <li>Added {@link #getAllProjectGroups()}</li>
 * </ul>
 * </p>
 *
 * Version 1.7(Topcoder - Add Basic Marathon Match Creation And Update In Direct App):
 * <ul>
 *     <li>Added {@link #createOrUpdateMarathonMatch(Project, Date, Date, Date, boolean, String)}</li>
 * </ul>
 *
 * <p>
 * Thread safety: The implementations of this interface do not have to be thread
 * safe.
 * </p>
 *
 * @author tuenm, iamajia, pulky, murphydog, bugbuka, GreatKevin, TCSCODER
 * @version 1.7
 */
public interface ProjectPersistence {
    /**
     * Create the project in the database using the given project instance. The
     * project information is stored to 'project' table, while its properties
     * are stored in 'project_info' table. The project's associating scorecards
     * are stored in 'project_scorecard' table. For the project, its properties
     * and associating scorecards, the operator parameter is used as the
     * creation/modification user and the creation date and modification date
     * will be the current date time when the project is created.
     *
     * @param project
     *            The project instance to be created in the database.
     * @param operator
     *            The creation user of this project.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public void createProject(Project project, String operator) throws PersistenceException;

    /**
     * Update the given project instance into the database. The project
     * information is stored to 'project' table, while its properties are stored
     * in 'project_info' table. The project's associating scorecards are stored
     * in 'project_scorecard' table. All related items in these tables will be
     * updated. If items are removed from the project, they will be deleted from
     * the persistence. Likewise, if new items are added, they will be created
     * in the persistence. For the project, its properties and associating
     * scorecards, the operator parameter is used as the modification user and
     * the modification date will be the current date time when the project is
     * updated. An update reason is provided with this method. Update reason
     * will be stored in the persistence for future references.
     *
     * @param project
     *            The project instance to be updated into the database.
     * @param reason
     *            The update reason. It will be stored in the persistence for
     *            future references.
     * @param operator
     *            The modification user of this project.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public void updateProject(Project project, String reason, String operator) throws PersistenceException;

    /**
     * Retrieves the project instance from the persistence given its id. The
     * project instances is retrieved with its properties.
     * 
     * @return The project instance.
     * @param id
     *            The id of the project to be retrieved.
     * @throws IllegalArgumentException
     *             if the input id is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project getProject(long id) throws PersistenceException;

    /**
     * <p>
     * Retrieves an array of project instance from the persistence given their
     * ids. The project instances are retrieved with their properties.
     * </p>
     * 
     * @param ids
     *            The ids of the projects to be retrieved.
     * @return An array of project instances.
     * @throws IllegalArgumentException
     *             if ids is null or empty or contain an id that is less than or equal to zero.
     * @throws PersistenceException if error occurred while accessing the
     *          database.
     */
    public Project[] getProjects(long[] ids) throws PersistenceException;

	/**
     * <p>
     * Retrieves an array of project instance from the persistence whose
	 * create date is within current - days 
     * </p>
     * @param days last 'days' 
	 * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
	public Project[] getProjectsByCreateDate(int days) throws PersistenceException ;

    /**
     * Gets an array of all project types in the persistence. The project types
     * are stored in 'project_type_lu' table.
     *
     * @return An array of all project types in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException;

    /**
     * Gets an array of all project categories in the persistence. The project
     * categories are stored in 'project_category_lu' table.
     *
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException;

    /**
     * Gets an array of all project statuses in the persistence. The project
     * statuses are stored in 'project_status_lu' table.
     *
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException;

    /**
     * Gets an array of all project property type in the persistence. The
     * project property types are stored in 'project_info_type_lu' table.
     *
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException;

    /**
     * Gets all the project platforms.
     *
     * @return the project platforms.
     * @throws PersistenceException if there is any error.
     * @since 1.5
     */
    public ProjectPlatform[] getAllProjectPlatforms() throws PersistenceException;

    /**
     * Build {@link Project} directly from the {@link CustomResultSet}
     * 
     * @param resultSet a {@link CustomResultSet} containing the data for build the {@link Project} instances. 
     * @return an array of {@link Project}
     * @throws PersistenceException if error occurred while accessing the database.
     */
	public Project[] getProjects(CustomResultSet resultSet) throws PersistenceException;
	
	 /**
     * <p>
     * Retrieves an array of project instance from the persistence where 
     * tc direct project id is not null 
     * The project instances are retrieved with their properties.
     * </p>
     * 
     * @return An array of project instances.
    
     * @throws PersistenceException if error occurred while accessing the
     *          database.
     */
	public Project[] getAllTcDirectProject()throws PersistenceException;
	
	 /**
     * <p>
     * Retrieves an array of project instance from the persistence given
     * operator. 
     * The project instances are retrieved with their properties.
     * </p>
     * 
	 * @param operator the modification user of this project
	 * @returnAn array of project instances.
	 * @throws IllegalArgumentException
     *             if operator is null or empty. 
	 * @throws PersistenceException
	 */
	public Project[] getAllTcDirectProject(String operator)throws PersistenceException;

    /**
     * Gets Project entities by given directProjectId.
     *
     * @param directProjectId
     *            the given directProjectId to find the Projects.
     * @return the found Project entities, return empty if cannot find any.
     * @throws IllegalArgumentException
     *             if the argument is non-positive
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public Project[] getProjectsByDirectProjectId(long directProjectId) throws PersistenceException;

    /**
     * Gets Project FileType entities by given projectId.
     *
     * @param projectId
     *            the given projectId to find the entities.
     * @return the found FileType entities, return empty if cannot find any.
     * @throws IllegalArgumentException
     *             if the argument is non-positive
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public FileType[] getProjectFileTypes(long projectId) throws PersistenceException;

    /**
     * Updates FileType entities by given projectId, it also updates the relationship between project and FileType.
     *
     * @param projectId
     *            the given projectId to update the fileTypes entities.
     * @param fileTypes
     *            the given fileTypes entities to update.
     * @param operator
     *            the given audit user.
     * @throws IllegalArgumentException
     *             if projectId is non-positive or fileTypes contains null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public void updateProjectFileTypes(long projectId, List<FileType> fileTypes, String operator)
        throws PersistenceException;

    /**
     * Gets Project Prize entities by given projectId.
     *
     * @param projectId
     *            the given projectId to find the entities.
     * @return the found Prize entities, return empty if cannot find any.
     * @throws IllegalArgumentException
     *             if projectId is non-positive
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public Prize[] getProjectPrizes(long projectId) throws PersistenceException;

    /**
     * Updates Prize entities by given projectId, it also updates the relationship between project and Prize.
     *
     * @param projectId
     *            the given projectId to update the prizes entities.
     * @param prizes
     *            the given prizes entities to update.
     * @param operator
     *            the given audit user.
     * @throws IllegalArgumentException
     *             if projectId is non-positive, or prizes contains null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public void updateProjectPrizes(long projectId, List<Prize> prizes, String operator) throws PersistenceException;

    /**
     * Creates the given FileType entity.
     *
     * @param fileType
     *            the given fileType entity to create.
     * @param operator
     *            the given audit user.
     * @return the created fileType entity.
     * @throws IllegalArgumentException
     *             if fileType is null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public FileType createFileType(FileType fileType, String operator) throws PersistenceException;

    /**
     * Updates the given FileType entity.
     *
     * @param fileType
     *            the given fileType entity to update.
     * @param operator
     *            the given audit user.
     * @throws IllegalArgumentException
     *             if fileType is null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public void updateFileType(FileType fileType, String operator) throws PersistenceException;

    /**
     * Removes the given FileType entity.
     *
     * @param fileType
     *            the given fileType entity to update.
     * @param operator
     *            the given audit user.
     * @throws IllegalArgumentException
     *             if fileType is null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public void removeFileType(FileType fileType, String operator) throws PersistenceException;

    /**
     * Creates the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @param milestoneId the id of the direct project milestone
     * @param operator the operator
     * @throws PersistenceException if any error occurs
     * @since 1.3
     */
    public void createProjectMilestoneRelation(long projectId, long milestoneId, String operator) throws PersistenceException;

    /**
     * Updates the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @param milestoneId the id of the direct project milestone
     * @param operator the operator
     * @throws PersistenceException if any error occurs
     * @since 1.3
     */
    public void updateProjectMilestoneRelation(long projectId, long milestoneId, String operator) throws PersistenceException;

    /**
     * Deletes the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @param operator the operator
     * @throws PersistenceException if any error occurs
     * @since 1.3
     */
    public void deleteProjectMilestoneRelation(long projectId, String operator) throws PersistenceException;

    /**
     * Deletes all the contests associations to the specified milestone.
     *
     * @param milestoneId the id of the milestone.
     * @param operator the operator.
     * @throws PersistenceException if any error occurs.
     * @since 1.4
     */
    public void deleteMilestoneProjectRelations(long milestoneId, String operator) throws PersistenceException;

    /**
     * Gets the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @return the project milestone id
     * @throws PersistenceException if any error occurs
     * @since 1.3
     */
    public long getProjectMilestoneRelation(long projectId) throws PersistenceException;

    /**
     * Gets all FileType entities.
     *
     * @return the found FileType entities, return empty if cannot find any.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public FileType[] getAllFileTypes() throws PersistenceException;

    /**
     * Gets all PrizeType entities.
     *
     * @return the found PrizeType entities, return empty if cannot find any.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public PrizeType[] getPrizeTypes() throws PersistenceException;

    /**
     * Creates the given Prize entity.
     *
     * @param prize
     *            the given prize entity to create.
     * @param operator
     *            the given audit user.
     * @return the created prize entity.
     * @throws IllegalArgumentException
     *             if prize is null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public Prize createPrize(Prize prize, String operator) throws PersistenceException;

    /**
     * Updates the given prize entity.
     *
     * @param prize
     *            the given prize entity to create.
     * @param operator
     *            the given audit user.
     * @throws IllegalArgumentException
     *             if prize is null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public void updatePrize(Prize prize, String operator) throws PersistenceException;

    /**
     * Removes the given prize entity.
     *
     * @param prize
     *            the given prize entity to create.
     * @param operator
     *            the given audit user.
     * @throws IllegalArgumentException
     *             if prize is null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public void removePrize(Prize prize, String operator) throws PersistenceException;

    /**
     * Creates the given ProjectStudioSpecification entity.
     *
     * @param spec
     *            the given ProjectStudioSpecification entity to create.
     * @param operator
     *            the given audit user.
     * @return the created spec entity
     * @throws IllegalArgumentException
     *             if spec is null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public ProjectStudioSpecification createProjectStudioSpecification(ProjectStudioSpecification spec, String operator)
        throws PersistenceException;
    
    /**
     * Creates the given ProjectMMSpecification entity.
     *
     * @param spec
     *            the given ProjectMMSpecification entity to create.
     * @param operator
     *            the given audit user.
     * @return the created spec entity
     * @throws IllegalArgumentException
     *             if spec is null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2.3
     */
    public ProjectMMSpecification createProjectMMSpecification(ProjectMMSpecification spec, String operator)
        throws PersistenceException;

    /**
     * Updates the given ProjectStudioSpecification entity.
     *
     * @param spec
     *            the given ProjectStudioSpecification entity to create.
     * @param operator
     *            the given audit user.
     * @throws IllegalArgumentException
     *             if spec is null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public void updateProjectStudioSpecification(ProjectStudioSpecification spec, String operator)
        throws PersistenceException;
    
    /**
     * Updates the given ProjectMMSpecification entity.
     *
     * @param spec
     *            the given ProjectMMSpecification entity to create.
     * @param operator
     *            the given audit user.
     * @throws IllegalArgumentException
     *             if spec is null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2.3
     */
    public void updateProjectMMSpecification(ProjectMMSpecification spec, String operator)
        throws PersistenceException;

    /**
     * Removes the given ProjectStudioSpecification entity.
     *
     * @param spec
     *            the given ProjectStudioSpecification entity to create.
     * @param operator
     *            the given audit user.
     * @throws IllegalArgumentException
     *             if spec is null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public void removeProjectStudioSpecification(ProjectStudioSpecification spec, String operator)
        throws PersistenceException;

    /**
     * Gets ProjectStudioSpecification entity by given projectId.
     *
     * @param projectId
     *            the given projectId to find the entities.
     * @return the found ProjectStudioSpecification entities, return null if cannot find any.
     * @throws IllegalArgumentException
     *             if projectId is non-positive
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public ProjectStudioSpecification getProjectStudioSpecification(long projectId) throws PersistenceException;
    
    /**
     * Gets ProjectMMSpecification entity by given projectId.
     *
     * @param projectId
     *            the given projectId to find the entities.
     * @return the found ProjectMMSpecification entities, return null if cannot find any.
     * @throws IllegalArgumentException
     *             if projectId is non-positive
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2.3
     */
    public ProjectMMSpecification getProjectMMSpecification(long projectId) throws PersistenceException;

    /**
     * Updates the given ProjectStudioSpecification entity for specified project id.
     *
     * @param spec
     *            the given ProjectStudioSpecification entity to update.
     * @param projectId
     *            the given project id to update.
     * @param operator
     *            the given audit user.
     * @throws IllegalArgumentException
     *             if spec is null, or projectId is non-positive or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public void updateStudioSpecificationForProject(ProjectStudioSpecification spec, long projectId, String operator)
        throws PersistenceException;
    
	/**
     * <p>
     * Creates a new contest sale and returns the created contest sale.
     * </p>
     *
     * @param contestSale the contest sale to create
     *
     * @return the created contest sale.
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public ContestSale createContestSale(ContestSale contestSale) throws PersistenceException;

    /**
     * <p>
     * Gets the sale status by given id.
     * </p>
     *
     * @param saleStatusId the given sale status id.
     *
     * @return the sale status by given id.
     *
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public SaleStatus getSaleStatus(long saleStatusId) throws PersistenceException;

    /**
     * <p>
     * Gets the sale type by given id.
     * </p>
     *
     * @param saleTypeId the given sale type id.
     *
     * @return the sale type by given id.
     *
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public SaleType getSaleType(long saleTypeId) throws PersistenceException;

    /**
     * <p>
     * Gets contest sale by id, and return the retrieved contest sale. If
     * the contest sale doesn't exist, null is returned.
     * </p>
     *
     * @param contestSaleId the contest sale id
     *
     * @return the retrieved contest sale, or null if id doesn't exist
     *
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public ContestSale getContestSale(long contestSaleId) throws PersistenceException;

    /**
     * <p>
     * Gets contest sales by contest id, and return the retrieved contest sales.
     * </p>
     *
     * @param contestId the contest id of the contest sale
     *
     * @return the retrieved contest sales, or empty if none exists
     *
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public List<ContestSale> getContestSales(long contestId) throws PersistenceException;

    /**
     * <p>
     * Updates the contest sale data.
     * </p>
     *
     * @param contestSale the contest sale to update
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public void editContestSale(ContestSale contestSale) throws PersistenceException;

    /**
     * <p>
     * Removes contest sale, return true if the contest sale exists and
     * removed successfully, return false if it doesn't exist.
     * </p>
     *
     * @param contestSaleId the contest sale id
     *
     * @return true if the contest sale exists and removed successfully,
     *         return false if it doesn't exist
     *
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public boolean removeContestSale(long contestSaleId) throws PersistenceException;

	/**
     * Retrieve scorecard id with given project type and scorecard type.
	 *
     * @param projectTypeId   the project type id
     * @param scorecardTypeId the scorecard type id
     * @return the scorecard id
     */
    public long getScorecardId(long projectTypeId, int scorecardTypeId) throws PersistenceException;
	
	public List<SimpleProjectContestData> getSimpleProjectContestData() throws PersistenceException;
    
    public List<SimpleProjectContestData> getSimpleProjectContestData(long pid) throws PersistenceException;
    public List<SimpleProjectContestData> getSimpleProjectContestDataByUser(String user) throws PersistenceException;
    
    /**
     * <p>
     * Gets the list of project their read/write/full permissions.
     * </p>
     * 
     * @param createdUser
     *            the specified user for which to get the permission
     * @return the list of project their read/write/full permissions.
     * 
     * @throws PersistenceException exception if error during retrieval from database.
     * 
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public List<SimpleProjectPermissionData> getSimpleProjectPermissionDataForUser(long createdUser)
            throws PersistenceException ;

    /**
     * Gets the list of simple pipeline data for specified user id and between specified start and end date.
     * 
     * @param userId
     *            the user id.
     * @param startDate
     *            the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate
     *            the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests
     *            whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws PersistenceException
     *             if error during retrieval from database.
     * @since 1.0.1             
     */
    public List<SimplePipelineData> getSimplePipelineData(long userId, Date startDate, Date endDate,
            boolean overdueContests) throws PersistenceException;

    /**
     * Retrieves a list of capacity data (date, number of scheduled contests) for the given contest type starting
     * from tomorrow.
     *
     * @param contestType the contest type
     *
     * @return the list of capacity data
     *
     * @throws PersistenceException if any error occurs during retrieval of information.
     *
     * @since 1.1
     */
    public List<SoftwareCapacityData> getCapacity(int contestType) throws PersistenceException;


     /**
     * Get all design components.
     *
     * @param userId
     *            The user id
     * @throws PersistenceException
     *             if any other error occurs
     * @since 1.1.1
     */
    public List<DesignComponents> getDesignComponents(long userId) throws PersistenceException;
     /**
     * Get corresponding development contest's id for the design contest.
     *
     * @param userId
     *            The user id
     * @throws PersistenceException
     *             if any other error occurs
     * @since 1.1.1
     */
    public long getDevelopmentContestId(long contestId) throws PersistenceException;

     /**
     * check contest permission, check if a user has permission (read or write) on a contest
     *
     * @param contestId the contest id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean checkContestPermission(long contestId, boolean readonly, long userId)  throws PersistenceException;

     /**
     * check contest permission, check if a user has permission (read or write) on a project
     *
     * @param projectId the tc direct project id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean checkProjectPermission(long tcprojectId, boolean readonly, long userId) throws PersistenceException;


     /**
     * <p>
     * get project ids by tc direct id
     * </p>
     *
     * @tcDirectId tc direct project id
     *
     * @return list of project ids
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public List<Long> getProjectIdByTcDirectProject(long tcprojectId) throws PersistenceException;

    /**
     * <p>
     * get forum id by project id
     * </p>
     *
     * @projectId project id
     *
     * @return forum id
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public long getForumId(long projectId) throws PersistenceException;

    
    /**
     * check if user has contest permission, it checks contest permission only (not project permission)
     *
     * @param contestId the contest id
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean hasContestPermission(long contestId, long userId)  throws PersistenceException;


    /**
     * <p>
     * get tc direct project id by project id
     * </p>
     *
     * @projectId project id
     *
     * @return tc direct project id
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public long getTcDirectProject(long projectId) throws PersistenceException;


     /**
     * <p>
     * check if it is dev only 
     * </p>
     *
     * @projectId  project id
     *
     * @return boolean
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public boolean isDevOnly(long projectId) throws PersistenceException;


    /**
     * Gets an array of all billing project configuration type in the persistence. The billing
     * project configuration types are stored in 'client_billing_config_type_lu' table.
     * 
     * @return An array of all BillingProjectConfigTypes in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public BillingProjectConfigType[] getAllBillingProjectConfigTypes() throws PersistenceException;

    /**
     * Gets an array of all the billing project configurations in the persistence. The billing
     * project configurations are stored in 'client_billing_config' table.
     * 
     * 
     * @param billingProjectId the id of the billing project.
     * @return an array of all BillingProjectConfiguration.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public BillingProjectConfiguration[] getAllBillingProjectConfigs(long billingProjectId) throws PersistenceException;

    /**
     * Get corresponding development contest's id for the design contest.
     *
     * @param contestId
     *            The dev contestId
     * @throws PersistenceException
     *             if any other error occurs
     */
    public long getDesignContestId(long contestId) throws PersistenceException;

    /**
     * Gets the corresponding contest id with the same asset setting (external id, component id and version id) with
     * the given contest id.
     *
     * @param contestId the contest id to match
     * @return the matched contest id
     * @throws PersistenceException if any error occurs
     * @since 1.2.1
     */
    public long getContestId(long contestId) throws PersistenceException;
    
    /**
     * Gets active contests for the given user, only active contests will be returned.
     * 
     * @param userId the id of the user.
     * @return the data stored in a list of SimpleProjectContestData instances.
     * @throws PersistenceException if any error occurs while retrieving data.
     * @since BUGR-3706
     */
    public List<SimpleProjectContestData> getActiveContestsForUser(long userId) throws PersistenceException;
	
	/**
     * Gets active/draft contests for the given user, only active contests will be returned.
     * 
     * @param userId the id of the user.
     * @return the data stored in a list of SimpleProjectContestData instances.
     * @throws PersistenceException if any error occurs while retrieving data.
     * @since BUGR-3706
     */
    public List<SimpleProjectContestData> getActiveDraftContestsForUser(long userId) throws PersistenceException;
    
    /**
     * Gets the general feedback for software checkpoint submissions.
     * 
     * @param contestId the contest id
     * @return the general feedback, or null if there's no matching record in DB
     * @throws IllegalArgumentException if the argument is non-positive
     * @throws PersistenceException if any other error occurs
     * @since 1.2.2
     */
    public String getSoftwareCheckpointSubmissionsGeneralFeedback(long contestId) throws PersistenceException;
    
    /**
     * Saves the general feedback for software checkpoint submissions. If the feedback don't exist it will create
     * a new record, otherwise update it.
     * 
     * @param contestId the contest id
     * @param feedback the general feedback
     * @throws IllegalArgumentException if the argument contestId is non-positive
     * @throws PersistenceException if any other error occurs
     * @since 1.2.2
     */
    public void saveSoftwareCheckpointSubmissionsGeneralFeedback(long contestId, String feedback)
        throws PersistenceException;

    /**
     * Get all project groups
     *
     * @return array of all project group
     * @throws PersistenceException if any database related exception occur
     * @since 1.6
     */
    public ProjectGroup[] getAllProjectGroups() throws PersistenceException;

    /**
     * Create/Update MMM entry on informixoltp
     *
     * @param project Project
     * @param startDate start date
     * @param regEndDate registration end date
     * @param endDate end date
     * @param isNew true if this is new challenge
     * @param operator operator
     * @throws PersistenceException if any error occurs from underlying persistence.
     * @since 1.7
     */
    public void createOrUpdateMarathonMatch(Project project, Date startDate, Date regEndDate, Date endDate , boolean isNew, String operator) throws PersistenceException;
}
