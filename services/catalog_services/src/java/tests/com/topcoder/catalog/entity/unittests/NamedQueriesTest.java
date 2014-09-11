/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.entity.TestHelper;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * <p>Unit test case for the named queries.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class NamedQueriesTest extends TestCase {
    /**
     * <p>The <p>EntityManager</p> instance for testing.</p>
     */
    private EntityManager entityManager;
    /**
     * <p>Transaction context for testing.</p>
     */
    private EntityTransaction entityTransaction;

    /**
     * <p>Sets up testing environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.bindIdGenerator();
        TestHelper.prepareTables(); // clear data before a test
        entityManager = TestHelper.getEntityManager();
        entityTransaction = TestHelper.getEntityTransaction();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.close(); // close entity manager and transaction
        TestHelper.clearTables(); // clear data
        TestHelper.unbindIdGenerator();
        entityManager = null;
        entityTransaction = null;
        super.tearDown();
    }

    /**
     * <p>Tests <tt>getAllPhases</tt> query.</p>
     *
     * @throws Exception to jUnit
     */
    public void testAllPhases() throws Exception {
        // there should be 4 predefined phases created by test_data.sql
        Query query = entityManager.createNamedQuery("getAllPhases");

        @SuppressWarnings("unchecked")
        final List<Phase> phases = query.getResultList();
        // check the result
        assertEquals("There should be all created phases", 4, phases.size());
    }

    /**
     * <p>Tests <tt>getActiveTechnologies</tt> query.</p>
     *
     * @throws Exception to jUnit
     */
    public void testActiveTechnologies() throws Exception {
        entityTransaction.begin();
        // create active technology
        Technology technology1 = new Technology();
        technology1.setDescription("technology1");
        technology1.setStatus(Status.ACTIVE);
        technology1.setName("1");
        // not active one
        Technology technology2 = new Technology();
        technology2.setDescription("technology2");
        technology2.setStatus(Status.DELETED);
        technology2.setName("2");
        entityManager.persist(technology1);
        entityManager.persist(technology2);
        entityTransaction.commit();

        Query query = entityManager.createNamedQuery("getActiveTechnologies");

        @SuppressWarnings("unchecked")
        final List<Technology> technologies = query.getResultList();
        // check the result
        assertEquals("There should be one active technology", 1, technologies.size());
        assertEquals("There should be the first technology", "1", technologies.get(0).getName());
    }

    /**
     * <p>Tests <tt>getActiveCategories</tt> query.</p>
     *
     * @throws Exception to jUnit
     */
    public void testActiveCategories() throws Exception {
        // there are four predefined categories created by test_data.sql
        // of them is active and viewable
        Query query = entityManager.createNamedQuery("getActiveCategories");

        @SuppressWarnings("unchecked")
        final List<Category> categories = query.getResultList();
        // check the result
        assertEquals("There should be one active category", 1, categories.size());
        assertEquals("There should be the first category", "1", categories.get(0).getName());
    }
}