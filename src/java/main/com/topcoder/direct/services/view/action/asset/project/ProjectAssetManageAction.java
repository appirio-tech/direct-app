/*
 * Copyright (C) 2013 - 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.asset.project;

import com.opensymphony.xwork2.interceptor.ValidationAware;
import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.entities.Category;
import com.topcoder.asset.entities.CategorySearchCriteria;
import com.topcoder.asset.entities.PagedResult;
import com.topcoder.asset.entities.User;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.asset.AssetContainerType;
import com.topcoder.direct.services.view.action.asset.BaseAbstractAssetAction;
import com.topcoder.direct.services.view.dto.asset.project.AssetFileTypes;
import com.topcoder.direct.services.view.dto.asset.project.ProjectAssetsViewDTO;
import com.topcoder.direct.services.view.dto.asset.project.SaveAssetDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.JSPHelper;
import com.topcoder.security.TCSubject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * <p>
 * This action provides the operations performed on project assets. In the version 1.0, it provides upload, save
 * and download operations of the single asset file.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Asset View And File Version)
 * <ul>
 *     <li>Added method {@link #getAssetVersion()} to get the details of an asset version</li>
 *     <li>Added method {@link #editAssetVersion()} to update the asset version</li>
 *     <li>Added method {@link #saveNewAssetVersion()} to add a new version to an asset</li>
 *     <li>Added method {@link #deleteAsset()} to delete an asset and all its versions</li>
 *     <li>Added method {@link #deleteAssetVersion()} to delete a specific asset version</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Cockpit Asset View Release 3)
 * <ul>
 *     <li>Adds method {@link #updateAssetsPermission()}</li>
 *     <li>Adds method {@link #getAssetsZip()}</li>
 *     <li>Adds method {@link #downloadAssetsZip()}</li>
 *     <li>Adds method {@link #batchEditAssets()}</li>
 *     <li>Adds method {@link #batchGetAssetsPermission()}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Cockpit Asset View Release 4 - Resource restriction update)
 * <ul>
 *     <li>Remove Client Managers, TopCoder Manager, Project Copilots and their getters and setters
 *     because private permission is removed.
 *     </li>
 *     <li>Updated {@link #executeAction()} to remove setting managers</li>
 *     <li>Updated {@link #batchEditAssets()} to check if user has write+ permission on each asset</li>
 *     <li>Made {@link #batchGetAssetsPermission()} deprecated because it's no longer used after the update</li>
 *     <li>Updated {@link #uploadAssetFile()} to check if user has write+ permission on the project</li>
 *     <li>Updated {@link #updateAssetsPermission()} to check if user has write+ permission on each asset</li>
 *     <li>Updated {@link #getAssetsZip()} ()} to check if user has download permission on each asset</li>
 *     <li>Removed codes to get private permission users in {@link #saveAssetFile()}</li>
 *     <li>Updated {@link #downloadAssetVersion()} to check if user has download permission</li>
 *     <li>Updated {@link #getAssetVersion()} to check if user has read+ permission to the project the asset is in</li>
 *     <li>Updated {@link #editAssetVersion()} to check if user has write+ permission to edit the asset and removed
 *     the code to update the private permission users</li>
 *     <li>Updated {@link #saveNewAssetVersion()} to check if user has write+ permission</li>
 *     <li>Updated {@link #deleteAssetVersion()} to check if user has write+ permission</li>
 *     <li>Updated {@link #deleteAsset()} to check if user has write+ permission</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TopCoder Cockpit Asset View Release 4 - Bug Fixes)
 * <ul>
 *     <li>Integrate the bug fixes for checking the duplicated category name when adding new category</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.5 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 * - Removes the statements to populate the right sidebar direct projects and project contests. It's changed to
 * load these data via ajax instead after the page finishes loading.
 * </p>
 *
 * <p>
 * Version 1.6 (Topcoder - Migrate Struts 2.3 to 2.5 For Direct App)
 * <ul>
 * <li>Changed the package for ValidationAware.</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin, Veve, duxiaoyang
 * @version 1.6
 */
public class ProjectAssetManageAction extends BaseAbstractAssetAction implements FormAction<ProjectIdForm>, ValidationAware {


    private static final long MAX_ASSET_UPLOAD_SIZE = 20971520;

    /**
     * The view DTO for project assets view.
     */
    private ProjectAssetsViewDTO viewData = new ProjectAssetsViewDTO();

    /**
     * The fileName session key.
     */
    private static final String FILE_NAME = "fileName";

    /**
     * The temp file path session key.
     */
    public static final String TEMP_FILE_PATH = "tempFilePath";

    /**
     * The file size session key.
     */
    public static final String FILE_SIZE = "fileSize";

    /**
     * The file size display session key.
     */
    public static final String FILE_SIZE_DISPLAY = "fileSizeDisplay";

    /**
     * The upload time session key.
     */
    public static final String UPLOAD_TIME = "uploadTime";

    /**
     * The uploader session key.
     */
    public static final String UPLOADER = "uploader";

    /**
     * The uploader user id session key.
     */
    public static final String UPLOADER_USER_ID = "uploaderUserId";

    /**
     * The project id session key.
     */
    public static final String PROJECT_ID = "projectId";

