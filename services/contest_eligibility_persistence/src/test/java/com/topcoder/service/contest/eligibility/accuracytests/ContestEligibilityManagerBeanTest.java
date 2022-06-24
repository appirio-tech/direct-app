/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.accuracytests;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.TestCase;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.GroupContestEligibility;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityManagerBean;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for class <code>ContestEligibilityManagerBean</code>.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestEligibilityManagerBeanTest extends TestCase {

    /**
     * <p>
     * SQL used to clear database.
     * </p>
     */
    private static final String[] CLEAR =
        {"DELETE FROM group_contest_eligibility", "DELETE FROM contest_eligibility"};
    /**
     * persistence unit name.
     */
    private static final String PERSISTENCE_UNIT = "ContestEligibilityPersistence";
    /**
     * Represents the entity manager used for CRUD operations on entity.
     */
    private static EntityManager manager;

    /**
     * <p>
     * Represent the ContestEligibilityManagerBean instance is used to call its method for test. It will be
     * initialized in setUp().
     * </p>
     */
    private ContestEligibilityManagerBean instance;
    /**
     * <p>
     * Sets the entity manager.
     * </p>
     */
    static {
        manager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();
    }

    /**
     * <p>
     * Set the test environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        instance = new ContestEligibilityManagerBean();
        setPrivateField(ContestEligibilityManagerBean.class, instance, "entityManager", manager);
        setPrivateField(ContestEligibilityManagerBean.class, instance, "logger", LogManager.getLog("test"));
        clearDatabase();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * 
     * @throws Exception
     *             to jUnit.
     */
    public void tearDown() throws Exception {
        instance = null;
        clearDatabase();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestEligibilityManagerBean()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */

    public void testConstuctor() {
        instance = new ContestEligibilityManagerBean();
        assertNotNull("The instance should be created successfully", instance);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestEligibility(long, boolean)</code>.<br>
     * Expects no error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */

    public void testGetContestEligibility() throws Exception {
        GroupContestEligibility entity1 = new GroupContestEligibility();
        entity1.setContestId(123);
        entity1.setStudio(true);
        entity1.setGroupId(1);
        instance.create(entity1);

        GroupContestEligibility entity2 = new GroupContestEligibility();
        entity2.setContestId(123);
        entity2.setStudio(true);
        entity2.setGroupId(2);
        instance.create(entity2);

        GroupContestEligibility entity3 = new GroupContestEligibility();
        entity3.setContestId(111);
        entity3.setStudio(false);
        entity3.setGroupId(4);
        instance.create(entity3);

        List<ContestEligibility> result = instance.getContestEligibility(123, true);
        assertEquals("The list size should be 0", 0, result.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>create(ContestEligibility)</code>.<br>
     * Expects no error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */

    public void testCreate() throws Exception {
        GroupContestEligibility entity = new GroupContestEligibility();
        entity.setContestId(111);
        entity.setStudio(true);
        entity.setGroupId(222);
        ContestEligibility created = instance.create(entity);
        assertTrue("The created entity id should be 111", (created.getContestId() == 111));
        assertTrue("The created entity id should not be 0", (created.getId() > 0));
    }

    /**
     * <p>
     * Accuracy test for the method <code>remove(ContestEligibility)</code>.<br>
     * Expects no error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */

    public void testRemvoe() throws Exception {
        List<ContestEligibility> result = instance.getContestEligibility(111, true);
        assertTrue("The entity list should be empty", result.isEmpty());

        GroupContestEligibility entity1 = new GroupContestEligibility();
        entity1.setContestId(111);
        entity1.setStudio(true);
        entity1.setGroupId(1);
        entity1 = (GroupContestEligibility) instance.create(entity1);

        result = instance.getContestEligibility(111, true);
        assertEquals("The result list's size should be ", 0, result.size());

        instance.remove(entity1);
        result = instance.getContestEligibility(111, true);
        assertTrue("The entity list should be empty", result.isEmpty());
    }

    /**
     * <p>
     * Accuracy test for the method <code>save(List&lt;ContestEligibility&gt;)</code>.<br>
     * Expects no error occurs.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */

    public void testSave() throws Exception {
        GroupContestEligibility entity1 = new GroupContestEligibility();
        entity1.setContestId(111);
        entity1.setStudio(true);
        entity1.setGroupId(1);
        instance.create(entity1);

        entity1.setDeleted(true);

        GroupContestEligibility entity2 = new GroupContestEligibility();
        entity2.setContestId(111);
        entity2.setStudio(true);
        entity2.setGroupId(22);
        instance.create(entity2);

        GroupContestEligibility entity3 = new GroupContestEligibility();
        entity3.setContestId(222);
        entity3.setStudio(true);
        entity3.setGroupId(33);

        List<ContestEligibility> list = new ArrayList<ContestEligibility>();
        list.add(entity1);
        list.add(entity2);
        list.add(entity3);

        List<ContestEligibility> result = instance.save(list);
        assertEquals("The list size should be ", 2, result.size());
        assertEquals("The entity's contest id should be 111 ", 111, result.get(0).getContestId());
        assertEquals("The entity's contest id should be 222 ", 222, result.get(1).getContestId());
        result = instance.getContestEligibility(111, true);
        assertEquals("The list size should be ", 0, result.size());
    }

    /**
     * <p>
     * Gets the entity manager.
     * </p>
     * 
     * @return the entity manager
     */
    private static EntityManager getEntityManager() {
        if (manager == null || !manager.isOpen()) {
            manager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();
        }
        return manager;
    }

    /**
     * Clears the database tables.
     */
    private static void clearDatabase() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        for (String sql : CLEAR) {
            em.createNativeQuery(sql).executeUpdate();
        }
        em.getTransaction().commit();
    }

    /**
     * <p>
     * Sets the value of a private field in the given class.
     * </p>
     *
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be set
     * @param value
     *            the value to set
     * @param classType
     *            the class to get field
     */
    @SuppressWarnings("unchecked")
    private static void setPrivateField(Class classType, Object instance, String name, Object value) {
        try {
            Field field = null;
            // get the reflection of the field
            field = classType.getDeclaredField(name);
            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (SecurityException e) {
            // Ignore
        } catch (IllegalArgumentException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        }
    }

}
