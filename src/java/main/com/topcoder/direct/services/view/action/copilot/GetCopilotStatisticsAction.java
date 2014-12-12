/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.dto.CopilotPoolMember;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.copilot.CopilotProfileDTO;
import com.topcoder.direct.services.view.util.DataProvider;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A class to be used for get copilot statistics.
 *
 * @author tangzx
 * @version 1.0
 */
public class GetCopilotStatisticsAction extends BaseDirectStrutsAction {

    /**
     * The default fullfillment text.
     */
    private static final String DEFAULT_FULLFILLMENT_TEXT = "n/a";

    /**
     * The format.
     */
    private static final NumberFormat FORMAT;

    /**
     * The copilot profile DAO.
     */
    private CopilotProfileDAO copilotProfileDAO;

    /**
     * Get the copilotProfileDAO.
     *
     * @return the copilotProfileDAO
     */
    public CopilotProfileDAO getCopilotProfileDAO() {
        return copilotProfileDAO;
    }

    /**
     * Set the copilotProfileDAO.
     *
     * @param copilotProfileDAO the copilotProfileDAO
     */
    public void setCopilotProfileDAO(CopilotProfileDAO copilotProfileDAO) {
        this.copilotProfileDAO = copilotProfileDAO;
    }

    /**
     * Initiate all static variables.
     */
    static {
        // set format
        FORMAT = NumberFormat.getPercentInstance();
        FORMAT.setMinimumFractionDigits(2);
        FORMAT.setMaximumFractionDigits(2);
    }

    /**
     * <p>
     * Handles the incoming request. Get copilot statistics.
     * </p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        Map<Long, CopilotPoolMember> members = DataProvider.getCopilotStatistics();
        HashMap<String, CopilotProfileDTO> profiles = new HashMap<String, CopilotProfileDTO>();

        for (Entry<Long, CopilotPoolMember> member : members.entrySet()) {
            CopilotProfileDTO profile = new CopilotProfileDTO();
            profile.setMember(member.getValue());

            // set fullfillment
            if (profile.getMember().getTotalContests() == 0) {
                profile.setFullfillment(DEFAULT_FULLFILLMENT_TEXT);
            } else {
                double fullfilment = (profile.getMember().getTotalContests() -
                        profile.getMember().getTotalFailedContests()) / (1.0 * profile.getMember().getTotalContests());
                profile.setFullfillment(FORMAT.format(fullfilment));
            }

            profiles.put(String.valueOf(member.getKey()), profile);
        }

        // set other profiles
        List<CopilotProfile> copilotProfiles = copilotProfileDAO.retrieveAll();
        for (CopilotProfile copilotProfile : copilotProfiles) {
            // set all fields to zero for others
            if (!profiles.containsKey(String.valueOf(copilotProfile.getUserId()))) {
                CopilotPoolMember member = new CopilotPoolMember();
                member.setCopilotProfile(copilotProfile);

                CopilotProfileDTO profile = new CopilotProfileDTO();
                profile.setFullfillment(DEFAULT_FULLFILLMENT_TEXT);
                profile.setMember(member);

                profiles.put(String.valueOf(copilotProfile.getUserId()), profile);
            }
        }

        setResult(profiles);
    }
}
