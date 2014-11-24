/*
 * Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.clients.dao.ProjectContestFeePercentageService;
import com.topcoder.clients.dao.ProjectContestFeeService;
import com.topcoder.clients.model.BillingAccount;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.ProjectContestFeePercentage;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.dto.project.ProjectDraftContestsEditDTO;
import com.topcoder.direct.services.view.form.DraftContestEditForm;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.SoftwareCompetition;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * This class provides actions to handle the requests in batch draft contests edit page.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Release 5 v1.0) Change notes:
 *   <ol>
 *     <li>Added annotation <code>WriteProject</code> to this class so that the interceptor will check
 *     the write permission before execution this action.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 * - Removes the statements to populate the right sidebar direct projects and project contests. It's changed to
 * load these data via ajax instead after the page finishes loading.
 * </p>
 *
 * @author Veve
 * @version 1.2
 */
@WriteProject
public class ProjectDraftContestsBatchEditAction extends BaseDirectStrutsAction
    implements FormAction<ProjectIdForm> {

    /**
     * The date format for the start date and end date in contests batch edit page.
     */
    private static final DateFormat START_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * <p>A <code>ProjectIdForm</code> providing the ID of a requested project.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * <p>A <code>List</code> providing the details for the requested draft contests.</p>
     */
    private ProjectDraftContestsEditDTO viewData = new ProjectDraftContestsEditDTO();

    /**
     * A list of <code>DraftContestEditForm</code>. Each form representing a draft contest to update.
     */
    private List<DraftContestEditForm> contestsToUpdate;

    /**
     * Instance of ProjectContestFeeService.
     */
    private ProjectContestFeeService contestFeeService;

    /**
     * Instance of ProjectContestFeeService.
     */
    private ProjectContestFeePercentageService contestFeePercentageService;

    /**
     * <p>
     * Entry action for the batch contests edit page.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        // get the contests from the project
        final ProjectContestsListDTO projectContests = DataProvider.getProjectContests(currentUser.getUserId(), getFormData().getProjectId());

        List<ProjectContestDTO> draftContests = new ArrayList<ProjectContestDTO>();

        // filter out the draft contests
        for (ProjectContestDTO contest : projectContests.getContests()) {
            if (contest.getStatus().equals(ContestStatus.DRAFT)) {
                draftContests.add(contest);
            }
        }

        // sort draft contest by the start date
        Collections.sort(draftContests, new DraftContestComparator());

        viewData.setDraftContests(draftContests);

        // populate the available contest types
        final Map<String, Map<Long, String>> allProjectCategoriesGrouped = DataProvider.getAllProjectCategoriesGrouped();

        viewData.setStudioContestTypes(allProjectCategoriesGrouped.get("studio"));
        viewData.setSoftwareContestTypes(allProjectCategoriesGrouped.get("software"));

        // remove copilot posting
        viewData.getSoftwareContestTypes().remove(ProjectCategory.COPILOT_POSTING.getId());

        ProjectBriefDTO currentDirectProject;

        if (projectContests.getContests().size() > 0) {
            currentDirectProject = projectContests.getContests().get(0).getContest().getProject();
        } else {
            currentDirectProject = DirectUtils.getCurrentProjectBrief(getProjectServiceFacade(), getFormData().getProjectId());
        }

        getSessionData().setCurrentProjectContext(currentDirectProject);
        getSessionData().setCurrentSelectDirectProjectID(currentDirectProject.getId());
    }

    /**
     * <p>Remove the draft contests via ajax request</p>
     *
     * @return the result code.
     */
    public String removeDraftContests() {
        try {
            // store the draft contests that have been removed
            List<Long> result = new ArrayList<Long>();
            Set<Long> contestIdsToUpdate = new HashSet<Long>();

            for (DraftContestEditForm contest : getContestsToUpdate()) {
                contestIdsToUpdate.add(contest.getContestId());
            }

            final List<SoftwareCompetition> deletedContests =
                    getContestServiceFacade().deleteSoftwareContests(DirectUtils.getTCSubjectFromSession(),
                            getFormData().getProjectId(), new ArrayList<Long>(contestIdsToUpdate));

            for (SoftwareCompetition contest : deletedContests) {
                // add deleted contest id to the result
                result.add(contest.getProjectHeader().getId());
            }

            setResult(result);
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * <p>Updates the draft contests via ajax request</p>
     *
     * @return the result code
     */
    public String updateDraftContests() {
        try {
            // store the draft contests that have been removed
            List<Long> result = new ArrayList<Long>();
            Map<Long, DraftContestEditForm> contestIdsToUpdate = new HashMap<Long, DraftContestEditForm>();
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            for (DraftContestEditForm contestForm : getContestsToUpdate()) {
                contestIdsToUpdate.put(contestForm.getContestId(), contestForm);
            }

            List<SoftwareCompetition> toUpdate = new ArrayList<SoftwareCompetition>();

            for (Long contestIdToUpdate : contestIdsToUpdate.keySet()) {
                SoftwareCompetition contest = getContestServiceFacade().getSoftwareContestByProjectId(currentUser, contestIdToUpdate);

                // change contest
                contest = updateContestWithDraftEditForm(currentUser, contest, contestIdsToUpdate.get(contestIdToUpdate));

                // set reason header
                toUpdate.add(contest);
            }

            // update the draft contests via contest service facade
            List<SoftwareCompetition> updatedContests = getContestServiceFacade().batchUpdateDraftSoftwareContests(currentUser, getFormData().getProjectId(), toUpdate);

            for (SoftwareCompetition sc : updatedContests) {
                // add the updated contest id to the result
                result.add(sc.getId());
            }

            setResult(result);

        } catch (Throwable e) {

            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * <p>
     * Gets all billing projects for the direct project.
     * </p>
     *
     * @return a list of billing projects
     * @throws Exception if any error occurs
     */
    public List<ProjectData> getBillingProjects() throws Exception {
        if (null == getProjectServiceFacade()) {
            throw new IllegalStateException("The project service facade is not initialized");
        }

        List<Project> projects = getProjectServiceFacade().getBillingAccountsByProject(getFormData().getProjectId());

        // wrap the data with project data, only id, name and description are retrieved
        List<ProjectData> projectDatas = new ArrayList<ProjectData>();
        for (Project project : projects) {
            ProjectData projectData = new ProjectData();
            projectData.setProjectId(project.getId());
            projectData.setName(project.getName());
            projectData.setDescription(project.getDescription());

            projectDatas.add(projectData);
        }

        return projectDatas;
    }

    /**
     * <p>
     * Applies the contest changes stored in <code>DraftContestEditForm</code> to the <code>SoftwareCompetition</code>
     * instance.
     * </p>
     *
     * @param tcSubject the tcSubject instance.
     * @param contest the contest to update
     * @param form the <code>DraftContestEditForm</code> which stores the contest changes.
     * @return updated contest instance.
     * @throws Exception if any error happens.
     */
    private SoftwareCompetition updateContestWithDraftEditForm(TCSubject tcSubject, SoftwareCompetition contest,
                                                               DraftContestEditForm form)
            throws Exception {
        // update name
        contest.getAssetDTO().setName(form.getContestName());
        contest.getProjectHeader().setProperty("Project Name", form.getContestName());

        // update contest type
        long previousContestCategoryId = contest.getProjectHeader().getProjectCategory().getId();

        boolean needToUpdateContestFee = false;

        if (previousContestCategoryId != form.getContestTypeId()) {
            // update only if updated
            contest.getProjectHeader().setProjectCategory(ProjectCategory.getProjectCategoryById(form.getContestTypeId()));

            // update copilot fee
            contest.getProjectHeader().setProperty("Copilot Cost", String.valueOf(ConfigUtils.getCopilotFees().get(String.valueOf(form.getContestTypeId())).getCopilotFee()));

            needToUpdateContestFee = true;
        }


        String previousBillingAccount = contest.getProjectHeader().getProperty("Billing Project").trim();

        if(!previousBillingAccount.equals(String.valueOf(form.getContestBillingAccountId()))) {
            // update billing account if the billing account is changed
            contest.getProjectHeader().setProperty("Billing Project", String.valueOf(form.getContestBillingAccountId()));

            needToUpdateContestFee = true;
        }

        if(needToUpdateContestFee) {
            contest = recalculateContestFee(form.getContestBillingAccountId(), form.getContestTypeId(), contest);
        }

        // update start date
        XMLGregorianCalendar previousDate = contest.getAssetDTO().getProductionDate();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(START_DATE_FORMAT.parse(form.getContestStartDate()));
        XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        // only update if the date is changed
        if (previousDate.getYear() != date.getYear() || previousDate.getMonth() != date.getMonth()
                || previousDate.getDay() != date.getDay()) {
            // set to 9:00 am if update
            date.setHour(9);
            contest.getAssetDTO().setProductionDate(date);
        }

        // set reason
        contest.setProjectHeaderReason("Batch draft contest update by user:" + tcSubject.getUserId());

        return contest;
    }


    private SoftwareCompetition recalculateContestFee(long newBillingId, long newContestTypeId,
                                                      SoftwareCompetition contest) throws Exception {
        if(newBillingId == 0) {
            // set contest fee to 0
            contest.getProjectHeader().setProperty("Admin Fee", "0");

            return contest;
        }

        ProjectContestFeePercentage percentage =
                getContestFeePercentageService().getByProjectId(newBillingId);
        if (percentage == null || !percentage.isActive()) {
            // fixed contest fee
            final BillingAccount billingAccount = getContestFeeService().getBillingAccount(newBillingId);

            // set to zero
            contest.getProjectHeader().setProperty("Contest Fee Percentage", "0");

            if(billingAccount == null || billingAccount.getContestFees() == null) {
                contest.getProjectHeader().setProperty("Admin Fee", "0");
                return contest;
            } else {
                final List<ProjectContestFee> contestFees = billingAccount.getContestFees();
                for(ProjectContestFee fee : contestFees) {
                    if(fee.getContestTypeId() == newContestTypeId) {
                        contest.getProjectHeader().setProperty("Admin Fee", String.valueOf(fee.getContestFee()));
                        return contest;
                    }
                }
            }
        } else {
            double percentageValue = percentage.getContestFeePercentage();

            // calculate total cost
            double total = 0;

            // 1) prize
            final List<Prize> prizes = contest.getProjectHeader().getPrizes();

            for(Prize p : prizes) {
                total += p.getPrizeAmount() * p.getNumberOfSubmissions();
            }

            // 2) review cost
            if (contest.getProjectHeader().getProperty("Review Cost") != null) {
                total += Double.parseDouble(contest.getProjectHeader().getProperty("Review Cost"));
            }

            // 3) DR points
            if(contest.getProjectHeader().getProperty("Digital Run Flag") != null &&
                    contest.getProjectHeader().getProperty("Digital Run Flag").toLowerCase() == "on") {
                total += Double.parseDouble(contest.getProjectHeader().getProperty("DR points"));
            }

            // 4) spec review cost
            if (contest.getProjectHeader().getProperty("Spec Review Cost") != null) {
                total += Double.parseDouble(contest.getProjectHeader().getProperty("Spec Review Cost"));
            }

            // 5) reliability bonus
            if (contest.getProjectHeader().getProperty("Reliability Bonus Cost") != null) {
                total += Double.parseDouble(contest.getProjectHeader().getProperty("Reliability Bonus Cost"));
            }

            // 6) copilot fee
            if (contest.getProjectHeader().getProperty("Copilot Cost") != null) {
                total += Double.parseDouble(contest.getProjectHeader().getProperty("Copilot Cost"));
            }

            double currentFee = total * percentageValue;
            DecimalFormat fmt = new DecimalFormat("0.00");

            contest.getProjectHeader().setProperty("Admin Fee", fmt.format(currentFee));
            contest.getProjectHeader().setProperty("Contest Fee Percentage", String.valueOf(percentageValue));
        }

        return contest;
    }

    public ProjectContestFeeService getContestFeeService() {
        return contestFeeService;
    }

    public void setContestFeeService(ProjectContestFeeService contestFeeService) {
        this.contestFeeService = contestFeeService;
    }

    public ProjectContestFeePercentageService getContestFeePercentageService() {
        return contestFeePercentageService;
    }

    public void setContestFeePercentageService(ProjectContestFeePercentageService contestFeePercentageService) {
        this.contestFeePercentageService = contestFeePercentageService;
    }

    /**
     * Gets the form data.
     *
     * @return the form data.
     */
    public ProjectIdForm getFormData() {
        return formData;
    }

    /**
     * Sets the form data.
     *
     * @param formData the form data.
     */
    public void setFormData(ProjectIdForm formData) {
        this.formData = formData;
    }

    /**
     * Gets the view data.
     *
     * @return the view data.
     */
    public ProjectDraftContestsEditDTO getViewData() {
        return viewData;
    }

    /**
     * Sets the view data.
     *
     * @param viewData the view data.
     */
    public void setViewData(ProjectDraftContestsEditDTO viewData) {
        this.viewData = viewData;
    }

    /**
     * Gets the contests to update.
     *
     * @return the contests to update.
     */
    public List<DraftContestEditForm> getContestsToUpdate() {
        return contestsToUpdate;
    }

    /**
     * Sets the contest to update.
     *
     * @param contestsToUpdate the contests to update.
     */
    public void setContestsToUpdate(List<DraftContestEditForm> contestsToUpdate) {
        this.contestsToUpdate = contestsToUpdate;
    }

    /**
     * Compare the <code>ProjectContestDTO</code> by start date.
     */
    private static class DraftContestComparator implements Comparator<ProjectContestDTO> {
        public int compare(ProjectContestDTO o1, ProjectContestDTO o2) {
            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    }
}
