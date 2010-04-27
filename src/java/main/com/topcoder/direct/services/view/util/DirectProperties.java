/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.MissingResourceException;

import com.topcoder.shared.util.TCResourceBundle;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>
 * A collection of properties for Direct application.
 * </p>
 *
 * @author BeBetter
 * @version 1.0 (Direct Search Assembly)
 */
public final class DirectProperties {
    /**
     * <p>
     * A <code>Logger</code> to be used for logging the events.
     * </p>
     */
    private static final Logger log = Logger.getLogger(DirectProperties.class);

    /**
     * <p>
     * A <code>TCResourceBundle</code> providing access to configuration properties for this class.
     * </p>
     */
    private static final TCResourceBundle bundle = new TCResourceBundle("Direct");

    /**
     * <p>
     * contest service facade jndi name.
     * </p>
     */
    public static String CONTEST_SERVICE_FACADE_JNDI_NAME = "remote/ContestServiceFacadeBean";

    /**
     * <p>
     * contest service facade context factory.
     * </p>
     */
    public static String CONTEST_SERVICE_FACADE_CONTEXT_FACTORY = "org.jnp.interfaces.NamingContextFactory";

    /**
     * <p>
     * contest service facade ejb provider url.
     * </p>
     */
    public static String CONTEST_SERVICE_FACADE_PROVIDER_URL = "jnp://localhost:1399";

    /**
     * <p>
     * Initializes non-final static fields for this class with values for the same-named properties from the resource
     * bundle.
     * </p>
     */
    static {
        initialize();
    }

    /**
     * <p>
     * Constructs new <code>DirectProperties</code> instance. This implementation does nothing.
     * </p>
     */
    private DirectProperties() {
    }

    /**
     * <p>
     * Initializes non-final static fields for this class with values for the same-named properties from the resource
     * bundle.
     * </p>
     */
    public static void initialize() {
        Field[] f = DirectProperties.class.getFields();
        for (int i = 0; i < f.length; i++) {
            try {
                if (!Modifier.isFinal(f[i].getModifiers())) {
                    if (f[i].getType().getName().equals("int")) {
                        try {
                            f[i].setInt(null, bundle.getIntProperty(f[i].getName()));
                        } catch (MissingResourceException ignore) {
                        }
                    } else if (f[i].getType().getName().equals("java.lang.String")) {
                        try {
                            System.out.println(f[i].getName() + ":"
                                + bundle.getProperty(f[i].getName()));
                            f[i].set(null, bundle.getProperty(f[i].getName()));
                        } catch (MissingResourceException ignore) {
                        }
                    } else {
                        throw new Exception("Unrecognized type: " + f[i].getType().getName());
                    }
                }
                if (f[i].get(null) == null) {
                    log.error("**DID NOT LOAD** " + f[i].getName() + " constant");
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug(f[i].getName() + " <== " + f[i].get(null));
                    }
                }
            } catch (Exception e) {
                /* probably harmless, could just be a type or modifier mismatch */
                e.printStackTrace();
            }
        }
    }

}
