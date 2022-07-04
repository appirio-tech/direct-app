/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

/**
 * <p>
 * It provides various getters on user object.
 * </p>
 *
 * <p>
 *      <strong>Version History:</strong>
 *      <ul>
 *              <li>Introduced since Cockpit Release Assembly for Receipts</li>
 *              <li>Updated for Jira and Confluence User Sync Widget 1.0</li>
 *                  <ul>
 *                      <li>Moved the methods that existed in user_sync_service
 *                      component's UserService.</li>
 *                  </ul>
 *              <li>Modified in version 1.1</li>
 *                  <ul>
 *                      <li>Added the <code>registerUser(User)</code> method.</li>
 *                      <li>Added the <code>getUserInfo(String)</code> method.</li>
 *                      <li>Added the <code>removeUserFromGroups(String,long[])</code> method.</li>
 *                      <li>Added the <code>addUserToGroups(String,long[])</code> method.</li>
 *                      <li>Added the <code>removeUserTerm(String,long)</code> method.</li>
 *                      <li>Added the <code>addUserTerm(String,long)</code> method.</li>
 *                  </ul>
 *      </ul>
 * </p>
 * 
 * <p>
 * Version 1.2 (TC Registration Feature in Popup Windows) change notes:
 * <ol>
 *  <li>Added {@link #activateUser(String)} method.</li>
 *  <li>Added {@link #getUserByEmail(String)} method.</li>
 * </ol>
 * </p>
 * 
 * @author snow01, woodjhon, ernestobf, freegod
 * @version 1.2 (TC Registration Feature in Popup Windows)
 * @since Cockpit Release Assembly for Receipts
 * @version 1.2
 */
@Remote
public interface UserService {

    /**
     * <p>
     * This method retrieve the email address for given user id.
     * </p>
     *
     * @param userid
     *            user id to look for
     *
     * @return the email address
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws UserServiceException
     *             if any error occurs when getting permissions.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    public String getEmailAddress(long userid) throws UserServiceException;

    /**
     * <p>
     * This method retrieve the email address for given user handle.
     * </p>
     *
     * @param userHandle
     *            user handle to look for
     *
     * @return the email address
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws UserServiceException
     *             if any error occurs when getting user details.
     * @since Jira & Confluence User Sync Service
     */
    public String getEmailAddress(String userHandle) throws UserServiceException;

    /**
     * <p>
     * This method retrieve the user id for given user handle.
     * </p>
     *
     * @param userHandle
     *            user handle to look for
     *
     * @return user id
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws UserServiceException
     *             if any error occurs when getting user details
     */
    public long getUserId(String userHandle) throws UserServiceException;

    /**
     * <p>
     * This method returns true if given user handle is admin otherwise it returns false.
     * </p>
     *
     * @param userHandle
     *            user handle to look for
     *
     * @return returns true if given user handle is admin otherwise it returns false.
     *
     * @throws UserServiceException
     *             if any error occurs when getting user details.
     * @since Jira & Confluence User Sync Service
     */
    public boolean isAdmin(String userHandle) throws UserServiceException;

    /**
     * <p>
     * This method retrieve the user handle for given user id.
     * </p>
     *
     * @param userId
     *            user id to look for
     *
     * @return user handle
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws UserServiceException
     *             if any error occurs when getting user details
     */
    String getUserHandle(long userId) throws UserServiceException;

    /**
     * Registers the given user.
     *
     * @param user
     *            The user to register
     * @return the generated user id
     * @throws IllegalArgumentException
     *             if <code>user</code> is null or has missing required fields.
     * @throws UserServiceException
     *             if any error occurs during the operation
     * @since 1.1
     */
    long registerUser(User user) throws UserServiceException;

    /**
     * Retrieves the user info given the user handle.
     *
     * @param handle
     *            the user handle
     * @return the user info
     * @throws IllegalArgumentException
     *             if <code>handle</code> is null or empty
     * @throws UserServiceException
     *             if any error occurs during the operation
     * @since 1.1
     */
    UserInfo getUserInfo(String handle) throws UserServiceException;

    /**
     * Adds the user to the given groups.
     *
     * @param groupIds
     *            the IDs of the groups to add the user to
     * @param handle
     *            the user handle
     * @throws IllegalArgumentException
     *             if <code>handle</code> is null or empty, if <code>groupsIds</code> is null, empty or if it contains
     *             non-positive values
     * @throws UserServiceException
     *             if any error occurs during the operation
     * @since 1.1
     */
    void addUserToGroups(String handle, long[] groupIds) throws UserServiceException;

