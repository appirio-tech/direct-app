/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.accuracytests;
import com.topcoder.management.scorecard.data.NamedScorecardStructure;
import junit.framework.TestCase;


/**
 * Tests for NamedScorecardStructure class.
 *
 * @author crackme
 * @version 1.0
 */
public class TestNamedScorecardStructure extends TestCase {
    /**
     * the id of this instance.
     */
    private final long id = new Long(1234567).longValue();
    /**
     * A NamedScorecardStructure instance used to test.
     */
    private NamedScorecardStructure scorecard = null;
    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        scorecard = new NamedScorecardStructureImpl(id, "scorecard");
    }

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        scorecard = null;
    }
    
    /**
     * Test NamedScorecardStructure() method with accuracy state.
     *
     */
    public void testNamedScorecardStructureAccuracy1() {
        NamedScorecardStructure structure = new NamedScorecardStructureImpl();
        assertNull("constructor is wrong.", structure.getName());
        assertEquals("constructor is wrong.", -1, structure.getId()); 
    }
    
    /**
     * Test NamedScorecardStructure(long id) method with accuracy state.
     *
     */
    public void testNamedScorecardStructureAccuracy2() {
        NamedScorecardStructure structure = new NamedScorecardStructureImpl(id);
        assertNull("constructor is wrong.", structure.getName());
        assertEquals("constructor is wrong.", id, structure.getId()); 
    }

    /**
     * Tests setId(long id) method with accuracy state.
     */
    public void testSetIdAccuracy() {
        long newId = new Long(7654321).longValue();
        scorecard.setId(newId);
        assertEquals("setId method is wrong.", newId, scorecard.getId());
    }

    /**
     * Tests resetId() method with accuracy state.
     */
    public void testResetIdAccuracy() {
        scorecard.resetId();
        assertEquals("resetId is wrong.", -1, scorecard.getId());
    }

    /**
     * Tests getId() method with accuracy state.
     */
    public void testGetIdAccuracy() {
        assertEquals("getId method is wrong.", id, scorecard.getId());
    }

    /**
     * Tests setName(String name) method with accuracy state.
     */
    public void testSetNameAccuracy() {
        scorecard.setName("new name");
        assertEquals("setName method is wrong.", "new name", scorecard.getName());
    }

    /**
     * Tests resetName() method with accuracy state.
     */
    public void testResetNameAccuracy() {
        scorecard.resetName();
        assertNull("resetName is wrong.", scorecard.getName());
    }

    /**
     * Tests getName() method with accuracy state.
     */
    public void testGetNameAccuracy() {
        assertEquals("getName method is wrong.", "scorecard", scorecard.getName());
    }
    
    /**
     * 
     * this class extends from NamedScorecardStructure used to test.
     */
    final class NamedScorecardStructureImpl extends NamedScorecardStructure {
        /**
         * NamedScorecardStructure constructor: Creates a new
         * NamedScorecardStructure, setting the id field to -1 and the name field to
         * null.
         *
         */
        protected NamedScorecardStructureImpl() {
            super();
        }

        /**
         * NamedScorecardStructure constructor: Creates a new
         * NamedScorecardStructure, setting the id field to the given value and the
         * name field to null.
         *
         * @param id
         *            The id of the scorecard structure
         * @throws IllegalArgumentException
         *             If id <= 0
         */
        protected NamedScorecardStructureImpl(long id) {
            super(id);
        }

        /**
         * NamedScorecardStructure constructor: Creates a new
         * NamedScorecardStructure, setting the id and name fields to the given
         * values.
         *
         * @param id
         *            The id of the scorecard structure
         * @param name
         *            The name of the scorecard structure
         * @throws IllegalArgumentException
         *             If id <= 0
         * @throws IllegalArgumentException
         *             If name is null
         */
        public NamedScorecardStructureImpl(long id, String name) {
            super(id, name);
        }
    }
}
