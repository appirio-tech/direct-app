/*
 * ComponentName: WorkDays
 * FileName: DefaultWorkdaysTest.java
 * Version: 1.0
 * Date: 01/06/2005
 *
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */
package com.topcoder.date.workdays.accuracytests;

import com.topcoder.date.workdays.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <p>
 * This class contains the accuracy unit tests for DefaultWorkdays.java
 * </p>
 *
 * @author woodjhon
 * @version 1.0
 */
public class DefaultWorkdaysTest extends TestCase {
    
    /**
     * <p>
     * Represents an instance of Calender to be used for testing.
     * </p>
     */
    private Calendar calendar = null;
    /**
     * <p>
     * Represents an instance of DefaultWorkdays to be used for testing.
     * </p>
     */
    private DefaultWorkdays wd = null;
    
    /**
     * <p>
     * Represents an instance of DefaultWorkdaysFactory to be used for testing.
     * </p>
     */
    private DefaultWorkdaysFactory factory = null;
    
    /**
     * <p>
     * Represents the file name of config file.
     * </p>
     */
    private String fileName = "test_files/accuracytests/workdays.properties";
    
    /**
     * <p>
     * Represents the file name of config file.
     * </p>
     */
    private String fileNameBackUp = "test_files/accuracytests/backUpworkdays.properties";
    
    /**
     * <p>
     * Represents the file name of config file.
     * </p>
     */
    private String fileNameSaveAsXML = "test_files/accuracytests/workdaysSaveAs.xml";
    
    /**
     * <p>
     * Represents the file name of config file.
     * </p>
     */
    private String fileNameSaveAsProp = "test_files/accuracytests/workdaysSaveAs.properties";
    
    /**
     * <p>
     * Represents the file name of config file.
     * </p>
     */
    private String fileNameRefreshXML = "test_files/accuracytests/workdaysReresh.xml";
    
    /**
     * <p>
     * Represents the file name of config file.
     * </p>
     */
    private String fileNameRefreshProp = "test_files/accuracytests/workdaysRefresh.properties";
    
    /**
     * <p>
     * Represents the format of config file.
     * </p>
     */
    private String fileFormat = DefaultWorkdays.PROPERTIES_FILE_FORMAT;
    
    /**
     * <p>
     * Represents the format of config file.
     * </p>
     */
    private String fileFormatSaveAs = DefaultWorkdays.XML_FILE_FORMAT;
    
    /** 
     * <p>
     * An locale instance for testing. 
     * </p>
     * */
    private Locale locale = null;
    
    /** 
     * <p>
     * The dateFormat to format date such as 2005:01:01 00:00:00. 
     * </p>
     * */
    private SimpleDateFormat dateFormat = null;

    /**
     * <p>
     * Creates an instance for the Test.
     * </p>
     *
     * @param name the name of the TestCase.
     */
    public DefaultWorkdaysTest(String name) {
        super(name);
    }

    /**
     * <p>
     * Sets up the fixture.
     * </p>
     * 
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.initialConfigManager();
        this.DeleteTempFiles();
        locale = new Locale("en", "US");
        dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", this.locale);
        
        factory = new DefaultWorkdaysFactory();
        wd = (DefaultWorkdays) factory.createWorkdaysInstance();
        
        this.calendar = Calendar.getInstance(this.locale);
        calendar.set(2005, 1, 3, 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * <p>
     * Tears down the fixture.
     * </p>
     */
    protected void tearDown() {
        // nothing to do here
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(DefaultWorkdaysTest.class);
    }

    /**
     * <p>
     * Tests accuracy of the constructor DefaultWorkdays()
     * </p>
     */
    public void testConstructorAccuracy() {
        assertNotNull(new DefaultWorkdays());
    }

    /**
     * <p>
     * Tests accuracy of the constructor DefaultWorkdays(String fileName,
     * String fileFormat)
     * </p>
     *
     * @throws ConfigurationFileException to JUnit
     */
    public void testConstructorTwoAccuracy() throws ConfigurationFileException {
        DefaultWorkdays dwd = new DefaultWorkdays(this.fileName, this.fileFormat);
        assertFalse(dwd.isSundayWorkday());
        assertTrue(dwd.isSaturdayWorkday());
    }

    /**
     * <p>
     * Tests accuracy of the method refresh()
     * </p>
     *
     * @throws ConfigurationFileException to JUnit
     */
    public void testRefreshAccuracy1() throws ConfigurationFileException {
        this.wd.setFileFormat(DefaultWorkdays.XML_FILE_FORMAT);
        this.wd.setFileName(this.fileNameRefreshXML);
        this.wd.refresh();
        assertEquals(this.wd.getWorkdayStartTimeMinutes(), 10);
    }
    
