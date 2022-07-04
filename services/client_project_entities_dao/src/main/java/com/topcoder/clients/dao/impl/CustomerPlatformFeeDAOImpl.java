/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.topcoder.clients.Utils;
import com.topcoder.clients.dao.CustomerPlatformFeeConfigurationException;
import com.topcoder.clients.dao.CustomerPlatformFeeEntityNotFoundException;
import com.topcoder.clients.dao.CustomerPlatformFeePersistenceException;
import com.topcoder.clients.dao.CustomerPlatformFeeDAO;
import com.topcoder.clients.model.CustomerPlatformFee;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This class implements CustomerPlatformFeeDAO, it contains methods to
 * manage customer platform fee.
 * 
 * Thread safety: The class is mutable and not thread safe. But it'll not caused thread safety issue if used under
 * Spring container.
 * 
 * @author minhu
 * @version 1.0 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
 */
public class CustomerPlatformFeeDAOImpl implements CustomerPlatformFeeDAO {
    /**
     * Instance of Logger used to perform logging operations. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Log logger;

    /**
     * Represents the session factory instance. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private SessionFactory sessionFactory;

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
            "Enter CustomerPlatformFeeDAOImpl#create(CustomerPlatformFee) with "
                + Utils.toString(customerPlatformFee));

        Session session = null;
        try {
            ParameterCheckUtility.checkNotNull(customerPlatformFee, "customerPlatformFee");
            if (customerPlatformFee.getId() != 0) {
                throw new IllegalArgumentException("customerPlatformFee.id should be zero.");
            }

            session = getSession();
            session.save(customerPlatformFee);
            session.flush();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            CustomerPlatformFeePersistenceException ex =
                new CustomerPlatformFeePersistenceException("Fail to create CustomerPlatformFee entity.",
                    e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        logger.log(Level.DEBUG, "Exit CustomerPlatformFeeDAOImpl#create(CustomerPlatformFee).");
    }

    /**
     * This method is responsible for retrieving customer platform fee given id.
     * 
     * @param id denotes the entity id, should be positive
     * @return instance of CustomerPlatformFee or null if not found
     * @throws IllegalArgumentException
     *         if id is not positive
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any exception
     */
    @SuppressWarnings("rawtypes")
    public CustomerPlatformFee get(long id) throws CustomerPlatformFeePersistenceException {
        logger.log(Level.DEBUG, "Enter CustomerPlatformFeeServiceImpl#get(long) with id:" + id);
        CustomerPlatformFee customerPlatformFee;

        String hql = "from CustomerPlatformFee p where p.id = :id";
        Session session = null;
        try {
            ParameterCheckUtility.checkPositive(id, "id");

            // create query to fetch the entity
            session = getSession();
            List res = session.createQuery(hql).setLong("id", id).list();
            customerPlatformFee = (res.isEmpty() ? null : (CustomerPlatformFee) res.get(0));
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            CustomerPlatformFeePersistenceException ex =
                new CustomerPlatformFeePersistenceException(
                    "Fail to get CustomerPlatformFee entity by clientId.", e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        logger.log(Level.DEBUG,
            "Exit CustomerPlatformFeeDAOImpl#get(long) with " + Utils.toString(customerPlatformFee));
        return customerPlatformFee;
    }

    /**
     * This method is responsible for retrieving the list of all customer platform fees.
     * 
     * @return the list of all customer platform fees
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any exception
     */
    @SuppressWarnings("unchecked")
    public List<CustomerPlatformFee> getAll() throws CustomerPlatformFeePersistenceException {
        logger.log(Level.DEBUG, "Enter CustomerPlatformFeeDAOImpl#getAll()");
        List<CustomerPlatformFee> customerPlatformFees = new ArrayList<CustomerPlatformFee>();

        String hql = "from CustomerPlatformFee";
        Session session = null;
        try {
            // create query to fetch the entity
            session = getSession();
            customerPlatformFees = (List<CustomerPlatformFee>) session.createQuery(hql).list();
        } catch (HibernateException e) {
            CustomerPlatformFeePersistenceException ex =
                new CustomerPlatformFeePersistenceException(
                    "Fail to get list of all CustomerPlatformFee instances.", e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        logger.log(
            Level.DEBUG,
            "Exit CustomerPlatformFeeDAOImpl#getAll() with "
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
    @SuppressWarnings("rawtypes")
    public CustomerPlatformFee getByClientIdAndPaymentDate(long clientId, Date paymentDate)
        throws CustomerPlatformFeePersistenceException {
        logger.log(Level.DEBUG,
            "Enter CustomerPlatformFeeDAOImpl#getByClientIdAndPaymentDate(long, Date) with clientId:"
            + clientId + ", paymentDate:" + paymentDate);
        CustomerPlatformFee customerPlatformFee;

        String hql = "from CustomerPlatformFee p where p.clientId = :clientId and " +
            "paymentDate >= :paymentDateMonthStart and paymentDate < :paymentDateNextMonthStart";
        Session session = null;
        try {
            ParameterCheckUtility.checkPositive(clientId, "clientId");
            ParameterCheckUtility.checkNotNull(paymentDate, "paymentDate");
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(paymentDate);            
            cal1.set(Calendar.DAY_OF_MONTH, 1);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(cal1.getTime());   
            cal2.add(Calendar.MONTH, 1);           

            // create query to fetch the entity
            session = getSession();
            List res = session.createQuery(hql).
                setLong("clientId", clientId).setDate("paymentDateMonthStart", cal1.getTime()).
                setDate("paymentDateNextMonthStart", cal2.getTime()).list();
            customerPlatformFee = (res.isEmpty() ? null : (CustomerPlatformFee) res.get(0));
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            CustomerPlatformFeePersistenceException ex =
                new CustomerPlatformFeePersistenceException(
                    "Fail to get CustomerPlatformFee entity by clientId and paymentDate.", e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        logger.log(
            Level.DEBUG,
            "Exit CustomerPlatformFeeDAOImpl#getByClientIdAndPaymentDate(long, Date) with "
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
            "Enter CustomerPlatformFeeDAOImpl#update(CustomerPlatformFee) with "
                + Utils.toString(customerPlatformFee));

        Session session = null;
        try {
            ParameterCheckUtility.checkNotNull(customerPlatformFee, "customerPlatformFee");
            ParameterCheckUtility.checkPositive(customerPlatformFee.getId(), "customerPlatformFee.id");

            // check if entity exists
            getAndCheckExist(customerPlatformFee.getId());

            session = getSession();
            session.update(customerPlatformFee);
            session.flush();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (CustomerPlatformFeeEntityNotFoundException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            CustomerPlatformFeePersistenceException ex =
                new CustomerPlatformFeePersistenceException("Fail to update CustomerPlatformFee entity.",
                    e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        logger.log(Level.DEBUG, "Exit CustomerPlatformFeeDAOImpl#update(CustomerPlatformFee).");
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
        logger.log(Level.DEBUG, "Enter CustomerPlatformFeeDAOImpl#delete(long) with id:" + id);

        Session session = null;
        try {
            ParameterCheckUtility.checkPositive(id, "id");
            CustomerPlatformFee customerPlatformFee = getAndCheckExist(id);

            session = getSession();
            session.delete(customerPlatformFee);
            session.flush();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (CustomerPlatformFeeEntityNotFoundException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            CustomerPlatformFeePersistenceException ex =
                new CustomerPlatformFeePersistenceException("Fail to delete CustomerPlatformFee entity.",
                    e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        logger.log(Level.DEBUG, "Exit CustomerPlatformFeeDAOImpl#delete(id).");
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
     * Check the parameters.
     * 
     * @throws CustomerPlatformFeeConfigurationException if this.logger or this.session is null.
     */
    @PostConstruct
    public void postConstruct() {
        if (logger == null || sessionFactory == null) {
            throw new CustomerPlatformFeeConfigurationException(
                "This class requires logger instance and session factory instance.");
        }
    }

    /**
     * Opens a session using the configured session factory.
     * 
     * @return the session opened
     */
    private Session getSession() {
        return sessionFactory.openSession();
    }

    /**
     * Gets the session factory.
     * 
     * @return the session factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Sets the session factory.
     * 
     * @param sessionFactory the session factory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Gets the CustomerPlatformFee entity and checks if it's not null.
     * 
     * @throws CustomerPlatformFeeEntityNotFoundException
     *         if the platform fee is not found in DB
     * @throws CustomerPlatformFeePersistenceException
     *         if any error occurred
     * @return the entity got
     */
    private CustomerPlatformFee getAndCheckExist(long id)
        throws CustomerPlatformFeeEntityNotFoundException, CustomerPlatformFeePersistenceException {
        CustomerPlatformFee customerPlatformFee = get(id);
        if (customerPlatformFee == null) {
            throw new CustomerPlatformFeeEntityNotFoundException(
                "The CustomerPlatformFee is not found with the given id: " + id);
        }
        return customerPlatformFee;
    }
}
