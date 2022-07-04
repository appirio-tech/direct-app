/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.gameplan.stresstests;

import com.topcoder.security.TCSubject;
import com.topcoder.service.gameplan.ejb.GamePlanServiceBean;
import com.topcoder.service.util.gameplan.SoftwareProjectData;
import com.topcoder.service.util.gameplan.StudioProjectData;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanData;
import org.hibernate.ejb.Ejb3Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transaction;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

/**
 * <p>
 * Stress test for <code>GamePlanServiceBean</code>.
 * This class is not thread safe.
 * </p>
 *
 * @author assistant
 * @version 1.0
 */
public class GamePlanServiceBeanTest {

    /**
     * <p>Represents the workload.</p>
     */
    private static final int LOAD = 100;

    /**
     * <p>Instance to test.</p>
     */
    private GamePlanServiceBean instance;

    /**
     * <p>Represents the entity manager for tcs_catalog database.</p>
     */
    private EntityManager softwareEntityManager;

    /**
     * <p>Represents the entity manager for studio_oltp database.</p>
     */
    private EntityManager studioEntityManager;

    /**
     * <p>Set up the environment.</p>
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new GamePlanServiceBean();
        setField(instance, "logName", "UnitTest");

        setField(instance, "softwareEntityManager", getSoftwareEntityManager());
        setField(instance, "studioEntityManager", getStudioEntityManager());

        invoke(instance, "initialize");

        clearDB();

        // initialize the software db (tcs_catalog)
        EntityTransaction t = getSoftwareEntityManager().getTransaction();
        // create 1000 tcs projects
        for (int i = 1; i <= LOAD; i++) {
            Query query = getSoftwareEntityManager().createNativeQuery(
                    "insert into project(project_id, project_status_id, project_category_id,"
                    + "tc_direct_project_id, create_user, create_date, modify_user, modify_date)"
                    + " values (" + i + ", 1, 1, " + i + ", 'user', CURRENT, 'user', CURRENT);");
            query.executeUpdate();
        }
        // create 1000 tc direct projects
        for (int i = 1; i <= LOAD; i++) {
            Query query = getSoftwareEntityManager().createNativeQuery(
                    "insert into tc_direct_project(project_id, name, description, user_id,"
                    + "create_date, modify_date)"
                    + " values(" + i + ", 'Project Name 1', 'Project Description 1', 1, CURRENT, CURRENT);");
            query.executeUpdate();
        }
        // create phases for these 1000 projects
        for (int i = 1; i <= LOAD; i++) {
            Query query = getSoftwareEntityManager().createNativeQuery(
                    "insert into project_phase(project_phase_id, project_id, phase_type_id, phase_status_id,"
                    + "scheduled_start_time, scheduled_end_time, duration, create_user, create_date, modify_user,"
                    + " modify_date) values (" + i + ", " + i + ", 1, 1, '2010-05-09 10:49:11.000', '2010-05-30 10:49:11.000',"
                    + "1, 'user', CURRENT, 'user', CURRENT);");
            query.executeUpdate();
        }
        t.commit();

        // initialize the studio db (studio_oltp)
        t = getStudioEntityManager().getTransaction();
        // create 1000 contest
        for (int i = 1; i <= LOAD; i++) {
            Query query = getStudioEntityManager().createNativeQuery(
                    "insert into contest (contest_id, name, project_id, tc_direct_project_id, start_time,"
                    + "end_time, contest_detailed_status_id) values "
                    + "(" + i + ", 'Contest Name 1', " + i + ", " + i + ","
                    + "'2010-05-01 10:49:11.000', '2010-05-14 10:49:11.000', 2);");
            query.executeUpdate();
        }
        t.commit();
    }

    /**
     * Inovke a non-public method on the given instance.
     * @param instance the instance on which the invoking is done
     * @param name the method name
     * @throws Exception if anything goes wrong
     */
    private void invoke(Object instance, String name) throws Exception {
        Method method = instance.getClass().getDeclaredMethod(name);
        try {
            method.setAccessible(true);
            method.invoke(instance);
        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }

    /**
     * <p>Clean up the environment.</p>
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        clearDB();
    }

    /**
     * Clears up the database.
     * @throws Exception to JUnit
     */
    private void clearDB() throws Exception {
        EntityTransaction t = getSoftwareEntityManager().getTransaction();
        Query query = getSoftwareEntityManager().createNativeQuery("delete from project_phase");
        query.executeUpdate();
        query = getSoftwareEntityManager().createNativeQuery("delete from tc_direct_project");
        query.executeUpdate();
        query = getSoftwareEntityManager().createNativeQuery("delete from project");
        query.executeUpdate();
        t.commit();

        t = getStudioEntityManager().getTransaction();
        query = getStudioEntityManager().createNativeQuery("delete from tc_direct_project");
        query.executeUpdate();
        query = getStudioEntityManager().createNativeQuery("delete from contest");
        query.executeUpdate();
        t.commit();        
    }

    /**
     * <p>Tests the <code>retrieveGamePlanData(TCSubject)</code> method.</p>
     * @throws Exception to JUnit
     */
    @Test
    public void testRetrieveGamePlanData() throws Exception {

        TCSubject tcSubject = new TCSubject(1l);

        long start = System.currentTimeMillis();
        List<TCDirectProjectGamePlanData> result = null;
        for (int i = 0; i < 10; i++) {
            result = instance.retrieveGamePlanData(tcSubject);
        }
        long end = System.currentTimeMillis();
        System.out.println("Run the retrieval method used " + (end - start) + "ms");

        // check the result
        assertEquals("There should be 100 game plan data entry.", LOAD, result.size());

        for (int i = 1; i <= LOAD; i++) {
            boolean found = false;
            for (int j = 1 ; j <= LOAD; j++) {
                List<SoftwareProjectData> softwareProjectDataList = result.get(j - 1).getSoftwareProjects();
                if (i == softwareProjectDataList.get(0).getProjectId()) {
                    found = true;
                    break;
                }
            }
            assertTrue("Not found for project " + i, found);
        }
    }

    /**
     * <p>Gets EntityManager for tcs_catalog database.</p>
     *
     * @return EntityManager
     */
    private EntityManager getSoftwareEntityManager() {
        if (softwareEntityManager == null || !softwareEntityManager.isOpen()) {
            // create entityManager
            Ejb3Configuration cfg = new Ejb3Configuration();
            cfg.configure("hibernate_software.cfg.xml");

            EntityManagerFactory emf = cfg.buildEntityManagerFactory();
            softwareEntityManager = emf.createEntityManager();
        }

        softwareEntityManager.clear();
        if (!softwareEntityManager.getTransaction().isActive()) {
            softwareEntityManager.getTransaction().begin();
        }
        return softwareEntityManager;
    }

    /**
     * <p>Gets EntityManager for studio_oltp database.</p>
     *
     * @return EntityManager
     */
    private EntityManager getStudioEntityManager() {
        if (studioEntityManager == null || !studioEntityManager.isOpen()) {
            // create entityManager
            Ejb3Configuration cfg = new Ejb3Configuration();
            cfg.configure("hibernate_studio.cfg.xml");

            EntityManagerFactory emf = cfg.buildEntityManagerFactory();
            studioEntityManager = emf.createEntityManager();
        }

        studioEntityManager.clear();
        if (!studioEntityManager.getTransaction().isActive()) {
            studioEntityManager.getTransaction().begin();
        }
        return studioEntityManager;
    }

    /**
     * <p>Set the specified field of the given object.</p>
     *
     * @param object    the object that holds the field
     * @param fieldName the name of the field
     * @param value     the value to set
     * @throws Exception pass any unexpected exception to JUnit.
     */
    private static void setField(Object object, String fieldName, Object value) throws Exception {
        Field field = null;
        Class<?> instanceClass = object.getClass();
        while (null == field) {
            try {
                field = instanceClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                instanceClass = instanceClass.getSuperclass();
                if (null == instanceClass) {
                    throw e;
                }
            }
        }

        field.setAccessible(true);
        field.set(object, value);
    }
}