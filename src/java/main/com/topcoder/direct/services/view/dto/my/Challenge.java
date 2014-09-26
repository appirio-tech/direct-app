/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.my;

import java.util.Date;

/**
 * <p>
 * The domain model for the challenge which is used to deserialize from json returned by Direct API.
 * </p>
 *
 * @author GreatKevin
 * @version 1.0
 */
public class Challenge {
    /**
     * The challenge id.
     */
    private long id;

    /**
     * The challenge name.
     */
    private String challengeName;

    /**
     * The challenge status.
     */
    private String challengeStatus;

    /**
     * The challenge type.
     */
    private String challengeType;

    /**
     * The client id.
     */
    private long clientId;

    /**
     * The client name.
     */
    private String clientName;

    /**
     * The billing id.
     */
    private long billingId;

    /**
     * The billing name.
     */
    private String billingName;

    /**
     * The direct project id.
     */
    private long directProjectId;

    /**
     * The direct project name.
     */
    private String directProjectName;

    /**
     * The challenge start date.
     */
    private Date challengeStartDate;

    /**
     * The challenge end date.
     */
    private Date challengeEndDate;

    /**
     * The total prize.
     */
    private double totalPrize;

    /**
     * The DR points.
     */
    private double drPoints;

    /**
     * Gets the challenge id.
     *
     * @return the challenge id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the challenge id.
     *
     * @param id the challenge id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the challenge name.
     *
     * @return the challenge name.
     */
    public String getChallengeName() {
        return challengeName;
    }

    /**
     * Sets the challenge name.
     *
     * @param challengeName the challenge name.
     */
    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    /**
     * Gets the challenge type.
     *
     * @return the challenge type.
     */
    public String getChallengeType() {
        return challengeType;
    }

    /**
     * Sets the challenge type.
     *
     * @param challengeType the challenge type.
     */
    public void setChallengeType(String challengeType) {
        this.challengeType = challengeType;
    }

    /**
     * Gets the client id.
     *
     * @return the client id.
     */
    public long getClientId() {
        return clientId;
    }

    /**
     * Sets the client id.
     *
     * @param clientId the client id.
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the client name.
     *
     * @return the client name.
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets the client name.
     *
     * @param clientName the client name.
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Gets the billing id.
     *
     * @return the billing id.
     */
    public long getBillingId() {
        return billingId;
    }

    /**
     * Sets the billing id.
     *
     * @param billingId the billing id.
     */
    public void setBillingId(long billingId) {
        this.billingId = billingId;
    }

    /**
     * Gets the billing name.
     *
     * @return the billing name.
     */
    public String getBillingName() {
        return billingName;
    }

    /**
     * Sets the billing name.
     *
     * @param billingName the billing name.
     */
    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     */
    public long getDirectProjectId() {
        return directProjectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param directProjectId the direct project id.
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * Gets the direct project name.
     *
     * @return the direct project name.
     */
    public String getDirectProjectName() {
        return directProjectName;
    }

    /**
     * Sets the direct project name.
     *
     * @param directProjectName the direct project name.
     */
    public void setDirectProjectName(String directProjectName) {
        this.directProjectName = directProjectName;
    }

    /**
     * Gets the challenge start date.
     *
     * @return the challenge start date.
     */
    public Date getChallengeStartDate() {
        return challengeStartDate;
    }

    /**
     * Sets the challenge start date.
     *
     * @param challengeStartDate the challenge start date.
     */
    public void setChallengeStartDate(Date challengeStartDate) {
        this.challengeStartDate = challengeStartDate;
    }

    /**
     * Gets the challenge end date.
     *
     * @return the challenge end date.
     */
    public Date getChallengeEndDate() {
        return challengeEndDate;
    }

    /**
     * Sets the challenge end date.
     *
     * @param challengeEndDate the challenge end date.
     */
    public void setChallengeEndDate(Date challengeEndDate) {
        this.challengeEndDate = challengeEndDate;
    }

    /**
     * Gets the total prize.
     *
     * @return the total prize.
     */
    public double getTotalPrize() {
        return totalPrize;
    }

    /**
     * Sets the total prize.
     *
     * @param totalPrize the total prize.
     */
    public void setTotalPrize(double totalPrize) {
        this.totalPrize = totalPrize;
    }

    /**
     * Gets the DR points.
     *
     * @return the DR points.
     */
    public double getDrPoints() {
        return drPoints;
    }

    /**
     * Sets the DR points.
     *
     * @param drPoints the DR points.
     */
    public void setDrPoints(double drPoints) {
        this.drPoints = drPoints;
    }

    /**
     * Gets the challenge status.
     *
     * @return the challenge status.
     */
    public String getChallengeStatus() {
        return challengeStatus;
    }

    /**
     * Sets the challenge status.
     *
     * @param challengeStatus the challenge status.
     */
    public void setChallengeStatus(String challengeStatus) {
        this.challengeStatus = challengeStatus;
    }
}
