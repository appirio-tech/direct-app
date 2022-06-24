/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.io.Serializable;
import java.util.List;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This class is a realization of the SearchByFilterUtility interface. This
 * class implements the method available for the SearchByFilterUtility
 * interface: search entities using the given filter. Search Builder TopCoder
 * component is used to perform the search operation.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class is stateless, it's field is
 * immutable. Search Builder used is used only local and not shared between
 * multiple threads so this component is thread safe.
 * </p>
 *
 * @param <T> The entity type to operate
 * @param <Id> The type of id of entity
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
public class SearchByFilterUtilityImpl<T extends AuditableEntity, Id extends Serializable>
        implements SearchByFilterUtility<T, Id> {
    /**
     * <p>
     * Represents the search bundle needed to search the entities using a given
     * filter.
     * </p>
     * <p>
     * Initialized in the constructor with non null value and never changed
     * afterwards.
     * </p>
     * <p>
     * By the search(..) method.
     * </p>
     * <p>
     * Will not change after initialization.
     * </p>
     * <p>
     * Can not be null.
     * </p>
     */
    private final SearchBundle searchBundle;

    /**
     * <p>
     * Constructs a new 'SearchByFilterUtilityImpl' instance. Initialize the
     * SearchBundle field.
     * </p>
     *
     * @param searchBundleManagerNamespace
     *                the search bundle manager namespace to be used to
     *                initialize the SearchBundleManager. Should be not empty
     *                and not null.
     * @param searchBundleName
     *                the search bundle name to be used to retrieve the
     *                SearchBundle. Should be not empty and not null.
     * @throws IllegalArgumentException
     *                 if searchBundleManagerNamespace or searchBundleName is
     *                 null or empty string.
     * @throws DAOConfigurationException
     *                 if this utility can not be configured. (if SearcBundle
     *                 could not be initialized).
     */
    public SearchByFilterUtilityImpl(String searchBundleManagerNamespace,
            String searchBundleName) {
        ExceptionUtils.checkNullOrEmpty(searchBundleManagerNamespace, null,
                null, "searchBundleManagerNamespace");
        ExceptionUtils.checkNullOrEmpty(searchBundleName, null, null,
                "searchBundleName");
        try {
            SearchBundleManager searchBundleManager = new SearchBundleManager(
                    searchBundleManagerNamespace);
            searchBundle = searchBundleManager
                    .getSearchBundle(searchBundleName);
        } catch (SearchBuilderConfigurationException e) {
            throw new DAOConfigurationException(
                    "Failed to create SearchBundleManager with searchBundleManagerNamespace:"
                            + searchBundleManagerNamespace
                            + " and searchBundleName:" + searchBundleName, e);
        }
    }

    /**
     * Performs the retrieval of all entities that match the given filter in the
     * persistence. If nothing is found, return an empty list.
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
    @SuppressWarnings("unchecked")
    public List<T> search(Filter filter) throws DAOException {
        ExceptionUtils.checkNull(filter, null, null, "filter");
        try {
            return (List<T>) searchBundle.search(filter);
        } catch (SearchBuilderException e) {
            throw new DAOException(
                    "Failed to search filter using search builder.", e);
        } catch (ClassCastException e) {
            throw new DAOException("The found entities are not correct type.",
                    e);
        }
    }
}
