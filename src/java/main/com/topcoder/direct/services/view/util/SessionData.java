/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.security.TCSubject;

/**
 * <p>A helper class to be used for wrapping the current HTTP session associated with the incoming request. This class
 * provides the helpful methods for managing the session attributes so that front-end actions, views as well as request
 * processors do not have to deal with concrete session attribute names and thus are provided with consistent API for
 * managing the user session state.</p>
 *
 * <p>
 * Version 1.1 - Direct Search Assembly - add getCurrentUser method
 * </p>
 *
 * @author isv, BeBetter
 * @version 1.1
 */
public class SessionData {

    /**
     * <p>An <code>HttpSession</code> managed by this helper.</p>
     */
    private HttpSession session;

    /**
     * <p>Constructs new <code>SessionData</code> instance wrapping the specified HTTP session.</p>
     *
     * @param session an <code>HttpSession</code> providing the current session associated with incoming request.
     * @throws IllegalArgumentException if specified <code>session</code> is <code>null</code>.
     */
    public SessionData(HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("The parameter [session] is NULL");
        }
        this.session = session;
    }

    /**
     * <p>Checks if the user associated with incoming request is authenticated to application or not.</p>
     *
     * @return <code>true</code> if user is anonymous; <code>false</code> otherwise.
     */
    public boolean isAnonymousUser() {
        return this.session.getAttribute("user") == null;
    }

    /**
     * <p>Gets the ID for current user associated with the incoming request.</p>
     *
     * @return a <code>long</code> providing the ID of authenticated user or <code>-1</code> if user is anonymous.
     */
    public long getCurrentUserId() {
        if (isAnonymousUser()) {
            return -1;
        } else {
            TCSubject user = (TCSubject) this.session.getAttribute("user");
            return user.getUserId();
        }
    }

    /**
     * <p>Gets the current number of registered <code>TopCoder</code> members.</p>
     *
     * @return an <code>int</code> providing the current number of registered <code>TopCoder</code> members.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public int getMemberCount() throws Exception {
        try {
            Integer memberCount = (Integer) this.session.getAttribute("member_count");
            if (memberCount == null) {
                memberCount = DataProvider.getMemberCount();
                this.session.setAttribute("member_count", memberCount);
            }
            return memberCount;
        } catch (Throwable e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * <p>Sets the details for current user authenticated to application and to be associated with the current HTTP
     * session.</p>
     *
     * @param tcSubject a <code>TCSubject</code> representing the authenticated user to be associated with the current
     *        HTTP session. <code>null</code> value results in removing current user details from session.
     */
    public void setCurrentUser(TCSubject tcSubject) {
        if (null == tcSubject) {
            this.session.removeAttribute("user");
        } else {
            this.session.setAttribute("user", tcSubject);
        }
    }

    /**
     * Gets current user.
     *
     * @return the current user.
     */
    public TCSubject getCurrentUser() {
        return (TCSubject)this.session.getAttribute("user");
    }

    /**
     * <p>Gets the username for current user authenticated to application.</p>
     *
     * @return a <code>String</code> providing the username for current user.
     */
    public String getCurrentUserHandle() {
        return (String) this.session.getAttribute("userHandle");
    }

    /**
     * <p>Sets the username for current user authenticated to application.</p>
     *
     * @param username a <code>String</code> providing the username for current user.
     */
    public void setCurrentUserHandle(String username) {
        this.session.setAttribute("userHandle", username);
    }

    /**
     * <p>Sets the specified project as current project being viewed/managed by the current user.</p>
     *
     * @param project a <code>ProjectBriefDTO</code> providing the details for a project to be set as current project
     *        context for views displayed to current user.
     */
    public void setCurrentProjectContext(ProjectBriefDTO project) {
        if (project == null) {
            this.session.removeAttribute("currentProject");
        } else {
            this.session.setAttribute("currentProject", project);
        }
    }

    /**
     * <p>Gets the current project if any is currently set for current session.</p>
     *
     * @return a <code>ProjectBriefDTO</code> providing the current project set for current session or <code>null</code>
     *         if there is no such project set.
     */
    public ProjectBriefDTO getCurrentProjectContext() {
        return (ProjectBriefDTO) this.session.getAttribute("currentProject");
    }

    /**
     * <p>Sets the list of contests for current project.</p>
     *
     * @param contests a <code>List</code> listing the contests for current project.
     */
    public void setCurrentProjectContests(List<TypedContestBriefDTO> contests) {
        if (contests == null) {
            this.session.removeAttribute("currentProjectContests");
        } else {
            this.session.setAttribute("currentProjectContests", contests);
        }
    }

    /**
     * <p>Gets the list of contests for current project.</p>
     *
     * @return a <code>List</code> listing the contests for current project.
     */
    public List<TypedContestBriefDTO> getCurrentProjectContests() {
        return (List<TypedContestBriefDTO>) this.session.getAttribute("currentProjectContests");
    }
}
