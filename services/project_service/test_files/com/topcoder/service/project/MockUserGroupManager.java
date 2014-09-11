/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.security.facade.BaseProfileType;
import com.topcoder.security.facade.BaseSecurityProfileType;
import com.topcoder.user.manager.Attribute;
import com.topcoder.user.manager.AttributeType;
import com.topcoder.user.manager.Group;
import com.topcoder.user.manager.GroupPersistenceException;
import com.topcoder.user.manager.Profile;
import com.topcoder.user.manager.User;
import com.topcoder.user.manager.UserGroupManager;
import com.topcoder.user.manager.UserGroupManagerAdjudicationException;
import com.topcoder.user.manager.UserPersistenceException;
import com.topcoder.user.manager.impl.UserImpl;

/**
 * Mock implement for <code>UserGroupManager</code>.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockUserGroupManager implements UserGroupManager {

    /**
     * <p>
     * The user is "unknown" in this mock class.
     * </p>
     */
    public static final String UNKNOWN_USER = "UnknownUser";

    /**
     * <p>
     * The user in this mock class without roles.
     * </p>
     */
    public static final String NO_ROLES_USER = "NoRolesUser";

    /**
     * The user in this mock class contains duplicate roles.
     */
    public static final String DUPLICATE_ROLES_USER = "DuplicateRolesUser";

    /**
     * the user in this mock class contains <code>null</code> role.
     */
    public static final String NULL_ROLES_USER = "NullRolesUser";

    /**
     * the user in this mock class contains empty <code>String</code> role.
     */
    public static final String EMPTY_STRING_ROLES_USER = "EmptyRolesUser";

    /**
     * <p>
     * This user in this mock class is locked.
     * </p>
     */
    public static final String LOCKED_ACCOUNT = "LockedAccount";

    /**
     * <p>
     * This user will cause <code>ExpiredPasswordException</code>.
     * </p>
     */
    public static final String EXPIRED_PASSWORD = "ExpiredPassword";

    /**
     * <p>
     * This user 's profile type is invalid.
     * </p>
     */
    public static final String INVALID_PROFILE_TYPE = "InvalidProfileType";

    /**
     * the use in this mock class will cause <code>UserPersistenceException</code>.
     */
    public static final String EXCEPTION_USER = "exceptionUser";

    /**
     * <p>
     * Adds specified group to the persistent storage.
     * </p>
     * @param group
     *            Group instance to be added. Should not be null
     * @throws IllegalArgumentException
     *             If given <code>group</code> is null
     * @throws GroupPersistenceException
     *             If any error occurs in <code>GroupPersistence</code> instance
     */
    public void addGroup(Group group) throws GroupPersistenceException {

    }

    /**
     * <p>
     * Adds specified user to the persistent storage.
     * </p>
     * @param user
     *            User instance to be added. Should not be null
     * @throws IllegalArgumentException
     *             If given <code>user</code> is null
     * @throws UserPersistenceException
     *             If any error occurs in <code>UserPersistence</code> instance
     */
    public void addUser(User user) throws UserPersistenceException {

    }

    /**
     * <p>
     * Performs attribute consolidation for the specified user.
     * </p>
     * <p>
     * The method fills the <code>Profile</code> instance passed as argument with the user's attributes
     * (which include attributes of all the user's profiles and all the profiles of each of the groups the
     * user belongs to).
     * </p>
     * <p>
     * Note that user's or group's profile need to have the same type as the passed one to be consolidated.
     * The actual algorithm details depends on the <code>ProfileAdjudicator</code> implementation used.
     * </p>
     * @param user
     *            User instance whose attributes needs to be consolidated. Should not be null
     * @param profile
     *            Profile instance in which the consolidated attributes will be added to. Should not be null
     * @throws IllegalArgumentException
     *             If any argument is null
     * @throws UserGroupManagerAdjudicationException
     *             If any other error occurs during attribute consolidation
     */
    public void adjudicateProfiles(User user, Profile profile) throws UserGroupManagerAdjudicationException {

    }

    /**
     * <p>
     * Gets group with the specified id from the persistent storage or <code>null</code> if not found.
     * </p>
     * @param groupId
     *            Id of the group.
     * @return Group instance. Null if no group for the specified id found
     * @throws GroupPersistenceException
     *             If any error occurs in <code>GroupPersistence</code> instance
     */
    public Group getGroup(long groupId) throws GroupPersistenceException {
        return null;
    }

    /**
     * <p>
     * Gets all the groups from the persistent storage.
     * </p>
     * @return Array of groups. Can be empty but should not be null. Values should not be null
     * @throws GroupPersistenceException
     *             If any error occurs in <code>GroupPersistence</code> instance
     */
    public Group[] getGroups() throws GroupPersistenceException {
        return null;
    }

    /**
     * <p>
     * Gets all the groups of the specified user from the persistent storage.
     * </p>
     * @param user
     *            User instance to search for groups associated with. Should not be null
     * @return Array of groups. Can be empty but should not be null. Values should not be null
     * @throws IllegalArgumentException
     *             If given <code>user</code> is null
     * @throws GroupPersistenceException
     *             If any error occurs in <code>GroupPersistence</code> instance
     */
    public Group[] getGroups(User user) throws GroupPersistenceException {
        return null;
    }

    /**
     * <p>
     * Gets all the groups with the specified name from the persistent storage.
     * </p>
     * @param groupName
     *            Name of the group. Should not be null or empty.
     * @return Array of groups. Can be empty but should not be null. Values should not be null.
     * @throws IllegalArgumentException
     *             If userName is null or empty
     * @throws GroupPersistenceException
     *             If any error occurs in <code>GroupPersistence</code> instance
     */
    public Group[] getGroups(String groupName) throws GroupPersistenceException {
        return null;
    }

    /**
     * <p>
     * Gets all the groups with the specified username from the persistent storage.
     * </p>
     * @param username
     *            Name of the user. Should not be null or empty.
     * @return Array of groups. Can be empty but should not be null. Values should not be null.
     * @throws IllegalArgumentException
     *             If userName is null or empty
     * @throws GroupPersistenceException
     *             If any error occurs in <code>GroupPersistence</code> instance
     */
    public Group[] getGroupsByUsername(String username) throws GroupPersistenceException {

        return null;
    }

    /**
     * <p>
     * Gets the user with the specified id from the persistent storage or <code>null</code> if not found.
     * </p>
     * @param userId
     *            Id of the user
     * @return User instance. Null if no user for the specified id found
     * @throws UserPersistenceException
     *             If any error occurs in <code>UserPersistence</code> instance
     */
    public User getUser(long userId) throws UserPersistenceException {

        return null;
    }

    /**
     * <p>
     * Gets all the users from the persistent storage.
     * </p>
     * @return Array of users. Can be empty but should not be null. Values should not be null
     * @throws UserPersistenceException
     *             If any error occurs in <code>UserPersistence</code> instance
     */
    public User[] getUsers() throws UserPersistenceException {

        return null;
    }

    /**
     * <p>
     * Gets all the users of the specified group from the persistent storage.
     * </p>
     * @param group
     *            Group instance to search for users within. Should not be null
     * @return Array of users. Can be empty but should not be null. Values should not be null
     * @throws IllegalArgumentException
     *             If given <code>group</code> is null
     * @throws UserPersistenceException
     *             If any error occurs in <code>UserPersistence</code> instance
     */
    public User[] getUsers(Group group) throws UserPersistenceException {

        return null;
    }

    /**
     * <p>
     * Gets all the users with the specified name from the persistent storage.
     * </p>
     * @param userName
     *            Name of the user. Should not be null or empty.
     * @return Array of users. Can be empty but should not be null. Values should not be null.
     * @throws IllegalArgumentException
     *             If userName is null or empty
     * @throws UserPersistenceException
     *             If any error occurs in <code>UserPersistence</code> instance
     */
    public User[] getUsers(String userName) throws UserPersistenceException {

        return null;
    }

    /**
     * <p>
     * Removes specified group from the persistent storage.
     * </p>
     * @param group
     *            Group instance to be removed. Should not be null
     * @throws IllegalArgumentException
     *             If given <code>group</code> is null
     * @throws GroupPersistenceException
     *             If any error occurs in <code>GroupPersistence</code> instance
     */
    public void removeGroup(Group group) throws GroupPersistenceException {

    }

    /**
     * <p>
     * Removes specified user from the persistent storage.
     * </p>
     * @param user
     *            User instance to be removed. Should not be null
     * @throws IllegalArgumentException
     *             If given <code>user</code> is null
     * @throws UserPersistenceException
     *             If any error occurs in <code>UserPersistence</code> instance
     */
    public void removeUser(User user) throws UserPersistenceException {

    }

    /**
     * <p>
     * Updates group in the persistent storage.
     * </p>
     * @param group
     *            Group instance to be updated. Should not be null
     * @throws IllegalArgumentException
     *             If given <code>group</code> is null
     * @throws GroupPersistenceException
     *             If any error occurs in <code>GroupPersistence</code> instance
     */
    public void updateGroup(Group group) throws GroupPersistenceException {

    }

    /**
     * <p>
     * Updates user in the persistent storage.
     * </p>
     * @param user
     *            User instance to be updated. Should not be null
     * @throws IllegalArgumentException
     *             If given <code>user</code> is null
     * @throws UserPersistenceException
     *             If any error occurs in <code>UserPersistence</code> instance
     */
    public void updateUser(User user) throws UserPersistenceException {

    }

    /**
     * <p>
     * Gets the user with the specified name from the persistent storage or <code>null</code> if not found.
     * </p>
     * @param username
     *            name of the user, cannot be null or empty
     * @return User instance. Null if no user for the specified id found
     * @throws IllegalArgumentException
     *             If given <code>username</code> is null or empty
     * @throws IllegalStateException
     *             If logger or user persistence is null
     * @throws UserPersistenceException
     *             If any error occurs in <code>UserPersistence</code> instance
     */
    public User getUser(String username) throws UserPersistenceException {
        if (EXCEPTION_USER.equals(username)) {
            throw new UserPersistenceException("throw from mock method");
        }
        if (UNKNOWN_USER.equals(username)) {
            return null;
        }
        User user = new UserImpl();
        user.setName(username);
        if (!INVALID_PROFILE_TYPE.equals(username)) {
            BaseSecurityProfileType profile = new BaseSecurityProfileType();

            profile.setLocked(username.equals(LOCKED_ACCOUNT));

            profile.setFailedLoginTimes(0);
            Date date = username.equals(EXPIRED_PASSWORD) ? new Date(0) : new Date();
            profile.setPasswordCreation(date);
            // add roles
            if (!NO_ROLES_USER.equals(username)) {
                Map roles = new HashMap();
                roles.put(1L, "User");

                if ("admin".equals(username)) {
                    roles.put(2L, "Cockpit Administrator");
                }

                if (EMPTY_STRING_ROLES_USER.equals(user)) {
                    roles.put(3L, "  ");
                }
                if (NULL_ROLES_USER.equals(user)) {
                    roles.put(4L, null);
                }
                if (DUPLICATE_ROLES_USER.equals(user)) {
                    roles.put(5L, "role2");
                }
                
                if ("username".equals(username)) {
                    roles.put(6L, "Cockpit User");
                }
                Attribute attr = new Attribute(new AttributeType(Map.class, false), "roles", roles);
                profile.addAttribute(attr);

            }
            user.addProfile(profile);
        } else {
            // add invalid profile type
            user.addProfile(new BaseProfileType());
        }
        return user;
    }

}
