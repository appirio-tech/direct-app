/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.util.Comparator;

/**
 * <p>
 * An implementation of <code>Comparator</code> to compare the phases by start date and end date of the
 * <code>Phase</code>. The two phases will first sorted by the start date, and then break the ties by end date of the
 * phase. If two phases have the same start date and end date, the order in the sorted list is unpredictable.
 * </p>
 * <p>
 * Thread Safety: This class do not have any state. so it's thread safe.
 * </p>
 *
 * @author oldbig, littlebull
 * @version 2.0
 */
public class PhaseDateComparator implements Comparator {
    /**
     * Create a new instance of <code>PhaseDateComparator</code>, empty constructor.
     */
    public PhaseDateComparator() {
        // Does nothing
    }

    /**
     * The phase will first be compared by the <code>startDate</code>, and then break the ties by
     * <code>endDate</code> of the phase. If two phases in the list have the same <code>startDate</code> and
     * <code>endDate</code>, the order in the sorted list is unpredictable.
     *
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater
     *         than the second.
     * @param obj1
     *            the first phase obj to compare
     * @param obj2
     *            the second phase obj to compare.
     * @throws ClassCastException
     *             if the given objs are null or not <code>Phase</code> type.
     */
    public int compare(Object obj1, Object obj2) {
        if (!(obj1 instanceof Phase) || !(obj2 instanceof Phase)) {
            throw new ClassCastException("The given objs are null or not Phase type.");
        }

        Phase phase1 = (Phase) obj1;
        Phase phase2 = (Phase) obj2;

        int startDateCompare = phase1.calcStartDate().compareTo(phase2.calcStartDate());
        if (startDateCompare != 0) {
            return startDateCompare;
        }

        return phase1.calcEndDate().compareTo(phase2.calcEndDate());
    }
}
