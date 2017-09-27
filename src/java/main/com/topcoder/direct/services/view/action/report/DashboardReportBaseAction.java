/*
 * Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.dto.ReportBaseDTO;
import com.topcoder.direct.services.view.form.DashboardReportForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>A <code>Struts</code> action which will be base class of report actions. This class will parse
 * and validate the parameters, prepare for the common data used by the report page.</p>
 *
 * <p>
 *  Version 1.1 (Release Assembly - TC Cockpit Report Filters Group By Metadata Feature and Coordination Improvement) change notes:
 *  <ol>
 *      <li>Add the handle of view data and form data of group by and group values filter for both Cost report and
 *      billing cost report</li>
 *  </ol>
 * </p>
 * <p>
 *  Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) change notes:
 *  <ol>
 *      <li>Update action error message to "Start date should be smaller than End date"</li>
 *  </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 * - Removes the statements to populate the right sidebar direct projects and project contests. It's changed to
 * load these data via ajax instead after the page finishes loading.
 * </p>
 *
 * @author Ghost_141, Veve
 * @version 1.3
 * @param <FORMT> a type of the form used by the report page. It must extends from <code>DashboardReportForm</code>.
 * @param <VIEWT> a type of the view used by the report page. It must extends from <code>ReportBaseDTO</code>.
 */
public abstract class DashboardReportBaseAction<FORMT extends DashboardReportForm, VIEWT extends ReportBaseDTO> extends BaseDirectStrutsAction {

    /**
     * <p>Represents the serial version unique id.</p>
     */
    private static final long serialVersionUID = -8277804713736310922L;

    /**
     * <p>The contest status used by report. There are 3 status used for report. It's initialized in the
     * static constructor of this action class.</p>
     */
    protected static final Map<Long, String> REPORT_CONTEST_STATUS;

    /**
     * <p>The status used by report. There are 3 status used for billing cost report. it's initialized in the
     * static constructor of this action class.</p>
     */
    protected static final Map<String, Long> REPORT_CONTEST_STATUS_IDS;

    /**
     * <p>The default duration used for date filter.</p>
     */
    protected static final long DEFAULT_DURATION = 182 * 24 * 3600 * 1000L;

    /**
     * <p>Status constructor of this action.</p>
     */
    static {

        // initialize the contest status
        REPORT_CONTEST_STATUS = new HashMap<Long, String>();
        REPORT_CONTEST_STATUS_IDS = new HashMap<String, Long>();

        IdNamePair scheduled = new IdNamePair();
        IdNamePair active = new IdNamePair();
        IdNamePair finished = new IdNamePair();
        scheduled.setId(1);
        scheduled.setName("Scheduled");
        active.setId(2);
        active.setName("Active");
        finished.setId(3);
        finished.setName("Finished");
        REPORT_CONTEST_STATUS.put(scheduled.getId(), scheduled.getName());
        REPORT_CONTEST_STATUS.put(active.getId(), active.getName());
        REPORT_CONTEST_STATUS.put(finished.getId(), finished.getName());
        REPORT_CONTEST_STATUS_IDS.put(scheduled.getName().toLowerCase(), scheduled.getId());
        REPORT_CONTEST_STATUS_IDS.put(active.getName().toLowerCase(), active.getId());
        REPORT_CONTEST_STATUS_IDS.put(finished.getName().toLowerCase(), finished.getId());
    }

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * <p>A <code>FORMT</code> providing the report form parameters submitted by user.</p>
     */
    private FORMT formData;

    /**
     * <p>A <code>VIEWT</code> providing the view data for displaying the view data for report page.</p>
     */
    private VIEWT viewData;

    /**
     * <p>Direct projects data accessible by current user.</p>
     */
    private List<ProjectData> directProjectsData;

    /**
     * <p>Constructs new <code>DashboardReportBaseAction</code> instance</p>
     */
    protected DashboardReportBaseAction() {
        super();
    }

