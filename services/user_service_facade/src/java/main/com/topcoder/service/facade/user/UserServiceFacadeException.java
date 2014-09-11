/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.user;

import javax.xml.ws.WebFault;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * A common exception class for the user service.
 * </p>
 * 
 * <p>
 * Updated for Jira and Confluence User Sync Widget 1.0
 *  - Specified the @WebFault mapping
 *  - Initialize fault message too.
 *  - For proper exception serialization to webservice faultInfo is also initialized now
 * </p>
 * 
 * @author snow01, TCSASSEMBLER
 * 
 * @since Jira & Confluence User  Service
 * @version 1.0
 */
@WebFault(name = "user_service_facade_fault", faultBean = "com.topcoder.service.facade.user.UserServiceFacadeFault")
public class UserServiceFacadeException extends BaseCriticalException {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * Represents the faultInfo, Java type that goes as soapenv:Fault detail element.
     * </p>
     */
    private UserServiceFacadeFault faultInfo;

    /**
     * <p>
     * Represents the faultMessage. It is retrieved from UserServiceFault.
     * </p>
     */
    private String faultMessage;

    /**
     * <p>
     * Constructor with error message.
     * </p>
     * 
     * <p>
     * Updated for Jira and Confluence User Sync Widget 1.0
     *  - fault message is also initialized now.
     * </p>
     * 
     * @param message
     *            the error message
     */
    public UserServiceFacadeException(String message) {
        this(message, message);
    }

    /**
     * <p>
     * Constructor with error message and inner cause.
     * </p>
     * 
     * <p>
     * Updated for Jira and Confluence User Sync Widget 1.0
     *  - fault message is also initialized now.
     * </p>
     * 
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     */
    public UserServiceFacadeException(String message, Throwable cause) {
        this(message, cause, message);
    }

    /**
     * <p>
     * Constructor with error message and exception data
     * </p>
     * 
     * <p>
     * Updated for Jira and Confluence User Sync Widget 1.0
     *  - fault message is also initialized now.
     * </p>
     * 
     * @param message
     *            the error message
     * @param data
     *            the exception data
     */
    public UserServiceFacadeException(String message, ExceptionData data) {
        this(message, data, message);
    }

    /**
     * <p>
     * Constructor with error message and inner cause and exception data
     * </p>
     * 
     * <p>
     * Updated for Jira and Confluence User Sync Widget 1.0
     *  - fault message is also initialized now.
     * </p>
     * 
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param data
     *            the exception data
     */
    public UserServiceFacadeException(String message, Throwable cause, ExceptionData data) {
        this(message, cause, data, message);
    }

    /**
     * <p>
     * Construct the exception. Call the super constructor,set the fault info and set the faultMessage from the
     * faultInfo.
     * </p>
     * 
     * @param message
     *            the message
     * @param faultInfo
     *            the fault info which contains the faultMessage
     */
    public UserServiceFacadeException(String message, UserServiceFacadeFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
        this.faultMessage = (faultInfo == null) ? null : faultInfo.getFaultMessage();
    }

    /**
     * <p>
     * Construct the exception. Call the super constructor,set the fault info and set the faultMessage from the
     * faultInfo.
     * </p>
     * 
     * @param message
     *            the message
     * @param faultInfo
     *            the fault info which contains the faultMessage
     * @param cause
     *            the cause
     */
    public UserServiceFacadeException(String message, UserServiceFacadeFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
        this.faultMessage = (faultInfo == null) ? null : faultInfo.getFaultMessage();
    }

    /**
     * <p>
     * Constructor with error message.
     * </p>
     * 
     * <p>
     * Updated for Jira and Confluence User Sync Widget 1.0
     *  - for proper exception serialization to webservice fault info is also initialized now.
     * </p>
     * 
     * @param message
     *            the error message
     * @param faultMessage
     *            the competition's id not found
     */
    public UserServiceFacadeException(String message, String faultMessage) {
        super(message);
        this.faultMessage = faultMessage;
        this.faultInfo = new UserServiceFacadeFault();
        this.faultInfo.setFaultMessage(this.faultMessage);
    }

    /**
     * <p>
     * Constructor with error message and inner cause.
     * </p>
     * 
     * <p>
     * Updated for Jira and Confluence User Sync Widget 1.0
     *  - for proper exception serialization to webservice fault info is also initialized now.
     * </p>
     * 
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param faultMessage
     *            the competition's id not found
     */
    public UserServiceFacadeException(String message, Throwable cause, String faultMessage) {
        super(message, cause);
        this.faultMessage = faultMessage;
        this.faultInfo = new UserServiceFacadeFault();
        this.faultInfo.setFaultMessage(this.faultMessage);
    }

    /**
     * <p>
     * Constructor with error message and exception data
     * </p>
     * 
     * <p>
     * Updated for Jira and Confluence User Sync Widget 1.0
     *  - for proper exception serialization to webservice fault info is also initialized now.
     * </p>
     * 
     * @param message
     *            the error message
     * @param data
     *            the exception data
     * @param faultMessage
     *            the competition's id not found
     */
    public UserServiceFacadeException(String message, ExceptionData data, String faultMessage) {
        super(message, data);
        this.faultMessage = faultMessage;
        this.faultInfo = new UserServiceFacadeFault();
        this.faultInfo.setFaultMessage(this.faultMessage);
    }

    /**
     * <p>
     * Constructor with error message and inner cause and exception data
     * </p>
     * 
     * <p>
     * Updated for Jira and Confluence User Sync Widget 1.0
     *  - for proper exception serialization to webservice fault info is also initialized now.
     * </p>
     * 
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param data
     *            the exception data
     * @param faultMessage
     *            the competition's id not found
     */
    public UserServiceFacadeException(String message, Throwable cause, ExceptionData data, String faultMessage) {
        super(message, cause, data);
        this.faultMessage = faultMessage;
        this.faultInfo = new UserServiceFacadeFault();
        this.faultInfo.setFaultMessage(this.faultMessage);
    }

    /**
     * <p>
     * Return the faultMessage
     * </p>
     * 
     * @return the faultMessage
     */
    public String getFaultMessage() {
        return faultMessage;
    }

    /**
     * <p>
     * Return the fault bean. This method is necessary for the serialization/deserialization. it returns null if the
     * constructors without fault bean are used.
     * </p>
     * 
     * @return returns fault bean
     */
    public UserServiceFacadeFault getFaultInfo() {
        return faultInfo;
    }
}
