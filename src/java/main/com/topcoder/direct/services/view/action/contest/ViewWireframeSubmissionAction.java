/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.user.UserService;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;


/**
 * This struts action extends the AbstractAction, and it's used to unzip the wireframe submission into a local
 * directory, and then redirect user to the index page of the wireframe.
 * 
 * Thread-safety: It's not thread-safe.
 *
 * <p>
 *     Version 1.1 (TC-Studio - Wireframe Viewer Modal Window Direct Updates assembly v1.0) change notes:
 *     <ol>
 *         <li>Add <code>WIREFRAME_HTML_ZIP</code> constants.</li>
 *         <li>Update {@link #execute()} method to extra the zip containing the wireframe htmls.</li>
 *     </ol>
 * </p>
 *
 * <p>
 *     Version 1.2 (Release Assembly - TopCoder Direct Wireframe Viewer Bug Fixes v1.0) change notes:
 *     <ol>
 *         <li>Removed <code>WIREFRAME_HTML_ZIP</code> constant.</li>
 *         <li>Added {@link #WIREFRAME_HTML_ZIP_DIR}, {@link #WIREFRAME_HTML_ZIP_FILE_NAME},
 *         {@link #WIREFRAME_HTML_ZIP_FILE_SUFFIX}, {@link #WIREFRAME_HTML_INDEX_LOCATION_FILE},
 *         {@link #WIREFRAME_HTML_INDEX_FILE} constants.</li>
 *         <li>Removed field <code>wireframeIndexPage</code> because it will be set internally.</li>
 *         <li>Updated {@link #executeAction()} to use the new logic of extracting submission html files and
 *         finding the index page, and sending the location of index page to JSON result.</li>
 *         <li>Added {@link #findSubmissionEntry(ZipFile)} method to find the zip entry of the submission in the
 *         whole submission zip.</li>
 *         <li>Added {@link #searchFile(File, String)} to search file recursively in a directory.</li>
 *         <li>Updated {@link #retrieveWireframeSubmissionFile()} to return <code>File</code> instead
 *         of <code>FileInputStream</code></li>
 *     </ol>
 * </p>
 *
 * @author TCASSEMBLER
 * @version 1.2
 * @since Wireframe Viewer Modal Window Direct integration assembly v1.0
 */
public class ViewWireframeSubmissionAction extends BaseDirectStrutsAction {
    /**
     * Represents the direct name which contains the submission zip file.
     *
     * @since 1.2
     */
    private static final String WIREFRAME_HTML_ZIP_DIR = "submission" + File.separator;

    /**
     * Represents the default submission zip file name.
     *
     * @since 1.2
     */
    private static final String WIREFRAME_HTML_ZIP_FILE_NAME = "submission.zip";

    /**
     * Represents the suffix of the submission zip file name.
     *
     * @since 1.2
     */
    private static final String WIREFRAME_HTML_ZIP_FILE_SUFFIX = ".zip";

    /**
     * Represents the file name to store the location of the html index page.
     *
     * @since 1.2
     */
    private static final String WIREFRAME_HTML_INDEX_LOCATION_FILE = "index_location.txt";

    /**
     * Represents the index file of the wireframe html pages.
     *
     * @since 1.2
     */
    private static final String WIREFRAME_HTML_INDEX_FILE = "index.html";

    /**
     * Represent the logger for this class.
     */
    private Logger logger = Logger.getLogger(ViewWireframeSubmissionAction.class);

    /**
     * Represent the class name of the action.
     */
    private static final String CLASS_NAME = ViewWireframeSubmissionAction.class.getName();
    
     /**
     * <code>UserService</code> injected by Spring.
     */
    private UserService userService;


    /**
     * The wireframe submission can be access via "/direct/viewWireframePage/$submissionId/$page" 
     */
    private static final String VIEW_WIREFRAME_PAGE = "viewWireframePage";
    
