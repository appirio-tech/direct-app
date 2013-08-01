/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.marathonmatch.service.dto.CompetitorInfoDTO;
import com.topcoder.marathonmatch.service.dto.MMCommonInfoDTO;

import java.util.List;

/**
 * The common dto for marathon match contest. This class hold the data for dashboard part in contest page.
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 *
 * <p>
 *     Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab
 *     <ol>
 *         <li>Add property {@link #active} to represent active/past contest. </li>
 *     </ol>
 * </p>
 *
 * @author Ghost_141
 * @version 1.1
 * @since 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Dashboard and Submissions Tab)
 */
public class MarathonMatchCommonDTO extends BaseContestCommonDTO {
    /**
     * The Common info dTO.
     */
    private MMCommonInfoDTO commonInfo;

    /**
     * The top competitors information of this marathon match.
     */
    private List<CompetitorInfoDTO> topCompetitors;

    /**
     * The JSON data used for showing the TimeLine Graph See the front-end assembly spec for expected format
     */
    private String timeLineGraphData;

    /**
     * Represent the contest is active or not. true if the contest is active otherwise false.
     * @since 1.1
     */
    private boolean active = false;

    /**
     * Create an instance of <code>MarathonMatchCommonDTO</code>.
     */
    public MarathonMatchCommonDTO() {
        //Empty
    }

    /**
     * Gets top competitors.
     *
     * @return the top competitors
     */
    public List<CompetitorInfoDTO> getTopCompetitors() {
        return topCompetitors;
    }

    /**
     * Sets top competitors.
     *
     * @param topCompetitors the top competitors
     */
    public void setTopCompetitors(List<CompetitorInfoDTO> topCompetitors) {
        this.topCompetitors = topCompetitors;
    }

    /**
     * Gets common info.
     *
     * @return the common info
     */
    public MMCommonInfoDTO getCommonInfo() {
        return commonInfo;
    }

    /**
     * Sets common info.
     *
     * @param commonInfo the common info
     */
    public void setCommonInfo(MMCommonInfoDTO commonInfo) {
        this.commonInfo = commonInfo;
    }

    /**
     * Gets time line graph data.
     *
     * @return the time line graph data
     */
    public String getTimeLineGraphData() {
        return timeLineGraphData;
    }

    /**
     * Sets time line graph data.
     *
     * @param timeLineGraphData the time line graph data
     */
    public void setTimeLineGraphData(String timeLineGraphData) {
        this.timeLineGraphData = timeLineGraphData;
    }

    /**
     * Is active.
     *
     * @return the boolean
     * @since 1.1
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active
     * @since 1.1
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
