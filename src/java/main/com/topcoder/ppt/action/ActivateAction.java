/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.ppt.action;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.security.ldap.LDAPClient;
import com.topcoder.service.user.UserService;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.WebConstants;

/**
 * <p>A <code>Struts</code> action to activate user account.</p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TC Registration Feature in Popup Windows)
 * @since 1.0
 */
public class ActivateAction extends ActionSupport{

    /**
     * <p>
     * Represents the serialVersionUID.
     * </p>
     */
    private static final long serialVersionUID = -7754179962819614125L;

    /**
     * <code>UserService</code> injected by Spring.
     */
    private UserService userService;

    public String execute() throws Exception {
        String code = ServletActionContext.getRequest().getParameter(WebConstants.ACTIVATION_CODE);
        if(null == code || code.trim().length() <= 0) {
            ServletActionContext.getRequest().setAttribute("errorMessage", 
                    "Activation Code is null or empty.");
            return ERROR;
        }
        try {
            userService.activateUser(code);
            
            LDAPClient ldapClient = new LDAPClient();
            ldapClient.connect();
            ldapClient.activateTopCoderMemberProfile(getCoderId(code));
            ldapClient.disconnect();

            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            ServletActionContext.getRequest().setAttribute("errorMessage", 
                    e.getMessage());
            return ERROR;
        }
    }

    /**
     * <p>
     * Getter of userService field.
     * </p>
     * @return the userService
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * <p>
     * Setter of userService field.
     * </p>
     * @param userService the userService to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
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
}
