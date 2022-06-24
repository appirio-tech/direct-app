/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;

/**
 * NOTE: THIS IS A MOCK CLASS FOR TEST.
 * <p>
 * The SqlDeliverablePersistence class implements the DeliverablePersistence interface, in order to
 * persist to the database structure given in the deliverable_management.sql script. This class does
 * not cache a Connection to the database. Instead a new Connection is used on every method call.
 * PreparedStatements should be used to execute the SQL statements.
 * </p>
 * <p>
 * This class is immutable and thread-safe in the sense that multiple threads can not corrupt its
 * internal data structures. However, the results if used from multiple threads can be unpredictable
 * as the database is changed from different threads. This can equally well occur when the component
 * is used on multiple machines or multiple instances are used, so this is not a thread-safety
 * concern.
 * </p>
 *
 * @author aubergineanode, singlewood, sparemax
 * @version 1.1
 */
public class SqlDeliverablePersistence implements DeliverablePersistence {
    /** Represents the name of implemented method last called. */
    private static String lastCalled = null;

    /**
     * SqlDeliverablePersistence constructor: Creates a new SqlDeliverablePersistence. The
     * connectionName field is set to null.
     *
     * @param connectionFactory The connection factory to use for getting connections to the
     *            database.
     * @throws IllegalArgumentException If connectionFactory is null.
     */
    public SqlDeliverablePersistence(DBConnectionFactory connectionFactory) {
    }

    /**
     * SqlDeliverablePersistence constructor: Creates a new SqlDeliverablePersistence. All fields
     * are set to the given values.
     *
     * @param connectionFactory The connection factory to use for getting connections to the
     *            database.
     * @param connectionName The name of the connection to use. Can be null.
     * @throws IllegalArgumentException If connectionFactory is null.
     */
    public SqlDeliverablePersistence(DBConnectionFactory connectionFactory, String connectionName) {
    }

    /**
     * <p>
     * loadDeliverables: Loads the deliverables associated with the given deliverable id. There may
     * be more than one deliverable returned if the deliverable is a "per submission" deliverable.
     * Hence the need for an array return type. If there is no matching deliverable in the
     * persistence, an empty array should be returned.
     * </p>
     *
     * @return The matching deliverable (possibly expanded by matching with each active submission,
     *         if it is a "per submission" deliverable), or an empty array.
     * @param deliverableId The id of the deliverable
     * @param resourceId The id of the resource
     * @param phaseId The id of the phase
     * @throws IllegalArgumentException If deliverableId is <= 0
     * @throws DeliverablePersistenceException If there is an error reading the persistence
     */
    public Deliverable[] loadDeliverables(long deliverableId, long resourceId, long phaseId)
        throws DeliverablePersistenceException {
        lastCalled = "loadDeliverables" + deliverableId;

        Deliverable[] deliverables = new Deliverable[1];

        Deliverable deliverable = new Deliverable(2, 2, 2, null, true);
        deliverable.setId(deliverableId);
        deliverable.setName("name " + (deliverableId + 1));

        deliverables[0] = deliverable;

        return deliverables;
    }

    /**
     * <p>
     * loadDeliverable: Loads the deliverable associated with the given submission. The deliverable
     * must be a "per submission" deliverable and the given submission must be "Active". If this is
     * not the case, null is returned.
     * </p>
     *
     * @return The deliverable, or null if there is no deliverable for the given id, or submission
     *         is not an 'Active' submission
     * @param deliverableId The id of the deliverable
     * @param resourceId The id of the resource
     * @param phaseId The id of the phase
     * @param submissionId The id of the submission the deliverable should be associated with
     * @throws IllegalArgumentException If deliverableId is <= 0
     * @throws DeliverablePersistenceException If there is an error reading the persistence data
     */
    public Deliverable loadDeliverable(long deliverableId, long resourceId, long phaseId, long submissionId)
        throws DeliverablePersistenceException {
        lastCalled = "loadDeliverable" + deliverableId + submissionId;

        Deliverable deliverable = new Deliverable(2, 2, 2, null, true);
        deliverable.setId(deliverableId);
        deliverable.setName("name " + (deliverableId + 1));

        return deliverable;
    }

    /**
     * <p>
     * loadDeliverables: Loads all Deliverables with the given ids from persistence. May return a
     * 0-length array.
     * </p>
     *
     * @param deliverableIds The ids of deliverables to load
     * @param resourceIds The ids of the resources
     * @param phaseIds The ids of the phases
     * @return The loaded deliverables
     * @throws IllegalArgumentException if any id is <= 0
     * @throws DeliverablePersistenceException if there is an error reading the persistence data
     */
    public Deliverable[] loadDeliverables(long[] deliverableIds, long[] resourceIds, long[] phaseIds)
        throws DeliverablePersistenceException {

        Deliverable[] deliverables = new Deliverable[deliverableIds.length];

        for (int i = 0; i < deliverables.length; ++i) {
            deliverables[i] = this.loadDeliverable(
                deliverableIds[i], resourceIds[i], phaseIds[i], 1);
        }

        lastCalled = "loadDeliverables" + deliverableIds;

        return deliverables;
    }

    /**
     * <p>
     * loadDeliverables: Loads the deliverables associated with the given submissions. The
     * deliverables must be "per submission" deliverables and the given submissions must be
     * "Active". Pairs of ids not meeting this requirement will not be returned.
     * </p>
     *
     * @param deliverableIds The ids of deliverables to load
     * @param submissionIds The ids of the submission for each deliverable
     * @param resourceIds The ids of the resources
     * @param phaseIds The ids of the phases
     * @return The loaded deliverables
     * @throws IllegalArgumentException If the two arguments do not have the same number of elements
     * @throws IllegalArgumentException if any id (in either array) is <= 0
     */
    public Deliverable[] loadDeliverables(long[] deliverableIds, long[] resourceIds, long[] phaseIds,
        long[] submissionIds) {
        lastCalled = "loadDeliverables" + deliverableIds + submissionIds;

        Deliverable[] deliverables = new Deliverable[deliverableIds.length];

        for (int i = 0; i < deliverables.length; ++i) {
            deliverables[i] = new Deliverable(2, 2, 2, new Long(submissionIds[i]), true);
            deliverables[i].setId(deliverableIds[i]);
            deliverables[i].setName("name" + (i + 1));
        }

        return deliverables;
    }

    /**
     * Gets the name of implemented method last called.
     *
     * @return the name of the method last called.
     */
    public static String getLastCalled() {
        return lastCalled;
    }
}
