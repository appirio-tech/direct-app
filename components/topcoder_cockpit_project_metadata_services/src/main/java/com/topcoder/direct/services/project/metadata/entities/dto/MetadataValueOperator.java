/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dto;

/**
 * <p>
 *  The enum class to define supported operator for metadata value.
 *  Operator which are support: EQUALS and LIKE.
 * </p>
 *
 * <p>
 *  Thread Safety: This class is immutable and thread safe.
 * </p>
 *
 * @author Standlove, CaDenza
 * @version 1.0
 */
public enum MetadataValueOperator {
    /**
     * The EQUALS operator.
     */
    EQUALS,

    /**
     * The LIKE operator.
     */
    LIKE;
}

