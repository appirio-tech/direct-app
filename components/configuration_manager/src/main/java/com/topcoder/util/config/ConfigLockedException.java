/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) ConfigLockedException.java
 *
 * 3.0  05/07/2003
 */
package com.topcoder.util.config;

/**
 * Exception to be thrown when there is an attempt to lock a namespace but it is
 * already locked by another user.
 *
 * @author  ilya
 * @author  isv
 * @author  WishingBone
 * @version 2.1  05/07/2003
 */
public class ConfigLockedException extends ConfigManagerException  {

    /**
     * Constructs a <code>ConfigLockedException</code> with <code>null</code>
     * as its error detail message.
     */
    public ConfigLockedException() {
        super();
    }

    /**
     * Constructs a <code>ConfigLockedException</code> with the specified
     * detail message. The error message string <code>detail</code> can later be
     * retrieved by the <code>getMessage()</code> method.
     *
     * @param detail the detail message
     */
    public ConfigLockedException(String detail) {
        super(detail);
    }

}
