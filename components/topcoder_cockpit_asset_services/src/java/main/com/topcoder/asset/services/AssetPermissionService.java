/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services;

import java.util.List;

import com.topcoder.asset.entities.User;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;

/**
 * <p>
 * This interface represents an asset permission service. It defines methods for create asset permission, remove asset
 * permission, check if the asset is public or the user has permission to access it, get allowed users for asset, and
 * set asset permissions.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> Implementations of this interface are required to be thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public interface AssetPermissionService {
    /**
     * This method will create the asset permission.
     *
     * @param assetId
     *            the asset id
     * @param userId
     *            the user id
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive, or userId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void createPermission(long assetId, long userId) throws ServiceException;

    /**
     * This method will remove the asset permission.
     *
     * @param assetId
     *            the asset id
     * @param userId
     *            the user id
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive, or userId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void removePermission(long assetId, long userId) throws ServiceException;

    /**
     * This method will check if the asset is public or the user has permission to access it.
     *
     * @param assetId
     *            the asset id
     * @param userId
     *            the user id
     *
     * @return true, if the asset is public or the user has permission to access it; false, otherwise.
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive, or userId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public boolean isAllowed(long assetId, long userId) throws ServiceException;

    /**
     * This method will get allowed users for the given asset.
     *
     * @param assetId
     *            the asset id
     *
     * @return the allowed users for the given asset.
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public List<User> getAllowedUsersForAsset(long assetId) throws ServiceException;

    /**
     * This method will set permissions for the given assets and users.
     *
     * @param assetIds
     *            the given asset ids
     * @param userIds
     *            the given user ids
     *
     * @throws IllegalArgumentException
     *             if assetIds or userIds is null, or contains null or not positive element.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void setPermissions(List<Long> assetIds, List<Long> userIds) throws ServiceException;
}
