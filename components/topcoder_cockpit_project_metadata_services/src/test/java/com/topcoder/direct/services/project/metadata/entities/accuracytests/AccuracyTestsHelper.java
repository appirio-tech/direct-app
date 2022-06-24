/**
 * Copyright (c) 2011, TopCoder, Inc. All rights reserved
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>A helper class used in accuracy tests.</p>
 *
 * @author jmn
 * @version 1.0
 */
final class AccuracyTestsHelper {

    /**
     * Represents the date format to be used in json serialization.
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * <p>Creates new instance of {@link AccuracyTestsHelper} class.</p>
     *
     * <p>Private constructor prevents from initialization outside this class.</p>
     */
    private AccuracyTestsHelper() {
        // empty constructor
    }

    /**
     * <p>Formats the date into expected representation.</p>
     *
     * @param date the date to format
     *
     * @return the formatted date
     */
    static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }
}
