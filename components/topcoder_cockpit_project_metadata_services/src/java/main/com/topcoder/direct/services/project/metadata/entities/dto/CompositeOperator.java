/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dto;

/**
 * <p>
 * The enum class for supported composite operator.
 * Supported operation are AND also OR.
 * </p>
 *
 * <p>
 *  Thread Safety: This class is immutable and thread safe.
 * </p>
 *
 * @author Standlove, CaDenza
 * @version 1.0
 */
public enum CompositeOperator {
    /**
     * The AND operator.
     */
    AND,

    /**
     * The OR operator.
     */
    OR;
}

