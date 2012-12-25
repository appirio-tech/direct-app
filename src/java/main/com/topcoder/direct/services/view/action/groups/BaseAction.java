/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.util.log.Log;

/**
 * <p>
 * BaseAction is used to hold the common fields such as logger for the sub classes to use.
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe.
 * However the struts framework will guarantee that it will be used in the thread safe model.
 * </p>
 *
 * <p>
 * Version 1.1 (TopCoder Security Groups Frontend - Create Update Group) change notes:
 * <ol>
 *   <li>Changed the base class to <code>BaseDirectStrutsAction</code> so it can be used in an
 *       ajax manner.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous) change notes:
 * <ol>
 *   <li>Moved {@link #INVITATION_DATE_FORMAT} and {@link #formatDate(Date)} from
 *   {@link GroupInvitationSearchBaseAction} class.</li>
 * </ol>
 * </p>
 *
 * @author woodjhon, hanshuai, flexme, TCSASSEMBLER
 * @version 1.2
 */
@SuppressWarnings("serial")
public abstract class BaseAction extends BaseDirectStrutsAction {    
    /**
     * The invitation date format.
     * 
     * @since 1.2
     */
    private static final DateFormat INVITATION_DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
    
    /**
     * <p>
     * The logger used to perform logging. It is not null after initialization.
     * </p>
     */
    private Log logger;

    /**
     * Purpose: auditService is used to represents the group audit service. It's required. Usage: It's injected.
     * Legal Values: Not null after set
     */
    private GroupAuditService auditService;

    /**
     * Purpose: authorizationService is used to represents the authorization service. It's required. Usage: It's
     * injected. Legal Values: Not null after set
     */
    private AuthorizationService authorizationService;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    protected BaseAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required fields are injected.
     * </p>
     *
     * @throws SecurityGroupsActionConfigurationException
     *             is thrown if any of these fields is null:<br>
     *             auditService, authorizationService.
     */
    public void checkInit() {
        ValidationUtility.checkNotNull(auditService, "auditService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(authorizationService, "authorizationService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * <p>
     * Get current user id method.
     * </p>
     *
     * @return the current user id.
     */
    protected long getCurrentUserId() {
        TCSubject subject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        return subject.getUserId();
    }

    /**
     * <p>
     * Get logger.
     * </p>
     *
     * @return the logger
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * <p>
     * Set logger.
     * </p>
     *
     * @param logger
     *            the logger to set.
     */
    public void setLogger(Log logger) {
        this.logger = logger;
    }

    /**
     * <p>
     * Get audit service.
     * </p>
     *
     * @return the audit service
     */
    public GroupAuditService getAuditService() {
        return auditService;
    }

    /**
     * <p>
     * Set audit service.
     * </p>
     *
     * @param auditService
     *            the audit service to set.
     */
    public void setAuditService(GroupAuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * <p>
     * Get authorization service.
     * </p>
     *
     * @return the authorization service
     */
    public AuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    /**
     * <p>
     * Set authorization service.
     * </p>
     *
     * @param authorizationService
     *            the authorization service to set.
     */
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
    
    /**
     * Formats the date instance.
     * 
     * @param date the date instance
     * @return the formatted date string
     * @since 1.1
     */
    protected String formatDate(Date date) {
        return date == null ? "N/A" : INVITATION_DATE_FORMAT.format(date);
    }
}
