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
 * <p>
 * Represents the User entity. It holds the attributes password, phone, and address. It's mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, ernestobf
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = { "password", "phone", "address", "regSource"})
public class User extends UserInfo implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the password attribute of the User entity. It's set and accessed in the set/get methods. It can be any
     * value. The default value is null.
     */
    private String password;

    /**
     * Represents the phone attribute of the User entity. It's set and accessed in the set/get methods. It can be any
     * value. The default value is null.
     */
    private String phone;

    /**
     * Represents the address attribute of the User entity. It's set and accessed in the set/get methods. It can be any
     * value. The default value is null.
     */
    private Address address;
	
	/**
     * Represents the reg source
     */
    private String regSource;

    /**
     * Creates an instance of this class. Empty constructor.
     */
    public User() {
    }

    /**
     * Retrieves the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password
     *            the password to set to the User
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the phone number.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number.
     *
     * @param phone
     *            the phone to set to the User
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Retrieves the user's address.
     *
     * @return the user's address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the user's address.
     *
     * @param address
     *            the user's address
     */
    public void setAddress(Address address) {
        this.address = address;
    }
	
	 /**
     * Retrieves the regSource .
     *
     * @return the regSource
     */
    public String getRegSource() {
        return regSource;
    }

    /**
     * Sets the regSource.
     *
     * @param regSource
     *            the regSource to set to the User
     */
    public void setRegSource(String regSource) {
        this.regSource = regSource;
    }
}
