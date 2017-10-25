/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.user.ejb;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebService;

import org.jboss.ws.annotation.EndpointConfig;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.login.AuthenticationException;
import com.topcoder.service.facade.user.UserServiceFacade;
import com.topcoder.service.facade.user.UserServiceFacadeException;
import com.topcoder.service.facade.user.UserServiceFacadeWebServiceLocal;
import com.topcoder.service.facade.user.UserServiceFacadeWebServiceRemote;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.service.util.LoginUtil;
/**
 * <p>
 * This is the EJB implementation class for interface UserServiceFacadeWebService.
 * </p>
 *  <p>
 * This interface is a copy of the old UserServiceFacade interface. UserServiceFacade is no longer a web service
 * point. The security part is covered in this new web-service component. This bean's methods create this TCSubject
 * instance by do the login with LoginBean class and simply call the corresponding UserServiceFacade method. This web
 * service must now be used instead of UserServiceFacade by web service clients.
 * </p>
 * <p>
 * It is stateless session bean and using CMT. It has the annotations:
 *  &#064;Stateless
 *  &#064;WebService
 *  &#064;EndpointConfig(configName = "Standard WSSecurity Endpoint")
 *  &#064;DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
 *  &#064;RolesAllowed( { "Cockpit User", "Cockpit Administrator" })
 *  &#064;TransactionManagement(TransactionManagementType.CONTAINER)
 *  &#064;TransactionAttribute(TransactionAttributeType.REQUIRED)
 * </p>
 *
 * @author waits
 * @version 1.0
 * @since Cockpit Security Facade Assembly V1.0
 */
@RolesAllowed( { "Cockpit User", "Cockpit Administrator" })
@DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
@Stateless
@WebService
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserServiceFacadeWebServiceBean implements UserServiceFacadeWebServiceLocal,
        UserServiceFacadeWebServiceRemote {
    /**
     * <p>
     * A <code>UserServiceFacade</code> providing access to all the methods.
     * </p>
     */
    @EJB(name = "ejb/UserServiceFacade")
    private UserServiceFacade facade;
    /**
     * <p>
     * The name of the resource bundle where the login bean URL is.
     * </p>
     */
    @Resource(name = "loginBeanResourceBundleBaseName")
    private String loginBeanResourceBundleBaseName;

    /**
     * <p>
     * The name of the resource bundle property which contains the login bean PROVIDER_URL.
     * </p>
     */
    @Resource(name = "loginBeanUrlPropertyName")
    private String loginBeanUrlPropertyName;
    /**
     * <p>
     * The Data-Source JNDI name of LoginBean.
     * </p>
     */
    @Resource(name = "loginBeanDSJndiName")
    private String loginBeanDSJndiName;
    /**
     * <p>
     * Represents the session context of this bean.
     * </p>
     * <p>
     * It is a resource injected by the EJB container and will not be null while client calls are being executed.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;
    /**
     * <p>
     * This is the login bean URL. It is read in the initialize method.
     * </p>
     */
    private String loginBeanURL;
    /**
     * <p>
     * Checks the resource injection and loginBean URL configuration as initialization.
     * </p>
     *
     * @throws IllegalStateException if any resource or loginBeanUrl is not injected or not configed properly
     */
    @PostConstruct
    protected void init() {
        checkResource(loginBeanResourceBundleBaseName, "loginBeanResourceBundleBaseName");
        checkResource(loginBeanUrlPropertyName, "loginBeanUrlPropertyName");
        checkResource(loginBeanDSJndiName, "loginBeanDSJndiName");
        try {
            ResourceBundle rb = ResourceBundle.getBundle(loginBeanResourceBundleBaseName);
            loginBeanURL = rb.getString(loginBeanUrlPropertyName);
        } catch (Exception e) {
            throw new IllegalStateException("Fail to init the EJB as: " + e.getMessage());
        }
        checkResource(loginBeanURL, "loginBeanURL");
    }
    /**
     * Checks if the value is not null and not empty.
     *
     * @param value the value to check
     * @param name the name of the value
     * @throws IllegalStateException if the value is null or empty
     */
    private static void checkResource(String value, String name) {
        if (value == null || value.trim().length() == 0) {
            throw new IllegalStateException("The resource " + name + " does not injected or configed properly.");
        }
    }    
    /**
     * <p>
     * Creates Confluence User (if does not exist already) and gets the email address of it from the Confluence Service.
     * Implementation should create the Confluence user if the user does not exist already.
     * </p>
     *
     * @param handle the user handle for which to retrieve the email address from Confluence Service.
     * @return the email address of the Confluence user for the given handle.
     * @throws UserServiceException if any error occurs when getting user details.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public String getConfluenceUser(String handle) throws UserServiceFacadeException {
        try
        {
            return facade.getConfluenceUser(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), handle);
        }
        catch (AuthenticationException e)
        {
            throw new UserServiceFacadeException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new UserServiceFacadeException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Creates Jira User (if does not exist already) and gets the email address of it from the Jira Service.
     * Implementation should create the Jira user if the user does not exist already.
     * </p>
     *
     * @param handle the user handle for which to retrieve the email address from Jira Service.
     * @return the email address of the Jira user for the given handle.
     * @throws UserServiceException if any error occurs when getting user details.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public String getJiraUser(String handle) throws UserServiceFacadeException {
        try
        {
            return facade.getJiraUser(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), handle);
        }
        catch (AuthenticationException e)
        {
            throw new UserServiceFacadeException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new UserServiceFacadeException(e.getMessage(), e);
        }
        
    }

}
