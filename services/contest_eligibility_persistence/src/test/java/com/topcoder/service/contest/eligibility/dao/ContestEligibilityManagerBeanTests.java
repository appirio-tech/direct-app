/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.GroupContestEligibility;
import com.topcoder.service.contest.eligibility.MockContestEligibility;

/**
 * <p>
 * UnitTest cases of the <code>ContestEligibilityManagerBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestEligibilityManagerBeanTests extends TestCase {

    /**
     * Represent the entityManager used for testing.
     */
    private EntityManager entityManager;

    /**
     * <p>
     * Represents the <code>ContestEligibilityManagerBean</code> instance used for test.
     * </p>
     */
    private ContestEligibilityManagerBean bean;

    /**
     * <p>
     * Creates a test suite for the tests.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ContestEligibilityManagerBeanTests.class);
        return suite;
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void setUp() throws Exception {
        entityManager = Persistence.createEntityManagerFactory("ContestEligibilityPersistence").createEntityManager();
        bean = new ContestEligibilityManagerBean();
        setPrivateField(ContestEligibilityManagerBean.class, bean, "entityManager", entityManager);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void tearDown() throws Exception {
        bean = null;
    }

    /**
     * <p>
     * Accuracy test case for constructor ContestEligibilityManagerBean.It verifies the new instance is
     * created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Unable to instantiate Entity", bean);
    }

    /**
     * <p>
     * Accuracy test case for initialize.It verifies that the logger will be initialized correctly in
     * initialize method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeAccuracy() throws Exception {
        assertNull("logger should be null before executing the initialize method.", getPrivateField(bean,
            "logger"));
        bean.initialize();
        assertNotNull("logger should not be null after executing the initialize method.", getPrivateField(
            bean, "logger"));
    }

    /**
     * <p>
     * Accuracy test case for create.It verifies that we can persist a new GroupContestEligibility instance
     * correctly.Actually,we also verify the mapping is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateAccuracy() throws Exception {
        runSQL("drop.sql", entityManager);
        bean.initialize();
        GroupContestEligibility groupContestEligibility = createGroupContestEligibility();
        entityManager.getTransaction().begin();
        bean.create(groupContestEligibility);
        entityManager.getTransaction().commit();
        checkEqualGroupContestEligibilities(groupContestEligibility, entityManager.find(
            GroupContestEligibility.class, groupContestEligibility.getId()));
    }

    /**
     * <p>
     * Failure test case for create.IAE is expected because the argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateFailure1() throws Exception {
        bean.initialize();
        try {
            bean.create(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for create.ContestEligibilityPersistenceException is expected because the
     * MockContestEligibility is not an entity.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateFailure2() throws Exception {
        bean.initialize();
        try {
            bean.create(new MockContestEligibility());
            fail("ContestEligibilityPersistenceException should be thrown.");
        } catch (ContestEligibilityPersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for remove.It verifies that we can remove the existed GroupContestEligibility
     * instance correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveAccuracy() throws Exception {
        runSQL("drop.sql", entityManager);
        bean.initialize();
        GroupContestEligibility groupContestEligibility =
            insertGroupContestEligibility(createGroupContestEligibility());
        entityManager.getTransaction().begin();
        bean.remove(groupContestEligibility);
        entityManager.getTransaction().commit();
        assertNull("Should be null because it has been removed.", entityManager.find(
            GroupContestEligibility.class, groupContestEligibility.getId()));
    }

    /**
     * <p>
     * Failure test case for remove.IAE is expected because the argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveFailure1() throws Exception {
        bean.initialize();
        try {
            bean.remove(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for remove.ContestEligibilityPersistenceException is expected because the
     * MockContestEligibility is not an entity.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveFailure2() throws Exception {
        bean.initialize();
        try {
            bean.remove(new MockContestEligibility());
            fail("ContestEligibilityPersistenceException should be thrown.");
        } catch (ContestEligibilityPersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for save.It verifies that we can save a list of contest eligibility correctly. This
     * test case prove that we can insert ContestEligibility by using save method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSaveAccuracy1() throws Exception {
        runSQL("drop.sql", entityManager);
        bean.initialize();
        // create an added ContestEligibility
        GroupContestEligibility addedContestEligibility = createGroupContestEligibility();
        List<ContestEligibility> list = new ArrayList<ContestEligibility>();
        list.add(addedContestEligibility);

        // save list
        entityManager.getTransaction().begin();
        List<ContestEligibility> returnedList = bean.save(list);
        entityManager.getTransaction().commit();

        // check returned list size
        assertTrue("The size should be 1.", returnedList.size() == 1);
        assertTrue("The addedContestEligibility should be the first item of return list.",
            addedContestEligibility == returnedList.get(0));

        // check whether the addedContestEligibility has been inserted into DB correctly.
        checkEqualGroupContestEligibilities(addedContestEligibility, entityManager.find(
            GroupContestEligibility.class, addedContestEligibility.getId()));
    }

    /**
     * <p>
     * Accuracy test case for save.It verifies that we can save a list of contest eligibility correctly. This
     * test case prove that we can update ContestEligibility by using save method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSaveAccuracy2() throws Exception {
        runSQL("drop.sql", entityManager);
        bean.initialize();
        // create an updated ContestEligibility
        GroupContestEligibility updatedContestEligibility =
            insertGroupContestEligibility(createGroupContestEligibility());
        updatedContestEligibility.setContestId(11);
        updatedContestEligibility.setStudio(false);
        updatedContestEligibility.setGroupId(11);
        List<ContestEligibility> list = new ArrayList<ContestEligibility>();
        list.add(updatedContestEligibility);

        // save list
        entityManager.getTransaction().begin();
        List<ContestEligibility> returnedList = bean.save(list);
        entityManager.getTransaction().commit();

        // check returned list size
        assertTrue("The size should be 1.", returnedList.size() == 1);
        assertTrue("The addedContestEligibility should be the first item of return list.",
            updatedContestEligibility == returnedList.get(0));

        // check whether the addedContestEligibility has been updated into DB correctly.
        checkEqualGroupContestEligibilities(entityManager.find(GroupContestEligibility.class,
            updatedContestEligibility.getId()), updatedContestEligibility);
    }

    /**
     * <p>
     * Accuracy test case for save.It verifies that we can save a list of contest eligibility correctly. This
     * test case prove that we can delete ContestEligibility by using save method.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSaveAccuracy3() throws Exception {
        runSQL("drop.sql", entityManager);
        bean.initialize();
        // create an deleted ContestEligibility
        GroupContestEligibility deletedContestEligibility =
            insertGroupContestEligibility(createGroupContestEligibility());
        deletedContestEligibility.setDeleted(true);
        List<ContestEligibility> list = new ArrayList<ContestEligibility>();
        list.add(deletedContestEligibility);

        // save list
        entityManager.getTransaction().begin();
        List<ContestEligibility> returnedList = bean.save(list);
        entityManager.getTransaction().commit();

        // check returned list size
        assertTrue("The size should be 0.", returnedList.size() == 0);

        // check whether the deletedContestEligibility has been removed correctly.
        assertNull("Should have been removed.", entityManager.find(GroupContestEligibility.class,
            deletedContestEligibility.getId()));
    }

    /**
     * <p>
     * Failure test case for save.IAE is expected because the argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSaveFailure1() throws Exception {
        bean.initialize();
        try {
            bean.save(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for save.IAE is expected because the argument list contains null element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSaveFailure2() throws Exception {
        bean.initialize();
        List<ContestEligibility> list = new ArrayList<ContestEligibility>();
        list.add(createGroupContestEligibility());
        list.add(createGroupContestEligibility());
        list.add(null);
        try {
            bean.save(list);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for save.ContestEligibilityPersistenceException is expected because the
     * MockContestEligibility is not an entity.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSaveFailure3() throws Exception {
        bean.initialize();
        List<ContestEligibility> list = new ArrayList<ContestEligibility>();
        list.add(new MockContestEligibility());
        try {
            bean.save(list);
            fail("ContestEligibilityPersistenceException should be thrown.");
        } catch (ContestEligibilityPersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for getContestEligibility.It verifies that we can get a list of contest eligibility
     * correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestEligibilityAccuracy() throws Exception {
        bean.initialize();

        // insert one ContestEligibility with contestId=1 and isStudio=true
        GroupContestEligibility first = createGroupContestEligibility();
        insertGroupContestEligibility(first);

        // insert one ContestEligibility with contestId=2 and isStudio=true
        GroupContestEligibility second = createGroupContestEligibility();
        second.setContestId(2);
        insertGroupContestEligibility(second);

        // insert one ContestEligibility with contestId=1 and isStudio=false
        GroupContestEligibility third = createGroupContestEligibility();
        third.setStudio(false);
        insertGroupContestEligibility(third);

        // search first
        List<ContestEligibility> resultList = bean.getContestEligibility(1, true);
        assertTrue("Only one contest eligibility matched.", resultList.size() == 1);
        checkEqualGroupContestEligibilities(first, (GroupContestEligibility) resultList.get(0));
        runSQL("drop.sql", entityManager);
    }


    /**
     * <p>
     * Accuracy test case for getContestEligibility.It verifies that we can get a list of contest eligibility
     * correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testHavetEligibilityAccuracy() throws Exception {
        bean.initialize();

        GroupContestEligibility first = createGroupContestEligibility();
        first.setContestId(1);
        first.setStudio(false);
        insertGroupContestEligibility(first);


        GroupContestEligibility second = createGroupContestEligibility();
        second.setContestId(2);
        second.setStudio(false);
        insertGroupContestEligibility(second);


        
        GroupContestEligibility third = createGroupContestEligibility();
        third.setContestId(3);
        third.setStudio(false);
        insertGroupContestEligibility(third);



        // search first
        Set<Long> resultList = bean.haveEligibility(new long[] {1,2,3,4,5,6}, false);
        assertTrue("Only three should return", resultList.size() == 3);
        runSQL("drop.sql", entityManager);
    }


    /**
     * <p>
     * Failure test case for getContestEligibility.IAE is expected because the contestId is 0.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestEligibilityFailure() throws Exception {
        bean.initialize();
        try {
            bean.getContestEligibility(0, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Check whether two GroupContestEligibilities are equal.
     *
     * @param expected
     *            the expected GroupContestEligibility
     * @param actual
     *            the actual GroupContestEligibility
     */
    private static void checkEqualGroupContestEligibilities(GroupContestEligibility expected,
        GroupContestEligibility actual) {
        assertEquals("The id should be equal.", expected.getId(), actual.getId());
        assertEquals("The group id should be equal.", expected.getGroupId(), actual.getGroupId());
        assertEquals("The contest id should be equal.", expected.getContestId(), actual.getContestId());
        assertEquals("The studio flag should be equal.", expected.isStudio(), actual.isStudio());
    }

    /**
     * Create a new GroupContestEligibility instance used for testing.
     *
     * @return a new GroupContestEligibility instance
     */
    private static GroupContestEligibility createGroupContestEligibility() {
        GroupContestEligibility groupContestEligibility = new GroupContestEligibility();
        groupContestEligibility.setContestId(1);
        groupContestEligibility.setGroupId(2);
        groupContestEligibility.setStudio(true);
        return groupContestEligibility;
    }

    /**
     * Insert a new GroupContestEligibility into DB.
     *
     * @param groupContestEligibility
     *            the added groupContestEligibility
     * @return the added GroupContestEligibility
     */
    private GroupContestEligibility insertGroupContestEligibility(
        GroupContestEligibility groupContestEligibility) {
        entityManager.getTransaction().begin();
        entityManager.persist(groupContestEligibility);
        entityManager.getTransaction().commit();
        return groupContestEligibility;
    }

    /**
     * <p>
     * Runs the given SQL file.
     * </p>
     *
     * @param filePath
     *            The filePath of SQL file
     * @param em
     *            the entity manager
     * @throws Exception
     *             to JUnit
     */
    private static void runSQL(String filePath, EntityManager em) throws Exception {
        String content = getFileAsString(filePath);
        em.getTransaction().begin();
        for (String st : content.split(";")) {
            em.createNativeQuery(st).executeUpdate();
        }
        em.getTransaction().commit();
    }

    /**
     * <p>
     * Gets the file content as string.
     * </p>
     *
     * @param filePath
     *            The filePath of SQL file
     * @return The content of file
     * @throws Exception
     *             to JUnit
     */
    private static String getFileAsString(String filePath) throws Exception {
        StringBuilder buf = new StringBuilder();
        BufferedReader in =
            new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(filePath)));
        try {
            String s;
            while ((s = in.readLine()) != null) {
                buf.append(s);
            }
            return buf.toString();
        } finally {
            in.close();
        }
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

    /**
     * <p>
     * Gets the value of a private field in the given instance by given name.
     * </p>
     *
     * @param instance
     *            the instance which the private field belongs to.
     * @param name
     *            the name of the private field to be retrieved.
     * @return the value of the private field.
     */
    private static Object getPrivateField(Object instance, String name) {
        Object obj = null;
        try {
            Field field = instance.getClass().getDeclaredField(name);

            // Set the field accessible.
            field.setAccessible(true);

            // Get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        }
        return obj;
    }
}