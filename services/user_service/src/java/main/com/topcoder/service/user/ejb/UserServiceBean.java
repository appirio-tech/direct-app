/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.topcoder.service.user.Address;
import com.topcoder.service.user.Helper;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserInfo;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.service.user.UserServiceLocal;
import com.topcoder.service.user.UserServiceRemote;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * It provides CRUD on user object.
 * </p>
 *
 * <p>
 *      <strong>Version History:</strong>
 *      <ul>
 *              <li>Introduced since Cockpit Release Assembly for Receipts</li>
 *              <li>Updated for Jira and Confluence User Sync Widget 1.0</li>
 *                  <ul>
 *                      <li>The mock implementation of getEmailAddress and isAdmin has
 *                      been moved from user_sync_service UserServiceBean class.</li>
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
 * The mock implementation for getEmailAddress(..) returns <userHandle>@topcoder.com email address for any user handle
 * that starts with alphabets and just has allowed character sets [A-Z], [a-z], [0-9], _ (a underscore). Other wise it
 * returns null.
 *
 * The mock implementation for isAdmin(..) returns true for 'user' handle or all those handles that has only Upper case
 * alphabets.
 * </p>
 * 
 * <p>
 * Version 1.2 (TC Registration Feature in Popup Windows) change notes:
 * <ol>
 *  <li>Added {@link #activateUser(String)} method.</li>
 *  <li>Added {@link #getCoderId(String)} method.</li>
 *  <li>Added {@link #getUserByEmail(String)} method.</li>
 * </ol>
 * </p>
 *
 * @author snow01, woodjhon, ernestobf, freegod
 * @since Cockpit Release Assembly for Receipts
 * @version 1.2 (TC Registration Feature in Popup Windows)
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class UserServiceBean implements UserServiceRemote, UserServiceLocal {
    /**
     * TC administrator group.
     */
    private static final String TC_GROUP_ADMIN = "group_Admin";

    /**
     * <p>
     * Represents the sessionContext of the EJB.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * This field represents the persistence unit name to lookup the <code>EntityManager</code> from the
     * <code>SessionContext</code>. It is initialized in the <code>initialize</code> method, and never changed
     * afterwards. It must be non-null, non-empty string.
     * </p>
     */
    @Resource(name = "unitName")
    private String unitName;

    /**
     * <p>
     * Represents the loggerName used to retrieve the logger.
     * </p>
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * <p>
     * Represents the email status id used in the registerUser method.
     * </p>
     * @since 1.1
     */
    @Resource(name = "emailStatusId")
    private long emailStatusId;

    /**
     * <p>
     * The name of the phone id generator.
     * </p>
     * @since 1.1
     */
    @Resource(name = "phoneIdGeneratorName")
    private String phoneIdGeneratorName;

    /**
     * <p>
     * The name of the email id generator.
     * </p>
     * @since 1.1
     */
    @Resource(name = "emailIdGeneratorName")
    private String emailIdGeneratorName;

    /**
     * <p>
     * The name of the address id generator.
     * </p>
     * @since 1.1
     */
    @Resource(name = "addressIdGeneratorName")
    private String addressIdGeneratorName;

    /**
     * <p>
     * The name of the user id generator.
     * </p>
     * @since 1.1
     */
    @Resource(name = "userIdGeneratorName")
    private String userIdGeneratorName;

    /**
     * <p>
     * The name of the security user id generator.
     * </p>
     * @since 1.1
     */
    @Resource(name = "securityUserIdGeneratorName")
    private String securityUserIdGeneratorName;

    /**
     * <p>
     * The name of the user-group id generator.
     * </p>
     * @since 1.1
     */
    @Resource(name = "userGroupIdGeneratorName")
    private String userGroupIdGeneratorName;

    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private Log logger;

    /**
     * default timezone id
     */
    private long DEFAULT_TIMEZONE_ID = 143;

    /**
     * A default empty constructor.
     */
    public UserServiceBean() {
    }

    /**
     * <p>
     * This is method is performed after the construction of the bean, at this point all the bean's resources will be
     * ready.
     * </p>
     */
    @PostConstruct
    public void init() {
        if (logName != null) {
            if (logName.trim().length() == 0) {
                throw new IllegalStateException("logName parameter not supposed to be empty.");
            }

            logger = LogManager.getLog(logName);
        }

        // first record in logger
        logExit("init");
    }

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
     * @throws UserServiceException
     *             if any error occurs when getting permissions.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getEmailAddress(long userid) throws UserServiceException {
        try {
            logEnter("getEmailAddress(userid)");
            logOneParameter(userid);

            EntityManager em = getEntityManager();
            Query query = em.createNativeQuery("select max(address) from email where primary_ind = 1 and user_id = " + userid);
            Object result = query.getSingleResult();
            if (result != null) {
                return result.toString();
            }

            return null;
        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "The EntityManager is closed.");
        } catch (NoResultException e) {
            return null;
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e, "There are errors while retrieving the user's email address.");
        } finally {
            logExit("getEmailAddress(userid)");
        }
    }

    /**
     * <p>
     * This method retrieve the email address for given user handle.
     *
     * This mock implementation returns <userHandle>@topcoder.com email address for any user handle that starts with
     * alphabets and just has allowed character sets [A-Z], [a-z], [0-9], _ (a underscore). Other wise it returns null.
     * </p>
     *
     * @param userHandle
     *            user handle to look for
     *
     * @return the email address
     *
     * @throws UserServiceException
     *             if any error occurs when getting user details.
     * @since Jira & Confluence User Sync Service
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getEmailAddress(String userHandle) throws UserServiceException {
        try {
            logEnter("getEmailAddress(userHandle)");
            logOneParameter(userHandle);

            Helper.checkNull(logger, userHandle, "userHandle");
            Helper.checkEmpty(logger, userHandle, "userHandle");

            EntityManager em = getEntityManager();
            Query query = em.createNativeQuery(
                    "select max(e.address) from email e, user u"
                    + " where e.primary_ind = 1 and e.user_id = u.user_id and u.handle_lower = :handle");
            // passing lower case
            query.setParameter("handle", userHandle.toLowerCase());
            Object result = query.getSingleResult();
            if (result != null) {
                return result.toString();
            }

            return null;
        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "The EntityManager is closed.");
        } catch (NoResultException e) {
            return null;
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e, "There are errors while retrieving the user's email address.");
        } finally {
            logExit("getEmailAddress(userid)");
        }
    }

   /**
     * <p>
     * This method retrieve the user id for given user handle.
     * </p>
     *
     * @param userHandle
     *            user handle to look for
     *
     * @return user id
     *             if the argument is invalid
     * @throws UserServiceException
     *             if any error occurs when getting user details
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public long getUserId(String userHandle) throws UserServiceException {
        try {
            logEnter("getUserId(userHandle)");
            logOneParameter(userHandle);

            Helper.checkNull(logger, userHandle, "userHandle");
            Helper.checkEmpty(logger, userHandle, "userHandle");

            EntityManager em = getEntityManager();
            Query query = em.createNativeQuery("select user_id from user u where u.handle_lower = :handle");
            query.setParameter("handle", userHandle.toLowerCase());
            Object result = query.getSingleResult();
            if (result != null) {
                return Long.parseLong(result.toString());
            }

            throw wrapUserServiceException(new NoResultException(), "No such user");

        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "The EntityManager is closed.");
        } catch (NoResultException e) {
            throw wrapUserServiceException(e, "No such user");
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e, "There are errors while retrieving the user's email address.");
        } finally {
            logExit("getEmailAddress(userid)");
        }
    }

    /**
     * <p>
     * This helper method retrieves the login id for the given user handle from the security_user table.
     * </p>
     *
     * @param handle
     *            user handle to look for
     *
     * @return the login id
     *
     * @throws IllegalArgumentException
     *             if <code>handle</code> is null or empty
     * @throws UserServiceException
     *             if any error occurs when getting the login id
     * @since 1.1
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private long getLoginId(String handle) throws UserServiceException {
        try {
            Helper.checkNullEmpty(logger, handle, "handle");

            EntityManager em = getEntityManager();
            Query query = em.createNativeQuery(
                    "select user_id from user where handle_lower = :handle");
            query.setParameter("handle", handle.toLowerCase());
            Object result = query.getSingleResult();
            return Long.parseLong(result.toString());

        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "The EntityManager is closed.");
        } catch (NoResultException e) {
            throw wrapUserServiceException(e, "No such user");
        } catch (NonUniqueResultException e) {
            throw wrapUserServiceException(e, "The query returned more than one login ids");
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e, "There are errors while retrieving the user's email address.");
        }
    }

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
     * @throws UserServiceException
     *             if any error occurs when getting user details
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getUserHandle(long userId) throws UserServiceException {
        try {
            logEnter("getUserHandle("+userId+")");
            logOneParameter(userId);

            EntityManager em = getEntityManager();
            Query query = em.createNativeQuery("select handle from user u where u.user_id = :userId");
            query.setParameter("userId", userId);
            Object result = query.getSingleResult();
            if (result != null) {
                return (result.toString());
            }

            throw wrapUserServiceException(new NoResultException(), "No such user");

        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "The EntityManager is closed.");
        } catch (NoResultException e) {
            throw wrapUserServiceException(e, "No such user");
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e, "There are errors in getUserHandle.");
        } finally {
            logExit("getUserHandle(userid)");
        }
    }


    /**
     * <p>
     * This method returns true if given user handle is admin otherwise it returns false.
     *
     * This mock implementation returns true for 'user' handle or all those handles that has only Upper case alphabets.
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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean isAdmin(String userHandle) throws UserServiceException {
        boolean ret = false;
        try {
            logEnter("isAdmin(userHandle)", userHandle);
            Helper.checkNull(logger, userHandle, "userHandle");
            Helper.checkEmpty(logger, userHandle, "userHandle");

            EntityManager em = getEntityManager();
            Query query = em.createNativeQuery(
                    "select security_status_id from user_role_xref x, user u, security_roles sr "
                                             + " where x.login_id = u.user_id and "
                                             + " x.role_id = sr.role_id and "
                                             + "sr.description = :description and "
                                             + " u.handle_lower = :handle");
            query.setParameter("description", TC_GROUP_ADMIN);
            query.setParameter("handle", userHandle.toLowerCase());
            Object result = query.getSingleResult();
            if (result != null) {
                ret = (1L == ((Number) result).longValue());
            }

        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "IllegalStateException.");
        } catch (NoResultException e) {
            return false;
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e, "There are errors while retrieving the user's admin status.");
        } finally {
            logExit("isAdmin(userHandle)", ret);
        }

        return ret;
    }

    /**
     * Registers the given user.
     *
     * @param user
     *            the user to register
     * @return the generated user id
     * @throws IllegalArgumentException
     *             if <code>user</code> is null or if any user field is null or empty except <code>groupIds</code> and
     *             <code>userId</code>.
     * @throws UserServiceException
     *             if any error occurs during the operation
     * @since 1.1
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long registerUser(User user) throws UserServiceException {

        try {
            logEnter("registerUser(User)");
            logParameters(formatUser(user));

            // The following fields are optional and thus not checked:
            // user.address.address1, user.address.address2, user.address.stateCode
            Helper.checkNull(logger, user, "user");
            Helper.checkNullEmpty(logger, user.getHandle(), "user.handle");
            Helper.checkNullEmpty(logger, user.getFirstName(), "user.firstName");
            Helper.checkNullEmpty(logger, user.getLastName(), "user.lastName");
            Helper.checkNullEmpty(logger, user.getEmailAddress(), "user.emailAddress");
            //Helper.checkNullEmpty(logger, user.getPhone(), "user.phone");
            Helper.checkNullEmpty(logger, user.getPassword(), "user.password");
            //Helper.checkNull(logger, user.getAddress(), "user.address");
            //Helper.checkNullEmpty(logger, user.getAddress().getAddress1(), "user.address.address1");
            //Helper.checkNullEmpty(logger, user.getAddress().getCountryCode(), "user.address.countryCode");
            //Helper.checkNullEmpty(logger, user.getAddress().getCity(), "user.address.city");
            //Helper.checkNullEmpty(logger, user.getAddress().getProvince(), "user.address.province");
            //Helper.checkNullEmpty(logger, user.getAddress().getZip(), "user.address.zip");

            EntityManager em = getEntityManager();

            // generate the user id
            long userId = IDGeneratorFactory.getIDGenerator(userIdGeneratorName).getNextID();

            // NULL is used for the last_login, activation_code, middle_name and last_site_hit_date columns.
            // 1 is assumed to be an existing time zone id.
            // 'A' is used as the status.
            // create_date is omitted because it has a default value
            // handle_lower and modify_date are not specified because a trigger sets these values.
            Query userInsert = em.createNativeQuery(
                    "insert into user "
                    + "(user_id, first_name, last_name, handle, last_login, status, "
                    + "activation_code, middle_name, timezone_id, last_site_hit_date, reg_source) "
                    + "values "
                    + "(:userId, :firstName, :lastName, :handle, NULL, :status, "
                    + "NULL, NULL, " + DEFAULT_TIMEZONE_ID + " , NULL, :regSource)");
			

            userInsert.setParameter("userId", userId);
            userInsert.setParameter("firstName", user.getFirstName());
            userInsert.setParameter("lastName", user.getLastName());
            userInsert.setParameter("handle", user.getHandle());
			userInsert.setParameter("regSource", user.getRegSource());
            if(null != user.getStatus()) {
                userInsert.setParameter("status", user.getStatus());
            } else {
                userInsert.setParameter("status", "A");
            }
			
			long coderId = userId;
			Query coderInsert = em.createNativeQuery(
                    "insert into coder "
                    + "(coder_id, quote, coder_type_id, comp_country_code, display_quote, quote_location, "
                    + "quote_color, display_banner, banner_style) "
                    + "values "
                    + "(:coderId, '', null, null,  1, 'md', '#000000', "
                    + " 1, 'bannerStyle4') ");
			coderInsert.setParameter("coderId", coderId);
					

            // generate the security user id
            long securityUserId = userId;

            // null is passed as the create_user_id
            Query securityUserInsert = em.createNativeQuery(
                    "insert into security_user "
                    + "(login_id, user_id, password, create_user_id) "
                    + "values "
                    + "(:loginId, :handle, :password, :createUserId)");

            securityUserInsert.setParameter("loginId", securityUserId);
            securityUserInsert.setParameter("handle", user.getHandle());
            securityUserInsert.setParameter("password", Util.encodePassword(user.getPassword()));
            securityUserInsert.setParameter("createUserId", null);

            // generate the email id
            long emailId = IDGeneratorFactory.getIDGenerator(emailIdGeneratorName).getNextID();

            // 1 is assumed to be a valid email type id.
            // 1 is used as a dummy value for primary_ind.
            // create_date and modify_date are omitted because they have a default value (the current date).
            // the injected value emailStatusId is used in the status_id column.
            Query emailInsert = em.createNativeQuery(
                    "insert into email "
                    + "(user_id, email_id, email_type_id, address, primary_ind, status_id) "
                    + "values "
                    + "(:userId, :emailId, 1, :emailAddress, 1, :statusId)");

            emailInsert.setParameter("userId", userId);
            emailInsert.setParameter("emailId", emailId);
            emailInsert.setParameter("emailAddress", user.getEmailAddress());
            emailInsert.setParameter("statusId", emailStatusId);

            Query phoneInsert = null;
            long phoneId = 0;

            if (user.getPhone() != null)
            {
                phoneId = IDGeneratorFactory.getIDGenerator(phoneIdGeneratorName).getNextID();

                // 1 is assumed to be a valid phone type id.
                // 1 is used as a dummy value for primary_ind.
                // create_date and modify_date are omitted because they have a default value (the current date).
                phoneInsert = em.createNativeQuery(
                    "insert into phone "
                    + "(user_id, phone_id, phone_type_id, phone_number, "
                    + "primary_ind) "
                    + "values "
                    + "(:userId, :phoneId, 1, :phoneNumber, 1)");

                phoneInsert.setParameter("userId", userId);
                phoneInsert.setParameter("phoneId", phoneId);
                phoneInsert.setParameter("phoneNumber", user.getPhone());
            }
            
            long addressId = 0;
            Query addressInsert = null;
            Query userAddressInsert = null;
            
            if (user.getAddress() != null)
            {
                 Helper.checkNullEmpty(logger, user.getAddress().getAddress1(), "user.address.address1");
                 Helper.checkNullEmpty(logger, user.getAddress().getCountryCode(), "user.address.countryCode");
                 Helper.checkNullEmpty(logger, user.getAddress().getCity(), "user.address.city");
                 Helper.checkNullEmpty(logger, user.getAddress().getProvince(), "user.address.province");
                 Helper.checkNullEmpty(logger, user.getAddress().getZip(), "user.address.zip");

                addressId = IDGeneratorFactory.getIDGenerator(addressIdGeneratorName).getNextID();

                // 1 is assumed to be a valid address type id.
                addressInsert = em.createNativeQuery(
                        "insert into address "
                        + "(address_id, address_type_id, address1, address2, "
                        + "city, state_code, zip, country_code, address3, province) "
                        + "values "
                        + "(:addressId, 1, :address1, :address2, "
                        + ":city, :stateCode, :zip, :countryCode, :address3, :province)");

                addressInsert.setParameter("addressId", addressId);
                addressInsert.setParameter("address1", user.getAddress().getAddress1());
                addressInsert.setParameter("address2", user.getAddress().getAddress2());
                addressInsert.setParameter("address3", user.getAddress().getAddress3());
                addressInsert.setParameter("countryCode", user.getAddress().getCountryCode());
                addressInsert.setParameter("province", user.getAddress().getProvince());
                addressInsert.setParameter("city", user.getAddress().getCity());
                addressInsert.setParameter("stateCode", user.getAddress().getStateCode());
                addressInsert.setParameter("zip", user.getAddress().getZip());

                // Finally the association between user and address
                userAddressInsert = em.createNativeQuery(
                        "insert into user_address_xref (user_id, address_id) "
                        + "values (:userId, :addressId)");

                userAddressInsert.setParameter("userId", userId);
                userAddressInsert.setParameter("addressId", addressId);
            }

            // execute the statements
            userInsert.executeUpdate();
			coderInsert.executeUpdate();
            securityUserInsert.executeUpdate();
            emailInsert.executeUpdate();
            if (user.getPhone() != null)
            {
                phoneInsert.executeUpdate();
            }
            
            if (user.getAddress() != null)
            {
                addressInsert.executeUpdate();
                userAddressInsert.executeUpdate();
            }
            

            // if user.groupIds is specified add the user to those groups
            if ((user.getGroupIds() != null) && (user.getGroupIds().length > 0)) {
                long loginId = userId;
                addUserToGroups(loginId, user.getGroupIds());
            }

            logReturn(userId);

            return userId;

        } catch (IDGenerationException e) {
            throw wrapUserServiceException(e, "An error occurred while generating an id.");
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e, "An persitence error occurred while registering the user.");
        } catch (Exception e) {
            throw wrapUserServiceException(e, "An persitence error occurred while registering the user.");
        }finally {
            logExit("registerUser(User)");
        }
    }

    /**
     * Retrieves the user info given the user handle.
     *
     * @param handle the user handle
     * @return the user info
     * @throws IllegalArgumentException If <code>handle</code> is null or empty
     * @throws UserServiceException if any error occurs during the operation
     * @since 1.1
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public UserInfo getUserInfo(String handle) throws UserServiceException {

        try {
            logEnter("getUserInfo(String)");
            logParameters(handle);

            Helper.checkNullEmpty(logger, handle, "handle");

            EntityManager em = getEntityManager();

            Query query1 = em.createNativeQuery(
                    "select "
                    + "user.user_id, user.handle, user.first_name, user.last_name, email.address, user.status "
                    + "from user "
                    + "left outer join email on user.user_id = email.user_id "
                    + "where user.handle_lower = :handle ");

            query1.setParameter("handle", handle.toLowerCase());

            // There could be more than one result if the user has more than one
            // email address, so just pick the first result
            List result = query1.getResultList();

            if (result.isEmpty()) {
                throw wrapUserServiceException(
                        "Couldn't find the user handle " + handle + " in the database.");
            }

            Object[] values = (Object[]) result.get(0);

            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(Long.parseLong(values[0].toString()));
            userInfo.setHandle(values[1].toString());
            userInfo.setFirstName(values[2].toString());
            userInfo.setLastName(values[3].toString());
            userInfo.setEmailAddress(values[4].toString());
            if (values[5] != null)
            {
                userInfo.setStatus(values[5].toString());
            }

            // this query retrieves the group IDs associated with the user
            Query query2 = em.createNativeQuery(
                    "select unique group_id from user_group_xref where login_id = :loginId");

            query2.setParameter("loginId", userInfo.getUserId());

            // this list contains the group IDs associated to the user
            List groupIdsList = query2.getResultList();

            // now just create the long array
            long[] groupIds = new long[groupIdsList.size()];
            int i = 0;
            for (Object groupIdObj : groupIdsList) {
                // groupIdObj is most likely a BigDecimal instance
                // but this can be driver-dependent. Converting
                // to string and parsing is safe.
                groupIds[i] = Long.parseLong(groupIdObj.toString());
                i++;
            }

            userInfo.setGroupIds(groupIds);

            logReturn(formatUserInfo(userInfo));

            return userInfo;
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e, "An persitence error occurred while retrieving the user info.");
        } finally {
            logExit("getUserInfo(String)");
        }
    }

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
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addUserToGroups(String handle, long[] groupIds) throws UserServiceException {

        logEnter("addUserToGroups(String,long[])");
        logParameters(handle, groupIds);

        try {
            Helper.checkNullEmpty(logger,  handle, "handle");
            Helper.checkNull(logger, groupIds, "groupIds");
            Helper.checkEmpty(logger, groupIds, "groupIds");
            Helper.checkNonPositive(logger, groupIds, "groupIds");

            long loginId = getLoginId(handle);
            addUserToGroups(loginId, groupIds);

        } catch (PersistenceException e) {
            throw wrapUserServiceException(e,
                    "An persitence error occurred while adding the user to the given groups.");
        } finally {
            logExit("addUserToGroups(String,long[])");
        }
    }

    /**
     * This helper method adds the user (given by its login id) to the given groups.
     *
     * @param loginId
     *            the login id of the user, this is the login_id in the security_user table
     * @param groupIds
     *            the IDs of the groups
     * @throws IllegalArgumentException
     *             if <code>loginId</code> is non-positive or if <code>groupIds</code> is null, empty or
     *             contains non-positive elements
     * @throws UserServiceException
     *             if any error occurs during the operation
     * @since 1.1
     */
    public void addUserToGroups(long loginId, long[] groupIds) throws UserServiceException {

        try {
            Helper.checkNonPositive(logger, loginId, "loginId");
            Helper.checkNull(logger, groupIds, "groupIds");
            Helper.checkEmpty(logger, groupIds, "groupIds");
            Helper.checkNonPositive(logger, groupIds, "groupIds");

            EntityManager em = getEntityManager();

            // Create a set since it is possible that the IDs are repeated, this set will contain the IDs
            // that will be inserted so we must first filter the IDs that are already associated.
            Set<Long> groupIdsSet = new HashSet<Long>();
            for (long groupId : groupIds) {
                groupIdsSet.add(groupId);
            }

            // with this query we find out if there are group IDs that don't exist in the security_groups table
            Query query1 = em.createNativeQuery(
                    "select group_id from security_groups where group_id in "
                    + createSQLIdList(groupIdsSet));

            if (query1.getResultList().size() < groupIdsSet.size()) {
                throw wrapUserServiceException(
                        "At least one group id in the given group ids does not exist.");
            }

            // with this query we find out which group IDs are already associated to the user
            Query query2 = em.createNativeQuery("select group_id from user_group_xref where login_id = :loginId");
            query2.setParameter("loginId", loginId);
            List associatedGroupIds = query2.getResultList();

            for (Object groupIdObj : associatedGroupIds) {
                long groupId = Long.parseLong(groupIdObj.toString());
                // Remove from the set of IDs to be associated since it is already
                groupIdsSet.remove(groupId);
            }

            // finally insert the associations

            for (long groupId : groupIdsSet) {
                long userGroupId = IDGeneratorFactory.getIDGenerator(userGroupIdGeneratorName).getNextID();

                // the create_date is omitted because it has a default value (the current date)
                // 1 is assumed to be user id
                // 1 is assumed to be a valid security status id
                Query userGroupInsert = em.createNativeQuery(
                        "insert into user_group_xref "
                        + "(user_group_id, login_id, group_id, create_user_id, security_status_id) "
                        + "values "
                        + "(:userGroupId, :loginId, :groupId, 1, 1)");

                userGroupInsert.setParameter("userGroupId", userGroupId);
                userGroupInsert.setParameter("loginId", loginId);
                userGroupInsert.setParameter("groupId", groupId);

                userGroupInsert.executeUpdate();
            }
        } catch (IDGenerationException e) {
            throw wrapUserServiceException(e,
                "An error occurred while generating a user_group_id.");
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e,
                    "A persistence error occurred while associating the user to the given groups.");
        }
    }

    /**
     * Removes the user from the given groups.
     *
     * @param groupIds the IDs of the groups to remove the user from
     * @param handle the user handle
     * @throws IllegalArgumentException
     *             if <code>handle</code> is null or empty, if <code>groupsIds</code> is null, empty or if it contains
     *             non-positive values
     * @throws UserServiceException
     *             if any error occurs during the operation
     * @since 1.1
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeUserFromGroups(String handle, long[] groupIds) throws UserServiceException {

        try {
            logEnter("removeUserFromGroups(String,long[])");
            logParameters(handle, groupIds);

            Helper.checkNullEmpty(logger, handle, "handle");
            Helper.checkNull(logger, groupIds, "groupIds");
            Helper.checkEmpty(logger, groupIds, "groupIds");
            Helper.checkNonPositive(logger, groupIds, "groupIds");

            // this will throw UserServiceException if the handle
            // cannot be found
            long loginId = getLoginId(handle);

            EntityManager em = getEntityManager();

            // put the group IDs in a set so we can be sure there are no
            // repeated IDs
            Set<Long> groupIdsSet = new HashSet<Long>();
            for (long groupId : groupIds) {
                groupIdsSet.add(groupId);
            }

            Query userGroupDelete = em.createNativeQuery(
                    "delete from user_group_xref where login_id = :loginId and group_id in "
                    + createSQLIdList(groupIdsSet));

            userGroupDelete.setParameter("loginId", loginId);

            if (userGroupDelete.executeUpdate() < groupIdsSet.size()) {
                // not all associations were deleted, this is because some associations don't exist
                throw wrapUserServiceException("At least one group isn't associated to the user.");
            }
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e,
                    "A persistence error occurred while removing the user from the given groups.");
        } finally {
            logExit("removeUserFromGroups(String,long[])");
        }
    }

    /**
     * This helper method creates an SQL id list with the given IDs to be used
     * in a SQL query using the IN operator.
     *
     * @param ids the list of IDs
     * @return a SQL string containing the list of IDs
     */
    private String createSQLIdList(Collection<Long> ids) {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        boolean first = true;
        for (Long id : ids) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(id);
        }
        sb.append(")");
        return sb.toString();
    }

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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addUserTerm(String handle, long termsId, Date termsAgreedDate) throws UserServiceException {

        logEnter("addUserTerm(String,long,Date)");
        logParameters(handle, termsId, termsAgreedDate);

        try {
            Helper.checkNullEmpty(logger, handle, "handle");
            Helper.checkNonPositive(logger, termsId, "termsId");

            long userId = getUserId(handle);

            EntityManager em = getEntityManager();

            // First check if the association already exist
            Query query1 = em.createNativeQuery(
                    "select terms_of_use_id from user_terms_of_use_xref "
                    + "where user_id  = :userId and terms_of_use_id = :termsId");

            query1.setParameter("userId", userId);
            query1.setParameter("termsId", termsId);

            if (!query1.getResultList().isEmpty()) {
                return;
            }

            // now check if the terms exist in the terms_of_use table
            Query query2 = em.createNativeQuery(
                    "select terms_of_use_id from terms_of_use where terms_of_use_id = :termsId");

            query2.setParameter("termsId", termsId);

            if (query2.getResultList().isEmpty()) {
                // the terms id does not exist, throw UserServiceException
                throw wrapUserServiceException(
                    "The terms with id " + termsId + " don't exist in terms_of_use.");
            }

            // finally insert the association

            Query userTermsInsert;

            if (termsAgreedDate != null) {
                // Only specify create_date and modify_date if termsAgreedDate is not null
                // otherwise use the columns' default values.
                userTermsInsert = em.createNativeQuery(
                        "insert into user_terms_of_use_xref "
                        + "(user_id, terms_of_use_id, create_date, modify_date) "
                        + "values "
                        + "(:userId, :termsId, :createDate, :modifyDate)");

                userTermsInsert.setParameter("createDate", termsAgreedDate);
                userTermsInsert.setParameter("modifyDate", termsAgreedDate);
            } else {
                // the columns create_date and modify_date have a default value (the current date)
                userTermsInsert = em.createNativeQuery(
                        "insert into user_terms_of_use_xref "
                        + "(user_id, terms_of_use_id) "
                        + "values "
                        + "(:userId, :termsId)");
            }

            userTermsInsert.setParameter("userId", userId);
            userTermsInsert.setParameter("termsId", termsId);

            userTermsInsert.executeUpdate();

        } catch (PersistenceException e) {
            throw wrapUserServiceException(e,
                    "A persitence error occurred while adding the given terms to the user.");
        } finally {
            logExit("addUserTerm(String,long,Date)");
        }
    }


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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addUserTerm(long userId, long termsId, Date termsAgreedDate) throws UserServiceException {

        logEnter("addUserTerm(String,long,Date)");
        logParameters(userId, termsId, termsAgreedDate);

        try {
           
            Helper.checkNonPositive(logger, termsId, "termsId");

            //long userId = getUserId(handle);

            EntityManager em = getEntityManager();

            // First check if the association already exist
            Query query1 = em.createNativeQuery(
                    "select terms_of_use_id from user_terms_of_use_xref "
                    + "where user_id  = :userId and terms_of_use_id = :termsId");

            query1.setParameter("userId", userId);
            query1.setParameter("termsId", termsId);

            if (!query1.getResultList().isEmpty()) {
                return;
            }

            // now check if the terms exist in the terms_of_use table
            Query query2 = em.createNativeQuery(
                    "select terms_of_use_id from terms_of_use where terms_of_use_id = :termsId");

            query2.setParameter("termsId", termsId);

            if (query2.getResultList().isEmpty()) {
                // the terms id does not exist, throw UserServiceException
                throw wrapUserServiceException(
                    "The terms with id " + termsId + " don't exist in terms_of_use.");
            }

            // finally insert the association

            Query userTermsInsert;

            if (termsAgreedDate != null) {
                // Only specify create_date and modify_date if termsAgreedDate is not null
                // otherwise use the columns' default values.
                userTermsInsert = em.createNativeQuery(
                        "insert into user_terms_of_use_xref "
                        + "(user_id, terms_of_use_id, create_date, modify_date) "
                        + "values "
                        + "(:userId, :termsId, :createDate, :modifyDate)");

                userTermsInsert.setParameter("createDate", termsAgreedDate);
                userTermsInsert.setParameter("modifyDate", termsAgreedDate);
            } else {
                // the columns create_date and modify_date have a default value (the current date)
                userTermsInsert = em.createNativeQuery(
                        "insert into user_terms_of_use_xref "
                        + "(user_id, terms_of_use_id) "
                        + "values "
                        + "(:userId, :termsId)");
            }

            userTermsInsert.setParameter("userId", userId);
            userTermsInsert.setParameter("termsId", termsId);

            userTermsInsert.executeUpdate();

        } catch (PersistenceException e) {
            throw wrapUserServiceException(e,
                    "A persitence error occurred while adding the given terms to the user.");
        } finally {
            logExit("addUserTerm(String,long,Date)");
        }
    }

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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeUserTerm(String handle, long termsId) throws UserServiceException {

        logEnter("removeUserTerm(String,long,Date)");
        logParameters(handle, termsId);

        try {
            Helper.checkNullEmpty(logger, handle, "handle");
            Helper.checkNonPositive(logger, termsId, "termsId");

            // get the user id, this will throw UserServiceException if
            // the user cannot be found
            long userId = getUserId(handle);

            EntityManager em = getEntityManager();

            // the delete statement, there is no need to check
            // whether the term
            Query userTermsDelete = em.createNativeQuery(
                    "delete from user_terms_of_use_xref where "
                    + "user_id = :userId and terms_of_use_id = :termsId");

            userTermsDelete.setParameter("userId", userId);
            userTermsDelete.setParameter("termsId", termsId);

            int count = userTermsDelete.executeUpdate();
            if (count < 1) {
                // the association does not exist
                // it is possible that the termsId does not exist in the terms_of_use table
                throw wrapUserServiceException(
                        "The user with id " + userId
                        + " is not associated to the terms with id " + termsId + ".");
            }

        } catch (PersistenceException e) {
            throw wrapUserServiceException(e,
                    "An persitence error occurred while removing given terms from the user.");
        } finally {
            logExit("removeUserTerm(String,long,Date)");
        }
    }


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
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public boolean checkUserTerm(long userId, long termsId) throws UserServiceException {

        logEnter("checkUserTerm(String,long)");
        logParameters(userId, termsId);

        try {
           
            Helper.checkNonPositive(logger, termsId, "termsId");

            //long userId = getUserId(handle);

            EntityManager em = getEntityManager();

            // First check if the association already exist
            Query query1 = em.createNativeQuery(
                    "select terms_of_use_id from user_terms_of_use_xref "
                    + "where user_id  = :userId and terms_of_use_id = :termsId");

            query1.setParameter("userId", userId);
            query1.setParameter("termsId", termsId);

            if (query1.getResultList().isEmpty()) {
                return false;
            }
            else {
                return true;
            }

            

        } catch (PersistenceException e) {
            throw wrapUserServiceException(e,
                    "A persitence error occurred in checkUserTerm");
        } finally {
            logExit("checkUserTerm(String,long)");
        }
    }

    /**
     * <p>
     * Returns the <code>EntityManager</code> looked up from the session context.
     * </p>
     *
     * @return the EntityManager looked up from the session context
     * @throws ContestManagementException
     *             if fail to get the EntityManager from the sessionContext.
     */
    private EntityManager getEntityManager() throws UserServiceException {
        try {
            Object obj = sessionContext.lookup(unitName);

            if (obj == null) {
                throw wrapUserServiceException("The object for jndi name '" + unitName + "' doesn't exist.");
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
            throw wrapUserServiceException(e, "The jndi name for '" + unitName + "' should be EntityManager instance.");
        }
    }

    /**
     * <p>
     * Formats the <code>UserInfo</code> instance into a string representation for logging.
     * </p>
     *
     * @param userInfo
     *            The <code>UserInfo</code> instance to format.
     * @return The string representation of the <code>UserInfo</code> instance.
     *
     * @since 1.1
     */
    private String formatUserInfo(UserInfo userInfo) {

        // Return fast
        if (null == userInfo || logger == null || !logger.isEnabled(Level.INFO)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append("user id: ").append(userInfo.getUserId());
        builder.append(", handle: ").append(userInfo.getHandle());
        builder.append(", email address: ").append(userInfo.getEmailAddress());
        builder.append(", first name: ").append(userInfo.getFirstName());
        builder.append(", last name: ").append(userInfo.getLastName());
        builder.append(", group ids: ").append(userInfo.getGroupIds());
        builder.append(">");

        return builder.toString();
    }

    /**
     * <p>
     * Formats the <code>User</code> instance into a string representation for logging.
     * </p>
     *
     * @param user
     *            The <code>User</code> instance to format.
     * @return The string representation of the <code>User</code> instance.
     *
     * @since 1.1
     */
    private String formatUser(User user) {

        // Return fast
        if (null == user || logger == null || !logger.isEnabled(Level.INFO)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append("user id: ").append(user.getUserId());
        builder.append(", handle: ").append(user.getHandle());
        builder.append(", password: ");
        if (user.getPassword() == null) {
            builder.append((String) null);
        } else {
            // don't log the password
            builder.append("*****");
        }
        builder.append(", phone: ").append(user.getPhone());
        builder.append(", address: ").append(formatAddress(user.getAddress()));
        builder.append(", email address: ").append(user.getEmailAddress());
        builder.append(", first name: ").append(user.getFirstName());
        builder.append(", last name: ").append(user.getLastName());
        builder.append(", group ids: ").append(user.getGroupIds());
        builder.append(">");

        return builder.toString();
    }

    /**
     * <p>
     * Formats the <code>Address</code> instance into a string representation for logging.
     * </p>
     *
     * @param address
     *            The <code>Address</code> instance to format.
     * @return The string representation of the <code>Address</code> instance.
     *
     * @since 1.1
     */
    private String formatAddress(Address address) {

        // Return fast
        if (null == address || logger == null || !logger.isEnabled(Level.INFO)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append("line 1: ").append(address.getAddress1());
        builder.append(", line 2: ").append(address.getAddress2());
        builder.append(", line 3: ").append(address.getAddress3());
        builder.append(", country code: ").append(address.getCountryCode());
        builder.append(", city: ").append(address.getCity());
        builder.append(", state code: ").append(address.getStateCode());
        builder.append(", province: ").append(address.getProvince());
        builder.append(">");

        return builder.toString();
    }

    /**
     * <p>
     * This method used to log enter in method. It will persist both method name and it's parameters if any.
     * </p>
     *
     * @param method
     *            name of the entered method
     * @param params
     *            array containing parameters used to invoke method
     */
    private void logEnter(String method, Object... params) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Enter method UserServiceBean.{0} with parameters {1}.", method, Arrays
                    .deepToString(params));
        }
    }

    /**
     * <p>
     * Logs the returned value by a method call.
     * </p>
     *
     * @param returnValue
     *            The return value to log
     *
     * @since 1.1
     */
    private void logReturn(Object returnValue) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[Return {0}]", returnValue);
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     *
     * @param method
     *            name of the leaved method
     * @param returnValue
     *            value returned from the method
     */
    private void logExit(String method, Object returnValue) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Leave method {0} with return value {1}.", method, returnValue);
        }
    }

    /**
     * <p>
     * Log the entrance of a method.
     * </p>
     *
     * @param methodName
     *            the method name
     */
    private void logEnter(String methodName) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[Enter method: UserServiceBean." + methodName + "]");
        }
    }

    /**
     * <p>
     * Log the exit of a method.
     * </p>
     *
     * @param methodName
     *            the method name
     */
    private void logExit(String methodName) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[Exit method: " + methodName + "]");
        }
    }

    /**
     * <p>
     * Log the parameter.
     * </p>
     *
     * @param param
     *            the parameter value
     */
    private void logOneParameter(Object param) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[param1: {0}]", param);
        }
    }

    /**
     * <p>
     * Log the exception.
     * </p>
     *
     * @param e
     *            the exception to log
     * @param message
     *            the string message
     */
    private void logException(Throwable e, String message) {
        if (logger != null) {
            // This will log the message and StackTrace of the exception.
            logger.log(Level.ERROR, e, message);

            while (e != null) {
                logger.log(Level.ERROR, "INNER: " + e.getMessage());
                e = e.getCause();
            }
        }
    }

