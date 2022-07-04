/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.user;

import javax.jws.WebService;


/**
 * <p>
 * This interface defines the Jira & Confluence back end service. This service provides mechanism to create users in
 * both Jira & Confluence.
 * </p>
 *
 * <p>
 * This interface is a copy of the old UserServiceFacade interface. UserServiceFacade is no longer a web service point.
 * The security part is covered in this new web-service component. This bean's methods create this TCSubject instance by
 * do the login with LoginBean class and simply call the corresponding UserServiceFacade method. This web service must
 * now be used instead of UserServiceFacade by web service clients.
 * </p>
 *
 * <p>
 * So the old UserServiceFacade will accepts a more parameter: TCSubject from this new-facade. AuthenticationException
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
@WebService(name = "UserServiceFacade")
public interface UserServiceFacadeWebService {
    /**
     * <p>
     * Creates Jira User (if does not exist already) and gets the email address of it from the Jira Service.
     * Implementation should create the Jira user if the user does not exist already.
     * </p>
     *
     * @param handle the user handle for which to retrieve the email address from Jira Service.
     * @return the email address of the Jira user for the given handle.
     * @throws UserServiceException if any error occurs when getting user details.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public String getJiraUser(String handle) throws UserServiceFacadeException;

    /**
     * <p>
     * Creates Confluence User (if does not exist already) and gets the email address of it from the Confluence Service.
     * Implementation should create the Confluence user if the user does not exist already.
     * </p>
     *
     * @param handle the user handle for which to retrieve the email address from Confluence Service.
     * @return the email address of the Confluence user for the given handle.
     * @throws UserServiceException if any error occurs when getting user details.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public String getConfluenceUser(String handle) throws UserServiceFacadeException;
}