    /**
     * The studio submission base to retrieve the wireframe submission. It's injected from spring configuration.
     */
    private static String studioSubmissionBase;

    /**
     * The directory to unzip the wireframe submission. It's injected from spring configuration.
     */
    private String wireframeDirectory;

    /**
     * The submission id of the wireframe submission. It's injected from request parameter.
     */
    private String submissionId;
    
     /**
     * <p>
     * Getter of userService field.
     * </p>
     * @return the userService
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * <p>
     * Setter of userService field.
     * </p>
     * @param userService the userService to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Empty constructor.
     */
    public ViewWireframeSubmissionAction() {
    }

    /**
     * Gets the studioSubmissionBase
     * 
     * @return the studioSubmissionBase service
     */
    public String getStudioSubmissionBase() {
        return studioSubmissionBase;
    }

    /**
     * Sets the studioSubmissionBase.
     * 
     * @param studioSubmissionBase
     *            the studioSubmissionBase
     */
    public void setStudioSubmissionBase(String studioSubmissionBase) {
        this.studioSubmissionBase = studioSubmissionBase;
    }

    /**
     * Gets the submission id.
     *
     * @return the submission id
     */
    public String getSubmissionId() {
        return submissionId;
    }

    /**
     * Setter for the submission id.
     *
     * @param submissionId the submission id.
     */
    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * Gets the wireframe directory.
     *
     * @return the wireframe directory
     */
    public String getWireframeDirectory() {
        return wireframeDirectory;
    }

    /**
     * Setter for the wireframe directory. (No need to validate).
     * 
     * #Param wireframeDirectory - the wireframe directory.
     * 
     * @param wireframeDirectory
     */
    public void setWireframeDirectory(String wireframeDirectory) {
        this.wireframeDirectory = wireframeDirectory;
    }

    /**
     * It will retrieve the wireframe submission and unzip it to the wireframe directory. The extraction directory 
     * structure is like: wireframeDirectory -- submissionId ---- wireframe pages (e.g. index.html)
     *  
     * <p>If there is already a submissionId sub-directory in wireframeDirectory, do nothing.</p>
     *
     * <p>The location of index page will be set in the result, so it will be sending to client as JSON result.</p>
     *
     * @throws Exception if any error occurs.
     */
    @Override
    public void executeAction() throws Exception {
        try {
            // the extract directory structure is like: wireframeDirectory -- submissionId ---- wireframe pages
            String extractDirectory = wireframeDirectory + File.separator + submissionId;

            // if there is already a submissionId sub-directory in wireframeDirectory, do not extract.
            File file = new File(wireframeDirectory, submissionId);
            if (!file.exists()) {
                File uploadedFile = retrieveWireframeSubmissionFile();
                ZipFile zipFile = new ZipFile(uploadedFile);
                InputStream zipIs = null;
                try {
                    ZipEntry targetEntry = findSubmissionEntry(zipFile);
                    if (targetEntry == null) {
                        throw new DirectException("Can't find the submission zip file in the submission");
                    }
                    zipIs = zipFile.getInputStream(targetEntry);
                    int countEntry = DirectUtils.storeZipStream(zipIs, extractDirectory);
                    logger.info(countEntry + " files extracted to " + extractDirectory);

                    String indexFile = searchFile(new File(extractDirectory), WIREFRAME_HTML_INDEX_FILE);
                    if (indexFile == null) {
                        throw new DirectException("Can't find the index page in the submission");
                    }
                    FileUtils.writeStringToFile(new File(extractDirectory, WIREFRAME_HTML_INDEX_LOCATION_FILE),
                            indexFile);
                } finally {
                    try {
                        zipFile.close();
                    } catch (IOException e) {
                        // ignore
                    }
                    if (zipIs != null) {
                        try {
                            zipIs.close();
                        } catch (IOException e) {
                            // ignore
                        }
                    }
                }
            }

            // create a link named submissionId in path "webRoot + File.separator + VIEW_WIREFRAME_PAGE + File.separator"
            // this link links to extractDirectory
            String webRoot = ServletActionContext.getServletContext().getRealPath("");
            String linkParent = webRoot + File.separator + VIEW_WIREFRAME_PAGE + File.separator;

            // if the link exist, do not create
            File linkFile = new File(linkParent, submissionId);
            if (!linkFile.exists()) {
                String linkPath = linkParent + submissionId;
                String cmd = "ln -s " + extractDirectory + " " + linkPath;
                Runtime.getRuntime().exec(cmd).waitFor();
            }

            File indexLocation = new File(extractDirectory, WIREFRAME_HTML_INDEX_LOCATION_FILE);
            if (!indexLocation.exists()) {
                throw new DirectException("Can't find the index page in the submission");
            }
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("indexPage", "/viewWireframePage/" + submissionId + "/" +
                    FileUtils.readFileToString(indexLocation));
            setResult(result);
        } catch (Exception e) {
            logger.error("Error when executing action : " + CLASS_NAME + " : " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Search a file in a directory. The sub-directories will also be searched recursively.
     * The file name matching will be case-insensitive. The relative path of the target file will be returned.
     *
     * @param dir the directory in which the file will be searched.
     * @param fileName the file name to search.
     * @return the relative path of the target file, null if can't find the file.
     * @since 1.2
     */
    private static String searchFile(File dir, String fileName) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().equalsIgnoreCase(fileName)) {
                return file.getName();
            }
        }
        for (File file : files) {
            if (file.isDirectory()) {
                String result = searchFile(file, fileName);
                if (result != null) {
                    return file.getName() + File.separator + result;
                }
            }
        }
        return null;
    }

