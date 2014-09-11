/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * This is a test class for IdentifiableDeliverableStructure. It extends IdentifiableDeliverableStructure and no method
 * is overridden.
 *
 * @author TCSDEVELOPER
 * @version 1.2
 * @since 1.2
 */
public class DummyIdentifiableDeliverableStructure extends IdentifiableDeliverableStructure {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = 8657059715173894198L;

    /**
     * Creates a new DummyIdentifiableDeliverableStructure.
     */
    public DummyIdentifiableDeliverableStructure() {
        super();
    }

    /**
     * Creates a new DummyIdentifiableDeliverableStructure.
     *
     * @param id
     *            The id of the DummyIdentifiableDeliverableStructure
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    public DummyIdentifiableDeliverableStructure(long id) {
        super(id);
    }
}
