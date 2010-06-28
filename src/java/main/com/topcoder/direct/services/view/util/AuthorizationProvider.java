/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

/**
 * <p>An utility class providing the methods for making authorization decisions.</p>
 *
 * <p>Sub-sequent assemblies may expand this class with additional methods for calling other queries when there is a
 * need or they can fully re-work the concept of authorization provider.</p>
 *
 * @author isv
 * @version 1.1
 */
public class AuthorizationProvider {

    /**
     * <p>Constructs new <code>AuthorizationProvider</code> instance. This implementation does nothing.</p>
     */
    private AuthorizationProvider() {
    }

    /**
     * <p>Checks if specified user is granted access permission to specified project.</p>
     *
     * @param currentUserId a <code>long</code> providing the user ID.
     * @param projectId a <code>long</code> providing the project ID.
     * @return <code>true</code> if user is granted access to project; <code>false</code> otherwise.
     */
    public static boolean isUserGrantedAccessToProject(long currentUserId, long projectId) {
        return true;
    }

    /**
     * <p>Checks if specified user is granted access permission to specified Studio contest.</p>
     *
     * @param currentUserId a <code>long</code> providing the user ID.
     * @param contestId a <code>long</code> providing the contest ID.
     * @return <code>true</code> if user is granted access to Studio contest; <code>false</code> otherwise.
     */
    public static boolean isUserGrantedAccessToContest(long currentUserId, long contestId) {
        return true;
    }

    /**
     * <p>Checks if specified user is granted access permission to specified Software contest.</p>
     *
     * @param currentUserId a <code>long</code> providing the user ID.
     * @param contestId a <code>long</code> providing the contest ID.
     * @return <code>true</code> if user is granted access to Software contest; <code>false</code> otherwise.
     * @since 1.1
     */
    public static boolean isUserGrantedAccessToSoftwareContest(long currentUserId, long contestId) {
        return true;
    }
}
