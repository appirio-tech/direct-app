/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.contest.ContestType;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineNumericalFilterType;

import java.io.Serializable;

/**
 * <p>A form bean providing the data submitted by user for filtering the pipeline report records.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (Direct Pipeline Integration Assembly)
 */
public class DashboardPipelineReportForm implements Serializable {

    /**
     * <p>A <code>ContestType</code> array listing the contest types to be selected by default.</p>
     */
    private static final ContestType[] DEFAULT_CONTEST_TYPES_SELECTION
        = new ContestType[] {ContestType.STUDIO, ContestType.CONCEPTUALIZATION, ContestType.SPECIFICATION,
                             ContestType.ARCHITECTURE, ContestType.COMPONENT_DESIGN, ContestType.COMPONENT_DEVELOPMENT,
                             ContestType.RIA_COMPONENT, ContestType.RIA_BUILD, ContestType.UI_PROTOTYPE,
                             ContestType.ASSEMBLY, ContestType.TEST_SUITES, ContestType.TEST_SCENARIOS};

    /**
     * <p>A <code>ContestStatus</code> array listing the statuses types to be selected by default.</p>
     */
    private static final ContestStatus[] DEFAULT_CONTEST_STATUSES_SELECTION
        = new ContestStatus[] {ContestStatus.DRAFT, ContestStatus.SCHEDULED, ContestStatus.ACTIVE,
                               ContestStatus.COMPLETED, ContestStatus.CANCELLED};

    /**
     * <p>A <code>boolean</code> flag indicating whether the retrieved data is expected to be converted into
     * <code>Excel</code> format or not.</p>
     */
    private boolean excel;

    /**
     * <p>A <code>ContestType[]</code> providing the contest types to be used for filtering the contests.</p>
     */
    private ContestType[] contestTypes = DEFAULT_CONTEST_TYPES_SELECTION;

    /**
     * <p>A <code>ContestStatus[]</code> providing the contest statuses to be used for filtering the contests.</p>
     */
    private ContestStatus[] contestStatuses = DEFAULT_CONTEST_STATUSES_SELECTION;

    /**
     * <p>A <code>String[]</code> providing the client names to be used for filtering the contests.</p>
     */
    private String[] clients;

    /**
     * <p>The startDate field.</p>
     */
    private String startDate;

    /**
     * <p>The endDate field.</p>
     */
    private String endDate;

    /**
     * <p>A <code>Double</code> providing the minimum value for numerical filter.</p>
     */
    private Double numericalFilterMinValue;

    /**
     * <p>A <code>Double</code> providing the maximum value for numerical filter.</p>
     */
    private Double numericalFilterMaxValue;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether reposted contests are to be included into report
     * or not.</p>
     */
    private boolean showReposts = true;

    /**
     * <p>A <code>PipelineNumericalFilterType</code> providing the type of numerical filter to filter the pipeline data
     * for.</p>
     */
    private PipelineNumericalFilterType numericalFilterType;

    /**
     * <p>Constructs new <code>DashboardPipelineReportForm</code> instance. This implementation does nothing.</p>
     */
    public DashboardPipelineReportForm() {
    }

    /**
     * <p>Gets the flag indicating whether the retrieved data is expected to be converted into <code>Excel</code> format
     * or not.</p>
     *
     * @return <code>true</code> if registrants list is to be converted into <code>Excel</code> format;
     *         <code>false</code> otherwise.
     */
    public boolean isExcel() {
        return this.excel;
    }

    /**
     * <p>Sets the flag indicating whether the retrieved data is expected to be converted into <code>Excel</code> format
     * or not.</p>
     *
     * @param excel <code>true</code> if registrants list is to be converted into <code>Excel</code> format;
     *        <code>false</code> otherwise.
     */
    public void setExcel(boolean excel) {
        this.excel = excel;
    }

    /**
     * <p>Gets the contest types to be used for filtering the contests.</p>
     *
     * @return a <code>ContestType[]</code> providing the contest types to be used for filtering the contests.
     */
    public ContestType[] getContestTypes() {
        return this.contestTypes;
    }

