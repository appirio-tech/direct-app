/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.stresstests;

import java.util.Date;
import java.util.Iterator;

import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.DefaultPhaseTemplate;
import com.topcoder.project.phases.template.PhaseTemplatePersistence;
import com.topcoder.project.phases.template.StartDateGenerator;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * The stress testing for Project Phase Template component.
 *
 * @author cmax
 * @version 1.0
 */
public class ProjectPhaseTemplateStressTest extends TestCase {
    /** Represents the small scale of stress test, the number of repeating the tests is only one. */
    private static final int SMALL = 1;

    /** Represents the medium scale of stress test, the number of repeating the tests is ten. */
    private static final int MEDIUM = 10;

    /** Represents the large scale of stress test, the number of repeating the tests is one hundred. */
    private static final int LARGE = 100;

    /** Represents the huge scale of stress test, the number of repeating the tests is one thousand. */
    private static final int HUGE = 1000;

    /** Represents the configuration file for default phase template. */
    private static final String DEFAULT_PHASE_TEMPLATE_CONFIG_FILE = "stresstests/Default_Phase_Template.xml";

    /** Represents the configuration file for xml phase template persistence. */
    private static final String XML_PHASE_TEMPLATE_PERSISTENCE_CONFIG_FILE = "stresstests/Xml_Phase_Temp"
            + "late_Persistence.xml";

    /** Represents the configuration file for relative week time start date generator phase template. */
    private static final String RELATIVE_WEEK_TIME_START_CONFIG_FILE = "stresstests/Relative_Week_Time_Start_"
            + "Date_Generator.xml";

    /** Represents current time. */
    private static long currentTime = -1;

    /**
     * Load namespace for testing.
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        loadNamespaces();
    }

    /**
     * Clears the configuration namespaces.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void tearDown() throws Exception {
        releaseNamespaces();
    }

    /**
     * Loads the namespaces under the default configuration file.
     *
     * @throws Exception to JUnit
     */
    private static void loadNamespaces() throws Exception {
        releaseNamespaces();

        ConfigManager config = ConfigManager.getInstance();

        config.add(DEFAULT_PHASE_TEMPLATE_CONFIG_FILE);
        config.add(XML_PHASE_TEMPLATE_PERSISTENCE_CONFIG_FILE);
        config.add(RELATIVE_WEEK_TIME_START_CONFIG_FILE);
    }

