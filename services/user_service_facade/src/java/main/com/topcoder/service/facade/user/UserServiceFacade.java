/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.user;

import com.topcoder.security.TCSubject;

/**
 * <p>
 * This interface defines the Jira & Confluence back end service.
 *
 * This service provides mechanism to create users in both Jira & Confluence.
 * </p>
 * <p>
 * Changes in v1.0.1(Cockpit Security Facade V1.0):
 *  - It is not a web-service facade any more.
 *  - All the methods accepts a parameter TCSubject which contains all the security info for current user.
 *    The implementation EJB should use TCSubject and now get these info from the sessionContext.
 *  - Please use the new UserServiceFacadeWebService as the facade now. That interface will delegates all the methods
 *    to this interface.
 * </p>
 * @author snow01, waits
 *
 * @since Jira & Confluence User Service
 * @version 1.0.1
 */
public interface UserServiceFacade {
    /**
     * <p>
     * Creates Jira User (if does not exist already) and gets the email address of it from the Jira Service.
     *
     * Implementation should create the Jira user if the user does not exist already.
     * </p>
     * <p>
     * Update in v1.0.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param handle
     *            the user handle for which to retrieve the email address from Jira Service.
     * @return the email address of the Jira user for the given handle.
     * @throws UserServiceException
     *             if any error occurs when getting user details.
     */
    public String getJiraUser(TCSubject tcSubject, String handle) throws UserServiceFacadeException;

    /**
     * <p>
     * Creates Confluence User (if does not exist already) and gets the email address of it from the Confluence Service.
     *
     * Implementation should create the Confluence user if the user does not exist already.
     * </p>
     * <p>
     * Update in v1.0.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param handle
     *            the user handle for which to retrieve the email address from Confluence Service.
     * @return the email address of the Confluence user for the given handle.
     * @throws UserServiceException
     *             if any error occurs when getting user details.
     */
    public String getConfluenceUser(TCSubject tcSubject, String handle) throws UserServiceFacadeException;

    /**
     * <p>
     * Creates Jira User (if does not exist already) and gets the email address of it from the Jira Service.
     *
     * Implementation should create the Jira user if the user does not exist already.
     *
     *   ******************
     *   ******************
     *   THIS METHOD DOES NOT CHECK EMAIL IN USER TALBE AND DOES NOT SET ROLLBACK ONLY IF ERRORS
     *   ******************
     *   ******************
     * </p>
     * <p>
     * Update in v1.0.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param handle
     *            the user handle for which to retrieve the email address from Jira Service.
     * @return the email address of the Jira user for the given handle.
     * @throws UserServiceException
     *             if any error occurs when getting user details.
     */
    public String syncJiraUser(TCSubject tcSubject, String handle) throws UserServiceFacadeException;
}
