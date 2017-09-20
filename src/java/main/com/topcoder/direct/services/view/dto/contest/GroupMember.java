/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * Group member dto
 *
 * @version 1.0
 */
public class GroupMember {
    public static final String GROUP = "group";

    /**
     * Member id
     */
    private Long memberId;

    /**
     * Membership type:
     *  "user" for user and "group" for group
     */
    private String membershipType;

    public GroupMember() {
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
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
