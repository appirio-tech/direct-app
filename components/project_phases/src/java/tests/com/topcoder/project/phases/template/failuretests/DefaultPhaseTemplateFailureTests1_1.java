/*
 * Copyright (C) 2007, 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.failuretests;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import com.topcoder.project.phases.template.DefaultPhaseTemplate;
import com.topcoder.project.phases.template.Helper;
import com.topcoder.project.phases.template.PersistenceException;
import com.topcoder.project.phases.template.PhaseGenerationException;
import com.topcoder.project.phases.template.persistence.DBPhaseTemplatePersistence;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for the DefaultPhaseTemplate's new methods that added in version 1.1.
 * </p>
 *
 * <p>
 * It tests its new methods for both accuracy and failure.
 * </p>
 *
 * @author iRabbit
 * @version 1.1
 */
public class DefaultPhaseTemplateFailureTests1_1 extends TestCase {

    /**
     * <p>
     * Configuration file.
     * </p>
     */
    private static final String CONFIG_FILE = FailureTestHelper.getPath("config" + File.separator
            + "Project_Phase_Template_Config.xml");

    /**
     * <p>
     * Bad configuration file.
     * </p>
     */
    private static final String BAD_CONFIG_FILE = FailureTestHelper.getPath("bad_config" + File.separator
            + "Default_Phase_Template_Bad_Config.xml");

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
    protected void setUp() throws Exception {
        // load configurations
        Helper.loadConfig(CONFIG_FILE);
        Helper.loadConfig(BAD_CONFIG_FILE);

        // create the template
        template = new DefaultPhaseTemplate(NAMESPACE);

        // set the db persistence
        template.setPersistence(new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "test"));

        // initializeDB
        FailureTestHelper.initializeDB();

        calendar = Calendar.getInstance();
        calendar.set(2002, 2, 10);
        fixedDate = calendar.getTime();
        calendar.set(2002, 1, 11);
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
     * Test the method <code>getAllTemplateNames(int)</code>for failure.
     * </p>
     *
     * <p>
     * It tests when the persistence layer error occurs(failed to connect to the database) and expects
     * PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTemplateNamesByCategory_Failure() throws Exception {
        template.setPersistence(new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(),
                "wrongConnection"));

        try {
            template.getAllTemplateNames(4);
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
            template.getTemplateCategory(null);
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
            template.getTemplateCategory("  ");
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
            template.getTemplateCategory("no template");
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
     * It tests when the persistence layer error occurs(failed to connect to the database) and expects
     * PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCategory_Failure4() throws Exception {
        template.setPersistence(new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(),
                "wrongConnection"));
        try {
            template.getTemplateCategory("New_Design");
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
            template.getTemplateDescription(null);
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
            template.getTemplateDescription("  ");
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
            template.getTemplateDescription("no template");
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
            template.getTemplateCreationDate(null);
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
            template.getTemplateCreationDate("  ");
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
            template.getTemplateCreationDate("no template");
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
            template.getDefaultTemplateName(4);
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
     * It tests when the persistence layer error occurs and expects PersistenceException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDefaultTemplateName_Failure2() throws Exception {
        template.setPersistence(new DBPhaseTemplatePersistence(FailureTestHelper.getDBConnectionFactory(), "root"));
        try {
            template.getDefaultTemplateName(1);
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // success
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
    public void testApplyTemplateName_Failure1() throws Exception {
        try {
            template.applyTemplate(null, 1, 1, new Date());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateName_Failure2() throws Exception {
        try {
            template.applyTemplate(" ", 1, 1, new Date());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateName_Failure3() throws Exception {
        try {
            template.applyTemplate("no template", 1, 1, new Date());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateStringIntIntDate_Failure1() throws Exception {
        try {
            template.applyTemplate("New_Design", 1, 1, null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateStringIntIntDate_Failure2() throws Exception {
        try {
            template.applyTemplate("New_Design", 1, 10001, fixedDate);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateStringIntIntDate_Failure3() throws Exception {
        try {
            template.applyTemplate("New_Design", 1000, 1, fixedDate);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateStringIntIntDate_Failure4() throws Exception {
        try {
            template.applyTemplate("New_Design", 1000, 1, new Date());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateStringIntIntDate_Failure5() throws Exception {
        try {
            template.applyTemplate("New_Design", 1, 2, fixedDate);
            fail("should get PhaseGenerationException");
        } catch (PhaseGenerationException e) {
            // success
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
    public void testApplyTemplateStringIntIntDateDate_Failure1() throws Exception {
        try {
            template.applyTemplate(null, 1, 1, fixedDate, calendar.getTime());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateStringIntIntDateDate_Failure2() throws Exception {
        try {
            template.applyTemplate(" ", 1, 1, fixedDate, calendar.getTime());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateStringIntIntDateDate_Failure3() throws Exception {
        try {
            template.applyTemplate("no template", 1, 1, fixedDate, calendar.getTime());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateStringIntIntDateDate_Failure4() throws Exception {
        try {
            template.applyTemplate("New_Design", 1, 1, null, calendar.getTime());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateStringIntIntDateDate_Failure5() throws Exception {
        try {
            template.applyTemplate("New_Design", 1, 1, fixedDate, null);
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateStringIntIntDateDate_Failure6() throws Exception {
        calendar.set(2010, 1, 1);
        try {
            template.applyTemplate("New_Design", 1, 1, fixedDate, calendar.getTime());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateStringIntIntDateDate_Failure7() throws Exception {
        try {
            template.applyTemplate("New_Design", 1, 10001, new Date(), calendar.getTime());
            fail("should get IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
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
    public void testApplyTemplateStringIntIntDateDate_Failure8() throws Exception {
        calendar.set(1999, 1, 1);
        try {
            template.applyTemplate("New_Design", 1, 2, fixedDate, calendar.getTime());
            fail("should get PhaseGenerationException");
        } catch (PhaseGenerationException e) {
            // success
        }
    }
}
