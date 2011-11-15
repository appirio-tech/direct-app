/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project.metadata;

import com.topcoder.direct.services.project.metadata.DirectProjectMetadataKeyService;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataService;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyNameValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataValueOperator;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchResultsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     This action contains all the operations related to the project metadata, project metadata key management.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Cockpit Project Metadata Service Setup and Integration)
 */
public class ProjectMetadataAction extends BaseDirectStrutsAction {

    /**
     * The metadata key service.
     */
    private DirectProjectMetadataKeyService metadataKeyService;

    /**
     * The project metadata service.
     */
    private DirectProjectMetadataService metadataService;

    /**
     * The view data which stores all the projects information.
     */
    private DashboardSearchResultsDTO viewData;

    /**
     * The direct project id.
     */
    private long directProjectId;

    /**
     * The project metadata key id.
     */
    private long projectMetadataKeyId;

    /**
     * The project metadata key value.
     */
    private String projectMetadataValue;

    /**
     * The project metadata id
     */
    private long projectMetadataId;

    /**
     * Store all the predefined keys.
     */
    private List<DirectProjectMetadataKey> predefinedKeys;

    /**
     * Stores all the search methods for project metadata.
     */
    private List<String> searchMethods;

    /**
     * The metadata key name.
     */
    private String metadataKeyName;

    /**
     * The search value for project metadata search.
     */
    private String searchValue;

    /**
     * The search method.
     */
    private String searchMethod;

    /**
     * The description of hte metadata key.
     */
    private String metaDataKeyDescription;

    /**
     * Whether the key is single.
     */
    private String keySingle;

    /**
     * Gets the predefined keys.
     *
     * @return the predefined keys.
     */
    public List<DirectProjectMetadataKey> getPredefinedKeys() {
        return predefinedKeys;
    }

    /**
     * Sets the predefined keys.
     *
     * @param predefinedKeys the predefined keys.
     */
    public void setPredefinedKeys(List<DirectProjectMetadataKey> predefinedKeys) {
        this.predefinedKeys = predefinedKeys;
    }

    /**
     * Gets the dashboard search view data.
     *
     * @return the dashboard search view data.
     */
    public DashboardSearchResultsDTO getViewData() {
        return viewData;
    }

    /**
     * Sets the dashboard search view data.
     *
     * @param viewData the dashboard search view data.
     */
    public void setViewData(DashboardSearchResultsDTO viewData) {
        this.viewData = viewData;
    }

    /**
     * Gets the project metadata key service.
     *
     * @return the project metadata key service.
     */
    public DirectProjectMetadataKeyService getMetadataKeyService() {
        return metadataKeyService;
    }

    /**
     * Sets the project metadata key service.
     *
     * @param metadataKeyService
     */
    public void setMetadataKeyService(DirectProjectMetadataKeyService metadataKeyService) {
        this.metadataKeyService = metadataKeyService;
    }

    /**
     * Gets the project metadata service.
     *
     * @return  the project metadata service.
     */
    public DirectProjectMetadataService getMetadataService() {
        return metadataService;
    }

