/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;

/**
 * <p>
 * This interface represents an asset version service. It defines CURD methods for asset version. It also define
 * methods to batch update asset versions and batch retrieve asset version contents.
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
public interface AssetVersionService {
    /**
     * This method will create the asset version. If the assetVersion.fileType is one of configured previewFileTypes,
     * then generate preview image for the file content, and it should be set back to the given entity.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetVersion
     *            the asset version to create
     * @param file
     *            the asset version file to be added, if it is null, then the service won't handle file persistence,
     *            it is assumed that file persistence is handled by caller. If it is not null, then the file content
     *            will be copied to configured location. Assigned location should be set back to the given entity.
     * @param currentVersion
     *            the flag specified whether the new asset version is the current version of the asset.
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, assetVersion is null, or assetVersion.assetId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void createAssetVersion(long userId, AssetVersion assetVersion, File file, boolean currentVersion)
        throws ServiceException;

    /**
     * This method will update the asset version. If file path is updated, the service will move the file to new
     * location before updating the entity.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetVersion
     *            the asset version to update.
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, assetVersion is null, assetVersion.id or assetVersion.assetId is not
     *             positive, or assetVersion.filePath is null/empty.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void updateAssetVersion(long userId, AssetVersion assetVersion) throws ServiceException;

    /**
     * This method will delete the asset version. The related files will also be deleted. If it is current version of
     * the asset, then the current version of asset should be set to null.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetVersionId
     *            the id of the asset version to delete.
     *
     * @throws IllegalArgumentException
     *             if userId or assetVersionId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void deleteAssetVersion(long userId, long assetVersionId) throws ServiceException;

    /**
     * This method will retrieve the asset version with the given id.
     *
     * @param assetVersionId
     *            the id of the asset version.
     *
     * @return the asset version with the given id.
     *
     * @throws IllegalArgumentException
     *             if assetVersionId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public AssetVersion getAssetVersion(long assetVersionId) throws ServiceException;

    /**
     * This method will retrieve the asset versions of a specific asset.
     *
     * @param assetId
     *            the id of the asset.
     *
     * @return the asset versions of a specific asset.
     *
     * @throws IllegalArgumentException
     *             if assetId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public List<AssetVersion> getAssetVersionsOfAsset(long assetId) throws ServiceException;

    /**
     * This method will batch update the asset versions.
     *
     * @param userId
     *            the id of the user who performs the action
     * @param assetVersions
     *            the asset versions to update.
     *
     * @throws IllegalArgumentException
     *             if userId is not positive, assetVersions is null, or any assetVersion is null, or any
     *             assetVersion.id/assetVersion.assetId is not positive, or any assetVersion.filePath is null/empty.
     *             (assetVersion is the element in assetVersions)
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void updateAssetVersions(long userId, List<AssetVersion> assetVersions) throws ServiceException;

    /**
     * This method will batch retrieve asset version contents. The asset version files contents are archived as a ZIP
     * file and written to the output stream.
     *
     * @param assetVersionIds
     *            the asset version ids
     * @param output
     *            the output stream of asset version contents
     *
     * @throws IllegalArgumentException
     *             if assetVersionIds is null, or any element in assetVersionIds is null or not positive, or output is
     *             null
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public void batchGetAssetVersionContents(List<Long> assetVersionIds, OutputStream output) throws ServiceException;
}
