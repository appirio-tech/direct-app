/**
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.date.workdays.failuretests;

import junit.framework.TestCase;

import com.topcoder.date.workdays.*;

import java.util.Date;
import java.io.File;

/**
 * Failure test cases for DefaultWorkdays.
 *
 * @author WishingBone
 * @version 1.0
 */
public class DefaultWorkdaysFailureTests extends TestCase {

    /**
     * Get the absolute file name.
     *
     * @param name the relative file name.
     *
     * @return the absolute file name.
     */
    private String getFile(String name) {
        return new File("test_files/failuretests/" + name).getAbsolutePath();
    }

    /**
     * A clean instance to test with. Created in setUp() method.
     */
    private DefaultWorkdays workdays = null;

    /**
     * Create the workdays instance with default constructor.
     */
    protected void setUp() {
        workdays = new DefaultWorkdays();
    }

    /**
     * addNonWorkday() with null date.
     */
    public void testAddNonWorkday_NullDate() {
        try {
            this.workdays.addNonWorkday(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * removeNonWorkday() with null date.
     */
    public void testRemoveNonWorkday_NullDate() {
        try {
            this.workdays.removeNonWorkday(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * removeNonWorkday() with non-existing date.
     */
    public void testRemoveNonWorkday_NonExistingDate() {
        try {
            this.workdays.removeNonWorkday(new Date());
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayStartTimeHours() with negative hour.
     */
    public void testSetWorkdayStartTimeHours_NegativeHour() {
        try {
            this.workdays.setWorkdayStartTimeHours(-1);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayStartTimeHours() with out of range hour.
     */
    public void testSetWorkdayStartTimeHours_OurOfRangeHour() {
        try {
            this.workdays.setWorkdayStartTimeHours(25);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayStartTimeHours() with greater hour than end time.
     */
    public void testSetWorkdayStartTimeHours_GreaterHour() {
        this.workdays.setWorkdayEndTimeHours(12);
        this.workdays.setWorkdayEndTimeMinutes(59);
        this.workdays.setWorkdayStartTimeMinutes(0);
        try {
            this.workdays.setWorkdayStartTimeHours(13);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayStartTimeMinutes() with negative minute.
     */
    public void testSetWorkdayStartTimeMinutes_NegativeMinute() {
        try {
            this.workdays.setWorkdayStartTimeMinutes(-1);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayStartTimeMinutes() with out of range minute.
     */
    public void testSetWorkdayStartTimeHours_OurOfRangeMinute() {
        try {
            this.workdays.setWorkdayStartTimeMinutes(60);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayStartTimeMinutes() with greater minute than end time.
     */
    public void testSetWorkdayStartTimeMinutes_GreaterMinute() {
        this.workdays.setWorkdayEndTimeHours(12);
        // modified at final fix, because when setWorkdayStartTimeHours, the exception will throw.
        // The startTimeMinute have a original value bigger than 0.
        this.workdays.setWorkdayEndTimeMinutes(1);
        this.workdays.setWorkdayStartTimeMinutes(0);
        this.workdays.setWorkdayStartTimeHours(12);
        try {
            this.workdays.setWorkdayStartTimeMinutes(1);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayEndTimeHours() with negative hour.
     */
    public void testSetWorkdayEndTimeHours_NegativeHour() {
        try {
            this.workdays.setWorkdayEndTimeHours(-1);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayEndTimeHours() with out of range hour.
     */
    public void testSetWorkdayEndTimeHours_OurOfRangeHour() {
        try {
            this.workdays.setWorkdayEndTimeHours(25);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayEndTimeHours() with smaller hour than start time.
     */
    public void testSetWorkdayEndTimeHours_SmallerHour() {
        this.workdays.setWorkdayStartTimeHours(13);
        this.workdays.setWorkdayStartTimeMinutes(0);
        this.workdays.setWorkdayEndTimeMinutes(59);
        try {
            this.workdays.setWorkdayEndTimeHours(12);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayEndTimeHours() with illegal hour.
     */
    public void testSetWorkdayEndTimeHours_IllegalHour() {
        this.workdays.setWorkdayEndTimeMinutes(1);
        try {
            this.workdays.setWorkdayEndTimeHours(24);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayEndTimeMinutes() with negative minute.
     */
    public void testSetWorkdayEndTimeMinutes_NegativeMinute() {
        try {
            this.workdays.setWorkdayEndTimeMinutes(-1);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayEndTimeMinutes() with out of range minute.
     */
    public void testSetWorkdayEndTimeMinutes_OurOfRangeMinute() {
        try {
            this.workdays.setWorkdayEndTimeMinutes(60);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayEndTimeMinutes() with smaller minute than start time.
     */
    public void testSetWorkdayEndTimeMinutes_SmallerMinute() {
        this.workdays.setWorkdayEndTimeHours(13);
        this.workdays.setWorkdayEndTimeMinutes(30);
        this.workdays.setWorkdayStartTimeHours(13);
        this.workdays.setWorkdayStartTimeMinutes(29);
        try {
        this.workdays.setWorkdayEndTimeMinutes(28);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setWorkdayEndTimeMinutes() with illegal minute.
     */
    public void testSetWorkdayEndTimeMinutes_IllegalMinute() {
        this.workdays.setWorkdayEndTimeHours(24);
        try {
            this.workdays.setWorkdayEndTimeMinutes(1);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * setFileName() with null fileName.
     */
    public void testSetFileName_NullFileName() {
        try {
            this.workdays.setFileName(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * setFileFormat() with null fileFormat.
     */
    public void testSetFileFormat_NullFileFormat() {
        try {
            this.workdays.setFileFormat(null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * setFileFormat() with invalid fileFormat.
     */
    public void testSetFileFormat_InvalidFileFormat() {
        try {
            this.workdays.setFileFormat(".unknown");
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Create from bad start hour.
     */
    public void testCreate_BadStartHour() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("bad_start_hour.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from invalid start hour.
     */
    public void testCreate_InvalidStartHour() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("invalid_start_hour.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from missing start hour.
     */
    public void testCreate_MissingStartHour() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("missing_start_hour.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from bad start minute.
     */
    public void testCreate_BadStartMinute() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("bad_start_minute.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from invalid start minute.
     */
    public void testCreate_InvalidStartMinute() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("invalid_start_minute.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from missing start minute.
     */
    public void testCreate_MissingStartMinute() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("missing_start_minute.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from bad end hour.
     */
    public void testCreate_BadEndHour() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("bad_end_hour.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from invalid end hour.
     */
    public void testCreate_InvalidEndHour() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("invalid_end_hour.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from missing end hour.
     */
    public void testCreate_MissingEndHour() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("missing_end_hour.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from bad end minute.
     */
    public void testCreate_BadEndMinute() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("bad_end_minute.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from invalid end minute.
     */
    public void testCreate_InvalidEndMinute() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("invalid_end_minute.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from missing end minute.
     */
    public void testCreate_MissingEndMinute() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("missing_end_minute.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from invalid Saturday workday.
     */
    public void testCreate_InvalidSaturdayWorkday() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("invalid_saturday_workday.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from missing Saturday workday.
     */
    public void testCreate_MissingSaturdayWorkday() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("missing_saturday_workday.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from invalid Sunday workday.
     */
    public void testCreate_InvalidSundayWorkday() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("invalid_sunday_workday.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from missing Sunday workday.
     */
    public void testCreate_MissingSundayWorkday() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("missing_sunday_workday.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from bad non workdays.
     */
    public void testCreate_BadNonWorkdays() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("bad_non_workdays.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

    /**
     * Create from invalid non workdays.
     */
    public void testCreate_InvalidNonWorkdays() throws ConfigurationFileException {
        try {
            new DefaultWorkdays(this.getFile("invalid_non_workdays.properties"), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
            fail("Should have thrown ConfigurationFileException.");
        } catch (ConfigurationFileException cfe) {
        }
    }

}
