/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents the UserInfo entity. It holds the attributes first name, last name, email address, handle, etc.
 * <p>
 * Thread safety: This class is mutable and not thread safe.
 *
 * @author woodjhon, ernestobf
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userInfo", propOrder = { "firstName", "lastName", "emailAddress", "handle", "groupIds", "userId", "status"})
public class UserInfo implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the first name attribute of the UserInfo entity. It's set and accessed in the set/get methods. It can
     * be any value. The default value is null.
     */
    private String firstName;

    /**
     * Represents the last name attribute of the UserInfo entity. It's set and accessed in the set/get methods. It can
     * be any value.The default value is null.
     */
    private String lastName;

    /**
     * Represents the email address attribute of the UserInfo entity. It's set and accessed in the set/get methods. It
     * can be any value. The default value is null.
     */
    private String emailAddress;

    /**
     * Represents the handle attribute of the UserInfo entity. It's set and accessed in the set/get methods. It can be
     * any value. The default value is null.
     */
    private String handle;

    /**
     * Represents the group IDs attribute of the UserInfo entity. It's set and accessed in the set/get methods. It can
     * be any value. The default value is null.
     */
    private long[] groupIds;

    /**
     * Represents the user id attribute of the UserInfo entity. It's set and accessed in the set/get methods. It can be
     * any value. The default value is 0.
     */
    private long userId;


    /**
     * Represents the status attribute of the UserInfo entity. It's set and accessed in the set/get methods. It can
     * be any value.The default value is null.
     */
    private String status;

    /**
     * Creates an instance of this class. Empty constructor.
     */
    public UserInfo() {
    }

    /**
     * Retrieves the user's first name.
     *
     * @return the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     *
     * @param firstName
     *            the user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the user's last name.
     *
     * @return the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     *
     * @param lastName
     *            the user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the email address.
     *
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address.
     *
     * @param emailAddress
     *            the email address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Retrieves the user's handle.
     *
     * @return the user's handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets user's handle.
     *
     * @param handle
     *            the user's handle
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Retrieves the IDs of the groups associated to the user.
     *
     * @return the group IDs
     */
    public long[] getGroupIds() {
        return groupIds;
    }

    /**
     * Sets the IDs of the groups associated to the user.
     *
     * @param groupIds
     *            the group IDs to set to the UserInfo
     */
    public void setGroupIds(long[] groupIds) {
        this.groupIds = groupIds;
    }

    /**
     * Retrieves the user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId
     *            the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }


    /**
     * Sets the user's status
     *
     * @param status
     *            the user's status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retrieves the user's status.
     *
     * @return the user's status
     */
    public String getStatus() {
        return status;
    }
}
