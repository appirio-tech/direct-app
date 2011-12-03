/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.ppt.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.topcoder.ppt.form.RegisterForm;
import com.topcoder.security.ldap.LDAPClient;
import com.topcoder.security.ldap.LDAPClientException;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserInfo;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for register a new user.</p>
 *
 * @author flexme
 * @version 1.0
 */
public class RegisterAction extends BaseAjaxAction {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 1906721920613739600L;
    
    /**
     * A <code>RegisterForm</code> providing the register data submitted by user.
     */
    private RegisterForm formData;
    
    /**
     * The <code>UserService</code> instance. It will be injected by Spring.
     */
    private UserService userService;
    
    /**
     * Sets the <code>UserService</code> instance.
     * 
     * @param userService he <code>UserService</code> instance.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets the register data submitted by user.
     * 
     * @return the register data submitted by user.
     */
    public RegisterForm getFormData() {
        return formData;
    }

    /**
     * Sets the register data submitted by user.
     * 
     * @param formData the register data submitted by user.
     */
    public void setFormData(RegisterForm formData) {
        this.formData = formData;
    }

    /**
     * Construct a new <code>RegisterAction</code> instance.
     */
    public RegisterAction() {
        super();
        this.formData = new RegisterForm();
    }
    
    /**
     * Handles the incoming request. Register a new user.
     * 
     * @throws Exception if any error occurs
     */
    public void executeAction() throws Exception {
        // check the parameters
        checkRequired("firstName", "first name", formData.getFirstName());
        checkRequired("lastName", "last name", formData.getLastName());
        checkRequired("handle", "handle", formData.getHandle());
        checkRequired("email", "email", formData.getEmail());
        checkRequired("regPassword", "password", formData.getPassword());
        
        HttpSession session = ServletActionContext.getRequest().getSession();
        String randomString = (String) session.getAttribute("captcha_str");
        session.removeAttribute("captcha_str");
        if (randomString == null || randomString.compareToIgnoreCase(formData.getVerificationCode()) != 0) {
            addFieldError("veriCode", "Verification code doesn't match");
        }
        if (hasFieldErrors()) {
            return;
        }
        
        // check whether the handle has already exists
        if (handleExists(formData.getHandle())) {
            addFieldError("handle", "The handle already exists");
            return;
        }
        
        User user = new User();
        user.setEmailAddress(formData.getEmail());
        user.setFirstName(formData.getFirstName());
        user.setLastName(formData.getLastName());
        user.setHandle(formData.getHandle());
        user.setPassword(formData.getPassword());
        long userId = userService.registerUser(user);
        
        // add user to LADP server
        addTopCoderMemberProfile(userId, user.getHandle(), user.getPassword());
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("userId", userId);
        setResult(result);
    }
    
    /**
     * Checks the parameter value is presented. If not, a field error message will be added.
     * 
     * @param fieldName the filed name of the parameter value
     * @param message the message to be displayed for end user
     * @param value the parameter value
     */
    private void checkRequired(String fieldName, String message, String value) {
        if (value == null || value.trim().length() == 0) {
            addFieldError("field", "Please fill your " + message);
        }
    }
    
    /**
     * Updates the <code>status</code> attributed for <code>LDAP</code> entry corresponding to specified
     * <code>TopCoder</code> member profile with <code>Active</code> status.
     *
     * @param userId a <code>long</code> providing the user ID.
     * @throws LDAPClientException if an unexpected error occurs while communicating to <code>LDAP</code> server.
     */
    private static void addTopCoderMemberProfile(long userId, String handle, String password) throws Exception {
        LDAPClient ldapClient = new LDAPClient();
        try {
            ldapClient.connect();
            ldapClient.addTopCoderMemberProfile(userId, handle, password, "A");
        } catch (LDAPClientException e) {
                LOGGER.error("Failed to connect to LDAP server while activating user account. "
                        + "The process is not interrupted." + e, e);
                throw e;
        } finally {
            try {
                ldapClient.disconnect();
            } catch (LDAPClientException e) {
                LOGGER.error("Failed to disconnect from LDAP server while activating user account. "
                          + "The process is not interrupted.", e);     
            }
        }
    }
    
    /**
     * Check if handle exists.
     *
     * @param handle
     *            the handle name
     * @return an boolean show if the handle already exists
     */
    private boolean handleExists(String handle) {
        try {
            // get user info for requester
            UserInfo userInfo = userService.getUserInfo(handle);
            if (userInfo == null) {
                return false;
            }
            return true;
        } catch (UserServiceException e) {
            return false;
        }
    }
}
