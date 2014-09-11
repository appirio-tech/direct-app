/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.failuretests;

import java.io.File;

import com.topcoder.project.phases.template.Helper;
import com.topcoder.project.phases.template.PersistenceException;
import com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistence;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for the XmlPhaseTemplatePersistence's new methods added in version 1.1.
 * </p>
 *
 * <p>
 * It tests its new methods for both failure. It also test the modified method generatePhases(String, Project).
 * </p>
 *
 * @author iRabbit
 * @version 1.1
 *
 */
public class XmlPhaseTemplatePersistenceFailureTests1_1 extends TestCase {

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
     * The set-up creates a XmlPhaseTemplatePersistence using a set of template files in the "test_files/new_template"
     * folder.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {

        // initialize a set of files to test, the files resides in "test_files/new_template" folder
        String[] files = {"New_TCSTemplate_Design.xml", "New_TCSTemplate_Design_Bad.xml",
            "New_TCSTemplate_Design_Default.xml", "New_TCSTemplate_Development.xml",
            "New_TCSTemplate_Development_Bad.xml", "New_TCSTemplate_Development_1.xml", "TCSTemplate_Design.xml",
            "TCSTemplate_Development.xml", "New_TCSTemplate_Development_2.xml"};

        for (int i = 0; i < files.length; ++i) {
            files[i] = FailureTestHelper.getPath("new_template" + File.separator) + files[i];
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
    protected void tearDown() throws Exception {
        Helper.clearConfig();
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
     * Test the method getTemplateCreationDate(String) for failure.
     * </p>
     *
     * <p>
     * It tests when the date can not be parsed and expects PersistenceException
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTemplateCreationDate_Failure4() throws Exception {
        try {
            persistence.getTemplateCreationDate("New_Development_Bad");
            fail("should get PersistenceException");
        } catch (PersistenceException e) {
            // success
        }
    }
}
