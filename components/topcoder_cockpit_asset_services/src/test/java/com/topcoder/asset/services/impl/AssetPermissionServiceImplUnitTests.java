/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;
import com.topcoder.asset.MockAssetService;
import com.topcoder.asset.entities.AssetPermission;
import com.topcoder.asset.entities.User;
import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for {@link AssetPermissionServiceImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class AssetPermissionServiceImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>AssetPermissionServiceImpl</code> instance used in tests.
     * </p>
     */
    private AssetPermissionServiceImpl instance;

    /**
     * <p>
     * Represents the entity manager used in tests.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>
     * Represents the log used in tests.
     * </p>
     */
    private Log log = LogManager.getLog(getClass().getName());

    /**
     * <p>
     * Represents the asset service used in tests.
     * </p>
     */
    private MockAssetService assetService;

    /**
     * <p>
     * Represents the asset id used in tests.
     * </p>
     */
    private long assetId = 1;

    /**
     * <p>
     * Represents the user id used in tests.
     * </p>
     */
    private long userId = 1;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AssetPermissionServiceImplUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        loadDB(true);

        entityManager = getEntityManager();
        assetService = new MockAssetService();

        instance = new AssetPermissionServiceImpl();
        instance.setEntityManager(entityManager);
        instance.setLog(log);
        instance.setAssetService(assetService);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AssetPermissionServiceImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new AssetPermissionServiceImpl();

        assertNull("'entityManager' should be correct.", getField(instance, "entityManager"));
        assertNull("'log' should be correct.", getField(instance, "log"));
        assertNull("'assetService' should be correct.", getField(instance, "assetService"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInit()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_checkInit_1() {
        instance.checkInit();

        assertNotNull("'checkInit' should be correct.", getField(instance, "entityManager"));
        assertNotNull("'checkInit' should be correct.", getField(instance, "log"));
        assertNotNull("'checkInit' should be correct.", getField(instance, "assetService"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInit()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_checkInit_2() {
        instance.setLog(null);

        instance.checkInit();

        assertNotNull("'checkInit' should be correct.", getField(instance, "entityManager"));
        assertNull("'checkInit' should be correct.", getField(instance, "log"));
        assertNotNull("'checkInit' should be correct.", getField(instance, "assetService"));
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code>
     * with entityManager is null.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_entityManagerNull() {
        instance.setEntityManager(null);

        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code>
     * with assetService is null.<br>
     * <code>AssetConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = AssetConfigurationException.class)
    public void test_checkInit_assetServiceNull() {
        instance.setAssetService(null);

        instance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for the method <code>createPermission(long assetId, long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createPermission() throws Exception {
        entityManager.getTransaction().begin();
        instance.createPermission(assetId, userId);
        entityManager.getTransaction().commit();
        entityManager.clear();

        AssetPermission retrievedAssetPermission = (AssetPermission) entityManager.createQuery(
            "SELECT e FROM AssetPermission e WHERE e.assetId=:assetId")
            .setParameter("assetId", assetId)
            .getSingleResult();

        assertTrue("'createPermission' should be correct.",
            retrievedAssetPermission.getId() > 0);
        assertEquals("'createPermission' should be correct.",
            assetId, retrievedAssetPermission.getAssetId());
        assertEquals("'createPermission' should be correct.",
            userId, retrievedAssetPermission.getUser().getId());
    }

    /**
     * <p>
     * Failure test for the method <code>createPermission(long assetId, long userId)</code> with assetId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createPermission_assetIdNegative() throws Exception {
        assetId = -1;

        instance.createPermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>createPermission(long assetId, long userId)</code> with assetId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createPermission_assetIdZero() throws Exception {
        assetId = 0;

        instance.createPermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>createPermission(long assetId, long userId)</code> with userId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createPermission_userIdNegative() throws Exception {
        userId = -1;

        instance.createPermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>createPermission(long assetId, long userId)</code> with userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createPermission_userIdZero() throws Exception {
        userId = 0;

        instance.createPermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>createPermission(long assetId, long userId)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_createPermission_EntityNotFound1() throws Exception {
        assetId = Long.MAX_VALUE;

        instance.createPermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>createPermission(long assetId, long userId)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_createPermission_EntityNotFound2() throws Exception {
        userId = Long.MAX_VALUE;

        instance.createPermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>createPermission(long assetId, long userId)</code> with a persistence error
     * has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_createPermission_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.createPermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>createPermission(long assetId, long userId)</code> with entity manager has
     * been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_createPermission_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.createPermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>createPermission(long assetId, long userId)</code> with an error has
     * occurred.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_createPermission_Error() throws Exception {
        assetService.setThrowException(true);

        instance.createPermission(assetId, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>removePermission(long assetId, long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_removePermission_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createPermission(assetId, userId);
        entityManager.getTransaction().commit();
        entityManager.clear();

        entityManager.getTransaction().begin();
        instance.removePermission(assetId, userId);
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<AssetPermission> list = entityManager.createQuery(
            "SELECT e FROM AssetPermission e WHERE e.assetId=:assetId")
            .setParameter("assetId", assetId)
            .getResultList();

        assertEquals("'removePermission' should be correct.", 0, list.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>removePermission(long assetId, long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_removePermission_2() throws Exception {
        entityManager.getTransaction().begin();
        instance.createPermission(assetId, userId);
        instance.createPermission(assetId, 2);
        entityManager.getTransaction().commit();
        entityManager.clear();

        entityManager.getTransaction().begin();
        instance.removePermission(assetId, userId);
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<AssetPermission> list = entityManager.createQuery(
            "SELECT e FROM AssetPermission e WHERE e.assetId=:assetId")
            .setParameter("assetId", assetId)
            .getResultList();

        assertEquals("'removePermission' should be correct.", 1, list.size());
        assertEquals("'removePermission' should be correct.", 2, list.get(0).getUser().getId());
    }

    /**
     * <p>
     * Failure test for the method <code>removePermission(long assetId, long userId)</code> with assetId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_removePermission_assetIdNegative() throws Exception {
        assetId = -1;

        instance.removePermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>removePermission(long assetId, long userId)</code> with assetId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_removePermission_assetIdZero() throws Exception {
        assetId = 0;

        instance.removePermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>removePermission(long assetId, long userId)</code> with userId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_removePermission_userIdNegative() throws Exception {
        userId = -1;

        instance.removePermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>removePermission(long assetId, long userId)</code> with userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_removePermission_userIdZero() throws Exception {
        userId = 0;

        instance.removePermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>removePermission(long assetId, long userId)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_removePermission_EntityNotFound1() throws Exception {
        assetId = Long.MAX_VALUE;

        instance.removePermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>removePermission(long assetId, long userId)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_removePermission_EntityNotFound2() throws Exception {
        userId = Long.MAX_VALUE;

        instance.removePermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>removePermission(long assetId, long userId)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_removePermission_EntityNotFound3() throws Exception {
        entityManager.getTransaction().begin();
        instance.removePermission(assetId, userId);
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    /**
     * <p>
     * Failure test for the method <code>removePermission(long assetId, long userId)</code> with a persistence error
     * has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_removePermission_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.removePermission(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>removePermission(long assetId, long userId)</code> with entity manager has
     * been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_removePermission_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.removePermission(assetId, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>isAllowed(long assetId, long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_isAllowed_1() throws Exception {
        clearDB();

        assetId = Long.MAX_VALUE;
        boolean res = instance.isAllowed(assetId, userId);
        assertFalse("'isAllowed' should be correct.", res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>isAllowed(long assetId, long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_isAllowed_2() throws Exception {
        entityManager.getTransaction().begin();
        entityManager.createQuery(
            "UPDATE Asset e SET e.isPublic=:isPublic WHERE e.id=:id")
            .setParameter("isPublic", false)
            .setParameter("id", assetId)
            .executeUpdate();
        entityManager.getTransaction().commit();

        boolean res = instance.isAllowed(assetId, userId);
        assertFalse("'isAllowed' should be correct.", res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>isAllowed(long assetId, long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_isAllowed_3() throws Exception {
        assetService.setPublic(true);

        boolean res = instance.isAllowed(assetId, userId);
        assertTrue("'isAllowed' should be correct.", res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>isAllowed(long assetId, long userId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_isAllowed_4() throws Exception {
        entityManager.getTransaction().begin();
        instance.createPermission(assetId, userId);
        entityManager.getTransaction().commit();
        entityManager.clear();

        boolean res = instance.isAllowed(assetId, userId);
        assertTrue("'isAllowed' should be correct.", res);
    }

    /**
     * <p>
     * Failure test for the method <code>isAllowed(long assetId, long userId)</code> with assetId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_isAllowed_assetIdNegative() throws Exception {
        assetId = -1;

        instance.isAllowed(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>isAllowed(long assetId, long userId)</code> with assetId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_isAllowed_assetIdZero() throws Exception {
        assetId = 0;

        instance.isAllowed(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>isAllowed(long assetId, long userId)</code> with userId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_isAllowed_userIdNegative() throws Exception {
        userId = -1;

        instance.isAllowed(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>isAllowed(long assetId, long userId)</code> with userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_isAllowed_userIdZero() throws Exception {
        userId = 0;

        instance.isAllowed(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>isAllowed(long assetId, long userId)</code> with a persistence error
     * has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_isAllowed_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.isAllowed(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>isAllowed(long assetId, long userId)</code> with entity manager has
     * been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_isAllowed_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.isAllowed(assetId, userId);
    }

    /**
     * <p>
     * Failure test for the method <code>isAllowed(long assetId, long userId)</code> with an error has occurred.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_isAllowed_Error() throws Exception {
        assetService.setThrowException(true);

        instance.isAllowed(assetId, userId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAllowedUsersForAsset(long assetId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAllowedUsersForAsset_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.createPermission(assetId, userId);
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<User> res = instance.getAllowedUsersForAsset(assetId);
        assertEquals("'getAllowedUsersForAsset' should be correct.", 1, res.size());

        assertEquals("'getAllowedUsersForAsset' should be correct.", userId, res.get(0).getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAllowedUsersForAsset(long assetId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAllowedUsersForAsset_2() throws Exception {
        entityManager.getTransaction().begin();
        instance.createPermission(assetId, userId);
        instance.createPermission(assetId, 2);
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<User> res = instance.getAllowedUsersForAsset(assetId);
        assertEquals("'getAllowedUsersForAsset' should be correct.", 2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAllowedUsersForAsset(long assetId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAllowedUsersForAsset_3() throws Exception {
        clearDB();

        List<User> res = instance.getAllowedUsersForAsset(assetId);
        assertEquals("'getAllowedUsersForAsset' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAllowedUsersForAsset(long assetId)</code> with assetId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAllowedUsersForAsset_assetIdNegative() throws Exception {
        assetId = -1;

        instance.getAllowedUsersForAsset(assetId);
    }

    /**
     * <p>
     * Failure test for the method <code>getAllowedUsersForAsset(long assetId)</code> with assetId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAllowedUsersForAsset_assetIdZero() throws Exception {
        assetId = 0;

        instance.getAllowedUsersForAsset(assetId);
    }

    /**
     * <p>
     * Failure test for the method <code>getAllowedUsersForAsset(long assetId)</code> with a persistence error
     * has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getAllowedUsersForAsset_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.getAllowedUsersForAsset(assetId);
    }

    /**
     * <p>
     * Failure test for the method <code>getAllowedUsersForAsset(long assetId)</code> with entity manager has
     * been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_getAllowedUsersForAsset_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.getAllowedUsersForAsset(assetId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPermissions(List&lt;Long&gt; assetIds,
     * List&lt;Long&gt; userIds)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_setPermissions_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.setPermissions(Arrays.asList(assetId), Arrays.asList(userId));
        entityManager.getTransaction().commit();
        entityManager.clear();

        AssetPermission retrievedAssetPermission = (AssetPermission) entityManager.createQuery(
            "SELECT e FROM AssetPermission e WHERE e.assetId=:assetId")
            .setParameter("assetId", assetId)
            .getSingleResult();

        assertTrue("'setPermissions' should be correct.",
            retrievedAssetPermission.getId() > 0);
        assertEquals("'setPermissions' should be correct.",
            assetId, retrievedAssetPermission.getAssetId());
        assertEquals("'setPermissions' should be correct.",
            userId, retrievedAssetPermission.getUser().getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPermissions(List&lt;Long&gt; assetIds,
     * List&lt;Long&gt; userIds)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_setPermissions_2() throws Exception {
        entityManager.getTransaction().begin();
        instance.setPermissions(Arrays.asList(assetId, 2L), Arrays.asList(userId, 2L));
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<AssetPermission> list = entityManager.createQuery(
            "SELECT e FROM AssetPermission e WHERE e.assetId=:assetId")
            .setParameter("assetId", assetId)
            .getResultList();

        assertEquals("'setPermissions' should be correct.", 2, list.size());

        list = entityManager.createQuery("SELECT e FROM AssetPermission e WHERE e.assetId=:assetId")
            .setParameter("assetId", 2L)
            .getResultList();

        assertEquals("'setPermissions' should be correct.", 2, list.size());
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(List&lt;Long&gt; assetIds,
     * List&lt;Long&gt; userIds)</code> with assetIds is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setPermissions_assetIdsNull() throws Exception {
        instance.setPermissions(null, Arrays.asList(userId));
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(List&lt;Long&gt; assetIds,
     * List&lt;Long&gt; userIds)</code> with assetIds contains null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setPermissions_assetIdsContainsNull() throws Exception {
        instance.setPermissions(Arrays.asList(assetId, null), Arrays.asList(userId));
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(List&lt;Long&gt; assetIds,
     * List&lt;Long&gt; userIds)</code> with assetIds contains negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setPermissions_assetIdsContainsNegative() throws Exception {
        instance.setPermissions(Arrays.asList(-1L), Arrays.asList(userId));
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(List&lt;Long&gt; assetIds,
     * List&lt;Long&gt; userIds)</code> with assetIds contains zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setPermissions_assetIdsContainsZero() throws Exception {
        instance.setPermissions(Arrays.asList(0L), Arrays.asList(userId));
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(List&lt;Long&gt; assetIds,
     * List&lt;Long&gt; userIds)</code> with userIds is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setPermissions_userIdsNull() throws Exception {
        instance.setPermissions(Arrays.asList(assetId), null);
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(List&lt;Long&gt; assetIds,
     * List&lt;Long&gt; userIds)</code> with userIds contains null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setPermissions_userIdsContainsNull() throws Exception {
        instance.setPermissions(Arrays.asList(assetId), Arrays.asList(userId, null));
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(List&lt;Long&gt; assetIds,
     * List&lt;Long&gt; userIds)</code> with userIds contains negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setPermissions_userIdsContainsNegative() throws Exception {
        instance.setPermissions(Arrays.asList(assetId), Arrays.asList(-1L));
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(List&lt;Long&gt; assetIds,
     * List&lt;Long&gt; userIds)</code> with userIds contains zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setPermissions_userIdsContainsZero() throws Exception {
        instance.setPermissions(Arrays.asList(assetId), Arrays.asList(0L));
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(long assetId, long userId)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_setPermissions_EntityNotFound1() throws Exception {
        assetId = Long.MAX_VALUE;

        instance.setPermissions(Arrays.asList(assetId), Arrays.asList(userId));
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(long assetId, long userId)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_setPermissions_EntityNotFound2() throws Exception {
        userId = Long.MAX_VALUE;

        instance.setPermissions(Arrays.asList(assetId), Arrays.asList(userId));
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(long assetId, long userId)</code> with a persistence error
     * has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_setPermissions_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.setPermissions(Arrays.asList(assetId), Arrays.asList(userId));
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(long assetId, long userId)</code> with entity manager has
     * been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_setPermissions_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.setPermissions(Arrays.asList(assetId), Arrays.asList(userId));
    }

    /**
     * <p>
     * Failure test for the method <code>setPermissions(long assetId, long userId)</code> with an error has
     * occurred.<br>
     * <code>ServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ServiceException.class)
    public void test_setPermissions_Error() throws Exception {
        assetService.setThrowException(true);

        instance.setPermissions(Arrays.asList(assetId), Arrays.asList(userId));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAssetService(AssetService assetService)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAssetService() {
        instance.setAssetService(assetService);

        assertSame("'setAssetService' should be correct.",
            assetService, getField(instance, "assetService"));
    }
}