/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.dao.LookupDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectOperationDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectOperationType;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.security.TCSubject;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.ProjectPermission;
import com.topcoder.service.user.UserServiceException;

/**
 * <p>
 * A <code>Struts</code> action used for handling udpate copilot project
 * request.
 * </p>
 * 
 * <p>
 * Version 1.0.1 (TC Direct - Permission Updates) Change notes:
 *   <ol>
 *     <li>Added COPILOT_PERMISSION constant.</li>
 *     <li>Added copilotProfileDAO field and corresponding get/set methods.</li>
 *     <li>Added updateProjectPermissions() method.</li>
 *     <li>Updated executeAction() method.</li>
 *   </ol>
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0.1
 * @since TC Direct Manage Copilots Assembly
 */
public class CopilotProjectAction extends BaseDirectStrutsAction {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 3974173577558839247L;
    
    /**
     * Represented the permission to assign to copilot.
     * 
     * @since 1.0.1
     */
    private static final String COPILOT_PERMISSION = "full";
    
    /**
     * The lookup DAO.
     */
    private LookupDAO lookupDAO;

    /**
     * The list of copilot project operations.
     */
    private List<CopilotProjectOperationDTO> copilotProjectOperations;

    /**
     * The copilot project DAO.
     */
    private CopilotProjectDAO copilotProjectDAO;
    
    /**
     * The copilot profile DAO.
	 *
	 * @since 1.0.1
     */	
    private CopilotProfileDAO copilotProfileDAO;

    /**
     * Retrieves the copilotProjectDAO field.
     * 
     * @return the copilotProjectDAO
     */
    public CopilotProjectDAO getCopilotProjectDAO() {
        return copilotProjectDAO;
    }

    /**
     * Sets the copilotProjectDAO field.
     * 
     * @param copilotProjectDAO
     *            the copilotProjectDAO to set
     */
    public void setCopilotProjectDAO(CopilotProjectDAO copilotProjectDAO) {
        this.copilotProjectDAO = copilotProjectDAO;
    }

    /**
     * Retrieves the copilotProjectOperations field.
     * 
     * @return the copilotProjectOperations
     */
    public List<CopilotProjectOperationDTO> getCopilotProjectOperations() {
        return copilotProjectOperations;
    }

    /**
     * Sets the copilotProjectOperations field.
     * 
     * @param copilotProjectOperations
     *            the copilotProjectOperations to set
     */
    public void setCopilotProjectOperations(
            List<CopilotProjectOperationDTO> copilotProjectOperations) {
        this.copilotProjectOperations = copilotProjectOperations;
    }

    /**
     * <p>
     * Handles the incoming request. Update copilot projects.
     * </p>
     * 
     * @throws Exception
     *             if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        for (CopilotProjectOperationDTO operation : copilotProjectOperations) {
            if (operation.getOperation() == CopilotProjectOperationType.REMOVE) {
                copilotProjectDAO.delete(operation.getCopilotProjectId());
            } else if (operation.getOperation() == CopilotProjectOperationType.ADD) {
                insertCopilotProject(operation);
            }
        }

        // update project permissions
        updateProjectPermissions();
        
        // set result
        setResult(copilotProjectOperations);
    }
    
    /**
     * Update project permissions.
     * 
     * @throws Exception if any exception occurs
     * @since 1.0.1
     */
    private void updateProjectPermissions() throws Exception {
        // retrieve current user
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        
        // retrieve user permissions
        List<ProjectPermission> permissions = getContestServiceFacade()
                .getProjectPermissions(currentUser);
        Map<Long, Map<Long, Long>> userPermissionMaps = new HashMap<Long, Map<Long, Long>>();
        for (ProjectPermission permission : permissions) {
            if (!userPermissionMaps.containsKey(permission.getProjectId())) {
                userPermissionMaps.put(permission.getProjectId(),
                        new HashMap<Long, Long>());
            }
            userPermissionMaps.get(permission.getProjectId()).put(
                    permission.getUserId(), permission.getUserPermissionId());
        }
        
        // generate project permissions
        Map<Long, String> projectNames = new HashMap<Long, String>();
        List<ProjectPermission> permissionsToAdd = new ArrayList<ProjectPermission>();
        for (CopilotProjectOperationDTO operation : copilotProjectOperations) {
            CopilotProfile copilotProfile = copilotProfileDAO.retrieve(operation.getCopilotProfileId());   
            long userId = copilotProfile.getUserId();
            
            if (operation.getOperation() == CopilotProjectOperationType.REMOVE) {
                if (userPermissionMaps.containsKey(operation.getProjectId())
                        && userPermissionMaps.get(operation.getProjectId())
                                .containsKey(userId)) {
                    ProjectPermission permission = new ProjectPermission();
                    permission.setPermission("");
                    permission.setProjectId(operation.getProjectId());
                    permission.setUserId(userId);
                    permission.setUserPermissionId(userPermissionMaps.get(operation.getProjectId()).get(userId));
                    permission.setStudio(false);
                    
                    permissionsToAdd.add(permission);
                } else {
                    // ignore, the copilot has no permission on this project
                }
            } else if (operation.getOperation() == CopilotProjectOperationType.ADD) {
                ProjectPermission permission = new ProjectPermission();
                permission.setPermission(COPILOT_PERMISSION);
                permission.setProjectId(operation.getProjectId());
                permission.setUserId(userId);
                permission.setStudio(false);
                permission.setHandle(getUserService().getUserHandle(userId));
                
                // set project name
                if (!projectNames.containsKey(operation.getProjectId())) {
                    projectNames.put(operation.getProjectId(),
                            getProjectServiceFacade().getProject(currentUser,
                                    operation.getProjectId()).getName());
                }
                permission.setProjectName(projectNames.get(operation.getProjectId()));

                if (userPermissionMaps.containsKey(operation.getProjectId())
                        && userPermissionMaps.get(operation.getProjectId())
                        .containsKey(userId)) {
                    // update permission
                    permission.setUserPermissionId(userPermissionMaps.get(
                            operation.getProjectId()).get(userId));
                } else {
                    // add permission
                    permission.setUserPermissionId(-1L);
                }
                
                permissionsToAdd.add(permission);
            }
        }

        // update project permissions
        getContestServiceFacade().updateProjectPermissions(currentUser,
                permissionsToAdd, ResourceRole.RESOURCE_ROLE_OBSERVER_ID);
    }

