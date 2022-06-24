package com.topcoder.management.project;

import java.io.Serializable;


/**
 * <p>
 * This data represent the design components.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe as it has mutable state.
 * </p>
 *
 * @author COCKPITASSEMBLIER
 * @version 1.0
 */
public class DesignComponents implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6991488651979864256L;

    /**
     * Represents the project id.
     */
    private Long projectId;

    /**
     * Represents the permission for contest.
     */
    private String cperm;

    /**
     * Represents the permissionfor project.
     */
    private String pperm;

    /** 
     * Represents the displayed text. 
     */
    private String displayText;
    

    /**
     * Get project id
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the project id attribute of the CommonProjectContestData entity
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Set project id
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param projectId
     *            the project id attribute of the CommonProjectContestData
     *            entity
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Get text
     * 
     * @return the text attribute of the CommonProjectContestData entity
     */
    public String getText() {
        return displayText;
    }

    /**
     * Set text
     * 
     * @param displayText
     *            the text attribute of the CommonProjectContestData entity
     */
    public void setText(String displayText) {
        this.displayText = displayText;
    }


    /**
     * Get cperm
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the cperm attribute of the CommonProjectContestData entity
     */
    public String getCperm() {
        return cperm;
    }

    /**
     * Set cperm
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param cperm
     *            the cperm attribute to set to the CommonProjectContestData
     *            entity
     */
    public void setCperm(String cperm) {
        this.cperm = cperm;
    }

    /**
     * Get pperm
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the pperm attribute of the CommonProjectContestData entity
     */
    public String getPperm() {
        return pperm;
    }

    /**
     * Set pperm
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param pperm
     *            the pperm attribute to set to the CommonProjectContestData
     *            entity
     */
    public void setPperm(String pperm) {
        this.pperm = pperm;
    }
}
