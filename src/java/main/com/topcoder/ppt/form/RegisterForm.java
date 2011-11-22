/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.ppt.form;

/**
 * <p>A form bean providing the parameters for registering a user.</p>
 *
 * @author flexme
 * @version 1.0
 */
public class RegisterForm {
    /**
     * Represents the handle of the user.
     */
    private String handle;
    
    /**
     * Represents the password of the user.
     */
    private String password;
    
    /**
     * Represents the first name of the user.
     */
    private String firstName;
    
    /**
     * Represents the last name of the user.
     */
    private String lastName;
    
    /**
     * Represents the email of the user.
     */
    private String email;
    
    /**
     * Construct a new <code>RegisterForm</code> instance.
     */
    public RegisterForm() {
        
    }

    /**
     * Gets the handle of the user.
     * 
     * @return the handle of the user.
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the handle of the user.
     * 
     * @param handle the handle of the user.
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password the password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the first name of the user.
     * 
     * @return the first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     * 
     * @param firstName the first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     * 
     * @return the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * 
     * @param lastName the last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email of the user.
     * 
     * @return the email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * 
     * @param email the email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
