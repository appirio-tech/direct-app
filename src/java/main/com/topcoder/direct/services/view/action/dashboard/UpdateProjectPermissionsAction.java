/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.security.TCSubject;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.ProjectPermission;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * <p>
 *     Version 1.2 (Release Assembly - TC Direct Cockpit Release One) update notes:
 *     <li>
 *         change UpdateProjectPermissionsAction to be ajax styled
 *     </li>
 * </p>
 *
 * <p>
 *     Version 1.3 (Release Assembly - TC Direct Cockpit Release Four)
 *     <li>
 *         update executeAction() to fix the issue that cannot remove read permission for the user.
 *     </li>
 * </p>
 *
 * @author isv, GreatKevin
 * @version 1.3
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
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        // build the return json result
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

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

            List<Permission> userProjectPermissions = getPermissionServiceFacade().getPermissions(tcSubject, permission.getUserId(), permission.getProjectId());

            boolean add = true;
            long permissionTypeId;

            if (permission.getPermission().toLowerCase().equals("full")) {
                permissionTypeId = 3;
            } else if (permission.getPermission().toLowerCase().equals("write")) {
                permissionTypeId = 2;
            } else if (permission.getPermission().toLowerCase().equals("read")) {
                permissionTypeId = 1;
            } else {
                // remove
                permissionTypeId = -1;
            }

            for (Permission upp : userProjectPermissions) {
                if (upp.getPermissionType().getPermissionTypeId() == permissionTypeId) {
                    add = false;
                    break;
                }

                permission.setUserPermissionId(upp.getPermissionId());
            }

            if (add) {
                permissions.add(permission);
            }


        }

        getPermissionServiceFacade().updateProjectPermissions(tcSubject, permissions, ResourceRole.RESOURCE_ROLE_OBSERVER_ID);


        for(ProjectPermission p : permissions) {
            Map<String, String> resultItem = new HashMap<String, String>();
            resultItem.put("projectId", String.valueOf(p.getProjectId()));
            resultItem.put("userId", String.valueOf(p.getUserId()));
            resultItem.put("permission", p.getPermission());
            resultItem.put("permissionId", String.valueOf(p.getUserPermissionId()));
            
            if(p.getUserId() == tcSubject.getUserId() && p.getPermission().trim().equals("")) {
                resultItem.put("removeTotalProject", "true");
            } else {
                resultItem.put("removeTotalProject", "false");
            }

            result.add(resultItem);
        }

        setResult(result);
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
