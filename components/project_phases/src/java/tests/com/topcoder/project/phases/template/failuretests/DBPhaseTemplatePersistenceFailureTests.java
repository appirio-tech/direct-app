/*
 * Copyright (C) 2007, 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.failuretests;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.Helper;
import com.topcoder.project.phases.template.PersistenceException;
import com.topcoder.project.phases.template.PersistenceRuntimeException;
import com.topcoder.project.phases.template.PhaseGenerationException;
import com.topcoder.project.phases.template.persistence.DBPhaseTemplatePersistence;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests on class <code>DBPhaseTemplatePersistence</code>.
 * </p>
 *
 * @author iRabbit
 * @version 1.1
 */
public class DBPhaseTemplatePersistenceFailureTests extends TestCase {

    /**
     * <p>
     * Bad configuration file.
     * </p>
     */
    private static final String BAD_CONFIG_FILE = FailureTestHelper.getPath("bad_config" + File.separator
            + "Xml_Phase_Template_Persistence_Bad_Config.xml");

    /**
     * <p>
     * Configuration file.
     * </p>
     */
    private static final String CONFIG_FILE = FailureTestHelper.getPath("config" + File.separator
            + "Project_Phase_Template_Config.xml");

    /**
     * <p>
     * An instance of DBPhaseTemplatePersistence for testing use.
     * </p>
     */
    private DBPhaseTemplatePersistence persistence;

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
     *
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        FailureTestHelper.initializeDB();
        Helper.loadConfig(CONFIG_FILE);
        Helper.loadConfig(BAD_CONFIG_FILE);
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "test");

        // create needed objects
        startDate = Calendar.getInstance().getTime();
        workdays = new DefaultWorkdays();
        project = new Project(startDate, workdays);

    }

    /**
     * <p>
     * Clear the environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        Helper.clearConfig();
        FailureTestHelper.clearDB();
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the namespace is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorString_Failure1() throws Exception {
        try {
            new DBPhaseTemplatePersistence(null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the namespace is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorString_Failure2() throws Exception {
        try {
            new DBPhaseTemplatePersistence(" ");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the namespace is not exist and expects ConfigurationException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorString_Failure3() throws Exception {
        try {
            new DBPhaseTemplatePersistence("wrong space");
            fail("should get ConfigurationException");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the created object under "connection_factory_class_name" is not instance of DBConnectionFactory
     * and expects ConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorString_Failure4() throws Exception {
        try {
            new DBPhaseTemplatePersistence("not_db_factory");
            fail("should get ConfigurationException");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the required parameters are missing and expects ConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorString_Failure5() throws Exception {
        // "connection_factory_class_name" is missing
        try {
            new DBPhaseTemplatePersistence("parameter_missing_1");
            fail("should get ConfigurationException");
            new String();
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the required parameters are missing and expects ConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorString_Failure6() throws Exception {
        // "object_factory_namespace" is missing
        try {
            new DBPhaseTemplatePersistence("parameter_missing_2");
            fail("should get ConfigurationException");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the class specification is invalid and expects ConfigurationException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorString_Failure7() throws Exception {
        // "connection_factory_class_name" is missing
        try {
            new DBPhaseTemplatePersistence("wrong_class");
            fail("should get ConfigurationException");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the namespace for the object factory is not exist and expects ConfigurationException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorString_Failure8() throws Exception {
        try {
            new DBPhaseTemplatePersistence("factory_space_not_exist");
            fail("should get ConfigurationException");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(DBConnectionFactory, String) for failure.
     * </p>
     *
     * <p>
     * It tests when the factory is null and expects IllegalArgumentException
     * </p>
     *
     */
    public void testCtorDBConnectionFactoryString_Failure() {
        try {
            new DBPhaseTemplatePersistence(null, "test");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method <code>generatePhases(String templateName, Project project)</code> for failure with null
     * templateName, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws PersistenceException to JUnit
     * @throws PhaseGenerationException to JUnit
     */
    public void testGeneratePhases_Failure1() throws PhaseGenerationException, PersistenceException {
        try {
            persistence.generatePhases(null, project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Tests method <code>generatePhases(String templateName, Project project)</code> for failure with empty
     * templateName, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws PersistenceException to JUnit
     * @throws PhaseGenerationException to JUnit
     */
    public void testGeneratePhases_Failure2() throws PhaseGenerationException, PersistenceException {
        try {
            persistence.generatePhases("  ", project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Tests method <code>generatePhases(String templateName, Project project)</code> for failure with unknown
     * templateName, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws PersistenceException to JUnit
     * @throws PhaseGenerationException to JUnit
     */
    public void testGeneratePhases_Failure3() throws PhaseGenerationException, PersistenceException {
        try {
            persistence.generatePhases("unknown", project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Tests method <code>generatePhases(String templateName, Project project)</code> for failure with null project,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws PersistenceException to JUnit
     * @throws PhaseGenerationException to JUnit
     */
    public void testGeneratePhases_Failure4() throws PhaseGenerationException, PersistenceException {
        try {
            persistence.generatePhases("New_Design", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test the method <code>generatePhases(String, Project)</code>for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to connect to the database and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhases_Failure5() throws Exception {
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "wrongConnection");

        try {
            persistence.generatePhases("New_Design", project);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test the method <code>generatePhases(String, Project)</code>for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to execute the statement and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhases_Failure6() throws Exception {
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "root");

        try {
            persistence.generatePhases("New_Design", project);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test the method <code>generatePhases(String, Project)</code>for failure.
     * </p>
     *
     * <p>
     * It tests when more than one templates with the given name is found and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhases_Failure7() throws Exception {
        try {
            persistence.generatePhases("Duplicate Name", project);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test the method <code>generatePhases(String, Project)</code>for failure.
     * </p>
     *
     * <p>
     * It tests when cycle dependency is detected and expects PhaseGenerationException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhases_Failure8() throws Exception {
        try {
            persistence.generatePhases("Cyclic Template", project);
            fail("PhaseGenerationException expected.");
        } catch (PhaseGenerationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test the method <code>generatePhases(String, Project)</code>for failure.
     * </p>
     *
     * <p>
     * It tests when a phase's phase type does not belong to the given template, and expects PhaseGenerationException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhases_Failure9() throws Exception {
        try {
            persistence.generatePhases("invalid phase", project);
            fail("PhaseGenerationException expected.");
        } catch (PhaseGenerationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test the method <code>generatePhases(String, Project)</code>for failure.
     * </p>
     *
     * <p>
     * It tests when a dependency contains a phase id that does not belong to the given template, and expects
     * PhaseGenerationException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhases_Failure10() throws Exception {
        try {
            persistence.generatePhases("invalid dependency", project);
            fail("PhaseGenerationException expected.");
        } catch (PhaseGenerationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test the method <code>getAllTemplateNames()</code>for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to connect to the database and expects PersistenceRuntimeException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTemplateNames_Failure1() throws Exception {
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "wrongConnection");

        try {
            persistence.getAllTemplateNames();
            fail("PersistenceRuntimeException expected.");
        } catch (PersistenceRuntimeException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test the method <code>getAllTemplateNames()</code>for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to execute the statement and expects PersistenceRuntimeException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTemplateNames_Failure2() throws Exception {
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "root");

        try {
            persistence.getAllTemplateNames();
            fail("PersistenceRuntimeException expected.");
        } catch (PersistenceRuntimeException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test the method <code>getAllTemplateNames(int)</code>for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to connect to the database and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTemplateNamesInt_Failure1() throws Exception {
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "wrongConnection");

        try {
            persistence.getAllTemplateNames(4);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test the method <code>getAllTemplateNames(int)</code>for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to execute the statement and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTemplateNamesInt_Failure2() throws Exception {
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "root");

        try {
            persistence.getAllTemplateNames(2);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getTemplateCategory(String) for failure.
     * </p>
     *
     * <p>
     * It tests when templateName is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCategory_Failure1() throws Exception {
        try {
            persistence.getTemplateCategory(null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getTemplateCategory(String) for failure.
     * </p>
     *
     * <p>
     * It tests when templateName is empty and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCategory_Failure2() throws Exception {
        try {
            persistence.getTemplateCategory("  ");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getTemplateCategory(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the template with the given name does not exist and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCategory_Failure3() throws Exception {
        try {
            persistence.getTemplateCategory("no template");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getTemplateCategory(String) for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to connect to the database and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCategory_Failure4() throws Exception {
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "wrongConnection");
        try {
            persistence.getTemplateCategory("New_Design");
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getTemplateCategory(String) for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to execute the statement and expects PersistenceException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCategory_Failure5() throws Exception {
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "root");
        try {
            persistence.getTemplateCategory("New_Design");
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getTemplateDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests when templateName is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateDescription_Failure1() throws Exception {
        try {
            persistence.getTemplateDescription(null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getTemplateDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests when templateName is empty and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateDescription_Failure2() throws Exception {
        try {
            persistence.getTemplateDescription("  ");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getTemplateDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the template with the given name does not exist and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateDescription_Failure3() throws Exception {
        try {
            persistence.getTemplateDescription("no template");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getTemplateCreationDate(String) for failure.
     * </p>
     *
     * <p>
     * It tests when templateName is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCreationDate_Failure1() throws Exception {
        try {
            persistence.getTemplateCreationDate(null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getTemplateCreationDate(String) for failure.
     * </p>
     *
     * <p>
     * It tests when templateName is empty and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCreationDate_Failure2() throws Exception {
        try {
            persistence.getTemplateCreationDate("  ");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getTemplateCreationDate(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the template with the given name does not exist and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCreationDate_Failure3() throws Exception {
        try {
            persistence.getTemplateCreationDate("no template");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getDefaultTemplateName(int) for failure.
     * </p>
     *
     * <p>
     * It tests when more than one default template is found, and expects PersistenceException
     * </p>
     *
     * @throws Exception to JUnit
     *
     */
    public void testGetDefaultTemplateName_Failure1() throws Exception {
        try {
            persistence.getDefaultTemplateName(4);
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getDefaultTemplateName(int) for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to connect to the database and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDefaultTemplateName_Failure2() throws Exception {
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "wrongConnection");
        try {
            persistence.getDefaultTemplateName(1);
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method getDefaultTemplateName(int) for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to execute the statement and expects PersistenceException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDefaultTemplateName_Failure3() throws Exception {
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "root");
        try {
            persistence.getDefaultTemplateName(1);
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method removeTemplate(String) for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to connect to the database and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveTemplate_Failure1() throws Exception {
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "wrongConnection");
        try {
            persistence.removeTemplate("New_Design");
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method removeTemplate(String) for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to connect to the database and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveTemplate_Failure2() throws Exception {
        persistence = new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "root");
        try {
            persistence.removeTemplate("New_Design");
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method removeTemplate(String) for failure.
     * </p>
     *
     * <p>
     * It tests when templateName is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveTemplate_Failure3() throws Exception {
        try {
            persistence.removeTemplate(null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method removeTemplate(String) for failure.
     * </p>
     *
     * <p>
     * It tests when templateName is empty and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveTemplate_Failure4() throws Exception {
        try {
            persistence.removeTemplate("  ");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Test the method removeTemplate(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the template with the given name does not exist and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveTemplate_Failure5() throws Exception {
        try {
            persistence.removeTemplate("no template");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
