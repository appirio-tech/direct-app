/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.stresstests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.topcoder.direct.services.copilot.dao.impl.GenericDAOImpl;
import com.topcoder.direct.services.copilot.model.CopilotProfile;

/**
 * <p>
 * Stress tests for class <code>GenericDAOImpl</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class GenericDAOImplStreeTest extends StressBaseTest {

    /**
     * <p>
     * Represent the GenericDAOImpl instance that used to call its method for test. It will be
     * initialized in setUp().
     * </p>
     */
    @Autowired
    @Qualifier("genericDAO")
    private GenericDAOImpl<CopilotProfile> impl;

    /**
     * <p>
     * Stress test for the method <code>create()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreate() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < STRESS_TEST_TIME; i++) {

            CopilotProfile copilotProfile = createCopilotProfile();
            hibernateTemplate.save(copilotProfile.getStatus());

            assertEquals("The return value should be same as ", 0, copilotProfile.getId());

            impl.create(copilotProfile);

            assertTrue("The return value should be true ", (copilotProfile.getId() != 0));

            List<CopilotProfile> result =
                hibernateTemplate.find("from CopilotProfile where id = ?", copilotProfile.getId());

            assertEquals("The return value should be same as ", 1, result.size());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testCreate spend : " + (endTime - startTime) + " ms");
    }

    /**
     * <p>
     * Stress test for the method <code>update()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testUpdate() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < STRESS_TEST_TIME; i++) {

            CopilotProfile copilotProfile = createCopilotProfile();
            hibernateTemplate.save(copilotProfile.getStatus());
            hibernateTemplate.save(copilotProfile);

            copilotProfile.setCreateUser("aaaaaaa");
            copilotProfile.setReliability(66f);

            impl.update(copilotProfile);

            List<CopilotProfile> result =
                hibernateTemplate.find("from CopilotProfile where id = ?", copilotProfile.getId());

            assertEquals("The return value should be same as ", 1, result.size());
            assertEquals("The return value should be same as ", "aaaaaaa", result.get(0).getCreateUser());
            assertEquals("The return value should be same as ", 66f, result.get(0).getReliability(), 0.00001);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testUpdate spend : " + (endTime - startTime) + " ms");
    }

    /**
     * <p>
     * Stress test for the method <code>delete()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDelete() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < STRESS_TEST_TIME; i++) {

            CopilotProfile copilotProfile = createCopilotProfile();
            hibernateTemplate.save(copilotProfile.getStatus());
            hibernateTemplate.save(copilotProfile);

            List<CopilotProfile> result =
                hibernateTemplate.find("from CopilotProfile where id = ?", copilotProfile.getId());

            assertEquals("The return value should be same as ", 1, result.size());

            impl.delete(copilotProfile.getId());

            result = hibernateTemplate.find("from CopilotProfile where id = ?", copilotProfile.getId());

            assertEquals("The return value should be same as ", 0, result.size());

        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testDelete spend : " + (endTime - startTime) + " ms");
    }

    /**
     * <p>
     * Stress test for the method <code>retrieve()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieve() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < STRESS_TEST_TIME; i++) {

            CopilotProfile copilotProfile = createCopilotProfile();
            hibernateTemplate.save(copilotProfile.getStatus());
            hibernateTemplate.save(copilotProfile);

            CopilotProfile result = impl.retrieve(copilotProfile.getId());

            assertNotNull("The return value should be not null", result);

        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testRetrieve spend : " + (endTime - startTime) + " ms");
    }

    /**
     * <p>
     * Stress test for the method <code>retrieveAll()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveAll() throws Exception {
        long startTime = System.currentTimeMillis();

        CopilotProfile copilotProfile1 = createCopilotProfile();
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);

        CopilotProfile copilotProfile2 = createCopilotProfile();
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        for (int i = 0; i < STRESS_TEST_TIME; i++) {

            List<CopilotProfile> result = impl.retrieveAll();

            assertEquals("The return value should be same as ", 2, result.size());

            for (CopilotProfile cp : result) {
                if (cp.getId() == copilotProfile1.getId()) {
                    assertEquals("The return value should be same as ", copilotProfile1.getId(), cp.getId());
                } else {
                    assertEquals("The return value should be same as ", copilotProfile2.getId(), cp.getId());
                }
            }

        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testRetrieveAll spend : " + (endTime - startTime) + " ms");
    }

}
