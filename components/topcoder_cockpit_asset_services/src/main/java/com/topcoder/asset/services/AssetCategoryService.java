/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services;

import com.topcoder.asset.entities.Category;
import com.topcoder.asset.entities.CategorySearchCriteria;
import com.topcoder.asset.entities.PagedResult;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;

/**
 * <p>
 * This interface represents an asset category service. It defines CURD and search methods for AssetCategory.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> Implementations of this interface are required to be thread safe when entities
 * passed to them are used by the caller in thread safe manner.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public interface AssetCategoryService {
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
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void create(Category category) throws ServiceException;

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
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void update(Category category) throws ServiceException;

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
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void delete(long categoryId) throws ServiceException;

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
     * @throws ServiceException
     *             if any other error occurs.
     */
    public Category get(long categoryId) throws ServiceException;

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
     * @throws ServiceException
     *             if any other error occurs.
     */
    public PagedResult<Category> search(CategorySearchCriteria criteria) throws ServiceException;
}
