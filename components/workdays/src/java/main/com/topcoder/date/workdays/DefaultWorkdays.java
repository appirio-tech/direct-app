/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * <p>
 * The DefaultrWorkdays class is the default implementation of the Workdays interface. It has a parameterless
 * constructor to create an empty DefaultWorkdays instance, with default settings, and a constructor with a
 * fileName:String and a fileFormat:String. The second constructor creates a DefaultWorkdays that loads its
 * information from the file with the given fileName, of the given fileFormat. It provides methods for refreshing the
 * configuration from the configuration file and for saving the modified configuration to the configuration file.
 * </p>
 *
 * <p>
 * Version 1.0.1 (Direct Release 6 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Turned instance <code>configManager</code> field into method local variables otherwise there is a
 *     serialization issue when serializing instances of <code>com.topcoder.project.phases.Project</code> type.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0.1
 */
public class DefaultWorkdays implements Workdays {
    /**
     * <p>
     * A string constant that reprezents the properties file format.
     * </p>
     */
    public static final String PROPERTIES_FILE_FORMAT = "properties";

    /**
     * <p>
     * A string constant that represents the xml file format.
     * </p>
     */
    public static final String XML_FILE_FORMAT = "XML";

    /**
     * <p>
     * The default value of start time hours.
     * </p>
     */
    private static final int DEFAULT_START_TIME_HOURS = 9;

    /**
     * <p>
     * The default value of start time minutes.
     * </p>
     */
    private static final int DEFAULT_START_TIME_MINUTES = 0;

    /**
     * <p>
     * The default value of end time hours.
     * </p>
     */
    private static final int DEFAULT_END_TIME_HOURS = 17;

    /**
     * <p>
     * The default value of end time minutes.
     * </p>
     */
    private static final int DEFAULT_END_TIME_MINUTES = 0;

    /**
     * <p>
     * Represents the name of the start time hours property for the configuration manager.
     * </p>
     */
    private static final String START_TIME_HOURS_PROPERTY = "startTime.hours";

    /**
     * <p>
     * Represents the name of the start time minutes property for the configuration manager.
     * </p>
     */
    private static final String START_TIME_MINUTES_PROPERTY = "startTime.minutes";

    /**
     * <p>
     * Represents the name of the end time hours property for the configuration manager.
     * </p>
     */
    private static final String END_TIME_HOURS_PROPERTY = "endTime.hours";

    /**
     * <p>
     * Represents the name of the end time minutes property for the configuration manager.
     * </p>
     */
    private static final String END_TIME_MINUTES_PROPERTY = "endTime.minutes";

    /**
     * <p>
     * Represents the name of the property for the configuration manager that tells if Saturday is to be considered a
     * normal workday or not.
     * </p>
     */
    private static final String IS_SATURDAY_WORKDAY_PROPERTY = "isSaturdayWorkday";

    /**
     * <p>
     * Represents the name of the property for the configuration manager that tells if Sunday is to be considered a
     * normal workday or not.
     * </p>
     */
    private static final String IS_SUNDAY_WORKDAY_PROPERTY = "isSundayWorkday";

    /**
     * <p>
     * Represents the name of the property for the configuration manager that contains a String array of the non-work
     * days.
     * </p>
     */
    private static final String NON_WORKDAYS_PROPERTY = "nonWorkdays";

    /**
     * <p>
     * Represents the name of the locale language property for the configuration manager.
     * </p>
     */
    private static final String LOCALE_LANGUAGE_PROPERTY = "locale.language";

    /**
     * <p>
     * Represents the name of the locale country property for the configuration manager.
     * </p>
     */
    private static final String LOCALE_COUNTRY_PROPERTY = "locale.country";

    /**
     * <p>
     * Represents the name of the locale variant property for the configuration manager.
     * </p>
     */
    private static final String LOCALE_VARIANT_PROPERTY = "locale.variant";

    /**
     * <p>
     * Represents the name of the date style property for the configuration manager. It can have one of the following
     * values: &quot;SHORT&quot;, &quot;MEDIUM&quot;, &quot;LONG&quot; or &quot;FULL&quot;.
     * </p>
     */
    private static final String DATE_STYLE_PROPERTY = "dateStyle";

    /**
     * <p>
     * Represents the namespace for the properties in the configuration manager for this instance of
     * ConfigManagerWorkdays.
     * </p>
     */
    private String namespace = null;

    /**
     * <p>
     * Represents the name of the configuration file.
     * </p>
     */
    private String fileName = null;

    /**
     * <p>
     * Represents the file format of the configuration file. It can have the value of one of the two file format
     * constants: XML_FILE_FORMAT, PROPERTIES_FILE_FORMAT.
     * </p>
     *
     * <p>
     * The fileFormat defaults to XML_FILE_FORMAT.
     * </p>
     */
    private String fileFormat = XML_FILE_FORMAT;

    /**
     * <p>
     * Represents a sorted set of non-work days, that are not Saturdays or Sundays (these two kind of non-work days are
     * stored in nonWorkSaturdayDays and nonWorkSundayDays sorted sets).
     * </p>
     *
     * <p>
     * The fact that this Set is sorted is used in the private method getNonWorkdaysCount().
     * </p>
     */
    private SortedSet nonWorkDays = new TreeSet();

    /**
     * <p>
     * Represents a sorted set of non-work Saturday days.
     * </p>
     *
     * <p>
     * The fact that this Set is sorted is used in the private method getNonWorkdaysCount().
     * </p>
     */
    private SortedSet nonWorkSaturdayDays = new TreeSet();

    /**
     * <p>
     * Represents a sorted set of non-work Sunday days.
     * </p>
     *
     * <p>
     * The fact that this Set is sorted is used in the private method getNonWorkdaysCount().
     * </p>
     */
    private SortedSet nonWorkSundayDays = new TreeSet();

    /**
     * <p>
     * This boolean field tells whether Saturdays are to be considered as a normal workday or not.
     * </p>
     */
    private boolean isSaturdayWorkday = false;

    /**
     * <p>
     * This boolean field tells whether Sundays are to be considered as a normal workday or not.
     * </p>
     */
    private boolean isSundayWorkday = false;

    /**
     * <p>
     * Represents the workday start time hours.
     * </p>
     */
    private int startTimeHours = DEFAULT_START_TIME_HOURS;

    /**
     * <p>
     * Represents the workday start time minutes.
     * </p>
     */
    private int startTimeMinutes = DEFAULT_START_TIME_MINUTES;

    /**
     * <p>
     * Represents the workday end time hours.
     * </p>
     */
    private int endTimeHours = DEFAULT_END_TIME_HOURS;

    /**
     * <p>
     * Represents the workday end time minutes.
     * </p>
     */
    private int endTimeMinutes = DEFAULT_END_TIME_MINUTES;

    /**
     * <p>
     * Represents the locale according to which date strings are parsed.
     * </p>
     */
    private Locale locale = Locale.getDefault();

    /**
     * <p>
     * Represents the date format used to parse date strings.
     * </p>
     */
    private DateFormat dateFormat = DateFormat.getDateInstance();

    /**
     * <p>
     * Represents the configuration manager to parse the configuration file.
     * </p>
     */
//    private ConfigManager configManager = ConfigManager.getInstance();

    /**
     * <p>
     * Creates an empty workdays schedule with default settings.
     * </p>
     */
    public DefaultWorkdays() {
    }

    /**
     * <p>
     * Creates a DefaultWorkdays that loads its information from the configuration file with the given fileName and
     * with the given fileFormat format.
     * </p>
     *
     * <p>
     * The file format can be one of the XML_FILE_FORMAT and PROPERTIES_FILE_FORMAT constants.
     * </p>
     *
     * <p>
     * The configuration file can look like this:
     * </p>
     *
     * <p>
     * <strong>properties </strong>
     * </p>
     *
     * <p>
     * <em>ListDelimiter = %</em>
     * </p>
     *
     * <p>
     * <em>startTime.hours = 8</em>
     * </p>
     *
     * <p>
     * <em>startTime.minutes = 0</em>
     * </p>
     *
     * <p>
     * <em>endTime.hours = 16</em>
     * </p>
     *
     * <p>
     * <em>endTime.minutes = 30</em>
     * </p>
     *
     * <p>
     * <em>isSaturdayWorkday = true</em>
     * </p>
     *
     * <p>
     * <em>isSundayWorkday = false</em>
     * </p>
     *
     * <p>
     * <em>locale.language = US</em>
     * </p>
     *
     * <p>
     * <em>locale.country = us</em>
     * </p>
     *
     * <p>
     * <em>locale.variant = MAC</em>
     * </p>
     *
     * <p>
     * <em>dateStyle = SHORT</em>
     * </p>
     *
     * <p>
     * <em>nonWorkdays = 3.23.2004%5.6.2004%7.2.2004%4.12.2004%6.18.2004</em>
     * </p>
     *
     * <p></p>
     *
     * <p>
     * <strong>XML: </strong>
     * </p>
     *
     * <p>
     * <em>&lt;CMConfig&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;Property name=&rdquo;startTime.hours&rdquo;&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;8&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;/Property&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;Property name=&rdquo;startTime.minutes&rdquo;&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;0&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;/Property&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;Property name=&rdquo;endTime.hours&rdquo;&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;16&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;/Property&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;Property name=&rdquo;endTime.minutes&rdquo;&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;30&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;/Property&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;Property name=&rdquo;isSaturdayWorkday&rdquo;&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;true&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;/Property&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;Property name=&rdquo;isSundayWorkday&rdquo;&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;false&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;/Property&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;Property name=&rdquo;locale.language&rdquo;&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;US&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;/Property&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;Property name=&rdquo;locale.country&rdquo;&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;us&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;/Property&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;Property name=&rdquo;locale.variant&rdquo;&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;MAC&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;/Property&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;Property name=&rdquo;dateStyle&rdquo;&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;SHORT&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;/Property&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;Property name=&rdquo;nonWorkdays&rdquo;&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;3.23.2004&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;5.6.2004&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;7.2.2004&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;4.12.2004&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp;&nbsp;&nbsp; &lt;Value&gt;6.18.2004&lt;/Value&gt;</em>
     * </p>
     *
     * <p>
     * <em>&nbsp; &lt;/Property&gt;</em>
     * </p>
     *
     * <p>
     * <em>&lt;/CMConfig&gt;</em>
     * </p>
     *
     * @param fileName the name of the configuration file
     * @param fileFormat the format of the configuration file
     *
     * @throws ConfigurationFileException - if something goes wrong in the process of loading the configuration file
     * @throws NullPointerException - if the arguments are null
     * @throws IllegalArgumentException - if the fileName is empty or the fileFormat in not one of the
     *         PROPERTIES_FILE_FORMAT and XML_FILE_FORMAT constants
     */
    public DefaultWorkdays(String fileName, String fileFormat)
        throws ConfigurationFileException {
        if (fileName == null) {
            throw new NullPointerException("Parameter fileName is null");
        }

        if (fileFormat == null) {
            throw new NullPointerException("Parameter fileFormat is null");
        }

        if (fileName.trim().length() == 0) {
            throw new IllegalArgumentException("Parameter fileName is empty");
        }

        if (!fileFormat.equals(XML_FILE_FORMAT) && !fileFormat.equals(PROPERTIES_FILE_FORMAT)) {
            throw new IllegalArgumentException(
                "Parameter fileFormat is not one of the PROPERTIES_FILE_FORMAT and XML_FILE_FORMAT constants");
        }

        this.fileName = fileName;
        this.fileFormat = fileFormat;

        // generate namespace from fileName
        this.namespace = this.getNamespace(fileName);

        // most initialization is implemented in refresh method
        this.refresh();
    }

    /**
     * <p>
     * Tries to reload the information from the configuration file. Before it reloads the information, it resets this
     * DefaultWorkdays instance to the default configuration and clears the set of non workdays.
     * </p>
     *
     * @throws ConfigurationFileException if something goes wrong in the process of reloading the configuration file
     * @throws IllegalArgumentException if no configuration file specified
     */
    public void refresh() throws ConfigurationFileException {
        // resets this DefaultWorkdays to the default configuration
        this.startTimeHours = DEFAULT_START_TIME_HOURS;
        this.startTimeMinutes = DEFAULT_START_TIME_MINUTES;
        this.endTimeHours = DEFAULT_END_TIME_HOURS;
        this.endTimeMinutes = DEFAULT_END_TIME_MINUTES;
        this.locale = Locale.getDefault();
        this.dateFormat = DateFormat.getDateInstance();
        this.isSaturdayWorkday = false;
        this.isSundayWorkday = false;

        // clear the sets of non workdays
        this.nonWorkDays.clear();
        this.nonWorkSaturdayDays.clear();
        this.nonWorkSundayDays.clear();

        if (this.fileName == null) {
            // there is no configuration file
            throw new IllegalArgumentException("No configuration file specified");
        }

        // reload configuration from the configuration file
        ConfigManager configManager = ConfigManager.getInstance();
        try {
            if (configManager.existsNamespace(this.namespace)) {
                configManager.refresh(this.namespace);
            } else {
                // add the namespace specified by the file to configuration manager
                if (fileFormat.equals(XML_FILE_FORMAT)) {
                    configManager.add(this.namespace, this.fileName, ConfigManager.CONFIG_XML_FORMAT);
                } else {
                    configManager.add(this.namespace, this.fileName, ConfigManager.CONFIG_PROPERTIES_FORMAT);
                }
            }
        } catch (ConfigManagerException cme) {
            throw new ConfigurationFileException("Errors occur when loading the namespace from the file", cme);
        }

        try {
            // initialize all the properties by configManager
            this.setTimeByConfigManager();
            this.isSaturdayWorkday = this.getBoolValueOfProperty(DefaultWorkdays.IS_SATURDAY_WORKDAY_PROPERTY);
            this.isSundayWorkday = this.getBoolValueOfProperty(DefaultWorkdays.IS_SUNDAY_WORKDAY_PROPERTY);
            this.setLocaleByConfigManager();
            this.setDateFormatByConfigManager();

            // load all nonworkdays from configManager
            String[] nonWorkdays = configManager.getStringArray(this.namespace,
                    DefaultWorkdays.NON_WORKDAYS_PROPERTY);

            if (nonWorkdays != null) {
                for (int i = 0; i < nonWorkdays.length; i++) {
                    this.addNonWorkday(this.dateFormat.parse(nonWorkdays[i].trim()));
                }
            }
        } catch (UnknownNamespaceException une) {
            // ignore here, never occurs as we add the namespace to configuration manager.
        } catch (ParseException pe) {
            throw new ConfigurationFileException(
                "One of the nonworkdays in configuration file is not in required format", pe);
        } catch (IllegalArgumentException iae) {
            throw new ConfigurationFileException("Some properties are not in required format", iae);
        }
    }

    /**
     * <p>
     * Try to save the current configuration to the config file. It replaces the namespace properties in the
     * configuartion manager with the appropriated values and tells the configuration manager to commit the changes.
     * </p>
     *
     * @throws ConfigurationFileException if something goes wrong in the process of saving to the configuration file
     * @throws IllegalArgumentException if no configuration file specified
     */
    public void save() throws ConfigurationFileException {
        if (this.fileName == null) {
            // there is no configuration file
            throw new IllegalArgumentException("No configuration file specified");
        }

        try {
            ConfigManager configManager = ConfigManager.getInstance();
            if (!configManager.existsNamespace(this.namespace)) {
                // if the file is not exist, create a empty file
                File file = new File(this.fileName);
                file.createNewFile();

                // the namespace is not in configManager
                // add the namespace specified by the file to configuration manager
                if (this.fileFormat.equals(XML_FILE_FORMAT)) {
                    // initialize the xml file
                    FileWriter writer = new FileWriter(file);
                    writer.write("<CMConfig></CMConfig>");
                    writer.close();

                    configManager.add(this.namespace, this.fileName, ConfigManager.CONFIG_XML_FORMAT);
                } else {
                    configManager.add(this.namespace, this.fileName, ConfigManager.CONFIG_PROPERTIES_FORMAT);
                }
            }

            configManager.createTemporaryProperties(this.namespace);

            // set properties to configManager
            // start time hours
            configManager.setProperty(this.namespace, DefaultWorkdays.START_TIME_HOURS_PROPERTY,
                String.valueOf(this.startTimeHours));

            // start time minutes
            configManager.setProperty(this.namespace, DefaultWorkdays.START_TIME_MINUTES_PROPERTY,
                String.valueOf(this.startTimeMinutes));

            // end time hours
            configManager.setProperty(this.namespace, DefaultWorkdays.END_TIME_HOURS_PROPERTY,
                String.valueOf(this.endTimeHours));

            // end time minutes
            configManager.setProperty(this.namespace, DefaultWorkdays.END_TIME_MINUTES_PROPERTY,
                String.valueOf(this.endTimeMinutes));

            // isSaturdayWorkday
            configManager.setProperty(this.namespace, DefaultWorkdays.IS_SATURDAY_WORKDAY_PROPERTY,
                String.valueOf(this.isSaturdayWorkday));

            // isSundayWorkday
            configManager.setProperty(this.namespace, DefaultWorkdays.IS_SUNDAY_WORKDAY_PROPERTY,
                String.valueOf(this.isSundayWorkday));

            // locale language
            configManager.setProperty(this.namespace, DefaultWorkdays.LOCALE_LANGUAGE_PROPERTY,
                this.locale.getLanguage());

            // locale country
            configManager.setProperty(this.namespace, DefaultWorkdays.LOCALE_COUNTRY_PROPERTY,
                this.locale.getCountry());

            // locale variant
            configManager.setProperty(this.namespace, DefaultWorkdays.LOCALE_VARIANT_PROPERTY,
                this.locale.getVariant());

            // non_workdays
            List nonWorkdays = new ArrayList();

            for (Iterator it = this.nonWorkDays.iterator(); it.hasNext();) {
                nonWorkdays.add(this.dateFormat.format((Date) it.next()));
            }

            for (Iterator it = this.nonWorkSaturdayDays.iterator(); it.hasNext();) {
                nonWorkdays.add(this.dateFormat.format((Date) it.next()));
            }

            for (Iterator it = this.nonWorkSundayDays.iterator(); it.hasNext();) {
                nonWorkdays.add(this.dateFormat.format((Date) it.next()));
            }

            if (nonWorkdays.size() > 0) {
                configManager.setProperty(this.namespace, DefaultWorkdays.NON_WORKDAYS_PROPERTY,
                    (String[]) nonWorkdays.toArray(new String[0]));
            } else {
                configManager.removeProperty(this.namespace, DefaultWorkdays.NON_WORKDAYS_PROPERTY);
            }

            // commit
            // use namespace name as userid to avoid the same configuration file modified by different
            // DefaultWorkdays instance at same time.
            configManager.commit(this.namespace, this.namespace);
        } catch (ConfigManagerException cme) {
            throw new ConfigurationFileException(
                "Something goes wrong in the process of saving to the configuration file", cme);
        } catch (IOException ioe) {
            throw new ConfigurationFileException("Can't create file with the name " + this.fileName, ioe);
        }
    }

    /**
     * <p>
     * Adds a non-workday to the list of non-work days.
     * </p>
     *
     * <p>
     * If the date is a Saturday it is added to nonWorkSaturdayDays set, if it is a Sunday it is added to
     * nonWorkSundayDays set and otherwise it is added to nonWorkDays set.
     * </p>
     *
     * <p>
     * This difference is made to ease the calculations for the add(x,x,x) method: there will be no need to check if a
     * non workday is Saturday, if isSaturdayWorkday is false; this check had to be made so a Saturday wouldn't be
     * considered twice as a non-work day.
     * </p>
     *
     * @param nonWorkday the date to add as a non work day
     *
     * @throws NullPointerException if nonWorkDay is null
     */
    public void addNonWorkday(Date nonWorkday) {
        if (nonWorkday == null) {
            throw new NullPointerException("Parameter nonWorkday is null");
        }

        Calendar nonWorkdayCal = Calendar.getInstance(this.locale);
        nonWorkdayCal.setTime(nonWorkday);

        // cut the part of nonWorkday's hour, minute , second and millisecond
        nonWorkdayCal.set(Calendar.HOUR_OF_DAY, 0);
        nonWorkdayCal.set(Calendar.MINUTE, 0);
        nonWorkdayCal.set(Calendar.SECOND, 0);
        nonWorkdayCal.set(Calendar.MILLISECOND, 0);

        if (nonWorkdayCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            // this nonWorkday is saturday
            this.nonWorkSaturdayDays.add(nonWorkdayCal.getTime());
        } else if (nonWorkdayCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            // this nonWorkday is sunday
            this.nonWorkSundayDays.add(nonWorkdayCal.getTime());
        } else {
            this.nonWorkDays.add(nonWorkdayCal.getTime());
        }
    }

    /**
     * <p>
     * Removes a non-workday from the list of non-work days
     * </p>
     *
     * <p>
     * If the date is a Saturday it is removed from nonWorkSaturdayDays set, if it is a Sunday it is removed from
     * nonWorkSundayDays set and otherwise it is removed from nonWorkDays set.
     * </p>
     *
     * @param nonWorkday the date to remove from the list
     *
     * @throws NullPointerException is thrown if nonWorkDay is null
     * @throws IllegalArgumentException is thrown if nonWorkDay does not exist
     */
    public void removeNonWorkday(Date nonWorkday) {
        if (nonWorkday == null) {
            throw new NullPointerException("Parameter nonWorkday is null");
        }

        // cut the part of nonWorkday's hour, minute , second and millisecond
        Calendar nonWorkdayCal = Calendar.getInstance(this.locale);
        nonWorkdayCal.setTime(nonWorkday);
        nonWorkdayCal.set(Calendar.HOUR_OF_DAY, 0);
        nonWorkdayCal.set(Calendar.MINUTE, 0);
        nonWorkdayCal.set(Calendar.SECOND, 0);
        nonWorkdayCal.set(Calendar.MILLISECOND, 0);

        boolean exist = this.nonWorkDays.remove(nonWorkday);

        if (!exist) {
            // not in nonWorkDays set
            exist = this.nonWorkSaturdayDays.remove(nonWorkday);
        }

        if (!exist) {
            // not in nonWorkDays and nonWorkSaturdayDays set
            exist = this.nonWorkSundayDays.remove(nonWorkday);
        }

        if (!exist) {
            // not exist
            throw new IllegalArgumentException("nonWorkday does not exist");
        }
    }

    /**
     * <p>
     * Returns a Set with all non-workdays. The resulting Set is a reunion of the 3 sorted sets for non workdays
     * </p>
     *
     * @return a Set with all non-workdays
     */
    public Set getNonWorkdays() {
        Set allNonWorkDays = new TreeSet();
        allNonWorkDays.addAll(this.nonWorkDays);
        allNonWorkDays.addAll(this.nonWorkSaturdayDays);
        allNonWorkDays.addAll(this.nonWorkSundayDays);

        return allNonWorkDays;
    }

    /**
     * <p>
     * Clears the non-workdays. Emptyies the sorted sets.
     * </p>
     */
    public void clearNonWorkdays() {
        this.nonWorkDays.clear();
        this.nonWorkSaturdayDays.clear();
        this.nonWorkSundayDays.clear();
    }

    /**
     * <p>
     * Sets whether or not Saturday is to be considered a work day
     * </p>
     *
     * @param isSaturdayWorkday <code>true</code> if Saturday is to be considered a workday
     */
    public void setSaturdayWorkday(boolean isSaturdayWorkday) {
        this.isSaturdayWorkday = isSaturdayWorkday;
    }

    /**
     * <p>
     * Returns whether or not Saturday is considered a workday.
     * </p>
     *
     * @return <code>true</code> if Saturday is considered a workday.
     */
    public boolean isSaturdayWorkday() {
        return this.isSaturdayWorkday;
    }

    /**
     * <p>
     * Sets whether or not Sunday is to be considered a work day
     * </p>
     *
     * @param isSundayWorkday <code>true</code> if Sunday is to be considered a workday
     */
    public void setSundayWorkday(boolean isSundayWorkday) {
        this.isSundayWorkday = isSundayWorkday;
    }

    /**
     * <p>
     * Returns whether or not Sunday is considered a workday.
     * </p>
     *
     * @return <code>true</code> if Sunday is considered a workday.
     */
    public boolean isSundayWorkday() {
        return this.isSundayWorkday;
    }

    /**
     * <p>
     * Sets the hours of the workday start time.&nbsp; This is to be in 24 hour mode.
     * </p>
     *
     * @param startTimeHours the hours of the workday start time (24 hour mode)
     *
     * @throws IllegalArgumentException if startTimeHours is not a valid hour
     */
    public void setWorkdayStartTimeHours(int startTimeHours) {
        if ((startTimeHours < 0) || (startTimeHours > 24)) {
            throw new IllegalArgumentException("Parameter startTimeHours is not a valid hour");
        }

        this.startTimeHours = startTimeHours;
        this.timeStateValidation();
    }

    /**
     * <p>
     * Returns the hours of the workday start time, in 24 hour mode.
     * </p>
     *
     * @return the hours of the workday start time
     */
    public int getWorkdayStartTimeHours() {
        return this.startTimeHours;
    }

    /**
     * <p>
     * Sets the minutes of the workday start time.
     * </p>
     *
     * @param startTimeMinutes the minutes of the workday start time
     *
     * @throws IllegalArgumentException if startTimeMinutes is not a valid minute
     */
    public void setWorkdayStartTimeMinutes(int startTimeMinutes) {
        if ((startTimeMinutes < 0) || (startTimeMinutes >= 60)) {
            throw new IllegalArgumentException("Parameter startTimeMinutes is not a valid hour");
        }

        this.startTimeMinutes = startTimeMinutes;
        this.timeStateValidation();
    }

    /**
     * <p>
     * Returns the minutes of the workday start time.
     * </p>
     *
     * @return the minutes of the workday start time
     */
    public int getWorkdayStartTimeMinutes() {
        return this.startTimeMinutes;
    }

    /**
     * <p>
     * Sets the hours of the workday end time.&nbsp; This is to be in 24 hour mode.
     * </p>
     *
     * @param endTimeHours the hours of the workday end time (24 hour mode).
     *
     * @throws IllegalArgumentException if endTimeHours is not a valid hour
     */
    public void setWorkdayEndTimeHours(int endTimeHours) {
        if ((endTimeHours < 0) || (endTimeHours > 24)) {
            throw new IllegalArgumentException("Parameter endTimeHours is not a valid hour");
        }

        this.endTimeHours = endTimeHours;
        this.timeStateValidation();
    }

    /**
     * <p>
     * Returns the hours of the workday end time, in 24 hour mode.
     * </p>
     *
     * @return the hours of the workday end time
     */
   public int getWorkdayEndTimeHours() {
        return this.endTimeHours;
    }

    /**
     * <p>
     * Sets the minutes of the workday end time.
     * </p>
     *
     * @param endTimeMinutes the minutes of the workday end time
     *
     * @throws IllegalArgumentException if endTimeMinutes is not a valid minute
     */
    public void setWorkdayEndTimeMinutes(int endTimeMinutes) {
        if ((endTimeMinutes < 0) || (endTimeMinutes >= 60)) {
            throw new IllegalArgumentException("Parameter endTimeMinutes is not a valid hour");
        }

        this.endTimeMinutes = endTimeMinutes;
        this.timeStateValidation();
    }

    /**
     * <p>
     * Returns the minutes of the workday end time.
     * </p>
     *
     * @return the minutes of the workday end time
     */
    public int getWorkdayEndTimeMinutes() {
        return this.endTimeMinutes;
    }

    /**
     * <p>
     * Method to add a certain amount of time to a Date to calculate end date that it would take to complete.
     * </p>
     *
     * <p>
     * There are there types of unit time, minutes, hours, and days.  To implement this method, binary search algorithm
     * can be used.
     * </p>
     *
     * @param startDate the date to perform the addition to
     * @param unitOfTime the unit of time to add (minutes, hours, days)
     * @param amount the amount of time to add
     *
     * @return the Date result of adding the amount of time to the startDate taking into consideration the workdays
     *         definition.
     *
     * @throws NullPointerException if startDate or unitOfTime is null
     * @throws IllegalArgumentException if amount parameter is negative or the start/end time set incorrectly
     */
    public Date add(Date startDate, WorkdaysUnitOfTime unitOfTime, int amount) {
        if (startDate == null) {
            throw new NullPointerException("Parameter startDate is null");
        }

        if (unitOfTime == null) {
            throw new NullPointerException("Parameter unitOfTime is null");
        }

        if (amount < 0) {
            throw new IllegalArgumentException("Parameter amount is negative");
        }

        // validate the start/end time.
        this.timeStateValidation();

        if (amount == 0) {
            return startDate;
        }

        Calendar startCal = Calendar.getInstance(this.locale);
        startCal.setTime(startDate);

        // ignore second and millisecond field of the startDate, otherwise, maybe the answer
        // is "2005.01.04 09:00:00 100" insteed of "2005.01.03 17:00:00"
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        // get the begin and end time of the startDate
        Calendar dayBegin = (Calendar) startCal.clone();

        // get the begin and end time of the startDate
        Calendar dayEnd = (Calendar) startCal.clone();
        dayBegin.set(Calendar.HOUR_OF_DAY, this.startTimeHours);
        dayBegin.set(Calendar.MINUTE, this.startTimeMinutes);

        dayEnd.set(Calendar.HOUR_OF_DAY, this.endTimeHours);
        dayEnd.set(Calendar.MINUTE, this.endTimeMinutes);

        // time in detail as milliseconds just for convenience
        long workdayInMilliSeconds = (long) this.getWorkdayDurationInMinutes() * 60 * 1000;

        long timeInMilliSeconds = (long) this.getAmountInMinutes(unitOfTime, amount) * 60 * 1000;

        if (!this.isNonWorkday(startCal)) {
            // the start date is a workday
            if (startCal.after(dayEnd)) {
                timeInMilliSeconds += workdayInMilliSeconds;
            } else if (startCal.after(dayBegin)) {
                timeInMilliSeconds += (startCal.getTime().getTime() - dayBegin.getTime().getTime());
            }
        }

        // total days to add
        long daysToAdd = ((timeInMilliSeconds + workdayInMilliSeconds) - 1) / workdayInMilliSeconds;

        // minimal days to add
        long min = daysToAdd;

        // maximal days to add
        // (maximal - nonWorkDays.size) * (5 / 7) >= daysToadd
        long max = (daysToAdd * 7) / 5
                 + this.nonWorkDays.size()
                 + this.nonWorkSaturdayDays.size()
                 + this.nonWorkSundayDays.size() + 7;

        // cut the part of startCal's hour, minute and second
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);

        // binary search
        Date startDay = startCal.getTime();
        Date endDay = new Date();

        for (long mid = (min + max) / 2; min <= max; mid = (min + max) / 2) {
            endDay.setTime((startDay.getTime() + (mid * 24 * 60 * 60 * 1000)) - 60000);

            int workdaysCount = this.getWorkdaysCount(startDay, endDay);

            if (workdaysCount >= daysToAdd) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }

        long disToBegin = (((timeInMilliSeconds + workdayInMilliSeconds) - 1) % workdayInMilliSeconds) + 1;
        endDay.setTime(dayBegin.getTime().getTime() + (max * 24 * 60 * 60 * 1000) + disToBegin);

        return endDay;
    }

    /**
     * <p>
     * Sets the name of the configuration file. The information from the new file it's not loaded automaticaly: the
     * file name is only stored. The user applicatioc has to call refresh() to load the data from the new file.
     * </p>
     *
     * @param fileName the new file name
     *
     * @throws NullPointerException if the fileName is null
     * @throws IllegalArgumentException if the fileName is empty
     */
    public void setFileName(String fileName) {
        if (fileName == null) {
            throw new NullPointerException("Parameter fileName is null");
        }

        if (fileName.trim().length() == 0) {
            throw new IllegalArgumentException("Parameter fileName is emtpy");
        }

        this.fileName = fileName;


        // this is a new file, remove the old namespace from configManager
        ConfigManager configManager = ConfigManager.getInstance();
        if ((this.namespace != null) && configManager.existsNamespace(this.namespace)) {
            try {
                configManager.removeNamespace(this.namespace);
            } catch (UnknownNamespaceException une) {
                // ignore here
            }
        }

        this.namespace = this.getNamespace(fileName);

    }

    /**
     * <p>
     * Returns the name of the configuration file.
     * </p>
     *
     * @return the name of the configuration file
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * <p>
     * Returns the file format of the configuration file.
     * </p>
     *
     * @return the file format of the configuration file
     */
    public String getFileFormat() {
        return this.fileFormat;
    }

    /**
     * <p>
     * Sets the file format of the configuration file. It must be one of the two constants: XML_FILE_FORMAT or
     * PROPERTIES_FILE_FORMAT.
     * </p>
     *
     * @param fileFormat the new file format
     *
     * @throws NullPointerException - if the fileFormat is null
     * @throws IllegalArgumentException - if the argument is not one of the two file format constants
     */
    public void setFileFormat(String fileFormat) {
        if (fileFormat == null) {
            throw new NullPointerException("Parameter fileFormat is null");
        }

        if (!fileFormat.equals(PROPERTIES_FILE_FORMAT) && !fileFormat.equals(XML_FILE_FORMAT)) {
            throw new IllegalArgumentException("Parameter fileFormat is not one of the two file format constants");
        }

        this.fileFormat = fileFormat;
    }

    /**
     * <p>
     * Calculates the amount of time from the unit of time specified by the unitOfTime, to minutes. This method is used
     * by add(x,x,x) method.
     * </p>
     *
     * @param unitOfTime the unit of time the amount is expressed in (minutes, hours, days)
     * @param amount the amount of time
     *
     * @return the amount of time calculated in minutes
     */
    private int getAmountInMinutes(WorkdaysUnitOfTime unitOfTime, int amount) {
        if (unitOfTime.equals(WorkdaysUnitOfTime.MINUTES)) {
            return amount;
        }

        if (unitOfTime.equals(WorkdaysUnitOfTime.HOURS)) {
            return amount * 60;
        }

        return this.getWorkdayDurationInMinutes() * amount;
    }

    /**
     * <p>
     * Calculates the duration of a normal workday in minutes. This method is used by add(x,x,x) method.
     * </p>
     *
     * @return the duration of a normal workday in minutes
     */
    private int getWorkdayDurationInMinutes() {
        return ((this.endTimeHours * 60) + this.endTimeMinutes) - (this.startTimeHours * 60) - this.startTimeMinutes;
    }

    /**
     * <p>
     * Calculates the number of workdays between the date represented by startCal (inclusive) and the date represented
     * by endCal (exclusive).
     * </p>
     *
     * <p>
     * it makes use of the fact that the non-workdays sets are sorted; for example: int
     * count=nonWorkDays.subSet(startDate,endDate).size().
     * </p>
     *
     * <p>
     * This method uses the nonWorkDays, nonWorkSaturdayDays and nonWorkSundayDays sorted sets and the boolean fields
     * isSaturdayWorkday and isSundayWorkday to perform its calculations.
     * </p>
     *
     * <p>
     * This method is used by the add(x,x,x) method.
     * </p>
     *
     * @param startDay the start date (inclusive)
     * @param endDay the end date (exclusive)
     *
     * @return the number of workdays between [ startDay , endDay )
     */
    private int getWorkdaysCount(Date startDay, Date endDay) {
        // initialize startCal with startDay, used to calcutlate the day of week
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDay);

        // total days between startDay and endDay
        int total = (int) ((endDay.getTime() - startDay.getTime() + 60000) / (24 * 60 * 60 * 1000));

        // non-workdays in nonWorkdays set, exclude Saturdays and Sundays
        int count = this.nonWorkDays.subSet(startDay, endDay).size();

        if (this.isSaturdayWorkday()) {
            // Saturday is workday, add nonWordays of Saturday to the count
            count += this.nonWorkSaturdayDays.subSet(startDay, endDay).size();
        } else {
            // Saturday isn't workday
            // in disToSaturday days, it is Saturday
            int disToSaturday = (Calendar.SATURDAY - startCal.get(Calendar.DAY_OF_WEEK) + 7) % 7;

            // add Saturdays between startDay and endDay to count
            count += ((total - disToSaturday + 6) / 7);
        }

        if (this.isSundayWorkday()) {
            // Sunday is workday, add nonWorkdays of Sunday to count
            count += this.nonWorkSundayDays.subSet(startDay, endDay).size();
        } else {
            // sunday isn't workday
            // in disToSunday days, it is Sunday
            int disToSunday = (Calendar.SUNDAY - startCal.get(Calendar.DAY_OF_WEEK) + 7) % 7;

            // add Sundays between startDay and endDay to count
            count += ((total - disToSunday + 6) / 7);
        }

        // workdays is the total days minus the non-workdays
        return total - count;
    }

    /**
     * <p>
     * Checks if the date represented by cal is a non-work day.
     * </p>
     *
     * @param cal the date to be checked
     *
     * @return <code>true</code> if cal represents a non-work day
     */
    private boolean isNonWorkday(Calendar cal) {
        if (!this.isSaturdayWorkday() && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
            return true;
        }

        if (!this.isSundayWorkday() && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
            return true;
        }

        if (this.nonWorkDays.contains(cal.getTime())) {
            return true;
        }

        if (this.isSaturdayWorkday() && this.nonWorkSaturdayDays.contains(cal.getTime())) {
            return true;
        }

        if (this.isSundayWorkday() && this.nonWorkSundayDays.contains(cal.getTime())) {
            return true;
        }

        return false;
    }

    /**
     * <p>
     * Creates a namespace from the fileName given. It replaces the delimiters ('\', '/') with '.'. For example:
     * </p>
     *
     * <p>
     * fileName = &quot;./files/sample.config&quot; ==&gt; namespace = &quot;files.sample.config&quot;
     * </p>
     *
     * <p>
     * fileName = &quot;c:\configfiles\workdays\config.xml&quot; ==&gt; namespace =
     * &quot;configfiles.workdays.config.xml&quot;
     * </p>
     *
     * @param fileName the name of a file
     *
     * @return the namespace string produced from the fileName
     */
    private String getNamespace(String fileName) {
        // FileName will never be null, as we check it before
        if ((fileName.length() > 1) && (fileName.charAt(1) == ':')) {
            fileName = fileName.substring(2);
        }

        if (fileName.startsWith("\\") || fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }

        StringBuffer answer = new StringBuffer();
        fileName = fileName.replace('\\', '.').replace('/', '.');

        // if the character before the current position is '.', the dotFlag is true.
        // the following code replace multiple dots with only one, the leeding dots always to be removed.
        boolean dotFlag = true;
        for (int i = 0; i < fileName.length(); i++) {
            if (fileName.charAt(i) == '.') {
                if (!dotFlag) {
                    answer.append('.');
                    dotFlag = true;
                }
            } else {
                answer.append(fileName.charAt(i));
                dotFlag = false;
            }
        }
        return answer.toString();
    }

    /**
     * <p>
     * Set the parameters startTimeHours, startTimeMinutes, endTimeHours, endTimeMinutes with corresponding properties
     * in configuration manager. Then validate them not exceed time limit and make sure the end time is behind the
     * start time.
     * </p>
     *
     * @throws IllegalArgumentException if any parameter of them is null or exceeded time limit or the  end time is
     *         before the start time.
     */
    private void setTimeByConfigManager() {
        // Set the parameters startTimeHours, startTimeMinutes, endTimeHours, endTimeMinutes
        this.startTimeHours = this.getIntValueOfProperty(DefaultWorkdays.START_TIME_HOURS_PROPERTY, 0, 24);
        this.startTimeMinutes = this.getIntValueOfProperty(DefaultWorkdays.START_TIME_MINUTES_PROPERTY, 0, 59);
        this.endTimeHours = this.getIntValueOfProperty(DefaultWorkdays.END_TIME_HOURS_PROPERTY, 0, 24);
        this.endTimeMinutes = this.getIntValueOfProperty(DefaultWorkdays.END_TIME_MINUTES_PROPERTY, 0, 59);

        // make sure the end time is after the start time
        this.timeStateValidation();
    }

    /**
     * <p>
     * Set parameter locale by properties locale.language, locale.country and locale.variant in configuration manager.
     * If these properties are not specified, set the locale as the default.
     * </p>
     */
    private void setLocaleByConfigManager() {
        try {
            // locale language in configuration file
            ConfigManager configManager = ConfigManager.getInstance();
            String language = configManager.getString(this.namespace, DefaultWorkdays.LOCALE_LANGUAGE_PROPERTY);

            // locale country in configuration file
            String country = configManager.getString(this.namespace, DefaultWorkdays.LOCALE_COUNTRY_PROPERTY);

            // locale variant in configuration file
            String variant = configManager.getString(this.namespace, DefaultWorkdays.LOCALE_VARIANT_PROPERTY);

            // because new Locale(language) is not defined in jre1.3, if the country is not specified,
            // default locale will be used.
            if ((language != null) && (country != null) && (variant != null)) {
                // all the three properties are specified
                this.locale = new Locale(language, country, variant);
            } else if ((language != null) && (country != null)) {
                // only language and country are specified
                this.locale = new Locale(language, country);
            }
        } catch (UnknownNamespaceException une) {
            // ignore here, never occurs as we add the namespace to configuration manager.
        }
    }

    /**
     * <p>
     * Set parameter dateFormat by property dateStyle in configuration manager. If this property is not specified, set
     * the dateFormat as the default dateFormat of the locale.
     * </p>
     */
    private void setDateFormatByConfigManager() {
        try {
            // dateStyle in configuration file
            ConfigManager configManager = ConfigManager.getInstance();
            String style = configManager.getString(this.namespace, DefaultWorkdays.DATE_STYLE_PROPERTY);

            if (style == null) {
                // dateStyle not specified in configuration file, set it as default
                this.dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, this.locale);
            } else if (style.equalsIgnoreCase("SHORT")) {
                this.dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, this.locale);
            } else if (style.equalsIgnoreCase("MEDIUM")) {
                this.dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, this.locale);
            } else if (style.equalsIgnoreCase("LONG")) {
                this.dateFormat = DateFormat.getDateInstance(DateFormat.LONG, this.locale);
            } else if (style.equalsIgnoreCase("FULL")) {
                this.dateFormat = DateFormat.getDateInstance(DateFormat.FULL, this.locale);
            } else {
                this.dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, this.locale);
            }
        } catch (UnknownNamespaceException une) {
            // ignore here, never occurs as we add the namespace to configuration manager.
        }
    }

    /**
     * <p>
     * Get the int value of property specified by propertyName. If the property does not exist or it is not a  number
     * or it is not between min and max, ConfigurationFileException will throws.
     * </p>
     *
     * <p></p>
     *
     * @param propertyName the property name
     * @param min the minimal value of the property
     * @param max the maximal value of the property
     *
     * @return the value of property specified by propertyName
     *
     * @throws IllegalArgumentException if the property does not exist or it is not a  number or it is not between min
     *         and max
     */
    private int getIntValueOfProperty(String propertyName, int min, int max) {
        try {
            ConfigManager configManager = ConfigManager.getInstance();
            String stringValue = configManager.getString(this.namespace, propertyName);

            if (stringValue == null) {
                throw new IllegalArgumentException("Property " + propertyName + " is not specified");
            }

            int value = Integer.parseInt(stringValue);

            if ((value < min) || (value > max)) {
                throw new IllegalArgumentException("Property " + propertyName + " must be between " + min + " and " +
                    max + "(inclusive)");
            }

            return value;
        } catch (UnknownNamespaceException une) {
            // ignore here, never occurs as we add the namespace to configuration manager.
            throw new IllegalArgumentException("Property " + propertyName + " is not specified");
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Property " + propertyName + " is not a number");
        }
    }

    /**
     * <p>
     * Get the bool value of property specified by propertyName. If the property does not exist or it is not a bool
     * value, ConfigurationFileException will throws.
     * </p>
     *
     * <p></p>
     *
     * @param propertyName the property name
     *
     * @return the value of property specified by propertyName
     *
     * @throws IllegalArgumentException if the property does not exist or it is not a bool value
     */
    private boolean getBoolValueOfProperty(String propertyName) {
        try {
            ConfigManager configManager = ConfigManager.getInstance();
            String stringValue = configManager.getString(this.namespace, propertyName);

            if (stringValue == null) {
                throw new IllegalArgumentException("Property " + propertyName + " is not specified");
            }

            if (stringValue.equalsIgnoreCase("true")) {
                return true;
            }

            if (stringValue.equalsIgnoreCase("false")) {
                return false;
            }

            throw new IllegalArgumentException("Property " + propertyName + " only can be true or false");
        } catch (UnknownNamespaceException une) {
            // ignore here, never occurs as we add the namespace to configuration manager.
            throw new IllegalArgumentException("Property " + propertyName + " is not specified");
        }
    }

    /**
     * Validation for the start/end time.
     * if start time is after end time or start/end hours is 24 but start/end minutes is bigger than 0,
     * IllegalArgumentException throws.
     *
     * @throws IllegalArgumentException if start time is after end time or start/end hours is 24 but start/end
     *         minutes is bigger than 0
     */
    private void timeStateValidation() {
        if (this.startTimeHours == 24 && this.startTimeMinutes > 0) {
            throw new IllegalArgumentException("The start time is after 24:00");
        }
        if (this.endTimeHours == 24 && this.endTimeMinutes > 0) {
            throw new IllegalArgumentException("The end time is after 24:00");
        }
        if (this.startTimeHours > this.endTimeHours
                || this.startTimeHours == this.endTimeHours && this.startTimeMinutes >= this.endTimeMinutes) {
            throw new IllegalArgumentException("The start time is after the end time");
        }
    }
}
