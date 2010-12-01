/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

/**
 * <p>A DTO providing the details on various <code>TopCoder Direct</code> facts like number of active and completed
 * projects.</p>
 *
 * <p>
 * Version 1.0.1 (Direct Release 6 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #bugRacesNumber} property.</li>
 *     <li>Added {@link #bugRacesNumber} property.</li>
 *   </ol>
 * </p>
 *
 *
 * @author isv
 * @version 1.0.1
 */
public class TopCoderDirectFactsDTO {

    /**
     * <p>An interface to be implemented by the parties interested in getting the details for
     * <code>TopCoder Direct</code> facts.</p> 
     */
    public static interface Aware {

        /**
         * <p>Sets the details on <code>TopCoder Direct</code> facts.</p>
         *
         * @param topcoderDirectFacts a <code>TopCoderDirectFactsDTO</code> providing the details on <code>TopCoder
         *        Direct</code> facts.
         */
        public void setTopcoderDirectFacts(TopCoderDirectFactsDTO topcoderDirectFacts);
    }

    /**
     * <p>An <code>int</code> providing the number of active projects.</p>
     */
    private int activeProjectsNumber;

    /**
     * <p>An <code>int</code> providing the number of completed projects.</p>
     */
    private int completedProjectsNumber;

    /**
     * <p>An <code>int</code> providing the number of active contests.</p>
     */
    private int activeContestsNumber;

    /**
     * <p>A <code>double</code> providing the total prize purse for available projects.</p>
     */
    private double prizePurse;

    /**
     * <p>An <code>int</code> providing the number of active members.</p>
     */
    private int activeMembersNumber;

    /**
     * <p>A <code>int</code> providing the number of bug races.</p>
     *
     * @since 1.0.1
     */
    private int bugRacesNumber;

    /**
     * <p>A <code>double</code> providing the prizes for bug races.</p>
     *
     * @since 1.0.1
     */
    private double bugRacesPrizes;

    /**
     * <p>Constructs new <code>TopCoderDirectFactsDTO</code> instance. This implementation does nothing.</p>
     */
    public TopCoderDirectFactsDTO() {
    }

    /**
     * <p>Gets the number of active projects.</p>
     *
     * @return an <code>int</code> providing the number of active projects.
     */
    public int getActiveProjectsNumber() {
        return activeProjectsNumber;
    }

    /**
     * <p>Sets the number of active projects.</p>
     *
     * @param activeProjectsNumber an <code>int</code> providing the number of active projects.
     */
    public void setActiveProjectsNumber(int activeProjectsNumber) {
        this.activeProjectsNumber = activeProjectsNumber;
    }

    /**
     * <p>Gets the number of completed projects.</p>
     *
     * @return an <code>int</code> providing the number of completed projects.
     */
    public int getCompletedProjectsNumber() {
        return completedProjectsNumber;
    }

    /**
     * <p>Sets the number of completed projects.</p>
     *
     * @param completedProjectsNumber an <code>int</code> providing the number of completed projects.
     */
    public void setCompletedProjectsNumber(int completedProjectsNumber) {
        this.completedProjectsNumber = completedProjectsNumber;
    }

    /**
     * <p>Gets the number of active contests.</p>
     *
     * @return an <code>int</code> providing the number of active contests.
     */
    public int getActiveContestsNumber() {
        return activeContestsNumber;
    }

    /**
     * <p>Sets the number of active contests.</p>
     *
     * @param activeContestsNumber an <code>int</code> providing the number of active contests.
     */
    public void setActiveContestsNumber(int activeContestsNumber) {
        this.activeContestsNumber = activeContestsNumber;
    }

    /**
     * <p>Gets the total prize purse for available projects.</p>
     *
     * @return a <code>double</code> providing the total prize purse for available projects.
     */
    public double getPrizePurse() {
        return prizePurse;
    }

    /**
     * <p>Sets the total prize purse for available projects.</p>
     *
     * @param prizePurse a <code>double</code> providing the total prize purse for available projects.
     */
    public void setPrizePurse(double prizePurse) {
        this.prizePurse = prizePurse;
    }

    /**
     * <p>Gets the number of active members.</p>
     *
     * @return an <code>int</code> providing the number of active members.
     */
    public int getActiveMembersNumber() {
        return activeMembersNumber;
    }

    /**
     * <p>Sets the number of active members.</p>
     *
     * @param activeMembersNumber an <code>int</code> providing the number of active members.
     */
    public void setActiveMembersNumber(int activeMembersNumber) {
        this.activeMembersNumber = activeMembersNumber;
    }

    /**
     * <p>Gets the number of bug races.</p>
     *
     * @return a <code>int</code> providing the number of bug races.
     * @since 1.0.1
     */
    public int getBugRacesNumber() {
        return this.bugRacesNumber;
    }

    /**
     * <p>Sets the number of bug races.</p>
     *
     * @param bugRacesNumber a <code>int</code> providing the number of bug races.
     * @since 1.0.1
     */
    public void setBugRacesNumber(int bugRacesNumber) {
        this.bugRacesNumber = bugRacesNumber;
    }

    /**
     * <p>Gets the prizes for bug races.</p>
     *
     * @return a <code>double</code> providing the prizes for bug races.
     * @since 1.0.1
     */
    public double getBugRacesPrizes() {
        return this.bugRacesPrizes;
    }

    /**
     * <p>Sets the prizes for bug races.</p>
     *
     * @param bugRacesPrizes a <code>double</code> providing the prizes for bug races.
     * @since 1.0.1
     */
    public void setBugRacesPrizes(double bugRacesPrizes) {
        this.bugRacesPrizes = bugRacesPrizes;
    }

}
