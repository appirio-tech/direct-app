/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

/**
 * This class represents the VM instance status. The instance can be in PENDING, RUNNING, SHUTTING_DOWN, or TERMINATED
 * status.
 *
 * Thread-safety: Enumeration class is thread-safe.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public enum VMInstanceStatus {
    /**
     * Represents the pending status.
     */
    PENDING,
    /**
     * Represents the running status.
     */
    RUNNING,
    /**
     * Represents the shutting down status.
     */
    SHUTTING_DOWN,
    /**
     * Represents the terminated status.
     */
    TERMINATED,
	
	STOPPED,
	
	UNKNOWN
}

