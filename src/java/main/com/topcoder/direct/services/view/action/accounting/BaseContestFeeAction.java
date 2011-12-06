/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.topcoder.clients.dao.ContestFeeConfigurationException;
import com.topcoder.clients.dao.ProjectContestFeeService;
import com.topcoder.clients.model.BillingAccount;
import com.topcoder.clients.model.ContestType;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>A base class for actions related to contest fees management.</p>
 * 
 * @author isv
 * @version 1.0 (Release Assembly - Project Contest Fees Management Update 1 Assembly)
 */
public abstract class BaseContestFeeAction extends BaseDirectStrutsAction {

    /**
     * Represents the BillingAccount identifier It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private long projectId = -1;

    /**
     * Instance of Logger used to perform logging operations. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Log logger;

    /**
     * Represents the instance of BillingAccount. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private BillingAccount formData;

    /**
     * Instance of ProjectContestFeeService used to perform persistence operations. it is injected through spring IoC. It is
     * managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private ProjectContestFeeService contestFeeService;

    /**
     * <p>Constructs new <code>BaseContestFeeAction</code> instance. This implementation does nothing.</p>
     */
    protected BaseContestFeeAction() {
    }

    /**
     * Returns the contestFeeService field value.
     * 
     * @return contestFeeService field value.
     */
    public ProjectContestFeeService getContestFeeService() {
        return contestFeeService;
    }

    /**
     * Sets the given value to contestFeeService field.
     * 
     * @param contestFeeService
     *            - the given value to set.
     */
    public void setContestFeeService(ProjectContestFeeService contestFeeService) {
        this.contestFeeService = contestFeeService;
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
     * <p>Gets the billing account with contest fees.</p>
     * 
     * @param projectId a <code>long</code> providing the ID of a billing account.
     * @return a <code>BillingAccount</code> referenced by the specified ID. 
     * @throws Exception if an unexpected error occurs.
     * @since 1.1
     */
    protected BillingAccount getBillingAccount(long projectId) throws Exception {
        BillingAccount billingAccount;
        if (projectId > 0) {
            billingAccount = getContestFeeService().getBillingAccount(projectId);
        } else {
            billingAccount = new BillingAccount();
        }
        Map<String, ContestType> contestTypes = DirectUtils.getContesetTypes();
        Set<Integer> contestTypesWithFees = new HashSet<Integer>(); // Holds Ids for contest types which billing
                                                              // account has contest fees set for
        List<ProjectContestFee> contestFees = billingAccount.getContestFees();
        if (contestFees == null) {
            contestFees = new ArrayList<ProjectContestFee>();
            billingAccount.setContestFees(contestFees);
        }

        if (!contestFees.isEmpty()) {
            Iterator<ProjectContestFee> it = contestFees.iterator();
            while (it.hasNext()) {
                ProjectContestFee contestFee = it.next();
                ContestType contestType = contestTypes.get(String.valueOf(contestFee.getContestTypeId()));
                if (contestType == null) {
                    // filter contest-types
                    it.remove();
                } else if (contestType.getDescription() != null) {
                    contestFee.setContestTypeDescription(contestType.getDescription());
                    contestTypesWithFees.add(contestType.getTypeId());
                }
            }
        }
        
        // Check for  possibly newly added contest types which do not have the fees for billing account - create
        // 0 fees for them so they can be edited by the user
        for (ContestType contestType : contestTypes.values()) {
            if (!contestTypesWithFees.contains(contestType.getTypeId())) {
                ProjectContestFee fee = new ProjectContestFee();
                fee.setContestType(contestType.getTypeId());
                fee.setContestTypeDescription(contestType.getDescription());
                fee.setContestFee(0.00);
                fee.setStudio(contestType.isStudio());
                fee.setProjectId(billingAccount.getProjectId());
                contestFees.add(fee);
            }
        }
        return billingAccount;
    }
}
