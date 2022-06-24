/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This is a bean class to hold the credit card details like number, expiry
 * date, payer details etc. This class simply captures the information from
 * client. Client would have done the validation of various fields. It doesn't
 * perform any validation over that.
 * </p>
 * ---------update in version 1.1--------- Add some comments for the setter and
 * getter of amount.
 * 
 * @author shailendra_80
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "creditCardPaymentData", propOrder = { "cardNumber",
        "cardType", "cardExpiryMonth", "cardExpiryYear", "firstName",
        "lastName", "address", "city", "state", "zipCode", "country",
        "phone", "email", "ipAddress", "sessionId", "amount", "comment1",
        "comment2", "csc" })
public class CreditCardPaymentData extends PaymentData {
    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The credit card number.
     */
    private String cardNumber;

    /**
     * The credit card type.
     */
    private String cardType;

    /**
     * The card expiry month.
     */
    private String cardExpiryMonth;

    /**
     * The card expiry year.
     */
    private String cardExpiryYear;

    /**
     * The first name of the payer.
     */
    private String firstName;

    /**
     * The last name of the payer.
     */
    private String lastName;

    /**
     * The address (usually street address) of the payer.
     */
    private String address;

    /**
     * The city of the payer.
     */
    private String city;

    /**
     * The 2 character state code of the payer.
     */
    private String state;

    /**
     * The zip code of the payer's location.
     */
    private String zipCode;

    /**
     * The 2 character country code of the payer.
     */
    private String country;

    /**
     * The phone number of the payer.
     */
    private String phone;

    /**
     * The email address of the payer.
     */
    private String email;

    /**
     * The ip address from the payer's current session.
     */
    private String ipAddress;

    /**
     * The session id from the payer's current session.
     */
    private String sessionId;

    /**
     * The amount that need to be charged to the credit card.
     */
    private String amount;

    /**
     * The comment line 1.
     * 
     * @since BUGR-1239
     */
    private String comment1;

    /**
     * The comment line 2.
     * 
     * @since BUGR-1239
     */
    private String comment2;

    /**
     * The card security code
     * 
     * @since BUGR-1398
     */
    private String csc;

    /**
     * A do nothing default constructor.
     */
    public CreditCardPaymentData() {
    }

    /**
     * <p>
     * Gets the credit card number of the payer.
     * </p>
     * 
     * @return the credit card number of the payer.
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * <p>
     * Sets the credit car number of the payer.
     * </p>
     * 
     * @param cardNumber
     *            the credit card number of the payer.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * <p>
     * Gets the credit card type of the payer.
     * </p>
     * 
     * @return the credit card type of the payer.
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * <p>
     * Sets the credit card type of the payer.
     * </p>
     * 
     * @param cardType
     *            the credit card type of the payer.
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * <p>
     * Gets the credit card expiry month.
     * </p>
     * 
     * @return the credit card expiry month.
     */
    public String getCardExpiryMonth() {
        return cardExpiryMonth;
    }

    /**
     * <p>
     * Sets the credit card expiry month.
     * </p>
     * 
     * @param cardExpiryMonth
     *            the credit card expiry month.
     */
    public void setCardExpiryMonth(String cardExpiryMonth) {
        this.cardExpiryMonth = cardExpiryMonth;
    }

    /**
     * <p>
     * Gets the credit card expiry year.
     * </p>
     * 
     * @return the credit card expiry year.
     */
    public String getCardExpiryYear() {
        return cardExpiryYear;
    }

    /**
     * <p>
     * Sets the credit card expiry year.
     * </p>
     * 
     * @param cardExpiryYear
     *            the credit card expiry year.
     */
    public void setCardExpiryYear(String cardExpiryYear) {
        this.cardExpiryYear = cardExpiryYear;
    }

