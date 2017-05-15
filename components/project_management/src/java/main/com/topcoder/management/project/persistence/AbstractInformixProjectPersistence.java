/*
 * Copyright (C) 2007 - 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.project.BillingProjectConfigType;
import com.topcoder.management.project.BillingProjectConfiguration;
import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.ContestSale;
import com.topcoder.management.project.CopilotContestExtraInfo;
import com.topcoder.management.project.CopilotContestExtraInfoType;
import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.project.FileType;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.PrizeType;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectCopilotType;
import com.topcoder.management.project.ProjectMMSpecification;
import com.topcoder.management.project.ProjectPersistence;
import com.topcoder.management.project.ProjectPlatform;
import com.topcoder.management.project.ProjectGroup;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectSpec;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectStudioSpecification;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.SaleStatus;
import com.topcoder.management.project.SaleType;
import com.topcoder.management.project.SimplePipelineData;
import com.topcoder.management.project.SimpleProjectContestData;
import com.topcoder.management.project.SimpleProjectPermissionData;
import com.topcoder.management.project.SoftwareCapacityData;
import com.topcoder.management.project.persistence.Helper.DataType;
import com.topcoder.management.project.persistence.logging.LogMessage;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;
import com.topcoder.util.sql.databaseabstraction.NullColumnValueException;

/**
 * <p>
 * This class contains operations to create/update/retrieve project instances
 * from the Informix database. It also provides methods to retrieve database
 * look up table values. It implements the ProjectPersistence interface to
 * provide a plug-in persistence for Informix database. It is used by the
 * ProjectManagerImpl class. The constructor takes a namespace parameter to
 * initialize database connection.
 * </p>
 * <p>
 * Note that in this class, delete operation is not supported. To delete a
 * project, its status is set to 'Deleted'. Create and update operations here
 * work on the project and its related items as well. It means creating/updating
 * a project would involve creating/updating its properties.
 * </p>
 * <p>
 * This abstract class does not manage the connection itself. It contains three
 * abstract methods which should be implemented by concrete subclass to manage
 * the connection:
 * <ul>
 * <li><code>openConnection()</code> is used to acquire and open the
 * connection.</li>
 * <li><code>closeConnection()</code> is used to dispose the connection if
 * the queries are executed successfully.</li>
 * <li><code>closeConnectionOnError()</code> is used to handle the error if
 * the SQL operation fails.</li>
 * </ul>
 * The transaction handling logic should be implemented in subclasses while the
 * <code>Statement</code>s and <code>ResultSet</code>s are handled in this
 * abstract class.
 * </p>
 * 
 * <p>
 * Module Contest Service Software Contest Sales Assembly change: new methods added to support creating/updating/query contest
 * sale for software contest.
 * </p>
 *
 * <p>
 * Updated for Cockpit Launch Contest - Update for Spec Creation v1.0
 *      - added persist for project_spec.
 * </p>
 * 
 * Version 1.1.1 (Spec Reviews Finishing Touch v1.0) Change Notes:
 *  - Changed the way now spec status is queried.
 * <p>
 * Version 1.1.2 (Cockpit Pipeline Release Assembly 1 v1.0) Change Notes:
 *  - Introduced method to retrieve SimplePipelineData for given date range.
 * </p>
 * <p>
 * Version 1.1.3 (Cockpit Release Assembly 6) Change Notes:
 *  - Defined project property for confidentiality type.
 *  - Defined values for confidentiality types.
 *  - Depending on the confidentiality type now the project role terms are stored in different ways.
 * </p>
 * <p>
 * Version 1.2 (Cockpit Pipeline Release Assembly 2 - Capacity) changelog:
 *     - added service that retrieves a list of capacity data (date, number of scheduled contests) starting from
 *       tomorrow for a given contest type
 * </p>
 * <p>
 * Version 1.2.1 Cockpit Release Assembly 10
 *     - Change three getSimpleProjectContestData methods, to include the submission_end_date
 * </p>
 * <p>
 * Changes in v1.2.2 - Cockpit Release Assembly 11
 * Add method getDesignComponents to get design components.
 * </p>
 * <p>
 * Version 1.2.3 (Cockpit Contest Eligibility) changelog:
 *     - Create terms for private contest
 * </p>
 * <p>
 * Version 1.2.5 (Cockpit Spec Review - Stage 2 v1.0) changelog:
 *    - update the getSimpleProjectContestData methods for spec review backend update.
 * </p>

 * <p>
 * Version 1.2.4 (Cockpit Pipeline Manager Widget Release Assembly V1.0) Change Notes:
 * - streamline the software contest's status
 * - Detail visits https://www.topcoder.com/bugs/browse/TCCC-1504
 * </p>
 * <p>
 * Thread Safety: This class is thread safe because it is immutable.
 * </p>
 * 
 * [BUGR-2038]: the new logic for returned contest 'status' will be
 * if status is 'active' in db, and there is a row in contest_sale, then returned/shown status will be 'Scheduled',
 * if status is 'active' in db, and there is no row in contest_sale, then returned/shwon status will be 'Draft",
 * otherwise, show 'status' from db. 
 * 
 *  [ Cockpit Release Assembly - Contest Repost and New Version v1.0]
 *  update the query used in the method getDevelopmentContestId, to remove the constraint on the project status.
 *
 *  <p>
 *  Version 1.3 - Direct Search Assembly
 *  - add payment information into getSimpleProjectContestData functions.
 *  </p>
 *
 *  <p>
 *  Version 1.3.1 - Direct Pipeline Integration Assembly
 *  - updated {@link #getSimplePipelineData(long, Date, Date, boolean)} method to return details for copilots.
 *  </p>
 *  <p>
 *  Version 1.3.2 - Launch Copilot Selection Contest assembly 1.0
 *  - add private description support in project spec operation
 *  </p>
 *  <p>
 *  Version 1.3.3 - TC Direct Replatforming Release 1
 *  - Add support for ProjectStudioSpecification.contestIntroduction and ProjectStudioSpecification.contestDescription fields.
 *  - Update {@link #createOrUpdateProjectFileTypes(long, List, Connection, String, boolean)} to let fileTypes can be null.
 *  - Update {@link #createOrUpdateProjectPrizes(long, List, Connection, String, boolean) to update the logic for updating prizes.
 *  </p>
 *  <p>
 *  Version 1.3.4 - TC Direct Replatforming Release 3
 *  - Update {@link #getProjectStudioSpecification(long)} and {@link #createProjectStudioSpecification(ProjectStudioSpecification, String)
 *  and {@link #updateProjectStudioSpecification(ProjectStudioSpecification, String)} methods to support the new generalFeedback field.
 *  </p>
 *
 * <p>
 * Changes in version 1.4 (TC Cockpit Post a Copilot Assembly 2):
 * <ul>
 * <li>Added {@link #createOrUpdateProjectCopilotTypes(long, List, Connection, String, boolean)} and
 * {@link #createOrUpdateCopilotContestExtraInfos(long, List, Connection, String, boolean)} to persist related data into
 * database.</li>
 * <li>Added {@link #getProjectCopilotTypes(long)} and {@link #getCopilotContestExtraInfos(long)} to retrieve related
 * data from database.</li>
 * <li>Modified {@link #createProject(Project, String)} and {@link #updateProject(Project, String, String)} to
 * create/update copilot type and extra info for copilot posting contest.</li>
 * <li>Modified {@link #getProjects(long[], Connection)} to retrieve copilot posting contest related data.</li>
 * </ul>
 * </p>

 * <p>
 * Version 1.2.2 (Online Review Replatforming Release 2) Change notes:
 * <ol>
 *   <li>Updated {@link #getProjects(long[], Connection)} method to handle the
 *   situation that tc_direct_project_id is null.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.5 (Release Assembly - TC Direct Select From Copilot Pool Assembly) Change notes:
 *   <ol>
 *     <li>Updated QUERY_ALL_PROJECT_CATEGORIES_COLUMN_TYPES to make it works.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.5.1 (Release Assembly - TopCoder Bug Hunt Assembly Integration 2) change notes:
 * <ol>
 *     <li>Add method {@link #getContestId(long)} to get the corresponding contest id which has
 *     the same asset setting as the give contest id.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.5.2 (Module Assembly - Contest Fee Based on % of Member Cost Cockpit Pages Update) change notes:
 * <ol>
 *     <li>Updated method {@link #getSimplePipelineData(long, Date, Date, boolean)}
 *      for % based contest fee.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.5.3 (Release Assembly - TC Cockpit Project Report Permission) change notes:
 * <ol>
 *     <li>Updated method {@link #getSimplePipelineData(long, java.util.Date, java.util.Date, boolean)} to
 *     change user permission checking on project. Change to >= 0 (report permission)
 *     </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.5.4 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) change log:
 *     - Added method {@link #saveSoftwareCheckpointSubmissionsGeneralFeedback(long, String)},
 *     {@link #getSoftwareCheckpointSubmissionsGeneralFeedback(long)} to support
 *     software checkpoint management.
 * </p>
 *
 * <p>
 * Version 1.5.5 (Release Assembly - TopCoder Security Groups Release 5 v1.0) change log:
 *     - Updated method {@link #getSimpleProjectContestDataByUser(String)} to check the customer groups.
 * </p>
 *
 * <p>
 * Version 1.5.6 (Release Assembly - Release Assembly - TopCoder Direct Prize-Project Link Update v1.0) change log:
 * <ul>
 *     <li> Updated private method createOrUpdateProjectFileTypescreateOrUpdateProjectFileTypes(long projectId,
 *      List<FileType> fileTypes, Connection conn, String operator, boolean update) to have a correct log info.
 *     </li>
 *     <li> Updated method {@link #getProjectPrizes(long projectId)} to set the projectId into prize object.
 *     </li>
 *     <li> Updated private method createOrUpdateProjectPrizes(long projectId, List<Prize> prizes, Connection conn, 
 *      String operator, boolean update) to set the projectId into prize object, and removed the queries related 
 *      with project_prize_xref table since this table is no longer needed.
 *     </li>
 *      <li> Updated method {@link #createPrize(Prize prize, String operator)} to check the projectId of prize, and 
 *      updated the insert sql to make the projectId inserted into prize table correctly.
 *      </li>
 *      <li> Updated method {@link #updatePrize(Prize prize, String operator)} to check the projectId of prize, and 
 *      updated the update sql to make the projectId updated into prize table correctly.
 *      </li>
 *      <li>Updated method {@link #removePrize(Prize prize, String operator)} to remove DELETE_PROJECT_PRIZE_XREF_SQL
 *      execution since project_prize_xref table is no longer needed.
 *      </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.5.7 (Release Assembly - TopCoder Security Groups Release 6 v1.0) change notes:
 * <ol>
 * 	<li>Updated {@link #getSimplePipelineData(long, Date, Date, boolean)} method to support security groups.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.5.8 (Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match) change log:
 * <ul>
 *     <li>Added constant {@link #PROJECT_CATEGORY_MM}</li>
 *     <li>Added constant {@link #MM_SPEC_ID_SEQUENCE_NAME}</li>
 *     <li>Added constant {@link #MM_SPEC_ID_SEQUENCE_NAME_PARAMETER}</li>
 *     <li>Added constant {@link #CREATE_MM_SPEC_SQL}</li>
 *     <li>Added constant {@link #UPDATE_MM_SPEC_SQL}</li>
 *     <li>Added constant {@link #QUERY_MM_SPEC_SQL}</li>
 *     <li>Added constant {@link #QUERY_MM_SPEC_COLUMN_TYPES}</li>
 *     <li>Added constant {@link #SET_PROJECT_MM_SPEC_WITH_PROJECT_SQL}</li>
 *     <li>Added field {@link #mmSpecIdGenerator}</li>
 *     <li>Updated constructor {@link #AbstractInformixProjectPersistence(String)} to support mm specification.</li>
 *     <li>Updated method {@link #createProject(Project, String)} to support mm specification.</li>
 *     <li>Updated method {@link #updateProject(Project, String, String)} to support mm specification.</li>
 *     <li>Updated method {@link #getProjects(long[], Connection)} to support mm specification.</li>     
 *     <li>Added method {@link #createProjectMMSpecification(ProjectMMSpecification, String)} to creates the given 
 *     ProjectMMSpecification entity.</li>
 *     <li>Added method {@link #updateProjectMMSpecification(ProjectMMSpecification, String)} to updates the given 
 *     ProjectMMSpecification entity.</li>
 *     <li>Added method {@link #getProjectMMSpecification(long)} to get the ProjectMMSpecification entity by given projectId.</li>
 *     <li>Added method {@link #createOrUpdateProjectMMSpecification(long, ProjectMMSpecification, Connection, String)} 
 *     to creates or updates project mm specification.</li>
 * </ul>    
 * </p>
 *
 * <p>
 * Version 1.5.9 (Release Assembly - TopCoder Cockpit - Marathon Match Contest Detail Page)
 * <ol>
 *     <li>Updates {@link #QUERY_MM_SPEC_SQL} to add problem statement name to the marathon contest specification</li>
 *     <li>Updates {@link #getProjectMMSpecification(long)} to populate problem statement name to the ProjectMMSpecification DTO</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.5.10 (TopCoder Security Groups Release 8 - Automatically Grant Permissions) change notes:
 * <ol>
 *    <li>Updated {@link #getSimplePipelineData(long, java.util.Date, java.util.Date, boolean)} method to
 *        support security groups automatically granted permissions.</li>
 *    <li>Updated {@link #getSimpleProjectContestDataByUser(String)} method to support auto grant permissions.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.6 (Module Assembly - TC Cockpit Contest Milestone Association 1) change notes:
 *  <ul>
 *      <li> Add method {@link #createProjectMilestoneRelation(long, long, String)}  to add contest milestone association</li>
 *      <li> Add method {@link #updateProjectMilestoneRelation(long, long, String)}  to update contest milestone association</li>
 *      <li> Add method {@link #deleteProjectMilestoneRelation(long, String)}   to remove contest milestone association</li>
 *      <li> Add method {@link #getProjectMilestoneRelation(long)}  to retrieve contest milestone association</li>
 * </ul>
 * <p>
 *
 * <p>
 * Version 1.7 (Module Assembly - TC Cockpit Contest Milestone Association Milestone Page Update)
 * <ul>
 *     <li>Add method {@link #deleteMilestoneProjectRelations(long, String)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.8 (Module Assembly - TC Cockpit Launch F2F contest)
 * <ul>
 *     <li>Added {@link #QUERY_ALL_PROJECT_PLATFORMS_SQL}</li>
 *     <li>Added {@link #QUERY_ALL_PROJECT_PLATFORMS_COLUMN_TYPES}</li>
 *     <li>Added {@link #QUERY_PROJECT_PLATFORMS_SQL}</li>
 *     <li>Added {@link #QUERY_PROJECT_PLATFORMS_COLUMN_TYPES}</li>
 *     <li>Added {@link #QUERY_PROJECT_PLATFORM_IDS_SQL}</li>
 *     <li>Added {@link #QUERY_PROJECT_PLATFORM_IDS_COLUMN_TYPES}</li>
 *     <li>Added {@link #CREATE_PROJECT_PLATFORM_SQL}</li>
 *     <li>Added {@link #DELETE_PROJECT_PLATFORMS_SQL}</li>
 *     <li>Added method {@link #getAllProjectPlatforms()}</li>
 *     <li>Updated method {@link #createProject(Long, com.topcoder.management.project.Project, String, java.sql.Connection)}
 *     to insert project platforms in project</li>
 *     <li>Updated method {@link #updateProject(com.topcoder.management.project.Project, String, String, java.sql.Connection)}
 *     to update project platforms in project</li>
 *     <li>Updated method {@link #makePropertyIdPropertyValueMap(java.util.Map, java.sql.Connection, java.util.Map)}
 *     to ignore the case when comparing the project property type name</li>
 *     <li>Updated method {@link #getProjects(long[], java.sql.Connection)} to get project platforms and
 *     set into the project to return</li>
 *     <li>Added method {@link #createProjectPlatforms(Long, java.util.List, String, java.sql.Connection)}</li>
 *     <li>Added method {@link #updateProjectPlatforms(Long, java.util.List, String, java.sql.Connection)}</li>
 *     <li>Added method {@link #getProjectPlatformsForProject(Long, java.sql.Connection)}</li>
 *     <li>Added method {@link #deleteProjectPlatforms(Long, java.util.List, java.sql.Connection)}</li>
 *     <li>Added method {@link #getAllProjectPlatforms()} (Long, java.util.List, java.sql.Connection)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.8.1 (Release Assembly - Port Design Challenge Forum to use Dev Forum)
 * <ul>
 *     <li>Updated {@link #getActiveDraftContestsForUser(long)} to set forumType data for contests</li>
 * </ul>
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread safe because it is immutable.
 * </p>
 *
 * <p>
 *  Version 1.8.2 (Topcoder - Support Groups Concept For Challenges)
 *  <ul>
 *      <li>Added method {@link #getAllProjectGroups()}</li>
 *      <li>Updated various method to retrieve, create, update and delete groups related to project</li>
 *  </ul>
 * </p>
 *
 *
 * @author tuenm, urtks, bendlund, fuyun, flytoj2ee, tangzx, GreatKevin, frozenfx, freegod, bugbuka, Veve, GreatKevin, TCCODER
 * @version 1.8.2
 * @since 1.0
 */
public abstract class AbstractInformixProjectPersistence implements ProjectPersistence {

    /**
     * private constant representing the active status id for a project
     *
     * @since 1.2
     */
    private static final String ACTIVE_PROJECT_STATUS_ID = "1";

    private static final com.topcoder.util.log.Log log = com.topcoder.util.log.LogManager
            .getLog(AbstractInformixProjectPersistence.class.getName());

	/**
     * <p>
     * Represents the default value for Project Id sequence name. It is used to
     * create id generator for project. This value will be overridden by
     * 'ProjectIdSequenceName' configuration parameter if it exist.
     * </p>
     */
    public static final String PROJECT_ID_SEQUENCE_NAME = "project_id_seq";

	/**
     * <p>
     * Represents the default value for Contest Sale Id sequence name. It is used to
     * create id generator for contest_sale. This value will be overridden by
     * 'ContestSaleIdSequenceName' configuration parameter if it exist.
     * </p>
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public static final String CONTEST_SALE_ID_SEQUENCE_NAME = "contest_sale_id_seq";

    /**
     * <p>
     * Represents the default value for project audit id sequence name. It is
     * used to create id generator for project audit. This value will be
     * overridden by 'ProjectAuditIdSequenceName' configuration parameter if it
     * exist.
     * </p>
     */
    public static final String PROJECT_AUDIT_ID_SEQUENCE_NAME = "project_audit_id_seq";

    /**
     * <p>
     * Represents the default value for file type id sequence name. It is used to create id generator for file type.
     * This value will be overridden by 'FileTypeIdGeneratorSequenceName' configuration parameter if it exist.
     * </p>
     *
     * @since 1.2
     */
    private static final String FILE_TYPE_ID_SEQUENCE_NAME = "file_type_id_seq";
    /**
     * <p>
     * Represents the default value for project spec id sequence name. It is
     * used to create id generator for project spec. This value will be
     * overridden by 'ProjectSpecIdSequenceName' configuration parameter if it
     * exist.
     * </p>
     * 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    public static final String PROJECT_SPEC_ID_SEQUENCE_NAME = "PROJECT_SPEC_ID_SEQ";


	/**
     * <p>
     * Represents the default value for public confidentiality submitter_terms_id.  This value will be
     * overridden by 'public_submitter_terms_id' configuration parameter if it
     * exist.
     * </p>
     * 
     * @since 1.1.2
     */
    public static final long PUBLIC_SUBMITTER_TERMS_ID = 21193; // 21173
	
	/**
     * <p>
     * Represents the default value for public confidentiality reviewer_terms_id.  This value will be
     * overridden by 'public_reviewer_terms_id' configuration parameter if it
     * exist.
     * </p>
     * 
     * @since 1.1.2
     */
    public static final long PUBLIC_REVIEWER_TERMS_ID = 20704;
    
    /**
     * <p>
     * Represents the default value for prize id sequence name. It is used to create id generator for prize. This value
     * will be overridden by 'PrizeIdGeneratorSequenceName' configuration parameter if it exist.
     * </p>
     *
     * @since 1.2
     */
    private static final String PRIZE_ID_SEQUENCE_NAME = "prize_id_seq";
    
	/**
     * <p>
     * Represents the default value for standard cca confidentiality terms_id. 
     * </p>
     * 
     * @since 1.1.2
     */
    public static final long STANDARD_CCA_TERMS_ID = 21153; //21113;   //20713;

    

    /**
     * <p>
     * Represents the default value for standard cca confidentiality terms_id. 
     * </p>
     * 
     * @since 1.1.2
     */
    public static final long MANAGER_TERMS_ID = 20794;


    /**
     * <p>
     * Represents the default value for copilot MSA
     * </p>
     * 
     * @since 1.1.2
     */
    public static final long COPILOT_MSA_TERMS_ID = 20893 ;



	/**
     * <p>
     * Represents the default value for  submitter_role_id.  This value will be
     * overridden by 'submitter_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int SUBMITTER_ROLE_ID = 1;

	/**
     * <p>
     * Represents the default value for  accuracy_reviewer_role_id.  This value will be
     * overridden by 'accuracy_reviewer_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int ACCURACY_REVIEWER_ROLE_ID = 5;

	/**
     * <p>
     * Represents the default value for  failure_reviewer_role_id.  This value will be
     * overridden by 'failure_reviewer_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int FAILURE_REVIEWER_ROLE_ID = 6;

	/**
     * <p>
     * Represents the default value for  stress_reviewer_role_id.  This value will be
     * overridden by 'stress_reviewer_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int STRESS_REVIEWER_ROLE_ID = 7;;

	/**
     * <p>
     * Represents the default value for  reviewer_role_id.  This value will be
     * overridden by 'reviewer_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int REVIEWER_ROLE_ID = 4;

	/**
     * <p>
     * Represents the default value for  primary_screener_role_id.  This value will be
     * overridden by 'primary_screener_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int PRIMARY_SCREENER_ROLE_ID = 2;

	/**
     * <p>
     * Represents the default value for  aggregator_role_id.  This value will be
     * overridden by 'aggregator_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int AGGREGATOR_ROLE_ID = 8;

	/**
     * <p>
     * Represents the default value for  final_reviewer_role_id.  This value will be
     * overridden by 'final_reviewer_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int FINAL_REVIEWER_ROLE_ID = 9;
    
    /**
     * <p>
     * The constant for marathon match category.
     * </p>
     * @since 1.5.8
     */
    private static final long PROJECT_CATEGORY_MM = 37;
    
	/**
     * <p>
     * Represents the default value for studio spec id sequence name. It is used to create id generator for studio spec.
     * This value will be overridden by 'StudioSpecIdGeneratorSequenceName' configuration parameter if it exist.
     * </p>
     *
     * @since 1.2
     */
    private static final String STUDIO_SPEC_ID_SEQUENCE_NAME = "studio_spec_id_seq";
    
    /**
     * <p>
     * Represents the default value for mm spec id sequence name. It is used to create id generator for mm spec.
     * This value will be overridden by 'MMSpecIdGeneratorSequenceName' configuration parameter if it exist.
     * </p>
     *
     * @since 1.5.8
     */
    private static final String MM_SPEC_ID_SEQUENCE_NAME = "mm_spec_id_seq";
    
	/**
     * <p>
     * Represents the default value for screenner_role_id. 
     * </p>
     * @since 1.2.1
     */
    public static final int SCREENER_ROLE_ID = 3;
    /**
     * <p>
     * Represents the default value for final_reviewer_role_id. 
     * </p>
     * @since 1.2.1
     */
    public static final int APPROVER_ROLE_ID = 10;
    /**
     * <p>
     * Represents the default value for designer_role_id. 
     * </p>
     * @since 1.2.1
     */
    public static final int DESIGNER_ROLE_ID = 11;
    /**
     * <p>
     * Represents the default value for observer_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int OBSERVER_ROLE_ID = 12;
    /**
     * <p>
     * Represents the default value for manager_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int MANAGER_ROLE_ID = 13;

     /**
     * <p>
     * Represents the default value for copilot_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int COPILOT_ROLE_ID = 14;
    /**

     /**
     * <p>
     * Represents the default value for client_manager_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int CLIENT_MANAGER_ROLE_ID = 15;
    /**

     /**
     * <p>
     * Represents the default value for post_mortem_reviewer_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int POST_MORTEM_REVIEWER_ROLE_ID = 16;

    /**
     * <p>
     * Represents the default value for specification reviewer role id. 
     * @since 1.2.1
     * </p>
     */
    public static final int SPECIFICATION_REVIEWER_ROLE_ID = 18;
	
	public static final int CHECKPOINT_SCREENER_ROLE_ID = 19;
	
	public static final int CHECKPOINT_REVIEWER_ROLE_ID = 20;
	
	public static final int ITERATIVE_REVIEWER_ROLE_ID = 21;
	
	

    /**

    /**
     * <p>
     * Represents the default value for deactivated_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int DEACTIVATED_ROLE_ID = 86;
    /**
     * <p>
     * Represents the default value for team_captain_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int TEAM_CAPTAIN_ROLE_ID = 1001;
    /**
     * <p>
     * Represents the default value for free_agent_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int FREE_AGENT_ROLE_ID = 1002;
    /**
     * <p>
     * Represents the default value for payment_manager_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int PAYMENT_MANAGER_ROLE_ID = 1003;

     /**
     * private constant representing the project_read permission
     *
     */
    private static final long PROJECT_READ_PERMISSION_ID = 1;

    /**
     * private constant representing the project_read permission
     *
     */
    private static final long PROJECT_WRITE_PERMISSION_ID = 2;

     /**
     * private constant representing the contest_read permission
     *
     */
    private static final long CONTEST_READ_PERMISSION_ID = 4;

    /**
     * private constant representing the contest_read permission
     *
     */
    private static final long CONTEST_WRITE_PERMISSION_ID = 5;
    /**
     * <p>
     * Represents the all roles' id
     * @since 1.2.1
     * </p>
     */
    public static final int[] ALL_ROLES_ID = new int[] {SUBMITTER_ROLE_ID, PRIMARY_SCREENER_ROLE_ID,
        SCREENER_ROLE_ID, REVIEWER_ROLE_ID, ACCURACY_REVIEWER_ROLE_ID, FAILURE_REVIEWER_ROLE_ID,
        STRESS_REVIEWER_ROLE_ID, AGGREGATOR_ROLE_ID, FINAL_REVIEWER_ROLE_ID,
        APPROVER_ROLE_ID, DESIGNER_ROLE_ID, OBSERVER_ROLE_ID, MANAGER_ROLE_ID, DEACTIVATED_ROLE_ID,
        TEAM_CAPTAIN_ROLE_ID, FREE_AGENT_ROLE_ID, PAYMENT_MANAGER_ROLE_ID, COPILOT_ROLE_ID, CLIENT_MANAGER_ROLE_ID,
        POST_MORTEM_REVIEWER_ROLE_ID, SPECIFICATION_REVIEWER_ROLE_ID, CHECKPOINT_SCREENER_ROLE_ID,
            CHECKPOINT_REVIEWER_ROLE_ID, ITERATIVE_REVIEWER_ROLE_ID };
    

	/**
	 * <p>
	 * DEV ID
	 * </p>
	 */
	public static final long PROJECT_CATEGORY_DEVELOPMENT = 2;

    /**
     * Represents the name of connection name parameter in configuration.
     */
    private static final String CONNECTION_NAME_PARAMETER = "ConnectionName";

    /**
     * Represents the name of connection factory namespace parameter in
     * configuration.
     */
    private static final String CONNECTION_FACTORY_NAMESPACE_PARAMETER = "ConnectionFactoryNS";

    /**
     * Represents the name of project id sequence name parameter in
     * configuration.
     */
    private static final String PROJECT_ID_SEQUENCE_NAME_PARAMETER = "ProjectIdSequenceName";

    /**
     * Represents the name of contest sale id sequence name parameter in
     * configuration.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String CONTEST_SALE_ID_SEQUENCE_NAME_PARAMETER = "ContestSaleIdSequenceName";

    /**
     * Represents the name of project audit id sequence name parameter in
     * configuration.
     */
    private static final String PROJECT_AUDIT_ID_SEQUENCE_NAME_PARAMETER = "ProjectAuditIdSequenceName";

    /**
     * Represents the name of file type id sequence name parameter in configuration.
     *
     * @since 1.2
     */
    private static final String FILE_TYPE_ID_SEQUENCE_NAME_PARAMETER = "FileTypeIdGeneratorSequenceName";
    /**
     * Represents the name of project audit id sequence name parameter in
     * configuration.
     */
    private static final String PROJECT_SPEC_ID_SEQUENCE_NAME_PARAMETER = "ProjectSpecIdSequenceName";
    
    /**
     * Represents the project info property for Confidentiality Type.
     * 
     * @sine 1.1.2
     */
    private static final String PROJECT_INFO_CONFIDENTIALITY_TYPE_PROPERTY = "Confidentiality Type";

    /**
     * Represents the name of prize id sequence name parameter in configuration.
     *
     * @since 1.2
     */
    private static final String PRIZE_ID_SEQUENCE_NAME_PARAMETER = "PrizeIdGeneratorSequenceName";

    /**
     * Represents the name of studio spec id sequence name parameter in configuration.
     *
     * @since 1.2
     */
    private static final String STUDIO_SPEC_ID_SEQUENCE_NAME_PARAMETER = "StudioSpecIdGeneratorSequenceName";
    
    /**
     * Represents the name of mm spec id sequence name parameter in configuration.
     *
     * @since 1.5.8
     */
    private static final String MM_SPEC_ID_SEQUENCE_NAME_PARAMETER = "MMSpecIdGeneratorSequenceName";
    
	/**
     * Represents the project info property for Billing Project.
     */
    private static final String PROJECT_INFO_BILLING_PROJECT_PROPERTY = "Billing Project";
    
    /**
     * Represents the public confidentiality type value.
     * 
     * @sine 1.1.2
     */
    private static final String CONFIDENTIALITY_TYPE_PUBLIC = "public";
    
	/**
     * Represents the name of public_submitter_terms_id parameter in
     * configuration.
     * 
     * Updated for Version 1.1.2
     */
    private static final String PUBLIC_SUBMITTER_TERMS_ID_PARAMETER = "public_submitter_terms_id";
    
    /**
     * Represents the name of public_reviewer_terms_id parameter in
     * configuration.
     * 
     * Updated for Version 1.1.2
     */
    private static final String PUBLIC_REVIEWER_TERMS_ID_PARAMETER = "public_reviewer_terms_id";

	/**
     * Represents the name of standard_cca_reviewer_terms_id parameter in
     * configuration.
     * 
     * @since 1.1.2
     */
    private static final String STANDARD_CCA_REVIEWER_TERMS_ID_PARAMETER = "standard_cca_reviewer_terms_id";
    
    /**
     * Represents the name of standard_cca_submitter_terms_id parameter in
     * configuration.
     * 
     * @since 1.1.2
     */
    private static final String STANDARD_CCA_SUBMITTER_TERMS_ID_PARAMETER = "standard_cca_submitter_terms_id";

	/**
     * Represents the name of submitter_role_id parameter in
     * configuration.
     */
    private static final String SUBMITTER_ROLE_ID_PARAMETER = "submitter_role_id";

	/**
     * Represents the name of accuracy_reviewer_role_id parameter in
     * configuration.
     */
    private static final String ACCURACY_REVIEWER_ROLE_ID_PARAMETER = "accuracy_reviewer_role_id";

	/**
     * Represents the name of failure_reviewer_role_id parameter in
     * configuration.
     */
    private static final String FAILURE_REVIEWER_ROLE_ID_PARAMETER = "failure_reviewer_role_id";

	/**
     * Represents the name of stress_reviewer_role_id parameter in
     * configuration.
     */
    private static final String STRESS_REVIEWER_ROLE_ID_PARAMETER = "stress_reviewer_role_id";

	/**
     * Represents the name of reviewer_role_id parameter in
     * configuration.
     */
    private static final String REVIEWER_ROLE_ID_PARAMETER = "reviewer_role_id";

	/**
     * Represents the name of primary_screener_role_id  parameter in
     * configuration.
     */
    private static final String PRIMARY_SCREENER_ROLE_ID_PARAMETER = "primary_screener_role_id";

	/**
     * Represents the name of aggregator_role_id  parameter in
     * configuration.
     */
    private static final String AGGREGATOR_ROLE_ID_PARAMETER = "aggregator_role_id";

	/**
     * Represents the name of final_reviewer_role_id  parameter in
     * configuration.
     */
    private static final String FINAL_REVIEWER_ROLE_ID_PARAMETER = "final_reviewer_role_id";


    /**
     * Represents the sql statement to query all project types.
     */
    private static final String QUERY_ALL_PROJECT_TYPES_SQL = "SELECT "
            + "project_type_id, name, description FROM project_type_lu";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all project types.
     */
    private static final DataType[] QUERY_ALL_PROJECT_TYPES_COLUMN_TYPES = new DataType[] { Helper.LONG_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the SQL statement to query all billing project configuration types.
     */
    private static final String QUERY_ALL_BILLING_PROJECT_CONFIG_TYPES_SQL = "SELECT "
            + "client_billing_config_type_id, name, description FROM client_billing_config_type_lu";

    /**
     * Represents the column types for the result set which is returned by executing the SQL
     * statement to query all billing project configuration types.
     */
    private static final DataType[] QUERY_ALL_BILLING_PROJECT_CONFIG_TYPES_COLUMN_TYPES = new DataType[] {
            Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the SQL statement to query the billing project configurations for a billing
     * project.
     */
    private static final String QUERY_ALL_BILLING_PROJECT_CONFIGS_SQL = "SELECT "
            + "client_billing_config_type_id, value FROM client_billing_config where client_billing_id=?";

    /**
     * Represents the column types for the result set which is returned by executing the SQL
     * statement to query billing project configurations for the specified billing project id.
     */
    private static final DataType[] QUERY_ALL_BILLING_PROJECT_CONFIGS_SQL_COLUMN_TYPES = new DataType[] {
            Helper.LONG_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to query the sale status via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String QUERY_SALE_STATUS_BY_ID_SQL = "SELECT "
            + "sale_status_id, sale_status_desc FROM sale_status_lu WHERE sale_status_id=?";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query the sale status via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final DataType[] QUERY_SALE_STATUS_BY_ID_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to query the sale type via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String QUERY_SALE_TYPE_BY_ID_SQL = "SELECT "
            + "sale_type_id, sale_type_name FROM sale_type_lu WHERE sale_type_id=?";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query the sale type via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final DataType[] QUERY_SALE_TYPE_BY_ID_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to query all project categories.
     */
    private static final String QUERY_ALL_PROJECT_CATEGORIES_SQL = "SELECT "
            + "category.project_category_id, category.name, category.description, "
            + "type.project_type_id, type.name, type.description "
            + "FROM project_category_lu AS category "
            + "JOIN project_type_lu AS type "
            + "ON category.project_type_id = type.project_type_id";

    /**
     * The SQL to get all the project platforms from project_platform_lu table.
     *
     * @since 1.8
     */
    private static final String QUERY_ALL_PROJECT_PLATFORMS_SQL = "SELECT project_platform_id, name " +
            " FROM project_platform_lu";


    /**
     * The SQL to get all the project groups from project_group_lu table.
     */
    private static final String QUERY_ALL_PROJECT_GROUPS_SQL = "SELECT project_group_id, name " +
            " FROM project_group_lu";



    /**
     * Represents the sql statement to query all project contest data.
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.1 & 1.1.3]
     *      - fetch tc project and s/w project permission.
     *      - whenever join with s/w project and user_permission_grant, add is_studio=0 constraint.
     * </p>
     * 
     * Updated for Spec Reviews Finishing Touch v1.0 (Version 1.1.1)
     *  - Changed the way now spec status is queried.
     *
     * Updated for Cockpit Spec Review - Stage 2(version 1.2.4).
     */

		private static final String QUERY_ALL_SIMPLE_PROJECT_CONTEST = " select p.project_id as contest_id, "
			+ " (select ptl.name from phase_type_lu ptl where phase_type_id = (select min(phase_type_id) from project_phase ph "
			+ " where ph.phase_status_id = 2 and ph.project_id=p.project_id)) as current_phase, "
			+ " (select value from project_info where project_id = p.project_id and project_info_type_id =6) as contest_name, "
			+ " (select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=p.project_id and ph.phase_type_id = 1) as start_date, "
			+ " (select max(nvl(actual_end_time, scheduled_end_time)) from project_phase ph where ph.project_id=p.project_id) as end_date, "
			+ "  pcl.name as contest_type, psl.name as status, "
			+ " (select count(resource_id) from resource r where r.project_id = p.project_id and resource_role_id = 1) as num_reg, "
			+ " (select count (distinct resource_id) from upload u where u.project_id = p.project_id and upload_status_id = 1 and upload_type_id = 1) as num_sub, "
			// fixed forum post
            		+ " (select count(messageid) from jivecategory c, jiveforum f, jivemessage m, project_info pi "
            		+ "        where pi.project_info_type_id =4 and c.categoryid = pi.value and c.categoryid = f.categoryid and m.forumid = f.forumid "
            		+ "             and pi.project_id =  p.project_id) as num_for, "
			+ " tc_direct_project_id as project_id, tcd.name, tcd.description, tcd.user_id, "
			+ "  (select value from project_info where project_id = p.project_id and project_info_type_id =4) as forum_id, "
			+ "  (select case when(count(*)>=1) then 'Scheduled' when(count(*)=0) then 'Draft' end "
			+ "   from contest_sale c where p.project_id = c.contest_id and upper(psl.name)='ACTIVE' ) as newstatus, "
			+ " (select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  "
            + " from user_permission_grant as upg  where resource_id=p.project_id and is_studio=0 "
            + " ),0)) as cperm, "

            + " (select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  "
            + " from user_permission_grant as upg  where resource_id=tcd.project_id  "
            + " ),0)) as pperm, "

            /* Added in cockpit R 10 */
            + " (select scheduled_end_time from project_phase ph "
            + " where ph.phase_type_id = 2 and ph.project_id=p.project_id) as submission_end_date,"
            /* R 10 end*/

            /* updated by Cockpit Spec Review - Stage 2  - start */
            /* sepc review project id*/
            + "(select source_project_id from linked_project_xref where dest_project_id = p.project_id and link_type_id = 3) as srprojectId,"
            /* sepc review user */
            + "(select count(*) from resource r, linked_project_xref linkp where linkp.dest_project_Id = p.project_id"
            + " and linkp.link_type_id = 3 and r.project_id = linkp.source_project_id and r.resource_role_id = 4) as srr,"
            /* reviewing status check */
            + "(select min(ph.phase_type_id) from project_phase ph, linked_project_xref linkp "
            + " where ph.phase_type_id = 4 and ph.phase_status_id = 2 and ph.project_id = linkp.source_project_id and linkp.link_type_id = 3"
            + " and linkp.dest_project_Id = p.project_id) as sprs,"
             /* sepc review result to find the 'required' fixed item. */
            + "(select count(*) from review r , review_item ri, review_item_comment ric, resource re, linked_project_xref linkp "
            + "                    where r.review_id = ri.review_id "
            + "                          and ri.review_item_id = ric.review_item_id and comment_type_id = 3 "
            + "                          and r.review_id = re.resource_id "
            + "                          and linkp.dest_project_Id = p.project_id "
            + "                          and linkp.link_type_id = 3 and re.resource_role_id = 4 "
            + "                          and re.project_id = linkp.source_project_id ) as srResult,"
            /* sepc review result to find the final fix 'fixed'. */
            + "(select count(*) from review r , review_item ri, review_item_comment ric, resource re, linked_project_xref linkp "
            + "                 where r.review_id = ri.review_id "
            + "                     and upper(nvl(ric.extra_info, '')) != 'FIXED' "
            + "                      and ric.comment_type_id = 3 and r.resource_id = re.resource_id and ri.review_item_id = ric.review_item_id "
            + "                      and linkp.dest_project_Id = p.project_id "
            + "                      and linkp.link_type_id = 3 and re.resource_role_id = 9 "
            + "                      and re.project_id = linkp.source_project_id) as srfresult, "
            /* sepc review result to find the final fix 'fixed' in response. */
            + "(select count(*) from review r , review_comment ri, resource re, linked_project_xref linkp "
            + "                 where r.review_id = ri.review_id "
            + "                     and upper(nvl(ri.extra_info, '')) == 'APPROVED' "
            + "                     and ri.comment_type_id = 10 and r.resource_id = re.resource_id "
            + "                     and linkp.dest_project_Id = p.project_id "
            + "                     and linkp.link_type_id = 3 and re.resource_role_id = 9 "
            + "                     and re.project_id = linkp.source_project_id) as srfrresult, "
            /* updated by Cockpit Spec Review - Stage 2  - end */

           // check if phase is FF open/close and there is final review
           + " (select min(ph.phase_type_id) from project_phase ph, linked_project_xref linkp "
           + "    where ph.phase_type_id = 10 and ph.phase_status_id in (2, 3) and ph.project_id = linkp.source_project_id "
           + "             and linkp.link_type_id = 3 and linkp.dest_project_Id = p.project_id "
           + "     and exists  "
           + "     (select * from review r , review_comment ri, resource re, linked_project_xref linkp "
           + "               where r.review_id = ri.review_id "
           + "                   and ri.comment_type_id = 10 and r.resource_id = re.resource_id "
           + "                   and linkp.dest_project_Id = p.project_id "
           + "                   and linkp.link_type_id = 3 and re.resource_role_id = 9 "
           + "                   and re.project_id = linkp.source_project_id)) as hassrfr, "
           // contest fee/ price sum
           + "  case when p.project_status_id in (1, 2) then "
           + "       nvl((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info "
           + "         where project_info_type_id in (31, 33, 35, 16, 38, 39, 49) "
           + "         and project_id = p.project_id), 0) "
           + "     + "
           + "       nvl((select nvl(sum (cast (nvl (pi30.value, '0') as DECIMAL (10,2))), 0) from project_info pi30, project_info pi26 "
           + "       where pi30.project_info_type_id = 30 and pi26.project_info_type_id = 26 and pi26.project_id = pi30.project_id  "
           + "       and pi26.value = 'On' "
           + "       and pi26.project_id =  p.project_id ), 0) "
           + "     + "
           + "     nvl(((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info "
           + "         where project_info_type_id = 16 "
           + "         and project_id = p.project_id)/2), 0) "
           + " when p.project_status_id = 7 then "
           + "   nvl((SELECT SUM(nvl(total_amount, 0))  "
           + "        FROM informixoltp:payment pm INNER JOIN informixoltp:payment_detail pmd ON pm.most_recent_detail_id = pmd.payment_detail_id  "
           + "         WHERE pmd.component_project_id = p.project_id and installment_number = 1 "
           + "         AND NOT pmd.payment_status_id IN (65, 68, 69)), 0) "
           + "   +  "
           + "   NVL((SELECT sum(pmd2.total_amount)  "
           + "   FROM  informixoltp:payment_detail pmd,   "
           + "         informixoltp:payment pm LEFT OUTER JOIN informixoltp:payment_detail pmd2 on pm.payment_id = pmd2.parent_payment_id,  "
           + "         informixoltp:payment pm2  "
           + "    WHERE pmd.component_project_id = p.project_id and pmd2.installment_number = 1  "
           + "    and pm.most_recent_detail_id = pmd.payment_detail_id   "
           + "   and pm2.most_recent_detail_id = pmd2.payment_detail_id  "
           + "   AND NOT pmd2.payment_status_id IN (65, 68, 69)), 0) "
           + "     + "
           + "     nvl((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info "
           + "         where project_info_type_id  = 31 "
           + "         and project_id = p.project_id), 0) "
           + "     + "
           + "       nvl((select nvl(sum (cast (nvl (pi30.value, '0') as DECIMAL (10,2))), 0) from project_info pi30, project_info pi26 "
           + "       where pi30.project_info_type_id = 30 and pi26.project_info_type_id = 26 and pi26.project_id = pi30.project_id  "
           + "       and pi26.value = 'On' "
           + "       and pi26.project_id =  p.project_id ), 0) "
           + "  else  nvl((SELECT SUM(nvl(total_amount, 0))  "
           + "        FROM informixoltp:payment pm INNER JOIN informixoltp:payment_detail pmd ON pm.most_recent_detail_id = pmd.payment_detail_id  "
           + "          WHERE pmd.component_project_id = p.project_id and installment_number = 1 "
           + "          AND NOT pmd.payment_status_id IN (65, 68, 69)), 0) "
           + "   +  "
           + "   NVL((SELECT sum(pmd2.total_amount)  "
           + "   FROM  informixoltp:payment_detail pmd,   "
           + "         informixoltp:payment pm LEFT OUTER JOIN informixoltp:payment_detail pmd2 on pm.payment_id = pmd2.parent_payment_id,  "
           + "         informixoltp:payment pm2  "
           + "    WHERE pmd.component_project_id = p.project_id and pmd2.installment_number = 1  "
           + "    and pm.most_recent_detail_id = pmd.payment_detail_id   "
           + "   and pm2.most_recent_detail_id = pmd2.payment_detail_id  "
           + "   AND NOT pmd2.payment_status_id IN (65, 68, 69)), 0) "
           + "     + "
           + "     nvl((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info "
           + "         where project_info_type_id  = 31 and exists (select * from project_phase where project_id = p.project_id and phase_type_id = 1 and phase_status_id in (2,3)) "
           + "         and project_id = p.project_id), 0) "
           + " end  as contest_fee "

            + " from project p, project_category_lu pcl, project_status_lu psl, tc_direct_project tcd "
            + " where p.project_category_id = pcl.project_category_id and p.project_status_id = psl.project_status_id and p.tc_direct_project_id = tcd.project_id "

                                                 // dont show spec review project
            + "		and p.project_status_id != 3 and p.project_category_id != 27 ";



    /**
     * Represents the sql statement to query all project contest data for a tc project id.
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.1 & 1.1.3]
     *      - fetch tc project and s/w project permission.
     *      - whenever join with s/w project and user_permission_grant, add is_studio=0 constraint.
     * </p>
     * 
     * Updated for Spec Reviews Finishing Touch v1.0
     *  - Changed the way now spec status is queried.
     *
     *  Updated for Cockpit Spec Review - Stage 2(version 1.2.4).
     */
	private static final String QUERY_ALL_SIMPLE_PROJECT_CONTEST_BY_PID = " select p.project_id as contest_id, "
	+		" (select ptl.name from phase_type_lu ptl where phase_type_id = (select min(phase_type_id) from project_phase ph " 
	+ " where ph.phase_status_id = 2 and ph.project_id=p.project_id)) as current_phase, "
	+ "(select value from project_info where project_id = p.project_id and project_info_type_id =6) as contest_name, "
	+ "(select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=p.project_id and ph.phase_type_id = 1) as start_date, "
	+ " (select max(nvl(actual_end_time, scheduled_end_time)) from project_phase ph where ph.project_id=p.project_id) as end_date, "
	+ "  pcl.name as contest_type, psl.name as status, "
	+ " (select count(resource_id) from resource r where r.project_id = p.project_id and resource_role_id = 1) as num_reg, "
	+ " (select count (distinct resource_id) from upload u where u.project_id = p.project_id and upload_status_id = 1 and upload_type_id = 1) as num_sub, "
	// fixed forum post
    + " (select count(messageid) from jivecategory c, jiveforum f, jivemessage m, project_info pi "
    + "        where pi.project_info_type_id =4 and c.categoryid = pi.value and c.categoryid = f.categoryid and m.forumid = f.forumid "
    + "             and pi.project_id =  p.project_id) as num_for, "
	+ " tc_direct_project_id as project_id , tcd.name, tcd.description, tcd.user_id, "
	+ "  (select value from project_info where project_id = p.project_id and project_info_type_id =4) as forum_id, "
	+ "  (select case when(count(*)>=1) then 'Scheduled' when(count(*)=0) then 'Draft' end "
	+ "   from contest_sale c where p.project_id = c.contest_id and upper(psl.name)='ACTIVE' ) as newstatus, "
	
	
    + " (select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  "
    + " from user_permission_grant as upg  where resource_id=p.project_id and is_studio=0"
    + " ),0)) as cperm, "

    + " (select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  "
    + " from user_permission_grant as upg  where resource_id=tcd.project_id"
    + " ),0)) as pperm, "
    /* Added in cockpit R 10 */
    + " (select scheduled_end_time from project_phase ph "
    + " where ph.phase_type_id = 2 and ph.project_id=p.project_id) as submission_end_date,"
    /* R 10 end*/

    /* updated by Cockpit Spec Review - Stage 2  - start */
    /* sepc review project id*/
    + "(select source_project_id from linked_project_xref where dest_project_id = p.project_id and link_type_id = 3) as srprojectId,"
    /* sepc review user */
    + "(select count(*) from resource r, linked_project_xref linkp where linkp.dest_project_Id = p.project_id"
    + " and linkp.link_type_id = 3 and r.project_id = linkp.source_project_id and r.resource_role_id = 4) as srr,"
    /* reviewing status check */
    + "(select min(ph.phase_type_id) from project_phase ph, linked_project_xref linkp "
    + " where ph.phase_type_id = 4 and ph.phase_status_id = 2 and ph.project_id = linkp.source_project_id and linkp.link_type_id = 3"
    + " and linkp.dest_project_Id = p.project_id) as sprs,"
   /* sepc review result to find the 'required' fixed item. */
            + "(select count(*) from review r , review_item ri, review_item_comment ric, resource re, linked_project_xref linkp "
            + "                    where r.review_id = ri.review_id "
            + "                          and ri.review_item_id = ric.review_item_id and comment_type_id = 3 "
            + "                          and r.review_id = re.resource_id "
            + "                          and linkp.dest_project_Id = p.project_id "
            + "                          and linkp.link_type_id = 3 and re.resource_role_id = 4 "
            + "                          and re.project_id = linkp.source_project_id ) as srResult,"
         /* sepc review result to find the final fix 'fixed'. */
            + "(select count(*) from review r , review_item ri, review_item_comment ric, resource re, linked_project_xref linkp "
            + "                 where r.review_id = ri.review_id "
            + "                     and upper(nvl(ric.extra_info, '')) != 'FIXED' "
            + "                      and ric.comment_type_id = 3 and r.resource_id = re.resource_id and ri.review_item_id = ric.review_item_id "
            + "                      and linkp.dest_project_Id = p.project_id "
            + "                      and linkp.link_type_id = 3 and re.resource_role_id = 9 "
            + "                      and re.project_id = linkp.source_project_id) as srfresult, "
            /* sepc review result to find the final fix 'fixed' in response. */
            + "(select count(*) from review r , review_comment ri, resource re, linked_project_xref linkp "
            + "                 where r.review_id = ri.review_id "
            + "                     and upper(nvl(ri.extra_info, '')) == 'APPROVED' "
            + "                     and ri.comment_type_id = 10 and r.resource_id = re.resource_id "
            + "                     and linkp.dest_project_Id = p.project_id "
            + "                     and linkp.link_type_id = 3 and re.resource_role_id = 9 "
            + "                     and re.project_id = linkp.source_project_id) as srfrresult, "
            /* updated by Cockpit Spec Review - Stage 2  - end */

           // check if phase is FF open/close and there is final review
           + " (select min(ph.phase_type_id) from project_phase ph, linked_project_xref linkp "
           + "    where ph.phase_type_id = 10 and ph.phase_status_id in (2, 3) and ph.project_id = linkp.source_project_id "
           + "             and linkp.link_type_id = 3 and linkp.dest_project_Id = p.project_id "
           + "     and exists  "
           + "     (select * from review r , review_comment ri, resource re, linked_project_xref linkp "
           + "               where r.review_id = ri.review_id "
           + "                   and ri.comment_type_id = 10 and r.resource_id = re.resource_id "
           + "                   and linkp.dest_project_Id = p.project_id "
           + "                   and linkp.link_type_id = 3 and re.resource_role_id = 9 "
           + "                   and re.project_id = linkp.source_project_id)) as hassrfr, "

           // contest fee/ price sum
         + "  case when p.project_status_id in (1, 2) then "
           + "       nvl((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info "
           + "         where project_info_type_id in (31, 33, 35, 16, 38, 39, 49) "
           + "         and project_id = p.project_id), 0) "
           + "     + "
           + "       nvl((select nvl(sum (cast (nvl (pi30.value, '0') as DECIMAL (10,2))), 0) from project_info pi30, project_info pi26 "
           + "       where pi30.project_info_type_id = 30 and pi26.project_info_type_id = 26 and pi26.project_id = pi30.project_id  "
           + "       and pi26.value = 'On' "
           + "       and pi26.project_id =  p.project_id ), 0) "
           + "     + "
           + "     nvl(((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info "
           + "         where project_info_type_id = 16 "
           + "         and project_id = p.project_id)/2), 0) "
           + " when p.project_status_id = 7 then "
           + "   nvl((SELECT SUM(nvl(total_amount, 0))  "
           + "        FROM informixoltp:payment pm INNER JOIN informixoltp:payment_detail pmd ON pm.most_recent_detail_id = pmd.payment_detail_id  "
           + "         WHERE pmd.component_project_id = p.project_id and installment_number = 1 "
           + "         AND NOT pmd.payment_status_id IN (65, 68, 69)), 0) "
           + "   +  "
           + "   NVL((SELECT sum(pmd2.total_amount)  "
           + "   FROM  informixoltp:payment_detail pmd,   "
           + "         informixoltp:payment pm LEFT OUTER JOIN informixoltp:payment_detail pmd2 on pm.payment_id = pmd2.parent_payment_id,  "
           + "         informixoltp:payment pm2  "
           + "    WHERE pmd.component_project_id = p.project_id and pmd2.installment_number = 1  "
           + "    and pm.most_recent_detail_id = pmd.payment_detail_id   "
           + "   and pm2.most_recent_detail_id = pmd2.payment_detail_id  "
           + "   AND NOT pmd2.payment_status_id IN (65, 68, 69)), 0) "
           + "     + "
           + "     nvl((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info "
           + "         where project_info_type_id = 31 "
           + "         and project_id = p.project_id), 0) "
           + "     + "
           + "       nvl((select nvl(sum (cast (nvl (pi30.value, '0') as DECIMAL (10,2))), 0) from project_info pi30, project_info pi26 "
           + "       where pi30.project_info_type_id = 30 and pi26.project_info_type_id = 26 and pi26.project_id = pi30.project_id  "
           + "       and pi26.value = 'On' "
           + "       and pi26.project_id =  p.project_id ), 0) "
           + "  else  nvl((SELECT SUM(nvl(total_amount, 0))  "
           + "        FROM informixoltp:payment pm INNER JOIN informixoltp:payment_detail pmd ON pm.most_recent_detail_id = pmd.payment_detail_id  "
           + "          WHERE pmd.component_project_id = p.project_id and installment_number = 1 "
           + "          AND NOT pmd.payment_status_id IN (65, 68, 69)), 0) "
           + "   +  "
           + "   NVL((SELECT sum(pmd2.total_amount)  "
           + "   FROM  informixoltp:payment_detail pmd,   "
           + "         informixoltp:payment pm LEFT OUTER JOIN informixoltp:payment_detail pmd2 on pm.payment_id = pmd2.parent_payment_id,  "
           + "         informixoltp:payment pm2  "
           + "    WHERE pmd.component_project_id = p.project_id and pmd2.installment_number = 1  "
           + "    and pm.most_recent_detail_id = pmd.payment_detail_id   "
           + "   and pm2.most_recent_detail_id = pmd2.payment_detail_id  "
           + "   AND NOT pmd2.payment_status_id IN (65, 68, 69)), 0) "
           + "     + "
           + "     nvl((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info "
           + "         where project_info_type_id = 31 and exists (select * from project_phase where project_id = p.project_id and phase_type_id = 1 and phase_status_id in (2,3)) "
           + "         and project_id = p.project_id), 0) "
           + " end  as contest_fee "

    + " from project p, project_category_lu pcl, project_status_lu psl, tc_direct_project tcd "
    + " where p.project_category_id = pcl.project_category_id and p.project_status_id = psl.project_status_id and p.tc_direct_project_id = tcd.project_id "
                                      // dont show spec review project
    + " and p.project_status_id != 3 and p.project_category_id != 27 "
    + " and p.tc_direct_project_id= ";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query all
     * project categories.
     */
    private static final DataType[] QUERY_ALL_PROJECT_CATEGORIES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the column types for the result which gets all the project platforms.
     *
     * @since 1.8
     */
    private static final DataType[] QUERY_ALL_PROJECT_PLATFORMS_COLUMN_TYPES = new DataType[] {
            Helper.LONG_TYPE, Helper.STRING_TYPE };


    /**
     * Represents the column types for the result which gets all the project groups.
     */
    private static final DataType[] QUERY_ALL_PROJECT_GROUPS_COLUMN_TYPES = new DataType[] {
            Helper.LONG_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query all
     * project categories.
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.1]
     *      - Added column type mapping for 2 new columns: tc project permission and contest permission.
     * </p>
     *
     * Updated for Cockpit Spec Review - Stage 2(version 1.2.4).
     *
     * <p>
     * Updated for Direct Search Assembly
     *      - Added column type for price sum/ contest fee
     * </p>
     */
    private static final DataType[] QUERY_ALL_SIMPLE_PROJECT_CONTEST_COLUMN_TYPES = new DataType[] { Helper.LONG_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE,
            Helper.DATE_TYPE,Helper.DATE_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
               Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE,
            Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE,Helper.LONG_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DOUBLE_TYPE};

    /**
     * Represents the sql statement to query all design components data for a user id.
     * @since 1.2.1
     */
    private static final String QUERY_ALL_DESIGN_COMPONENTS = "select p.project_id as project_id, " + 
        "(select (c.category_name ||' ' || pi.value || ' ' || cv.version_text) " + 
        "from comp_catalog cc, categories c, project_info pi, comp_versions cv " + 
        "where  c.category_id = cc.root_category_id and " + 
        "cc.component_id = (select pi.value from project_info pi " + 
        "where pi.project_info_type_id = 2 and pi.project_id = p.project_id )" + 
        "and project_id = p.project_id and " + 
        "project_info_type_id =6 " + 
        "and cv.comp_vers_id = (select pi.value from project_info pi " + 
        "where pi.project_info_type_id = 1 and pi.project_id = p.project_id) " + 
        ") as component_name,  pt.name " +
        "from project p, project_category_lu pcl," + 
        " project_status_lu psl, tc_direct_project tcd , project_info pi , user_permission_grant upg, permission_type pt " + 
        " where p.project_category_id = pcl.project_category_id and p.project_status_id = psl.project_status_id " + 
        "and p.tc_direct_project_id = tcd.project_id " + 
        "and p.project_status_id != 3 and p.project_category_id = 1 " +
        "and p.project_id = pi.project_id and pi.project_info_type_id = 1 " +
        "and not exists (" + 
        "select 1 from project_info q1 , project q " + 
        " where q1.project_info_type_id = 1 and q1.project_id = q.project_id and q1.project_id <> p.project_id and q1.value = pi.value " +
        " and (q.project_status_id = 1 or q.project_status_id = 7 or q.project_status_id = 2)  and q.project_category_id == 2) "  +
        " and upg.resource_id = tcd.project_id  " +
        " and upg.user_id = ? " +
        " and pt.permission_type_id = upg.permission_type_id " +
        " and upg.permission_type_id in (1,2,3) ";
        


    /**
     * Represents  the column types for the result set which is returned by executing the sql statement
     * to query all design components data for a user id.
     * @since 1.2.1
     */
    private static final DataType[] QUERY_ALL_DESIGN_COMPONENTS_COLUMN_TYPES = new DataType[] { Helper.LONG_TYPE,
	Helper.STRING_TYPE, Helper.STRING_TYPE};
    /**
     * Represents the sql statement to query all project statuses.
     */
    private static final String QUERY_ALL_PROJECT_STATUSES_SQL = "SELECT "
            + "project_status_id, name, description FROM project_status_lu";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all project statuses.
     */
    private static final DataType[] QUERY_ALL_PROJECT_STATUSES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to query all project property types.
     */
    private static final String QUERY_ALL_PROJECT_PROPERTY_TYPES_SQL = "SELECT "
            + "project_info_type_id, name, description FROM project_info_type_lu";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all project property types.
     */
    private static final DataType[] QUERY_ALL_PROJECT_PROPERTY_TYPES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to query projects.
     */
    private static final String QUERY_PROJECTS_SQL = "SELECT "
            + "project.project_id, status.project_status_id, status.name,"
            + "category.project_category_id, category.name, type.project_type_id, type.name,"
            + "project.create_user, project.create_date, project.modify_user, project.modify_date, category.description, "
            + "project.tc_direct_project_id, u.handle "
            + "FROM project JOIN project_status_lu AS status "
            + "ON project.project_status_id=status.project_status_id "
            + "JOIN project_category_lu AS category "
            + "ON project.project_category_id=category.project_category_id "
            + "JOIN project_type_lu AS type "
            + "ON category.project_type_id=type.project_type_id "
            + "JOIN user AS u "
            + "ON u.user_id=project.create_user "
            + "WHERE project.project_id IN ";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query
     * projects.
     *
     * @since 1.0
     */
    private static final DataType[] QUERY_PROJECTS_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE};
    private static final String QUERY_PROJECTS_BY_CREATE_DATE_SQL = "SELECT "

			+ "  project.project_id, project_status_lu.project_status_id, project_status_lu.name, "
            + "                  project_category_lu.project_category_id, project_category_lu.name, project_type_lu.project_type_id, project_type_lu.name, "
            + "                  project.create_user, project.create_date, project.modify_user, project.modify_date, project_category_lu.description, "
			+ "                  pi1.value as project_name, pi2.value as project_version "
            + "              FROM project, "
            + "                   project_category_lu, "
            + "                   project_status_lu, "
            + "                   project_type_lu, "
			+ "                   project_info pi1, "
            + "                   project_info pi2 "
            + "             WHERE project.project_category_id = project_category_lu.project_category_id "
            + "               AND project.project_status_id = project_status_lu.project_status_id "
            + "               AND project_category_lu.project_type_id = project_type_lu.project_type_id "
			+ "               AND pi1.project_id = project.project_id AND pi1.project_info_type_id = 6 "
            + "               AND pi2.project_id = project.project_id AND pi2.project_info_type_id = 7 " 				 
            + "               AND (project.project_status_id != 3)"
			+ "			      AND date(project.create_date) > date(current) - ";
    
    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query projects.
     */
    private static final DataType[] QUERY_PROJECTS_BY_CREATE_DATE_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, 
		Helper.STRING_TYPE, Helper.STRING_TYPE};
    
    /**
     * Represents the sql statement to query tc direct projects.
     */
    private static final String QUERY_TC_DIRECT_PROJECTS_SQL = "SELECT "
            + "project.project_id, status.project_status_id, status.name,"
            + "category.project_category_id, category.name, type.project_type_id, type.name,"
            + "project.create_user, project.create_date, project.modify_user, project.modify_date, category.description, "
            + "project.tc_direct_project_id "
            + "FROM project JOIN project_status_lu AS status "
            + "ON project.project_status_id=status.project_status_id "
            + "JOIN project_category_lu AS category "
            + "ON project.project_category_id=category.project_category_id "
            + "JOIN project_type_lu AS type "
            + "ON category.project_type_id=type.project_type_id "
            + "WHERE project.tc_direct_project_id is not null ";

   
    /**
     * Represents the sql statement to query tc direct projects.
     */
    private static final String QUERY_USER_TC_DIRECT_PROJECTS_SQL = "SELECT "
            + "project.project_id, status.project_status_id, status.name, "
            + "category.project_category_id, category.name, type.project_type_id, type.name,"
            + "project.create_user, project.create_date, project.modify_user, project.modify_date, category.description, "
            + "project.tc_direct_project_id "
            + "FROM project JOIN project_status_lu AS status "
            + "ON project.project_status_id=status.project_status_id "
            + "JOIN project_category_lu AS category "
            + "ON project.project_category_id=category.project_category_id "
            + "JOIN project_type_lu AS type "
            + "ON category.project_type_id=type.project_type_id "
            + "WHERE project.tc_direct_project_id is not null AND project.create_user = ?";  
    
    /**
     * Represents the sql statement to query project properties.
     */
    private static final String QUERY_PROJECT_PROPERTIES_SQL = "SELECT "
            + "info.project_id, info_type.name, info.value "
            + "FROM project_info AS info "
            + "JOIN project_info_type_lu AS info_type "
            + "ON info.project_info_type_id=info_type.project_info_type_id "
            + "WHERE info.project_id IN ";

    /**
     * Represents the sql statement to query project platforms for a set of project ids.
     *
     * @since 1.8
     */
    private static final String QUERY_PROJECT_PLATFORMS_SQL =
            "SELECT pp.project_id, pl.project_platform_id, pl.name \n" +
                    "FROM project_platform pp \n" +
                    "INNER JOIN project_platform_lu pl \n" +
                    "ON pp.project_platform_id = pl.project_platform_id WHERE pp.project_id IN";

    /**
     * Represents the sql statement to query project groups for a set of project ids.
     */
    private static final String QUERY_PROJECT_GROUPS_SQL =
            "SELECT pg.project_id, pg.project_group_id, pl.name \n" +
                    "FROM project_group_xref pg \n" +
                    "INNER JOIN project_group_lu pl \n" +
                    "ON pg.project_group_id = pl.project_group_id WHERE pg.project_id IN";

    /**
     * Represents the sql statement to query project properties.
     */
    private static final String QUERY_ONE_PROJECT_PROPERTIES_SQL = "SELECT "
            + "info.project_id, info_type.name, info.value "
            + "FROM project_info AS info "
            + "JOIN project_info_type_lu AS info_type "
            + "ON info.project_info_type_id=info_type.project_info_type_id "
            + "WHERE info.project_id = ?";
    
    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query project properties.
     */
    private static final DataType[] QUERY_PROJECT_PROPERTIES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the column types for the result set which query project platforms of a set
     * of project ids.
     *
     * @since 1.8
     */
    private static final DataType[] QUERY_PROJECT_PLATFORMS_COLUMN_TYPES = new DataType[] {
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the column types for the result set which query project groups of a set
     * of project ids.
     */
    private static final DataType[] QUERY_PROJECT_GROUPS_COLUMN_TYPES = new DataType[] {
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to query project property ids.
     */
    private static final String QUERY_PROJECT_PROPERTY_IDS_SQL = "SELECT "
            + "project_info_type_id FROM project_info WHERE project_id=?";

    /**
     * Represents the sql statement to query project property ids and values.
     */
    private static final String QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_SQL = "SELECT "
            + "project_info_type_id, value FROM project_info WHERE project_id=?";

    /**
     * Represents the sql statement to query the platform ids of a given project id.
     *
     * @since 1.8
     */
    private static final String QUERY_PROJECT_PLATFORM_IDS_SQL = "SELECT "
            + "project_platform_id FROM project_platform WHERE project_id = ?";

    /**
     * Represents the sql statement to query the group ids of a given project id.
     */
    private static final String QUERY_PROJECT_GROUP_IDS_SQL = "SELECT "
            + "project_group_id FROM project_group_xref WHERE project_id = ?";

    /**
     * Represents the column types for the result set returne from getting platform ids of a project id.
     *
     * @since 1.8
     */
    private static final DataType[] QUERY_PROJECT_PLATFORM_IDS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE};

    /**
     * Represents the column types for the result set returne from getting group ids of a project id.
     */
    private static final DataType[] QUERY_PROJECT_GROUP_IDS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE};

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query project property ids.
     */
    private static final DataType[] QUERY_PROJECT_PROPERTY_IDS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE};

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query project property ids and values.
     */
    private static final DataType[] QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_COLUMN_TYPES = new DataType[] {
    	Helper.LONG_TYPE, Helper.STRING_TYPE
    };

    /**
     * Represents the sql statement to create project.
     */
    private static final String CREATE_PROJECT_SQL = "INSERT INTO project "
            + "(project_id, project_status_id, project_category_id, "
            + "create_user, create_date, modify_user, modify_date, tc_direct_project_id) "
            + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT, ?)";

    /**
     * Represents the sql statement to query the contest sale via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String QUERY_CONTEST_SALE_BY_ID_SQL = "SELECT "
            + "contest_sale_id, contest_id, sale_status_id, price, paypal_order_id, create_date, sale_reference_id, sale_type_id "
            + "FROM contest_sale WHERE contest_sale_id=?";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query the contest sale via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final DataType[] QUERY_CONTEST_SALE_BY_ID_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.DOUBLE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE };

    

    /**
     * Represents the sql statement to query the contest sale via contest id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String QUERY_CONTEST_SALE_BY_CONTEST_ID_SQL = "SELECT "
            + "contest_sale_id, contest_id, sale_status_id, price, paypal_order_id, create_date, sale_reference_id, sale_type_id "
            + "FROM contest_sale WHERE contest_id=?";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query the contest sale via contest id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final DataType[] QUERY_CONTEST_SALE_BY_CONTEST_ID_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.DOUBLE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE };

    /**
     * Represents the sql statement to create contest sale.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String CREATE_CONTEST_SALE_SQL = "INSERT INTO contest_sale "
            + "(contest_sale_id, contest_id, sale_status_id, "
            + "price, paypal_order_id, sale_reference_id, sale_type_id) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Represents the sql statement to delete contest sale via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String DELETE_CONTEST_SALE_BY_ID_SQL = "DELETE FROM contest_sale "
            + "WHERE contest_sale_id=?";

    /**
     * Represents the sql statement to create project property.
     */
    private static final String CREATE_PROJECT_PROPERTY_SQL = "INSERT INTO project_info "
            + "(project_id, project_info_type_id, value, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";

    /**
     * Represents the SQL statement to insert a new project platform in to table project_platform
     *
     * @since 1.8
     */
    private static final String CREATE_PROJECT_PLATFORM_SQL = "INSERT INTO project_platform "
            + "(project_id, project_platform_id,"
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, CURRENT, ?, CURRENT)";

    /**
     * Represents the SQL statement to insert a new project group in to table project_group_xref
     */
    private static final String CREATE_PROJECT_GROUP_SQL = "INSERT INTO project_group_xref "
            + "(project_id, project_group_id,"
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, CURRENT, ?, CURRENT)";

    /**
     * Represents the sql statement to create project audit.
     */
    private static final String CREATE_PROJECT_AUDIT_SQL = "INSERT INTO project_audit "
            + "(project_audit_id, project_id, update_reason, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";

    /**
     * Represents the sql statement to update project.
     */
    private static final String UPDATE_PROJECT_SQL = "UPDATE project "
            + "SET project_status_id=?, project_category_id=?, modify_user=?, modify_date= ?, tc_direct_project_id=? "
            + "WHERE project_id=?";

    /**
     * Represents the sql statement to update contest sale.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String UPDATE_CONTEST_SALE_SQL = "UPDATE contest_sale "
            + "SET contest_id=?, sale_status_id=?, price=?, paypal_order_id=?, sale_reference_id=?, sale_type_id=? "
            + "WHERE contest_sale_id=?";

    /**
     * Represents the sql statement to update project property.
     */
    private static final String UPDATE_PROJECT_PROPERTY_SQL = "UPDATE project_info "
            + "SET value=?, modify_user=?, modify_date=CURRENT "
            + "WHERE project_id=? AND project_info_type_id=?";

    /**
     * Represents the sql statement to delete project properties.
     */
    private static final String DELETE_PROJECT_PROPERTIES_SQL = "DELETE FROM project_info "
            + "WHERE project_id=? AND project_info_type_id IN ";

    /**
     * Represents the sql statement to delete the given platforms of a given project.
     *
     * @since 1.8
     */
    private static final String DELETE_PROJECT_PLATFORMS_SQL = "DELETE FROM project_platform "
            + "WHERE project_id = ? AND project_platform_id IN ";

    /**
     * Represents the sql statement to delete the given groups of a given project.
     */
    private static final String DELETE_PROJECT_GROUPS_SQL = "DELETE FROM project_group_xref "
            + "WHERE project_id = ? AND project_group_id IN ";

    /**
     * Represents the sql statement to query project_spec for specified project_id.
     * 
     * <p>
     * Updated since 1.3.1 to add private description support
     * </p>
     * 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private static final String QUERY_PROJECT_SPEC_SQL = "SELECT " 
        + " ps1.project_spec_id, " 
        + " ps1.version, "
        + " ps1.detailed_requirements, "
        + " ps1.submission_deliverables, "
        + " ps1.environment_setup_instruction,  "
        + " ps1.final_submission_guidelines, "
        + " ps1.create_user, " 
        + " ps1.create_date, "
        + " ps1.modify_user, "
        + " ps1.modify_date, "
        + " ps1.private_description, "
        + " ps1.detailed_requirements_text, "
        + " ps1.final_submission_guidelines_text, "
        + " ps1.private_description_text "
        + " FROM project_spec as ps1 "
        + " WHERE ps1.project_id = ? "
        + " AND ps1.version = (SELECT max(ps2.version) FROM project_spec as ps2 WHERE ps2.project_id = ?)";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all project categories.
     */
    private static final DataType[] QUERY_SIMPLE_PROJECT_PERMISSION_DATA_COLUMN_TYPES = new DataType[] {
            Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE};

    /**
     * Represents the column types for query project spec.
     * 
     * <p>
     * Updated since 1.3.1 to add private description support
     * </p>
     */
    private static final DataType[] QUERY_PROJECT_SPEC_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.LONG_TYPE, 
        Helper.STRING_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};
    
    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all active projects of the given user.
     * 
     * @since BUGR-3706
     */
    private static final DataType[] QUERY_SIMPLE_PROJECT_INFO_BY_USER_COLUMN_TYPES = new DataType[] {
            Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
            Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,};
    
    /**
     * Represents the sql statement to create project spec.
     * 
     * <p>
     * Updated since 1.3.1 to add private description support
     * </p>
     * 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private static final String CREATE_PROJECT_SPEC_SQL = "INSERT INTO project_spec "
            + "(project_spec_id, project_id, version, " 
            + "detailed_requirements_text, submission_deliverables, environment_setup_instruction, final_submission_guidelines_text, "
            + "create_user, create_date, modify_user, modify_date, private_description_text) "
            + "VALUES (?, ?, 1, " 
            + "?, ?, ?, ?, "
            + "?, CURRENT, ?, CURRENT, ?)";
    
    /**
     * Represents the sql statement to update project spec.
     * 
     * Since the project_spec table maintains all the versions of the records, we just need to insert the entry with the next version.
     * 
     * <p>
     * Updated since 1.3.1 to add private description support
     * </p>
     * 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private static final String UPDATE_PROJECT_SPEC_SQL = "INSERT INTO project_spec "
            + "(project_spec_id, project_id, version, " 
            + "detailed_requirements_text, submission_deliverables, environment_setup_instruction, final_submission_guidelines_text, "
            + "create_user, create_date, modify_user, modify_date, private_description_text) "
            + "VALUES (?, ?, (select NVL(max(ps.version), 0)  + 1 from project_spec as ps where ps.project_id = ?), " 
            + "?, ?, ?, ?, "
            + "?, CURRENT, ?, CURRENT, ?)";
    
    /**
     * Represents the column types mapping for retrieving simple pipeline data.
     * @since 1.1.1
     */
    private static final DataType[] QUERY_SIMPLE_PIPELINE_DATA_COLUMN_TYPES = new DataType[] {
            Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.DATE_TYPE,
            Helper.DATE_TYPE, Helper.DATE_TYPE, Helper.DATE_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
            Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, 
            Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, 
            Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE};

    /**
     * Represents the sql statement to find the corresponding develop contest for the design contest.
     * 
     * @since 1.2.1
     */
    private static final String FIND_CORRESPONDING_DEVELOPMENT_CONTEST = "select q1.project_id from project p, project_info q1 "
        + "join project_info p1 on p1.value = q1.value and p1.project_info_type_id = 1 and p1.project_id = ? "
        + "join project_info q2 on q2.project_id = q1.project_id and q2.project_info_type_id = 2 "
        + "join project_info p2 on p2.value = q2.value and p2.project_info_type_id = 2 and p2.project_id = ? "
        + "join project_info q3 on q3.project_id = q1.project_id and q3.project_info_type_id = 3 "
        + "join project_info p3 on p3.value = q3.value and p3.project_info_type_id = 3 and p3.project_id = ? "
        + "where q1.project_info_type_id = 1 and p.project_id = q1.project_id and p.project_category_id = 2 "
        + " and p.project_status_id in (1,2,7) and q1.project_id <> ?";

    private static final String FIND_CORRESPONDING_DESIGN_CONTEST = "select q1.project_id from project p, project_info q1 "
        + "join project_info p1 on p1.value = q1.value and p1.project_info_type_id = 1 and p1.project_id = ? "
        + "join project_info q2 on q2.project_id = q1.project_id and q2.project_info_type_id = 2 "
        + "join project_info p2 on p2.value = q2.value and p2.project_info_type_id = 2 and p2.project_id = ? "
        + "join project_info q3 on q3.project_id = q1.project_id and q3.project_info_type_id = 3 "
        + "join project_info p3 on p3.value = q3.value and p3.project_info_type_id = 3 and p3.project_id = ? "
        + "where q1.project_info_type_id = 1 and p.project_id = q1.project_id and p.project_category_id = 1 "
        + " and p.project_status_id in (1,2,7) and q1.project_id <> ?";

    /**
     * The query to getContestId, it matches the External Reference ID, Component ID and Version ID.
     * @since 1.5.1
     */
    private static final String FIND_CORRESPONDING_CONTEST = "select q1.project_id from project p, project_info q1 "
            + "join project_info p1 on p1.value = q1.value and p1.project_info_type_id = 1 and p1.project_id = ? "
            + "join project_info q2 on q2.project_id = q1.project_id and q2.project_info_type_id = 2 "
            + "join project_info p2 on p2.value = q2.value and p2.project_info_type_id = 2 and p2.project_id = ? "
            + "join project_info q3 on q3.project_id = q1.project_id and q3.project_info_type_id = 3 "
            + "join project_info p3 on p3.value = q3.value and p3.project_info_type_id = 3 and p3.project_id = ? "
            + "where q1.project_info_type_id = 1 and p.project_id = q1.project_id "
            + " and p.project_status_id in (1,2,7) and q1.project_id <> ?";


     /**
     * query to check if it is dev only
     */
     private static final String QUERY_IS_DEV_ONLY_SQL = "SELECT 'devonly' "

      + " from project_info q1 , project q, project_info pi "
      + "   where q1.project_info_type_id = 1 and q1.project_id = q.project_id and q1.project_id <> pi.project_id and q1.value = pi.value "
      + "   and pi.project_info_type_id = 1 "
      + "   and (q.project_status_id = 1 or q.project_status_id = 7 or q.project_status_id = 2) and q.project_category_id = 1 "
      + "     and pi.project_id = ? ";
   
    /**
     * query types for QUERY_IS_DEV_ONLY_SQL
     */
     private static final DataType[]QUERY_IS_DEV_ONLY_SQL_COLUMN_TYPES = new DataType[] { Helper.STRING_TYPE};


    /**
     *  get all project ids by tc direct project
     *
     */
    private static final String QUERY_PROJECT_ID_BY_TC_DIRECT = "select project_id from project where tc_direct_project_id = ? and project_status_id != 3 ";


    /**
     *  get tc direct project by project
     *
     */
    private static final String QUERY_TC_DIRECT_PROJECT_BY_PROJECT_ID = "select tc_direct_project_id from project where project_id = ? ";


    /**
     *  get all project ids by tc direct project data type
     *
     */
    private static final DataType[] QUERY_PROJECT_ID_BY_TC_DIRECT_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE}; 

    /**
     *  get tc direct project by project data type
     *
     */
    private static final DataType[] QUERY_TC_DIRECT_PROJECT_BY_PROJECT_ID_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE}; 

    /**
     *  get all project ids by tc direct project
     *
     */
    private static final String QUERY_FORUM_ID = "select value from project_info where project_info_type_id = 4 and project_id = ? ";


    /**
     *  get all project ids by tc direct project data type
     *
     */
    private static final DataType[] QUERY_FORUM_ID_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE}; 

            
    /**
        * Represents the sql statement to insert the term of use.
        * @since 1.2.1
        */
    private static final String INSERT_PRIVATE_CONTEST_TERMS = "insert into "
        + "project_role_terms_of_use_xref (project_id, resource_role_id, terms_of_use_id) values (?, ?, ?)";
            
    /**
        * Represents the sql statement to select all term of use's id for a proejct.
        * @since 1.2.1
        */
    private static final String SELCT_PRIVATE_CONTEST_TERMS = 
        "select terms_of_use_id, resource_role_id  from client_terms_mapping where client_project_id = ?";

    /**
	 * 'final review' phase name
	 */
	private static final String PROJECT_PHASE_FINAL_REVIEW = "Final Review";

    /**
     * <p>
     * Represents the audit creation type.
     * </p>
     *
     * @since 1.1.2
     */
    private static final int AUDIT_CREATE_TYPE = 1;

    /**
     * <p>
     * Represents the audit deletion type.
     * </p>
     *
     * @since 1.1.2
     */
    private static final int AUDIT_DELETE_TYPE = 2;
    
    /**
     * <p>
     * Represents the audit update type.
     * </p>
     *
     * @since 1.1.2
     */
    private static final int AUDIT_UPDATE_TYPE = 3;
    
    /**
     * Represents the SQL statement to audit project info.
     * 
     * @since 1.1.2
     */
    private static final String PROJECT_INFO_AUDIT_INSERT_SQL = "INSERT INTO project_info_audit "
    	+ "(project_id, project_info_type_id, value, audit_action_type_id, action_date, action_user_id) "
    	+ "VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Represents the sql statement to query file types.
     *
     * @since 1.2
     */
    private static final String QUERY_FILE_TYPES_SQL = "SELECT "
        + "type.file_type_id, type.description, type.sort, type.image_file," + " type.extension, type.bundled_file "
        + "FROM file_type_lu AS type " + "JOIN project_file_type_xref AS xref "
        + "ON type.file_type_id=xref.file_type_id " + "WHERE xref.project_id=";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query file
     * types.
     *
     * @since 1.2
     */
    private static final DataType[] QUERY_FILE_TYPES_COLUMN_TYPES = {Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE, Helper.BOOLEAN_TYPE, Helper.STRING_TYPE, Helper.BOOLEAN_TYPE};

    /**
     * Represents the sql statement to query prizes.
     *
     * @since 1.2
     */
    private static final String QUERY_PRIZES_SQL = "SELECT "
        + "prize.prize_id, prize.place, prize.prize_amount, prize.number_of_submissions, "
        + "prize_type.prize_type_id, prize_type.prize_type_desc " + "FROM prize AS prize "
        + "JOIN prize_type_lu AS prize_type ON prize.prize_type_id=prize_type.prize_type_id "
        + "WHERE prize.project_id=";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query prizes.
     *
     * @since 1.2
     */
    private static final DataType[] QUERY_PRIZES_COLUMN_TYPES = {Helper.LONG_TYPE, Helper.LONG_TYPE,
        Helper.DOUBLE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to insert prize to the prize table.
     *
     * @since 1.2
     */
    private static final String CREATE_PRIZE_SQL = "INSERT INTO prize "
        + "(prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, "
        + "create_user, create_date, modify_user, modify_date) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    /**
     * Represents the sql statement to update prize to the prize table.
     *
     * @since 1.2
     */
    private static final String UPDATE_PRIZE_SQL = "UPDATE prize "
        + "SET project_id=?, place=?, prize_amount=?, prize_type_id=?, number_of_submissions=?, modify_user=?, "
        + "modify_date=?  WHERE prize_id=";


    /**
     * Represents the sql statement to delete the prize by the specified prize id.
     *
     * @since 1.2
     */
    private static final String DELETE_PRIZE_SQL = "DELETE FROM prize WHERE prize_id=?";
    /**
     * Represents the sql statement to query prize types from the prize_type_lu table.
     *
     * @since 1.2
     */
    private static final String QUERY_ALL_PRIZE_TYPES_SQL = "SELECT " + "prize_type_id, description FROM prize_type_lu";
    /**
     * Represents the column types for the result set which is returned by querying prize types from the prize_type_lu
     * table.
     *
     * @since 1.2
     */
    private static final DataType[] QUERY_ALL_PRIZE_TYPES_COLUMN_TYPES = {Helper.LONG_TYPE, Helper.STRING_TYPE};
    /**
     * Represents the sql statement to query file types from the file_type_lu table.
     *
     * @since 1.2
     */
    private static final String QUERY_ALL_FILE_TYPES_SQL = "SELECT file_type_id, description, sort, image_file, "
        + "extension, bundled_file FROM file_type_lu";
    /**
     * Represents the column types for the result set which is returned by querying file types from the file_type_lu
     * table.
     *
     * @since 1.2
     */
    private static final DataType[] QUERY_ALL_FILE_TYPES_COLUMN_TYPES = {Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE, Helper.BOOLEAN_TYPE, Helper.STRING_TYPE, Helper.BOOLEAN_TYPE};

    /**
     * Represents the sql statement to delete the project file types reference by the specified file type id.
     *
     * @since 1.2
     */
    private static final String DELETE_PROJECT_FILE_TYPE_XREF_SQL = "DELETE FROM project_file_type_xref "
        + "WHERE file_type_id=?";
    /**
     * Represents the sql statement to delete the file types by the specified file type id.
     *
     * @since 1.2
     */
    private static final String DELETE_FILE_TYPE_SQL = "DELETE FROM file_type_lu WHERE file_type_id=?";
    /**
     * Represents the sql statement to update the file types by the specified file type id.
     *
     * @since 1.2
     */
    private static final String UPDATE_FILE_TYPE_SQL = "UPDATE file_type_lu "
        + "SET description=?, sort=?, image_file=?, extension=?, bundled_file=?, modify_user=?, modify_date=? "
        + "WHERE file_type_id=";
    /**
     * Represents the sql statement to create the file type.
     *
     * @since 1.2
     */
    private static final String CREATE_FILE_TYPE_SQL = "INSERT INTO file_type_lu "
        + "(file_type_id, description, sort, image_file, extension, bundled_file, "
        + "create_user, create_date, modify_user, modify_date) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    /**
     * Represents the sql statement to create contest milestone association.
     *
     * @since 1.6
     */
    private static final String CREATE_CONTEST_MILESTONE_XREF_SQL = "INSERT INTO contest_milestone_xref "
            + "(contest_id, project_milestone_id,"
            + "create_user, create_date, modify_user, modify_date) " + "VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Represents the sql statement to update the contest milestone association.
     *
     * @since 1.6
     */
    private static final String UPDATE_CONTEST_MILESTONE_XREF_SQL = "UPDATE contest_milestone_xref "
            + "SET project_milestone_id = ?, modify_user = ?, modify_date = ? "
            + "WHERE contest_id=";

    /**
     * Represents the sql statement to delete the contest milestone association.
     *
     * @since 1.6
     */
    private static final String DELETE_CONTEST_MILESTONE_XREF_SQL = "DELETE FROM contest_milestone_xref "
            + "WHERE contest_id=?";

    /**
     * Represents the sql statement to delete all the contest milestone association for a milestone.
     *
     * @since 1.7
     */
    private static final String DELETE_MILESTONE_CONTESTS_XREF_SQL = "DELETE FROM contest_milestone_xref "
            + "WHERE project_milestone_id=?";

    /**
     * Represents the sql statement to get the contest milestone association.
     *
     * @since 1.6
     */
    private static final String QUERY_CONTEST_MILESTONE_XREF_SQL = "SELECT project_milestone_id "
            + "FROM contest_milestone_xref where contest_id = ?";

    /**
     * Represents the sql statement to insert the project file types reference with the provided project id.
     *
     * @since 1.2
     */
    private static final String DELETE_PROJECT_FILE_TYPES_XREF__WITH_PROJECT_ID_SQL
        = "DELETE FROM project_file_type_xref " + "WHERE project_id=?";
    /**
     * Represents the sql statement to insert the project file types reference data.
     *
     * @since 1.2
     */
    private static final String INSERT_PROJECT_FILE_TYPES_XREF_SQL
        = "INSERT INTO project_file_type_xref (project_id, file_type_id) VALUES (?, ?)";

    /**
     * Represents the sql statement to create studio specification data.
     *
     * @since 1.2
     */
    private static final String CREATE_STUDIO_SPEC_SQL
        = "INSERT INTO project_studio_specification (project_studio_spec_id, "
            + "goals, target_audience, branding_guidelines, disliked_design_websites, other_instructions, "
            + "winning_criteria, submitters_locked_between_rounds, round_one_introduction, round_two_introduction, "
            + "colors, fonts, layout_and_size, contest_introduction, contest_description_text, general_feedback, create_user, create_date, modify_user, modify_date)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Represents the sql statement to create mm specification data.
     *
     * @since 1.5.8
     */
    private static final String CREATE_MM_SPEC_SQL
        = "INSERT INTO project_mm_specification (project_mm_spec_id, "
            + "match_details, match_rules, problem_id, create_user, create_date, modify_user, modify_date)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    /**
     * Represents the sql statement to update studio specification data.
     *
     * @since 1.2
     */
    private static final String UPDATE_STUDIO_SPEC_SQL = "UPDATE project_studio_specification "
        + "SET goals=?, target_audience=?, branding_guidelines=?, disliked_design_websites=?, "
        + "other_instructions=?, winning_criteria=?, submitters_locked_between_rounds=?, "
        + "round_one_introduction=?, round_two_introduction=?, colors=?, fonts=?, "
        + "layout_and_size=?, contest_introduction=?, contest_description_text=?, general_feedback=?, modify_user=?, modify_date=? " + "WHERE project_studio_spec_id=";
    
    /**
     * Represents the sql statement to update mm specification data.
     *
     * @since 1.5.8
     */
    private static final String UPDATE_MM_SPEC_SQL = "UPDATE project_mm_specification "
        + "SET match_details=?, match_rules=?, problem_id=?, modify_user=?, modify_date=? " + "WHERE project_mm_spec_id=";

    /**
     * Represents the sql statement to delete studio specification data with the specified project studio specification
     * id.
     *
     * @since 1.2
     */
    private static final String DELETE_STUDIO_SPEC_SQL = "DELETE FROM project_studio_specification "
        + "WHERE project_studio_spec_id=?";

    /**
     * Represents the sql statement to set studio specification id to null for project table with the specified project
     * studio specification id.
     *
     * @since 1.2
     */
    private static final String SET_PROJECT_STUDIO_SPEC_SQL = "UPDATE project SET project_studio_spec_id="
        + "NULL WHERE project_studio_spec_id=?";
    /**
     * Represents the sql statement to query studio specification data with the specified project id.
     *
     * @since 1.2
     */
    private static final String QUERY_STUDIO_SPEC_SQL = "SELECT "
        + "spec.project_studio_spec_id, spec.goals, spec.target_audience, "
        + "spec.branding_guidelines, spec.disliked_design_websites, spec.other_instructions, "
        + "spec.winning_criteria, spec.submitters_locked_between_rounds, "
        + "spec.round_one_introduction, spec.round_two_introduction, spec.colors, "
        + "spec.fonts, spec.layout_and_size, spec.contest_introduction, spec.contest_description, spec.general_feedback, contest_description_text " + "FROM project_studio_specification AS spec JOIN project AS project "
        + "ON project.project_studio_spec_id=spec.project_studio_spec_id " + "WHERE project.project_id=";
    
    /**
     * Represents the sql statement to query marathon match specification data with the specified project id.
     *
     * @since 1.5.8
     */
    private static final String QUERY_MM_SPEC_SQL = "SELECT "
        + "spec.project_mm_spec_id, spec.match_details, spec.match_rules, "
        + "spec.problem_id, prob.name " + "FROM project_mm_specification AS spec JOIN project AS project "
        + "ON project.project_mm_spec_id=spec.project_mm_spec_id LEFT OUTER JOIN informixoltp:problem prob ON spec.problem_id = prob.problem_id "
        + "WHERE project.project_id=";
    
    /**
     * Represents the data types for the result set by querying studio specification data with the specified project id.
     *
     * @since 1.2
     */
    private static final DataType[] QUERY_STUDIO_SPEC_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE, Helper.BOOLEAN_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};
    
    /**
     * Represents the data types for the result set by querying mm specification data with the specified project id.
     *
     * @since 1.5.8
     */
    private static final DataType[] QUERY_MM_SPEC_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to set studio specification id for project table with the specified project id.
     *
     * @since 1.2
     */
    private static final String SET_PROJECT_STUDIO_SPEC_WITH_PROJECT_SQL = "UPDATE project SET project_studio_spec_id="
        + "? WHERE project.project_id=";
    
    /**
     * Represents the sql statement to set mm specification id for project table with the specified project id.
     *
     * @since 1.5.8
     */
    private static final String SET_PROJECT_MM_SPEC_WITH_PROJECT_SQL = "UPDATE project SET project_mm_spec_id="
        + "? WHERE project.project_id=";

    /**
     * Represents the sql statement to query project id for project table with the specified tc direct project id.
     *
     * @since 1.2
     */
    private static final String QUERY_PROJECT_IDS_SQL
        = "SELECT DISTINCT project_id FROM project WHERE tc_direct_project_id=";

    /**
     * Represents the data types for the result set by querying project id for project table.
     *
     * @since 1.2
     */
    private static final DataType[] QUERY_PROJECT_IDS__COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE};

    /**
     * Represents the sql statement to query project id for project table with the specified studio specification id.
     *
     * @since 1.2
     */
    private static final String QUERY_PROJECT_IDS_WITH_STUDIO_SPEC_SQL
        = "SELECT DISTINCT project_id FROM project WHERE project_studio_spec_id=";

    /**
     * Represents the sql statement to query project id for project table with the specified file type id.
     *
     * @since 1.2
     */
    private static final String QUERY_PROJECT_IDS_WITH_FILE_TYPE_SQL
        = "SELECT DISTINCT project_id FROM project_file_type_xref WHERE file_type_id=";
    /**
     * Represents the sql statement to query project id for project table with the specified prize id.
     *
     * @since 1.5.6
     */
    private static final String QUERY_PROJECT_IDS_WITH_PRIZE_SQL
        = "SELECT DISTINCT project_id FROM prize WHERE prize_id=";

    /**
     * Represents the sql statement to delete project copilot types for specified project id.
     * @since 1.4
     */
    private static final String DELETE_PROJECT_COPILOT_TYPES_SQL = "DELETE FROM project_copilot_type WHERE project_id = ?";

    /**
     * Represents the sql statement to create project copilot type cross reference.
     * @since 1.4
     */
    private static final String CREATE_PROJECT_COPILOT_TYPE_SQL = "INSERT INTO project_copilot_type "
            + "(project_id, project_copilot_type_id, create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Represents the sql statement to delete copilot contest extra infos for specified project id.
     * @since 1.4
     */
    private static final String DELETE_COPILOT_CONTEST_EXTRA_INFOS_SQL = "DELETE FROM copilot_contest_extra_info WHERE copilot_posting_contest_id = ?";

    /**
     * Represents the sql statement to create copilot contest extra infos cross reference.
     * @since 1.4
     */
    private static final String CREATE_COPILOT_CONTEST_EXTRA_INFO_SQL = "INSERT INTO copilot_contest_extra_info "
            + "(copilot_posting_contest_id, copilot_contest_extra_info_type_id, value, create_user, create_date, modify_user, modify_date)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Represents the sql statement to retrieve project copilot types for specified project id.
     * @since 1.4
     */
    private static final String QUERY_PROJECT_COPILOT_TYPES_SQL = "SELECT "
            + "lu.project_copilot_type_id, lu.name, lu.project_copilot_type_desc "
            + "FROM project_copilot_type_lu lu, project_copilot_type t "
            + "WHERE lu.project_copilot_type_id = t.project_copilot_type_id " + "AND t.project_id = ";

    /**
     * Represents the Represents the data types for querying project copilot types.
     * @since 1.4
     */
    private static final DataType[] QUERY_PROJECT_COPILOT_TYPES_COLUMN_TYPES = new DataType[] { Helper.LONG_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to retrieve copilot contest extra infos for specified project id.
     * @since 1.4
     */
    private static final String QUERY_COPILOT_CONTEST_EXTRA_INFOS_SQL = "SELECT "
            + "t.copilot_contest_extra_info_type_id, t.name, t.copilot_contest_extra_info_type_desc, i.value "
            + "FROM copilot_contest_extra_info_type t, copilot_contest_extra_info i "
            + "WHERE t.copilot_contest_extra_info_type_id = i.copilot_contest_extra_info_type_id "
            + "AND i.copilot_posting_contest_id = ";

    /**
     * Represents the Represents the data types for querying copilot contest extra infos.
     * @since 1.4
     */
    private static final DataType[] QUERY_COPILOT_CONTEST_EXTRA_INFOS_COLUMN_TYPES = new DataType[] { Helper.LONG_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to query checkpoint feedback for contest with the specified contest id.
     *
     * @since 1.5.4
     */
    private static final String QUERY_CHECKPOINT_FEEDBACK_SQL
        = "SELECT feedback FROM comp_milestone_feedback WHERE project_id = ";

    /**
     * Represents the data types for the result set by querying project id for project table.
     *
     * @since 1.5.4
     */
    private static final DataType[] QUERY_CHECKPOINT_FEEDBACK_COLUMN_TYPES = new DataType[]{Helper.STRING_TYPE};

    /**
     * Represents the sql statement to insert checkpoint feedback for contest.
     *
     * @since 1.5.4
     */
    private static final String INSERT_CHECKPOINT_FEEDBACK_SQL = "INSERT INTO comp_milestone_feedback "
            + "(project_id, feedback) VALUES (?, ?)";

    /**
     * Represents the sql statement to update checkpoint feedback for contest.
     *
     * @since 1.5.4
     */
    private static final String UPDATE_CHECKPOINT_FEEDBACK_SQL = "UPDATE comp_milestone_feedback "
            + "SET feedback = ? WHERE project_id = ";    

    /**
     * <p>
     * The factory instance used to create connection to the database. It is
     * initialized in the constructor using DBConnectionFactory component and
     * never changed after that. It will be used in various persistence methods
     * of this project.
     * </p>
     */
    private final DBConnectionFactory factory;

    /**
     * <p>
     * Represents the database connection name that will be used by
     * DBConnectionFactory. This variable is initialized in the constructor and
     * never changed after that. It will be used in create/update/retrieve
     * method to create connection. This variable can be null, which mean
     * connection name is not defined in the configuration namespace and default
     * connection will be created.
     * </p>
     */
    private final String connectionName;


	private final String namespace;

    /**
     * <p>
     * Represents the IDGenerator for project table. This variable is
     * initialized in the constructor and never change after that. It will be
     * used in createProject() method to generate new Id for project..
     * </p>
     */
    private final IDGenerator projectIdGenerator;

    
    /**
     * <p>
     * Represents the IDGenerator for file_type table. This variable is initialized in the constructor and never change
     * after that. It will be used in createFileType() method to generate new Id for project. It could not be null after
     * initialized.
     * </p>
     *
     * @since 1.2
     */
    private final IDGenerator fileTypeIdGenerator;
     
	 /**
     * <p>
     * Represents the IDGenerator for contest_sale table. This variable is
	 * initialized in the constructor and never change after that. It will be
     * used in createContestSale() method to generate new Id for contest_sale.
     * </p>
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private final IDGenerator contestSaleIdGenerator;
    /**
     * <p>
     * Represents the IDGenerator for prize table. This variable is initialized in the constructor and never change
     * after that. It will be used in createPrize() method to generate new Id for project. It could not be null after
     * initialized.
     * </p>
     *
     * @since 1.2
     */
    private final IDGenerator prizeIdGenerator;
    
    /**
     * <p>
     * Represents the IDGenerator for project audit table. This variable is
     * initialized in the constructor and never change after that. It will be
     * used in updateProject() method to store update reason.
     * </p>
     */
    private final IDGenerator projectAuditIdGenerator;

    /**
     * <p>
     * Represents the IDGenerator for project_studio_specification table. This variable is initialized in the
     * constructor and never change after that. It will be used in createProjectStudioSpecification() method to generate
     * new Id for project. It could not be null after initialized.
     * </p>
     *
     * @since 1.2
     */
    private final IDGenerator studioSpecIdGenerator;
    
    /**
     * <p>
     * Represents the IDGenerator for project_mm_specification table. This variable is initialized in the
     * constructor and never change after that. It will be used in createProjectMMSpecification() method to generate
     * new Id for project. It could not be null after initialized.
     * </p>
     *
     * @since 1.5.8
     */
    private final IDGenerator mmSpecIdGenerator;
    
	/**
     * <p>
     * Represents the IDGenerator for project spec table. This variable is
     * initialized in the constructor and never change after that.
     * </p>
     * 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private final IDGenerator projectSpecIdGenerator;

    /**
     * <p>
     * Creates a new instance of <code>AbstractProjectPersistence</code> from the settings in configuration.
     * </p>
     * <p>
     * The following parameters are configured.
     * <ul>
     * <li>ConnectionFactoryNS: The namespace that contains settings for DB Connection Factory. It is required.</li>
     * <li>ConnectionName: The name of the connection that will be used by DBConnectionFactory to create connection. If
     * missing, default connection will be created. It is optional.</li>
     * <li>ProjectIdSequenceName: The sequence name used to create Id generator for project Id. If missing default value
     * (project_id_seq) is used. It is optional.</li>
     * <li>ProjectAuditIdSequenceName: The sequence name used to create Id generator for project audit Id. If missing,
     * default value (project_audit_id_seq) is used. It is optional.</li>
     * </ul>
     * </p>
     * <p>
     * Configuration sample:
     *
     * <pre>
     * &lt;Config name="InformixProjectPersistence.CustomNamespace"&gt;
     *     &lt;!-- The DBConnectionFactory class name used to create DB Connection Factory, required --&gt;
     *     &lt;Property name="ConnectionFactoryNS"&gt;
     *         &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
     *     &lt;/Property&gt;
     *     &lt;!-- the connection name to retrieve connection in DB Connection Factory, required --&gt;
     *     &lt;Property name="ConnectionName"&gt;
     *         &lt;Value&gt;informix_connection&lt;/Value&gt;
     *     &lt;/Property&gt;
     *     &lt;Property name="ProjectIdSequenceName"&gt;
     *         &lt;Value&gt;project_id_seq&lt;/Value&gt;
     *     &lt;/Property&gt;
     *     &lt;Property name="ProjectAuditIdSequenceName"&gt;
     *         &lt;Value&gt;project_audit_id_seq&lt;/Value&gt;
     *     &lt;/Property&gt;
     *     &lt;Property name="FileTypeIdGeneratorSequenceName"&gt;
     *         &lt;Value&gt;file_type_id_seq&lt;/Value&gt;
     *     &lt;/Property&gt;
     *     &lt;Property name="PrizeIdGeneratorSequenceName"&gt;
     *         &lt;Value&gt;prize_id_seq&lt;/Value&gt;
     *     &lt;/Property&gt;
     *     &lt;Property name="StudioSpecIdGeneratorSequenceName"&gt;
     *         &lt;Value&gt;studio_spec_id_seq&lt;/Value&gt;
     *     &lt;/Property&gt;
     * &lt;/Config&gt;
     * </pre>
     *
     * </p>
     * <p>
     * Updated in version 1.2: get sequence name and create following IdGenerators.
     * <ul>
     * <li>fileTypeIdGenerator</li>
     * <li>prizeIdGenerator</li>
     * <li>studioSpecIdGenerator</li>
     * </ul>
     * </p>
     *
     * @param namespace
     *            The namespace to load configuration setting.
     * @throws IllegalArgumentException
     *             if the input is null or empty string.
     * @throws ConfigurationException
     *             if error occurs while loading configuration settings, or required configuration parameter is missing.
     * @throws PersistenceException
     *             if cannot initialize the connection to the database.
     */
    @SuppressWarnings("deprecation")
    protected AbstractInformixProjectPersistence(String namespace) throws ConfigurationException, PersistenceException {
        Helper.assertStringNotNullNorEmpty(namespace, "namespace");

		this.namespace = namespace;


        // get the instance of ConfigManager
        ConfigManager cm = ConfigManager.getInstance();

        // get db connection factory namespace
        String factoryNamespace = Helper.getConfigurationParameterValue(cm,
                namespace, CONNECTION_FACTORY_NAMESPACE_PARAMETER, true, getLogger());

        // try to create a DBConnectionFactoryImpl instance from
        // factoryNamespace
        try {
            factory = new DBConnectionFactoryImpl(factoryNamespace);
        } catch (Exception e) {
            throw new ConfigurationException(
                    "Unable to create a new instance of DBConnectionFactoryImpl class"
                            + " from namespace [" + factoryNamespace + "].", e);
        }

        // get the connection name
        connectionName = Helper.getConfigurationParameterValue(cm, namespace,
                CONNECTION_NAME_PARAMETER, false, getLogger());

        // try to get project id sequence name
        String projectIdSequenceName = Helper.getConfigurationParameterValue(
                cm, namespace, PROJECT_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if project id sequence name is not provided
        if (projectIdSequenceName == null) {
            projectIdSequenceName = PROJECT_ID_SEQUENCE_NAME;
        }

        // try to get contest sale id sequence name
        String contestSaleIdSequenceName = Helper.getConfigurationParameterValue(
                cm, namespace, CONTEST_SALE_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if contest sale id sequence name is not provided
        if (contestSaleIdSequenceName == null) {
        	contestSaleIdSequenceName = CONTEST_SALE_ID_SEQUENCE_NAME;
        }

        // try to get project audit id sequence name
        String projectAuditIdSequenceName = Helper
                .getConfigurationParameterValue(cm, namespace,
                        PROJECT_AUDIT_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if project audit id sequence name is not provided
        if (projectAuditIdSequenceName == null) {
            projectAuditIdSequenceName = PROJECT_AUDIT_ID_SEQUENCE_NAME;
        }
        
        //
        // try to get file type id sequence name
        String fileTypeIdGeneratorSequenceName = Helper.getConfigurationParameterValue(cm, namespace,
            FILE_TYPE_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if file type id sequence name is not provided
        if (fileTypeIdGeneratorSequenceName == null) {
            fileTypeIdGeneratorSequenceName = FILE_TYPE_ID_SEQUENCE_NAME;
        }

        // try to get prize id sequence name
        String prizeIdGeneratorSequenceName = Helper.getConfigurationParameterValue(cm, namespace,
            PRIZE_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if prize id sequence name is not provided
        if (prizeIdGeneratorSequenceName == null) {
            prizeIdGeneratorSequenceName = PRIZE_ID_SEQUENCE_NAME;
        }

        // try to get studio spec id sequence name
        String studioSpecIdGeneratorSequenceName = Helper.getConfigurationParameterValue(cm, namespace,
            STUDIO_SPEC_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if studio spec id sequence name is not provided
        if (studioSpecIdGeneratorSequenceName == null) {
            studioSpecIdGeneratorSequenceName = STUDIO_SPEC_ID_SEQUENCE_NAME;
        }
        
        // try to get mm spec id sequence name
        String mmSpecIdGeneratorSequenceName = Helper.getConfigurationParameterValue(cm, namespace,
            MM_SPEC_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if mm spec id sequence name is not provided
        if (mmSpecIdGeneratorSequenceName == null) {
            mmSpecIdGeneratorSequenceName = MM_SPEC_ID_SEQUENCE_NAME;
        }
        
		// try to get project spec id sequence name
        // since Cockpit Launch Contest - Update for Spec Creation v1.0
        //
        String projectSpecIdSequenceName = Helper
                .getConfigurationParameterValue(cm, namespace,
                        PROJECT_SPEC_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if project spec id sequence name is not provided
        if (projectSpecIdSequenceName == null) {
            projectSpecIdSequenceName = PROJECT_SPEC_ID_SEQUENCE_NAME;
        }

        // try to get the IDGenerators
        try {
            projectIdGenerator = IDGeneratorFactory
                    .getIDGenerator(projectIdSequenceName);
        } catch (IDGenerationException e) {
        	getLogger().log(Level.ERROR, "The projectIdSequence [" + projectIdSequenceName+"] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '"
                    + projectIdSequenceName + "'.", e);
        }
        try {
            contestSaleIdGenerator = IDGeneratorFactory
                    .getIDGenerator(contestSaleIdSequenceName);
        } catch (IDGenerationException e) {
        	getLogger().log(Level.ERROR, "The contestSaleIdSequence [" + contestSaleIdSequenceName+"] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '"
                    + contestSaleIdSequenceName + "'.", e);
        }
        
		try {
            fileTypeIdGenerator = IDGeneratorFactory.getIDGenerator(fileTypeIdGeneratorSequenceName);
        } catch (IDGenerationException e) {
            getLogger().log(Level.ERROR,
                "The fileTypeIdGeneratorSequence [" + fileTypeIdGeneratorSequenceName + "] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '" + fileTypeIdGeneratorSequenceName
                + "'.", e);
        }
        
		try {
            projectAuditIdGenerator = IDGeneratorFactory
                    .getIDGenerator(projectAuditIdSequenceName);
        } catch (IDGenerationException e) {
        	getLogger().log(Level.ERROR, "The projectAuditIdSequence [" + projectAuditIdSequenceName +"] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '"
                    + projectAuditIdSequenceName + "'.", e);
        }
        
        
        try {
            prizeIdGenerator = IDGeneratorFactory.getIDGenerator(prizeIdGeneratorSequenceName);
        } catch (IDGenerationException e) {
            getLogger().log(Level.ERROR,
                "The prizeIdGeneratorSequence [" + prizeIdGeneratorSequenceName + "] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '" + prizeIdGeneratorSequenceName + "'.",
                e);
        }
        try {
            studioSpecIdGenerator = IDGeneratorFactory.getIDGenerator(studioSpecIdGeneratorSequenceName);
        } catch (IDGenerationException e) {
            getLogger().log(Level.ERROR,
                "The studioSpecIdGeneratorSequence [" + studioSpecIdGeneratorSequenceName + "] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '" + studioSpecIdGeneratorSequenceName
                + "'.", e);
        }
        
        try {
            mmSpecIdGenerator = IDGeneratorFactory.getIDGenerator(mmSpecIdGeneratorSequenceName);
        } catch (IDGenerationException e) {
            getLogger().log(Level.ERROR,
                "The mmSpecIdGeneratorSequence [" + mmSpecIdGeneratorSequenceName + "] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '" + mmSpecIdGeneratorSequenceName
                + "'.", e);
        }
        
		//
        // create the instance of project spec id generator.
        // since Cockpit Launch Contest - Update for Spec Creation v1.0
        //
		try {
            projectSpecIdGenerator = IDGeneratorFactory
                    .getIDGenerator(projectSpecIdSequenceName);
        } catch (IDGenerationException e) {
            getLogger().log(Level.ERROR, "The projectSpecIdSequence [" + projectSpecIdSequenceName +"] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '"
                    + projectSpecIdSequenceName + "'.", e);
        }
    }

    /**
     * <p>
     * Creates the project in the database using the given project instance.
     * </p>
     * <p>
     * The project information is stored to 'project' table, while its
     * properties are stored in 'project_info' table. The project's associating
     * scorecards are stored in 'project_scorecard' table. For the project, its
     * properties and associating scorecards, the operator parameter is used as
     * the creation/modification user and the creation date and modification
     * date will be the current date time when the project is created.
     * </p>
     * @param project The project instance to be created in the database.
     * @param operator The creation user of this project.
     * @throws IllegalArgumentException if any input is null or the operator is
     *             empty string.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public void createProject(Project project, String operator)
        throws PersistenceException {
        Helper.assertObjectNotNull(project, "project");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        // newId will contain the new generated Id for the project
        Long newId;
      
        
        getLogger().log(Level.INFO, new LogMessage(null, operator, 
        		"creating new project: " + project.getAllProperties()));

        
        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (project.getId() > 0) {
                if (Helper.checkEntityExists("project", "project_id", project
                        .getId(), conn)) {
                	throw new PersistenceException(
                            "The project with the same id [" + project.getId()
                                    + "] already exists.");
                }
            }

            try {
                // generate id for the project
                newId = new Long(projectIdGenerator.getNextID());
                getLogger().log(Level.INFO, new LogMessage(newId, operator, "generate id for new project"));
            } catch (IDGenerationException e) {
                throw new PersistenceException(
                        "Unable to generate id for the project.", e);
            }

            // create the project
            createProject(newId, project, operator, conn);

            // set the file types
            createOrUpdateProjectFileTypes(newId, project.getProjectFileTypes(), conn, operator, false);

            // set the prizes
			createOrUpdateProjectPrizes(newId, project.getPrizes(), conn, operator, false);
			
            // set the project studio specification

            if (project.getProjectCategory().getProjectType().getId() == ProjectType.STUDIO.getId())
            {
                createOrUpdateProjectStudioSpecification(newId, project.getProjectStudioSpecification(), conn, operator);
            }
            
            if (project.getProjectCategory().getId() == PROJECT_CATEGORY_MM)
            {
                createOrUpdateProjectMMSpecification(newId, project.getProjectMMSpecification(), conn, operator);
            }

            // set project copilot types and copilot contest extra info for copilot posting contest
            if (project.getProjectCategory().getId() == ProjectCategory.COPILOT_POSTING.getId()) {
                createOrUpdateProjectCopilotTypes(newId, project.getProjectCopilotTypes(), conn, operator, false);
                createOrUpdateCopilotContestExtraInfos(newId, project.getCopilotContestExtraInfos(), conn, operator, false);
            }

            closeConnection(conn);
        } catch (PersistenceException e) {
            /*project.setCreationUser(null);
            project.setCreationTimestamp(null);
            project.setModificationUser(null);
            project.setModificationTimestamp(null); */
        	getLogger().log(Level.ERROR,
        			new LogMessage(null, operator, "Fails to create project " + project.getAllProperties(), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        // set the newId when no exception occurred
        project.setId(newId.longValue());

        
    }

    /**
     * <p>
     * Update the given project instance into the database.
     * </p>
     * <p>
     * The project information is stored to 'project' table, while its
     * properties are stored in 'project_info' table. The project's associating
     * scorecards are stored in 'project_scorecard' table. All related items in
     * these tables will be updated. If items are removed from the project, they
     * will be deleted from the persistence. Likewise, if new items are added,
     * they will be created in the persistence. For the project, its properties
     * and associating scorecards, the operator parameter is used as the
     * modification user and the modification date will be the current date time
     * when the project is updated. An update reason is provided with this
     * method. Update reason will be stored in the persistence for future
     * references.
     * </p>
     * @param project The project instance to be updated into the database.
     * @param reason The update reason. It will be stored in the persistence for
     *            future references.
     * @param operator The modification user of this project.
     * @throws IllegalArgumentException if any input is null or the operator is
     *             empty string. Or project id is zero.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public void updateProject(Project project, String reason, String operator)
        throws PersistenceException {
        Helper.assertObjectNotNull(project, "project");
        Helper.assertLongPositive(project.getId(), "project id");
        Helper.assertObjectNotNull(reason, "reason");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        // modifyDate will contain the modify_date retrieved from database.
        Date modifyDate;

        Connection conn = null;
        
        getLogger().log(Level.INFO, new LogMessage(new Long(project.getId()), operator, 
        		"updating project: " + project.getAllProperties()));
        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", project
                    .getId(), conn)) {
                throw new PersistenceException("The project id ["
                        + project.getId() + "] does not exist in the database.");
            }

            // update the project
            updateProject(project, reason, operator, conn);

            getLogger().log(Level.INFO, new LogMessage(new Long(project.getId()), operator,
                    "execute sql:" + "SELECT modify_date " + "FROM project WHERE project_id=?"));
            // get the modification date.
            modifyDate = (Date) Helper.doSingleValueQuery(conn,
                    "SELECT modify_date " + "FROM project WHERE project_id=?",
                    new Object[] {new Long(project.getId())},
                    Helper.DATE_TYPE);
			
			// set the file types
            createOrUpdateProjectFileTypes(project.getId(), project.getProjectFileTypes(), conn, operator, true);

            // set the prizes
			if (project.getProjectStatus().getId() == ProjectStatus.ACTIVE.getId() || 
					project.getProjectStatus().getId() == ProjectStatus.DRAFT.getId()){
				createOrUpdateProjectPrizes(project.getId(), project.getPrizes(), conn, operator, true);
			}

            if (project.getProjectCategory().getProjectType().getId() == ProjectType.STUDIO.getId())
            {
                // set the project studio specification
                createOrUpdateProjectStudioSpecification(project.getId(), project.getProjectStudioSpecification(), conn, operator);          
            }
            
            if (project.getProjectCategory().getId() == PROJECT_CATEGORY_MM)
            {
                // set the project marathon match specification
                createOrUpdateProjectMMSpecification(project.getId(), project.getProjectMMSpecification(), conn, operator);          
            }

            // set project copilot types and copilot contest extra info for copilot posting contest
            if (project.getProjectCategory().getId() == ProjectCategory.COPILOT_POSTING.getId()) {
                createOrUpdateProjectCopilotTypes(project.getId(), project.getProjectCopilotTypes(), conn, operator, true);
                createOrUpdateCopilotContestExtraInfos(project.getId(), project.getCopilotContestExtraInfos(), conn, operator, true);
            }

            closeConnection(conn);
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR,
        			new LogMessage(null, operator, "Fails to update project " + project.getAllProperties(), e));
        	if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        // set the modification user and date when no exception
        // occurred.
        project.setModificationUser(operator);
        project.setModificationTimestamp(modifyDate);
    }

    /**
     * Retrieves the project instance from the persistence given its id. The
     * project instance is retrieved with its related items, such as properties
     * and scorecards.
     * @return The project instance.
     * @param id The id of the project to be retrieved.
     * @throws IllegalArgumentException if the input id is less than or equal to
     *             zero.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public Project getProject(long id) throws PersistenceException {
        Helper.assertLongPositive(id, "id");

        Project[] projects = getProjects(new long[] {id});
        return projects.length == 0 ? null : projects[0];
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence given their
     * ids. The project instances are retrieved with their properties.
     * </p>
     * @param ids ids The ids of the projects to be retrieved.
     * @return An array of project instances.
     * @throws PersistenceException
     * @throws IllegalArgumentException if the input id is less than or equal to
     *             zero.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public Project[] getProjects(long[] ids) throws PersistenceException {
        Helper.assertObjectNotNull(ids, "ids");

        // check if ids is empty
        if (ids.length == 0) {
            throw new IllegalArgumentException(
                    "Array 'ids' should not be empty.");
        }

        String idstring = "";
        // check if ids contains an id that is <= 0
        for (int i = 0; i < ids.length; ++i) {
        	idstring += ids[i] + ",";
            if (ids[i] <= 0) {
                throw new IllegalArgumentException(
                        "Array 'ids' contains an id that is <= 0.");
            }
        }

        Connection conn = null;

        getLogger().log(Level.INFO, "get projects with the ids: " + idstring.substring(0, idstring.length() - 1));
        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Project[] projects = getProjects(ids, conn);
            closeConnection(conn);
            return projects;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieving projects with ids: " + idstring.substring(0, idstring.length() - 1), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        } catch (ParseException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieving projects with ids: " + idstring.substring(0, idstring.length() - 1), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            
            throw new PersistenceException("Fails to retrieve projects", e);
        }
    }

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
    public Project[] getProjectsByCreateDate(int days)
        throws PersistenceException {
       
        Connection conn = null;

        getLogger().log(Level.INFO, "get projects by create date: " + days);
        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Project[] projects = getProjectsByCreateDate(days, conn);
            closeConnection(conn);
            return projects;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieving by create date: " + days, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }
    
    /**
     * Gets the general feedback for software checkpoint submissions.
     * 
     * @param contestId the contest id
     * @return the general feedback, or null if there's no matching record in DB
     * @throws IllegalArgumentException if the argument is non-positive
     * @throws PersistenceException if any other error occurs
     * @since 1.5.4
     */
    public String getSoftwareCheckpointSubmissionsGeneralFeedback(long contestId) throws PersistenceException {
        Helper.assertLongPositive(contestId, "contestId");

        Connection conn = null;
        String signature = "AbstractInformixProjectPersistence#getSoftwareCheckpointSubmissionsGeneralFeedback(long)";
        getLogger().log(Level.INFO, "Enter " + signature + " with contestId: " + contestId);
        try {
            // create the connection
            conn = openConnection();

            // do query
            Object[][] rows = Helper.doQuery(conn, QUERY_CHECKPOINT_FEEDBACK_SQL + contestId, new Object[]{},
                QUERY_CHECKPOINT_FEEDBACK_COLUMN_TYPES);

            String res = null;
            if (rows.length > 0) {
                res = (String) rows[0][0];
                if (res == null)res = "";
            }
            closeConnection(conn);
            getLogger().log(Level.INFO, "Exit " + signature + " with " + res);
            return res;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fails to retrieve general feedback.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }
    
    /**
     * Saves the general feedback for software checkpoint submissions. If the feedback don't exist it will create
     * a new record, otherwise update it.
     * 
     * @param contestId the contest id
     * @param feedback the general feedback
     * @throws IllegalArgumentException if the argument contestId is non-positive
     * @throws PersistenceException if any other error occurs
     * @since 1.5.4
     */
    public void saveSoftwareCheckpointSubmissionsGeneralFeedback(long contestId, String feedback)
        throws PersistenceException {
        Helper.assertLongPositive(contestId, "contestId");

        Connection conn = null;
        String signature = "AbstractInformixProjectPersistence#saveSoftwareCheckpointSubmissionsGeneralFeedback" + 
            "(long, String)";
        getLogger().log(Level.INFO, "Enter " + signature + " with contestId: " + contestId + ", feedback: " + feedback);        

        // check if exist
        String oldFeedback = getSoftwareCheckpointSubmissionsGeneralFeedback(contestId);
        try {
            // create the connection
            conn = openConnection();

            // insert or update
            if (oldFeedback == null) {
                Helper.doDMLQuery(conn, INSERT_CHECKPOINT_FEEDBACK_SQL, new Object[]{contestId, feedback});
            } else {
                Helper.doDMLQuery(conn, UPDATE_CHECKPOINT_FEEDBACK_SQL + contestId, new Object[]{feedback});
            }
            
            closeConnection(conn);
            getLogger().log(Level.INFO, "Exit " + signature);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fails to save general feedback.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }
    
    /**
     * Gets an array of all project types in the persistence. The project types
     * are stored in 'project_type_lu' table.
     * @return An array of all project types in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getAllProjectTypes method."));
        try {
            // create the connection
            conn = openConnection();

            // get all the project types
            ProjectType[] projectTypes = getAllProjectTypes(conn);
            closeConnection(conn);
            return projectTypes;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,"Fail to getAllProjectTypes.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all project categories in the persistence. The project
     * categories are stored in 'project_category_lu' table.
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public ProjectCategory[] getAllProjectCategories()
        throws PersistenceException {
        Connection conn = null;
        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getAllProjectCategories method."));
        try {
            // create the connection
            conn = openConnection();

            // get all categories
            ProjectCategory[] projectCategories = getAllProjectCategories(conn);

            closeConnection(conn);
            return projectCategories;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,"Fail to getAllProjectCategories.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all project statuses in the persistence. The project
     * statuses are stored in 'project_status_lu' table.
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        Connection conn = null;
        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getAllProjectStatuses method."));
        try {
            // create the connection
            conn = openConnection();

            // get all the project statuses
            ProjectStatus[] projectStatuses = getAllProjectStatuses(conn);
            closeConnection(conn);
            return projectStatuses;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,"Fail to getAllProjectStatuses.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all project property type in the persistence. The
     * project property types are stored in 'project_info_type_lu' table.
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes()
        throws PersistenceException {
    	getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getAllProjectPropertyTypes method."));
        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            ProjectPropertyType[] propertyTypes = getAllProjectPropertyTypes(conn);
            closeConnection(conn);
            return propertyTypes;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,"Fail to getAllProjectPropertyTypes.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets all the project platforms.
     *
     * @return all the project platforms.
     * @throws PersistenceException if any error.
     *
     * @since 1.8
     */
    public ProjectPlatform[] getAllProjectPlatforms() throws PersistenceException {
        Connection conn = null;
        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getAllProjectPlatforms method."));
        try {
            // create the connection
            conn = openConnection();

            // get all platforms
            ProjectPlatform[] projectPlatforms = getAllProjectPlatforms(conn);

            closeConnection(conn);
            return projectPlatforms;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getAllProjectPlatforms.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }


    /**
     * Gets all project groups.
     *
     * @return all the project groups.
     * @throws PersistenceException if there is any error.
     */
    public ProjectGroup[] getAllProjectGroups() throws PersistenceException {
        Connection conn = null;
        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getAllProjectGroups method."));
        try {
            // create the connection
            conn = openConnection();

            // get all groups
            ProjectGroup[] projectGroups = getAllProjectGroups(conn);

            closeConnection(conn);
            return projectGroups;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getAllProjectGroups.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
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
        Helper.assertLongPositive(directProjectId, "directProjectId");

        Connection conn = null;

        getLogger().log(Level.INFO, "get projects with the direct project id: " + directProjectId);
        try {
            // create the connection
            conn = openConnection();

            // find all the project ids with the specified direct project id in the table
            Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_IDS_SQL + directProjectId, new Object[]{},
                QUERY_PROJECT_IDS__COLUMN_TYPES);

            if (0 == rows.length) {
                return new Project[0];
            }

            long[] ids = new long[rows.length];

            for (int i = 0; i < rows.length; i++) {
                ids[i] = (Long) rows[i][0];
            }

            // get the project objects
            Project[] projects = getProjects(ids, conn);
            closeConnection(conn);
            return projects;
        } catch (PersistenceException e) {
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to retrieving projects with the direct project id: "
                    + directProjectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        } catch (ParseException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieving projects with the direct project id: "
                    + directProjectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            
            throw new PersistenceException("Fails to retrieve projects", e);
        }
    }
	
	/**
     * Retrieve scorecard id with given project type and scorecard type.
	 *
     * @param projectTypeId   the project type id
     * @param scorecardTypeId the scorecard type id
     * @return the scorecard id
     */
    public long getScorecardId(long projectTypeId, int scorecardTypeId) throws PersistenceException
	{

		getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getScorecardId method."));
        Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // create the connection
            conn = openConnection();

            ps = conn.prepareStatement("SELECT scorecard_id FROM default_scorecard " +
                    "WHERE project_category_id = ? and scorecard_type_id = ? ");
            ps.setLong(1, projectTypeId);
            ps.setInt(2, scorecardTypeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("scorecard_id");
            } else {
                throw new RuntimeException("Cannot find default scorecard id for project type: " +
                        projectTypeId + ", scorecard type: " + scorecardTypeId);
            }

        } catch (Exception e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,"Fail to getScorecardId.", e));
			if (rs != null)
			{
				try
				{
					rs.close();	
				}
				catch (Exception ee)
				{
				}
				
			}
			if (ps != null)
			{
				try
				{
					ps.close();
				}
				catch (Exception ee)
				{
				}
				
			}
            if (conn != null) {
                closeConnectionOnError(conn);
                conn = null;
            }
            throw new PersistenceException(e.getMessage());
        } finally {
            if (rs != null)
			{
				try
				{
					rs.close();	
				}
				catch (Exception ee)
				{
				}
				
			}
			if (ps != null)
			{
				try
				{
					ps.close();
				}
				catch (Exception ee)
				{
				}
				
			}
            if (conn != null) {
                closeConnection(conn);
            }
        }


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
        Helper.assertLongPositive(projectId, "projectId");

        Connection conn = null;

        getLogger().log(Level.INFO, "get Project file types with the project id: " + projectId);
        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            }

            // find file types in the table
            Object[][] rows = Helper.doQuery(conn, QUERY_FILE_TYPES_SQL + projectId, new Object[]{},
                QUERY_FILE_TYPES_COLUMN_TYPES);

            FileType[] fileTypes = new FileType[rows.length];

            // enumerate each row
            for (int i = 0; i < rows.length; ++i) {
                Object[] row = rows[i];

                FileType fileType = new FileType();
                fileType.setId((Long) row[0]);
                fileType.setDescription((String) row[1]);
                fileType.setSort(((Long) row[2]).intValue());
                fileType.setImageFile((Boolean) row[3]);
                fileType.setExtension((String) row[4]);
                fileType.setBundledFile((Boolean) row[5]);

                fileTypes[i] = fileType;
            }

            closeConnection(conn);
            return fileTypes;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieving project file types with project id: " + projectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
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
        Helper.assertLongPositive(projectId, "projectId");
        Helper.assertObjectNotNull(fileTypes, "fileTypes");
        for (int i = 0; i < fileTypes.size(); i++) {
            Helper.assertObjectNotNull(fileTypes.get(i), "fileTypes[" + i + "]");
        }
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO,
            new LogMessage(null, operator, "updating the file types for the project with id: " + projectId));

        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            }

            createOrUpdateProjectFileTypes(projectId, fileTypes, conn, operator, true);

            // create project audit record into project_audit table
            createProjectAudit(projectId, "Updates the project file types", operator, conn);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger()
                .log(
                    Level.ERROR,
                    new LogMessage(null, operator, "Fails to update the file types for the project with id "
                        + projectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Creates or updates project file types.
     *
     * @param projectId
     *            the project id
     * @param fileTypes
     *            the file types for the project
     * @param conn
     *            the database connection
     * @param operator
     *            the given audit user.
     * @param update
     *            true to update the project file types reference; otherwise, just create the project file types
     *            reference
     * @throws PersistenceException
     *             if any error occurs
     * @since 1.2
     */
    private void createOrUpdateProjectFileTypes(long projectId, List<FileType> fileTypes, Connection conn,
        String operator, boolean update) throws PersistenceException {
        if (fileTypes == null) {
            return;
        }
        Object[] queryArgs = null;

        if (update) {

            getLogger().log(Level.INFO,
                "delete the project file typs reference from database with the specified project id: " + projectId);
            // delete the project file types reference from database with the specified project id
            queryArgs = new Object[]{projectId};
            Helper.doDMLQuery(conn, DELETE_PROJECT_FILE_TYPES_XREF__WITH_PROJECT_ID_SQL, queryArgs);
        }

        for (FileType fileType : fileTypes) {
            // the file type with the specified file type id exists, just update it
            if (fileType.getId() > 0
                && Helper.checkEntityExists("file_type_lu", "file_type_id", fileType.getId(), conn)) {
                updateFileType(fileType, operator);
            } else { // the file type with the specified file types id does not exist, insert it to the database
                createFileType(fileType, operator);
            }

            // insert projectId and file type id into project_file_type_xref table
            getLogger().log(
                Level.INFO,
                "insert projectId: " + projectId + " and file type id: " + fileType.getId()
                    + " into project_file_type_xref table");

            queryArgs = new Object[]{projectId, fileType.getId()};
            Helper.doDMLQuery(conn, INSERT_PROJECT_FILE_TYPES_XREF_SQL, queryArgs);
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
        Helper.assertLongPositive(projectId, "projectId");

        Connection conn = null;

        getLogger().log(Level.INFO, "get project prizes with the project id: " + projectId);
        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            }

            // find prizes in the table
            Object[][] rows = Helper.doQuery(conn, QUERY_PRIZES_SQL + projectId, new Object[]{},
                QUERY_PRIZES_COLUMN_TYPES);

            Prize[] prizes = new Prize[rows.length];
            // enumerate each row
            for (int i = 0; i < rows.length; ++i) {
                Object[] row = rows[i];

                Prize prize = new Prize();
                prize.setId((Long) row[0]);
                // set the projectId here.
                prize.setProjectId(projectId);
                prize.setPlace(((Long) row[1]).intValue());
                prize.setPrizeAmount((Double) row[2]);
                prize.setNumberOfSubmissions(((Long) row[3]).intValue());
                PrizeType prizeType = new PrizeType();
                prizeType.setId((Long) row[4]);
                prizeType.setDescription((String) row[5]);
                prize.setPrizeType(prizeType);

                prizes[i] = prize;
            }

            closeConnection(conn);
            return prizes;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieving project prizes with project id: " + projectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
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
        Helper.assertLongPositive(projectId, "projectId");
        Helper.assertObjectNotNull(prizes, "prizes");
        for (int i = 0; i < prizes.size(); i++) {
            Helper.assertObjectNotNull(prizes.get(i), "prizes[" + i + "]");
        }
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO,
            new LogMessage(null, operator, "updating the prizes for the project with id: " + projectId));

        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            }

            createOrUpdateProjectPrizes(projectId, prizes, conn, operator, true);

            // create project audit record into project_audit table
            createProjectAudit(projectId, "Updates the project prizes", operator, conn);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR,
                new LogMessage(null, operator, "Fails to update the prizes for the project with id " + projectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Creates or updates project prizes.
     *
     * @param projectId
     *            the project id
     * @param prizes
     *            the prizes for the project
     * @param conn
     *            the database connection
     * @param operator
     *            the given audit user.
     * @param update
     *            true to update the project prizes reference; otherwise, just create the project prizes reference
     * @throws PersistenceException
     *             if any error occurs
     * @since 1.2
     */
    private void createOrUpdateProjectPrizes(long projectId, List<Prize> prizes, Connection conn, String operator,
        boolean update) throws PersistenceException {
        if (prizes == null) {
            return;
        }
        if (update) {
            getLogger().log(Level.INFO,
                "delete the project prize reference from database with the specified project id: " + projectId);
            Prize[] oldPrizes = getProjectPrizes(projectId);
            Set<Long> ids = new HashSet<Long>();
            for (Prize prize : prizes) {
                ids.add(prize.getId());
            }
            for (Prize oldPrize : oldPrizes) {
                if (!ids.contains(oldPrize.getId())) {
                    removePrize(oldPrize, operator);
                }
            }
        }

        for (Prize prize : prizes) {
        	prize.setProjectId(projectId);
            // the prize with the specified prize id exists, just update it
            if (prize.getId() > 0 && Helper.checkEntityExists("prize", "prize_id", prize.getId(), conn)) {
                updatePrize(prize, operator);
            } else { // the prize with the specified prize id does not exist, insert it to the database
                createPrize(prize, operator);
            }

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
        Helper.assertObjectNotNull(fileType, "fileType");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        // newId will contain the new generated Id for the file type
        Long newId = null;

        getLogger().log(Level.INFO, new LogMessage(null, operator, "creating new file type: " + fileType));

        try {
            // create the connection
            conn = openConnection();

            // check whether the file type id is already in the database
            if (fileType.getId() > 0) {
                if (Helper.checkEntityExists("file_type_lu", "file_type_id", fileType.getId(), conn)) {
                    throw new PersistenceException("The file type with the same id [" + fileType.getId()
                        + "] already exists.");
                }
            }

            try {
                // generate id for the file type
                newId = new Long(fileTypeIdGenerator.getNextID());
                getLogger().log(Level.INFO, new LogMessage(newId, operator, "generate id for new file type"));
            } catch (IDGenerationException e) {
                throw new PersistenceException("Unable to generate id for the file type.", e);
            }

            // create the file type
            getLogger().log(Level.INFO, "insert record into file type with id:" + newId);

            Timestamp createDate = new Timestamp(System.currentTimeMillis());

            // insert the file type into database
            Object[] queryArgs = new Object[]{newId, fileType.getDescription(), fileType.getSort(),
                convertBooleanToString(fileType.isImageFile()), fileType.getExtension(),
                convertBooleanToString(fileType.isBundledFile()), operator, createDate, operator, createDate};
            Helper.doDMLQuery(conn, CREATE_FILE_TYPE_SQL, queryArgs);

            closeConnection(conn);

            fileType.setCreationUser(operator);
            fileType.setCreationTimestamp(createDate);
            fileType.setModificationUser(operator);
            fileType.setModificationTimestamp(createDate);

            // set the newId when no exception occurred
            fileType.setId(newId.longValue());
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, operator, "Fails to create file type " + newId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        return fileType;
    }

    /**
     * Converts the boolean value to a string representation. For true, we use 't'; For false, we use 'f'.
     *
     * @param booleanVal
     *            the boolean value to convert
     * @return 't' if the paramter is true; otherwise, returns 'f'
     * @since 1.2
     */
    private Object convertBooleanToString(boolean booleanVal) {
        return booleanVal ? "t" : "f";
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
        Helper.assertObjectNotNull(fileType, "fileType");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO,
            new LogMessage(null, operator, "updating the file type with id: " + fileType.getId()));

        // modifyDate will contain the modify_date retrieved from database.
        Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
        try {
            // create the connection
            conn = openConnection();

            // check whether the file type id is already in the database
            if (!Helper.checkEntityExists("file_type_lu", "file_type_id", fileType.getId(), conn)) {
                throw new PersistenceException("The file type id [" + fileType.getId()
                    + "] does not exist in the database.");
            }

            // update the file type into database
            Object[] queryArgs = new Object[]{fileType.getDescription(), fileType.getSort(),
                convertBooleanToString(fileType.isImageFile()), fileType.getExtension(),
                convertBooleanToString(fileType.isBundledFile()), operator, modifyDate};
            Helper.doDMLQuery(conn, UPDATE_FILE_TYPE_SQL + fileType.getId(), queryArgs);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, operator, "Fails to update file type " + fileType, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        fileType.setModificationUser(operator);
        fileType.setModificationTimestamp(modifyDate);
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
        Helper.assertObjectNotNull(fileType, "fileType");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO,
            new LogMessage(null, operator, "deleting the file type with id: " + fileType.getId()));

        try {
            // create the connection
            conn = openConnection();

            // check whether the file type id is already in the database
            if (!Helper.checkEntityExists("file_type_lu", "file_type_id", fileType.getId(), conn)) {
                throw new PersistenceException("The file type id [" + fileType.getId()
                    + "] does not exist in the database.");
            }

            // delete the project file type reference from database
            Object[] queryArgs = new Object[]{fileType.getId()};

            Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_IDS_WITH_FILE_TYPE_SQL + fileType.getId(),
                new Object[]{}, QUERY_PROJECT_IDS__COLUMN_TYPES);

            // create project audit record into project_audit table
            auditProjects(rows, conn, "Removes the project file type", operator);

            Helper.doDMLQuery(conn, DELETE_PROJECT_FILE_TYPE_XREF_SQL, queryArgs);

            // delete the file type from database
            Helper.doDMLQuery(conn, DELETE_FILE_TYPE_SQL, queryArgs);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, operator, "Fails to delete file type " + fileType, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
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
        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getAllFileTypes method."));
        try {
            // create the connection
            conn = openConnection();

            // get all the file types
            Object[][] rows = Helper.doQuery(conn, QUERY_ALL_FILE_TYPES_SQL, new Object[]{},
                QUERY_ALL_FILE_TYPES_COLUMN_TYPES);

            closeConnection(conn);

            // create the FileType array.
            FileType[] fileTypes = new FileType[rows.length];

            for (int i = 0; i < rows.length; ++i) {
                Object[] row = rows[i];

                // create a new instance of FileType class
                fileTypes[i] = new FileType();
                fileTypes[i].setId((Long) row[0]);
                fileTypes[i].setDescription((String) row[1]);
                fileTypes[i].setSort(((Long) row[2]).intValue());
                fileTypes[i].setImageFile((Boolean) row[3]);
                fileTypes[i].setExtension((String) row[4]);
                fileTypes[i].setBundledFile((Boolean) row[5]);
            }

            return fileTypes;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getAllFileTypes.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Creates the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @param milestoneId the id of the direct project milestone
     * @param operator the operator
     * @throws PersistenceException if any error occurs
     * @since 1.6
     */
    public void createProjectMilestoneRelation(long projectId, long milestoneId, String operator)
            throws PersistenceException {
        Helper.assertLongPositive(projectId, "projectId");
        Helper.assertLongPositive(milestoneId, "milestoneId");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null, operator,
                                                   "creating new contest milestone relation: [contest ID]:"
                                                           + projectId + " [project milestone ID]:" + milestoneId));

        try {
            // create the connection
            conn = openConnection();

            // check whether the contest id is already in the database
            if (projectId > 0) {
                if (Helper.checkEntityExists("contest_milestone_xref", "contest_id", projectId, conn)) {
                    throw new PersistenceException("The contest_milestone_xref record with the same id [" + projectId
                                                           + "] already exists.");
                }
            }
            Timestamp createDate = new Timestamp(System.currentTimeMillis());

            // insert the file type into database
            Object[] queryArgs = new Object[]{projectId, milestoneId, operator, createDate, operator, createDate};
            Helper.doDMLQuery(conn, CREATE_CONTEST_MILESTONE_XREF_SQL, queryArgs);

            closeConnection(conn);

        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, operator,
                                                        "Fails to create contest_milestone_xref record [contestId, milestoneId]: ["
                                                                + projectId + ", " + milestoneId + "]", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

    }

    /**
     * Updates the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @param milestoneId the id of the direct project milestone
     * @param operator the operator
     * @throws PersistenceException if any error occurs
     * @since 1.6
     */
    public void updateProjectMilestoneRelation(long projectId, long milestoneId, String operator)
            throws PersistenceException {
        Helper.assertLongPositive(projectId, "projectId");
        Helper.assertLongPositive(milestoneId, "milestoneId");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO,
                        new LogMessage(null, operator, "updating the contest_milestone_xref with contest id: " + projectId));

        // modifyDate will contain the modify_date retrieved from database.
        Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
        try {
            // create the connection
            conn = openConnection();

            // check whether the contest_milestone_xref with the given contest id is already in the database
            if (!Helper.checkEntityExists("contest_milestone_xref", "contest_id", projectId, conn)) {
                throw new PersistenceException("The contest_milestone_xref record with contest id [" + projectId
                                                       + "] does not exist in the database.");
            }

            // update contest_milestone_xref into database
            Object[] queryArgs = new Object[]{milestoneId, operator, modifyDate};
            Helper.doDMLQuery(conn, UPDATE_CONTEST_MILESTONE_XREF_SQL + projectId, queryArgs);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, operator, "Fails to update contest_milestone_xref with contest ID: " + projectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Deletes the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @param operator the operator
     * @throws PersistenceException if any error occurs
     * @since 1.6
     */
    public void deleteProjectMilestoneRelation(long projectId, String operator) throws PersistenceException {
        Helper.assertLongPositive(projectId, "projectId");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO,
                        new LogMessage(null, operator, "deleting the contest_milestone_xref record with contest id: " + projectId));

        try {
            // create the connection
            conn = openConnection();

            // check whether the contest_milestone_xref record is already in the database
            if (!Helper.checkEntityExists("contest_milestone_xref", "contest_id", projectId, conn)) {
                throw new PersistenceException("The contest_milestone_xref record with contest id [" + projectId
                                                       + "] does not exist in the database.");
            }

            // delete the contest_milestone_xref reference from database
            Object[] queryArgs = new Object[]{projectId};

            Helper.doDMLQuery(conn, DELETE_CONTEST_MILESTONE_XREF_SQL, queryArgs);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, operator, "Fails to delete contest_milestone_xref with contest ID: " + projectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Deletes all the contests associations to the specified milestone.
     *
     * @param milestoneId the id of the milestone.
     * @param operator the operator.
     * @throws PersistenceException if any error occurs.
     * @since 1.7
     */
    public void deleteMilestoneProjectRelations(long milestoneId, String operator) throws PersistenceException {
        Helper.assertLongPositive(milestoneId, "milestoneId");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO,
                new LogMessage(null, operator, "deleting the contest_milestone_xref record with milestone id: " + milestoneId));

        try {
            // create the connection
            conn = openConnection();

            // delete the contest_milestone_xref reference from database
            Object[] queryArgs = new Object[]{milestoneId};

            Helper.doDMLQuery(conn, DELETE_MILESTONE_CONTESTS_XREF_SQL, queryArgs);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, operator, "Fails to delete contest_milestone_xref with milestone ID: " + milestoneId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets the contest milestone relationship.
     *
     * @param projectId the id of the contest
     * @return the project milestone id
     * @throws PersistenceException if any error occurs
     * @since 1.6
     */
    public long getProjectMilestoneRelation(long projectId) throws PersistenceException {
        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getProjectMilestoneRelation method with contest ID:" + projectId));
        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Object[][] rows = Helper.doQuery(conn,
                                             QUERY_CONTEST_MILESTONE_XREF_SQL,
                                             new Object[]{projectId},
                                             new DataType[]{Helper.LONG_TYPE});

            getLogger().log(Level.INFO, new LogMessage(null, null, "Found " + rows.length + " records"));

            long rst = 0;

            if (rows.length != 0) {
                rst = ((Long) rows[0][0]).longValue();
            }

            getLogger().log(Level.INFO, new LogMessage(null, null, "The milestone id is: " + rst));

            closeConnection(conn);
            getLogger().log(Level.INFO, new LogMessage(null, null, "Exit getProjectMilestoneRelation method."));

            return rst;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getProjectMilestoneRelation.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
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
        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getPrizeTypes method."));
        try {
            // create the connection
            conn = openConnection();

            // get all the prize types
            Object[][] rows = Helper.doQuery(conn, QUERY_ALL_PRIZE_TYPES_SQL, new Object[]{},
                QUERY_ALL_PRIZE_TYPES_COLUMN_TYPES);

            // create the PrizeType array.
            PrizeType[] prizeTypes = new PrizeType[rows.length];

            for (int i = 0; i < rows.length; ++i) {
                Object[] row = rows[i];

                // create a new instance of PrizeType class
                prizeTypes[i] = new PrizeType();
                prizeTypes[i].setId((Long) row[0]);
                prizeTypes[i].setDescription((String) row[1]);
            }

            closeConnection(conn);
            return prizeTypes;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getPrizeTypes.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
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
     *             if prize is null, or prize.projectId is not long positive number, or prize.prizeType is null, 
     *             or if operator is null or empty.
     * @throws PersistenceException
     *             if there are any exceptions.
     * @since 1.2
     */
    public Prize createPrize(Prize prize, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(prize, "prize");
        Helper.assertLongPositive(prize.getProjectId(), "prize.projectId");
        Helper.assertObjectNotNull(prize.getPrizeType(), "prize.prizeType");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        // newId will contain the new generated Id for the prize
        Long newId = null;

        getLogger().log(Level.INFO, new LogMessage(null, operator, "creating new prize: " + prize));

        try {
            // create the connection
            conn = openConnection();

            // check whether the prize id is already in the database
            if (prize.getId() > 0) {
                if (Helper.checkEntityExists("prize", "prize_id", prize.getId(), conn)) {
                    throw new PersistenceException(
                        "The prize with the same id [" + prize.getId() + "] already exists.");
                }
            }

            try {
                // generate id for the prize
                newId = new Long(prizeIdGenerator.getNextID());
                getLogger().log(Level.INFO, new LogMessage(newId, operator, "generate id for new prize"));
            } catch (IDGenerationException e) {
                throw new PersistenceException("Unable to generate id for the prize.", e);
            }

            // create the prize
            getLogger().log(Level.INFO, "insert record into prize with id:" + newId);

            Timestamp createDate = new Timestamp(System.currentTimeMillis());

            // insert the prize into database
            Object[] queryArgs = new Object[]{newId, new Long(prize.getProjectId()), new Long(prize.getPlace()), 
            		new Double(prize.getPrizeAmount()), new Long(prize.getPrizeType().getId()), 
            		prize.getNumberOfSubmissions(), operator, createDate, operator, createDate};
            Helper.doDMLQuery(conn, CREATE_PRIZE_SQL, queryArgs);

            closeConnection(conn);

            prize.setCreationUser(operator);
            prize.setCreationTimestamp(createDate);
            prize.setModificationUser(operator);
            prize.setModificationTimestamp(createDate);

            // set the newId when no exception occurred
            prize.setId(newId.longValue());
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, operator, "Fails to create prize " + newId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

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
        Helper.assertObjectNotNull(prize, "prize");
        Helper.assertLongPositive(prize.getProjectId(), "prize.projectId");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null, operator, "updating the prize with id: " + prize.getId()));

        // modifyDate will contain the modify_date retrieved from database.
        // modifyDate will contain the modify_date retrieved from database.
        Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
        try {
            // create the connection
            conn = openConnection();

            // check whether the prize id is already in the database
            if (!Helper.checkEntityExists("prize", "prize_id", prize.getId(), conn)) {
                throw new PersistenceException("The prize id [" + prize.getId() + "] does not exist in the database.");
            }

            // insert the prize into database
            Object[] queryArgs = new Object[]{new Long(prize.getProjectId()), new Long(prize.getPlace()), 
                new Double(prize.getPrizeAmount()), new Long(prize.getPrizeType().getId()), 
                prize.getNumberOfSubmissions(), operator, modifyDate};
            Helper.doDMLQuery(conn, UPDATE_PRIZE_SQL + prize.getId(), queryArgs);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, operator, "Fails to update prize " + prize, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        prize.setModificationUser(operator);
        prize.setModificationTimestamp(modifyDate);
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
        Helper.assertObjectNotNull(prize, "prize");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null, operator, "deleting the prize with id: " + prize.getId()));

        try {
            // create the connection
            conn = openConnection();

            // check whether the prize id is already in the database
            if (!Helper.checkEntityExists("prize", "prize_id", prize.getId(), conn)) {
                throw new PersistenceException("The prize id [" + prize.getId() + "] does not exist in the database.");
            }

            // delete the project prize reference from database
            Object[] queryArgs = new Object[]{prize.getId()};

            Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_IDS_WITH_PRIZE_SQL + prize.getId(), new Object[]{},
                QUERY_PROJECT_IDS__COLUMN_TYPES);

            // create project audit record into project_audit table
            auditProjects(rows, conn, "Removes the project prize", operator);

            // delete the prize from database
            Helper.doDMLQuery(conn, DELETE_PRIZE_SQL, queryArgs);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, operator, "Fails to delete prize " + prize, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
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
        Helper.assertObjectNotNull(spec, "spec");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        // newId will contain the new generated Id for the project studio specification
        Long newId = null;

        getLogger().log(Level.INFO,
            new LogMessage(null, operator, "creating new project studio specification: " + spec));

        try {
            // create the connection
            conn = openConnection();

            // check whether the project studio specification id is already in the database
            if (spec.getId() > 0) {
                if (Helper.checkEntityExists("project_studio_specification", "project_studio_spec_id", spec.getId(),
                    conn)) {
                    throw new PersistenceException("The project studio specification with the same id [" + spec.getId()
                        + "] already exists.");
                }
            }

            try {
                // generate id for the project studio specification
                newId = new Long(studioSpecIdGenerator.getNextID());
                getLogger().log(Level.INFO,
                    new LogMessage(newId, operator, "generate id for new project studio specification"));
            } catch (IDGenerationException e) {
                throw new PersistenceException("Unable to generate id for the project studio specification.", e);
            }

            // create the project studio specification
            getLogger().log(Level.INFO, "insert record into project studio specification with id:" + newId);

            Timestamp createDate = new Timestamp(System.currentTimeMillis());

            // insert the project studio specification into database
            Object[] queryArgs = new Object[]{newId, spec.getGoals(), spec.getTargetAudience(),
                spec.getBrandingGuidelines(), spec.getDislikedDesignWebSites(), spec.getOtherInstructions(),
                spec.getWinningCriteria(), spec.isSubmittersLockedBetweenRounds(), spec.getRoundOneIntroduction(),
                spec.getRoundTwoIntroduction(), spec.getColors(), spec.getFonts(), spec.getLayoutAndSize(), spec.getContestIntroduction(), spec.getContestDescription(), spec.getGeneralFeedback(), operator,
                createDate, operator, createDate};
            Helper.doDMLQuery(conn, CREATE_STUDIO_SPEC_SQL, queryArgs);

            closeConnection(conn);

            spec.setCreationUser(operator);
            spec.setCreationTimestamp(createDate);
            spec.setModificationUser(operator);
            spec.setModificationTimestamp(createDate);

            // set the newId when no exception occurred
            spec.setId(newId.longValue());
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR,
                new LogMessage(null, operator, "Fails to create project studio specification " + newId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        return spec;
    }
    
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
     * @since 1.5.8
     */
    public ProjectMMSpecification createProjectMMSpecification(ProjectMMSpecification spec, String operator)
        throws PersistenceException {
        Helper.assertObjectNotNull(spec, "spec");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        // newId will contain the new generated Id for the project mm specification
        Long newId = null;

        getLogger().log(Level.INFO,
            new LogMessage(null, operator, "creating new project marathon match specification: " + spec));

        try {
            // create the connection
            conn = openConnection();

            // check whether the project mm specification id is already in the database
            if (spec.getId() > 0) {
                if (Helper.checkEntityExists("project_mm_specification", "project_mm_spec_id", spec.getId(),
                    conn)) {
                    throw new PersistenceException("The project mm specification with the same id [" + spec.getId()
                        + "] already exists.");
                }
            }

            try {
                // generate id for the project mm specification
                newId = new Long(mmSpecIdGenerator.getNextID());
                getLogger().log(Level.INFO,
                    new LogMessage(newId, operator, "generate id for new project mm specification"));
            } catch (IDGenerationException e) {
                throw new PersistenceException("Unable to generate id for the project mm specification.", e);
            }

            // create the project mm specification
            getLogger().log(Level.INFO, "insert record into project mm specification with id:" + newId);

            Timestamp createDate = new Timestamp(System.currentTimeMillis());

            // insert the project mm specification into database
            Object[] queryArgs = new Object[]{newId, spec.getMatchDetails(), spec.getMatchRules(),
                spec.getProblemId(), operator, createDate, operator, createDate};
            Helper.doDMLQuery(conn, CREATE_MM_SPEC_SQL, queryArgs);

            closeConnection(conn);

            spec.setCreationUser(operator);
            spec.setCreationTimestamp(createDate);
            spec.setModificationUser(operator);
            spec.setModificationTimestamp(createDate);

            // set the newId when no exception occurred
            spec.setId(newId.longValue());
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR,
                new LogMessage(null, operator, "Fails to create project mm specification " + newId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

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
        Helper.assertObjectNotNull(spec, "spec");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO,
            new LogMessage(null, operator, "updating the project studio specification with id: " + spec.getId()));

        // modifyDate will contain the modify_date retrieved from database.
        Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
        try {
            // create the connection
            conn = openConnection();

            // check whether the project studio specification id is already in the database
            if (!Helper.checkEntityExists("project_studio_specification",
                "project_studio_spec_id", spec.getId(), conn)) {
                    throw new PersistenceException("The project studio specification id [" + spec.getId()
                        + "] does not exist in the database.");
            }

            // insert the project studio specification into database
            Object[] queryArgs = new Object[]{spec.getGoals(), spec.getTargetAudience(), spec.getBrandingGuidelines(),
                spec.getDislikedDesignWebSites(), spec.getOtherInstructions(), spec.getWinningCriteria(),
                spec.isSubmittersLockedBetweenRounds(), spec.getRoundOneIntroduction(), spec.getRoundTwoIntroduction(),
                spec.getColors(), spec.getFonts(), spec.getLayoutAndSize(), spec.getContestIntroduction(), spec.getContestDescription(), spec.getGeneralFeedback(), operator, modifyDate};
            Helper.doDMLQuery(conn, UPDATE_STUDIO_SPEC_SQL + spec.getId(), queryArgs);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR,
                new LogMessage(null, operator, "Fails to update project studio specification " + spec.getId(), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        spec.setModificationUser(operator);
        spec.setModificationTimestamp(modifyDate);
    }
    
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
     * @since 1.5.8
     */
    public void updateProjectMMSpecification(ProjectMMSpecification spec, String operator)
        throws PersistenceException {
        Helper.assertObjectNotNull(spec, "spec");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO,
            new LogMessage(null, operator, "updating the project marathon match specification with id: " + spec.getId()));

        // modifyDate will contain the modify_date retrieved from database.
        Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
        try {
            // create the connection
            conn = openConnection();

            // check whether the project mm specification id is already in the database
            if (!Helper.checkEntityExists("project_mm_specification",
                "project_mm_spec_id", spec.getId(), conn)) {
                    throw new PersistenceException("The project marathon match specification id [" + spec.getId()
                        + "] does not exist in the database.");
            }

            // insert the project mm specification into database
            Object[] queryArgs = new Object[]{spec.getMatchDetails(), spec.getMatchRules(), spec.getProblemId(), 
                operator, modifyDate};
            Helper.doDMLQuery(conn, UPDATE_MM_SPEC_SQL + spec.getId(), queryArgs);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR,
                new LogMessage(null, operator, "Fails to update project marathon match specification " + spec.getId(), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        spec.setModificationUser(operator);
        spec.setModificationTimestamp(modifyDate);
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
        Helper.assertObjectNotNull(spec, "spec");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO,
            new LogMessage(null, operator, "deleting the project studio specification with id: " + spec.getId()));

        try {
            // create the connection
            conn = openConnection();

            // check whether the project studio specification id is already in the database
            if (!Helper.checkEntityExists("project_studio_specification",
                "project_studio_spec_id", spec.getId(), conn)) {
                    throw new PersistenceException("The project studio specification id [" + spec.getId()
                        + "] does not exist in the database.");
            }

            // delete the project project studio specification reference from database
            Object[] queryArgs = new Object[]{spec.getId()};

            Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_IDS_WITH_STUDIO_SPEC_SQL + spec.getId(),
                new Object[]{}, QUERY_PROJECT_IDS__COLUMN_TYPES);

            // create project audit record into project_audit table
            auditProjects(rows, conn, "Removes the project studion specification", operator);

            Helper.doDMLQuery(conn, SET_PROJECT_STUDIO_SPEC_SQL, queryArgs);

            // delete the project studio specification from database
            Helper.doDMLQuery(conn, DELETE_STUDIO_SPEC_SQL, queryArgs);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR,
                new LogMessage(null, operator, "Fails to delete project studio specification " + spec.getId(), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    } 

    /**
     * Audits the projects.
     *
     * @param rows
     *            the rows containing the projects ids
     * @param conn
     *            the database connection
     * @param reason
     *            the reason to audit
     * @param operator the audit user
     * @throws PersistenceException
     *             if any error occurs
     * @since 1.2
     */
    private void auditProjects(Object[][] rows, Connection conn, String reason, String operator)
        throws PersistenceException {
        if (0 != rows.length) {
            for (int i = 0; i < rows.length; i++) {
                createProjectAudit((Long) rows[i][0], reason, operator, conn);
            }
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
        Helper.assertLongPositive(projectId, "projectId");

        Connection conn = null;

        getLogger().log(Level.INFO, "get project studio specification with the project id: " + projectId);
        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            }

            // find project studio specification in the table
            Object[][] rows = Helper.doQuery(conn, QUERY_STUDIO_SPEC_SQL + projectId, new Object[]{},
                QUERY_STUDIO_SPEC_COLUMN_TYPES);

            if (rows.length == 0) { // no project studio specification is found, return null
                closeConnection(conn);
                return null;
            }

            ProjectStudioSpecification studioSpec = new ProjectStudioSpecification();

            // sets the properties for the studio specification
            studioSpec.setId((Long) rows[0][0]);
            studioSpec.setGoals((String) rows[0][1]);
            studioSpec.setTargetAudience((String) rows[0][2]);
            studioSpec.setBrandingGuidelines((String) rows[0][3]);
            studioSpec.setDislikedDesignWebSites((String) rows[0][4]);
            studioSpec.setOtherInstructions((String) rows[0][5]);
            studioSpec.setWinningCriteria((String) rows[0][6]);
            studioSpec.setSubmittersLockedBetweenRounds((Boolean) rows[0][7]);
            studioSpec.setRoundOneIntroduction((String) rows[0][8]);
            studioSpec.setRoundTwoIntroduction((String) rows[0][9]);
            studioSpec.setColors((String) rows[0][10]);
            studioSpec.setFonts((String) rows[0][11]);
            studioSpec.setLayoutAndSize((String) rows[0][12]);
            studioSpec.setContestIntroduction((String) rows[0][13]);
            studioSpec.setContestDescription((String) rows[0][14]);
            studioSpec.setGeneralFeedback((String) rows[0][15]);

            if(rows[0][16] != null && ((String) rows[0][16]).trim().length() > 0 ) {
                studioSpec.setContestDescription((String) rows[0][16]);
            }
            closeConnection(conn);
            return studioSpec;
        } catch (PersistenceException e) {
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to retrieving project studio specification with project id: "
                    + projectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }
    
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
     * @since 1.5.8
     */
    public ProjectMMSpecification getProjectMMSpecification(long projectId) throws PersistenceException {
        Helper.assertLongPositive(projectId, "projectId");

        Connection conn = null;

        getLogger().log(Level.INFO, "get project mm specification with the project id: " + projectId);
        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            }

            // find project mm specification in the table
            Object[][] rows = Helper.doQuery(conn, QUERY_MM_SPEC_SQL + projectId, new Object[]{},
                QUERY_MM_SPEC_COLUMN_TYPES);

            if (rows.length == 0) { // no project mm specification is found, return null
                closeConnection(conn);
                return null;
            }

            ProjectMMSpecification mmSpec = new ProjectMMSpecification();

            // sets the properties for the mm specification
            mmSpec.setId((Long) rows[0][0]);
            mmSpec.setMatchDetails((String) rows[0][1]);
            mmSpec.setMatchRules((String) rows[0][2]);
            mmSpec.setProblemId((Long) rows[0][3]);
            mmSpec.setProblemName((String) rows[0][4]);

            closeConnection(conn);
            return mmSpec;
        } catch (PersistenceException e) {
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to retrieving project marathon match specification with project id: "
                    + projectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
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
        Helper.assertObjectNotNull(spec, "spec");
        Helper.assertLongPositive(projectId, "projectId");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO,
            new LogMessage(null, operator, "updating the studio specification for the project with id: " + projectId));

        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            }

            createOrUpdateProjectStudioSpecification(projectId, spec, conn, operator);
            

            // create project audit record into project_audit table
            createProjectAudit(projectId, "Updates the project studion specification", operator, conn);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, operator, "Fails to update the studio specification for the project with id "
                    + projectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }
    
    /**
     * Updates the given ProjectMMSpecification entity for specified project id.
     *
     * @param spec
     *            the given ProjectMMSpecification entity to update.
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
    public void updateMMSpecificationForProject(ProjectMMSpecification spec, long projectId, String operator)
        throws PersistenceException {
        Helper.assertObjectNotNull(spec, "spec");
        Helper.assertLongPositive(projectId, "projectId");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        getLogger().log(Level.INFO,
            new LogMessage(null, operator, "updating the mm specification for the project with id: " + projectId));

        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            }

            createOrUpdateProjectMMSpecification(projectId, spec, conn, operator);
            

            // create project audit record into project_audit table
            createProjectAudit(projectId, "Updates the project mmn specification", operator, conn);

            closeConnection(conn);
        } catch (PersistenceException e) {
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, operator, "Fails to update the mm specification for the project with id "
                    + projectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }
    
    /**
     * Returns the database connection name that will be used by DB Connection
     * Factory.
     * @return a possibly <code>null</code> string representing the connection name
     *         used in DB Connection Factory.
     */
    protected String getConnectionName() {
        return connectionName;
    }
    
	/**
     * Creates or updates project studio specification.
     *
     * @param projectId
     *            the project id
     * @param spec
     *            the studio specification for the project
     * @param conn
     *            the database connection
     * @param operator
     *            the given audit user
     * @throws PersistenceException
     *             if any error occurs
     * @since 1.2
     */
    private void createOrUpdateProjectStudioSpecification(long projectId, ProjectStudioSpecification spec,
        Connection conn, String operator) throws PersistenceException {
        if (spec == null) {
            return;
        }
        // the studio specification with the specified id exists, just update it
        if (spec.getId() > 0
            && Helper.checkEntityExists("project_studio_specification", "project_studio_spec_id", spec.getId(), conn)) {
            updateProjectStudioSpecification(spec, operator);
        } else { // the studio specification with the specified id does not exist, insert it to the database
            createProjectStudioSpecification(spec, operator);
        }

        // update the project studio specification reference for the project table
        Object[] queryArgs = new Object[]{spec.getId()};
        Helper.doDMLQuery(conn, SET_PROJECT_STUDIO_SPEC_WITH_PROJECT_SQL + projectId, queryArgs);
    }
    
    /**
     * Creates or updates project mm specification.
     *
     * @param projectId
     *            the project id
     * @param spec
     *            the mm specification for the project
     * @param conn
     *            the database connection
     * @param operator
     *            the given audit user
     * @throws PersistenceException
     *             if any error occurs
     * @since 1.5.8
     */
    private void createOrUpdateProjectMMSpecification(long projectId, ProjectMMSpecification spec,
        Connection conn, String operator) throws PersistenceException {
        if (spec == null) {
            return;
        }
        // the mm specification with the specified id exists, just update it
        if (spec.getId() > 0
            && Helper.checkEntityExists("project_mm_specification", "project_mm_spec_id", spec.getId(), conn)) {
            updateProjectMMSpecification(spec, operator);
        } else { // the mm specification with the specified id does not exist, insert it to the database
            createProjectMMSpecification(spec, operator);
        }

        // update the project mm specification reference for the project table
        Object[] queryArgs = new Object[]{spec.getId()};
        Helper.doDMLQuery(conn, SET_PROJECT_MM_SPEC_WITH_PROJECT_SQL + projectId, queryArgs);
    }

    /**
     * returns the <code>DBConnectionFactory</code> instance used to create
     * connection to the database.
     * @return the <code>DBConnectionFactory</code> instance used to create
     *         connection to the database.
     */
    protected DBConnectionFactory getConnectionFactory() {
        return factory;
    }

    /**
     * <p>Return the getLogger().</p>
     * @return the <code>Log</code> instance used to take the log message
     */
    protected abstract Log getLogger();
    /**
     * <p>
     * Abstract method used to get an open connection from which to perform DB
     * operations.
     * </p>
     * <p>
     * The implementations in subclasses will get a connection and properly
     * prepare it for use, depending on the transaction management strategy used
     * in the subclass.
     * </p>
     * @return an open connection used for DB operation.
     * @throws PersistenceException if there's a problem getting the Connection
     */
    protected abstract Connection openConnection() throws PersistenceException;

    /**
     * <p>
     * Abstract method used to commit any transaction (if necessary in the
     * subclass) and close the given connection after an operation completes
     * successfully.
     * </p>
     * <p>
     * It is used by all public methods after the successful execution of DB
     * operation.
     * </p>
     * <p>
     * The implementations in subclasses will close the given connection.
     * Depending on the transaction management strategy used in the subclass, a
     * transaction on the connection may be committed.
     * </p>
     * @param connection connection to close
     * @throws PersistenceException if any problem occurs trying to close the
     *             connection
     * @throws IllegalArgumentException if the argument is <code>null</code>
     */
    protected abstract void closeConnection(Connection connection)
        throws PersistenceException;

    /**
     * <p>
     * Abstract method used to rollback any transaction (if necessary in the
     * subclass) and close the given connection when an error occurs.
     * </p>
     * <p>
     * It is used by all public methods just before any exception is thrown if
     * fails to do DB operation..
     * </p>
     * <p>
     * The implementations in subclasses will close the given connection.
     * Depending on the transaction management strategy used in the subclass, a
     * transaction on the connection may be rolled back.
     * </p>
     * @param connection connection to close
     * @throws PersistenceException if any problem occurs closing the Connection
     * @throws IllegalArgumentException if the argument is <code>null</code>
     */
    protected abstract void closeConnectionOnError(Connection connection)
        throws PersistenceException;

    /**
     * Creates the project in the database using the given project instance.
     * <p>
     * Updated for Cockpit Launch Contest - Update for Spec Creation v1.0
     *      - added creation of project spec.
     * </p>
     * 
     * <p>
     * Updated for Version 1.1.2 -- from project property we determine whether standardCCA is required or not.
     * </p>
     * 
     * @param projectId The new generated project id
     * @param project The project instance to be created in the database.
     * @param operator The creation user of this project.
     * @param conn The database connection
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private void createProject(Long projectId, Project project,
            String operator, Connection conn) throws PersistenceException {

    	getLogger().log(Level.INFO, "insert record into project with id:" + projectId);
    	
    	//set tc_direct_project_id  
    	Long tcDirectProjectId;
        if(project.getTcDirectProjectId() > 0 ) {
        	tcDirectProjectId = new Long(project.getTcDirectProjectId());
        } else {
        	tcDirectProjectId = null;
        }
        
        // insert the project into database
        Object[] queryArgs = new Object[] {projectId,
            new Long(project.getProjectStatus().getId()),
            new Long(project.getProjectCategory().getId()), operator,
            operator, tcDirectProjectId};
        Helper.doDMLQuery(conn, CREATE_PROJECT_SQL, queryArgs);

        // get the creation date.
          // createDate will contain the create_date value retrieved from
        // database.
        Date    createDate = (Date) Helper.doSingleValueQuery(conn,
                    "SELECT create_date FROM project WHERE project_id=?",
                    new Object[] {projectId}, Helper.DATE_TYPE);

        // set the creation/modification user and date when no exception
        // occurred
        project.setCreationUser(operator);
        project.setCreationTimestamp(createDate);
        project.setModificationUser(operator);
        project.setModificationTimestamp(createDate);

        //
        // Added for Cockpit Launch Contest - Update for Spec Creation v1.0
        //
        if (project.getProjectCategory().getProjectType().getId()  !=  ProjectType.STUDIO.getId())
        {
            createProjectSpec(projectId, project.getProjectSpec(), operator, conn);
        }
        
        
        Map nameIdMap = makePropertyNamePropertyIdMap(getAllProjectPropertyTypes(conn));
        
        // get the property id - property value map from the project.
        Map idValueMap = makePropertyIdPropertyValueMap(project
                .getAllProperties(), conn, nameIdMap);
        
        // get the standard cca value from project property. 
        String value = (String) idValueMap.get(nameIdMap.get(PROJECT_INFO_CONFIDENTIALITY_TYPE_PROPERTY));

        // get the standard cca value from project property. 
        boolean standardCCA = (value != null && !value.equalsIgnoreCase(CONFIDENTIALITY_TYPE_PUBLIC));

        // get the billing project id
        long billingProjectId = new Long((String) idValueMap.get(nameIdMap.get(PROJECT_INFO_BILLING_PROJECT_PROPERTY)));

        boolean isStudio = isStudio(project);

        //insert the term of use for the project
        createRoleTermOfUse(projectId, billingProjectId, project.getProjectCategory().getId(), standardCCA, isStudio, conn);
        
        // create the project properties
        createProjectProperties(projectId, project, idValueMap, operator, conn);

        // create the project platforms
        createProjectPlatforms(projectId, project.getPlatforms(), operator, conn);

        // create the project groups
        createProjectGroups(projectId, project.getGroups(), operator, conn);
    }

    /**
     * <p>
     * Persists the specified ProjectSpec instance for the specified projectId.
     * It basically creates a new row in table corresponding to ProjectSpec - version number for the row is choosen as 1.
     * </p>
     * 
     * <p>
     * Updated since 1.3.1 to add private description support
     * </p> 
     * 
     * @param projectId the project id for which project spec need to be persisted.
     * @param projectSpec the project spec
     * @param operator the operator/user who is creating this project spec.
     * @param conn the db connection to be used to create this project spec
     * @throws PersistenceException exception is thrown when there is some error in persisting 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private void createProjectSpec(Long projectId, ProjectSpec projectSpec, String operator, Connection conn) throws PersistenceException {
        // check whether the project spec id is already in the database
        if (projectSpec.getProjectSpecId() > 0) {
            if (Helper.checkEntityExists("project_spec", "project_spec_id", projectSpec
                    .getProjectSpecId(), conn)) {
                throw new PersistenceException(
                        "The projectSpec with the same id [" + projectSpec.getProjectSpecId()
                                + "] already exists.");
            }
        }
        
        Long newId = 0L;
        
        try {
            // generate id for the project spec
            newId = new Long(projectSpecIdGenerator.getNextID());
            getLogger().log(Level.INFO, new LogMessage(newId, operator, "generate id for new project spec"));
        } catch (IDGenerationException e) {
            throw new PersistenceException(
                    "Unable to generate id for the project spec.", e);
        }
        
        getLogger().log(Level.INFO, "insert record into project_spec with id:" + newId);
        
        // insert the project spec into database
        Object[] queryArgs = new Object[] {newId,
            projectId,
            projectSpec.getDetailedRequirements() != null ? projectSpec.getDetailedRequirements() : "",
            projectSpec.getSubmissionDeliverables() != null ? projectSpec.getSubmissionDeliverables() : "",
            projectSpec.getEnvironmentSetupInstructions() != null ? projectSpec.getEnvironmentSetupInstructions() : "",
            projectSpec.getFinalSubmissionGuidelines() != null ? projectSpec.getFinalSubmissionGuidelines() : "",
            operator,
            operator,
            projectSpec.getPrivateDescription() != null ? projectSpec.getPrivateDescription() : ""};
        Helper.doDMLQuery(conn, CREATE_PROJECT_SPEC_SQL, queryArgs);
        
        // get the creation date.
        Date specCreateDate = (Date) Helper.doSingleValueQuery(conn,
                "SELECT create_date FROM project_spec WHERE project_spec_id=?",
                new Object[] {newId}, Helper.DATE_TYPE);
        
        // set the newId when no exception occurred
        projectSpec.setProjectSpecId(newId.longValue());

        // set the creation/modification user and date when no exception occurred
        projectSpec.setCreationUser(operator);
        projectSpec.setCreationTimestamp(specCreateDate);
        projectSpec.setModificationUser(operator);
        projectSpec.setModificationTimestamp(specCreateDate);
    }

    /**
     * Update the given project instance into the database.
     * 
     * <p>
     * Updated for Cockpit Launch Contest - Update for Spec Creation v1.0
     *      -- added update for project spec.
     * </p>
     * 
     * @param project The project instance to be updated into the database.
     * @param reason The update reason. It will be stored in the persistence for
     *            future references.
     * @param operator The modification user of this project.
     * @param conn the database connection
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private void updateProject(Project project, String reason, String operator,
            Connection conn) throws PersistenceException {
        Long projectId = new Long(project.getId());

        getLogger().log(Level.INFO, new LogMessage(projectId, operator,
        		"update project with projectId:" + projectId));
        // 
        Long tcDirectProjectId;
        if(project.getTcDirectProjectId() > 0 ) {
        	tcDirectProjectId = new Long(project.getTcDirectProjectId());
        } else {
        	tcDirectProjectId = null;
        }

        Map<Long, String> projectPropertiesBeforeUpdate = getProjectPropertyIdsAndValues(projectId, conn);

        Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
        // update the project type and project category
        Object[] queryArgs = new Object[]{new Long(project.getProjectStatus().getId()),
            new Long(project.getProjectCategory().getId()), operator, modifyDate, project.getTcDirectProjectId() == 0 ? null : project.getTcDirectProjectId(),
            projectId};
        Helper.doDMLQuery(conn, UPDATE_PROJECT_SQL, queryArgs);

       // update the project object so this data's correct for audit purposes
        project.setModificationUser(operator);
        project.setModificationTimestamp(modifyDate);
        
        //
        // Added for Cockpit Launch Contest - Update for Spec Creation v1.0
        //
        if (project.getProjectCategory().getProjectType().getId()  !=  ProjectType.STUDIO.getId())
        {
            updateProjectSpec(project.getId(), project.getProjectSpec(), operator, conn);
        }

        Map nameIdMap = makePropertyNamePropertyIdMap(getAllProjectPropertyTypes(conn));        
        // get the property id - property value map from the project.
        Map idValueMap = makePropertyIdPropertyValueMap(project
                .getAllProperties(), conn, nameIdMap);

        // get the old settings of CCA and billing account id before the update
        String oldCCASettingValue = projectPropertiesBeforeUpdate.get(
                nameIdMap.get(PROJECT_INFO_CONFIDENTIALITY_TYPE_PROPERTY));
        boolean oldStandardCCA = (oldCCASettingValue != null && !oldCCASettingValue.equalsIgnoreCase(CONFIDENTIALITY_TYPE_PUBLIC));
        String oldBillingIdValue = projectPropertiesBeforeUpdate.get(nameIdMap.get(PROJECT_INFO_BILLING_PROJECT_PROPERTY));
        long oldBillingProjectId = (oldBillingIdValue == null || oldBillingIdValue.trim().length() == 0) ? 0 : new Long(oldBillingIdValue);


        // get the standard cca value from project property.
        String ccaSettingValue = (String) idValueMap.get(nameIdMap.get(PROJECT_INFO_CONFIDENTIALITY_TYPE_PROPERTY));
        boolean standardCCA = (ccaSettingValue != null && !ccaSettingValue.equalsIgnoreCase(CONFIDENTIALITY_TYPE_PUBLIC));
        // get the billing project id value from project property.
        long billingProjectId = new Long((String) idValueMap.get(nameIdMap.get(PROJECT_INFO_BILLING_PROJECT_PROPERTY)));
        
        boolean isStudio = isStudio(project);


        // update / insert the term of use for the project ONLY WHEN billing account is changed or CCA setting is changed
        if(oldBillingProjectId != billingProjectId || oldStandardCCA != standardCCA) {
            cleanProjectRoleTermsOfUseAssociations(projectId, conn);
            createRoleTermOfUse(projectId, billingProjectId, project.getProjectCategory().getId(), standardCCA,
                    isStudio, conn);
        }

        // update the project properties
        updateProjectProperties(project, idValueMap, operator, conn);

        // create project audit record into project_audit table
        createProjectAudit(projectId, reason, operator, conn);

        // update the project platforms
        updateProjectPlatforms(projectId, project.getPlatforms(), operator, conn);

        // update the project groups
        updateProjectGroups(projectId, project.getGroups(), operator, conn);
    }

    /**
     * <p>
     * Persists the specified ProjectSpec instance for the specified projectId.
     * It basically creates a new row in table corresponding to ProjectSpec - version number for the row is earlier version number + 1.
     * </p>
     * 
     * <p>
     * Updated since 1.3.1 to add private description support
     * </p>
     * 
     * @param projectId the project id for which project spec need to be persisted.
     * @param projectSpec the project spec
     * @param operator the operator/user who is creating this project spec.
     * @param conn the db connection to be used to create this project spec
     * @throws PersistenceException exception is thrown when there is some error in persisting 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private void updateProjectSpec(Long projectId, ProjectSpec projectSpec, String operator, Connection conn) throws PersistenceException {
        Long newId = 0L;
        
        try {
            // generate id for the project spec
            newId = new Long(projectSpecIdGenerator.getNextID());
            getLogger().log(Level.INFO, new LogMessage(newId, operator, "generate id for new project spec"));
        } catch (IDGenerationException e) {
            throw new PersistenceException(
                    "Unable to generate id for the project spec.", e);
        }
        
        getLogger().log(Level.INFO, "insert record into project_spec with id:" + newId);
        
        // insert the project spec into database
        Object[] queryArgs = new Object[] {newId,
            projectId, 
            projectId,
            projectSpec.getDetailedRequirements() != null ? projectSpec.getDetailedRequirements() : "",
            projectSpec.getSubmissionDeliverables() != null ? projectSpec.getSubmissionDeliverables() : "",
            projectSpec.getEnvironmentSetupInstructions() != null ? projectSpec.getEnvironmentSetupInstructions() : "",
            projectSpec.getFinalSubmissionGuidelines() != null ? projectSpec.getFinalSubmissionGuidelines() : "",
            operator,
            operator,
            projectSpec.getPrivateDescription() != null ? projectSpec.getPrivateDescription() : ""};
        Helper.doDMLQuery(conn, UPDATE_PROJECT_SPEC_SQL, queryArgs);
        
        // get the creation date.
        Date specCreateDate = (Date) Helper.doSingleValueQuery(conn,
                "SELECT create_date FROM project_spec WHERE project_spec_id=?",
                new Object[] {newId}, Helper.DATE_TYPE);
        
        // set the newId when no exception occurred
        projectSpec.setProjectSpecId(newId.longValue());

        // set the creation/modification user and date when no exception occurred
        projectSpec.setCreationUser(operator);
        projectSpec.setCreationTimestamp(specCreateDate);
        projectSpec.setModificationUser(operator);
        projectSpec.setModificationTimestamp(specCreateDate);
    }

    /**
     * Makes a property id-property value(String) map from property
     * name-property value map.
     * 
     * Refactored for 1.1.2
     * 
     * @param nameValueMap the property name-property value map
     * @param conn the database connection
     * @param nameIdMap property name to propery id map.
     * @return a property id-property value map
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private Map makePropertyIdPropertyValueMap(Map nameValueMap, Connection conn)
        throws PersistenceException {
        return makePropertyIdPropertyValueMap(nameValueMap, conn, null);
    }

    /**
     * Makes a property id-property value(String) map from property
     * name-property value map.
     * 
     * Refactored for 1.1.2
     * 
     * @param nameValueMap the property name-property value map
     * @param conn the database connection
     * @param nameIdMap property name to propery id map.
     * @return a property id-property value map
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private Map makePropertyIdPropertyValueMap(Map nameValueMap, Connection conn, Map nameIdMap)
        throws PersistenceException {
        Map idValueMap = new HashMap();

        // get the property name-property id map if not passed already.
        if (nameIdMap == null) {
            nameIdMap = makePropertyNamePropertyIdMap(getAllProjectPropertyTypes(conn));
        }

        Map lowerCasedNameIdMap = new HashMap();

        for(Iterator it = nameIdMap.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();
            lowerCasedNameIdMap.put(entry.getKey().toString().toLowerCase(), entry.getValue());
        }


        // enumerate each property
        for (Iterator it = nameValueMap.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();

            // find the property id from property name
            Object propertyId = lowerCasedNameIdMap.get(entry.getKey().toString().toLowerCase());
            // check if the property id can be found
            if (propertyId == null) {
                throw new PersistenceException(
                        "Unable to find ProjectPropertyType name ["
                                + entry.getKey()
                                + "] in project_info_type_lu table.");
            }

            // put the property id-property value(String) pair into the map
            idValueMap.put(propertyId, entry.getValue().toString());
        }
        return idValueMap;
    }

    /**
     * Gets an array of all project property type in the persistence. The
     * project property types are stored in 'project_info_type_lu' table.
     * @param conn the database connection
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private ProjectPropertyType[] getAllProjectPropertyTypes(Connection conn)
        throws PersistenceException {
    	// find all project property types in the table.
        Object[][] rows = Helper.doQuery(conn,
                QUERY_ALL_PROJECT_PROPERTY_TYPES_SQL, new Object[] {},
                QUERY_ALL_PROJECT_PROPERTY_TYPES_COLUMN_TYPES);

        // create the ProjectPropertyType array.
        ProjectPropertyType[] propertyTypes = new ProjectPropertyType[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectPropertyType class
            propertyTypes[i] = new ProjectPropertyType(((Long) row[0])
                    .longValue(), (String) row[1], (String) row[2]);
        }

        return propertyTypes;
    }
    
    /**
     * Build {@link Project} directly from the {@link CustomResultSet}
     * 
     * @param result a {@link CustomResultSet} containing the data for build the {@link Project} instances. 
     * @return an array of {@link Project}
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project[] getProjects(CustomResultSet result) throws PersistenceException {
    	Connection conn = null;
        try {
        	conn = openConnection();
        	PreparedStatement ps = conn.prepareStatement(QUERY_ONE_PROJECT_PROPERTIES_SQL);
			int size = result.getRecordCount();
			Project[] projects = new Project[size];
			for (int i = 0; i < size; i++) {
				result.absolute(i + 1);
				
				// create the ProjectStatus object
			    ProjectStatus status = new ProjectStatus(result.getLong(2), result.getString(3));

			    // create the ProjectType object
			    ProjectType type = new ProjectType(result.getLong(6), result.getString(7));

			    // create the ProjectCategory object
			    ProjectCategory category = new ProjectCategory(result.getLong(4), result.getString(5), type);

			    // create a new instance of ProjectType class
			    projects[i] = new Project(result.getLong(1), category, status);

			    // assign the audit information
			    projects[i].setCreationUser(result.getString(8));
			    projects[i].setCreationTimestamp(result.getDate(9));
			    projects[i].setModificationUser(result.getString(10));
			    projects[i].setModificationTimestamp(result.getDate(11));
			    projects[i].setTcDirectProjectId(result.getLong(12));
			    
			    //
	            // Added for Cockpit Launch Contest - Update for Spec Creation v1.0
	            //
	            ProjectSpec[] specs = getProjectSpecs(projects[i].getId(), conn);
	            if (specs != null && specs.length > 0) {
	                projects[i].setProjectSpec(specs[0]);
	            }
			    
			    ps.setLong(1, projects[i].getId());
			    ResultSet rs = ps.executeQuery();
			    while (rs.next()) {
			    	projects[i].setProperty(rs.getString(2), rs.getString(3));
			    }			    
			}
			return projects;
		} catch (NullColumnValueException ne) {
            throw new PersistenceException(ne.getMessage(), ne);
		} catch (InvalidCursorStateException icse) {
            throw new PersistenceException("cursor state is invalid.", icse);
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			if (conn != null) {
				closeConnection(conn);
			}
		}
	}
    
    /**
     * <p>
     * Retrieves an array of project instance from the persistence given their
     * ids. The project instances are retrieved with their properties.
     * </p>
     * @param ids The ids of the projects to be retrieved.
     * @param conn the database connection
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     * @throws ParseException if error occurred while parsing the result
     */
    private Project[] getProjects(long ids[], Connection conn)
        throws PersistenceException, ParseException {

        // build the id list string
        StringBuffer idListBuffer = new StringBuffer();
        idListBuffer.append('(');
        for (int i = 0; i < ids.length; ++i) {
            if (i != 0) {
                idListBuffer.append(',');
            }
            idListBuffer.append(ids[i]);
        }
        idListBuffer.append(')');
        // get the id list string
        String idList = idListBuffer.toString();

        // find projects in the table.
        Object[][] rows = Helper
            .doQuery(conn, QUERY_PROJECTS_SQL + idList, new Object[]{}, QUERY_PROJECTS_COLUMN_TYPES);

        // create the Project array.
        Project[] projects = new Project[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create the ProjectStatus object
            ProjectStatus status = new ProjectStatus(((Long) row[1]).longValue(), (String) row[2]);

            // create the ProjectType object
            ProjectType type = new ProjectType(((Long) row[5]).longValue(), (String) row[6]);

            // create the ProjectCategory object
            ProjectCategory category = new ProjectCategory(((Long) row[3]).longValue(), (String) row[4], type);
            category.setDescription((String) row[11]);

            long projectId = (Long) row[0];
            // create a new instance of Project class
            projects[i] = new Project(projectId, category, status);

            // assign the audit information
            projects[i].setCreationUser((String) row[7]);
            projects[i].setCreationTimestamp((Date) row[8]);
            projects[i].setModificationUser((String) row[9]);
            projects[i].setModificationTimestamp((Date) row[10]);

            // set the tc direct project id
            projects[i].setTcDirectProjectId(row[12] == null ? 0 : ((Long) row[12]).intValue());

            //creator
            projects[i].setCreator((String) row[13]);

            // set the file types
            projects[i].setProjectFileTypes(Arrays.asList(getProjectFileTypes(projectId)));

            // set the prizes
            projects[i].setPrizes(Arrays.asList(getProjectPrizes(projectId)));

            // set the studio specification
            projects[i].setProjectStudioSpecification(getProjectStudioSpecification(projectId));

            
            // set the marathon match specification
            projects[i].setProjectMMSpecification(getProjectMMSpecification(projectId));
            
            //
            // Added for Cockpit Launch Contest - Update for Spec Creation v1.0
            //
            ProjectSpec[] specs = getProjectSpecs(projects[i].getId(), conn);
            if (specs != null && specs.length > 0) {
                projects[i].setProjectSpec(specs[0]);
            }

            if (projects[i].getProjectCategory().getId() == ProjectCategory.COPILOT_POSTING.getId()) {
                projects[i].setProjectCopilotTypes(getProjectCopilotTypes(projects[i].getId()));
                projects[i].setCopilotContestExtraInfos(getCopilotContestExtraInfos(projects[i].getId()));
            }
        }

        // get the Id-Project map
        Map projectMap = makeIdProjectMap(projects);

        // find project properties in the table.
        rows = Helper.doQuery(conn, QUERY_PROJECT_PROPERTIES_SQL + idList, new Object[]{},
            QUERY_PROJECT_PROPERTIES_COLUMN_TYPES);

        // enumerate each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // get the corresponding Project object
            Project project = (Project) projectMap.get(row[0]);

            // set the property to project
            project.setProperty((String) row[1], (String) row[2]);
        }

        // find the project platforms in the database
        rows = Helper.doQuery(conn, QUERY_PROJECT_PLATFORMS_SQL + idList, new Object[]{},
                QUERY_PROJECT_PLATFORMS_COLUMN_TYPES);

        for(int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // get the corresponding Project object
            Project project = (Project) projectMap.get(row[0]);

            if(project.getPlatforms() == null) {
                List<ProjectPlatform> platforms = new ArrayList<ProjectPlatform>();
                project.setPlatforms(platforms);
            }

            project.getPlatforms().add(new ProjectPlatform((Long) row[1], (String) row[2]));
        }

        // find the project groups in the database
        rows = Helper.doQuery(conn, QUERY_PROJECT_GROUPS_SQL + idList, new Object[]{},
                QUERY_PROJECT_GROUPS_COLUMN_TYPES);

        for(int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // get the corresponding Project object
            Project project = (Project) projectMap.get(row[0]);

            if(project.getGroups() == null) {
                List<ProjectGroup> groups = new ArrayList<ProjectGroup>();
                project.setGroups(groups);
            }

            project.getGroups().add(new ProjectGroup((Long) row[1], (String) row[2]));
        }

        return projects;
    }
    /**
     * <p>
     * Retrieves an array of project instance from the persistence whose create date is within current - days.
     * </p>
     *
     * @param days
     *            last 'days'
     * @param conn
     *            the database connection
     * @return An array of project instances.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    private Project[] getProjectsByCreateDate(int days, Connection conn) throws PersistenceException {

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECTS_BY_CREATE_DATE_SQL + days, new Object[]{},
            QUERY_PROJECTS_BY_CREATE_DATE_COLUMN_TYPES);

        // create the Project array.
        Project[] projects = new Project[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create the ProjectStatus object
            ProjectStatus status = new ProjectStatus(((Long) row[1]).longValue(), (String) row[2]);

            // create the ProjectType object
            ProjectType type = new ProjectType(((Long) row[5]).longValue(), (String) row[6]);

            // create the ProjectCategory object
            ProjectCategory category = new ProjectCategory(((Long) row[3]).longValue(), (String) row[4], type);
            category.setDescription((String) row[11]);
            // create a new instance of ProjectType class
            projects[i] = new Project(((Long) row[0]).longValue(), category, status);

            // assign the audit information
            projects[i].setCreationUser((String) row[7]);
            projects[i].setCreationTimestamp((Date) row[8]);
            projects[i].setModificationUser((String) row[9]);
            projects[i].setModificationTimestamp((Date) row[10]);

            // here we only get project name and project version
            projects[i].setProperty("Project Name", (String) row[12]);
            projects[i].setProperty("Project Version", (String) row[13]);
        }

        return projects;
    }

    /**
     * Gets an array of all project types in the persistence. The project types
     * are stored in 'project_type_lu' table.
     * @param conn the database connection
     * @return An array of all project types in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private ProjectType[] getAllProjectTypes(Connection conn)
        throws PersistenceException {
    	// find all project types in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_PROJECT_TYPES_SQL,
                new Object[] {}, QUERY_ALL_PROJECT_TYPES_COLUMN_TYPES);

        // create the ProjectType array.
        ProjectType[] projectTypes = new ProjectType[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectType class
            projectTypes[i] = new ProjectType(((Long) row[0]).longValue(),
                    (String) row[1], (String) row[2]);
        }

        return projectTypes;
    }

    /**
     * Create the project properties in the database.
     * @param project The new generated project
     * @param idValueMap The property id - property value map
     * @param operator The creation user of this project
     * @param conn The database connection
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private void createProjectProperties(Long projectId, Project project, Map idValueMap,
            String operator, Connection conn) throws PersistenceException {

    	getLogger().log(Level.INFO, new LogMessage(projectId, operator,
    			"insert record into project_info with project id" + projectId));
        PreparedStatement preparedStatement = null;
        try {
            // prepare the statement.
            preparedStatement = conn
                    .prepareStatement(CREATE_PROJECT_PROPERTY_SQL);

            // enumerator each property in the idValueMap
            for (Iterator it = idValueMap.entrySet().iterator(); it.hasNext();) {
                Entry entry = (Entry) it.next();

                // insert the project property into database
                Object[] queryArgs = new Object[] {projectId, entry.getKey(),
                        entry.getValue(), operator, operator };
                Helper.doDMLQuery(preparedStatement, queryArgs);
                
                auditProjectInfo(conn, projectId, project, AUDIT_CREATE_TYPE, (Long) entry.getKey(),
                		(String) entry.getValue());
            }

        } catch (SQLException e) {
            throw new PersistenceException(
                    "Unable to create prepared statement ["
                            + CREATE_PROJECT_PROPERTY_SQL + "].", e);
        } finally {
            Helper.closeStatement(preparedStatement);
        }
    }

    /**
     * Creates the project platforms into the project.
     *
     * @param projectId the id of the project
     * @param platforms the list of ProjectPlatform to create
     * @param operator the operator
     * @param conn the database connection
     * @throws PersistenceException if there is any error.
     * @since 1.8
     */
    private void createProjectPlatforms(Long projectId, List<ProjectPlatform> platforms, String operator, Connection conn)
            throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(projectId, operator,
                "insert record into project_platform with project id" + projectId));

        if (platforms == null || platforms.size() == 0) {
            return;
        }

        PreparedStatement preparedStatement = null;

        try {
            // prepare the statement.
            preparedStatement = conn
                    .prepareStatement(CREATE_PROJECT_PLATFORM_SQL);

            // enumerator each project platform
            for (ProjectPlatform platform : platforms) {
                // insert the project platform into database
                Object[] queryArgs = new Object[]{projectId, platform.getId(),
                        operator, operator};
                Helper.doDMLQuery(preparedStatement, queryArgs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(
                    "Unable to create prepared statement ["
                            + CREATE_PROJECT_PLATFORM_SQL + "].", e);
        } finally {
            Helper.closeStatement(preparedStatement);
        }
    }

    /**
     * Creates the project groups into the project.
     *
     * @param projectId the id of the project
     * @param groups the list of ProjectGroup to create
     * @param operator the operator
     * @param conn the database connection
     * @throws PersistenceException if there is any error.
     */
    private void createProjectGroups(Long projectId, List<ProjectGroup> groups, String operator, Connection conn)
            throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(projectId, operator,
                "insert record into project_group_xref with project id" + projectId));

        if (groups == null || groups.size() == 0) {
            return;
        }

        PreparedStatement preparedStatement = null;

        try {
            // prepare the statement.
            preparedStatement = conn
                    .prepareStatement(CREATE_PROJECT_GROUP_SQL);

            // enumerator each project group
            for (ProjectGroup group : groups) {
                // insert the project group into database
                Object[] queryArgs = new Object[]{projectId, group.getId(),
                        operator, operator};
                Helper.doDMLQuery(preparedStatement, queryArgs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(
                    "Unable to create prepared statement ["
                            + CREATE_PROJECT_GROUP_SQL + "].", e);
        } finally {
            Helper.closeStatement(preparedStatement);
        }
    }

    /**
     * Make an Id-Project map from Project[].
     * @param projects the Id-Project array
     * @return an Id-Project map
     */
    private Map makeIdProjectMap(Project[] projects) {
        Map map = new HashMap();

        for (int i = 0; i < projects.length; ++i) {
            map.put(new Long(projects[i].getId()), projects[i]);
        }
        return map;
    }

    /**
     * Update the project properties into the database.
     * @param project the project object
     * @param idValueMap the property id - property value map
     * @param operator the modification user of this project
     * @param conn the database connection
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private void updateProjectProperties(Project project, Map idValueMap,
            String operator, Connection conn) throws PersistenceException {
    	
    	Long projectId = project.getId();
    	
        // get old property ids and values from database
        Map<Long, String> propertyMap = getProjectPropertyIdsAndValues(projectId, conn);

        // create a property id-property value map that contains the properties
        // to insert
        Map createIdValueMap = new HashMap();

        PreparedStatement preparedStatement = null;
        try {
        	getLogger().log(Level.INFO, new LogMessage(projectId, operator,
             				"update project, update project_info with projectId:" + projectId));
        	 
            // prepare the statement.
            preparedStatement = conn.prepareStatement(UPDATE_PROJECT_PROPERTY_SQL);

            // enumerator each property id in the project object
            for (Iterator it = idValueMap.entrySet().iterator(); it.hasNext();) {
                Entry entry = (Entry) it.next();

                Long propertyId = (Long) entry.getKey();

                // check if the property in the project object already exists in
                // the database
                if (propertyMap.containsKey(propertyId)) {
                	// if the value hasn't been changed, we don't need to update anything
                	if (!propertyMap.get(propertyId).equals((String) entry.getValue())) {
                		// update the project property
                		Object[] queryArgs = new Object[] {entry.getValue(),
                    		operator, projectId, propertyId };
                    	Helper.doDMLQuery(preparedStatement, queryArgs);
                    
                    	auditProjectInfo(conn, project, AUDIT_UPDATE_TYPE, propertyId, (String) entry.getValue());
                	}
                	propertyMap.remove(propertyId);
                } else {
                    // add the entry to the createIdValueMap
                    createIdValueMap.put(propertyId, entry.getValue());
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Unable to create prepared statement ["
                            + UPDATE_PROJECT_PROPERTY_SQL + "].", e);
        } finally {
            Helper.closeStatement(preparedStatement);
        }

        // create the new properties
        createProjectProperties(project.getId(), project, createIdValueMap, operator, conn);

        // delete the remaining property ids that are not in the project object
        // any longer
        deleteProjectProperties(project, propertyMap.keySet(), conn);
    }

    /**
     * Updates the project platforms.
     *
     * @param projectId the id of the project
     * @param platforms the list of ProjectPlatform to create
     * @param operator the operator
     * @param conn the database connection
     * @throws PersistenceException if any error
     * @since 1.8
     */
    private void updateProjectPlatforms(Long projectId, List<ProjectPlatform> platforms, String operator, Connection conn)
            throws PersistenceException {

        if(platforms == null) {
            platforms = new ArrayList<ProjectPlatform>();
        }

        // get old platform ids from database
        Set<Long> oldPlatformIds = getProjectPlatformsForProject(projectId, conn);

        // create a list to contain the platforms to insert
        List<ProjectPlatform> platformsToAdd = new ArrayList<ProjectPlatform>();

        // create a list to contain the platforms to remove
        List<Long> platformsToDelete = new ArrayList<Long>();

        Set<Long> newPlatformIds = new HashSet<Long>();

        for(ProjectPlatform p : platforms) {
            if(!oldPlatformIds.contains(p.getId())) {
                // the existing does not contain, to add
                platformsToAdd.add(p);
            }
            newPlatformIds.add(p.getId());
        }

        for(Long oldPID : oldPlatformIds) {
            if(!newPlatformIds.contains(oldPID)) {
                // the old platform is not in the new platform, to remove
                platformsToDelete.add(oldPID);
            }
        }

        // create the new platforms
        createProjectPlatforms(projectId, platformsToAdd, operator, conn);

        // delete the old platforms
        deleteProjectPlatforms(projectId, platformsToDelete, conn);
    }

    /**
     * Updates the project groups.
     *
     * @param projectId the id of the project
     * @param groups the list of ProjectGroup to create
     * @param operator the operator
     * @param conn the database connection
     * @throws PersistenceException if any error
     */
    private void updateProjectGroups(Long projectId, List<ProjectGroup> groups, String operator, Connection conn)
            throws PersistenceException {

        if(groups == null) {
            groups = new ArrayList<ProjectGroup>();
        }

        // get old group ids from database
        Set<Long> oldGroupIds = getProjectGroupsForProject(projectId, conn);

        // create a list to contain the groups to insert
        List<ProjectGroup> groupsToAdd = new ArrayList<ProjectGroup>();

        // create a list to contain the groups to remove
        List<Long> groupsToDelete = new ArrayList<Long>();

        Set<Long> newGroupIds = new HashSet<Long>();

        for(ProjectGroup p : groups) {
            if(!oldGroupIds.contains(p.getId())) {
                // the existing does not contain, to add
                groupsToAdd.add(p);
            }
            newGroupIds.add(p.getId());
        }

        for(Long oldPID : oldGroupIds) {
            if(!newGroupIds.contains(oldPID)) {
                // the old group is not in the new group, to remove
                groupsToDelete.add(oldPID);
            }
        }

        // create the new groups
        createProjectGroups(projectId, groupsToAdd, operator, conn);

        // delete the old groups
        deleteProjectGroups(projectId, groupsToDelete, conn);
    }

    /**
     * Gets all the property ids associated to this project.
     * @param projectId The id of this project
     * @param conn The database connection
     * @return A set that contains the property ids
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private Set getProjectPropertyIds(Long projectId, Connection conn)
        throws PersistenceException {
        Set idSet = new HashSet();

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_PROPERTY_IDS_SQL,
                new Object[] {projectId},
                QUERY_PROJECT_PROPERTY_IDS_COLUMN_TYPES);

        // enumerator each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // add the id to the set
            idSet.add(row[0]);
        }
        return idSet;
    }

    /**
     * Gets all the property ids and values associated to this project.
     * 
     * @param projectId The id of this project
     * @param conn The database connection
     * @return A map that contains the property values, keyed by id
     * 
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private Map<Long, String> getProjectPropertyIdsAndValues(Long projectId, Connection conn)
    		throws PersistenceException {
    	
        Map<Long, String> idMap = new HashMap<Long, String>();

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_SQL,
                new Object[] {projectId},
                QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_COLUMN_TYPES);

        // enumerator each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // add the id to the map
            idMap.put((Long) row[0], (String) row[1]);
        }
        
        return idMap;
    }

    /**
     * Gets the project platform ids for the given project.
     *
     * @param projectId the id of the project
     * @param conn the database connection
     * @return a set of project platform ids.
     * @throws PersistenceException if any error
     * @since 1.8
     */
    private Set<Long> getProjectPlatformsForProject(Long projectId, Connection conn) throws PersistenceException {
        Set<Long> platformIds = new HashSet<Long>();

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_PLATFORM_IDS_SQL,
                new Object[]{projectId},
                QUERY_PROJECT_PLATFORM_IDS_COLUMN_TYPES);

        // enumerator each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // add the id to the map
            platformIds.add((Long) row[0]);
        }

        return platformIds;
    }

    /**
     * Gets the project group ids for the given project.
     *
     * @param projectId the id of the project
     * @param conn the database connection
     * @return a set of project group ids.
     * @throws PersistenceException if any error
     */
    private Set<Long> getProjectGroupsForProject(Long projectId, Connection conn) throws PersistenceException {
        Set<Long> groupIds = new HashSet<Long>();

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_GROUP_IDS_SQL,
                new Object[]{projectId},
                QUERY_PROJECT_GROUP_IDS_COLUMN_TYPES);

        // enumerator each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // add the id to the map
            groupIds.add((Long) row[0]);
        }

        return groupIds;
    }

    /**
     * Delete the project properties from the database.
     * @param project the project object
     * @param propertyIdSet the Set that contains the property ids to delete
     * @param conn the database connection
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private void deleteProjectProperties(Project project, Set<Long> propertyIdSet, Connection conn)
    		throws PersistenceException {
    	
    	Long projectId = project.getId();
    	
        // check if the property id set is empty
        // do nothing if property id set is empty
        if (!propertyIdSet.isEmpty()) {

            // build the id list string
            StringBuffer idListBuffer = new StringBuffer();
            idListBuffer.append('(');
            int idx = 0;
            for (Long id : propertyIdSet) {
                if (idx++ != 0) {
                    idListBuffer.append(',');
                }
                idListBuffer.append(id);
            }
            idListBuffer.append(')');

            getLogger().log(Level.INFO, new LogMessage(projectId, null,
            		"delete records from project_info with projectId:" + projectId));
            
            // delete the properties whose id is in the set
            Helper.doDMLQuery(conn, DELETE_PROJECT_PROPERTIES_SQL
                    + idListBuffer.toString(), new Object[] {projectId});
            
            for (Long id : propertyIdSet) {
            	auditProjectInfo(conn, project, AUDIT_DELETE_TYPE, id, null);
            }
        }
    }


    /**
     * Deletes the project platforms.
     *
     * @param projectId the id of the project
     * @param platformIds the platform ids to delete.
     * @param conn the database connection
     * @throws PersistenceException if any error.
     * @since 1.8
     */
    private void deleteProjectPlatforms(Long projectId, List<Long> platformIds, Connection conn)
        throws PersistenceException {

        // check if the platform set to delete is empty
        if (platformIds!= null && !platformIds.isEmpty()) {

            // build the id list string
            StringBuffer idListBuffer = new StringBuffer();
            idListBuffer.append('(');
            int idx = 0;
            for (Long pid : platformIds) {
                if (idx++ != 0) {
                    idListBuffer.append(',');
                }
                idListBuffer.append(pid);
            }
            idListBuffer.append(')');

            getLogger().log(Level.INFO, new LogMessage(projectId, null,
                    "delete records from project_platform with projectId:" + projectId));

            // delete the project platforms whose id is in the set
            Helper.doDMLQuery(conn, DELETE_PROJECT_PLATFORMS_SQL
                    + idListBuffer.toString(), new Object[] {projectId});
        }
    }

    /**
     * Deletes the project groups.
     *
     * @param projectId the id of the project
     * @param groupIds the group ids to delete.
     * @param conn the database connection
     * @throws PersistenceException if any error.
     */
    private void deleteProjectGroups(Long projectId, List<Long> groupIds, Connection conn)
        throws PersistenceException {

        // check if the group set to delete is empty
        if (groupIds != null && !groupIds.isEmpty()) {

            // build the id list string
            StringBuffer idListBuffer = new StringBuffer();
            idListBuffer.append('(');
            int idx = 0;
            for (Long pid : groupIds) {
                if (idx++ != 0) {
                    idListBuffer.append(',');
                }
                idListBuffer.append(pid);
            }
            idListBuffer.append(')');

            getLogger().log(Level.INFO, new LogMessage(projectId, null,
                    "delete records from project_group with projectId:" + projectId));

            // delete the project groups whose id is in the set
            Helper.doDMLQuery(conn, DELETE_PROJECT_GROUPS_SQL
                    + idListBuffer.toString(), new Object[] {projectId});
        }
    }

    /**
     * Create a project audit record into the database.
     * @param projectId The id of the project
     * @param reason The update reason
     * @param operator the modification user of this project
     * @param conn the database connection
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private void createProjectAudit(Long projectId, String reason,
            String operator, Connection conn) throws PersistenceException {

        Long auditId;
        try {
            // generate a new audit id
            auditId = new Long(projectAuditIdGenerator.getNextID());
            getLogger().log(Level.INFO,
            		new LogMessage(projectId, operator, "generate new id for the project audit."));
        } catch (IDGenerationException e) {
            throw new PersistenceException(
                    "Unable to generate id for project.", e);
        }
        getLogger().log(Level.INFO, "insert record into project_audit with projectId:" + projectId);
        // insert the update reason information to project_audit table
        Object[] queryArgs = new Object[] {auditId, projectId, reason,
            operator, operator};
        Helper.doDMLQuery(conn, CREATE_PROJECT_AUDIT_SQL, queryArgs);
    }

    /**
     * Make a property name - property id map from ProjectPropertyType[].
     * @param propertyTypes the ProjectPropertyType array
     * @return a property name - property id map
     * @throws PersistenceException if duplicate property type names are found
     */
    private Map makePropertyNamePropertyIdMap(
            ProjectPropertyType[] propertyTypes) throws PersistenceException {
        Map map = new HashMap();

        // enumerate each property type
        for (int i = 0; i < propertyTypes.length; ++i) {

            // check if the property name already exists
            if (map.containsKey(propertyTypes[i].getName())) {
                throw new PersistenceException(
                        "Duplicate project property type names ["
                                + propertyTypes[i].getName()
                                + "] found in project_info_type_lu table.");
            }

            // put the name-id pair to the map
            map.put(propertyTypes[i].getName(), new Long(propertyTypes[i]
                    .getId()));
        }
        return map;
    }

    /**
     * Gets an array of all project categories in the persistence. The project
     * categories are stored in 'project_category_lu' table.
     * @param conn the database connection
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private ProjectCategory[] getAllProjectCategories(Connection conn)
        throws PersistenceException {
    	// find all project categories in the table.
        Object[][] rows = Helper.doQuery(conn,
                QUERY_ALL_PROJECT_CATEGORIES_SQL, new Object[] {},
                QUERY_ALL_PROJECT_CATEGORIES_COLUMN_TYPES);

        // create the ProjectCategory array.
        ProjectCategory[] projectCategories = new ProjectCategory[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create the ProjectType object
            ProjectType type = new ProjectType(((Long) row[3]).longValue(),
                    (String) row[4], (String) row[5]);

            // create a new instance of ProjectCategory class
            projectCategories[i] = new ProjectCategory(((Long) row[0])
                    .longValue(), (String) row[1], (String) row[2], type);
        }

        return projectCategories;
    }

    /**
     * Gets all project platforms.
     *
     * @param conn the database connection
     * @return the array of all project platforms.
     * @throws PersistenceException if any error.
     * @since 1.8
     */
    private ProjectPlatform[] getAllProjectPlatforms(Connection conn)
            throws PersistenceException {
        // find all project platforms in the table.
        Object[][] rows = Helper.doQuery(conn,
                QUERY_ALL_PROJECT_PLATFORMS_SQL, new Object[] {},
                QUERY_ALL_PROJECT_PLATFORMS_COLUMN_TYPES);

        // create the ProjectPlatform array.
        ProjectPlatform[] projectPlatforms = new ProjectPlatform[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create the ProjectPlatform object
            projectPlatforms[i] = new ProjectPlatform(((Long) row[0]).longValue(),
                    (String) row[1]);
        }

        return projectPlatforms;
    }

    /**
     * Gets all project groups.
     *
     * @param conn the database connection
     * @return the array of all project groups.
     * @throws PersistenceException if any error.
     */
    private ProjectGroup[] getAllProjectGroups(Connection conn)
            throws PersistenceException {
        // find all project groups in the table.
        Object[][] rows = Helper.doQuery(conn,
                QUERY_ALL_PROJECT_GROUPS_SQL, new Object[] {},
                QUERY_ALL_PROJECT_GROUPS_COLUMN_TYPES);

        // create the ProjectGroup array.
        ProjectGroup[] projectGroups = new ProjectGroup[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create the ProjectGroup object
            projectGroups[i] = new ProjectGroup(((Long) row[0]).longValue(),
                    (String) row[1]);
        }

        return projectGroups;
    }

    /**
     * Gets an array of all project statuses in the persistence. The project
     * statuses are stored in 'project_status_lu' table.
     * @param conn the database connection
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private ProjectStatus[] getAllProjectStatuses(Connection conn)
        throws PersistenceException {
    	// find all project statuses in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_PROJECT_STATUSES_SQL,
                new Object[] {}, QUERY_ALL_PROJECT_STATUSES_COLUMN_TYPES);

        // create the ProjectStatus array.
        ProjectStatus[] projectStatuses = new ProjectStatus[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectStatus class
            projectStatuses[i] = new ProjectStatus(((Long) row[0]).longValue(),
                    (String) row[1], (String) row[2]);
        }

        return projectStatuses;
    }

     /**
     * This method will audit project information. This information is generated when most project properties are
     * inserted, deleted, or edited.
     *
     * @param connection the connection to database
     * @param projectId the id of the project being audited
     * @param project the project being audited
     * @param auditType the audit type. Can be AUDIT_CREATE_TYPE, AUDIT_DELETE_TYPE, or AUDIT_UPDATE_TYPE
     * @param projectInfoTypeId the project info type id
     * @param value the project info value that we're changing to
     *
     * @throws PersistenceException if validation error occurs or any error occurs in the underlying layer
     *
     * @since 1.1.2
     */
    private void auditProjectInfo(Connection connection, Long projectId, Project project, int auditType,
    		long projectInfoTypeId, String value) throws PersistenceException {

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(PROJECT_INFO_AUDIT_INSERT_SQL);

            int index = 1;
            statement.setLong(index++, projectId);
            statement.setLong(index++, projectInfoTypeId);
            statement.setString(index++, value);
            statement.setInt(index++, auditType);
            statement.setTimestamp(index++, new Timestamp(project.getModificationTimestamp().getTime()));
            statement.setLong(index++, Long.parseLong(project.getModificationUser()));

            if (statement.executeUpdate() != 1) {
                throw new PersistenceException("Audit information was not successfully saved.");
            }
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            throw new PersistenceException("Unable to insert project_info_audit record.", e);
        } finally {
            Helper.closeStatement(statement);
        }
    }
    
    /**
     * This method will audit project information. This information is generated when most project properties are
     * inserted, deleted, or edited.
     *
     * @param connection the connection to database
     * @param project the project being audited
     * @param auditType the audit type. Can be AUDIT_CREATE_TYPE, AUDIT_DELETE_TYPE, or AUDIT_UPDATE_TYPE
     * @param projectInfoTypeId the project info type id
     * @param value the project info value that we're changing to
     *
     * @throws PersistenceException if validation error occurs or any error occurs in the underlying layer
     *
     * @since 1.1.2
     */
    private void auditProjectInfo(Connection connection, Project project, int auditType, long projectInfoTypeId, String value)
			throws PersistenceException {
    	auditProjectInfo(connection, project.getId(), project, auditType, projectInfoTypeId, value);
    }

    
    /**
     * <p>
     * Retrieves an array of project instance from the persistence where tc direct 
     * project id is not null. The project instances are retrieved with their properties.
     * </p>
     * 
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
	public Project[] getAllTcDirectProject() throws PersistenceException {		

        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            // find projects in the table.
            Object[][] rows = Helper.doQuery(conn, QUERY_TC_DIRECT_PROJECTS_SQL,
                    new Object[] {}, QUERY_PROJECTS_COLUMN_TYPES);

              
            Project[] projects = getProjects(rows, conn); 
            closeConnection(conn);
            return projects;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieving all tc direct projects " , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
		
	}
	
	/**
     * <p>
     * Retrieves an array of project instance from the persistence given user.
     * The project instances are retrieved with their properties.
     * </p>
     * @param operator The id of create user.     
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
	public Project[] getAllTcDirectProject(String operator) throws PersistenceException {
//		 check if ids is empty
        if (operator == null  ||  operator.length() == 0) {
            throw new IllegalArgumentException("Create user should not be null.");
        }
		Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            // find projects in the table.
            Object[][] rows = Helper.doQuery(conn, QUERY_USER_TC_DIRECT_PROJECTS_SQL,
                    new Object[] {operator}, QUERY_PROJECTS_COLUMN_TYPES);
            
            Project[] projects = getProjects(rows, conn); 
            closeConnection(conn);
            return projects;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieving all tc direct projects " , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
		
	}

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
    public ContestSale createContestSale(ContestSale contestSale) throws PersistenceException {
        Helper.assertObjectNotNull(contestSale, "contestSale");

        Connection conn = null;

        // newId will contain the new generated Id for the contest sale
        Long newId;

        getLogger().log(Level.INFO, new LogMessage(null, null, "creating new contest sale"));
        
        try {
            // create the connection
            conn = openConnection();

            // check whether the contest_sale_id is already in the database
            if (contestSale.getContestSaleId() > 0) {
                if (Helper.checkEntityExists("contest_sale", "contest_sale_id", contestSale
                        .getContestSaleId(), conn)) {
                	throw new PersistenceException(
                            "The contest_sale with the same id [" + contestSale.getContestSaleId()
                                    + "] already exists.");
                }
            }

            try {
                // generate id for the contest sale
                newId = new Long(contestSaleIdGenerator.getNextID());
                getLogger().log(Level.INFO, new LogMessage(null, null, "generate id for new contest sale"));
            } catch (IDGenerationException e) {
                throw new PersistenceException(
                        "Unable to generate id for the contest sale.", e);
            }

            // create the contest sale
            Object[] queryArgs = new Object[] {newId, contestSale.getContestId(), contestSale.getStatus().getSaleStatusId(),
            		contestSale.getPrice(), contestSale.getPayPalOrderId(),
            		contestSale.getSaleReferenceId(), contestSale.getSaleType().getSaleTypeId()};
            Helper.doDMLQuery(conn, CREATE_CONTEST_SALE_SQL, queryArgs);
            
            closeConnection(conn);
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR,
        			new LogMessage(null, null, "Fails to create contest sale", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        // set the newId when no exception occurred
        contestSale.setContestSaleId(newId.longValue());

        return contestSale;
    }

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
    public SaleStatus getSaleStatus(long saleStatusId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            return getSaleStatus(saleStatusId, conn);

        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve the sale status" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
        finally {

            closeConnection(conn);
        }
    }


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
    private SaleStatus getSaleStatus(long saleStatusId, Connection conn) throws PersistenceException {

            // get the sale status
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_SALE_STATUS_BY_ID_SQL, new Object[] {saleStatusId},
            		QUERY_SALE_STATUS_BY_ID_COLUMN_TYPES);

            if (rows.length == 0) {
                return null;
            }

            // create a new instance of SaleStatus class
            SaleStatus saleStatus = new SaleStatus();
            saleStatus.setSaleStatusId(((Long) rows[0][0]));
            saleStatus.setDescription((String) rows[0][1]);


            return saleStatus;

    }

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
    public SaleType getSaleType(long saleTypeId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            return getSaleType(saleTypeId, conn);

        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve the sale type" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        } finally {
            closeConnection(conn);

        }

    }



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
    private SaleType getSaleType(long saleTypeId, Connection conn) throws PersistenceException {

            // get the sale type
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_SALE_TYPE_BY_ID_SQL, new Object[] {saleTypeId},
            		QUERY_SALE_TYPE_BY_ID_COLUMN_TYPES);

            if (rows.length == 0) {
                return null;
            }

            // create a new instance of SaleType class
            SaleType saleType = new SaleType();
            saleType.setSaleTypeId(((Long) rows[0][0]));
            saleType.setDescription((String) rows[0][1]);

            return saleType;

    }

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
    public ContestSale getContestSale(long contestSaleId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the contest sale
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_CONTEST_SALE_BY_ID_SQL, new Object[] {contestSaleId},
            		QUERY_CONTEST_SALE_BY_ID_COLUMN_TYPES);

            if (rows.length == 0) {
                return null;
            }

            // create a new instance of ContestSale class
            ContestSale contestSale = new ContestSale();
            contestSale.setContestSaleId(((Long) rows[0][0]));
            contestSale.setContestId((Long) rows[0][1]);
            contestSale.setStatus(this.getSaleStatus((Long) rows[0][2], conn));
            contestSale.setPrice((Double) rows[0][3]);
            contestSale.setPayPalOrderId((String) rows[0][4]);
            contestSale.setCreateDate((Date) rows[0][5]);
            contestSale.setSaleReferenceId((String) rows[0][6]);
            contestSale.setSaleType(this.getSaleType((Long) rows[0][7], conn));


            return contestSale;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve the contest sale" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
        finally {

            closeConnection(conn);
        }
    }

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
    public List<ContestSale> getContestSales(long contestId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the contest sale
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_CONTEST_SALE_BY_CONTEST_ID_SQL, new Object[] {contestId},
            		QUERY_CONTEST_SALE_BY_CONTEST_ID_COLUMN_TYPES);

            List<ContestSale> ret = new ArrayList<ContestSale>();

            for (int i = 0; i < rows.length; i++) {
                // create a new instance of ContestSale class
                ContestSale contestSale = new ContestSale();
                contestSale.setContestSaleId(((Long) rows[i][0]));
                contestSale.setContestId((Long) rows[i][1]);
                contestSale.setStatus(this.getSaleStatus((Long) rows[i][2], conn));
                contestSale.setPrice((Double) rows[i][3]);
                contestSale.setPayPalOrderId((String) rows[i][4]);
                contestSale.setCreateDate((Date) rows[i][5]);
                contestSale.setSaleReferenceId((String) rows[i][6]);
                contestSale.setSaleType(this.getSaleType((Long) rows[i][7], conn));

                ret.add(contestSale);
            }


            return ret;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve the contest sales" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
         finally {

            closeConnection(conn);
        }
    }

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
    public void editContestSale(ContestSale contestSale) throws PersistenceException {
        Helper.assertObjectNotNull(contestSale, "contestSale");

        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null, null, "updating contest sale"));
        try {
            // create the connection
            conn = openConnection();

            // check whether the contest_sale_id is already in the database
            if (!Helper.checkEntityExists("contest_sale", "contest_sale_id", contestSale
                    .getContestSaleId(), conn)) {
                throw new PersistenceException("The contest_sale id ["
                        + contestSale.getContestSaleId() + "] does not exist in the database.");
            }

            // update the contest sale
            Object[] queryArgs = new Object[] {
                contestSale.getContestId(), contestSale.getStatus().getSaleStatusId(), contestSale.getPrice(), contestSale.getPayPalOrderId(),
                contestSale.getSaleReferenceId(), contestSale.getSaleType().getSaleTypeId(), 
                contestSale.getContestSaleId()};
            Helper.doDMLQuery(conn, UPDATE_CONTEST_SALE_SQL, queryArgs);

            closeConnection(conn);
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR,
        			new LogMessage(null, null, "Fails to update contest sale", e));
        	if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

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
    public boolean removeContestSale(long contestSaleId) throws PersistenceException {
        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null, null, "removing contest sale"));
        try {
            // create the connection
            conn = openConnection();

            // check whether the contest_sale_id is already in the database
            if (!Helper.checkEntityExists("contest_sale", "contest_sale_id", contestSaleId, conn)) {
                return false;
            }

            // remove the contest sale
            Object[] queryArgs = new Object[] {contestSaleId};
            Helper.doDMLQuery(conn, DELETE_CONTEST_SALE_BY_ID_SQL, queryArgs);

            closeConnection(conn);

            return true;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR,
        			new LogMessage(null, null, "Fails to remove contest sale", e));
        	if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }
	/**
	 * Retrieves an array of project
	 * The project instances are retrieved with their properties.
	 * @param rows
	 * @param conn the database connection
	 * @return An array of project instances.
	 * @throws PersistenceException
	 */
	
	private Project[] getProjects(Object [][]rows, Connection conn) throws PersistenceException {
		
		// 	create the Project array.
        Project[] projects = new Project[rows.length];
        
        // if no tc direct project found
        if(projects.length == 0 ) {        	
            return projects;
        }

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create the ProjectStatus object
            ProjectStatus status = new ProjectStatus(((Long) row[1])
                    .longValue(), (String) row[2]);

            // create the ProjectType object
            ProjectType type = new ProjectType(((Long) row[5]).longValue(),
                    (String) row[6]);

            // create the ProjectCategory object
            ProjectCategory category = new ProjectCategory(((Long) row[3])
                    .longValue(), (String) row[4], type);
            category.setDescription((String) row[11]);
            // create a new instance of ProjectType class
            projects[i] = new Project(((Long) row[0]).longValue(), category,
                    status);

            // assign the audit information
            projects[i].setCreationUser((String) row[7]);
            projects[i].setCreationTimestamp((Date) row[8]);
            projects[i].setModificationUser((String) row[9]);
            projects[i].setModificationTimestamp((Date) row[10]);
            projects[i].setTcDirectProjectId(((Long)row[12]).longValue());
            
            //
            // Added for Cockpit Launch Contest - Update for Spec Creation v1.0
            //
            ProjectSpec[] specs = getProjectSpecs(projects[i].getId(), conn);
            if (specs != null && specs.length > 0) {
                projects[i].setProjectSpec(specs[0]);
            }
        }

        // get the Id-Project map
        Map projectMap = makeIdProjectMap(projects);
        String ids = projectMap.keySet().toString();
        ids = ids.replace('[', '(');
        ids = ids.replace(']', ')');
        
        // find project properties in the table.
        rows = Helper.doQuery(conn, QUERY_PROJECT_PROPERTIES_SQL + ids ,
                new Object[] {}, QUERY_PROJECT_PROPERTIES_COLUMN_TYPES);

        // enumerate each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // get the corresponding Project object
            Project project = (Project) projectMap.get(row[0]);

            // set the property to project
            project.setProperty((String) row[1], (String)row[2]);
        }         
        return projects;
        
	}
	
	/**
     * <p>
     * Gets the list of ProjectSpec for the specified projectId.
     * Though the method signature is to return list, but it essentially returns the ProjectSpec corresponding to the latest version id
     * for the specified projectId's spec.
     * </p>
     * 
     * <p>
     * Updated since 1.3.1 to add private description support
     * </p>
     * 
     * @param projectId the project id for which project spec need to be persisted.
     * @param conn the db connection to be used to create this project spec
     * 
     * @return the list of ProjectSpec for the specified projectId.
     * @throws PersistenceException exception is thrown when there is some error in persisting 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
	private ProjectSpec[] getProjectSpecs(Long projectId, Connection conn) throws PersistenceException {
	     // get the project objects
        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn,
                QUERY_PROJECT_SPEC_SQL, new Object[] {projectId, projectId},
                QUERY_PROJECT_SPEC_COLUMN_TYPES);
        
        if (rows == null || rows.length == 0) {
            return null;
        }
        
        ProjectSpec[] specs = new ProjectSpec[rows.length];
        for (int i = 0; i < rows.length; i++) {
            ProjectSpec spec = new ProjectSpec();
         
            spec.setProjectSpecId((Long) rows[i][0]);
            spec.setProjectId(projectId);
            spec.setVersion((Long) rows[i][1]);
            spec.setDetailedRequirements((String) rows[i][2]);
            spec.setSubmissionDeliverables((String) rows[i][3]);
            spec.setEnvironmentSetupInstructions((String) rows[i][4]);
            spec.setFinalSubmissionGuidelines((String) rows[i][5]);
            spec.setCreationUser((String) rows[i][6]);
            spec.setCreationTimestamp((Date) rows[i][7]);
            spec.setModificationUser((String) rows[i][8]);
            spec.setModificationTimestamp((Date) rows[i][9]);
            spec.setPrivateDescription((String) rows[i][10]);


            // use the corresponding xxx_texts column if there are data present.
            if(rows[i][11] != null && ((String) rows[i][11]).trim().length() > 0) {
                spec.setDetailedRequirements((String) rows[i][11]);
            }

            if(rows[i][12] != null && ((String) rows[i][12]).trim().length() > 0) {
                spec.setFinalSubmissionGuidelines((String) rows[i][12]);
            }

            if(rows[i][13] != null && ((String) rows[i][13]).trim().length() > 0) {
                spec.setPrivateDescription((String) rows[i][13]);
            }

            specs[i] = spec;
        }
        
        return specs;
	}
	
	/**
     * <p>
     * Gets the full list of contests.
     * </p>
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.1]
     *     - project and contest permissions are also fetched now.
     * Updated for Cockpit Release Assembly 10:
     * 	   - add set SubmissionEndDate
     * Updated for Direct Search Assembly
     *     - add payment information.
     * </p>
     * 
     * @return the full list of contests.
     * @throws PersistenceException exception is thrown when there is error retrieving the list from persistence.
     * @throws ParseException exception is thrown when there is error in parsing the results retrieved from persistence.
     */
	public List<SimpleProjectContestData> getSimpleProjectContestData() throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getSimpleProjectContestData method."));

		Connection conn = null;
		try {
			// create the connection
			conn = openConnection();

			// get the project objects
			// find projects in the table.
			Object[][] rows = Helper.doQuery(conn,
					QUERY_ALL_SIMPLE_PROJECT_CONTEST, new Object[] {},
					this.QUERY_ALL_SIMPLE_PROJECT_CONTEST_COLUMN_TYPES);
	        getLogger().log(Level.INFO, new LogMessage(null,null,"Found "+rows.length + " records"));

			SimpleProjectContestData[] ret = new SimpleProjectContestData[rows.length];
			 SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			 List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();

			for(int i=0;i<rows.length;i++)
			{
				ret[i]=new SimpleProjectContestData();
				ret[i].setContestId((Long)rows[i][0]);
				// if have phase, use phase as stutus, otherwise use project status
				/* [BUGR-2038]: See comments at the class level. Status is either 'Scheduled'
				  or 'Draft' or from the DB as done previously.*/

				// try to use phase if not null
				if (rows[i][1] != null && ((String)rows[i][6]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName()))
				{
					ret[i].setSname(((String)rows[i][1]).trim());
				}
				// else for active, use 'newstatus'
				else if (rows[i][15] != null && ((String)rows[i][6]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName()))
				{
					ret[i].setSname(((String)rows[i][15]).trim());
				}
				// use status
				else
				{
					ret[i].setSname(((String)rows[i][6]).trim());
				}
				
				ret[i].setCname((String)rows[i][2]);
				if (rows[i][3] != null)
				{
					ret[i].setStartDate(myFmt.parse(rows[i][3].toString()));
				}
				if (rows[i][4] != null)
				{
					ret[i].setEndDate(myFmt.parse(rows[i][4].toString()));
				}
				
				ret[i].setType((String)rows[i][5]);
				ret[i].setNum_reg(new Integer(((Long)rows[i][7]).intValue()));
				ret[i].setNum_sub(new Integer(((Long)rows[i][8]).intValue()));
				ret[i].setNum_for(new Integer(((Long)rows[i][9]).intValue()));
				ret[i].setProjectId((Long)rows[i][10]);
				ret[i].setPname((String)rows[i][11]);
				ret[i].setDescription((String)rows[i][12]);
				ret[i].setCreateUser((String)rows[i][13]);
				if (rows[i][14] != null)
				{
					ret[i].setForumId(new Integer(((Long)rows[i][14]).intValue()));
				}
				
				if (rows[i][16] != null) {
				    ret[i].setCperm((String) rows[i][16]);
				}
				
				if (rows[i][17] != null) {
                    		    ret[i].setPperm((String) rows[i][17]);
                		}

                if (rows[i][18] != null) {
                    ret[i].setSubmissionEndDate(myFmt.parse(rows[i][18].toString()));
                }
                //it is the srprojectId
                if (rows[i][19] != null) {
                    ret[i].setSpecReviewProjectId((Long)rows[i][19]);
                    //show spec review status only if the original project has not been started, so it is draft or scheduled
                    if (ret[i].getSname().equalsIgnoreCase("Scheduled") || ret[i].getSname().equalsIgnoreCase("Draft")) {
                      //it is number of reviewer assign to spec review project
                        int reviewerAssignToSepcReviewProject = ((Long)rows[i][20]).intValue();
                        //no reviwer assigned yet
                        if (reviewerAssignToSepcReviewProject == 0) {
                            ret[i].setSpecReviewStatus("Assigning Reviewer");
                        } else {
                            //21, checking whether in 'reviewing' status
                            if (rows[i][21] != null) {
                                ret[i].setSpecReviewStatus("Reviewing");
                            } else {
                              //check next value
                                int requiredItems = ((Long)rows[i][22]).intValue();
                                //no required items now
                                if (requiredItems == 0) {
                                    ret[i].setSpecReviewStatus("Spec Review Passed");
                                } else {
                                    // if not in final review, or in FF but no review item
                                    if (rows[i][25] == null) {
                                        ret[i].setSpecReviewStatus("Spec Review Failed");
                                    }
                                    else {
                                        int notFixedItems = ((Long)rows[i][23]).intValue();
                                        if (notFixedItems == 0) {
                                            ret[i].setSpecReviewStatus("Spec Review Passed");
                                        } else {
                                            int notFixedFinalItems = ((Long)rows[i][24]).intValue();
                                            if (notFixedFinalItems > 0) {
                                                ret[i].setSpecReviewStatus("Spec Review Passed");
                                            } else {
                                                ret[i].setSpecReviewStatus("Spec Review Failed");
                                            }
                                        }
                                    }
                                }
                            }                            
                        }
                    }
                }

                // set contest fee/ price sum
                ret[i].setContestFee((Double)rows[i][26]);

				if (ret[i].getCperm() != null || ret[i].getPperm() != null)
				{
					result.add(ret[i]);
				}

			}
			closeConnection(conn);
	        getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getSimpleProjectContestData method."));

			return Arrays.asList(ret);
		} catch (PersistenceException e) {
			getLogger().log(
					Level.ERROR,
					new LogMessage(null, null,
							"Fails to retrieving all tc direct projects ", e));
			if (conn != null) {
				closeConnectionOnError(conn);
			}
			throw e;
		} catch (ParseException e) {
			getLogger().log(
					Level.ERROR,
					new LogMessage(null, null,
							"Fails to retrieving all tc direct projects ", e));
			if (conn != null) {
				closeConnectionOnError(conn);
			}
			throw new PersistenceException("Fails to retrieve all tc direct projects", e);
		}

	}

	/**
     * <p>
     * Gets the list of contest for specified tc project id.
     * </p>
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.1]
     *     - project and contest permissions are also fetched now.
     * </p>
     * Updated for Cockpit Release Assembly 10:
     * 	   - add set SubmissionEndDate
     *
     * Updated for Cockpit Spec Review - Stage 2(version 1.2.4).
     *
     * Updated for Direct Search Assembly
     *     - add payment information.
     *
     * @param pid the specified tc project id for which to get the list of contest.
     * @return the list of contest for specified tc project id.
     * @throws PersistenceException exception is thrown when there is error retrieving the list from persistence.
     * @throws ParseException exception is thrown when there is error in parsing the results retrieved from persistence.
     */
	public List<SimpleProjectContestData> getSimpleProjectContestData(long pid) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getSimpleProjectContestData(pid) method."));

		Connection conn = null;
		try {
			// create the connection
			conn = openConnection();

			// get the project objects
			// find projects in the table.
			Object[][] rows = Helper.doQuery(conn,
					QUERY_ALL_SIMPLE_PROJECT_CONTEST_BY_PID+pid, new Object[] {},
					this.QUERY_ALL_SIMPLE_PROJECT_CONTEST_COLUMN_TYPES);
	        getLogger().log(Level.INFO, new LogMessage(null,null,"Found "+rows.length + " records"));

			SimpleProjectContestData[] ret = new SimpleProjectContestData[rows.length];
			 SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();


			for(int i=0;i<rows.length;i++)
			{
				ret[i]=new SimpleProjectContestData();
				ret[i].setContestId((Long)rows[i][0]);
				/* [BUGR-2038]: See comments at the class level. Status is either 'Scheduled'
				  or 'Draft' or from the DB as done previously.*/

				// try to use phase if not null
				if (rows[i][1] != null && ((String)rows[i][6]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName()))
				{
					ret[i].setSname(((String)rows[i][1]).trim());
				}
				// else for active, use 'newstatus'
				else if (rows[i][15] != null && ((String)rows[i][6]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName()))
				{
					ret[i].setSname(((String)rows[i][15]).trim());
				}
				// use status
				else
				{
					ret[i].setSname(((String)rows[i][6]).trim());
				}
				
				ret[i].setCname((String)rows[i][2]);
				if (rows[i][3] != null)
				{
					ret[i].setStartDate(myFmt.parse(rows[i][3].toString()));
				}
				if (rows[i][4] != null)
				{
					ret[i].setEndDate(myFmt.parse(rows[i][4].toString()));
				}
				
				ret[i].setType((String)rows[i][5]);
				ret[i].setNum_reg(new Integer(((Long)rows[i][7]).intValue()));
				ret[i].setNum_sub(new Integer(((Long)rows[i][8]).intValue()));
				ret[i].setNum_for(new Integer(((Long)rows[i][9]).intValue()));
				ret[i].setProjectId((Long)rows[i][10]);
				ret[i].setPname((String)rows[i][11]);
				ret[i].setDescription((String)rows[i][12]);
				ret[i].setCreateUser((String)rows[i][13]);
				if (rows[i][14] != null)
				{
					ret[i].setForumId(new Integer(((Long)rows[i][14]).intValue()));
				}
				
				if (rows[i][16] != null) {
				    ret[i].setCperm((String) rows[i][16]);
				}
				
				if (rows[i][17] != null) {
                     		    ret[i].setPperm((String) rows[i][17]);
                		}

                // set contest fee/ price sum
                ret[i].setContestFee((Double)rows[i][26]);

                if (ret[i].getCperm() != null || ret[i].getPperm() != null)
                {
                    result.add(ret[i]);
                }

                if (rows[i][18] != null) {
                    ret[i].setSubmissionEndDate(myFmt.parse(rows[i][18].toString()));
                }

                 //it is the srprojectId
                if (rows[i][19] != null) {
                    ret[i].setSpecReviewProjectId((Long)rows[i][19]);
                } else {
                    //there is no sepc review project, so don't need to set the spec review status
                    continue;
                }
                
               //show spec review status only if the original project has not been started, so it is draft or scheduled
                if (!ret[i].getSname().equalsIgnoreCase("Scheduled") && !ret[i].getSname().equalsIgnoreCase("Draft")) {
                    continue;
                }

                //it is number of reviewer assign to spec review project
                int reviewerAssignToSepcReviewProject = ((Long)rows[i][20]).intValue();
                //no reviwer assigned yet
                if (reviewerAssignToSepcReviewProject == 0) {
                    ret[i].setSpecReviewStatus("Assigning Reviewer");
                } else {
                    if (rows[i][21] != null) {
                        ret[i].setSpecReviewStatus("Reviewing");
                        continue;
                    }
                    //check next value
                    int requiredItems = ((Long)rows[i][22]).intValue();
                    //no required items now
                    if (requiredItems == 0) {
                        ret[i].setSpecReviewStatus("Spec Review Passed");
                    } else {
                        // if not in final review, or in FF but no review item
                        if (rows[i][25] == null) {
                            ret[i].setSpecReviewStatus("Spec Review Failed");
                        }
                        else {
                            int notFixedItems = ((Long)rows[i][23]).intValue();
                            if (notFixedItems == 0) {
                                ret[i].setSpecReviewStatus("Spec Review Passed");
                            } else {
                                int notFixedFinalItems = ((Long)rows[i][24]).intValue();
                                if (notFixedFinalItems > 0) {
                                    ret[i].setSpecReviewStatus("Spec Review Passed");
                                } else {
                                    ret[i].setSpecReviewStatus("Spec Review Failed");
                                }
                            }
                        }
                    }
                }
            }

			closeConnection(conn);
	        getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getSimpleProjectContestData(pid) method."));
			return Arrays.asList(ret);

		} catch (PersistenceException e) {
			getLogger().log(
					Level.ERROR,
					new LogMessage(null, null,
							"Fails to retrieving all tc direct projects ", e));
			if (conn != null) {
				closeConnectionOnError(conn);
			}
			throw e;
		} catch (ParseException e) {
			getLogger().log(
					Level.ERROR,
					new LogMessage(null, null,
							"Fails to retrieving all tc direct projects ", e));
			if (conn != null) {
				closeConnectionOnError(conn);
			}
			throw new PersistenceException("Fails to retrieve all tc direct projects", e);
		}

	}
	
	/**
	 * <p>
	 * Gets the list of contest for specified user.
	 * </p>
	 * 
	 * <p>
	 * Updated for Cockpit Release Assembly 3 [RS: 1.1.1]
	 *     - project and contest permissions are also fetched now.
	 * </p>
	 * 
	 * Updated for Spec Reviews Finishing Touch v1.0
     *  - Changed the way now spec status is queried.
	 * 
     * Updated for Cockpit Release Assembly 10:
     * 	   - add set SubmissionEndDate
     *
     * Updated for Cockpit Spec Review - Stage 2(version 1.2.4).
     *
     * Updated for Direct Search Assembly
     *     - add payment information.
     *
     * @param createdUser the specified user for which to get the list of contest.
     * @return the list of contest for specified user.
     * @throws PersistenceException exception is thrown when there is error retrieving the list from persistence.
     * @throws ParseException exception is thrown when there is error in parsing the results retrieved from persistence.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestDataByUser(String createdUser) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getSimpleProjectContestDataByUser method."));

		Connection conn = null;
		try {
			// create the connection
			conn = openConnection();


			String qstr = 
			" select p.project_id as contest_id, "
			+		" (select ptl.name from phase_type_lu ptl where phase_type_id = (select min(phase_type_id) from project_phase ph " 
			+ " where ph.phase_status_id = 2 and ph.project_id=p.project_id)) as current_phase, "
			+ "(select value from project_info where project_id = p.project_id and project_info_type_id =6) as contest_name, "
			+ "(select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=p.project_id and ph.phase_type_id = 1) as start_date, "
			+ " (select max(nvl(actual_end_time, scheduled_end_time)) from project_phase ph where ph.project_id=p.project_id) as end_date, "
			+ "  pcl.name as contest_type, psl.name as status, "
			+ " (select count(resource_id) from resource r where r.project_id = p.project_id and resource_role_id = 1) as num_reg, "
			+ " (select count (distinct resource_id) from upload u where u.project_id = p.project_id and upload_status_id = 1 and upload_type_id = 1) as num_sub, "
			// fixed forum post
            		+ " (select count(messageid) from jivecategory c, jiveforum f, jivemessage m, project_info pi "
            		+ "        where pi.project_info_type_id =4 and c.categoryid = pi.value and c.categoryid = f.categoryid and m.forumid = f.forumid "
            		+ "             and pi.project_id =  p.project_id) as num_for, "
			+ " tc_direct_project_id as project_id, tcd.name, tcd.description, tcd.user_id, "
			+ "  (select value from project_info where project_id = p.project_id and project_info_type_id =4) as forum_id, "
			+ "  (select case when(count(*)>=1) then 'Scheduled' when(count(*)=0) then 'Draft' end "
			+ "   from contest_sale c where p.project_id = c.contest_id and upper(psl.name)='ACTIVE' ) as newstatus, "


			+ " (select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  "
			+ " from user_permission_grant as upg  where resource_id=p.project_id and is_studio=0 and user_id = " + createdUser
			+ " ),0)) as cperm, "

            + " (select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  "
            + " from user_permission_grant as upg  where resource_id=tcd.project_id and user_id = " + createdUser
            + " ),0)) as pperm, "

            /* Added in cockpit R 10 */
            + " (select scheduled_end_time from project_phase ph "
            + " where ph.phase_type_id = 2 and ph.project_id=p.project_id) as submission_end_date,"
            /* R 10 end*/

            /* updated by Cockpit Spec Review - Stage 2  - start */
            /* sepc review project id*/
            + "(select source_project_id from linked_project_xref where dest_project_id = p.project_id and link_type_id = 3) as srprojectId,"
            /* sepc review user */
            + "(select count(*) from resource r, linked_project_xref linkp where linkp.dest_project_Id = p.project_id"
            + " and linkp.link_type_id = 3 and r.project_id = linkp.source_project_id and r.resource_role_id = 4) as srr,"
            /* reviewing status check */
            + "(select min(ph.phase_type_id) from project_phase ph, linked_project_xref linkp "
            + " where ph.phase_type_id = 4 and ph.phase_status_id = 2 and ph.project_id = linkp.source_project_id and linkp.link_type_id = 3"
            + " and linkp.dest_project_Id = p.project_id) as sprs,"
            /* sepc review result to find the 'required' fixed item. */
            + "(select count(*) from review r , review_item ri, review_item_comment ric, resource re, linked_project_xref linkp "
            + "                    where r.review_id = ri.review_id "
            + "                          and ri.review_item_id = ric.review_item_id and comment_type_id = 3 "
            + "                          and r.resource_id = re.resource_id "
            + "                          and linkp.dest_project_Id = p.project_id "
            + "                          and linkp.link_type_id = 3 and re.resource_role_id = 4 "
            + "                          and re.project_id = linkp.source_project_id ) as srResult,"
            /* sepc review result to find the final fix 'fixed'. */
            + "(select count(*) from review r , review_item ri, review_item_comment ric, resource re, linked_project_xref linkp "
            + "                 where r.review_id = ri.review_id "
            + "                     and upper(nvl(ric.extra_info, '')) != 'FIXED' "
            + "                      and ric.comment_type_id = 3 and r.resource_id = re.resource_id and ri.review_item_id = ric.review_item_id "
            + "                      and linkp.dest_project_Id = p.project_id "
            + "                      and linkp.link_type_id = 3 and re.resource_role_id = 9 "
            + "                      and re.project_id = linkp.source_project_id) as srfresult, "
            /* sepc review result to find the final fix 'fixed' in response. */
            + "(select count(*) from review r , review_comment ri, resource re, linked_project_xref linkp "
            + "                 where r.review_id = ri.review_id "
            + "                     and upper(nvl(ri.extra_info, '')) == 'APPROVED' "
            + "                     and ri.comment_type_id = 10 and r.resource_id = re.resource_id "
            + "                     and linkp.dest_project_Id = p.project_id "
            + "                     and linkp.link_type_id = 3 and re.resource_role_id = 9 "
            + "                     and re.project_id = linkp.source_project_id) as srfrresult, "
            /* updated by Cockpit Spec Review - Stage 2  - end */

           // check if phase is FF open/close and there is final review
           + " (select min(ph.phase_type_id) from project_phase ph, linked_project_xref linkp "
           + "    where ph.phase_type_id = 10 and ph.phase_status_id in (2, 3) and ph.project_id = linkp.source_project_id "
           + "             and linkp.link_type_id = 3 and linkp.dest_project_Id = p.project_id "
           + "     and exists  "
           + "     (select * from review r , review_comment ri, resource re, linked_project_xref linkp "
           + "               where r.review_id = ri.review_id "
           + "                   and ri.comment_type_id = 10 and r.resource_id = re.resource_id "
           + "                   and linkp.dest_project_Id = p.project_id "
           + "                   and linkp.link_type_id = 3 and re.resource_role_id = 9 "
           + "                   and re.project_id = linkp.source_project_id)) as hassrfr, "

           // contest fee/ price sum
           + "  case when p.project_status_id in (1, 2) then "
           + "       nvl((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info "
           + "         where project_info_type_id in (31, 33, 35, 16, 38, 39, 49) "
           + "         and project_id = p.project_id), 0) "
           + "     + "
           + "       nvl((select nvl(sum (cast (nvl (pi30.value, '0') as DECIMAL (10,2))), 0) from project_info pi30, project_info pi26 "
           + "       where pi30.project_info_type_id = 30 and pi26.project_info_type_id = 26 and pi26.project_id = pi30.project_id  "
           + "       and pi26.value = 'On' "
           + "       and pi26.project_id =  p.project_id ), 0) "
           + "     + "
           + "     nvl(((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info "
           + "         where project_info_type_id = 16 "
           + "         and project_id = p.project_id)/2), 0) "
           + " when p.project_status_id = 7 then "
           + "   nvl((SELECT SUM(nvl(total_amount, 0))  "
           + "        FROM informixoltp:payment pm INNER JOIN informixoltp:payment_detail pmd ON pm.most_recent_detail_id = pmd.payment_detail_id  "
           + "         WHERE pmd.component_project_id = p.project_id and installment_number = 1 "
           + "         AND NOT pmd.payment_status_id IN (65, 68, 69)), 0) "
           + "   +  "
           + "   NVL((SELECT sum(pmd2.total_amount)  "
           + "   FROM  informixoltp:payment_detail pmd,   "
           + "         informixoltp:payment pm LEFT OUTER JOIN informixoltp:payment_detail pmd2 on pm.payment_id = pmd2.parent_payment_id,  "
           + "         informixoltp:payment pm2  "
           + "    WHERE pmd.component_project_id = p.project_id and pmd2.installment_number = 1  "
           + "    and pm.most_recent_detail_id = pmd.payment_detail_id   "
           + "   and pm2.most_recent_detail_id = pmd2.payment_detail_id  "
           + "   AND NOT pmd2.payment_status_id IN (65, 68, 69)), 0) "
           + "     + "
           + "     nvl((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info "
           + "         where project_info_type_id  = 31 "
           + "         and project_id = p.project_id), 0) "
           + "     + "
           + "       nvl((select nvl(sum (cast (nvl (pi30.value, '0') as DECIMAL (10,2))), 0) from project_info pi30, project_info pi26 "
           + "       where pi30.project_info_type_id = 30 and pi26.project_info_type_id = 26 and pi26.project_id = pi30.project_id  "
           + "       and pi26.value = 'On' "
           + "       and pi26.project_id =  p.project_id ), 0) "
           + "  else  nvl((SELECT SUM(nvl(total_amount, 0))  "
           + "        FROM informixoltp:payment pm INNER JOIN informixoltp:payment_detail pmd ON pm.most_recent_detail_id = pmd.payment_detail_id  "
           + "          WHERE pmd.component_project_id = p.project_id and installment_number = 1 "
           + "          AND NOT pmd.payment_status_id IN (65, 68, 69)), 0) "
           + "   +  "
           + "   NVL((SELECT sum(pmd2.total_amount)  "
           + "   FROM  informixoltp:payment_detail pmd,   "
           + "         informixoltp:payment pm LEFT OUTER JOIN informixoltp:payment_detail pmd2 on pm.payment_id = pmd2.parent_payment_id,  "
           + "         informixoltp:payment pm2  "
           + "    WHERE pmd.component_project_id = p.project_id and pmd2.installment_number = 1  "
           + "    and pm.most_recent_detail_id = pmd.payment_detail_id   "
           + "   and pm2.most_recent_detail_id = pmd2.payment_detail_id  "
           + "   AND NOT pmd2.payment_status_id IN (65, 68, 69)), 0) "
           + "     + "
           + "     nvl((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info "
           + "         where project_info_type_id  = 31 and exists (select * from project_phase where project_id = p.project_id and phase_type_id = 1 and phase_status_id in (2,3)) "
           + "         and project_id = p.project_id), 0) "
           + " end  as contest_fee "

            + " from project p, project_category_lu pcl, project_status_lu psl, tc_direct_project tcd "
            + " where p.project_category_id = pcl.project_category_id and p.project_status_id = psl.project_status_id and p.tc_direct_project_id = tcd.project_id "
            + " and p.project_status_id != 3 "
            + " and (p.create_user = " + createdUser + " OR exists "
            + "     (select user_id from user_permission_grant upg where upg.user_id = " + createdUser
            + "      and ((upg.resource_id = p.project_id and is_studio = 0) "
            + "        OR upg.resource_id = tcd.project_id)) OR exists "
            + "       (select gm.group_id from group_member gm, "
            + "         group_associated_direct_projects gadp, "
            + "         customer_group g "
            + "         where gm.active=1 and gm.unassigned_on is null "
            + "         and gadp.group_id=gm.group_id "
            + "         and gadp.tc_direct_project_id=tcd.project_id "
            + "         and gm.specific_permission != 'REPORT' "
            + "         and gm.user_id = " + createdUser
            + "         and g.group_id = gm.group_id "
            + "         and g.archived = 0) "
            + "    0R exists "
            + "       (select gm.group_id from group_member gm, "
            + "         group_associated_billing_accounts gaba, "
            + "         customer_group g, "
            + "         corporate_oltp:direct_project_account dpa "
            + "         where gm.active=1 and gm.unassigned_on is null "
            + "         and gaba.group_id=gm.group_id "
            + "         and dpa.project_id=tcd.project_id "
            + "         and gaba.billing_account_id=dpa.billing_account_id "
            + "         and ((gm.use_group_default=0 and gm.specific_permission!='REPORT') "
            + "         or (gm.use_group_default=1 and g.default_permission != 'REPORT')) "
            + "         and gm.user_id = " + createdUser
            + "         and g.group_id = gm.group_id "
            + "         and g.archived = 0) "
            + "    OR exists "
            + "         ( SELECT "
            + "               gm.group_id "
            + "           FROM"
            + "              tc_direct_project tdp"
            + "        INNER JOIN corporate_oltp:direct_project_account dpa ON tdp.project_id = dpa.project_id"
            + "        INNER JOIN tt_project ttp ON dpa.billing_account_id = ttp.project_id"
            + "        INNER JOIN tt_client_project ttcp ON ttp.project_id = ttcp.project_id"
            + "        INNER JOIN tt_client ttc ON ttcp.client_id = ttc.client_id"
            + "        INNER JOIN customer_group sg ON sg.client_id = ttc.client_id"
            + "        INNER JOIN group_member gm ON sg.group_id = gm.group_id"
            + "    WHERE"
            + "        sg.auto_grant = 1"
            + "        AND   gm.active = 1 and gm.unassigned_on is null "
            + "        AND   sg.archived = 0"
            + "        AND tdp.project_id = tcd.project_id"
            + "        AND ttc.client_status_id = 1"
            + "         and ((gm.use_group_default=0 and gm.specific_permission!='REPORT') "
            + "         or (gm.use_group_default=1 and g.default_permission != 'REPORT')) "
            + "        AND   gm.user_id = " + createdUser
            + "         )"
            + "    ) "
            // dont show spec review project
            + " and p.project_category_id != 27 ";

			// get the project objects
			// find projects in the table.
			Object[][] rows = Helper.doQuery(conn,
					qstr, new Object[] {},
					this.QUERY_ALL_SIMPLE_PROJECT_CONTEST_COLUMN_TYPES);
	        getLogger().log(Level.INFO, new LogMessage(null,null,"Found "+rows.length + " records"));

			SimpleProjectContestData[] ret = new SimpleProjectContestData[rows.length];
			 SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			 List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();

			for(int i=0;i<rows.length;i++)
			{
				ret[i]=new SimpleProjectContestData();
				ret[i].setContestId((Long)rows[i][0]);
				/* [BUGR-2038]: See comments at the class level. Status is either 'Scheduled'
				  or 'Draft' or from the DB as done previously.*/

				// try to use phase if not null
				if (rows[i][1] != null && ((String)rows[i][6]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName()))
				{
					ret[i].setSname(((String)rows[i][1]).trim());
				}
				// else for active, use 'newstatus'
				else if (rows[i][15] != null && ((String)rows[i][6]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName()))
				{
					ret[i].setSname(((String)rows[i][15]).trim());
				}
				// use status
				else
				{
					ret[i].setSname(((String)rows[i][6]).trim());
				}
				
				ret[i].setCname((String)rows[i][2]);
				if (rows[i][3] != null)
				{
					ret[i].setStartDate(myFmt.parse(rows[i][3].toString()));
				}
				if (rows[i][4] != null)
				{
					ret[i].setEndDate(myFmt.parse(rows[i][4].toString()));
				}
				
				ret[i].setType((String)rows[i][5]);
				ret[i].setNum_reg(new Integer(((Long)rows[i][7]).intValue()));
				ret[i].setNum_sub(new Integer(((Long)rows[i][8]).intValue()));
				ret[i].setNum_for(new Integer(((Long)rows[i][9]).intValue()));
				ret[i].setProjectId((Long)rows[i][10]);
				ret[i].setPname((String)rows[i][11]);
				ret[i].setDescription((String)rows[i][12]);
				ret[i].setCreateUser((String)rows[i][13]);
				if (rows[i][14] != null)
				{
					ret[i].setForumId(new Integer(((Long)rows[i][14]).intValue()));
				}
				
				if (rows[i][16] != null) {
				    ret[i].setCperm((String) rows[i][16]);
				}
				
				if (rows[i][17] != null) {
                    		   ret[i].setPperm((String) rows[i][17]);
                		}


                // set contest fee/ price sum
                ret[i].setContestFee((Double)rows[i][26]);

                if (ret[i].getCperm() != null || ret[i].getPperm() != null)
                {
                    result.add(ret[i]);
                }

                if (rows[i][18] != null) {
                    ret[i].setSubmissionEndDate(myFmt.parse(rows[i][18].toString()));
                }

                //it is the srprojectId
                if (rows[i][19] != null) {
                    ret[i].setSpecReviewProjectId((Long)rows[i][19]);
                } else {
                    //there is no sepc review project, so don't need to set the spec review status
                    continue;
                }
                
               //show spec review status only if the original project has not been started, so it is draft or scheduled
                if (!ret[i].getSname().equalsIgnoreCase("Scheduled") && !ret[i].getSname().equalsIgnoreCase("Draft")) {
                    continue;
                }

                //it is number of reviewer assign to spec review project
                int reviewerAssignToSepcReviewProject = ((Long)rows[i][20]).intValue();
                //no reviwer assigned yet
                if (reviewerAssignToSepcReviewProject == 0) {
                    ret[i].setSpecReviewStatus("Assigning Reviewer");
                } else {
                    if (rows[i][21] != null) {
                        ret[i].setSpecReviewStatus("Reviewing");
                        continue;
                    }
                    //check next value
                    int requiredItems = ((Long)rows[i][22]).intValue();
                    //no required items now
                    if (requiredItems == 0) {
                        ret[i].setSpecReviewStatus("Spec Review Passed");
                    } else {
                        // if not in final review, or in FF but no review item
                        if (rows[i][25] == null) {
                            ret[i].setSpecReviewStatus("Spec Review Failed");
                        }
                        else {
                            int notFixedItems = ((Long)rows[i][23]).intValue();
                            if (notFixedItems == 0) {
                                ret[i].setSpecReviewStatus("Spec Review Passed");
                            } else {
                                int notFixedFinalItems = ((Long)rows[i][24]).intValue();
                                if (notFixedFinalItems > 0) {
                                    ret[i].setSpecReviewStatus("Spec Review Passed");
                                } else {
                                    ret[i].setSpecReviewStatus("Spec Review Failed");
                                }
                            }
                        }
                    }
                }
            }

			closeConnection(conn);
	        getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getSimpleProjectContestDataByUser method."));

			return result;
		} catch (PersistenceException e) {
			getLogger().log(
					Level.ERROR,
					new LogMessage(null, null,
							"Fails to retrieving all tc direct projects ", e));
			if (conn != null) {
				closeConnectionOnError(conn);
			}
			throw e;
		} catch (ParseException e) {
			getLogger().log(
					Level.ERROR,
					new LogMessage(null, null,
							"Fails to retrieving all tc direct projects ", e));
			if (conn != null) {
				closeConnectionOnError(conn);
			}
			throw new PersistenceException("Fails to retrieve all tc direct projects", e);
		}

	}
	
	/**
     * <p>
     * Gets the list of project their read/write/full permissions.
     * </p>
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.3]
     *      - Added check for is_studio=0 whenever user_permission_grant is joined with project table.
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
            throws PersistenceException {

        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            String qstr = "select project_id as contest_id, " +
            		" (select pinfo.value from project_info as pinfo where pinfo.project_id = c.project_id and pinfo.project_info_type_id = 6) as name, "
                    + " tc_direct_project_id, "
                    + " ( select name from tc_direct_project p where c.tc_direct_project_id = p.project_id) as pname, "
                    + " (select count( *)  from user_permission_grant as upg  where resource_id=c.tc_direct_project_id  and user_id= "
                    + createdUser
                    + " and permission_type_id=1 ) as pread, "
                    + " (select count( *)  from user_permission_grant as upg  where resource_id=c.tc_direct_project_id  and user_id=  "
                    + createdUser
                    + " and permission_type_id=2 ) as pwrite, "
                    + " (select count( *)  from user_permission_grant as upg  where resource_id=c.tc_direct_project_id  and user_id=  "
                    + createdUser
                    + " and permission_type_id=3 ) as pfull, "
                    + " (select count( *)  from user_permission_grant as upg  where resource_id=c.project_id and is_studio=0 and user_id=  "
                    + createdUser
                    + " and permission_type_id=4 ) as cread, "
                    + " (select count( *)  from user_permission_grant as upg  where resource_id=c.project_id and is_studio=0 and user_id=  "
                    + createdUser
                    + " and permission_type_id=5 ) as cwrite, "
                    + " (select count( *)  from user_permission_grant as upg  where resource_id=c.project_id and is_studio=0 and user_id=  "
                    + createdUser
                    + " and permission_type_id=6 ) as cfull "
                    + " from project c  "
                    + " where not c.tc_direct_project_id is null ";

            Object[][] rows = Helper.doQuery(conn, qstr, new Object[] {},
                    this.QUERY_SIMPLE_PROJECT_PERMISSION_DATA_COLUMN_TYPES);

            List<SimpleProjectPermissionData> result = new ArrayList<SimpleProjectPermissionData>();

            for (int i = 0; i < rows.length; i++) {

                SimpleProjectPermissionData c = new SimpleProjectPermissionData();
                c.setStudio(false);
                Object[] os = rows[i];
                
                // skip records that doesn't have tc project names.
                if (os[3] == null || os[3].equals("")) {
                    continue;
                }

                if (os[0] != null)
                    c.setContestId((Long) os[0]);
                if (os[1] != null)
                    c.setCname(os[1].toString());
                if (os[2] != null)
                    c.setProjectId((Long) os[2]);
                if (os[3] != null)
                    c.setPname(os[3].toString());

                if (createdUser < 0) {
                    // admin
                    c.setPfull(1);
                    c.setCfull(1);
                    result.add(c);
                    continue;
                }

                int pp = 0;
                if (os[4] != null) {
                    c.setPread(((Long) os[4]).intValue());
                    pp++;
                }
                if (os[5] != null) {
                    c.setPwrite(((Long) os[5]).intValue());
                    pp++;
                }
                if (os[6] != null) {
                    c.setPfull(((Long) os[6]).intValue());
                    pp++;
                }
                int cp = 0;
                if (os[7] != null) {
                    c.setCread(((Long) os[7]).intValue());
                    cp++;
                }
                if (os[8] != null) {
                    c.setCwrite(((Long) os[8]).intValue());
                    cp++;
                }
                if (os[9] != null) {
                    c.setCfull(((Long) os[9]).intValue());
                    cp++;
                }
                if (pp > 0 || cp > 0) {
                    result.add(c);
                }
            }

			closeConnection(conn);
            return result;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fails to retrieving all tc direct projects ", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        } 
    }


    private static boolean isSpecReviewSubmitter(int roleId, long projectCategoryId) {
        return roleId == SUBMITTER_ROLE_ID && projectCategoryId == ProjectCategory.PROJECT_CATEGORY_SPEC_REVIEW;
    }


     /**
     * Checks if the contest is studio contest.
     *
     * @param contest the conetst to check
     * @return true if the contest is studio contest, false otherwise
     * @since 1.6.6
     */
    private boolean isStudio(Project project) {
        return project.getProjectCategory().getProjectType().getId() == ProjectType.STUDIO.getId();
    }

    /**
     * 1. if there are client terms defined, insert these terms.
     * 2. if CCA, insert CCA terms
     * 3. insert standard terms if a role is not defined in 1.
     *
     * @param projectId the project id to associate
     * @param billingProjectId the client project id
     * @param projectCategoryId the project category
     * @param standardCCA true for cca
     * @throws PersistenceException if any error occurs
     */
    private void createRoleTermOfUse(long projectId, long billingProjectId, long projectCategoryId, boolean standardCCA, boolean isStudio,
            Connection conn) throws PersistenceException {        
        PreparedStatement preparedStatement = null;
        try {
            //hold the role ids that has client's terms of use
            Set<String> added = new HashSet<String>();
            // build the statement            
            Object[][] rows = Helper.doQuery(conn, SELCT_PRIVATE_CONTEST_TERMS,
                new Object[] {billingProjectId}, new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE});
                
            //1. step 1, insert client defined terms
            preparedStatement = conn.prepareStatement(INSERT_PRIVATE_CONTEST_TERMS);
            preparedStatement.setLong(1, projectId);

            

            boolean hasClientTerm = false;
            if (rows.length > 0) {

                hasClientTerm = true;
                for (int i = 0; i < rows.length; i++) {
                    Object[] os = rows[i];
                    // if resource role id is 0 or null, insert for all
                    if (os[1] == null || os[1].toString().equals("") || os[1].toString().equals("0")) {
                        for (int roleId : ALL_ROLES_ID) { 
                            // no submitter for spec review
                            if (!isSpecReviewSubmitter(roleId, projectCategoryId)) {
                                preparedStatement.setInt(2, roleId);
                                preparedStatement.setObject(3, os[0]);
                                preparedStatement.execute();
                                added.add(roleId+"-"+os[0]);
                            }
                        }
                    } else { // otherwise insert for specified role                        
                        int roleId = ((Long)os[1]).intValue();
                         // no submitter for spec review
                        if (!isSpecReviewSubmitter(roleId, projectCategoryId)) {
                            preparedStatement.setObject(2, os[1]);
                            preparedStatement.setObject(3, os[0]);
                            preparedStatement.execute();
                            added.add(os[1]+"-"+os[0]);
                        }
                    }
                }
            }
            
            //2. insert cca terms
            if (standardCCA) {
                preparedStatement = conn.prepareStatement(INSERT_PRIVATE_CONTEST_TERMS);
                preparedStatement.setLong(1, projectId);

                if (projectCategoryId != ProjectCategory.COPILOT_POSTING.getId())
                {
                    for (int roleId : ALL_ROLES_ID) {
                        // no manager or observer for cca, for spec review, no submitter
                        if (roleId != MANAGER_ROLE_ID 
                            && roleId != APPROVER_ROLE_ID
                            && roleId != OBSERVER_ROLE_ID
                            && roleId != CLIENT_MANAGER_ROLE_ID
                            && roleId != COPILOT_ROLE_ID
                            && !isSpecReviewSubmitter(roleId, projectCategoryId)) {
                            preparedStatement.setInt(2, roleId);
                            if (!added.contains(roleId+"-"+STANDARD_CCA_TERMS_ID))
                            {
                                preparedStatement.setLong(3, STANDARD_CCA_TERMS_ID);
                                preparedStatement.execute(); 
                                added.add(roleId+"-"+STANDARD_CCA_TERMS_ID);
                            }

                            
                        }        
                    }
                }
                else 
                {
                    for (int roleId : ALL_ROLES_ID) {
                        // no manager or observer for cca, for spec review, no submitter
                        if (roleId != MANAGER_ROLE_ID 
                            && roleId != APPROVER_ROLE_ID
                            && roleId != OBSERVER_ROLE_ID
                            && roleId != CLIENT_MANAGER_ROLE_ID
                            && roleId != COPILOT_ROLE_ID                 
                            && roleId != REVIEWER_ROLE_ID
                            && roleId != PRIMARY_SCREENER_ROLE_ID
                            && roleId != FINAL_REVIEWER_ROLE_ID
                            && roleId != AGGREGATOR_ROLE_ID
                            && !isSpecReviewSubmitter(roleId, projectCategoryId)) {
                            preparedStatement.setInt(2, roleId);
                            if (!added.contains(roleId+"-"+STANDARD_CCA_TERMS_ID))
                            {
                                preparedStatement.setLong(3, STANDARD_CCA_TERMS_ID);
                                preparedStatement.execute(); 
                                added.add(roleId+"-"+STANDARD_CCA_TERMS_ID);
                            }

                        }        
                    }

                }
                
                

            } 

            if (projectCategoryId == ProjectCategory.COPILOT_POSTING.getId())
            {
                preparedStatement = conn.prepareStatement(INSERT_PRIVATE_CONTEST_TERMS);
                preparedStatement.setLong(1, projectId);

                for (int roleId : ALL_ROLES_ID) {
                    if (roleId == SUBMITTER_ROLE_ID 
                        || roleId == COPILOT_ROLE_ID)
                {
                        preparedStatement.setInt(2, roleId);

                        preparedStatement.setLong(3, COPILOT_MSA_TERMS_ID);
                        if (!added.contains(roleId+"-"+COPILOT_MSA_TERMS_ID))
                        {
                            preparedStatement.execute(); 
                            added.add(roleId+"-"+COPILOT_MSA_TERMS_ID);
                        }
                    }
                }
            }
            // set copilot terms
            else
            {
                preparedStatement = conn.prepareStatement(INSERT_PRIVATE_CONTEST_TERMS);
                preparedStatement.setLong(1, projectId);

                preparedStatement.setInt(2, COPILOT_ROLE_ID);

				preparedStatement.setLong(3, COPILOT_MSA_TERMS_ID);
                if (!added.contains(COPILOT_ROLE_ID+"-"+COPILOT_MSA_TERMS_ID))
                {
                    preparedStatement.execute(); 
                    added.add(COPILOT_ROLE_ID+"-"+COPILOT_MSA_TERMS_ID);
                }

            }

            //3. insert stardard terms
            if (!hasClientTerm && !added.contains(MANAGER_ROLE_ID+"-"+MANAGER_TERMS_ID))
            {
                createProjectRoleTermsOfUse(projectId, MANAGER_ROLE_ID, MANAGER_TERMS_ID, conn);  
                added.add(MANAGER_ROLE_ID+"-"+MANAGER_TERMS_ID);
            }
            // get the instance of ConfigManager
            ConfigManager cm = ConfigManager.getInstance();
            // always insert standard
            long submitterTermsId =  Long.parseLong(Helper.getConfigurationParameterValue(cm, namespace,
                PUBLIC_SUBMITTER_TERMS_ID_PARAMETER,
                getLogger(), Long.toString(PUBLIC_SUBMITTER_TERMS_ID)));
            int submitterRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                    SUBMITTER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(SUBMITTER_ROLE_ID)));
            long reviewerTermsId = Long.parseLong(Helper.getConfigurationParameterValue(cm, namespace,
                    PUBLIC_REVIEWER_TERMS_ID_PARAMETER, getLogger(), Long.toString(PUBLIC_REVIEWER_TERMS_ID)));

            // no submitter for spec review and no any client term
            if (projectCategoryId != ProjectCategory.PROJECT_CATEGORY_SPEC_REVIEW 
                    && !hasClientTerm && !added.contains(submitterRoleId+"-"+submitterTermsId)) {
                createProjectRoleTermsOfUse(projectId, submitterRoleId, submitterTermsId, conn);
                added.add(submitterRoleId+"-"+submitterTermsId);
            }

            if (projectCategoryId == PROJECT_CATEGORY_DEVELOPMENT) {
                int accuracyReviewerRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                        ACCURACY_REVIEWER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(ACCURACY_REVIEWER_ROLE_ID)));
                int failureReviewerRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                        FAILURE_REVIEWER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(FAILURE_REVIEWER_ROLE_ID)));
                int stressReviewerRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                        STRESS_REVIEWER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(STRESS_REVIEWER_ROLE_ID)));
                // if it's a development project there are several reviewer roles
                if (!hasClientTerm) {
                    if (!added.contains(accuracyReviewerRoleId+"-"+reviewerTermsId))
                    {
                        createProjectRoleTermsOfUse(projectId, accuracyReviewerRoleId, reviewerTermsId, conn);
                        added.add(accuracyReviewerRoleId+"-"+reviewerTermsId);
                    }
                    if (!added.contains(failureReviewerRoleId+"-"+reviewerTermsId))
                    {
                        createProjectRoleTermsOfUse(projectId, failureReviewerRoleId, reviewerTermsId, conn);
                        added.add(failureReviewerRoleId+"-"+reviewerTermsId);
                    }
                    if (!added.contains(stressReviewerRoleId+"-"+reviewerTermsId))
                    {
                        createProjectRoleTermsOfUse(projectId, stressReviewerRoleId, reviewerTermsId, conn);
                        added.add(stressReviewerRoleId+"-"+reviewerTermsId);
                    }
                    
                }
               
            } else {
                int reviewerRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                        REVIEWER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(REVIEWER_ROLE_ID)));
                // if it's not development there is a single reviewer role
                if (projectCategoryId != ProjectCategory.COPILOT_POSTING.getId() 
                        // no reviewer term for studio reviewer
                        && !isStudio
                        && !hasClientTerm 
                        && !added.contains(reviewerRoleId+"-"+reviewerTermsId)) {
                    createProjectRoleTermsOfUse(projectId, reviewerRoleId, reviewerTermsId, conn);
                    added.add(reviewerRoleId+"-"+reviewerTermsId);
                }
            }

            // also add terms for the rest of the reviewer roles
            int primaryScreenerRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                    PRIMARY_SCREENER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(PRIMARY_SCREENER_ROLE_ID)));
            int aggregatorRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                    AGGREGATOR_ROLE_ID_PARAMETER, getLogger(), Integer.toString(AGGREGATOR_ROLE_ID)));
            int finalReviewerRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                    FINAL_REVIEWER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(FINAL_REVIEWER_ROLE_ID)));
            
            if (projectCategoryId != ProjectCategory.COPILOT_POSTING.getId() && !hasClientTerm) {
                if (!added.contains(primaryScreenerRoleId+"-"+reviewerTermsId))
                {
                    createProjectRoleTermsOfUse(projectId, primaryScreenerRoleId, reviewerTermsId, conn);
                    added.add(primaryScreenerRoleId+"-"+reviewerTermsId);
                }
                if (!added.contains(aggregatorRoleId+"-"+reviewerTermsId))
                {
                    createProjectRoleTermsOfUse(projectId, aggregatorRoleId, reviewerTermsId, conn);
                    added.add(aggregatorRoleId+"-"+reviewerTermsId);
                }
                if (!added.contains(finalReviewerRoleId+"-"+reviewerTermsId))
                {
                    createProjectRoleTermsOfUse(projectId, finalReviewerRoleId, reviewerTermsId, conn);
                    added.add(finalReviewerRoleId+"-"+reviewerTermsId);
                }
                if (!added.contains(SPECIFICATION_REVIEWER_ROLE_ID+"-"+reviewerTermsId))
                {
                    createProjectRoleTermsOfUse(projectId, SPECIFICATION_REVIEWER_ROLE_ID, reviewerTermsId, conn);
                    added.add(SPECIFICATION_REVIEWER_ROLE_ID+"-"+reviewerTermsId);
                }
                
            }

        } catch (ConfigurationException e) {
            throw new PersistenceException(e.getMessage(), e);
        } catch (SQLException e) {
            getLogger().log(
                    Level.ERROR, new LogMessage(null, null,
                            "Fails to create the private project role terms of use ", e));      
            throw new PersistenceException(e.getMessage(), e);
                  
        } finally {
            Helper.closeStatement(preparedStatement);
        }
    }


    /**
     * Private helper method to generate default Project Role Terms of Use associations for a given project.
     * 
     * Updated for version 1.1.2
     *
     * @param projectId the project id for the associations
     * @param conn the database connection
     *
     * @throws ConfigManagerException if Configuration Manager fails to retrieve the configurations
     * @throws PersistenceException if any errors occur during EJB lookup
     */
    private void cleanProjectRoleTermsOfUseAssociations(long projectId, Connection conn)throws PersistenceException {

        PreparedStatement ps = null;
        try {
            StringBuffer query = new StringBuffer(1024);
            query.append("delete ");
            query.append("from project_role_terms_of_use_xref ");
            query.append("where project_id = ? ");

            // delete all
            ps = conn.prepareStatement(query.toString());
            ps.setLong(1, projectId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage(), e);
        } finally {
			Helper.closeStatement(ps);
        }
        
    }


    /**
     * This method will create a project role terms of use association.
     *
     * @param projectId the project id to associate
     * @param resourceRoleId the role id to associate
     * @param termsOfUseId the terms of use id to associate
     * @param Connection the data source connection.
     * @throws PersistenceException if any error occurs
     */
    private void createProjectRoleTermsOfUse(long projectId, int resourceRoleId, long termsOfUseId, Connection conn)
            throws PersistenceException {

        PreparedStatement ps = null;

        try
        {
            StringBuffer query = new StringBuffer(1024);
            query.append("INSERT ");
            query.append("INTO project_role_terms_of_use_xref (project_id, resource_role_id, terms_of_use_id) ");
            query.append("VALUES (?, ?, ?)");

            ps = conn.prepareStatement(query.toString());
            ps.setLong(1, projectId);
            ps.setInt(2, resourceRoleId);
            ps.setLong(3, termsOfUseId);

            int rc = ps.executeUpdate();
            if (rc != 1) {
                throw(new PersistenceException("Wrong number of rows inserted into " +
                        "'project_role_terms_of_use_xref'. Inserted " + rc + ", " +
                        "should have inserted 1."));
            }
        }
        catch (SQLException e)
        {
            throw(new PersistenceException(e.getMessage()));
        }
         finally {
            Helper.closeStatement(ps);
        }
        
    }
    
    
    /**
     * Gets the list of simple pipeline data for specified user id and between specified start and end date.
     *
     * <p>
     * Update in version 1.5.3 (Release Assembly - TC Cockpit Project Report Permission)
     * - Check the user permission checking on direct project in SQL to allow project report permission.
     * </p>
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
     * @since 1.1.1             
     */
    public List<SimplePipelineData> getSimplePipelineData(long userId, Date startDate, Date endDate,
            boolean overdueContests) throws PersistenceException {
        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();
            
            Calendar start = new GregorianCalendar();
            start.setTime(startDate);
            start.set(Calendar.HOUR_OF_DAY, 0);
            start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0);
            startDate = start.getTime();

            Calendar end = new GregorianCalendar();
            end.setTime(endDate);
            end.set(Calendar.HOUR_OF_DAY, 23);
            end.set(Calendar.MINUTE, 59);
            end.set(Calendar.SECOND, 59);
            endDate = end.getTime();


            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT s.contest_id, s.cname, s.cversion, s.project_id,");
            sb.append("   s.pname, s.contest_type_desc, s.category, s.sname,");
            sb.append("   s.start_time, s.end_time, s.duration_start_time,");
            sb.append("   s.duration_end_time, s.creation_time, s.modification_time,");
            sb.append("   s.client_name, s.tot_prize,");
            sb.append("   (case when s.contest_fee_percentage = 0 then s.contest_fee");
            sb.append("      else s.contest_fee_percentage * s.tot_prize end) as contest_fee,");
            sb.append("   s.manager, s.pperm, s.cperm, s.current_phase, s.newstatus,");
            sb.append("   s.cpname, s.repost, s.copilot, s.project_category_id,");
            sb.append("   s.phases, s.client_id");
            sb.append(" FROM table(MULTISET(");            
            sb.append(" select   c.project_id as contest_id, ");
            sb.append("     (select value from project_info where project_info_type_id = 6 and project_id = c.project_id) as cname, ");
            sb.append("     (select value from project_info where project_info_type_id = 7 and project_id = c.project_id) as cversion, ");
            sb.append("     p.project_id as project_id, ");
            sb.append("      NVL(p.name, 'None') as pname, ");
            sb.append("     ptl.name as contest_type_desc, ");
            sb.append("     pcl.name as category, ");
            sb.append("     psl.name as sname, ");            
            sb.append("     nvl((select cast (nvl(pi57.value, '0') as DECIMAL (10,2)) from project_info pi57");
            sb.append("       where p.project_id = pi57.project_id and pi57.project_info_type_id = 57),0) as contest_fee_percentage, ");
            sb.append("     c.project_status_id,");
            sb.append("     (select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=c.project_id) as start_time, ");
            sb.append("     (select max(nvl(actual_end_time, scheduled_end_time)) from project_phase ph where ph.project_id=c.project_id) as end_time, ");
            sb.append("     (select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=c.project_id and ph.phase_type_id=2) as duration_start_time, ");
            sb.append("     (select max(nvl(actual_end_time, scheduled_end_time)) from project_phase ph where ph.project_id=c.project_id and ph.phase_type_id=2) as duration_end_time, ");
            sb.append("     c.create_date as creation_time, ");
            sb.append("     c.modify_date as modification_time, ");
            sb.append("  ");
            sb.append("     NVL((select unique cl.name ");
            sb.append("     from project_info as pi ");
            sb.append("     left outer join tt_project as ttp  ");
            sb.append("     on pi.value::DECIMAL(10,2) = ttp.project_id ");
            sb.append("     left outer join tt_client_project cpx ");
            sb.append("     on ttp.project_id = cpx.project_id   ");
            sb.append("     left outer join tt_client as cl ");
            sb.append("     on cpx.client_id = cl.client_id ");
            sb.append("     where pi.project_id = c.project_id and pi.project_info_type_id = 32), 'One Off') as client_name, ");
            sb.append("  ");
            sb.append("     case when c.project_status_id in (1, 2) then   ");
            sb.append("        nvl((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info ");
            sb.append("             where project_info_type_id in (33, 35, 16, 38, 39, 49) ");
            sb.append("             and project_id = c.project_id), 0)  ");
            sb.append("        +  ");
            sb.append("       nvl((select nvl(sum (cast (nvl (pi30.value, '0') as DECIMAL (10,2))), 0) from project_info pi30, project_info pi26 ");
            sb.append("       where pi30.project_info_type_id = 30 and pi26.project_info_type_id = 26 and pi26.project_id = pi30.project_id   ");
            sb.append("       and pi26.value = 'On'  ");
            sb.append("       and pi26.project_id =  p.project_id ), 0)   ");
            sb.append("        +  ");
            sb.append("       nvl(((select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info ");
            sb.append("             where project_info_type_id = 16 ");
            sb.append("             and project_id = c.project_id)/2), 0) ");
            sb.append("     when   c.project_status_id = 7 then ");
            sb.append("       nvl((SELECT SUM(total_amount)  ");
            sb.append("             FROM informixoltp:payment pm INNER JOIN informixoltp:payment_detail pmd ON pm.most_recent_detail_id = pmd.payment_detail_id  ");
            sb.append("             WHERE pmd.component_project_id = c.project_id and installment_number = 1 ");
            sb.append("                   AND NOT pmd.payment_status_id IN (65, 68, 69)),0 ) + ");

            sb.append("       NVL((SELECT sum(pmd2.total_amount)  ");
            sb.append("          FROM  informixoltp:payment_detail pmd,   ");
            sb.append("                informixoltp:payment pm LEFT OUTER JOIN informixoltp:payment_detail pmd2 on pm.payment_id = pmd2.parent_payment_id,  ");
            sb.append("                informixoltp:payment pm2  ");
            sb.append("           WHERE pmd.component_project_id = c.project_id and pmd2.installment_number = 1  ");
            sb.append("           and pm.most_recent_detail_id = pmd.payment_detail_id   ");
            sb.append("          and pm2.most_recent_detail_id = pmd2.payment_detail_id  ");
            sb.append("         AND NOT pmd2.payment_status_id IN (65, 68, 69)), 0) ");
            sb.append("        +    ");
            sb.append("       nvl((select nvl(sum (cast (nvl (pi30.value, '0') as DECIMAL (10,2))), 0) from project_info pi30, project_info pi26 ");
            sb.append("       where pi30.project_info_type_id = 30 and pi26.project_info_type_id = 26 and pi26.project_id = pi30.project_id   ");
            sb.append("       and pi26.value = 'On'  ");
            sb.append("       and pi26.project_id =  p.project_id ), 0)   ");
            sb.append("     else  nvl((SELECT SUM(total_amount)  ");
            sb.append("             FROM informixoltp:payment pm INNER JOIN informixoltp:payment_detail pmd ON pm.most_recent_detail_id = pmd.payment_detail_id  ");
            sb.append("             WHERE pmd.component_project_id = c.project_id and installment_number = 1 ");
            sb.append("                   AND NOT pmd.payment_status_id IN (65, 68, 69)),0 ) ");
            sb.append("        +    ");
             sb.append("       NVL((SELECT sum(pmd2.total_amount)  ");
            sb.append("          FROM  informixoltp:payment_detail pmd,   ");
            sb.append("                informixoltp:payment pm LEFT OUTER JOIN informixoltp:payment_detail pmd2 on pm.payment_id = pmd2.parent_payment_id,  ");
            sb.append("                informixoltp:payment pm2  ");
            sb.append("           WHERE pmd.component_project_id = c.project_id and pmd2.installment_number = 1  ");
            sb.append("           and pm.most_recent_detail_id = pmd.payment_detail_id   ");
            sb.append("          and pm2.most_recent_detail_id = pmd2.payment_detail_id  ");
            sb.append("         AND NOT pmd2.payment_status_id IN (65, 68, 69)), 0) ");
            sb.append("     end as tot_prize, ");
            sb.append("  ");
            sb.append("     (case when (p.project_status_id in (9, 10)  ");
            sb.append("         and exists (select * from project_phase where project_id = p.project_id and phase_type_id = 1 and phase_status_id in (2,3))) ");
            sb.append("         OR p.project_status_id not in (9, 10) then ");
            sb.append("         (select nvl(sum (cast (nvl (value, '0') as DECIMAL (10,2))), 0) from project_info ");
            sb.append("                 where project_info_type_id = 31 ");
            sb.append("                 and project_id = c.project_id) ");
            sb.append("     else 0 end) as contest_fee, ");
            sb.append("  ");

            // for now use creator as manager
            sb.append("         (select u.handle from project pp, user u ");
            sb.append("         where pp.create_user = u.user_id ");
            sb.append("         and pp.project_id = c.project_id) as manager, ");
            sb.append("  "); 
            sb.append("     (select name  ");
            sb.append("         from permission_type  ");
            sb.append("         where permission_type_id= NVL( (select max( permission_type_id)  ");
            sb.append("                         from user_permission_grant as upg   ");
            sb.append("                         where resource_id=p.project_id ");
            sb.append("                         and upg.user_id = ").append(userId).append("), ");
            sb.append("             case when exists (select u.user_id ");
            sb.append("                     from user as u ");
            sb.append("                     join user_role_xref as uref ");
            sb.append("                     on u.user_id = ").append(userId).append(" ");
            sb.append("                     and u.user_id = uref.login_id ");
            sb.append("                     join security_roles as sr ");
            sb.append("                     on uref.role_id = sr.role_id ");
            sb.append("                     and sr.description in ('Cockpit Administrator','Admin Super Role')) then 3 else 0 end)) as pperm, ");
            sb.append("     (select name  ");
            sb.append("         from permission_type  ");
            sb.append("         where permission_type_id = NVL( (select max( permission_type_id)  ");
            sb.append("                         from user_permission_grant as upg   ");
            sb.append("                         where resource_id=c.project_id   ");
            sb.append("                         and is_studio=0 ");
            sb.append("                         and upg.user_id = ").append(userId).append("),0)) as cperm,   ");
            sb.append(" ( select ptl.name from phase_type_lu ptl where phase_type_id = (select min(phase_type_id) from project_phase ph ");
			sb.append("    where ph.phase_status_id = 2 and ph.project_id=c.project_id)) as current_phase,  ");
            sb.append("  (select case when(count(*)>=1) then 'Scheduled' when(count(*)=0) then 'Draft' end  ");
	        sb.append("     from contest_sale cs where c.project_id = cs.contest_id and upper(psl.name)='ACTIVE' ) as newstatus, ");
            sb.append("  NVL((select unique ttp.name ");
            sb.append("     from project_info as pi ");
            sb.append("     left outer join tt_project as ttp  ");
            sb.append("     on pi.value::DECIMAL(10,2) = ttp.project_id ");
            sb.append("     left outer join tt_client_project cpx ");
            sb.append("     on ttp.project_id = cpx.project_id   ");
            sb.append("     left outer join tt_client as cl ");
            sb.append("     on cpx.client_id = cl.client_id ");
            sb.append("     where pi.project_id = c.project_id and pi.project_info_type_id = 32), '') as cpname, ");	
            sb.append("  (select 'Repost' from linked_project_xref where link_type_id = 5 and source_project_id = c.project_id) as repost, ");
            sb.append("    replace(replace(replace(replace(  ");
            sb.append("      multiset (SELECT  item cpri.value FROM resource cpr INNER JOIN resource_info cpri  ");
            sb.append("             ON cpr.resource_id = cpri.resource_id  ");
            sb.append("              WHERE cpr.project_id = c.project_id AND cpr.resource_role_id = 14 AND cpri.resource_info_type_id = 2)::lvarchar,  ");
            sb.append("          'MULTISET{'''), '''}'),''''),'MULTISET{}') AS copilot, c.project_category_id,  ");
            sb.append("   (case when exists (select project_phase_id from project_phase where c.project_id = project_id and phase_status_id = 3)  ");
            sb.append("              and exists (select project_phase_id from project_phase where c.project_id = project_id and phase_status_id = 1) then 'Active'   ");
            sb.append("         when not exists (select project_phase_id from project_phase where c.project_id = project_id and phase_status_id = 2)   ");
            sb.append("              and not exists (select project_phase_id from project_phase where c.project_id = project_id and phase_status_id = 1) then 'Completed'  ");
            sb.append("         else null end) as phases,  ");
			sb.append("  ");
            sb.append("     NVL((select unique cl.client_id ");
            sb.append("     from project_info as pi ");
            sb.append("     left outer join tt_project as ttp  ");
            sb.append("     on pi.value::DECIMAL(10,2) = ttp.project_id ");
            sb.append("     left outer join tt_client_project cpx ");
            sb.append("     on ttp.project_id = cpx.project_id   ");
            sb.append("     left outer join tt_client as cl ");
            sb.append("     on cpx.client_id = cl.client_id ");
            sb.append("     where pi.project_id = c.project_id and pi.project_info_type_id = 32), 0) as client_id ");
            sb.append("  ");
            sb.append(" from project as c ");
            sb.append(" join project_info as piccat ");
            sb.append("     on c.project_id = piccat.project_id ");
            sb.append("     and piccat.project_info_type_id = 2 ");
            sb.append(" join comp_catalog as ccat ");
            sb.append("     on piccat.value = ccat.component_id ");
            sb.append(" join project_category_lu as pcl ");
            sb.append("     on c.project_category_id = pcl.project_category_id ");
            sb.append(" join project_type_lu as ptl ");
            sb.append("     on pcl.project_type_id = ptl.project_type_id ");
            sb.append(" join project_status_lu as psl ");
            sb.append("     on c.project_status_id = psl.project_status_id ");
            sb.append(" left outer join tc_direct_project as p ");
            sb.append("     on c.tc_direct_project_id = p.project_id ");
            sb.append(" left outer join software_competition_pipeline_info as pipe ");
            sb.append("     on pipe.competition_id = c.project_id ");
            sb.append("     and pipe.component_id = ccat.component_id ");
            sb.append("  ");
            sb.append(" where (exists (select u.user_id ");
            sb.append("     from user as u ");
            sb.append("     join user_role_xref as uref ");
            sb.append("     on u.user_id = ").append(userId).append(" ");
            sb.append("     and u.user_id = uref.login_id ");
            sb.append("     join security_roles as sr ");
            sb.append("     on uref.role_id = sr.role_id ");
            sb.append("     and sr.description in ('Cockpit Administrator','Admin Super Role','TC Staff')) ");
            sb.append(" OR ");
            sb.append(" NVL( (select max( permission_type_id)  ");
            sb.append("     from user_permission_grant as upg   ");
            sb.append("     where resource_id=p.project_id and permission_type_id >= 0 ");
            sb.append("     and upg.user_id = ").append(userId).append("),0) > 0 ");
            sb.append(" OR ");
            sb.append(" NVL( (select max( permission_type_id)  ");
            sb.append("     from user_permission_grant as upg   ");
            sb.append("     where resource_id=c.project_id and permission_type_id >= 5  ");
            sb.append("     and is_studio=0 ");
            sb.append("     and upg.user_id = ").append(userId).append("),0) > 0 ");
            sb.append(" OR ");
            sb.append(" exists(select gm.group_id ");
            sb.append(" 	   from group_member as gm, group_associated_direct_projects as gdap  ");
            sb.append("        where gm.group_id = gdap.group_id and gm.active = 1 and gm.user_id = ").append(userId);
            sb.append(" and gdap.tc_direct_project_id = p.project_id) ");
            sb.append(" OR ");
            sb.append(" exists(SELECT gm.group_id");
            sb.append("      FROM tc_direct_project tdp");
            sb.append("      INNER JOIN corporate_oltp:direct_project_account dpa ON tdp.project_id = dpa.project_id");
            sb.append("      INNER JOIN tt_project ttp ON dpa.billing_account_id = ttp.project_id");
            sb.append("      INNER JOIN tt_client_project ttcp ON ttp.project_id = ttcp.project_id");
            sb.append("      INNER JOIN tt_client ttc ON ttcp.client_id = ttc.client_id");
            sb.append("      INNER JOIN customer_group sg ON sg.client_id = ttc.client_id");
            sb.append("      INNER JOIN group_member gm ON sg.group_id = gm.group_id");
            sb.append("      WHERE sg.auto_grant = 1");
            sb.append("      AND   gm.active = 1");
            sb.append("      AND   sg.archived = 0");
            sb.append("      AND tdp.project_id = p.project_id");
            sb.append("      AND ttc.client_status_id = 1");
            sb.append("      AND   gm.user_id = ").append(userId);
            sb.append("      )");
            sb.append(" OR ");
            sb.append(" exists (select pi32.value  from project_info as pi32  join tt_project as ttp     ");
			sb.append("            on pi32.project_id = c.project_id and pi32.project_info_type_id =32  and ttp.project_id = pi32.value      ");
			sb.append("        and ttp.project_id in ( ");
            sb.append("             SELECT distinct project_id FROM tt_project_worker ttw, tt_user_account u  ");
            sb.append("                 WHERE ttw.user_account_id = u.user_account_id and ");
            sb.append("                                           u.user_name = (select handle from user where user_id = ").append(userId).append(") ");
            sb.append("             union  ");
            sb.append("             SELECT distinct project_id FROM tt_project_manager ttm, tt_user_account u  ");
			 sb.append("                 WHERE ttm.user_account_id = u.user_account_id and ");
            sb.append("                                           u.user_name = (select handle from user where user_id = ").append(userId).append(") ");
            sb.append("             union  ");
            sb.append("             SELECT distinct billing_account_id FROM group_member as gm, group_associated_billing_accounts as gaba ");
            sb.append("                  WHERE gm.group_id = gaba.group_id and gm.active = 1 and gm.user_id = ").append(userId);
            sb.append("        ) )  ");
            sb.append(" ) ");
            sb.append(" AND ");
            // not show inactive or deleted
            sb.append(" (c.project_status_id != 3)  ");
            // dont show spec review project
            sb.append(" and c.project_category_id != 27 ");
            sb.append(" AND ");
            sb.append(" ( ");
            sb.append(" ((select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=c.project_id) BETWEEN to_date('")
                    .append(formatter.format(startDate))
                    .append("','%Y-%m-%d %H:%M:%S') AND to_date('")
                    .append(formatter.format(endDate))
                    .append("','%Y-%m-%d %H:%M:%S')) ");

            sb.append(" ) ");


            sb.append(" order by start_time ");
            sb.append(" )) as s;");
			
			//System.out.println("-----------------query--------\n"+sb.toString());

            Object[][] rows = Helper.doQuery(conn, sb.toString(), new Object[] {},
                    QUERY_SIMPLE_PIPELINE_DATA_COLUMN_TYPES);

            SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            List<SimplePipelineData> result = new ArrayList<SimplePipelineData>();

            for (int i = 0; i < rows.length; i++) {

                SimplePipelineData c = new SimplePipelineData();
                Object[] os = rows[i];

                if (os[0] != null)
                    c.setContestId((Long) os[0]);
                if (os[1] != null)
                    c.setCname(os[1].toString());
                if (os[2] != null)
                    c.setCversion(os[2].toString());
                if (os[3] != null)
                    c.setProjectId((Long) os[3]);
                if (os[4] != null)
                    c.setPname(os[4].toString());
                if (os[5] != null)
                    c.setContestType(os[5].toString());
                if (os[6] != null)
                    c.setContestCategory(os[6].toString());

                if (os[8] != null)
                    c.setStartDate(myFmt.parse(os[8].toString()));
                if (os[9] != null)
                    c.setEndDate(myFmt.parse(os[9].toString()));
                if (os[10] != null)
                    c.setDurationStartTime(myFmt.parse(os[10].toString()));
                if (os[11] != null)
                    c.setDurationEndTime(myFmt.parse(os[11].toString()));
                if (os[12] != null)
                    c.setCreateTime(myFmt.parse(os[12].toString()));
                if (os[13] != null)
                    c.setModifyTime(myFmt.parse(os[13].toString()));
                if (os[14] != null)
                    c.setClientName(os[14].toString());
                if (os[15] != null)
                    c.setTotalPrize((Double)os[15]);
                if (os[16] != null)
                    c.setContestFee((Double)os[16]);

                if (os[17] != null)
                    c.setManager(os[17].toString());

                if (os[18] != null)
                    c.setPperm(os[18].toString());
                if (os[19] != null)
                    c.setCperm(os[19].toString());
                
                
				if (rows[i][20] != null && ((String)rows[i][7]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName())) {
				 // any contest that has an open phase in Online Review
					c.setSname("Active");
				} else if (rows[i][21] != null && ((String)rows[i][7]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName())) {
                    // all phases are done, then it is completed
                    if (rows[i][26] != null && ((String)rows[i][26]).trim().equalsIgnoreCase("Completed"))
                    {
                        c.setSname("Completed");
                    }
                    else if (rows[i][26] != null && ((String)rows[i][26]).trim().equalsIgnoreCase("Active"))
                    {
                        c.setSname("Active");
                    }
                    else 
                    {
                        //scheduled or draft
                        c.setSname(((String)rows[i][21]).trim());
                    }
				} else if(!((String)rows[i][7]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName())) {

                    if (((String)rows[i][7]).equalsIgnoreCase(ProjectStatus.CANCELLED_CLIENT_REQUEST.getName()) 
                          || ((String)rows[i][7]).equalsIgnoreCase(ProjectStatus.CANCELLED_REQUIREMENTS_INFEASIBLE.getName()))
                    {
                        c.setSname("Cancelled");
                    }
                    else if (((String)rows[i][7]).equalsIgnoreCase(ProjectStatus.DRAFT.getName()))
                    {
                        c.setSname("Draft");
                    }
                    else if (((String)rows[i][7]).equalsIgnoreCase(ProjectStatus.CANCELLED_FAILED_REVIEW.getName())
                            || ((String)rows[i][7]).equalsIgnoreCase(ProjectStatus.CANCELLED_FAILED_SCREENING.getName())
                            || ((String)rows[i][7]).equalsIgnoreCase(ProjectStatus.CANCELLED_ZERO_SUBMISSIONS.getName())
                            || ((String)rows[i][7]).equalsIgnoreCase(ProjectStatus.CANCELLED_WINNER_UNRESPONSIVE.getName()))
                    {
                        c.setSname("Failed");
                    }
					else
                    {
                        c.setSname("Completed");
                    }
				}

                if (os[22] != null)
                    c.setCpname(os[22].toString());

                if (os[23] != null)
                    c.setWasReposted(Boolean.TRUE);

                if (os[24] != null) {
                    c.setCopilots(new String[] {os[24].toString()});
                }

                if (os[25] != null)
                {
                    c.setContestTypeId((Long)os[25]);
                }
				
				if (os[27] != null)
                {
                    c.setClientId((Long)os[27]);
                }
                
                result.add(c);
                
                
                
            }

            closeConnection(conn);
            return result;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fails to retrieving simple pipeline data.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
        catch (ParseException e) {
			getLogger().log(Level.ERROR, new LogMessage(null, null, "Fails to retrieving simple pipeline data.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw new PersistenceException("Fails to retrieving simple pipeline data.", e);
		}
    }

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
     * @since 1.2
     */
    public List<SoftwareCapacityData> getCapacity(int contestType) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getCapacity(" + contestType + ") method."));

        Connection conn = null;

        StringBuffer queryBuffer = new StringBuffer();

        queryBuffer.append(" select");
        queryBuffer.append(" (select min(date(nvl(actual_start_time, scheduled_start_time))) ");
        queryBuffer.append(" from project_phase ph where ph.project_id=p.project_id and ph.phase_type_id = 1) as start_date,");
        queryBuffer.append(" p.project_id");
        queryBuffer.append(" from project p, contest_sale c");
        queryBuffer.append(" where p.project_category_id = ").append(contestType);
        queryBuffer.append(" and p.project_status_id = ").append(ACTIVE_PROJECT_STATUS_ID);
        queryBuffer.append(" and p.project_id = c.contest_id");
        queryBuffer.append(" and (select min(date(nvl(actual_start_time, scheduled_start_time))) ");
        queryBuffer.append(" from project_phase ph where ph.project_id=p.project_id) > date(current)");
        queryBuffer.append(" AND NOT EXISTS (SELECT 'has_eligibility_constraints' FROM contest_eligibility ce ");
        queryBuffer.append("           WHERE ce.is_studio = 0 AND ce.contest_id = p.project_id) ");
        queryBuffer.append(" order by 1");

        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Object[][] rows = Helper.doQuery(conn,
                    queryBuffer.toString(), new Object[] {},
                    new DataType[] { Helper.DATE_TYPE, Helper.LONG_TYPE } );

            List<SoftwareCapacityData> capacityList = new ArrayList<SoftwareCapacityData>();
            getLogger().log(Level.INFO, new LogMessage(null,null,"Found "+rows.length + " records"));

            SoftwareCapacityData cap = null;
            String previous = "";
            for(int i=0;i<rows.length;i++)
            {
                Object[] os = rows[i];

                // new date
                if (!previous.equals(os[0].toString()))
                {

                    previous = os[0].toString();
                    cap = new SoftwareCapacityData();
                    cap.setDate((Date) os[0]);
                    cap.setNumScheduledContests(1);
                    cap.getContests().add((((Long)(os[1])).intValue()));
                    capacityList.add(cap);
                    
                }
                else
                {
                    cap.setNumScheduledContests(cap.getNumScheduledContests() + 1);
                    cap.getContests().add((((Long)(os[1])).intValue()));
                }
                
            }

            closeConnection(conn);
            getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getCapacity method."));
            getLogger().log(Level.INFO, new LogMessage(null,null,"Got "+capacityList.size() + " capacities"));
            return capacityList;
        } catch (PersistenceException e) {
            getLogger().log(
                    Level.ERROR,
                    new LogMessage(null, null,
                            "Fails to retrieving capacity information ", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

     /**
     * Get all design components.
     *
     * @param userId
     *            The user id
     * @throws PersistenceException
     *             if any other error occurs
     * @since 1.2.2
     */
    public List<DesignComponents> getDesignComponents(long userId) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getDesignComponents() method."));

        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Object[][] rows = Helper.doQuery(conn,
                    QUERY_ALL_DESIGN_COMPONENTS, new Object[] {userId},
                    QUERY_ALL_DESIGN_COMPONENTS_COLUMN_TYPES );

            List<DesignComponents> designList = new ArrayList<DesignComponents>();
            getLogger().log(Level.INFO, new LogMessage(null, null, "Found "+rows.length + " records"));

            DesignComponents designComponents = null;

            for(int i=0;i<rows.length;i++)
            {   
		        designComponents = new DesignComponents();
                {
                    designComponents.setProjectId((Long)rows[i][0]);
                    designComponents.setText(rows[i][1].toString());
                    designComponents.setPperm(rows[i][2] == null ? null : rows[i][2].toString());
                }

                designList.add(designComponents);
    
            }

            closeConnection(conn);
            getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getDesignComponents method."));
            return designList;
        } catch (PersistenceException e) {
            getLogger().log(
                    Level.ERROR,
                    new LogMessage(null, null,
                            "Fails to retrieving design components information ", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

     /**
     * Get corresponding development contest's id for the design contest.
     *
     * @param contestId
     *            The design contestId id
     * @throws PersistenceException
     *             if any other error occurs
     * @since 1.2.2
     */
    public long getDevelopmentContestId(long contestId) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getDevelopmentContestId method."));

        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Object[][] rows = Helper.doQuery(conn,
                FIND_CORRESPONDING_DEVELOPMENT_CONTEST, 
                new Object[] {contestId, contestId, contestId, contestId},
                new DataType[] {Helper.LONG_TYPE});

            getLogger().log(Level.INFO, new LogMessage(null, null, "Found "+rows.length + " records"));
            long rst = 0;
            if (rows.length != 0) {
                rst = ((Long)rows[0][0]).longValue();
            }
            getLogger().log(Level.INFO, new LogMessage(null, null, "The id is: " + rst));

            closeConnection(conn);
            getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getDesignComponents method."));
            return rst;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null,
                "Fails to retrieving development contest's id ", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }


    /**
     * Get corresponding design contest's id for the dev contest.
     *
     * @param contestId
     *            The dev contestId id
     * @throws PersistenceException
     *             if any other error occurs
     */
    public long getDesignContestId(long contestId) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getDesignContestId() method."));

        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Object[][] rows = Helper.doQuery(conn,
                FIND_CORRESPONDING_DESIGN_CONTEST, 
                new Object[] {contestId, contestId, contestId, contestId},
                new DataType[] {Helper.LONG_TYPE});

            getLogger().log(Level.INFO, new LogMessage(null, null, "Found "+rows.length + " records"));
            long rst = 0;
            if (rows.length != 0) {
                rst = ((Long)rows[0][0]).longValue();
            }
            getLogger().log(Level.INFO, new LogMessage(null, null, "The id is: " + rst));

            closeConnection(conn);
            getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getDesignContestId method."));
            return rst;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null,
                "Fails to retrieving development contest's id ", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Get corresponding contest's id for the given contest.
     *
     * @param contestId The contestId id
     * @throws PersistenceException if any other error occurs
     * @since 1.5.1
     */
    public long getContestId(long contestId) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getContestId(long contestId) method."));

        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Object[][] rows = Helper.doQuery(conn,
                    FIND_CORRESPONDING_CONTEST,
                    new Object[]{contestId, contestId, contestId, contestId},
                    new DataType[]{Helper.LONG_TYPE});

            getLogger().log(Level.INFO, new LogMessage(null, null, "Found " + rows.length + " records"));
            long rst = 0;
            if (rows.length != 0) {
                rst = ((Long) rows[0][0]).longValue();
            }
            getLogger().log(Level.INFO, new LogMessage(null, null, "The id is: " + rst));

            closeConnection(conn);
            getLogger().log(Level.INFO, new LogMessage(null, null, "Exit getDesignContestId method."));
            return rst;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null,
                    "Fails to retrieving contest's id ", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }
    
    

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
    public boolean checkContestPermission(long contestId, boolean readonly, long userId)  throws PersistenceException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            conn = openConnection();
            
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select 'has permssion' from user_permission_grant ");
            queryBuffer.append(" where (resource_id = ").append(contestId).append(" and is_studio = 0 and permission_type_id >= ");
            queryBuffer.append(readonly ? CONTEST_READ_PERMISSION_ID : CONTEST_WRITE_PERMISSION_ID);  
            queryBuffer.append(" and user_id = ").append(userId).append(")"); 
            queryBuffer.append(" or ");
            queryBuffer.append(" (resource_id = (select tc_direct_project_id from project where project_id = ").append(contestId).append(") and permission_type_id >= ");
            queryBuffer.append(readonly ? PROJECT_READ_PERMISSION_ID : PROJECT_WRITE_PERMISSION_ID);  
            queryBuffer.append(" and user_id = ").append(userId).append(")"); 

            preparedStatement = conn.prepareStatement(queryBuffer.toString());
         
            // execute the query and build the result into a list
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return true;
            }

            return false;
        
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Error occurs while executing query ");
        } catch (PersistenceException e) {
           
            throw new PersistenceException("There are errors while retrieving the information.", e);
        }
        finally {
            Helper.closeResultSet(resultSet);
            Helper.closeStatement(preparedStatement);
            if (conn != null) {
                closeConnectionOnError(conn);
            }
        }

    }


    /**
     * check contest permission, check if a user has permission (read or write) on a project
     *
     * @param tcprojectId the tc direct project id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean checkProjectPermission(long tcprojectId, boolean readonly, long userId) throws PersistenceException
    {
        
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            conn = openConnection();
            
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select 'has permssion' from user_permission_grant ");
            queryBuffer.append(" where  resource_id =  ").append(tcprojectId).append(" and permission_type_id >= ");
            queryBuffer.append(readonly ? PROJECT_READ_PERMISSION_ID : PROJECT_WRITE_PERMISSION_ID);  
            queryBuffer.append(" and user_id = ").append(userId).append(" "); 
        
            preparedStatement = conn.prepareStatement(queryBuffer.toString());
         
            // execute the query and build the result into a list
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return true;
            }

            return false;
        
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Error occurs while executing query " + e, e);
        } catch (PersistenceException e) {
            
            throw new PersistenceException("There are errors while retrieving the information.", e);
        }
        finally {
            Helper.closeResultSet(resultSet);
            Helper.closeStatement(preparedStatement);
            if (conn != null) {
                closeConnectionOnError(conn);
            }
        }

    }



    /**
     * <p>
     * get project ids by tc direct id
     * </p>
     *
     * @param tcprojectId tc direct project id
     *
     * @return list of project ids
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public List<Long> getProjectIdByTcDirectProject(long tcprojectId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the contest sale
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_PROJECT_ID_BY_TC_DIRECT, new Object[] {tcprojectId},
            		QUERY_PROJECT_ID_BY_TC_DIRECT_COLUMN_TYPES);

            List<Long> ret = new ArrayList<Long>();

            for (int i = 0; i < rows.length; i++) {

                ret.add((Long) rows[i][0]);
            }
            return ret;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve project ids by tc direct" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
         finally {

            closeConnection(conn);
        }
    }


    /**
     * <p>
     * get tc direct project id by project id
     * </p>
     *
     * @param projectId project id
     *
     * @return tc direct project id
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public long getTcDirectProject(long projectId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the contest sale
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_TC_DIRECT_PROJECT_BY_PROJECT_ID, new Object[] {projectId},
            		QUERY_TC_DIRECT_PROJECT_BY_PROJECT_ID_COLUMN_TYPES);

            if (rows.length > 0 && rows[0][0] != null)
            {
                return ((Long) rows[0][0]).longValue();
            }
            
            return 0;

        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve project ids by tc direct" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
         finally {

            closeConnection(conn);
        }
    }
    
    

     /**
     * <p>
     * get forum id by project id
     * </p>
     *
     * @param projectId project id
     *
     * @return forum id
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public long getForumId(long projectId) throws PersistenceException 
    {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the contest sale
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_FORUM_ID, new Object[] {projectId},
            		QUERY_FORUM_ID_COLUMN_TYPES);


            if (rows.length > 0 && rows[0][0] != null)
            {
                return ((Long) rows[0][0]).longValue();
            }
            
            return 0;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve project ids by tc direct" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
         finally {

            closeConnection(conn);
        }
    }


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
    public boolean hasContestPermission(long contestId, long userId)  throws PersistenceException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            conn = openConnection();
            
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select 'has permssion' from user_permission_grant ");
            queryBuffer.append(" where (resource_id = ").append(contestId).append(" and is_studio = 0 and permission_type_id >= ");
            queryBuffer.append(CONTEST_READ_PERMISSION_ID);  
            queryBuffer.append(" and user_id = ").append(userId).append(")");  

            preparedStatement = conn.prepareStatement(queryBuffer.toString());
         
            // execute the query and build the result into a list
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return true;
            }

            return false;
        
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Error occurs while executing query ");
        } catch (PersistenceException e) {
           
            throw new PersistenceException("There are errors while retrieving the information.", e);
        }
        finally {
            Helper.closeResultSet(resultSet);
            Helper.closeStatement(preparedStatement);
            if (conn != null) {
                closeConnectionOnError(conn);
            }
        }

    }


    /**
     * <p>
     * check if it is dev only 
     * </p>
     *
     * @param projectId  project id
     *
     * @return boolean
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public boolean isDevOnly(long projectId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the contest sale
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_IS_DEV_ONLY_SQL, new Object[] {projectId},
            		QUERY_IS_DEV_ONLY_SQL_COLUMN_TYPES);

            if (rows.length > 0)
            {
                return false;
            }

            return true;

        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to check isDevOnly" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
         finally {

            closeConnection(conn);
        }
    }

     /**
     * Gets an array of all billing project configuration types in the persistence. The billing
     * project configuration types are stored in 'client_billing_config_type_lu' table.
     * 
     * @return An array of all BillingProjectConfigTypes in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public BillingProjectConfigType[] getAllBillingProjectConfigTypes() throws PersistenceException {
        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getAllBillingProjectConfigTypes method."));
        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            BillingProjectConfigType[] billingProjectConfigTypes = getAllBillingProjectConfigTypes(conn);
            closeConnection(conn);
            return billingProjectConfigTypes;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getAllBillingProjectConfigTypes.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all billing project configuration types in the persistence. The project
     * property types are stored in 'client_billing_config_type_lu' table.
     * 
     * @param conn the database connection
     * @return An array of all BillingProjectConfigTypes in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private BillingProjectConfigType[] getAllBillingProjectConfigTypes(Connection conn) throws PersistenceException {

        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_BILLING_PROJECT_CONFIG_TYPES_SQL, new Object[] {},
                QUERY_ALL_BILLING_PROJECT_CONFIG_TYPES_COLUMN_TYPES);

        BillingProjectConfigType[] billingProjectConfigTypes = new BillingProjectConfigType[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of BillingProjectConfigType class
            billingProjectConfigTypes[i] = new BillingProjectConfigType(((Long) row[0]).longValue(), (String) row[1],
                    (String) row[2]);
        }

        return billingProjectConfigTypes;
    }

    /**
     * Gets an array of all the billing project configurations in the persistence. The billing
     * project configurations are stored in 'client_billing_config' table.
     * 
     * 
     * @param billingProjectId the id of the billing project.
     * @return an array of all BillingProjectConfiguration.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public BillingProjectConfiguration[] getAllBillingProjectConfigs(long billingProjectId) throws PersistenceException {
        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getAllBillingProjectConfigs method."));
        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            BillingProjectConfiguration[] billingProjectConfigs = getAllBillingProjectConfigs(conn, billingProjectId);
            closeConnection(conn);
            return billingProjectConfigs;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getAllBillingProjectConfigs.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }
    
    /**
     * Gets active contests for the given user, only active contests will be returned.
     * 
     * @param userId the id of the user.
     * @return the data stored in a list of SimpleProjectContestData instances.
     * @throws PersistenceException if any error occurs while retrieving data.
     * @since BUGR-3706
     */
    public List<SimpleProjectContestData> getActiveContestsForUser(long userId) throws PersistenceException {
        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getActiveContestsForUser method."));
        
        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            String qstr = "select c.project_id as contest_id, " +
                    " (select pinfo.value from project_info as pinfo where pinfo.project_id = c.project_id and pinfo.project_info_type_id = 6) as contest_name, "
                    + " tc_direct_project_id as project_id, "
                    + "  tcd.name as project_name, "
                    + " ( select pi.value from project_info pi where c.project_id = pi.project_id and pi.project_info_type_id = 4 ) as forum_id, "
                    + " pcl.name as contest_type, pcl.project_type_id  "
                    + " from project c, tc_direct_project tcd, project_category_lu pcl  "
                    + " where c.tc_direct_project_id = tcd.project_id and c.project_status_id = 1 "
                    + " and pcl.project_category_id = c.project_category_id "
                    + " and (c.create_user = " + userId + " OR exists "
                    + "     (select user_id from user_permission_grant upg where upg.user_id = " + userId
                    + "      and ((upg.resource_id = c.project_id and is_studio = 0) "
                    + "        OR upg.resource_id = tcd.project_id))) ";

            Object[][] rows = Helper.doQuery(conn, qstr, new Object[] {},
                    this.QUERY_SIMPLE_PROJECT_INFO_BY_USER_COLUMN_TYPES);

            List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();

            for (int i = 0; i < rows.length; i++) {

                SimpleProjectContestData c = new SimpleProjectContestData();
               
                Object[] os = rows[i];

                if (os[0] != null) {
                    // set the contest id
                    c.setContestId((Long) os[0]);
                }
                if (os[1] != null) {
                    // set the contest name
                    c.setCname(os[1].toString());
                }
                if (os[2] != null) {
                    // set the tc direct project id
                    c.setProjectId((Long) os[2]);
                }
                if (os[3] != null) {
                    // set the tc direct project name
                    c.setPname(os[3].toString());
                }
                
                if (os[4] != null) {
                    // set the forum id
                    c.setForumId( ((Long) os[4]).intValue());
                }

                if (os[5] != null)
                    // set the contest type
                    c.setType(os[5].toString());

                 if (os[6] != null) {
                    // set the contest type
                    c.setIsStudio(((Long) os[6]) == ProjectType.STUDIO.getId());
                 }


                result.add(c);
            }

            closeConnection(conn);
            
            return result;
            
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fails to retrieving active projects info for user", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        } finally {
            getLogger().log(Level.INFO, new LogMessage(null, null, "Exit getActiveContestsForUser method."));
        }
    }
	
	/**
     * Gets active contests for the given user, only active contests will be returned.
     * 
     * @param userId the id of the user.
     * @return the data stored in a list of SimpleProjectContestData instances.
     * @throws PersistenceException if any error occurs while retrieving data.
     * @since BUGR-3706
     */
    public List<SimpleProjectContestData> getActiveDraftContestsForUser(long userId) throws PersistenceException {
        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getActiveDraftContestsForUser method."));
        
        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            String qstr = "select c.project_id as contest_id, " +
                    " (select pinfo.value from project_info as pinfo where pinfo.project_id = c.project_id and pinfo.project_info_type_id = 6) as contest_name, "
                    + " tc_direct_project_id as project_id, "
                    + "  tcd.name as project_name, "
                    + " ( select pi.value from project_info pi where c.project_id = pi.project_id and pi.project_info_type_id = 4 ) as forum_id, "
                    + " pcl.name as contest_type, pcl.project_type_id,  "
                    + " ( select pi.value from project_info pi where c.project_id = pi.project_id and pi.project_info_type_id = 78 ) as forum_type "
                    + " from project c, tc_direct_project tcd, project_category_lu pcl  "
                    + " where c.tc_direct_project_id = tcd.project_id and c.project_status_id  in (1,2) "
                    + " and pcl.project_category_id = c.project_category_id "
                    + " and (c.create_user = " + userId + " OR exists "
                    + "     (select user_id from user_permission_grant upg where upg.user_id = " + userId
                    + "      and ((upg.resource_id = c.project_id and is_studio = 0) "
                    + "        OR upg.resource_id = tcd.project_id))) ";

            Object[][] rows = Helper.doQuery(conn, qstr, new Object[] {},
                    this.QUERY_SIMPLE_PROJECT_INFO_BY_USER_COLUMN_TYPES);

            List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();

            for (int i = 0; i < rows.length; i++) {

                SimpleProjectContestData c = new SimpleProjectContestData();
               
                Object[] os = rows[i];

                if (os[0] != null) {
                    // set the contest id
                    c.setContestId((Long) os[0]);
                }
                if (os[1] != null) {
                    // set the contest name
                    c.setCname(os[1].toString());
                }
                if (os[2] != null) {
                    // set the tc direct project id
                    c.setProjectId((Long) os[2]);
                }
                if (os[3] != null) {
                    // set the tc direct project name
                    c.setPname(os[3].toString());
                }
                
                if (os[4] != null) {
                    // set the forum id
                    c.setForumId( ((Long) os[4]).intValue());
                }

                if (os[5] != null)
                    // set the contest type
                    c.setType(os[5].toString());

                 if (os[6] != null) {
                    // set the contest type
                    c.setIsStudio(((Long) os[6]) == ProjectType.STUDIO.getId());
                 }

                 if (os[7] != null) {
                     c.setForumType(os[7].toString());
                 }


                result.add(c);
            }

            closeConnection(conn);
            
            return result;
            
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fails to retrieving active/draft projects info for user", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        } finally {
            getLogger().log(Level.INFO, new LogMessage(null, null, "Exit getActiveDraftContestsForUser method."));
        }
    }


    /**
     * Gets an array of all the billing project configurations in the persistence. The billing
     * project configurations are stored in 'client_billing_config' table.
     * 
     * @param conn the database connection.
     * @param billingProjectId the ID of the billing project.
     * @return an array of retrieved BillingProjectConfigurations
     * @throws PersistenceException if any error occurred while accessing database.
     */
    private BillingProjectConfiguration[] getAllBillingProjectConfigs(Connection conn, long billingProjectId)
            throws PersistenceException {
        // find all billing project configs in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_BILLING_PROJECT_CONFIGS_SQL,
                new Object[] { billingProjectId }, QUERY_ALL_BILLING_PROJECT_CONFIGS_SQL_COLUMN_TYPES);

        BillingProjectConfigType[] allTypes = getAllBillingProjectConfigTypes();

        BillingProjectConfiguration[] result = new BillingProjectConfiguration[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            long configTypeId = ((Long) row[0]).longValue();

            // create a new instance of ProjectPropertyType class
            result[i] = new BillingProjectConfiguration(getBillingProjectConfigType(allTypes, configTypeId),
                    (String) row[1]);
        }

        return result;
    }

    /**
     * Utility method to retrieve a BillingProjectConfigType from all the config types with a ID.
     * 
     * @param allTypes the array which contains all the config types.
     * @param typeId the id of the billing project config type.
     * @return the retrieved BillingProjectConfigType instance.
     */
    private BillingProjectConfigType getBillingProjectConfigType(BillingProjectConfigType[] allTypes, long typeId) {
        for (BillingProjectConfigType type : allTypes) {
            if (type.getId() == typeId) {
                return type;
            }
        }

        return null;
    }

    /**
     * <p>
     * Creates or updates project copilot types for the specified project.
     * </p>
     * @param projectId
     *            the id of the project.
     * @param projectCopilotTypes
     *            a list of <code>ProjectCopilotType</code> to create/update.
     * @param conn
     *            the database connection to use.
     * @param operator
     *            the operator of this operation.
     * @param update
     *            true if the operation is to update, or false if it is to create.
     * @throws PersistenceException
     *             if any error occurs from underlying persistence.
     */
    private void createOrUpdateProjectCopilotTypes(long projectId, List<ProjectCopilotType> projectCopilotTypes,
            Connection conn, String operator, boolean update) throws PersistenceException {
        if (update) {
            getLogger().log(Level.INFO,
                    "delete the project copilot types from database with the specified project id: " + projectId);
            Helper.doDMLQuery(conn, DELETE_PROJECT_COPILOT_TYPES_SQL, new Object[] { projectId });
        }

        if (projectCopilotTypes == null) {
            return;
        }

        for (ProjectCopilotType copilotType : projectCopilotTypes) {
            getLogger()
                    .log(Level.INFO,
                            "insert record into project copilot type with id: [" + projectId + ", "
                                    + copilotType.getId() + "]");

            Timestamp createDate = new Timestamp(System.currentTimeMillis());

            // insert the prize into database
            Object[] queryArgs = new Object[] { projectId, copilotType.getId(), operator, createDate, operator,
                    createDate };
            Helper.doDMLQuery(conn, CREATE_PROJECT_COPILOT_TYPE_SQL, queryArgs);
        }
    }

    /**
     * <p>
     * Creates or updates copilot contest extra infos for the specified project.
     * </p>
     * @param projectId
     *            the id of the project.
     * @param copilotContestExtraInfos
     *            a list of <code>CopilotContestExtraInfo</code> to create/update.
     * @param conn
     *            the database connection to use.
     * @param operator
     *            the operator of this operation.
     * @param update
     *            true if the operation is to update, or false if it is to create.
     * @throws PersistenceException
     *             if any error occurs from underlying persistence.
     */
    private void createOrUpdateCopilotContestExtraInfos(long projectId,
            List<CopilotContestExtraInfo> copilotContestExtraInfos, Connection conn, String operator, boolean update)
            throws PersistenceException {
        if (update) {
            getLogger().log(Level.INFO,
                    "delete the copilot extra infos from database with the specified project id: " + projectId);
            Helper.doDMLQuery(conn, DELETE_COPILOT_CONTEST_EXTRA_INFOS_SQL, new Object[] { projectId });
        }

        if (copilotContestExtraInfos == null) {
            return;
        }

        for (CopilotContestExtraInfo extraInfo : copilotContestExtraInfos) {
            getLogger().log(
                    Level.INFO,
                    "insert record into copilot contest extra info with id: [" + projectId + ", "
                            + extraInfo.getType().getId() + "]");

            Timestamp createDate = new Timestamp(System.currentTimeMillis());

            // insert the prize into database
            Object[] queryArgs = new Object[] { projectId, extraInfo.getType().getId(), extraInfo.getValue(), operator,
                    createDate, operator, createDate };
            Helper.doDMLQuery(conn, CREATE_COPILOT_CONTEST_EXTRA_INFO_SQL, queryArgs);
        }
    }

    /**
     * <p>
     * Gets the project copilot types associated with the given project.
     * </p>
     * @param projectId
     *            the id of the project.
     * @return a list of <code>ProjectCopilotType</code>s representing the project copilot types associated with the
     *         given project.
     * @throws PersistenceException
     *             if any error occurs from underlying persistence.
     */
    private List<ProjectCopilotType> getProjectCopilotTypes(long projectId) throws PersistenceException {
        Helper.assertLongPositive(projectId, "projectId");

        Connection conn = null;

        getLogger().log(Level.INFO, "get project copilot types with the project id: " + projectId);
        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            }

            // find project copilot types in the table
            Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_COPILOT_TYPES_SQL + projectId, new Object[] {},
                    QUERY_PROJECT_COPILOT_TYPES_COLUMN_TYPES);

            List<ProjectCopilotType> copilotTypes = new ArrayList<ProjectCopilotType>();

            // enumerate each row
            for (int i = 0; i < rows.length; ++i) {
                Object[] row = rows[i];

                ProjectCopilotType copilotType = new ProjectCopilotType((Long) row[0], (String) row[1], (String) row[2]);
                copilotTypes.add(copilotType);
            }

            closeConnection(conn);
            return copilotTypes;
        } catch (PersistenceException e) {
            getLogger().log(
                    Level.ERROR,
                    new LogMessage(null, null, "Fails to retrieving project copilot types with project id: "
                            + projectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * <p>
     * Gets the copilot contest extra infos associated with the given project.
     * </p>
     * @param projectId
     *            the id of the project.
     * @return a list of <code>CopilotContestExtraInfo</code>s representing the copilot contest extra infos associated
     *         with the given project.
     * @throws PersistenceException
     *             if any error occurs from underlying persistence.
     */
    private List<CopilotContestExtraInfo> getCopilotContestExtraInfos(long projectId) throws PersistenceException {
        Helper.assertLongPositive(projectId, "projectId");

        Connection conn = null;

        getLogger().log(Level.INFO, "get copilot contest extra infos with the project id: " + projectId);
        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            }

            // find copilot contest extra info in the table
            Object[][] rows = Helper.doQuery(conn, QUERY_COPILOT_CONTEST_EXTRA_INFOS_SQL + projectId, new Object[] {},
                    QUERY_COPILOT_CONTEST_EXTRA_INFOS_COLUMN_TYPES);

            List<CopilotContestExtraInfo> extraInfos = new ArrayList<CopilotContestExtraInfo>();

            // enumerate each row
            for (int i = 0; i < rows.length; ++i) {
                Object[] row = rows[i];

                CopilotContestExtraInfoType type = new CopilotContestExtraInfoType((Long) row[0], (String) row[1],
                        (String) row[2]);
                CopilotContestExtraInfo extraInfo = new CopilotContestExtraInfo();
                extraInfo.setType(type);
                extraInfo.setValue((String) row[3]);
                extraInfos.add(extraInfo);
            }

            closeConnection(conn);
            return extraInfos;
        } catch (PersistenceException e) {
            getLogger().log(
                    Level.ERROR,
                    new LogMessage(null, null, "Fails to retrieving copilot contest extra infos with project id: "
                            + projectId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }
}
