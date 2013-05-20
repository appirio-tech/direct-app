/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.asset.project;

import com.topcoder.direct.services.view.form.ProjectIdForm;

/**
 * <p>
 * The form data for action <code>ProjectAssetManageAction</code>
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload version 1.0)
 */
public class ProjectAssetManageFormData extends ProjectIdForm {
    /**
     * The asset id.
     */
    private long assetId;

    /**
     * The asset version id.
     */
    private long assetVersionId;

    /**
     * The asset file name.
     */
    private String assetFileName;

    /**
     * Gets the asset id.
     *
     * @return the asset id.
     */
    public long getAssetId() {
        return assetId;
    }

    /**
     * Sets the asset id.
     *
     * @param assetId the asset id.
     */
    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    /**
     * Gets the asset version id.
     *
     * @return the asset version id.
     */
    public long getAssetVersionId() {
        return assetVersionId;
    }

    /**
     * Sets the asset version id.
     *
     * @param assetVersionId the asset version id.
     */
    public void setAssetVersionId(long assetVersionId) {
        this.assetVersionId = assetVersionId;
    }

    /**
     * Gets the asset file name.
     *
     * @return the asset file name.
     */
    public String getAssetFileName() {
        return assetFileName;
    }

    /**
     * Sets the asset file name.
     *
     * @param assetFileName the asset file name.
     */
    public void setAssetFileName(String assetFileName) {
        this.assetFileName = assetFileName;
    }
}
