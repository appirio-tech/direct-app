/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.failuretests;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.GenericDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;

/**
 * Failure test for class GenericDAOImpl.
 *
 * @author extra
 * @version 1.0
 */
public class GenericDAOImplFailureTests {

    /**
     * The dao for failure test.
     */
    private GenericDAO<CopilotProfile> dao;

    /**
     * The dao for failure test.
     */
    private GenericDAO<CopilotProfile> failDao;

    /**
     * <p>
     * Gets test which is compatible with JUnit3 runner.
     * </p>
     *
     * @return The test which is compatible with JUnit3 runner.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(GenericDAOImplFailureTests.class);
    }

    /**
     * <p>
     * Gets <code>CopilotProfileDAO</code> instance from Spring bean factory.
     * </p>
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        dao = (GenericDAO<CopilotProfile>) FailureTestHelper.getBeanFactory().getBean("copilotProfileDAO");

        failDao = (GenericDAO<CopilotProfile>) FailureTestHelper.getFailBeanFactory().getBean("copilotProfileDAO");
    }

    /**
     * Failure test for method create.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateFail1() throws Exception {
        dao.create((CopilotProfile) null);
    }

    /**
     * Failure test for method create.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testCreateFail2() throws Exception {
        CopilotProfile entity = new CopilotProfile();
        failDao.create(entity);
    }

    /**
     * Failure test for method update.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateFail1() throws Exception {
        dao.update((CopilotProfile) null);
    }

    /**
     * Failure test for method update.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testUpdateFail2() throws Exception {
        CopilotProfile entity = new CopilotProfile();
        entity.setId(1);
        failDao.update(entity);
    }

    /**
     * Failure test for method delete.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteFail1() throws Exception {
        dao.delete(-1);
    }

    /**
     * Failure test for method delete.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testDeleteFail2() throws Exception {
        failDao.delete(1);
    }

    /**
     * Failure test for method retrieve.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveFail1() throws Exception {
        dao.retrieve(-1);
    }

    /**
     * Failure test for method retrieve.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testRetrieveFail2() throws Exception {
        failDao.retrieve(1);
    }

    /**
     * Failure test for method retrieveAll.
     *
     * @throws Exception
     *             to Junit
     */
    @Test(expected = CopilotDAOException.class)
    public void testRetrieveAllFail1() throws Exception {
        failDao.retrieveAll();
    }

}
