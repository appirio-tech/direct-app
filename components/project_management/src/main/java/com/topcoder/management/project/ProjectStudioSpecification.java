/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

/**
 * <p>
 * This is a ProjectStudioSpecification entity which represents the Project Studio Specification. It extends from
 * AuditableObject class. Added in version 1.2.
 * </p>
 * <p>
 * <strong>Thread-Safety:</strong> This class is mutable and not thread safe. But it will be used as entity so it'll not
 * cause any thread safe problem.
 * </p>
 *
 * <p>
 * Version 1.2.1 - TC Direct Replatforming Release 1 change note:
 * <ul>
 * <li>Add {@link #contestDescription} and {@link #contestIntroduction} fields.</li>
 * <li>Remove the checkers from setters.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.2.2 - TC Direct Replatforming Release 3 change note:
 * <ul>
 * <li>Add {@link #generalFeedback} field.</li>
 * </ul>
 * </p>
 * 
 * @author flytoj2ee, TCSDEVELOPER
 * @version 1.2.2
 * @since 1.2
 */
@SuppressWarnings("serial")
public class ProjectStudioSpecification extends AuditableObject {
    /**
     * Represents the goals of ProjectStudioSpecification. The default value is null. It's changeable. It could not be
     * null or empty. It's accessed in setter and getter.
     */
    private String goals;

    /**
     * Represents the targetAudience of ProjectStudioSpecification. The default value is null. It's changeable. It could
     * not be null or empty. It's accessed in setter and getter.
     */
    private String targetAudience;

    /**
     * Represents the brandingGuidelines of ProjectStudioSpecification. The default value is null. It's changeable. It
     * not be null or empty. It's accessed in setter and getter.
     */
    private String brandingGuidelines;

    /**
     * Represents the dislikedDesignWebSites of ProjectStudioSpecification. The default value is null. It's changeable.
     * It could not be null or empty. It's accessed in setter and getter.
     */
    private String dislikedDesignWebSites;

    /**
     * Represents the otherInstructions of ProjectStudioSpecification. The default value is null. It's changeable. It
     * could not be null or empty. It's accessed in setter and getter.
     */
    private String otherInstructions;

    /**
     * Represents the winningCriteria of ProjectStudioSpecification. The default value is null. It's changeable. It
     * could not be null or empty. It's accessed in setter and getter.
     */
    private String winningCriteria;

    /**
     * Represents the submittersLockedBetweenRounds of ProjectStudioSpecification. The default value is false. It's
     * changeable. It could be any value. It's accessed in setter and getter.
     */
    private boolean submittersLockedBetweenRounds;

    /**
     * Represents the roundOneIntroduction of ProjectStudioSpecification. The default value is null. It's changeable. It
     * could not be null or empty. It's accessed in setter and getter.
     */
    private String roundOneIntroduction;

    /**
     * Represents the roundTwoIntroduction of ProjectStudioSpecification. The default value is null. It's changeable. It
     * could not be null or empty. It's accessed in setter and getter.
     */
    private String roundTwoIntroduction;

    /**
     * Represents the colors of ProjectStudioSpecification. The default value is null. It's changeable. It could not be
     * null or empty. It's accessed in setter and getter.
     */
    private String colors;

    /**
     * Represents the fonts of ProjectStudioSpecification. The default value is null. It's changeable. It could not be
     * null or empty. It's accessed in setter and getter.
     */
    private String fonts;

    /**
     * Represents the layoutAndSize of ProjectStudioSpecification. The default value is null. It's changeable. It could
     * not be null or empty. It's accessed in setter and getter.
     */
    private String layoutAndSize;

    /**
     * Represents the contestIntroduction of ProjectStudioSpecification. The default value is null. It's changeable.
     */
    private String contestIntroduction;
    
    /**
     * Represents the contestDescription of ProjectStudioSpecification. The default value is null. It's changeable.
     */
    private String contestDescription;

    /**
     * Represents the general feedback of ProjectStudioSpecification. The default value is null. It's changeable.
     * 
     * @since 1.2.2
     */
    private String generalFeedback;

    /**
     * Represents the id of ProjectStudioSpecification. The default value is 0. It's changeable.
     * It should be a positive value.
     * It's accessed in setter and getter.
     */
    private long id;

    /**
     * Empty constructor.
     */
    public ProjectStudioSpecification() {
    }

    /**
     * Returns the value of goals attribute.
     *
     * @return the value of goals.
     */
    public String getGoals() {
        return this.goals;
    }

    /**
     * Sets the given value to goals attribute.
     *
     * @param goals
     *            the given value to set.
     */
    public void setGoals(String goals) {
        this.goals = goals;
    }

    /**
     * Returns the value of targetAudience attribute.
     *
     * @return the value of targetAudience.
     */
    public String getTargetAudience() {
        return this.targetAudience;
    }

    /**
     * Sets the given value to targetAudience attribute.
     *
     * @param targetAudience
     *            the given value to set.
     */
    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    /**
     * Returns the value of brandingGuidelines attribute.
     *
     * @return the value of brandingGuidelines.
     */
    public String getBrandingGuidelines() {
        return this.brandingGuidelines;
    }

    /**
     * Sets the given value to brandingGuidelines attribute.
     *
     * @param brandingGuidelines
     *            the given value to set.
     */
    public void setBrandingGuidelines(String brandingGuidelines) {
        this.brandingGuidelines = brandingGuidelines;
    }

    /**
     * Returns the value of dislikedDesignWebSites attribute.
     *
     * @return the value of dislikedDesignWebSites.
     */
    public String getDislikedDesignWebSites() {
        return this.dislikedDesignWebSites;
    }