    /**
     * <p>
     * Tests accuracy of the method refresh()
     * </p>
     *
     * @throws ConfigurationFileException to JUnit
     */
    public void testRefreshAccuracy2() throws ConfigurationFileException {
        this.wd.setWorkdayStartTimeHours(9);
        this.wd.setFileFormat(DefaultWorkdays.PROPERTIES_FILE_FORMAT);
        this.wd.setFileName(this.fileNameRefreshProp);
        this.wd.refresh();
        
        assertEquals(this.wd.getWorkdayStartTimeMinutes(), 11);
    }
    
    /**
     * <p>
     * Tests accuracy of the method refresh()
     * </p>
     *
     * @throws ConfigurationFileException to JUnit
     */
    public void testRefreshAccuracy3() throws ConfigurationFileException {
        this.wd.setWorkdayEndTimeMinutes(15);
        this.wd.save();
        this.wd.refresh();
        assertEquals(this.wd.getWorkdayEndTimeMinutes(), 15);
    }

    /**
     * <p>
     * Tests accuracy of the method save()
     * </p>
     *
     * @throws ConfigurationFileException to JUnit
     */
    public void testSaveAccuracy1() throws ConfigurationFileException {
        this.wd.setWorkdayEndTimeHours(15);
        this.wd.setFileFormat(DefaultWorkdays.XML_FILE_FORMAT);
        this.wd.setFileName(this.fileNameSaveAsXML);
        this.wd.save();
        this.wd = new DefaultWorkdays(this.fileNameSaveAsXML, DefaultWorkdays.XML_FILE_FORMAT);
        assertEquals(this.wd.getWorkdayEndTimeHours(), 15);
    }
    
    /**
     * <p>
     * Tests accuracy of the method save()
     * </p>
     *
     * @throws ConfigurationFileException to JUnit
     */
    public void testSaveAccuracy2() throws ConfigurationFileException {
        this.wd.setWorkdayStartTimeHours(9);
        this.wd.setFileFormat(DefaultWorkdays.PROPERTIES_FILE_FORMAT);
        this.wd.setFileName(this.fileNameSaveAsProp);
        this.wd.save();
        this.wd = new DefaultWorkdays(this.fileNameSaveAsProp, 
                DefaultWorkdays.PROPERTIES_FILE_FORMAT);
        assertEquals(this.wd.getWorkdayStartTimeHours(), 9);
    }
    
    /**
     * <p>
     * Tests accuracy of the method save()
     * </p>
     *
     * @throws ConfigurationFileException to JUnit
     */
    public void testSaveAccuracy3() throws ConfigurationFileException {
        this.wd.setWorkdayEndTimeMinutes(15);
        this.wd.save();
        this.wd = new DefaultWorkdays(this.fileName, DefaultWorkdays.PROPERTIES_FILE_FORMAT);
        assertEquals(this.wd.getWorkdayEndTimeMinutes(), 15);
    }

    /**
     * <p>
     * Tests accuracy of the method addNonWorkday(Date nonWorkday)
     * </p>
     */
    public void testAddNonWorkdayAccuracy() {
        Date date = this.calendar.getTime();
        this.wd.addNonWorkday(date);
        assertTrue("The date should be added", 
                this.wd.getNonWorkdays().contains(date));
    }

    /**
     * <p>
     * Tests accuracy of the method removeNonWorkday(Date nonWorkday)
     * </p>
     */
    public void testRemoveNonWorkdayAccuracy() {
        this.wd.addNonWorkday(this.calendar.getTime());
        assertTrue("The date should be added", 
                this.wd.getNonWorkdays().contains(this.calendar.getTime()));
        this.wd.removeNonWorkday(this.calendar.getTime());
        assertFalse("The date should be removed", 
                this.wd.getNonWorkdays().contains(this.calendar.getTime()));
    }

    /**
     * <p>
     * Tests accuracy of the method getNonWorkdays()
     * </p>
     */
    public void testGetNonWorkdaysAccuracy() {
        assertEquals("There should be 4 dates configured", 4, 
                this.wd.getNonWorkdays().size());
    }

    /**
     * <p>
     * Tests accuracy of the method clearNonWorkdays()
     * </p>
     */
    public void testClearNonWorkdaysAccuracy() {
        this.wd.clearNonWorkdays();
        assertEquals(0, this.wd.getNonWorkdays().size());
    }

