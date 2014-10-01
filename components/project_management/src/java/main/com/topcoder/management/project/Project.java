/*
 * Copyright (C) 2006 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents a project from the persistence. Each project must
 * belong to a project category and must have a status. Project also have contains some properties. Projects are
 * stored in 'project' table, project category in 'project_category_lu' table,
 * project status in 'project_status_lu' table. project
 * properties are stored in 'project_info' table. This class is used by
 * ProjectPersistence implementors to store projects in the persistence.
 * </p>
 * 
 * <p>
 * Updated for Cockpit Launch Contest - Update for Spec Creation v1.0
 *      - added projectSpec attribute to this class.
 * </p>
 * 
 * <p>
 * Updated for Cockpit Release Assembly for Receitps 
 *      - added tcDirectProjectName field.
 * </p>
 * 
 * <p>
 * Changes in version 1.3 (TC Cockpit Post a Copilot Assembly 2):
 * <ul>
 * <li>Added projectCopilotTypes field.</li>
 * <li>Added copilotContestExtraInfos field.</li>
 * </ul>
 * </p>
 *
 * <p>
  * Changes in version 1.4 (Release Assembly - TopCoder Bug Hunt Assembly Integration 2):
  * <ul>
  * <li>Added autoCreationBugHunt field and getter/setter</li>
  * </ul>
  * </p>
 *
 * <p>
 * Changes in version 1.5 (Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match):
 * <ul>
 * <li>Added projectMMSpecification field.</li>
 * </ul>
 * </p>
 *
 * <p>
 *  Version 1.6 (Module Assembly - TC Cockpit Launch F2F contest) updates
 *  <ul>
 *      <li>Added property {@link #platforms} and its getter and setter</li>
 *  </ul>
 * </p>
 *
 * <p>
 *  Version 1.7 (Release Assembly - TC Cockpit Private Challenge Update)
 *  <ul>
 *      <li>Added property {@link #securityGroupId} and its getter and setter</li>
 *  </ul>
 * </p>
 *
 * <p>
 *  Version 1.8 (First2Finish - TC Cockpit Auto Assign Reviewer Update)
 *  <ul>
 *      <li>Added property {@link #autoAssignReviewerId}</li>
 *  </ul>
 * </p>
 * 
 * <p>
 * This class implements Serializable interface to support serialization.
 * </p>
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 *
 * @author tuenm, iamajia, duxiaoyang, bugbuka, GreatKevin
 * @version 1.9
 * @since 1.0
 */
