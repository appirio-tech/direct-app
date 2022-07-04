/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.contest.ContestType;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineNumericalFilterType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>A form bean providing the data submitted by user for filtering the pipeline report records.</p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Report Filters Group By Metadata Feature and Coordination Improvement ) updates:
 * - Adds {@link #clientIds}, {@link #groupId}, {@link #groupValues} and their getters and setters.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.1
 */
public class DashboardPipelineReportForm implements Serializable {

    /**
     * <p>A <code>ContestType</code> array listing the contest types to be selected by default.</p>
     */
    private static final ContestType[] DEFAULT_CONTEST_TYPES_SELECTION
        = new ContestType[] {ContestType.WEB_DESIGN, ContestType.LOGO_DESIGN, ContestType.BANNERS_ICONS,
                             ContestType.APP_FRONT_END_DESIGN, ContestType.WIDGET_MOBILE_SCREEN_DESIGN, ContestType.FRONT_END_FLASH,
                             ContestType.PRINT_DESIGN, ContestType.WIREFRAME, ContestType.IDEA_GENERATION, 
                             ContestType.CONCEPTUALIZATION, ContestType.SPECIFICATION, ContestType.OTHER,
                             ContestType.ARCHITECTURE, ContestType.COMPONENT_DESIGN, ContestType.COMPONENT_DEVELOPMENT,
                             ContestType.RIA_COMPONENT, ContestType.RIA_BUILD, ContestType.UI_PROTOTYPE,
                             ContestType.ASSEMBLY, ContestType.TEST_SUITES, ContestType.TEST_SCENARIOS,
                             ContestType.COPILOT_POSTING, ContestType.CONTENT_CREATION,
                             ContestType.REPORTING, ContestType.MARATHON_MATCH,
                             ContestType.BUG_HUNT, ContestType.FIRST2FINISH, ContestType.CODE};

    /**
     * <p>A <code>ContestStatus</code> array listing the statuses types to be selected by default.</p>
     */
    private static final ContestStatus[] DEFAULT_CONTEST_STATUSES_SELECTION
        = new ContestStatus[] {ContestStatus.DRAFT, ContestStatus.SCHEDULED, ContestStatus.ACTIVE,
                               ContestStatus.COMPLETED, ContestStatus.FAILED, ContestStatus.CANCELLED};

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
     * <p>A <code>long[]</code> providing the client ids to be used for filtering the contests.</p>
     * @since 1.1
     */
    private long[] clientIds;

    /**
     * <p>The startDate field.</p>
     */
    private String startDate;

    /**
     * <p>The endDate field.</p>
     */
    private String endDate;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether reposted contests are to be included into report
     * or not.</p>
     */
    private boolean showReposts = true;

    /**
     * <p>A <code>Double</code> providing the minimum value for numerical filter.</p>
     */
    private Double numericalFilterMinValue;

    /**
     * <p>A <code>Double</code> providing the maximum value for numerical filter.</p>
     */
    private Double numericalFilterMaxValue;

    /**
     * <p>A <code>PipelineNumericalFilterType</code> providing the type of numerical filter to filter the pipeline data
     * for.</p>
     */
    private PipelineNumericalFilterType numericalFilterType;

    /**
     * <p>A <code>Double</code> providing the minimum value for numerical filter.</p>
     */
    private List<Double> numericalFilterMinValues = new ArrayList<Double>();

    /**
     * <p>A <code>Double</code> providing the maximum value for numerical filter.</p>
     */
    private List<Double> numericalFilterMaxValues = new ArrayList<Double>();

    /**
     * <p>A <code>PipelineNumericalFilterType</code> providing the type of numerical filter to filter the pipeline data
     * for.</p>
     */
    private List<PipelineNumericalFilterType> numericalFilterTypes = new ArrayList<PipelineNumericalFilterType>();

    /**
     * A <code>long</code> provided the id of the group by option.
     */
    private long groupId;

    /**
     * A <code>List<String></code> providing the group value options
     */
    private List<String> groupValues;

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

    /**
     * <p>Gets the types of numerical filter to filter the pipeline data for.</p>
     *
     * @return a <code>PipelineNumericalFilterType</code> array providing the type of numerical filter to filter the
     *         pipeline data for.
     */
    public PipelineNumericalFilterType[] getNumericalFilterTypes() {
        return this.numericalFilterTypes.toArray(new PipelineNumericalFilterType[this.numericalFilterTypes.size()]);
    }

    /**
     * <p>Sets the types of numerical filter to filter the pipeline data for.</p>
     *
     * @param numericalFilterTypes a <code>PipelineNumericalFilterType</code> array providing the types of numerical
     *        filters to filter the pipeline data for.
     */
    public void setNumericalFilterTypes(PipelineNumericalFilterType[] numericalFilterTypes) {
        this.numericalFilterTypes.clear();
        if (numericalFilterTypes != null) {
            this.numericalFilterTypes.addAll(Arrays.asList(numericalFilterTypes));
        }
    }

    /**
     * <p>Gets the maximum values for numerical filter.</p>
     *
     * @return a <code>Double</code> array providing the maximum values for numerical filter.
     */
    public Double[] getNumericalFilterMaxValues() {
        return this.numericalFilterMaxValues.toArray(new Double[this.numericalFilterMaxValues.size()]);
    }

    /**
     * <p>Sets the maximum values for numerical filter.</p>
     *
     * @param numericalFilterMaxValues a <code>Double</code> array providing the maximum values for numerical filter.
     */
    public void setNumericalFilterMaxValues(Double[] numericalFilterMaxValues) {
        this.numericalFilterMaxValues.clear();
        if (numericalFilterMaxValues != null) {
            this.numericalFilterMaxValues.addAll(Arrays.asList(numericalFilterMaxValues));
        }
    }

    /**
     * <p>Gets the minimum values for numerical filter.</p>
     *
     * @return a <code>Double</code> array providing the minimum values for numerical filter.
     */
    public Double[] getNumericalFilterMinValues() {
        return this.numericalFilterMinValues.toArray(new Double[this.numericalFilterMinValues.size()]);
    }

    /**
     * <p>Sets the minimum valuse for numerical filter.</p>
     *
     * @param numericalFilterMinValues a <code>Double</code> array providing the minimum values for numerical filter.
     */
    public void setNumericalFilterMinValues(Double[] numericalFilterMinValues) {
        this.numericalFilterMinValues.clear();
        if (numericalFilterMinValues != null) {
            this.numericalFilterMinValues.addAll(Arrays.asList(numericalFilterMinValues));
        }
    }

    /**
     * Gets the group id.
     *
     * @return the group id.
     * @since 1.1
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * Sets the group id.
     *
     * @param groupId the group id.
     * @since 1.1
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets the group values.
     *
     * @return the group values.
     * @since 1.1
     */
    public List<String> getGroupValues() {
        return groupValues;
    }

    /**
     * Sets the group values.
     *
     * @param groupValues the group values.
     * @since 1.1
     */
    public void setGroupValues(List<String> groupValues) {
        this.groupValues = groupValues;
    }

    /**
     * Gets client ids.
     *
     * @return the client ids.
     * @since 1.1
     */
    public long[] getClientIds() {
        return clientIds;
    }

    /**
     * Sets the client ids.
     *
     * @param clientIds the client ids.
     * @since 1.1
     */
    public void setClientIds(long[] clientIds) {
        this.clientIds = clientIds;
    }

    /**
     * <p>Sets the specified numerical filter to specified values.</p>
     * 
     * @param filterType a <code>PipelineNumericalFilterType</code> referencing the filter type.
     * @param minValue a <code>Double</code> providing the minimum value for filter.
     * @param maxValue a <code>Double</code> providing the maximum value for filter.
     */
    public void setNumericalFilter(PipelineNumericalFilterType filterType, Double minValue, Double maxValue) {
        boolean found = false;

        for (int i = 0; (i < this.numericalFilterTypes.size()) && (!found); i++) {
            PipelineNumericalFilterType type = this.numericalFilterTypes.get(i);
            if (type == filterType) {
                found = true;
                this.numericalFilterMinValues.set(i, minValue);
                this.numericalFilterMaxValues.set(i, maxValue);
            }
        }

        if (!found) {
            this.numericalFilterTypes.add(filterType);
            this.numericalFilterMinValues.add(minValue);
            this.numericalFilterMaxValues.add(maxValue);
        }
    }
}
