/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.copilot;

import com.topcoder.direct.services.copilot.dto.CopilotPoolMember;
import com.topcoder.direct.services.view.dto.MemberPhotoDTO;

/**
 * A class to be used for represent copilot profile.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TC Direct Select From Copilot Pool Assembly)
 */
public class CopilotProfileDTO {
    /**
     * The copilot pool member.
     */
    private CopilotPoolMember member;

    /**
     * The class of status.
     */
    private String statusClass;

    /**
     * the text of status.
     */
    private String statusText;

    /**
     * The fullfillment.
     */
    private String fullfillment;

    /**
     * The photo of copilot.
     */
    private MemberPhotoDTO photo;

    /**
     * Get the member.
     *
     * @return the member.
     */
    public CopilotPoolMember getMember() {
        return member;
    }

    /**
     * Set the member.
     *
     * @param member the member.
     */
    public void setMember(CopilotPoolMember member) {
        this.member = member;
    }

    /**
     * Get the status class.
     *
     * @return the status class.
     */
    public String getStatusClass() {
        return statusClass;
    }

    /**
     * Set the status class.
     *
     * @param statusClass the status class.
     */
    public void setStatusClass(String statusClass) {
        this.statusClass = statusClass;
    }

    /**
     * Get the status text.
     *
     * @return the status text.
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * Set the status text.
     *
     * @param statusText the status text.
     */
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    /**
     * Get the fullfillment.
     *
     * @return the fullfillment.
     */
    public String getFullfillment() {
        return fullfillment;
    }

    /**
     * Set the fullfillment.
     *
     * @param fullfillment the fullfillment.
     */
    public void setFullfillment(String fullfillment) {
        this.fullfillment = fullfillment;
    }

    /**
     * Get the photo.
     *
     * @return the photo.
     */
    public MemberPhotoDTO getPhoto() {
        return photo;
    }

    /**
     * Set the photo.
     *
     * @param photo the photo.
     */
    public void setPhoto(MemberPhotoDTO photo) {
        this.photo = photo;
    }
}
