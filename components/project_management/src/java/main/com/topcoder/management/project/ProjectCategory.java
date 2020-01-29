/*
 * Copyright (C) 2006 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class represents a project category from the persistence. Each project
 * category must belong to a project type. Project type are stored in
 * 'project_type_lu' table, project category in 'project_category_lu'. A project
 * category instance contains id, name and description and a reference to
 * project type. This class is used in Project class to specify the project
 * category of a project. This class implements Serializable interface to
 * support serialization.
 * </p>
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 *
 * <p>
 * Version 1.0.1 (Direct Copilot Posting Launching Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #COPILOT_POSTING} constant.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (Module Assembly - TC Cockpit Project Contests Batch Edit) changes:
 * <ul>
 *     <li>Add static map to store the mapping of project category id to <code>ProjectCategory</code> instance</li>
 *     <li>Add static method {@link #getProjectCategoryById(long)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.4 (Module Assembly - TC Cockpit Launch Code contest)
 * <ul>
 *     <li>Added new static project Category {@link #CODE}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.5 (Module Assembly - TC Cockpit Launch F2F contest)
 * <ul>
 *     <li>Added new static project Category {@link #FIRST2FINISH}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6 (Module Assembly - TC Direct Studio Design First2Finish Challenge Type)
 * <ul>
 *     <li>Added {@link #DESIGN_FIRST2FINISH}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.7 (TopCoder Direct - Review Cost Calculation Quick Updates) @author GreatKevin @challenge 30044580
 * <ul>
 *     <li>Put DESIGN_FIRST2FINISH into PROJECT_CATEGORIES</li>
 * </ul>
 * </p>
 *
 * @author tuenm, iamajia, GreatKevin
 * @version 1.7
 */
public class ProjectCategory implements Serializable {
    /**
     * Represents Web Page Design project category.
     *
     * @since 1.2
     */
    public static final ProjectCategory WEB_DESIGN = new ProjectCategory(17, "Web Design", ProjectType.STUDIO);

    public static final long PROJECT_CATEGORY_SPEC_REVIEW = 27;

    public static final ProjectCategory DESIGN = new ProjectCategory(1, "Design", ProjectType.COMPONENT);

    /**
     * Represents Logo Design project category.
     *
     * @since 1.2
     */
    public static final ProjectCategory LOGO_DESIGN = new ProjectCategory(20, "Logo Design", ProjectType.STUDIO);
    
	public static final ProjectCategory DEVELOPMENT = new ProjectCategory(2, "Development", ProjectType.COMPONENT);

    /**
     * Represents Banners and Icons project category.
     *
     * @since 1.2
     */
    public static final ProjectCategory BANNERS_ICONS = new ProjectCategory(16, "Banners/Icons", ProjectType.STUDIO);
    
	public static final ProjectCategory ARCHITECTURE = new ProjectCategory(7, "Architecture", ProjectType.APPLICATION);

    public static final ProjectCategory TEST_SUITES = new ProjectCategory(13, "Test Suites", ProjectType.APPLICATION);

    /**
     * Represents Application Front End Design project category.
     *
     * @since 1.2
     */
    public static final ProjectCategory APPLICATION_FRONT_END_DESIGN = new ProjectCategory(32,
        "Application Front End Design", ProjectType.STUDIO);
    
	public static final ProjectCategory ASSEMBLY = new ProjectCategory(14, "Assembly Competition", ProjectType.APPLICATION);

    public static final ProjectCategory UI_PROTOTYPES = new ProjectCategory(19, "UI Prototypes", ProjectType.APPLICATION);

    /**
     * Represents Widget or Mobile Screen project category.
     *
     * @since 1.2
     */
    public static final ProjectCategory WIDGET_OR_MOBILE_SCREEN_DESIGN = new ProjectCategory(30,
        "Widget or Mobile Screen Design", ProjectType.STUDIO);
    
	public static final ProjectCategory SPECIFICATION = new ProjectCategory(6, "Specification", ProjectType.APPLICATION);

    /**
     * Represents Front End Flash project category.
     *
     * @since 1.2
     */
    public static final ProjectCategory FRONT_END_FLASH
        = new ProjectCategory(31, "Front End Flash", ProjectType.STUDIO);
    
	public static final ProjectCategory CONCEPTUALIZATION = new ProjectCategory(23, "Conceptualization", ProjectType.APPLICATION);

    /**
     * Represents Print or Presentation project category.
     *
     * @since 1.2
     */
    public static final ProjectCategory PRINT_OR_PRESENTATION = new ProjectCategory(21, "Print/Presentation",
        ProjectType.STUDIO);
    
	public static final ProjectCategory RIA_BUILD = new ProjectCategory(24, "RIA Build", ProjectType.APPLICATION);

    /**
     * Represents Other project category.
     *
     * @since 1.2
     */
    public static final ProjectCategory OTHER = new ProjectCategory(34, "Other", ProjectType.STUDIO);
    
