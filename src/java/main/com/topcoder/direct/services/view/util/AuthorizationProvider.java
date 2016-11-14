/*
 * Copyright (C) 2010 - 2016 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.direct.services.view.dto.contest.ContestCopilotDTO;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>An utility class providing the methods for making authorization decisions.</p>
 *
 * <p>Sub-sequent assemblies may expand this class with additional methods for calling other queries when there is a
 * need or they can fully re-work the concept of authorization provider.</p>
 *
 * <p>
 * Version 1.1 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>Remove method <code>isUserGrantedAccessToSoftwareContest</code>.</li>
 *   <li>Add a parameter <code>TCSubject</code> to methods {@link #isUserGrantedAccessToProject(TCSubject, long)}
 *   and {@link #isUserGrantedAccessToContest(TCSubject, long)}.</li>
 *   <li>Provide implementation for {@link #isUserGrantedAccessToProject(TCSubject, long)}
 *   and {@link #isUserGrantedAccessToContest(TCSubject, long)}.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Topcoder Security Groups Backend - Direct Permissions Propagation Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #isUserGrantedAccessToContest(TCSubject, long)} and 
 *     {@link #isUserGrantedAccessToProject(TCSubject, long)} to check user permissions against security groups also.
 *     </li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (Module Assembly - TC Cockpit Project Milestones Management Front End) change log:
 *   <ol>
 *       <li>Add method {@link #isUserGrantedToModifyMilestone(com.topcoder.security.TCSubject, long)}</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TopCoder Security Groups Release 5 v1.0) change log:
 *   <ol>
 *       <li>Add method {@link #isUserGrantedWriteAccessToProject(TCSubject, long)} to check whether the user
 *       has write permission on a project.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.5 (Release Assembly - TopCoder Cockpit Asset View Release 4 - Resource restriction update)
 * <ul>
 *     <li>Updated method {@link #isUserGrantedWriteAccessToProject(com.topcoder.security.TCSubject, long)} to fix the write permission checking</li>
 *     <li>Updated method {@link #isUserGrantedAccessToProject(com.topcoder.security.TCSubject, long)} (com.topcoder.security.TCSubject, long)} to pass in permission type ids</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6 (TopCoder Direct - Add Group Permission Logic and project full permission checking)
 * <ul>
 *     <li>Added method {@link #isUserGrantedFullAccessToProject(com.topcoder.security.TCSubject, long)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.7 (TC Direct - ASP Integration Work Management)
 * <ul>
 *     <li>Added method {@link #isUserGrantedToAccessWorkManager(TCSubject, long)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Changes in version 1.8 (TopCoder Direct - Remove ASP Integration Related Logic):
 * <ul>
 * <li>Remove {@link #isUserGrantedToAccessWorkManager(TCSubject, long)} method.</li>
 * </ul>
 * </p>
 *
 * @author isv, GreatKevin, TCSCODER
 * @version 1.8
 */
public class AuthorizationProvider {

    /**
     * Private constant specifying administrator role.
     *
     * @since 1.2
     */
    private static final String ADMIN_ROLE = "Cockpit Administrator";

    /**
     * <p>Constructs new <code>AuthorizationProvider</code> instance. This implementation does nothing.</p>
     */
    private AuthorizationProvider() {
    }

    /**
     * <p>Checks if specified user is granted access permission to specified project.</p>
     *
     * @param tcSubject a <code>TCSubject</code> providing the user subject.
     * @param projectId a <code>long</code> providing the project ID.
     * @return <code>true</code> if user is granted access to project; <code>false</code> otherwise.
     * @throws Exception if any error occurs
     */
    public static boolean isUserGrantedAccessToProject(TCSubject tcSubject, long projectId) throws Exception {
        if (DirectUtils.isTcStaff(tcSubject) || DirectUtils.isRole(tcSubject, ADMIN_ROLE)) {
            return true;
        }
        
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("has_cockpit_project_permissions");
        request.setProperty("tcdirectid", String.valueOf(projectId));
        request.setProperty("uid", String.valueOf(tcSubject.getUserId()));
        request.setProperty("permTypeIds", "1,2,3");
        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("has_cockpit_project_permissions");
        if (resultContainer.isEmpty()) {
            HttpServletRequest servletRequest = DirectUtils.getServletRequest();
            ServletContext ctx = servletRequest.getSession().getServletContext();
            WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
            AuthorizationService authorizationService 
                = (AuthorizationService) applicationContext.getBean("groupAuthorizationService");

            return DirectUtils.hasPermissionBySecurityGroups(tcSubject, projectId, authorizationService, 
                                                             GroupPermissionType.READ, GroupPermissionType.WRITE, 
                                                             GroupPermissionType.FULL);
        } else {
            return true;
        }
    }

