/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

/**
 * <p>A DTO class providing the data for <code>Home</code> page view for <code>TopCoder Direct</code> application.</p>
 *
 */
public class HomePageDTO implements CoPilotStatsDTO.Aware, TopCoderDirectFactsDTO.Aware {

    /**
     * <p>A <code>CoPilotStatsDTO</code> providing statistics on co-piloting projects and available co-pilots.</p>
     */
    private CoPilotStatsDTO coPilotStats;

    /**
     * <p>A <code>TopCoderDirectFactsDTO</code> providing details on various <code>TopCoder Direct</code> facts.</p>
     */
    private TopCoderDirectFactsDTO topcoderDirectFacts;

    /**
     * <p>Constructs new <code>HomePageDTO</code> instance. This implementation does nothing.</p>
     */
    public HomePageDTO() {
    }

    /**
     * <p>Gets the statistics on co-piloting projects and available co-pilots.</p>
     *
     * @return a <code>CoPilotStatsDTO</code> providing statistics on co-piloting projects and available co-pilots.
     */
    public CoPilotStatsDTO getCoPilotStats() {
        return this.coPilotStats;
    }

    /**
     * <p>Sets the statistics on co-piloting projects and available co-pilots.</p>
     *
     * @param coPilotStats a <code>CoPilotStatsDTO</code> providing statistics on co-piloting projects and available
     *        co-pilots. 
     */
    public void setCoPilotStats(CoPilotStatsDTO coPilotStats) {
        this.coPilotStats = coPilotStats;
    }

    /**
     * <p>Gets the details on <code>TopCoder Direct</code> facts.</p>
     *
     * @return a <code>TopCoderDirectFactsDTO</code> providing the details on <code>TopCoder Direct</code> facts.
     */
    public TopCoderDirectFactsDTO getTopcoderDirectFacts() {
        return topcoderDirectFacts;
    }

    /**
     * <p>Sets the details on <code>TopCoder Direct</code> facts.</p>
     *
     * @param topcoderDirectFacts a <code>TopCoderDirectFactsDTO</code> providing the details on <code>TopCoder
     *        Direct</code> facts.
     */
    public void setTopcoderDirectFacts(TopCoderDirectFactsDTO topcoderDirectFacts) {
        this.topcoderDirectFacts = topcoderDirectFacts;
    }
}
