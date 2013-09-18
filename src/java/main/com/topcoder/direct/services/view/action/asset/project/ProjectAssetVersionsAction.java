/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.asset.project;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.CategorySearchCriteria;
import com.topcoder.direct.services.view.action.asset.AssetContainerType;
import com.topcoder.direct.services.view.action.asset.BaseAbstractAssetAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.asset.project.ProjectAssetVersionsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * This action handles the operation of viewing all the versions of an asset.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Asset View Release 4 - Resource restriction update)
 * <ul>
 *     <li>Updated {@link #executeAction()} to check if user has read+ permission to access the asset</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin, TCSASSEMBLER
 * @version 1.1
 */
public class ProjectAssetVersionsAction extends BaseAbstractAssetAction {

    /**
     * The id of the asset to view.
     */
    private long assetId;

    /**
     * The view data of the project asset versions page.
     */
    private ProjectAssetVersionsDTO viewData = new ProjectAssetVersionsDTO();

    /**
     * Gets the view data.
     *
     * @return the view data.
     */
    public ProjectAssetVersionsDTO getViewData() {
        return viewData;
    }

    /**
     * Sets the view data.
     *
     * @param viewData the view data.
     */
    public void setViewData(ProjectAssetVersionsDTO viewData) {
        this.viewData = viewData;
    }

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
     * The action execution logic
     *
     * @throws Exception if any error
     */
    @Override
    protected void executeAction() throws Exception {
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        // check if asset id is valid and user has permission to the asset
        if(getAssetId() <= 0) {
            throw new IllegalArgumentException("The asset file ID is incorrect.");
        }

        Asset asset = getAssetService().getAsset(getAssetId());

        checkIfAssetAccessAllowed(asset, currentUser, false);

        // get and set the asset
        viewData.setAsset(asset);

        // get and set the asset versions
        viewData.setAssetVersions(getAssetVersionService().getAssetVersionsOfAsset(viewData.getAsset().getId()));

        // get asset categories - used by the edit modal
        CategorySearchCriteria categorySearch = new CategorySearchCriteria();
        categorySearch.setContainerType(AssetContainerType.GLOBAL.toString());
        getViewData().setAssetCategories(getAssetCategoryService().search(categorySearch).getRecords());

        getViewData().setClientManagers(getManagerService().getClientManagers(viewData.getAsset().getContainerId()));
        getViewData().setTopcoderManagers(getManagerService().getTopCoderManagers(viewData.getAsset().getContainerId()));
        getViewData().setProjectCopilots(getManagerService().getCopilots(viewData.getAsset().getContainerId()));

        // prepare right sidebar data
        final ProjectContestsListDTO projectContests = DataProvider.getProjectContests(currentUser.getUserId(), viewData.getAsset().getContainerId());

        // populate the data needed for the right sidebar
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(DataProvider.getUserProjects(currentUser.getUserId()));
        viewData.setUserProjects(userProjectsDTO);

        ProjectBriefDTO currentDirectProject;

        if (projectContests.getContests().size() > 0) {
            currentDirectProject = projectContests.getContests().get(0).getContest().getProject();
        } else {
            currentDirectProject = DirectUtils.getCurrentProjectBrief(getProjectServiceFacade(), viewData.getAsset().getContainerId());
        }

        getSessionData().setCurrentProjectContext(currentDirectProject);
        getSessionData().setCurrentSelectDirectProjectID(currentDirectProject.getId());

    }
}
