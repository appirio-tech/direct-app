/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.asset;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.services.AssetCategoryService;
import com.topcoder.asset.services.AssetService;
import com.topcoder.asset.services.AssetVersionService;
import com.topcoder.asset.services.FileTypeIconService;
import com.topcoder.asset.services.ManagerService;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * The abstract action acts as the base of all the asset related actions. It provides the access to all the asset
 * services in the action.
 * </p>
 *
 * <p>
 *  Version 1.1 (Release Assembly - TopCoder Cockpit Asset View Release 4 - Resource restriction update)
 *  <ul>
 *      <li>Added method {@link #checkIfAssetDownloadAllowed(com.topcoder.asset.entities.Asset, com.topcoder.security.TCSubject)}</li>
 *      <li>Added method {@link #checkIfAssetAccessAllowed(com.topcoder.asset.entities.Asset, com.topcoder.security.TCSubject, boolean)}</li>
 *  </ul>
 * </p>
 *
 * @author GreatKevin, TCSASSEMBLER
 * @version 1.1
 */
public abstract class BaseAbstractAssetAction extends BaseDirectStrutsAction {

    /**
     * The error message to display if user does not have write permission to the specified asset file/version
     *
     * @since 1.1
     */
    private static final String ASSET_ACCESS_WRITE_PERMISSION_ERROR_MSG = "You don't have permission to make changes to the file(s)";

    /**
     * The error message to display if user does not have download permission to the specified asset file/version
     *
     * @since 1.1
     */
    private static final String ASSET_ACCESS_DOWNLOAD_PERMISSION_ERROR_MSG = "You don't have permission to download the file(s)";

    /**
     * The action service.
     */
    private AssetService assetService;

    /**
     * The asset version service.
     */
    private AssetVersionService assetVersionService;

    /**
     * The asset category service.
     */
    private AssetCategoryService assetCategoryService;

    /**
     * The file type icon service.
     */
    private FileTypeIconService fileTypeIconService;

    /**
     * The asset manager service.
     */
    private ManagerService managerService;

    /**
     * Gets the asset service.
     *
     * @return the asset service.
     */
    public AssetService getAssetService() {
        return assetService;
    }

    /**
     * Sets the asset service.
     *
     * @param assetService the asset service.
     */
    public void setAssetService(AssetService assetService) {
        this.assetService = assetService;
    }

    /**
     * Gets the asset version service.
     *
     * @return the asset version service.
     */
    public AssetVersionService getAssetVersionService() {
        return assetVersionService;
    }

    /**
     * Sets the asset version service.
     *
     * @param assetVersionService the asset version service.
     */
    public void setAssetVersionService(AssetVersionService assetVersionService) {
        this.assetVersionService = assetVersionService;
    }

    /**
     * Gets the asset category service.
     *
     * @return the asset category service.
     */
    public AssetCategoryService getAssetCategoryService() {
        return assetCategoryService;
    }

    /**
     * Sets the asset category service.
     *
     * @param assetCategoryService the asset category service.
     */
    public void setAssetCategoryService(AssetCategoryService assetCategoryService) {
        this.assetCategoryService = assetCategoryService;
    }

    /**
     * Gets the file type icon service.
     *
     * @return the file type icon service.
     */
    public FileTypeIconService getFileTypeIconService() {
        return fileTypeIconService;
    }

    /**
     * Sets the file type icon service.
     *
     * @param fileTypeIconService the file type icon service.
     */
    public void setFileTypeIconService(FileTypeIconService fileTypeIconService) {
        this.fileTypeIconService = fileTypeIconService;
    }

    /**
     * Gets the manager service.
     *
     * @return the manager service.
     */
    public ManagerService getManagerService() {
        return managerService;
    }

    /**
     * Sets the manager service.
     *
     * @param managerService the manager service.
     */
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    /**
     * Checks if the specified user has permission to download the specified asset
     *
     * @param asset the asset instance
     * @param user the TCSubject instance representing the user
     * @throws Exception if any error
     * @since 1.1
     */
    protected void checkIfAssetDownloadAllowed(Asset asset, TCSubject user) throws Exception {
        if (!asset.isPublic()) {
            // asset is not public, then it's 'project' permission, we check if
            // current user has access to the project the asset is in
            boolean accessAllowed = AuthorizationProvider.isUserGrantedAccessToProject(user, asset.getContainerId());
            if (!accessAllowed) {
                // user does not have permission on the project
                DirectUtils.setErrorMessageInErrorPage(ASSET_ACCESS_DOWNLOAD_PERMISSION_ERROR_MSG);
                throw new IllegalArgumentException(ASSET_ACCESS_DOWNLOAD_PERMISSION_ERROR_MSG);
            }
        }
    }

    /**
     * Checks if the specific user has access permission to the specified asset. The needsWrite argument
     * indicates it's a read access or write access.
     *
     * @param asset the asset instance
     * @param user the TCSubject instance representing the user
     * @param needsWrite true if the access is write, false if the access is read only
     * @throws Exception if there is any error
     * @since 1.1
     */
    protected void checkIfAssetAccessAllowed(Asset asset, TCSubject user, boolean needsWrite) throws Exception {
            boolean accessAllowed = needsWrite ? AuthorizationProvider.isUserGrantedWriteAccessToProject(user, asset.getContainerId())
                    : AuthorizationProvider.isUserGrantedAccessToProject(user, asset.getContainerId());
            if (!accessAllowed) {
                // user does not have permission on the project
                DirectUtils.setErrorMessageInErrorPage(ASSET_ACCESS_WRITE_PERMISSION_ERROR_MSG);
                throw new IllegalArgumentException(ASSET_ACCESS_WRITE_PERMISSION_ERROR_MSG);
            }
        }
}
