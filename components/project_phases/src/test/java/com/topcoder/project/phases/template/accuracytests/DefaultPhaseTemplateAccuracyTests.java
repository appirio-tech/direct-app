/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.accuracytests;

import java.util.Date;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.DefaultPhaseTemplate;
import com.topcoder.project.phases.template.PhaseTemplate;
import com.topcoder.project.phases.template.PhaseTemplatePersistence;
import com.topcoder.project.phases.template.StartDateGenerator;
import com.topcoder.util.config.ConfigManager;

/**
 * The class <code>DefaultPhaseTemplateAccuracyTests</code> contains tests for the class
 * {@link <code>DefaultPhaseTemplate</code>}.
 * @author FireIce
 * @version 1.0
 */
public class DefaultPhaseTemplateAccuracyTests extends TestCase {

    /**
     * <p>
     * Represents the <code>DefaultPhaseTemplate</code> instance used in tests.
     * </p>
     */
    private DefaultPhaseTemplate defaultPhaseTemplate;

    /**
     * Run the DefaultPhaseTemplate(String) constructor test.
     * @throws Exception
     */
    public void testDefaultPhaseTemplateAccuracy1() throws Exception {
        String namespace = "com.topcoder.project.phases.template.DefaultPhaseTemplate1";
        defaultPhaseTemplate = new DefaultPhaseTemplate(namespace);
        assertNotNull(defaultPhaseTemplate);
        assertNotNull(defaultPhaseTemplate.getPersistence());
        assertNotNull(defaultPhaseTemplate.getStartDateGenerator());
        assertNotNull(defaultPhaseTemplate.getWorkdays());
    }

    /**
     * Run the DefaultPhaseTemplate(String) constructor test.
     * @throws Exception
     */
    public void testDefaultPhaseTemplateAccuracy2() throws Exception {
        String namespace = "com.topcoder.project.phases.template.DefaultPhaseTemplate2";
        defaultPhaseTemplate = new DefaultPhaseTemplate(namespace);
        assertNotNull(defaultPhaseTemplate);
        assertNotNull(defaultPhaseTemplate.getPersistence());
        assertNotNull(defaultPhaseTemplate.getStartDateGenerator());
        assertNotNull(defaultPhaseTemplate.getWorkdays());
    }

    /**
     * Run the getAllTemplateNames() method test.
     */
    public void testGetAllTemplateNamesAccuracy() {
        String[] result = defaultPhaseTemplate.getAllTemplateNames();
        assertNotNull(result);
        assertEquals("the array size should be 0.", 0, result.length);
    }

    /**
     * Run the getPersistence() method test.
     */
    public void testGetPersistenceAccuracy() {
        assertNotNull(defaultPhaseTemplate.getPersistence());
    }

    /**
     * Run the setPersistence(PhaseTemplatePersistence) method test.
     */
    public void testSetPersistenceAccuracy() {
        PhaseTemplatePersistence persistence = new MockPhaseTemplatePersistence();
        defaultPhaseTemplate.setPersistence(persistence);
        assertEquals("field not updated", persistence, defaultPhaseTemplate.getPersistence());
    }

    /**
     * Run the getStartDateGenerator() method test.
     */
    public void testGetStartDateGeneratorAccuracy() {
        assertNotNull(defaultPhaseTemplate.getStartDateGenerator());
    }

    /**
     * Run the setStartDateGenerator(StartDateGenerator) method test.
     */
    public void testSetStartDateGeneratorAccuracy() {
        StartDateGenerator startDateGenerator = new MockStartDateGenerator();
        defaultPhaseTemplate.setStartDateGenerator(startDateGenerator);
        assertEquals("field not updated.", startDateGenerator, defaultPhaseTemplate.getStartDateGenerator());
    }

    /**
     * Run the getWorkdays() method test.
     */
    public void testGetWorkdaysAccuracy() {
        assertNotNull(defaultPhaseTemplate.getWorkdays());
    }

    /**
     * Run the applyTemplate(String,Date) method test.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testApplyTemplate1Accuracy() throws Exception {
        Project project = defaultPhaseTemplate.applyTemplate("FireIce", new Date());
        assertNotNull(project);
        assertNotNull(project.getAllPhases());
        assertEquals("incorrect phase number.", 5, project.getAllPhases().length);

        project = defaultPhaseTemplate.applyTemplate("NotFireIce", new Date());
        assertEquals("incorrect phase number.", 6, project.getAllPhases().length);
    }
    
    /**
     * Run the applyTemplate(String,Date) method test.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testApplyTemplate2Accuracy() throws Exception {
        Project project = defaultPhaseTemplate.applyTemplate("FireIce");
        assertNotNull(project);
        assertNotNull(project.getAllPhases());
        assertEquals("incorrect phase number.", 5, project.getAllPhases().length);

        project = defaultPhaseTemplate.applyTemplate("NotFireIce");
        assertEquals("incorrect phase number.", 6, project.getAllPhases().length);
    }

    /**
     * Run the setWorkdays(Workdays) method test.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSetWorkdaysAccuracy() throws Exception {
        Workdays workdays = new DefaultWorkdays("test_files/accuracytests/workdays.xml", "XML");
        defaultPhaseTemplate.setWorkdays(workdays);
        assertEquals("field not updated.", workdays, defaultPhaseTemplate.getWorkdays());
    }

    /**
     * Perform pre-test initialization.
     * @throws Exception
     *             if the initialization fails for some reason
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add("test_files/accuracytests/WorkdaysObjectFactoryConfig.xml");
        configManager.add("test_files/accuracytests/DefaultPhaseTemplate.xml");
        defaultPhaseTemplate = new DefaultPhaseTemplate(new MockPhaseTemplatePersistence(),
            new MockStartDateGenerator(), new DefaultWorkdays("test_files/accuracytests/workdays.xml", "XML"));
    }

    /**
     * Perform post-test clean-up.
     * @throws Exception
     *             if the clean-up fails for some reason
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        ConfigManager configManager = ConfigManager.getInstance();
        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Test inheritance.
     * </p>
     * <p>
     * <code>DefaultPhaseTemplate</code> should implement the <code>PhaseTemplate</code> interface.
     * </p>
     */
    public void testImplemantation() {
        assertTrue("inheritance fails", defaultPhaseTemplate instanceof PhaseTemplate);
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DefaultPhaseTemplateAccuracyTests.class);
    }
}
