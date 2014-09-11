/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.util.Calendar;
import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence;
import com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator;

import junit.framework.TestCase;

/**
 * <p>
 * This test case demonstrates the usage of the <b>Project Phase Template</b>
 * component.
 * </p>
 * <p>
 * A project is usually executed in a predefined set of phases for a particular customer.
 * Requiring the user to manually define the phase hierarchy is laborious and unnecessary.
 * The component provides a template mechanism to handle this scenario.
 * Template storage is pluggable and can be added without code changes.
 * An XML storage is provided with this release.
 * </p>
 * <p>
 * The following use scenarios are covered in this demo:
 * </p>
 * <ul>
 * <li>
 * Create the DefaultPhaseTemplate from configuration.
 * </li>
 * <li>
 * Create the DefaultPhaseTemplate from given PhaseTemplatePersistence, StartDateGenerator and Workdays.
 * </li>
 * <li>
 * Retrieve all template names.
 * </li>
 * <li>
 * Apply a template to generate project phases.
 * </li>
 * <li>
 * Change template persistence, start date generator or workdays dynamically.
 * </li>
 * <li>
 * Other misc scenarios.
 * </li>
 * </ul>
 * @author flying2hk
 * @version 1.1
 * @since 1.0
 */
public class Demo extends TestCase {
    /**
     * <p>
     * Configuration file for the demonstration.
     * </p>
     */
    private static final String DEMO_CONFIG_FILE = "test_files/config/Demo_Config.xml";

    /**
     * <p>
     * Set up environment.
     * </p>
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        Helper.loadConfig(DEMO_CONFIG_FILE);
    }

    /**
     * <p>
     * Clear the configurations.
     * </p>
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception {
        Helper.clearConfig();
    }

    /**
     * <p>
     * Demonstration of <b>Project Phase Template</b> component.
     * </p>
     * <p>
     * The following use scenarios are covered in this demo:
     * </p>
     * <ul>
     * <li>
     * Create the DefaultPhaseTemplate from configuration.
     * </li>
     * <li>
     * Create the DefaultPhaseTemplate from given PhaseTemplatePersistence, StartDateGenerator and Workdays.
     * </li>
     * <li>
     * Retrieve all template names.
     * </li>
     * <li>
     * Apply a template to generate project phases.
     * </li>
     * <li>
     * Change template persistence, start date generator or workdays dynamically.
     * </li>
     * <li>
     * Other misc scenarios.
     * </li>
     * </ul>
     */
    public void testDemo() {
        try {
            /*
             * 1. Create the DefaultPhaseTemplate from configuration.
             */
            DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                    "com.topcoder.project.phases.template.DefaultPhaseTemplate");
            /*
             * 2. Create the DefaultPhaseTemplate from given PhaseTemplatePersistence,
             * StartDateGenerator and Workdays.
             */
            // create XmlPhaseTemplatePersistence from configuration
            PhaseTemplatePersistence persistence = new XmlPhaseTemplatePersistence(
                    "com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence");
            // create RelativeWeekTimeStartDateGenerator from configuration
            StartDateGenerator startDateGenerator = new RelativeWeekTimeStartDateGenerator(
                    "com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator");
            // create Workdays object
            Workdays workdays = new DefaultWorkdays();
            // create DefaultPhaseTemplate
            template = new DefaultPhaseTemplate(persistence, startDateGenerator, workdays);
            /*
             * 3. Retrieve all template names.
             */
            String[] templateNames = template.getAllTemplateNames();
            // print all template names
            for (int i = 0; i < templateNames.length; i++) {
                System.out.println(templateNames[i]);
            }
            /*
             * 4. Apply a template to generate project phases.
             */
            // apply a template without start date
            Project project = template.applyTemplate("Design");
            // apply a template with given start date
            Date startDate = Calendar.getInstance().getTime();
            project = template.applyTemplate("Development", startDate);
            // adjust the start date after generation
            project.setStartDate(Calendar.getInstance().getTime());
            /*
             * 5. Change template persistence, start date generator
             * or workdays dynamically.
             */
            // create XmlPhaseTemplatePersistence from file names
            persistence = new XmlPhaseTemplatePersistence(
                    new String[] {"test_files/template/TCSTemplate_Design.xml"});
            // change persistence
            template.setPersistence(persistence);

            // create RelativeWeekTimeStartDateGenerator from scratch
            startDateGenerator = new RelativeWeekTimeStartDateGenerator(Calendar.THURSDAY, 8, 30, 0, 2);
            // change start date generator
            template.setStartDateGenerator(startDateGenerator);

            // create workdays from scratch
            workdays = new DefaultWorkdays("config/workdays.xml", "XML");
            // change workdays
            template.setWorkdays(workdays);
            /*
             * 6. Others
             */
            // retrieve the persistence of a template
            PhaseTemplatePersistence myPersistence = template.getPersistence();
            // retrieve the start date generator of a template
            StartDateGenerator myStartDateGenerator = template.getStartDateGenerator();
            // retrieve the workdays of a template
            Workdays myWorkdays = template.getWorkdays();
            // retrieve time components from RelativeWeekTimeStartDateGenerator
            RelativeWeekTimeStartDateGenerator rwtStartDateGenerator =
                (RelativeWeekTimeStartDateGenerator) myStartDateGenerator;
            System.out.println("DayOfWeek : " + rwtStartDateGenerator.getDayOfWeek());
            System.out.println("Hour : " + rwtStartDateGenerator.getHour());
            System.out.println("Minute : " + rwtStartDateGenerator.getMinute());
            System.out.println("Second : " + rwtStartDateGenerator.getSecond());
            System.out.println("WeekOffset : " + rwtStartDateGenerator.getWeekOffset());
        } catch (ConfigurationException ce) {
            // add code to handle ConfigurationException
            ce.printStackTrace();
        } catch (PhaseTemplateException pte) {
            // add code to handle PhaseTemplateException
            pte.printStackTrace();
        } catch (Exception ex) {
            // add code to handle other exception
            ex.printStackTrace();
        }
    }
}