@XmlType(name = "project", namespace = "com.topcoder.management.project")
public class Project extends AuditableObject implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the id of this instance. Only values greater than or equal to
     * zero is allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private long id = 0;

    /**
     * Represents the project category of this instance. Null values are not
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private ProjectCategory projectCategory = null;

    /**
     * Represents the project status of this instance. Null values are not
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private ProjectStatus projectStatus = null;

    /**
     * Represents the properties of this instance. Null values are not allowed.
     * This variable is initialized in the constructor and can be accessed in
     * the corresponding getter/setter/getAll method. The map key is property
     * name and map value is property value. Key is string type and value is
     * Object type.
     * 
     * Updated to generics for Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private Map<String, String> properties = new HashMap<String, String>();
    
    
    /**
     * Represents the prizes of Project. The default value is null. It's changeable. It could not contain null. It's
     * accessed in setter and getter.
     *
     * @since 1.2
     */
    private List<Prize> prizes;

    /**
     * Represents the platforms of Project. The default value is null. It's changeable. It could not contain null.
     * It's accessed in getter and setter.
     *
     * @since 1.6
     */
    private List<ProjectPlatform> platforms;
	
    /**
     *  it will be not zero if this project is associated with direct project     
     */    
    private long tcDirectProjectId;

    /**
     * The security group id.
     *
     * @since 1.7
     */
    private long securityGroupId;
    
    /**
     * Represents the projectStudioSpecification of Project. The default value is null. It's changeable. It could not be
     * null if project type is studio. It's accessed in setter and getter.
     *
     * @since 1.2
     */
    private ProjectStudioSpecification projectStudioSpecification;
    
    /**
     * Represents the projectMMSpecification of Project. The default value is null. It's changeable. It could not be
     * null if project type is studio. It's accessed in setter and getter.
     *
     * @since 1.5
     */
    private ProjectMMSpecification projectMMSpecification;
   
    /**
     * <p>
     * Represents the project spec for this Project instance.
     * </p>
     * 
     *  @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private ProjectSpec projectSpec;
    
    /**
     * Represents the projectFileTypes of Project. The default value is null. It's changeable. It could not contain
     * null. It's accessed in setter and getter.
     *
     * @since 1.2
     */
    private List<FileType> projectFileTypes;
    /**
     * The TC Direct Project Name
     * 
     * @since Cockpit Release Assembly for Receipts
     */
    private String tcDirectProjectName;

    /**
     * The creator
     * 
     * @since 
     */
    private String creator;

     /**
     * whether it is dev only
     */
    private boolean devOnly;

    /**
     * Whether the contest is an auto created bug hunt.
     * @since 1.4
     */
    private boolean autoCreationBugHunt;

    /**
     * The user id of the reviewer who is auto assigned to the contest.
     * @since 1.8
     */
    private long autoAssignReviewerId;

    /**
     * Represents the selected project copilot types. It is valid only for copilot posting contest.
     * @since 1.3
     */
    private List<ProjectCopilotType> projectCopilotTypes;

    /**
     * Represents the copilot contest extra info. It is valid only for copilot posting contest.
     * @since 1.3
     */
    private List<CopilotContestExtraInfo> copilotContestExtraInfos;

     /**
     * <p>
     * Create a new Project instance with the given project type and project
     * status. These fields are required for a project to be created. Project id
     * will be zero which indicates that the project instance is not yet created
     * in the database.
     * </p>
     *
     */
    public Project() {
    }

    /**
     * <p>
     * Create a new Project instance with the given project type and project
     * status. These fields are required for a project to be created. Project id
     * will be zero which indicates that the project instance is not yet created
     * in the database.
     * </p>
     *
     * @param projectCategory
     *            The project category instance of this project.
     * @param projectStatus
     *            The project status instance of this project.
     * @throws IllegalArgumentException
     *             If any parameter is null.
     */
    public Project(ProjectCategory projectCategory, ProjectStatus projectStatus) {
        this(0, projectCategory, projectStatus);
    }

    /**
     * Create a new Project instance with the given project id, project type and
     * project status. This method is supposed to use by persistence
     * implementation to load project from the persistence when the project id
     * is already set.
     *
     * @param projectId
     *            The project id.
     * @param projectCategory
     *            The project category instance of this project.
     * @param projectStatus
     *            The project status instance of this project.
     * @throws IllegalArgumentException
     *             If id is less than zero, or any parameter is null.
     */
    public Project(long projectId, ProjectCategory projectCategory, ProjectStatus projectStatus) {
        this(projectId, projectCategory, projectStatus, new HashMap<String, String>());
    }

    /**
     * Create a new Project instance with the given project id, project type id,
     * project status id and the associated properties. The input properties map
     * should contains String/Object as key/value pairs. This method is supposed
     * to use by persistence implementation to load project from the persistence
     * when the project id is already set.
     *
     * @param projectId
     *            The project id.
     * @param projectCategory
     *            The project category instance of this project.
     * @param projectStatus
     *            The project status instance of this project.
     * @param properties
     *            A map of properties of this project.
     * @throws IllegalArgumentException
     *             If id is less than zero, or any parameter is null. Or if in
     *             the 'properties' map, the key/value type is not
     *             String/Object, Or key is null or empty, Object is null.
     */
    public Project(long projectId, ProjectCategory projectCategory, ProjectStatus projectStatus, Map<String, String> properties) {
        if (projectId < 0) {
            throw new IllegalArgumentException("id can not less than zero.");
        }
        this.id = projectId;
        setProjectCategory(projectCategory);
        setProjectStatus(projectStatus);
        Helper.checkObjectNotNull(properties, "properties");
        // check all entry in properties.
        for (Iterator<Map.Entry<String, String>> iter = properties.entrySet().iterator(); iter.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
            if (!(entry.getKey() instanceof String)) {
                throw new IllegalArgumentException("properties contains some key that is not String.");
            }
            Helper.checkStringNotNullOrEmpty((String) entry.getKey(), "properties's key");
            Helper.checkObjectNotNull(entry.getValue(), "properties's value");
        }
        this.properties = new HashMap<String, String>(properties);
    }

    /**
     * Sets the id for this project instance. Only positive values are allowed.
     *
     * @param id
     *            The id of this project instance.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the id of this project instance.
     *
     * @return the id of this project instance.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the project status for this project instance. Null value is not allowed.
     *
     * @param projectStatus
     *            The project status instance to set.
     */
    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    /**
     * Gets the project status of this project instance.
     *
     * @return The project status of this project instance.
     */
    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    /**
     * Sets the project category for this project instance. Null value is not
     * allowed.
     *
     * @param projectCategory
     *            The project category instance to set.
     */
    public void setProjectCategory(ProjectCategory projectCategory) {
        this.projectCategory = projectCategory;
    }

    /**
     * Gets the project category of this project instance.
     *
     * @return The project category of this project instance.
     */
    public ProjectCategory getProjectCategory() {
        return projectCategory;
    }

    /**
     * Sets a property for this project instance. If the property name already
     * exist, its value will be updated. Otherwise, the property will be added
     * to the project instance. If value parameter is null, the property will be
     * removed from the project instance. Value of the property will be saved as
     * string in the persistence using toString() method. Project property value
     * is stored in 'project_info' table, while its name is stored in
     * 'project_info_type_lu' table.
     *
     * @param name
     *            The property name.
     * @param value
     *            The property value.
     * @throws IllegalArgumentException
     *             If name is null or empty string.
     */
    public void setProperty(String name, String value) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        if (value == null) {
            properties.remove(name);
        } else {
            properties.put(name, value);
        }
    }

    /**
     * Gets a property for this project instance. If the property name does not
     * exist in this instance, null value is returned.
     *
     * @return The property value, or null if the property does not exist.
     * @param name
     *            The property name.
     * @throws IllegalArgumentException
     *             If name is null or empty string.
     */
    public String getProperty(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        return properties.get(name);
    }

    /**
     * Gets a map of property name/value pairs. If there is no property in this
     * project instance, an empty map is returned.
     *
     * @return A map of property name/value pairs. or an empty map if there is
     *         no property in this project instance.
     */
    public Map<String, String> getAllProperties() {
        return new HashMap<String, String>(properties);
    }

    /**
     * Returns the value of prizes attribute.
     *
     * @return the value of prizes.
     * @since 1.2
     */
    public List<Prize> getPrizes() {
        return this.prizes;
    }
	
    /**
     * sets a direct project id  for this project instance if this project 
     * associated with direct project
     * @return
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }
    /**
     * Sets the given value to prizes attribute.
     *
     * @param prizes
     *            the given value to set.
     * @throws IllegalArgumentException if the parameter contains null
     * @since 1.2
     */
    public void setPrizes(List<Prize> prizes) {
        if (null != prizes) {
            if (prizes.contains(null)) {
                throw new IllegalArgumentException("The prizes can not contain null.");
            }
        }
        this.prizes = prizes;
    }

    /**
     * Gets the platforms of the project.
     *
     * @return the list of project platforms.
     * @since 1.6
     */
    public List<ProjectPlatform> getPlatforms() {
        if(platforms == null) {
            platforms = new ArrayList<ProjectPlatform>();
        }

        return platforms;
    }

    /**
     * Sets the platforms of the project.
     *
     * @param platforms the list of project platforms.
     * @since 1.6
     */
    public void setPlatforms(List<ProjectPlatform> platforms) {
        if (null != platforms) {
            if (platforms.contains(null)) {
                throw new IllegalArgumentException("The project platforms can not contain null.");
            }
        }
        this.platforms = platforms;
    }

    /**
     * <p>
     * Gets the properties map for this project.
     * </p>
     * 
     * @return the properties
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public Map<String, String> getProperties() {
        return this.properties;
    }

    /**
     * <p>
     * Sets the properties map for this project.
     * </p>
     * 
     * @param properties the properties to set
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * Returns the value of projectStudioSpecification attribute.
     *
     * @return the value of projectStudioSpecification.
     * @since 1.2
     */
    public ProjectStudioSpecification getProjectStudioSpecification() {
        return this.projectStudioSpecification;
    }
	
    /**
     * Returns the value of projectMMSpecification attribute.
     *
     * @return the value of projectMMSpecification.
     * @since 1.5
     */
    public ProjectMMSpecification getProjectMMSpecification() {
        return projectMMSpecification;
    }

    /**
     * <p>
     * Gets the ProjectSpec for this Project instance.
     * </p>
     * 
     * @return the ProjectSpec for this Project instance
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    public ProjectSpec getProjectSpec() {
        return projectSpec;
    }

    /**
     * Sets the given value to projectStudioSpecification attribute.
     *
     * @param projectStudioSpecification
     *            the given value to set.
     * @throws IllegalArgumentException
     *             if the project type is Studio and the parameter is null.
     * @since 1.2
     */
    public void setProjectStudioSpecification(ProjectStudioSpecification projectStudioSpecification) {
        if (projectCategory != null && projectCategory.getProjectType() == ProjectType.STUDIO) {
            Helper.checkObjectNotNull(projectStudioSpecification, "projectStudioSpecification");
        }
        this.projectStudioSpecification = projectStudioSpecification;
    }
	
    /**
     * Sets the given value to projectMMSpecification attribute.
     *
     * @param projectMMSpecification
     *            the given value to set.
     * @throws IllegalArgumentException
     *             if the project category is 37 (Marathon Match) and the parameter is null.
     * @since 1.5
     */
    public void setProjectMMSpecification(ProjectMMSpecification projectMMSpecification) {
        if (projectCategory != null && projectCategory.getId() == 37) {
            Helper.checkObjectNotNull(projectMMSpecification, "projectMMSpecification");
        }
        this.projectMMSpecification = projectMMSpecification;
    }

    /**
     * <p>
     * Sets the ProjectSpec for this Project instance.
     * </p>
     * 
     * @param projectSpec
     *            the ProjectSpec for this Project instance
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    public void setProjectSpec(ProjectSpec projectSpec) {
        this.projectSpec = projectSpec;
    }

    /**
     * Returns the value of projectFileTypes attribute.
     *
     * @return the value of projectFileTypes.
     * @since 1.2
     */
    public List<FileType> getProjectFileTypes() {
        return this.projectFileTypes;
    }
	
    /**
     * Gets the tc direct project name.
     * 
     * @return the tc direct project name.
     * @since Cockpit Release Assembly for Receipts
     */
    public String getTcDirectProjectName() {
        return this.tcDirectProjectName;
    }

    /**
     * Sets the given value to projectFileTypes attribute.
     *
     * @param projectFileTypes
     *            the given value to set.
     * @throws IllegalArgumentException if the parameter contains null
     * @since 1.2
     */
    public void setProjectFileTypes(List<FileType> projectFileTypes) {
        if (null != projectFileTypes) {
            if (projectFileTypes.contains(null)) {
                throw new IllegalArgumentException("The projectFileTypes can not contain null.");
            }
        }
        this.projectFileTypes = projectFileTypes;
    }
    /**
     * Sets the tc direct project name.
     * 
     * @param tcDirectProjectName the tc direct project name.
     * @since Cockpit Release Assembly for Receipts
     */
    public void setTcDirectProjectName(String tcDirectProjectName) {
        this.tcDirectProjectName = tcDirectProjectName;
    }
	
    /**
     * Gets the dev only
     * 
     * @return dev only
     */
    public boolean isDevOnly()
    {
        return devOnly;
    }

    /**
     * Sets the given value to tcDirectProjectId attribute.
     *
     * @param tcDirectProjectId
     *            the directProjectId to set
     * @since 1.2
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

     /**
     * Sets the dev only
     * 
     * @param devOnly the devOnly.
     */
    public void setDevOnly(boolean devOnly)
    {
        this.devOnly = devOnly;
    }

    /**
     * <p>
     * Gets the project copilot types used for copilot posting contest.
     * </p>
     * @return the project copilot types used for copilot posting contest.
     */
    public List<ProjectCopilotType> getProjectCopilotTypes() {
        return projectCopilotTypes;
    }

    /**
     * <p>
     * Sets the project copilot types used for copilot posting contest.
     * </p>
     * @param projectCopilotTypes
     *            the project copilot types to set.
     */
    public void setProjectCopilotTypes(List<ProjectCopilotType> projectCopilotTypes) {
        this.projectCopilotTypes = projectCopilotTypes;
    }

    /**
     * <p>
     * Gets the copilot contest extra info for copilot posting contest.
     * </p>
     * @return the copilot contest extra info used for copilot posting contest.
     */
    public List<CopilotContestExtraInfo> getCopilotContestExtraInfos() {
        return copilotContestExtraInfos;
    }

    /**
     * <p>
     * Sets the copilot contest extra info for copilot posting contest.
     * </p>
     * @param copilotContestExtraInfos
     *            the copilot contest extra info to set.
     */
    public void setCopilotContestExtraInfos(List<CopilotContestExtraInfo> copilotContestExtraInfos) {
        this.copilotContestExtraInfos = copilotContestExtraInfos;
    }

    /**
     * Gets the auto creation bug hunt flag.
     *
     * @return the auto creation bug hunt flag
     * @since 1.4
     */
    public boolean isAutoCreationBugHunt() {
        return autoCreationBugHunt;
    }

    /**
     * Sets the auto creation bug hunt flag
     *
     * @param autoCreationBugHunt the auto creation bug hunt flag
     * @since 1.4
     */
    public void setAutoCreationBugHunt(boolean autoCreationBugHunt) {
        this.autoCreationBugHunt = autoCreationBugHunt;
    }

    /**
     * Gets the security group id.
     *
     * @return the security group id.
     * @since 1.7
     */
    public long getSecurityGroupId() {
        return securityGroupId;
    }

    /**
     * Sets the security group id.
     *
     * @param securityGroupId the security group id.
     * @since 1.7
     */
    public void setSecurityGroupId(long securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    /**
     * Gets the auto assigned reviewer user id.
     *
     * @return the auto assigned reviewer user id.
     * @since 1.8
     */
    public long getAutoAssignReviewerId() {
        return autoAssignReviewerId;
    }

    /**
     * Gets the auto assigned reviewer user id.
     *
     * @param autoAssignReviewerId the auto assigned reviewer user id.
     * @since 1.8
     */
    public void setAutoAssignReviewerId(long autoAssignReviewerId) {
        this.autoAssignReviewerId = autoAssignReviewerId;
    }

    /**
     * Gets the creator
     * 
     * @return the creator
     * @since 1.9
     */
    public String getCreator() {
        return this.creator;
    }

   
    /**
     * Sets creator
     * 
     * @param creator
     * @since 1.9
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }
}