    /**
     * <p>
     * Gets the first name of the payer.
     * </p>
     * 
     * @return the first name of the payer.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * <p>
     * Sets the first name of the payer.
     * </p>
     * 
     * @param firstName
     *            the first name of the payer.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * <p>
     * Gets the last name of the payer
     * </p>
     * 
     * @return the last name of the payer.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * <p>
     * Sets the last name of the payer.
     * </p>
     * 
     * @param lastName
     *            the last name of the payer.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * <p>
     * Gets the zip code of the payer.
     * </p>
     * 
     * @return the zip code of the payer.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * <p>
     * Sets the zip code of the payer.
     * </p>
     * 
     * @param zipCode
     *            the zip code of the payer.
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * <p>
     * Gets the address of the payer.
     * </p>
     * 
     * @return the address of the payer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * <p>
     * Sets the address of the payer.
     * </p>
     * 
     * @param address
     *            the address of the payer.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * <p>
     * Gets the city of the payer.
     * </p>
     * 
     * @return the city of the payer.
     */
    public String getCity() {
        return city;
    }

    /**
     * <p>
     * Sets the city of the payer.
     * </p>
     * 
     * @param city
     *            the city of the payer.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * <p>
     * Gets the state of the payer.
     * </p>
     * 
     * @return the state of the payer.
     */
    public String getState() {
        return state;
    }

    /**
     * <p>
     * Sets the state of the payer.
     * </p>
     * 
     * @param state
     *            the state of the payer.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * <p>
     * Gets the country of the payer.
     * </p>
     * 
     * @param country
     *            the country of the payer.
     */
    public String getCountry() {
        return country;
    }

    /**
     * <p>
     * Sets the country of the payer.
     * </p>
     * 
     * @param country
     *            the country of the payer.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * <p>
     * Gets the phone of the payer.
     * </p>
     * 
     * @return the phone of the payer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * <p>
     * Sets the phone of the payer.
     * </p>
     * 
     * @param phone
     *            the phone of the payer.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * <p>
     * Gets the email of the payer.
     * </p>
     * 
     * @return the email of the payer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>
     * Sets the email of the payer.
     * </p>
     * 
     * @param email
     *            the email of the payer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>
     * Gets the ip address of the payer.
     * </p>
     * 
     * @return the ip address of the payer.
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * <p>
     * Sets the ip address of the payer.
     * </p>
     * 
     * @param ipAddress
     *            the ip address of the payer.
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * <p>
     * Gets the session id of the payer.
     * </p>
     * 
     * @return the session id of the payer.
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * <p>
     * Sets the session id of the payer.
     * </p>
     * 
     * @param sessionId
     *            the session id of the payer.
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * get amount of this payment
     * 
     * @return amount value
     */
    public String getAmount() {
        return this.amount;
    }

    /**
     * set amount of this payment
     * 
     * @param amount
     *            The amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * <p>
     * Gets the comment line 1.
     * </p>
     * 
     * @return the comment line 1.
     * @since BUGR-1239
     */
    public String getComment1() {
        return comment1;
    }

    /**
     * <p>
     * Sets the comment line 1.
     * </p>
     * 
     * @param comment1
     *            the comment line 1
     * @since BUGR-1239
     */
    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    /**
     * <p>
     * Gets the comment line 2.
     * </p>
     * 
     * @return the comment line 2.
     * @since BUGR-1239
     */
    public String getComment2() {
        return comment2;
    }

    /**
     * <p>
     * Sets the comment line 2.
     * </p>
     * 
     * @param comment2
     *            the comment line 2
     * @since BUGR-1239
     */
    public void setComment2(String comment2) {
        this.comment2 = comment2;
    }

    /**
     * <p>
     * Gets the card security code.
     * </p>
     * 
     * @return the card security code.
     * @since BUGR-1398
     */
    public String getCsc() {
        return csc;
    }

    /**
     * <p>
     * Sets the card security code.
     * </p>
     * 
     * @param csc
     *            card security code.
     * @since BUGR-1398
     */
    public void setCsc(String csc) {
        this.csc = csc;
    }
}
