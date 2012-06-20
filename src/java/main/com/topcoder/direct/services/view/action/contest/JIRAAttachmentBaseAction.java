/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionFileStore;
import com.topcoder.project.phases.Phase;

/**
 * <p>This class is a abstract class which can be used to upload attachments to JIRA issue.</p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
public abstract class JIRAAttachmentBaseAction extends StudioOrSoftwareContestAction {
    /**
     * <p>Represents the serial version unique id.</p>
     */
    private static final long serialVersionUID = 35643521124334L;

    /**
     * <p>Represents the directory location where the submissions stored. It will be injected
     * by Spring IoC.</p>
     */
    private String fileLocation;
    
    /**
     * <p>Represents whether to use final fix as the attachment.</p>
     */
    private boolean finalfix;
    
    /**
     * <p>Represents the attachment ids.</p>
     */
    private String attachmentIds;
    
    /**
     * <p>Sets the flag indicates whether to use final fix as the attachment.</p>
     * 
     * @param finalfix the flag indicates whether to use final fix as the attachment.
     */
    public void setFinalfix(boolean finalfix) {
        this.finalfix = finalfix;
    }

    /**
     * <p>Sets the attachment ids.</p>
     * 
     * @param attachmentIds the attachment ids to set
     */
    public void setAttachmentIds(String attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    /**
     * <p>Sets the directory location where the submissions stored.</p>
     * 
     * @param fileLocation the directory location where the submissions stored.
     */
    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    /**
     * <p>Empty constructor.</p>
     */
    public JIRAAttachmentBaseAction() {
        
    }
    
    /**
     * <p>Handles the incoming request. It will upload the corresponding attachments to an issue.</p>
     *
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    public void executeAction() throws Exception {
        SessionFileStore fileStore = new SessionFileStore(DirectUtils.getServletRequest().getSession(true));
        if (finalfix) {
            Phase lastClosedFinalFixPhase = DirectUtils.getLastClosedFinalFixPhase(getProjectServices(), getProjectId());
            
            if (lastClosedFinalFixPhase != null) {
                DirectUtils.addFinalFixToIssue(getIssueKey(),
                        DirectUtils.getFinalFixUpload(getProjectId(), lastClosedFinalFixPhase.getId(), getContestServiceFacade()),
                        fileLocation);
            }
        } else {
            DirectUtils.addAttachmentsToIssue(getIssueKey(), fileStore, attachmentIds.split(","));
        }
        fileStore.getFileMap().clear();
    }
    
    /**
     * <p>Gets the corresponding issue key.</p>
     * 
     * @return the corresponding issue key.
     */
    protected abstract String getIssueKey();
}
