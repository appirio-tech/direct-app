/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) ConfigManagerException.java
 *
 * 3.0  05/07/2003
 */
package com.topcoder.util.config;

import java.io.IOException;

/**
 * Generic Exception thrown by the <code>Config Manager</code>. Thrown when
 * errors specific to the <code>Config Manager</code> occurs.
 *
 * @author  debedeb
 * @author  ilya
 * @author  isv
 * @author  WishingBone
 * @version 2.1  05/07/2003
 */
public class ConfigManagerException extends IOException {

    /**
     * Constructs a <code>ConfigManagerException</code> with <code>null</code>
     * as its error detail message.
     */
    public ConfigManagerException() {
        super();
    }

    /**
     * Constructs a <code>ConfigManagerException</code> with the specified
     * detail message. The error message string <code>detail</code> can later be
     * retrieved by the <code>getMessage()</code> method.
     *
     * @param detail the detail message
     */
    public ConfigManagerException(String detail) {
        super(detail);
    }

}
