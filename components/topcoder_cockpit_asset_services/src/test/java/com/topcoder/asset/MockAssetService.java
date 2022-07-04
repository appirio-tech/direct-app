/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetSearchCriteria;
import com.topcoder.asset.entities.Category;
import com.topcoder.asset.entities.PagedResult;
import com.topcoder.asset.exceptions.ServiceException;
import com.topcoder.asset.services.AssetService;

/**
 * <p>
 * A mockup class of AssetService. Used for testing.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class MockAssetService implements AssetService {
    /**
     * The flag indicating whether an exception should be thrown.
     */
    private boolean throwException;

    /**
     * The flag indicates whether it's public. Can be any value. Has getter and setter.
     */
    private boolean isPublic;

    /**
     * Creates an instance of MockAssetService.
     */
    public MockAssetService() {
        // Empty
    }

    /**
     * Sets the flag indicates whether it's public.
     *
     * @param isPublic
     *            the flag indicates whether it's public.
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Sets the flag indicating whether an exception should be thrown.
     *
     * @param throwException
     *            the flag indicating whether an exception should be thrown.
     */
    public void setThrowException(boolean throwException) {
        this.throwException = throwException;
    }

    /**
     * This method will create the asset. Its child entities currentVersion and categories needn't be managed, but
     * relationship should be managed.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param asset
     *            the asset to create
     */
    public void createAsset(long userId, Asset asset) {

    }

    /**
     * This method will update the asset. Its child entities currentVersion and categories need not be managed, but
     * relationship should be managed.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param asset
     *            the asset to update
     */
    public void updateAsset(long userId, Asset asset) {

    }

    /**
     * This method will delete the asset. All version files and all versions should also be deleted.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetId
     *            the asset id to delete
     */
    public void deleteAsset(long userId, long assetId) {

    }

    /**
     * This method will retrieve the asset with the given asset id. It will also retrieve its child entity
     * currentVersion:AssetVersion and categories:List&lt;Category&gt;.
     *
     * @param assetId
     *            the asset id
     *
     * @return the asset with the given id or null if none is found
     *
     * @throws ServiceException
     *             if any other error occurs.
     */
    public Asset getAsset(long assetId) throws ServiceException {
        if (throwException) {
            throw new ServiceException("ServiceException for testing.");
        }
        if (assetId == Long.MAX_VALUE) {
            return null;
        }

        // Prepare asset to create
        List<Category> categories = new ArrayList<Category>();
        Category category1 = new Category();
        category1.setId(100);
        categories.add(category1);

        Category category2 = new Category();
        category2.setId(101);
        categories.add(category2);

        Asset asset = new Asset();
        asset.setPublic(isPublic);
        asset.setId(assetId);
        asset.setName("design document");
        asset.setCategories(categories);
        asset.setContainerType("containerType1");
        asset.setContainerId(1);

        return asset;
    }

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
     */
    public PagedResult<Asset> searchAssets(AssetSearchCriteria criteria) {
        return null;
    }

    /**
     * This method will batch create the assets.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assets
     *            the list of assets to create
     */
    public void createAssets(long userId, List<Asset> assets) {

    }

    /**
     * This method will batch delete the assets.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetIds
     *            the list of asset ids to delete.
     */
    public void deleteAssets(long userId, List<Long> assetIds) {

    }

    /**
     * This method will batch update the assets.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assets
     *            the list of assets to update
     */
    public void updateAssets(long userId, List<Asset> assets) {

    }
}
