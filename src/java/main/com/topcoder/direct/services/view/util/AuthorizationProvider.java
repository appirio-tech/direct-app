/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.security.TCSubject;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;

/**
 * <p>An utility class providing the methods for making authorization decisions.</p>
 *
 * <p>Sub-sequent assemblies may expand this class with additional methods for calling other queries when there is a
 * need or they can fully re-work the concept of authorization provider.</p>
 *
 * <p>
 * Version 1.1 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>Remove method <code>isUserGrantedAccessToSoftwareContest</code>.</li>
 *   <li>Add a parameter <code>TCSubject</code> to methods {@link #isUserGrantedAccessToProject(TCSubject, long)}
 *   and {@link #isUserGrantedAccessToContest(TCSubject, long)}.</li>
 *   <li>Provide implementation for {@link #isUserGrantedAccessToProject(TCSubject, long)}
 *   and {@link #isUserGrantedAccessToContest(TCSubject, long)}.</li>
 * </ol>
 * </p>
 * 
 * @author isv, TCSASSEMBER
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
     * @param tcSubject a <code>TCSubject</code> providing the user subject.
     * @param projectId a <code>long</code> providing the project ID.
     * @return <code>true</code> if user is granted access to project; <code>false</code> otherwise.
     * @throws Exception if any error occurs
     */
    public static boolean isUserGrantedAccessToProject(TCSubject tcSubject, long projectId) throws Exception {
        if (DirectUtils.isTcStaff(tcSubject)) {
            return true;
        }
        
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("has_cockpit_project_permissions");
        request.setProperty("tcdirectid", String.valueOf(projectId));
        request.setProperty("uid", String.valueOf(tcSubject.getUserId()));
        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("has_cockpit_project_permissions");
        return resultContainer.size() > 0;
    }

    /**
     * <p>Checks if specified user is granted access permission to specified contest.</p>
     *
     * @param tcSubject a <code>TCSubject</code> providing the user subject.
     * @param contestId a <code>long</code> providing the contest ID.
     * @return <code>true</code> if user is granted access to Studio contest; <code>false</code> otherwise.
     * @throws Exception if any error occurs
     */
    public static boolean isUserGrantedAccessToContest(TCSubject tcSubject, long contestId) throws Exception {
        if (DirectUtils.isTcStaff(tcSubject)) {
            return true;
        }
        
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("has_cockpit_permissions");
        request.setProperty("pj", String.valueOf(contestId));
        request.setProperty("uid", String.valueOf(tcSubject.getUserId()));
        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("has_cockpit_permissions");
        return resultContainer.size() > 0;
    }
}
