/**
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays.stresstests;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.WorkdaysUnitOfTime;

/**
 * Stress tests for DefaultWorkdays.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class DefaultWorkdaysStressTest extends TestCase {

    /** Propertis configuration file name */
    private static final String CONFIG_PROPERTIES = "stresstests.properties";

    /** XML configuration file name */
    private static final String CONFIG_XML = "stresstests.xml";

    /** Max amount of non work days */
    private static final int LOOP = 10000;

    /** File containing configuration */
    private File file = null;

    /**
     * Clears after test.
     * 
     * @throws Exception propagated to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        if (file != null) {
            file.delete();
        }

        file = null;
    }

    /**
     * Measures the time of loading non work days from XML file.   
     * 
     * @throws Exception propagated to JUnit
     */
    public void testLoadingXML() throws Exception {
        generateXMLConfigFile();
        long start = System.currentTimeMillis();
        Workdays wd = new DefaultWorkdays(file.getAbsolutePath(), DefaultWorkdays.XML_FILE_FORMAT);
        long cost = System.currentTimeMillis() - start;

        assertEquals("Incorrect amount of non work days", LOOP, wd.getNonWorkdays().size());

        System.out.println("Loading a xml file that contains " + LOOP + " non work days costs " + cost + " ms.");

    }

    /**
     * Measures the time of loading non work days from properties file.   
     * 
     * @throws Exception propagated to JUnit
     */
    public void testLoadingProperties() throws Exception {
        generatePropertiesConfigFile();

        long start = System.currentTimeMillis();
        Workdays wd = new DefaultWorkdays(file.getAbsolutePath(), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
        long cost = System.currentTimeMillis() - start;

        assertEquals("Incorrect amount of non work days", LOOP, wd.getNonWorkdays().size());

        System.out.println("Loading a properties file that contains " + LOOP + " non work days costs " + cost + " ms.");

    }

    /**
     * Measures the time of saving non work days to properties file.   
     * 
     * @throws Exception propagated to JUnit
     */
    public void testSaveProperties() throws Exception {
        generatePropertiesConfigFile();
        DefaultWorkdays wd = new DefaultWorkdays(file.getAbsolutePath(), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
        wd.clearNonWorkdays();
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < LOOP; i++) {
            cal.add(Calendar.DATE, 1);
            wd.addNonWorkday(cal.getTime());
        }

        long start = System.currentTimeMillis();
        wd.save();
        long cost = System.currentTimeMillis() - start;

        System.out.println("Saving " + LOOP + " non work days to properties file costs " + cost + " ms.");
    }

    /**
     * Measures the time of saving non work days to XML file.   
     * 
     * @throws Exception propagated to JUnit
     */
    public void testSaveXML() throws Exception {
        generateXMLConfigFile();
        DefaultWorkdays wd = new DefaultWorkdays(file.getAbsolutePath(), DefaultWorkdays.XML_FILE_FORMAT);
        wd.clearNonWorkdays();
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < LOOP; i++) {
            cal.add(Calendar.DATE, 1);
            wd.addNonWorkday(cal.getTime());
        }

        long start = System.currentTimeMillis();
        wd.save();
        long cost = System.currentTimeMillis() - start;

        System.out.println("Saving " + LOOP + " non work days to xml file costs " + cost + " ms.");
    }

    /**
     * Measures the time of adding LOOP days. Sunday and Saturday are
     * non work days.   
     * 
     * @throws Exception propagated to JUnit
     */
    public void testAddWithFreeWeekends() throws Exception {
        Workdays wd = new DefaultWorkdays();

        long start = System.currentTimeMillis();
        wd.add(new Date(), WorkdaysUnitOfTime.DAYS, LOOP);
        long cost = System.currentTimeMillis() - start;

        System.out.println("Calculating end date for " + (LOOP) + " days costs " + cost + " ms.");
    }

    /**
     * Measures the time of adding LOOP days. Sunday is a free day.   
     * 
     * @throws Exception propagated to JUnit
     */
    public void testAddWithFreeSunday() throws Exception {
        Workdays wd = new DefaultWorkdays();
        wd.setSaturdayWorkday(true);

        long start = System.currentTimeMillis();
        wd.add(new Date(), WorkdaysUnitOfTime.DAYS, LOOP);

        long cost = System.currentTimeMillis() - start;

        System.out.println("Calculating end date for " + (LOOP) + " days costs " + cost + " ms.");
    }

    /**
     * Measures the time of adding LOOP days. Sunday and Saturday day are working days.
     * Also some non-work days is loaded from file.   
     * 
     * @throws Exception propagated to JUnit
     */
    public void testAddWithFreeDaysAndWorkingWeekends() throws Exception {
        generatePropertiesConfigFile();

        Workdays wd = new DefaultWorkdays(file.getAbsolutePath(), DefaultWorkdays.PROPERTIES_FILE_FORMAT);
        wd.setSaturdayWorkday(true);
        wd.setSundayWorkday(true);
        long start = System.currentTimeMillis();
        wd.add(new Date(), WorkdaysUnitOfTime.DAYS, LOOP);
        long cost = System.currentTimeMillis() - start;

        System.out.println("Calculating end date for " + LOOP + " period with " + LOOP + " free days " + "costs "
                + cost + " ms.");

    }

    /**
     * Generates propertires config file. This file has set only
     * required properties and has LOOP amount of non-working days.   
     * 
     * @throws Exception propagated to JUnit
     */
    private void generatePropertiesConfigFile() throws Exception {
        file = new File(CONFIG_PROPERTIES);

        PrintWriter pw = new PrintWriter(new FileWriter(file));
        pw.println("startTime.hours=9");
        pw.println("startTime.minutes=0");
        pw.println("endTime.hours=17");
        pw.println("endTime.minutes=0");
        pw.println("isSaturdayWorkday=false");
        pw.println("isSundayWorkday=false");
        pw.println("dateStyle=SHORT");
        pw.print("nonWorkdays=");

        Calendar cal = Calendar.getInstance();
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        for (int i = 0; i < LOOP; i++) {
            pw.print(df.format(cal.getTime()) + (i == LOOP - 1 ? "" : ";"));
            cal.add(Calendar.DATE, 1);
        }

        pw.close();
    }

    /**
     * Generates XML config file. This file has set only
     * required properties and has LOOP amount of non-working days.   
     * 
     * @throws Exception propagated to JUnit
     */
    private void generateXMLConfigFile() throws Exception {
        file = new File(CONFIG_XML);
        PrintWriter pw = new PrintWriter(new FileWriter(file));

        pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        pw.println("<CMConfig>");
        pw.println("<Property name=\"startTime\">");
        pw.println("<Property name=\"hours\"><Value>10</Value></Property>");
        pw.println("<Property name=\"minutes\"><Value>0</Value></Property>");
        pw.println("</Property>");

        pw.println("<Property name=\"endTime\">");
        pw.println("<Property name=\"hours\"><Value>20</Value></Property>");
        pw.println("<Property name=\"minutes\"><Value>30</Value></Property>");
        pw.println("</Property>");

        pw.println("<Property name=\"isSaturdayWorkday\"><Value>true</Value></Property>");

        pw.println("<Property name=\"isSundayWorkday\"><Value>false</Value></Property>");

        pw.println("<Property name=\"dateStyle\"><Value>SHORT</Value></Property>");

        pw.println("<Property name=\"nonWorkdays\">");

        Calendar cal = Calendar.getInstance();        
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        for (int i = 0; i < LOOP; i++) {
            pw.println("<Value>" + df.format(cal.getTime()) + "</Value>");
            cal.add(Calendar.DATE, 1);
        }
        pw.println("</Property>");
        pw.println("</CMConfig>");

        pw.close();

    }

}