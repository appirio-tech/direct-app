/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.asset.project;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.entities.Category;
import com.topcoder.asset.entities.CategorySearchCriteria;
import com.topcoder.asset.entities.PagedResult;
import com.topcoder.asset.entities.User;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.asset.AssetContainerType;
import com.topcoder.direct.services.view.action.asset.BaseAbstractAssetAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.asset.project.AssetFileTypes;
import com.topcoder.direct.services.view.dto.asset.project.ProjectAssetsViewDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This action provides the operations performed on project assets. In the version 1.0, it provides upload, save
 * and download operations of the single asset file.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload version 1.0)
 */
public class ProjectAssetManageAction extends BaseAbstractAssetAction implements FormAction<ProjectIdForm> {

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
     */
    private File uploadFile;

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
     * The asset categories available to the user.
     */
    private List<Category> assetCategories;


    /**
     * The client managers for the asset permission chosen
     */
    private List<User> clientManagers;

    /**
     * The TopCoder manager for the asset permission chosen.
     */
    private List<User> topcoderManagers;

    /**
     * The project copilots for the asset permission chosen.
     */
    private List<User> projectCopilots;

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
     * Gets the upload file.
     *
     * @return the upload file.
     */
    public File getUploadFile() {
        return uploadFile;
    }

    /**
     * Sets the upload file.
     *
     * @param uploadFile the upload file.
     */
    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
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
     * Gets client managers for permission setting.
     *
     * @return client managers for permission setting
     */
    public List<User> getClientManagers() {
        return clientManagers;
    }

    /**
     * Sets client managers for permission setting.
     *
     * @param clientManagers client managers for permission setting.
     */
    public void setClientManagers(List<User> clientManagers) {
        this.clientManagers = clientManagers;
    }

    /**
     * Gets TopCoder managers for permission setting.
     *
     * @return TopCoder managers for permission setting
     */
    public List<User> getTopcoderManagers() {
        return topcoderManagers;
    }

    /**
     * Sets TopCoder managers for permission setting.
     *
     * @param topcoderManagers TopCoder managers for permission setting
     */
    public void setTopcoderManagers(List<User> topcoderManagers) {
        this.topcoderManagers = topcoderManagers;
    }

    /**
     * Gets project copilots for permission setting.
     *
     * @return project copilots for permission setting
     */
    public List<User> getProjectCopilots() {
        return projectCopilots;
    }

    /**
     * Sets project copilots for permission setting.
     *
     * @param projectCopilots project copilots for permission setting.
     */
    public void setProjectCopilots(List<User> projectCopilots) {
        this.projectCopilots = projectCopilots;
    }

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
     * Checks whether the asset is public.
     *
     * @return whether the asset is public.
     */
    public boolean isAssetPublic() {
        return isAssetPublic;
    }

    /**
     * Checks whether the asset is public
     *
     * @param assetPublic whether asset is public
     */
    public void setAssetPublic(boolean assetPublic) {
        isAssetPublic = assetPublic;
    }

    /**
     * Gets the private user ids for permission setting.
     *
     * @return the private user ids for permission setting.
     */
    public List<Long> getPrivateUserIds() {
        return privateUserIds;
    }

    /**
     * Sets the private user ids for permission setting.
     *
     * @param privateUserIds the private user ids for permission setting.
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
     * Gets the download MIME type of the asset.
     *
     * @return the asset download MIME type of the asset.
     */
    public String getAssetDownloadMIMEType() {
        return assetDownloadMIMEType;
    }

    public ProjectAssetsViewDTO getViewData() {
        return viewData;
    }

