/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The DeliverableManager interface provides the ability to persist, retrieve and search for
 * persisted deliverable modeling objects. This interface provides a higher level of interaction
 * than the DeliverablePersistence interface. This interface simply provides two methods to search a
 * persistence store for Deliverables.
 * </p>
 * <p>
 * Implementations of this interface are not required to be thread safe.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0.2
 */
public interface DeliverableManager {
    /**
     * Searches the deliverables in the persistence store using the given filter. The filter can be
     * formed using the field names and utility methods in DeliverableFilterBuilder. The return will
     * always be a non-null (possibly 0 item) array. This method is designed to be used with
     * submission id filters, and returns only deliverables that are "per submission".
     *
     * @return The Deliverables meeting the filter and complete conditions
     * @param filter The filter to use
     * @param complete True to include only those deliverables that have been completed, false to
     *            include only those deliverables that are not complete, null to include both
     *            complete and incomplete deliverables
     * @throws IllegalArgumentException If filter is null
     * @throws DeliverablePersistenceException If there is an error reading the persistence
     * @throws SearchBuilderException If there is an error executing the filter
     * @throws SearchBuilderConfigurationException If the manager is not properly configured with a
     *             correct SearchBundle for searching
     * @throws DeliverableCheckingException If there is an error when determining whether a
     *             Deliverable has been completed or not
     */
    public Deliverable[] searchDeliverablesWithSubmissionFilter(Filter filter, Boolean complete)
        throws DeliverablePersistenceException, SearchBuilderException, DeliverableCheckingException;

    /**
     * Searches the deliverables in the persistence store using the given filter. The filter can be
     * formed using the field names and utility methods in DeliverableFilterBuilder. The return will
     * always be a non-null (possibly 0 item) array. This method should not be used with submission
     * id filters.
     *
     * @return The Deliverables meeting the Filter and complete conditions
     * @param filter The filter to use
     * @param complete True to include only those deliverables that have been completed, false to
     *            include only those deliverables that are not complete, null to include both
     *            complete and incomplete deliverables
     * @throws IllegalArgumentException If filter is null
     * @throws DeliverablePersistenceException If there is an error reading the persistence store
     * @throws SearchBuilderException If there is an error executing the filter
     * @throws SearchBuilderConfigurationException If the manager is not properly configured with a
     *             correct SearchBundle for searching
     * @throws DeliverableCheckingException If there is an error when determining whether a
     *             Deliverable has been completed or not
     */
    public Deliverable[] searchDeliverables(Filter filter, Boolean complete)
        throws DeliverablePersistenceException, SearchBuilderException, DeliverableCheckingException;
}