/**
     * <p>
     * Logs the given parameters.
     * </p>
     *
     * @param params the parameters
     * @since 1.1
     */
    private void logParameters(Object ... params) {
        if ((logger != null) && logger.isEnabled(Level.DEBUG)) {
            for (int i = 0; i < params.length; i++) {
                logger.log(Level.DEBUG, "[param" + i + ": {0}]", params[i]);
            }
        }
    }

    /**
     * <p>
     * Creates a <code>ContestManagementException</code> with inner exception and message. It will log the exception,
     * and set the sessionContext to rollback only.
     * </p>
     *
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     * @return the created exception
     */
    private UserServiceException wrapUserServiceException(Exception e, String message) {
        UserServiceException ce = new UserServiceException(message, e);
        logException(ce, message);
        //sessionContext.setRollbackOnly();

        return ce;
    }

    /**
     * <p>
     * Creates a <code>ContestManagementException</code> with inner exception and message. It will log the exception,
     * and set the sessionContext to rollback only.
     * </p>
     *
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     * @return the created exception
     */
    private UserServiceException wrapUserServiceException(String message) {
        UserServiceException ce = new UserServiceException(message);
        logException(ce, message);
        //sessionContext.setRollbackOnly();

        return ce;
    }
    
    /**
     * Gets User information by the given user id.
     * 
     * @param userId the id of the user.
     * @return the User instance.
     * @throws UserServiceException if any error occurs during operation.
     * @since BUGR-3739
     */
    public User getUser(long userId) throws UserServiceException {
        
        User user = new User();
        
        user.setPassword(null);
        user.setGroupIds(null);
        
        try {
            
            logEnter("getUser("+userId+")");
            logOneParameter(userId);

            EntityManager em = getEntityManager();

            // Query the basic information
            Query basicInfoquery = em.createNativeQuery(
                    "select "
                    + "user.user_id, user.handle, user.first_name, user.last_name, email.address "
                    + "from user "
                    + "left outer join email on user.user_id = email.user_id "
                    + "where user.user_id = :userId ");

            basicInfoquery.setParameter("userId", userId);

            // There could be more than one result if the user has more than one
            // email address, so just pick the first result
            List basicResult = basicInfoquery.getResultList();

            if (basicResult.isEmpty()) {
                throw wrapUserServiceException(
                        "Couldn't find the user id " + userId + " in the database.");
            }

            Object[] basicValues = (Object[]) basicResult.get(0);
            
            user.setUserId(Long.parseLong(basicValues[0].toString()));
            user.setHandle(basicValues[1].toString());
            user.setFirstName(basicValues[2].toString());
            user.setLastName(basicValues[3].toString());
            user.setEmailAddress(basicValues[4].toString());
            
            // Query the phone information
            Query phoneQuery = em.createNativeQuery(
                    "select " + "phone_number" + " from phone "
                    + " where user_id = :userId and phone_type_id = 1");
            phoneQuery.setParameter("userId", userId);
            
            Object phoneValue = phoneQuery.getSingleResult();
            
            if (phoneValue != null) {
                user.setPhone(phoneValue.toString());
            } else {
                user.setPhone(null);
            }
          
       
            // Query the address information
            Query addressQuery = em.createNativeQuery(
                    "select  a.address1, a.address2, a.address3, a.city, a.state_code, a.zip, a.country_code, a.province from address a, user_address_xref uax "
                    + " where a.address_id = uax.address_id and uax.user_id = :userId and a.address_type_id = 1");
            
            List addressResult = addressQuery.getResultList();
            
            if (!addressResult.isEmpty()) {
                
                Address a = new Address();
                
                Object[] addressValues = (Object[]) addressResult.get(0);
                
                a.setAddress1(addressValues[0] == null ? null : addressValues[0].toString());
                a.setAddress2(addressValues[1] == null ? null : addressValues[1].toString());
                a.setAddress3(addressValues[2] == null ? null : addressValues[2].toString());
                a.setCity(addressValues[3] == null ? null : addressValues[3].toString());
                a.setStateCode(addressValues[4] == null ? null : addressValues[4].toString());
                a.setZip(addressValues[5] == null ? null : addressValues[5].toString());
                a.setCountryCode(addressValues[6] == null ? null : addressValues[6].toString());
                a.setProvince(addressValues[7] == null ? null : addressValues[7].toString());
                
                // add Address instance into user
                user.setAddress(a);
                
            } else {
                // set address to null
                user.setAddress(null);
            }
         
            return user;

        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "The EntityManager is closed.");
        } catch (NoResultException e) {
            throw wrapUserServiceException(e, "No such user");
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e, "There are errors in getUserHandle.");
        } finally {
            logExit("getUser(" + userId + ")");
        }
    }
    
    /**
     * Searches User by given search string.
     * 
     * @param key the search string to use.
     * @return the List of User instances that match passed string.
     * @throws UserServiceException if any error occurs during operation.
     */
    public List<User> searchUser(String key) throws UserServiceException {
        List<User> users = new ArrayList<User>();
                
        try {
            
            logEnter("searchUser("+key+")");
            logOneParameter(key);

            EntityManager em = getEntityManager();

            // Query the basic information
            Query basicInfoquery = em.createNativeQuery(
                    "select "
                    + "user.user_id, user.handle, user.first_name, user.last_name, email.address "
                    + "from user "
                    + "left outer join email on user.user_id = email.user_id and email.primary_ind = 1 "
                    + "where UPPER(user.handle) like(UPPER(:userHandle)) ");

            basicInfoquery.setParameter("userHandle", key);

            // There could be more than one result if the user has more than one
            // email address, so just pick the first result
            List basicResult = basicInfoquery.getResultList();

            for (Object obj : basicResult) {
            	Object[] values = (Object[]) obj;
            	User user = new User();
            	user.setUserId(Long.parseLong(values[0].toString()));
                user.setHandle(values[1].toString());
                user.setFirstName(values[2].toString());
                user.setLastName(values[3].toString());
                user.setEmailAddress(values[4].toString());
                users.add(user);
            }
            
            return users;

        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "The EntityManager is closed.");
        } catch (NoResultException e) {
            throw wrapUserServiceException(e, "No such user");
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e, "There are errors in getUserHandle.");
        } finally {
            logExit("searchUser(" + key + ")");
        }
    }

    /**
     * Activate user by activation code.
     * 
     * @param activationCode
     *          activation code
     * @throws UserServiceException
     *          if any error occurs.
     */
    public void activateUser(String activationCode) throws UserServiceException {
        try {
            logEnter("activateUser("+activationCode+")");
            logOneParameter("activationCode : " + activationCode);
            
            Helper.checkNullEmpty(logger, activationCode, "activationCode");

            long userId = getCoderId(activationCode);

            EntityManager em = getEntityManager();
            Query getActivationCodeQuery = em.createNativeQuery(
            		"SELECT activation_code FROM user WHERE user_id = :userId");
            getActivationCodeQuery.setParameter("userId", userId);
            String codeFromDb = (String)getActivationCodeQuery.getSingleResult();
            if(null == codeFromDb || !codeFromDb.equals(activationCode)) {
                throw wrapUserServiceException("activation code is invalid.");
            }

            Query checkUserStatusQuery = em.createNativeQuery(
            		"SELECT status FROM user WHERE user_id = :userId");
            checkUserStatusQuery.setParameter("userId", userId);
            String userStatus = (String)checkUserStatusQuery.getSingleResult();
            if(userStatus == null || ! userStatus.equals("U")) {
                throw wrapUserServiceException("User has been activated");
            }

            Query checkEmailStatusQuery = em.createNativeQuery(
                    "SELECT status_id FROM email WHERE user_id = :userId");
            checkEmailStatusQuery.setParameter("userId", userId);
            BigDecimal emailStatus = (BigDecimal)checkEmailStatusQuery.getSingleResult();
            if(null == emailStatus || emailStatus.equals(BigDecimal.ONE)) {
                throw wrapUserServiceException("Email has been activated.");
            }

            Query activateUserQuery = em.createNativeQuery(
            		"UPDATE user SET status = 'A' WHERE user_id = :userId");
            activateUserQuery.setParameter("userId", userId);
            activateUserQuery.executeUpdate();

            Query activateEmailQuery = em.createNativeQuery(
            		"UPDATE email SET status_id = 1 WHERE user_id = :userId");
            activateEmailQuery.setParameter("userId", userId);
            activateEmailQuery.executeUpdate();

        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "The EntityManager is closed.");
        } finally {
            logExit("activateUser(" + activationCode + ")");
        }
    }
    
    /**
     * Get user id by activation code.
     * 
     * @param activationCode
     *          user's activation code
     * @return
     *          user id
     */
    private int getCoderId(String activationCode) {
        try {
            String idhash = new BigInteger(activationCode, 36).toString();
            if (idhash.length() % 2 != 0) return 0;
            String id = idhash.substring(0, idhash.length() / 2);
            String hash = idhash.substring(idhash.length() / 2);
            if (new BigInteger(new BigInteger(id).bitLength(), new Random(Long.parseLong(id))).add(new BigInteger("TopCoder", 36)).toString().endsWith(hash))
            {
                return Integer.parseInt(id);
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
    
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
    public User getUserByEmail(String emailAddress) throws UserServiceException {
        User user = null;
        try {
            logEnter("getUserByEmail("+ emailAddress + ")");
            logOneParameter(emailAddress);
            EntityManager em = getEntityManager();
            Query queryUser = em.createNativeQuery(
                    "SELECT " +
                    "user.user_id, user.handle, user.first_name, user.last_name " +
                    "FROM user " +
                    "JOIN email ON email.user_id = user.user_id and email.primary_ind = 1 " +
                    "WHERE email.address = :emailAddress");
            queryUser.setParameter("emailAddress", emailAddress);
            List result = queryUser.getResultList();
            if(null != result) {
                for(Object obj : result) {
                    Object[] values = (Object[]) obj;
                    user = new User();
                    user.setUserId(Long.parseLong(values[0].toString()));
                    user.setHandle(values[1].toString());
                    user.setFirstName(values[2].toString());
                    user.setLastName(values[3].toString());
                    user.setEmailAddress(emailAddress);
                }
            }
            return user;
        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "The EntityManager is closed.");
        } catch (NoResultException e) {
            throw wrapUserServiceException(e, "No such user");
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e, "There are errors in getUserHandle.");
        } finally {
            logExit("getUserByEmail("+ emailAddress + ")");
        }
    }
    
}
