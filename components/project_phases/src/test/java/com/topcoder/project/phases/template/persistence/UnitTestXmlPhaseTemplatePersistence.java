/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.Helper;
import com.topcoder.project.phases.template.PersistenceException;
import com.topcoder.project.phases.template.PhaseGenerationException;

/**
 * <p>
 * Unit tests on class <code>XmlPhaseTemplatePersistence</code>.
 * </p>
 * @author flying2hk
 * @version 1.0
 */
public class UnitTestXmlPhaseTemplatePersistence extends TestCase {

    /**
     * <p>
     * Template file for "Design" template.
     * </p>
     */
    private static final String TEMPLATE_FILE_DESIGN = "test_files/template/TCSTemplate_Design.xml";

    /**
     * <p>
     * Template file for "Development" template.
     * </p>
     */
    private static final String TEMPLATE_FILE_DEVELOPMENT = "test_files/template/TCSTemplate_Development.xml";

    /**
     * <p>
     * Template file for "UnknownPhaseType" template, this template is a bad one.
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
     * Template file for "EmptyName" template, this template is a bad one.
     * </p>
     */
    private static final String TEMPLATE_FILE_EMPTY_NAME =
        "test_files/bad_template/BadTemplate_EmptyName.xml";

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
     * Configuration file.
     * </p>
     */
    private static final String CONFIG_FILE = "test_files/config/Project_Phase_Template_Config.xml";

    /**
     * <p>
     * Bad configuration file.
     * </p>
     */
    private static final String BAD_CONFIG_FILE =
        "test_files/bad_config/Xml_Phase_Template_Persistence_Bad_Config.xml";

    /**
     * <p>
     * Configuration namespace.
     * </p>
     */
    private static final String CONFIG_NAMESPACE =
        "com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence";

    /**
     * <p>
     * Instance of <code>XmlPhaseTemplatePersistence</code> used in this test.
     * </p>
     */
    private XmlPhaseTemplatePersistence persistence1 = null;

    /**
     * <p>
     * Instance of <code>XmlPhaseTemplatePersistence</code> used in this test.
     * </p>
     */
    private XmlPhaseTemplatePersistence persistence2 = null;

    /**
     * <p>
     * Instance of <code>Project</code> used in this test.
     * </p>
     */
    private Project project = null;

    /**
     * <p>
     * Instance of <code>Workdays</code> used in this test.
     * </p>
     */
    private Workdays workdays = null;

    /**
     * <p>
     * Instance of <code>Date</code> as the start date used in this test.
     * </p>
     */
    private Date startDate = null;

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

        // create persistence instances
        this.persistence1 = new XmlPhaseTemplatePersistence(CONFIG_NAMESPACE);
        this.persistence2 = new XmlPhaseTemplatePersistence(
                new String[] {TEMPLATE_FILE_DESIGN, TEMPLATE_FILE_DEVELOPMENT});

