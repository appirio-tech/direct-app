/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionContext;
import javax.ejb.TimerService;
import javax.transaction.UserTransaction;
import javax.xml.rpc.handler.MessageContext;
import java.io.Serializable;
import java.security.Identity;
import java.security.Principal;
import java.util.Properties;

/**
 * <p>
 * Mockup of <code>SessionContext</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class MockSessionContext implements SessionContext, Serializable {

    /**
     * <p>
     * Initializes <code>persistenceUnit</code>.
     * </p>
     */
    public MockSessionContext() {
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>null</code>.
     * </p>
     * @return <code>null</code> always.
     */
    public EJBLocalObject getEJBLocalObject() {
        return null;
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>null</code>.
     * </p>
     * @return <code>null</code> always.
     */
    public EJBObject getEJBObject() {
        return null;
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>null</code>.
     * </p>
     * @return <code>null</code> always.
     */
    public MessageContext getMessageContext() {
        return null;
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>null</code>.
     * </p>
     * @param aClass
     *        ignored
     * @return <code>null</code> always.
     */
    public Object getBusinessObject(Class aClass) {
        return null;
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>null</code>.
     * </p>
     * @return <code>null</code> always.
     */
    public Class getInvokedBusinessInterface() {
        return null;
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>null</code>.
     * </p>
     * @return <code>null</code> always.
     */
    public EJBHome getEJBHome() {
        return null;
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>null</code>.
     * </p>
     * @return <code>null</code> always.
     */
    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>null</code>.
     * </p>
     * @return <code>null</code> always.
     */
    public Properties getEnvironment() {
        return null;
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>null</code>.
     * </p>
     * @return <code>null</code> always.
     */
    @SuppressWarnings("deprecation")
    public Identity getCallerIdentity() {
        return null;
    }

    /**
     * <p>
     * The method is a stub.
     * </p>
     * @return mockup principal with "MockupPrincipal" name.
     */
    public Principal getCallerPrincipal() {
        return null;
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>false</code>.
     * </p>
     * @param identity
     *        ignored
     * @return <code>false</code> always.
     */
    @SuppressWarnings("deprecation")
    public boolean isCallerInRole(Identity identity) {
        return false;
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>null</code>.
     * </p>
     * @param string
     *        ignored
     * @return <code>null</code> always.
     */
    public boolean isCallerInRole(String string) {
        return false;
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>null</code>.
     * </p>
     * @return <code>null</code> always.
     */
    public UserTransaction getUserTransaction() {
        return null;
    }

    /**
     * <p>
     * The method is a stub. Does nothing.
     */
    public void setRollbackOnly() {
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>false</code>.
     * </p>
     * @return <code>false</code> always.
     */
    public boolean getRollbackOnly() {
        return false;
    }

    /**
     * <p>
     * The method is a stub. Always returns <code>null</code>.
     * </p>
     * @return <code>null</code> always.
     */
    public TimerService getTimerService() {
        return null;
    }

    /**
     * Looks up the object.
     * @param string
     *        The name
     * @return corresponding object to input: <code>persistenceUnit</code>,
     *         <code>EntityManager</code> or <code>FailTestEntityManager</code>.
     */
    public Object lookup(String string) {
        return null;
    }
}
