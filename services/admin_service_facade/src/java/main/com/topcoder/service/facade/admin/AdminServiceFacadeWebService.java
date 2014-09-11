/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.admin;

import java.util.List;

import javax.jws.WebService;

import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;

/**
 * <p>
 * This service provides some admin related service such as manage
 * contest fees for each project.
 * </p>
 * <p>
 * This interface is a copy of the old AdminServiceFacade interface. AdminServiceFacade is no longer a web service point.
 * The security part is covered in this new web-service component. This bean's methods create this TCSubject instance by
 * do the login with LoginBean class and simply call the corresponding AdminServiceFacade method. This web service must
 * now be used instead of AdminServiceFacade by web service clients.
 * </p>
 * <p>
 * So the old AdminServiceFacade will accepts a more parameter: TCSubject from this new-facade. AuthenticationException
 * and GeneralSecurityException from login now will be directly propagated.
 * </p>
 * <p>
 * Thread-safe: the Implementation is required to be thread-safe.
 * </p>
 *
 * @author waits
 * @since Cockpit Security Facade V1.0
 * @version 1.0
 */
@WebService(name = "AdminServiceFacade")
public interface AdminServiceFacadeWebService {
    /**
     * Gets all contest fees by project id.
     *
     * @param projectId the project id
     * @return the list of project contest fees for the given project id
     * @throws AdminServiceFacadeException if any error occurs during the service call
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<ProjectContestFee> getContestFeesByProject(long projectId) throws AdminServiceFacadeException;

    /**
     * Saves contest fees. It will refresh contest fees for the given project.
     *
     * @param contestFees the contest fees
     * @param projectId the project id
     * @throws AdminServiceFacadeException if any error occurs during the service call
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public void saveContestFees(List<ProjectContestFee> contestFees, long projectId)
            throws AdminServiceFacadeException;

    /**
     * Searches projects by project name. The name search is case insensitive and also allows for partial name search.
     * The name doesn't allow wildcard characters: *, %. If it is null or empty, all projects will be returned.
     *
     * @param projectName the project name
     * @return projects matched with the project name
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws AdminServiceFacadeException if any error occurs during the service call
     */
    public List<Project> searchProjectsByProjectName(String projectName) throws AdminServiceFacadeException;

    /**
     * Searches projects by client name. The name search is case insensitive and also allows for partial name search.
     * The name doesn't allow wildcard characters: *, %. If it is null or empty, all projects will be returned.
     *
     * @param clientName the client name
     * @return projects matched with the client name
     * @throws AdminServiceFacadeException if any error occurs during the service call
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<Project> searchProjectsByClientName(String clientName) throws AdminServiceFacadeException;
}
