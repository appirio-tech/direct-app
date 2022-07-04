/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.WorkdaysUnitOfTime;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.persistence.DBPhaseTemplatePersistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test cases for the DefaultPhaseTemplate's new methods that added in version 1.1.
 * </p>
 *
 * <p>
 * It tests its new methods for both accuracy and failure.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 *
 */
public class DefaultPhaseTemplateNewMethodTests extends TestCase {

    /**
     * <p>
     * Configuration file.
     * </p>
     */
    private static final String CONFIG_FILE = "test_files" + File.separator + "config" + File.separator
        + "Project_Phase_Template_Config.xml";

    /**
     * <p>
     * Bad configuration file.
     * </p>
     */
    private static final String BAD_CONFIG_FILE = "test_files" + File.separator + "bad_config"
        + File.separator + "Default_Phase_Template_Bad_Config.xml";

    /**
     * <p>
     * Configuration namespace for <code>DefaultPhaseTemplate</code>.
     * </p>
     */
    private static final String NAMESPACE = "com.topcoder.project.phases.template.DefaultPhaseTemplate";

    /**
     * <p>
     * Represent an instance of DefaultPhaseTemplate for testing use.
     * </p>
     */
    private DefaultPhaseTemplate template;

    /**
     * <p>
     * Represent an instance Calendar for testing use.
     * </p>
     */
    private Calendar calendar = null;

    /**
     * <p>
     * Represent an instance Date for testing use.
     * </p>
     */
    private Date fixedDate = null;

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        // load configurations
        Helper.loadConfig(CONFIG_FILE);
        Helper.loadConfig(BAD_CONFIG_FILE);

        // create the template
        template = new DefaultPhaseTemplate(NAMESPACE);

