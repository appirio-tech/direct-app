/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.topcoder.management.resource.Resource;

/**
 * <p>
 * This data object represents a competition and is a dummy class whose only properties are an ID and a parent project.
 * Future components may design and use sub-classes of this class.
 * </p>
 * <p>
 * We simply define two properties and getter/setters for these properties. Note that this is a dumb DTO - no validation
 * is done. We also override hash code and equality testing to allow this object to be used in hash sets.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe as it has mutable state.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
@XmlSeeAlso ({SoftwareCompetition.class})
public abstract class Competition implements Cloneable, Serializable {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 6507810893310812187L;

    /**
     * <p>
     * Represents the ID of this competition.
     * </p>
     * <p>
     * It uses <code>Long</code> type instead of <code>long</code> type to allow for null values to be set before
     * entity creation in persistence. This variable is mutable and is retrieved by the {@link #getCompetitionId()}
     * method and set by the {@link #setCompetitionId(Long)} method. It is initialized to null, and may be set to ANY
     * value.
     * </p>
     */
    private Long competitionId;

    /**
     * <p>
     * Represents the project to which this competition belongs.
     * </p>
     * <p>
     * This variable is mutable and is retrieved by the {@link #getProject()} method and set by the
     * {@link #setProject(Project)} method. It is initialized to null, and may be set to ANY value.
     * </p>
     */
    private Project project;

    /**
     * <p>
     * Constructs a <code>Competition</code> instance.
     * </p>
     */
    public Competition() {
    }

    /**
     * <p>
     * Gets the ID of the competition.
     * </p>
     *
     * @return The ID of the competition.
     */
    public Long getCompetitionId() {
        return competitionId;
    }

    /**
     * <p>
     * Sets the ID of this competition.
     * </p>
     *
     * @param competitionId
     *            The desired ID of this competition. ANY value.
     */
    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    /**
     * <p>
     * Gets the project to which the competition belongs.
     * </p>
     *
     * @return The project to which the competition belongs.
     */
    public Project getProject() {
        return project;
    }

    /**
     * <p>
     * Sets the project to which this competition belongs.
     * </p>
     *
     * @param project
     *            The desired project to which this competition should belong.
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * <p>
     * Computes the hash code for this competition, in accordance with the equality test.
     * </p>
     *
     * @return the hash code for this competition, in accordance with the equality test.
     */
    public int hashCode() {
        return null == competitionId ? super.hashCode() : competitionId.hashCode();
    }

    /**
     * <p>
     * Compares whether this object is equal to the given object.
     * </p>
     *
     * @param obj
     *            The object to test equality with. Possibly null.
     * @return Whether this object is equal to the given object.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Competition)) {
            return false;
        }

        Competition competition = (Competition) obj;
        if (null == competitionId || null == competition.getCompetitionId()) {
            return this == obj;
        }

        return competitionId.equals(competition.getCompetitionId());
    }

    /**
     * <p>
     * Returns the id.
     * </p>
     * @return the id.
     */
    public abstract long getId();

    /**
     * Sets the id.
     * @param id the id.
     */
    public abstract void setId(long id);

    /**
     * <p>
     * Returns the start time of the competition.
     * </p>
     * @return the start time of the competition.
     */
    public abstract XMLGregorianCalendar getStartTime();

    /**
     * <p>
     * Sets the start time of the competition.
     * </p>
     * @param startTime the start time of the competition.
     */
    public abstract void setStartTime(XMLGregorianCalendar startTime);

    /**
     * <p>
     * Returns the end time of the competition.
     * </p>
     * @return the end time of the competition.
     */
    public abstract XMLGregorianCalendar getEndTime();

    /**
     * <p>
     * Sets the end time of the competition.
     * </p>
     * @param endTime the end time of the competition.
     */
    public abstract void setEndTime(XMLGregorianCalendar endTime);

    /**
     * <p>
     * Returns the prizes for the competition.
     * </p>
     * @return the prizes for the competition.
     */
    public abstract List<CompetitionPrize> getPrizes();

    /**
     * <p>
     * Sets the prizes for the competition.
     * </p>
     * @param prizes the prizes for the competition.
     */
    public abstract void setPrizes(List<CompetitionPrize> prizes);

    /**
     * <p>
     * Returns the digital run points for the competition.
     * </p>
     * @return the digital run points for the competition.
     */
    public abstract double getDrPoints();

    /**
     * <p>
     * Sets the digital run points for the competition.
     * </p>
     * @param drPoints the digital run points for the competition.
     */
    public abstract void setDrPoints(double drPoints);

    /**
     * <p>
     * Returns the admin fee for the competition.
     * </p>
     * @return the admin fee for the competition.
     */
    public abstract Double getAdminFee();

    /**
     * <p>
     * Sets the admin fee for the competition.
     * </p>
     * @param adminFee the admin fee for the competition.
     */
    public abstract void setAdminFee(Double adminFee);

    /**
     * <p>
     * Returns the short summary of the competition.
     * </p>
     * @return the short summary of the competition.
     */
    public abstract String getShortSummary();

    /**
     * <p>
     * Sets the short summary of the competition.
     * </p>
     * @param shortSummary the short summary of the competition.
     */
    public abstract void setShortSummary(String shortSummary);

    /**
     * <p>
     * Returns the eligibility of the competition.
     * </p>
     * @return the eligibility of the competition.
     */
    public abstract String getEligibility();

    /**
     * <p>
     * Sets the eligibility of the competition.
     * </p>
     * @param eligibility the eligibility of the competition.
     */
    public abstract void setEligibility(String eligibility);

    /**
     * <p>
     * Returns the creator user id of the competition.
     * </p>
     * @return the creator user id of the competition.
     */
    public abstract long getCreatorUserId();

    /**
     * <p>
     * Sets the creator user id of the competition.
     * </p>
     * @param creatorUserId the creator user id of the competition.
     */
    public abstract void setCreatorUserId(long creatorUserId);

    /**
     * <p>
     * Returns the competition type.
     * </p>
     * @return the competition type.
     */
    public abstract CompetionType getType();

    /**
     * <p>
     * Sets the competition type.
     * </p>
     * @param type the competition type.
     */
    public abstract void setType(CompetionType type);
    
    /**
     * @return ReviewPayment
    */
    public abstract double getReviewPayment();
    /**
     * @param reviewPayment 
    */
    public abstract void setReviewPayment(double reviewPayment);
    /**
     * @return SpecificationReviewPayment
    */
    public abstract double getSpecificationReviewPayment();
    /**
     * @param specificationReviewPayment 
    */
    public abstract void setSpecificationReviewPayment(double specificationReviewPayment);
    /**
     * @return ContestFee
    */
    public abstract double getContestFee() ;
    /**
     * @param contestFee 
    */
    public abstract void setContestFee(double contestFee) ;
    /**
     * @return ClientName
    */
    public abstract String getClientName();
    /**
     * @param clientName 
    */
    public abstract void setClientName(String clientName) ;
    /**
     * @return Resources
    */
    public abstract Resource[] getResources();
    /**
     * @param resources 
    */
    public abstract void setResources(Resource[] resources) ;
    /**
     * @return Confidence
    */
    public abstract int getConfidence() ;
    /**
     * @param confidence 
    */
    public abstract void setConfidence(int confidence) ;
    /**
     * @return ClientApproval
    */
    public abstract boolean getClientApproval();
    /**
     * @param clientApproval 
    */
    public abstract void setClientApproval(boolean clientApproval) ;
    /**
     * @return PricingApproval
    */
    public abstract boolean getPricingApproval();
    /**
     * @param pricingApproval 
    */
    public abstract void setPricingApproval(boolean pricingApproval);
    /**
     * @return HasWikiSpecification
    */
    public abstract boolean getHasWikiSpecification();
    /**
     * @param hasWikiSpecification 
    */
    public abstract void setHasWikiSpecification(boolean hasWikiSpecification);
    /**
     * @return PassedSpecReview
    */
    public abstract boolean getPassedSpecReview() ;
    /**
     * @param passedSpecReview 
    */
    public abstract void setPassedSpecReview(boolean passedSpecReview) ;
    /**
     * @return HasDependentCompetitions
    */
    public abstract boolean getHasDependentCompetitions();
    /**
     * @param hasDependentCompetitions 
    */
    public abstract void setHasDependentCompetitions(boolean hasDependentCompetitions) ;
    /**
     * @return WasReposted
    */
    public abstract boolean getWasReposted() ;
    /**
     * @param wasReposted 
    */
    public abstract void setWasReposted(boolean wasReposted) ;
    /**
     * @return Notes
    */
    public abstract String getNotes();
    /**
     * @param notes 
    */
    public abstract void setNotes(String notes);
    
    public Object clone() {
        Object clonedObj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            clonedObj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            //ignore
        }
        return clonedObj;

    }
}
