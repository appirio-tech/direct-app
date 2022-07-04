/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.persistence.DummyPhaseTemplatePersistence;
import com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence;
import com.topcoder.project.phases.template.startdategenerator.DummyStartDateGenerator;

/**
 * <p>
 * Unit tests on class <code>DefaultPhaseTemplate</code>.
 * </p>
 * @author flying2hk
 * @version 1.1
 * @since 1.0
 */
public class UnitTestDefaultPhaseTemplate extends TestCase {
    /**
     * <p>
     * Configuration file.
     * </p>
     */
    private static final String CONFIG_FILE = "test_files/config/Project_Phase_Template_Config.xml";

    /**
     * <p>
     * Bad configuration file.
     * </p>
     */
    private static final String BAD_CONFIG_FILE = "test_files/bad_config/Default_Phase_Template_Bad_Config.xml";

    /**
     * <p>
     * Configuration namespace for <code>DefaultPhaseTemplate</code>.
     */
    private static final String NAMESPACE = "com.topcoder.project.phases.template.DefaultPhaseTemplate";

    /**
     * <p>
     * Configuration namespace for <code>DefaultPhaseTemplate</code>, in
     * which the persistence and start date generator are both configured to be
     * created from default constructors of dummy implementations. It will be
     * used in test case <code>testCtor1_Success2()</code>.
     * </p>
     */
    private static final String NAMESPACE_DUMMY = "com.topcoder.project.phases.template.DefaultPhaseTemplate.Dummy";

    /**
     * <p>
     * Template file for "UnknownPhaseType" template, this template is a bad
     * one.
     * </p>
     */
    private static final String TEMPLATE_FILE_UNKNOWN_PHASE_TYPE =
        "test_files/bad_template/BadTemplate_UnknownPhaseType.xml";

    /**
     * <p>
     * Template file for "UnknownPhase" template, this template is a bad one.
     * </p>
     */
    private static final String TEMPLATE_FILE_UNKNOWN_PHASE =
        "test_files/bad_template/BadTemplate_UnknownPhase.xml";

    /**
     * <p>
     * Template name for "Design" template.
     * </p>
     */
    private static final String TEMPLATE_DESIGN = "Design";

    /**
     * <p>
     * Template name for "Development" template.
     * </p>
     */
    private static final String TEMPLATE_DEVELOPMENT = "Development";

    /**
     * <p>
     * Template name for "UnknownPhaseType" template.
     * </p>
     */
    private static final String TEMPLATE_UNKNOWN_PHASE_TYPE = "UnknownPhaseType";

    /**
     * <p>
     * Template name for "UnknownPhase" template.
     * </p>
     */
    private static final String TEMPLATE_UNKNOWN_PHASE = "UnknownPhase";

    /**
     * <p>
     * Instance of <code>DefaultPhaseTemplate</code> used in this test.
     * </p>
     */
    private DefaultPhaseTemplate template = null;

    /**
     * <p>
     * Instance of <code>Date</code> as the start date used in this test.
     * </p>
     */
    private Date startDate = null;

    /**
     * <p>
     * Instance of <code>PhaseTemplatePersistence</code> used in this test.
     * </p>
     */
    private PhaseTemplatePersistence persistence = null;

    /**
     * <p>
     * Instance of <code>StartDateGenerator</code> used in this test.
     * </p>
     */
    private StartDateGenerator startDateGenerator = null;

    /**
     * <p>
     * Instance of <code>Workdays</code> used in this test.
     * </p>
     */
    private Workdays workdays = null;

