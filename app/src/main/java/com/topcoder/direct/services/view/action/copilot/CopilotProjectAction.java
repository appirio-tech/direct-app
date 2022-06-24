/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectOperationDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectOperationType;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * A <code>Struts</code> action used for handling udpate copilot project
 * request.
 * </p>
 * 
 * <p>
 * Version 1.0.1 (TC Direct - Permission Updates) Change notes:
 *   <ol>
 *     <li>Updated executeAction() method.</li>
 *   </ol>
 * </p>
 * 
 * @author tangzx
 * @version 1.0.1
 * @since TC Direct Manage Copilots Assembly
 */
public class CopilotProjectAction extends BaseDirectStrutsAction {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 3974173577558839247L;

    /**
     * The list of copilot project operations.
     */
    private List<CopilotProjectOperationDTO> copilotProjectOperations;

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
        List<CopilotProject> copilotProjects = new ArrayList<CopilotProject>();
        List<Boolean> removeFlags = new ArrayList<Boolean>();
        
        for (CopilotProjectOperationDTO operation : copilotProjectOperations) {
            if (operation.getOperation() == CopilotProjectOperationType.REMOVE) {
                removeFlags.add(true);
            } else if (operation.getOperation() == CopilotProjectOperationType.ADD) {
                removeFlags.add(false);
            }
            
            CopilotProject copilotProject = new CopilotProject();
            copilotProject.setTcDirectProjectId(operation.getProjectId());
            copilotProject.setCopilotProfileId(operation.getCopilotProfileId());
            copilotProject.setId(operation.getCopilotProjectId());
            copilotProjects.add(copilotProject);
        }
        
        // retrieve current user
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        
        // update copilots projects
        List<CopilotProject> cProjects = getContestServiceFacade()
        .updateCopilotProjects(currentUser, copilotProjects,
                removeFlags);
        
        for (int i = 0; i < removeFlags.size(); i++) {
            if (!removeFlags.get(i)) {
                CopilotProjectOperationDTO operation = copilotProjectOperations
                        .get(i);
                CopilotProject copilotProject = cProjects.get(i);

                operation.setCopilotProjectId(copilotProject.getId());
                operation.setCopilotType(copilotProject.getCopilotType()
                        .getName());
            }
        }

        // set result
        setResult(copilotProjectOperations);
    }
}