    /**
     * <p>Checks if specified user is granted writer access permission to specified project.</p>
     *
     * @param tcSubject a <code>TCSubject</code> providing the user subject.
     * @param projectId a <code>long</code> providing the project ID.
     * @return <code>true</code> if user is granted access to project; <code>false</code> otherwise.
     * @throws Exception if any error occurs
     * @since 1.4
     */
    public static boolean isUserGrantedWriteAccessToProject(TCSubject tcSubject, long projectId) throws Exception {
        if (DirectUtils.isTcStaff(tcSubject) || DirectUtils.isRole(tcSubject, ADMIN_ROLE)) {
            return true;
        }
        
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("has_cockpit_project_permissions");
        request.setProperty("tcdirectid", String.valueOf(projectId));
        request.setProperty("uid", String.valueOf(tcSubject.getUserId()));
        request.setProperty("permTypeIds", "2,3");
        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("has_cockpit_project_permissions");
        if (resultContainer.isEmpty()) {
            HttpServletRequest servletRequest = DirectUtils.getServletRequest();
            ServletContext ctx = servletRequest.getSession().getServletContext();
            WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
            AuthorizationService authorizationService 
                = (AuthorizationService) applicationContext.getBean("groupAuthorizationService");

            return DirectUtils.hasPermissionBySecurityGroups(tcSubject, projectId, authorizationService, 
                                                             GroupPermissionType.WRITE, GroupPermissionType.FULL);
        } else {
            return true;
        }
    }

    /**
     * <p>Checks if specified user is granted full access permission to specified project.</p>
     *
     * @param tcSubject a <code>TCSubject</code> providing the user subject.
     * @param projectId a <code>long</code> providing the project ID.
     * @return <code>true</code> if user is granted access to project; <code>false</code> otherwise.
     * @throws Exception if any error occurs
     * @since 1.6
     */
    public static boolean isUserGrantedFullAccessToProject(TCSubject tcSubject, long projectId) throws Exception {
        if (DirectUtils.isTcStaff(tcSubject) || DirectUtils.isRole(tcSubject, ADMIN_ROLE)) {
            return true;
        }

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("has_cockpit_project_permissions");
        request.setProperty("tcdirectid", String.valueOf(projectId));
        request.setProperty("uid", String.valueOf(tcSubject.getUserId()));
        request.setProperty("permTypeIds", "3");
        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("has_cockpit_project_permissions");
        if (resultContainer.isEmpty()) {
            HttpServletRequest servletRequest = DirectUtils.getServletRequest();
            ServletContext ctx = servletRequest.getSession().getServletContext();
            WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
            AuthorizationService authorizationService
                    = (AuthorizationService) applicationContext.getBean("groupAuthorizationService");

            return DirectUtils.hasPermissionBySecurityGroups(tcSubject, projectId, authorizationService,
                    GroupPermissionType.FULL);
        } else {
            return true;
        }
    }
    
    /**
     * <p>Checks if specified user is granted access permission to specified contest.</p>
     *
     * @param tcSubject a <code>TCSubject</code> providing the user subject.
     * @param contestId a <code>long</code> providing the contest ID.
     * @return <code>true</code> if user is granted access to Studio contest; <code>false</code> otherwise.
     * @throws Exception if any error occurs
     */
    public static boolean isUserGrantedAccessToContest(TCSubject tcSubject, long contestId) throws Exception {
        if (DirectUtils.isTcStaff(tcSubject) || DirectUtils.isRole(tcSubject, ADMIN_ROLE)) {
            return true;
        }
        
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("has_cockpit_permissions");
        request.setProperty("pj", String.valueOf(contestId));
        request.setProperty("uid", String.valueOf(tcSubject.getUserId()));
        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("has_cockpit_permissions");
        if (resultContainer.isEmpty()) {
            HttpServletRequest servletRequest = DirectUtils.getServletRequest();
            ServletContext ctx = servletRequest.getSession().getServletContext();
            WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
            AuthorizationService authorizationService
                = (AuthorizationService) applicationContext.getBean("groupAuthorizationService");
            ProjectServices projectServices
                = (ProjectServices) applicationContext.getBean("projectServices");
            long projectId = projectServices.getTcDirectProject(contestId);
            return DirectUtils.hasPermissionBySecurityGroups(tcSubject, projectId, authorizationService,
                                                             GroupPermissionType.READ, GroupPermissionType.WRITE,
                                                             GroupPermissionType.FULL);
        } else {
            return true;
        }
    }
    
    /**
     * <p>Checks if specified user is granted access permission to modify the specified milestone.</p>
     *
     * @param tcSubject  a <code>TCSubject</code> providing the user subject.
     * @param milestoneId a <code>long</code> providing the contest ID.
     * @return <code>true</code> if user is granted access to modify the milestone, <code>false</code> otherwise.
     * @throws Exception if any error occurs.
     * @since 1.2
     */
    public static boolean isUserGrantedToModifyMilestone(TCSubject tcSubject, long milestoneId) throws Exception {
        if (DirectUtils.isTcStaff(tcSubject)) {
            return true;
        }

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("has_milestone_write_permission");
        request.setProperty("mid", String.valueOf(milestoneId));
        request.setProperty("uid", String.valueOf(tcSubject.getUserId()));
        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("has_milestone_write_permission");
        return resultContainer.size() > 0;
    }
}
