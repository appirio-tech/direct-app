/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import java.util.Date;
import java.util.List;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.dao.LookupDAO;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectOperationDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectOperationType;
import com.topcoder.service.user.UserServiceException;

/**
 * <p>
 * A <code>Struts</code> action used for handling udpate copilot project
 * request.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since TC Direct Manage Copilots Assembly
 */
public class CopilotProjectAction extends BaseDirectStrutsAction {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 3974173577558839247L;
    
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

        // set result
        setResult(copilotProjectOperations);
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
}
