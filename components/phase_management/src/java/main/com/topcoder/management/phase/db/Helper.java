/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase.db;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Utility class providing the method to check the argument.
 * @author TCSDEVELOPER
 * @version 1.1
 */
final class Helper {

    /**
     * private constructor used to prevent the instantiation.
     */
    private Helper() {

    }

    /**
     * Checks if the map contains null key or value. Null map is allowed and
     * consider as valid.
     * @param map the map to check.
     * @param name the name of the map variable.
     * @throws IllegalArgumentException if the map contains null key or value.
     */
    static void checkMap(Map map, String name) {
        if (map == null) {
            return;
        }

        // iterate the entry in the map.
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();
            if (entry.getKey() == null) {
                throw new IllegalArgumentException("The map " + name
                        + " contains null key.");
            }
            if (entry.getValue() == null) {
                throw new IllegalArgumentException("The map " + name
                        + " contains null value.");
            }
        }
    }

    /**
     * Checks if the given object is <code>null</code>.
     * @param param the object to check.
     * @param name the name of the object to check.
     * @throws IllegalArgumentException if the given <code>param</code> is
     *             <code>null</code>.
     */
    static void checkNull(Object param, String name) {
        if (param == null) {
            throw new IllegalArgumentException("The argument " + name
                    + " should not be null.");
        }
    }
}
