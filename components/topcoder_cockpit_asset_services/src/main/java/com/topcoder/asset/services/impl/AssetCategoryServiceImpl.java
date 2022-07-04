/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.asset.entities.Category;
import com.topcoder.asset.entities.CategorySearchCriteria;
import com.topcoder.asset.entities.PagedResult;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.services.AssetCategoryService;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of AssetCategoryService that uses injected JPA EntityManager interface for
 * accessing Category data in database. This class uses Logging Wrapper logger to perform logging. It's assumed that
 * this class is initialized with Spring setter dependency injection only.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
 *        xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
 *        xmlns:context=&quot;http://www.springframework.org/schema/context&quot;
 *        xmlns:tx=&quot;http://www.springframework.org/schema/tx&quot;
 *        xmlns:aop=&quot;http://www.springframework.org/schema/aop&quot;
 *        xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
 *        http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
 *        http://www.springframework.org/schema/context
 *        http://www.springframework.org/schema/context/spring-context-2.5.xsd
 *        http://www.springframework.org/schema/tx
 *        http://www.springframework.org/schema/tx/spring-tx-2.5.xsd
 *        http://www.springframework.org/schema/aop
 *        http://www.springframework.org/schema/aop/spring-aop-2.5.xsd&quot;&gt;
 *
 *     &lt;context:annotation-config/&gt;
 *     &lt;bean class=&quot;org.springframework.context.annotation.CommonAnnotationBeanPostProcessor&quot;/&gt;
 *
 *     &lt;bean id=&quot;assetCategoryService&quot;
 *         class=&quot;com.topcoder.asset.services.impl.AssetCategoryServiceImpl&quot;&gt;
 *         &lt;property name=&quot;entityManager&quot; ref=&quot;entityManager&quot;/&gt;
 *         &lt;property name=&quot;log&quot; ref=&quot;log&quot;/&gt;
 *         &lt;/bean&gt;
 *
 *
 *     &lt;!-- Log --&gt;
 *     &lt;bean id=&quot;log&quot;
 *         class=&quot;org.springframework.beans.factory.config.MethodInvokingFactoryBean&quot;&gt;
 *         &lt;property name=&quot;staticMethod&quot;&gt;
 *             &lt;value&gt;com.topcoder.util.log.LogManager.getLog&lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;arguments&quot;&gt;
 *             &lt;list&gt;
 *                 &lt;value&gt;com.topcoder.asset.logger&lt;/value&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *
 *
 *     &lt;bean id=&quot;entityManagerFactory&quot;
 *       class=&quot;org.springframework.orm.jpa.LocalEntityManagerFactoryBean&quot; &gt;
 *       &lt;property name=&quot;persistenceUnitName&quot; value=&quot;persistenceUnit&quot; /&gt;
 *     &lt;/bean&gt;
 *     &lt;bean id=&quot;entityManager&quot;
 *         class = &quot;org.springframework.orm.jpa.support.SharedEntityManagerBean&quot;&gt;
 *             &lt;property name = &quot;entityManagerFactory&quot; ref=&quot;entityManagerFactory&quot;/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean class=&quot;org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor&quot; /&gt;
 *     &lt;context:annotation-config/&gt;
 *     &lt;context:component-scan base-package=&quot;com.topcoder.asset.entities&quot; /&gt;
 *
 *     &lt;!-- the transactional advice (what 'happens'; see the &lt;aop:advisor/&gt; bean below) --&gt;
 *     &lt;tx:advice id=&quot;txAdvice&quot; transaction-manager=&quot;txManager&quot;&gt;
 *       &lt;tx:attributes&gt;
 *         &lt;tx:method name=&quot;create*&quot;/&gt;
 *         &lt;tx:method name=&quot;remove*&quot;/&gt;
 *         &lt;tx:method name=&quot;set*&quot;/&gt;
 *         &lt;tx:method name=&quot;update*&quot;/&gt;
 *         &lt;tx:method name=&quot;delete*&quot;/&gt;
 *         &lt;tx:method name=&quot;*&quot; read-only=&quot;true&quot;/&gt;
 *       &lt;/tx:attributes&gt;
 *     &lt;/tx:advice&gt;
 *
 *     &lt;!-- ensure that the above transactional advice runs for any execution of an operation
 *         defined by the service interfaces --&gt;
 *     &lt;aop:config&gt;
 *       &lt;aop:pointcut id=&quot;serviceOperation&quot;
 *       expression=&quot;execution(* com.topcoder.asset.services.impl.*Service*.*(..))&quot;/&gt;
 *       &lt;aop:advisor advice-ref=&quot;txAdvice&quot; pointcut-ref=&quot;serviceOperation&quot;/&gt;
 *     &lt;/aop:config&gt;
 *
 *  &lt;bean id=&quot;txManager&quot; class=&quot;org.springframework.orm.jpa.JpaTransactionManager&quot;&gt;
 *      &lt;property name=&quot;entityManagerFactory&quot; ref=&quot;entityManagerFactory&quot; /&gt;
 *  &lt;/bean&gt;
 *
 * &lt;/beans&gt;
 *
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Load application context
 * ApplicationContext context = new ClassPathXmlApplicationContext(&quot;beans.xml&quot;);
 *
 * // Retrieve AssetCategoryService from the Spring application context
 * AssetCategoryService assetCategoryService = (AssetCategoryService) context.getBean(&quot;assetCategoryService&quot;);
 *
 * // Prepare category
 * Category category = new Category();
 * category.setName(&quot;category11&quot;);
 * category.setContainerType(&quot;project&quot;);
 * category.setContainerId(11);
 *
 * // Create category
 * assetCategoryService.create(category);
 * // The corresponding record will be inserted into database. And the id of newly created category will be set.
 *
 * category.setContainerId(12);
 * // Update category
 * assetCategoryService.update(category);
 * // The corresponding record will be updated in database
 *
 * // Retrieve category
 * category = assetCategoryService.get(category.getId());
 * // The corresponding record will be retrieved from database;
 *
 * // Prepare criteria
 * CategorySearchCriteria criteria = new CategorySearchCriteria();
 * criteria.setPage(1);
 * criteria.setPageSize(10);
 * criteria.setSortingColumn(&quot;name&quot;);
 * criteria.setAscending(true);
 * criteria.setContainerType(&quot;project&quot;);
 *
 * // Search categories
 * PagedResult&lt;Category&gt; result = assetCategoryService.search(criteria);
 * // The corresponding matched result will be returned, sorted by name.
 *
 * // Remove category
 * assetCategoryService.delete(category.getId());
 * // The corresponding record will be removed in database
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable since it provides public setters for its properties. But it
 * doesn't change its state and is thread safe when the following condition is met: this class is initialized by
 * Spring right after construction and its parameters are never changed after that. All entities passed to this class
 * are used by the caller in thread safe manner (accessed from a single thread only).
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class AssetCategoryServiceImpl extends BaseMiscService implements AssetCategoryService {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = AssetCategoryServiceImpl.class.getName();

    /**
     * The JPQL to query the category.
     */
    private static final String JPQL_QUERY_CATEGORY = "SELECT e FROM Category e ";

    /**
     * The JPQL to query the count of categories.
     */
    private static final String JPQL_QUERY_CATEGORIES_COUNT = "SELECT COUNT(e) FROM Category e ";

    /**
     * <p>
     * Represents the fields of category.
     * </p>
     */
    private static final List<String> CATEGORY_FIELDS = Arrays.asList("id", "name", "containerType", "containerId");

    /**
     * Creates an instance of AssetCategoryServiceImpl.
     */
    public AssetCategoryServiceImpl() {
        // Empty
    }

    /**
     * This method will create the asset category. It will also set newly assigned id to the given entity.
     *
     * @param category
     *            the asset category to create.
     *
     * @throws IllegalArgumentException
     *             if category is null.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public void create(Category category) throws PersistenceException {
        String signature = CLASS_NAME + ".create(Category category)";

        MiscHelper.create(getLog(), signature, getEntityManager(), category, "category");
    }

    /**
     * This method will update the asset category.
     *
     * @param category
     *            the asset category to update.
     *
     * @throws IllegalArgumentException
     *             if category is null, or category.id is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public void update(Category category) throws PersistenceException {
        String signature = CLASS_NAME + ".update(Category category)";

        MiscHelper.update(getLog(), signature, getEntityManager(), category, "category");
    }

    /**
     * This method will remove the asset category with the given id.
     *
     * @param categoryId
     *            the id of the asset category to remove.
     *
     * @throws IllegalArgumentException
     *             if categoryId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public void delete(long categoryId) throws PersistenceException {
        String signature = CLASS_NAME + ".delete(long categoryId)";

        MiscHelper.delete(getLog(), signature, getEntityManager(), Category.class, categoryId, "categoryId");
    }

    /**
     * This method will retrieve the asset category with the given id.
     *
     * @param categoryId
     *            the id of the asset category to retrieve.
     *
     * @return the asset category with the given id
     *
     * @throws IllegalArgumentException
     *             if categoryId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public Category get(long categoryId) throws PersistenceException {
        String signature = CLASS_NAME + ".get(long categoryId)";

        return MiscHelper.get(getLog(), signature, getEntityManager(), Category.class, categoryId, "categoryId");
    }

    /**
     * This method will search the asset categories with the given criteria. Any field of CategorySearchCriteria is
     * optional, null means a condition is ignored. All fields of CategorySearchCriteria are exact match. If
     * criteria.page = 0, all matched result should be returned.
     *
     * @param criteria
     *            the category search criteria.
     *
     * @return the paged result of the matched asset categories.
     *
     * @throws IllegalArgumentException
     *             if criteria is null, or criteria.page &lt; 0, or criteria.page &gt; 0 and criteria.pageSize &lt;=
     *             0.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    public PagedResult<Category> search(CategorySearchCriteria criteria) throws PersistenceException {
        String signature = CLASS_NAME + ".search(CategorySearchCriteria criteria)";
        Log log = getLog();

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"criteria"},
            new Object[] {MiscHelper.toString(criteria)});

        try {
            ParameterCheckUtility.checkNotNull(criteria, "criteria");

            int page = criteria.getPage();
            ParameterCheckUtility.checkNotNegative(page, "criteria.getPage()");

            int pageSize = criteria.getPageSize();
            if ((page > 0) && (pageSize <= 0)) {
                throw new IllegalArgumentException("criteria.getPageSize() should be positive"
                    + " when criteria.getPage() is positive.");
            }

            PagedResult<Category> result = search(getEntityManager(), criteria, page, pageSize);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {MiscHelper.toString(result)});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * Searches asset categories.
     *
     * @param entityManager
     *            the entity manager
     * @param criteria
     *            the criteria
     * @param page
     *            the page
     * @param pageSize
     *            the page size
     *
     * @return the result
     *
     * @throws IllegalStateException
     *             if the entity manager has been closed
     * @throws javax.persistence.PersistenceException
     *             if any error occurs while accessing the persistence
     */
    @SuppressWarnings("unchecked")
    private static PagedResult<Category> search(EntityManager entityManager, CategorySearchCriteria criteria,
        int page, int pageSize) {
        List<String> paramNames = new ArrayList<String>();
        List<Object> paramValues = new ArrayList<Object>();
        String whereClause = buildWhere(criteria, paramNames, paramValues);

        String orderByClause = buildOrderBy(criteria);

        // Create query
        Query query = entityManager.createQuery(JPQL_QUERY_CATEGORY + whereClause + orderByClause);

        // Set parameters
        setParameters(query, paramNames, paramValues);

        // Set pagination
        if (page > 0) {
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
        }

        // Execute the query:
        List<Category> categories = query.getResultList();

        // Build paged result:
        // Create result instance
        PagedResult<Category> result = new PagedResult<Category>();

        // Set page, page size, records:
        result.setPage(page);
        result.setPageSize(pageSize);
        result.setRecords(categories);

        // Set total pages
        if (page == 0) {
            if (!categories.isEmpty()) {
                result.setTotalPages(1);
            }
        } else {
            // Create query
            query = entityManager.createQuery(JPQL_QUERY_CATEGORIES_COUNT + whereClause);
            // Set parameters
            setParameters(query, paramNames, paramValues);

            // Execute the query
            int totalRecordsNum = ((Number) query.getSingleResult()).intValue();

            // Set total pages
            result.setTotalPages((totalRecordsNum + pageSize - 1) / pageSize);
        }

        return result;
    }

    /**
     * Sets the parameters.
     *
     * @param query
     *            the query
     * @param paramNames
     *            the parameter names
     * @param paramValues
     *            the parameter values
     */
    private static void setParameters(Query query, List<String> paramNames, List<Object> paramValues) {
        int paramSize = paramNames.size();
        for (int i = 0; i < paramSize; i++) {
            query.setParameter(paramNames.get(i), paramValues.get(i));
        }
    }

    /**
     * Builds the ORDER BY string.
     *
     * @param criteria
     *            the criteria
     *
     * @return the ORDER BY string.
     */
    private static String buildOrderBy(CategorySearchCriteria criteria) {
        StringBuilder sb = new StringBuilder();

        String sortingColumn = criteria.getSortingColumn();
        if (!CATEGORY_FIELDS.contains(sortingColumn)) {
            sb.append(" ORDER BY e.name");
        } else {
            sb.append(" ORDER BY e.").append(sortingColumn);
        }

        sb.append(" ").append(criteria.getAscending() ? "ASC" : "DESC");

        return sb.toString();
    }

    /**
     * Builds the WHERE string.
     *
     * @param criteria
     *            the criteria
     * @param paramNames
     *            the parameter name
     * @param paramValues
     *            the parameter values
     *
     * @return the WHERE string.
     */
    private static String buildWhere(CategorySearchCriteria criteria, List<String> paramNames,
        List<Object> paramValues) {
        StringBuilder sb = new StringBuilder();

        appendCondition(sb, "e.name = :name", criteria.getName(), "name", paramNames, paramValues);
        appendCondition(sb, "e.containerType = :containerType", criteria.getContainerType(), "containerType",
            paramNames, paramValues);
        appendCondition(sb, "e.containerId = :containerId", criteria.getContainerId(), "containerId", paramNames,
            paramValues);

        if (sb.length() != 0) {
            sb.insert(0, " WHERE ");
        }

        return sb.toString();
    }

    /**
     * Appends the condition string.
     *
     * @param sb
     *            the string builder
     * @param str
     *            the condition string.
     * @param value
     *            the value
     * @param name
     *            the name
     * @param paramNames
     *            the parameter name
     * @param paramValues
     *            the parameter values
     */
    private static void appendCondition(StringBuilder sb, String str, Object value, String name,
        List<String> paramNames, List<Object> paramValues) {
        if (value == null) {
            return;
        }

        if (sb.length() != 0) {
            sb.append(" AND ");
        }

        sb.append(str);

        paramNames.add(name);
        paramValues.add(value);
    }
}
