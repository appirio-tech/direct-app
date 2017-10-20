/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.configs.ServerConfiguration;
import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.SortOrder;
import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.excel.Row;
import com.topcoder.excel.Sheet;
import com.topcoder.excel.Workbook;
import com.topcoder.excel.impl.ExcelSheet;
import com.topcoder.excel.impl.ExcelWorkbook;
import com.topcoder.excel.output.Biff8WorkbookSaver;
import com.topcoder.excel.output.WorkbookSaver;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.gameplan.GamePlanService;
import com.topcoder.service.util.gameplan.SoftwareProjectData;
import com.topcoder.service.util.gameplan.StudioProjectData;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * <p>
 * Version 1.2 (Module Assembly - TopCoder Cockpit Game Plan JSGantt Version) changes:
 * - Add data generation for the  game plan jsgantt version.
 * </p>
 *
 * <p>
 * Version 1.3 (Module Assembly - TC Cockpit Operations Dashboard)
 * - Add project milestones data to game plan jsgantt version
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
 * <ol>
 *     <li>
 *         Include dependency type id for the jsgantt game plan result
 *     </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.5 (BUGR-7728 Cockpit Game Plan Export Feature - Excel and Microsoft Project Support)
 * <ul>
 *    <li>Add export game plan.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6 (BUGR-8694 TC Cockpit Add project bug races to the project game plan)
 * <ul>
 *     <li>Adds project level bug races to the project game plan</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.7 (Release Assembly - TopCoder Security Groups Release 7)
 * <ul>
 *     <li>Fix the issue when group permission is used, the project name is missing the game plan header</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.8 - Topcoder - Remove JIRA Issues Related Functionality In Direct App v1.0
 * - remove JIRA related functionality
 * </p>
 *
 * @author GreatKevin, Veve, TCSASSEMBLER
 * @version 1.88
 */
public class CurrentProjectGamePlanAction extends AbstractAction implements FormAction<ProjectIdForm> {

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
     * The format for the date in the jsgantt game plan.
     *
     * @since 1.2
     */
    private static final DateFormat JSGANTT_GAME_PLAN_DATE_FORMAT = new SimpleDateFormat("M/d/yyyy");

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
     * The link prefix for the contest details page.
     *
     * @since 1.2
     */
    private static final String CONTEST_DETAIL_LINK = "contest/detail.action?projectId=";

    /**
     * The link prefix for the copilot posting details page.
     */
    private static final String COPILOT_POSTING_LINK = "copilot/copilotContestDetails.action?projectId=";


    /**
     * The link prefix for the project overview page.
     *
     * @since 1.2
     */
    private static final String PROJECT_OVERVIEW_LINK = "projectOverview.action?formData.projectId=";

    /**
     * The project milestone link.
     *
     * @since 1.3
     */
    private static final String MILESTONE_LINK = "projectMilestoneView.action?formData.viewType=list&formData.projectId=";

    /**
     * List of all the available milestone status.
     */
    private static final List ALL_MILESTONE_STATUS = Arrays.asList(MilestoneStatus.values());

    /**
     * The game plan service which is used to retrieve project game plan data.
     */
    private GamePlanService gamePlanService;

    /**
     * The project service facade service.
     *
     * @since 1.7
     */
    private ProjectServiceFacade projectServiceFacade;

    /**
     * The project milestone service.
     *
     * @since 1.3
     */
    private MilestoneService milestoneService;

    /**
     * The input stream which is used to stream returned data.
     */
    private InputStream inputStream;

    /**
     * The buffer for exported data.
     *
     * @since 1.5
     */
    private List<ExportDataBuffer> exportedData;

    /**
     * The direct project name.
     *
     * @since 1.5
     */
    private String directProjectName;

    /**
     * Gets the direct project name.
     *
     * @return the direct project name.
     *
     * @since 1.5
     */
    public String getDirectProjectName() {
        return directProjectName;
    }

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
     * Gets the project milestone service.
     *
     * @return the project milestone service.
     * @since 1.3
     */
    public MilestoneService getMilestoneService() {
        return milestoneService;
    }