	public static final ProjectCategory RIA_COMPONENT = new ProjectCategory(25, "RIA Component", ProjectType.APPLICATION);

    /**
     * Represents Wireframes project category.
     *
     * @since 1.2
     */
    public static final ProjectCategory WIREFRAMES = new ProjectCategory(18, "Wireframes", ProjectType.STUDIO);
    
	public static final ProjectCategory TEST_SCENARIOS = new ProjectCategory(26, "Test Scenarios", ProjectType.APPLICATION);

    /**
     * Represents Idea Generation project category.
     *
     * @since 1.2
     */
    public static final ProjectCategory IDEA_GENERATION
        = new ProjectCategory(22, "Idea Generation", ProjectType.STUDIO);
    
	public static final ProjectCategory GENERIC_SCORECARDS = new ProjectCategory(28, "Generic Scorecards", ProjectType.GENERIC);
    
	/**
     * Represents copilot posting category.
     *
     * @since 1.2
     */
    public static final ProjectCategory COPILOT_POSTING = new ProjectCategory(29, "Copilot Posting", ProjectType.APPLICATION);
    
    public static final ProjectCategory CONTENT_CREATION = new ProjectCategory(35, "Content Creation", ProjectType.APPLICATION);

    public static final ProjectCategory REPORTING = new ProjectCategory(36, "REPORTING", ProjectType.APPLICATION);
    
    public static final ProjectCategory BUG_HUNT = new ProjectCategory(9, "Bug Hunt", ProjectType.APPLICATION);
  
    /**
     * The project category for First2Finish.
     *
     * @since 1.5
     */
    public static final ProjectCategory FIRST2FINISH = new ProjectCategory(38, "First2Finish", ProjectType.APPLICATION);

    /**
     * The project category for Code.
     *
     * @since 1.4
     */
    public static final ProjectCategory CODE = new ProjectCategory(39, "Code", ProjectType.APPLICATION);

    public static final ProjectCategory MARATHON_MATCH = new ProjectCategory(37, "Marathon Match", ProjectType.APPLICATION);

    /**
     * The project category for Design First2Finish
     *
     * @since 1.6
     */
    public static final ProjectCategory DESIGN_FIRST2FINISH = new ProjectCategory(40, "Design First2Finish", ProjectType.STUDIO);

    /**
     * The project category for Design First2Finish
     *
     * @since 1.6
     */
    public static final ProjectCategory AUTOMATED_TESTING = new ProjectCategory(41, "Automated Testing", ProjectType.APPLICATION);

    /**
     * Map to store the mapping of project category id to <code>ProjectCategory</code> instance.
     *
     * @since 1.9
     */
    private static final Map<Long, ProjectCategory> PROJECT_CATEGORIES = new HashMap<Long, ProjectCategory>();


    /**
     * Static initializer
     *
     * @since 1.9
     */
    static {
        PROJECT_CATEGORIES.put(DESIGN.getId(), DESIGN);
        PROJECT_CATEGORIES.put(DEVELOPMENT.getId(), DEVELOPMENT);
        PROJECT_CATEGORIES.put(SPECIFICATION.getId(), SPECIFICATION);
        PROJECT_CATEGORIES.put(ARCHITECTURE.getId(), ARCHITECTURE);
        PROJECT_CATEGORIES.put(BUG_HUNT.getId(), BUG_HUNT);
        PROJECT_CATEGORIES.put(TEST_SUITES.getId(), TEST_SUITES);
        PROJECT_CATEGORIES.put(ASSEMBLY.getId(), ASSEMBLY);
        PROJECT_CATEGORIES.put(BANNERS_ICONS.getId(), BANNERS_ICONS);
        PROJECT_CATEGORIES.put(WEB_DESIGN.getId(), WEB_DESIGN);
        PROJECT_CATEGORIES.put(WIREFRAMES.getId(), WIREFRAMES);
        PROJECT_CATEGORIES.put(UI_PROTOTYPES.getId(), UI_PROTOTYPES);
        PROJECT_CATEGORIES.put(LOGO_DESIGN.getId(), LOGO_DESIGN);
        PROJECT_CATEGORIES.put(PRINT_OR_PRESENTATION.getId(), PRINT_OR_PRESENTATION);
        PROJECT_CATEGORIES.put(IDEA_GENERATION.getId(), IDEA_GENERATION);
        PROJECT_CATEGORIES.put(CONCEPTUALIZATION.getId(), CONCEPTUALIZATION);
        PROJECT_CATEGORIES.put(RIA_BUILD.getId(), RIA_BUILD);
        PROJECT_CATEGORIES.put(RIA_COMPONENT.getId(), RIA_COMPONENT);
        PROJECT_CATEGORIES.put(TEST_SCENARIOS.getId(), TEST_SCENARIOS);
        PROJECT_CATEGORIES.put(COPILOT_POSTING.getId(), COPILOT_POSTING);
        PROJECT_CATEGORIES.put(WIDGET_OR_MOBILE_SCREEN_DESIGN.getId(), WIDGET_OR_MOBILE_SCREEN_DESIGN);
        PROJECT_CATEGORIES.put(FRONT_END_FLASH.getId(), FRONT_END_FLASH);
        PROJECT_CATEGORIES.put(APPLICATION_FRONT_END_DESIGN.getId(), APPLICATION_FRONT_END_DESIGN);
        PROJECT_CATEGORIES.put(OTHER.getId(), OTHER);
        PROJECT_CATEGORIES.put(CONTENT_CREATION.getId(), CONTENT_CREATION);
        PROJECT_CATEGORIES.put(REPORTING.getId(), REPORTING);
        PROJECT_CATEGORIES.put(FIRST2FINISH.getId(), FIRST2FINISH);
        PROJECT_CATEGORIES.put(DESIGN_FIRST2FINISH.getId(), DESIGN_FIRST2FINISH);
        PROJECT_CATEGORIES.put(CODE.getId(), CODE);
        PROJECT_CATEGORIES.put(MARATHON_MATCH.getId(), MARATHON_MATCH);
        PROJECT_CATEGORIES.put(AUTOMATED_TESTING.getId(), AUTOMATED_TESTING);
    }

