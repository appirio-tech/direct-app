/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.roadmap;

import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.SortOrder;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DataProvider;
import viecili.jrss.generator.RSSFeedGenerator;
import viecili.jrss.generator.RSSFeedGeneratorImpl;
import viecili.jrss.generator.elem.Channel;
import viecili.jrss.generator.elem.Item;
import viecili.jrss.generator.elem.RSS;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This action gets the milestones (roadmap) for the configured client. It also handles request to get the RSS 2.0
 * feed of TopCoder milestones. The configuration of client is done by spring injection via applicationContext.xml
 * to the field <code>clientName</code>.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 * @since 1.0 (Module Assembly - TC Cockpit Public Page for TopCoder Road map and RSS syndication)
 */
public class PublicRoadMapAction extends BaseDirectStrutsAction {

    /**
     * The default filter range for the completed milestones.
     */
    private static final int DEFAULT_COMPLETED_MILESTONE_PAST_DAYS = 365;

    /**
     * The date format for displaying due date and completion date of milestone.
     */
    private static final DateFormat RELEASE_DATE_FORMAT = new SimpleDateFormat("MMMMM dd, yyyy");

    /**
     * The name of the client to get milestones, set via spring injection.
     */
    private String clientName;

    /**
     * The day filter range for the completed milestones, set via spring injection.
     */
    private int completedMilestonePastDays;


    /**
     * The title of the RSS channel, set via spring injection.
     */
    private String channelTitle;

    /**
     * The description of RSS channel, set via spring injection.
     */
    private String channelDescription;

    /**
     * The link of RSS channel, set via spring injection.
     */
    private String channelLink;

    /**
     * The map of all projects (projectID -> ProjectName) for the client.
     */
    private Map<Long, String> directProjectsForClient;

    /**
     * The rss feed input stream.
     */
    private InputStream rssFeed;

