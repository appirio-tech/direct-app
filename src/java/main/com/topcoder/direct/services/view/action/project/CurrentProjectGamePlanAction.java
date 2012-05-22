/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.dto.TcJiraIssue;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.direct.services.view.util.jira.JiraRpcServiceWrapper;
import com.topcoder.security.TCSubject;
import com.topcoder.service.gameplan.GamePlanService;
import com.topcoder.service.util.gameplan.SoftwareProjectData;
import com.topcoder.service.util.gameplan.StudioProjectData;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanData;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>A <code>Struts</code> action to be used for generating the project game plan data for the client
 * side JavaScript Gantt chart to consume. This action will return the raw XML data in plain text.</p>
 * <p/>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread
 * safety is not required (instead in Struts 1 the thread safety is required because the action instances are reused).
 * This class is mutable and stateful: it's not thread safe.
 * </p>
 *
 * <p>
 * Version 1.1 updates:
 * - Include bug races into the game plan.
 * </p>
 *
 * @author GreatKevin
 * @version 1.1 (TopCoder Cockpit - Bug Race Project Contests View)
 */
public class CurrentProjectGamePlanAction extends AbstractAction {

    /**
     * <p>A <code>ProjectIdForm</code> providing the ID of a requested project.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();
    
    /**
     * The error message header which is used to distinguish the action error message.
     */
    private static final String ERROR_HEADER = "GamePlanActionError:";

    /**
     * The error message returned when the action fails to get data from Game Plan Service.
     */
    private static final String DATA_RETRIEVAL_ERROR_MSG = "Failed to retrieve project game plan" +
            " data from Game Plan Service";

    /**
     * The date format which is used to format all the dates in the game plan.
     */
    private static final DateFormat GAME_PLAN_DATE_FORMAT = new SimpleDateFormat("yyyy,M,d");

    /**
     * The error message returned when no project is selected to view.
     */
    private static final String NO_PROJECT_ERROR_MSG = "No project is selected to view, please select a project first.";

    /**
     * The default percentage when the elapsed time exceeds contest duration, but the contest
     * is not finished. This may happen when the contest is hang or late for some reason.
     */
    private static final int NOT_FINISHED_PERCENTAGE = 85;

    /**
     * The game plan service which is used to retrieve project game plan data.
     */
    private GamePlanService gamePlanService;

    /**
     * The input stream which is used to stream returned data.
     */
    private InputStream inputStream;

    /**
     * Getter for the game plan service.
     *
     * @return the game plan service.
     */
    public GamePlanService getGamePlanService() {
        return gamePlanService;
    }

    /**
     * Setter for the game plan service.
     *
     * @param gamePlanService the game plan service to set.
     */
    public void setGamePlanService(GamePlanService gamePlanService) {
        this.gamePlanService = gamePlanService;
    }

    /**
     * Getter for the input stream.
     *
     * @return the input stream used for result streaming.
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>Object</code> providing the data for form submitted by user..
     */
    public ProjectIdForm getFormData() {
        return this.formData;
    }

