/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;


/**
 * <p>
 * <code>PhaseGenerationException</code> indicates that there're errors in the project phases
 * generation process, e.g. the cyclic dependency, a phase depends on an undefined phase, etc.
 * </p>
 * <p>
 * It may be thrown from <code>{@link PhaseTemplatePersistence}</code> and
 * <code>{@link PhaseTemplate}</code> implementations.
 * </p>
 *
 * @author albertwang, TCSDEVELOPER
 * @author flying2hk, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class PhaseGenerationException extends PhaseTemplateException {

    /**
     * serialVersionUID instance.
     */
    private static final long serialVersionUID = -800289348796613541L;

    /**
     * <p>
     * Create <code>PhaseGenerationException</code> instance with the error message.
     * </p>
     * @param message the error message
     */
    public PhaseGenerationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create <code>PhaseGenerationException</code> instance with error message and the cause
     * exception.
     * </p>
     * @param message the error message
     * @param cause the cause
     */
    public PhaseGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