        // create needed objects
        startDate = Calendar.getInstance().getTime();
        workdays = new DefaultWorkdays();
        project = new Project(startDate, workdays);
    }

    /**
     * <p>
     * Clear the environment.
     * </p>
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception {
        Helper.clearConfig();
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String namespace)</code>
     * for accuracy.
     * </p>
     */
    public void testCtor1_Success() {
        assertNotNull("Failed to create XmlPhaseTemplatePersistence.", this.persistence1);
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String namespace)</code> for failure
     * with null namespace, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws ConfigurationException to JUnit
     */
    public void testCtor1_NullNamespace() throws ConfigurationException {
        try {
            new XmlPhaseTemplatePersistence((String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String namespace)</code> for failure
     * with empty namespace, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws ConfigurationException to JUnit
     */
    public void testCtor1_EmptyNamespace() throws ConfigurationException {
        try {
            new XmlPhaseTemplatePersistence("   ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String namespace)</code> for failure
     * with unknown namespace, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_UnknownNamespace() {
        try {
            new XmlPhaseTemplatePersistence("unknown");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String namespace)</code> for failure
     * with bad config in which "template_files" property is missing,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_NoTemplateFilesProperty() {
        try {
            new XmlPhaseTemplatePersistence("no_template_files_property");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String namespace)</code> for failure
     * with bad config in which "template_files" property has no files,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_NoTemplateFiles() {
        try {
            new XmlPhaseTemplatePersistence("no_files");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String namespace)</code> for failure
     * with bad config in which "template_files" property has empty filename,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_EmptyFilename() {
        try {
            new XmlPhaseTemplatePersistence("empty_filename");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String namespace)</code> for failure
     * with bad config in which "template_files" property has nonexist filename,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_NonExistFilename() {
        try {
            new XmlPhaseTemplatePersistence("nonexist_filename");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String namespace)</code> for failure
     * with bad config in which "template_files" property has malformed file,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_MalformedFile() {
        try {
            new XmlPhaseTemplatePersistence("malformed_file");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String namespace)</code> for failure
     * with bad config in which "template_files" property has invalid file,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_InvalidFile() {
        try {
            new XmlPhaseTemplatePersistence("invalid_file");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String namespace)</code> for failure
     * with bad config in which "template_files" property has template which has an empty name,
     * <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor1_EmptyTemplateName() {
        try {
            new XmlPhaseTemplatePersistence("empty_template_name");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String[] fileNames)</code> for failure
     * with null fileNames, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testCtor2_NullFileNames() throws PersistenceException {
        try {
            new XmlPhaseTemplatePersistence((String[]) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String[] fileNames)</code> for failure
     * with empty array of fileNames, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testCtor2_EmptyFileNames() throws PersistenceException {
        try {
            new XmlPhaseTemplatePersistence(new String[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String[] fileNames)</code> for failure
     * with invalid fileNames in which some element is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testCtor2_NullElementInFileNames() throws PersistenceException {
        try {
            new XmlPhaseTemplatePersistence(new String[] {null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String[] fileNames)</code> for failure
     * with invalid fileNames in which some element is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testCtor2_EmptyElementInFileNames() throws PersistenceException {
        try {
            new XmlPhaseTemplatePersistence(new String[] {"  "});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String[] fileNames)</code> for failure
     * with invalid fileNames in which some file doesn't exist, <code>PersistenceException</code> is expected.
     * </p>
     */
    public void testCtor2_NonexistFileInFileNames() {
        try {
            new XmlPhaseTemplatePersistence(new String[] {"test_files/nonexist.xml"});
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String[] fileNames)</code> for failure
     * with invalid fileNames in which some file is malformed xml, <code>PersistenceException</code>
     * is expected.
     * </p>
     */
    public void testCtor2_MalformedXmlFileInFileNames() {
        try {
            new XmlPhaseTemplatePersistence(new String[] {"test_files/bad_template/malformed.xml"});
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String[] fileNames)</code> for failure
     * with invalid fileNames in which some file is invalid persistence file, <code>PersistenceException</code>
     * is expected.
     * </p>
     */
    public void testCtor2_InvalidXmlFileInFileNames() {
        try {
            new XmlPhaseTemplatePersistence(new String[] {"test_files/bad_template/invalid.xml"});
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String[] fileNames)</code> for failure
     * with invalid fileNames in which some template file is with empty name, <code>PersistenceException</code>
     * is expected.
     * </p>
     */
    public void testCtor2_EmptyNameTemplateInFileNames() {
        try {
            new XmlPhaseTemplatePersistence(new String[] {TEMPLATE_FILE_EMPTY_NAME});
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests constructor <code>XmlPhaseTemplatePersistence(String[] fileNames)</code> for accuracy.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testCtor2_Success() throws PersistenceException {
        assertNotNull("Failed to create XmlPhaseTemplatePersistence.", new XmlPhaseTemplatePersistence(
                new String[] {TEMPLATE_FILE_DESIGN, TEMPLATE_FILE_DEVELOPMENT}));
    }

    /**
     * <p>
     * Tests method <code>generatePhases(String templateName, Project project)</code> for failure with
     * null templateName, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     * @throws PhaseGenerationException to JUnit
     */
    public void testGeneratePhases_NullTemplateName() throws PhaseGenerationException, PersistenceException {
        try {
            this.persistence1.generatePhases(null, project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>generatePhases(String templateName, Project project)</code> for failure with
     * empty templateName, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     * @throws PhaseGenerationException to JUnit
     */
    public void testGeneratePhases_EmptyTemplateName() throws PhaseGenerationException, PersistenceException {
        try {
            this.persistence1.generatePhases("  ", project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>generatePhases(String templateName, Project project)</code> for failure with
     * unknown templateName, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     * @throws PhaseGenerationException to JUnit
     */
    public void testGeneratePhases_UnknownTemplateName() throws PhaseGenerationException,
            PersistenceException {
        try {
            this.persistence1.generatePhases("unknown", project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>generatePhases(String templateName, Project project)</code> for failure with
     * null project, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     * @throws PhaseGenerationException to JUnit
     */
    public void testGeneratePhases_NullProject() throws PhaseGenerationException, PersistenceException {
        try {
            this.persistence1.generatePhases(TEMPLATE_DESIGN, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>generatePhases(String templateName, Project project)</code> for failure.
     * Some phase is associated with unknown phase type, <code>PhaseGenerationException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testGeneratePhases_UnknownPhaseTypeInTemplate() throws PersistenceException {
        this.persistence2 = new XmlPhaseTemplatePersistence(new String[] {TEMPLATE_FILE_UNKNOWN_PHASE_TYPE});
        try {
            this.persistence2.generatePhases(TEMPLATE_UNKNOWN_PHASE_TYPE, project);
            fail("PhaseGenerationException expected.");
        } catch (PhaseGenerationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>generatePhases(String templateName, Project project)</code> for failure.
     * Some phase dependents on unknown phase, <code>PhaseGenerationException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     */
    public void testGeneratePhases_UnknownPhaseInTemplate() throws PersistenceException {
        this.persistence2 = new XmlPhaseTemplatePersistence(new String[] {TEMPLATE_FILE_UNKNOWN_PHASE});
        try {
            this.persistence2.generatePhases(TEMPLATE_UNKNOWN_PHASE, project);
            fail("PhaseGenerationException expected.");
        } catch (PhaseGenerationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Tests method <code>generatePhases(String templateName, Project project)</code> for accuracy.
     * </p>
     * @throws PersistenceException to JUnit
     * @throws PhaseGenerationException to JUnit
     */
    public void testGeneratePhases_Success() throws PhaseGenerationException, PersistenceException {
        // generate phases
        this.persistence1.generatePhases(TEMPLATE_DESIGN, project);
        // check the generated phases
        Helper.checkSamplePhases(project.getAllPhases());
    }

    /**
     * <p>
     * Tests method <code>getAllTemplateNames()</code> for accuracy.
     * Here the persistence is created from configuration.
     * </p>
     */
    public void testGetAllTemplateNames_FromConfig() {
        String[] names = this.persistence1.getAllTemplateNames();
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
     * Tests method <code>getAllTemplateNames()</code> for accuracy.
     * Here the persistence is created from API.
     * </p>
     */
    public void testGetAllTemplateNames_FromScratch() {
        String[] names = this.persistence2.getAllTemplateNames();
        assertEquals("Incorrect names.", 2, names.length);
        Set set = new HashSet();
        for (int i = 0; i < names.length; i++) {
            set.add(names[i]);
        }
        assertTrue("Incorrect names.", set.contains(TEMPLATE_DESIGN));
        assertTrue("Incorrect names.", set.contains(TEMPLATE_DEVELOPMENT));
    }
}
