/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.asset;

import com.topcoder.asset.services.AssetCategoryService;
import com.topcoder.asset.services.AssetPermissionService;
import com.topcoder.asset.services.AssetService;
import com.topcoder.asset.services.AssetVersionService;
import com.topcoder.asset.services.FileTypeIconService;
import com.topcoder.asset.services.ManagerService;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;

/**
 * <p>
 * The abstract action acts as the base of all the asset related actions. It provides the access to all the asset
 * services in the action.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload version 1.0)
 */
public abstract class BaseAbstractAssetAction extends BaseDirectStrutsAction {

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
     * The asset permission service.
     */
    private AssetPermissionService assetPermissionService;

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
     * Gets the asset permission service.
     *
     * @return the asset permission service.
     */
    public AssetPermissionService getAssetPermissionService() {
        return assetPermissionService;
    }

    /**
     * Sets the asset permission service.
     *
     * @param assetPermissionService the asset permission service.
     */
    public void setAssetPermissionService(AssetPermissionService assetPermissionService) {
        this.assetPermissionService = assetPermissionService;
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
}
