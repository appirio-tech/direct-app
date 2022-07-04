/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import junit.framework.TestCase;

import java.io.FileWriter;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;


/**
 * This class is used to check the behavior of the <code>DefaultWorkdays</code> class, including tests of all getters,
 * setters, refresh, save methods and constructors. The add(x, x, x) method is tested in
 * DefaultWorkdaysFunctionalityTest.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultWorkdaysTest extends TestCase {
    /** The name of workdays' properties configuration file. */
    private static final String PROPERTIES_FILE_NAME = "test_files/workdays_unittest.properties";

    /** The name of workdays' xml configuration file. */
    private static final String XML_FILE_NAME = "test_files/workdays_unittest.xml";

    /** The namespace name in config manager of properties configuration file. */
    private static final String PROPERTIES_NAMESPACE = "com.topcoder.date.workdays.unittest.properties";

    /** The namespace name in config manager of xml configuration file. */
    private static final String XML_NAMESPACE = "com.topcoder.date.workdays.unittest.xml";

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

    /** The DefaultWorkdays instance to test */
    private DefaultWorkdays workdays = null;

    /**
     * The configManager to load the xml and properties configuration file, it is used in validating  the properties of
     * workdays when created.
     */
    private ConfigManager configManager = ConfigManager.getInstance();

    /**
     * Initialize workdays as default configuration, add XML_NAMESPACE, PROPERTIES_NAMESPACE to configManager.
     *
     * @throws Exception DOCUMENT ME!
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.workdays = new DefaultWorkdays();

        FileWriter file = new FileWriter(PROPERTIES_FILE_NAME);
        file.write("startTime.hours=8\n" + "startTime.minutes=0\n" + "endTime.hours=16\n" + "endTime.minutes=40\n"
            + "isSaturdayWorkday=true\n" + "isSundayWorkday=false\n" + "locale.language=en\n" + "locale.country=US\n"
            + "nonWorkdays=Jan 23, 2004; May 6, 2004;Jul 2, 2004;Apr 12, 2004;Jun 18, 2004\n");
        file.close();
        this.configManager.add(XML_NAMESPACE, XML_FILE_NAME, ConfigManager.CONFIG_XML_FORMAT);
        this.configManager.add(PROPERTIES_NAMESPACE, PROPERTIES_FILE_NAME, ConfigManager.CONFIG_PROPERTIES_FORMAT);
    }

    /**
     * Remove the namespace XML_NAMESPACE, PROPERTIES_NAMESPACE from configManager. Set the workdays null,
     *
     * @throws Exception DOCUMENT ME!
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        this.workdays = null;
        this.configManager.removeNamespace(XML_NAMESPACE);
        this.configManager.removeNamespace(PROPERTIES_NAMESPACE);
    }

    ////////////////////////////////////////////
    // Constructor test cases

    /**
     * Test constructing a DefaultWorkdays instance with properties configuration file. This method call
     * assertPropertiesInWorkdays method to do all the assert works.
     *
     * @throws Exception if the configuration file wasn't configged correctly
     */
    public void testConstructorWithProperties() throws Exception {
        try {
            this.workdays = new DefaultWorkdays(PROPERTIES_FILE_NAME, DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            this.assertPropertiesInWorkdays(PROPERTIES_NAMESPACE);
        } catch (ConfigurationFileException cfe) {
            fail("Should not throw exception");
        }
    }

    /**
     * Test constructing a DefaultWorkdays instance with xml configuration file. This method call
     * assertPropertiesInWorkdays method to do all the assert works.
     *
     * @throws Exception if the configuration file wasn't configged correctly
     */
    public void testConstructorWithXML() throws Exception {
        try {
            this.workdays = new DefaultWorkdays(XML_FILE_NAME, DefaultWorkdays.XML_FILE_FORMAT);
            this.assertPropertiesInWorkdays(XML_NAMESPACE);
        } catch (ConfigurationFileException cfe) {
            fail("Should not throw exception");
        }
    }

    /**
     * Test constructing a DefaultWorkdays instance with illegal startTimeHours property.
     *
     * @throws ConfigManagerException if errors occer when change the properties in configManager
     */
    public void testConstructorWithIllegalStartTimeHours()
        throws ConfigManagerException {
        this.constructorWithIllegalProperties(START_TIME_HOURS_PROPERTY, "-1", "25", "9");
    }

    /**
     * Test constructing a DefaultWorkdays instance with illegal startTimeMinutes property.
     *
     * @throws ConfigManagerException if errors occer when change the properties in configManager
     */
    public void testConstructorWithIllegalStartTimeMinutes()
        throws ConfigManagerException {
        this.constructorWithIllegalProperties(START_TIME_MINUTES_PROPERTY, "-1", "60", "0");
    }

    /**
     * Test constructing a DefaultWorkdays instance with illegal endTimeHours property.
     *
     * @throws ConfigManagerException if errors occer when change the properties in configManager
     */
    public void testConstructorWithIllegalEndTimeHours()
        throws ConfigManagerException {
        this.constructorWithIllegalProperties(END_TIME_HOURS_PROPERTY, "-1", "25", "17");
    }

    /**
     * Test constructing a DefaultWorkdays instance with illegal EndTimeMinutes property.
     *
     * @throws ConfigManagerException if errors occer when change the properties in configManager
     */
    public void testConstructorWithIllegalEndTimeMinutes()
        throws ConfigManagerException {
        this.constructorWithIllegalProperties(END_TIME_MINUTES_PROPERTY, "-1", "60", "0");
    }

    /**
     * Test constructing a DefaultWorkdays instance when the start time is not before the end time.
     *
     * @throws ConfigManagerException if errors occer when change the properties in configManager
     */
    public void testConstructorWithIllegalTimeRelationShip()
        throws ConfigManagerException {
        try {
            this.setPropertyToConfigurationFile(START_TIME_HOURS_PROPERTY, "8");
            this.setPropertyToConfigurationFile(END_TIME_HOURS_PROPERTY, "8");
            this.setPropertyToConfigurationFile(START_TIME_MINUTES_PROPERTY, "30");
            this.setPropertyToConfigurationFile(END_TIME_MINUTES_PROPERTY, "30");

            this.workdays = new DefaultWorkdays(PROPERTIES_FILE_NAME, DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should throw ConfigurationFileException");
        } catch (ConfigurationFileException cfe) {
            assertEquals("Test for validation of property relationship", IllegalArgumentException.class,
                cfe.getCause().getClass());
            assertTrue("Test for validation of property relationship",
                cfe.getCause().getMessage().startsWith("The start time is after the end time"));
        }
    }

    //////////////////////////////////////////////////////////
    // test cases for getter and setter

    /**
     * Test setting startTimeHours property normally.
     */
    public void testStartTimeHoursNormally() {
        try {
            this.workdays.setWorkdayStartTimeHours(7);
            assertEquals("Test for startTimeHours", 7, this.workdays.getWorkdayStartTimeHours());
        } catch (IllegalArgumentException iae) {
            fail("Should not throw exception");
        }
    }

    /**
     * Test setting startTimeHours property with illegal argument.  Including negative value and too big value.
     */
    public void testStartTimeHoursIllegally() {
        // negative value
        try {
            this.workdays.setWorkdayStartTimeHours(-1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        // invalid value (too big)
        try {
            this.workdays.setWorkdayStartTimeHours(25);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test setting startTimeMinutes property normally.
     */
    public void testStartTimeMinutesNormally() {
        try {
            this.workdays.setWorkdayStartTimeMinutes(30);
            assertEquals("Test for startTimeMinutes", 30, this.workdays.getWorkdayStartTimeMinutes());
        } catch (IllegalArgumentException iae) {
            fail("Should not throw exception");
        }
    }

    /**
     * Test setting startTimeMinutes property with illegal argument.  Including negative value, and too big value.
     */
    public void testStartTimeMinutesIllegally() {
        // negative value
        try {
            this.workdays.setWorkdayStartTimeMinutes(-1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        // invalid value (too big)
        try {
            this.workdays.setWorkdayStartTimeMinutes(60);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test setting endTimeHours property normally.
     */
    public void testEndTimeHoursNormally() {
        try {
            this.workdays.setWorkdayEndTimeHours(14);
            assertEquals("Test for endTimeHours", 14, this.workdays.getWorkdayEndTimeHours());
        } catch (IllegalArgumentException iae) {
            fail("Should not throw exception");
        }
    }

    /**
     * Test setting endTimeHours property with illegal argument.  Including negative value and too big value.
     */
    public void testEndTimeHoursIllegally() {
        // negative value
        try {
            this.workdays.setWorkdayEndTimeHours(-1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        // invalid value (too big)
        try {
            this.workdays.setWorkdayEndTimeHours(25);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test setting endTimeMinutes property normally.
     */
    public void testEndTimeMinutesNormally() {
        try {
            this.workdays.setWorkdayEndTimeMinutes(30);
            assertEquals("Test for endTimeMinutes", 30, this.workdays.getWorkdayEndTimeMinutes());
        } catch (IllegalArgumentException iae) {
            fail("Should not throw exception");
        }
    }

    /**
     * Test setting endTimeMinutes property with illegal argument.  Including negative value, and too big value.
     */
    public void testEndTimeMinutesIllegally() {
        // negative value
        try {
            this.workdays.setWorkdayEndTimeMinutes(-1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        // invalid value (too big)
        try {
            this.workdays.setWorkdayEndTimeMinutes(60);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test setting saturdayWorkday normally.
     */
    public void testSetSaturdayWorkday() {
        this.workdays.setSaturdayWorkday(true);
        assertTrue("Test for setSaturdayWorkday", this.workdays.isSaturdayWorkday());
        this.workdays.setSaturdayWorkday(false);
        assertTrue("Test for setSaturdayWorkday", !this.workdays.isSaturdayWorkday());
    }

    /**
     * Test setting sundayWorkday normally.
     */
    public void testSetSundayWorkday() {
        this.workdays.setSundayWorkday(true);
        assertTrue("Test for setSundayWorkday", this.workdays.isSundayWorkday());
        this.workdays.setSundayWorkday(false);
        assertTrue("Test for setSundayWorkday", !this.workdays.isSundayWorkday());
    }

    /**
     * Test setting fileName normally.
     */
    public void testSetFileNameNormally() {
        try {
            this.workdays.setFileName("fileName");
            assertEquals("Test for fileName", "fileName", this.workdays.getFileName());
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    /**
     * Test setting fileName null or empty string.
     */
    public void testSetFileNameIllegally() {
        try {
            this.workdays.setFileName(null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException npe) {
        }

        try {
            this.workdays.setFileName(" ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Test setting fileFormat either "properties" or "XML".
     */
    public void testSetFileFormatNormally() {
        try {
            this.workdays.setFileFormat(DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            assertEquals("Test for fileFormat", DefaultWorkdays.PROPERTIES_FILE_FORMAT, this.workdays.getFileFormat());
            this.workdays.setFileFormat(DefaultWorkdays.XML_FILE_FORMAT);
            assertEquals("Test for fileFormat", DefaultWorkdays.XML_FILE_FORMAT, this.workdays.getFileFormat());
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    /**
     * Test setting fileFormat will null or with neither "properties" nor "XML".
     */
    public void testSetFileFormatIllegally() {
        try {
            this.workdays.setFileFormat(null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException npe) {
        }

        try {
            // set fileFormat neither "properties" nor "XML".
            this.workdays.setFileFormat("IllegalFileFormat");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
    }

    ////////////////////////////////////////////////////////////////////////
    // test cases for add, refresh, and save

    /**
     * Test refreshing without configuration file, IllegalArgumentException should be throwed.
     */
    public void testRefreshWithourConfigurationFile() {
        try {
            // in setUp method, workdays default instance withour configuration file was constructed.
            this.workdays.refresh();
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        } catch (ConfigurationFileException cfe) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * Test saving without configuration file, IllegalArgumentException should be throwed.
     */
    public void testSaveWithourConfigurationFile() {
        try {
            // in setUp method, workdays default instance withour configuration file was constructed.
            this.workdays.save();
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        } catch (ConfigurationFileException cfe) {
            fail("Should throw IllegalArgumentException");
        }
    }

    /**
     * Modify the configuration file by configManager, than refresh the workdays instance to see it's behavior.
     *
     * @throws Exception if the configuration file wasn't configged correctly
     */
    public void testRefreshNormally() throws Exception {
        try {
            // initialize workdays
            this.workdays = new DefaultWorkdays(PROPERTIES_FILE_NAME, DefaultWorkdays.PROPERTIES_FILE_FORMAT);

            // assert all properties in workdays.
            this.assertPropertiesInWorkdays(PROPERTIES_NAMESPACE);

            // modefy the properties configuration file by configManager
            this.setPropertyToConfigurationFile(START_TIME_HOURS_PROPERTY, "5");
            this.setPropertyToConfigurationFile(IS_SATURDAY_WORKDAY_PROPERTY, "true");

            // refresh the properties configuration file
            this.workdays.refresh();
            this.assertPropertiesInWorkdays(PROPERTIES_NAMESPACE);
        } catch (ConfigurationFileException cfe) {
            fail("Should not throw ConfigurationFileException");
        }
    }

    /**
     * Modefied the properties in DefaultWorkdays instance, save the change to properties configuration file,  then
     * assert all the properties in DefaultWorkdays instance and configuration file.
     *
     * @throws Exception if the configuration file wasn't configged correctly
     */
    public void testSaveNormally() throws Exception {
        try {
            // initialize workdays
            this.workdays = new DefaultWorkdays(PROPERTIES_FILE_NAME, DefaultWorkdays.PROPERTIES_FILE_FORMAT);

            // assert all properties in workdays.
            this.assertPropertiesInWorkdays(PROPERTIES_NAMESPACE);

            // modefy some properties in workdays
            this.workdays.setWorkdayStartTimeHours(5);
            this.workdays.setSaturdayWorkday(true);

            // save the changes to configuration file
            this.workdays.save();

            // refresh the namespace PROPERTIES_NAMESPACE in configManager
            this.configManager.refresh(PROPERTIES_NAMESPACE);

            // assert all properties in workdays
            this.assertPropertiesInWorkdays(PROPERTIES_NAMESPACE);
        } catch (ConfigurationFileException cfe) {
            fail("Should not throw ConfigurationFileException");
        }
    }

    /**
     * Test save the current configuration in workdays instance to a specified file.
     *
     * @throws Exception if the configuration file wasn't configged correctly
     */
    public void testSaveAs() throws Exception {
        try {
            this.workdays.addNonWorkday(new Date());

            // save as XML
            this.workdays.setFileFormat(DefaultWorkdays.XML_FILE_FORMAT);
            this.workdays.setFileName("test_files/saveasdefault.XML");
            this.workdays.save();

            // make sure save correctly
            this.configManager.add("save_as_default_xml", "test_files/saveasdefault.XML",
                ConfigManager.CONFIG_XML_FORMAT);
            this.assertPropertiesInWorkdays("save_as_default_xml");
            this.configManager.removeNamespace("save_as_default_xml");

            // save as properties
            this.workdays.setFileFormat(DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            this.workdays.setFileName("test_files/saveasdefault.properties");
            this.workdays.save();

            // make sure save correctly
            this.configManager.add("save_as_default_properties", "test_files/saveasdefault.properties",
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
            this.assertPropertiesInWorkdays("save_as_default_properties");
            this.configManager.removeNamespace("save_as_default_properties");
        } catch (ConfigurationFileException cfe) {
            fail("Should not throw ConfigurationFileException");
        }
    }

    /**
     * Test adding nonWorkdays.
     */
    public void testAddNonWorkdays() {
        try {
            Set nonWorkdays = new TreeSet();
            Calendar cal = Calendar.getInstance();

            // cut the part of cal's hour, minute, second and millisecond
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            for (int i = 0; i < 100; i++) {
                cal.add(Calendar.DATE, 1);
                nonWorkdays.add(((Calendar) cal.clone()).getTime());
                this.workdays.addNonWorkday(((Calendar) cal.clone()).getTime());
            }

            assertEquals("Test for addNonWorkdays", nonWorkdays, this.workdays.getNonWorkdays());
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    /**
     * Test constructing a DefaultWorkdays with illegal property. Including three situatoins, first,  the property is
     * not specified, second, the property value is smaller than the minimal value the  property can be, third, the
     * property value is bigger than the maximal value the property can be.
     *
     * @param propertyName the name of property to test
     * @param lessThanMinValue the illegal value which is min_value_of_the_property - 1
     * @param moreThanMaxValue the illegal value which is max_value_of_the_property + 1
     * @param defaultValue the default value of property
     *
     * @throws ConfigManagerException if errors occer when change the properties in configManager
     */
    private void constructorWithIllegalProperties(String propertyName, String lessThanMinValue,
        String moreThanMaxValue, String defaultValue) throws ConfigManagerException {
        // without this property
        try {
            // remove the this property from configuration file
            this.removePropertyFromConfigurationFile(propertyName);

            this.workdays = new DefaultWorkdays(PROPERTIES_FILE_NAME, DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should throw ConfigurationFileException");
        } catch (ConfigurationFileException cfe) {
            assertEquals("Test for validation of property " + propertyName, IllegalArgumentException.class,
                cfe.getCause().getClass());
            assertTrue("Test for validation of property " + propertyName,
                cfe.getCause().getMessage().startsWith("Property " + propertyName + " is not specified"));

            // reset the default value of this property
            this.setPropertyToConfigurationFile(propertyName, defaultValue);
        }

        // with lessThanMinValue of the property
        try {
            // set lessThanMinValue of the property
            this.setPropertyToConfigurationFile(propertyName, lessThanMinValue);

            this.workdays = new DefaultWorkdays(PROPERTIES_FILE_NAME, DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should throw ConfigurationFileException");
        } catch (ConfigurationFileException cfe) {
            assertEquals("Test for validation of property " + propertyName, IllegalArgumentException.class,
                cfe.getCause().getClass());
            assertTrue("Test for validation of property",
                cfe.getCause().getMessage().startsWith("Property " + propertyName + " must be between "
                    + String.valueOf(Integer.parseInt(lessThanMinValue) + 1) + " and "
                    + String.valueOf(Integer.parseInt(moreThanMaxValue) - 1) + "(inclusive)"));

            // reset the correct value of startTimeHours
            this.setPropertyToConfigurationFile(propertyName, defaultValue);
        }

        // with moreThanMaxValue of the property
        try {
            // set moreThanMaxValue of the property
            this.setPropertyToConfigurationFile(propertyName, moreThanMaxValue);

            this.workdays = new DefaultWorkdays(PROPERTIES_FILE_NAME, DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should throw ConfigurationFileException");
        } catch (ConfigurationFileException cfe) {
            assertEquals("Test for validation of property " + propertyName, IllegalArgumentException.class,
                cfe.getCause().getClass());
            assertTrue("Test for validation of property",
                cfe.getCause().getMessage().startsWith("Property " + propertyName + " must be between "
                    + String.valueOf(Integer.parseInt(lessThanMinValue) + 1) + " and "
                    + String.valueOf(Integer.parseInt(moreThanMaxValue) - 1) + "(inclusive)"));

            // reset the correct value of startTimeHours
            this.setPropertyToConfigurationFile(propertyName, defaultValue);
        }
    }

    /**
     * Validate that all the properties in configuration file are loaded to DefaultWorkdays instance correctly.
     *
     * @param namespace the namespace name in configManager
     *
     * @throws Exception if the configuration file wasn't configged correctly
     */
    private void assertPropertiesInWorkdays(String namespace)
        throws Exception {
        assertEquals("Test for startTimeHours", this.configManager.getString(namespace, START_TIME_HOURS_PROPERTY),
            String.valueOf(this.workdays.getWorkdayStartTimeHours()));
        assertEquals("Test for startTimeMinutes", this.configManager.getString(namespace, START_TIME_MINUTES_PROPERTY),
            String.valueOf(this.workdays.getWorkdayStartTimeMinutes()));
        assertEquals("Test for endTimeHours", this.configManager.getString(namespace, END_TIME_HOURS_PROPERTY),
            String.valueOf(this.workdays.getWorkdayEndTimeHours()));
        assertEquals("Test for endTimeMinutes", this.configManager.getString(namespace, END_TIME_MINUTES_PROPERTY),
            String.valueOf(this.workdays.getWorkdayEndTimeMinutes()));
        assertEquals("Test for isSaturdayWorkday",
            this.configManager.getString(namespace, IS_SATURDAY_WORKDAY_PROPERTY),
            String.valueOf(this.workdays.isSaturdayWorkday()));
        assertEquals("Test for isSundayWorkday", this.configManager.getString(namespace, IS_SUNDAY_WORKDAY_PROPERTY),
            String.valueOf(this.workdays.isSundayWorkday()));

        // Test all non-workdays
        // locale language in configuration file
        String language = this.configManager.getString(namespace, LOCALE_LANGUAGE_PROPERTY);

        // locale country in configuration file
        String country = this.configManager.getString(namespace, LOCALE_COUNTRY_PROPERTY);

        // locale variant in configuration file
        String variant = this.configManager.getString(namespace, LOCALE_VARIANT_PROPERTY);

        Locale locale = Locale.getDefault();

        if ((language != null) && (country != null) && (variant != null)) {
            // all the three properties are specified
            locale = new Locale(language, country, variant);
        } else if ((language != null) && (country != null)) {
            // only language and country are specified
            locale = new Locale(language, country);
        }

        // dateStyle in configuration file
        String style = this.configManager.getString(namespace, DATE_STYLE_PROPERTY);

        // DateFormat instance specified by the configuration file
        DateFormat dateFormat = null;

        if (style == null) {
            // dateStyle not specified in configuration file, set it as default
            dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        } else if (style.equalsIgnoreCase("SHORT")) {
            dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        } else if (style.equalsIgnoreCase("MEDIUM")) {
            dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        } else if (style.equalsIgnoreCase("LONG")) {
            dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
        } else if (style.equalsIgnoreCase("FULL")) {
            dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
        } else {
            dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        }

        Set nonWorkdays = new TreeSet();
        String[] nonWorkdaysValue = this.configManager.getStringArray(namespace, NON_WORKDAYS_PROPERTY);

        if (nonWorkdaysValue != null) {
            for (int i = 0; i < nonWorkdaysValue.length; i++) {
                nonWorkdays.add(dateFormat.parse(nonWorkdaysValue[i].trim()));
            }
        }

        assertEquals("Test for nonWorkdays set", nonWorkdays, this.workdays.getNonWorkdays());
    }

    /**
     * Set the property value to configuration file through configManager.
     *
     * @param propertyName the name of property to set
     * @param value the new value to set
     *
     * @throws ConfigManagerException if errors occer when set the property in configManager
     */
    private void setPropertyToConfigurationFile(String propertyName, String value)
        throws ConfigManagerException {
        // before setting the value, a temporary namespace must be created.
        this.configManager.createTemporaryProperties(PROPERTIES_NAMESPACE);

        // set the value of property
        this.configManager.setProperty(PROPERTIES_NAMESPACE, propertyName, value);

        // set the user name as namespace name when commit the change
        this.configManager.commit(PROPERTIES_NAMESPACE, PROPERTIES_NAMESPACE);
    }

    /**
     * Remove the property from configuration file through configManager.
     *
     * @param propertyName the name of property to remove
     *
     * @throws ConfigManagerException if errors occer when remove the property in configManager
     */
    private void removePropertyFromConfigurationFile(String propertyName)
        throws ConfigManagerException {
        this.configManager.createTemporaryProperties(PROPERTIES_NAMESPACE);

        // remove the property
        this.configManager.removeProperty(PROPERTIES_NAMESPACE, propertyName);

        // set the user name as namespace name when commit the change
        this.configManager.commit(PROPERTIES_NAMESPACE, PROPERTIES_NAMESPACE);
    }
}
