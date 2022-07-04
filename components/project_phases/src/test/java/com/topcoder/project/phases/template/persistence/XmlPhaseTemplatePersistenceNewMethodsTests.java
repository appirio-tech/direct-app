/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.persistence;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.Helper;
import com.topcoder.project.phases.template.PersistenceException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test cases for the XmlPhaseTemplatePersistence's new methods added in version 1.1.
 * </p>
 *
 * <p>
 * It tests its new methods for both accuracy and failure. It also test the modified method
 * generatePhases(String, Project).
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 *
 */
public class XmlPhaseTemplatePersistenceNewMethodsTests extends TestCase {

    /**
     * <p>
     * Instance of <code>XmlPhaseTemplatePersistence</code> used in this test.
     * </p>
     */
    private XmlPhaseTemplatePersistence persistence = null;

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     * <p>
     * The set-up creates a XmlPhaseTemplatePersistence using a set of template files in the
     * "test_files/new_template" folder.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {

        // initialize a set of files to test, the files resides in "test_files/new_template" folder
        String[] files = {"New_TCSTemplate_Design.xml", "New_TCSTemplate_Design_Bad.xml",
            "New_TCSTemplate_Design_Default.xml", "New_TCSTemplate_Development.xml",
            "New_TCSTemplate_Development_Bad.xml", "New_TCSTemplate_Development_1.xml", "TCSTemplate_Design.xml",
            "TCSTemplate_Development.xml", "New_TCSTemplate_Development_2.xml"};

        for (int i = 0; i < files.length; ++i) {
            files[i] = "test_files" + File.separator + "new_template" + File.separator + files[i];
        }

        persistence = new XmlPhaseTemplatePersistence(files);
    }

    /**
     * <p>
     * Clear the environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception {
        Helper.clearConfig();
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(XmlPhaseTemplatePersistenceNewMethodsTests.class);
    }

    /**
     * <p>
     * Test the method getAllTemplateNames(int) for accuracy.
     * </p>
     *
     * <p>
     * It call the method getAllTemplateNames(int) and verify that the results are correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTemplateNames() throws Exception {
        // get the names of category 1, 3 results should be returned.
        String[] names = persistence.getAllTemplateNames(1);
        assertEquals("3 Names should be returned.", 3, names.length);
        // verify the category
        for (int i = 0; i < names.length; ++i) {
            assertEquals("The category is incorrect.", 1, persistence.getTemplateCategory(names[i]));
        }

        // get the names of category 2, 43 results should be returned.
        names = persistence.getAllTemplateNames(2);
        assertEquals("4 Names should be returned.", 4, names.length);
        for (int i = 0; i < names.length; ++i) {
            assertEquals("The category is incorrect.", 2, persistence.getTemplateCategory(names[i]));
        }

        // get the names of category 0, 2 results should be returned.
        assertEquals("2 Names should be returned.", 2, persistence.getAllTemplateNames(0).length);
        // get the names of category 4, 0 results should be returned.
        assertEquals("0 Names should be returned.", 0, persistence.getAllTemplateNames(4).length);
    }

    /**
     * <p>
     * Test the method getTemplateCategory(String) for accuracy.
     * </p>
     *
     * <p>
     * It call the method getTemplateCategory(String) with valid template name and verify that the
     * returned category is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCategory() throws Exception {
        assertEquals("Category not correct.", 0, persistence.getTemplateCategory("Design"));
        assertEquals("Category not correct.", 1, persistence.getTemplateCategory("New_Design"));
        assertEquals("Category not correct.", 2, persistence.getTemplateCategory("New_Development"));
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
     * It tests when the template with the given name does not exist and expects
     * IllegalArgumentException
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
     * It tests when the template with the given name does not exist and expects
     * IllegalArgumentException
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
     * It tests when the template with the given name does not exist and expects
     * IllegalArgumentException
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
     * Test the method getTemplateCreationDate(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the date can not be parsed and expects PersistenceException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCreationDateParseException() throws Exception {
        try {
            persistence.getTemplateCreationDate("New_Development_Bad");
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method <code>generatePhases(String, Project)</code> for accuracy.
     * </p>
     *
     * <p>
     * It tests that the method work correctly after the modification, and the phase id can be read
     * correctly.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGeneratePhasesWithPhaseId() throws Exception {
        // create needed objects
        Date startDate = Calendar.getInstance().getTime();
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(startDate, workdays);

        // generate phases
        persistence.generatePhases("New_Design", project);

        // check the generated phases
        Helper.checkSamplePhases(project.getAllPhases());
        // check the phase id
        Phase[] phases = project.getAllPhases();
        for (int i = 0; i < phases.length; ++i) {
            assertEquals("Phase id incorrect.", phases[i].getPhaseType().getId(), phases[i].getId());
        }
    }
}
