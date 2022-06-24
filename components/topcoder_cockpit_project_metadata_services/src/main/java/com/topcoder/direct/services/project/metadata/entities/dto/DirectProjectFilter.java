/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dto;

import java.io.Serializable;

/**
 * The interface for Direct Project Filter.
 * The implementation class must be implement toJSONString method.
 *
 * <p>
 *  Thread Safety: This class is immutable and thread safe.
 * </p>
 *
 * @author Standlove, CaDenza
 * @version 1.0
 */
public interface DirectProjectFilter extends Serializable {
    /**
     * Converts the entity to a JSON string that can be used for logging.
     * @return the JSON string with entity data (not null)
     */
    public String toJSONString();
}

