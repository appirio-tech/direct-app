/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import java.io.File;
import java.lang.reflect.Constructor;

import com.topcoder.project.service.impl.TestHelper;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>Util</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class UtilTest extends TestCase {

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
                + "unit_test_configuration.xml");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Accuracy test for the constructor.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCtorAccuracy() throws Exception {
        Constructor constructor = Util.class.getDeclaredConstructor(new Class[0]);
        constructor.setAccessible(true);
        constructor.newInstance(new Object[0]);
    }

    /**
     * <p>
     * Test for <code>checkObjNotNull</code> method.
     * </p>
     * <p>
     * Tests it against null object. Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCheckObjNotNullWithNullObj() throws Exception {
        try {
            Util.checkObjNotNull(null, "null", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>checkObjNotNull</code> method.
     * </p>
     * <p>
     * Tests it with non-null object, no exception should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCheckObjNotNullWithNonNullObj() throws Exception {
        Util.checkObjNotNull(new Object(), "non_null", null);
    }

    /**
     * <p>
     * Test for <code>checkStrNotNullOrEmpty</code> method.
     * </p>
     * <p>
     * Tests it against null string. Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCheckStrNotNullOrEmptyWithNullStr() throws Exception {
        try {
            Util.checkStrNotNullOrEmpty(null, "null");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>checkStrNotNullOrEmpty</code> method.
     * </p>
     * <p>
     * Tests it against empty string. Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCheckStrNotNullOrEmptyWithEmptyStr() throws Exception {
        try {
            Util.checkStrNotNullOrEmpty("    ", "empty");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>checkStrNotNullOrEmpty</code> method.
     * </p>
     * <p>
     * Tests it with non-null and non-empty string. no exception should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCheckStrNotNullOrEmpty() throws Exception {
        Util.checkStrNotNullOrEmpty("he", "non");
    }

    /**
     * <p>
     * Test for <code>checkArrayHasNull</code> method.
     * </p>
     * <p>
     * Tests it against array having null elements. Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCheckArrayHasNullWithArrayHavingNull() throws Exception {
        try {
            Util.checkArrrayHasNull(new Object[] {null}, "hasNull");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>checkStringArrayHasNullOrEmptyEle</code> method.
     * </p>
     * <p>
     * Tests it with array having null string. Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCheckStringArrayHasNullOrEmptyWithArrayHavingNull() throws Exception {
        try {
            Util.checkStringArrayHasNullOrEmptyEle(new String[] {null}, "hasNull");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>checkStringArrayHasNullOrEmptyEle</code> method.
     * </p>
     * <p>
     * Tests it with array having empty string. Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCheckStringArrayHasNullOrEmptyWithArrayHavingEmpty() throws Exception {
        try {
            Util.checkStringArrayHasNullOrEmptyEle(new String[] {"   "}, "empty");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>checkIDNotNegative</code> method.
     * </p>
     * <p>
     * Tests it with negative ID. Expects <code>IllegalArgumentException</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCheckIDNotNegativeWithNegID() throws Exception {
        try {
            Util.checkIDNotNegative(-1, "negID", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getStringPropertyValue</code> with the property is
     * required, and the value is not null or empty. Verifies the string property value is got
     * properly.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetStringPropertyValueAccuracy1() throws Exception {
        assertEquals("The string property value should be got properly.", "valid", Util.getStringPropertyValue(
                "test", "requiredPropertyValid", true));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getStringPropertyValue</code>with the property is
     * optional and the value is empty. Verifies the string property value is got properly.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetStringPropertyValueAccuracy2() throws Exception {
        assertEquals("The string property value should be got properly.", "", Util.getStringPropertyValue("test",
                "loggerNameEmpty", false));
    }

    /**
     * <p>
     * Failure test for the method <code>getStringPropertyValue</code> with the namespace doesn't
     * exist, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetStringPropertyValueWithUnknownNamespace() throws Exception {
        try {
            Util.getStringPropertyValue("unknown namespace", "requiredPropertyValid", true);
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>getStringPropertyValue</code> with the property doesn't
     * exist when it is required, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetStringPropertyValueWithRequiredPropertyNotExist() throws Exception {
        try {
            Util.getStringPropertyValue("test", "requiredPropertyNull", true);
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>getStringPropertyValue</code> with the property value is
     * an empty string when it is required, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetStringPropertyValueWithRequiredPropertyEmpty() throws Exception {
        try {
            Util.getStringPropertyValue("test", "requiredPropertyEmpty", true);
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getObjectFactory</code>. Verifies the object factory is
     * got properly.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetObjectFactoryAccuracy() throws Exception {
        assertNotNull("The object factory should be got properly.", Util
                .getObjectFactory("com.topcoder.util.objectfactory"));
    }

    /**
     * <p>
     * Failure test for the method <code>getObjectFactory</code> with the specNamespace is
     * invalid, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetObjectFactoryWithSpecNamespaceInvalid() throws Exception {
        try {
            Util.getObjectFactory("invalid");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>getObjectFactory</code> with IllegalReferenceException
     * occurs, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetObjectFactoryWithIRE() throws Exception {
        try {
            Util.getObjectFactory("IllegalReferenceException");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>createObject</code>. Verifies the object is created
     * properly.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateObjectAccuracy() throws Exception {
        ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(
                "com.topcoder.util.objectfactory"));
        assertNotNull("The object should be created properly.", Util.createObject(objectFactory,
                "projectServicesKey", ProjectServices.class));
    }

    /**
     * <p>
     * Failure test for the method <code>createObject</code> with the key is invalid class
     * specification, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateObjectWithInvalidKey() throws Exception {
        ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(
                "com.topcoder.util.objectfactory"));
        try {
            Util.createObject(objectFactory, "invalidClassSpecification", ProjectServices.class);
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createObject</code> with the object is not instance of
     * the specified type, ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateObjectWithInvalidType() throws Exception {
        ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(
                "com.topcoder.util.objectfactory"));
        try {
            Util.createObject(objectFactory, "projectServicesKey", String.class);
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>log</code>. Verifies the message is logged properly.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testLogAccuracy() throws Exception {
        Util.log(LogManager.getLog(), Level.INFO, "message");
    }
}