    /**
     * <p>Sets the contest types to be used for filtering the contests.</p>
     *
     * @param contestTypes a <code>ContestType[]</code> providing the contest types to be used for filtering the
     *                     contests.
     */
    public void setContestTypes(ContestType[] contestTypes) {
        this.contestTypes = contestTypes;
    }

    /**
     * <p>Gets the contest statuses to be used for filtering the contests.</p>
     *
     * @return a <code>ContestStatus[]</code> providing the contest statuses to be used for filtering the contests.
     */
    public ContestStatus[] getContestStatuses() {
        return this.contestStatuses;
    }

    /**
     * <p>Sets the contest statuses to be used for filtering the contests.</p>
     *
     * @param contestStatuses a <code>ContestStatus[]</code> providing the contest statuses to be used for filtering the
     *                        contests.
     */
    public void setContestStatuses(ContestStatus[] contestStatuses) {
        this.contestStatuses = contestStatuses;
    }

    /**
     * <p>Gets the client names to be used for filtering the contests.</p>
     *
     * @return a <code>String[]</code> providing the client names to be used for filtering the contests.
     */
    public String[] getClients() {
        return this.clients;
    }

    /**
     * <p>Sets the client names to be used for filtering the contests.</p>
     *
     * @param clients a <code>String[]</code> providing the client names to be used for filtering the contests.
     */
    public void setClients(String[] clients) {
        this.clients = clients;
    }

    /**
     * <p>Sets the <code>startDate</code> field value.</p>
     *
     * @param startDate the value to set.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>Gets the <code>startDate</code> field value.</p>
     *
     * @return the <code>startDate</code> field value.
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * <p>Sets the <code>endDate</code> field value.</p>
     *
     * @param endDate the value to set.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * <p>Gets the <code>endDate</code> field value.</p>
     *
     * @return the <code>endDate</code> field value.
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * <p>Gets the flag indicating whether reposted contests are to be included into report or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether reposted contests are to be included into
     *         report or not.
     */
    public boolean getShowReposts() {
        return this.showReposts;
    }

    /**
     * <p>Sets the flag indicating whether reposted contests are to be included into report or not.</p>
     *
     * @param showReposts a <code>boolean</code> providing the flag indicating whether reposted contests are to be
     *                    included into report or not.
     */
    public void setShowReposts(boolean showReposts) {
        this.showReposts = showReposts;
    }

    /**
     * <p>Gets the maximum value for numerical filter.</p>
     *
     * @return a <code>Double</code> providing the maximum value for numerical filter.
     */
    public Double getNumericalFilterMaxValue() {
        return this.numericalFilterMaxValue;
    }

    /**
     * <p>Sets the maximum value for numerical filter.</p>
     *
     * @param numericalFilterMaxValue a <code>Double</code> providing the maximum value for numerical filter.
     */
    public void setNumericalFilterMaxValue(Double numericalFilterMaxValue) {
        this.numericalFilterMaxValue = numericalFilterMaxValue;
    }

    /**
     * <p>Gets the minimum value for numerical filter.</p>
     *
     * @return a <code>Double</code> providing the minimum value for numerical filter.
     */
    public Double getNumericalFilterMinValue() {
        return this.numericalFilterMinValue;
    }

    /**
     * <p>Sets the minimum value for numerical filter.</p>
     *
     * @param numericalFilterMinValue a <code>Double</code> providing the minimum value for numerical filter.
     */
    public void setNumericalFilterMinValue(Double numericalFilterMinValue) {
        this.numericalFilterMinValue = numericalFilterMinValue;
    }

    /**
     * <p>Gets the type of numerical filter to filter the pipeline data for.</p>
     *
     * @return a <code>PipelineNumericalFilterType</code> providing the type of numerical filter to filter the pipeline
     *         data for.
     */
    public PipelineNumericalFilterType getNumericalFilterType() {
        return this.numericalFilterType;
    }

    /**
     * <p>Sets the type of numerical filter to filter the pipeline data for.</p>
     *
     * @param numericalFilterType a <code>PipelineNumericalFilterType</code> providing the type of numerical filter to
     *                            filter the pipeline data for.
     */
    public void setNumericalFilterType(PipelineNumericalFilterType numericalFilterType) {
        this.numericalFilterType = numericalFilterType;
    }
}
