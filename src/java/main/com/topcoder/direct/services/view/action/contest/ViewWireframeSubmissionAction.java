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

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.user.UserService;
import com.topcoder.servlet.request.LocalFileUpload;
import com.topcoder.servlet.request.UploadedFile;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;


/**
 * This struts action extends the AbstractAction, and it's used to unzip the wireframe submission into a local
 * directory, and then redirect user to the index page of the wireframe.
 * 
 * Thread-safety: It's not thread-safe.
 *
 * <p> Version 1.1 (TC-Studio - Wireframe Viewer Modal Window Direct Updates assembly v1.0) change notes:
 *   <ol>
 *     <li>Add {@link #WIREFRAME_HTML_ZIP} constants.</li>
 *     <li>Update {@link #execute()} method to extra the zip containing the wireframe htmls.</li>
 *   </ol>
 * </p>
 *
 * @author TCASSEMBLER
 * @version 1.1
 * @since Wireframe Viewer Modal Window Direct integration assembly v1.0
 */
public class ViewWireframeSubmissionAction extends AbstractAction {
    /**
     * Represents the zip file containing the wireframe htmls in the submission.
     *
     * @since 1.1
     */
    private static final String WIREFRAME_HTML_ZIP = "submission/submission.zip";

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
     * The index page name of the wireframe.
     */
    private String wireframeIndexPage;

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
     * Gets the wireframe index page.
     *
     * @return the wireframe index page
     */
    public String getWireframeIndexPage() {
        return wireframeIndexPage;
    }

    /**
     * Setter for the wireframe index page name. (No need to validate).
     * 
     * #Param wireframeIndexPage - the wireframe index page name.
     * 
     * @param wireframeIndexPage
     */
    public void setWireframeIndexPage(String wireframeIndexPage) {
        this.wireframeIndexPage = wireframeIndexPage;
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
     * If there is already a submissionId sub-directory in wireframeDirectory, do nothing. 
     * 
     * @return a <code>String</code> referencing the next view or action to route request to. This implementation
     *          returns {@link #SUCCESS} always. In result, it is configured to redirect to
     *          viewWireframePage/$submission/$wireframeIndexPage        
     * @throws Exception if any error occurs.
     */
    @Override
    public String execute() throws Exception {
        try {
            String result = super.execute();
            if (SUCCESS.equals(result)) {
                // the extract directory structure is like: wireframeDirectory -- submissionId ---- wireframe pages
                String extractDirectory = wireframeDirectory + File.separator + submissionId;
                
                // if there is already a submissionId sub-directory in wireframeDirectory, do not extract. 
                File file = new File(wireframeDirectory, submissionId);
                if (!file.exists()) {
                    FileInputStream uploadedFile = retrieveWireframeSubmissionFile();
                    ZipInputStream zis = new ZipInputStream(uploadedFile);
                    try {
                        ZipEntry entry = zis.getNextEntry();
                        boolean found = false;
                        while (entry != null) {
                            if (entry.getName().equalsIgnoreCase(WIREFRAME_HTML_ZIP)) {
                                found = true;
                                break;
                            }
							entry = zis.getNextEntry();
                        }
                        if (!found) {
                            throw new DirectException("Cann't find " + WIREFRAME_HTML_ZIP + " in the submission");
                        }
                        int countEntry = DirectUtils.storeZipStream(zis, extractDirectory);
                        logger.info(countEntry + " files extracted to " + extractDirectory);
                    } finally {
                        try {
                            zis.close();
                        } catch (IOException e) {
                            // ignore
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

                return SUCCESS;
            } else {
                return result;
            }
        } catch (Exception e) {
            logger.error("Error when executing action : " + CLASS_NAME + " : " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Using file uploader to get the wireframe submission. 
     * 
     * @return the wireframe submission file
     * @throws Exception if any error occurs.
     */
    private FileInputStream retrieveWireframeSubmissionFile() throws Exception {
        
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

		
        File file = new File(studioSubmissionBase + File.separator + projectId + File.separator
                        + handle.toLowerCase() + "_" + submitterId + File.separator
                        + submission.getUpload().getParameter());
        FileInputStream is = new FileInputStream(file);
				
        return is;
    }
	
	
}
