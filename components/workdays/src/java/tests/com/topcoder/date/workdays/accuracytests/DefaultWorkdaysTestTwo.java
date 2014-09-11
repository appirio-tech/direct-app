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
public class DefaultWorkdaysTestTwo extends TestCase {
    
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
     * Represents the file name of config file.
     * </p>
     */
    private String fileName = "test_files/accuracytests/workdaysDE.xml";
    
    /**
     * <p>
     * Represents the file name of config file.
     * </p>
     */
    private String fileNameBackUp = "test_files/accuracytests/workdaysDEBackUp.xml";
    
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
    public DefaultWorkdaysTestTwo(String name) {
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
        locale = new Locale("de", "DE");
        dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", this.locale);
        
        this.wd = new DefaultWorkdays(this.fileName, DefaultWorkdays.XML_FILE_FORMAT);
        
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
        return new TestSuite(DefaultWorkdaysTestTwo.class);
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
    } // end testAddAccuracy()
    
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
        File file = new File(this.fileName);
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