    /**
     * The session key for store upload information in the session key.
     */
    public static final String UPLOAD_SESSION_KEY = "uploadSessionKey";

    /**
     * The name header of the batch download zip file.
     *
     * @since 1.2
     */
    private static final String BATCH_DOWNLOAD_FILE_NAME_HEADER = "CockpitFiles";

    /**
     * The temp file root for the asset upload.
     */
    private String assetUploadTempFileRoot;

    /**
     * The project ID form.
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * The file name of the uploaded asset.
     */
    private String uploadFileName;

    /**
     * The uploaded file.
     *
     * @since 1.2
     */
    private File files;

    /**
     * The project id of the uploaded asset.
     */
    private long uploadFileProjectId;

    /**
     * The download stream for downloading the asset from the server.
     */
    private InputStream assetDownloadStream;

    /**
     * The MIME type of the downloaded asset file.
     */
    private String assetDownloadMIMEType;

    /**
     * The date formatter to format the upload time.
     */
    private DateFormat uploadTimeFormatter = new SimpleDateFormat("MM/dd/yyyy | HH:mm:ss");

    /**
     * The display format for the asset upload time in the front end.
     *
     * @since 1.1
     */
    private DateFormat uploadTimeDisplayFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    /**
     * The asset categories available to the user.
     */
    private List<Category> assetCategories;

    /**
     * The assets to batch edit.
     *
     * @since 1.2
     */
    private List<Asset> assetsToEdit;

    /**
     * The assets to save.
     *
     * @since 1.2
     */
    private List<SaveAssetDTO> assets;

    /**
     * The transaction manager
     */
    PlatformTransactionManager transactionManager;

    /**
     * The asset id.
     */
    private long assetId;

    /**
     * The asset version id.
     */
    private long assetVersionId;

    /**
     * The asset version ids to process.
     *
     * @since 1.2
     */
    private long[] assetVersionIds;

    /**
     * The asset ids to process.
     *
     * @since 1.2
     */
    private long[] assetIds;

    /**
     * Flag for whether the asset is public.
     */
    private boolean isAssetPublic;

    /**
     * The user ids which are given the private permission on the asset.
     */
    private List<Long> privateUserIds;

    /**
     * The session key.
     *
     * @since 1.2
     */
    private String sessionKey;

    /**
     * The asset category.
     *
     * @since 1.2
     */
    private Category category;

    /**
     * Gets the files.
     *
     * @return the files.
     * @since 1.2
     */
    public File getFiles() {
        return files;
    }

    /**
     * Sets the files.
     *
     * @param files the files to upload
     * @since 1.2
     */
    public void setFiles(File files) {
        this.files = files;
    }

    /**
     * Sets the file name of the files.
     *
     * @param filename the file name of the files.
     * @since 1.2
     */
    public void setFilesFileName(String filename) {
        this.uploadFileName = filename;
    }

    /**
     * Gets the asset public flag.
     *
     * @return the asset public flag.
     * @since 1.2
     */
    public boolean isAssetPublic() {
        return isAssetPublic;
    }

    /**
     * Sets the asset public flag.
     *
     * @param assetPublic the asset public flag.
     * @since 1.2
     */
    public void setAssetPublic(boolean assetPublic) {
        isAssetPublic = assetPublic;
    }

    /**
     * Gets private user ids.
     *
     * @return the private user ids.
     * @since 1.2
     */
    public List<Long> getPrivateUserIds() {
        return privateUserIds;
    }

    /**
     * Sets the private user ids.
     *
     * @param privateUserIds the private user ids.
     * @since 1.2
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
     * @param sessionKey
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * Gets the project Id form data for the action.
     *
     * @return the form data.
     */
    public ProjectIdForm getFormData() {
        return formData;
    }

    /**
     * Sets the form data.
     *
     * @param formData the form data.
     */
    public void setFormData(ProjectIdForm formData) {
        this.formData = formData;
    }

    /**
     * Gets the asset upload temp file root.
     *
     * @return the asset upload temp file root.
     */
    public String getAssetUploadTempFileRoot() {
        return assetUploadTempFileRoot;
    }

    /**
     * Sets the asset upload temp file root.
     *
     * @param assetUploadTempFileRoot the asset upload temp file root.
     */
    public void setAssetUploadTempFileRoot(String assetUploadTempFileRoot) {
        this.assetUploadTempFileRoot = assetUploadTempFileRoot;
    }


    /**
     * Gets the asset download stream.
     *
     * @return the asset download stream.
     */
    public InputStream getAssetDownloadStream() {
        return assetDownloadStream;
    }

    /**
     * Gets the upload file name.
     *
     * @return the upload file name.
     */
    public String getUploadFileName() {
        return uploadFileName;
    }

    /**
     * Sets the upload file name.
     *
     * @param uploadFileName the upload file name.
     */
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    /**
     * Gets the direct project id of the upload file.
     *
     * @return the direct project id of the upload file.
     */
    public long getUploadFileProjectId() {
        return uploadFileProjectId;
    }

    /**
     * Sets the upload file project id.
     *
     * @param uploadFileProjectId the upload file project id.
     */
    public void setUploadFileProjectId(long uploadFileProjectId) {
        this.uploadFileProjectId = uploadFileProjectId;
    }

