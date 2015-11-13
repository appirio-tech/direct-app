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
import com.topcoder.direct.services.view.dto.contest.ContestCopilotDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.ContestFinalFixDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseStatus;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseType;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

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
 * @author TCSASSEMBLER
 * @version 1.0
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

    private Map<Long, String> userHandlesMap;

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
            throw new Exception("You don't have permission to access this page."
                    + " Please try login again.");
        }
    }


    private void checkPermission() throws Exception {
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
        // check if user is one of the following roles, if yes, pass the permission checking and return
        if (DirectUtils.isTcStaff(currentUser) || DirectUtils.isTcOperations(currentUser) ||
                DirectUtils.isTCPlatformSpecialist(currentUser)) {
            // pass permission checking
            return;
        }

        // check if user is one of the copilot of the project
        // 1) get the copilots of the project
        List<ContestCopilotDTO> copilots = DataProvider.getCopilotsForDirectProject(
                getFormData().getProjectId());

        for (ContestCopilotDTO copilot : copilots) {
            if (copilot.getUserId() == currentUser.getUserId()) {
                // pass permission checking
                return;
            }
        }

        // still not pass permission checking, throw exception
        throw new Exception("You don't have permission to access");
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
     * The ajax action to get the active / completed design challenges for the selected work step.
     *
     * @return the result code.
     */
    public String getDesignChallenges() {
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
                        && contestDTO.getIsStudio()) {
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
            logger.error("Unable to retrieve design contests", e);
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
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

            Map<String, ProjectPhaseDTO> closedPhases = new HashMap<String, ProjectPhaseDTO>();

            for (ProjectPhaseDTO phase : allPhases) {
                if (phase.getPhaseStatus().equals(ProjectPhaseStatus.CLOSED)) {
                    closedPhases.put(phase.getPhaseType().toString(), phase);
                }
            }

            if (getWorkStepName().equalsIgnoreCase(WorkStep.StepType.designConcepts.name())) {
                // check phases for designConcept workstep
                // If the work step is designConcepts, and the chosen challenge has closed
                // “checkpoint submission” and “checkpoint screening” phases, display the checkpoint submission phase.
                if (closedPhases.containsKey(ProjectPhaseType.CHECKPOINT_SUBMISSION.toString())
                        && closedPhases.containsKey(ProjectPhaseType.CHECKPOINT_SCREENING.toString())) {
                    Map<String, Object> resultItem = new HashMap<String, Object>();
                    resultItem.put("id", getContestId() + ":" + ProjectPhaseType.CHECKPOINT_SUBMISSION.toString());
                    resultItem.put("label", ProjectPhaseType.CHECKPOINT_SUBMISSION.toString());
                    resultItem.put("type", "phase");
                    result.add(resultItem);
                }

            } else if (getWorkStepName().equalsIgnoreCase(WorkStep.StepType.completeDesigns.name())) {
                // check phases for completeDesigns workstep
                // If the workstep is completeDesigns, and the chosen challenge has
                // a closed “submission” and “screening” phases,
                // display the submission phase.
                if (closedPhases.containsKey(ProjectPhaseType.SUBMISSION.toString())
                        && closedPhases.containsKey(ProjectPhaseType.SCREENING.toString())) {
                    Map<String, Object> resultItem = new HashMap<String, Object>();
                    resultItem.put("id", getContestId() + ":" + ProjectPhaseType.SUBMISSION.toString());
                    resultItem.put("label", ProjectPhaseType.SUBMISSION.toString());
                    resultItem.put("type", "phase");
                    result.add(resultItem);
                }

            } else if (getWorkStepName().equalsIgnoreCase(WorkStep.StepType.finalFixes.name())) {
                // check phases for completeDesigns workstep
                // If the workstep is completeDesigns, and the chosen challenge has
                // a closed “submission” and “screening” phases,
                // display the submission phase.
                if (closedPhases.containsKey(ProjectPhaseType.SUBMISSION.toString())) {
                    Map<String, Object> resultItem = new HashMap<String, Object>();
                    resultItem.put("id", getContestId() + ":" + ProjectPhaseType.FINAL_FIX.toString());
                    resultItem.put("label", ProjectPhaseType.FINAL_FIX.toString());
                    resultItem.put("type", "phase");
                    result.add(resultItem);
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
                if (CHECKPOINT_SUBMISSION_PHASE.equalsIgnoreCase(getPhaseName())) {
                    List<Submission> checkpointSubmissions = DirectUtils.getStudioContestSubmissions(getContestId(),
                            ContestRoundType.CHECKPOINT,
                            DirectUtils.getTCSubjectFromSession(), getContestServiceFacade());

                    if (checkpointSubmissions != null) {
                        pushStatus = checkpointSubmissions.size() + " checkpoint submissions";
                    }

                } else if (SUBMISSION_PHASE.equalsIgnoreCase(getPhaseName())) {
                    List<Submission> submissions = DirectUtils.getStudioContestSubmissions(getContestId(),
                            ContestRoundType.FINAL,
                            DirectUtils.getTCSubjectFromSession(), getContestServiceFacade());

                    if (submissions != null) {
                        pushStatus = submissions.size() + " submissions";
                    }
                } else if (FINAL_FIX_PHASE.equalsIgnoreCase(getPhaseName())) {
                    List<ContestFinalFixDTO> finalFixes = DataProvider.getContestFinalFixes(getContestId());

                    if (finalFixes != null && finalFixes.size() > 0) {
                        pushStatus = " final fix submissions";
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

                            Long submissionUserId = Long.parseLong(s.getCreationUser());

                            if (organizingMap.containsKey(submissionUserId)) {
                                Map<Integer, Submission> userSubmissionMap = organizingMap.get(submissionUserId);
                                userSubmissionMap.put(s.getUserRank(), s);
                            } else {
                                Map<Integer, Submission> userSubmissionMap = new TreeMap<Integer, Submission>();
                                userSubmissionMap.put(s.getUserRank(), s);
                                organizingMap.put(submissionUserId, userSubmissionMap);
                                userHandlesMap.put(submissionUserId,
                                        this.getUserService().getUserHandle(submissionUserId));
                            }
                        }

                        for (Long key : organizingMap.keySet()) {
                            Map<Integer, Submission> value = organizingMap.get(key);
                            List<com.appirio.client.asp.api.Submission> userSubmissionsToPush = new ArrayList<com.appirio.client.asp.api.Submission>();
                            for (Map.Entry<Integer, Submission> entry : value.entrySet()) {
                                userSubmissionsToPush.add(getSubmissionDataForAPI(entry.getValue(), false));
                            }
                            submissionsToPush.add(userSubmissionsToPush);
                        }


                    } else if (SUBMISSION_PHASE.equalsIgnoreCase(getPhaseName())) {

                        List<Submission> finalRoundSubmissions = DirectUtils.getStudioContestSubmissions(getContestId(),
                                ContestRoundType.FINAL,
                                DirectUtils.getTCSubjectFromSession(), getContestServiceFacade());

                        Map<Long, Map<Integer, Submission>> organizingMap = new HashMap<Long, Map<Integer, Submission>>();

                        for (Submission s : finalRoundSubmissions) {

                            Long submissionUserId = Long.parseLong(s.getCreationUser());

                            if (organizingMap.containsKey(submissionUserId)) {
                                Map<Integer, Submission> userSubmissionMap = organizingMap.get(submissionUserId);
                                userSubmissionMap.put(s.getUserRank(), s);
                            } else {
                                Map<Integer, Submission> userSubmissionMap = new TreeMap<Integer, Submission>();
                                userSubmissionMap.put(s.getUserRank(), s);
                                organizingMap.put(submissionUserId, userSubmissionMap);
                                userHandlesMap.put(submissionUserId,
                                        this.getUserService().getUserHandle(submissionUserId));
                            }
                        }

                        for (Long key : organizingMap.keySet()) {
                            Map<Integer, Submission> value = organizingMap.get(key);
                            List<com.appirio.client.asp.api.Submission> userSubmissionsToPush = new ArrayList<com.appirio.client.asp.api.Submission>();
                            for (Map.Entry<Integer, Submission> entry : value.entrySet()) {
                                userSubmissionsToPush.add(getSubmissionDataForAPI(entry.getValue(), false));
                            }
                            submissionsToPush.add(userSubmissionsToPush);
                        }
                    } else if (FINAL_FIX_PHASE.equalsIgnoreCase(getPhaseName())) {
                        List<Submission> finalFixesSubmissions = DirectUtils.getStudioContestSubmissions(getContestId(),
                                ContestRoundType.STUDIO_FINAL_FIX_SUBMISSION,
                                DirectUtils.getTCSubjectFromSession(), getContestServiceFacade());

                        if (finalFixesSubmissions != null && finalFixesSubmissions.size() > 0) {

                            // find out the latest final fix submission
                            Submission latestSubmission = finalFixesSubmissions.get(0);
                            for (int i = 1, n = finalFixesSubmissions.size(); i < n; ++i) {
                                if (latestSubmission.getModificationTimestamp().compareTo(
                                        finalFixesSubmissions.get(i).getModificationTimestamp()) < 0) {
                                    latestSubmission = finalFixesSubmissions.get(i);
                                }
                            }

                            userHandlesMap.put(Long.parseLong(latestSubmission.getCreationUser()),
                                    this.getUserService().getUserHandle(Long.parseLong(latestSubmission.getCreationUser())));

                            List<com.appirio.client.asp.api.Submission> finalFixSubmissionsToPush = new ArrayList<com.appirio.client.asp.api.Submission>();

                            finalFixSubmissionsToPush.add(getSubmissionDataForAPI(latestSubmission, true));

                            submissionsToPush.add(finalFixSubmissionsToPush);
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

                //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                //String submissionsJson = ow.writeValueAsString(submissionsToPush);
                //String workStepJson = ow.writeValueAsString(workStep);

                aspClient.publishSubmissionsToWorkStep(workStep, submissionsToPush);

                //logger.info(submissionsJson);
                //logger.info(workStepJson);

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
     * Builds the <code>com.appirio.client.asp.api.Submission</code> from the direct submission.
     *
     * @param submission                 the direct submission
     * @param isOriginalSubmissionNeeded whether the original source file is needed.
     * @return result code
     * @throws Exception if any error occus.
     */
    private com.appirio.client.asp.api.Submission getSubmissionDataForAPI(Submission submission,
                                                                          boolean isOriginalSubmissionNeeded) throws Exception {
        com.appirio.client.asp.api.Submission s = new com.appirio.client.asp.api.Submission();
        s.setSubmitterId(String.valueOf(submission.getUpload().getOwner()));
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
                SubmissionFile.FileRole fileRole = SubmissionFile.FileRole.PREVIEW;

                if (imageTypeId == 29L || imageTypeId == 31L) {
                    if (sortOrder == 1) {
                        fileRole = SubmissionFile.FileRole.COVER;
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
            throw new IllegalArgumentException(
                    String.format("No preview zip file for contestID:%s, submissionID:%s",
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
}
