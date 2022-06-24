/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) ConfigManager.java
 *
 * 2.1  05/07/2003
 */
package com.topcoder.util.config;

import java.util.Iterator;
import java.util.Enumeration;
import java.util.Properties;
import java.util.MissingResourceException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.MalformedURLException;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * The <code>ConfigManager</code> class centralizes the management of and access
 * to applications' configuration details. It provides a common interface for
 * accessing and updating applications' properties.
 *
 * <p>The <code>ConfigManager</code> provides a possibility to maintain a list
 * of applications' properties through standard .properties files, an .xml files
 * based on Configuration Manager DTD, an .xml files containing multiple
 * namespaces. Since version 2.1 a pluggable sources of configuration properties
 * are also available. Those sources are described in .config files that
 * represent a standard properties files. The .config files should contain a
 * required <code>classname</code> property that contains the name of class
 * implementing the <code>PluggableConfigSource</code> interface, and any number
 * of key-value pairs that should be used to configure this <code>
 * PluggableConfigSource</code> instance.
 *
 * <p>The public methods provided by <code>ConfigManager</code> allow to :
 *    <ol>
 *      <li>query the value of properties of namespaces loaded into
 *          Configuration Manager</li>
 *      <li>add new namespaces to Configuration Manager</li>
 *      <li>modify the properties of existing namespaces</li>
 *      <li>remove namespaces from memory</li>
 *      <li>remove properties and their values from namespaces</li>
 *      <li>nest the properties within other properties as deep as needed</li>
 *    </ol>
 *
 * <p>The ConfigManager has it's own configuration file named <code>
 * com/topcoder/util/config/ConfigManager.properties</code> that should be
 * placed in directory which <code>CLASSPATH</code> system environment variable
 * points to. This file contains the definitions of namespaces that should be
 * loaded into memory with every start of <code>Configuration Manager</code>.
 * The format of records in this file is as follows : <code>namespace=filename
 * </code>, for example : <code>com.topcoder.ConfigManagerNamespace =
 * com/topcoder/util/config/ConfigManager.properties</code>.
 *
 * <p>The methods of <code>ConfigManager</code> modifying the namespaces, such
 * as : <code>addToProperty(), setProperty(), removeProperty(), removeValue()
 * </code> are executed only after a temporary copy of namespace has been
 * created. Any changes made by those methods are not visible untill the <code>
 * commit()</code> method is invoked.
 *
 * <p>Examples of usage of <code>ConfigManager</code> :
 * <pre>
 *
 *      // First an instance of ConfigManager should be obtained. This will also
 *      // load the predefined set of namespaces and their properties into
 *      // memory
 *
 *     ConfigManager manager = ConfigManager.getInstance();
 *
 *
 *     // any additional namespace may be loaded then
 *
 *     manager.add("namespace.name","somefile.xml",
 *         ConfigManager.CONFIG_XML_FORMAT);
 *
 *
 *     // once the namespace properties are loaded they can be queried
 *
 *     String value = manager.getString("namespace.name", "property.name");
 *
 *     // multiple values of same property are also supported
 *
 *     String[] values = manager.getStringArray("namespace.name",
 *                                              "multi-value.property.name");
 *
 *     // a new properties may be defined and stored for further use
 *
 *     manager.createTemporaryProperties("namespace.to.modify");
 *     manager.setProperty("namespace.to.modify", "new.property", "value");
 *     manager.addToProperty("namespace.to.modify", "existing.property",
 *         "new value");
 *     manager.commit("namespace.to.modify", "user");
 *
 *     // since Configuration Manager 2.1 following features are available :
 *
 *     // removing namespaces from in-memory set of loaded namespaces
 *
 *     manager.removeNamespace("namespace.to.remove");
 *
 *     // removing of properties
 *
 *     manager.createTemporaryProperties("namespace.to.modify");
 *     manager.removeProperty("namespace.to.modify", "property.to.remove");
 *     manager.commit("namespace.to.modify", "user");
 *
 *     // removing of values of properties
 *
 *     manager.createTemporaryProperties("namespace.to.modify");
 *     manager.removeValue("namespace.to.modify",
 *                         "property.to.remove.value.from",
 *                         "value.to.remove");
 *     manager.commit("namespace.to.modify", "user");
 *
 *     // obtaining the object representation of properties tree or sub-tree
 *     // for convenience of querying
 *
 *     Property property = manager.getPropertyObject("namespace.name",
 *                                                   "property.name");
 *     property.getValue("nested.property");
 *
 *     // addition of pluggable sources of configuration properties
 *
 *     manager.add("pluggable.namespace", "somefile.config",
 *         ConfigManager.CONFIG_PLUGGABLE_FORMAT );
 * </pre>
 *
 * @author  ilya
 * @author  debedeb
 * @author  isv
 * @author  WishingBone
 * @version 2.1  05/07/2003
 */
