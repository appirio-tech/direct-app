/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.asset.project;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetSearchCriteria;
import com.topcoder.asset.entities.CategorySearchCriteria;
import com.topcoder.asset.entities.PagedResult;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.asset.AssetContainerType;
import com.topcoder.direct.services.view.action.asset.BaseAbstractAssetAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.asset.project.ProjectAssetsViewDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * <p>
 * This action handles the view of project assets.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Asset View And File Version)
 * <ul>
 *     <li>Updates {@link #executeAction()} to prepare the data for the assets view filter panel</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.1 (Release Assembly - TopCoder Cockpit Asset View And File Version)
 */
public class ProjectAssetsAction extends BaseAbstractAssetAction implements FormAction<ProjectIdForm> {

    /**
     * The form data
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * The view data
     */
    private ProjectAssetsViewDTO viewData = new ProjectAssetsViewDTO();

    /**
     * Sets the form data.
     *
     * @param formData the form data.
     */
    public void setFormData(ProjectIdForm formData) {
        this.formData = formData;
    }

    /**
     * Gets the form data.
     *
     * @return the form data.
     */
    public ProjectIdForm getFormData() {
        return formData;
    }

    /**
     * Gets the view data.
     *
     * @return the view data.
     */
    public ProjectAssetsViewDTO getViewData() {
        return viewData;
    }

    /**
     * Sets the view data.
     *
     * @param viewData the view data.
     */
    public void setViewData(ProjectAssetsViewDTO viewData) {
        this.viewData = viewData;
    }


    /**
     * The action execution logic.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        // create the asset search criteria for current project and user
        AssetSearchCriteria searchCriteria = new AssetSearchCriteria();

        // set the container search criteria - container type and container id
        searchCriteria.setContainerType(AssetContainerType.PROJECT.toString());
        searchCriteria.setContainerId(getFormData().getProjectId());

        // set the user search criteria - use current user's handle
        searchCriteria.setUser(getUserService().getUserHandle(DirectUtils.getTCSubjectFromSession().getUserId()));
        PagedResult<Asset> assets = getAssetService().searchAssets(searchCriteria);

        // grouped assets result
        Map<Date, List<Asset>> dateGroupedAssets = new TreeMap<Date, List<Asset>>();
        Map<String, List<Asset>> categoryGroupedAssets = new TreeMap<String, List<Asset>>();

        // my grouped assets result
        Map<Date, List<Asset>> myDateGroupedAssets = new TreeMap<Date, List<Asset>>();
        Map<String, List<Asset>> myCategoryGroupedAssets = new TreeMap<String, List<Asset>>();

        // available categories and uploaders - used by filter panel
        Set<String> availableCategories = new TreeSet<String>();
        Set<String> availableUploaders = new TreeSet<String>();

        Set<String> myAvailableCategories = new TreeSet<String>();

        // current user id
        long currentUserId = DirectUtils.getTCSubjectFromSession().getUserId();

        // group the assets by category and date
        for(Asset a : assets.getRecords()) {

            if(a.getCurrentVersion() == null) {
                // ignore empty asset without any version
                continue;
            }

            String category = null;
            if(a.getCategories() != null && a.getCategories().size() > 0) {
                category  = a.getCategories().get(0).getName();
            }

            Date uploadDate = DirectUtils.trim(a.getCurrentVersion().getUploadTime());
            String uploader = a.getCurrentVersion().getUploader().getName();

            if (category != null) {
                availableCategories.add(category);
            }

            availableUploaders.add(uploader);

            if (!dateGroupedAssets.containsKey(uploadDate)) {
                List<Asset> assetList = new ArrayList<Asset>();
                assetList.add(a);
                dateGroupedAssets.put(uploadDate, assetList);
            } else {
                dateGroupedAssets.get(uploadDate).add(a);
            }

            if (!categoryGroupedAssets.containsKey(category)) {
                List<Asset> assetList = new ArrayList<Asset>();
                assetList.add(a);
                categoryGroupedAssets.put(category, assetList);
            } else {
                categoryGroupedAssets.get(category).add(a);
            }

            if(a.getCurrentVersion().getUploader().getId() == currentUserId) {
                myAvailableCategories.add(category);

                if (!myDateGroupedAssets.containsKey(uploadDate)) {
                    List<Asset> assetList = new ArrayList<Asset>();
                    assetList.add(a);
                    myDateGroupedAssets.put(uploadDate, assetList);
                } else {
                    myDateGroupedAssets.get(uploadDate).add(a);
                }

                if (!myCategoryGroupedAssets.containsKey(category)) {
                    List<Asset> assetList = new ArrayList<Asset>();
                    assetList.add(a);
                    myCategoryGroupedAssets.put(category, assetList);
                } else {
                    myCategoryGroupedAssets.get(category).add(a);
                }
            }
        }

        // get asset categories - used by the edit modal
        CategorySearchCriteria categorySearch = new CategorySearchCriteria();
        categorySearch.setContainerType(AssetContainerType.GLOBAL.toString());
        getViewData().setAssetCategories(getAssetCategoryService().search(categorySearch).getRecords());

        categorySearch.setContainerType(AssetContainerType.PROJECT.toString());
        categorySearch.setContainerId(getFormData().getProjectId());

        getViewData().getAssetCategories().addAll(getAssetCategoryService().search(categorySearch).getRecords());

        // set to view data
        getViewData().setDateGroupedAssets(dateGroupedAssets);
        getViewData().setCategoryGroupedAssets(categoryGroupedAssets);
        getViewData().setAvailableCategories(availableCategories);
        getViewData().setAvailableUploaders(availableUploaders);
        getViewData().setMyCategoryGroupedAssets(myCategoryGroupedAssets);
        getViewData().setMyDateGroupedAssets(myDateGroupedAssets);
        getViewData().setMyAvailableCategories(myAvailableCategories);
        getViewData().setClientManagers(getManagerService().getClientManagers(getFormData().getProjectId()));
        getViewData().setTopcoderManagers(getManagerService().getTopCoderManagers(getFormData().getProjectId()));
        getViewData().setProjectCopilots(getManagerService().getCopilots(getFormData().getProjectId()));

        // right sidebar data
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
        final ProjectContestsListDTO projectContests = DataProvider.getProjectContests(currentUser.getUserId(), getFormData().getProjectId());

        // populate the data needed for the right sidebar
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(DataProvider.getUserProjects(currentUser.getUserId()));
        viewData.setUserProjects(userProjectsDTO);

        ProjectBriefDTO currentDirectProject;

        if (projectContests.getContests().size() > 0) {
            currentDirectProject = projectContests.getContests().get(0).getContest().getProject();
        } else {
            currentDirectProject = DirectUtils.getCurrentProjectBrief(getProjectServiceFacade(), getFormData().getProjectId());
        }

        getSessionData().setCurrentProjectContext(currentDirectProject);
        getSessionData().setCurrentSelectDirectProjectID(currentDirectProject.getId());
    }
}
