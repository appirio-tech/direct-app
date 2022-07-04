/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.dashboard.specialistreport.PlatformSpecialistReportDTO;
import com.topcoder.direct.services.view.util.DataProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This action processes all the requests for platform specialists report.
 * </p>
 *
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TC Cockpit Platform Specialist Utilization Report and Graph)
 */
public class PlatformSpecialistsReportAction extends BaseDirectStrutsAction {
    /**
     * The default start year of the timeline filter.
     */
    private static final int DEFAULT_START_YEAR = 2005;

    /**
     * The months label.
     */
    private static final String[] MONTHS = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    /**
     * The user ids of the platform specialist in the request.
     */
    private String userIds;

    /**
     * The start filter month.
     */
    private String startMonth;

    /**
     * The end filter month.
     */
    private String endMonth;

    /**
     * The list stores all the TopCoder Platform Specialists.
     */
    private Map<Long, String> platformSpecialists;

    /**
     * Gets the TopCoder Platform Specialists.
     *
     * @return the TopCoder Platform Specialists.
     */
    public Map<Long, String> getPlatformSpecialists() {
        return platformSpecialists;
    }

    /**
     * Gets the ids of the platform specialists in the request.
     *
     * @return ids of the platform specialists in the request.
     */
    public String getUserIds() {
        return userIds;
    }

    /**
     * Sets ids of the platform specialists in the request.
     *
     * @param userIds ids of the platform specialists in the request.
     */
    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    /**
     * Gets the start month.
     *
     * @return the start month.
     */
    public String getStartMonth() {
        return startMonth;
    }

    /**
     * Sets the start month.
     *
     * @param startMonth the start month.
     */
    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    /**
     * Gets the end month.
     *
     * @return the end month.
     */
    public String getEndMonth() {
        return endMonth;
    }

    /**
     * Sets the end month.
     *
     * @param endMonth the end month.
     */
    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    /**
     * Gets the month options to display on the filter panel.
     *
     * @return the month options to display on the filter panel.
     */
    public List<String> getMonthOptions() {
        int startYear =  DEFAULT_START_YEAR;
        int endYear = Calendar.getInstance().get(Calendar.YEAR);

        List<String> monthOptions = new ArrayList<String>();

        for(int year = startYear; year <= endYear; year++) {
            for(String m : MONTHS) {
                monthOptions.add(m + "'" + String.valueOf(year).substring(2));
            }
        }

        return monthOptions;
    }

    /**
     * Prepares the data for displaying the platform specialist report and filter panel.
     *
     * @throws Exception if there is error
     */
    @Override
    protected void executeAction() throws Exception {
        this.platformSpecialists = DataProvider.getAllPlatformSpecialists();
    }

    /**
     * Gets the json data for the platform specialists report.
     *
     * @return the struts action result
     */
    public String getPlatformSpecialistsReportData() {
        try {

            List<PlatformSpecialistReportDTO> platformSpecialistReportData =
                    DataProvider.getPlatformSpecialistReportData(getUserIds(), getStartMonth(), getEndMonth());

            Map<String, List<Double>> result = new HashMap<String, List<Double>>();

            for (PlatformSpecialistReportDTO pmDTO : platformSpecialistReportData) {
                result.put(pmDTO.getUserHandle(), new ArrayList(pmDTO.getMonthsMemberSpend().values()));
            }

            setResult(result);
        } catch (Throwable e) {
            e.printStackTrace(System.err);
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }
}
