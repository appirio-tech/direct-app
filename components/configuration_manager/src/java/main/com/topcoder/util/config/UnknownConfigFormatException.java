/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) UnknownConfigFormatException.java
 *
 * 2.1  05/07/2003
 */
package com.topcoder.util.config;

/**
 * Thrown when specified format of configuration properties is not in list of
 * valid values, that are : <code>ConfigManager.CONFIG_XML_FORMAT,
 * ConfigManager.CONFIG_MULTIPLE_XML_FORMAT,
 * ConfigManager.CONFIG_PROPERTIES_FORMAT, ConfigManager.CONFIG_PLUGGABLE_FORMAT
 * </code>
 *
 * @author  debedeb
 * @author  ilya
 * @author  isv
 * @author  WishingBone
 * @version 2.1  05/07/2003
 *
 * @see ConfigManager#CONFIG_PROPERTIES_FORMAT
 * @see ConfigManager#CONFIG_XML_FORMAT
 * @see ConfigManager#CONFIG_MULTIPLE_XML_FORMAT
 * @see ConfigManager#CONFIG_PLUGGABLE_FORMAT
 */
public class UnknownConfigFormatException extends ConfigManagerException {

    /**
     * Constructs an <code>UnknownConfigFormaException</code> with <code>null
     * </code> as its error detail message.
     */
    public UnknownConfigFormatException() {
        super();
    }

    /**
     * Constructs an <code>UnknownConfigFormatException</code> with the
     * specified detail message. The error message string <code>detail</code>
     * can later be retrieved by the <code>getMessage()</code> method.
     *
     * @param detail the detail message
     */
    public UnknownConfigFormatException(String detail) {
        super(detail);
    }

}