    /**
     * Generate the game plan data XML which will be consumed by the client side JavaScript
     * Gantt chart.
     *
     * @return the execution response.
     * @throws Exception if any error occurs.
     */
    public String execute() throws Exception {
        // Get current user from session
        TCSubject user = getCurrentUser();

        long projectId = -1;
        // string which is used to store response data
        String responseData;

        if (this.getSessionData().getCurrentProjectContext() != null || getFormData() != null) {
            // Get current selected project from session
            projectId = this.getSessionData().getCurrentProjectContext().getId();

            if(getFormData().getProjectId() > 0) {
                projectId = getFormData().getProjectId();
            }

            // Get project game plan data from Game Plan Service
            TCDirectProjectGamePlanData data = getGamePlanService().retrieveGamePlanData(user, projectId);

            List<SoftwareProjectData> softwareProjects = data.getSoftwareProjects();

            System.out.println("******************************************");

            for (SoftwareProjectData spt : softwareProjects) {
                System.out.println("name:" + spt.getProjectName());
                System.out.println("current status:" + spt.getProjectStatus());
                System.out.println("current phase:" + spt.getCurrentPhase());
                System.out.println("type:" + spt.getProjectType());
                System.out.println("start date:" + spt.getStartDate());
                System.out.println("end date:" + spt.getEndDate());
                System.out.println("dependency ID:" + Arrays.toString(spt.getDependencyProjectIds()));
                System.out.println("=============================================");

            }

            System.out.println("******************************************");

            if (data == null) {
                responseData = ERROR_HEADER + DATA_RETRIEVAL_ERROR_MSG;
            } else {
                // generate the response data
                responseData = generateProjectGamePlanData(data);
            }


        } else {
            // no project is selected to view, return error message
            responseData = ERROR_HEADER + NO_PROJECT_ERROR_MSG;
        }

        // send response data as plain text
        inputStream = new ByteArrayInputStream(responseData.getBytes("UTF8"));

        return SUCCESS;
    }

    /**
     * Gets current user from the request.
     *
     * @return current user as TCSubject or null if request is null.
     */
    private TCSubject getCurrentUser() {
        // get HttpServletRequest
        HttpServletRequest request = DirectUtils.getServletRequest();

        if (request == null) {
            // return null if request does not exist.
            return null;
        }

        return new SessionData(request.getSession()).getCurrentUser();
    }


