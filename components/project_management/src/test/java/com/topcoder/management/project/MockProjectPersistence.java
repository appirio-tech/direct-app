/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.sql.Connection;
import java.util.List;

import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * This a mock class implements ProjectPersistence.
 *
 * @author iamajia, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
public class MockProjectPersistence implements ProjectPersistence {
    /**
     * do nothing.
     *
     * @param ns
     *            The namespace to load configuration settings from.
     */
    public MockProjectPersistence(String ns) {
    }

    /**
     * simply validate the input.
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
    public void createProject(Project project, String operator) throws PersistenceException {
        Helper.checkObjectNotNull(project, "project");
        Helper.checkStringNotNullOrEmpty(operator, "operator");
    }

    /**
     * simply validate the input.
     *
     * @param project
     *            The project instance to be updated into the database.
     * @param reason
     *            The update reason. It will be stored in the persistence for future references.
     * @param operator
     *            The modification user of this project.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public void updateProject(Project project, String reason, String operator) throws PersistenceException {
        Helper.checkObjectNotNull(project, "project");
        Helper.checkStringNotNullOrEmpty(reason, "reason");
        Helper.checkStringNotNullOrEmpty(operator, "operator");
    }

    /**
     * simply validate the input.
     *
     * @return The project instance.
     * @param id
     *            The id of the project to be retrieved.
     * @throws IllegalArgumentException
     *             if the input id is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project getProject(long id) throws PersistenceException {
        Helper.checkNumberPositive(id, "id");
        return null;
    }

    /**
     * simply validate the input.
     *
     * @param ids
     *            The ids of the projects to be retrieved.
     * @return An array of project instances.
     * @throws IllegalArgumentException
     *             if ids empty or contain an id that is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project[] getProjects(long[] ids) throws PersistenceException {
        Helper.checkObjectNotNull(ids, "ids");
        if (ids.length == 0) {
            throw new IllegalArgumentException("ids can not be empty.");
        }
        for (int i = 0; i < ids.length; i++) {
            Helper.checkNumberPositive(ids[i], "ids[" + i + "]");
        }
        Project[] projects = new Project[ids.length];
        for (int i = 0; i < ids.length; i++) {
            projects[i] = new Project(ids[i], getAllProjectCategories()[0], getAllProjectStatuses()[0]);
        }
        return projects;
    }

    /**
     * return a ProjectType array, it is used in the test.
     *
     * @return An array of all project types in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        return new ProjectType[]{new ProjectType(1, "type1"), new ProjectType(2, "type2")};
    }

    /**
     * return a ProjectCategory array.
     *
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        return new ProjectCategory[]{new ProjectCategory(1, "category1", getAllProjectTypes()[0]),
            new ProjectCategory(2, "category2", getAllProjectTypes()[1])};
    }

    /**
     * return a ProjectStatus array, it is used in the test.
     *
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        return new ProjectStatus[]{new ProjectStatus(1, "status1"), new ProjectStatus(2, "status2")};
    }

    /**
     * return a ProjectPropertyType array, it is used in the test.
     *
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        return new ProjectPropertyType[]{new ProjectPropertyType(1, "propertyType1"),
            new ProjectPropertyType(2, "propertyType2")};
    }

    public Project[] getProjects(CustomResultSet resultSet) throws PersistenceException {
        Project[] projects = new Project[4];
        for (int i = 0; i < 4; i++) {
            projects[i] = new Project(i + 1, getAllProjectCategories()[0], getAllProjectStatuses()[0]);
        }
        return projects;
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence whose create date is within current - days
     * </p>
     *
     * @param days
     *            last 'days'
     * @return An array of project instances.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project[] getProjectsByCreateDate(int days) throws PersistenceException {
        return null;
    }

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
    public Project[] getProjectsByDirectProjectId(long directProjectId) throws PersistenceException {
        Helper.checkNumberPositive(directProjectId, "directProjectId");
        if (directProjectId == 2) {
            throw new PersistenceException("The parameter 2 is not allowed!");
        }
        Project[] ret = {};
        return ret;
    }

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
    public FileType[] getProjectFileTypes(long projectId) throws PersistenceException {
        Helper.checkNumberPositive(projectId, "projectId");

        if (projectId == 2) {
            throw new PersistenceException("The parameter 2 is not allowed!");
        }
        return new FileType[]{};
    }

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
        throws PersistenceException {
        Helper.checkNumberPositive(projectId, "projectId");
        for (int i = 0; i < fileTypes.size(); i++) {
            Helper.checkObjectNotNull(fileTypes.get(i), "fileTypes[" + i + "]");
        }
        Helper.checkStringNotNullOrEmpty(operator, "operator");

        if (fileTypes.size() > 0) {
            fileTypes.get(0).setModificationUser(operator);
        }
        if (projectId == 2) {
            throw new PersistenceException("The parameter 2 is not allowed!");
        }
    }

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
    public Prize[] getProjectPrizes(long projectId) throws PersistenceException {
        Helper.checkNumberPositive(projectId, "projectId");

        if (projectId == 2) {
            throw new PersistenceException("The parameter 2 is not allowed!");
        }
        return new Prize[]{};
    }

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
     *             if projectId is non-positive, prizes contains null, or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public void updateProjectPrizes(long projectId, List<Prize> prizes, String operator) throws PersistenceException {
        Helper.checkNumberPositive(projectId, "projectId");
        for (int i = 0; i < prizes.size(); i++) {
            Helper.checkObjectNotNull(prizes.get(i), "prizes[" + i + "]");
        }
        Helper.checkStringNotNullOrEmpty(operator, "operator");
        if (prizes.size() > 0) {
            prizes.get(0).setModificationUser(operator);
        }
        if (projectId == 2) {
            throw new PersistenceException("The parameter 2 is not allowed!");
        }
    }

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
    public FileType createFileType(FileType fileType, String operator) throws PersistenceException {
        Helper.checkObjectNotNull(fileType, "fileType");
        Helper.checkStringNotNullOrEmpty(operator, "operator");

        if (operator.equals("t")) {
            throw new PersistenceException("The operator t is not allowed!");
        }
        fileType.setCreationUser(operator);

        return fileType;
    }

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
    public void updateFileType(FileType fileType, String operator) throws PersistenceException {
        Helper.checkObjectNotNull(fileType, "fileType");
        Helper.checkStringNotNullOrEmpty(operator, "operator");

        if (operator.equals("t")) {
            throw new PersistenceException("The operator t is not allowed!");
        }
        fileType.setModificationUser(operator);
    }

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
    public void removeFileType(FileType fileType, String operator) throws PersistenceException {
        Helper.checkObjectNotNull(fileType, "fileType");
        Helper.checkStringNotNullOrEmpty(operator, "operator");

        if (operator.equals("t")) {
            throw new PersistenceException("The operator t is not allowed!");
        }
    }

    /**
     * Gets all FileType entities.
     *
     * @return the found FileType entities, return empty if cannot find any.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public FileType[] getAllFileTypes() throws PersistenceException {
        return new FileType[]{};
    }

    /**
     * Gets all PrizeType entities.
     *
     * @return the found PrizeType entities, return empty if cannot find any.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public PrizeType[] getPrizeTypes() throws PersistenceException {
        return new PrizeType[]{};
    }

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
    public Prize createPrize(Prize prize, String operator) throws PersistenceException {
        Helper.checkObjectNotNull(prize, "prize");
        Helper.checkStringNotNullOrEmpty(operator, "operator");

        if (operator.equals("t")) {
            throw new PersistenceException("The operator t is not allowed!");
        }
        prize.setCreationUser(operator);

        return prize;
    }

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
    public void updatePrize(Prize prize, String operator) throws PersistenceException {
        Helper.checkObjectNotNull(prize, "prize");
        Helper.checkStringNotNullOrEmpty(operator, "operator");

        if (operator.equals("t")) {
            throw new PersistenceException("The operator t is not allowed!");
        }
        prize.setModificationUser(operator);;
    }

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
    public void removePrize(Prize prize, String operator) throws PersistenceException {
        Helper.checkObjectNotNull(prize, "prize");
        Helper.checkStringNotNullOrEmpty(operator, "operator");

        if (operator.equals("t")) {
            throw new PersistenceException("The operator t is not allowed!");
        }
    }

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
        throws PersistenceException {
        Helper.checkObjectNotNull(spec, "spec");
        Helper.checkStringNotNullOrEmpty(operator, "operator");

        if (operator.equals("t")) {
            throw new PersistenceException("The operator t is not allowed!");
        }

        spec.setCreationUser(operator);
        return spec;
    }

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
        throws PersistenceException {
        Helper.checkObjectNotNull(spec, "spec");
        Helper.checkStringNotNullOrEmpty(operator, "operator");

        if (operator.equals("t")) {
            throw new PersistenceException("The operator t is not allowed!");
        }

        spec.setModificationUser(operator);
    }

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
        throws PersistenceException {
        Helper.checkObjectNotNull(spec, "spec");
        Helper.checkStringNotNullOrEmpty(operator, "operator");

        if (operator.equals("t")) {
            throw new PersistenceException("The operator t is not allowed!");
        }
    }

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
    public ProjectStudioSpecification getProjectStudioSpecification(long projectId) throws PersistenceException {
        Helper.checkNumberPositive(projectId, "projectId");

        Connection conn = null;

        if (projectId == 2) {
            throw new PersistenceException("The parameter 2 is not allowed!");
        }

        return null;
    }

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
        throws PersistenceException {
        Helper.checkObjectNotNull(spec, "spec");
        Helper.checkNumberPositive(projectId, "projectId");
        Helper.checkStringNotNullOrEmpty(operator, "operator");

        if (projectId == 2) {
            throw new PersistenceException("The parameter 2 is not allowed!");
        }
    }
}
