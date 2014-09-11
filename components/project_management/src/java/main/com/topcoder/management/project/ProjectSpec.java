package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This class is used by ProjectPersistence implementors to persist Project's specifications to the project_spec table.
 * A project spec has detailed requirements, submission deliverables, environment setup instructions, final submission
 * guidelines.
 * </p>
 * 
 * <p>
 * Version 1.0.1 (TC Direct - Launch Copilot Selection Contest assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #privateDescription} field and corresponding get/set methods.</li>
 *   </ol>
 * </p>
 * 
 * @author TCSASSEMBLER
 * @since Cockpit Launch Contest - Update for Spec Creation v1.0
 * @version 1.0.1
 */
public class ProjectSpec extends AuditableObject implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the id of this instance. Only values greater than or equal to zero is allowed. This variable is
     * initialized in the constructor and can be accessed in the corresponding getter/setter method.
     */
    private long projectSpecId = 0;

    /**
     * Represents the id of the parent project. Only values greater than or equal to zero is allowed. This variable is
     * initialized in the constructor and can be accessed in the corresponding getter/setter method.
     */
    private long projectId = 0;

    /**
     * Represents the version number of the project specs. On project create version number gets initialized to 1. On
     * each update of the project, version number gets incremented by 1.
     */
    private long version = 0;

    /**
     * Represents the detailed requirements for the project.
     */
    private String detailedRequirements;

    /**
     * Represents the submission deliverables for the project.
     */
    private String submissionDeliverables;

    /**
     * Represents the environment setup instructions.
     */
    private String environmentSetupInstructions;

    /**
     * Represents the final submission guidelines.
     */
    private String finalSubmissionGuidelines;
    
    /**
     * Represents the private description.
     * 
     * @since 1.0.1
     */
    private String privateDescription;

    /**
     * <p>
     * Gets the id of this project spec instance.
     * </p>
     * 
     * @return the id of this project spec instance.
     */
    public long getProjectSpecId() {
        return projectSpecId;
    }

    /**
     * <p>
     * Sets the id of this project spec instance.
     * </p>
     * 
     * @param id
     *            the id of this project spec instance.
     */
    public void setProjectSpecId(long id) {
        this.projectSpecId = id;
    }

    /**
     * <p>
     * Gets the id of the parent project.
     * </p>
     * 
     * @return the id of the parent project.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets the id of the parent project.
     * </p>
     * 
     * @param id
     *            the id of the parent project.
     */
    public void setProjectId(long id) {
        this.projectId = id;
    }

    /**
     * <p>
     * Gets the version number of this project spec instance.
     * </p>
     * 
     * @return the version number of this project spec instance.
     */
    public long getVersion() {
        return version;
    }

    /**
     * <p>
     * Sets the version number of this project spec instance.
     * </p>
     * 
     * @param version
     *            the version number of this project spec instance.
     */
    public void setVersion(long version) {
        this.version = version;
    }

    /**
     * <p>
     * Gets the detailed requirements of this instance parent Project
     * </p>
     * 
     * @return the detailed requirements of this instance parent Project
     */
    public String getDetailedRequirements() {
        return detailedRequirements;
    }

    /**
     * <p>
     * Sets the detailed requirements of this instance parent Project
     * </p>
     * 
     * @param detailedRequirements
     *            the detailed requirements of this instance parent Project
     */
    public void setDetailedRequirements(String detailedRequirements) {
        this.detailedRequirements = detailedRequirements;
    }

    /**
     * <p>
     * Gets the submission deliverables of this instance parent Project
     * </p>
     * 
     * @return the submission deliverables of this instance parent Project
     */
    public String getSubmissionDeliverables() {
        return submissionDeliverables;
    }

    /**
     * <p>
     * Sets the submission deliverables of this instance parent Project
     * </p>
     * 
     * @param submissionDeliverables
     *            the submission deliverables of this instance parent Project
     */
    public void setSubmissionDeliverables(String submissionDeliverables) {
        this.submissionDeliverables = submissionDeliverables;
    }

    /**
     * <p>
     * Gets the environment setup instructions of this instance parent Project
     * </p>
     * 
     * @return the environment setup instructions of this instance parent Project
     */
    public String getEnvironmentSetupInstructions() {
        return environmentSetupInstructions;
    }

    /**
     * <p>
     * Sets the environment setup instructions of this instance parent Project
     * </p>
     * 
     * @param environmentSetupInstructions
     *            the environment setup instructions of this instance parent Project
     */
    public void setEnvironmentSetupInstructions(String environmentSetupInstructions) {
        this.environmentSetupInstructions = environmentSetupInstructions;
    }

    /**
     * <p>
     * Gets the final submission guidelines of this instance parent Project
     * </p>
     * 
     * @return the final submission guidelines of this instance parent Project
     */
    public String getFinalSubmissionGuidelines() {
        return finalSubmissionGuidelines;
    }

    /**
     * <p>
     * Sets the final submission guidelines of this instance parent Project
     * </p>
     * 
     * @param finalSubmissionGuidelines
     *            the final submission guidelines of this instance parent Project
     */
    public void setFinalSubmissionGuidelines(String finalSubmissionGuidelines) {
        this.finalSubmissionGuidelines = finalSubmissionGuidelines;
    }

    /**
     * Retrieves the private description field.
     *
     * @return the private description
     * @since 1.0.1
     */
    public String getPrivateDescription() {
        return privateDescription;
    }

    /**
     * Sets the private description field.
     *
     * @param privateDescription the private description to set
     * @since 1.0.1
     */
    public void setPrivateDescription(String privateDescription) {
        this.privateDescription = privateDescription;
    }
}
