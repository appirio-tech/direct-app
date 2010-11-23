/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.copilot;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.topcoder.direct.services.view.dto.CommonDTO;

/**
 * <p>
 * A DTO used to store information for copilot manage action.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since TC Direct Manage Copilots Assembly
 */
public class CopilotManageDTO extends CommonDTO implements Serializable {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -6357761681786991407L;

    /**
     * A map stores information of each project.
     */
    private Map<Long, CopilotProjectDTO> copilotProjects;

    /**
     * A list stores all copilots' information.
     */
    private List<CopilotBriefDTO> copilots;

    /**
     * Retrieves the copilotProjects field.
     * 
     * @return the copilotProjects
     */
    public Map<Long, CopilotProjectDTO> getCopilotProjects() {
        return copilotProjects;
    }

    /**
     * Sets the copilotProjects field.
     * 
     * @param copilotProjects
     *            the copilotProjects to set
     */
    public void setCopilotProjects(Map<Long, CopilotProjectDTO> copilotProjects) {
        this.copilotProjects = copilotProjects;
    }

    /**
     * Retrieves the copilots field.
     * 
     * @return the copilots
     */
    public List<CopilotBriefDTO> getCopilots() {
        return copilots;
    }

    /**
     * Sets the copilots field.
     * 
     * @param copilots
     *            the copilots to set
     */
    public void setCopilots(List<CopilotBriefDTO> copilots) {
        this.copilots = copilots;
    }

}
