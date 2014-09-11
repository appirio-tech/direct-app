/*
 * Copyright (C) 2008-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import com.topcoder.management.resource.Resource;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

/**
 * <p>
 * This entity represents an Design competition.
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated class to make it compilable.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER, isv
 * @version 1.2
 */
public class DesignCompetition extends Competition {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 3483940144907276525L;

    /**
     * <p>
     * Represents the competition name.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Creates a <code>DesignCompetition</code>.
     * </p>
     *
     * @param name
     *            the competition name.
     */
    public DesignCompetition(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Returns the competition name.
     * </p>
     *
     * @return the competition name.
     */
    public String getName() {
        return name;
    }

    /**
     * <p> Returns the id. </p>
     *
     * @return the id.
     */
    @Override
    public long getId() {
        return 0;  
    }

    /**
     * Sets the id.
     *
     * @param id the id.
     */
    @Override
    public void setId(long id) {
        
    }

    /**
     * <p> Returns the start time of the competition. </p>
     *
     * @return the start time of the competition.
     */
    @Override
    public XMLGregorianCalendar getStartTime() {
        return null;  
    }

    /**
     * <p> Sets the start time of the competition. </p>
     *
     * @param startTime the start time of the competition.
     */
    @Override
    public void setStartTime(XMLGregorianCalendar startTime) {
        
    }

    /**
     * <p> Returns the end time of the competition. </p>
     *
     * @return the end time of the competition.
     */
    @Override
    public XMLGregorianCalendar getEndTime() {
        return null;  
    }

    /**
     * <p> Sets the end time of the competition. </p>
     *
     * @param endTime the end time of the competition.
     */
    @Override
    public void setEndTime(XMLGregorianCalendar endTime) {
        
    }

    /**
     * <p> Returns the prizes for the competition. </p>
     *
     * @return the prizes for the competition.
     */
    @Override
    public List<CompetitionPrize> getPrizes() {
        return null;  
    }

    /**
     * <p> Sets the prizes for the competition. </p>
     *
     * @param prizes the prizes for the competition.
     */
    @Override
    public void setPrizes(List<CompetitionPrize> prizes) {
        
    }

    /**
     * <p> Returns the digital run points for the competition. </p>
     *
     * @return the digital run points for the competition.
     */
    @Override
    public double getDrPoints() {
        return 0;  
    }

    /**
     * <p> Sets the digital run points for the competition. </p>
     *
     * @param drPoints the digital run points for the competition.
     */
    @Override
    public void setDrPoints(double drPoints) {
        
    }

    /**
     * <p> Returns the admin fee for the competition. </p>
     *
     * @return the admin fee for the competition.
     */
    @Override
    public Double getAdminFee() {
        return null;  
    }

    /**
     * <p> Sets the admin fee for the competition. </p>
     *
     * @param adminFee the admin fee for the competition.
     */
    @Override
    public void setAdminFee(Double adminFee) {
        
    }

    /**
     * <p> Returns the short summary of the competition. </p>
     *
     * @return the short summary of the competition.
     */
    @Override
    public String getShortSummary() {
        return null;  
    }

    /**
     * <p> Sets the short summary of the competition. </p>
     *
     * @param shortSummary the short summary of the competition.
     */
    @Override
    public void setShortSummary(String shortSummary) {
        
    }

    /**
     * <p> Returns the eligibility of the competition. </p>
     *
     * @return the eligibility of the competition.
     */
    @Override
    public String getEligibility() {
        return null;  
    }

    /**
     * <p> Sets the eligibility of the competition. </p>
     *
     * @param eligibility the eligibility of the competition.
     */
    @Override
    public void setEligibility(String eligibility) {
        
    }

    /**
     * <p> Returns the creator user id of the competition. </p>
     *
     * @return the creator user id of the competition.
     */
    @Override
    public long getCreatorUserId() {
        return 0;  
    }

    /**
     * <p> Sets the creator user id of the competition. </p>
     *
     * @param creatorUserId the creator user id of the competition.
     */
    @Override
    public void setCreatorUserId(long creatorUserId) {
        
    }

    /**
     * <p> Returns the competition type. </p>
     *
     * @return the competition type.
     */
    @Override
    public CompetionType getType() {
        return null;  
    }

    /**
     * <p> Sets the competition type. </p>
     *
     * @param type the competition type.
     */
    @Override
    public void setType(CompetionType type) {
        
    }

    /**
     * @return ReviewPayment
     */
    @Override
    public double getReviewPayment() {
        return 0;  
    }

    /**
     * @param reviewPayment
     */
    @Override
    public void setReviewPayment(double reviewPayment) {
        
    }

    /**
     * @return SpecificationReviewPayment
     */
    @Override
    public double getSpecificationReviewPayment() {
        return 0;  
    }

    /**
     * @param specificationReviewPayment
     */
    @Override
    public void setSpecificationReviewPayment(double specificationReviewPayment) {
        
    }

    /**
     * @return ContestFee
     */
    @Override
    public double getContestFee() {
        return 0;  
    }

    /**
     * @param contestFee
     */
    @Override
    public void setContestFee(double contestFee) {
        
    }

    /**
     * @return ClientName
     */
    @Override
    public String getClientName() {
        return null;  
    }

    /**
     * @param clientName
     */
    @Override
    public void setClientName(String clientName) {
        
    }

    /**
     * @return Resources
     */
    @Override
    public Resource[] getResources() {
        return new Resource[0];  
    }

    /**
     * @param resources
     */
    @Override
    public void setResources(Resource[] resources) {
        
    }

    /**
     * @return Confidence
     */
    @Override
    public int getConfidence() {
        return 0;  
    }

    /**
     * @param confidence
     */
    @Override
    public void setConfidence(int confidence) {
        
    }

    /**
     * @return ClientApproval
     */
    @Override
    public boolean getClientApproval() {
        return false;  
    }

    /**
     * @param clientApproval
     */
    @Override
    public void setClientApproval(boolean clientApproval) {
        
    }

    /**
     * @return PricingApproval
     */
    @Override
    public boolean getPricingApproval() {
        return false;  
    }

    /**
     * @param pricingApproval
     */
    @Override
    public void setPricingApproval(boolean pricingApproval) {
        
    }

    /**
     * @return HasWikiSpecification
     */
    @Override
    public boolean getHasWikiSpecification() {
        return false;  
    }

    /**
     * @param hasWikiSpecification
     */
    @Override
    public void setHasWikiSpecification(boolean hasWikiSpecification) {
        
    }

    /**
     * @return PassedSpecReview
     */
    @Override
    public boolean getPassedSpecReview() {
        return false;  
    }

    /**
     * @param passedSpecReview
     */
    @Override
    public void setPassedSpecReview(boolean passedSpecReview) {
        
    }

    /**
     * @return HasDependentCompetitions
     */
    @Override
    public boolean getHasDependentCompetitions() {
        return false;  
    }

    /**
     * @param hasDependentCompetitions
     */
    @Override
    public void setHasDependentCompetitions(boolean hasDependentCompetitions) {
        
    }

    /**
     * @return WasReposted
     */
    @Override
    public boolean getWasReposted() {
        return false;  
    }

    /**
     * @param wasReposted
     */
    @Override
    public void setWasReposted(boolean wasReposted) {
        
    }

    /**
     * @return Notes
     */
    @Override
    public String getNotes() {
        return null;  
    }

    /**
     * @param notes
     */
    @Override
    public void setNotes(String notes) {
        
    }
}