    /**
     * Sets the project metadata service.
     *
     * @param metadataService the project metadata service.
     */
    public void setMetadataService(DirectProjectMetadataService metadataService) {
        this.metadataService = metadataService;
    }

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     */
    public long getDirectProjectId() {
        return directProjectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param directProjectId the direct project id.
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * Gets the project metadata key id
     *
     * @return  the project metadata key id
     */
    public long getProjectMetadataKeyId() {
        return projectMetadataKeyId;
    }

    /**
     * Sets the project metadata key id
     *
     * @param projectMetadataKeyId the project metadata key id
     */
    public void setProjectMetadataKeyId(long projectMetadataKeyId) {
        this.projectMetadataKeyId = projectMetadataKeyId;
    }

    /**
     * Gets the project metadata value.
     *
     * @return the project metadata value.
     */
    public String getProjectMetadataValue() {
        return projectMetadataValue;
    }

    /**
     * Sets the project metadata value.
     *
     * @param projectMetadataValue the project metadata value.
     */
    public void setProjectMetadataValue(String projectMetadataValue) {
        this.projectMetadataValue = projectMetadataValue;
    }

    /**
     * Gets the project metadata id.
     *
     * @return the project metadata id.
     */
    public long getProjectMetadataId() {
        return projectMetadataId;
    }

    /**
     * Sets the project metadata id.
     *
     * @param projectMetadataId the project metadata id.
     */
    public void setProjectMetadataId(long projectMetadataId) {
        this.projectMetadataId = projectMetadataId;
    }

    /**
     * Gets the search methods.
     *
     * @return  the search methods.
     */
    public List<String> getSearchMethods() {
        return searchMethods;
    }

    /**
     * Sets the search methods.
     *
     * @param searchMethods the search methods.
     */
    public void setSearchMethods(List<String> searchMethods) {
        this.searchMethods = searchMethods;
    }

    /**
     * Gets the metadata key name.
     *
     * @return the metadata key name.
     */
    public String getMetadataKeyName() {
        return metadataKeyName;
    }

    /**
     * Sets the metadata key name.
     *
     * @param metadataKeyName the metadata key name.
     */
    public void setMetadataKeyName(String metadataKeyName) {
        this.metadataKeyName = metadataKeyName;
    }

    /**
     * Gets the search value.
     *
     * @return the search value.
     */
    public String getSearchValue() {
        return searchValue;
    }

    /**
     * Sets the search value.
     *
     * @param searchValue the search value.
     */
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    /**
     * Gets the search method.
     *
     * @return the search method.
     */
    public String getSearchMethod() {
        return searchMethod;
    }

    /**
     * Sets the search method.
     *
     * @param searchMethod the search method.
     */
    public void setSearchMethod(String searchMethod) {
        this.searchMethod = searchMethod;
    }

    /**
     * Gets the metadata key description.
     *
     * @return the metadata key description.
     */
    public String getMetaDataKeyDescription() {
        return metaDataKeyDescription;
    }

    /**
     * Sets the metadata key description.
     *
     * @param metaDataKeyDescription the metadata key description.
     */
    public void setMetaDataKeyDescription(String metaDataKeyDescription) {
        this.metaDataKeyDescription = metaDataKeyDescription;
    }

    /**
     * Gets whether the metadata key is single
     *
     * @return  whether the metadata key is single
     */
    public String getKeySingle() {
        return keySingle;
    }

    /**
     *  Sets whether the metadata key is single
     *
     * @param keySingle whether the metadata key is single
     */
    public void setKeySingle(String keySingle) {
        this.keySingle = keySingle;
    }

    /**
     * The main body of execution action, it prepares data for the project-metadata-demo.jsp.
     *
     * @throws Exception when error
     */
    @Override
    protected void executeAction() throws Exception {
        HttpServletRequest request = DirectUtils.getServletRequest();
        SessionData sessionData = new SessionData(request.getSession());
        TCSubject currentUser = getCurrentUser();
        setViewData(new DashboardSearchResultsDTO());

        // Set projects data
        List<ProjectBriefDTO> projects = DataProvider.getUserProjects(currentUser.getUserId());
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        getViewData().setUserProjects(userProjectsDTO);

        // Set current project contests
        ProjectBriefDTO currentProject = sessionData.getCurrentProjectContext();
        if (currentProject != null) {
            List<TypedContestBriefDTO> contests
                    = DataProvider.getProjectTypedContests(currentUser.getUserId(), currentProject.getId());
            sessionData.setCurrentProjectContests(contests);
        }

        viewData.setProjects(DataProvider.searchUserProjects(currentUser, ""));

        setPredefinedKeys(getMetadataKeyService().getCommonProjectMetadataKeys());

        List<String> searches = new ArrayList<String>();
        searches.add("Like");
        searches.add("Equals to");

        setSearchMethods(searches);
    }

    /**
     * Adds the project metadata via ajax.
     *
     * @return the result string
     * @throws Exception when error.
     */
    public String addProjectMetaData() throws Exception {
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        // Map to store the ajax result
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            DirectProjectMetadata toAdd = new DirectProjectMetadata();

            DirectProjectMetadataKey key = getMetadataKeyService().getProjectMetadataKey(getProjectMetadataKeyId());

            toAdd.setMetadataValue(getProjectMetadataValue());
            toAdd.setProjectMetadataKey(key);
            toAdd.setTcDirectProjectId(getDirectProjectId());

            long metadataID = getMetadataService().createProjectMetadata(toAdd, currentUser.getUserId());

            result.put("id", metadataID);
            result.put("key", key.getName());
            result.put("value", getProjectMetadataValue());
            result.put("directProjectId", getDirectProjectId());

            setResult(result);

        } catch (Throwable e) {

            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }
        }

