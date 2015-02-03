/*
 * Copyright (C) 2013 - 2015 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project.planner;

import com.topcoder.clients.dao.ProjectContestFeePercentageService;
import com.topcoder.clients.dao.ProjectContestFeeService;
import com.topcoder.clients.model.BillingAccount;
import com.topcoder.clients.model.ContestType;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.ProjectContestFeePercentage;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.planner.ProjectPlannerDTO;
import com.topcoder.direct.services.view.dto.project.planner.ProjectPlannerTransferDTO;
import com.topcoder.direct.services.view.exception.CockpitPermissionDeniedException;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.user.Registrant;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * This action handles all the requests in the project planner page.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Project Planner and game plan preview Update)
 * <ul>
 *     <li>Updated {@link #executeAction()} to set the bug race fee into view data.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (TopCoder Direct - Issues Fix Release Assembly 1)
 * <ul>
 *     <li>
 *         Fix "Win/Chrome-challenge details of "Copilot Posting" -after click submissions show server error"
 *         (https://github.com/cloudspokes/direct-app/issues/18)
 *     </li>
 * </ul>
 * </p>
 *
 * @author GreatKevin, Blues
 * @version 1.2
 * @since 1.0 (Module Assembly - TopCoder Cockpit Project Planner version 1.0)
 */
public class ProjectPlannerAction extends BaseDirectStrutsAction implements FormAction<ProjectIdForm> {

    /**
     * The date range of the data used to calculate the market average cost and duration for contest.
     */
    private static final int PAST_DAYS_FOR_MARKET_DATA = 6 * 30;

    /**
     * The default contest member cost for the contest if no market average data exists for the contest type.
     */
    private static final int DEFAULT_CONTEST_MEMBER_COST = 3500;

    /**
     * The default contest duration for the contest if no market average data exists for the contest type.
     */
    private static final int DEFAULT_CONTEST_DURATION = 24 * 10;

    /**
     * The form data.
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * The view data.
     */
    private ProjectPlannerDTO viewData = new ProjectPlannerDTO();

    /**
     * The billing account id.
     */
    private long billingAccountId;

    /**
     * The DTO for transferring project plan data.
     */
    private ProjectPlannerTransferDTO projectPlan = new ProjectPlannerTransferDTO();

    /**
     * The input stream to download the exported project plan excel.
     */
    private InputStream inputStream;

    /**
     * The session key for downloading the exported project plan excel.
     */
    private String projectPlanExportKey;

    /**
     * The uploaded project plan for importing.
     */
    private File uploadProjectPlan;

