/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) ConfigProperties.java
 *
 * 2.1  05/07/2003
 */
package com.topcoder.util.config;

import java.io.IOException;
import java.io.Serializable;

/**
 * This abstract class is the superclass of all classes representing the
 * different sources of configuration properties. While this class maintains
 * the properties data it's subclasses are responsible for interacting with
 * underlying persistent storage to save and load properties data.
 *
 * @auhtor  isv
 * @author  WishingBone
 * @version 2.1  05/07/2003
 */
abstract class ConfigProperties implements Serializable {

    /**
     * A "root" property that is an instance of <code>Property</code> class and
     * serves as an entry point to whole properties hierarchy tree. This "root"
     * property manages all the properties contained within namespace. All
     * properties that are in namespace are nested within this "root" property.
     *
     * Such "root" property is provided to simplify the interface provided by
     * ConfigProperties. Instead of ConfigProperties having a methods to
     * add, remove, find the properties it just provide a getRoot() method
     * that returns a "root" property that then can be used to perform any
     * operations on a namespace's properties.
     *
     * @since Configuration Manager 2.1
     */
    private Property root = null;

    /**
     * Constructs an instance of <code>ConfigProperties</code>
     */
    ConfigProperties() {
    }

    /**
     * Gets the "root" property that is an entry point to whole properties
     * hierarchy tree.
     *
     * @return a "root" property
     */
    Property getRoot() {
        return root;
    }

    /**
     * The list delimetir associated with the ConfigProperties.
     * ';' is used as the default delimiter.
     */
    private char listDelimiter = ';';

    /**
     * Gets the list delimiter.
     *
     * @return the list delimiter.
     */
    char getListDelimiter() {
        return listDelimiter;
    }

    /**
     * Saves the data(properties and their values) from properties tree into
     * persistent storage.
     *
     * @throws IOException if any exception related to underlying persistent
     *         storage occurs
     */
    protected abstract void save() throws IOException;

    /**
     * Loads the properties and their values from persistent storage.
     *
     * @throws IOException if any exception related to underlying persistent
     *         storage occurs
     */
    protected abstract void load() throws IOException;

    /**
     * Sets the "root" property.
     *
     * @param  root a new "root" property
     * @throws NullPointerException if given <code>root</code> is <code>null
     *         </code>
     */
    protected void setRoot(Property root) {
        if (root == null) {
            throw new NullPointerException("parameter root is null");
        }
        this.root = root;
    }

    /**
     * Sets the list delimiter.
     *
     * @param  listDelimiter the new list delimiter
     */
    protected void setListDelimiter(char listDelimiter) {
        this.listDelimiter = listDelimiter;
    }

    /**
     * Gets the clone copy of this object.
     *
     * @return a clone copy of this object.
     */
    public abstract Object clone();

}