        return SUCCESS;
    }

    /**
     * Gets all the project metadata via ajax.
     *
     * @return the result string
     * @throws Exception when error.
     */
    public String getAllProjectMetadata() throws Exception {

        // Map to store the ajax result
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        try {


            List<DirectProjectMetadata> data = getMetadataService().getProjectMetadataByProject(getDirectProjectId());

            for (DirectProjectMetadata m : data) {

                Map<String, Object> metaData = new HashMap<String, Object>();

                metaData.put("value", m.getMetadataValue());
                metaData.put("keyId", m.getProjectMetadataKey().getId());
                metaData.put("keyName", m.getProjectMetadataKey().getName());
                metaData.put("projectId", m.getTcDirectProjectId());
                metaData.put("id", m.getId());

                result.add(metaData);
            }

            setResult(result);

        } catch (Throwable e) {

            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }
        }

        return SUCCESS;
    }

    /**
     * Deletes the project metadata via ajax.
     *
     * @return the result string
     * @throws Exception when error.
     */
    public String deleteProjectMetadata() throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            getMetadataService().deleteProjectMetadata(getProjectMetadataId(), currentUser.getUserId());

            result.put("metadataId", getProjectMetadataId());
            result.put("userId", currentUser.getUserId());

            setResult(result);

        } catch (Throwable e) {

            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }
        }

        return SUCCESS;
    }

    /**
     * Updates the project metadata via ajax.
     *
     * @return the result string
     * @throws Exception when error.
     */
    public String updateProjectMetadata() {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            DirectProjectMetadata updateData = new DirectProjectMetadata();

            updateData.setId(getProjectMetadataId());
            updateData.setMetadataValue(getProjectMetadataValue());

            long keyId = getProjectMetadataKeyId();

            updateData.setProjectMetadataKey(getMetadataKeyService().getProjectMetadataKey(keyId));
            updateData.setTcDirectProjectId(getDirectProjectId());

            getMetadataService().updateProjectMetadata(updateData, currentUser.getUserId());

            result.put("metadataId", getProjectMetadataId());
            result.put("updatedValue", getProjectMetadataValue());
            result.put("userId", currentUser.getUserId());

            setResult(result);

        } catch (Throwable e) {

            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }
        }

        return SUCCESS;
    }

    /**
     * Searches the direct projects via metadata via ajax.
     *
     * @return the result string
     * @throws Exception when error.
     */
    public String searchProjectByMetadata() throws Exception {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        try {
            MetadataKeyNameValueFilter filter = new MetadataKeyNameValueFilter();

            filter.setProjectMetadataKeyName(getMetadataKeyName());
            filter.setMetadataValue(getSearchValue());
            filter.setMetadataValueOperator(getSearchMethod().equals("Like") ? MetadataValueOperator.LIKE
                    : MetadataValueOperator.EQUALS);

            List<TcDirectProject> tcDirectProjects = getMetadataService().searchProjects(filter);

            for (TcDirectProject project : tcDirectProjects) {
                Map<String, Object> projectResult = new HashMap<String, Object>();
                projectResult.put("projectId", project.getProjectId());
                projectResult.put("projectStatusId", project.getProjectStatusId());
                projectResult.put("projectName", project.getName());
                projectResult.put("projectDescription", project.getDescription());

                result.add(projectResult);
            }

            setResult(result);

        } catch (Throwable e) {

            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }
        }

        return SUCCESS;
    }

    /**
     * Adds the project metadata key via ajax.
     *
     * @return the result string
     * @throws Exception when error.
     */
    public String addProjectMetadataKey() throws Exception {
      Map<String, Object> result = new HashMap<String, Object>();

        try {
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            DirectProjectMetadataKey key = new DirectProjectMetadataKey();

            key.setName(getMetadataKeyName());
            key.setDescription(getMetaDataKeyDescription());
            key.setGrouping(null);
            key.setClientId(null);
            key.setSingle(getKeySingle().equals("single"));

            long keyId = getMetadataKeyService().createProjectMetadataKey(key, currentUser.getUserId());

            result.put("newKeyId", keyId);

            setResult(result);

        } catch (Throwable e) {

            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }
        }

        return SUCCESS;
    }

}