public abstract class ConfigManager implements ConfigManagerInterface {

    /**
     * A namespace owned by <code>Configuration Manager</code> component.
     *
     * @since Configuration Manager 2.1
     */
    public final static String CONFIG_MANAGER_NAMESPACE = "com.topcoder.util.config.ConfigManager";

    /**
     * The constant specifying XML config file format. Corresponding files
     * should have names ending with <code>.xml</code>
     */
    public final static String CONFIG_XML_FORMAT = ".xml";

    /**
     * The constant specifying PROPERTIES config file format. Corresponding
     * files should have names ending with <code>.properties</code>
     */
    public final static String CONFIG_PROPERTIES_FORMAT = ".properties";

    /**
     * The constant specifying the format of XML files containg multiple
     * namespaces.
     */
    public final static String CONFIG_MULTIPLE_XML_FORMAT = "MXML";

    /**
     * The constant specifying PLUGGABLE config file format. Corresponding
     * files should have names ending with <code>.config</code>
     *
     * @since Configuration Manager 2.1
     */
    public final static String CONFIG_PLUGGABLE_FORMAT = ".config";

    /**
     * The constant specifying the mode when all exceptions should be returned.
     */
    public final static int EXCEPTIONS_ALL = 0;

    /**
     * The constant specifying the mode when  major exceptions only should be
     * returned.
     */
    public final static int EXCEPTIONS_MAJOR = 1;

    /**
     * A single per JVM instance of <code>ConfigManager</code>
     */
    private static ConfigManager defaultConfigManager = null;

    /**
     * Load a filename with a URL. This method searches the classpath for the specific file.
     *
     * @param  filename the filename to load.
     * @return the url for the file.
     * @throws ConfigManagerException if the file can not be loaded.
     */
    static URL getURL(String filename) throws ConfigManagerException {
        if (new File(filename).isAbsolute()) {
            // absolute filename
            try {
                return new URL("file:" + filename);
            } catch (MalformedURLException murle) {
                throw new ConfigManagerException(filename + " is invalid");
            }
        } else {
            // relative filename
            try {
                URL url = ConfigManager.class.getClassLoader().getResource(filename);
                if (url != null) {
                    return url;
                }
            } catch (MissingResourceException mre) {
            }
            throw new ConfigManagerException("can not locate " + filename);
        }
    }

