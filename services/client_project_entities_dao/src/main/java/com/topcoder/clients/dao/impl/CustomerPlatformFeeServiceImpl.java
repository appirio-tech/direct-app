/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import com.topcoder.clients.Utils;
import com.topcoder.clients.dao.CustomerPlatformFeeConfigurationException;
import com.topcoder.clients.dao.CustomerPlatformFeeEntityNotFoundException;
import com.topcoder.clients.dao.CustomerPlatformFeePersistenceException;
import com.topcoder.clients.dao.CustomerPlatformFeeDAO;
import com.topcoder.clients.dao.CustomerPlatformFeeService;
import com.topcoder.clients.model.CustomerPlatformFee;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This class implements CustomerPlatformFeeService, it contains methods to
 * manage customer platform fee.
 * 
 * Thread safety: The class is mutable and not thread safe. But it'll not caused thread safety issue if used under
 * Spring container.
 * 
 * @author minhu
 * @version 1.0 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
 */
public class CustomerPlatformFeeServiceImpl implements CustomerPlatformFeeService {
    /**
     * Instance of Logger used to perform logging operations. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Log logger;

    /**
     * Instance of DataAccess used to perform db operations. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private CustomerPlatformFeeDAO persistence;

    /**
     * This method is responsible for creating customer platform fee.
     * 
     * @param customerPlatformFee a CustomerPlatformFee instance to save
     * @throws IllegalArgumentException
     *         if customerPlatformFee is null or its id is not zero
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any exception
     */
    public void create(CustomerPlatformFee customerPlatformFee)
        throws CustomerPlatformFeePersistenceException {
        logger.log(
            Level.DEBUG,
            "Enter CustomerPlatformFeeServiceImpl#create(CustomerPlatformFee) with "
                + Utils.toString(customerPlatformFee));
        try {
            persistence.create(customerPlatformFee);
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (CustomerPlatformFeePersistenceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        logger.log(Level.DEBUG, "Exit CustomerPlatformFeeServiceImpl#create(CustomerPlatformFee).");
    }

    /**
     * This method is responsible for retrieving customer platform fee given entity id.
     * 
     * @param id denotes the entity id, should be positive
     * @return instance of CustomerPlatformFee or null if not found
     * @throws IllegalArgumentException
     *         if id is not positive
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any exception
     */
    public CustomerPlatformFee get(long id) throws CustomerPlatformFeePersistenceException {
        logger.log(Level.DEBUG,
            "Enter CustomerPlatformFeeServiceImpl#get(long) with id:" + id);
        CustomerPlatformFee customerPlatformFee;
        try {
            customerPlatformFee = persistence.get(id);
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (CustomerPlatformFeePersistenceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        logger.log(
            Level.DEBUG,
            "Exit CustomerPlatformFeeServiceImpl#get(long) with "
                + Utils.toString(customerPlatformFee));
        return customerPlatformFee;
    }

    /**
     * This method is responsible for retrieving the list of all customer platform fees.
     * 
     * @return the list of all customer platform fees
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any exception
     */
    public List<CustomerPlatformFee> getAll() throws CustomerPlatformFeePersistenceException {
        logger.log(Level.DEBUG, "Enter CustomerPlatformFeeServiceImpl#getAll()");
        List<CustomerPlatformFee> customerPlatformFees;
        try {
            customerPlatformFees = persistence.getAll();
        } catch (CustomerPlatformFeePersistenceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        logger.log(
            Level.DEBUG,
            "Exit CustomerPlatformFeeServiceImpl#getAll() with "
                + Utils.toString(customerPlatformFees));
        return customerPlatformFees;
    }
    
    /**
     * This method is responsible for retrieving customer platform fee given client id and payment date.
     * 
     * @param clientId - denotes client id, should be positive
     * @param paymentDate - denotes payment date, should be non-null
     * @return instance of CustomerPlatformFee or null if not found
     * @throws IllegalArgumentException
     *         if clientId is not positive, or paymentDate is null
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any exception
     */
    public CustomerPlatformFee getByClientIdAndPaymentDate(long clientId, Date paymentDate)
        throws CustomerPlatformFeePersistenceException {
        logger.log(Level.DEBUG,
            "Enter CustomerPlatformFeeServiceImpl#getByClientIdAndPaymentDate(long, Date) with clientId:"
            + clientId + ", paymentDate:" + paymentDate);
        CustomerPlatformFee customerPlatformFee;
        try {
            customerPlatformFee = persistence.getByClientIdAndPaymentDate(clientId, paymentDate);
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (CustomerPlatformFeePersistenceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        logger.log(
            Level.DEBUG,
            "Exit CustomerPlatformFeeServiceImpl#getByClientIdAndPaymentDate(long, Date) with "
                + Utils.toString(customerPlatformFee));
        return customerPlatformFee;
    }

    /**
     * This method is responsible for updating customer platform fee given
     * instance of CustomerPlatformFee.
     * 
     * @param customerPlatformFee a CustomerPlatformFee instance to update
     * @throws IllegalArgumentException
     *         if customerPlatformFee is null or its id is not positive
     * @throws CustomerPlatformFeeEntityNotFoundException
     *         if the platform fee to update is not found in DB
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any other exception
     */
    public void update(CustomerPlatformFee customerPlatformFee)
        throws CustomerPlatformFeeEntityNotFoundException, CustomerPlatformFeePersistenceException {
        logger.log(
            Level.DEBUG,
            "Enter CustomerPlatformFeeServiceImpl#update(CustomerPlatformFee) with "
                + Utils.toString(customerPlatformFee));
        try {
            persistence.update(customerPlatformFee);
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (CustomerPlatformFeeEntityNotFoundException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (CustomerPlatformFeePersistenceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        logger
            .log(Level.DEBUG, "Exit CustomerPlatformFeeServiceImpl#update(CustomerPlatformFee).");
    }

    /**
     * This method is responsible for deleting customer platform fee given its id.
     * 
     * @param id denotes customer platform fee id
     * @throws IllegalArgumentException
     *         if id is not positive
     * @throws CustomerPlatformFeeEntityNotFoundException
     *         if the platform fee to delete is not found in DB
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any other exception
     */
    public void delete(long id) throws CustomerPlatformFeeEntityNotFoundException,
        CustomerPlatformFeePersistenceException {
        logger.log(Level.DEBUG, "Enter CustomerPlatformFeeServiceImpl#delete(long) with id:" + id);
        try {
            persistence.delete(id);
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (CustomerPlatformFeeEntityNotFoundException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (CustomerPlatformFeePersistenceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        logger.log(Level.DEBUG, "Exit CustomerPlatformFeeServiceImpl#delete(id).");
    }

    /**
     * Gets the logger instance.
     * 
     * @return the logger
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * Sets the logger name.
     * 
     * @param loggerName the logger name used
     */
    public void setLoggerName(String loggerName) {
        if (loggerName == null) {
            this.logger = null;
        } else {
            this.logger = LogManager.getLog(loggerName);
        }
    }

    /**
     * Gets the CustomerPlatformFeeDAO instance.
     * 
     * @return the CustomerPlatformFeeDAO instance
     */
    public CustomerPlatformFeeDAO getPersistence() {
        return persistence;
    }

    /**
     * Sets the CustomerPlatformFeeDAO instance.
     * 
     * @param persistence the CustomerPlatformFeeDAO instance to set
     */
    public void setPersistence(CustomerPlatformFeeDAO persistence) {
        this.persistence = persistence;
    }

    /**
     * Check parameters.
     * 
     * @throws CustomerPlatformFeeConfigurationException if this.logger or this.persistence is null
     */
    @PostConstruct
    public void postConstruct() {
        if (logger == null || persistence == null) {
            throw new CustomerPlatformFeeConfigurationException(
                "The class requires logger instance and persistence instance.");
        }
    }
}