    /**
     * <p>
     * Set up environment.
     * </p>
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        // load configurations
        Helper.loadConfig(CONFIG_FILE);
        Helper.loadConfig(BAD_CONFIG_FILE);

        // create the template
        template = new DefaultPhaseTemplate(NAMESPACE);

        // create needed objects
        Calendar calendar = Calendar.getInstance();
        calendar.set(2007, 2, 1);
        startDate = calendar.getTime();
        persistence = new DummyPhaseTemplatePersistence();
        startDateGenerator = new DummyStartDateGenerator();
        workdays = new DefaultWorkdays();
    }

    /**
     * <p>
     * Clear the configuration.
     * </p>
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception {
        Helper.clearConfig();
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor1_Success1() {
        assertNotNull("Failed to create DefaultPhaseTemplate.", template);
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for accuracy. The persistence and start date generator instances will be
     * created via default constructor.
     * </p>
     * @throws ConfigurationException to JUnit
     */
    public void testCtor1_Success2() throws ConfigurationException {
        template = new DefaultPhaseTemplate(NAMESPACE_DUMMY);
        assertNotNull("Failed to create DefaultPhaseTemplate.", template);
        assertTrue("Incorrect persistence.",
                template.getPersistence() instanceof DummyPhaseTemplatePersistence);
        assertTrue("Incorrect start date generator.",
                template.getStartDateGenerator() instanceof DummyStartDateGenerator);
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with null namespace, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     * @throws ConfigurationException to JUnit
     */
    public void testCtor1_NullNamespace() throws ConfigurationException {
        try {
            new DefaultPhaseTemplate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with empty namespace, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     * @throws ConfigurationException to JUnit
     */
    public void testCtor1_EmptyNamespace() throws ConfigurationException {
        try {
            new DefaultPhaseTemplate("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with empty namespace, <code>ConfigurationException</code>
     * is expected.
     * </p>
     */
    public void testCtor1_UnknownNamespace() {
        try {
            new DefaultPhaseTemplate("unknown");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which "persistence" property is
     * missing, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_NoPersistence() {
        try {
            new DefaultPhaseTemplate("no_persistence");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which "persistence.class" property
     * is missing, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_NoPersistenceClass() {
        try {
            new DefaultPhaseTemplate("no_persistence_class");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which "persistence.class" property
     * is unknown, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_UnknownPersistenceClass() {
        try {
            new DefaultPhaseTemplate("unknown_persistence_class");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which "persistence.namespace"
     * property is unknown for <code>XmlPhaseTemplatePersistence</code>,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_UnknownXmlPersistenceNamespace() {
        try {
            new DefaultPhaseTemplate("unknown_xml_persistence_namespace");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which "persistence.namespace"
     * property is missing for <code>XmlPhaseTemplatePersistence</code>,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_NoXmlPersistenceNamespace() {
        try {
            new DefaultPhaseTemplate("no_xml_persistence_namespace");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which "start_date_generator"
     * property is missing, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_NoStartDateGenerator() {
        try {
            new DefaultPhaseTemplate("no_start_date_generator");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which "start_date_generator.class"
     * property is missing, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_NoStartDateGeneratorClass() {
        try {
            new DefaultPhaseTemplate("no_start_date_generator_class");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which "start_date_generator.class"
     * property is unknown, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_UnknownStartDateGeneratorClass() {
        try {
            new DefaultPhaseTemplate("unknown_start_date_generator_class");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which
     * "start_date_generator.namespace" property is unknown for
     * <code>RelativeWeekTimeStartDateGenerator</code>,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_UnknownRelativeStartDateGeneratorNamespace() {
        try {
            new DefaultPhaseTemplate("unknown_relative_start_date_generator_namespace");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which
     * "start_date_generator.namespace" property is missing for
     * <code>XmlPhaseTemplatePersistence</code>,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_NoRelativeStartDateGeneratorNamespace() {
        try {
            new DefaultPhaseTemplate("no_relative_start_date_generator_namespace");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which "workdays" property is
     * missing, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_NoWorkdays() {
        try {
            new DefaultPhaseTemplate("no_workdays");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which
     * "workdays.object_specification_namespace" property is missing,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_NoWorkdaysNamespace() {
        try {
            new DefaultPhaseTemplate("no_workdays_namespace");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which "workdays.object_key"
     * property is missing, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_NoWorkdaysObjectKey() {
        try {
            new DefaultPhaseTemplate("no_workdays_object_key");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which
     * "workdays.object_specification_namespace" property is unknown,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_UnknownWorkdaysNamespace() {
        try {
            new DefaultPhaseTemplate("unknown_workdays_namespace");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>DefaultPhaseTemplate(String namespace)</code>
     * for failure with bad configuration in which "workdays.object_identifier"
     * property is unknown, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_UnknownWorkdaysIdentifier() {
        try {
            new DefaultPhaseTemplate("unknown_workdays_object_identifier");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor
     * <code>DefaultPhaseTemplate(PhaseTemplatePersistence persistence,
     * StartDateGenerator startDateGenerator, Workdays workdays)</code>
     * for failure with null persistence, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testCtor2_NullPersistence() {
        try {
            new DefaultPhaseTemplate(null, startDateGenerator, workdays);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor
     * <code>DefaultPhaseTemplate(PhaseTemplatePersistence persistence,
     * StartDateGenerator startDateGenerator, Workdays workdays)</code>
     * for failure with null startDateGenerator,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor2_NullStartDateGenerator() {
        try {
            new DefaultPhaseTemplate(persistence, null, workdays);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor
     * <code>DefaultPhaseTemplate(PhaseTemplatePersistence persistence,
     * StartDateGenerator startDateGenerator, Workdays workdays)</code>
     * for failure with null workdays, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     */
    public void testCtor2_NullWorkdays() {
        try {
            new DefaultPhaseTemplate(persistence, startDateGenerator, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor
     * <code>DefaultPhaseTemplate(PhaseTemplatePersistence persistence,
     * StartDateGenerator startDateGenerator, Workdays workdays)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor2_Success() {
        assertNotNull("Failed to create DefaultPhaseTemplate.", new DefaultPhaseTemplate(persistence,
                startDateGenerator, workdays));
    }

    /**
     * <p>
     * Tests method <code>getAllTemplateNames()</code> for accuracy.
     * </p>
     */
    public void testGetAllTemplateNames() {
        String[] names = template.getAllTemplateNames();
        assertEquals("Incorrect names.", 2, names.length);
        Set set = new HashSet();
        for (int i = 0; i < names.length; i++) {
            set.add(names[i]);
        }
        assertTrue("Incorrect names.", set.contains(TEMPLATE_DESIGN));
        assertTrue("Incorrect names.", set.contains(TEMPLATE_DEVELOPMENT));
    }

    /**
     * <p>
     * Tests method <code>applyTemplate(String templateName)</code> for
     * failure with null templateName, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     * @throws PhaseTemplateException to JUnit
     */
    public void testApplyTemplate1_NullTemplateName() throws PhaseTemplateException {
        try {
            template.applyTemplate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>applyTemplate(String templateName)</code> for
     * failure with empty templateName, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     * @throws PhaseTemplateException to JUnit
     */
    public void testApplyTemplate1_EmptyTemplateName() throws PhaseTemplateException {
        try {
            template.applyTemplate("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>applyTemplate(String templateName)</code> for
     * failure with unknown templateName, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     * @throws PhaseTemplateException to JUnit
     */
    public void testApplyTemplate1_UnknownTemplateName() throws PhaseTemplateException {
        try {
            template.applyTemplate("unknown");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>applyTemplate(String templateName)</code> for
     * failure. Some phase is associated with unknown phase type,
     * <code>PhaseGenerationException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testApplyTemplate1_UnknownPhaseTypeInTemplate() throws PersistenceException {
        template.setPersistence(new XmlPhaseTemplatePersistence(
                new String[] {TEMPLATE_FILE_UNKNOWN_PHASE_TYPE}));
        try {
            template.applyTemplate(TEMPLATE_UNKNOWN_PHASE_TYPE);
            fail("PhaseTemplateException expected.");
        } catch (PhaseTemplateException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>applyTemplate(String templateName)</code> for
     * failure. Some phase depends on unknown phase,
     * <code>PhaseGenerationException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testApplyTemplate1_UnknownPhaseInTemplate() throws PersistenceException {
        template.setPersistence(new XmlPhaseTemplatePersistence(new String[] {TEMPLATE_FILE_UNKNOWN_PHASE}));
        try {
            template.applyTemplate(TEMPLATE_UNKNOWN_PHASE);
            fail("PhaseTemplateException expected.");
        } catch (PhaseTemplateException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>applyTemplate(String templateName)</code> for
     * failure. Some phase depends on unknown phase,
     * <code>PhaseGenerationException</code> is expected.
     * </p>
     * @throws PhaseTemplateException to JUnit
     */
    public void testApplyTemplate1_Success() throws PhaseTemplateException {
        Project project = template.applyTemplate(TEMPLATE_DESIGN);
        // check the start date
        Helper.checkGeneratedStartDate(project.getStartDate());
        // check the generated phases
        Helper.checkSamplePhases(project.getAllPhases());
    }

    /**
     * <p>
     * Tests method
     * <code>applyTemplate(String templateName, Date startDate)</code> for
     * failure with null templateName, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     * @throws PhaseTemplateException to JUnit
     */
    public void testApplyTemplate2_NullTemplateName() throws PhaseTemplateException {
        try {
            template.applyTemplate(null, startDate);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method
     * <code>applyTemplate(String templateName, Date startDate)</code> for
     * failure with null startDate, <code>IllegalArgumentException</code> is
     * expected.
     * </p>
     * @throws PhaseTemplateException to JUnit
     */
    public void testApplyTemplate2_NullStartDate() throws PhaseTemplateException {
        try {
            template.applyTemplate(TEMPLATE_DESIGN, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method
     * <code>applyTemplate(String templateName, Date startDate)</code> for
     * failure with empty templateName, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     * @throws PhaseTemplateException to JUnit
     */
    public void testApplyTemplate2_EmptyTemplateName() throws PhaseTemplateException {
        try {
            template.applyTemplate("  ", startDate);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method
     * <code>applyTemplate(String templateName, Date startDate)</code> for
     * failure with unknown templateName, <code>IllegalArgumentException</code>
     * is expected.
     * </p>
     * @throws PhaseTemplateException to JUnit
     */
    public void testApplyTemplate2_UnknownTemplateName() throws PhaseTemplateException {
        try {
            template.applyTemplate("unknown", startDate);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>applyTemplate(String templateName)</code> for
     * failure. Some phase is associated with unknown phase type,
     * <code>PhaseGenerationException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testApplyTemplate2_UnknownPhaseTypeInTemplate() throws PersistenceException {
        template.setPersistence(new XmlPhaseTemplatePersistence(
                new String[] {TEMPLATE_FILE_UNKNOWN_PHASE_TYPE}));
        try {
            template.applyTemplate(TEMPLATE_UNKNOWN_PHASE_TYPE, startDate);
            fail("PhaseTemplateException expected.");
        } catch (PhaseTemplateException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method
     * <code>applyTemplate(String templateName, Date startDate)</code> for
     * failure. Some phase depends on unknown phase,
     * <code>PhaseGenerationException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testApplyTemplate2_UnknownPhaseInTemplate() throws PersistenceException {
        template.setPersistence(new XmlPhaseTemplatePersistence(new String[] {TEMPLATE_FILE_UNKNOWN_PHASE}));
        try {
            template.applyTemplate(TEMPLATE_UNKNOWN_PHASE, startDate);
            fail("PhaseTemplateException expected.");
        } catch (PhaseTemplateException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method
     * <code>applyTemplate(String templateName, Date startDate)</code> for
     * failure. Some phase depends on unknown phase,
     * <code>PhaseGenerationException</code> is expected.
     * </p>
     * @throws PhaseTemplateException to JUnit
     */
    public void testApplyTemplate2_Success() throws PhaseTemplateException {
        Project project = template.applyTemplate(TEMPLATE_DESIGN, startDate);
        // check the start date
        assertEquals("Incorrect start date.", startDate, project.getStartDate());
        // check the generated phases
        Helper.checkSamplePhases(project.getAllPhases());
    }

    /**
     * <p>
     * Tests method
     * <code>setPersistence(PhaseTemplatePersistence persistence)</code> for
     * failure with null persistence, <code>IllegalArgumentException</code> is
     * expected.
     * </p>
     */
    public void testSetPersistence_NullPersistence() {
        try {
            template.setPersistence(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method
     * <code>setPersistence(PhaseTemplatePersistence persistence)</code> for
     * accuracy.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testSetPersistence_Success() throws PersistenceException {
        template.setPersistence(persistence);
        assertEquals("Incorrect persistence.", persistence, template.getPersistence());
    }

    /**
     * <p>
     * Tests method <code>getPersistence()</code> for accuracy.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testGetPersistence() throws PersistenceException {
        template.setPersistence(persistence);
        assertEquals("Incorrect persistence.", persistence, template.getPersistence());
    }

    /**
     * <p>
     * Tests method
     * <code>setStartDateGenerator(StartDateGenerator startDateGenerator)</code>
     * for failure with null startDateGenerator,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testSetStartDateGenerator_NullStartDateGenerator() {
        try {
            template.setStartDateGenerator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method
     * <code>setStartDateGenerator(StartDateGenerator startDateGenerator)</code>
     * for accuracy.
     * </p>
     */
    public void testSetStartDateGenerator_Success() {
        template.setStartDateGenerator(startDateGenerator);
        assertEquals("Incorrect start date generator.", startDateGenerator, template.getStartDateGenerator());
    }

    /**
     * <p>
     * Tests method <code>getStartDateGenerator()</code> for accuracy.
     * </p>
     */
    public void testGetStartDateGenerator_Success() {
        template.setStartDateGenerator(startDateGenerator);
        assertEquals("Incorrect start date generator.", startDateGenerator, template.getStartDateGenerator());
    }

    /**
     * <p>
     * Tests method <code>getWorkdays()</code> for accuracy.
     * </p>
     */
    public void testGetWorkdays() {
        template.setWorkdays(workdays);
        assertEquals("Incorrect workdays.", workdays, template.getWorkdays());
    }

    /**
     * <p>
     * Tests method <code>setWorkdays(Workdays workdays)</code> for failure
     * with null workdays, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testSetWorkdays_NullWorkdays() {
        try {
            template.setWorkdays(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>setWorkdays(Workdays workdays)</code> for accuracy.
     * </p>
     */
    public void testSetWorkdays_Success() {
        Workdays myWorkdays = new DefaultWorkdays();
        template.setWorkdays(myWorkdays);
        assertEquals("Incorrect workdays.", myWorkdays, template.getWorkdays());
    }
}
