/*
 * ComponentName: WorkDays
 * FileName: ConfigHelper.java
 * Version: 1.0
 * Date: 06/01/2005
 *
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */

package com.topcoder.date.workdays.accuracytests;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import java.util.Iterator;

/**
 * <p>
 * This package private class is used to get the configured  ConfigManger,
 * only contains one static method. Please see the  doc of  the method for
 * more details.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
class ConfigHelper {

    /**
     * <p>
     * Represents the location of the configuration file WorkdaysFactory.properties
     * </p>
     */
    public static String LOCATION_CONFIG = 
        "test_files/accuracytests/defaultWorkdaysFactory.properties";

    /**
     * <p>
     * Represents the namespace of the configuration file WorkdaysFactory.properties
     * </p>
     */
    public static String NAMESPACE = "com.topcoder.date.workdays";

    /**
     * <p>
     * Initial the ConfigManager with the configuration files in the
     * test_files.
     * </p>
     *
     * @return config manager
     *
     * @throws ConfigManagerException if any config exception occurs
     */
    public static ConfigManager initialConfigManager()
        throws ConfigManagerException {
        ConfigManager cm = ConfigManager.getInstance();
        
        Iterator it = cm.getAllNamespaces();
        //while (it.hasNext()) {
            //cm.removeNamespace((String) it.next());
        //}
        if (cm.existsNamespace(NAMESPACE)) {
            cm.removeNamespace(NAMESPACE);
        }


        cm.add(NAMESPACE, LOCATION_CONFIG,
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
        
        return cm;
    }
}
