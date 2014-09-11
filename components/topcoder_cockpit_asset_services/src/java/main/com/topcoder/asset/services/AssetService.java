/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services;

import java.util.List;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetSearchCriteria;
import com.topcoder.asset.entities.PagedResult;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;

/**
 * <p>
 * This interface represents an asset service. It defines CURD and search methods for asset. It also define methods to
 * batch create/delete/update asset.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> Implementations of this interface are required to be thread safe when entities
 * passed to them are used by the caller in thread safe manner.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Asset View Release 3) changes:
 * <ul>
 *     <li>Adds method {@link #getAssets(long[])}</li>
 * </ul>
 * </p>
 *
 * @author LOY, sparemax, TCSASSEMBLER
 * @version 1.1
 */
public interface AssetService {
    /**
     * This method will create the asset. Its child entities currentVersion and categories needn't be managed, but
     * relationship should be managed.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param asset
     *            the asset to create
     *
     * @throws IllegalArgumentException
     *             if userId is not positive or asset is null.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void createAsset(long userId, Asset asset) throws ServiceException;

    /**
     * This method will update the asset. Its child entities currentVersion and categories need not be managed, but
     * relationship should be managed.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param asset
     *            the asset to update
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, asset is null, or asset.id is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void updateAsset(long userId, Asset asset) throws ServiceException;

    /**
     * This method will delete the asset. All version files and all versions should also be deleted.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetId
     *            the asset id to delete
     *
     * @throws IllegalArgumentException
     *             if userId or assetId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void deleteAsset(long userId, long assetId) throws ServiceException;

    /**
     * This method will retrieve the asset with the given asset id. It will also retrieve its child entity
     * currentVersion:AssetVersion and categories:List&lt;Category&gt;.
     *
     * @param assetId
     *            the asset id
     *
     * @return the asset with the given id or null if none is found
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public Asset getAsset(long assetId) throws ServiceException;

    /**
     * This method will retrieve the asset with the given asset id. It will also retrieve its child entity
     * currentVersion:AssetVersion and categories:List&lt;Category&gt;.
     *
     * @param assetIds
     *            the array of asset id
     *
     * @return the assets with the specified ids or empty list if none if found
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     * @since 1.1
     */
    public List<Asset> getAssets(long[] assetIds) throws ServiceException;

    /**
     * This method will search the asset with the given criteria. Any field of AssetSearchCriteria is optional, null
     * means a condition is ignored. AssetSearchCriteria.name is partial matching condition, it is matched if asset
     * name contains the given name. Other string condition matching is exact match.. If criteria.page = 0, all
     * matched result should be returned. If criteria.sortingColumn is null, or not any field of Asset, the result
     * will be sorted by name.
     *
     * @param criteria
     *            the asset search criteria.
     *
     * @return the paged result of the matched asset.
     *
     * @throws IllegalArgumentException
     *             if criteria is null, or criteria.page &lt; 0, or criteria.page &gt; 0 and criteria.pageSize &lt;=
     *             0.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public PagedResult<Asset> searchAssets(AssetSearchCriteria criteria) throws ServiceException;

    /**
     * This method will batch create the assets.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assets
     *            the list of assets to create
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, assets is null, or any element is null.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void createAssets(long userId, List<Asset> assets) throws ServiceException;

    /**
     * This method will batch delete the assets.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetIds
     *            the list of asset ids to delete.
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, assetIds is null, or any element is null or not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void deleteAssets(long userId, List<Long> assetIds) throws ServiceException;

    /**
     * This method will batch update the assets.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assets
     *            the list of assets to update
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, assets is null, or any element is null, or the id of any element is not
     *             positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void updateAssets(long userId, List<Asset> assets) throws ServiceException;
}
