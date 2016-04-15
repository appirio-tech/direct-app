/*
 * Copyright (C) 2015 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;


import com.appirio.client.asp.ASPClient;
import com.appirio.client.asp.ASPClientFactory;
import com.appirio.client.asp.api.SubmissionFile;
import com.appirio.client.asp.api.WorkStep;
import com.topcoder.direct.services.configs.ServerConfiguration;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.contest.launch.MimeTypeRetriever;
import com.topcoder.direct.services.view.dto.SoftwareContestWinnerDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.ContestFinalFixDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseStatus;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseType;
import com.topcoder.direct.services.view.dto.contest.SoftwareContestSubmissionsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.project.phases.Phase;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Action supporting project work management page.
 *
 * <p>
 * Version 1.1 Change notes:
 *   <ol>
 *     <li>Added {@link #resourceManager} property.</li>
 *     <li>Added {@link #resolveSubmitter(Submission)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (TOPCODER DIRECT - ASP INTEGRATION WORK MANAGEMENT IMPROVEMENT) Change notes:
 *   <ol>
 *     <li>Temporarily switched {@link ASPClient} into <code>demo</code> mode just for sake of testing/reviewing this
 *     submission.</li>
 *     <li>Added {@link SubmissionPusher} class.</li>
 *     <li>Added {@link #pushId} property.</li>
 *     <li>Added {@link #getSubmissionPushStatus()} method.</li>
 *     <li>Moved the code pushing the submissions to separate thread.</li>
 *   </ol>
 * </p>
 *
 * @author isv
 * @version 1.2
 */
public class ProjectWorkManagementAction extends BaseDirectStrutsAction implements FormAction<ProjectIdForm> {

    /**
     * the phase name of the submission phase.
     */
    private static final String SUBMISSION_PHASE = "submission";

    /**
     * The phase name of the checkpoint submission phase.
     */
    private static final String CHECKPOINT_SUBMISSION_PHASE = "checkpoint_submission";

    /**
     * The phase name of the final fix phase.
     */
    private static final String FINAL_FIX_PHASE = "final_fix";

    /**
     * The work step name.
     */
    private String workStepName;

    /**
     * The base path of the studio contest submissions. Injected via spring.
     */
    private String studioSubmissionBase;


    /**
     * The base path of the software contest submissions. Injected via spring.
     *
     * @since 1.1
     */
    private String softwareSubmissionBase;

    /**
     * The contest id.
     */
    private long contestId;

    /**
     * The phase name.
     */
    private String phaseName;

    /**
     * The id of the work step.
     */
    private String workStepId;

    /**
     * The list of work steps of a project.
     */
    private List<WorkStep> workSteps;

    /**
     * The cache maps user id to user handle.
     */
    private Map<Long, String> userHandlesMap;

    /**
     * The demand work id of the direct project.
     */
    private String demandWorkId;

    /**
     * <p>A <code>long</code> providing the value for pushId property.</p>
     *
     * @since 1.2
     */
    private long pushId;

    /**
     * <p>
     * Represents the MIME type retriever used to retrieve the MIME types.
     * </p>
     * <p/>
     * <p>
     * It's injected by the setter and it will be not null when the logic of the action is performed.
     * </p>
     */
    private MimeTypeRetriever mimeTypeRetriever;

    /**
     * <p>A <code>ResourceManager</code> providing the value for resourceManager property.</p>
     *
     * @since 1.1
     */
    private ResourceManager resourceManager;

    /**
     * Logger for this class.
     */
    private static final Logger logger = Logger.getLogger(ProjectWorkManagementAction.class);

    /**
     * <p>A <code>ProjectIdForm</code> providing the ID of a requested project.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * The display name for the work step type. The key is workstep type name in enum, the value is the display name in direct.
     */
    private static final Map<String, String> WORK_STEP_DISPLAY_NAME = new HashMap<String, String>();

    /**
     * Static initializer which initialize the map which maps the step type to its display name.
     */
    static {
        WORK_STEP_DISPLAY_NAME.put(WorkStep.StepType.designConcepts.name(), "Design Concepts");
        WORK_STEP_DISPLAY_NAME.put(WorkStep.StepType.completeDesigns.name(), "Complete Designs");
        WORK_STEP_DISPLAY_NAME.put(WorkStep.StepType.finalFixes.name(), "Final Fixes");
        WORK_STEP_DISPLAY_NAME.put(WorkStep.StepType.code.name(), "Code");
        WORK_STEP_DISPLAY_NAME.put(WorkStep.StepType.codeFinalFixes.name(), "Code Final Fixes");
    }

    /**
     * Gets the the jwt cookie for current user.
     *
     * @return the jwt cookie.
     * @throws Exception if there is no jwt cookie for current user.
     */
    private String getJWTCookieValue() throws Exception {
        HttpServletRequest servletRequest = DirectUtils.getServletRequest();
        Cookie[] cookies = servletRequest.getCookies();
        String tcjwt = null;
        for (Cookie c : cookies) {
            if (c != null && c.getName().equalsIgnoreCase(ServerConfiguration.JWT_COOOKIE_KEY)) {
                tcjwt = c.getValue();
            }
        }

        if (tcjwt != null && tcjwt.trim().length() > 0) {
            return tcjwt;
        } else {
            String errorMessage = "You don't have required jwt cookie to access the work manager page."
                    + " Please try login again.";
            DirectUtils.setErrorMessageInErrorPage(errorMessage);
            throw new Exception(errorMessage);
        }
    }


    /**
     * Checks whether current user has permission to access the work manager.
     *
     * @throws Exception if any error.
     */
    private void checkPermission() throws Exception {
        if (!AuthorizationProvider.isUserGrantedToAccessWorkManager(
                DirectUtils.getTCSubjectFromSession(),
                getFormData().getProjectId())) {
            String errorMessage = "You don't have permission to access the work manager page.";
            DirectUtils.setErrorMessageInErrorPage(errorMessage);
            throw new Exception(errorMessage);
        }
    }


