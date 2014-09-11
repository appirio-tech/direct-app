/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.impl;

import com.topcoder.clients.Utils;
import com.topcoder.clients.dao.ClientInvoiceUploadConfigurationException;
import com.topcoder.clients.dao.ClientInvoiceUploadDAO;
import com.topcoder.clients.dao.ClientInvoiceUploadEntityNotFoundException;
import com.topcoder.clients.dao.ClientInvoiceUploadPersistenceException;
import com.topcoder.clients.model.ClientInvoiceUpload;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * The default JPA implementation of <code>ClientInvoiceUploadDAO</code>.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit - Billing Management)
 */
public class ClientInvoiceUploadDAOImpl implements ClientInvoiceUploadDAO {

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
     * Creates the new <code>ClientInvoiceUpload</code> record.
     *
     * @param invoiceUpload the <code>ClientInvoiceUpload</code> instance to insert
     * @throws ClientInvoiceUploadPersistenceException
     *          if there is error with the persistence
     */
    public void create(ClientInvoiceUpload invoiceUpload) throws ClientInvoiceUploadPersistenceException {
        logger.log(
                Level.DEBUG,
                "Enter ClientInvoiceUploadDAOImpl#create(ClientInvoiceUpload) with "
                        + Utils.toString(invoiceUpload));

        Session session = null;
        try {
            ParameterCheckUtility.checkNotNull(invoiceUpload, "invoiceUpload");
            if (invoiceUpload.getId() != 0) {
                throw new IllegalArgumentException("invoiceUpload.id should be zero.");
            }

            session = getSession();
            session.save(invoiceUpload);
            session.flush();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            ClientInvoiceUploadPersistenceException ex =
                    new ClientInvoiceUploadPersistenceException("Fail to create ClientInvoiceUpload entity.",
                            e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        logger.log(Level.DEBUG, "Exit ClientInvoiceUploadDAOImpl#create(ClientInvoiceUpload).");
    }

    /**
     * Updates the <code>ClientInvoiceUpload</code>.
     *
     * @param invoiceUpload the <code>ClientInvoiceUpload</code> to update
     * @throws ClientInvoiceUploadEntityNotFoundException
     *          if the entity to update does not exist
     * @throws ClientInvoiceUploadPersistenceException
     *          if there is any error with persistence.
     */
    public void update(ClientInvoiceUpload invoiceUpload) throws ClientInvoiceUploadEntityNotFoundException,
            ClientInvoiceUploadPersistenceException {
        logger.log(
                Level.DEBUG,
                "Enter ClientInvoiceUploadDAOImpl#update(ClientInvoiceUpload) with "
                        + Utils.toString(invoiceUpload));

        Session session = null;
        try {
            ParameterCheckUtility.checkNotNull(invoiceUpload, "invoiceUpload");
            ParameterCheckUtility.checkPositive(invoiceUpload.getId(), "invoiceUpload.id");

            // check if entity exists
            getAndCheckExist(invoiceUpload.getId());

            session = getSession();
            session.update(invoiceUpload);
            session.flush();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (ClientInvoiceUploadEntityNotFoundException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            ClientInvoiceUploadPersistenceException ex =
                    new ClientInvoiceUploadPersistenceException("Fail to update ClientInvoiceUpload entity:" + Utils
                            .toString(invoiceUpload),
                            e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        logger.log(Level.DEBUG, "Exit ClientInvoiceUploadDAOImpl#update(ClientInvoiceUpload).");
    }

    /**
     * Deletes the invoice upload record with the specified <code>invoiceUploadId</code>
     *
     * @param invoiceUploadId the id of the invoice upload record
     * @throws ClientInvoiceUploadEntityNotFoundException
     *          if the invoice upload record does not exist
     * @throws ClientInvoiceUploadPersistenceException
     *          if there is any error with the persistence.
     */
    public void delete(long invoiceUploadId) throws ClientInvoiceUploadEntityNotFoundException,
            ClientInvoiceUploadPersistenceException {
        logger.log(Level.DEBUG, "Enter ClientInvoiceUploadDAOImpl#delete(long) with id:" + invoiceUploadId);

        Session session = null;
        try {
            ParameterCheckUtility.checkPositive(invoiceUploadId, "id");
            ClientInvoiceUpload invoiceUpload = getAndCheckExist(invoiceUploadId);

            session = getSession();
            session.delete(invoiceUpload);
            session.flush();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (ClientInvoiceUploadEntityNotFoundException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            ClientInvoiceUploadPersistenceException ex =
                    new ClientInvoiceUploadPersistenceException("Fail to delete ClientInvoiceUpload entity.",
                            e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        logger.log(Level.DEBUG, "Exit ClientInvoiceUploadDAOImpl#delete(id).");
    }

    /**
     * Gets the <code>ClientInvoiceUpload</code> from persistence with the given <code>invoiceUploadId</code>
     *
     * @param invoiceUploadId the id of the invoice upload record
     * @return the <code>ClientInvoiceUpload</code> instance
     * @throws ClientInvoiceUploadEntityNotFoundException
     *          if the entity is not found with the given id
     * @throws ClientInvoiceUploadPersistenceException
     *          if there is any error with the persistence
     */
    public ClientInvoiceUpload get(long invoiceUploadId) throws ClientInvoiceUploadEntityNotFoundException,
            ClientInvoiceUploadPersistenceException {
        logger.log(Level.DEBUG, "Enter ClientInvoiceUploadDAOImpl#get(long) with id:" + invoiceUploadId);
        ClientInvoiceUpload clientInvoiceUpload;

        String hql = "from ClientInvoiceUpload cu where cu.id = :id";
        Session session = null;
        try {
            ParameterCheckUtility.checkPositive(invoiceUploadId, "id");

            // create query to fetch the entity
            session = getSession();
            List res = session.createQuery(hql).setLong("id", invoiceUploadId).list();
            clientInvoiceUpload = (res.isEmpty() ? null : (ClientInvoiceUpload) res.get(0));
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            ClientInvoiceUploadPersistenceException ex =
                    new ClientInvoiceUploadPersistenceException(
                            "Fail to get ClientInvoiceUpload entity by invoice upload id:" + invoiceUploadId, e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        logger.log(Level.DEBUG,
                "Exit ClientInvoiceUploadDAOImpl#get(long) with " + Utils.toString(clientInvoiceUpload));
        return clientInvoiceUpload;
    }

    /**
     * Gets all the <code>ClientInvoiceUpload</code> belong to the given <code>clientId</code>
     *
     * @param clientId the id of the client
     * @return a list of <code>ClientInvoiceUpload</code>
     * @throws ClientInvoiceUploadPersistenceException
     *          if there is any error with the persistence.
     */
    public List<ClientInvoiceUpload> getAllByClientId(long clientId) throws ClientInvoiceUploadPersistenceException {
        logger.log(Level.DEBUG, "Enter ClientInvoiceUploadDAOImpl#getAllByClientId(long clientId)");
        List<ClientInvoiceUpload> clientInvoiceUploads = new ArrayList<ClientInvoiceUpload>();

        String hql = "from ClientInvoiceUpload cu where cu.clientId = :clientId";
        Session session = null;
        try {
            ParameterCheckUtility.checkPositive(clientId, "clientId");
            // create query to fetch the entity
            session = getSession();
            clientInvoiceUploads = (List<ClientInvoiceUpload>) session.createQuery(hql).setLong("clientId",
                    clientId).list();
        } catch (HibernateException e) {
            ClientInvoiceUploadPersistenceException ex =
                    new ClientInvoiceUploadPersistenceException(
                            "Fail to get list of all ClientInvoiceUpload instances by client id:" + clientId, e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        logger.log(
                Level.DEBUG,
                "Exit ClientInvoiceUploadDAOImpl#getAllByClientId(long clientId) with "
                        + Utils.invoiceUploadListToString(clientInvoiceUploads));
        return clientInvoiceUploads;
    }

    /**
     * Gets all the <code>ClientInvoiceUpload</code> of the given client and filtered
     * by the given startDate and endDate.
     *
     * @param clientId  the id of the client
     * @param startDate the start date used to filter
     * @param endDate   the end date used to filter
     * @return a list of <code>ClientInvoiceUpload</code> instances
     * @throws ClientInvoiceUploadPersistenceException
     *          if there is any error with the persistence
     */
    public List<ClientInvoiceUpload> getAllByClientId(long clientId, Date startDate,
                                                      Date endDate) throws ClientInvoiceUploadPersistenceException {
        logger.log(Level.DEBUG, "Enter ClientInvoiceUploadDAOImpl#getAllByClientId(long clientId, , Date startDate, " +
                "Date endDate)");
        List<ClientInvoiceUpload> clientInvoiceUploads = new ArrayList<ClientInvoiceUpload>();

        String hql = "from ClientInvoiceUpload cu where cu.clientId = :clientId " +
                " and cu.createDate >= :startDate and cu.createDate <= :endDate";
        Session session = null;
        try {
            ParameterCheckUtility.checkPositive(clientId, "clientId");
            ParameterCheckUtility.checkNotNull(startDate, "startDate");
            ParameterCheckUtility.checkNotNull(endDate, "endDate");
            // create query to fetch the entity
            session = getSession();

            clientInvoiceUploads = (List<ClientInvoiceUpload>) session.createQuery(hql).setLong("clientId",
                    clientId).setDate("startDate", startDate).setDate("endDate", endDate).list();
        } catch (HibernateException e) {
            ClientInvoiceUploadPersistenceException ex =
                    new ClientInvoiceUploadPersistenceException(
                            "Fail to get list of all ClientInvoiceUpload instances by client id:" + clientId + " " +
                                    "start date:" + startDate + " end Date:" + endDate, e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        logger.log(
                Level.DEBUG,
                "Exit ClientInvoiceUploadDAOImpl#getAllByClientId(long clientId, , Date startDate, Date endDate) with "
                        + Utils.invoiceUploadListToString(clientInvoiceUploads));
        return clientInvoiceUploads;
    }

    /**
     * Gets the logger
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
     * @throws ClientInvoiceUploadConfigurationException
     *          if this.logger or this.session is null.
     */
    @PostConstruct
    public void postConstruct() {
        if (logger == null || sessionFactory == null) {
            throw new ClientInvoiceUploadConfigurationException(
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
     * Gets the ClientInvoiceUpload entity and checks if it's not null.
     *
     * @return the entity got
     * @throws ClientInvoiceUploadEntityNotFoundException
     *          if the platform fee is not found in DB
     * @throws ClientInvoiceUploadPersistenceException
     *          if any error occurred
     */
    private ClientInvoiceUpload getAndCheckExist(long id)
            throws ClientInvoiceUploadEntityNotFoundException, ClientInvoiceUploadPersistenceException {
        ClientInvoiceUpload clientInvoiceUpload = get(id);
        if (clientInvoiceUpload == null) {
            throw new ClientInvoiceUploadEntityNotFoundException(
                    "The ClientInvoiceUpload is not found with the given id: " + id);
        }
        return clientInvoiceUpload;
    }
}