    /**
     * <p>Handles the incoming request. It will parse and validate the parameters, prepare for the
     * common data used by the report page.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // Get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());
        TCSubject currentUser = getCurrentUser();

        DashboardReportForm form = getFormData();
        long projectId = form.getProjectId();
        long[] categoryIds = form.getProjectCategoryIds();
        long customerId = form.getCustomerId();
        long[] statusIds = form.getStatusIds();
        Date startDate = DirectUtils.getDate(form.getStartDate());
        Date endDate = DirectUtils.getDate(form.getEndDate());

        // If start date is not set then use date for half of a year before current time
        SimpleDateFormat dateFormat = new SimpleDateFormat(DirectUtils.DATE_FORMAT);
        Date now = new Date();
        if (startDate == null) {
            startDate = new Date(now.getTime() - (DEFAULT_DURATION));
            form.setStartDate(dateFormat.format(startDate));
        }

        // If end date is not set then use current time
        if (endDate == null) {
            endDate = now;
            form.setEndDate(dateFormat.format(endDate));
        }

        // Get the list of available project categories
        Map<Long, String> projectCategories = DataProvider.getAllProjectCategories();

        // Get all the clients accessible by current user
        Map<Long, String> customers = DirectUtils.getAllClients(currentUser);

        boolean isFirstCall = this.viewData.isShowJustForm();

        // If client account IDs are not specified then use the first client account id
        boolean customerIdIsSet = customerId > 0;
        if (isFirstCall && !customerIdIsSet) {
            for (long clientId : customers.keySet()) {
                customerId = clientId;
                form.setCustomerId(customerId);
                customerIdIsSet = true;
                break;
            }

        } else {
            // check the customerId parameter
            if(customerId > 0 ) {
               checkParameters(customerId, "customerId", customers);
            }
        }

        // If project category IDs are not specified then use all project category Ids
        boolean categoryIdsAreSet = (categoryIds != null) && (categoryIds.length > 0);
        if (isFirstCall && !categoryIdsAreSet) {
            Set<Long> keySet = projectCategories.keySet();
            int index = 0;
            categoryIds = new long[keySet.size()];
            for (Long id : keySet) {
                categoryIds[index++] = id;
            }
            form.setProjectCategoryIds(categoryIds);
            categoryIdsAreSet = true;
        }

        // if status IDs are not specified then use all status ids
        boolean statusIdsAreSet = (statusIds != null) && (statusIds.length > 0);
        if (isFirstCall && !statusIdsAreSet) {
            statusIds = new long[REPORT_CONTEST_STATUS.size()];
            int count = 0;
            for (Long l : REPORT_CONTEST_STATUS.keySet()) {
                statusIds[count] = l;
                count++;
            }
            getFormData().setStatusIds(statusIds);
            statusIdsAreSet = true;
        }

        // set all the project categories to view data to populate project category selection
        getViewData().setProjectCategories(projectCategories);

        // set all the report status to view data to populate report contest status (3 total: active, scheduled, finished)
        getViewData().setContestStatus(REPORT_CONTEST_STATUS);

        // set view data for clients
        getViewData().setClientAccounts(customers);

        // set view data for billings
        if (getFormData().getCustomerId() > 0) {
            getViewData().setClientBillingProjects(DirectUtils.getBillingsForClient(currentUser, getFormData().getCustomerId()));
        } else {
            getViewData().setClientBillingProjects(new HashMap<Long, String>());
        }

        getViewData().getClientBillingProjects().put(0L, "All Billing Accounts");

        // set view data for projects
        if (getFormData().getBillingAccountId() <= 0) {
            if (getFormData().getCustomerId() > 0) {
                getViewData().setProjectsLookupMap(DirectUtils.getProjectsForClient(currentUser, getFormData().getCustomerId()));
            } else {
                getViewData().setProjectsLookupMap(new HashMap<Long, String>());
            }
        } else {
            // check the billing parameter
            checkParameters(getFormData().getBillingAccountId(), "billingAccountId", getViewData().getClientBillingProjects());
            getViewData().setProjectsLookupMap(DirectUtils.getProjectsForBilling(currentUser,
                    getFormData().getBillingAccountId()));
        }

        // add the default all for projects
        getViewData().getProjectsLookupMap().put(0L, "All Projects");
        if (projectId > 0) {
            // check projectId parameter
            checkParameters(projectId, "projectId", getViewData().getProjectsLookupMap());
        }

        // handle the group by and group values view data and form data
        getViewData().setGroupKeys(new LinkedHashMap<Long, String>());
        getViewData().getGroupKeys().put(-1L, "No Grouping");
        getViewData().setGroupValues(new LinkedHashSet<String>());

        // Validate the dates range
        if (startDate.compareTo(endDate) > 0) {
            addActionError("Start date should be smaller than End date");
            return;
        }

        if (getFormData().getCustomerId() > 0) {
            // set Group By drop down view data
            final List<DirectProjectMetadataKey> clientProjectMetadataKeys =
                    getMetadataKeyService().getClientProjectMetadataKeys(getFormData().getCustomerId(), true);

            for (DirectProjectMetadataKey key : clientProjectMetadataKeys) {
                getViewData().getGroupKeys().put(key.getId(), key.getName());
            }

            if (getFormData().getGroupId() > 0) {
                // set Group Values multiple selection view data
                final List<DirectProjectMetadata> values =
                        getMetadataService().getProjectMetadataByKey(getFormData().getGroupId());
                for (DirectProjectMetadata value : values) {
                    getViewData().getGroupValues().add(value.getMetadataValue());
                }
                
                // add None option to the end
                getViewData().getGroupValues().add("None");
            }
        }
    }

    /**
     * <p>Sets the form data.</p>
     *
     * @param formData A <code>FORMT</code> providing the report form parameters submitted by user.
     */
    public void setFormData(FORMT formData) {
        this.formData = formData;
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return A <code>FORMT</code> providing the report form parameters submitted by user.
     */
    public FORMT getFormData() {
        return formData;
    }

    /**
     * <p>Sets the view data.</p>
     *
     * @param viewData A <code>VIEWT</code> providing the view data for displaying the view data for report page.
     */
    public void setViewData(VIEWT viewData) {
        this.viewData = viewData;
    }

    /**
     * <p>Gets the view data.</p>
     *
     * @return A <code>VIEWT</code> providing the view data for displaying the view data for report page.
     */
    public VIEWT getViewData() {
        return viewData;
    }

    /**
     * <p>Gets the current session associated with the incoming request from client.</p>
     *
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData() {
        return sessionData;
    }

    /**
     * <p>Gets the direct project the user has access to.</p>
     *
     * @return the list of project data
     * @throws Exception if any error occurs.
     */
    public List<ProjectData> getDashboardDirectProjects() throws Exception {
        if(this.directProjectsData == null) {
            this.directProjectsData = getProjects();
        }
        return this.directProjectsData;
    }
    

    /**
     * <p>Validate the parameter. The valid values is the key set of a map.</p>
     * <p>If the parameter is invalid, the exception message will contain the parameter name
     * and parameter value so it will be logged.</p>
     *
     * @param parameter the value of the parameter
     * @param parameterName the name of the parameter
     * @param validData the valid values map. The valid values is the key set of this map.
     * @throws DirectException if the parameter is not valid.
     */
    private static void checkParameters(long parameter, String parameterName, Map<Long, String> validData) throws DirectException {
        if (validData.size() == 0 && parameter == 0) {
            return;
        }
        if (!validData.keySet().contains(new Long(parameter))) {
            throw new DirectException("The parameter [" + parameterName + ":" + parameter + "] is invalid.");
        }
    }
}
