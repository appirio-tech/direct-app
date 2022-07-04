/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
/**
 *
 */
package com.topcoder.service.facade.project.ejb;

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
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.jboss.ws.annotation.EndpointConfig;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.Project;
import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.login.AuthenticationException;
import com.topcoder.service.facade.project.DAOFault;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.UserNotFoundFault;
import com.topcoder.service.util.LoginUtil;

/**
 * <p>
 * This is an implementation of <code>ProjectServiceFacadeWebService</code> web service in form of stateless session
 * EJB. It holds a reference to {@link ProjectServiceFacade} which is delegated the fulfillment of requests.
 * </p>
 * <p>
 * This interface is a copy of the old ProjectServiceFacade interface. ProjectServiceFacade is no longer a web service
 * point. The security part is covered in this new web-service component. This bean's methods create this TCSubject
 * instance by do the login with LoginBean class and simply call the corresponding ContestServiceFacade method. This web
 * service must now be used instead of ProjectServiceFacade by web service clients.
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
@Stateless
@WebService
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
@DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
@RolesAllowed( { "Cockpit User", "Cockpit Administrator" })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProjectServiceFacadeWebServiceBean implements ProjectServiceFacadeWebServiceLocal, ProjectServiceFacadeWebServiceRemote{
    /**
     * <p>
     * A <code>ProjectServiceFacade</code> providing access to all the methods.
     * </p>
     */
    @EJB(name = "ejb/ProjectServiceFacade")
    private ProjectServiceFacade facade;
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
     * Creates a project with the given project data.
     * </p>
     * <p>
     * Note, any user can create project and the project will associate with him/her.
     * </p>
     *
     * @param projectData The project data to be created. Must not be null. The <code>ProjectData.name</code> must not
     *            be null/empty. The <code>ProjectData.projectId</code>, if any, is ignored.
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will
     *         never be null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @see ProjectService#createProject(ProjectData)
     */
    @WebMethod
    public @WebResult ProjectData createProject(@WebParam ProjectData projectData) throws PersistenceFault, IllegalArgumentFault {
        try
        {
            return facade.createProject(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), projectData);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceFault("" + e);
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceFault("" + e);
        }
        
    }

    /**
     * <p>
     * Gets the project data for all projects viewable from the calling principal.
     * </p>
     * <p>
     * Notes, for user, it will retrieve only the projects associated with him; for administrators, it will retrieve all
     * the existing projects.
     * </p>
     *
     * @return The project data for all projects viewable from the calling principal. The returned collection will not
     *         be null or contain nulls. Possibly empty.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws AuthorizationFailedFault if errors occurs during authorization of the caller user.
     * @throws UserNotFoundFault if errors occurs during authorization of the caller user.
     * @see ProjectService#getAllProjects()
     */
    @WebMethod
    public @WebResult List<ProjectData> getAllProjects() throws PersistenceFault, AuthorizationFailedFault, UserNotFoundFault {
        try
        {
            return facade.getAllProjects(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceFault("" + e);
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceFault("" + e);
        }
        
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects with the given user id. If nothing is
     * found, return an empty list.
     * <p>
     *
     * @return List of Project, if nothing is found, return an empty string
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws DAOException if any error occurs while performing this operation.
     */
    @WebMethod
    public @WebResult List<Project> getClientProjectsByUser() throws DAOFault {
        try
        {
            return facade.getClientProjectsByUser(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext));
        }
        catch (AuthenticationException e)
        {
            throw new DAOFault(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new DAOFault(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Gets the project data for the project with the given Id.
     * </p>
     * <p>
     * Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.
     * </p>
     *
     * @param projectId the ID of the project to be retrieved.
     * @return The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault if a generic persistence error.
     * @throws ProjectNotFoundFault if no project with the given ID exists.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to retrieve the project.
     * @see ProjectService#getProject(long)
     */
    @WebMethod
    public @WebResult ProjectData getProject(@WebParam long projectId) throws PersistenceFault, ProjectNotFoundFault,
            AuthorizationFailedFault {
        try
        {
            return facade.getProject(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), projectId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceFault("" + e);
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceFault("" +  e);
        }
        
    }

    /**
     * <p>
     * Gets the project data for all projects of the given user.
     * </p>
     * <p>
     * Notes, only administrator can do this.
     * </p>
     *
     * @param userId the ID of the user whose projects are to be retrieved.
     * @return The project data for all projects of the given user. The returned collection will not be null or contain
     *         nulls. Will never be empty.
     * @throws UserNotFoundFault if there are no projects linked to the given user.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws AuthorizationFailedFault should not be thrown from version 1.1. It is here only to leave the web
     *             interface unchanged.
     * @see ProjectService#getProjectsForUser(long)
     */
    @WebMethod
    public @WebResult List<ProjectData> getProjectsForUser(@WebParam long userId) throws PersistenceFault, UserNotFoundFault,
            AuthorizationFailedFault{
        try
        {
            return facade.getProjectsForUser(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), userId);
        }
        catch (AuthenticationException e)
        {
            throw new PersistenceFault("" +  e);
        }
        catch (GeneralSecurityException e)
        {
            throw new PersistenceFault("" +  e);
        }
        

        
    }

}
