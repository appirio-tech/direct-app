/*
 * Copyright (C) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.deliverable;


/**
 * This is a test class for NamedDeliverableStructure.
 * It extends NamedDeliverableStructure and no method is overridden.
 *
 * @author singlewood
 * @version 1.0
 *
 */
public class NamedDeliverableStructureExtends extends NamedDeliverableStructure {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = -5829695335615506564L;

    /**
     * Creates a new NamedDeliverableStructureExtends.
     */
    protected NamedDeliverableStructureExtends() {
        super();
    }

    /**
     * Creates a new NamedDeliverableStructureExtends.
     *
     * @param id The id of the structure
     * @throws IllegalArgumentException If id is <= 0
     */
    protected NamedDeliverableStructureExtends(long id) {
        super(id);
    }

    /**
     * Creates a new NamedDeliverableStructureExtends.
     *
     * @param id The id of the structure
     * @param name The name of the structure
     * @throws IllegalArgumentException If id is <= 0
     */
    protected NamedDeliverableStructureExtends(long id, String name) {
        super(id, name);
    }
}
