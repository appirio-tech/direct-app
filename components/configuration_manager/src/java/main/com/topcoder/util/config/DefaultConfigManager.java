/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) DefaultConfigManager.java
 *
 * 2.1  05/07/2003
 */
package com.topcoder.util.config;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * This is a default implementation of ConfigManager's logic that is renamed
 * ConfigManager class from previous version of TC Configuration Manager.
 *
 * @author  ilya
 * @author  debedeb
 * @author  isv
 * @author  WishingBone
 * @version 2.1 05/07/2003
 */
class DefaultConfigManager extends ConfigManager {

    /**
     * A set of namespaces with properties existing within ConfigManager.
     */
    private Map namespaces = new HashMap();

    /**
     * A set of temporary properties used to modify the namespaces existing
     * within ConfigManager.
     */
    private Map tempProperties = new HashMap();

    /**
     * Part of ConfigManagerInterface implementation.  Returns the namespace
     * owned by the <code>Configuration Manager</code>.
     *
     * @return a <code>String</code> representing the namespace owned by <code>
     *         ConfigurationManager</code>
     */
    public String getNamespace() {
        return CONFIG_MANAGER_NAMESPACE;
    }

    /**
     * Part of ConfigManagerInterface implementation.  Returns all known
     * properties for the config manager.
     *
     * @return all known properties for the config manager.
     */
    public Enumeration getConfigPropNames() {
        // nothing
        return new Vector().elements();
    }

    /**
     * Auxialiary function used by all add()'s.  Adds properties into
     * namespace.
     *
     * @param  namespace namespace to add properties in URL to.
     * @param  url       location where data is to be read from.
     * @param  format    a format constant from this file describing type of URL.
     * @param  exceptionLevel a level of exceptions
     * @throws NamespaceAlreadyExistsException when namespace already exists.
     * @throws UnknownConfigFormatException if format is not a known constant
     * @throws ConfigManagerException if any error using the URL or other
     *         problems in the config manager
     */
    private void addURL(String namespace, URL url, String format, int exceptionLevel)
            throws ConfigManagerException {
        synchronized (this) {
            if (namespaces.containsKey(namespace)) {
                throw new NamespaceAlreadyExistsException(namespace + "already exists");
            }

            // construct config properties
            ConfigProperties cp;
            try {
                if (format.equals(CONFIG_XML_FORMAT)) {
                    cp = new XMLConfigProperties(url);
                } else if (format.equals(CONFIG_PROPERTIES_FORMAT)) {
                    cp = new PropConfigProperties(url);
                } else if (format.equals(CONFIG_MULTIPLE_XML_FORMAT)) {
                    cp = new XMLConfigProperties(url, namespace);
                } else if (format.equals(CONFIG_PLUGGABLE_FORMAT)) {
                    cp = new PluggableConfigProperties(url);
                } else {
                    throw new UnknownConfigFormatException("parameter format is illegal");
                }
            } catch (IOException ioe) {
                throw new ConfigManagerException(ioe.getMessage());
            }

            // add the namespace at last!
            namespaces.put(namespace, new Namespace(namespace, url, format, exceptionLevel, cp));
            tempProperties.remove(namespace);
        }
    }

    /**
     * Adds the specified namespace to the Configuration Manager.
     *
     * @param  namespace describes both the namespace to which the configuration
     *         properties are being added.
     * @param  filename the name of the file containing properties
     * @param  format describes the format of the configuration properties file,
     *         thus identifying the <code>ConfigParser</code> to be used for
     *         reading it.
     * @param  exceptionLevel identified level of exception reporting
     * @throws NamespaceAlreadyExistsException if the specified namespace has
     *         already been added to the Configuration Manager
     * @throws UnknownConfigFormatException if the specified format is not known
     * @throws ConfigManagerException or a subclass of it such as <code>
     *         ConfigParserException</code>, when other problems are encountered;
     *         namespace is not created
     * @throws IllegalArgumentException if given exceptionLevel or format is not
     *         from set of predefined constants or any of given arguments is
     *         empty
     * @throws NullPointerException if any of given arguments is null
     */
    public void add(String namespace, String filename, String format, int exceptionLevel)
            throws ConfigManagerException {
        if (namespace == null) {
            throw new NullPointerException("parameter filename is null");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("parameter filename is empty");
        }
        if (filename == null) {
            throw new NullPointerException("parameter filename is null");
        }
        if (filename.trim().length() == 0) {
            throw new IllegalArgumentException("parameter filename is empty");
        }
        if (format == null) {
            throw new NullPointerException("parameter format is null");
        }
        if (!format.equals(CONFIG_XML_FORMAT) && !format.equals(CONFIG_PROPERTIES_FORMAT) &&
            !format.equals(CONFIG_MULTIPLE_XML_FORMAT) && !format.equals(CONFIG_PLUGGABLE_FORMAT)) {
            throw new UnknownConfigFormatException("parameter format is illegal");
        }
        if (exceptionLevel != EXCEPTIONS_ALL && exceptionLevel != EXCEPTIONS_MAJOR) {
            throw new IllegalArgumentException("parameter exceptionLevel is illegal");
        }

        addURL(namespace, ConfigManager.getURL(filename), format, exceptionLevel);
    }

