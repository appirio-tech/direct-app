/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;
import com.topcoder.asset.entities.Category;
import com.topcoder.asset.entities.CategorySearchCriteria;
import com.topcoder.asset.entities.PagedResult;
import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for {@link AssetCategoryServiceImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class AssetCategoryServiceImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>AssetCategoryServiceImpl</code> instance used in tests.
     * </p>
     */
    private AssetCategoryServiceImpl instance;

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
     * Represents the category used in tests.
     * </p>
     */
    private Category category;

    /**
     * <p>
     * Represents the criteria used in tests.
     * </p>
     */
    private CategorySearchCriteria criteria;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AssetCategoryServiceImplUnitTests.class);
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

        instance = new AssetCategoryServiceImpl();
        instance.setEntityManager(entityManager);
        instance.setLog(log);

        category = new Category();
        category.setName("category11");
        category.setContainerType("project");
        category.setContainerId(11);

        criteria = new CategorySearchCriteria();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AssetCategoryServiceImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new AssetCategoryServiceImpl();

        assertNull("'entityManager' should be correct.", getField(instance, "entityManager"));
        assertNull("'log' should be correct.", getField(instance, "log"));
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
     * Accuracy test for the method <code>create(Category category)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_create() throws Exception {
        entityManager.getTransaction().begin();
        instance.create(category);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertTrue("'create' should be correct.", category.getId() > 0);

        Category retrievedCategory = entityManager.find(Category.class, category.getId());

        assertEquals("'create' should be correct.",
            category.getName(), retrievedCategory.getName());
        assertEquals("'create' should be correct.",
            category.getContainerType(), retrievedCategory.getContainerType());
        assertEquals("'create' should be correct.",
            category.getContainerId(), retrievedCategory.getContainerId());
    }

    /**
     * <p>
     * Failure test for the method <code>create(Category category)</code> with category is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_create_categoryNull() throws Exception {
        category = null;

        instance.create(category);
    }

    /**
     * <p>
     * Failure test for the method <code>create(Category category)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_create_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        category.setName(null);
        instance.create(category);
    }

    /**
     * <p>
     * Failure test for the method <code>create(Category category)</code> with entity manager has been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_create_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.create(category);
    }

    /**
     * <p>
     * Accuracy test for the method <code>update(Category category)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_update() throws Exception {
        entityManager.getTransaction().begin();
        instance.create(category);
        entityManager.getTransaction().commit();
        entityManager.clear();

        category.setName("new");
        entityManager.getTransaction().begin();
        instance.update(category);
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertTrue("'update' should be correct.", category.getId() > 0);

        Category retrievedCategory = entityManager.find(Category.class, category.getId());

        assertEquals("'update' should be correct.",
            category.getName(), retrievedCategory.getName());
        assertEquals("'update' should be correct.",
            category.getContainerType(), retrievedCategory.getContainerType());
        assertEquals("'update' should be correct.",
            category.getContainerId(), retrievedCategory.getContainerId());
    }

    /**
     * <p>
     * Failure test for the method <code>update(Category category)</code> with category is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_categoryNull() throws Exception {
        category = null;

        instance.update(category);
    }

    /**
     * <p>
     * Failure test for the method <code>update(Category category)</code> with category.getId() is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_categoryIdNegative() throws Exception {
        category.setId(-1);

        instance.update(category);
    }

    /**
     * <p>
     * Failure test for the method <code>update(Category category)</code> with category.getId() is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_categoryIdZero() throws Exception {
        category.setId(0);

        instance.update(category);
    }

    /**
     * <p>
     * Failure test for the method <code>update(Category category)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_update_EntityNotFound() throws Exception {
        category.setId(Long.MAX_VALUE);

        instance.update(category);
    }

    /**
     * <p>
     * Failure test for the method <code>update(Category category)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_update_PersistenceError() throws Exception {
        category.setId(1);

        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.update(category);
    }

    /**
     * <p>
     * Failure test for the method <code>update(Category category)</code> with entity manager has been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_update_entityManagerClosed() throws Exception {
        category.setId(1);

        entityManager.close();

        instance.update(category);
    }

    /**
     * <p>
     * Accuracy test for the method <code>delete(long categoryId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_delete() throws Exception {
        entityManager.getTransaction().begin();
        instance.create(category);
        entityManager.getTransaction().commit();
        entityManager.clear();

        entityManager.getTransaction().begin();
        instance.delete(category.getId());
        entityManager.getTransaction().commit();
        entityManager.clear();

        Category retrievedCategory = entityManager.find(Category.class, category.getId());

        assertNull("'delete' should be correct.", retrievedCategory);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long categoryId)</code> with categoryId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_delete_categoryIdNegative() throws Exception {
        instance.delete(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long categoryId)</code> with categoryId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_delete_categoryIdZero() throws Exception {
        instance.delete(0);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long categoryId)</code> with
     * the entity is not found.<br>
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = EntityNotFoundException.class)
    public void test_delete_EntityNotFound() throws Exception {
        instance.delete(Long.MAX_VALUE);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long categoryId)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_delete_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.delete(1);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long categoryId)</code> with entity manager has been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_delete_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.delete(1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>get(long categoryId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_get_1() throws Exception {
        entityManager.getTransaction().begin();
        instance.create(category);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Category res = instance.get(category.getId());

        assertEquals("'get' should be correct.",
            category.getName(), res.getName());
        assertEquals("'get' should be correct.",
            category.getContainerType(), res.getContainerType());
        assertEquals("'get' should be correct.",
            category.getContainerId(), res.getContainerId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>get(long categoryId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_get_2() throws Exception {
        clearDB();

        Category res = instance.get(Long.MAX_VALUE);

        assertNull("'get' should be correct.", res);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long categoryId)</code> with categoryId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_get_categoryIdNegative() throws Exception {
        instance.get(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long categoryId)</code> with categoryId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_get_categoryIdZero() throws Exception {
        instance.get(0);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long categoryId)</code> with a persistence error has occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_get_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.get(1);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long categoryId)</code> with entity manager has been closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_get_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.get(1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(CategorySearchCriteria criteria)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_1() throws Exception {
        clearDB();

        PagedResult<Category> res = instance.search(criteria);

        assertEquals("'search' should be correct.", criteria.getPage(), res.getPage());
        assertEquals("'search' should be correct.", criteria.getPageSize(), res.getPageSize());
        assertEquals("'search' should be correct.", 0, res.getTotalPages());

        List<Category> records = res.getRecords();
        assertEquals("'search' should be correct.", 0, records.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(CategorySearchCriteria criteria)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_2() throws Exception {
        entityManager.getTransaction().begin();
        instance.create(category);
        entityManager.getTransaction().commit();
        entityManager.clear();

        PagedResult<Category> res = instance.search(criteria);

        assertEquals("'search' should be correct.", criteria.getPage(), res.getPage());
        assertEquals("'search' should be correct.", criteria.getPageSize(), res.getPageSize());
        assertEquals("'search' should be correct.", 1, res.getTotalPages());

        List<Category> records = res.getRecords();
        assertEquals("'search' should be correct.", 1, records.size());

        Category retrievedCategory = records.get(0);

        assertEquals("'search' should be correct.",
            category.getName(), retrievedCategory.getName());
        assertEquals("'search' should be correct.",
            category.getContainerType(), retrievedCategory.getContainerType());
        assertEquals("'search' should be correct.",
            category.getContainerId(), retrievedCategory.getContainerId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(CategorySearchCriteria criteria)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_3() throws Exception {
        Category category2 = new Category();
        category2.setName("category12");
        category2.setContainerType("project2");
        category2.setContainerId(12);

        entityManager.getTransaction().begin();
        instance.create(category);
        instance.create(category2);
        entityManager.getTransaction().commit();
        entityManager.clear();

        PagedResult<Category> res = instance.search(criteria);

        assertEquals("'search' should be correct.", criteria.getPage(), res.getPage());
        assertEquals("'search' should be correct.", criteria.getPageSize(), res.getPageSize());
        assertEquals("'search' should be correct.", 1, res.getTotalPages());

        List<Category> records = res.getRecords();
        assertEquals("'search' should be correct.", 2, records.size());

        assertEquals("'search' should be correct.",
            category2.getName(), records.get(0).getName());
        assertEquals("'search' should be correct.",
            category.getName(), records.get(1).getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(CategorySearchCriteria criteria)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_4() throws Exception {
        Category category2 = new Category();
        category2.setName("category12");
        category2.setContainerType("project2");
        category2.setContainerId(12);

        entityManager.getTransaction().begin();
        instance.create(category);
        instance.create(category2);
        entityManager.getTransaction().commit();
        entityManager.clear();

        criteria.setName("category12");
        criteria.setContainerType("project2");
        criteria.setContainerId(12L);
        PagedResult<Category> res = instance.search(criteria);

        assertEquals("'search' should be correct.", criteria.getPage(), res.getPage());
        assertEquals("'search' should be correct.", criteria.getPageSize(), res.getPageSize());
        assertEquals("'search' should be correct.", 1, res.getTotalPages());

        List<Category> records = res.getRecords();
        assertEquals("'search' should be correct.", 1, records.size());

        assertEquals("'search' should be correct.",
            category2.getName(), records.get(0).getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(CategorySearchCriteria criteria)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_5() throws Exception {
        Category category2 = new Category();
        category2.setName("category12");
        category2.setContainerType("project2");
        category2.setContainerId(12);

        entityManager.getTransaction().begin();
        instance.create(category);
        instance.create(category2);
        entityManager.getTransaction().commit();
        entityManager.clear();

        criteria.setPage(2);
        criteria.setPageSize(1);
        criteria.setSortingColumn("id");
        criteria.setAscending(true);
        PagedResult<Category> res = instance.search(criteria);

        assertEquals("'search' should be correct.", criteria.getPage(), res.getPage());
        assertEquals("'search' should be correct.", criteria.getPageSize(), res.getPageSize());
        assertEquals("'search' should be correct.", 2, res.getTotalPages());

        List<Category> records = res.getRecords();
        assertEquals("'search' should be correct.", 1, records.size());

        assertEquals("'search' should be correct.",
            category2.getName(), records.get(0).getName());
    }

    /**
     * <p>
     * Failure test for the method <code>search(CategorySearchCriteria criteria)</code> with
     * criteria is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_search_criteriaNull() throws Exception {
        criteria = null;

        instance.search(criteria);
    }

    /**
     * <p>
     * Failure test for the method <code>search(CategorySearchCriteria criteria)</code> with
     * criteria.page is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_search_criteriaPageNegative() throws Exception {
        criteria.setPage(-1);

        instance.search(criteria);
    }

    /**
     * <p>
     * Failure test for the method <code>search(CategorySearchCriteria criteria)</code> with
     * criteria.pageSize is negative when criteria.page is positive.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_search_criteriaPageSizeNegative() throws Exception {
        criteria.setPage(1);
        criteria.setPageSize(-1);

        instance.search(criteria);
    }

    /**
     * <p>
     * Failure test for the method <code>search(CategorySearchCriteria criteria)</code> with
     * criteria.pageSize is zero when criteria.page is positive.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_search_criteriaPageSizeZero() throws Exception {
        criteria.setPage(1);
        criteria.setPageSize(0);

        instance.search(criteria);
    }

    /**
     * <p>
     * Failure test for the method <code>search(CategorySearchCriteria criteria)</code> with a persistence error has
     * occurred.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_search_PersistenceError() throws Exception {
        instance.setEntityManager((EntityManager) APP_CONTEXT.getBean("entityManagerInvalid"));

        instance.search(criteria);
    }

    /**
     * <p>
     * Failure test for the method <code>search(CategorySearchCriteria criteria)</code> with entity manager has been
     * closed.<br>
     * <code>PersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = PersistenceException.class)
    public void test_search_entityManagerClosed() throws Exception {
        entityManager.close();

        instance.search(criteria);
    }
}