    /**
     * Sets the project milestone service.
     *
     * @param milestoneService the project milestone service.
     * @since 1.3
     */
    public void setMilestoneService(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
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
     * Sets the project service facade service.
     *
     * @param projectServiceFacade the project service facade service.
     *
     * @since 1.7
     */
    public void setProjectServiceFacade(ProjectServiceFacade projectServiceFacade) {
        this.projectServiceFacade = projectServiceFacade;
    }

    /**
     * <p>
     * Sets the form data.
     * </p>
     *
     * @param formData the ProjectIdForm to set.
     * @since 1.2
     */
    public void setFormData(ProjectIdForm formData) {
        this.formData = formData;
    }

    /**
     * Generate the game plan data XML which will be consumed by the client side JavaScript
     * Gantt chart.
     *
     * @return the execution response.
     * @throws Exception if any error occurs.
     */
    public String execute() throws Exception {
        generateGamePlanResponseStream(false);
        return SUCCESS;
    }

    /**
     * Generates the game plan data XML which will be consumed by the jsgantt chart.
     *
     * @return the result
     * @throws Exception if any error occurs.
     * @since 1.2
     */
    public String getJsGanttGamePlanData() throws Exception {
        generateGamePlanResponseStream(true);
        return SUCCESS;
    }

    /**
     * Gets the game plan data export.
     *
     * @return the game plan data export.
     * @throws Exception if any error.
     *
     * @since 1.5
     */
    public String getGamePlanDataExcelExport() throws Exception {
        generateGamePlanResponseStream(true);
        return "export";
    }

    /**
     * Gets the game plan exported file name.
     *
     * @return the game plan exported file name.
     *
     * @since 1.5
     */
    public String getExportGamePlanFilename() {
        String fileName = directProjectName;
        fileName = fileName.replaceAll(" ", "_");

        return fileName + "_game_plan.xls";
    }

    /**
     * Generates the game plan response stream
     *
     * @param isJsGantt whether generates for the jsgantt chart.
     * @throws Exception if any error occurs.
     * @since 1.2
     */
    private void generateGamePlanResponseStream(boolean isJsGantt) throws Exception {
        TCSubject user = DirectUtils.getTCSubjectFromSession();

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

            if (data.getTcDirectProjectId() <= 0) {
                data.setTcDirectProjectId(projectId);
            }

            if (data.getTcDirectProjectName() == null) {
                data.setTcDirectProjectName(projectServiceFacade.getProject(user, projectId).getName());
            }

            /* System.out.println("******************************************");

           for (SoftwareProjectData spt : data.getSoftwareProjects()) {
               System.out.println("name:" + spt.getProjectName());
               System.out.println("current status:" + spt.getProjectStatus());
               System.out.println("current phase:" + spt.getCurrentPhase());
               System.out.println("type:" + spt.getProjectType());
               System.out.println("start date:" + spt.getStartDate());
               System.out.println("end date:" + spt.getEndDate());
               System.out.println("dependency ID:" + Arrays.toString(spt.getDependencyProjectIds()));
               System.out.println("dependency type ID:" + Arrays.toString(spt.getDependencyProjectTypeIds()));
               System.out.println("=============================================");

           }

           System.out.println("******************************************");  */

            exportedData = new ArrayList<ExportDataBuffer>();

            if (data == null) {
                responseData = ERROR_HEADER + DATA_RETRIEVAL_ERROR_MSG;
            } else {
                // generate the response data
                responseData = generateProjectGamePlanData(data, isJsGantt, getMilestoneService(), exportedData);
                directProjectName = data.getTcDirectProjectName();
            }


        } else {
            // no project is selected to view, return error message
            responseData = ERROR_HEADER + NO_PROJECT_ERROR_MSG;
        }

        // send response data as plain text
        inputStream = new ByteArrayInputStream(responseData.getBytes("UTF8"));
    }