    /**
     * Sets the given value to dislikedDesignWebSites attribute.
     *
     * @param dislikedDesignWebSites
     *            the given value to set.
     */
    public void setDislikedDesignWebSites(String dislikedDesignWebSites) {
        this.dislikedDesignWebSites = dislikedDesignWebSites;
    }

    /**
     * Returns the value of otherInstructions attribute.
     *
     * @return the value of otherInstructions.
     */
    public String getOtherInstructions() {
        return this.otherInstructions;
    }

    /**
     * Sets the given value to otherInstructions attribute.
     *
     * @param otherInstructions
     *            the given value to set.
     */
    public void setOtherInstructions(String otherInstructions) {
        this.otherInstructions = otherInstructions;
    }

    /**
     * Returns the value of winningCriteria attribute.
     *
     * @return the value of winningCriteria.
     */
    public String getWinningCriteria() {
        return this.winningCriteria;
    }

    /**
     * Sets the given value to winningCriteria attribute.
     *
     * @param winningCriteria
     *            the given value to set.
     */
    public void setWinningCriteria(String winningCriteria) {
        this.winningCriteria = winningCriteria;
    }

    /**
     * Returns the value of submittersLockedBetweenRounds attribute.
     *
     * @return the value of submittersLockedBetweenRounds.
     */
    public boolean isSubmittersLockedBetweenRounds() {
        return this.submittersLockedBetweenRounds;
    }

    /**
     * Sets the given value to submittersLockedBetweenRounds attribute.
     *
     * @param submittersLockedBetweenRounds
     *            the given value to set.
     */
    public void setSubmittersLockedBetweenRounds(boolean submittersLockedBetweenRounds) {
        this.submittersLockedBetweenRounds = submittersLockedBetweenRounds;
    }

    /**
     * Returns the value of roundOneIntroduction attribute.
     *
     * @return the value of roundOneIntroduction.
     */
    public String getRoundOneIntroduction() {
        return this.roundOneIntroduction;
    }

    /**
     * Sets the given value to roundOneIntroduction attribute.
     *
     * @param roundOneIntroduction
     *            the given value to set.
     */
    public void setRoundOneIntroduction(String roundOneIntroduction) {
        this.roundOneIntroduction = roundOneIntroduction;
    }

    /**
     * Returns the value of roundTwoIntroduction attribute.
     *
     * @return the value of roundTwoIntroduction.
     */
    public String getRoundTwoIntroduction() {
        return this.roundTwoIntroduction;
    }

    /**
     * Sets the given value to roundTwoIntroduction attribute.
     *
     * @param roundTwoIntroduction
     *            the given value to set.
     */
    public void setRoundTwoIntroduction(String roundTwoIntroduction) {
        this.roundTwoIntroduction = roundTwoIntroduction;
    }

    /**
     * Returns the value of colors attribute.
     *
     * @return the value of colors.
     */
    public String getColors() {
        return this.colors;
    }

    /**
     * Sets the given value to colors attribute.
     *
     * @param colors
     *            the given value to set.
     */
    public void setColors(String colors) {
        this.colors = colors;
    }

    /**
     * Returns the value of fonts attribute.
     *
     * @return the value of fonts.
     */
    public String getFonts() {
        return this.fonts;
    }

    /**
     * Sets the given value to fonts attribute.
     *
     * @param fonts
     *            the given value to set.
     */
    public void setFonts(String fonts) {
        this.fonts = fonts;
    }

    /**
     * Returns the value of layoutAndSize attribute.
     *
     * @return the value of layoutAndSize.
     */
    public String getLayoutAndSize() {
        return this.layoutAndSize;
    }

    /**
     * Sets the given value to layoutAndSize attribute.
     *
     * @param layoutAndSize
     *            the given value to set.
     */
    public void setLayoutAndSize(String layoutAndSize) {
        this.layoutAndSize = layoutAndSize;
    }

    /**
     * Returns the value of contestIntroducation attribute.
     * 
     * @return the value of contestIntroducation.
     * @since 1.2.1
     */
    public String getContestIntroduction() {
        return contestIntroduction;
    }

    /**
     * Sets the given value to contestIntroducation attribute.
     * 
     * @param contestIntroduction
     *              the given value to set.
     * @since 1.2.1
     */
    public void setContestIntroduction(String contestIntroduction) {
        this.contestIntroduction = contestIntroduction;
    }

    /**
     * Returns the value of contestDescription attribute.
     * 
     * @return the value of contestDescription.
     * @since 1.2.1
     */
    public String getContestDescription() {
        return contestDescription;
    }

    /**
     * Sets the given value to contestDescription attribute.
     * 
     * @param contestDescription
     *              the given value to set.
     * @since 1.2.1
     */
    public void setContestDescription(String contestDescription) {
        this.contestDescription = contestDescription;
    }

    /**
     * Returns the value of generalFeedback attribute.
     *
     * @return the value of generalFeedback.
     * @since 1.2.2
     */
    public String getGeneralFeedback() {
        return generalFeedback;
    }

    /**
     * Sets the given value to generalFeedback attribute.
     * 
     * @param generalFeedback
     *              the given value to set.
     * @since 1.2.2
     */
    public void setGeneralFeedback(String generalFeedback) {
        this.generalFeedback = generalFeedback;
    }

    /**
     * Returns the value of id attribute.
     *
     * @return the value of id.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the given value to id attribute.
     *
     * @param id
     *            the given value to set.
     * @throws IllegalArgumentException
     *             if the id parameter is non-positive
     */
    public void setId(long id) {
        this.id = id;
    }
}