    /**
     * The action redirects to project-work-management.jsp
     *
     * @throws Exception
     */
    @Override
    protected void executeAction() throws Exception {

        // check jwt cookie
        getJWTCookieValue();

        // check user permission
        checkPermission();

        // sets the demand work id.
        this.setDemandWorkId(DataProvider.getDirectProjectDemandWorkId(this.getFormData().getProjectId()));
    }

    /**
     * The ajax action to get the work steps via asp client for current project.
     *
     * @return the result code.
     */
    public String getDirectProjectWorkSteps() {
        try {

            String tcjwt = getJWTCookieValue();

            checkPermission();

            ASPClient aspClient = ASPClientFactory.build(tcjwt);

            logger.info("Retrieving work steps...");

            // get the worksteps via API
            this.workSteps = aspClient.retrieveWorkSteps(String.valueOf(getFormData().getProjectId()));

            logger.info("Found " + this.workSteps.size() + " steps");

/*
            WorkStep step1 = new WorkStep();
            step1.setId("ws1");
            step1.setStatus("Active");
            step1.setStepType(WorkStep.StepType.designConcepts);

            WorkStep step2 = new WorkStep();
            step2.setId("ws2");
            step2.setStatus("Active");
            step2.setStepType(WorkStep.StepType.completeDesigns);

            WorkStep step3 = new WorkStep();
            step3.setId("ws3");
            step3.setStatus("Active");
            step3.setStepType(WorkStep.StepType.finalFixes);

            this.workSteps = new ArrayList<WorkStep>();
            this.workSteps.add(step1);
            this.workSteps.add(step2);
            this.workSteps.add(step3);
*/


            // put work steps in session
            HttpSession session = DirectUtils.getServletRequest().getSession(true);
            for (WorkStep ws : workSteps) {
                session.setAttribute(ws.getId(), ws);
            }

            // construct the result
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

            for (WorkStep step : this.workSteps) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("id", step.getId());
                item.put("label", WORK_STEP_DISPLAY_NAME.get(step.getStepType().name()));
                item.put("type", "folder");
                item.put("workStepType", step.getStepType().name());
                result.add(item);
            }

            setResult(result);

        } catch (Throwable e) {

            logger.error("Unable to retrieve work steps", e);

            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Checks whether the work step is in design (studio) or development.
     *
     * @param workStepName the type name of the work step.
     * @return true if it's in design, false if it's in development
     */
    private boolean isWorkStepForStudio(String workStepName) {
        if (getWorkStepName() == null || getWorkStepName().trim().length() == 0) {
            throw new IllegalArgumentException("Workstep name should not be null for method isWorkStepForStudio");
        }

        if (getWorkStepName().equalsIgnoreCase(WorkStep.StepType.designConcepts.name())
                || getWorkStepName().equalsIgnoreCase(WorkStep.StepType.completeDesigns.name())
                || getWorkStepName().equalsIgnoreCase(WorkStep.StepType.finalFixes.name())) {
            return true;
        } else if (getWorkStepName().equalsIgnoreCase(WorkStep.StepType.code.name())) {
            return false;
        } else {
            throw new IllegalArgumentException("Unknown workstep name:" + workStepName);
        }
    }

    /**
     * The ajax action to get the active / completed design challenges for the selected work step.
     *
     * @return the result code.
     */
    public String getChallengesForWorkStepAndProject() {
        try {

            checkPermission();

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

            logger.info("Getting design challenges...");

            ProjectContestsListDTO projectContests = DataProvider.getProjectContests(
                    DirectUtils.getTCSubjectFromSession().getUserId(),
                    getFormData().getProjectId());

            for (ProjectContestDTO contestDTO : projectContests.getContests()) {
                // filter out the status (active, completed) - studio contests
                if (!contestDTO.getStatus().equals(ContestStatus.DRAFT)
                        && !contestDTO.getStatus().equals(ContestStatus.DELETED)
                        && !contestDTO.getStatus().getName().toLowerCase().contains("cancelled")
                        && contestDTO.getIsStudio() == isWorkStepForStudio(getWorkStepName())) {
                    Map<String, Object> item = new HashMap<String, Object>();
                    item.put("id", contestDTO.getContest().getId());
                    item.put("label", contestDTO.getContest().getTitle());
                    item.put("type", "folder");
                    item.put("status", contestDTO.getStatus().toString());
                    result.add(item);
                }
            }

            setResult(result);

        } catch (Throwable e) {
            logger.error(String.format("Unable to retrieve challenges for project:%s, workstep:%s",
                    getFormData().getProjectId(), getWorkStepName()), e);
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * <p>Gets the current status for requested submission's push.</p>
     * 
     * @return {@link #SUCCESS} always.
     * @since 1.2
     */
    public String getSubmissionPushStatus() {
        try {
            checkPermission();
            
            String submissionPushStatus = DirectUtils.getSubmissionPushStatus(getPushId());

            Map<String, String> result = new HashMap<String, String>();
            result.put("pushStatus", submissionPushStatus);

            setResult(result);
        } catch (Throwable e) {
            logger.error("Unable to get submission's push status", e);
            if (getModel() != null) {
                setResult(e);
            }
        }
        
        return SUCCESS;
    }

    /**
     * Helper method to build the phase result for the ajax response.
     *
     * @param contestID the contest id
     * @param phaseType the phase type
     * @return the built map result.
     */
    private static Map<String, Object> buildPhaseResult(long contestID, ProjectPhaseType phaseType) {
        Map<String, Object> resultItem = new HashMap<String, Object>();
        resultItem.put("id", contestID + ":" + phaseType.toString());
        resultItem.put("label", phaseType.toString());
        resultItem.put("type", "phase");

        return resultItem;
    }

    /**
     * Gets the phases that can be pushed for the passed in contest and work step type.
     *
     * @return the result code.
     */
    public String getContestPhasesForWorkStep() {
        try {

            checkPermission();

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

            // get the all phases for the contest
            ContestDashboardDTO contestDashboardData = DataProvider.getContestDashboardData(getContestId(), true,
                    false);
            List<ProjectPhaseDTO> allPhases = contestDashboardData.getAllPhases();

            Map<String, ProjectPhaseDTO> closedPhasesMap = new HashMap<String, ProjectPhaseDTO>();
            Map<String, ProjectPhaseDTO> opennedPhasesMap = new HashMap<String, ProjectPhaseDTO>();
            Map<String, ProjectPhaseDTO> allPhasesMap = new HashMap<String, ProjectPhaseDTO>();

            for (ProjectPhaseDTO phase : allPhases) {
                if (phase.getPhaseStatus().equals(ProjectPhaseStatus.CLOSED)) {
                    closedPhasesMap.put(phase.getPhaseType().toString(), phase);
                } else if (phase.getPhaseStatus().equals(ProjectPhaseStatus.OPEN)) {
                    opennedPhasesMap.put(phase.getPhaseType().toString(), phase);
                }

                allPhasesMap.put(phase.getPhaseType().toString(), phase);
            }

            if (getWorkStepName().equalsIgnoreCase(WorkStep.StepType.designConcepts.name())) {
                // check phases for designConcept work step
                // If the WorkStep is designConcepts, and the chosen challenge has closed
                // “checkpoint submission” and “checkpoint screening” phases, display the checkpoint submission phase.
                if (closedPhasesMap.containsKey(ProjectPhaseType.CHECKPOINT_SUBMISSION.toString())
                        && closedPhasesMap.containsKey(ProjectPhaseType.CHECKPOINT_SCREENING.toString())) {
                    result.add(buildPhaseResult(getContestId(), ProjectPhaseType.CHECKPOINT_SUBMISSION));
                }

            } else if (getWorkStepName().equalsIgnoreCase(WorkStep.StepType.completeDesigns.name())) {
                // check phases for completeDesigns work step
                // If the WorkStep is completeDesigns, and the chosen challenge has
                // a closed “submission” and “screening” phases,
                // display the submission phase.
                if (closedPhasesMap.containsKey(ProjectPhaseType.SUBMISSION.toString())
                        && closedPhasesMap.containsKey(ProjectPhaseType.SCREENING.toString())) {
                    result.add(buildPhaseResult(getContestId(), ProjectPhaseType.SUBMISSION));
                }

            } else if (getWorkStepName().equalsIgnoreCase(WorkStep.StepType.finalFixes.name())) {
                // check phases for finalFixes work step
                // If the WorkStep is finalFixes, and the chosen challenge has
                // a closed “final fix” phases and no open final fix
                // display the final fix phase.
                if (closedPhasesMap.containsKey(ProjectPhaseType.FINAL_FIX.toString())
                        && !opennedPhasesMap.containsKey(ProjectPhaseType.FINAL_FIX.toString())) {
                    result.add(buildPhaseResult(getContestId(), ProjectPhaseType.FINAL_FIX));
                }
            } else if (getWorkStepName().equalsIgnoreCase(WorkStep.StepType.code.name())) {
                // check phases for code work step (dev challenges)
                // if the chosen challenge has a closed final
                if (allPhasesMap.containsKey(ProjectPhaseType.FINAL_FIX.toString())) {
                    // the challenge has final fix, check if there is closed final fix/ final review and no open final fix
                    if (closedPhasesMap.containsKey(ProjectPhaseType.FINAL_FIX.toString())
                            && closedPhasesMap.containsKey(ProjectPhaseType.FINAL_REVIEW.toString())
                            && !opennedPhasesMap.containsKey(ProjectPhaseType.FINAL_FIX.toString())) {
                        result.add(buildPhaseResult(getContestId(), ProjectPhaseType.FINAL_FIX));
                    }
                } else {
                    // the challenge does not have final fix, check if there is closed submission/review phase
                    if (closedPhasesMap.containsKey(ProjectPhaseType.SUBMISSION.toString())
                            && closedPhasesMap.containsKey(ProjectPhaseType.REVIEW.toString())
                            && closedPhasesMap.containsKey(ProjectPhaseType.APPEALS.toString())
                            && closedPhasesMap.containsKey(ProjectPhaseType.APPEALS_RESPONSE.toString())) {
                        result.add(buildPhaseResult(getContestId(), ProjectPhaseType.SUBMISSION));
                    }
                }
            }

            setResult(result);

        } catch (Throwable e) {
            logger.error("get workstep phases error", e);
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Gets the submission data brief to use on push button for the selected phase of the selected challenge.
     *
     * @return the result code.
     */
    public String getSubmissionDataForPhase() {
        try {

            checkPermission();

            Map<String, String> result = new HashMap<String, String>();

            // input: 1) phase 2) challenge id

            String pushStatus = "No submissions to push";

            if (getPhaseName() != null) {
                if (CHECKPOINT_SUBMISSION_PHASE.equalsIgnoreCase(getPhaseName()) && isWorkStepForStudio(getWorkStepName())) {
                    List<Submission> checkpointSubmissions = DirectUtils.getStudioContestSubmissions(getContestId(),
                            ContestRoundType.CHECKPOINT,
                            DirectUtils.getTCSubjectFromSession(), getContestServiceFacade());

                    if (checkpointSubmissions != null) {
                        pushStatus = checkpointSubmissions.size() + " checkpoint submissions";
                    }

                } else if (SUBMISSION_PHASE.equalsIgnoreCase(getPhaseName()) && isWorkStepForStudio(getWorkStepName())) {
                    List<Submission> submissions = DirectUtils.getStudioContestSubmissions(getContestId(),
                            ContestRoundType.FINAL,
                            DirectUtils.getTCSubjectFromSession(), getContestServiceFacade());

                    if (submissions != null) {
                        pushStatus = submissions.size() + " submissions";
                    }
                } else if (FINAL_FIX_PHASE.equalsIgnoreCase(getPhaseName()) && isWorkStepForStudio(getWorkStepName())) {
                    List<ContestFinalFixDTO> finalFixes = DataProvider.getContestFinalFixes(getContestId());

                    if (finalFixes != null && finalFixes.size() > 0) {
                        pushStatus = " final fix submissions";
                    }
                } else if (FINAL_FIX_PHASE.equalsIgnoreCase(getPhaseName()) && !isWorkStepForStudio(getWorkStepName())) {
                    List<ContestFinalFixDTO> finalFixes = DataProvider.getContestFinalFixes(getContestId());

                    if (finalFixes != null && finalFixes.size() > 0) {
                        pushStatus = " final fixed submission";
                    }
                } else if (SUBMISSION_PHASE.equalsIgnoreCase(getPhaseName()) && !isWorkStepForStudio(getWorkStepName())) {
                    SoftwareContestSubmissionsDTO softwareSubmissionDTO = new SoftwareContestSubmissionsDTO();
                    softwareSubmissionDTO.setProjectId(getContestId());
                    DataProvider.setSoftwareSubmissionsData(softwareSubmissionDTO);

                    List<SoftwareContestWinnerDTO> winners = softwareSubmissionDTO.getWinners();

                    if (winners != null && winners.size() > 0) {
                        pushStatus = " 1st place submission";
                    }
                }
            }

            result.put("pushStatus", pushStatus);

            setResult(result);

        } catch (Throwable e) {
            logger.error("get submission data error", e);
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Push the submissions of the selected contest and phase for the selected workstep via API.
     *
     * @return result code.
     */
    public String pushSubmissions() {
        try {

            String tcjwt = getJWTCookieValue();

            checkPermission();

            Map<String, String> result = new HashMap<String, String>();

            // input: 1) phase 2) challenge id 3) WorkStep

            if (tcjwt != null && tcjwt.trim().length() > 0) {

                // build the client
                ASPClient aspClient = ASPClientFactory.build(tcjwt);

                // get workstep from the session
                WorkStep workStep = (WorkStep) DirectUtils.getServletRequest().getSession(true).getAttribute(
                        getWorkStepId());

                List<List<com.appirio.client.asp.api.Submission>> submissionsToPush = new ArrayList<List<com.appirio.client.asp.api.Submission>>();

                userHandlesMap = new HashMap<Long, String>();

                // construct the correct submissions for the given contest ID and phase

                if (getPhaseName() != null) {
                    if (CHECKPOINT_SUBMISSION_PHASE.equalsIgnoreCase(getPhaseName())) {
                        List<Submission> checkpointSubmissions = DirectUtils.getStudioContestSubmissions(getContestId(),
                                ContestRoundType.CHECKPOINT,
                                DirectUtils.getTCSubjectFromSession(), getContestServiceFacade());

                        Map<Long, Map<Integer, Submission>> organizingMap = new HashMap<Long, Map<Integer, Submission>>();

                        for (Submission s : checkpointSubmissions) {
                            resolveSubmitter(s);

                            Long submissionUserId = Long.parseLong(s.getCreationUser());

                            if (organizingMap.containsKey(submissionUserId)) {
                                Map<Integer, Submission> userSubmissionMap = organizingMap.get(submissionUserId);
                                userSubmissionMap.put(s.getUserRank(), s);
                            } else {
                                Map<Integer, Submission> userSubmissionMap = new TreeMap<Integer, Submission>();
                                userSubmissionMap.put(s.getUserRank(), s);
                                organizingMap.put(submissionUserId, userSubmissionMap);
                            }
                        }

                        for (Long key : organizingMap.keySet()) {
                            Map<Integer, Submission> value = organizingMap.get(key);
                            List<com.appirio.client.asp.api.Submission> userSubmissionsToPush = new ArrayList<com.appirio.client.asp.api.Submission>();
                            for (Map.Entry<Integer, Submission> entry : value.entrySet()) {
                                userSubmissionsToPush.add(getStudioSubmissionDataForAPI(entry.getValue(), false));
                            }
                            submissionsToPush.add(userSubmissionsToPush);
                        }


                    } else if (SUBMISSION_PHASE.equalsIgnoreCase(getPhaseName()) && isWorkStepForStudio(getWorkStepName())) {

                        List<Submission> finalRoundSubmissions = DirectUtils.getStudioContestSubmissions(getContestId(),
                                ContestRoundType.FINAL,
                                DirectUtils.getTCSubjectFromSession(), getContestServiceFacade());

                        Map<Long, Map<Integer, Submission>> organizingMap = new HashMap<Long, Map<Integer, Submission>>();

                        for (Submission s : finalRoundSubmissions) {
                            resolveSubmitter(s);

                            Long submissionUserId = Long.parseLong(s.getCreationUser());

                            if (organizingMap.containsKey(submissionUserId)) {
                                Map<Integer, Submission> userSubmissionMap = organizingMap.get(submissionUserId);
                                userSubmissionMap.put(s.getUserRank(), s);
                            } else {
                                Map<Integer, Submission> userSubmissionMap = new TreeMap<Integer, Submission>();
                                userSubmissionMap.put(s.getUserRank(), s);
                                organizingMap.put(submissionUserId, userSubmissionMap);
                            }
                        }

                        for (Long key : organizingMap.keySet()) {
                            Map<Integer, Submission> value = organizingMap.get(key);
                            List<com.appirio.client.asp.api.Submission> userSubmissionsToPush = new ArrayList<com.appirio.client.asp.api.Submission>();
                            for (Map.Entry<Integer, Submission> entry : value.entrySet()) {
                                userSubmissionsToPush.add(getStudioSubmissionDataForAPI(entry.getValue(), false));
                            }
                            submissionsToPush.add(userSubmissionsToPush);
                        }
                    } else if (FINAL_FIX_PHASE.equalsIgnoreCase(getPhaseName()) && isWorkStepForStudio(getWorkStepName())) {
                        List<Submission> finalFixesSubmissions = DirectUtils.getStudioContestSubmissions(getContestId(),
                                ContestRoundType.STUDIO_FINAL_FIX_SUBMISSION,
                                DirectUtils.getTCSubjectFromSession(), getContestServiceFacade());

                        if (finalFixesSubmissions != null && finalFixesSubmissions.size() > 0) {

                            // find out the latest final fix submission
                            Submission latestSubmission = finalFixesSubmissions.get(0);
                            resolveSubmitter(latestSubmission);
                            for (int i = 1, n = finalFixesSubmissions.size(); i < n; ++i) {
                                if (latestSubmission.getModificationTimestamp().compareTo(
                                        finalFixesSubmissions.get(i).getModificationTimestamp()) < 0) {
                                    latestSubmission = finalFixesSubmissions.get(i);
                                }
                            }

                            List<com.appirio.client.asp.api.Submission> finalFixSubmissionsToPush = new ArrayList<com.appirio.client.asp.api.Submission>();

                            finalFixSubmissionsToPush.add(getStudioSubmissionDataForAPI(latestSubmission, true));

                            submissionsToPush.add(finalFixSubmissionsToPush);
                        }

                    } else if (SUBMISSION_PHASE.equalsIgnoreCase(getPhaseName()) && !isWorkStepForStudio(getWorkStepName())) {
                        SoftwareContestSubmissionsDTO softwareSubmissionDTO = new SoftwareContestSubmissionsDTO();
                        softwareSubmissionDTO.setProjectId(getContestId());
                        DataProvider.setSoftwareSubmissionsData(softwareSubmissionDTO);

                        SoftwareContestWinnerDTO firstPlaceWinner = softwareSubmissionDTO.getFirstPlaceWinner();

                        Submission[] submissions = getContestServiceFacade().getSoftwareProjectSubmissions(
                                getCurrentUser(), firstPlaceWinner.getProjectId());

                        for (Submission sub : submissions) {
                            if (sub.getId() == firstPlaceWinner.getSubmissionId()) {
                                // find the winner
                                resolveSubmitter(sub);
                                List<com.appirio.client.asp.api.Submission> winnerSubmissionToPush = new ArrayList<com.appirio.client.asp.api.Submission>();

                                winnerSubmissionToPush.add(getSoftwareSubmissionDataForAPI(sub));

                                submissionsToPush.add(winnerSubmissionToPush);
                            }
                        }

                    } else if (FINAL_FIX_PHASE.equalsIgnoreCase(getPhaseName()) && !isWorkStepForStudio(getWorkStepName())) {
                        Phase lastClosedFinalFixPhase = DirectUtils.getLastClosedFinalFixPhase(
                                this.getProjectServices(), getContestId());
                        List<ContestFinalFixDTO> finalFixes = DataProvider.getContestFinalFixes(
                                getContestId());
                        Upload[] uploads = this.getContestServiceFacade().getActiveUploads(getContestId(), DirectUtils.FINAL_FIX_UPLOAD_TYPE_ID);
                        for (Upload upload : uploads) {
                            if (upload.getProjectPhase() != null && upload.getProjectPhase().longValue() == lastClosedFinalFixPhase.getId()) {
                                Submission finalFixSubmission = new Submission();
                                resolveSubmitter(finalFixSubmission);
                                long finalFixSubmissionId = 0;
                                long submitterId = 0;
                                for (ContestFinalFixDTO ff : finalFixes) {
                                    if (ff.getUploadId() == upload.getId()) {
                                        finalFixSubmissionId = upload.getId();
                                        submitterId = ff.getFinalFixerUserId();
                                        finalFixSubmission.setUpload(upload);
                                    }
                                }

                                if (finalFixSubmissionId > 0 && submitterId > 0) {
                                    finalFixSubmission.setId(finalFixSubmissionId);
                                    finalFixSubmission.setCreationUser(String.valueOf(submitterId));
                                    List<com.appirio.client.asp.api.Submission> finalFixSubmissionToPush = new ArrayList<com.appirio.client.asp.api.Submission>();

                                    finalFixSubmissionToPush.add(getSoftwareSubmissionDataForAPI(finalFixSubmission));

                                    submissionsToPush.add(finalFixSubmissionToPush);
                                }
                            }
                        }
                    }
                }

                if (workStep == null) {
                    throw new IllegalArgumentException("The specified work step could not be found");
                }

                if (submissionsToPush == null || submissionsToPush.size() == 0) {
                    throw new IllegalArgumentException(
                            "The specified challenge and phase do not have submissions to push");
                }

//                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//                String submissionsJson = ow.writeValueAsString(submissionsToPush);
//                String workStepJson = ow.writeValueAsString(workStep);

                logger.info("Starting submission publishing...");

                // Start a separate thread for pushing the submissions
                long userId = getCurrentUser().getUserId();
                long pushId = DirectUtils.insertSubmissionPushStatus(getFormData().getProjectId(), userId);
                if (pushId == 0) {
                    throw new Exception("Could not create new record for submission's push status");
                }
                SubmissionPusher submissionPusher = new SubmissionPusher(aspClient, workStep, submissionsToPush, userId,
                    pushId);
                Thread submissionPusherThread = new Thread(submissionPusher);
                submissionPusherThread.start();
                result.put("pushId", String.valueOf(pushId));

                logger.info("Submissions published");
//
//                logger.info(submissionsJson);
//                logger.info(workStepJson);

            } else {
                throw new Exception("You don't have permission to push submissions for workstep in the project."
                        + " Please try login again.");
            }


            setResult(result);

        } catch (Throwable e) {
            logger.error("Unable to publish submissions", e);
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Builds the submission for the asp client to use.
     *
     * @param submission the submission object
     * @return the built asp client submission DTO.
     * @throws Exception if any error.
     */
    private com.appirio.client.asp.api.Submission getSoftwareSubmissionDataForAPI(Submission submission) throws Exception {
        logger.info(
                "Processing software submission " + submission.getId() + " for user " + submission.getCreationUser());
        com.appirio.client.asp.api.Submission s = new com.appirio.client.asp.api.Submission();
        s.setSubmitterId(submission.getCreationUser());
        s.setTopcoderSubmissionId(String.valueOf(submission.getId()));
        List<SubmissionFile> files = new ArrayList<SubmissionFile>();
        s.setFiles(files);

        System.out.println("software submission base:" + getSoftwareSubmissionBase());
        System.out.println("submission upload:" + submission.getUpload());
        System.out.println("submission upload parameter:" + submission.getUpload().getParameter());


        files.add(createSubmissionFile(SubmissionFile.FileRole.DELIVERABLE,
                getSoftwareSubmissionBase() + File.separator + submission.getUpload().getParameter()));

        return s;
    }


    /**
     * Builds the <code>com.appirio.client.asp.api.Submission</code> from the direct submission.
     *
     * @param submission                 the direct submission
     * @param isOriginalSubmissionNeeded whether the original source file is needed.
     * @return result code
     * @throws Exception if any error occus.
     */
    private com.appirio.client.asp.api.Submission getStudioSubmissionDataForAPI(Submission submission,
                                                                                boolean isOriginalSubmissionNeeded) throws Exception {
        logger.info("Processing studio submission " + submission.getId() + " for user " + submission.getCreationUser());
        com.appirio.client.asp.api.Submission s = new com.appirio.client.asp.api.Submission();
        //s.setSubmitterId(String.valueOf(submission.getUpload().getOwner()));
        s.setSubmitterId(submission.getCreationUser());
        s.setTopcoderSubmissionId(String.valueOf(submission.getId()));

        List<SubmissionFile> files = new ArrayList<SubmissionFile>();
        s.setFiles(files);

        // 1) get the COVER and PREVIEW first
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("studio_submission_artifacts");
        request.setProperty("subid", String.valueOf(submission.getId()));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("studio_submission_artifacts");

        final long recordNum = resultContainer.size();

        if (recordNum > 0) {
            // means the submission has images files - the results is sorted by order asc
            for (int i = 0; i < recordNum; i++) {
                long sortOrder = resultContainer.getIntItem(i, "sort_order");
                long imageTypeId = resultContainer.getLongItem(i, "image_type_id");
                String imageFileName = resultContainer.getStringItem(i, "file_name");
                SubmissionFile.FileRole fileRole;

                // 29 = small watermark, 31 = full watermark

                if (imageTypeId == 29L || imageTypeId == 31L) {
                    if (sortOrder == 1) {
                        fileRole = SubmissionFile.FileRole.COVER;
                    } else {
                        fileRole = imageTypeId == 29L ? SubmissionFile.FileRole.PREVIEW_SMALL : SubmissionFile.FileRole.PREVIEW_FULL;
                    }

                    files.add(createSubmissionFile(fileRole, getImageFilePath(submission, imageFileName)));
                }
            }
        }

        // 2) get the DELIVERABLE
        if (isOriginalSubmissionNeeded) {
            // get the source submission
            files.add(createSubmissionFile(SubmissionFile.FileRole.DELIVERABLE, getSourceSubmissionPath(submission)));
        } else {
            // get the preview submission
            files.add(createSubmissionFile(SubmissionFile.FileRole.DELIVERABLE, getPreviewFilePath(submission)));
        }

        return s;
    }

    /**
     * Creates a <code>SubmissionFile</code> instance with the given file role and absolute file path.
     *
     * @param fileRole the file role
     * @param path     the absolute path of the file.
     * @return created SubmissionFile instance.
     */
    private SubmissionFile createSubmissionFile(SubmissionFile.FileRole fileRole, String path) {
        SubmissionFile submissionFile = new SubmissionFile();
        submissionFile.setFileRole(fileRole);
        File f = new File(path);
        submissionFile.setMimeType(mimeTypeRetriever.getMimeTypeFromFileName(f.getName()));
        submissionFile.setPath(path);

        return submissionFile;
    }


    /**
     * Creates the absolute submission base path for the studio submission.
     *
     * @param submission the studio submission.
     * @return the base path of the submission.
     */
    private String createAbsoluteStudioSubmissionBasePath(Submission submission) {
        StringBuilder buf = new StringBuilder(200);
        buf.append(getStudioSubmissionBase());
        buf.append(System.getProperty("file.separator"));
        buf.append(submission.getUpload().getProject());
        buf.append(System.getProperty("file.separator"));
        //buf.append(this.userHandlesMap.get(Long.parseLong(submission.getCreationUser())));
        // files are stored on disk with lowercase handle
        buf.append(this.userHandlesMap.get(Long.parseLong(submission.getCreationUser())).toLowerCase());
        buf.append("_");
        buf.append(submission.getCreationUser());
        buf.append(System.getProperty("file.separator"));

        logger.info(String.format("createAbsoluteStudioSubmissionBasePath- submissionID:%d, base path:%s",
                submission.getId(), buf.toString()));

        return buf.toString();
    }

    /**
     * <p>Gets the details for actual submitter based on the submitter resource associated with the specified submission
     * and updates {@link Submission#getCreationUser()} accordingly.</p>
     *
     * @param submission a <code>Submission</code> providing submission details.
     * @throws ResourcePersistenceException if an unexpected error occurs.
     * @since 1.1
     */
    private void resolveSubmitter(Submission submission) throws ResourcePersistenceException {
        long submitterResourceId = submission.getUpload().getOwner();
        Resource submitterResource = getResourceManager().getResource(submitterResourceId);
        long submitterUserId = Long.parseLong(submitterResource.getProperty("External Reference ID"));
        String submitterHandle = submitterResource.getProperty("Handle");

        this.userHandlesMap.put(submitterUserId, submitterHandle);

        submission.setCreationUser(String.valueOf(submitterUserId));
    }

    /**
     * Gets the absolute path of the preview deliverable zip.
     *
     * @param submission the submission.
     * @return the absolute path of the preview deliverable zip.
     */
    private String getPreviewFilePath(Submission submission) {
        String baseDirPath = createAbsoluteStudioSubmissionBasePath(submission);
        File baseDir = new File(baseDirPath);

        String[] fileNames = baseDir.list(new SubmissionPresentationFilter("preview", submission.getId()));

        if (fileNames == null || fileNames.length < 1) {
            logger.error("Unable to find preview file " + baseDir.getAbsolutePath() + "/" + submission.getId() +
                    "_preview.zip");
            throw new IllegalArgumentException(
                    String.format(
                            "No preview zip file found for contest %s and submissionID %s. Please verify the submission was uploaded successfully.",
                            submission.getUpload().getProject(),
                            submission.getId()));
        }

        logger.info(String.format("getPreviewFilePath- submissionID:%d, path:%s",
                submission.getId(), baseDirPath + fileNames[0]));

        return baseDirPath + fileNames[0];
    }

    /**
     * Gets the absolute preview image file path.
     *
     * @param submission    the submission.
     * @param imageFileName the image file name.
     * @return the absolute path of the preview image.
     */
    private String getImageFilePath(Submission submission, String imageFileName) {
        String path = createAbsoluteStudioSubmissionBasePath(submission) + imageFileName;

        logger.info(String.format("getImageFilePath- submissionID:%d, image file name:%s, path:%s",
                submission.getId(), imageFileName, path));

        return path;
    }

    /**
     * Gets the absolute path of the source submission.
     *
     * @param submission the submission.
     * @return the absolute path of the submission.
     */
    private String getSourceSubmissionPath(Submission submission) {
        String path = createAbsoluteStudioSubmissionBasePath(submission) + submission.getUpload().getParameter();

        logger.info(String.format("getSourceSubmissionPath- submissionID:%d, path:%s",
                submission.getId(), path));
        return path;
    }


    /**
     * Gets the forum data.
     *
     * @return the form data.
     */
    @Override
    public ProjectIdForm getFormData() {
        return formData;
    }

    /**
     * Gets the work steps.
     *
     * @return the work steps.
     */
    public List<WorkStep> getWorkSteps() {
        return workSteps;
    }

    /**
     * Sets the work steps.
     *
     * @param workSteps the work steps.
     */
    public void setWorkSteps(List<WorkStep> workSteps) {
        this.workSteps = workSteps;
    }

    /**
     * Gets the work step name.
     *
     * @return the work step name.
     */
    public String getWorkStepName() {
        return workStepName;
    }

    /**
     * Sets the work step name.
     *
     * @param workStepName the work step name.
     */
    public void setWorkStepName(String workStepName) {
        this.workStepName = workStepName;
    }

    /**
     * Gets the contest id.
     *
     * @return the contest id.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the contest id.
     *
     * @param contestId the contest id.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Gets the phase name.
     *
     * @return the phase name.
     */
    public String getPhaseName() {
        return phaseName;
    }

    /**
     * Sets the phase name.
     *
     * @param phaseName the phase name.
     */
    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    /**
     * Gets the studio submission base path.
     *
     * @return the studio submission base path.
     */
    public String getStudioSubmissionBase() {
        return studioSubmissionBase;
    }

    /**
     * Sets the studio submission base path.
     *
     * @param studioSubmissionBase the studio submission base path.
     */
    public void setStudioSubmissionBase(String studioSubmissionBase) {
        this.studioSubmissionBase = studioSubmissionBase;
    }

    /**
     * Gets the software submission base path.
     *
     * @return the software submission base path.
     * @since 1.1
     */
    public String getSoftwareSubmissionBase() {
        return softwareSubmissionBase;
    }

    /**
     * Sets the software submission base path.
     *
     * @param softwareSubmissionBase the software submission base path.
     * @since 1.1
     */
    public void setSoftwareSubmissionBase(String softwareSubmissionBase) {
        this.softwareSubmissionBase = softwareSubmissionBase;
    }

    /**
     * Gets the work step id.
     *
     * @return the work step id.
     */
    public String getWorkStepId() {
        return workStepId;
    }

    /**
     * Sets the work step id.
     *
     * @param workStepId the work step id.
     */
    public void setWorkStepId(String workStepId) {
        this.workStepId = workStepId;
    }

    /**
     * Gets the demand work id.
     *
     * @return the demand work id.
     */
    public String getDemandWorkId() {
        return demandWorkId;
    }

    /**
     * Sets the demand work id.
     *
     * @param demandWorkId the demand work id.
     */
    public void setDemandWorkId(String demandWorkId) {
        this.demandWorkId = demandWorkId;
    }

    /**
     * Gets the MIME type retriever.
     *
     * @return the MIME type retriever.
     */
    public MimeTypeRetriever getMimeTypeRetriever() {
        return mimeTypeRetriever;
    }

    /**
     * Sets the MIME type retriever.
     *
     * @param mimeTypeRetriever the MIME type retriever.
     */
    public void setMimeTypeRetriever(MimeTypeRetriever mimeTypeRetriever) {
        this.mimeTypeRetriever = mimeTypeRetriever;
    }

    /**
     * <p>Gets the resourceManager property.</p>
     *
     * @return a <code>ResourceManager</code> providing the value for resourceManager property.
     * @since 1.1
     */
    public ResourceManager getResourceManager() {
        return this.resourceManager;
    }

    /**
     * <p>Sets the resourceManager property.</p>
     *
     * @param resourceManager a <code>ResourceManager</code> providing the value for resourceManager property.
     * @since 1.1
     */
    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }


    /**
     * <p>Gets the pushId property.</p>
     *
     * @return a <code>long</code> providing the value for pushId property.
     * @since 1.2
     */
    public long getPushId() {
        return this.pushId;
    }

    /**
     * <p>Sets the pushId property.</p>
     *
     * @param pushId a <code>long</code> providing the value for pushId property.
     * @since 1.2
     */
    public void setPushId(long pushId) {
        this.pushId = pushId;
    }

    public static class SubmissionPresentationFilter implements FilenameFilter {

        /**
         * <p>A <code>String</code> providing the prefix for the filenames to be used for filtering the files.</p>
         */
        private String filenamePrefix = null;

        /**
         * <p>Constructs new <code>SubmissionPresentationFilter</code> instance for finding the file matching the specified
         * prefix.</p>
         *
         * @param filenamePrefix a <code>String</code> providing the filter for file names.
         * @since Studio Submission Slideshow
         */
        public SubmissionPresentationFilter(String filenamePrefix) {
            this.filenamePrefix = filenamePrefix;
        }

        /**
         * <p>Constructs new <code>SubmissionPresentationFilter</code> instance for finding the file for specified type
         * of alternate presentation of specified submission.</p>
         *
         * @param type         a <code>String</code> referencing the type of requested alternate presentation.
         * @param submissionId a <code>Long</code> providing the ID of a submission.
         */
        public SubmissionPresentationFilter(String type, Long submissionId) {
            this.filenamePrefix = submissionId + "_" + type + ".";
        }

        /**
         * <p>Tests if a specified file should be included in a file list.</p>
         *
         * @param dir  a <code>File</code> representing the directory in which the file was found.
         * @param name a <code>String</code> providing name the name of the file.
         * @return <code>true</code> if and only if the name should be included in the file list; <code>false</code>
         * otherwise.
         */
        public boolean accept(File dir, String name) {
            return name.startsWith(this.filenamePrefix);
        }
    }

    /**
     * <p>A separate thread to be used for pushing the submissions to <code>TopCoder Connect</code> system.</p>
     *
     * @author TCSCODER
     * @version 1.0
     * @since 1.1 (TOPCODER DIRECT - ASP INTEGRATION WORK MANAGEMENT IMPROVEMENT)
     */
    private static class SubmissionPusher implements Runnable {

        /**
         * <p>Logger for this class.</p>
         */
        private static final Logger logger = Logger.getLogger(SubmissionPusher.class);

        /**
         * <p>An <code>ASPClient</code> providing interface to TopCoder Connect system.</p>
         */
        private ASPClient aspClient;

        /**
         * <p>A <code>WorkStep</code> specifying the work step the submissions correspond to.</p>
         */
        private WorkStep workStep;

        /**
         * <p>A <code>List</code> of submissions to push to TopCoder Connect.</p>
         */
        private List<List<com.appirio.client.asp.api.Submission>> submissionsToPush;

        /**
         * <p>A <code>Long</code> providing the ID for submission push status.</p>
         */
        private long pushId;

        /**
         * <p>A <code>Long</code> providing the ID for user pushing the submission.</p>
         */
        private long userId;
        
        /**
         * <p>Constructs new <code>SubmissionPusher</code> instance with specified ASP client.</p>
         *
         * @param aspClient an <code>ASPClient</code> providing interface to TopCoder Connect system.
         * @param workStep a <code>WorkStep</code> specifying the work step the submissions correspond to.
         * @param submissionsToPush a <code>List</code> of submissions to push to TopCoder Connect.
         * @param userId a <code>long</code> providing the ID for user pushing the submission.
         * @param pushId a <code>long</code> providing the ID for submission push status.
         */
        private SubmissionPusher(ASPClient aspClient, WorkStep workStep,
                                 List<List<com.appirio.client.asp.api.Submission>> submissionsToPush,
                                 long userId, long pushId) {
            this.aspClient = aspClient;
            this.workStep = workStep;
            this.submissionsToPush = submissionsToPush;
            this.userId = userId;
            this.pushId = pushId;
        }

        /**
         * <p>Pushes intended submissions to <code>TopCoder Connect</code> system via <code>ASP Client</code>.</p>
         */
        public void run() {
            logger.info("Starting to push the submissions to TopCoder Connect. Push ID: " + this.pushId);

            boolean success = false;
            try {
                this.aspClient.publishSubmissionsToWorkStep(this.workStep, this.submissionsToPush);
                success = true;
                logger.info("Pushed submissions to TopCoder Connect. Push ID: " + this.pushId);
            } catch (Exception e) {
                logger.error("Failed to push submissions for push ID: " + this.pushId, e);
            }

            try {
                if (success) {
                    DirectUtils.updateSubmissionPushStatus(this.pushId, this.userId, "SUCCESS");
                } else {
                    DirectUtils.updateSubmissionPushStatus(this.pushId, this.userId, "FAIL");
                }
            } catch (Exception e) {
                logger.error("Failed to update submission's push status for push ID: " + this.pushId, e);
            }
        }
    }
}