    /**
     * <p>
     * Tests accuracy of the method setSaturdayWorkday(boolean
     * isSaturdayWorkday)
     * </p>
     */
    public void testSetSaturdayWorkdayAccuracy() {
        this.wd.setSaturdayWorkday(false);
        assertFalse(this.wd.isSaturdayWorkday());
    }

    /**
     * <p>
     * Tests accuracy of the method isSaturdayWorkday()
     * </p>
     */
    public void testIsSaturdayWorkdayAccuracy() {
        assertTrue(this.wd.isSaturdayWorkday());
    }

    /**
     * <p>
     * Tests accuracy of the method setSundayWorkday(boolean isSundayWorkday)
     * </p>
     */
    public void testSetSundayWorkdayAccuracy() {
        this.wd.setSundayWorkday(true);
        assertTrue(this.wd.isSundayWorkday());
    }

    /**
     * <p>
     * Tests accuracy of the method isSundayWorkday()
     * </p>
     */
    public void testIsSundayWorkdayAccuracy() {
        assertFalse(this.wd.isSundayWorkday());
    }

    /**
     * <p>
     * Tests accuracy of the method setWorkdayStartTimeHours(int
     * startTimeHours)
     * </p>
     */
    public void testSetWorkdayStartTimeHoursAccuracy() {
        this.wd.setWorkdayStartTimeHours(10);
        assertEquals(this.wd.getWorkdayStartTimeHours(), 10);
    }

    /**
     * <p>
     * Tests accuracy of the method getWorkdayStartTimeHours()
     * </p>
     */
    public void testGetWorkdayStartTimeHoursAccuracy() {
        assertEquals("8 should be configured", this.wd.getWorkdayStartTimeHours(), 8);
    }

    /**
     * <p>
     * Tests accuracy of the method setWorkdayStartTimeMinutes(int
     * startTimeMinutes)
     * </p>
     */
    public void testSetWorkdayStartTimeMinutesAccuracy() {
        this.wd.setWorkdayStartTimeMinutes(25);
        assertEquals(this.wd.getWorkdayStartTimeMinutes(), 25);
    }

    /**
     * <p>
     * Tests accuracy of the method getWorkdayStartTimeMinutes()
     * </p>
     */
    public void testGetWorkdayStartTimeMinutesAccuracy() {
        assertEquals(this.wd.getWorkdayStartTimeMinutes(), 0);
    }

    /**
     * <p>
     * Tests accuracy of the method setWorkdayEndTimeHours(int endTimeHours)
     * </p>
     */
    public void testSetWorkdayEndTimeHoursAccuracy() {
        this.wd.setWorkdayEndTimeHours(17);
        assertEquals(this.wd.getWorkdayEndTimeHours(), 17);
    }

    /**
     * <p>
     * Tests accuracy of the method getWorkdayEndTimeHours()
     * </p>
     */
    public void testGetWorkdayEndTimeHoursAccuracy() {
        assertEquals("16 should be configured", this.wd.getWorkdayEndTimeHours(), 16);
    }

    /**
     * <p>
     * Tests accuracy of the method setWorkdayEndTimeMinutes(int
     * endTimeMinutes)
     * </p>
     */
    public void testSetWorkdayEndTimeMinutesAccuracy() {
        this.wd.setWorkdayEndTimeMinutes(45);
        assertEquals(this.wd.getWorkdayEndTimeMinutes(), 45);
    }

