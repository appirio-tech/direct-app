/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import com.topcoder.service.facade.contest.ContestServiceFacade;

/**
 * <p>
 * This is a base class only to hold the contest service facade shared among several actions.
 * </p>
 * <p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread safety
 * is not required (instead in Struts 1 the thread safety is required because the action instances are reused). This
 * class is mutable and stateful: it's not thread safe.
 * </p>
 *
 * @author fabrizyo, FireIce
 * @version 1.0
 */
public abstract class ContestAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = -329554689416913910L;

    /**
     * <p>
     * Creates a <code>ContestAction</code> instance.
     * </p>
     */
    protected ContestAction() {
    }
}
