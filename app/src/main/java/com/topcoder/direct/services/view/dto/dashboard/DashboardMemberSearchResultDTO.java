/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.PermissionType;

import java.io.Serializable;

/**
 * <p>A DTO class providing the results for member search to be displayed by dashboard view.</p>
 * 
 * @author isv
 * @version 1.0
 */
public class DashboardMemberSearchResultDTO implements Serializable {

    private ContestBriefDTO contest;

    private long userId;

    private String handle;

    private PermissionType[] permissions;

    /**
     * <p>Constructs new <code>DashboardMemberSearchResultDTO</code> instance. This implementation does nothing.</p>
     */
    public DashboardMemberSearchResultDTO() {
    }

    public ContestBriefDTO getContest() {
        return contest;
    }

    public void setContest(ContestBriefDTO contest) {
        this.contest = contest;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public PermissionType[] getPermissions() {
        return permissions;
    }

    public void setPermissions(PermissionType[] permissions) {
        this.permissions = permissions;
    }
}
