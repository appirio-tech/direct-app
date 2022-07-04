/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.asset.project;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * The DTO for the save asset request.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Asset View Release 3)
 */
public class SaveAssetDTO implements Serializable {
    /**
     * The asset id to save.
     */
    private long assetId;

    /**
     * The asset version id to save
     */
    private long assetVersionId;

    /**
     * The asset category id.
     */
    private long assetCategoryId;

    /**
     * The asset description.
     */
    private String assetDescription;

    /**
     * Flag for whether the asset is public.
     */
    private boolean isAssetPublic;

    /**
     * The user ids which are given the private permission on the asset.
     */
    private List<Long> privateUserIds;

    /**
     * The session key for the uploaded asset information.
     */
    private String sessionKey;

    /**
     * Gets the asset category id.
     *
     * @return the asset category id.
     */
    public long getAssetCategoryId() {
        return assetCategoryId;
    }

    /**
     * Sets the asset category id.
     *
     * @param assetCategoryId the asset category id.
     */
    public void setAssetCategoryId(long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    /**
     * Gets the asset description.
     *
     * @return the asset description.
     */
    public String getAssetDescription() {
        return assetDescription;
    }

    /**
     * Sets the asset description.
     *
     * @param assetDescription the asset description.
     */
    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    /**
     * Gets the asset public flag.
     *
     * @return the asset public flag.
     */
    public boolean isAssetPublic() {
        return isAssetPublic;
    }

    /**
     * Sets the asset public flag.
     *
     * @param assetPublic the asset public flag.
     */
    public void setAssetPublic(boolean assetPublic) {
        isAssetPublic = assetPublic;
    }

    /**
     * Gets the private user ids.
     *
     * @return the private user ids.
     */
    public List<Long> getPrivateUserIds() {
        return privateUserIds;
    }

    /**
     * Sets the private user ids.
     *
     * @param privateUserIds the private user ids.
     */
    public void setPrivateUserIds(List<Long> privateUserIds) {
        this.privateUserIds = privateUserIds;
    }

    /**
     * Gets the session key.
     *
     * @return the session key.
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * Sets the session key.
     *
     * @param sessionKey the session key.
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * Gets the asset id to save.
     *
     * @return the asset id to save.
     */
    public long getAssetId() {
        return assetId;
    }

    /**
     * Sets the asset id to save.
     *
     * @param assetId the asset id to save.
     */
    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    /**
     * Gets the asset version id to save.
     *
     * @return the asset version id to save.
     */
    public long getAssetVersionId() {
        return assetVersionId;
    }

    /**
     * Sets the asset version id to save.
     *
     * @param assetVersionId the asset version id to save.
     */
    public void setAssetVersionId(long assetVersionId) {
        this.assetVersionId = assetVersionId;
    }
}
