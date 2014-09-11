/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.startdategenerator;

import java.util.Calendar;
import java.util.Date;

import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.StartDateGenerationException;
import com.topcoder.project.phases.template.StartDateGenerator;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * <code>RelativeWeekTimeStartDateGenerator</code> is the initial built-in implementation of
 * <code>{@link StartDateGenerator}</code> interface.
 * </p>
 * <p>
 * It generates a relative time in a week as the default start date. The
 * relative time is specified by the following values:
 * </p>
 * <ol>
 * <li><b>weekOffset</b> <br>
 * Week offset determines in WHICH week the time will be generated. <br>
 * For example, a weekOffset 1 tells us that a relative time in the NEXT(from current) week will be generated,
 * a weekOffset 0 tell us that a relative time in THIS(current) week will be generated.</li>
 * <li><b>dayOfWeek</b> <br>
 * dayOfWeek is a number which determines in WHICH day the time will be generated. The values is from 1 to 7,
 * which represents SUNDAY to SATURDAY respectively (this is consistent with the
 * <code>java.util.Calendar</code> definitions). <br>
 * For example, a dayOfWeek 1 tells us that a relative time in SUNDAY will be generated.</li>
 * <li><b>hour</b> <br>
 * hour determines the hour component of the time to generate. hour values are in 24-hour clock, values 0-23
 * is acceptable. <br>
 * For example, an hour 9 tells us that the hour component is 9 AM, while an hour 15 tells us that the hour
 * component is 3 PM.</li>
 * <li><b>minute</b> <br>
 * minute component of the time to generate, values 0-59 are acceptable.</li>
 * <li><b>second</b> <br>
 * second component of the time to generate, values 0-59 are acceptable.</li>
 * </ol>
 * <p>
 * For example, given the next values: (weekOffSet, dayOfWeek, hour, minute, second) = (1, 5, 9, 0, 0), then
 * 9:00:00 AM next Thursday will be generated, just as the TCS project posting time in the next week.
 * </p>
 * <p>
 * <code>RelativeWeekTimeStartDateGenerator</code> can be created from configuration, please consult the
 * component specification and sample configuration file (docs/Relative_Week_Time_Start_Date_Generator.xml)
 * for details.
 * </p>
 * <p>
 * This class is thread safe since it is immutable.
 * </p>
 *
 * @author albertwang, TCSDEVELOPER
 * @author flying2hk, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class RelativeWeekTimeStartDateGenerator implements StartDateGenerator {
    /**
     * <p>
     * Represents the property key of "week_offset".
     * </p>
     */
    private static final String KEY_WEEK_OFFSET = "week_offset";

    /**
     * <p>
     * Represents the property key of "day_of_week".
     * </p>
     */
    private static final String KEY_DAY_OF_WEEK = "day_of_week";

    /**
     * <p>
     * Represents the property key of "hour".
     * </p>
     */
    private static final String KEY_HOUR = "hour";

    /**
     * <p>
     * Represents the property key of "minute".
     * </p>
     */
    private static final String KEY_MINUTE = "minute";

    /**
     * <p>
     * Represents the property key of "second".
     * </p>
     */
    private static final String KEY_SECOND = "second";

    /**
     * <p>
     * Represents the valid range of hour field, the first element is the min value,
     * the second element is the max value.
     * </p>
     */
    private static final int[] HOUR_RANGE = {0, 23};

    /**
     * <p>
     * Represents the valid range of minute field, the first element is the min value,
     * the second element is the max value.
     * </p>
     */
    private static final int[] MINUTE_RANGE = {0, 59};

    /**
     * <p>
     * Represents the valid range of second field, the first element is the min value,
     * the second element is the max value.
     * </p>
     */
    private static final int[] SECOND_RANGE = {0, 59};

    /**
     * <p>
     * Represents the valid range of dayOfWeek field, the first element is the min value,
     * the second element is the max value.
     * </p>
     */
    private static final int[] DAY_OF_WEEK_RANGE = {1, 7};

    /**
     * <p>
     * Represents the hour value in 24-hour clock of the relative time to
     * generate.
     * </p>
     * <p>
     * It is initialized in constructor, can be accessed by the getter and can
     * not be modified afterwards.The valid values will be from 0 to 23.
     * </p>
     */
    private final int hour;

    /**
     * <p>
     * Represents the minute value of the relative time to generate.
     * </p>
     * <p>
     * It is initialized in constructor, can be accessed by the getter and can
     * not be modified afterwards.The valid values will be from 0 to 59.
     * </p>
     */
    private final int minute;

    /**
     * <p>
     * Represents the second value of the relative time to generate.
     * </p>
     * <p>
     * It is initialized in constructor, can be accessed by the getter and can
     * not be modified afterwards.The valid values will be from 0 to 59.
     * </p>
     */
    private final int second;

    /**
     * <p>
     * Represents the day of the week of the relative time to generate.
     * </p>
     * <p>
     * It is initialized in constructor, can be accessed by the getter and can
     * not be modified afterwards.The valid values will be from 1 to 7, which
     * represents Sunday to Saturday respectively.
     * </p>
     */
    private final int dayOfWeek;

    /**
     * <p>
     * Represents the week offset(from current week) of the time to generate.
     * </p>
     * <p>
     * E.g. 1 means the next week, 0 means current week, -1 means the past
     * week(though generally it is rarely used as a start date.).
     * </p>
     * <p>
     * It is initialized in the constructor,can be accessed by the getter and
     * can not be modified afterwards.Any integer values are valid.
     * </p>
     */
    private final int weekOffset;

    /**
     * <p>
     * Create <code>RelativeWeekTimeStartDateGenerator</code> instance from the
     * configuration namespace.
     * </p>
     * @param namespace the configuration namespace
     * @throws ConfigurationException if the namespace is not loaded or any
     *             value is missing or in invalid format or out of range
     * @throws IllegalArgumentException if namespace is null or empty string
     */
    public RelativeWeekTimeStartDateGenerator(String namespace) throws ConfigurationException {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace can not be null!");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace can not be empty string!");
        }
        try {
            ConfigManager cm = ConfigManager.getInstance();
            // retrieve the configuration properties and initialize the variables
            this.dayOfWeek = checkIntegerRange(DAY_OF_WEEK_RANGE, Integer.parseInt(cm.getString(namespace,
                    KEY_DAY_OF_WEEK)), KEY_DAY_OF_WEEK);
            this.hour = checkIntegerRange(HOUR_RANGE, Integer.parseInt(cm.getString(namespace, KEY_HOUR)),
                    KEY_HOUR);
            this.minute = checkIntegerRange(MINUTE_RANGE,
                    Integer.parseInt(cm.getString(namespace, KEY_MINUTE)), KEY_MINUTE);
            this.second = checkIntegerRange(SECOND_RANGE,
                    Integer.parseInt(cm.getString(namespace, KEY_SECOND)), KEY_SECOND);
            this.weekOffset = Integer.parseInt(cm.getString(namespace, KEY_WEEK_OFFSET));
        } catch (Exception ex) {
            // any exception caught, wrap to ConfigurationException
            throw new ConfigurationException("Error occurs while loading the configuration.", ex);
        }
    }

    /**
     * <p>
     * Create <code>RelativeWeekTimeStartDateGenerator</code> instance with
     * given values.
     * </p>
     * @param dayOfWeek the day of the week
     * @param hour the hour value in 24-hour clock
     * @param minute the minute value
     * @param second the second value
     * @param weekOffset the week offset from current week
     * @throws IllegalArgumentException if hour is not in [0, 23] or minute is
     *             not in [0, 59] or second is not in [0, 59] or dayOfWeek is
     *             not in [1, 7]
     */
    public RelativeWeekTimeStartDateGenerator(int dayOfWeek, int hour, int minute, int second, int weekOffset) {
        this.hour = checkIntegerRange(HOUR_RANGE, hour, "hour");
        this.minute = checkIntegerRange(MINUTE_RANGE, minute, "minute");
        this.second = checkIntegerRange(SECOND_RANGE, second, "second");
        this.dayOfWeek = checkIntegerRange(DAY_OF_WEEK_RANGE, dayOfWeek, "dayOfWeek");
        this.weekOffset = weekOffset;
    }

    /**
     * <p>
     * Generate a start date according to a "relative time in a week" generation
     * logic.
     * </p>
     * <p>
     * It generates a relative time in a week as the default start date.
     * </p>
     * <p>
     * For example, given the next values: (weekOffSet, dayOfWeek, hour, minute,
     * second) = (1, 5, 9, 0, 0) Then 9:00:00 AM next Thursday will be
     * generated, just as the TCS project posting time in the next week.
     * </p>
     * @return the generated relative time in a week
     * @throws StartDateGenerationException if any error occurs so that the
     *             start date can not be generated(not used in this initial
     *             version, just for future extension)
     */
    public Date generateStartDate() throws StartDateGenerationException {
        // obtain a Calendar instance for current time
        Calendar cal = Calendar.getInstance();
        // adjust to the week according to the weekOffset
        cal.add(Calendar.WEEK_OF_YEAR, weekOffset);
        // adjust the time to the day of week according to the dayOfWeek
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        // adjust the time to the exact time according to the hour, minute,
        // second
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        // return the adjusted time
        return cal.getTime();
    }

    /**
     * <p>
     * Returns the hour component of the relative time to generate.
     * </p>
     * @return the hour component
     */
    public int getHour() {
        return this.hour;
    }

    /**
     * <p>
     * Returns the minute component of the relative time to generate.
     * </p>
     * @return the minute component
     */
    public int getMinute() {
        return this.minute;
    }

    /**
     * <p>
     * Returns the second component of the relative time to generate.
     * </p>
     * @return the second component
     */
    public int getSecond() {
        return this.second;
    }

    /**
     * <p>
     * Return the week offset value from current week of the relative time to generate.
     * </p>
     * @return the week offset from current week
     */
    public int getWeekOffset() {
        return this.weekOffset;
    }

    /**
     * <p>
     * Return the day of the week of the relative time to generate.
     * </p>
     * @return the day of the week
     */
    public int getDayOfWeek() {
        return this.dayOfWeek;
    }

    /**
     * <p>
     * Check if a given integer falls in the given range. If it is out of the given range,
     * <code>IllegalArgumentException</code> will be thrown, otherwise the integer will be returned.
     * </p>
     *
     * @param range
     *            the valid range, the first element is the min value, the second is the max value
     * @param actual
     *            the actual value
     * @param paramName
     *            the name of the param
     * @return the actual integer if it falls in the given range
     * @throws IllegalArgumentException
     *             if actual doesn't fall in the given range
     */
    private static int checkIntegerRange(int[] range, int actual, String paramName) {
        if (actual < range[0] || actual > range[1]) {
            throw new IllegalArgumentException(paramName + " should be within [" + range[0] + ", " + range[1] + "].");
        }
        return actual;
    }
}