    /**
     * Clears all the namespaces.
     *
     * @throws Exception to JUnit
     */
    private static void releaseNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator itor = cm.getAllNamespaces(); itor.hasNext();) {
            try {
                cm.removeNamespace((String) itor.next());
            } catch (Exception e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Stress test of the applyTemplate(String, Date) method of DefaultPhaseTemplate, will mark down the performence of
     * the component in small scale test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateInSamllScale() throws Exception {
        DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                "com.topcoder.project.phases.template.DefaultPhaseTemplate");
        assertNotNull("Fail to create DefaultPhaseTemplate instance", template);

        int i;

        // Test of samll scale.
        start();

        for (i = 0; i < SMALL; i++) {
            template.applyTemplate("TCS Component Project", new Date());
        }

        printResult("applyTemplate(String, Date)", SMALL);

    }

    /**
     * <p>
     * Stress test of the applyTemplate(String, Date) method of DefaultPhaseTemplate, will mark down the performence of
     * the component in medium scale test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateInMediumScale() throws Exception {
        DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                "com.topcoder.project.phases.template.DefaultPhaseTemplate");
        assertNotNull("Fail to create DefaultPhaseTemplate instance", template);

        int i;

        // Test of medium scale.
        start();

        for (i = 0; i < MEDIUM; i++) {
            template.applyTemplate("TCS Component Project", new Date());
        }

        printResult("applyTemplate(String, Date)", MEDIUM);

    }

    /**
     * <p>
     * Stress test of the applyTemplate(String, Date) method of DefaultPhaseTemplate, will mark down the performence of
     * the component in large scale test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateInLargeScale() throws Exception {
        DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                "com.topcoder.project.phases.template.DefaultPhaseTemplate");
        assertNotNull("Fail to create DefaultPhaseTemplate instance", template);

        int i;

        // Test of large scale.
        start();

        for (i = 0; i < LARGE; i++) {
            template.applyTemplate("TCS Component Project", new Date());
        }

        printResult("applyTemplate(String, Date)", LARGE);

    }

    /**
     * <p>
     * Stress test of the applyTemplate(String, Date) method of DefaultPhaseTemplate, will mark down the performence of
     * the component in huge scale test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateInHugeScale() throws Exception {
        DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                "com.topcoder.project.phases.template.DefaultPhaseTemplate");
        assertNotNull("Fail to create DefaultPhaseTemplate instance", template);

        int i;

        // Test of huge scale.
        start();

        for (i = 0; i < HUGE; i++) {
            template.applyTemplate("TCS Component Project", new Date());
        }

        printResult("applyTemplate(String, Date)", HUGE);

    }

    /**
     * <p>
     * Stress test of the generatePhases(Date, Workdays) method of XmlPhaseTemplatePersistence, will mark down the
     * performence of the component in small scale test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhasesInSmallScale() throws Exception {
        DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                "com.topcoder.project.phases.template.DefaultPhaseTemplate");
        assertNotNull("Fail to create DefaultPhaseTemplate instance", template);
        PhaseTemplatePersistence persistence = template.getPersistence();

        int i;

        Project project = new Project(template.getStartDateGenerator().generateStartDate(), template.getWorkdays());

        // Test of small scale.
        start();

        for (i = 0; i < SMALL; i++) {
            persistence.generatePhases("TCS Component Project", project);
        }

        printResult("generatePhases(Date, Workdays)", SMALL);
    }

    /**
     * <p>
     * Stress test of the generatePhases(Date, Workdays) method of XmlPhaseTemplatePersistence, will mark down the
     * performence of the component in medium scale test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhasesInMediumScale() throws Exception {
        DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                "com.topcoder.project.phases.template.DefaultPhaseTemplate");
        assertNotNull("Fail to create DefaultPhaseTemplate instance", template);
        PhaseTemplatePersistence persistence = template.getPersistence();

        int i;

        Project project = new Project(template.getStartDateGenerator().generateStartDate(), template.getWorkdays());

        // Test of medium scale.
        start();

        for (i = 0; i < MEDIUM; i++) {
            persistence.generatePhases("TCS Component Project", project);
        }

        printResult("generatePhases(Date, Workdays)", MEDIUM);
    }

    /**
     * <p>
     * Stress test of the generatePhases(Date, Workdays) method of XmlPhaseTemplatePersistence, will mark down the
     * performence of the component in large scale test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhasesInLargeScale() throws Exception {
        DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                "com.topcoder.project.phases.template.DefaultPhaseTemplate");
        assertNotNull("Fail to create DefaultPhaseTemplate instance", template);
        PhaseTemplatePersistence persistence = template.getPersistence();

        int i;

        Project project = new Project(template.getStartDateGenerator().generateStartDate(), template.getWorkdays());

        // Test of large scale.
        start();

        for (i = 0; i < LARGE; i++) {
            persistence.generatePhases("TCS Component Project", project);
        }

        printResult("generatePhases(Date, Workdays)", LARGE);
    }

    /**
     * <p>
     * Stress test of the generatePhases(Date, Workdays) method of XmlPhaseTemplatePersistence, will mark down the
     * performence of the component in huge scale test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    /*public void testGeneratePhasesInHugeScale() throws Exception {
        DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                "com.topcoder.project.phases.template.DefaultPhaseTemplate");
        assertNotNull("Fail to create DefaultPhaseTemplate instance", template);
        PhaseTemplatePersistence persistence = template.getPersistence();

        int i;

        Project project = new Project(template.getStartDateGenerator().generateStartDate(), template.getWorkdays());

        // Test of huge scale.
        start();

        for (i = 0; i < HUGE; i++) {
            persistence.generatePhases("TCS Component Project", project);
        }

        printResult("generatePhases(Date, Workdays)", HUGE);
    }*/

    /**
     * <p>
     * Stress test of the generateStartDate() method of RelativeWeekTimeStartDateGenerator, will mark down the
     * performence of the component in huge scale test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGenerateStartDateInSmallScale() throws Exception {
        DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                "com.topcoder.project.phases.template.DefaultPhaseTemplate");
        assertNotNull("Fail to create DefaultPhaseTemplate instance", template);

        StartDateGenerator generator = template.getStartDateGenerator();

        int i;

        // Test of small scale.
        start();

        for (i = 0; i < SMALL; i++) {
            generator.generateStartDate();
        }

        printResult("generateStartDate()", SMALL);
    }

    /**
     * <p>
     * Stress test of the generateStartDate() method of RelativeWeekTimeStartDateGenerator, will mark down the
     * performence of the component in medium scale test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGenerateStartDateInMediumScale() throws Exception {
        DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                "com.topcoder.project.phases.template.DefaultPhaseTemplate");
        assertNotNull("Fail to create DefaultPhaseTemplate instance", template);

        StartDateGenerator generator = template.getStartDateGenerator();

        int i;

        // Test of medium scale.
        start();

        for (i = 0; i < MEDIUM; i++) {
            generator.generateStartDate();
        }

        printResult("generateStartDate()", MEDIUM);
    }

    /**
     * <p>
     * Stress test of the generateStartDate() method of RelativeWeekTimeStartDateGenerator, will mark down the
     * performence of the component in large scale test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGenerateStartDateInLargeScale() throws Exception {
        DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                "com.topcoder.project.phases.template.DefaultPhaseTemplate");
        assertNotNull("Fail to create DefaultPhaseTemplate instance", template);

        StartDateGenerator generator = template.getStartDateGenerator();

        int i;

        // Test of large scale.
        start();

        for (i = 0; i < LARGE; i++) {
            generator.generateStartDate();
        }

        printResult("generateStartDate()", LARGE);
    }

    /**
     * <p>
     * Stress test of the generateStartDate() method of RelativeWeekTimeStartDateGenerator, will mark down the
     * performence of the component in huge scale test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGenerateStartDateInHugeScale() throws Exception {
        DefaultPhaseTemplate template = new DefaultPhaseTemplate(
                "com.topcoder.project.phases.template.DefaultPhaseTemplate");
        assertNotNull("Fail to create DefaultPhaseTemplate instance", template);

        StartDateGenerator generator = template.getStartDateGenerator();

        int i;

        // Test of huge scale.
        start();

        for (i = 0; i < HUGE; i++) {
            generator.generateStartDate();
        }

        printResult("generateStartDate()", HUGE);
    }

    /**
     * Start to time the test.
     */
    private static void start() {
        currentTime = System.currentTimeMillis();
    }

    /**
     * Print the test information.
     *
     * @param name the test name
     * @param number the number of the test repeated
     */
    private static void printResult(String name, int number) {
        System.out.println("Test " + name + " for " + number + " time(s) cost: "
                + (System.currentTimeMillis() - currentTime) + "ms");
    }
}
