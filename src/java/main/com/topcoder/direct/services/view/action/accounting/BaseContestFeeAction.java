/*
 * Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.topcoder.clients.dao.ContestFeeConfigurationException;
import com.topcoder.clients.dao.ProjectContestFeePercentageService;
import com.topcoder.clients.dao.ProjectContestFeeService;
import com.topcoder.clients.model.BillingAccount;
import com.topcoder.clients.model.ContestType;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.ProjectContestFeePercentage;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * A base class for actions related to contest fees management.
 * </p>
 * <p>
 * Versions 1.1 (Module Assembly - Contest Fee Based on % of Member Cost Admin Part)
 * <ol>
 * <li>Added contestFeePercentageService and its getter/setter.</li>
 * <li>Updated getBillingAccount to set the contest fee percentage information.</li>
 * <li>Added validateFormData to validate form data when creating/updating contest fees.</li>
 * <ol>
 * </p>
 *
 * <p>
 *     Version 1.2 (Release Assembly - TC Cockpit Bug Race Cost and Fees Part 1) change notes:
 *     <ol>
 *         <li>Added {@link #directProjectService} field, also the getter/setter were added.</li>
 *         <li>Updated {@link #getBillingAccount(long)} method to add the fake Bug Race to the contest types.</li>
 *     </ol>
 * </p>
 *
 * <p>
 * Changes in version 1.3 (BUGR-10395 Cockpit Fixed Contest Fee Not Saved):
 * <ol>
 * 		<li>Add {@link #populateContestFeeData(List<ProjectContestFee> contestFees, long projectId)} method.</li>
 * </ol>
 * </p>
 * @author isv, minhu, TCSASSEMBLER
 * @version 1.3
 */
public abstract class BaseContestFeeAction extends BaseDirectStrutsAction {
    /**
     * The contest fee percentage form input name.
     * 
     * @since 1.1
     */
    private static final String FORMDATA_CONTEST_FEE_PERCENTAGE = "formData.contestFeePercentage";

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
     * Instance of ProjectContestFeeService used to perform persistence operations. it is injected through spring IoC.
     * It is
     * managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private ProjectContestFeeService contestFeeService;

    /**
     * Instance of ProjectContestFeeService used to perform persistence operations. it is injected through spring IoC.
     * It is
     * managed with a getter and setter. It may have any value. It is fully mutable.
     * 
     * @since 1.1
     */
    private ProjectContestFeePercentageService contestFeePercentageService;
    /**
     * <p>The instance of direct project service interface. It will be injected by Spring framework.</p>
     *
     * @since 1.2
     */
    private DirectProjectService directProjectService;

    /**
     * <p>
     * Constructs new <code>BaseContestFeeAction</code> instance. This implementation does nothing.
     * </p>
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
     *        - the given value to set.
     */
    public void setContestFeeService(ProjectContestFeeService contestFeeService) {
        this.contestFeeService = contestFeeService;
    }

    /**
     * <p>Gets the direct project service interface.</p>
     *
     * @return the direct project service interface.
     * @since 1.2
     */
    public DirectProjectService getDirectProjectService() {
        return directProjectService;
    }

