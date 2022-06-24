/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) NamespaceAlreadyExistsException.java
 *
 * 2.1  05/07/2003
 */
package com.topcoder.util.config;

/**
 * Thrown when there is an attempt to add a <code>Namespace</code> which already
 * exists in <code>ConfigManager</code>.
 *
 * @author  debedeb
 * @author  ilya
 * @author  isv
 * @author  WishingBone
 * @version 2.1  05/07/2003
 */
public class NamespaceAlreadyExistsException extends ConfigManagerException {

    /**
     * Constructs a <code>NamespaceAlreadyExistsException</code> with <code>null
     * </code> as its error detail message.
     */
    public NamespaceAlreadyExistsException() {
        super();
    }

    /**
     * Constructs a <code>NamespaceAlreadyExistsException</code> with the
     * specified detail message. The error message string <code>detail</code>
     * can later be retrieved by the <code>getMessage()</code> method.
     *
     * @param detail the detail message
     */
    public NamespaceAlreadyExistsException(String detail) {
        super(detail);
    }

}