    /**
     * Actions for entry for the asset upload page.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        // prepare asset categories
        CategorySearchCriteria categorySearch = new CategorySearchCriteria();
        categorySearch.setContainerType(AssetContainerType.GLOBAL.toString());

        PagedResult<Category> categories = getAssetCategoryService().search(categorySearch);
        setAssetCategories(categories.getRecords());

        // prepare asset permission users
        setClientManagers(getManagerService().getClientManagers(getFormData().getProjectId()));
        setTopcoderManagers(getManagerService().getTopCoderManagers(getFormData().getProjectId()));
        setProjectCopilots(getManagerService().getCopilots(getFormData().getProjectId()));

        // right side bar data
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

            if (!AuthorizationProvider.isUserGrantedAccessToProject(currentUser, getUploadFileProjectId())) {
                throw new IllegalArgumentException("Current user does not have permission to access the project:" + getUploadFileProjectId());
            }

            String tempFilePath = getAssetUploadTempFilePath(getUploadFileName(), getUploadFileProjectId(), currentTimeMillis);
            assetUploadTempFile = new File(tempFilePath);

            if(getUploadFile() == null || !getUploadFile().exists() || getUploadFile().length() == 0L) {
                throw new IllegalArgumentException("The uploaded file is of 0 bytes or does not exist, please check.");
            }

            uploadFileStream = new FileInputStream(getUploadFile());
            saveFileStream = new FileOutputStream(assetUploadTempFile);

            // save the upload file after inserting the record
            IOUtils.copy(uploadFileStream, saveFileStream);

            // the session data for the following operation
            Map<String, String> uploadSession = new HashMap<String, String>();
            uploadSession.put(FILE_NAME, getUploadFileName());
            uploadSession.put(TEMP_FILE_PATH, tempFilePath);
            long fileSize = assetUploadTempFile.length();
            uploadSession.put(FILE_SIZE, String.valueOf(fileSize));
            uploadSession.put(FILE_SIZE_DISPLAY, FileUtils.byteCountToDisplaySize(fileSize));
            uploadSession.put(UPLOAD_TIME, uploadTimeFormatter.format(currentTime));
            uploadSession.put(UPLOADER, getUserService().getUserHandle(DirectUtils.getTCSubjectFromSession().getUserId()));
            uploadSession.put(UPLOADER_USER_ID, String.valueOf(DirectUtils.getTCSubjectFromSession().getUserId()));
            uploadSession.put(PROJECT_ID, String.valueOf(getUploadFileProjectId()));

            String sessionKey = getUploadFileName() + currentTimeMillis;

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
     * Handles the ajax operation to save the asset information.
     *
     * @return the result code.
     */
    public String saveAssetFile() {

        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        if (getSessionKey() == null || getSessionKey().length() == 0) {
            throw new IllegalArgumentException("The upload session does not exist");
        }

        Object value = getSessionData().getSession().getAttribute(getSessionKey());

        if (value == null) {
            throw new IllegalArgumentException("The upload session does not exist or the session is time out");
        }

        Map<String, String> uploadSession = (Map<String, String>) value;
        File uploadedFile = new File(uploadSession.get(TEMP_FILE_PATH));

        boolean needToDelete = false;

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(this.getClass().getName() + ".saveAssetFile");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {

            // create new asset
            Asset asset = new Asset();
            asset.setContainerType(AssetContainerType.PROJECT.toString());
            asset.setContainerId(Long.parseLong(uploadSession.get(PROJECT_ID)));
            asset.setName(uploadSession.get(FILE_NAME));
            asset.setPublic(isAssetPublic);
            List<Category> categories = new ArrayList<Category>();
            categories.add(getAssetCategoryService().get(getAssetCategoryId()));
            // set asset categories
            asset.setCategories(categories);

            getAssetService().createAsset(currentUser.getUserId(), asset);

            // create the asset version for the upload
            AssetVersion version = new AssetVersion();
            version.setDescription(getAssetDescription());
            version.setFileName(uploadSession.get(FILE_NAME));
            version.setFileSizeBytes(Long.parseLong(uploadSession.get(FILE_SIZE)));
            version.setUploadTime(uploadTimeFormatter.parse(uploadSession.get(UPLOAD_TIME)));
            User uploader = new User();
            uploader.setId(Long.parseLong(uploadSession.get(UPLOADER_USER_ID)));
            version.setUploader(uploader);
            // always user 1.0 for now
            version.setVersion("1.0");

            // set asset id
            version.setAssetId(asset.getId());

            getAssetVersionService().createAssetVersion(currentUser.getUserId(), version, uploadedFile, true);

            // set permission
            if (!isAssetPublic() && (getPrivateUserIds() == null || getPrivateUserIds().size() > 0)) {

                if (getPrivateUserIds() == null) {
                    privateUserIds = new ArrayList<Long>();
                }

                // by default, give uploader the permission if the restriction is private
                if (!getPrivateUserIds().contains(currentUser.getUserId())) {
                    getPrivateUserIds().add(currentUser.getUserId());
                }
                getAssetPermissionService().setPermissions(Arrays.asList(new Long[]{asset.getId()}), getPrivateUserIds());
            }

            transactionManager.commit(status);

            // build the result set
            Map<String, String> result = new HashMap<String, String>();
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

            if (!status.isCompleted()) {
                transactionManager.rollback(status);
                needToDelete = false;
            }
        } finally {
            if (needToDelete) {
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
     * Handles the ajax operation to download the asset file from the server.
     *
     * @return the result code
     */
    public String downloadAssetVersion() {
        try {
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            // check if user has access to the asset
            if (!getAssetPermissionService().isAllowed(getAssetId(), currentUser.getUserId())) {
                throw new IllegalArgumentException("You don't have permission to download the asset");
            }

            Asset asset = getAssetService().getAsset(getAssetId());

            if (asset.getCurrentVersion().getId() != getAssetVersionId()) {
                throw new IllegalArgumentException("The asset version id is incorrect");
            }

            String assetFilePath = asset.getCurrentVersion().getFilePath();

            setUploadFileName(asset.getCurrentVersion().getFileName());
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
