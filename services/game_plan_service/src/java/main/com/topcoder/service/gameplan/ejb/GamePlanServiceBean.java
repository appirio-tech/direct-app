/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.gameplan.ejb;

import com.topcoder.security.TCPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.gameplan.GamePlanPersistenceException;
import com.topcoder.service.gameplan.GamePlanServiceConfigurationException;
import com.topcoder.service.util.gameplan.SoftwareProjectData;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanData;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>This class is an EJB that implements <code>GamePlanService</code> business interface. This bean uses <b>Logging
 * Wrapper</b> component to log exceptions and debug information. Also it uses injected JPA entity managers to access
 * software and studio projects data in the persistence.</p>
 *
 * <p>
 * Following is the sample usage for this component.
 *
 * <pre>
 * // Get game plan service
 * Context context = new InitialContext();
 * GamePlanService gamePlanService =
 *     (GamePlanServiceRemote) context.lookup("GamePlanServiceBean/remote");
 * // Create TCSubject instance for admin user
 * Set&lt;RolePrincipal&gt; principals = new HashSet&lt;RolePrincipal&gt;();
 * RolePrincipal adminRole = new RolePrincipal("Cockpit Administrator", 1);
 * principals.add(adminRole);
 * TCSubject tcSubject1 = new TCSubject(principals, 12345);
 * // Retrieve game plan data for all existing TC Direct projects
 * List&lt;TCDirectProjectGamePlanData&gt; gamePlanDataList =
 *     gamePlanService.retrieveGamePlanData(tcSubject1);
 * for (TCDirectProjectGamePlanData gamePlanData : gamePlanDataList) {
 * // Get data for software projects
 * List&lt;SoftwareProjectData&gt; softwareProjects = gamePlanData.getSoftwareProjects();
 * // Get data for studio projects
 * List&lt;StudioProjectData&gt; studioProjects = gamePlanData.getStudioProjects();
 * // Process projects data
 * // ...
 * }
 * // Create TCSubject instance for non-admin user
 * principals = new HashSet&lt;RolePrincipal&gt;();
 * TCSubject tcSubject2 = new TCSubject(principals, 23456);
 * // Retrieve game plan data for all TC Direct projects associated with user with ID=23456
 * gamePlanDataList = gamePlanService.retrieveGamePlanData(tcSubject2);
 *
 * // Retrieve the game plan data for the specific TC Direct Project associated with the current user.
 * TCDirectProjectGamePlanData gamePlanData = gamePlanService.retrieveGamePlanData(tcSubject2, 1L);
 * </pre>
 * </p>
 *
 * <p><b>Thread Safety</b>: This class is mutable and not thread safe. But it is always used in thread safe manner in
 * EJB container because its state doesn't change after initialization. Instances of EntityManager used by this class
 * are thread safe. This bean assumes that transactions are managed by the container.</p>
 *
 * <p>
 * Version 1.0.1 (TC Cockpit Contest Duration Calculation Updates Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #RETRIEVE_SOFTWARE_PROJECT_DATA} statement to exclude duration of <code>Approval</code>,
 *     <code>Specification Submission/Review</code> phases from contest duration.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.0.2 (TCCC-3658) Change notes:
 *   <ol>
 *     <li>Removed dependencies to studio components</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.0.3 ( Release Assembly - TopCoder BugHunt Competition Integration 2)
 * - Add Bug Hunt For as link type in game plan
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
 * <ol>
 *     <li>
 *       Uses the actual_end_time of last opened phase for cencelled and completed contest in RETRIEVE_SOFTWARE_PROJECT_DATA
 *     </li>
 *     <li>
 *       Retrieves the dependency type id in RETRIEVE_DEPENDENCY_PROJECT_IDS_SQL
 *     </li>
 *     <li>
 *        Update method {@link #updateDependencyProjectIds(java.util.List)} to set dependency type ids.
 *     </li>
 *     <li>
 *        Update method {@link #retrieveSoftwareProjectData} to set end date for cancelled project without opening any phase.
 *     </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Security Groups Release 7)
 * <ul>
 *     <li>Updates {@link #PERMISSION_CHECKING} to allow user with group permission on the project and the billing account of the project
 *     accesses the game plan data of the project
 *     </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.3 (TopCoder Security Groups Release 8 - Automatically Grant Permissions) change notes:
 * <ol>
 *     <li>Updated {@link #PERMISSION_CHECKING} to allow user with automatically grant permissions.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.4 (TopCoder Direct - Add Group Permission Logic and project full permission checking)
 * <ul>
 *     <li>Updated {@link #PERMISSION_CHECKING} to add customer admin logic</li>
 * </ul>
 * </p>
 *
 * @author saarixx, FireIce, isv, lmmortal, GreatKevin, TCSASSEMBLER, freegod
 * @version 1.4
 */
@Stateless
public class GamePlanServiceBean implements GamePlanServiceLocal, GamePlanServiceRemote {
    /**
     * Represents the sql to retrieve software project data. Any project with 'Deleted' status or belong to
     * 'Spec Review' category will be excluded.
     */
    private static final String RETRIEVE_SOFTWARE_PROJECT_DATA = "SELECT p.tc_direct_project_id, p.project_id,"
            + "(select pi.value from project_info pi where pi.project_id = p.project_id AND pi.project_info_type_id = 6) as project_name,"
            + "(SELECT MIN(NVL(actual_start_time, scheduled_start_time)) FROM project_phase ph"
            + "    WHERE ph.project_id = p.project_id and ph.phase_type_id = 1) as start_time,"
            + "    CASE WHEN p.project_status_id IN(1,2) " +
            "        THEN " +
            "       (SELECT MAX(NVL(actual_end_time, scheduled_end_time)) " +
            "        FROM    project_phase ph " +
            "        WHERE   ph.project_id         = p.project_id " +
            "            AND ph.phase_type_id = 10 " +
            "        ) " +
            "        ELSE " +
            "        (SELECT MAX(actual_end_time) " +
            "        FROM    project_phase ph " +
            "        WHERE   ph.project_id = p.project_id AND ph.phase_type_id = 10 " +
            "        ) " +
            "        END " +
            "        AS final_review_end_time             ,"
            + "    CASE WHEN p.project_status_id IN(1,2) " +
            "        THEN " +
            "       (SELECT MAX(NVL(actual_end_time, scheduled_end_time)) " +
            "        FROM    project_phase ph " +
            "        WHERE   ph.project_id         = p.project_id " +
            "            AND NOT ph.phase_type_id IN (13, 14) " +
            "        ) " +
            "        ELSE " +
            "        (SELECT MAX(actual_end_time) " +
            "        FROM    project_phase ph " +
            "        WHERE   ph.project_id         = p.project_id " +
            "        ) " +
            "        END " +
            "        AS end_time             ,"
            + "tcd.user_id, psl.name as project_status,"
            + "(SELECT ptl.name FROM phase_type_lu ptl WHERE phase_type_id = "
            + "    (SELECT MIN(phase_type_id) FROM project_phase ph "
            + "        WHERE ph.phase_status_id = 2 AND ph.project_id = p.project_id)) as current_phase,"
            + "pcl.name as project_type,"
            + "(SELECT COUNT(lpx.dest_project_id) FROM linked_project_xref lpx"
            + "    WHERE lpx.source_project_id = p.project_id AND lpx.link_type_id = 5) "
            + "FROM project p"
            + "  INNER JOIN tc_direct_project tcd ON tcd.project_id = p.tc_direct_project_id"
            + "  LEFT OUTER JOIN project_status_lu psl ON psl.project_status_id = p.project_status_id"
            + "  LEFT OUTER JOIN project_category_lu pcl ON pcl.project_category_id = p.project_category_id "
            + "WHERE p.project_status_id != 3 AND p.project_category_id != 27  ";


    /**
     * Represents the sql for retrieving IDs of dependency projects.
     */
    private static final String RETRIEVE_DEPENDENCY_PROJECT_IDS_SQL = 
            "SELECT  lp.dest_project_id, lp.link_type_id FROM linked_project_xref lp, project p " +
			" WHERE lp.source_project_id = :projectId " + 
			" AND lp.source_project_id = p.project_id AND lp.link_type_id in (1, 4, 5, 6) " +
			" AND p.tc_direct_project_id = (SELECT tc_direct_project_id FROM project WHERE project_id = lp.dest_project_id) " +
			" AND p.tc_direct_project_id IS NOT NULL and p.project_status_id != 3 ";
                    
    /**
     * Represents the sql for retrieving tc direct project.
     */
    private static final String RETRIEVE_DIRECT_PROJECT_SQL =
            "SELECT project_id, name FROM user_permission_grant upg, tc_direct_project WHERE upg.resource_id = project_id and  upg.permission_type_id in (1,2,3 ) ";

    /**
     * The permission checking sub query to check whether the user has permission on the project.
     *
     * Version 1.2 (Release Assembly - TopCoder Security Groups Release 7) Updates:
     * <p>allow user with group permission on the project and the billing account of the project
     * accesses the game plan data of the project</p>
     */
    private static final String PERMISSION_CHECKING = " AND (exists (select u.user_id  "
            + "    from user as u "
            + "    join user_role_xref as uref  "
            + "       on u.user_id = :userId "
            + "       and u.user_id = uref.login_id  "
            + "    join security_roles as sr  "
            + "      on uref.role_id = sr.role_id  "
            + "      and sr.description in ('TC Staff')) "
            + "   OR exists (select permission_type_id  "
            + "          from user_permission_grant as upg    "
            + "         where resource_id=p.tc_direct_project_id and permission_type_id in (1,2,3)  "
            + "          and upg.user_id = :userId  )" + "OR\n" +
            "               exists (\n" +
            "                       SELECT\n" +
            "                              tdp.project_id as tc_direct_project_id\n" +
            "                       FROM tc_direct_project tdp\n" +
            "                       INNER JOIN group_associated_direct_projects gadp ON tdp.project_id = gadp.tc_direct_project_id\n" +
            "                       INNER JOIN customer_group sg ON sg.group_id = gadp.group_id\n" +
            "                       INNER JOIN group_member gm ON gm.group_id = sg.group_id\n" +
            "                       WHERE gm.user_id = :userId \n" +
            "                       AND   gm.active = 1\n" +
            "                       AND   sg.archived = 0\n" +
            "                       AND   tdp.project_id = p.tc_direct_project_id\n" +
            "                       )\n" +
            "            OR\n" +
            "                exists (\n" +
            "                        SELECT\n" +
            "                               tdp.project_id as tc_direct_project_id\n" +
            "                        FROM tc_direct_project tdp\n" +
            "                        INNER JOIN direct_project_account dpa ON tdp.project_id = dpa.project_id\n" +
            "                        INNER JOIN group_associated_billing_accounts gaba ON gaba.billing_account_id = dpa.billing_account_id\n" +
            "                        INNER JOIN customer_group sg ON sg.group_id = gaba.group_id\n" +
            "                        INNER JOIN group_member gm ON gm.group_id = sg.group_id\n" +
            "                        WHERE gm.user_id = :userId \n" +
            "                        AND   gm.active = 1\n" +
            "                        AND   sg.archived = 0\n" +
            "                        AND tdp.project_id = p.tc_direct_project_id\n" +
            "                        )" +
            "            OR\n" +
            "                exists (\n" +
            "                        SELECT\n" +
            "                               tdp.project_id as tc_direct_project_id\n" +
            "                        FROM tc_direct_project tdp\n" +
            "                        INNER JOIN direct_project_account dpa ON tdp.project_id = dpa.project_id\n" +
            "                        INNER JOIN tt_project ttp ON dpa.billing_account_id = ttp.project_id\n" +
            "                        INNER JOIN tt_client_project ttcp ON ttp.project_id = ttcp.project_id\n" +
            "                        INNER JOIN tt_client ttc ON ttcp.client_id = ttc.client_id\n" +
            "                        INNER JOIN customer_group sg ON sg.client_id = ttc.client_id\n" +
            "                        INNER JOIN group_member gm ON sg.group_id = gm.group_id\n" +
            "                        WHERE sg.auto_grant = 1\n" +
            "                        AND   gm.user_id = :userId \n" +
            "                        AND   gm.active = 1\n" +
            "                        AND   sg.archived = 0\n" +
            "                        AND ttc.client_status_id = 1\n" +
            "                        AND tdp.project_id = p.tc_direct_project_id\n" +
            "                        )\n" +
            "OR EXISTS (\n"+
            "                        SELECT \n"+
            "                        tdp.project_id as tc_direct_project_id\n"+
            "                        FROM project_info pi\n"+
            "                        INNER JOIN tc_direct_project tdp ON p.tc_direct_project_id = tdp.project_id\n"+
            "                        INNER JOIN tt_client_project cp ON cp.project_id = pi.value\n"+
            "                        INNER JOIN tt_client c ON c.client_id = cp.client_id\n"+
            "                        INNER JOIN customer_administrator ca ON ca.client_id = c.client_id\n"+
            "                        WHERE pi.project_info_type_id = 32\n"+
            "                        AND   c.status = 1\n"+
            "                        AND   c.is_deleted = 0\n"+
            "                        AND   pi.project_id = p.project_id\n"+
            "                        AND   tdp.project_id = p.tc_direct_project_id\n"+
            "                        AND   ca.user_id = :userId \n"+
            "               )\n" +
            "OR EXISTS (\n" +
            "                        SELECT tdp.project_id AS tc_direct_project_id\n" +
            "                        FROM tc_direct_project tdp\n" +
            "                        INNER JOIN direct_project_account dpa ON tdp.project_id = dpa.project_id\n" +
            "                        INNER JOIN tt_project ttp ON dpa.billing_account_id = ttp.project_id\n" +
            "                        INNER JOIN tt_client_project ttcp ON ttp.project_id = ttcp.project_id\n" +
            "                        INNER JOIN tt_client ttc ON ttcp.client_id = ttc.client_id\n" +
            "                        INNER JOIN customer_administrator ca ON ca.client_id = ttc.client_id\n" +
            "                        WHERE ttc.status = 1\n" +
            "                           AND   ttc.is_deleted = 0\n" +
            "                           AND   tdp.project_id = p.tc_direct_project_id\n" +
            "                           AND   ca.user_id = :userId \n" +
            "               ))\n";

    /**
     * Represents the name for active status.
     */
    private static final String ACTIVE_STATUS = "Active";

    /**
     * Represents the cockpit administrator role name.
     */
    private static final String COCKPIT_ADMINISTRATOR_ROLE = "Cockpit Administrator";

    /**
     * Represents the tc staff role name.
     */
    private static final String TC_STAFF_ROLE = "TC Staff";

    /**
     * Represents the <b>EntityManager</b> instance to be used by this class for accessing software contests specific
     * data.
     *
     * Cannot be null and is not modified after initialization. Initialized by EJB container injection. Is used in
     * retrieveGamePlanData().
     */
    @PersistenceContext(name = "softwarePersistence")
    private EntityManager softwareEntityManager;

    /**
     * The name of the logger to be used by this class.
     *
     * Can be set with EJB container injection. Is null when logging is not required. Must not be empty after injection.
     * Is used in initialize().
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * The logger instance to be used by this class.
     *
     * Can be set in initialize(). Is null if logging is not required. Is used in retrieveGamePlanData().
     */
    private Log log;

    /**
     * Creates a <code>GamePlanServiceBean</code> instance.
     */
    public GamePlanServiceBean() {
    }

    /**
     * Initializes this class. This method is called after the bean is constructed by the EJB container.
     *
     * @throws GamePlanServiceConfigurationException
     *          if any error occurred when initializing this class
     */
    @PostConstruct
    protected void initialize() {
        if (null != logName) {
            if (0 == logName.trim().length()) {
                throw new GamePlanServiceConfigurationException("The 'logName' configuration should not be empty");
            }

            log = LogManager.getLog(logName);
        }
    }

    /**
     * Retrieves the game plan data of TC Direct projects for the current user. If the current user is an admin, data
     * for all existing TC Direct projects are retrieved.
     *
     * @param tcSubject the TC subject
     * @return the retrieved TC Direct projects game plan data (not null, doesn't contain null; empty if there is no TC
     *         Direct projects for the current user)
     * @throws IllegalArgumentException     if tcSubject is null
     * @throws IllegalStateException        if softwareEntityManager or studioEntityManager is null
     * @throws GamePlanPersistenceException if some error occurred when accessing the persistence
     */
    public List<TCDirectProjectGamePlanData> retrieveGamePlanData(TCSubject tcSubject)
            throws GamePlanPersistenceException {
        logInfo(MessageFormat.format("Enter into GamePlanServiceBean#retrieveGamePlanData(TCSubject) with [{0}]",
                null == tcSubject ? null : "TCSubject with userId - " + tcSubject.getUserId()));
        final long start = System.currentTimeMillis();

        Long userId = getUserId(tcSubject);

        try {
            List<TCDirectProjectGamePlanData> result = retrieveGamePlanDataByUser(userId, null);

            final long end = System.currentTimeMillis();
            logInfo(MessageFormat.format(
                    "Exit GamePlanServiceBean#retrieveGamePlanData(TCSubject) with {0}, the method call takes {1}ms",
                    format(result), end - start));

            return result;
        } catch (GamePlanPersistenceException e) {
            throw logException(e);
        }
    }

    /**
     * Retrieves the game plan data of specified TC Direct project for the current user. If the current user is an
     * admin, any TC Direct project can be retrieved.
     *
     * @param tcSubject       the TC subject
     * @param directProjectId the TC Direct Project id
     * @return the retrieved TC Direct project game plan data (possibly null if there is no TC Direct projects for the
     *         current user)
     * @throws IllegalArgumentException     if tcSubject is null
     * @throws IllegalStateException        if softwareEntityManager or studioEntityManager is null
     * @throws GamePlanPersistenceException if some error occurred when accessing the persistence
     */
    public TCDirectProjectGamePlanData retrieveGamePlanData(TCSubject tcSubject, long directProjectId)
            throws GamePlanPersistenceException {
        logInfo(MessageFormat.format(
                "Enter into GamePlanServiceBean#retrieveGamePlanData(TCSubject, long) with [{0}] and [{1}]",
                null == tcSubject ? null : "TCSubject with userId - " + tcSubject.getUserId(), directProjectId));
        final long start = System.currentTimeMillis();

        Long userId = getUserId(tcSubject);

        try {
            List<TCDirectProjectGamePlanData> result = retrieveGamePlanDataByUser(userId, directProjectId);

            TCDirectProjectGamePlanData tcDirectProjectGamePlanData = null;
            if (result.size() != 0) {
                tcDirectProjectGamePlanData = result.get(0);
            }
            final long end = System.currentTimeMillis();
            logInfo(MessageFormat.format(
                    "Exit GamePlanServiceBean#retrieveGamePlanData(TCSubject, long) with {0},"
                            + " the method call takes {1}ms",
                    tcDirectProjectGamePlanData == null ? null :
                            "TCDirectProjectGamePlanData[id = " + tcDirectProjectGamePlanData.getTcDirectProjectId()
                                    + "]", end - start));

            return tcDirectProjectGamePlanData;
        } catch (Exception e) {
            logException(e);
            throw new GamePlanPersistenceException(e.getMessage(), e);
        }
    }

    /**
     * Gets the user id from the TCSubject instance. If the current user is an admin, null will return.
     * @param tcSubject the TCSubject instance.
     * @return the current user id, or null If the current user is an admin.
     * @throws IllegalArgumentException if the tcSubject is null.
     */
    private Long getUserId(TCSubject tcSubject) {
        if (null == tcSubject) {
            throw logException(new IllegalArgumentException("The tcSubject should not be null."));
        }

/*        // checks whether the current user is an admin
        boolean isAdmin = false;
        Set<TCPrincipal> roles = tcSubject.getPrincipals();

        if (roles != null) {
            for (TCPrincipal role : roles) {
                String roleName = role.getName();

                if (COCKPIT_ADMINISTRATOR_ROLE.equalsIgnoreCase(roleName) || TC_STAFF_ROLE.equalsIgnoreCase(roleName)) {
                    isAdmin = true;
                    break;
                }
            }
        }

        // if current user is not admin, retrieve the user id to retrieve game plan data.
        Long userId = null;
        if (!isAdmin) {
            userId = tcSubject.getUserId();
        }
        return userId; */

        return tcSubject.getUserId();
    }

    /**
     * Retrieves the game plan data of TC Direct projects for the specified user. If userId is null, data for all
     * existing TC Direct projects are retrieved.
     *
     * @param userId the ID of the user associated with TC Direct projects (null if data for all existing TC Direct
     *               projects must be retrieved)
     * @param directProjectId the specific TC Direct Project id.
     * @return the retrieved TC Direct projects game plan data (not null, doesn't contain null; empty if there is no TC
     *         Direct projects for the specified user)
     * @throws IllegalStateException        if softwareEntityManager or studioEntityManager is null
     * @throws GamePlanPersistenceException if some error occurred when accessing the persistence
     */
    private List<TCDirectProjectGamePlanData> retrieveGamePlanDataByUser(Long userId, Long directProjectId)
            throws GamePlanPersistenceException {
        if (null == softwareEntityManager) {
            throw logException(new IllegalStateException("The softwareEntityManager is not properly initialized."));
        }

        Date now = new Date();
        // Create mapping from TC Direct Project ID to TCDirectProjectGamePlanData instance
        Map<Long, TCDirectProjectGamePlanData> tcDirectProjectsMap = new HashMap<Long, TCDirectProjectGamePlanData>();
        
        String directProjectQuery = RETRIEVE_DIRECT_PROJECT_SQL; 
        
        if (userId != null) {
            directProjectQuery += " AND upg.user_id = :userId ";
        }

        if (directProjectId != null) {
            directProjectQuery += " AND project_id = :directProjectId";
        }
        
        // Create query for retrieving direct projects data
        Query query = softwareEntityManager.createNativeQuery(directProjectQuery);
        
        // set the parameter value if present
        if (userId != null) {
            query.setParameter("userId", userId);
        }

        if (directProjectId != null) {
            query.setParameter("directProjectId", directProjectId);
        }

        List<Object[]> resultList = query.getResultList();
        
        for (Object[] row : resultList) {
        	TCDirectProjectGamePlanData data = new TCDirectProjectGamePlanData();
        	long tcDirectProjectId = (Integer) row[0];
        	String tcDirectProjectName = (String) row[1];
        	
            data.setTcDirectProjectId(tcDirectProjectId);
            data.setTcDirectProjectName(tcDirectProjectName);
            
            tcDirectProjectsMap.put(tcDirectProjectId, data); 
        }
        
        try {
            retrieveSoftwareProjectData(userId, directProjectId, now, tcDirectProjectsMap);

            return new ArrayList<TCDirectProjectGamePlanData>(tcDirectProjectsMap.values());
        } catch (ClassCastException e) {
            throw new GamePlanPersistenceException("Fail to convert the retrieved data.", e);
        } catch (PersistenceException e) {
            throw new GamePlanPersistenceException("Error occurred when accessing the persistence", e);
        }
    }

    /**
     * Updates the IDs of dependency projects for the given list of <b>SoftwareProjectData</b>.
     *
     * @param softwareProjects the list of SoftwareProjectData instance to update dependency project ids.
     * @throws PersistenceException If any problem to access the persistence.
     */
    private void updateDependencyProjectIds(List<SoftwareProjectData> softwareProjects) {
        // Create query for retrieving dependency project IDs
        Query dependencyQuery = softwareEntityManager.createNativeQuery(RETRIEVE_DEPENDENCY_PROJECT_IDS_SQL);
        for (SoftwareProjectData softwareProjectData : softwareProjects) {
            // Set project ID to the query
            dependencyQuery.setParameter("projectId", softwareProjectData.getProjectId());
//            List<Integer> dependencyProjectIdsList = dependencyQuery.getResultList();
            List<Object> dependencyResults = dependencyQuery.getResultList();

            // extract the IDs of dependency projects
            long[] dependencyProjectIds = new long[dependencyResults.size()];
            long[] dependencyProjectTypeIds = new long[dependencyProjectIds.length];
            for (int i = 0; i < dependencyProjectIds.length; i++) {
                Object[] row = (Object[]) dependencyResults.get(i);
                dependencyProjectIds[i] = (Integer) row[0];
                dependencyProjectTypeIds[i] = (Integer) row[1];
            }
            softwareProjectData.setDependencyProjectIds(dependencyProjectIds);
            softwareProjectData.setDependencyProjectTypeIds(dependencyProjectTypeIds);
        }
    }

    /**
     * Retrieves the software project data.
     *
     * @param userId              the user id that the project associated with.
     * @param now                 the current retrieval time.
     * @param tcDirectProjectsMap the map to hold the TCDirectProjectGamePlanData instance.
     * @throws ClassCastException If fail to convert the retrieved data.
     * @throws PersistenceException If any problem to access the persistence.
     */
    private void retrieveSoftwareProjectData(Long userId, Long directProjectId, Date now,
                                             Map<Long, TCDirectProjectGamePlanData> tcDirectProjectsMap) {
        // Create list to hold all software projects
        List<SoftwareProjectData> allSoftwareProjects = new ArrayList<SoftwareProjectData>();

        String queryStr = RETRIEVE_SOFTWARE_PROJECT_DATA;
        if (userId != null) {
            queryStr += PERMISSION_CHECKING;
        }

        if (directProjectId != null) {
            queryStr += " AND p.tc_direct_project_id = :directProjectId";
        }

        // Create query to retrieve software project data
        Query query = softwareEntityManager.createNativeQuery(queryStr);
        if (userId != null) {
            query.setParameter("userId", userId);
        }

        if (directProjectId != null) {
            query.setParameter("directProjectId", directProjectId);
        }

        List<Object[]> resultList = query.getResultList();

        // extract the software project data.
        for (Object[] row : resultList) {
            SoftwareProjectData softwareProjectData = new SoftwareProjectData();
            // Since INNER JOIN is applied to project and tc_direct_project, tc_direct_project_id will never null.
            long tcDirectProjectId = (Integer) row[0];
            softwareProjectData.setTcDirectProjectId(tcDirectProjectId);
            // project_id is the primary key, so will never null.
            softwareProjectData.setProjectId((Integer) row[1]);
            softwareProjectData.setProjectName(escapeName((String) row[2]));
            softwareProjectData.setStartDate((Date) row[3]);

            if(row[4] != null) {
                softwareProjectData.setFinalReviewEndDate((Date) row[4]);
            }

            if(row[5] == null) {
                final Calendar cal = Calendar.getInstance();
                cal.setTime(softwareProjectData.getStartDate());
                cal.add(Calendar.HOUR, 24);
                softwareProjectData.setEndDate(cal.getTime());

            } else {
                softwareProjectData.setEndDate((Date) row[5]);
            }

            if (softwareProjectData.getFinalReviewEndDate() == null) {
                // no final review phase, use contest end date as final review end date
                softwareProjectData.setFinalReviewEndDate(softwareProjectData.getEndDate());
            }

            // user_id is not null in tc_direct_project table, so cast is safe.
            softwareProjectData.setCreateUserId(new Long((Integer) row[6]));
            softwareProjectData.setProjectStatus((String) row[7]);
            softwareProjectData.setCurrentPhase((String) row[8]);
            softwareProjectData.setProjectType((String) row[9]);
            // the count function will never return null, so cast is safe.
            softwareProjectData.setRepost(((BigDecimal) row[10]).intValue() > 0);

            if (softwareProjectData.getStartDate() != null && softwareProjectData.getStartDate().before(now) &&
                    ACTIVE_STATUS.equals(softwareProjectData.getProjectStatus())) {
                softwareProjectData.setStarted(true);
            }
            if (softwareProjectData.getEndDate() != null && softwareProjectData.getEndDate().before(now)) {
                softwareProjectData.setFinished(true);
            }

            // Get list of software projects for this TC Direct project and add it.
            getTCDirectProjectGamePlanData(tcDirectProjectsMap, tcDirectProjectId).getSoftwareProjects()
                    .add(softwareProjectData);
            allSoftwareProjects.add(softwareProjectData);
        }

        updateDependencyProjectIds(allSoftwareProjects);
    }

    /**
     * <p>Gets the <code>TCDirectProjectGamePlanData</code> instance associated with the given
     * <code>tcDirectProjectId</code>. If not present, create one and save in the map.</p>
     *
     * @param tcDirectProjectsMap the map holds the mapping from tcDirectProjectId to TCDirectProjectGamePlanData
     *                            instance.
     * @param tcDirectProjectId   the TC Direct project id.
     * @return the associated <code>TCDirectProjectGamePlanData</code> instance
     */
    private TCDirectProjectGamePlanData getTCDirectProjectGamePlanData(
            Map<Long, TCDirectProjectGamePlanData> tcDirectProjectsMap,
            long tcDirectProjectId) {
        // Retrieve TC Direct project data from the map
        TCDirectProjectGamePlanData tcDirectProjectData = tcDirectProjectsMap.get(tcDirectProjectId);
        if (tcDirectProjectData == null) {
            // Create new TC Direct project game plan data instance and saved in the map.
            tcDirectProjectData = new TCDirectProjectGamePlanData();
            tcDirectProjectData.setTcDirectProjectId(tcDirectProjectId);
            
            tcDirectProjectsMap.put(tcDirectProjectId, tcDirectProjectData);
        }
        return tcDirectProjectData;
    }

    /**
     * <p>Logs the message at INFO level.</p>
     *
     * @param message the log message
     */
    private void logInfo(String message) {
        if (null != log) {
            log.log(Level.INFO, message);
        }
    }

    /**
     * <p>Logs the exception at ERROR level.</p>
     *
     * @param e the error exception
     * @return the error exception
     */
    private <T extends Throwable> T logException(T e) {
        if (null != log) {
            log.log(Level.ERROR, e, e.getMessage());
        }

        return e;
    }

    /**
     * <p>Formats the List&lt;TCDirectProjectGamePlanData&gt; instance with string representation.</p>
     *
     * @param tcDirectProjectGamePlanDataList
     *         the list of TCDirectProjectGamePlanData.
     * @return the string representation of List&lt;TCDirectProjectGamePlanData&gt; instance
     */
    private String format(List<TCDirectProjectGamePlanData> tcDirectProjectGamePlanDataList) {

        List<Long> ids = new ArrayList<Long>();
        for (TCDirectProjectGamePlanData tcDirectProjectGamePlanData : tcDirectProjectGamePlanDataList) {
            ids.add(tcDirectProjectGamePlanData.getTcDirectProjectId());
        }
        StringBuilder stringBuilder = new StringBuilder("list of TCDirectProjectGamePlanData with tcDirectProjectIds ");
        stringBuilder.append(ids.toString());

        return stringBuilder.toString();
    }
    
    /**
     * Escape the special characters in project name and contest name.
     *
     * @param original the string needs to escape
     * @return the escape characters.
     */
    private static String escapeName(String original) { 
        if(original==null) return ""; 
        StringBuffer out=new StringBuffer(""); 
        char[] chars=original.toCharArray(); 
        for(int i=0;i<chars.length;i++) 
        { 
          boolean found=true; 
          switch(chars[i]) 
          { 
            case 60:out.append("&lt;"); break; //< 
            case 62:out.append("&gt;"); break; //> 
            case 34:out.append("&quot;"); break; //" 
            case 38:out.append("&amp;"); break; // &
            default:found=false;break; 
          } 
          if(!found) out.append(chars[i]); 
           
        } 
        return out.toString();   
  } 
}

