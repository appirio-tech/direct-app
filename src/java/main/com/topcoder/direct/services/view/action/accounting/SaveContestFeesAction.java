/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.opensymphony.xwork2.Preparable;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.util.log.Level;

import java.util.List;
import java.util.Map;

/**
 * <p>An action to be used for servicing the requests for updating the existing contest fees for selected billing
 * account.</p>
 * 
 * @author isv
 * @version 1.0 (Release Assembly - Project Contest Fees Management Update 1 Assembly)
 */
public class SaveContestFeesAction extends BaseContestFeeAction implements Preparable {

    /**
     * <p>Constructs new <code>SaveContestFeesAction</code> instance. This implementation does nothing.</p>
     */
    public SaveContestFeesAction() {
    }

    /**
     * <p>Handles the incoming request.</p>
     * 
     * @return a <code>String</code> referencing the next view to be displayed to user.
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    public String execute() throws Exception {
        if (getFieldErrors() != null && !getFieldErrors().isEmpty()) {
            return INPUT;
        } else {
            return super.execute();
        }
    }

    /**
     * Implements validate method.
     */
    public void validate() {
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
                        map.get(str)
                            .add("Invalid value for " + description + ". It should use non-negative Double type.");
                    } catch (Exception e) {
                        getLogger().log(Level.ERROR, e);
                    }
                }
            }
        }
        // Validate against negative fees
        List<ProjectContestFee> contestFees = getFormData().getContestFees();
        for (int i = 0; i < contestFees.size(); i++) {
            ProjectContestFee fee = contestFees.get(i);
            if (fee.getContestFee() < 0) {
                addFieldError("formData.contestFees[" + i + "].fee", "Value for " + fee.getContestTypeDescription()
                                                                     + " is negative");
            }
        }
    }

    /**
     * Implements prepare save method.
     */
    public void prepare() {
        try {
            if (getFormData() == null) {
                setFormData(getBillingAccount(getProjectId()));
            }
        } catch (Exception e) {
            getLogger().log(Level.ERROR, e);
        }
    }


    @Override
    protected void executeAction() throws Exception {
        // clean up the old contest fees first
        getContestFeeService().cleanUpContestFees(getProjectId());
        // create new ones
        getContestFeeService().save(getFormData().getContestFees());
    }
}
