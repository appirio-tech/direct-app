/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.asset.project;

import com.topcoder.asset.entities.Asset;
import com.topcoder.direct.services.view.dto.CommonDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * The view data for the <code>ProjectAssetsAction</code>
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload version 1.0)
 */
public class ProjectAssetsViewDTO extends CommonDTO {
    /**
     * The assets grouped by date.
     */
    private Map<Date, List<Asset>> dateGroupedAssets;

    /**
     * The assets grouped by asset category.
     */
    private Map<String, List<Asset>> categoryGroupedAssets;

    /**
     * Gets the assets grouped by date.
     *
     * @return the assets grouped by date.
     */
    public Map<Date, List<Asset>> getDateGroupedAssets() {
        return dateGroupedAssets;
    }

    /**
     * Sets the assets grouped by date.
     *
     * @param dateGroupedAssets the assets grouped by date.
     */
    public void setDateGroupedAssets(Map<Date, List<Asset>> dateGroupedAssets) {
        this.dateGroupedAssets = dateGroupedAssets;
    }

    /**
     * Gets the assets grouped by category.
     *
     * @return the assets grouped by category.
     */
    public Map<String, List<Asset>> getCategoryGroupedAssets() {
        return categoryGroupedAssets;
    }

    /**
     * Sets the assets grouped by category.
     *
     * @param categoryGroupedAssets the assets grouped by category.
     */
    public void setCategoryGroupedAssets(Map<String, List<Asset>> categoryGroupedAssets) {
        this.categoryGroupedAssets = categoryGroupedAssets;
    }
}