    /**
     * Generate the game plan XML data with the given DTO TCDirectProjectGamePlanData.
     *
     * <p>
     * Update in version 1.1:
     * - Update the generation codes to inlude bug races for project into the gantt chart.
     * </p>
     *
     * <p>
     * Update in version 1.2:
     * - Add generation codes for the jsgantt chart game plan data.
     * </p>
     *
     * <p>
     * Update in version 1.3 (Module Assembly - TC Cockpit Operations Dashboard):
     * - Add project milestone data to jsgantt game plan.
     * </p>
     *
     * @param gamePlan the TCDirectProjectGamePlanData which stores the data.
     * @param isJSGantt whether generates for the jsgantt
     * @param milestoneService the project milestone service
     * @return the generated data response.
     */
    private static String generateProjectGamePlanData(TCDirectProjectGamePlanData gamePlan, boolean isJSGantt, MilestoneService milestoneService, List<ExportDataBuffer> exportedData) throws Exception {
        // create a string builder to store the XML result
        StringBuilder result = new StringBuilder();

        long directProjectId = gamePlan.getTcDirectProjectId();

        // Get the direct project name from session
        String directProjectName = gamePlan.getTcDirectProjectName();

        Set<Long> contestIds = getAllContestsIdsFromGamePlan(gamePlan);

        // DATA BEGIN
        if (!isJSGantt) {
            // append the data header
            result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

            // get the start date of the project
            Date directProjectStartDate = getDirectProjectStartDate(gamePlan);

            // append the direct project header
            result.append("<projects><project id=\"" + directProjectId + "\" name=\"" +
                    directProjectName + "\" startdate=\"" +
                    (directProjectStartDate == null ? "" : GAME_PLAN_DATE_FORMAT.format(directProjectStartDate)) + "\">");
        } else {
            result.append("<project>");
            result.append(generateProjectGamePlanDataJsGantt(directProjectId, directProjectName));
        }

        List<JsGanttDataBuffer> jsGanttDataBuffer = new ArrayList<JsGanttDataBuffer>();

        if (gamePlan != null) {
            Map<Long, SoftwareProjectData> idMapping = new HashMap<Long, SoftwareProjectData>();

            final List<SoftwareProjectData> softwareProjects = gamePlan.getSoftwareProjects();

            if (isJSGantt) {
                Collections.sort(softwareProjects, new Comparator<SoftwareProjectData>() {
                    public int compare(SoftwareProjectData o1, SoftwareProjectData o2) {
                        return o1.getStartDate().compareTo(o2.getStartDate());
                    }
                });
            }

            // build the id mapping
            for (SoftwareProjectData swc : softwareProjects) {
                idMapping.put(swc.getProjectId(), swc);
            }

            // generate software contest data
            for (SoftwareProjectData swc : softwareProjects) {
                String id = String.valueOf(swc.getProjectId());
                String name = swc.getProjectName();
                String type = swc.getProjectType();

                ExportDataBuffer exportContest = new ExportDataBuffer();

                exportContest.setName(name);
                exportContest.setResourceName(type);
                exportContest.setMilestone(false);
                exportContest.setUniqueID(id);

                if(!isJSGantt) {
                    name = type + " - " + name;
                }

                String startTime = isJSGantt ? JSGANTT_GAME_PLAN_DATE_FORMAT.format(swc.getStartDate()) : GAME_PLAN_DATE_FORMAT.format(swc.getStartDate());
                String finalReviewEndTime = isJSGantt ? JSGANTT_GAME_PLAN_DATE_FORMAT.format(swc.getFinalReviewEndDate()) : GAME_PLAN_DATE_FORMAT.format(swc.getFinalReviewEndDate());
                String endTime = isJSGantt ? JSGANTT_GAME_PLAN_DATE_FORMAT.format(swc.getEndDate()) : GAME_PLAN_DATE_FORMAT.format(swc.getEndDate());
                String contestStatus = swc.getProjectStatus();

                exportContest.setStartDate(swc.getStartDate());
                exportContest.setFinalReviewEndDate(swc.getFinalReviewEndDate());
                exportContest.setEndDate(swc.getEndDate());

                // calculate the duration in hours
                long duration = calculateDuration(swc.getStartDate(), swc.getEndDate());

                // calculate the completion percentage
                long percentage = calculateProgressPercentage(swc.isStarted(), swc.isFinished(), duration,
                        swc.getEndDate());
                if(contestStatus.equals("Draft")) {
                    // set percentage to 0 if draft
                    percentage = 0;
                } else if (contestStatus.contains("Cancelled")) {
                    contestStatus = "Cancelled";
                    percentage = 100;
                }

                if(percentage == 100 && !contestStatus.contains("Cancelled")) {
                    exportContest.setStatus("Completed");
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

                exportContest.setPredecessorId(predecessorId > 0 ? predecessorId : 0L);

                long dependencyTypeId = -1;

                if(predecessorId > 0) {
                    dependencyTypeId = swc.getDependencyProjectTypeIds().length > 0 ? swc.getDependencyProjectTypeIds()[0] : -1;
                }


                if (isJSGantt) {
                    jsGanttDataBuffer.add(new JsGanttDataBuffer(swc.getStartDate(), swc.getEndDate(), generateContestGamePlanDataJsGantt(id, directProjectId, name, type, startTime, finalReviewEndTime,
                            endTime, percentage, predecessorId, dependencyTypeId, contestStatus, null, false)));
                } else {
                    result.append(generateContestGamePlanData(id, name, startTime, duration, percentage, predecessorId, contestStatus));
                }

                exportedData.add(exportContest);
            }

            // generate project milestone data
            if(isJSGantt) {
                final List<Milestone> milestones = milestoneService.getAll(directProjectId, ALL_MILESTONE_STATUS, SortOrder.ASCENDING);

                for(Milestone milestone : milestones) {
                    Date milestoneDate = milestone.isCompleted() ? milestone.getCompletionDate() : milestone.getDueDate();
                    String MilestoneDateStr = JSGANTT_GAME_PLAN_DATE_FORMAT.format(milestoneDate);
                    jsGanttDataBuffer.add(new JsGanttDataBuffer(milestoneDate, milestoneDate, generateContestGamePlanDataJsGantt(String.valueOf(milestone.getId()), directProjectId, milestone.getName(), "Milestone", MilestoneDateStr,
                            MilestoneDateStr, MilestoneDateStr, milestone.isCompleted() ? 100 : 0, -1, -1, milestone.getStatus().name(), null, true) ));

                    ExportDataBuffer exportMilestone = new ExportDataBuffer();
                    exportMilestone.setMilestone(true);
                    exportMilestone.setName(milestone.getName());
                    if(milestone.isCompleted()) {
                        exportMilestone.setStatus("Completed");
                    }
                    exportMilestone.setUniqueID(String.valueOf(milestone.getId()));
                    exportMilestone.setStartDate(milestoneDate);
                    exportMilestone.setFinalReviewEndDate(milestoneDate);
                    exportMilestone.setEndDate(milestoneDate);
                    exportMilestone.setResourceName("Milestone");
                    exportedData.add(exportMilestone);
                }
            }
        }


        // append the direct project footer
        if(!isJSGantt) {
            result.append("</project></projects>");
        } else {

            // sort buffer
            Collections.sort(jsGanttDataBuffer);

            for(JsGanttDataBuffer entry : jsGanttDataBuffer) {
                result.append(entry.getDataString());
            }
            result.append("</project>");
        }

        Collections.sort(exportedData);

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
     *
     * Generates the data for direct project for jsgantt.
     *
     * @param directProjectId the id of the direct project.
     * @param directProjectName the name of the direct project.
     * @return generated data
     * @since 1.2
     */
    private static String generateProjectGamePlanDataJsGantt(long directProjectId, String directProjectName) {
        StringBuilder projectData = new StringBuilder();

        projectData.append("<task>");
        projectData.append("<pID>" + directProjectId + "</pID>");
        projectData.append("<pName><![CDATA[" + directProjectName + "]]></pName>");
        projectData.append("<pStart></pStart><pEnd></pEnd><pColor></pColor>");
        projectData.append("<pLink>" + PROJECT_OVERVIEW_LINK + directProjectId + "</pLink>");
        projectData.append("<pMile>0</pMile><pRes></pRes><pComp>0</pComp><pGroup>1</pGroup><pParent>0</pParent>");
        projectData.append("<pOpen>1</pOpen><pDepend/>");
        projectData.append("</task>");
        
        return projectData.toString();
    }


    /**
     * Generates the data for the contest for jsgantt
     *
     * @param id the id of the contest
     * @param directProjectId the id of the direct project
     * @param name the name of the direct project
     * @param contestType the contest type
     * @param startTime the start time
     * @param endTime the end time
     * @param percentage the completion percentage
     * @param predecessorId the id of the predecessor
     * @param status the status
     * @param isMilestone whether is a milestone
     * @return generated data
     * @since 1.2
     */
    private static String generateContestGamePlanDataJsGantt(String id, long directProjectId, String name,
                                                             String contestType, String startTime, String finalReviewEndDate,
                                                             String endTime, long percentage,
                                                             long predecessorId, long dependencyTypeId,
                                                             String status, String key, boolean isMilestone) {
        StringBuilder contestData = new StringBuilder();

        boolean isBugRace = contestType.equalsIgnoreCase("bug race");

        boolean isCopilotPosting = contestType.equalsIgnoreCase("copilot posting");

        String link = "";

        if (isMilestone) {
            link = MILESTONE_LINK + directProjectId;
        } else if (isCopilotPosting) {
            link = COPILOT_POSTING_LINK + id;
        } else {
            link = CONTEST_DETAIL_LINK + id;
        }

        contestData.append("<task>");
        contestData.append("<pID>" + id + "</pID>");
        contestData.append("<pName><![CDATA[" + name + "]]></pName>");
        contestData.append("<pStart>" + startTime + "</pStart>");
        contestData.append("<pReviewEnd>" + finalReviewEndDate + "</pReviewEnd>");
        contestData.append("<pEnd>" + endTime + "</pEnd>");
        contestData.append("<pColor>" + status + "</pColor>");
        contestData.append("<pLink><![CDATA[" + link + "]]></pLink>");
        contestData.append("<pMile>" + (isMilestone ? 1 : 0) + "</pMile>");
        contestData.append("<pRes>" + contestType + "</pRes>");
        contestData.append("<pComp>" + percentage + "</pComp>");
        contestData.append("<pGroup>0</pGroup>");
        contestData.append("<pParent>" + directProjectId + "</pParent>");
        contestData.append("<pOpen>1</pOpen>");
        contestData.append("<pDepend>" + (predecessorId > 0 ? predecessorId : "") + "</pDepend>");
        contestData.append("<pDependType>" + (dependencyTypeId > 0 ? dependencyTypeId : "") + "</pDependType>");
        contestData.append("<pCaption></pCaption>");
        contestData.append("</task>");

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
    private static Date getDirectProjectStartDate(TCDirectProjectGamePlanData gamePlan) {
        if (gamePlan.getSoftwareProjects().size() == 0
                && gamePlan.getStudioProjects().size() == 0) {
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

    /**
     * Class to store the generated jsgantt contest data for sorting
     *
     * @since 1.2
     */
    private static class JsGanttDataBuffer implements Comparable<JsGanttDataBuffer> {

        /**
         * The generated data string.
         */
        private String dataString;

        /**
         * The start date.
         */
        private Date startDate;

        /**
         * The end date.
         */
        private Date endDate;

        /**
         * Creates a new JsGanttDataBuffer.
         *
         * @param startDate the start date.
         * @param endDate the end date.
         * @param dataString the data string.
         */
        public JsGanttDataBuffer(Date startDate, Date endDate, String dataString) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.dataString = dataString;
        }

        /**
         * Gets the data string.
         *
         * @return the data string.
         */
        public String getDataString() {
            return dataString;
        }

        /**
         * Gets the start date.
         *
         * @return the start date.
         */
        public Date getStartDate() {
            return startDate;
        }


        /**
         * Gets the end date.
         *
         * @return the end date.
         */
        public Date getEndDate() {
            return endDate;
        }


        /**
         * Compares to another JsGanttDataBuffer.
         *
         * @param toCompare another JsGanttDataBuffer to compare
         * @return the compare result
         */
        public int compareTo(JsGanttDataBuffer toCompare) {
            int result = startDate.compareTo(toCompare.getStartDate());
            if (result == 0) {
               return endDate.compareTo(toCompare.getEndDate());
            }

            return result;
        }
    }

    /**
     * Gets the input stream for the game plan export.
     *
     * @return the input stream for the game plan export.
     * @since 1.5
     */
    public InputStream getGamePlanExport() {
        try {

            Workbook workbook = new ExcelWorkbook();
            Sheet sheet = new ExcelSheet("Game Plan", (ExcelWorkbook) workbook);


            // the date format used for displaying 'completion date'
            DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

            // set up the sheet header first
            Row row = sheet.getRow(1);
            int index = 1;

            row.getCell(index++).setStringValue("ID");
            row.getCell(index++).setStringValue("Name");
            row.getCell(index++).setStringValue("Start");
            row.getCell(index++).setStringValue("Final Review End");
            row.getCell(index++).setStringValue("Approval End");
            row.getCell(index++).setStringValue("Predecessors");
            row.getCell(index++).setStringValue("Resource Names");
            row.getCell(index++).setStringValue("Status");
            row.getCell(index).setStringValue("Milestone");

            // insert sheet data from 2nd row
            int rowIndex = 2;
            Map<String, Integer> uniqueIDtoIndexIdMapping = new HashMap<String, Integer>();

            int count = 1;
            for(ExportDataBuffer data : exportedData) {
                uniqueIDtoIndexIdMapping.put(data.getUniqueID(), count++);
            }

            count = 1;
            for(ExportDataBuffer data: exportedData) {
                // project name
                row = sheet.getRow(rowIndex++);

                row.getCell(1).setStringValue(String.valueOf(count++));
                row.getCell(2).setStringValue(data.getName());
                row.getCell(3).setStringValue(dateFormatter.format(data.getStartDate()));
                row.getCell(4).setStringValue(dateFormatter.format(data.getFinalReviewEndDate()));
                row.getCell(5).setStringValue(dateFormatter.format(data.getEndDate()));
                if(data.getPredecessorId() > 0) {
                    row.getCell(6).setStringValue(uniqueIDtoIndexIdMapping.get(String.valueOf(data.getPredecessorId())).toString());
                }
                row.getCell(7).setStringValue(data.getResourceName());
                if(data.getStatus() != null) {
                    row.getCell(8).setStringValue(data.getStatus());
                }
                row.getCell(9).setStringValue(data.isMilestone() ? "Yes" : "No");
            }

            workbook.addSheet(sheet);

            // Create a new WorkBookSaver
            WorkbookSaver saver = new Biff8WorkbookSaver();
            ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
            saver.save(workbook, saveTo);
            return new ByteArrayInputStream(saveTo.toByteArray());

        } catch (Throwable e) {
            e.printStackTrace(System.err);
            throw new IllegalStateException(e);
        }
    }


    private static class ExportDataBuffer implements Comparable<ExportDataBuffer> {

        /**
         * The name.
         */
        private String name;

        /**
         * The start date.
         */
        private Date startDate;

        /**
         * The final review end date.
         */
        private Date finalReviewEndDate;

        /**
         * The end date.
         */
        private Date endDate;

        /**
         * The duration in days
         */
        private int duration;

        private long predecessorId;

        private String resourceName;

        private String status;

        private boolean isMilestone;

        private String uniqueID;

        /**
         * Gets the start date.
         *
         * @return the start date.
         */
        public Date getStartDate() {
            return startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        private Date getFinalReviewEndDate() {
            return finalReviewEndDate;
        }

        private void setFinalReviewEndDate(Date finalReviewEndDate) {
            this.finalReviewEndDate = finalReviewEndDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public long getPredecessorId() {
            return predecessorId;
        }

        public void setPredecessorId(long predecessorId) {
            this.predecessorId = predecessorId;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean isMilestone() {
            return isMilestone;
        }

        public void setMilestone(boolean milestone) {
            isMilestone = milestone;
        }

        public String getUniqueID() {
            return uniqueID;
        }

        public void setUniqueID(String uniqueID) {
            this.uniqueID = uniqueID;
        }

        /**
         * Compares to another ExportDataBuffer.
         *
         * @param toCompare another ExportDataBuffer to compare
         * @return the compare result
         */
        public int compareTo(ExportDataBuffer toCompare) {
            int result = startDate.compareTo(toCompare.getStartDate());
            if (result == 0) {
                return endDate.compareTo(toCompare.getEndDate());
            }

            return result;
        }
    }

}
