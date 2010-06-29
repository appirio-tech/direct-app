/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.cloudvm;

import com.topcoder.direct.cloudvm.service.CloudVMService;
import com.topcoder.direct.services.view.action.contest.launch.AbstractAction;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.cloudvm.VMContestType;
import com.topcoder.direct.services.view.dto.cloudvm.VMImage;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceData;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * Action supporting vm management page.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class DashboardVMAction extends AbstractAction {

    private CommonDTO viewData =  new CommonDTO();

    private SessionData sessionData;

    /**
     * Represents the CloudVMService to manage the VM. It has getter & setter. Can be any value.
     */
    private CloudVMService cloudVMService;

    /**
     * List of vm images.
     */
    private List<VMImage> vmImages;

    /**
     * List of contest types.
     */
    private List<VMContestType> vmContestTypes;

    /**
     * List of current vm instances.
     */
    private List<VMInstanceData> vmInstances;

    /**
     * Whether current user is admin or not.
     */
    private boolean admin;

    /**
     * <p> This is the template method where the action logic will be performed by children classes. </p>
     *
     * @throws Exception if any error occurs
     */
    public String execute() throws Exception {
        TCSubject user = AbstractVMAction.getUser();
        AbstractVMAction.authorize(user);
        vmImages = getCloudVMService().getVMImages(user);
        vmContestTypes = getCloudVMService().getVMContestTypes(user);
        vmInstances = getCloudVMService().getVMInstances(user);
        admin = inRole(user, "Administrator");

        HttpServletRequest request = DirectUtils.getServletRequest();

        HttpSession session = request.getSession(false);

        if (session != null) {
            sessionData = new SessionData(session);
        }

        List<ProjectBriefDTO> projects = DataProvider.getUserProjects(sessionData.getCurrentUserId());

        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        viewData.setUserProjects(userProjectsDTO);

        return SUCCESS;
    }

    /**
     * Returns list of vm images.
     *
     * @return list of vm images
     */
    public List<VMImage> getVmImages() {
        return vmImages;
    }

    /**
     * Returns list of contest types.
     *
     * @return list of contest types
     */
    public List<VMContestType> getVmContestTypes() {
        return vmContestTypes;
    }

    /**
     * Returns list of vm instances.
     *
     * @return list of vm instances
     */
    public List<VMInstanceData> getVmInstances() {
        return vmInstances;
    }


    /**
     * Getter for cloud vm service.
     *
     * @return service referene
     */
    public CloudVMService getCloudVMService() {
        return cloudVMService;
    }

    /**
     * Setter for cloud vm service.
     *
     * @param cloudVMService value to set
     */
    public void setCloudVMService(CloudVMService cloudVMService) {
        this.cloudVMService = cloudVMService;
    }

    /**
     * Tells whether user is admin or not.
     *
     * @return true if admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Checks if the user has given role.
     *
     * @param tcSubject TCSubject instance for user
     * @param roleName  role name
     * @return whether user has such role
     */
    public static boolean inRole(TCSubject tcSubject, String roleName) {
        Set<RolePrincipal> roles = tcSubject.getPrincipals();
        if (roles != null) {
            for (RolePrincipal role : roles) {
                if (role.getName().equalsIgnoreCase(roleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isApplicable() {
        TCSubject user = AbstractVMAction.getUser();
        return inRole(user, "Administrator") || inRole(user, "VMManager");
    }

     public CommonDTO getViewData() {
        return viewData;
    }

    public SessionData getSessionData() {
        return sessionData;
    }
}
