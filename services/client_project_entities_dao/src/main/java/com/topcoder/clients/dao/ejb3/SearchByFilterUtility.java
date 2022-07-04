/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.io.Serializable;
import java.util.List;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface represents the SearchByFilterUtility interface. This interface
 * defines the method needed to search entities using a given filter as
 * criteria.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> Implementations of this interface should be
 * thread safe.
 * </p>
 *
 * @param <T> The entity type to operate
 * @param <Id> The type of id of entity
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
public interface SearchByFilterUtility<T extends AuditableEntity, Id extends Serializable> {
    /**
     * Defines the operation that performs the retrieval of all entities that
     * match the given filter in the persistence. If nothing is found, return an
     * empty list.
     *
     * @param filter
     *                the filter that should be used to search the matched
     *                entities. Should not be null.
     * @return the list with entities that match the given filter retrieved from
     *         the persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if filter is null.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<T> search(Filter filter) throws DAOException;

}
