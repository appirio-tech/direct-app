/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.services.GroupInvitationService;

/**
 * <p>
 * This is a base class for Struts actions, which must be aware of group
 * invitation service. This class inherits from BaseAction and additionally
 * holds a configurable instance of GroupInvitationService. It performs
 * validation of the mentioned configuration parameter and exposes its value to
 * subclasses via protected getter.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is technically mutable and not thread-safe,
 * however it's expected to be used as Spring bean and thus it will be immutable
 * after initialization (as well as base class), so it's thread-safe.
 * </p>
 * <p>
 * <b>Sample Usage:</b>
 *
 * Spring configuration:
 *
 * <pre>
 * &lt;bean id="GroupInvitationAwareBaseAction" abstract="true"
 *   class="com.topcoder.security.groups.actions.GroupInvitationAwareBaseAction"&gt;
 *   &lt;property name="logger" ref="logger"/&gt;
 *   &lt;property name="groupInvitationService" ref="groupInvitationService"/&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * Struts configuration:
 *
 * <pre>
 * &lt;action name="groupInvitationAwareBaseAction" class="GroupInvitationAwareBaseAction"
 *    method="execute"&gt;
 *    &lt;result name="success"&gt;success.jsp&lt;/result&gt;
 *    &lt;result name="input"&gt;group_invitation_aware.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - Invitations Approvals) change notes:
 * <ol>
 *   <li>Updated to use the same package name as other group actions.</li>
 * </ol>
 * </p>
 *
 * @author gevak, TCSDEVELOPER, TCSASSEMBLER
 * @version 1.1
 */
@SuppressWarnings("serial")
public abstract class GroupInvitationAwareBaseAction extends BaseAction {
    /**
     * GroupInvitationService exposed to subclasses via protected getter.
     * Expected to be used by subclasses to manage group invitations. It is
     * injected via the setter with no validation, thus can be any value.
     * However, <code>checkInit</code> method will ensure that it's not null. Mutable
     * via public setter. Has protected getter.
     */
    private GroupInvitationService groupInvitationService;

    /**
     * Empty default constructor.
     */
    protected GroupInvitationAwareBaseAction() {
    }

    /**
     * Checks whether this class was configured by Spring properly.
     *
     * @throws SecurityGroupsActionConfigurationException if the class was not
     *             configured properly (e.g. when required property was not
     *             injected or property value is invalid).
     *
     */
    @PostConstruct
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(groupInvitationService,
                "groupInvitationService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * Gets GroupInvitationService expected to be used by subclasses to manage
     * group invitations.
     *
     * @return GroupInvitationService expected to be used by subclasses to
     *         manage group invitations.
     *
     */
    protected GroupInvitationService getGroupInvitationService() {
        return groupInvitationService;
    }

    /**
     * Sets GroupInvitationService expected to be used by subclasses to manage
     * group invitations.
     *
     * @param groupInvitationService GroupInvitationService expected to be used
     *            by subclasses to manage group invitations.
     *
     */
    public void setGroupInvitationService(
            GroupInvitationService groupInvitationService) {
        this.groupInvitationService = groupInvitationService;
    }
}
