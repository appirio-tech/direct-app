/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.failuretests;

import java.util.Date;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseDateComparator;
import com.topcoder.project.phases.Project;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>PhaseDateComparator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class PhaseDateComparatorFailureTest extends TestCase {
    /**
     * <p>
     * An instance of <code>Phase</code> for test.
     * </p>
     */
    private Phase phase;

    /**
     * <p>
     * An instnce of <code>PhaseDateComparator</code> to test.
     * </p>
     */
    private PhaseDateComparator tester;

    /**
     * <p>
     * Initializes the <code>tester</code> instance.
     * </p>
     */
    protected void setUp() throws Exception {
        super.setUp();

        Project project = new Project(new Date(100), new MockWorkDays());
        phase = new Phase(project, 1);
        tester = new PhaseDateComparator();
    }

    /**
     * <p>
     * Test compare(Object obj1, Object obj2),
     * when obj1 is not Phase type, ClassCastException is expected.
     * </p>
     */
    public void testCompare_Obj1IsNull() {
        try {
            tester.compare(null, phase);
            fail("when obj1 is not Phase type, ClassCastException is expected.");
        } catch (ClassCastException e) {
            // good
        }
    }

    /**
     * <p>
     * Test compare(Object obj1, Object obj2),
     * when obj1 is not Phase type, ClassCastException is expected.
     * </p>
     */
    public void testCompare_Obj1NotPhase() {
        try {
            tester.compare(new Object(), phase);
            fail("when obj1 is not Phase type, ClassCastException is expected.");
        } catch (ClassCastException e) {
            // good
        }
    }

    /**
     * <p>
     * Test compare(Object obj1, Object obj2),
     * when obj2 is not Phase type, ClassCastException is expected.
     * </p>
     */
    public void testCompare_Obj2IsNull() {
        try {
            tester.compare(phase, null);
            fail("when obj2 is not Phase type, ClassCastException is expected.");
        } catch (ClassCastException e) {
            // good
        }
    }

    /**
     * <p>
     * Test compare(Object obj1, Object obj2),
     * when obj2 is not Phase type, ClassCastException is expected.
     * </p>
     */
    public void testCompare_Obj2NotPhase() {
        try {
            tester.compare(phase, new Object());
            fail("when obj2 is not Phase type, ClassCastException is expected.");
        } catch (ClassCastException e) {
            // good
        }
    }
}
