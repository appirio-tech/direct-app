/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.admin.ejb;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.admin.AdminServiceFacadeException;

import com.topcoder.clients.Utils;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This is the implementation class for admin service facade.
 *
 * This service provides some admin related service such as manage contest fees
 * for each project.
 * </p>
 * <p>
 * Changes in v1.0.1(Cockpit Security Facade V1.0):
 *  - It is not a web-service facade any more.
 *  - All the methods accepts a parameter TCSubject which contains all the security info for current user.
 *    The implementation EJB should use TCSubject and now get these info from the sessionContext.
 *  - Please use the new AdminServiceFacadeWebService as the facade now. That interface will delegates all the methods
 *    to this interface.
 * </p>
 * @author waits
 * @version 1.0.1
 * @since Configurable Contest Fees v1.0 Assembly
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AdminServiceFacadeBean implements AdminServiceFacadeLocal,
        AdminServiceFacadeRemote {

    /**
     * <p>
     * Represents the sessionContext of the EJB.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * Represents the project dao service.
     * </p>
     */
    @EJB(name = "ejb/ProjectDAOBean")
    private ProjectDAO projectDAO;

    /**
     * <p>
     * Represents the loggerName used to retrieve the logger.
     * </p>
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private Log logger;

    /**
     * A default empty constructor.
     */
    public AdminServiceFacadeBean() {
    }

    /**
     * <p>
     * This is method is performed after the construction of the bean, at this
     * point all the bean's resources will be ready.
     *
     * It sets the log.
     * </p>
     *
     * @throws IllegalStateException
     *             On some error this runtime error is thrown such as log Name
     *             is not set as empty.
     */
    @PostConstruct
    public void init() {
        if (logName != null) {
            if (logName.trim().length() == 0) {
                throw new IllegalStateException(
                        "logName parameter not supposed to be empty.");
            }

            logger = LogManager.getLog(logName);
        }

        if (projectDAO == null) {
            throw new IllegalStateException("projectDAO is not set.");
        }

        // first record in logger
        logExit("init");
    }

    /**
     * Gets all contest fees by project id.
     * <p>
     * Update in v1.0.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId
     *            the project id
     * @return the list of project contest fees for the given project id
     *
     * @throws AdminServiceFacadeException
     *             if any error occurs during the service call
     */
    public List<ProjectContestFee> getContestFeesByProject(TCSubject tcSubject, long projectId)
            throws AdminServiceFacadeException {
        logEnter("getContestFeesByProject(tcSubject, projectId)", projectId);
        List<ProjectContestFee> ret = null;
        try {
            ret = projectDAO.getContestFeesByProject(projectId);
            return ret;
        } catch (DAOException e) {
            throw wrapAdminServiceFacadeException(e,
                    "DAO exception occurred when get contest fees by the project id of "
                            + projectId);
        } catch (Exception e) {
            throw wrapAdminServiceFacadeException(e,
                    "Error when get contest fees by the project id of "
                            + projectId);
        } finally {
            logExit("getContestFeesByProject(tcSubject, projectId)", ret.size());
        }
    }

    /**
     * Saves contest fees. It will refresh contest fees for the given project.
     * <p>
     * Update in v1.0.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestFees
     *            the contest fees
     * @param projectId
     *            the project id
     *
     * @throws AdminServiceFacadeException
     *             if any error occurs during the service call
     */
    public void saveContestFees(TCSubject tcSubject, List<ProjectContestFee> contestFees,
            long projectId) throws AdminServiceFacadeException {
        logEnter("saveContestFees(tcSubject, contestFees,projectId)", tcSubject, contestFees,
                projectId);
        try {
            Utils.checkContestFees(contestFees, projectId);
            projectDAO.saveContestFees(contestFees, projectId);
        } catch (IllegalArgumentException e) {
            throw wrapAdminServiceFacadeException(e,
                    "Invalid parameters for contestFees<" + contestFees
                            + "> projectId<" + projectId + ">");
        } catch (DAOException e) {
            throw wrapAdminServiceFacadeException(
                    e,
                    "DAO exception occurred when save contest fees for the project with the id of  "
                            + projectId);
        } catch (Exception e) {
            throw wrapAdminServiceFacadeException(e,
                    "Error when save contest fees for the project with the id of  "
                            + projectId);
        } finally {
            logExit("saveContestFees(tcSubject, contestFees,projectId)");
        }

    }

    /**
     * Searches projects by project name. The name search is case insensitive
     * and also allows for partial name search. The name doesn't allow wildcard
     * characters: *, %. If it is null or empty, all projects will be returned.
     * <p>
     * Update in v1.0.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectName
     *            the project name
     * @return projects matched with the project name
     *
     * @throws AdminServiceFacadeException
     *             if any error occurs during the service call
     */
    public List<Project> searchProjectsByProjectName(TCSubject tcSubject, String projectName)
            throws AdminServiceFacadeException {
        logEnter("searchProjectsByProjectName(tcSubject, projectName)", tcSubject, projectName);
        List<Project> ret = null;
        try {
            Utils.checkSearchName(projectName);
            ret = projectDAO.searchProjectsByProjectName(projectName);
            return ret;
        } catch (IllegalArgumentException e) {
            throw wrapAdminServiceFacadeException(e,
                    "Invalid project name to be searched : " + projectName);
        } catch (DAOException e) {
            throw wrapAdminServiceFacadeException(e,
                    "DAO exception occurred project name to be searched : "
                            + projectName);
        } catch (Exception e) {
            throw wrapAdminServiceFacadeException(e,
                    "Error when searching projects by project name of  "
                            + projectName);
        } finally {
            logExit("searchProjectsByProjectName(tcSubject, projectName)", ret.size());
        }
    }

    /**
     * Searches projects by client name. The name search is case insensitive and
     * also allows for partial name search. The name doesn't allow wildcard
     * characters: *, %. If it is null or empty, all projects will be returned.
     * <p>
     * Update in v1.0.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param clientName
     *            the client name
     * @return projects matched with the client name
     *
     * @throws AdminServiceFacadeException
     *             if any error occurs during the service call
     */
    public List<Project> searchProjectsByClientName(TCSubject tcSubject, String clientName)
            throws AdminServiceFacadeException {
        logEnter("searchProjectsByclientName(tcSubject, clientName)", tcSubject, clientName);
        List<Project> ret = null;
        try {
            Utils.checkSearchName(clientName);
            ret = projectDAO.searchProjectsByClientName(clientName);
            return ret;
        } catch (IllegalArgumentException e) {
            throw wrapAdminServiceFacadeException(e,
                    "Invalid client name to be searched : " + clientName);
        } catch (DAOException e) {
            throw wrapAdminServiceFacadeException(e,
                    "DAO exception occurred client name to be searched : "
                            + clientName);
        } catch (Exception e) {
            throw wrapAdminServiceFacadeException(e,
                    "Error when searching projects by client name of  "
                            + clientName);
        } finally {
            logExit("searchProjectsByClientName(tcSubject, clientName)", ret.size());
        }
    }

    /**
     * <p>
     * This method used to log enter in method. It will persist both method name
     * and it's parameters if any.
     * </p>
     *
     * @param method
     *            name of the entered method
     * @param params
     *            array containing parameters used to invoke method
     */
    private void logEnter(String method, Object... params) {
        if (logger != null) {
            logger
                    .log(
                            Level.DEBUG,
                            "Enter method AdminServiceFacadeBean.{0} with parameters {1}.",
                            method, Arrays.deepToString(params));
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     *
     * @param method
     *            name of the leaved method
     */
    private void logExit(String method) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Leave method {0}.", method);
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     *
     * @param method
     *            name of the leaved method
     * @param returnValue
     *            value returned from the method
     */
    private void logExit(String method, Object returnValue) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Leave method {0} with return value {1}.",
                    method, returnValue);
        }
    }

    /**
     * <p>
     * This method used to log arbitrary error. It will persist error's data.
     * </p>
     *
     * @param error
     *            exception describing error
     * @param message
     *            additional message information
     */
    private void logError(Throwable error, String message) {
        if (error == null) {
            logError(message);
        }
        if (logger != null) {
            logger.log(Level.ERROR, error, message);
        }
    }

    /**
     * <p>
     * This method used to log arbitrary error. It will persist error's data.
     * </p>
     *
     * @param message
     *            message information
     */
    private void logError(String message) {
        if (logger != null) {
            logger.log(Level.ERROR, message);
        }
    }

    /**
     * <p>
     * Creates a <code>AdminServiceFacadeException</code> with inner exception
     * and message. It will log the exception, and set the sessionContext to
     * rollback only.
     * </p>
     *
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     * @return the created exception
     */
    private AdminServiceFacadeException wrapAdminServiceFacadeException(
            Exception e, String message) {
        AdminServiceFacadeException ce = new AdminServiceFacadeException(
                message, e);
        logError(ce, message);
        sessionContext.setRollbackOnly();

        return ce;
    }
}