    /**
     * Decode the url file into valid file name.
     *
     * @param  url the url to decode.
     * @return the decoded file name.
     * @throws ConfigManagerException if the url's format is invalid.
     */
    static String decodeURL(String url) throws ConfigManagerException {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            throw new ConfigManagerException("The URL is not encoded with UTF-8.");
        }
    }

    /**
     * Gets the instance of the config manager for this JVM. If no instance currently
     * exists a new one is created. This newly created instance is either instance of
     * some ConfigManager descendant class specified by property names "implementor"
     * in namespace owned by ConfigManager or DeafultConfigManager by default.
     *
     * @return an instance of <code>ConfigManager</code> if any exists,
     *         otherwise a new one.
     */
    public static ConfigManager getInstance() {
    	synchronized (ConfigManager.class) {
    		if (defaultConfigManager == null) {
    	        defaultConfigManager = new DefaultConfigManager();

    	        // preload namepaces
    	        try {
    	            // load into a Properties
    	            InputStream is = getURL("com/topcoder/util/config/ConfigManager.properties").openStream();
    	            Properties properties = new Properties();

    	            try {
    	                properties.load(is);
    	            } finally {
    	                is.close();
    	            }

    	            // get all namespaces
    	            for (Enumeration enu = properties.propertyNames(); enu.hasMoreElements();) {
    	                String namespace = (String) enu.nextElement();
    	                String filename = properties.getProperty(namespace);

    	                try {
    	                    // TCS Component
    	                    if (filename == null || filename.length() == 0) {
    	                        defaultConfigManager.add(namespace, CONFIG_XML_FORMAT);
    	                        continue;
    	                    }

    	                    // judge file type from extension
    	                    String lower = filename.toLowerCase();
    	                    if (lower.endsWith(CONFIG_PROPERTIES_FORMAT)) {
    	                        // properties
    	                        defaultConfigManager.add(namespace, filename, CONFIG_PROPERTIES_FORMAT);
    	                    } else if (lower.endsWith(CONFIG_PLUGGABLE_FORMAT)) {
    	                        // pluggable
    	                        defaultConfigManager.add(namespace, filename, CONFIG_PLUGGABLE_FORMAT);
    	                    } else if (lower.endsWith(CONFIG_XML_FORMAT)) {
    	                        // xml, whether multiple namespace?
    	                        Enumeration test = XMLConfigProperties.getNamespaces(getURL(filename));
    	                        if (test != null && test.hasMoreElements()) {
    	                            defaultConfigManager.add(namespace, filename, CONFIG_MULTIPLE_XML_FORMAT);
    	                        } else {
    	                            defaultConfigManager.add(namespace, filename, CONFIG_XML_FORMAT);
    	                        }
    	                    } else {
    	                        System.err.println("unrecognized file type : " + filename);
    	                    }
    	                } catch (Exception exception) {
    	                    System.err.println("encounter exception loading " + filename + " : " + exception.getMessage());
    	                    exception.printStackTrace();
    	                }
    	            }
    	        } catch (Exception exception) {
    	            exception.printStackTrace();
    	        }
    		}
        }
        return defaultConfigManager;
    }

    /**
     * Part of ConfigManagerInterface implementation.  Returns the namespace
     * owned by the <code>Configuration Manager</code>.
     *
     * @return a <code>String</code> representing the namespace owned by <code>
     *         ConfigurationManager</code>
     */
    public abstract String getNamespace();

    /**
     * Part of ConfigManagerInterface implementation.  Returns all known
     * properties for the config manager.
     *
     * @return all known properties for the config manager.
     */
    public abstract Enumeration getConfigPropNames();

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
    public abstract void add(String namespace, String filename, String format, int exceptionLevel)
            throws ConfigManagerException;

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
    public abstract void add(String namespace, String format, int exceptionLevel) throws ConfigManagerException;

    /**
     * An extension to the add method to add files that already list their namespaces
     * (i.e. those containing multiple namespaces).
     *
     * @param  url location where data is to be read from.
     * @param  exceptionLevel level of Exceptions to display.  See constants.
     * @throws ConfigManagerException when url is invalid or error occur while parsing file.
     * @throws IllegalArgumentException if given exceptionLevel is not from
     *         set of predefined constants or given filename is empty
     * @throws NullPointerException if given URL is <code>null</code>.
     */
    public abstract void add(URL url, int exceptionLevel) throws ConfigManagerException;

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
    public abstract void add(String filename, int exceptionLevel) throws ConfigManagerException;

    /**
     * Adds properties to namespace.  Defaults to exceptionLevel=EXCEPTIONS_ALL
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
    public abstract void add(String namespace, String filename, String format) throws ConfigManagerException;

    /**
     * Adds properties to namespace.  Defaults to exceptionLevel=EXCEPTIONS_ALL
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
    public abstract void add(String namespace, String format) throws ConfigManagerException;

    /**
     * Adds properties to namespace associated with filename.  Defaults to
     * exceptionLevel=EXCEPTIONS_ALL
     *
     * @param  filename  filename with properties in it.
     * @throws ConfigManagerException when filename can't be used.
     * @throws IllegalArgumentException if given filename is empty
     * @throws NullPointerException if given filename is null
     */
    public abstract void add(String filename) throws ConfigManagerException;

    /**
     * Adds properties to namespace associated with url.  Defaults to
     * exceptionLevel=EXCEPTIONS_ALL
     *
     * @param  url location where data is to be read from.
     * @throws ConfigManagerException if url is invalid or error occur while parsing file.
     * @throws IllegalArgumentException if given filename is empty
     * @throws NullPointerException if given filename is null
     */
    public abstract void add(URL url) throws ConfigManagerException;

    /**
     * Reloads configuration information for all added namespaces. Namespaces
     * for which the refresh operation fails will become invalid.
     *
     * @throws ConfigManagerException or a subclasses of it if any errors
     *         happened during refresh. Namespaces for which refresh succeeded
     *         are not invalidated.
     */
    public abstract void refreshAll() throws ConfigManagerException;

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
    public abstract void refresh(String namespace) throws ConfigManagerException, UnknownNamespaceException;

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
    public abstract Object getProperty(String namespace, String key) throws UnknownNamespaceException;

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
    public abstract Property getPropertyObject(String namespace, String key) throws UnknownNamespaceException;

    /**
     * Get the property specified from the specified namespace.
     * Should be used when single value is expected for the given property key.
     *
     * @param  namespace namespace to retrieve from
     * @param  key a property to retrieve.
     * @return a <code>String</code> value of the property specified by
     *         namespace and key or empty string if property does not exist
     * @throws UnknownNamespaceException if the namespace
     *         has not been added to the CM.
     * @see    #getProperty
     * @see    #getStringArray
     * @throws NullPointerException if any of given arguments is null
     */
    public abstract String getString(String namespace, String key) throws UnknownNamespaceException;

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
    public abstract String[] getStringArray(String namespace, String key) throws UnknownNamespaceException;

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
    public abstract Enumeration getPropertyNames(String namespace) throws UnknownNamespaceException;

    /**
     * Checks whether a namespace exists in the config manager.
     *
     * @param namespace namespace to check the existance of.
     * @return <code>true</code> if the namespace has been susccesfully loaded
     * @throws NullPointerException if any of given arguments is null
     */
    public abstract boolean existsNamespace(String namespace);

    /**
     * Get the format in which specified namespace was parsed.
     *
     * @param  namespace namespace to get format for.
     * @return <tt>String</tt> describing format of namespace.
     * @throws UnknownNamespaceException if the namespace has not been added to
     *         the Configuration Manager
     * @throws NullPointerException if any of given arguments is null
     */
    public abstract String getConfigFormat(String namespace) throws UnknownNamespaceException;

    /**
     * Gets the name of file in which specified namespace was stored.
     *
     * @param  namespace namespace to get source file for.
     * @return filename of the file storing data for namespace.
     * @throws UnknownNamespaceException if the namespace has not been added to
     *         the <code>ConfigManager</code>
     * @throws NullPointerException if any of given arguments is null
     */
    public abstract String getConfigFilename(String namespace) throws UnknownNamespaceException;

    /**
     * Gets an <code>Iterator</code> over list of existing namespaces.
     *
     * @return an <code>Iterator</code> over all existing namespaces.
     */
    public abstract Iterator getAllNamespaces();

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
    public abstract void commit(String namespace, String user) throws ConfigManagerException;

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
    public abstract void lock(String namespace, String user) throws UnknownNamespaceException, ConfigLockedException;

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
    public abstract boolean canLock(String namespace, String user) throws UnknownNamespaceException;

    /**
     * Unlocks namespace regardless of current status.
     *
     * @param  namespace namespace to unlock.
     * @throws UnknownNamespaceException if namespace not loaded in
     *         Configuration Manager
     * @throws NullPointerException if any of given arguments is null
     * @see    #lock
     */
    public abstract void forceUnlock(String namespace) throws UnknownNamespaceException;

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
    public abstract void createTemporaryProperties(String namespace) throws UnknownNamespaceException;

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
    public abstract Enumeration getTemporaryPropertyNames(String namespace) throws UnknownNamespaceException;

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
    public abstract String getTemporaryString(String namespace, String key) throws UnknownNamespaceException;

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
    public abstract Property getTemporaryPropertyObject(String namespace, String key) throws UnknownNamespaceException;

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
    public abstract String[] getTemporaryStringArray(String namespace, String key) throws UnknownNamespaceException;

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
    public abstract void setProperty(String namespace, String key, String value) throws UnknownNamespaceException;

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
    public abstract void setProperty(String namespace, String key, String[] values) throws UnknownNamespaceException;

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
    public abstract void addToProperty(String namespace, String key, String value) throws UnknownNamespaceException;

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
    public abstract void removeProperty(String namespace, String key) throws UnknownNamespaceException;

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
    public abstract void removeValue(String namespace, String key, String value) throws UnknownNamespaceException;

    /**
     * Removes specified namespace from ConfigManager.
     *
     * @param  namespace a namespace to remove
     * @throws UnknownNamespaceException if required namespace does not exist
     * @throws NullPointerException if any of given arguments is null
     * @since  Configuration Manager 2.1
     */
    public abstract void removeNamespace(String namespace) throws UnknownNamespaceException;

}
