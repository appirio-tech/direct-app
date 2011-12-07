/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import com.topcoder.direct.services.copilot.CopilotProfileService;
import com.topcoder.direct.services.copilot.dto.CopilotPoolMember;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.MemberPhotoDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotProfileDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to be used for handling request of get a copilot page.
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class GetACopilotAction extends BaseDirectStrutsAction {
    /**
     * The mapping of status type to status class.
     */
    private static final Map<Long, String> STATUS_CLASS_MAPPING;

    /**
     * The default fullfillment text.
     */
    private static final String DEFAULT_FULLFILLMENT_TEXT = "n/a";

    /**
     * The default photo if can't find any specified photo.
     */
    private static final MemberPhotoDTO DEFAULT_MEMBER_PHOTO;

    /**
     * The format.
     */
    private static final NumberFormat FORMAT;

    /**
     * Initiate all static variables.
     */
    static {
        // set status class mapping
        STATUS_CLASS_MAPPING = new HashMap<Long, String>();

        STATUS_CLASS_MAPPING.put(1L, "greenText");
        STATUS_CLASS_MAPPING.put(2L, "yellowText");
        STATUS_CLASS_MAPPING.put(3L, "redText");

        // set format
        FORMAT = NumberFormat.getPercentInstance();
        FORMAT.setMinimumFractionDigits(2);
        FORMAT.setMaximumFractionDigits(2);

        // set default member photo
        DEFAULT_MEMBER_PHOTO = new MemberPhotoDTO();
        DEFAULT_MEMBER_PHOTO.setPhotoPath("/images/copilot_img.png");
        DEFAULT_MEMBER_PHOTO.setWidth(96);
        DEFAULT_MEMBER_PHOTO.setHeight(106);
    }

    /**
     * <p>
     * A <code>SessionData</code> providing interface to current session.
     * </p>
     */
    private SessionData sessionData;

    /**
     * The list of profiles.
     */
    private List<CopilotProfileDTO> profiles;

    /**
     * The copilot profile service.
     */
    private CopilotProfileService copilotProfileService;

    /**
     * Execute the action, include all handling logics.
     *
     * @throws Exception if any exception occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());

        // initiate profiles
        profiles = new ArrayList<CopilotProfileDTO>();

        // get all copilots
        List<CopilotPoolMember> members = copilotProfileService.getCopilotPoolMembers();
        long[] userIds = new long[members.size()];

        for (int i = 0; i < members.size(); i++) {
            CopilotPoolMember member = members.get(i);
            CopilotProfileDTO profile = new CopilotProfileDTO();
            profile.setMember(member);

            profiles.add(profile);

            // add to userIds
            userIds[i] = member.getCopilotProfile().getUserId();
        }

        // retrieve member photos
        Map<Long, MemberPhotoDTO> photos = DataProvider.getMemberPhotos(userIds);

        // set other fields
        for (CopilotProfileDTO profile : profiles) {
            // set status class and text
            CopilotProfileStatus tmpStatus = profile.getMember().getCopilotProfile().getStatus();
            profile.setStatusClass(STATUS_CLASS_MAPPING.get(tmpStatus.getId()));
            profile.setStatusText(tmpStatus.getName());

            // set fullfillment
            if (profile.getMember().getTotalContests() == 0) {
                profile.setFullfillment(DEFAULT_FULLFILLMENT_TEXT);
            } else {
                double fullfilment = (profile.getMember().getTotalContests() -
                        profile.getMember().getTotalFailedContests()) / (1.0 * profile.getMember().getTotalContests());
                profile.setFullfillment(FORMAT.format(fullfilment));
            }

            // set photo
            if (photos.containsKey(profile.getMember().getCopilotProfile().getUserId())) {
                profile.setPhoto(photos.get(profile.getMember().getCopilotProfile().getUserId()));
            } else {
                profile.setPhoto(DEFAULT_MEMBER_PHOTO);
            }
        }

    }

    /**
     * Get the session data.
     *
     * @return the session data
     */
    public SessionData getSessionData() {
        return sessionData;
    }

    /**
     * Set the session data.
     *
     * @param sessionData the session data
     */
    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }

    /**
     * Get the profiles.
     *
     * @return the profiles.
     */
    public List<CopilotProfileDTO> getProfiles() {
        return profiles;
    }

    /**
     * Set the profiles.
     *
     * @param profiles the profiles.
     */
    public void setProfiles(List<CopilotProfileDTO> profiles) {
        this.profiles = profiles;
    }

    /**
     * Get the copilot profile service.
     *
     * @return the copilot profile service.
     */
    public CopilotProfileService getCopilotProfileService() {
        return copilotProfileService;
    }

    /**
     * Set the copilot profile service.
     *
     * @param copilotProfileService the copilot profile service.
     */
    public void setCopilotProfileService(CopilotProfileService copilotProfileService) {
        this.copilotProfileService = copilotProfileService;
    }
}
