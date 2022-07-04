/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.persistence;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.Helper;
import com.topcoder.project.phases.template.PersistenceException;
import com.topcoder.project.phases.template.PersistenceRuntimeException;
import com.topcoder.project.phases.template.PhaseGenerationException;
import com.topcoder.project.phases.template.TestHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests on class <code>DBPhaseTemplatePersistence</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class DBPhaseTemplatePersistenceTests extends TestCase {

    /**
     * <p>
     * Represent the name space for creating DBPhaseTemplatePersistence.
     * </p>
     */
    private static final String DB_PERSISTENCE_NAMESPACE = "com.topcoder.project.phases.template."
        + "persistence.DBPhaseTemplatePersistence";

    /**
     * <p>
     * Bad configuration file.
     * </p>
     */
    private static final String BAD_CONFIG_FILE = "test_files" + File.separator + "bad_config" + File.separator
        + "Xml_Phase_Template_Persistence_Bad_Config.xml";

    /**
     * <p>
     * Configuration file.
     * </p>
     */
    private static final String CONFIG_FILE = "test_files" + File.separator + "config" + File.separator
        + "Project_Phase_Template_Config.xml";

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
    public void setUp() throws Exception {
        TestHelper.initializeDB();
        Helper.loadConfig(CONFIG_FILE);
        Helper.loadConfig(BAD_CONFIG_FILE);
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "test");

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
        TestHelper.clearDB();
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DBPhaseTemplatePersistenceTests.class);
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the namespace is nul and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor1stNull() throws Exception {
        try {
            new DBPhaseTemplatePersistence(null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the namespace is nul and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor1stEmpty() throws Exception {
        try {
            new DBPhaseTemplatePersistence(" ");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testCtor1stUnknownNameSpace() throws Exception {
        try {
            new DBPhaseTemplatePersistence("wrong space");
            fail("should get ConfigurationException");
        } catch (ConfigurationException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testCtor1stNotDBFactory() throws Exception {
        try {
            new DBPhaseTemplatePersistence("not_db_factory");
            fail("should get ConfigurationException");
        } catch (ConfigurationException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testCtor1stRequiredParameterMissing() throws Exception {
        // "connection_factory_class_name" is misisng
        try {
            new DBPhaseTemplatePersistence("parameter_missing_1");
            fail("should get ConfigurationException");
            new String();
        } catch (ConfigurationException e) {
            // good
            System.out.println(e.getMessage());
        }

        // "object_factory_namespace" is missing
        try {
            new DBPhaseTemplatePersistence("parameter_missing_2");
            fail("should get ConfigurationException");
        } catch (ConfigurationException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testCtor1stWrongClassSpec() throws Exception {
        // "connection_factory_class_name" is misisng
        try {
            new DBPhaseTemplatePersistence("wrong_class");
            fail("should get ConfigurationException");
        } catch (ConfigurationException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testCtor1stFactoryNameSpaceNotExist() throws Exception {
        try {
            new DBPhaseTemplatePersistence("factory_space_not_exist");
            fail("should get ConfigurationException");
        } catch (ConfigurationException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests that it can created the instance correctly if it is properly configured.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor1st() throws Exception {
        assertNotNull("Should not be null", new DBPhaseTemplatePersistence(DB_PERSISTENCE_NAMESPACE));
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(DBConnectionFactory, String) for failure.
     * </p>
     *
     * <p>
     * It tests when the factory is nul and expects IllegalArgumentException
     * </p>
     *
     */
    public void testCtor2ndFactoryNull() {
        try {
            new DBPhaseTemplatePersistence(null, "test");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method DBPhaseTemplatePersistence(DBConnectionFactory, String) for accuracy.
     * </p>
     *
     * <p>
     * It tests when the arguments are valid and the instance could be created correctly.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor2nd() throws Exception {
        assertNotNull("Should not be null", persistence);
        assertNotNull("Should not be null", new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), " "));
        assertNotNull("Should not be null", new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), null));
    }

    /**
     * <p>
     * Tests method <code>generatePhases(String templateName, Project project)</code> for failure with
     * null templateName, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws PersistenceException to JUnit
     * @throws PhaseGenerationException to JUnit
     */
    public void testGeneratePhasesNullTemplateName() throws PhaseGenerationException, PersistenceException {
        try {
            persistence.generatePhases(null, project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
            System.out.println(ex.getMessage());
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
    public void testGeneratePhasesEmptyTemplateName() throws PhaseGenerationException, PersistenceException {
        try {
            persistence.generatePhases("  ", project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
            System.out.println(ex.getMessage());
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
    public void testGeneratePhasesUnknownTemplateName() throws PhaseGenerationException, PersistenceException {
        try {
            persistence.generatePhases("unknown", project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
            System.out.println(ex.getMessage());
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
    public void testGeneratePhasesNullProject() throws PhaseGenerationException, PersistenceException {
        try {
            persistence.generatePhases("New_Design", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
            System.out.println(ex.getMessage());
        }
    }

    /**
     * <p>
     * Test the method <code>generatePhases(String, Project)</code>for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to connnect to the database and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhaseConnectionException() throws Exception {
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "wrongConnection");

        try {
            persistence.generatePhases("New_Design", project);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // expected
            System.out.println(ex.getMessage());
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
    public void testGeneratePhaseSQLException() throws Exception {
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "root");

        try {
            persistence.generatePhases("New_Design", project);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // expected
            System.out.println(ex.getMessage());
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
    public void testGeneratePhaseDuplicateTemplate() throws Exception {
        try {
            persistence.generatePhases("Duplicate Name", project);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // expected
            System.out.println(ex.getMessage());
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
    public void testGeneratePhaseCycleDependency() throws Exception {
        try {
            persistence.generatePhases("Cyclic Template", project);
            fail("PhaseGenerationException expected.");
        } catch (PhaseGenerationException ex) {
            // expected
            System.out.println(ex.getMessage());
        }
    }

    /**
     * <p>
     * Test the method <code>generatePhases(String, Project)</code>for failure.
     * </p>
     *
     * <p>
     * It tests when a phase's phase type does not belong to the given template, and expects
     * PhaseGenerationException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhasePhaseTypeNotInTemplate() throws Exception {
        try {
            persistence.generatePhases("invalid phase", project);
            fail("PhaseGenerationException expected.");
        } catch (PhaseGenerationException ex) {
            // expected
            System.out.println(ex.getMessage());
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
    public void testGeneratePhasePhaseNotInTemplate() throws Exception {
        try {
            persistence.generatePhases("invalid dependency", project);
            fail("PhaseGenerationException expected.");
        } catch (PhaseGenerationException ex) {
            // expected
            System.out.println(ex.getMessage());
        }
    }

    /**
     * <p>
     * Test the method <code>generatePhases(String, Project)</code>for accuracy.
     * </p>
     *
     * <p>
     * It tests with a template containning no phases and expects no error would occur.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhasesEmptyTemplate() throws Exception {
        persistence.generatePhases("empty template", project);
        assertEquals("Should get 0 phases.", 0, project.getAllPhases().length);
    }

    /**
     * <p>
     * Test the method <code>generatePhases(String, Project)</code>for accuracy.
     * </p>
     *
     * <p>
     * It tests with the template "New_Design" and check that the sample phases are built correctly
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhases() throws Exception {
        persistence.generatePhases("New_Design", project);
        Helper.checkSamplePhases(project.getAllPhases());
    }

    /**
     * <p>
     * Test the method <code>getAllTemplateNames()</code>for accuracy.
     * </p>
     *
     * <p>
     * It tests that it can retrieve the names without any errors.
     * </p>
     *
     */
    public void testGetAllTemplateNames() {
        String[] names = persistence.getAllTemplateNames();
        assertNotNull("Should not be null.", names);
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
    public void testGetAllTemplateNamesConnectionException() throws Exception {
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "wrongConnection");

        try {
            persistence.getAllTemplateNames();
            fail("PersistenceRuntimeException expected.");
        } catch (PersistenceRuntimeException ex) {
            // expected
            System.out.println(ex.getMessage());
        }
    }

    /**
     * <p>
     * Test the method <code>getAllTemplateNames()</code>for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to  execute the statement and expects PersistenceRuntimeException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTemplateNamesSQLException() throws Exception {
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "root");

        try {
            persistence.getAllTemplateNames();
            fail("PersistenceRuntimeException expected.");
        } catch (PersistenceRuntimeException ex) {
            // expected
            System.out.println(ex.getMessage());
        }
    }

    /**
     * <p>
     * Test the method <code>getAllTemplateNames(int)</code>for accuracy.
     * </p>
     *
     * <p>
     * It call the method getAllTemplateNames(int) and verify that the results are correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTemplateNamesByCategory() throws Exception {
        String[] names = persistence.getAllTemplateNames(4);

        // verify the result
        assertEquals("Should not be null.", 4, names.length);

        // build the expected
        Set expected = new HashSet();
        for (int i = 0; i < 4; ++i) {
            expected.add("cate4" + i);
        }
        for (int i = 0; i < 4; ++i) {
            assertTrue("The value is not in the expected names.", expected.contains(names[i]));
        }

        // test with empty result
        assertEquals("Should return 0 length array.", 0, persistence.getAllTemplateNames(100).length);
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
    public void testGetAllTemplateNamesByCategoryConnectionException() throws Exception {
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "wrongConnection");

        try {
            persistence.getAllTemplateNames(4);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // expected
            System.out.println(ex.getMessage());
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
    public void testGetAllTemplateNamesByCategorySQLException() throws Exception {
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "root");

        try {
            persistence.getAllTemplateNames(2);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // expected
            System.out.println(ex.getMessage());
        }
    }

    /**
     * <p>
     * Test the method getTemplateCategory(String) for accuracy.
     * </p>
     *
     * <p>
     * It call the method getTemplateCategory(String) with valid template name and verify that
     * the returned category is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCategory() throws Exception {
        assertEquals("Category not correct.", 1, persistence.getTemplateCategory("New_Design"));
        assertEquals("Category not correct.", 5, persistence.getTemplateCategory("research"));
        assertEquals("Category not correct.", 2, persistence.getTemplateCategory("test"));
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
    public void testGetTemplateCategoryNull() throws Exception {
        try {
            persistence.getTemplateCategory(null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testGetTemplateCategoryEmpty() throws Exception {
        try {
            persistence.getTemplateCategory("  ");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testGetTemplateCategoryNotExist() throws Exception {
        try {
            persistence.getTemplateCategory("no template");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testGetTemplateCategoryConnectionException() throws Exception {
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "wrongConnection");
        try {
            persistence.getTemplateCategory("New_Design");
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testGetTemplateCategorySQLException() throws Exception {
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "root");
        try {
            persistence.getTemplateCategory("New_Design");
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method getTemplateDescription(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests with a set of correct template names and verify that the description is returned as
     * expected.
     * </p>
     *
     * @throws Exception to JUnit
     *
     */
    public void testGetTemplateDescription() throws Exception {
        assertEquals("Description Incorrect", "This is a design phases template", persistence
            .getTemplateDescription("New_Design"));
        assertEquals("Description Incorrect", "This is a development phases template", persistence
            .getTemplateDescription("New_Development"));
        assertNull("Description Incorrect", persistence.getTemplateDescription("Development"));
        assertNull("Description Incorrect", persistence.getTemplateDescription("Design"));
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
    public void testGetTemplateDescriptionNull() throws Exception {
        try {
            persistence.getTemplateDescription(null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testGetTemplateDescriptionEmpty() throws Exception {
        try {
            persistence.getTemplateDescription("  ");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testGetTemplateDescriptionNotExist() throws Exception {
        try {
            persistence.getTemplateDescription("no template");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method getTemplateCreationDate(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests with a set of correct template names and verify that the date value is returned as
     * expected.
     * </p>
     *
     * @throws Exception to JUnit
     *
     */
    public void testGetTemplateCreationDate() throws Exception {
        // get the date
        Date date = persistence.getTemplateCreationDate("New_Development");

        // verify the results
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals("Date Incorrect", 2007, calendar.get(calendar.YEAR));
        assertEquals("Date Incorrect", 11, calendar.get(calendar.MONTH));
        assertEquals("Date Incorrect", 4, calendar.get(calendar.DAY_OF_MONTH));
        assertEquals("Date Incorrect", 14, calendar.get(calendar.HOUR_OF_DAY));
        assertEquals("Date Incorrect", 45, calendar.get(calendar.MINUTE));
        assertEquals("Date Incorrect", 2, calendar.get(calendar.SECOND));

        // test with null date
        assertNull("Should get null value", persistence.getTemplateCreationDate("Development"));
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
    public void testGetTemplateCreationDateNull() throws Exception {
        try {
            persistence.getTemplateCreationDate(null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testGetTemplateCreationDateEmpty() throws Exception {
        try {
            persistence.getTemplateCreationDate("  ");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testGetTemplateCreationDateNotExist() throws Exception {
        try {
            persistence.getTemplateCreationDate("no template");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method getDefaultTemplateName(int) for accuracy.
     * </p>
     *
     * <p>
     * It tests with the category 1 and expects the name "New_Design_Default" would be returned.
     * </p>
     *
     * @throws Exception to JUnit
     *
     */
    public void testGetDefaultTemplateName() throws Exception {
        assertEquals("Template name not correct.", "New_Design_Default", persistence.getDefaultTemplateName(1));
    }

    /**
     * <p>
     * Test the method getDefaultTemplateName(int) for accuracy.
     * </p>
     *
     * <p>
     * It tests with the category 2 and expects null would be returned.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDefaultTemplateNameNull() throws Exception {
        assertNull("Should get null.", persistence.getDefaultTemplateName(2));
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
    public void testGetDefaultTemplateNameMoreThanOne() throws Exception {
        try {
            persistence.getDefaultTemplateName(4);
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testGetDefaultTemplateNameConnectionException() throws Exception {
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "wrongConnection");
        try {
            persistence.getDefaultTemplateName(1);
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testGetDefaultTemplateNameSQLException() throws Exception {
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "root");
        try {
            persistence.getDefaultTemplateName(1);
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method removeTemplate(String) for failure.
     * </p>
     *
     * <p>
     * It remove the template "New_Design" and verify that the related entries are removed correctly
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveTemplate() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;

        // remove "New_Design"
        persistence.removeTemplate("New_Design");

        // verify the result
        try {
            try {
                connection = TestHelper.getDBConnectionFactory().createConnection("test");
                statement = connection.createStatement();

                // verify the template entry with id = 1 is removed
                try {
                    result = statement.executeQuery("select id from template where id=1");
                    assertFalse("template should be removed", result.next());
                } finally {
                    closeResultSet(result);
                }

                // verify the phases with template_id = 1 are removed
                try {
                    result = statement.executeQuery("select id from phase where template_id=1");
                    assertFalse("phases should be removed", result.next());
                } finally {
                    closeResultSet(result);
                }

                // verify the phase_type with template_id = 1 are removed
                try {
                    result = statement.executeQuery("select id from phase_type where template_id=1");
                    assertFalse("phase_type should be removed", result.next());
                } finally {
                    closeResultSet(result);
                }

                // verify the default_template with template_id = 1 are removed
                try {
                    result = statement.executeQuery("select id from default_template where template_id=1");
                    assertFalse("default_template should be removed", result.next());
                } finally {
                    closeResultSet(result);
                }

                // verify the dependency with id from 1 to 9 is removed
                for (int i = 1; i <= 9; ++i) {
                    try {
                        result = statement.executeQuery("select id from dependency where id=" + i);
                        assertFalse("dependency with id " + i + " should be removed", result.next());
                    } finally {
                        closeResultSet(result);
                    }
                }

            } finally {

                if (statement != null) {
                    statement.close();
                }
            }

        } finally {
            if (connection != null) {
                connection.close();
            }
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
    public void testRemoveTemplateConnectionException() throws Exception {
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "wrongConnection");
        try {
            persistence.removeTemplate("New_Design");
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testRemoveTemplateSQLException() throws Exception {
        persistence = new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "root");
        try {
            persistence.removeTemplate("New_Design");
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testRemoveTemplateNull() throws Exception {
        try {
            persistence.removeTemplate(null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testRemoveTemplateEmpty() throws Exception {
        try {
            persistence.removeTemplate("  ");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
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
    public void testRemoveTemplateNotExist() throws Exception {
        try {
            persistence.removeTemplate("no template");
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Close the given ResultSet.
     * </p>
     * @param result the ResultSet to close
     * @throws Exception to JUnit
     */
    private void closeResultSet(ResultSet result) throws Exception {
        if (result != null) {
            result.close();
        }
    }
}