    /**
     * Insert a copilot project record.
     * 
     * @param operation
     *            the operation
     * @throws UserServiceException
     *             if any exception occurs when retireving user handle
     * @throws CopilotDAOException
     *             if any exception occurs when performing DB operation
     */
    private void insertCopilotProject(CopilotProjectOperationDTO operation)
            throws UserServiceException, CopilotDAOException {
        CopilotProject copilotProject = new CopilotProject();

        // populate actual values
        copilotProject.setTcDirectProjectId(operation.getProjectId());
        copilotProject.setCopilotProfileId(operation.getCopilotProfileId());
        copilotProject.setCreateUser("" + DirectStrutsActionsHelper.getTCSubjectFromSession()
                .getUserId());
        copilotProject.setCreateDate(new Date());
        copilotProject.setModifyUser(copilotProject.getCreateUser());
        copilotProject.setModifyDate(new Date());

        // populate copilot type
        for (CopilotType copilotType : lookupDAO.getAllCopilotTypes()) {
            if (copilotType.getId() == 1L) {
                copilotProject.setCopilotType(copilotType);
            }
        }
        for (CopilotProjectStatus copilotProjectStatus : lookupDAO.getAllCopilotProjectStatuses()) {
            if (copilotProjectStatus.getId() == 1L) {
                copilotProject.setStatus(copilotProjectStatus);
            }
        }
        copilotProject.setPrivateProject(false);

        // insert into DB
        copilotProjectDAO.create(copilotProject);

        // set copilot project id
        operation.setCopilotProjectId(copilotProject.getId());
        operation.setCopilotType(copilotProject.getCopilotType().getName());
    }

    /**
     * Retrieves the lookupDAO field.
     *
     * @return the lookupDAO
     */
    public LookupDAO getLookupDAO() {
        return lookupDAO;
    }

    /**
     * Sets the lookupDAO field.
     *
     * @param lookupDAO the lookupDAO to set
     */
    public void setLookupDAO(LookupDAO lookupDAO) {
        this.lookupDAO = lookupDAO;
    }

    /**
     * Gets the copilot profile DAO.
     * 
     * @return the copilotProfileDAO
     * @since 1.0.1
     */
    public CopilotProfileDAO getCopilotProfileDAO() {
        return copilotProfileDAO;
    }

    /**
     * Sets the copilot profile DAO.
     * 
     * @param copilotProfileDAO the copilotProfileDAO to set
     * @since 1.0.1
     */
    public void setCopilotProfileDAO(CopilotProfileDAO copilotProfileDAO) {
        this.copilotProfileDAO = copilotProfileDAO;
    }
}
