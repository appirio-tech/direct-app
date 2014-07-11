/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import com.topcoder.shared.util.ApplicationServer;
import com.topcoder.shared.util.TCResourceBundle;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>This class extends ApplicationServer </p>
 *
 * @author GreatKevin
 * @version 1.0 (Tokenize the server part of URLs used in TopCoder Direct)
 */
public class ServerConfiguration extends ApplicationServer {

    /**
     * The logger.
     */
    private static Logger log = Logger.getLogger(ServerConfiguration.class);

    /**
     * The resource bundle to read properties
     */
    private static TCResourceBundle bundle = new TCResourceBundle("ApplicationServer");

    /**
     * The new community server name.
     */
    public static String NEW_SERVER_NAME = bundle.getProperty("NEW_SERVER_NAME", "www.topcoder.com");

    /**
     * The Jira server name.
     */
    public static String JIRA_SERVER_NAME = bundle.getProperty("JIRA_SERVER_NAME", "apps.topcoder.com/bugs/");

    /**
     * The studio forum name.
     */
    public static String STUDIO_FORUMS_SERVER_NAME = bundle.getProperty("STUDIO_FORUMS_SERVER_NAME", "studio.topcoder.com/forums");
}
