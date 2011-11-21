/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.fees.view.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.topcoder.accounting.fees.entities.BillingAccount;
import com.topcoder.accounting.fees.entities.ContestFeeDetails;
import com.topcoder.accounting.fees.services.ContestFeeConfigurationException;
import com.topcoder.accounting.fees.services.ContestFeeService;
import com.topcoder.accounting.fees.services.ContestFeeServiceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This struts action is used to display contest fees details for each contest type of a billing account.
 * 
 ** Thread safety: The class is mutable and not thread safe. But it'll not caused thread safety issue if used under
 * Spring container.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public class ContestFeesDetailsAction extends ActionSupport implements Preparable, ModelDriven<BillingAccount> {
    /**
     * Represents the instance of BillingAccount. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private BillingAccount formData;
    /**
     * Represents the BillingAccount identifier It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private long projectId;

    /**
     * Instance of Logger used to perform logging operations. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Log logger;

    /**
     * Instance of ContestFeeService used to perform persistence operations. it is injected through spring IoC. It is
     * managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private ContestFeeService contestFeeService;

    /**
     * Default Constructor.
     */
    public ContestFeesDetailsAction() {
    }

    /**
     * Responsible for displaying a billing account details given project id.
     * 
     * @return success flag.
     * @throws Exception
     *             if there is any exception.
     */

    public String doList() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String projectIdStr = request.getParameter("projectId");
        if (projectIdStr != null && projectIdStr.trim().length() != 0) {
            projectId = Long.parseLong(projectIdStr);
        } else {
            projectId = 0;
        }
        try {
            setFormData(contestFeeService.getBillingAccount(projectId));
        } catch (ContestFeeServiceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        return SUCCESS;
    }

    /**
     * Responsible for save billing account details.
     * 
     * @return success flag.
     * @throws Exception
     *             if there is any exception.
     */
    public String doSave() throws Exception {
        if (getFieldErrors() != null && !getFieldErrors().isEmpty()) {
            return INPUT;
        }
        try {
            for (ContestFeeDetails detail : formData.getContestFees()) {
                contestFeeService.update(detail);
            }
        } catch (ContestFeeServiceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        return SUCCESS;
    }

    /**
     * Implements prepare save method.
     */
    public void prepareSave() {
        try {
            if (formData == null) {
                setFormData(contestFeeService.getBillingAccount(projectId));
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
        }
    }
    /**
     * Implements validate method.
     */
    public void validate() {
        if (getFieldErrors() != null && !getFieldErrors().isEmpty()) {
            Map<String, List<String>> map = getFieldErrors();
            for (String str : map.keySet()) {
                if (str != null && str.startsWith("formData.contestFees[")) {
                    try {
                        String str2 = str.replace("formData.contestFees[", "");
                        int i = Integer.parseInt(""+str2.charAt(0));
                        String description = formData.getContestFees().get(i).getContestTypeDescription();
                        map.get(str).clear();
                        map.get(str).add("Invalid value for " + description +". It should use Double type.");
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.log(Level.ERROR, e);
                    }
                }
            }
        }
    }
    /**
     * Returns the formData field value.
     * 
     * @return formData field value.
     */
    public BillingAccount getFormData() {
        return formData;
    }

    /**
     * Sets the given value to formData field.
     * 
     * @param formData
     *            - the given value to set.
     */
    public void setFormData(BillingAccount formData) {
        this.formData = formData;
    }

    /**
     * Returns the logger field value.
     * 
     * @return logger field value.
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * Sets the corresponding member field
     * 
     * @param loggerName
     *            - the given name to set.
     */
    public void setLoggerName(String loggerName) {
        if (loggerName == null) {
            this.logger = null;
        } else {
            this.logger = LogManager.getLog(loggerName);
        }
    }

    /**
     * Returns the contestFeeService field value.
     * 
     * @return contestFeeService field value.
     */
    public ContestFeeService getContestFeeService() {
        return contestFeeService;
    }

    /**
     * Sets the given value to contestFeeService field.
     * 
     * @param contestFeeService
     *            - the given value to set.
     */
    public void setContestFeeService(ContestFeeService contestFeeService) {
        this.contestFeeService = contestFeeService;
    }

    /**
     * Returns the projectId field value.
     * 
     * @return projectId field value.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the given value to projectId field.
     * 
     * @param projectId
     *            - the given value to set.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Check parameters.
     * 
     * @throws ContestFeeConfigurationException
     *             if this.contestFeeService or this.logger is null
     *
     */
    public void postConstruct() {
        if (contestFeeService == null || logger == null) {
            throw new ContestFeeConfigurationException("The contestFeeService and logger should not be null.");
        }
    }

    /**
     * Implements prepare method.
     */
    public void prepare() throws Exception {
        setFormData(contestFeeService.getBillingAccount(projectId));
    }

    /**
     * Returns the formData field value.
     * 
     * @return formData field value.
     */
    public BillingAccount getModel(){
        try {
            if (formData == null) {
                setFormData(contestFeeService.getBillingAccount(projectId));
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
        }
        return formData;
    }
}
