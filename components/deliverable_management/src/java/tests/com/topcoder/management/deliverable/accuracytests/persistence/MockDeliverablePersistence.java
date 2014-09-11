/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests.persistence;

import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;


/**
 * A mock class implements <code>DeliverablePersistence</code> interface. Just records which implemented method was
 * last called.
 *
 * @author skatou
 * @version 1.0
 */
public class MockDeliverablePersistence implements DeliverablePersistence {
    /** Represents the name of implemented method last called. */
    private static String lastCalled = null;

    /**
     * Gets the name of implemented method last called.
     *
     * @return the name of the method last called.
     */
    public static String getLastCalled() {
        return lastCalled;
    }

    /**
     * Records this method is called.
     *
     * @param deliverableId parameter 1.
     *
     * @return null.
     *
     * @throws DeliverablePersistenceException never thrown.
     */
    public Deliverable[] loadDeliverables(long deliverableId, long resourceId, long phaseId)
        throws DeliverablePersistenceException {
        lastCalled = "loadDeliverables" + deliverableId;

        return null;
    }

    /**
     * Records this method is called.
     *
     * @param deliverableId parameter 1.
     * @param submissionId parameter 2.
     *
     * @return null.
     *
     * @throws DeliverablePersistenceException never thrown.
     */
    public Deliverable loadDeliverable(long deliverableId, long resourceId, long phaseId, long submissionId)
        throws DeliverablePersistenceException {
        lastCalled = "loadDeliverable" + deliverableId + submissionId;

        return null;
    }

    /**
     * Gets an array of deliverables with the given deliverable ids.
     *
     * @param deliverableIds the deliverable ids.
     *
     * @return an array of deliverables with the given ids.
     *
     * @throws DeliverablePersistenceException never thrown.
     */
    public Deliverable[] loadDeliverables(long[] deliverableIds, long[] resourceIds, long[] phaseIds)
        throws DeliverablePersistenceException {
        lastCalled = "loadDeliverables" + deliverableIds;

        Deliverable[] deliverables = new Deliverable[deliverableIds.length];

        for (int i = 0; i < deliverables.length; ++i) {
            deliverables[i] = new Deliverable(2, 2, 2, null, true);
            deliverables[i].setId(deliverableIds[i]);
            deliverables[i].setName("name" + (i + 1));
        }

        return deliverables;
    }

    /**
     * Gets an array of deliverables with the given deliverable ids and submission ids.
     *
     * @param deliverableIds the deliverable ids.
     * @param submissionIds the submission ids.
     *
     * @return an array of deliverable with the given deliverable ids and submission ids.
     *
     * @throws DeliverablePersistenceException never thrown.
     */
    public Deliverable[] loadDeliverables(long[] deliverableIds, long[] resourceIds, long[] phaseIds, long[] submissionIds)
        throws DeliverablePersistenceException {
        lastCalled = "loadDeliverables" + deliverableIds + submissionIds;

        Deliverable[] deliverables = new Deliverable[deliverableIds.length];

        for (int i = 0; i < deliverables.length; ++i) {
            deliverables[i] = new Deliverable(2, 2, 2, new Long(submissionIds[i]), true);
            deliverables[i].setId(deliverableIds[i]);
            deliverables[i].setName("name" + (i + 1));
        }

        return deliverables;
    }
}