    /**
     * Generate the game plan XML data with the given DTO TCDirectProjectGamePlanData.
     *
     * <p>
     * Update in version 1.1:
     * - Update the generation codes to inlude bug races for project into the gantt chart.
     * </p>
     *
     * @param gamePlan the TCDirectProjectGamePlanData which stores the data.
     * @return the generated data response.
     */
    private static String generateProjectGamePlanData(TCDirectProjectGamePlanData gamePlan) throws Exception {
        // create a string builder to store the XML result
        StringBuilder result = new StringBuilder();

        // append the data header
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><projects>");

        long directProjectId = gamePlan.getTcDirectProjectId();
        // Get the direct project name from session
        String directProjectName = gamePlan.getTcDirectProjectName();

        // the list to store bug race of the project
        List<TcJiraIssue> bugRaceForDirectProject = null;

        Set<Long> contestIds = getAllContestsIdsFromGamePlan(gamePlan);

        if(contestIds.size() > 0) {
            // if there are contest IDs, search for the bug races
            bugRaceForDirectProject = JiraRpcServiceWrapper.getBugRaceForDirectProject(contestIds);
        }

        // get the start date of the project
        Date directProjectStartDate = getDirectProjectStartDate(gamePlan, bugRaceForDirectProject);

        // append the direct project header
        result.append("<project id=\"" + directProjectId + "\" name=\"" +
                directProjectName + "\" startdate=\"" +
                (directProjectStartDate == null ? "" : GAME_PLAN_DATE_FORMAT.format(directProjectStartDate)) + "\">");

        if (gamePlan != null) {

            // generate studio contests data
            for (StudioProjectData sc : gamePlan.getStudioProjects()) {
                String id = String.valueOf(sc.getContestId());
                String name = sc.getContestName() + " (" + sc.getContestType() + ")";
                String startTime = GAME_PLAN_DATE_FORMAT.format(sc.getStartDate());
                // calculate the duration in hours
                long duration = calculateDuration(sc.getStartDate(), sc.getEndDate());
                long percentage = calculateProgressPercentage(sc.isStarted(), sc.isFinished(), duration, sc.getEndDate());

                result.append(generateContestGamePlanData(id, name, startTime, duration, percentage, -1, sc.getContestStatus()));
            }

            Map<Long, SoftwareProjectData> idMapping = new HashMap<Long, SoftwareProjectData>();

            // build the id mapping
            for (SoftwareProjectData swc : gamePlan.getSoftwareProjects()) {
                idMapping.put(swc.getProjectId(), swc);
            }

            // generate software contest data
            for (SoftwareProjectData swc : gamePlan.getSoftwareProjects()) {
                String id = String.valueOf(swc.getProjectId());
                String name = swc.getProjectType() + " - " + swc.getProjectName();
                String startTime = GAME_PLAN_DATE_FORMAT.format(swc.getStartDate());
                // calculate the duration in hours
                long duration = calculateDuration(swc.getStartDate(), swc.getEndDate());
                long percentage = calculateProgressPercentage(swc.isStarted(), swc.isFinished(), duration,
                        swc.getEndDate());

                String contestStatus = swc.getProjectStatus();

                if(contestStatus.equals("Draft")) {
                    // set percentage to 0 if draft
                    percentage = 0;
                } else if (contestStatus.contains("Cancelled")) {
                    contestStatus = "Cancelled";
                    percentage = 100;
                }

                // software contest will only have single dependency contest
                long predecessorId = swc.getDependencyProjectIds().length > 0 ? swc.getDependencyProjectIds()[0] : -1;
                
                // check the predecessor's start date
                if (predecessorId > 0) {
                    SoftwareProjectData preSw = idMapping.get(predecessorId);
                    if (preSw != null) {
                        // check startDate
                        if (DirectUtils.setTimeToMidnight(preSw.getStartDate()).compareTo(DirectUtils.setTimeToMidnight(swc.getStartDate())) > 0) {
                            predecessorId = -1;
                        }
                    } else {
                        // does not exist, set to -1
                        predecessorId = -1;
                    }

                }

                result.append(generateContestGamePlanData(id, name, startTime, duration, percentage, predecessorId, contestStatus));
            }


            // generate bug race data
            if (contestIds.size() > 0) {

                for (TcJiraIssue bugRace : bugRaceForDirectProject) {
                    String id = bugRace.getIssueKey();
                    String name = "Bug Race - " + bugRace.getIssueKey() + " " + bugRace.getTitle();
                    String startTime = GAME_PLAN_DATE_FORMAT.format(bugRace.getCreationDate());
                    long duration = calculateDuration(bugRace.getCreationDate(), bugRace.getEndDate());
                    String contestLikeStatus = bugRace.getContestLikeStatus();
                    boolean isFinished = contestLikeStatus.equals("Completed")
                            || contestLikeStatus.equals("Cancelled")
                            || contestLikeStatus.equals("On Hold");
                    long percentage = calculateProgressPercentage(true, isFinished, duration, bugRace.getEndDate());

                    if(contestLikeStatus.toLowerCase().equals("on hold") || contestLikeStatus.toLowerCase().equals("n/a")) {
                        contestLikeStatus = "cancelled";
                    }

                    result.append(generateContestGamePlanData(id, name, startTime, duration, percentage, -1, contestLikeStatus));
                }
            }

        }


        // append the direct project footer
        result.append("</project></projects>");

        return result.toString();

    }

    /**
     * Calculate contest progress percentage.
     *
     * @param isStarted  flag to determine whether the contest is started.
     * @param isFinished flag to determine whether the contest is finished.
     * @param duration   the contest duration
     * @param end        the end date of the contest
     * @return the calculated percentage.
     */
    private static long calculateProgressPercentage(boolean isStarted, boolean isFinished, long duration, Date end) {
        if (isFinished) {
            // finished, return 100
            return 100;
        }

        if (!isStarted) {
            // not started, return 0
            return 0;
        }

        // not finished, but started, perform calculation
        long currentDuration = calculateDuration(new Date(), end);

        if (currentDuration >= duration) {
            // return this constant percentage when the
            // contest is late for some reason
            return NOT_FINISHED_PERCENTAGE;
        } else {
            return (long) ((duration - currentDuration) * 1.0 / (duration) * 100);
        }

    }

