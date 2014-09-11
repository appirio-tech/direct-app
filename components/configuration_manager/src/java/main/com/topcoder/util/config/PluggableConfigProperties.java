/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) PluggableConfigProperties.java
 *
 * 1.0  04/23/2003
 */
package com.topcoder.util.config;

import java.util.List;
import java.util.Iterator;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;

/**
 * An extension of <code>ConfigProperties</code> providing the ability to use
 * pluggable sources of configuration properties.
 *
 * @author  isv
 * @author  WishingBone
 * @version 1.0  04/23/2003
 * @since   Configuration Manager 2.1
 */
class PluggableConfigProperties extends ConfigProperties {

    /**
     * An underlying source of configuration data elements
     */
    private PluggableConfigSource source = null;

    /**
     * A private no-arg constructor (for clone).
     */
    private PluggableConfigProperties() {
    }

    /**
     * Constructs a new instance of <code>PluggableConfigProperties</code>
     * interacting with <code>PluggableConfigSource</code> described by
     * properties containing within given file. Loads the <code>Properties
     * </code> object from given <code>file</code>, instantiates and configure
     * <code>PluggableConfigSource</code> object with created <code>Properties
     * </code>.
     *
     * @param  file a file with definition and initial properties of <code>
     *         PluggableConfigSouirce</code>
     * @throws ConfigManagerException if any exception preventing from
     *         successfull loading properties from given file, instantiation of
     *         <code>PluggableConfigSource</code> occurs
     * @throws IOException if any exception related to underlying persistent
     *         storage occurs
     */
    protected PluggableConfigProperties(URL file) throws ConfigManagerException, IOException {
        // load properties
        Properties properties = new Properties();
        InputStream stream = null;
        try {
            stream = file.openStream();
            properties.load(stream);
        } catch (IOException ioe) {
            throw new ConfigManagerException("can not load config file");
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        // get class name
        String className = properties.getProperty("classname");
        if (className == null) {
            throw new ConfigManagerException("config file does not contain the classname");
        }

        // instantiate
        Object object;
        try {
            object = Class.forName(className).newInstance();
        } catch (Exception exception) {
            throw new ConfigManagerException("unable to instantiate : " + exception.getMessage());
        }

        // configure
        if (!(object instanceof PluggableConfigSource)) {
            throw new ConfigManagerException("incorrect object type");
        }
        source = (PluggableConfigSource) object;
        source.configure(properties);
        load();
    }

    /**
     * Saves the data(properties and their values) from properties tree into
     * persistent storage.
     *
     * @throws IOException if any exception related to underlying persistent
     *         storage occurs
     */
    protected void save() throws IOException {
        source.save(getRoot());
    }

    /**
     * Loads the properties and their values from persistent storage.
     *
     * @throws IOException if any exception related to underlying persistent
     *         storage occurs
     */
    protected void load() throws IOException {
        List list = source.getProperties();
        Property root = new Property();
        for (Iterator itr = list.iterator(); itr.hasNext();) {
            Object object = itr.next();
            if (object instanceof Property) {
                root.addProperty((Property) object);
            } else {
                throw new ConfigParserException("contains non Property instance");
            }
        }
        setRoot(root);
    }

    /**
     * Gets the clone copy of this object.
     *
     * @return a clone copy of this object
     */
    public Object clone() {
        PluggableConfigProperties properties = new PluggableConfigProperties();
        properties.source = source;
        properties.setRoot((Property) getRoot().clone());
        return properties;
    }

}
