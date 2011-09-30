/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.participationreport;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.ReportType;

/**
 * <p>
 * The DTO to store the participation metrics report data. It includes basic metrics report data and 4 aggregation
 * participation metrics report data:
 * <ol>
 *   <li>project aggregation participation metrics report</li>
 *   <li>billing aggregation participation metrics report</li>
 *   <li>contest status aggregation participation metrics report</li>
 *   <li>contest type aggregation participation metrics report</li>
 * </ol>
 * </p>
 *
 * @author TCSASSEMBER
 * @version  1.0 (TC Cockpit Participation Metrics Report Part One Assembly 1)
 */
public class ParticipationReportDTO extends CommonDTO implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -3985093109381162149L;

    /**
     * The map stores the direct project information.
     */
    private Map<Long, String> projectsLookupMap;

    /**
     * <p>A <code>Map</code> providing the mapping from IDs to names of client billing projects.</p>
     */
    private Map<Long, String> clientBillingProjects;
    
    /**
     * <p>A <code>Map</code> providing the mapping from IDs to names of available client accounts.</p>
     */
    private Map<Long, String> clientAccounts;

    /**
     * <p>A <code>Map</code> providing the mapping from project category IDs to names.</p>
     */
    private Map<Long, String> projectCategories;
    
    /**
     * <p>A <code>Map</code> providing the mapping from status id to status names.</p>
     */
    private Map<Long, String> contestStatus;
    
    /**
     * The basic participation metrics report.
     */
    private ParticipationBasicReportDTO participationBasicReport;
    
    /**
     * The project aggregation participation metrics report data.
     */
    private List<ParticipationAggregationReportDTO> projectAggregation;

    /**
     * The contest type aggregation participation metrics report data.
     */
    private List<ParticipationAggregationReportDTO> contestTypeAggregation;

    /**
     * The status aggregation participation metrics report data.
     */
    private List<ParticipationAggregationReportDTO> statusAggregation;
    
    /**
     * The billing account aggregation participation metrics report data.
     */
    private List<ParticipationAggregationReportDTO> billingAggregation;
    
    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the participation metrics report data is
     * to be calculated and displayed.</p>
     */
    private boolean showJustForm;
    
    /**
     * Empty constructor.
     */
    public ParticipationReportDTO() {
        
    }

    /**
     * Gets the report type of this report DTO. It simply returns ReportType.PARTICIPATION.
     *
     * @return the the cost report type.
     */
    public ReportType getReportType() {
        return ReportType.PARTICIPATION;
    }

    /**
     * Gets the projects lookup map which stores the direct project information.
     *
     * @return the projects lookup map.
     */
    public Map<Long, String> getProjectsLookupMap() {
        return projectsLookupMap;
    }

    /**
     * Sets the project lookup map.
     *
     * @param projectsLookupMap the projects lookup map to set.
     */
    public void setProjectsLookupMap(Map<Long, String> projectsLookupMap) {
        this.projectsLookupMap = projectsLookupMap;
    }

    /**
     * Gets the client billing projects.
     *
     * @return the client billing projects.
     */
    public Map<Long, String> getClientBillingProjects() {
        return clientBillingProjects;
    }

    /**
     * Sets the client billing projects.
     *
     * @param clientBillingProjects the client billing projects to set.
     */
    public void setClientBillingProjects(Map<Long, String> clientBillingProjects) {
        this.clientBillingProjects = clientBillingProjects;
    }

    /**
     * Gets the client accounts.
     *
     * @return the client accounts.
     */
    public Map<Long, String> getClientAccounts() {
        return clientAccounts;
    }

    /**
     * Sets the client accounts.
     *
     * @param clientAccounts the client accounts to set.
     */
    public void setClientAccounts(Map<Long, String> clientAccounts) {
        this.clientAccounts = clientAccounts;
    }

    /**
     * Gets the project categories.
     *
     * @return the project categories.
     */
    public Map<Long, String> getProjectCategories() {
        return projectCategories;
    }

    /**
     * Sets the project categories.
     *
     * @param projectCategories the project categories to set.
     */
    public void setProjectCategories(Map<Long, String> projectCategories) {
        this.projectCategories = projectCategories;
    }

    /**
     * Gets the basic participation metrics report.
     * 
     * @return the basic participation metrics report.
     */
    public ParticipationBasicReportDTO getParticipationBasicReport() {
        return participationBasicReport;
    }

    /**
     * Sets the basic participation metrics report.
     * 
     * @param participationBasicReport the basic participation metrics report.
     */
    public void setParticipationBasicReport(ParticipationBasicReportDTO participationBasicReport) {
        this.participationBasicReport = participationBasicReport;
    }

    /**
     * Gets the flag to determine whether only show the form.
     *
     * @return the flag to determine whether only show the form.
     */
    public boolean isShowJustForm() {
        return showJustForm;
    }

    /**
     * Sets the flag of whether only show the form.
     *
     * @param showJustForm flag to determine whether only show the form.
     */
    public void setShowJustForm(boolean showJustForm) {
        this.showJustForm = showJustForm;
    }

    /**
     * Gets the contest status.
     *
     * @return the contest status.
     */
    public Map<Long, String> getContestStatus() {
        return contestStatus;
    }

    /**
     * Sets the contest status.
     *
     * @param contestStatus the contest status to set.
     */
    public void setContestStatus(Map<Long, String> contestStatus) {
        this.contestStatus = contestStatus;
    }

    /**
     * Gets the project aggregation participation metrics report data.
     * 
     * @return the project aggregation participation metrics report data.
     */
    public List<ParticipationAggregationReportDTO> getProjectAggregation() {
        return projectAggregation;
    }

    /**
     * Sets the project aggregation participation metrics report data.
     * 
     * @param projectAggregation the project aggregation participation metrics report data.
     */
    public void setProjectAggregation(List<ParticipationAggregationReportDTO> projectAggregation) {
        this.projectAggregation = projectAggregation;
    }

    /**
     * Gets the contest type aggregation participation metrics report data.
     * 
     * @return the contest type aggregation participation metrics report data.
     */
    public List<ParticipationAggregationReportDTO> getContestTypeAggregation() {
        return contestTypeAggregation;
    }

    /**
     * Sets the contest type aggregation participation metrics report data.
     * 
     * @param contestTypeAggregation the contest type aggregation participation metrics report data.
     */
    public void setContestTypeAggregation(List<ParticipationAggregationReportDTO> contestTypeAggregation) {
        this.contestTypeAggregation = contestTypeAggregation;
    }

    /**
     * Gets the status aggregation participation metrics report data.
     * 
     * @return the status aggregation participation metrics report data.
     */
    public List<ParticipationAggregationReportDTO> getStatusAggregation() {
        return statusAggregation;
    }

    /**
     * Sets the status aggregation participation metrics report data.
     * 
     * @param statusAggregation the status aggregation participation metrics report data.
     */
    public void setStatusAggregation(List<ParticipationAggregationReportDTO> statusAggregation) {
        this.statusAggregation = statusAggregation;
    }

    /**
     * Gets the billing aggregation participation metrics report data.
     * 
     * @return the billing aggregation participation metrics report data.
     */
    public List<ParticipationAggregationReportDTO> getBillingAggregation() {
        return billingAggregation;
    }

    /**
     * Sets the billing aggregation participation metrics report data.
     * 
     * @param billingAggregation the billing aggregation participation metrics report data.
     */
    public void setBillingAggregation(List<ParticipationAggregationReportDTO> billingAggregation) {
        this.billingAggregation = billingAggregation;
    }
}
