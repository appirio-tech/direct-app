/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.failuretests;

import java.util.Date;

import com.topcoder.management.deliverable.NamedDeliverableStructure;

/**
 * This class is a helper class for failure test.
 * This class will create unvalid NamedDeliverableStructure instance for test.
 *
 * @author Chenhong
 * @version 1.0
 */
final class Util {

    /**
     * Private constructor.
     *
     */
    private Util() {
        // empty.
    }

    /**
     * Create NamedDeliverableStructure instance for test.
     *
     * @param level
     *            defined which property is null.
     * @return NamedDeliverableStructure instance
     */
    static NamedDeliverableStructure getNamedDeliverableStructure(NamedDeliverableStructure obj, int level) {
        // level -1 mean null.
        if (level == -1) {
            return null;
        }

        if (level == 0) {
            return obj;
        }

        obj.setId(10);
        if (level == 1) {
            return obj;
        }

        obj.setName("name");

        if (level == 2) {
            return obj;
        }

        obj.setDescription("description");

        if (level == 3) {
            return obj;
        }

        obj.setCreationUser("failure");

        if (level == 4) {
            return obj;
        }

        obj.setCreationTimestamp(new Date());

        if (level == 5) {
            return obj;
        }

        obj.setModificationUser("f");

        if (level == 6) {
            return obj;
        }

        obj.setModificationTimestamp(new Date());

        return obj;
    }
}
