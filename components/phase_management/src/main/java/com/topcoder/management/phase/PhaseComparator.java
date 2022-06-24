/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import java.util.Comparator;

import com.topcoder.project.phases.Phase;

/**
 * A comparator that compares to {@link Phase Phase} instances.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

class PhaseComparator implements Comparator {
    /**
     * Returns -1, 0, or 1 if the {@link Phase#getId ID} of the first Phase argument is less than, equal to, or
     * greater than the ID of the second <code>Phase</code>, respectively.
     *
     * @param o1 the first <code>Phase</code> to compare
     * @param o2 the second <code>Phase</code> to compare
     * @throws ClassCastException if either argument is not a Phase instance
     * @return -1, 0, or 1 if the ID of the first <code>Phase</code> argument is less than, equal to, or greater
     *   than the ID of the second <code>Phase</code>, respectively
     */
    public int compare(Object o1, Object o2) {
        long p1 = ((Phase) o1).getId();
        long p2 = ((Phase) o2).getId();

        if (p1 < p2) {
            return -1;
        } else if (p1 > p2) {
            return 1;
        } else {
            return 0;
        }
    }
}
