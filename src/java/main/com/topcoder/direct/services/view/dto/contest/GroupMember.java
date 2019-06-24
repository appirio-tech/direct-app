/*
 * Copyright (C) 2017 - 2019 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * Group member dto
 *
 * Version 1.1 (Topcoder - Integrate Direct with Groups V5)
 * <ul>
 *     <li>Refactor projectGroup to comply with v5</li>
 * </ul>
 * @author TCSCODER
 * @version 1.1
 */
public class GroupMember {
    public static final String GROUP = "group";

    /**
     * Member id
     */
    private String memberId;

    /**
     * Membership type:
     *  "user" for user and "group" for group
     */
    private String membershipType;

    public GroupMember() {
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    /**
     * Check whether this is group
     *
     * @return true if membership type is "group"
     */
    public boolean isGroup() {
        return GROUP.equals(membershipType.toLowerCase());
    }
}
