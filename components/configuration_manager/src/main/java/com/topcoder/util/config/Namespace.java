/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) Namespace.java
 *
 * 2.1  05/07/2003
 */
package com.topcoder.util.config;

import java.net.URL;
import java.util.Iterator;
import java.io.Serializable;

/**
 * A namespace existing whithin <code>Configuration Manager</code>. Is described
 * with name uniquely identifying it among other namespaces, format of source
 * containing the properties of namespace, URL pointing to source containing the
 * properties of namespace, level of exceptions and <code>ConfigProperties</code>
 * associated with it.
 *
 * Before attempting to make any updates to namespace a namespace should be
 * locked first with <code>lock()</code> method. After namespace has been
 * locked and all updates have been made a namespace should be unlocked with
 * <code>unlock()</code> method. <code>canLock()</code> can be used to check
 * whether this namespace can be locked by given user or not.
 *
 * @author  ilya
 * @author  isv
 * @author  WishingBone
 * @version 2.1  05/07/2003
 */
class Namespace implements Serializable {

    /**
     * A name uniquely identifying this namespace among other namespaces
     * existing within <code>Configuration Manager</code>
     */
    private String name = null;

    /**
     * A format of this namespace (any of <code>ConfigManager.CONFIG_XML_FORMAT,
     * ConfigManager.CONFIG_PROPERTIES_FORMAT,
     * ConfigManager.CONFIG_MULTIPLE_XML_FORMAT,
     * ConfigManager.CONFIG_PLUGGABLE_FORMAT</code>).
     *
     * @see ConfigManager#CONFIG_XML_FORMAT
     * @see ConfigManager#CONFIG_PROPERTIES_FORMAT
     * @see ConfigManager#CONFIG_MULTIPLE_XML_FORMAT
     * @see ConfigManager#CONFIG_PLUGGABLE_FORMAT
     */
    private String format = null;

    /**
     * An <code>URL</code> pointing to source of properties containing within
     * this namespace.
     */
    private URL file = null;

    /**
     * A level of exception (either <code>ConfigManager.EXCEPTION_ALL</code> or
     * ConfigManager.EXCEPTION_MAJOR</code>).
     *
     * @see ConfigManager#EXCEPTION_ALL
     * @see ConfigManager#EXCEPTION_MAJOR
     */
    private int exceptionLevel = 0;

    /**
     * A properties existing within this namespace.
     */
    private ConfigProperties properties = null;

    /**
     * A "semaphore" signaling that namespace is already locked by some user for
     * update.
     */
    private boolean lock = false;

    /**
     * A user that locked this namespace for update.
     */
    private String user = null;

    /**
     * Constructs a new <code>Namespace</code> with given name, data source,
     * format, exception level and properties.
     *
     * @param namespace      name for this namespace
     * @param url            data source for this namespace
     * @param format         format for the data source
     * @param exceptionLevel level of detail to pay attention to on Exceptions
     * @param prop           set of properties for this namespace
     */
    Namespace(String namespace, URL url, String format, int exceptionLevel, ConfigProperties prop) {
        name = namespace;
        file = url;
        this.format = format;
        this.exceptionLevel = exceptionLevel;
        properties = prop;
    }


	 /**
     * Constructs 
	 */
    Namespace() {

    }

	/**
	 *
	 * @param name 
	 * set name
	 */
	public  void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param file 
	 * set name
	 */
	public void setFile(URL file)
	{
		this.file = file;
	}


	/**
	 * @param format 
	 * set format
	 */
	public void setFormat(String format)
	{
		this.format = format;
	}


	public void setExceptionLevel(int exceptionLevel)
	{
		this.exceptionLevel = exceptionLevel;
	}
    /**
     * Gets the name for this <code>Namespace</code> uniquely identifying this
     * namespace among other namespaces existing within Configuration Manager.
     *
     * @return the name for this <code>Namespace</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the format for the data source associated with this <code>
     * Namespace</code>.
     *
     * @return the format for the data source (any of <code>
     *         ConfigManager.CONFIG_XML_FORMAT,
     *         ConfigManager.CONFIG_PROPERTIES_FORMAT,
     *         ConfigManager.CONFIG_MULTIPLE_XML_FORMAT,
     *         ConfigManager.CONFIG_PLUGGABLE_FORMAT</code>)
     * @see ConfigManager#CONFIG_XML_FORMAT
     * @see ConfigManager#CONFIG_PROPERTIES_FORMAT
     * @see ConfigManager#CONFIG_MULTIPLE_XML_FORMAT
     * @see ConfigManager#CONFIG_PLUGGABLE_FORMAT
     */
    public String getFormat() {
        return format;
    }