    /**
     * Instance of ProjectContestFeeService used to perform persistence operations. it is injected through spring IoC.
     * It is managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private ProjectContestFeeService contestFeeService;

    /**
     * Instance of ProjectContestFeeService used to perform persistence operations. it is injected through spring IoC.
     * It is managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private ProjectContestFeePercentageService contestFeePercentageService;

    /**
     * Sets the form data.
     *
     * @param formData the form data.
     */
    public void setFormData(ProjectIdForm formData) {
        this.formData = formData;
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
     * Gets the view data.
     *
     * @return the view data.
     */
    public ProjectPlannerDTO getViewData() {
        return viewData;
    }

    /**
     * Sets the view data.
     *
     * @param viewData the view data.
     */
    public void setViewData(ProjectPlannerDTO viewData) {
        this.viewData = viewData;
    }

    /**
     * Sets the billing account id.
     *
     * @param billingAccountId the billing account id.
     */
    public void setBillingAccountId(long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    /**
     * Gets the billing account id.
     *
     * @return the billing account id.
     */
    public long getBillingAccountId() {
        return billingAccountId;
    }

    /**
     * Gets the contest fee service.
     *
     * @return the contest fee service.
     */
    public ProjectContestFeeService getContestFeeService() {
        return contestFeeService;
    }

    /**
     * Sets the contest fee service.
     *
     * @param contestFeeService the contest fee service.
     */
    public void setContestFeeService(ProjectContestFeeService contestFeeService) {
        this.contestFeeService = contestFeeService;
    }

    /**
     * Gets the contest fee percentage service.
     *
     * @return the contest fee percentage service.
     */
    public ProjectContestFeePercentageService getContestFeePercentageService() {
        return contestFeePercentageService;
    }

    /**
     * Sets the contest fee percentage service.
     *
     * @param contestFeePercentageService the contest fee percentage service.
     */
    public void setContestFeePercentageService(ProjectContestFeePercentageService contestFeePercentageService) {
        this.contestFeePercentageService = contestFeePercentageService;
    }

    /**
     * Gets the project plan transfer DTO.
     *
     * @return the project plan transfer DTO.
     */
    public ProjectPlannerTransferDTO getProjectPlan() {
        return projectPlan;
    }

    /**
     * Sets the project plan transfer DTO.
     *
     * @param projectPlan the project plan transfer DTO.
     */
    public void setProjectPlan(ProjectPlannerTransferDTO projectPlan) {
        this.projectPlan = projectPlan;
    }

    /**
     * Gets the input stream.
     *
     * @return the input stream.
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * Sets the project plan export key.
     *
     * @param projectPlanExportKey the project plan export key.
     */
    public void setProjectPlanExportKey(String projectPlanExportKey) {
        this.projectPlanExportKey = projectPlanExportKey;
    }

    /**
     * Gets the upload project plan.
     *
     * @return the upload project plan.
     */
    public File getUploadProjectPlan() {
        return uploadProjectPlan;
    }

    /**
     * Sets the upload project plan.
     *
     * @param uploadProjectPlan the upload project plan.
     */
    public void setUploadProjectPlan(File uploadProjectPlan) {
        this.uploadProjectPlan = uploadProjectPlan;
    }

    /**
     * Entry action for displaying the project planner page.
     *
     * @throws Exception if any error.
     */
    @Override
    protected void executeAction() throws Exception {
        // check the permission
        checkProjectPlannerPagePermission();

        // all contest types
        getViewData().setContestTypes(DirectUtils.getContesetTypes());

        // all billing accounts
        getViewData().setBillingAccounts(
                getProjectServiceFacade().getBillingAccountsByProject(getFormData().getProjectId()));

        final ProjectData project = getProjectServiceFacade().getProject(DirectUtils.getTCSubjectFromSession(),
                getFormData().getProjectId());

        // bug race fee
        getViewData().setFixedBugRaceFee(project.getFixedBugContestFee());
        getViewData().setPercentageBugRaceFee(project.getPercentageBugContestFee());

        // project context data
        ProjectBriefDTO currentDirectProject = DirectUtils.createProjectBriefDTOFromProjectData(project);
        getSessionData().setCurrentProjectContext(currentDirectProject);
        getSessionData().setCurrentSelectDirectProjectID(currentDirectProject.getId());
    }

    /**
     * Helper method to check whether the user has permission to use the project planner of the current project.
     *
     * @throws Exception if any error
     * @throws CockpitPermissionDeniedException if the permission is denied
     */
    private void checkProjectPlannerPagePermission() throws Exception {
        // check permission first
        boolean hasPermission = false;

        if (AuthorizationProvider.isUserGrantedAccessToProject(DirectUtils.getTCSubjectFromSession(),
                                                               getFormData().getProjectId())) {
            // check if user has permission on the project
            hasPermission = true;
            getViewData().setCopilotPostingRegistrants(false);
        } else {
            // check if user is registrants of copilot postings of the project
            if (getCopilotPostingRegistrants().contains(DirectUtils.getTCSubjectFromSession().getUserId())) {
                hasPermission = true;
                getViewData().setCopilotPostingRegistrants(true);
            }
        }

        if (!hasPermission) {
            DirectUtils.getServletRequest().setAttribute("errorPageMessage",
                                                         "Sorry, you don't have permission"
                                                                 + " to access project planner of this project.");
            throw new CockpitPermissionDeniedException("User:" + DirectUtils.getTCSubjectFromSession().getUserId()
                                                               + " does not have permission to view project planner page of project:"
                                                               + getFormData().getProjectId());
        }
    }


    /**
     * Handles the ajax operation to get the project planner configuration with specific billing account id.
     *
     * @return the result code.
     */
    public String getProjectPlannerConfiguration() {

        try {
            // check the permission
            checkProjectPlannerPagePermission();

            setResult(getBillingAccountId() > 0 ?
                    getProjectPlannerConfigurationByBillingId(getBillingAccountId()) : new HashMap<String, String>());
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the ajax operation to export the project plan.
     *
     * @return the result code.
     */
    public String exportProjectPlan() {
        try {

            ProjectPlannerTransferDTO data = getProjectPlan();

            HSSFWorkbook workbook = ProjectPlanUtil.exportProjectPlanToExcel(data);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            String exportKey = "projectPlan" + System.currentTimeMillis();

            getSessionData().getSession().setAttribute(exportKey, new ByteArrayInputStream(out.toByteArray()));

            Map<String, String> result = new HashMap<String, String>();
            result.put("exportKey", exportKey);
            setResult(result);

        } catch (Throwable e) {

            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Handles the ajax operation to download the project plan.
     *
     * @return the result code.
     */
    public String downloadProjectPlan() {
        try {

            inputStream = (ByteArrayInputStream) getSessionData().getSession().getAttribute(this.projectPlanExportKey);
            getSessionData().getSession().removeAttribute(this.projectPlanExportKey);

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return "download";
    }

    /**
     * Handles the ajax operation to import the project plan.
     *
     * @return the result code.
     */
    public String importProjectPlan() {

        try {

            if (getUploadProjectPlan() == null || !getUploadProjectPlan().exists() || getUploadProjectPlan().length() == 0L) {
                throw new IllegalArgumentException(
                        "The import project plan file is of 0 bytes or does not exist, please check.");
            }

            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(getUploadProjectPlan()));

            ProjectPlannerTransferDTO data = ProjectPlanUtil.importProjectPlanFromExcel(workbook);

            ObjectMapper m = new ObjectMapper();
            Map<String, Object> result = m.convertValue(data, Map.class);

            setResult(result);

        } catch (IllegalArgumentException iae) {
            if (getModel() != null) {
                setResult(iae);
            }
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(new IllegalArgumentException("Error occurs when processing the import game plan,"
                                                               + " please check if the data in the project plan is correct"));
            }
        }

        return SUCCESS;
    }


    /**
     * Gets the project planner configuration with the specific billing account id.
     *
     * @param billingAccountId the billing account id.
     * @return the project planner configuration stored in a map.
     * @throws Exception if any error
     */
    private Map<String, Map<String, Object>> getProjectPlannerConfigurationByBillingId(long billingAccountId)
            throws Exception {

        // 1) market average contest member cost & market average contest duration for each contest type
        // in the past 6 months (180 days)
        Date endDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.DAY_OF_YEAR, -PAST_DAYS_FOR_MARKET_DATA);
        Date startDate = cal.getTime();

        Map<String, ContestType> contestTypes = DirectUtils.getContesetTypes();

        Set<String> typesSet = contestTypes.keySet();
        long[] typesId = new long[typesSet.size()];
        int count = 0;
        for (String s : typesSet) {
            typesId[count++] = Long.parseLong(s);
        }
        Map<Integer, List<Double>> averageData = DataProvider.getEnterpriseContestsAvgStatus(typesId, startDate,
                                                                                             endDate);

        // 2) get fee configuration for the billing account: fixed fee or percentage fee
        BillingAccount billingAccount = getContestFeeService().getBillingAccount(billingAccountId);
        ProjectContestFeePercentage percentage =
                getContestFeePercentageService().getByProjectId(billingAccountId);

        Map<Long, Double> fixedFees = new HashMap<Long, Double>();

        if (percentage == null || !percentage.isActive()) {
            billingAccount.setContestFeeFixed(true);

            if (billingAccount.getContestFees() == null || billingAccount.getContestFees().size() == 0) {
                billingAccount.setContestFeeFixed(false);
                billingAccount.setContestFeePercentage(1.0f);
            } else {
                // put fees into the map
                for (ProjectContestFee fee : billingAccount.getContestFees()) {
                    fixedFees.put(fee.getContestTypeId(), fee.getContestFee());
                }
                for (String typeIdStr : typesSet) {
                    if (!fixedFees.containsKey(Long.parseLong(typeIdStr))) {
                        fixedFees.put(Long.parseLong(typeIdStr), 0.0);
                    }
                }
            }

        } else {
            billingAccount.setContestFeeFixed(false);
            billingAccount.setContestFeePercentage(percentage.getContestFeePercentage());
        }

        Map<String, Map<String, Object>> configuration = new HashMap<String, Map<String, Object>>();

        for (String contestTypeStr : typesSet) {
            Map<String, Object> contestConfiguration = new HashMap<String, Object>();

            // market average duration & cost
            if (averageData.containsKey(Integer.parseInt(contestTypeStr))) {
                List<Double> averageList = averageData.get(Integer.parseInt(contestTypeStr));
                contestConfiguration.put("time", averageList.get(2) * 24);
                contestConfiguration.put("cost", averageList.get(1) > 0 ? averageList.get(1) : DEFAULT_CONTEST_MEMBER_COST);
            } else {
                contestConfiguration.put("time", DEFAULT_CONTEST_DURATION);
                contestConfiguration.put("cost", DEFAULT_CONTEST_MEMBER_COST);
            }

            // contest fee
            contestConfiguration.put("isFixedFee", billingAccount.isContestFeeFixed());

            if (billingAccount.isContestFeeFixed()) {
                contestConfiguration.put("fixedFee", fixedFees.get(Long.parseLong(contestTypeStr)));
                contestConfiguration.put("percentageFee", 0.0);
            } else {
                contestConfiguration.put("fixedFee", 0);
                contestConfiguration.put("percentageFee", billingAccount.getContestFeePercentage());
            }

            // contest type name
            contestConfiguration.put("name", contestTypes.get(contestTypeStr).getDescription());

            configuration.put(contestTypeStr, contestConfiguration);
        }

        return configuration;
    }

    /**
     * Gets the registrants of the copilot posting contests of the project.
     *
     * @return a set of the user ids of the registrants
     * @throws Exception if any error
     */
    private Set<Long> getCopilotPostingRegistrants() throws Exception {
        // result data
        Set<Long> copilotPostingRegistrants = new HashSet<Long>();
        Set<Long> billingAccounts = new TreeSet<Long>();

        // get all the copilot postings of the contest
        List<ContestBriefDTO> projectCopilotPostingContests = DataProvider.getProjectCopilotPostingContests(
                getFormData().getProjectId());

        for (ContestBriefDTO contest : projectCopilotPostingContests) {

            // it's copilot posting, get its registrants
            List<Registrant> registrants = getContestServiceFacade().getRegistrantsForProject(
                    DirectUtils.getTCSubjectFromSession(), contest.getId());

            for (Registrant r : registrants) {
                copilotPostingRegistrants.add(r.getUserId());
            }

            billingAccounts.add(contest.getBillingAccountId());
        }

        if (billingAccounts.size() >= 1) {
            // use the latest billing account id
            billingAccountId = ((TreeSet<Long>) billingAccounts).last();
        }

        return copilotPostingRegistrants;
    }
}