    /**
     * Find the zip entry of the submission zip file in the whole submission.
     *
     * @param zipFile the zip file of the whole submission.
     * @return the zip entry of the submission zip file, null if not found.
     * @since 1.2
     */
    private static ZipEntry findSubmissionEntry(ZipFile zipFile) {
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        ZipEntry targetEntry = null;
        String fileName = WIREFRAME_HTML_ZIP_DIR + WIREFRAME_HTML_ZIP_FILE_NAME;
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                continue;
            }
            if (entry.getName().equalsIgnoreCase(fileName)) {
                targetEntry = entry;
                break;
            } else if (targetEntry == null && entry.getName().toLowerCase().startsWith(WIREFRAME_HTML_ZIP_DIR)
                    && entry.getName().toLowerCase().endsWith(WIREFRAME_HTML_ZIP_FILE_SUFFIX)) {
                targetEntry = entry;
            }
        }
        return targetEntry;
    }

    /**
     * Using file uploader to get the wireframe submission. 
     * 
     * @return the wireframe submission file
     * @throws Exception if any error occurs.
     */
    private File retrieveWireframeSubmissionFile() throws Exception {
        
        ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(
            "com.topcoder.util.objectfactory"));
        UploadManager uploadManager = (UploadManager) objectFactory.createObject("uploadManagerKey");

        Filter filter = SubmissionFilterBuilder.createSubmissionIdFilter(Long.parseLong(submissionId));
        Submission[] submissions = uploadManager.searchSubmissions(filter);
        if (submissions.length == 0) {
            throw new Exception("Cannot find submission " + submissionId);
        }
        Submission submission = submissions[0];
        if (submission == null) {
            throw new Exception("Cannot find submission " + submissionId);
        }
        Upload upload = submission.getUpload();
        
        long projectId = upload.getProject();
        long submitterId = Long.parseLong(submission.getCreationUser());
        String handle = getUserService().getUserHandle(submitterId);
        
        return new File(studioSubmissionBase + File.separator + projectId + File.separator
                        + handle.toLowerCase() + "_" + submitterId + File.separator
                        + submission.getUpload().getParameter());
    }
}