    /**
     * <p>
     * Generates the ajax json result for the public road map page and send back to the view page.
     * </p>
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {

        // the map to store the json result
        Map<String, List<Map<String, String>>> jsonResultMap = new HashMap<String, List<Map<String, String>>>();
        jsonResultMap.put("overdue", new ArrayList<Map<String, String>>());
        jsonResultMap.put("upcoming", new ArrayList<Map<String, String>>());
        jsonResultMap.put("completed", new ArrayList<Map<String, String>>());

        final Map<MilestoneStatus, List<Milestone>> allForProjectsGroupedByStatus = getMilestonesForClient(true);

        if (allForProjectsGroupedByStatus == null) {
            // set empty result
            setResult(jsonResultMap);
            return;
        }

        // add overdue
        for (Milestone m : allForProjectsGroupedByStatus.get(MilestoneStatus.OVERDUE)) {
            jsonResultMap.get("overdue").add(getMilestoneJsonMap(m, directProjectsForClient));
        }
        // add upcoming
        for (Milestone m : allForProjectsGroupedByStatus.get(MilestoneStatus.UPCOMING)) {
            jsonResultMap.get("upcoming").add(getMilestoneJsonMap(m, directProjectsForClient));
        }
        // add completed
        // filter by completedMilestonePastDays
        long time = new Date().getTime() - getCompletedMilestonePastDays() * 24L * 60 * 60 * 1000;
        for (Milestone m : allForProjectsGroupedByStatus.get(MilestoneStatus.COMPLETED)) {
            if (m.getCompletionDate().getTime() >= time) {
                jsonResultMap.get("completed").add(getMilestoneJsonMap(m, directProjectsForClient));
            }
        }

        setResult(jsonResultMap);
    }

    /**
     * Handles the action request to get the RSS 2.0 feed for the client roadmap.
     *
     * @return the action result code.
     * @throws Exception if there is any error.
     */
    public String getClientRoadMapRSS() throws Exception {
        List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();

        try {

            // create the RSS
            RSS roadmapRSS = new RSS();

            // create the channel for the RSS
            Channel rssChannel = new Channel(channelTitle, channelLink, channelDescription);

            // Get the milestone for the client without completed milestones
            final Map<MilestoneStatus, List<Milestone>> allForProjectsGroupedByStatus = getMilestonesForClient(false);

            List<Milestone> allMilestones = new ArrayList<Milestone>();

            // add overdue first - overdue has due date earlier than upcoming
            allMilestones.addAll(allForProjectsGroupedByStatus.get(MilestoneStatus.OVERDUE));
            // add upcoming
            allMilestones.addAll(allForProjectsGroupedByStatus.get(MilestoneStatus.UPCOMING));

            // insert RSS item  - one RSS item per milestone
            for (Milestone m : allMilestones) {
                Item item = new Item(m.getName(), "", getRSSDescriptionForMilestone(m));
                rssChannel.addItem(item);
            }

            roadmapRSS.addChannel(rssChannel);
            RSSFeedGenerator rssGenerator = new RSSFeedGeneratorImpl();

            // generate the feed and put into input stream
            rssFeed = new ByteArrayInputStream(rssGenerator.generateAsString(roadmapRSS).getBytes("UTF8"));

        } catch (Throwable e) {
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * <p>
     * Gets the milestones for the client set in <code>clientName</code>.
     * </p>
     *
     * @param hasCompleted whether to include completed milestones.
     * @return the map of milestone status to the list of milestone of that status.
     * @throws Exception if there is any error.
     */
    private Map<MilestoneStatus, List<Milestone>> getMilestonesForClient(boolean hasCompleted) throws Exception {

        if (getClientName() == null || getClientName().trim().length() == 0) {
            throw new IllegalStateException("The client name for public road map is not initialized");
        }

        if (getMilestoneService() == null) {
            throw new IllegalStateException("The project milestone service is not initialized");
        }

        if (getCompletedMilestonePastDays() <= 0) {
            // if completed milestone past days is not set, use the default one
            setCompletedMilestonePastDays(DEFAULT_COMPLETED_MILESTONE_PAST_DAYS);
        }


        // Gets all the projects for the specified client
        directProjectsForClient = DataProvider.getDirectProjectsForClient(getClientName());

        Map<MilestoneStatus, SortOrder> filters = new HashMap<MilestoneStatus, SortOrder>();

        // create the filters to send to milestone service
        filters.put(MilestoneStatus.OVERDUE, SortOrder.ASCENDING);
        filters.put(MilestoneStatus.UPCOMING, SortOrder.ASCENDING);
        if (hasCompleted) {
            // add completed filter if hasCompleted is true
            filters.put(MilestoneStatus.COMPLETED, SortOrder.DESCENDING);
        }
        if (directProjectsForClient.size() == 0) {
            // return null if no projects for the client
            return null;
        }

        // get the milestones grouped by status from project milestone service
        final Map<MilestoneStatus, List<Milestone>> allForProjectsGroupedByStatus =
                getMilestoneService().getAllForProjectsGroupedByStatus(new ArrayList<Long>(directProjectsForClient.keySet()), filters);

        return allForProjectsGroupedByStatus;

    }

    /**
     * Gets the json map representation of single milestone.
     *
     * @param m the milestone
     * @param directProjects the projects map
     * @return the json map representation of the milestone.
     */
    private Map<String, String> getMilestoneJsonMap(Milestone m, Map<Long, String> directProjects) {
        Map<String, String> result = new HashMap<String, String>();

        result.put("project", directProjects.get(m.getProjectId()));
        result.put("milestone", m.getName());
        result.put("detail", m.getDescription());
        result.put("date", RELEASE_DATE_FORMAT.format(m.getStatus().equals(MilestoneStatus.COMPLETED) ?
                m.getCompletionDate() : m.getDueDate()));

        return result;
    }

    /**
     * Gets the RSS item description for the milestone.
     *
     * @param m the milestone.
     * @return the RSS item description.
     */
    private String getRSSDescriptionForMilestone(Milestone m) {

        StringBuilder sb = new StringBuilder();
        sb.append("Project: ");
        sb.append(directProjectsForClient.get(m.getProjectId()));
        sb.append("<br/>");
        sb.append("Due Date: ");
        sb.append(RELEASE_DATE_FORMAT.format(m.getDueDate()));
        sb.append("<br/>");
        sb.append("Responsible Person Handle: ");
        sb.append((m.getOwners() != null && m.getOwners().size() > 0) ? m.getOwners().get(0).getName() : "None");
        sb.append("<br/>");
        sb.append("Description: ");
        sb.append(m.getDescription());

        return sb.toString();
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
     * Sets the client name
     *
     * @param clientName the client name.
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Gets the filter date range in days for the completed milestones.
     *
     * @return the filter date range in days for the completed milestones.
     */
    public int getCompletedMilestonePastDays() {
        return completedMilestonePastDays;
    }

    /**
     * Sets the filter date range in days for the completed milestones.
     *
     * @param completedMilestonePastDays the filter date range in days for the completed milestones.
     */
    public void setCompletedMilestonePastDays(int completedMilestonePastDays) {
        this.completedMilestonePastDays = completedMilestonePastDays;
    }

    /**
     * Sets the title of the RSS channel.
     *
     * @param channelTitle the title of the RSS channel.
     */
    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    /**
     * Sets the description of the RSS channel.
     *
     * @param channelDescription the description of the RSS channel.
     */
    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    /**
     * Sets the link of the RSS channel.
     *
     * @param channelLink the link of the RSS channel.
     */
    public void setChannelLink(String channelLink) {
        this.channelLink = channelLink;
    }

    /**
     * Gets the RSS feed as input stream.
     *
     * @return the RSS feed as input stream.
     */
    public InputStream getRssFeed() {
        return rssFeed;
    }
}
