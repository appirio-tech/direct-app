/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.admin.ejb;

import java.util.List;
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
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.jboss.ws.annotation.EndpointConfig;

import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.login.AuthenticationException;
import com.topcoder.service.facade.admin.AdminServiceFacade;
import com.topcoder.service.facade.admin.AdminServiceFacadeException;
import com.topcoder.service.util.LoginUtil;
/**
 * <p>
 * This is the EJB implementation class for interface AdminServiceFacadeWebService.
 * </p>
 *  <p>
 * This interface is a copy of the old AdminServiceFacade interface. AdminServiceFacade is no longer a web service
 * point. The security part is covered in this new web-service component. This bean's methods create this TCSubject
 * instance by do the login with LoginBean class and simply call the corresponding AdminServiceFacade method. This web
 * service must now be used instead of AdminServiceFacade by web service clients.
 * </p>
 * <p>
 * It is stateless session bean and using CMT. It has the annotations:
 *  &#064;Stateless
 *  &#064;WebService
 *  &#064;EndpointConfig(configName = "Standard WSSecurity Endpoint")
 *  &#064;DeclareRoles( { "TC Accounting" })
 *  &#064;RolesAllowed( { "TC Accounting" })
 *  &#064;TransactionManagement(TransactionManagementType.CONTAINER)
 *  &#064;TransactionAttribute(TransactionAttributeType.REQUIRED)
 * </p>
 *
 * @author waits
 * @version 1.0
 * @since Cockpit Security Facade Assembly V1.0
 */

@RolesAllowed( { "TC Accounting" })
@DeclareRoles( { "TC Accounting" })
@Stateless
@WebService
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AdminServiceFacadeWebServiceBean implements AdminServiceFacadeWebServiceLocal,
        AdminServiceFacadeWebServiceRemote {
    /**
     * <p>
     * A <code>AdminServiceFacade</code> providing access to all the methods.
     * </p>
     */
    @EJB(name = "ejb/AdminServiceFacade")
    private AdminServiceFacade facade;
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
     * Gets all contest fees by project id.
     *
     * @param projectId the project id
     * @return the list of project contest fees for the given project id
     * @throws AdminServiceFacadeException if any error occurs during the service call
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    @WebMethod
    public List<ProjectContestFee> getContestFeesByProject(long projectId) throws AdminServiceFacadeException {
        try
        {
            return facade.getContestFeesByProject(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), projectId);
        }
        catch (AuthenticationException e)
        {
            throw new AdminServiceFacadeException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new AdminServiceFacadeException(e.getMessage(), e);
        }
        
    }

    /**
     * Saves contest fees. It will refresh contest fees for the given project.
     *
     * @param contestFees the contest fees
     * @param projectId the project id
     * @throws AdminServiceFacadeException if any error occurs during the service call
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    @WebMethod
    public void saveContestFees(List<ProjectContestFee> contestFees, long projectId)
            throws AdminServiceFacadeException{
        try
        {
            facade.saveContestFees(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestFees, projectId);
        }
        catch (AuthenticationException e)
        {
            throw new AdminServiceFacadeException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new AdminServiceFacadeException(e.getMessage(), e);
        }
        
    }

    /**
     * Searches projects by project name. The name search is case insensitive and also allows for partial name search.
     * The name doesn't allow wildcard characters: *, %. If it is null or empty, all projects will be returned.
     *
     * @param projectName the project name
     * @return projects matched with the project name
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws AdminServiceFacadeException if any error occurs during the service call
     */
    @WebMethod
    public List<Project> searchProjectsByProjectName(String projectName) throws AdminServiceFacadeException {
        try
        {
            return facade.searchProjectsByProjectName(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), projectName);
        }
        catch (AuthenticationException e)
        {
            throw new AdminServiceFacadeException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new AdminServiceFacadeException(e.getMessage(), e);
        }
        
    }

    /**
     * Searches projects by client name. The name search is case insensitive and also allows for partial name search.
     * The name doesn't allow wildcard characters: *, %. If it is null or empty, all projects will be returned.
     *
     * @param clientName the client name
     * @return projects matched with the client name
     * @throws AdminServiceFacadeException if any error occurs during the service call
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    @WebMethod
    public List<Project> searchProjectsByClientName(String clientName) throws AdminServiceFacadeException {
        try
        {
            return facade.searchProjectsByClientName(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), clientName);
        }
        catch (AuthenticationException e)
        {
            throw new AdminServiceFacadeException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new AdminServiceFacadeException(e.getMessage(), e);
        }
        
    }

}
