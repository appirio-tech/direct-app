/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;

/**
 * <p>A form bean providing the data submitted by user for creating/updating a JIRA issue.</p>
 *
 * Version 1.1 (TC Direct Issue Tracking Tab Update Assembly 3) change notes:
 *   <ol>
 *     <li>Added {@link #projectBug} fields.
 *     Also the getters/setters were added.</li>
 *   </ol>
 * </p>
 *
 * @author xjtufreeman, TCSASSEMBER
 * @version 1.1
 */
public class JIRAIssueForm implements Serializable {
    /**
     * <p>Represents the serial version unique id.</p>
     */
    private static final long serialVersionUID = -435431124446L;

    /**
     * <p>Represents the id of the JIRA issue.</p>
     */
    private String issueId;
    
    /**
     * <p>Represents the name of the JIRA issue.</p>
     */
    private String name;
    
    /**
     * <p>Represents the environment of the JIRA issue.</p>
     */
    private String environment;
    
    /**
     * <p>Represents the description of the JIRA issue.</p>
     */
    private String description;
    
    /**
     * <p>Represents the first place payment of the JIRA issue.</p>
     */
    private float firstPlacePayment;
    
    /**
     * <p>Represents the payment status of the JIRA issue.</p>
     */
    private String paymentStatus;
    
    /**
     * <p>Represents the TCO points of the JIRA issue.</p>
     */
    private int tcoPoints;
    
    /**
     * <p>Represents whether the issue is CCA only.</p>
     */
    private boolean cca;
    
    /**
     * <p>Represents the bug type.<p>
     */
    private String type;

    /**
     * <p>Represents whether the bug is a project type bug.<p>
     *
     * @since 1.1
     */
    private boolean projectBug;
    
    /**
     * <p>Empty constructor.</p>
     */
    public JIRAIssueForm() {
        
    }

    /**
     * <p>Gets the id of the issue.</p>
     * 
     * @return the id of the issue.
     */
    public String getIssueId() {
        return issueId;
    }

    /**
     * <p>Sets the id of the issue.</p>
     * 
     * @param issueId the id of the issue.
     */
    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    /**
     * <p>Gets the name of the issue.</p>
     * 
     * @return the name of the issue.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets the name of the issue.</p>
     * 
     * @param name the name of the issue.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Gets the environment of the issue.</p>
     * 
     * @return the environment of the issue.
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * <p>Sets the environment of the issue.</p>
     * 
     * @param environment the environment of the issue.
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * <p>Gets the description of the issue.</p>
     * 
     * @return the description of the issue.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Sets the description of the issue.</p>
     * 
     * @param description the description of the issue.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>Gets the first place payment of the issue.</p>
     * 
     * @return the first place payment of the issue
     */
    public float getFirstPlacePayment() {
        return firstPlacePayment;
    }

    /**
     * <p>Sets the first place payment of the issue.</p>
     * 
     * @param firstPlacePayment the first place payment of the issue.
     */
    public void setFirstPlacePayment(float firstPlacePayment) {
        this.firstPlacePayment = firstPlacePayment;
    }

    /**
     * <p>Gets the payment status of the issue.</p>
     * 
     * @return the payment status of the issue.
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * <p>Sets the payment status of the issue.</p>
     * 
     * @param paymentStatus the payment status of the issue.
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * <p>Gets the TCO points of the issue.</p>
     * 
     * @return the tcoPoints the TCO points of the issue.
     */
    public int getTcoPoints() {
        return tcoPoints;
    }

    /**
     * <p>Sets the TCO points of the issue.</p>
     * 
     * @param tcoPoints the TCO points of the issue.
     */
    public void setTcoPoints(int tcoPoints) {
        this.tcoPoints = tcoPoints;
    }

    /**
     * <p>Gets the flag indicates whether the issue is CCA only.</p>
     * 
     * @return the true if the issue is CCA only, false otherwise.
     */
    public boolean isCca() {
        return cca;
    }

    /**
     * <p>Sets the flag indicates whether the issue is CCA only.</p>
     * 
     * @param cca true if the issue is CCA only, false otherwise.
     */
    public void setCca(boolean cca) {
        this.cca = cca;
    }

    /**
     * <p>Gets the bug type.</p>
     * 
     * @return the bug type.
     */
    public String getType() {
        return type;
    }

    /**
     * <p>Sets the bug type.</p>
     * 
     * @param type the bug type to set
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * <p>Gets the flag indicates whether bug is a project bug.</p>
     *
     * @return the true if the bug is a project bug, false otherwise.
     * @since 1.1
     */
    public boolean isProjectBug() {
        return projectBug;
    }

    /**
     * <p>Sets the flag indicates whether the bug is a project bug.</p>
     *
     * @param projectBug true if the bug is a project bug, false otherwise
     * @since 1.1
     */
    public void setProjectBug(boolean projectBug) {
        this.projectBug = projectBug;
    }
}
