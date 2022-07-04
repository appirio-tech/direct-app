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
 * Represents the Address entity. It holds the attributes address1, address2, address3, country code, etc. It's mutable
 * and not thread safe.
 *
 * @author woodjhon, ernestobf
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "address", propOrder = { "address1", "address2", "address3", "countryCode", "city", "stateCode", 
                                         "province", "zip"})
public class Address implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the address line 1.
     */
    private String address1;

    /**
     * Represents the address line 2.
     */
    private String address2;

    /**
     * Represents the address line 3.
     */
    private String address3;

    /**
     * Represents this address' country code.
     */
    private String countryCode;

    /**
     * Represents this address' city.
     */
    private String city;

    /**
     * Represents this address' state code.
     */
    private String stateCode;

    /**
     * Represents this address' province.
     */
    private String province;

    /**
     * Represents this address' ZIP code.
     */
    private String zip;

    /**
     * Creates an instance of this class. Empty constructor.
     */
    public Address() {
    }

    /**
     * Retrieves the address line 1.
     *
     * @return the address line 1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the address line 1.
     *
     * @param address1
     *            the address line 1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Retrieves the address line 2.
     *
     * @return the address line 2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the address line 2.
     *
     * @param address2
     *            the address line 2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Retrieves the address line 3.
     *
     * @return the address line 3
     */
    public String getAddress3() {
        return address3;
    }

    /**
     * Sets the address line 3.
     *
     * @param address3
     *            the address line 3
     */
    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    /**
     * Retrieves the country code.
     *
     * @return the country code
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the country code.
     *
     * @param countryCode
     *            the country code
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Retrieves the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city
     *            the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Retrieves the state code.
     *
     * @return the state code
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * Sets the state code.
     *
     * @param stateCode
     *            the state code
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    /**
     * Retrieves the province.
     *
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets the province.
     *
     * @param province
     *            the province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Retrieves the ZIP code.
     *
     * @return the ZIP code.
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the ZIP code.
     *
     * @param zip
     *            the ZIP code.
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
}
