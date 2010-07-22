/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.cloudvm;

import com.topcoder.direct.cloudvm.service.CloudVMServiceException;
import com.topcoder.direct.services.view.action.contest.launch.ValidationErrorRecord;
import com.topcoder.direct.services.view.action.contest.launch.ValidationErrors;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstance;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.security.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * This action extends the AbstractVMAction, and it is used to launch the VM.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class LaunchVMInstanceAction extends AbstractVMAction {
    /**
     * Represents the ContestServiceFacade object. It has getter & setter. Can be any value.
     */
    private ContestServiceFacade contestServiceFacade;

    /**
     * Represents the UserService object. It has getter & setter. Can be any value.
     */
    private UserService userService;

    /**
     * Represents the contest type id for software contest. It has getter & setter. Can be any value.
     */
    private long softwareContestTypeId;

    /**
     * Represents the contest type id for studio contest. It has getter & setter. Can be any value.
     */
    private long studioContestTypeId;

    /**
     * Represents the contest type id for bug race. It has getter & setter. Can be any value.
     */
    private long bugRaceContestTypeId;

    /**
     * Represents the URL to verify the user has provided security key in profile or not. It has getter & setter. Can be
     * any value.
     */
    private String userSecurityKeyCheckUrl;

    /**
     * Empty constructor.
     */
    public LaunchVMInstanceAction() {
    }

    /**
     * Launch VM instance.
     *
     * @throws Exception from remote services
     */
    protected void executeAction() throws Exception {
        Map<String, String> errors = new HashMap<String, String>();

        TCSubject user = getUser();
        authorize(user);

        HttpServletRequest request = DirectUtils.getServletRequest();
        VMInstance instance = new VMInstance();

        instance.setVmImageId(fetchId(request, "vmImageId", errors));
        instance.setContestId(fetchId(request, "contestId", errors));
        instance.setContestTypeId(fetchId(request, "vmContestTypeId", errors));
        instance.setSvnBranch(fetchString(request, "svnBranch", errors));
        instance.setTcMemberHandle(fetchString(request, "tcHandle", errors));
        instance.setUserData(fetchNullIfEmptyString(request, "userData")); // BUGR-3931

        // validate contest id, if present
        if (!errors.containsKey("contestId")) {
            Object result = null;
            try {
                if (instance.getContestTypeId() == softwareContestTypeId) {
                    result = contestServiceFacade.getSoftwareContestByProjectId(getUser(), instance.getContestId());
                } else if (instance.getContestTypeId() == studioContestTypeId) {
                    result = contestServiceFacade.getContest(getUser(), instance.getContestId());
                } else if (instance.getContestTypeId() == bugRaceContestTypeId) {
                    result = 1;
                    // no check for bug races
                }
            } catch (Exception ex) {
                // ignored, no such contest
                // just log it
                ex.printStackTrace();
            }
            if (result == null) {
                errors.put("contestId",
                    "No contest with such id. Contest type may be wrong or you may have no permission to access contest data.");
            }
        }

        // check member handle
        if (!errors.containsKey("tcHandle")) {
            String userName = "";
            try {
                String[] userNames = instance.getTcMemberHandle().split(";");
                for(int i = 0; i < userNames.length; i++) {
                    userName = userNames[i];
                    userService.getUserId(userName);
                }
            } catch (UserServiceException ex) {
                errors.put("tcHandle", "Unable to find such member: " + userName);
            }
        }
        
        if (!errors.containsKey("userData")) { // BUGR-3931
            if(instance.getUserData() != null) {
                String[] userDataLines = instance.getUserData().split("\n");
                int i = 0;
                for(String userDataLine : userDataLines) {
                    ++i;
                    userDataLine = userDataLine.trim();
                    if(userDataLine.length() == 0) continue;
                    int idx = userDataLine.indexOf('=');
                    if(idx == -1 || idx == 0 || idx == userDataLine.length() - 1) {
                        errors.put("userData", "Line " + i + " should be in key=value format");
                        break;
                    }
                }
            }
        }

        if (!errors.containsKey("tcHandle")) {
            // verify that user has security key
        }
        
        // let's call cloud service
        if (errors.isEmpty()) {
            try {
                setResult(getCloudVMService().launchVMInstance(getUser(), instance));
            } catch (CloudVMServiceException e) {
                String message = "Failed to launch VM.";
                Throwable cause = e.getCause();
                while (cause != null) {
                    message += "<br/>" + cause.getMessage();
                    cause = cause.getCause();
                }
                errors.put("general", message);
            }
        }

        if (!errors.isEmpty()) {
            // there are errors, render them in jsp
            ValidationErrorRecord[] a = new ValidationErrorRecord[errors.size()];
            int i = 0;
            for (String key : errors.keySet()) {
                ValidationErrorRecord record = new ValidationErrorRecord();
                record.setPropertyName(key);
                record.setMessages(new String[] {errors.get(key)});
                a[i++] = record;
            }
            ValidationErrors result = new ValidationErrors();
            result.setErrors(a);

            setResult(result);
            getModel().setData("errors", errors);
        }
    }

    /**
     * Retrieves string parametr.
     *
     * @param request servlet request
     * @param key     parameter key
     * @param errors  errors map
     * @return parameter value
     * @throws IllegalArgumentException if value is null or empty
     */
    private static String fetchString(HttpServletRequest request, String key, Map<String, String> errors) {
        String value = request.getParameter(key);
        if (value == null || value.trim().length() == 0) {
            errors.put(key, "This value is required.");
        }
        return value;
    }

    /**
     * Returns null if string is null or empty
     *
     * @param request servlet request
     * @param key     parameter key
     * @return parameter value
     * @since BUGR-3931
     */
    private static String fetchNullIfEmptyString(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        if(value != null && value.trim().length() == 0) {
            value = null;
        }
        return value;
    }

    /**
     * Retrieves long parametr.
     *
     * @param request servlet request
     * @param key     parameter key
     * @param errors  errors map
     * @return parameter value
     * @throws IllegalArgumentException if value is null or not a number
     */
    private static long fetchId(HttpServletRequest request, String key, Map<String, String> errors) {
        String value = request.getParameter(key);
        if (value == null || value.trim().length() == 0) {
            errors.put(key, "This value is required.");
            return 0;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            errors.put(key, "Valid integer number is expected.");
            return 0;
        }
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return contest service facade
     */
    public ContestServiceFacade getContestServiceFacade() {
        return contestServiceFacade;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param contestServiceFacade contest service facade
     */
    public void setContestServiceFacade(ContestServiceFacade contestServiceFacade) {
        this.contestServiceFacade = contestServiceFacade;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return user service
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param userService user service
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return software contest type id
     */
    public long getSoftwareContestTypeId() {
        return softwareContestTypeId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param softwareContestTypeId software contest type id
     */
    public void setSoftwareContestTypeId(long softwareContestTypeId) {
        this.softwareContestTypeId = softwareContestTypeId;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return studio contest type id
     */
    public long getStudioContestTypeId() {
        return studioContestTypeId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param studioContestTypeId studio contest type id
     */
    public void setStudioContestTypeId(long studioContestTypeId) {
        this.studioContestTypeId = studioContestTypeId;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return user security key check url
     */
    public String getUserSecurityKeyCheckUrl() {
        return userSecurityKeyCheckUrl;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param userSecurityKeyCheckUrl user security key check url
     */
    public void setUserSecurityKeyCheckUrl(String userSecurityKeyCheckUrl) {
        this.userSecurityKeyCheckUrl = userSecurityKeyCheckUrl;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return bug race contest type id
     */
    public long getBugRaceContestTypeId() {
        return bugRaceContestTypeId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param bugRaceContestTypeId bug race contest type id
     */
    public void setBugRaceContestTypeId(long bugRaceContestTypeId) {
        this.bugRaceContestTypeId = bugRaceContestTypeId;
    }
}

