/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>An enumeration over the overall status of the registration for single contest.</p>
 *
 * @author isv
 * @version 1.0 (Direct Contest Dashboard assembly)
 */
public enum RegistrationStatus {

    /**
     * <p>A <code>RegistrationStatus</code> item corresponding to case when contest has enough registrants with
     * sufficient reliability.</p>
     */
    HEALTHY,

    /**
     * <p>A <code>RegistrationStatus</code> item corresponding to case when contest does not have enough registrants
     * with sufficient reliability and Registration phase is active.</p>
     */
    REGISTRATION_LESS_IDEAL_ACTIVE,

    /**
     * <p>A <code>RegistrationStatus</code> item corresponding to case when contest does not have enough registrants
     * with sufficient reliability and Registration phase is closed.</p>
     */
    REGISTRATION_LESS_IDEAL_CLOSED,

    /**
     * <p>A <code>RegistrationStatus</code> item corresponding to case when contest has no or minimum registrants
     * with sufficient reliability and Registration phase is closed.</p>
     */
    REGISTRATION_POOR

}