    /**
     * Gets the asset categories.
     *
     * @return the asset categories.
     */
    public List<Category> getAssetCategories() {
        return assetCategories;
    }

    /**
     * Sets the asset categories.
     *
     * @param assetCategories the asset categories.
     */
    public void setAssetCategories(List<Category> assetCategories) {
        this.assetCategories = assetCategories;
    }


    /**
     * Gets the save assets.
     *
     * @return the save assets.
     * @since 1.2
     */
    public List<SaveAssetDTO> getAssets() {
        return assets;
    }

    /**
     * Sets the save assets.
     *
     * @param assets the save assets.
     * @since 1.2
     */
    public void setAssets(List<SaveAssetDTO> assets) {
        this.assets = assets;
    }

    /**
     * Sets the transaction manager.
     *
     * @param transactionManager the transaction manager.
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
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
     * Gets the asset version ids.
     *
     * @return the asset version ids.
     * @since 1.2
     */
    public long[] getAssetVersionIds() {
        return assetVersionIds;
    }

    /**
     * Sets the asset version ids.
     *
     * @param assetVersionIds the asset version ids.
     * @since 1.2
     */
    public void setAssetVersionIds(long[] assetVersionIds) {
        this.assetVersionIds = assetVersionIds;
    }

    /**
     * Gets the asset version ids.
     *
     * @return the asset version ids.
     * @since 1.2
     */
    public long[] getAssetIds() {
        return assetIds;
    }

    /**
     * Sets the asset version ids.
     *
     * @param assetIds the asset version ids.
     * @since 1.2
     */
    public void setAssetIds(long[] assetIds) {
        this.assetIds = assetIds;
    }

    /**
     * Gets the assets to batch edit.
     *
     * @return the assets to batch edit.
     * @since 1.2
     */
    public List<Asset> getAssetsToEdit() {
        return assetsToEdit;
    }

    /**
     * Sets the assets to batch edit.
     *
     * @param assetsToEdit the assets to batch edit.
     * @since 1.2
     */
    public void setAssetsToEdit(List<Asset> assetsToEdit) {
        this.assetsToEdit = assetsToEdit;
    }

    /**
     * Gets the download MIME type of the asset.
     *
     * @return the asset download MIME type of the asset.
     */
    public String getAssetDownloadMIMEType() {
        return assetDownloadMIMEType;
    }

    /**
     * Gets the asset view data.
     *
     * @return the asset view data.
     */
    public ProjectAssetsViewDTO getViewData() {
        return viewData;
    }

