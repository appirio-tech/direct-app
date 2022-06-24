/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This is helper class of this component.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class does not contain any changeable
 * fields, so is thread safe.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
final class Helper {
    /**
     * Prevent this class to be created.
     */
    private Helper() {

    }

    /**
     * Check the entityManager for non-null.
     *
     * @param entityManager
     *                the entityManager used to operate entity
     * @return entityManager given
     * @throws DAOConfigurationException
     *                 if entityManager is null.
     */
    static EntityManager checkEntityManager(EntityManager entityManager) {
        if (entityManager == null) {
            throw new DAOConfigurationException(
                    "The configured entityManager is invalid(null).");
        }
        return entityManager;
    }

    /**
     * Checks and throws IllegalArgumentException if the parameter is null.
     *
     * @param param
     *                value of the parameter to be checked
     * @param paramName
     *                name of the parameter
     * @throws IllegalArgumentException
     *                 if given parameter is null
     */
    static void checkNull(Object param, String paramName) {
        ExceptionUtils.checkNull(param, null, null, paramName
                + " should not be null.");
    }

    /**
     * Checks and throws IllegalArgumentException if the string is null or
     * empty.
     *
     * @param param
     *                value of the parameter to be checked
     * @param paramName
     *                name of the parameter
     * @throws IllegalArgumentException
     *                 if the string is null or empty.
     */
    static void checkNullAndEmpty(String param, String paramName) {
        ExceptionUtils.checkNullOrEmpty(param, null, null, paramName
                + " should not be null or empty.");
    }

    /**
     * Wrap exception, will wrap all non-DAOException with DAOException.
     *
     * @param e
     *                the exception to wrap
     * @param message
     *                the message for DAOException wrapper
     * @return DAOException wrapper
     */
    public static DAOException wrapWithDAOException(Exception e,
            String message) {
        if (e instanceof DAOException) {
            return (DAOException) e;
        }
        return new DAOException(message, e);
    }

    /**
     * Using the given query entity and query string to query.
     *
     * @param <T> The return entity type
     * @param <S> The query entity type
     * @param queryName the query name
     * @param queryEntity The query entity
     * @param entityManager the entityManager used to query
     * @param queryString the query string
     * @return The entity list find
     */
    @SuppressWarnings("unchecked")
    static final <T extends AuditableEntity, S> List<T> getEntities(String queryName,
            S queryEntity, EntityManager entityManager, String queryString) {
        Query query = entityManager.createQuery(queryString);
        query.setParameter(queryName, queryEntity);

        return query.getResultList();
    }
}