    /**
     * Gets the <code>ProjectCategory</code> instance by project category id.
     *
     * @param projectCategoryId the id of the project category
     * @return the <code>ProjectCategory</code>
     * @since 1.9
     */
    public static ProjectCategory getProjectCategoryById(long projectCategoryId) {
        return PROJECT_CATEGORIES.get(projectCategoryId);
    }

    /**
     * Represents the id of this instance. Only values greater than zero is
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private long id = 0;

    /**
     * Represents the name of this instance. Null or empty values are not
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private String name = null;

    /**
     * Represents the description of this instance. Null value is not allowed.
     * This variable is initialized in the constructor and can be accessed in
     * the corresponding getter/setter method.
     */
    private String description = null;

    /**
     * The project type instance associated with this instance. Null value is
     * not allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private ProjectType projectType = null;


    /**
     * Create a new ProjectCategory instance with the given id and name. The two
     * fields are required for a this instance to be persisted.
     *
     * @param id
     *            The project category id.
     * @param name
     *            The project category name.
     * @param projectType
     *            The project type of this instance.
     * @throws IllegalArgumentException
     *             If id is less than or equals to zero, any parameter is null
     *             or name is empty string.
     */
    public ProjectCategory(long id, String name, ProjectType projectType) {
        this(id, name, "", projectType);
    }

	/**
     * Create a new ProjectCategory instance with the given id and name. The two
     * fields are required for a this instance to be persisted.
     */
    public ProjectCategory() {
        
    }

    /**
     * Create a new ProjectCategory instance with the given id, name and
     * description. The two first fields are required for a this instance to be
     * persisted.
     *
     * @param id
     *            The project category id.
     * @param name
     *            The project category name.
     * @param description
     *            The project category description.
     * @param projectType
     *            The project type of this instance.
     * @throws IllegalArgumentException
     *             If id is less than or equals to zero, any parameter is null
     *             or name is empty string.
     */
    public ProjectCategory(long id, String name, String description, ProjectType projectType) {
        setId(id);
        setName(name);
        setDescription(description);
        setProjectType(projectType);
    }

    /**
     * Sets the id for this project category instance. Only positive values are
     * allowed.
     *
     * @param id
     *            The id of this project category instance.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the id of this project category instance.
     *
     * @return the id of this project category instance.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the name for this project category instance. Null or empty values
     * are not allowed.
     *
     * @param name
     *            The name of this project category instance.
     * @throws IllegalArgumentException
     *             If project category name is null or empty string.
     */
    public void setName(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        this.name = name;
    }

    /**
     * Gets the name of this project category instance.
     *
     * @return the name of this project category instance.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the description for this project category instance. Null value are
     * not allowed.
     *
     * @param description
     *            The description of this project category instance.
     * @throws IllegalArgumentException
     *             If project category description is null.
     */
    public void setDescription(String description) {
        Helper.checkObjectNotNull(description, "description");
        this.description = description;
    }

    /**
     * Gets the description of this project category instance.
     *
     * @return the description of this project category instance.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the project type for this project category instance. Null value is
     * not allowed.
     *
     * @param projectType
     *            The project type instance to set.
     * @throws IllegalArgumentException
     *             If input is null.
     */
    public void setProjectType(ProjectType projectType) {
        Helper.checkObjectNotNull(projectType, "projectType");
        this.projectType = projectType;
    }

    /**
     * Gets the project type of this project category instance.
     *
     * @return The project type of this project category instance.
     */
    public ProjectType getProjectType() {
        return projectType;
    }
}