    /**
     * Gets the asset category.
     *
     * @return the asset category.
     * @since 1.2
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the asset category.
     *
     * @param category the asset category.
     * @since 1.2
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Actions for entry for the asset upload page.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {

        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        if(!AuthorizationProvider.isUserGrantedWriteAccessToProject(currentUser, getFormData().getProjectId())) {
            DirectUtils.setErrorMessageInErrorPage("You don't have write permission on this project to upload assets");
            throw new IllegalArgumentException("You don't have write permission on this project to upload assets");
        }

        // prepare asset categories
        CategorySearchCriteria categorySearch = new CategorySearchCriteria();
        categorySearch.setContainerType(AssetContainerType.GLOBAL.toString());

        PagedResult<Category> categories = getAssetCategoryService().search(categorySearch);
        setAssetCategories(categories.getRecords());

        categorySearch.setContainerType(AssetContainerType.PROJECT.toString());
        categorySearch.setContainerId(getFormData().getProjectId());

        getAssetCategories().addAll(getAssetCategoryService().search(categorySearch).getRecords());

        // right sidebar data
        final ProjectContestsListDTO projectContests = DataProvider.getProjectContests(currentUser.getUserId(), getFormData().getProjectId());

        ProjectBriefDTO currentDirectProject;

        if (projectContests.getContests().size() > 0) {
            currentDirectProject = projectContests.getContests().get(0).getContest().getProject();
        } else {
            currentDirectProject = DirectUtils.getCurrentProjectBrief(getProjectServiceFacade(), getFormData().getProjectId());
        }

        getSessionData().setCurrentProjectContext(currentDirectProject);
        getSessionData().setCurrentSelectDirectProjectID(currentDirectProject.getId());
    }


    /**
     * Handles the ajax request to add new file category for the project.
     *
     * @return the result code.
     * @since 1.2
     */
    public String addNewCategory() {
        try {

            if (getCategory() == null || getCategory().getName() == null
                    || getCategory().getName().trim().length() == 0) {
                throw new IllegalArgumentException("The file category to add is empty");
            }

            // Get existing categories
            CategorySearchCriteria categorySearch = new CategorySearchCriteria();
            categorySearch.setContainerType(AssetContainerType.GLOBAL.toString());

            PagedResult<Category> categories = getAssetCategoryService().search(categorySearch);
            setAssetCategories(categories.getRecords());

            categorySearch.setContainerType(AssetContainerType.PROJECT.toString());
            categorySearch.setContainerId(getFormData().getProjectId());
            getAssetCategories().addAll(getAssetCategoryService().search(categorySearch).getRecords());

            // check the duplicated name
            for(Category c : getAssetCategories()) {
                if(c.getName().equalsIgnoreCase(getCategory().getName())) {
                    throw new IllegalArgumentException(
                            "The category name " + getCategory().getName() + " already exists");
                }
            }

            Category categoryToAdd = getCategory();
            categoryToAdd.setContainerType(AssetContainerType.PROJECT.toString());
            categoryToAdd.setContainerId(getFormData().getProjectId());

            getAssetCategoryService().create(categoryToAdd);

            ObjectMapper m = new ObjectMapper();
            Map<String, Object> result = m.convertValue(categoryToAdd, Map.class);

            setResult(result);


        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }
        }
        return SUCCESS;
    }

    /**
     * Handles the request to batch edit assets.
     *
     * @return the result code
     * @throws Exception if any error.
     * @since 1.2
     */
    public String batchEditAssets() throws Exception {

        if(getAssetIds() == null || getAssetIds().length == 0) {
            throw new IllegalArgumentException("The assets to batch edit are empty");
        }

        List<Asset> assets = getAssetService().getAssets(getAssetIds());

        // check the permission of each asset
        for(Asset assetToCheck : assets) {
            checkIfAssetAccessAllowed(assetToCheck, DirectUtils.getTCSubjectFromSession(), true);
        }

        setAssetsToEdit(assets);

        // prepare asset categories
        CategorySearchCriteria categorySearch = new CategorySearchCriteria();
        categorySearch.setContainerType(AssetContainerType.GLOBAL.toString());

        PagedResult<Category> categories = getAssetCategoryService().search(categorySearch);
        setAssetCategories(categories.getRecords());

        // right sidebar data
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
        final ProjectContestsListDTO projectContests = DataProvider.getProjectContests(currentUser.getUserId(),
                                                                                       getFormData().getProjectId());
        ProjectBriefDTO currentDirectProject;

        if (projectContests.getContests().size() > 0) {
            currentDirectProject = projectContests.getContests().get(0).getContest().getProject();
        } else {
            currentDirectProject = DirectUtils.getCurrentProjectBrief(getProjectServiceFacade(),
                                                                      getFormData().getProjectId());
        }

        getSessionData().setCurrentProjectContext(currentDirectProject);
        getSessionData().setCurrentSelectDirectProjectID(currentDirectProject.getId());


        return SUCCESS;
    }

    /**
     * Handles the action operation to upload the asset file.
     *
     * @return the result code.
     */
    public String uploadAssetFile() {
        File assetUploadTempFile;
        InputStream uploadFileStream = null;
        FileOutputStream saveFileStream = null;
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        try {
            Date currentTime = new Date();
            long currentTimeMillis = currentTime.getTime();

            if (!AuthorizationProvider.isUserGrantedWriteAccessToProject(currentUser, getUploadFileProjectId())) {
                DirectUtils.setErrorMessageInErrorPage("Current user does not have permission to upload file to this project");
                throw new IllegalArgumentException("Current user does not have permission to access the project:" + getUploadFileProjectId());
            }

            String tempFilePath = getAssetUploadTempFilePath(this.uploadFileName, getUploadFileProjectId(), currentTimeMillis);
            assetUploadTempFile = new File(tempFilePath);

            if(getFiles() == null || !getFiles().exists() || getFiles().length() == 0L) {
                throw new IllegalArgumentException("The uploaded file is of 0 bytes or does not exist, please check.");
            }

            File uploadFile = getFiles();

            if(uploadFile.length() > MAX_ASSET_UPLOAD_SIZE) {
                throw new IllegalArgumentException("The uploaded file exceeds the max allowed size 20M");
            }

            uploadFileStream = new FileInputStream(uploadFile);
            saveFileStream = new FileOutputStream(assetUploadTempFile);

            // save the upload file after inserting the record
            IOUtils.copy(uploadFileStream, saveFileStream);

            // the session data for the following operation
            Map<String, String> uploadSession = new HashMap<String, String>();
            uploadSession.put(FILE_NAME, this.uploadFileName);
            uploadSession.put(TEMP_FILE_PATH, tempFilePath);
            long fileSize = assetUploadTempFile.length();
            uploadSession.put(FILE_SIZE, String.valueOf(fileSize));
            uploadSession.put(FILE_SIZE_DISPLAY, FileUtils.byteCountToDisplaySize(fileSize));
            uploadSession.put(UPLOAD_TIME, uploadTimeFormatter.format(currentTime));
            uploadSession.put(UPLOADER, getUserService().getUserHandle(DirectUtils.getTCSubjectFromSession().getUserId()));
            uploadSession.put(UPLOADER_USER_ID, String.valueOf(DirectUtils.getTCSubjectFromSession().getUserId()));
            uploadSession.put(PROJECT_ID, String.valueOf(getUploadFileProjectId()));

            String sessionKey = this.uploadFileName + currentTimeMillis;

            getSessionData().getSession().setAttribute(sessionKey, uploadSession);

            // build the result set
            Map<String, String> result = new HashMap<String, String>();
            result.putAll(uploadSession);
            result.put(UPLOAD_SESSION_KEY, sessionKey);
            result.put("uploadFileTypeIcon", AssetFileTypes.getFileTypeIconName(FilenameUtils.getExtension(tempFilePath)));

            setResult(result);

        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }
        } finally {
            try {
                if (uploadFileStream != null) {
                    uploadFileStream.close();
                }
                if (saveFileStream != null) {
                    saveFileStream.close();
                }
            } catch (Throwable ex) {
                //ignore
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the ajax request to updates the assets permission in batch.
     *
     * @return the result code
     * @since 1.2
     */
    public String updateAssetsPermission() {

        if (getAssetIds() == null || getAssetIds().length == 0) {
            throw new IllegalArgumentException("The asset ids to update are empty");
        }

        TransactionStatus status = null;

        try {
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setName(this.getClass().getName() + ".updateAssetsPermission");
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            status = transactionManager.getTransaction(def);

            List<Asset> assets = getAssetService().getAssets(getAssetIds());

            for(Asset assetToCheck : assets) {
                checkIfAssetAccessAllowed(assetToCheck, currentUser, true);
            }

            for (Asset asset : assets) {
                asset.setPublic(isAssetPublic());
            }

            // update the assets
            getAssetService().updateAssets(currentUser.getUserId(), assets);

            transactionManager.commit(status);

            Map<String, Object> result = new HashMap<String, Object>();

            setResult(result);

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }

            if (status != null && !status.isCompleted()) {
                transactionManager.rollback(status);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the ajax request to get multiple assets file in a zip.
     *
     * @return the result code.
     * @since 1.2
     */
    public String getAssetsZip() {
        try {
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            if (getAssetVersionIds() == null || getAssetVersionIds().length == 0) {
                throw new IllegalArgumentException("The asset versions to download are not specified");
            }

            // check permission first
            for(long assetVersionId : getAssetVersionIds()) {
                Asset assetToCheck = getAssetService().getAsset(getAssetVersionService().getAssetVersion(assetVersionId).getAssetId());
                // check if has permission on each asset
                checkIfAssetDownloadAllowed(assetToCheck, currentUser);
            }

            long currentTimeMillis = System.currentTimeMillis();
            String assetsZipFileName = BATCH_DOWNLOAD_FILE_NAME_HEADER + currentTimeMillis + ".zip";
            String assetsZipFilePath = getAssetUploadTempFilePath(assetsZipFileName, getUploadFileProjectId(),
                    currentTimeMillis);

            OutputStream zipStream = new FileOutputStream(new File(assetsZipFilePath));

            List<Long> assetVersionIdsList = new ArrayList<Long>();
            for (long l : getAssetVersionIds()) {
                assetVersionIdsList.add(l);
            }

            getAssetVersionService().batchGetAssetVersionContents(assetVersionIdsList, zipStream);

            // flush and close the string (writes to the zip file)
            zipStream.close();
            Map<String, String> sessionData = new HashMap<String, String>();
            sessionData.put(TEMP_FILE_PATH, assetsZipFilePath);
            sessionData.put(UPLOADER_USER_ID, String.valueOf(currentUser.getUserId()));

            String sessionUUID = UUID.randomUUID().toString();
            getSessionData().getSession().setAttribute(sessionUUID, sessionData);

            Map<String, String> result = new HashMap<String, String>();
            result.put(UPLOAD_SESSION_KEY, sessionUUID);
            result.put(FILE_NAME, assetsZipFileName);
            result.put(FILE_SIZE_DISPLAY, FileUtils.byteCountToDisplaySize((new File(assetsZipFilePath)).length()));

            setResult(result);

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the request to download the zipped assets file.
     *
     * @return the result code.
     * @since 1.2
     */
    public String downloadAssetsZip() {
        try {
            // get session data with session key
            Map<String, String> sessionData = (Map<String, String>) getSessionData().getSession().getAttribute(getSessionKey());

            String assetFilePath = sessionData.get(TEMP_FILE_PATH);
            File assetFile = new File(assetFilePath);

            setUploadFileName(assetFile.getName());

            this.assetDownloadStream = new BufferedInputStream(new FileInputStream(assetFilePath));

            this.assetDownloadMIMEType = DirectUtils.getFileMIMEType(assetFile);

            if (this.assetDownloadMIMEType == null) {
                // cannot get the MIME type, use the default
                this.assetDownloadMIMEType = "text/plain";
            }

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return "download";
    }


    /**
     * Handles the ajax operation to save the asset information.
     *
     * @return the result code.
     */
    public String saveAssetFile() {

        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        if(getAssets() == null || getAssets().size() == 0) {
            throw new IllegalArgumentException("The assets to save are empty");
        }


        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(this.getClass().getName() + ".saveAssetFile");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        List<File> uploadedFiles = new ArrayList<File>();
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        boolean needToDelete = false;

        try {

           for(SaveAssetDTO assetToSave : assets) {
               if (assetToSave.getSessionKey() == null || assetToSave.getSessionKey().length() == 0) {
                   throw new IllegalArgumentException("The upload session does not exist");
               }

               Object value = getSessionData().getSession().getAttribute(assetToSave.getSessionKey());

               if (value == null) {
                   throw new IllegalArgumentException("The upload session does not exist or the session is time out");
               }

               Map<String, String> uploadSession = (Map<String, String>) value;
               File uploadedFile = new File(uploadSession.get(TEMP_FILE_PATH));

               // create new asset
               Asset asset = new Asset();
               asset.setContainerType(AssetContainerType.PROJECT.toString());
               asset.setContainerId(Long.parseLong(uploadSession.get(PROJECT_ID)));
               asset.setName(uploadSession.get(FILE_NAME));
               asset.setPublic(assetToSave.isAssetPublic());
               List<Category> categories = new ArrayList<Category>();
               categories.add(getAssetCategoryService().get(assetToSave.getAssetCategoryId()));
               // set asset categories
               asset.setCategories(categories);

               getAssetService().createAsset(currentUser.getUserId(), asset);

               // create the asset version for the upload
               AssetVersion version = new AssetVersion();
               version.setDescription(assetToSave.getAssetDescription());
               version.setFileName(uploadSession.get(FILE_NAME));
               version.setFileSizeBytes(Long.parseLong(uploadSession.get(FILE_SIZE)));
               version.setUploadTime(uploadTimeFormatter.parse(uploadSession.get(UPLOAD_TIME)));
               User uploader = new User();
               uploader.setId(Long.parseLong(uploadSession.get(UPLOADER_USER_ID)));
               version.setUploader(uploader);

               // always use 1.0 for the new asset file for now
               version.setVersion("1.0");

               // set asset id
               version.setAssetId(asset.getId());

               getAssetVersionService().createAssetVersion(currentUser.getUserId(), version, uploadedFile, true);

               // build the result set
               Map<String, String> singleResult = new HashMap<String, String>();
               singleResult.put("assetId", String.valueOf(asset.getId()));
               singleResult.put("assetVersionId", String.valueOf(version.getId()));
               singleResult.put("directProjectId", uploadSession.get(PROJECT_ID));
               result.add(singleResult);

               // add this file to the uploaded file list
               uploadedFiles.add(uploadedFile);
           }


            transactionManager.commit(status);

            setResult(result);

            // the whole operation succeeds without any exception - the temp file uploaded can be deleted
            needToDelete = true;

        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }

            if (!status.isCompleted()) {
                transactionManager.rollback(status);
                needToDelete = false;
            }
        } finally {
            if (needToDelete) {
                try {
                    if(uploadedFiles != null && uploadedFiles.size() > 0) {
                        for(File fileToDelete : uploadedFiles) {
                            FileUtils.forceDelete(fileToDelete);
                        }
                    }
                } catch (Throwable th) {
                    // ignore
                }
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the ajax operation to download the asset file from the server.
     *
     * @return the result code
     */
    public String downloadAssetVersion() {
        try {
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            Asset asset = getAssetService().getAsset(getAssetId());

            // check if user has access to the asset - Update in (TopCoder Cockpit Asset View Release 4 - Resource restriction update)
            // do not check with asset permission service because we only check project permission now
            checkIfAssetDownloadAllowed(asset, currentUser);

            List<AssetVersion> assetVersions = getAssetVersionService().getAssetVersionsOfAsset(asset.getId());

            AssetVersion assetVersion = null;

            for(AssetVersion av : assetVersions) {
                if (av.getId() == getAssetVersionId()) {
                    assetVersion = av;
                }
            }

            if (assetVersion == null) {
                throw new IllegalArgumentException("The asset version id is incorrect");
            }

            String assetFilePath = assetVersion.getFilePath();

            setUploadFileName(assetVersion.getFileName());
            this.assetDownloadStream = new BufferedInputStream(new FileInputStream(assetFilePath));

            this.assetDownloadMIMEType = DirectUtils.getFileMIMEType(new File(assetFilePath));

            if (this.assetDownloadMIMEType == null) {
                // cannot get the MIME type, use the default
                this.assetDownloadMIMEType = "text/plain";
            }

        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }
        }

        return "download";
    }

    /**
     * Handles the ajax operation to get the details of an asset version.
     *
     * @return the result code
     * @since 1.1
     */
    public String getAssetVersion() {
        try {
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            // check asset version id
            if(getAssetVersionId() <= 0) {
                throw new IllegalArgumentException("The asset version id should be positive");
            }

            AssetVersion assetVersion = getAssetVersionService().getAssetVersion(getAssetVersionId());

            // check if the user has access to the asset
            Asset assetToCheck = getAssetService().getAsset(assetVersion.getAssetId());
            checkIfAssetAccessAllowed(assetToCheck, currentUser, false);

            // serialize asset version to ajax
            ObjectMapper m = new ObjectMapper();
            m.setDateFormat(uploadTimeDisplayFormatter);
            Map<String, Object> result = m.convertValue(assetVersion, Map.class);

            // remove path information - reduce security issue
            result.remove("filePath");
            result.remove("previewImagePath");

            // add file size readable format
            result.put("fileSizeDisplay", JSPHelper.getFileSizeDisplay(assetVersion.getFileSizeBytes()));

            Asset asset = getAssetService().getAsset(assetVersion.getAssetId());

            // put the asset permission data - if the asset is private
            if(!asset.isPublic()) {
                Set<Long> allowedUserIds = new HashSet<Long>();
                result.put("users", allowedUserIds);
                result.put("isPublic", false);
            } else {
                result.put("isPublic", true);
            }

            // put asset category
            result.put("categoryId", asset.getCategories().get(0).getId());
            result.put("categoryName", asset.getCategories().get(0).getName());

            result.put("isLatest", asset.getCurrentVersion().getId() == assetVersion.getId());

            setResult(result);

        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }


    /**
     * Handles the ajax operation to edit the details of an asset version.
     *
     * @return the result code
     * @since 1.1
     */
    public String editAssetVersion() {


        if(getAssets() == null || getAssets().size() == 0) {
            throw new IllegalArgumentException("The asset to update is empty");
        }

        TransactionStatus status = null;

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        try {

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setName(this.getClass().getName() + ".editAssetVersion");
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            status = transactionManager.getTransaction(def);

            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            for(SaveAssetDTO assetToUpdate : getAssets()) {

                Asset asset = null;
                AssetVersion assetVersion = null;

                // check asset version id
                if(assetToUpdate.getAssetVersionId() <= 0 && assetToUpdate.getAssetId() <= 0) {
                    throw new IllegalArgumentException("The asset id and asset version id should be positive");
                }

                if(assetToUpdate.getAssetVersionId() <= 0) {
                    asset = getAssetService().getAsset(assetToUpdate.getAssetId());
                    // use current asset version
                    assetToUpdate.setAssetVersionId(asset.getCurrentVersion().getId());
                    assetVersion = asset.getCurrentVersion();
                } else if (assetToUpdate.getAssetId() <= 0) {
                    assetVersion = getAssetVersionService().getAssetVersion(assetToUpdate.getAssetVersionId());
                    asset = getAssetService().getAsset(assetVersion.getAssetId());
                }

                if(asset == null || assetVersion == null) {
                    throw new IllegalArgumentException("The asset id and asset version does not exist");
                }


                checkIfAssetAccessAllowed(asset, currentUser, true);

                // check description length
                if(assetToUpdate.getAssetDescription() == null || assetToUpdate.getAssetDescription().trim().length() == 0) {
                    throw new IllegalArgumentException("File description cannot be empty");
                }

                assetVersion.setDescription(assetToUpdate.getAssetDescription());

                getAssetVersionService().updateAssetVersion(currentUser.getUserId(), assetVersion);

                // if asset category is changed, update it
                if(asset.getCategories() != null && asset.getCategories().size() > 0) {
                    if(asset.getCategories().get(0).getId() != assetToUpdate.getAssetCategoryId()) {
                        List<Category> newCategories = new ArrayList<Category>();
                        newCategories.add(getAssetCategoryService().get(assetToUpdate.getAssetCategoryId()));
                        asset.setCategories(newCategories);
                    }
                }

                if(assetToUpdate.isAssetPublic()) {
                    asset.setPublic(true);
                } else {
                    asset.setPublic(false);
                }

                // update asset
                getAssetService().updateAsset(currentUser.getUserId(), asset);

                Map<String, Object> resultItem = new HashMap<String, Object>();

                resultItem.put("assetId", String.valueOf(asset.getId()));
                resultItem.put("assetVersionId", String.valueOf(assetVersion.getId()));
                resultItem.put("directProjectId", asset.getContainerId());
                result.add(resultItem);
            }

            transactionManager.commit(status);

            setResult(result);

        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }

            if (status != null && !status.isCompleted()) {
                transactionManager.rollback(status);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the ajax operation to add a new asset version.
     *
     * @return the result code
     * @since 1.1
     */
    public String saveNewAssetVersion() {

        if(getAssets() == null || getAssets().size() == 0) {
            throw new IllegalArgumentException("The new asset version to add is empty");
        }

        File uploadedFile = null;
        TransactionStatus status = null;
        boolean needToDelete = false;

        try {
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            SaveAssetDTO newAssetVersion = getAssets().get(0);

            // 1) Perform validations and permission checking
            if(getAssetId() <= 0) {
                throw new IllegalArgumentException("The ID of the asset the new version is added to should be positive");
            }

            // get the asset
            Asset asset = getAssetService().getAsset(getAssetId());

            checkIfAssetAccessAllowed(asset, currentUser, true);

            // check if the upload new version session exists
            if (newAssetVersion.getSessionKey() == null || newAssetVersion.getSessionKey().length() == 0) {
                throw new IllegalArgumentException("The upload session does not exist");
            }

            Object value = getSessionData().getSession().getAttribute(newAssetVersion.getSessionKey());

            if (value == null) {
                throw new IllegalArgumentException("The upload session does not exist or the session is time out");
            }

            // check if file description is filled
            if(newAssetVersion.getAssetDescription() == null || newAssetVersion.getAssetDescription().trim().length() == 0) {
                throw new IllegalArgumentException("File description of the new version cannot be empty");
            }

            Map<String, String> uploadSession = (Map<String, String>) value;
            uploadedFile = new File(uploadSession.get(TEMP_FILE_PATH));

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setName(this.getClass().getName() + ".saveNewAssetVersion");
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            status = transactionManager.getTransaction(def);

            // get the current latest version
            AssetVersion currentVersion = asset.getCurrentVersion();
            double currentVersionNumber = Double.parseDouble(currentVersion.getVersion());
            currentVersionNumber = currentVersionNumber + 0.1;
            NumberFormat versionFormat = NumberFormat.getInstance();
            versionFormat.setMaximumFractionDigits(1);
            versionFormat.setMinimumFractionDigits(1);

            // create the asset version for the upload
            AssetVersion version = new AssetVersion();
            version.setDescription(newAssetVersion.getAssetDescription());
            version.setFileName(uploadSession.get(FILE_NAME));
            version.setFileSizeBytes(Long.parseLong(uploadSession.get(FILE_SIZE)));
            version.setUploadTime(uploadTimeFormatter.parse(uploadSession.get(UPLOAD_TIME)));
            User uploader = new User();
            uploader.setId(Long.parseLong(uploadSession.get(UPLOADER_USER_ID)));
            version.setUploader(uploader);

            // use the new version number
            version.setVersion(versionFormat.format(currentVersionNumber));

            // set asset id
            version.setAssetId(asset.getId());

            getAssetVersionService().createAssetVersion(currentUser.getUserId(), version, uploadedFile, true);

            // commit the transaction
            transactionManager.commit(status);

            Map<String, Object> result = new HashMap<String, Object>();

            result.put("assetId", String.valueOf(asset.getId()));
            result.put("assetVersionId", String.valueOf(version.getId()));
            result.put("directProjectId", uploadSession.get(PROJECT_ID));

            setResult(result);

            // the whole operation succeeds - the temp file uploaded can be deleted
            needToDelete = true;

        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }

            if (status != null && !status.isCompleted()) {
                // not completed - roll back
                transactionManager.rollback(status);
                needToDelete = false;
            }
        } finally {
            if (needToDelete && uploadedFile != null) {
                try {
                    FileUtils.forceDelete(uploadedFile);
                } catch (Throwable th) {
                    // ignore
                }
            }
        }


        return SUCCESS;
    }

    /**
     * Handles the ajax operation to delete an specific asset version.
     *
     * @return the result code
     * @since 1.1
     */
    public String deleteAssetVersion() {

        TransactionStatus status = null;

        try {
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            if(getAssetVersionIds() == null || getAssetVersionIds().length == 0) {
                throw new IllegalArgumentException("The id(s) of the asset version(s) to delete are not specified");
            }

            for(long value : getAssetVersionIds()) {
                // check asset version id
                if (value <= 0) {
                    throw new IllegalArgumentException("The id of the asset version to delete is invalid");
                }

            }

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setName(this.getClass().getName() + ".deleteAssetVersion");
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            status = transactionManager.getTransaction(def);

            for(long assetVersionId : getAssetVersionIds()) {
                AssetVersion assetVersion = getAssetVersionService().getAssetVersion(assetVersionId);

                Asset assetToCheck = getAssetService().getAsset(assetVersion.getAssetId());

                checkIfAssetAccessAllowed(assetToCheck, currentUser, true);

                // delete the asset version
                getAssetVersionService().deleteAssetVersion(currentUser.getUserId(), assetVersion.getId());
            }

            transactionManager.commit(status);

            Map<String, Object> result = new HashMap<String, Object>();

            setResult(result);

        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }

            if (status != null && !status.isCompleted()) {
                // not completed - roll back
                transactionManager.rollback(status);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the ajax operation to delete the whole asset and its versions.
     *
     * @return the result code
     * @since 1.1
     */
    public String deleteAsset() {

        TransactionStatus status = null;

        try {
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            // check asset version id
            if (getAssetId() <= 0) {
                throw new IllegalArgumentException("The id of the asset to delete is invalid");
            }

            // check permission
            Asset asset = getAssetService().getAsset(getAssetId());

            checkIfAssetAccessAllowed(asset, currentUser, true);

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setName(this.getClass().getName() + ".deleteAsset");
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            status = transactionManager.getTransaction(def);

            // delete the asset
            getAssetService().deleteAsset(currentUser.getUserId(), getAssetId());

            transactionManager.commit(status);

            Map<String, Object> result = new HashMap<String, Object>();

            setResult(result);

        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }

            if (status != null && !status.isCompleted()) {
                // not completed - roll back
                transactionManager.rollback(status);
            }
        }

        return SUCCESS;
    }


    /**
     * Helper method to get the asset upload file temp storage path by the given file name, direct project id
     * and upload time.
     *
     * @param fileName   the file name.
     * @param projectId  the direct project id
     * @param uploadTime the file upload time in milliseconds
     * @return the generated absolute temp file path
     * @throws Exception if there is any error
     */
    private String getAssetUploadTempFilePath(String fileName, long projectId, long uploadTime) throws Exception {
        StringBuffer path = new StringBuffer();
        path.append(assetUploadTempFileRoot).append(File.separator)
                .append(projectId).append(File.separator).append(uploadTime).append(File.separator);
        File tempFolder = new File(path.toString());
        if (!tempFolder.exists()) {
            FileUtils.forceMkdir(tempFolder);
        }
        path.append(fileName);
        return path.toString();
    }
}