    /**
     * An extension to the add method to make it possible to find
     * com/topcoder/Bla.xml given com.topcoder.Bla and xml as arguments
     *
     * @param  namespace namespace to add properties to.
     * @param  format    format constant for the source.  See constants.
     * @param  exceptionLevel level of Exceptions to display.  See constants.
     * @throws ConfigManagerException when namespace is badly formed
     * @throws IllegalArgumentException if given exceptionLevel or format is not
     *         from set of predefined constants or any of given arguments is
     *         empty
     * @throws NullPointerException if any of given arguments is null
     */
    public void add(String namespace, String format, int exceptionLevel) throws ConfigManagerException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("parameter namespace is empty");
        }
        if (format == null) {
            throw new NullPointerException("parameter format is null");
        }
        if (!format.equals(CONFIG_XML_FORMAT) && !format.equals(CONFIG_PROPERTIES_FORMAT) &&
            !format.equals(CONFIG_MULTIPLE_XML_FORMAT) && !format.equals(CONFIG_PLUGGABLE_FORMAT)) {
            throw new UnknownConfigFormatException("parameter format is illegal");
        }
        if (exceptionLevel != EXCEPTIONS_ALL && exceptionLevel != EXCEPTIONS_MAJOR) {
            throw new IllegalArgumentException("parameter exceptionLevel is illegal");
        }

        // construct filename
        String filename = namespace.replace('.', '/');
        if (format.equals(CONFIG_MULTIPLE_XML_FORMAT)) {
            filename += CONFIG_XML_FORMAT;
        } else {
            filename += format;
        }
        add(namespace, filename, format, exceptionLevel);
    }

    /**
     * An extension to the add method to add XML files
     * that already list their namespaces
     * (i.e. those containing multiple namespaces)
     *
     * @param  filename name of the file to read from.
     * @param  exceptionLevel level of Exceptions to display.  See constants.
     * @throws ConfigManagerException when filename is invalid.
     * @throws IllegalArgumentException if given exceptionLevel is not from
     *         set of predefined constants or given filename is empty
     * @throws NullPointerException if given filename is null
     */
    public void add(String filename, int exceptionLevel) throws ConfigManagerException {
        if (filename == null) {
            throw new NullPointerException("parameter filename is null");
        }
        if (filename.trim().length() == 0) {
            throw new IllegalArgumentException("parameter filename is empty");
        }
        if (exceptionLevel != EXCEPTIONS_ALL && exceptionLevel != EXCEPTIONS_MAJOR) {
            throw new IllegalArgumentException("parameter exceptionLevel is illegal");
        }

        // try to locate absolute url
        URL url = ConfigManager.getURL(filename);

        // for each namespace, create url
        synchronized (this) {
            for (Enumeration enu = XMLConfigProperties.getNamespaces(url); enu.hasMoreElements();) {
                String namespace = (String) enu.nextElement();
                addURL(namespace, url, CONFIG_MULTIPLE_XML_FORMAT, exceptionLevel);
            }
        }
    }

    /**
     * An extension to the add method to add XML files that already list their namespaces
     * (i.e. those containing multiple namespaces).
     *
     * @param  url location where data is to be read from.
     * @param  exceptionLevel level of Exceptions to display.  See constants.
     * @throws ConfigManagerException when url is invalid or error occur while parsing file.
     * @throws IllegalArgumentException if given exceptionLevel is not from
     *         set of predefined constants or given filename is empty
     * @throws NullPointerException if given URL is <code>null</code>.
     */
    public void add(URL url, int exceptionLevel) throws ConfigManagerException {
        if (url == null) {
            throw new NullPointerException("parameter url is null");
        }
        if ((exceptionLevel != EXCEPTIONS_ALL) && (exceptionLevel != EXCEPTIONS_MAJOR)) {
            throw new IllegalArgumentException("parameter exceptionLevel is illegal");
        }

        addURLs(url, exceptionLevel);
    }

    /**
     * Loads all the namespace from the config file.
     *
     * @param url location where data is to be read from.
     * @param exceptionLevel level of Exceptions to display.
     * @throws ConfigManagerException if url is invalid or error occur while parsing file.
     */
    private void addURLs(URL url, int exceptionLevel) throws ConfigManagerException {
        // for each namespace name, add it to CM
        synchronized (this) {
            for (Enumeration enu = XMLConfigProperties.getNamespaces(url); enu.hasMoreElements();) {
                String namespace = (String) enu.nextElement();
                addURL(namespace, url, CONFIG_MULTIPLE_XML_FORMAT, exceptionLevel);
            }
        }
    }

    /**
     * Adds properties to namespace.  Default to exceptionLevel=EXCEPTIONS_ALL.
     *
     * @param namespace namespace to add to.
     * @param url       location to read data from.
     * @param format    format of url.  See constants.
     * @throws ConfigManagerException when an error occurs adding url.
     */
    private void addURL(String namespace, URL url, String format) throws ConfigManagerException {
        addURL(namespace, url, format, EXCEPTIONS_ALL);
    }

    /**
     * Adds properties to namespace.  Defaults to exceptionLevel=EXCEPTONS_ALL
     *
     * @param namespace namespace to add to.
     * @param filename  filename with properties in it.
     * @param format    format of file.  See constants.
     * @throws ConfigManagerException when an error occurs using filename.
     * @throws IllegalArgumentException if given format is not
     *         from set of predefined constants or any of given arguments is
     *         empty
     * @throws NullPointerException if any of given arguments is null
     */
    public void add(String namespace, String filename, String format) throws ConfigManagerException {
        add(namespace, filename, format, EXCEPTIONS_ALL);
    }

    /**
     * Adds properties to namespace.  Defaults to exceptionLevel=EXCEPTONS_ALL
     *
     * @param  namespace namespace to add to.
     * @param  format    format of file.  See constants.
     * @throws ConfigManagerException when valid data for namespace can't be
     *         found.
     * @throws IllegalArgumentException if given ormat is not
     *         from set of predefined constants or any of given arguments is
     *         empty
     * @throws NullPointerException if any of given arguments is null
     */
    public void add(String namespace, String format) throws ConfigManagerException {
        add(namespace, format, EXCEPTIONS_ALL);
    }

    /**
     * Adds properties to namespace associated with filename.  Defaults to
     * exceptionLevel=EXCEPTONS_ALL
     *
     * @param  filename  filename with properties in it.
     * @throws ConfigManagerException when filename can't be used.
     * @throws IllegalArgumentException if given filename is empty
     * @throws NullPointerException if given filename is null
     */
    public void add(String filename) throws ConfigManagerException {
        add(filename, EXCEPTIONS_ALL);
    }

    /**
     * Adds properties to namespace associated with url.  Defaults to
     * exceptionLevel=EXCEPTIONS_ALL
     *
     * @param  url location where data is to be read from.
     * @throws ConfigManagerException if url is invalid or error occur while parsing file.
     * @throws IllegalArgumentException if given filename is empty
     * @throws NullPointerException if given filename is null
     */
    public void add(URL url) throws ConfigManagerException {
        add(url, EXCEPTIONS_ALL);
    }

    /**
     * Reloads configuration information for all added namespaces. Namespaces
     * for which the refresh operation fails will become invalid.
     *
     * @throws ConfigManagerException or a subclasses of it if any errors
     *         happened during refresh. Namespaces for which refresh succeeded
     *         are not invalidated.
     */
    public void refreshAll() throws ConfigManagerException {
        synchronized (this) {
            String message = "";
            // get a copy of the key set
            for (Iterator itr = new HashSet(namespaces.keySet()).iterator(); itr.hasNext();) {
                String namespace = (String) itr.next();
                try {
                    ((Namespace) namespaces.get(namespace)).getProperties().load();
                } catch (Exception exception) {
                    // remove namespace and accumulate error messages
                    namespaces.remove(namespace);
                    tempProperties.remove(namespace);
                    message += exception.getMessage();
                }
            }
            if (message.length() > 0) {
                throw new ConfigManagerException("refresh failed for some namespaces : " + message);
            }
        }
    }

    /**
     * Reloads configuration information for the specified namespace. If the
     * refresh operation fails the namespace will become invalid.
     *
     * @param  namespace namespace to refresh.
     * @throws UnknownNamespaceException if namespace has not yet been added to
     *         the CM.
     * @throws ConfigManagerException or a subclass of it on other errors
     * @throws NullPointerException if any of given arguments is null
     */
    public void refresh(String namespace) throws ConfigManagerException, UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        synchronized (this) {
            if (!namespaces.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            try {
                ((Namespace) namespaces.get(namespace)).getProperties().load();
            } catch (IOException ioe) {
                // remove namespace
                namespaces.remove(namespace);
                tempProperties.remove(namespace);
                throw new ConfigManagerException(ioe.getMessage());
            }
        }
    }

    /**
     * Gets the property specified from the specified namespace.
     *
     * @param  namespace namespace to retrieve from.
     * @param  key       properties to retrieve.
     * @return the value of the given property, or <code>null</code> if the
     *         property does not exist in the corresponding namespace.
     * @throws UnknownNamespaceException if the namespace has not been added to
     *         the Configuration Manager
     * @see    #getString
     * @throws NullPointerException if any of given arguments is null
     */
    public Object getProperty(String namespace, String key) throws UnknownNamespaceException {
        return getString(namespace, key);
    }

    /**
     * Get the property specified from the specified namespace as <code>Property
     * </code> object.
     *
     * @return The value of the given property, or <code>null</code> if the
     *         property does not exist in the corresponding namespace.
     * @param  namespace namespace to retrieve from.
     * @param  key       properties to retrieve.
     * @throws UnknownNamespaceException if the namespace
     *         has not been added to the CM.
     * @since  Configuration Manager 2.1
     * @see    #getString
     * @throws NullPointerException if any of given arguments is null
     */
    public Property getPropertyObject(String namespace, String key) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        if (key == null) {
            throw new NullPointerException("parameter key is null");
        }
        synchronized (this) {
            if (!namespaces.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            return ((Namespace) namespaces.get(namespace)).getProperties().getRoot().getProperty(key);
        }
    }

    /**
     * Get the property specified from the specified namespace.
     * Should be used when single value is expected for the given property key.
     *
     * @param  namespace namespace to retrieve from
     * @param  key a property to retrieve.
     * @return a <code>String</code> value of the property specified by
     *         namespace and key or null if property does not exist
     * @throws UnknownNamespaceException if the namespace
     *         has not been added to the CM.
     * @see    #getProperty
     * @see    #getStringArray
     * @throws NullPointerException if any of given arguments is null
     */
    public String getString(String namespace, String key) throws UnknownNamespaceException {
        synchronized (this) {
            Property property = getPropertyObject(namespace, key);
            if (property == null || property.getValue() == null) {
                return null;
            } else {
                // construct a delimeted string
                char listDelimiter = ((Namespace) namespaces.get(namespace)).getProperties().getListDelimiter();
                String[] values = property.getValues();
                return mergeEscaped(values, listDelimiter);
            }
        }
    }

    /**
     * Gets the property specified from the specified namespace.
     * Should be used when multiple values are expected for the given
     * property key.  If a single value exists, returns a one-member array.
     *
     * @param  namespace namespace to retrieve from
     * @param  key a name of property to retrieve
     * @return an array containing multiple <code>String</code> values
     * @throws UnknownNamespaceException if the namespace has not been added to
     *         the Configuration Manager
     * @see    #getProperty
     * @see    #getString
     * @throws NullPointerException if any of given arguments is null
     */
    public String[] getStringArray(String namespace, String key) throws UnknownNamespaceException {
        Property property = getPropertyObject(namespace, key);
        if (property == null) {
            return null;
        } else {
            return property.getValues();
        }
    }

    /**
     * Gets the list of property names from the specified namespace. Returns the
     * names of properties at first level of properties hierarchy tree.
     *
     * @param  namespace namespace to retrieve property names from.
     * @return <tt>Enumeration</tt> containing all property names from namespace
     * @throws UnknownNamespaceException if the namespace has not been added to
     *         the Configuration Manager
     * @see    #getProperty
     * @throws NullPointerException if any of given arguments is null
     */
    public Enumeration getPropertyNames(String namespace) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        synchronized (this) {
            if (!namespaces.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            return ((Namespace) namespaces.get(namespace)).getProperties().getRoot().propertyNames();
        }
    }

    /**
     * Checks whether a namespace exists in the config manager.
     *
     * @param namespace namespace to check the existance of.
     * @return <code>true</code> if the namespace has been susccesfully loaded
     * @throws NullPointerException if any of given arguments is null
     */
    public boolean existsNamespace(String namespace) {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        synchronized (this) {
            return namespaces.containsKey(namespace);
        }
    }

    /**
     * Get the format in which specified namespace was parsed.
     *
     * @param  namespace namespace to get format for.
     * @return <tt>String</tt> describing format of namespace.
     * @throws UnknownNamespaceException if the namespace has not been added to
     *         the Configuration Manager
     * @throws NullPointerException if any of given arguments is null
     */
    public String getConfigFormat(String namespace) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        synchronized (this) {
            if (!namespaces.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            return ((Namespace) namespaces.get(namespace)).getFormat();
        }
    }

    /**
     * Gets the name of file in which specified namespace was stored.
     *
     * @param  namespace namespace to get source file for.
     * @return filename of the file storing data for namespace.
     * @throws UnknownNamespaceException if the namespace has not been added to
     *         the <code>ConfigManager</code>
     * @throws NullPointerException if any of given arguments is null
     */
    public String getConfigFilename(String namespace) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        synchronized (this) {
            if (!namespaces.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            return ((Namespace) namespaces.get(namespace)).getFile().getFile();
        }
    }

    /**
     * Gets an <code>Iterator</code> over list of existing namespaces.
     *
     * @return an <code>Iterator</code> over all existing namespaces.
     */
    public Iterator getAllNamespaces() {
        // make a copy to prevent modification
        synchronized (this) {
            return new HashSet(namespaces.keySet()).iterator();
        }
    }

    /**
     * Commits the temporary mutable version of ConfigProperties to permanent
     * memory and attempts to save the properties to the file from which they
     * were originally loaded.
     *
     * @param namespace namespace to commit changes to.
     * @param user      user who made and is committing changes.
     * @throws UnknownNamespaceException if the temporary properties for this
     *         namespace have not yet been created or namespace not loaded in CM
     * @throws ConfigLockedException if namespace is being edited by a user
     *         other than the current user
     * @throws ConfigManagerException on I/O errors when persisiting to a file
     * @see    #createTemporaryProperties
     * @throws NullPointerException if any of given arguments is null
     */
    public void commit(String namespace, String user) throws ConfigManagerException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        if (user == null) {
            throw new NullPointerException("parameter user is null");
        }
        synchronized (this) {
            if (!tempProperties.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }

            // get namespace
            Namespace ns = (Namespace) namespaces.get(namespace);
            if (!ns.canLock(user)) {
                throw new ConfigLockedException(namespace + " is currently locked by another user");
            }
            ns.lock(user);
            try {
                ((ConfigProperties) tempProperties.get(namespace)).save();
            } catch (UnsupportedOperationException uoe) {
                throw new ConfigManagerException(uoe.getMessage());
            } catch (IOException ioe) {
                throw new ConfigManagerException(ioe.getMessage());
            } finally {
                tempProperties.remove(namespace);
                ns.releaseLock();
    
                // refresh namespaces sharing the same source
                for (Iterator itr = new HashSet(namespaces.keySet()).iterator(); itr.hasNext();) {
                    String shareNamespace = (String) itr.next();
                    try {
                        Namespace share = (Namespace) namespaces.get(shareNamespace);
                        if (share.getFile().getFile().equals(ns.getFile().getFile())) {
                            share.getProperties().load();
                        }
                    } catch (Exception exception) {
                        // ignore
                    }
                }
            }
        }
    }

    /**
     * Attempts to lock namespace to give current user sole editing rights
     *
     * @param  namespace namespace to lock.
     * @param  user      user performing lock.
     * @throws UnknownNamespaceException if namespace not loaded in CM
     * @throws ConfigLockedException if namespace is being edited by a user
     *         other than the current user
     * @throws NullPointerException if any of given arguments is null
     * @see    #forceUnlock
     */
    public void lock(String namespace, String user) throws UnknownNamespaceException, ConfigLockedException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        if (user == null) {
            throw new NullPointerException("parameter user is null");
        }
        synchronized (this) {
            if (!namespaces.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            ((Namespace) namespaces.get(namespace)).lock(user);
        }
    }

    /**
     * Checks if namespace can be locked to give current user sole editing
     * rights without generation ConfigLockedException
     *
     * @param  namespace namespace to test lock on.
     * @param  user      who's checking the lock.
     * @return true if namespace can be locked, false otherwise.
     * @throws UnknownNamespaceException if namespace not loaded in
     *         Configuration Manager
     * @throws NullPointerException if any of given arguments is null
     */
    public boolean canLock(String namespace, String user) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        if (user == null) {
            throw new NullPointerException("parameter user is null");
        }
        synchronized (this) {
            if (!namespaces.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }

            Namespace ns = (Namespace) namespaces.get(namespace);
            for (Iterator itr = new HashSet(namespaces.keySet()).iterator(); itr.hasNext();) {
                String shareNamespace = (String) itr.next();
                try {
                    Namespace share = (Namespace) namespaces.get(shareNamespace);
                    if (share.getFile().getFile().equals(ns.getFile().getFile())) {
                        if (!share.canLock(user)) {
                            return false;
                        }
                    }
                } catch (Exception exception) {
                    // ignore
                }
            }
            return true;
        }
    }

    /**
     * Unlocks namespace regardless of current status.
     *
     * @param  namespace namespace to unlock.
     * @throws UnknownNamespaceException if namespace not loaded in
     *         Configuration Manager
     * @throws NullPointerException if any of given arguments is null
     * @see    #lock
     */
    public void forceUnlock(String namespace) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        synchronized (this) {
            if (!namespaces.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }

            Namespace ns = (Namespace) namespaces.get(namespace);
            for (Iterator itr = new HashSet(namespaces.keySet()).iterator(); itr.hasNext();) {
                String shareNamespace = (String) itr.next();
                try {
                    Namespace share = (Namespace) namespaces.get(shareNamespace);
                    if (share.getFile().getFile().equals(ns.getFile().getFile())) {
                        share.releaseLock();
                    }
                } catch (Exception exception) {
                    // ignore
                }
            }
        }
    }

    /**
     * Create a temporary version of the ConfigProperties for the namespace
     * to be used for editing.  Only a single mutable copy is stored for each
     * namespace.  Changes made to this copy are not permanent unless commit
     * is called.
     *
     * @param  namespace namespace to create temporary properties for.
     * @throws UnknownNamespaceException if the namespace has not been added to
     *         Configuration Manager
     * @see    #getTemporaryPropertyNames
     * @see    #commit
     * @throws NullPointerException if any of given arguments is null
     */
    public void createTemporaryProperties(String namespace) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        synchronized (this) {
            if (!namespaces.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            tempProperties.put(namespace, ((Namespace) namespaces.get(namespace)).getProperties().clone());
        }
    }

    /**
     * Gets a list (Enumeration) of the keys in the temporary version
     * of ConfigProperties for the given namespace
     * that is used for editing.  Should only be called by editing
     * programs after createTemporaryProperties.
     *
     * @param  namespace namespace to get property names for.
     * @return <tt>Enumeration</tt> over keys of the temporary properties for
     *         namespace.
     * @throws UnknownNamespaceException if the temporary properties for this
     *         namespace have not yet been created
     * @throws NullPointerException if any of given arguments is null
     * @see    #createTemporaryProperties
     */
    public Enumeration getTemporaryPropertyNames(String namespace) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        synchronized (this) {
            if (!tempProperties.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            return ((ConfigProperties) tempProperties.get(namespace)).getRoot().propertyNames();
        }
    }

    /**
     * Gets the property specified from the temporary version of properties for
     * the specified namespace. Should be used when a single String value is
     * expected for given key.
     *
     * @param  namespace namespace to get property for.
     * @param  key       property to get.
     * @return value stored for key in namespace.
     * @throws UnknownNamespaceException if the temporary properties for this
     *         namespace have not yet been created
     * @throws NullPointerException if any of given arguments is null
     * @see #createTemporaryProperties
     * @see #getString
     */
    public String getTemporaryString(String namespace, String key) throws UnknownNamespaceException {
        synchronized (this) {
            Property property = getTemporaryPropertyObject(namespace, key);
            if (property == null) {
                return null;
            } else {
                // construct a delimeted string
                char listDelimiter = ((Namespace) namespaces.get(namespace)).getProperties().getListDelimiter();
                String[] values = property.getValues();
                return mergeEscaped(values, listDelimiter);
            }
        }
    }

    /**
     * Get the property specified from the temporary version of
     * properties for the specified namespace.
     * Should be used when a single String value is expected for given key.
     *
     * @param  namespace namespace to get property for.
     * @param  key       property to get.
     * @return value stored for key in namespace.
     * @throws UnknownNamespaceException if the temporary properties for this
     *         namespace have not yet been created
     * @throws NullPointerException if any of given arguments is null
     * @since  Configuration Manager 2.1
     * @see    #createTemporaryProperties
     * @see    #getString
     */
    public Property getTemporaryPropertyObject(String namespace, String key) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        if (key == null) {
            throw new NullPointerException("parameter key is null");
        }
        synchronized (this) {
            if (!tempProperties.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            return ((ConfigProperties) tempProperties.get(namespace)).getRoot().getProperty(key);
        }
    }

    /**
     * Get the property specified from the temporary version of
     * properties for the specified namespace.
     * Should be used when the value expected for given key is an array of
     * Strings.  If the value is actually a single String, returns a one-member
     * array.
     *
     * @param  namespace namespace to get values for.
     * @param  key       property to get values from.
     * @return all String's stored in key for namespace.
     * @throws UnknownNamespaceException if the temporary properties for this
     *         namespace have not yet been created
     * @throws NullPointerException if any of given arguments is null
     * @see    #createTemporaryProperties
     * @see    #getStringArray
     */
    public String[] getTemporaryStringArray(String namespace, String key) throws UnknownNamespaceException {
        Property property = getTemporaryPropertyObject(namespace, key);
        if (property == null) {
            return null;
        } else {
            return property.getValues();
        }
    }

    /**
     * Sets property given by key of namespace to hold value. If property does
     * not exist - creates it.
     * Changes only temporary mutable version of ConfigProperties.
     * Changes made to this copy are not permanent unless commit is called.
     *
     * @param  namespace namespace to change values in.
     * @param  key       property to change value of.
     * @param  value     new value for property in namespace.
     * @throws UnknownNamespaceException if the namespace has not been added to
     *         Configuration Manager
     * @throws NullPointerException if any of given arguments is null
     * @see    #createTemporaryProperties
     * @see    #commit
     */
    public void setProperty(String namespace, String key, String value) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        if (key == null) {
            throw new NullPointerException("parameter key is null");
        }
        if (value == null) {
            throw new NullPointerException("parameter value is null");
        }
        synchronized (this) {
            if (!tempProperties.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            ((ConfigProperties) tempProperties.get(namespace)).getRoot().setProperty(key, value);
        }
    }

    /**
     * Sets property given by key of namespace to hold given set of values. If
     * property does not exist - creates it.
     * Changes only temporary mutable version of ConfigProperties.
     * Changes made to this copy are not permanent unless commit is called.
     *
     * @param  namespace namespace to set property in.
     * @param  key       property to set values for.
     * @param  values    new values for property in namespace.
     * @throws UnknownNamespaceException if the namespace has not been added to
     *         Configuration Manager
     * @throws NullPointerException if any of given arguments is null
     * @see    #createTemporaryProperties
     * @see    #commit
     */
    public void setProperty(String namespace, String key, String[] values) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        if (key == null) {
            throw new NullPointerException("parameter key is null");
        }
        if (values == null) {
            throw new NullPointerException("parameter values is null");
        }
        synchronized (this) {
            if (!tempProperties.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            ((ConfigProperties) tempProperties.get(namespace)).getRoot().setProperty(key, values);
        }
    }

    /**
     * Add value to property given by key in namespace.
     * Changes only temporary mutable version of ConfigProperties.
     * Changes made to this copy are not permanent unless commit is called.
     *
     * @param  namespace namespace to add values to.
     * @param  key       property to add to.
     * @param  value     value to add.
     * @see    #createTemporaryProperties
     * @see    #commit
     * @throws UnknownNamespaceException if the namespace has not been added to
     *         CM
     * @throws NullPointerException if any of given arguments is null
     */
    public void addToProperty(String namespace, String key, String value) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        if (key == null) {
            throw new NullPointerException("parameter key is null");
        }
        if (value == null) {
            throw new NullPointerException("parameter value is null");
        }
        synchronized (this) {
            if (!tempProperties.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            Property property = ((ConfigProperties) tempProperties.get(namespace)).getRoot().getProperty(key);
            if (property != null) {
                property.addValue(value);
            } else {
                ((ConfigProperties) tempProperties.get(namespace)).getRoot().setProperty(key, value);
            }
        }
    }

    /**
     * Removes property from specified namespace. If property has nested
     * properties they are removed too.
     *
     * @param  namespace a Namespace that owns the requested property
     * @param  key a <code>String</code> representing a name of requested
     *         property
     * @throws UnknownNamespaceException if required namespace does not exist
     * @throws NullPointerException if any of given arguments is null
     * @since  Configuration Manager 2.1
     */
    public void removeProperty(String namespace, String key) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        if (key == null) {
            throw new NullPointerException("parameter key is null");
        }
        synchronized (this) {
            if (!tempProperties.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            ((ConfigProperties) tempProperties.get(namespace)).getRoot().removeProperty(key);
        }   
    }

    /**
     * Removes value from given property of specified namespace. Does not remove
     * values from properties nested within given property.
     *
     * @param  namespace a Namespace that owns the requested property
     * @param  key a <code>String</code> representing a name of requested
     *         property
     * @param  value a <code>String</code> representing a value to remove
     *         from requested property
     * @throws UnknownNamespaceException if required namespace does not exist
     * @throws NullPointerException if any of given arguments is null
     * @since  Configuration Manager 2.1
     */
    public void removeValue(String namespace, String key, String value) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        if (key == null) {
            throw new NullPointerException("parameter key is null");
        }
        if (value == null) {
            throw new NullPointerException("parameter value is null");
        }
        synchronized (this) {
            if (!tempProperties.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            Property property = ((ConfigProperties) tempProperties.get(namespace)).getRoot().getProperty(key);
            if (property != null) {
                property.removeValue(value);
            }
        }
    }

    /**
     * Removes specified namespace from ConfigManager.
     *
     * @param  namespace a namespace to remove
     * @throws UnknownNamespaceException if required namespace does not exist
     * @throws NullPointerException if any of given arguments is null
     * @since  Configuration Manager 2.1
     */
    public void removeNamespace(String namespace) throws UnknownNamespaceException {
        if (namespace == null) {
            throw new NullPointerException("parameter namespace is null");
        }
        synchronized (this) {
            if (!namespaces.containsKey(namespace)) {
                throw new UnknownNamespaceException(namespace + " is unknown");
            }
            namespaces.remove(namespace);
            tempProperties.remove(namespace);
        }
    }

    /**
     * Merge a list of values into a single value with specified list delimiter.
     * Possible occurance of list delimiter in the value itself should be escaped.
     *
     * @param  values an array of values to merge
     * @param  listDelimiter the list delimiter to use
     * @return the merged string
     */
    private static String mergeEscaped(String[] values, char listDelimiter) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < values.length; ++i) {
            if (i > 0) {
                buffer.append(listDelimiter);
            }
            for (int k = 0; k < values[i].length(); ++k) {
                char ch = values[i].charAt(k);
                if (ch == listDelimiter) {
                    buffer.append('\\');
                    buffer.append(ch);
                } else {
                    buffer.append(ch);
                }
            }
        }
        return buffer.toString();
    }

}