    /**
     * Removes the user from the given groups.
     *
     * @param handle
     *            The user handle
     * @param groupIds
     *            the IDs of the groups to remove the user from
     * @throws IllegalArgumentException
     *             if <code>handle</code> is null or empty, if <code>groupsIds</code> is null, empty or if it contains
     *             non-positive values
     * @throws UserServiceException
     *             if any error occurs during the operation
     * @since 1.1
     */
    void removeUserFromGroups(String handle, long[] groupIds) throws UserServiceException;

    /**
     * Adds the given agreed term to the user.
     *
     * @param handle
     *            the user handle
     * @param termsId
     *            the ID of the term agreed by the user
     * @param termsAgreedDate
     *            the date the user agreed the terms
     * @throws IllegalArgumentException
     *             if <code>handle</code> is null or empty, or if <code>termsId</code> is non-positive
     * @throws UserServiceException
     *             if the association already exists, the user cannot be found in the DB, or if the given term
     *             does not exist in the DB
     * @since 1.1
     */
    void addUserTerm(String handle, long termsId, Date termsAgreedDate) throws UserServiceException;

    /**
     * Removes the given term from the user.
     *
     * @param handle
     *            the user handle
     * @param termsId
     *            the ID of the term
     * @throws IllegalArgumentException
     *             if <code>handle</code> is null or empty, or if <code>termsId</code> is non-positive.
     * @throws UserServiceException
     *             if the association does not exist, the user does not exist in the DB, or if the given term does not
     *             exist in the DB
     * @since 1.1
     */
    void removeUserTerm(String handle, long termsId) throws UserServiceException;

     /**
     * Adds the user to the given groups.
     *
     * @param groupIds
     *            the IDs of the groups to add the user to
     * @param userId
     *            userId
     * @throws IllegalArgumentException
     *             if <code>handle</code> is null or empty, if <code>groupsIds</code> is null, empty or if it contains
     *             non-positive values
     * @throws UserServiceException
     *             if any error occurs during the operation
     * @since 1.1
     */
    void addUserToGroups(long userId, long[] groupIds) throws UserServiceException;


    /**
     * Adds the given agreed term to the user.
     *
     * @param userId
     *            the userId
     * @param termsId
     *            the ID of the term agreed by the user
     * @param termsAgreedDate
     *            the date the user agreed the terms
     * @throws IllegalArgumentException
     *             if <code>handle</code> is null or empty, or if <code>termsId</code> is non-positive
     * @throws UserServiceException
     *             if the association already exists, the user cannot be found in the DB, or if the given term
     *             does not exist in the DB
     * @since 1.1
     */
    void addUserTerm(long userId, long termsId, Date termsAgreedDate) throws UserServiceException;
    
    /**
     * Gets User information by the given user id.
     * 
     * @param userId the id of the user.
     * @return the User instance.
     * @throws UserServiceException if any error occurs during operation.
     * @since BUGR-3739
     */
    public User getUser(long userId) throws UserServiceException;
    
    /**
     * Get user by email address.
     * 
     * @param emailAddress
     *          email address
     * @return
     *          first user with this email address
     * @throws UserServiceException
     *          if any error occurs
     */
    public User getUserByEmail(String emailAddress) throws UserServiceException;
    
    /**
     * Searches User by given search string.
     * 
     * @param key the search string to use.
     * @return the List of User instances that match passed string.
     * @throws UserServiceException if any error occurs during operation.
     */
    public List<User> searchUser(String key) throws UserServiceException;

     /**
     * Check if user has agreed term
     *
     * @param handle
     *            the user handle
     * @param termsId
     *            the ID of the term agreed by the user
     * @return boolean
     *
     * @throws IllegalArgumentException
     *             if <code>handle</code> is null or empty, or if <code>termsId</code> is non-positive
     * @throws UserServiceException
     *             if the association already exists, the user cannot be found in the DB, or if the given term
     *             does not exist in the DB
     * @since 1.1
     */
    public boolean checkUserTerm(long userId, long termsId) throws UserServiceException;

    /**
     * Activate user by activation code.
     * 
     * @param activationCode
     *          activation code
     * @throws UserServiceException
     *          if any error occurs.
     */
    public void activateUser(String activationCode) throws UserServiceException;

}
