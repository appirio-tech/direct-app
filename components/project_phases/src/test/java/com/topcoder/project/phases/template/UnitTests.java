/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import com.topcoder.project.phases.template.converter.ConverterUtilityTests;
import com.topcoder.project.phases.template.persistence.DBPhaseTemplatePersistenceTests;
import com.topcoder.project.phases.template.persistence.UnitTestXmlPhaseTemplatePersistence;
import com.topcoder.project.phases.template.persistence.XmlPhaseTemplatePersistenceNewMethodsTests;
import com.topcoder.project.phases.template.startdategenerator.UnitTestRelativeWeekTimeStartDateGenerator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * <p>
 * Change for version 1.1: New test cases added.
 * </p>
 *
 * @author flying2hk
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class UnitTests extends TestCase {

    static {
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory"
            , "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
    }

    /**
     * <p>
     * Return the aggregated unit tests.
     * </p>
     * @return the aggregated unit tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(UnitTestPhaseTemplateException.class);
        suite.addTestSuite(UnitTestConfigurationException.class);
        suite.addTestSuite(UnitTestPersistenceException.class);
        suite.addTestSuite(UnitTestPhaseGenerationException.class);
        suite.addTestSuite(UnitTestStartDateGenerationException.class);
        suite.addTestSuite(UnitTestRelativeWeekTimeStartDateGenerator.class);
        suite.addTestSuite(UnitTestXmlPhaseTemplatePersistence.class);
        suite.addTestSuite(UnitTestDefaultPhaseTemplate.class);
        suite.addTestSuite(Demo.class);

        suite.addTestSuite(XmlPhaseTemplatePersistenceNewMethodsTests.class);
        suite.addTestSuite(DBPhaseTemplatePersistenceTests.class);
        suite.addTestSuite(DefaultPhaseTemplateNewMethodTests.class);
        suite.addTestSuite(ConverterUtilityTests.class);
        suite.addTestSuite(PersistenceRuntimeExceptionTests.class);
        suite.addTestSuite(PersistenceRuntimeExceptionTests.class);
        suite.addTestSuite(DemoNewTests.class);
        return suite;
    }

}
