/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.jirareport;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * The DTO for a single entry in the jira issues report.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class JiraIssuesReportEntryDTO implements Serializable {

    /**
     * The customer name.
     */
    private String customer;

    /**
     * The billing account.
     */
    private String billingAccount;

    /**
     * The contest name.
     */
    private String contestName;

    /**
     * The contest id.
     */
    private long contestId;

    /**
     * The project name.
     */
    private String projectName;

    /**
     * The project id.
     */
    private long projectId;

    /**
     * The ticket id.
     */
    private String ticketId;

    /**
     * The launch date.
     */
    private Date launchDate;

    /**
     * The ticket title.
     */
    private String ticketTitle;

    /**
     * The ticket description.
     */
    private String ticketDescription;

    /**
     * The prize.
     */
    private double prize;

    /**
     * The status.
     */
    private String status;

    /**
     * The reporter.
     */
    private String reporter;

    /**
     * The assignee.
     */
    private String assignee;

    /**
     * The TCO points.
     */
    private int tcoPoints;

    /**
     * The resolution date.
     */
    private Date resolutionDate;

    /**
     * The number of votes.
     */
    private int votesNumber;

    /**
     * The winner handle.
     */
    private String winner;

    /**
     * Gets the name of the customer.
     *
     * @return the name of the customer.
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * Sets the name of the customer.
     *
     * @param customer the name of the customer.
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     * Gets the billing account.
     *
     * @return the billing account.
     */
    public String getBillingAccount() {
        return billingAccount;
    }

    /**
     * Sets the billing account.
     *
     * @param billingAccount the billing account.
     */
    public void setBillingAccount(String billingAccount) {
        this.billingAccount = billingAccount;
    }

    /**
     * Gets the contest name.
     *
     * @return the contest name.
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * Sets the contest name.
     *
     * @param contestName the contest name.
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * Gets the project name.
     *
     * @return the project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the project name.
     *
     * @param projectName the project name.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the project id.
     *
     * @return the project id.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     *
     * @param projectId the project id.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the contest id.
     *
     * @return the contest id.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the contest id.
     *
     * @param contestId the contest id.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Gets the ticket id.
     *
     * @return the ticket id.
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     * Sets the ticket id.
     *
     * @param ticketId the ticket id.
     */
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * Gets the launch date.
     *
     * @return the launch date.
     */
    public Date getLaunchDate() {
        return launchDate;
    }

    /**
     * Sets the launch date.
     *
     * @param launchDate the launch date.
     */
    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    /**
     * Gets the ticket title.
     *
     * @return the ticket title.
     */
    public String getTicketTitle() {
        return ticketTitle;
    }

    /**
     * Sets the ticket title.
     *
     * @param ticketTitle the ticket title.
     */
    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    /**
     * Gets the ticket description.
     *
     * @return the ticket description.
     */
    public String getTicketDescription() {
        return ticketDescription;
    }

    /**
     * Sets the ticket description.
     *
     * @param ticketDescription the ticket description.
     */
    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    /**
     * Gets the prize.
     *
     * @return the prize.
     */
    public double getPrize() {
        return prize;
    }

    /**
     * Sets the prize.
     *
     * @param prize the prize.
     */
    public void setPrize(double prize) {
        this.prize = prize;
    }

    /**
     * Gets the status.
     *
     * @return the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the reporter.
     *
     * @return the reporter.
     */
    public String getReporter() {
        return reporter;
    }

    /**
     * Sets the reporter.
     *
     * @param reporter the reporter.
     */
    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    /**
     * Gets the assignee.
     *
     * @return the assignee.
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * Sets the assignee.
     *
     * @param assignee the assignee.
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * Gets the TCO points.
     *
     * @return the TCO points.
     */
    public int getTcoPoints() {
        return tcoPoints;
    }

    /**
     * Sets the TCO points.
     *
     * @param tcoPoints the TCO points.
     */
    public void setTcoPoints(int tcoPoints) {
        this.tcoPoints = tcoPoints;
    }

    /**
     * Gets the resolution date.
     *
     * @return the resolution date.
     */
    public Date getResolutionDate() {
        return resolutionDate;
    }

    /**
     * Sets the resolution date.
     *
     * @param resolutionDate the resolution date.
     */
    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    /**
     * Gets the votes number.
     *
     * @return the votes number.
     */
    public int getVotesNumber() {
        return votesNumber;
    }

    /**
     * Sets the votes number.
     *
     * @param votesNumber the votes number.
     */
    public void setVotesNumber(int votesNumber) {
        this.votesNumber = votesNumber;
    }

    /**
     * Gets the winner.
     *
     * @return the winner.
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Sets the winner
     *
     * @param winner the winner.
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }


}