    /**
     * Helper method to generate the data for each single contest.
     *
     * @param id            the id of the contest.
     * @param name          the name of the contest.
     * @param startTime     the start date of the contest.
     * @param duration      the duration of the contest.
     * @param percentage    the contest progress percentage.
     * @param predecessorId the contest id this contest depends on.
     * @param status the contest status
     * @return the generated data in string format.
     */
    private static String generateContestGamePlanData(String id, String name, String startTime, long duration, long percentage,
                                               long predecessorId, String status) {
        StringBuilder contestData = new StringBuilder();

        contestData.append("<task id=\"" + id + "\" status=\"" + status + "\">");
        contestData.append("<name><![CDATA[" + name + "]]></name>");
        contestData.append("<est>" + startTime + "</est>");
        contestData.append("<duration>" + duration + "</duration>");
        contestData.append("<percentcompleted>" + percentage + "</percentcompleted>");
        contestData.append("<predecessortasks>" + (predecessorId > 0 ? predecessorId : "") + "</predecessortasks>");
        contestData.append("<childtasks></childtasks></task>");

        return contestData.toString();
    }

    /**
     * Calculate the duration between the given two dates in hours. It
     *
     * @param startDate the start date.
     * @param endDate   the end date.
     * @return the duration in hours.
     */
    private static long calculateDuration(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60);
    }

    /**
     * Calculate the direct project start date. It picks up the earliest start date
     * among all the studio and software contests as the direct project start date.
     *
     * <p>
     * Version 1.1 TopCoder Cockpit - Bug Race Project Contests View Updates:
     * - update calculation logic to consider bug races start date too
     * </p>
     *
     * @param gamePlan the game plan data which stores project contests.
     * @param bugRaceForDirectProject the bug races of the project.
     * @return the calculated project start date.
     */
    private static Date getDirectProjectStartDate(TCDirectProjectGamePlanData gamePlan, List<TcJiraIssue> bugRaceForDirectProject) {
        if (gamePlan.getSoftwareProjects().size() == 0
                && gamePlan.getStudioProjects().size() == 0
                && (bugRaceForDirectProject == null || bugRaceForDirectProject.size() == 0) ) {
            // if there is no software & studio contests & bug races, return null for start date
            return null;
        }

        // initialize the startDate to a maximum date for comparing
        Date startDate = new Date(Long.MAX_VALUE);

        List<StudioProjectData> studioContests = gamePlan.getStudioProjects();
        List<SoftwareProjectData> softwareContests = gamePlan.getSoftwareProjects();

        // get the minimum start date from all the studio and software contests
        // check studio contests first
        for (StudioProjectData studioData : studioContests) {
            if (studioData.getStartDate().before(startDate)) {
                startDate = studioData.getStartDate();
            }
        }

        // check software contests
        for (SoftwareProjectData softwareData : softwareContests) {
            if (softwareData.getStartDate().before(startDate)) {
                startDate = softwareData.getStartDate();
            }
        }

        // check bug races
        if(bugRaceForDirectProject != null && bugRaceForDirectProject.size() > 0) {
            for(TcJiraIssue bugRace : bugRaceForDirectProject) {
                if(bugRace.getCreationDate().before(startDate)) {
                    startDate = bugRace.getCreationDate();
                }
            }
        }

        return startDate;
    }

    /**
     * Gets the all the contest ids of the software contests and studio contests in the game plan.
     *
     * @param gamePlan the game plan data.
     * @return the set of contest ids.
     * @since 1.1
     */
    private static Set<Long> getAllContestsIdsFromGamePlan(TCDirectProjectGamePlanData gamePlan) {
        Set<Long> contestIds = new HashSet<Long>();
        for (StudioProjectData studioContest : gamePlan.getStudioProjects()) {
            contestIds.add(studioContest.getContestId());
        }
        for (SoftwareProjectData softwareContest : gamePlan.getSoftwareProjects()) {
            contestIds.add(softwareContest.getProjectId());
        }

        return contestIds;
    }
}