    /**
     * <p>Sets the direct project service interface.</p>
     *
     * @param directProjectService the direct project service interface.
     * @since 1.2
     */
    public void setDirectProjectService(DirectProjectService directProjectService) {
        this.directProjectService = directProjectService;
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
     *        - the given value to set.
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
     *        - the given value to set.
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
     *        - the given name to set.
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
     *         if this.contestFeeService or this.logger is null
     */
    public void postConstruct() {
        if (contestFeeService == null || logger == null || contestFeePercentageService == null) {
            throw new ContestFeeConfigurationException(
                "The contestFeeService, contestFeePercentageService and logger should not be null.");
        }
    }

    /**
     * <p>
     * Gets the billing account with contest fees.
     * </p>
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

            // set the percentage info
            ProjectContestFeePercentage percentage =
                getContestFeePercentageService().getByProjectId(projectId);
            if (percentage == null || !percentage.isActive()) {
                billingAccount.setContestFeeFixed(true);                
            } else {
                billingAccount.setContestFeeFixed(false);
                billingAccount.setContestFeePercentage(percentage.getContestFeePercentage());
            }
        } else {
            billingAccount = new BillingAccount();
        }
        Map<String, ContestType> contestTypes = DirectUtils.getContesetTypes();
        // add the fake bug race contest type
        ContestType bugrContestType = new ContestType();
        bugrContestType.setDescription("Bug Race");
        bugrContestType.setTypeId((int) DirectUtils.BUGR_CONTEST_TYPE_ID);
        contestTypes.put(String.valueOf(DirectUtils.BUGR_CONTEST_TYPE_ID), bugrContestType);

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

        // Check for possibly newly added contest types which do not have the fees for billing account - create
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

    /**
     * Sets the contest fee percentage service.
     * 
     * @param contestFeePercentageService the contest fee percentage service to set
     * @since 1.1
     */
    public void setContestFeePercentageService(ProjectContestFeePercentageService contestFeePercentageService) {
        this.contestFeePercentageService = contestFeePercentageService;
    }

    /**
     * Gets the contest fee percentage service.
     * 
     * @return the contest fee percentage service
     * @since 1.1
     */
    public ProjectContestFeePercentageService getContestFeePercentageService() {
        return contestFeePercentageService;
    }
    
    /**
     * Validates the form data for contest fees.
     * 
     * @since 1.1
     */
    protected void validateFormData() {
        Map<String, List<String>> map = getFieldErrors();
        if (getFieldErrors() != null && !getFieldErrors().isEmpty()) {
            for (String str : map.keySet()) {
                if (str != null && str.startsWith("formData.contestFees[")) {
                    try {
                        String str2 = str.replace("formData.contestFees[", "");
                        int pos = str2.indexOf("]");
                        int i = Integer.parseInt(str2.substring(0, pos));
                        String description = getFormData().getContestFees().get(i).getContestTypeDescription();
                        map.get(str).clear();
                        map.get(str).add(
                            "Invalid value for " + description + ". It should use non-negative Double type.");
                    } catch (Exception e) {
                        getLogger().log(Level.ERROR, e);
                    }
                } else if (str != null && str.equals(FORMDATA_CONTEST_FEE_PERCENTAGE)) {
                    map.get(str).clear();
                    if (!getFormData().isContestFeeFixed()) {
                        map.get(str).add(
                            "Invalid value for contest fee percentage. It should use non-negative Double type.");
                    }
                }
            }
        }
        // Validate against negative fees
        List<ProjectContestFee> contestFees = getFormData().getContestFees();
        for (int i = 0; i < contestFees.size(); i++) {
            ProjectContestFee fee = contestFees.get(i);
            if (fee.getContestFee() < 0) {
                addFieldError("formData.contestFees[" + i + "].fee",
                    "Value for " + fee.getContestTypeDescription() + " is negative");
            }
        }
        
        // check percentage value
        double percentage = getFormData().getContestFeePercentage(); 
        if (!getFormData().isContestFeeFixed() && (percentage < 0 || percentage > 2)) {
            addFieldError(FORMDATA_CONTEST_FEE_PERCENTAGE,
                "Value for contest fee percentage should be in [0, 2.0].");
        } else if (map.get(FORMDATA_CONTEST_FEE_PERCENTAGE) != null &&
                        map.get(FORMDATA_CONTEST_FEE_PERCENTAGE).isEmpty()) {
            map = getFieldErrors();
            map.remove(FORMDATA_CONTEST_FEE_PERCENTAGE);
            setFieldErrors(map);
        }
    }
    /**
     * <p>
     * populate the contest fee with project and is_studio data.
     * </p>
     * @param contestFees the contest fee list.
     * @param projectId the project id.
     * @throws Exception if an unexpected error occurs.
     * @since 1.3
     */
    protected void populateContestFeeData(List<ProjectContestFee> contestFees, long projectId) throws Exception {
    	if (contestFees != null) {
    		Map<String, ContestType> contestTypes = DirectUtils.getContesetTypes();
    		for(ProjectContestFee fee : contestFees) {
    			ContestType contestType = contestTypes.get(String.valueOf(fee.getContestTypeId()));
    			if (contestType != null) {
    				fee.setStudio(contestType.isStudio());
    			}
    			fee.setProjectId(projectId);
    		}
    	}
    }
}
