/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;


/**
 * <p>
 * This is a base class only to hold the project service facade shared among several actions.
 * </p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread safety
 * is not required (instead in Struts 1 the thread safety is required because the action instances are reused). This
 * class is mutable and stateful: it's not thread safe.
 * </p>
 *
 * @author fabrizyo, FireIce
 * @version 1.0
 */
public abstract class ProjectAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = -8666921545810824646L;

    /**
     * <p>
     * Creates a <code>ProjectAction</code> instance.
     * </p>
     */
    protected ProjectAction() {

    }
}
