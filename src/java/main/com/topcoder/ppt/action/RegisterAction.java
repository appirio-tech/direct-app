/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.ppt.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.topcoder.ppt.form.RegisterForm;
import com.topcoder.security.ldap.LDAPClient;
import com.topcoder.security.ldap.LDAPClientException;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserInfo;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.shared.util.ApplicationServer;
import com.topcoder.shared.util.DBMS;
import com.topcoder.shared.util.EmailEngine;
import com.topcoder.shared.util.TCSEmailMessage;
import com.topcoder.web.common.WebConstants;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for register a new user.</p>
 * 
 * <p>
 * Version 1.1 (Module Assembly - TC Registration Feature in Popup Windows) change notes:
 * <ol>
 *  <li>Updated {@link #executeAction()} method to send activation email.</li>
 *  <li>Updated {@link #addTopCoderMemberProfile(long, String, String)} method.</li>
 *  <li>Added {@link #getActivationCode(long)} method.</li>
 *  <li>Added {@link #executeSqlByUserId(long, String, Connection)} method.</li>
 *  <li>Added {@link #sendActivationEmail(String, String, String, String, String)} method.</li>
 *  <li>Added {@link #sendEmail(String, String, String, String)} method.</li>
 *  <li>Added {@link #readFileAsString(String)} method.</li>
 *  <li>Added {@link #emailExists(String)} method.</li>
 * </ol>
 * </p>
 *
 * @author flexme, TCSASSEMBLER
 * @version 1.1 (Module Assembly - TC Registration Feature in Popup Windows)
 * @since 1.0
 */
public class RegisterAction extends BaseAjaxAction {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 1906721920613739600L;

    /**
     * Represents the name of present activation action.
     */
    private static final String PPT_ACTIVATE_ACTION_NAME = "/present/activate.action";

    /**
     * Represents the name of mobile activation name.
     */
    private static final String MOBILE_ACTICATE_ACTION_NAME = "/mobile/activate.action";

    /**
     * Represents the sql statement to unactivate user email.
     */
    private static final String UNACTIVATE_USER_EMAIL = 
            "UPDATE email SET status_id = 2 WHERE user_id = ?";

    /**
     * Represents the sql statement to update user's activation code.
     */
    private static final String SAVE_ACTIVATION_CODE_SQL = 
            "UPDATE user SET activation_code = ? WHERE user_id = ?";

    /**
     * Represents the name of the /present registration source.
     */
    private static final String PPT_REGISTER_SOURCE_NAME = "present";

    /**
     * Represents the name of the /mobile registration source.
     */
    private static final String MOBILE_REGISTER_SOURCE_NAME = "mobile";
	
	/**
     * Represents the default registration source.
     */
    private static final String HOME_REGISTER_SOURCE_NAME = "home";

    /**
     * A <code>RegisterForm</code> providing the register data submitted by user.
     */
    private RegisterForm formData;

    /**
     * The <code>UserService</code> instance. It will be injected by Spring.
     */
    private UserService userService;

    /**
     * Represents activation email body template file.
     */
    private String activationEmailBodyTemplateFile;

    /**
     * Represents activation email subject.
     */
    private String activationEmailSubject;

    /**
     * Represents activation email from address.
     */
    private String activationEmailFromAddress;
    
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

        if(emailExists(formData.getEmail())) {
            addFieldError("email", "The email address already exists");
            return ;
        }

        User user = new User();
        user.setEmailAddress(formData.getEmail());
        user.setFirstName(formData.getFirstName());
        user.setLastName(formData.getLastName());
        user.setHandle(formData.getHandle());
        user.setPassword(formData.getPassword());
        user.setStatus("U");
        if (formData.getModuleFrom().equals("ppt")) {
            user.setRegSource(PPT_REGISTER_SOURCE_NAME);
        } else if (formData.getModuleFrom().equals("mobile")) {
            user.setRegSource(MOBILE_REGISTER_SOURCE_NAME);
        } else {
			user.setRegSource(HOME_REGISTER_SOURCE_NAME);
		}
        long userId = userService.registerUser(user);

        //generate and save activation code
        String activationCode = getActivationCode(userId);

        Connection conn = null;
        try {
            conn = DBMS.getConnection(DBMS.COMMON_OLTP_DATASOURCE_NAME);
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(SAVE_ACTIVATION_CODE_SQL);
    
            ps.setString(1, activationCode);
            ps.setLong(2, userId);
            ps.executeUpdate();

            //un-activate user email
            executeSqlByUserId(userId, UNACTIVATE_USER_EMAIL, conn);

            conn.commit();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != conn) {
                conn.close();
            }
        }
        // add user to LADP server
        addTopCoderMemberProfile(userId, user.getHandle(), user.getPassword());

        sendActivationEmail(activationEmailSubject, activationCode, activationEmailBodyTemplateFile, 
                user.getEmailAddress(), activationEmailFromAddress);

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
            ldapClient.addTopCoderMemberProfile(userId, handle, password, "U");
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

    /**
     * Check if email address has been in use
     * 
     * @param emailAddress
     *          the email address
     * @return
     *          an boolean show if the email address already exists
     */
    private boolean emailExists(String emailAddress) {
        try {
            // get user info for requester
            User user = userService.getUserByEmail(emailAddress);
            if (user == null) {
                return false;
            }
            return true;
        } catch (UserServiceException e) {
            return false;
        }
    }
    
    /**
     * <p>
     * Getter of activationEmailBodyTemplateFile field.
     * </p>
     * @return the activationEmailBodyTemplateFile
     */
    public String getActivationEmailBodyTemplateFile() {
        return activationEmailBodyTemplateFile;
    }

    /**
     * <p>
     * Setter of activationEmailBodyTemplateFile field.
     * </p>
     * @param activationEmailBodyTemplateFile the activationEmailBodyTemplateFile to set
     */
    public void setActivationEmailBodyTemplateFile(String activationEmailBodyTemplateFile) {
        this.activationEmailBodyTemplateFile = activationEmailBodyTemplateFile;
    }

    /**
     * <p>
     * Getter of activationEmailSubject field.
     * </p>
     * @return the activationEmailSubject
     */
    public String getActivationEmailSubject() {
        return activationEmailSubject;
    }

    /**
     * <p>
     * Setter of activationEmailSubject field.
     * </p>
     * @param activationEmailSubject the activationEmailSubject to set
     */
    public void setActivationEmailSubject(String activationEmailSubject) {
        this.activationEmailSubject = activationEmailSubject;
    }

    /**
     * <p>
     * Getter of activationEmailFromAddress field.
     * </p>
     * @return the activationEmailFromAddress
     */
    public String getActivationEmailFromAddress() {
        return activationEmailFromAddress;
    }

    /**
     * <p>
     * Setter of activationEmailFromAddress field.
     * </p>
     * @param activationEmailFromAddress the activationEmailFromAddress to set
     */
    public void setActivationEmailFromAddress(String activationEmailFromAddress) {
        this.activationEmailFromAddress = activationEmailFromAddress;
    }

    /**
     * Get user activation code.
     * 
     * @param coderId
     *          user id
     * @return
     *          randomly generated activation code
     */
    private String getActivationCode(long coderId) {
        String id = Long.toString(coderId);
        String hash = new BigInteger(new BigInteger(id).bitLength(), new Random(coderId)).add(new BigInteger("TopCoder", 36)).toString();
        while (hash.length() < id.length()) {
            hash = "0" + hash;
        }
        hash = hash.substring(hash.length() - id.length());
        return new BigInteger(id + hash).toString(36).toUpperCase();
    }

    /**
     * Sends the activation email for a newly registered user.
     *
     * @param subject the email subject
     * @param activationCode the activation code
     * @param activationEmailBodyTemplateFile the activation email body template file
     * @param toAddress the to address
     * @param fromAddress the from address
     * @throws Exception if any exception occurs while sending the email
     */
    private void sendActivationEmail(String subject, String activationCode,
        String activationEmailBodyTemplateFile, String toAddress, String fromAddress) throws Exception {
        TCSEmailMessage mail = new TCSEmailMessage();
        mail.setSubject(subject);
        String url = ApplicationServer.SERVER_NAME;
        
        if(formData.getModuleFrom().equals("ppt")) {
            url += PPT_ACTIVATE_ACTION_NAME + "?" + 
                    WebConstants.ACTIVATION_CODE + "=" + activationCode;
        } else if(formData.getModuleFrom().equals("mobile")){
            url += MOBILE_ACTICATE_ACTION_NAME + "?" + 
                    WebConstants.ACTIVATION_CODE + "=" + activationCode;
        } else if(formData.getModuleFrom().equals("home")){
            url += PPT_ACTIVATE_ACTION_NAME + "?" + 
                    WebConstants.ACTIVATION_CODE + "=" + activationCode;
        }
        String msg = readFileAsString(activationEmailBodyTemplateFile);
        msg = msg.replace("%ACTIVATION_CODE%", activationCode).replace("%ACTIVATION_URL%", url);
        sendEmail(subject, msg, toAddress, fromAddress);
    }

    /**
     * Sends an email.
     *
     * @param subject the subject of email
     * @param content the email content
     * @param toAddress the to address
     * @param fromAddress the from address
     * @throws Exception if any errors occurs while sending email
     */
    private void sendEmail(String subject, String content, String toAddress, String fromAddress)
        throws Exception {
        TCSEmailMessage mail = new TCSEmailMessage();
        mail.setSubject(subject);
        mail.setBody(content);
        mail.addToAddress(toAddress, TCSEmailMessage.TO);
        mail.setFromAddress(fromAddress);
        EmailEngine.send(mail);
    }

    /**
     * Read file to a String.
     * 
     * @param filePath
     *          path of input file
     * @return
     *          content of this file
     * @throws Exception
     *          if any exception occurs
     */
    private String readFileAsString(String filePath) throws Exception {
        StringBuilder buf = new StringBuilder();
        BufferedReader in =
            new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
        try {
            String s;
            while ((s = in.readLine()) != null) {
                buf.append(s + System.getProperty("line.separator"));
            }
            return buf.toString();
        } finally {
            in.close();
        }
    }

    /**
     * Execute sql statement using only user_id
     * 
     * @param userId
     *          user id
     * @param sql
     *          sql statement
     * @param conn
     *          connection
     * @throws Exception
     *          if any exception occurs
     */
    private void executeSqlByUserId(long userId, String sql, Connection conn) throws Exception {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, userId);
        ps.executeUpdate();
        ps.close();
    }
}
