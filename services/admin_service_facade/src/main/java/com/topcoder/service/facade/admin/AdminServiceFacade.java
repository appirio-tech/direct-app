/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.admin;

import java.util.List;

import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * This interface defines the admin service facade. This service provides some admin related service such as manage
 * contest fees for each project.
 * </p>
 * <p>
 * Changes in v1.0.1(Cockpit Security Facade V1.0): - It is not a web-service facade any more. - All the methods accepts
 * a parameter TCSubject which contains all the security info for current user. The implementation EJB should use
 * TCSubject and now get these info from the sessionContext. - Please use the new AdminServiceFacadeWebService as the
 * facade now. That interface will delegates all the methods to this interface.
 * </p>
 *
 * @author waits
 * @version 1.0.1
 * @since Configurable Contest Fees v1.0 Assembly
 */
public interface AdminServiceFacade {
    /**
     * Gets all contest fees by project id. *
     * <p>
     * Update in v1.0.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project id
     * @return the list of project contest fees for the given project id
     * @throws AdminServiceFacadeException if any error occurs during the service call
     */
    public List<ProjectContestFee> getContestFeesByProject(TCSubject tcSubject, long projectId)
            throws AdminServiceFacadeException;

    /**
     * Saves contest fees. It will refresh contest fees for the given project. *
     * <p>
     * Update in v1.0.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestFees the contest fees
     * @param projectId the project id
     * @throws AdminServiceFacadeException if any error occurs during the service call
     */
    public void saveContestFees(TCSubject tcSubject, List<ProjectContestFee> contestFees, long projectId)
            throws AdminServiceFacadeException;

    /**
     * Searches projects by project name. The name search is case insensitive and also allows for partial name search.
     * The name doesn't allow wildcard characters: *, %. If it is null or empty, all projects will be returned. *
     * <p>
     * Update in v1.0.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectName the project name
     * @return projects matched with the project name
     * @throws AdminServiceFacadeException if any error occurs during the service call
     */
    public List<Project> searchProjectsByProjectName(TCSubject tcSubject, String projectName)
            throws AdminServiceFacadeException;

    /**
     * Searches projects by client name. The name search is case insensitive and also allows for partial name search.
     * The name doesn't allow wildcard characters: *, %. If it is null or empty, all projects will be returned. *
     * <p>
     * Update in v1.0.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param clientName the client name
     * @return projects matched with the client name
     * @throws AdminServiceFacadeException if any error occurs during the service call
     */
    public List<Project> searchProjectsByClientName(TCSubject tcSubject, String clientName)
            throws AdminServiceFacadeException;
}