    /**
     * Gets the URL of data source for this <code>Namespace</code>. If the
     * format of this namespace is <code>ConfigManager.CONFIG_PLUGGABLE_FORMAT
     * </code> then returned URL points to .config file containing the
     * description of pluggable data source, otherwise the URL points to .xml or
     * .properties file with namespace properties data.
     *
     * @return the <code>URL</code> pointing to data source containing the
     *         properties data.
     */
    public URL getFile() {
        return file;
    }

    /**
     * Gets the exception level associated with this <code>Namespace</code>.
     *
     * @return exception level (either <code>ConfigManager.EXCEPTION_ALL</code>
     *         or ConfigManager.EXCEPTION_MAJOR</code>)
     * @see ConfigManager#EXCEPTION_ALL
     * @see ConfigManager#EXCEPTION_MAJOR
     */
    public int getExceptionLevel() {
        return exceptionLevel;
    }

    /**
     * Gets the <code>ConfigProperties</code> associated with this namespace.
     *
     * @return properties associated with this namespace.
     */
    public ConfigProperties getProperties() {
        return properties;
    }

    /**
     * Sets the properties for this namespace.
     *
     * @param  properties properties for this namespace.
     * @throws NullPointerException if given <code>properties</code> is <code>
     *         null</code>
     */
    public void setProperties(ConfigProperties properties) {
        if (properties == null) {
            throw new NullPointerException("parameter properties is null");
        }
        this.properties = properties;
    }

    /**
     * Locks this namespace. Once the namespace is locked it can be updated then.
     * Before attempting to lock this namespace checks whether the source of
     * properties data associated with this namespace is not already locked.
     * This could happen on a multi-namespace XML file or if the same source of
     * properties data is loaded twice into two different namespaces.
     *
     * @param  newUser name of user responsible for locking.
     * @throws ConfigLockedException if this <code>Namespace</code> is already
     *         locked by another user
     * @throws UnknownNamespaceException if namespace not loaded in
     *         Configuration Manager
     */
    void lock(String newUser) throws ConfigLockedException, UnknownNamespaceException {

        // check whether the user can lock this namespace
        if (lock && !user.equals(newUser)) {
            throw new ConfigLockedException(newUser + " can not lock namespace " + name);
        }

        // check whether another namespace has locked the same source
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator itr = cm.getAllNamespaces(); itr.hasNext();) {
            String namespace = (String) itr.next();
            String filename = cm.getConfigFilename(namespace);
            if (file.getFile().equals(filename) && !cm.canLock(namespace, newUser)) {
                throw new ConfigLockedException("the source of " + name + " is already locked");
            }
        }

        lock = true;
        user = newUser;
    }

    /**
     * Removes lock from this namespace.
     */
    void releaseLock() {
        lock = false;
        user = null;
    }

    /**
     * Checks if a given user can lock this namespace. The namespace can be
     * locked if it hasn't been locked yet, or if it is already locked by given
     * user. The namespace can't be locked by given user if it is already locked
     * by another user.
     *
     * @param  user a name of user attempting to lock namespace
     * @return <code>true</code> if newUser can lock this namespace, <code>false
     *         </code> otherwise
     */
    public boolean canLock(String user) {
        return !lock || this.user.equals(user);
    }

    /**
     * Sets the <code>XML Document</code> for this namespace's ConfigProperties
     * same as argument namespace.
     * This method is applicable only if both this and given namespaces have
     * format set to <code>ConfigManager.CONFIG_MULTIPLE_XML_FORMAT</code>
     *
     * @param ns the Namespace to get the document from.
     */
    void setDocument(Namespace ns) {
        if (ConfigManager.CONFIG_MULTIPLE_XML_FORMAT.equals(format) &&
            ConfigManager.CONFIG_MULTIPLE_XML_FORMAT.equals(ns.format)) {
            ((XMLConfigProperties) properties).setDocument(((XMLConfigProperties) ns.properties).getDocument());
        }
    }

}