        // set the db persistence
        template.setPersistence(new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "test"));

        // initializeDB
        TestHelper.initializeDB();

        calendar = Calendar.getInstance();
        calendar.set(2002, 2, 10);
        fixedDate = calendar.getTime();
        calendar.set(2002, 1, 11);
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
        return new TestSuite(DefaultPhaseTemplateNewMethodTests.class);
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
        String[] names = template.getAllTemplateNames(4);

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
        assertEquals("Should return 0 length array.", 0, template.getAllTemplateNames(100).length);
    }

    /**
     * <p>
     * Test the method <code>getAllTemplateNames(int)</code>for failure.
     * </p>
     *
     * <p>
     * It tests when the persistence layer error occurs(failed to connect to the database)
     * and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTemplateNamesByCategoryPersistenceException() throws Exception {
        template.setPersistence(new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "wrongConnection"));

        try {
            template.getAllTemplateNames(4);
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
        assertEquals("Category not correct.", 1, template.getTemplateCategory("New_Design"));
        assertEquals("Category not correct.", 5, template.getTemplateCategory("research"));
        assertEquals("Category not correct.", 2, template.getTemplateCategory("test"));
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
            template.getTemplateCategory(null);
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
            template.getTemplateCategory("  ");
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
            template.getTemplateCategory("no template");
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
     * It tests when the persistence layer error occurs(failed to connect to the database)
     * and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCategoryPersistenceException() throws Exception {
        template.setPersistence(new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "wrongConnection"));
        try {
            template.getTemplateCategory("New_Design");
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
        assertEquals("Description Incorrect", "This is a design phases template", template
            .getTemplateDescription("New_Design"));
        assertEquals("Description Incorrect", "This is a development phases template", template
            .getTemplateDescription("New_Development"));
        assertNull("Description Incorrect", template.getTemplateDescription("Development"));
        assertNull("Description Incorrect", template.getTemplateDescription("Design"));
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
            template.getTemplateDescription(null);
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
            template.getTemplateDescription("  ");
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
            template.getTemplateDescription("no template");
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
        Date date = template.getTemplateCreationDate("New_Development");

        // verify the results
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(date);
        assertEquals("Date Incorrect", 2007, tempCalendar.get(tempCalendar.YEAR));
        assertEquals("Date Incorrect", 11, tempCalendar.get(tempCalendar.MONTH));
        assertEquals("Date Incorrect", 4, tempCalendar.get(tempCalendar.DAY_OF_MONTH));
        assertEquals("Date Incorrect", 14, tempCalendar.get(tempCalendar.HOUR_OF_DAY));
        assertEquals("Date Incorrect", 45, tempCalendar.get(tempCalendar.MINUTE));
        assertEquals("Date Incorrect", 2, tempCalendar.get(tempCalendar.SECOND));

        // test with null date
        assertNull("Should get null value", template.getTemplateCreationDate("Development"));
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
            template.getTemplateCreationDate(null);
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
            template.getTemplateCreationDate("  ");
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
            template.getTemplateCreationDate("no template");
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
        assertEquals("Template name not correct.", "New_Design_Default", template.getDefaultTemplateName(1));
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
        assertNull("Should get null.", template.getDefaultTemplateName(2));
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
            template.getDefaultTemplateName(4);
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
     * It tests when the persistence layer error occurs and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDefaultTemplateNamePersistenceException() throws Exception {
        template.setPersistence(new DBPhaseTemplatePersistence(TestHelper.getDBConnectionFactory(), "root"));
        try {
            template.getDefaultTemplateName(1);
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when templateName is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateNameNull() throws Exception {
        try {
            template.applyTemplate(null, 1, 1, new Date());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when templateName is empty and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateNameEmpty() throws Exception {
        try {
            template.applyTemplate(" ", 1, 1, new Date());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when the template with the given name does not exist and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateNameExist() throws Exception {
        try {
            template.applyTemplate("no template", 1, 1, new Date());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when fixedPhaseStartDate is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateFixedPhaseStartDateNull() throws Exception {
        try {
            template.applyTemplate("New_Design", 1, 1, null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when fixedPhaseStartDate is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplatePhaseIdNotFoundNull() throws Exception {
        try {
            template.applyTemplate("New_Design", 1, 10001, fixedDate);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }

        try {
            template.applyTemplate("New_Design", 1000, 1, fixedDate);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when templateName is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateNameNull() throws Exception {
        try {
            template.applyTemplate(null, 1, 1, fixedDate, calendar.getTime());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when templateName is empty and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateNameEmpty() throws Exception {
        try {
            template.applyTemplate(" ", 1, 1, fixedDate, calendar.getTime());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when the template with the given name does not exist and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateNameExist() throws Exception {
        try {
            template.applyTemplate("no template", 1, 1, fixedDate, calendar.getTime());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when fixedPhaseStartDate is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateFixedPhaseStartDateNull() throws Exception {
        try {
            template.applyTemplate("New_Design", 1, 1, null, calendar.getTime());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when startDate is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateStartDateNull() throws Exception {
        try {
            template.applyTemplate("New_Design", 1, 1, fixedDate, null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when startDate > fixedPhaseStartDate and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateStartDateLater() throws Exception {
        calendar.set(2010, 1, 1);
        try {
            template.applyTemplate("New_Design", 1, 1, fixedDate, calendar.getTime());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when fixedPhaseStartDate is null and expects IllegalArgumentException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDatePhaseIdNotFoundNull() throws Exception {
        try {
            template.applyTemplate("New_Design", 1, 10001, new Date(), calendar.getTime());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }

        try {
            template.applyTemplate("New_Design", 1000, 1, new Date());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to adjust the date and expects PhaseGenerationException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateAdjustDateFail() throws Exception {
        try {
            template.applyTemplate("New_Design", 1, 2, fixedDate);
            fail("should get PhaseGenerationException");
        } catch (PhaseGenerationException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date, Date) for failure.
     * </p>
     *
     * <p>
     * It tests when it failed to adjust the date and expects PhaseGenerationException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateAdjustDateFail() throws Exception {
        calendar.set(1999, 1, 1);
        try {
            template.applyTemplate("New_Design", 1, 2, fixedDate, calendar.getTime());
            fail("should get PhaseGenerationException");
        } catch (PhaseGenerationException e) {
            // good
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date, Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests its functionality to adjust the start date of the given phase. It test the case that
     * the desired date is the same as the original date. If verifies the date is adjusted correctly.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateAdjustAreadyEqual() throws Exception {
        // set the fixeDate to the start date of phase 2
        long workTime = 604800000;
        Workdays workDays = template.getWorkdays();
        fixedDate = workDays.add(calendar.getTime(), WorkdaysUnitOfTime.MINUTES, (int) workTime / 60000);

        Project project = template.applyTemplate("New_Design", 1, 2, fixedDate, calendar.getTime());
        // verify the start date is adjustied
        Phase phase = getPhase(project, 2);
        assertEquals("Should be equal.", 0, (phase.calcStartDate().getTime() - fixedDate.getTime()) / 60000);
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date, Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests its functionality to adjust the start date of the given phase. It test the case that
     * the desired date is ealier than the original date. If verifies the date is adjusted correctly.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateAdjustAreadyFixedDateBefore() throws Exception {
        // set the fixeDate to the start date of phase 2
        long workTime = 604800000;
        Workdays workDays = template.getWorkdays();
        fixedDate = workDays.add(calendar.getTime(), WorkdaysUnitOfTime.MINUTES, (int) workTime / 60000);

        // modified the date 4 days ealier
        Calendar fixedCalendar = Calendar.getInstance();
        fixedCalendar.setTime(fixedDate);
        fixedCalendar.add(Calendar.DATE, -4);

        Project project = template.applyTemplate("New_Design", 1, 2, fixedCalendar.getTime(), calendar.getTime());
        // verify the start date is adjustied
        Phase phase = getPhase(project, 2);
        assertEquals("Should be equal.", 0,
            (phase.calcStartDate().getTime() - fixedCalendar.getTime().getTime()) / 60000);
    }

    /**
     * <p>
     * Test the method applyTemplate(String, int, int, Date, Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests its functionality to adjust the start date of the given phase. It test the case that
     * the desired date is later than the original date. If verifies the date is adjusted correctly.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testApplyTemplateWithStartDateAdjustAreadyFixedDateAfter() throws Exception {
        // set the fixeDate to the start date of phase 2
        long workTime = 604800000;
        Workdays workDays = template.getWorkdays();
        fixedDate = workDays.add(calendar.getTime(), WorkdaysUnitOfTime.MINUTES, (int) workTime / 60000);

        // modified the date 2 days later
        Calendar fixedCalendar = Calendar.getInstance();
        fixedCalendar.setTime(fixedDate);
        fixedCalendar.add(Calendar.DATE, 2);

        Project project = template.applyTemplate("New_Design", 1, 2, fixedCalendar.getTime(), calendar.getTime());
        // verify the start date is adjustied
        Phase phase = getPhase(project, 2);
        assertEquals("Should be equal.", 0,
            (phase.calcStartDate().getTime() - fixedCalendar.getTime().getTime()) / 60000);
    }

    /**
     * <p>
     * Get a phase from the project with the given id.
     * </p>
     *
     * @param project the project to get the phase
     * @param phaseId the phase id
     * @return the Phase with given id
     */
    private Phase getPhase(Project project, long phaseId) {
        Phase[] phases = project.getAllPhases();
        for (int i = 0; i < phases.length; ++i) {
            if (phases[i].getId() == phaseId) {
                return phases[i];
            }
        }
        return null;
    }
}
