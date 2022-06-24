/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>An enumeration over the overall statuses of the dependencies for single contest.</p>
 *
 * @author isv
 * @version 1.0 (Direct Contest Dashboard assembly)
 */
public enum DependenciesStatus {

    /**
     * <p>A <code>DependenciesStatus</code> item corresponding to case when contest has no dependencies at all.</p>
     */
    NO_DEPENDENCIES,

    /**
     * <p>A <code>DependenciesStatus</code> item corresponding to case when contest has dependencies and all of them are
     * satisfied.</p>
     */
    DEPENDENCIES_SATISFIED,

    /**
     * <p>A <code>DependenciesStatus</code> item corresponding to case when contest has dependencies and at least one of
     * them are not satisfied.</p>
     */
    DEPENDENCIES_NON_SATISFIED
}
