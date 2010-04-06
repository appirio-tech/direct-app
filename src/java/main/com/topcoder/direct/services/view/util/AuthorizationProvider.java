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
 * @version 1.0
 */
public class AuthorizationProvider {

    /**
     * <p>Constructs new <code>AuthorizationProvider</code> instance. This implementation does nothing.</p>
     */
    private AuthorizationProvider() {
    }


    public static boolean isUserGrantedAccessToProject(long currentUserId, long projectId) {
        return true;
    }

    public static boolean isUserGrantedAccessToContest(long currentUserId, long contestId) {
        return true;
    }
}
