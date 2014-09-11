/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.ejb;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebService;

import org.jboss.ws.annotation.EndpointConfig;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.login.AuthenticationException;
import com.topcoder.service.pipeline.CapacityData;
import com.topcoder.service.pipeline.CommonPipelineData;
import com.topcoder.service.pipeline.ContestPipelineServiceException;
import com.topcoder.service.pipeline.PipelineServiceFacade;
import com.topcoder.service.pipeline.entities.CompetitionChangeHistory;
import com.topcoder.service.pipeline.searchcriteria.ContestsSearchCriteria;
import com.topcoder.service.pipeline.searchcriteria.DateSearchCriteria;
import com.topcoder.service.project.Competition;
import com.topcoder.service.util.LoginUtil;
/**
 * <p>
 * This is the EJB implementation class for interface PipelineServiceFacadeWebService.
 * </p>
 *  <p>
 * This interface is a copy of the old PipelineServiceFacade interface. PipelineServiceFacade is no longer a web service
 * point. The security part is covered in this new web-service component. This bean's methods create this TCSubject
 * instance by do the login with LoginBean class and simply call the corresponding PipelineServiceFacade method.
 * This web service must now be used instead of PipelineServiceFacade by web service clients.
 * </p>
 * <p>
 * It is stateless session bean and using CMT. It has the annotations:
 *  &#064;WebService
 *  &#064;EndpointConfig(configName = "Standard WSSecurity Endpoint")
 *  &#064;DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
 *  &#064;RolesAllowed( { "Cockpit User", "Cockpit Administrator" })
 *  &#064;TransactionManagement(TransactionManagementType.CONTAINER)
 *  &#064;TransactionAttribute(TransactionAttributeType.REQUIRED)
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 * @since Cockpit Security Facade Assembly V1.0
 */
@WebService
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
@DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
@RolesAllowed( { "Cockpit User", "Cockpit Administrator" })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PipelineServiceFacadeWebServiceBean implements PipelineServiceFacadeWebServiceLocal,
        PipelineServiceFacadeWebServiceRemote {
    /**
     * <p>
     * A <code>PipelineServiceFacade</code> providing access to all the methods.
     * </p>
     */
    @EJB(name = "ejb/PipelineServiceFacade")
    private PipelineServiceFacade facade;
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
     * Search the contests by the given criteria.
     * </p>
     *
     * @param criteria the search criteria, not null
     * @return List of Competition, could be empty if nothing found
     * @throws ContestPipelineServiceException fail to do the search
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<Competition> getContests(ContestsSearchCriteria criteria) throws ContestPipelineServiceException {
        try
        {
            return facade.getContests(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), criteria);
        }
        catch (AuthenticationException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Search the contests by the given date criteria.
     * </p>
     *
     * @param criteria the date search criteria, not null
     * @return List of Competition, could be empty if nothing found
     * @throws ContestPipelineServiceException fail to do the search
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<Competition> getContestsByDate(DateSearchCriteria criteria) throws ContestPipelineServiceException {
        try
        {
            return facade.getContestsByDate(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), criteria);
        }
        catch (AuthenticationException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Search the date competition change history for the given contest and competition type.
     * </p>
     *
     * @param contestId the contest id
     * @return List of CompetitionChangeHistory
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws ContestPipelineServiceException fail to do the query
     */
    public List<CompetitionChangeHistory> getContestDateChangeHistory(long contestId)
            throws ContestPipelineServiceException {
        try
        {
            return facade.getContestDateChangeHistory(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Search the prize competition change history for the given contest and competition type.
     * </p>
     *
     * @param contestId the contest id 
     * @return List of CompetitionChangeHistory
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     * @throws ContestPipelineServiceException fail to do the query
     */
    public List<CompetitionChangeHistory> getContestPrizeChangeHistory(long contestId)
            throws ContestPipelineServiceException {
        try
        {
            return facade.getContestPrizeChangeHistory(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestId);
        }
        catch (AuthenticationException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Search the date competition change history for the given contest ids and their competition types.
     * </p>
     *
     * @param contestIds the contest ids
     * @param competitionTypess competition types, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException fail to do the query
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestDateChangeHistories(long[] contestIds, String[] competitionTypes)
            throws ContestPipelineServiceException {
        try
        {
            return facade.getContestDateChangeHistories(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestIds, competitionTypes);
        }
        catch (AuthenticationException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * <p>
     * Search the prize competition change history for the given contest ids and their competition types.
     * </p>
     *
     * @param contestIds the contest ids
     * @param competitionTypess competition types, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException fail to do the query
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestPrizeChangeHistories(long[] contestIds, String[] competitionTypes)
            throws ContestPipelineServiceException {
         try
        {
            return facade.getContestPrizeChangeHistories(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestIds, competitionTypes);
        }
        catch (AuthenticationException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }

        
    }

    /**
     * Gets the list of common pipeline data within between specified start and end date.
     *
     * @param startDate the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ContestManagementException if error during retrieval from database.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public List<CommonPipelineData> getCommonPipelineData(Date startDate, Date endDate, boolean overdueContests)
            throws ContestPipelineServiceException {
         try
        {
            return facade.getCommonPipelineData(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), startDate, endDate, overdueContests);
        }
        catch (AuthenticationException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        
    }

    /**
     * Gets the list of dates that have full capacity starting from tomorrow for the given contest type (for software or
     * studio contests) This method delegates to Pipeline Service layer.
     *
     * @param contestType the contest type
     * @param isStudio true of it is a studio competition, false otherwise
     * @return the list of dates that have full capacity.
     * @throws ContestPipelineServiceException if any error occurs during retrieval of information.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CapacityData> getCapacityFullDates(int contestType, boolean isStudio)
            throws ContestPipelineServiceException {
         try
        {
            return facade.getCapacityFullDates(LoginUtil.login(loginBeanURL, loginBeanDSJndiName, sessionContext), contestType, isStudio);
        }
        catch (AuthenticationException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
        catch (GeneralSecurityException e)
        {
            throw new ContestPipelineServiceException(e.getMessage(), e);
        }
       
    }

}
