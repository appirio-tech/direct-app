/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) ConfigManagerInterface.java
 *
 * 2.1  05/07/2003
 */
package com.topcoder.util.config;

import java.util.Enumeration;
import java.io.Serializable;

/**
 * This is an interface that all classes which deal with the ConfigManager
 * (including ConfigManager itself) should implement.
 *
 * @author  ilya
 * @author  isv
 * @author  WishingBone
 * @version 2.1  05/07/2003
 */
public interface ConfigManagerInterface extends Serializable{

    /**
     * Gets the namespace for this component.
     *
     * @return namespace for this component.
     */
    String getNamespace();

    /**
     * Gets all known property keys for this Component.
     *
     * @return all known property keys for this Component.
     */
    Enumeration getConfigPropNames();

}
