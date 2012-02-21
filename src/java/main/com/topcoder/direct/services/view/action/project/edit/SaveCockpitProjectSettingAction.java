/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project.edit;


import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.project.edit.ProjectMetadataOperation;
import com.topcoder.direct.services.view.form.SaveProjectSettingsForm;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;

import java.util.*;

/**
 * <p>
 * The action handles the operation of saving project settings and all the ajax operations take in Edit Project
 * Settings page.
 * </p>
 * 
 * <p>
 *     Version 1.1 (Release Assembly - TC Cockpit Edit Project and Project General Info Update) change notes:
 *     - Update method ${@link #executeAction()} to add ProjectMetadataOperation for new added project ratings.
 * </p>
 *
 * @author GreatKevin
 * @version 1.1
 */
public class SaveCockpitProjectSettingAction extends BaseDirectStrutsAction implements FormAction<SaveProjectSettingsForm> {

    /**
     * The form of saving project settings.
     */
    private SaveProjectSettingsForm formData = new SaveProjectSettingsForm();

    /**
     * Gets form data.
     *
     * @return the form data.
     */
    public SaveProjectSettingsForm getFormData() {
        return formData;
    }

    /**
     * Sets the form data.
     *
     * @param formData the form data to set.
     */
    public void setFormData(SaveProjectSettingsForm formData) {
        this.formData = formData;
    }

    /**
     * Main logic of action execution.
     *
     * @throws Exception if any error.
     */
    @Override
    protected void executeAction() throws Exception {

        Map<String, String> result = new HashMap<String, String>();
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        List<ProjectMetadataOperation> metadataOperations = new ArrayList<ProjectMetadataOperation>();
        metadataOperations.add(getFormData().getBudget());
        metadataOperations.add(getFormData().getDuration());
        metadataOperations.add(getFormData().getSvn());
        metadataOperations.add(getFormData().getJira());
        metadataOperations.add(getFormData().getPrivacy());

        if (getFormData().getProjectRatings() != null && getFormData().getProjectRatings().size() > 0) {
            for (ProjectMetadataOperation op : getFormData().getProjectRatings()) {
                metadataOperations.add(op);
            }
        }

        if (getFormData().getCustomMetadataValues() != null && getFormData().getCustomMetadataValues().size() > 0) {
            for (ProjectMetadataOperation op : getFormData().getCustomMetadataValues()) {
                metadataOperations.add(op);
            }
        }

        persistAllProjectMetadata(metadataOperations, currentUser.getUserId());

        ProjectData project = getProjectServiceFacade().getProject(currentUser, getFormData().getProjectId());

        project.setName(formData.getProjectName());
        project.setDescription(formData.getProjectDescription());
        project.setProjectStatusId(formData.getProjectStatusId());


        getProjectServiceFacade().updateProject(currentUser, project);

        result.put("save project setting", "success");

        setResult(result);
    }

    /**
     * Saves all the project metadata in one batch.
     *
     * @param operations the project metadata operations could be update / add / remove.
     * @param userId     the id of the user.
     * @throws Exception if any error.
     */
    private void persistAllProjectMetadata(List<ProjectMetadataOperation> operations, long userId) throws Exception {

        List<DirectProjectMetadata> updateOrCreate = new ArrayList<DirectProjectMetadata>();
        List<Long> remove = new ArrayList<Long>();

        for (ProjectMetadataOperation op : operations) {

            if (op == null) continue;

            String action = op.getOperation().toLowerCase();
            DirectProjectMetadata metadata = new DirectProjectMetadata();
            if (action.equals(ProjectMetadataOperation.REMOVE)) {
                remove.add(op.getId());
                continue;
            } else if (action.equals(ProjectMetadataOperation.UPDATE)) {
                metadata.setId(op.getId());
            }

            metadata.setMetadataValue(op.getValue());
            metadata.setTcDirectProjectId(getFormData().getProjectId());
            DirectProjectMetadataKey key = getMetadataKeyService().getProjectMetadataKey(op.getKeyId());
            metadata.setProjectMetadataKey(key);
            updateOrCreate.add(metadata);
        }

        // delete first
        for (Long idToDelete : remove) {
            getMetadataService().deleteProjectMetadata(idToDelete, userId);
        }

        // add and update
        getMetadataService().saveProjectMetadata(updateOrCreate, userId);

    }

    /**
     * Handles the save client project managers ajax request.
     *
     * @return result code.
     */
    public String saveClientProjectManagers() {
        try {
            Map<String, Map<String, String>> result = new LinkedHashMap<String, Map<String, String>>();
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            persistAllProjectMetadata(getFormData().getClientManagers(), currentUser.getUserId());

            List<DirectProjectMetadata> values = getMetadataService().getProjectMetadataByProjectAndKey(getFormData().getProjectId(), 1L);

            for (DirectProjectMetadata value : values) {
                Map<String, String> user = new HashMap<String, String>();
                user.put("userId", value.getMetadataValue());
                user.put("handle", getUserService().getUserHandle(Long.parseLong(value.getMetadataValue())));
                result.put(String.valueOf(value.getId()), user);
            }

            setResult(result);
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the save TopCoder project managers ajax request.
     *
     * @return result code.
     */
    public String saveTopCoderManagers() {
        try {
            Map<String, Map<String, String>> result = new LinkedHashMap<String, Map<String, String>>();
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            persistAllProjectMetadata(getFormData().getProjectManagers(), currentUser.getUserId());

            List<DirectProjectMetadata> values = getMetadataService().getProjectMetadataByProjectAndKey(getFormData().getProjectId(), 2L);

            for (DirectProjectMetadata value : values) {
                Map<String, String> user = new HashMap<String, String>();
                user.put("userId", value.getMetadataValue());
                user.put("handle", getUserService().getUserHandle(Long.parseLong(value.getMetadataValue())));
                result.put(String.valueOf(value.getId()), user);
            }
            setResult(result);
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the add new custom metadata key ajax request.
     *
     * @return result code.
     */
    public String addNewCustomMetadataKey() {
        try {
            Map<String, String> result = new HashMap<String, String>();
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            long id = getMetadataKeyService().createProjectMetadataKey(getFormData().getNewCustomKey(), currentUser.getUserId());

            result.put("id", String.valueOf(id));
            result.put("single", String.valueOf(getFormData().getNewCustomKey().isSingle()));
            result.put("name", String.valueOf(getFormData().getNewCustomKey().getName()));

            setResult(result);

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }
}
