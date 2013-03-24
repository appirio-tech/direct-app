/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.enterpriseDashboard;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  The base action for the new enterprise dashboard, it acts as the entry point as the JSP pages of enterprise
 *  dashboard.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 1)
 * <ul>
 *     <li>Adds a getter for getting current time in server</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
 * <ul>
 *     <li>Adds method {@link #getFilteredProjects()}</li>
 *     <li>Adds method {@link #getOptionsForClient()}</li>
 *     <li>Adds properties {@link #formData} and its getter and setter</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.2
 */
public class BaseDashboardAction extends BaseDirectStrutsAction {

    /**
     * The default start year of the timeline filter.
     */
    private static final int DEFAULT_START_YEAR = 2005;

    /**
     * The months label.
     */
    private static final String[] MONTHS = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    /**
     * The clients displayed in the filter.
     */
    private Map<Long, String> clients;

    /**
     * The project status displayed in the filter.
     */
    private Map<Long, String> directProjectStatus;

    /**
     * The client projects displayed in the filter.
     */
    private Map<Long, String> clientProjects;

    /**
     * The client metadata keys displayed in the filter.
     */
    private Map<Long, String> clientMetadataKeys;

    /**
     * Indicates whether the enterprise dashboard page allow checking future timeline.
     *
     * @since BUGR-7664
     */
    private boolean allowFuturePeek;

    /**
     * The default project status used for filtering - Active is default.
     */
    private long defaultProjectStatus = 1L;

    /**
     * The form data of the action.
     *
     * @since 1.2
     */
    private EnterpriseDashboardFilterForm formData = new EnterpriseDashboardFilterForm();

    /**
     * Gets the form data of the action.
     *
     * @return the form data of the action.
     *
     * @since 1.2
     */
    public EnterpriseDashboardFilterForm getFormData() {
        return this.formData;
    }

    /**
     * Sets the form data of the action.
     *
     * @param formData the form data of the action.
     *
     * @since 1.2
     */
    public void setFormData(EnterpriseDashboardFilterForm formData) {
        this.formData = formData;
    }

    /**
     * Gets the clients map.
     *
     * @return the clients map.
     */
    public Map<Long, String> getClients() {
        return clients;
    }

    /**
     * Sets the client map.
     *
     * @param clients the clients map.
     */
    public void setClients(Map<Long, String> clients) {
        this.clients = clients;
    }

    /**
     * Gets the direct project status.
     *
     * @return the direct project status.
     */
    public Map<Long, String> getDirectProjectStatus() {
        return directProjectStatus;
    }

    /**
     * Sets the direct project status.
     *
     * @param directProjectStatus the direct project status.
     */
    public void setDirectProjectStatus(Map<Long, String> directProjectStatus) {
        this.directProjectStatus = directProjectStatus;
    }

    /**
     * Gets the client projects.
     *
     * @return the client projects.
     */
    public Map<Long, String> getClientProjects() {
        return clientProjects;
    }

    /**
     * Sets the client projects.
     *
     * @param clientProjects the client projects.
     */
    public void setClientProjects(Map<Long, String> clientProjects) {
        this.clientProjects = clientProjects;
    }

    /**
     * Gets the client metadata keys.
     *
     * @return the client metadata keys.
     */
    public Map<Long, String> getClientMetadataKeys() {
        return clientMetadataKeys;
    }

    /**
     * Sets the client metadata keys.
     *
     * @param clientMetadataKeys the client metadata keys.
     */
    public void setClientMetadataKeys(Map<Long, String> clientMetadataKeys) {
        this.clientMetadataKeys = clientMetadataKeys;
    }

    /**
     * Gets the default client to display when first enters the JSP page.
     *
     * @return the default client to display when first enters the JSP page.
     */
    public String getDefaultClient() {

        for(Map.Entry<Long, String> client : getClients().entrySet()) {
            return client.getValue();
        }
        return "None";
    }

    /**
     * Gets the month options to display on the filter panel.
     *
     * @return the month options to display on the filter panel.
     */
    public List<String> getMonthOptions() {
        int startYear =  DEFAULT_START_YEAR;
        int endYear = Calendar.getInstance().get(Calendar.YEAR);

        if(allowFuturePeek) {
            // allow to view next year if allowFuturePeek is true
            endYear++;
        }

        List<String> monthOptions = new ArrayList<String>();

        for(int year = startYear; year <= endYear; year++) {
            for(String m : MONTHS) {
                monthOptions.add(m + "'" + String.valueOf(year).substring(2));
            }
        }

        return monthOptions;
    }

    /**
     * Gets the default project status used for filter.
     *
     * @return the default project status id.
     */
    public long getDefaultProjectStatus() {
        return defaultProjectStatus;
    }

    /**
     * Sets the default project status used for filter.
     *
     * @param defaultProjectStatus the default project status id.
     *
     * @since 1.2
     */
    public void setDefaultProjectStatus(long defaultProjectStatus) {
        this.defaultProjectStatus = defaultProjectStatus;
    }

    /**
     * Prepares the data for displaying the enterprise dashboard page and filter panel.
     *
     * @throws Exception if there is error
     */
    @Override
    protected void executeAction() throws Exception {
        final TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
        // prepare the available clients for the filter panel
        setClients(DirectUtils.getAllClients(currentUser));

        // prepare the available project status for the filter panel
        setDirectProjectStatus(new LinkedHashMap<Long, String>());
        getDirectProjectStatus().put(0L, "All Project Status");
        final Map<Long, String> allDirectProjectStatus = DataProvider.getAllDirectProjectStatus();
        for(Long statusId : allDirectProjectStatus.keySet()) {
            getDirectProjectStatus().put(statusId, allDirectProjectStatus.get(statusId));
        }

        setClientProjects(new LinkedHashMap<Long, String>());
        setClientMetadataKeys(new LinkedHashMap<Long, String>());

        if(getClients().size() > 0) {
            for(Map.Entry<Long, String> client : getClients().entrySet()) {
                // get projects for the first client
                getClientProjects().put(0L, "All Projects");
                final Map<Long, String> projectsForClient = DirectUtils.getProjectsForClient(currentUser, client.getKey());

                long[] projectIdsToFilter = new long[projectsForClient.size()];
                int count = 0;
                for(Long projectId : projectsForClient.keySet()) {
                    projectIdsToFilter[count++] = projectId;
                }

                Map<Long, String> filteredProjectIds = DataProvider.getFilteredProjectsResult(10000, 0,
                        getDefaultProjectStatus(), projectIdsToFilter, 0, "");

                getClientProjects().putAll(filteredProjectIds);

                // get metadata keys for the first client
                getClientMetadataKeys().put(0L, "None");
                final List<DirectProjectMetadataKey> clientProjectMetadataKeys =
                        getMetadataKeyService().getClientProjectMetadataKeys(client.getKey(), true);
                for(DirectProjectMetadataKey key : clientProjectMetadataKeys) {
                    getClientMetadataKeys().put(key.getId(), key.getName());
                }

                break;
            }

        }
    }

    /**
     * Sets whether the enterprise dashboard page allows future peek.
     *
      * @param allowFuturePeek whether the enterprise dashboard page allows future peek.
     */
    public void setAllowFuturePeek(boolean allowFuturePeek) {
        this.allowFuturePeek = allowFuturePeek;
    }

    /**
     * Gets current time on the server.
     *
     * @return the current time on the server.
     * @since 1.1
     */
    public Date getCalendarToday() {
        return new Date();
    }


    /**
     * Gets the filtered projects for the Enterprise Dashboard filter panel
     *
     * @return the result code.
     *
     * @since 1.2
     */
    public String getFilteredProjects() {
        try {
            setResult(DirectUtils.convertMapKeyToString(DataProvider.getEnterpriseDashboardFilteredProjects(getFormData())));
        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Gets the options of filter panel when a client is selected. It gets the projects of the client, project filters
     * of the client and project filter values if a project filter is specified.
     *
     * @return tje result code.
     * @since 1.2
     */
    public String getOptionsForClient() {
        try {
            Map<String, Object> result = new HashMap<String, Object>();

            // get projects for the client and match the given project status
            result.put("projects", DirectUtils.convertMapKeyToString(DataProvider
                    .getEnterpriseDashboardFilteredProjects(getFormData())));


            // get the project filters of a client
            final List<DirectProjectMetadataKey> clientProjectMetadataKeys = getMetadataKeyService()
                    .getClientProjectMetadataKeys(getFormData().getClientId(), true);
            Map<String, String> keyResult = new HashMap<String, String>();
            for (DirectProjectMetadataKey key : clientProjectMetadataKeys) {
                keyResult.put(String.valueOf(key.getId()), key.getName());
            }

            result.put("projectFilters", keyResult);

            // get the project filter values if a project filter is selected in filter panel
            if (getFormData().getProjectFilterId() > 0) {
                final List<DirectProjectMetadata> projectMetadataByKey = getMetadataService().getProjectMetadataByKey
                        (getFormData().getProjectFilterId());

                Set<String> valuesSet = new LinkedHashSet<String>();

                for (DirectProjectMetadata metadata : projectMetadataByKey) {
                    valuesSet.add(metadata.getMetadataValue());
                }

                // Map to store the ajax result
                List<String> values = new ArrayList<String>(valuesSet);

                Collections.sort(values);
                result.put("projectFilterValues", values);
            }

            setResult(result);
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }
}
