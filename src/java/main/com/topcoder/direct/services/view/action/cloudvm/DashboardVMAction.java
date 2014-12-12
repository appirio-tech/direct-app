/*
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.cloudvm;

import com.topcoder.direct.cloudvm.service.CloudVMService;
import com.topcoder.direct.services.view.action.contest.launch.AbstractAction;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.cloudvm.VMContestType;
import com.topcoder.direct.services.view.dto.cloudvm.VMImage;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceData;
import com.topcoder.direct.services.view.dto.cloudvm.VMUsage;
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
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Direct VM Instances Management) changes:
 * - added contestId so that the action can receive a pre-populated contestId
 * </p>
 *
 * <p>
 * Version 1.2 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 * - Removes the statements to populate the right sidebar direct projects and project contests. It's changed to
 * load these data via ajax instead after the page finishes loading.
 * </p>
 *
 * @author Standlove, jiajizhou86, Veve
 * @version 1.2
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
     * List of vm usages.
     */
    private List<VMUsage> vmUsages;

    /**
     * List of current vm instances.
     */
    private List<VMInstanceData> vmInstances;

    /**
     * Whether current user is admin or not.
     */
    private boolean admin;

    /**
     * The pre-populated contest id.
     * @since 1.1
     */
    private long contestId;

    /**
     * <p> This is the template method where the action logic will be performed by children classes. </p>
     *
     * @throws Exception if any error occurs
     */
    public String execute() throws Exception {
        String messages = "";
        
        TCSubject user = AbstractVMAction.getUser();
        AbstractVMAction.authorize(user);
        vmImages = getCloudVMService().getVMImages(user);
        vmContestTypes = getCloudVMService().getVMContestTypes(user);
        vmUsages = getCloudVMService().getVMUsages(user);
        
        // try {
            vmInstances = getCloudVMService().getVMInstances(user);
        // } catch (Exception ex) { 
        //     vmInstances = new ArrayList<VMInstanceData>(); // BUGR-3930
        // }
        admin = inRole(user, "Administrator");

        HttpServletRequest request = DirectUtils.getServletRequest();
        request.setAttribute("messages", messages);
        HttpSession session = request.getSession(false);

        if (request.getParameterMap().containsKey("contestId")) {
            try {
                contestId = Long.valueOf(request.getParameter("contestId"));
            } catch (Exception ex) {
                // ignore such number conversion exception
                contestId = 0;
            }
        } else {
            contestId = 0;
        }

        if (session != null) {
            sessionData = new SessionData(session);
        }

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
     * Returns list of usages.
     *
     * @return list of usages
     */
    public List<VMUsage> getVmUsages() {
        return vmUsages;
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

    /**
     * Gets the pre-populated contest id.
     * @return the contest id.
     * @since 1.1
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the pre-populated contest id.
     * @param contestId the contest id.
     * @since 1.1
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }
}
