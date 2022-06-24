/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.accuracytests;

import java.util.Comparator;

import com.topcoder.project.phases.Phase;

/**
 * An implementation of <code>Comparator</code> interface used to compare <code>Phase</code> with their
 * IDs.
 * <p>
 * This class is used to test. After we retrieved some phases, we can sort them with this comparator, thus the
 * results can be checked more easily.
 * </p>
 *
 * @author mayi
 * @version 1.0
 */
public class PhaseIdComparator implements Comparator {

    /**
     * Default constructor.
     */
    public PhaseIdComparator() {
        // empy
    }

    /**
     * Compare <code>Phase</code> o1 and o2.
     *
     * @param o1 the left phase instance
     * @param o2 the right phase instance
     * @return zero if they have same id, or a negative value if o1's id is less than o2's id, and vice versa
     *
     * @throws ClassCastException if o1 and o2 are not instance of Phase
     */
    public int compare(Object o1, Object o2) {
        Phase phase1 = (Phase) o1;
        Phase phase2 = (Phase) o2;

        return (int) (phase1.getId() - phase2.getId());
    }

}