    /**
     * <p>
     * Tests accuracy of the method getWorkdayEndTimeMinutes()
     * </p>
     */
    public void testGetWorkdayEndTimeMinutesAccuracy() {
        assertEquals("30 should be configured", this.wd.getWorkdayEndTimeMinutes(), 30);
    }

    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy1() {
        Date startDate = this.getDate(2005, 0, 1, 8, 30);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.DAYS, 1);
        assertEquals("2005.01.03 08:30:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy2() {
        Date startDate = this.getDate(2005, 0, 1, 7, 30);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.DAYS, 1);
        assertEquals("2005.01.01 16:30:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy3() {
        Date startDate = this.getDate(2005, 0, 1, 8, 30);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.DAYS, 25);
        assertEquals("2005.02.03 08:30:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy31() {
        Date startDate = this.getDate(2005, 0, 1, 8, 00);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.DAYS, 25);
        assertEquals("2005.02.02 16:30:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy32() {
        Date startDate = this.getDate(2005, 0, 1, 7, 00);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.DAYS, 25);
        assertEquals("2005.02.02 16:30:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy4() {
        Date startDate = this.getDate(2005, 0, 1, 8, 30);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.HOURS, 5);
        assertEquals("2005.01.01 13:30:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy5() {
        Date startDate = this.getDate(2005, 0, 1, 8, 30);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.HOURS, 24);
        assertEquals("2005.01.04 15:30:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy51() {
        Date startDate = this.getDate(2005, 0, 1, 8, 30);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.HOURS, 212);
        assertEquals("2005.02.02 16:30:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy52() {
        Date startDate = this.getDate(2005, 0, 1, 8, 00);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.HOURS, 212);
        assertEquals("2005.02.02 16:00:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy53() {
        Date startDate = this.getDate(2005, 0, 1, 7, 00);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.HOURS, 212);
        assertEquals("2005.02.02 16:00:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy6() {
        Date startDate = this.getDate(2005, 0, 1, 8, 30);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.MINUTES, 5 * 60);
        assertEquals("2005.01.01 13:30:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy7() {
        Date startDate = this.getDate(2005, 0, 1, 8, 30);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.MINUTES, 24 * 60);
        assertEquals("2005.01.04 15:30:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy71() {
        Date startDate = this.getDate(2005, 0, 1, 8, 30);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.MINUTES, 212 * 60);
        assertEquals("2005.02.02 16:30:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy72() {
        Date startDate = this.getDate(2005, 0, 1, 8, 00);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.MINUTES, 212 * 60);
        assertEquals("2005.02.02 16:00:00", this.dateFormat.format(endDate));
    }
    
    /**
     * <p>
     * Tests accuracy of the method add(Date startDate, WorkdaysUnitOfTime
     * unitOfTime, int amount)
     * </p>
     */
    public void testAddAccuracy73() {
        Date startDate = this.getDate(2005, 0, 1, 7, 00);
        Date endDate = this.wd.add(startDate, WorkdaysUnitOfTime.MINUTES, 212 * 60);
        assertEquals("2005.02.02 16:00:00", this.dateFormat.format(endDate));
    }

    /**
     * <p>
     * Tests accuracy of the method setFileName(String fileName)
     * </p>
     */
    public void testSetFileNameAccuracy() {
        this.wd.setFileName("test_files/accuracytests/workdays.xml");
        assertTrue(this.wd.getFileName().endsWith("workdays.xml"));
    }

    /**
     * <p>
     * Tests accuracy of the method getFileName()
     * </p>
     */
    public void testGetFileNameAccuracy() {
        assertTrue(this.wd.getFileName().endsWith("workdays.properties"));
    }

    /**
     * <p>
     * Tests accuracy of the method getFileFormat()
     * </p>
     */
    public void testGetFileFormatAccuracy() {
        assertEquals(this.wd.getFileFormat(), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
    }

    /**
     * <p>
     * Tests accuracy of the method setFileFormat(String fileFormat)
     * </p>
     */
    public void testSetFileFormatAccuracy() {
        this.wd.setFileFormat(DefaultWorkdays.XML_FILE_FORMAT);
        assertTrue(this.wd.getFileFormat().equalsIgnoreCase(DefaultWorkdays.XML_FILE_FORMAT));
    }
    
    /**
     * <p>
     * an instance of Date with the specified year, month, day, and hour, 
     * and min.
     * </p>
     * @param year an int represents the year
     * @param month an int represents the month
     * @param day an int represents the day
     * @param hour an int represents the hour
     * @param min an int represents the minute
     * @return an instance of Date with the specified year, month, day, and hour, 
     *         and min.
     */
    private Date getDate(int year, int month, int day, int hour, int min) {
        Calendar calendar = Calendar.getInstance(this.locale);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        return calendar.getTime();
    }
    
    /**
     * <p>
     * Delete the temp files under test_files/accuracytests/
     * </p>
     * @throws Exception to JUnit
     */
    private void DeleteTempFiles() throws Exception {
        File file = new File(this.fileNameSaveAsXML);
        if (file.exists()) {
            file.delete();
        }
        file = new File(this.fileNameSaveAsProp);
        if (file.exists()) {
            file.delete();
        }
        file = new File(this.fileName);
        if (file.exists()) {
            file.delete();
        }
        BufferedReader reader = new BufferedReader(new FileReader(this.fileNameBackUp));
        PrintWriter writer = new PrintWriter(new FileOutputStream(new File(this.fileName)));
        String line = reader.readLine();
        while (line != null) {
            writer.write(line + "\n");
            writer.flush();
            line = reader.readLine();
        }
        reader.close();
        writer.close();
    }
}
