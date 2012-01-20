/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.ajax.DirectJsonBeanProcessorMatcher;
import com.topcoder.direct.services.view.ajax.SoftwareCompetitionBeanProcessor;
import com.topcoder.direct.services.view.ajax.XMLGregorianCalendarBeanProcessor;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.permission.ProjectPermission;
import com.topcoder.service.project.SoftwareCompetition;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>A <code>Struts</code> action for handling requests for updating the project level permissions for all projects
 * which current user is granted <code>Full Access</code> permission for.</p>
 * 
 * <p>
 * Version 1.0.1 (TC Direct - Permission Updates) Change notes:
 *   <ol>
 *     <li>Updated executeAction() method.</li>
 *   </ol>
 * </p>
 * 
 * <p>
 *     Version 1.1 update notes:
 *     <li>
 *         change call of contest service facade #updateProjectPermissions to permission service facade #updateProjectPermissions
 *     </li>
 * </p>
 * 
 * @author isv, TCSASSEMBLER
 * @version 1.1 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part One)
 * @since Direct Permissions Setting Back-end and Integration Assembly 1.0
 */
public class UpdateProjectPermissionsAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>String</code> providing the JSON presentation of list of permissions to be updated.</p>
     */
    private String permissionsJSON;


    /**
     * <p>Constructs new <code>UpdateProjectPermissionsAction</code> instance. This implementation does nothing.</p>
     */
    public UpdateProjectPermissionsAction() {
    }

    /**
     * <p>Handles the incoming request. Gets the list of project level permissions for all projects which current user
     * is granted <code>Full Access</code> permission for and binds them to request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        ArrayList<ProjectPermission> permissions = new ArrayList<ProjectPermission>();
        JSONObject jsonData = (JSONObject) JSONSerializer.toJSON(getPermissionsJSON());
        JSONArray jsonPermissions
            = jsonData.getJSONObject("result").getJSONObject("return").getJSONArray("permissions");
        for (int i = 0; i < jsonPermissions.size(); i++) {
            JSONObject jsonPermission = jsonPermissions.getJSONObject(i);
            ProjectPermission permission = new ProjectPermission();
            permission.setHandle(jsonPermission.getString("handle"));
            permission.setPermission(jsonPermission.getString("permission"));
            permission.setProjectId(jsonPermission.getLong("projectId"));
            permission.setProjectName(jsonPermission.getString("projectName"));
            permission.setUserId(jsonPermission.getLong("userId"));
            permission.setStudio(jsonPermission.getBoolean("studio"));
            permission.setUserPermissionId(jsonPermission.getLong("userPermissionId"));
            permissions.add(permission);
        }
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        getPermissionServiceFacade().updateProjectPermissions(tcSubject, permissions, ResourceRole.RESOURCE_ROLE_OBSERVER_ID);
    }

    /**
     * <p>Gets the JSON presentation of list of permissions to be updated.</p>
     *
     * @return a <code>String</code> providing the JSON presentation of list of permissions to be updated.
     */
    public String getPermissionsJSON() {
        return this.permissionsJSON;
    }

    /**
     * <p>Sets the JSON presentation of list of permissions to be updated.</p>
     *
     * @param permissionsJSON a <code>String</code> providing the JSON presentation of list of permissions to be
     *        updated.
     */
    public void setPermissionsJSON(String permissionsJSON) {
        this.permissionsJSON = permissionsJSON;
    }
}
