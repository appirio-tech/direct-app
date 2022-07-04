/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.AuditedDeliverableStructure;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.persistence.TestHelper;
import com.topcoder.management.deliverable.persistence.sql.Helper.DataType;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * Tests Helper class.
 * </p>
 * <p>
 * Changes in version 1.2:
 * <ul>
 * <li>Reformat the code.</li>
 * <li>Added test cases for static classes and helper methods.</li>
 * </ul>
 * </p>
 *
 * @author urtks, TCSDEVELOPER
 * @version 1.2
 */
public class HelperTest extends TestCase {

    /**
     * <p>
     * Logger instance using the class name as category.
     * </p>
     */
    private static final Log LOGGER = LogFactory.getLog(HelperTest.class.getName());

    /**
     * <p>
     * Connection factory used for testing.
     * </p>
     */
    private DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Creates test suite for this TestCase.
     * </p>
     *
     * @return test suite for this test case
     */
    public static Test suite() {

        return new TestSuite(HelperTest.class);
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadConfig();

        connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
    }

    /**
     * <p>
     * Tests <code>assertObjectNotNull(Object, String)</code> method.
     * </p>
     * <p>
     * No exception is expected.
     * </p>
     */
    public void testAssertObjectNotNull() {
        Helper.assertObjectNotNull(new Object(), "name", LOGGER);
    }

    /**
     * <p>
     * Tests <code>assertObjectNotNull(Object, String)</code> method when param is null.
     * </p>
     * <p>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testAssertObjectNotNullNullParam() {
        try {
            Helper.assertObjectNotNull(null, "name", LOGGER);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Tests the <code>assertArrayNotNullNorHasNull(Object[], String)</code> method.
     * </p>
     * <p>
     * If the array is null, should throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertArrayNotNullNorHasNull_null() {
        try {
            Helper.assertArrayNotNullNorHasNull(null, "name", LOGGER);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>assertArrayNotNullNorHasNull(Object[], String)</code> method.
     * </p>
     * <p>
     * If the array contains null, should throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertArrayNotNullNorHasNull_containsNull() {
        try {
            Helper.assertArrayNotNullNorHasNull(new Object[] {null}, "name", LOGGER);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>assertArrayNotNullNorHasNull(Object[], String)</code> method.
     * </p>
     * <p>
     * If the array is valid, should not throw any exception.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertArrayNotNullNorHasNull_valid() {
        Helper.assertArrayNotNullNorHasNull(new Object[0], "name", LOGGER);
    }

    /**
     * <p>
     * Tests the <code>assertStringNotEmpty(String, String)</code> method.
     * </p>
     * <p>
     * If the string is empty, should throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertStringNotEmpty_empty() {
        try {
            Helper.assertStringNotEmpty("", "name", LOGGER);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>assertStringNotEmpty(String, String)</code> method.
     * </p>
     * <p>
     * If the string is trimmed empty, should throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertStringNotEmpty_trimmedEmpty() {
        try {
            Helper.assertStringNotEmpty("  ", "name", LOGGER);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>assertStringNotEmpty(String, String)</code> method.
     * </p>
     * <p>
     * If the string is null, no exception should be thrown.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertStringNotEmpty_null() {
        Helper.assertStringNotEmpty(null, "name", LOGGER);
    }

    /**
     * <p>
     * Tests the <code>assertStringNotEmpty(String, String)</code> method.
     * </p>
     * <p>
     * If the string is not null/empty, no exception should be thrown.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertStringNotEmpty_notNullOrEmpty() {
        Helper.assertStringNotEmpty("value", "name", LOGGER);
    }

    /**
     * <p>
     * Tests the <code>assertStringNotNullNorEmpty(String, String)</code> method.
     * </p>
     * <p>
     * If the string is null, should throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertStringNotNullNorEmpty_null() {
        try {
            Helper.assertStringNotNullNorEmpty(null, "name", LOGGER);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>assertStringNotNullNorEmpty(String, String)</code> method.
     * </p>
     * <p>
     * If the string is empty, should throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertStringNotNullNorEmpty_empty() {
        try {
            Helper.assertStringNotNullNorEmpty("", "name", LOGGER);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>assertStringNotNullNorEmpty(String, String)</code> method.
     * </p>
     * <p>
     * If the string is trimmed empty, should throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertStringNotNullNorEmpty_trimmedEmpty() {
        try {
            Helper.assertStringNotNullNorEmpty("  ", "name", LOGGER);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>assertStringNotNullNorEmpty(String, String)</code> method.
     * </p>
     * <p>
     * If the string is not null/empty, no exception should be thrown.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertStringNotNullNorEmpty_notNullOrEmpty() {
        Helper.assertStringNotNullNorEmpty("value", "name", LOGGER);
    }

    /**
     * <p>
     * Tests the <code>assertIdNotUnset(long, String)</code> method.
     * </p>
     * <p>
     * If the id equals to UNSET_ID, should throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertIdNotUnset_Fail() {
        try {
            Helper.assertIdNotUnset(AuditedDeliverableStructure.UNSET_ID, "id", LOGGER);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>assertIdNotUnset(long, String)</code> method.
     * </p>
     * <p>
     * If the id doesn't equal to UNSET_ID, should not throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertIdNotUnset_Accuracy() {
        Helper.assertIdNotUnset(1000, "id", LOGGER);
    }

    /**
     * <p>
     * Tests the <code>assertLongArrayNotNullAndOnlyHasPositive(long[], String)</code> method.
     * </p>
     * <p>
     * If the long array is null, should throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertLongArrayNotNullAndOnlyHasPositive_null() {
        try {
            Helper.assertLongArrayNotNullAndOnlyHasPositive(null, "name", LOGGER);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>assertLongArrayNotNullAndOnlyHasPositive(long[], String)</code> method.
     * </p>
     * <p>
     * If the long array contains negative value, should throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertLongArrayNotNullAndOnlyHasPositive_containsNegative() {
        try {
            Helper.assertLongArrayNotNullAndOnlyHasPositive(new long[] {-1, 1}, "name", LOGGER);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>assertLongArrayNotNullAndOnlyHasPositive(long[], String)</code> method.
     * </p>
     * <p>
     * If the long array contains zero value, should throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertLongArrayNotNullAndOnlyHasPositive_containsZero() {
        try {
            Helper.assertLongArrayNotNullAndOnlyHasPositive(new long[] {0, 1}, "name", LOGGER);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>assertLongArrayNotNullAndOnlyHasPositive(long[], String)</code> method.
     * </p>
     * <p>
     * If the long array contains only positive values, should not throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertLongArrayNotNullAndOnlyHasPositiveAccuracy() {
        Helper.assertLongArrayNotNullAndOnlyHasPositive(new long[] {2, 1}, "name", LOGGER);
    }

    /**
     * <p>
     * Tests the <code>getIdString(long[])</code> method.
     * </p>
     * <p>
     * If the long array is empty, should return empty string.
     * </p>
     *
     * @since 1.2
     */
    public void testGetIdStringAccuracy1() {
        assertEquals("the id string is incorrect.", "", Helper.getIdString(new long[0]));
    }

    /**
     * <p>
     * Tests the <code>getIdString(long[])</code> method.
     * </p>
     * <p>
     * If the long array is {1, 2, 3}, should return string, like "1,2,3".
     * </p>
     *
     * @since 1.2
     */
    public void testGetIdStringAccuracy2() {
        assertEquals("the id string is incorrect.", "1,2,3", Helper.getIdString(new long[] {1, 2, 3}));
    }

    /**
     * <p>
     * Tests the <code>assertArrayLengthEqual(Object[] array, String, Object[], String)</code> method.
     * </p>
     * <p>
     * If the array length are not same, should throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertArrayLengthEqual_notSame() {
        try {
            Helper.assertArrayLengthEqual(new Object[0], "name", new Object[] {null}, "name1", LOGGER);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>assertArrayLengthEqual(Object[] array, String, Object[], String)</code> method.
     * </p>
     * <p>
     * If the array length are same, should not throw IllegalArgumentException.
     * </p>
     *
     * @since 1.2
     */
    public void testAssertArrayLengthEqual_same() {
        Helper.assertArrayLengthEqual(new Object[0], "name", new Object[0], "name1", LOGGER);
    }

    /**
     * <p>
     * Tests the <code>loadEntityFieldsSequentially(AuditedDeliverableStructure, Object[], int)</code> method.
     * </p>
     * <p>
     * The fields should be set properly.
     * </p>
     *
     * @since 1.2
     */
    public void testLoadEntityFieldsSequentiallyAccuracy() {
        SubmissionType submissionType = new SubmissionType();

        long id = 1;
        String creationUser = "creator";
        String modificationUser = "modifier";
        Date creationDate = new Date();
        Date modificationDate = new Date();
        Helper.loadEntityFieldsSequentially(submissionType, new Object[] {id, creationUser, creationDate,
            modificationUser, modificationDate}, 0);

        // verify the population.
        assertEquals("The id is not set.", id, submissionType.getId());
        assertEquals("The creation user is not set.", creationUser, submissionType.getCreationUser());
        assertEquals("The creation timestamp is not set.", creationDate, submissionType.getCreationTimestamp());
        assertEquals("The modification user is not set.", modificationUser, submissionType.getModificationUser());
        assertEquals("The modification date is not set.", modificationDate, submissionType.getModificationTimestamp());
    }

    /**
     * <p>
     * Tests the <code>loadNamedEntityFieldsSequentially(NamedDeliverableStructure, Object[], int)</code> method.
     * </p>
     * <p>
     * The fields should be set properly.
     * </p>
     *
     * @since 1.2
     */
    public void testLoadNamedEntityFieldsSequentiallyAccuracy() {
        SubmissionType submissionType = new SubmissionType();

        long id = 1;
        String creationUser = "creator";
        String modificationUser = "modifier";
        Date creationDate = new Date();
        Date modificationDate = new Date();
        String name = "name";
        String description = "description";
        Helper.loadNamedEntityFieldsSequentially(submissionType, new Object[] {id, creationUser, creationDate,
            modificationUser, modificationDate, name, description}, 0);

        // verify the population.
        assertEquals("The id is not set.", id, submissionType.getId());
        assertEquals("The creation user is not set.", creationUser, submissionType.getCreationUser());
        assertEquals("The creation timestamp is not set.", creationDate, submissionType.getCreationTimestamp());
        assertEquals("The modification user is not set.", modificationUser, submissionType.getModificationUser());
        assertEquals("The modification date is not set.", modificationDate, submissionType.getModificationTimestamp());
        assertEquals("The name is not set.", name, submissionType.getName());
        assertEquals("The description is not set.", description, submissionType.getDescription());
    }

    /**
     * <p>
     * Tests STRING_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testStringDataTypeGetValue() throws Exception {
        testGetDataType(Helper.STRING_TYPE, "select 'a' from systables WHERE tabid = 1", "a");
    }

    /**
     * <p>
     * Tests STRING_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testStringDataTypeSetValue() throws Exception {
        testSetDataType(Helper.STRING_TYPE, "insert into Submission(submission_id) values (?)", "a");
    }

    /**
     * <p>
     * Tests STRING_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     * @since 1.2
     */
    public void testStringDataTypeSetValue2() throws Exception {
        testSetDataType(Helper.STRING_TYPE, "insert into Submission(submission_id) values (?)", null);
    }

    /**
     * <p>
     * Tests LONG_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testLongDataTypeGetValue() throws Exception {
        testGetDataType(Helper.LONG_TYPE, "select 1 from systables WHERE tabid = 1", new Long(1));
    }

    /**
     * <p>
     * Tests LONG_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testLongDataTypeSetValue() throws Exception {
        testSetDataType(Helper.LONG_TYPE, "insert into Submission(submission_id) values (?)", new Long(1L));
    }

    /**
     * <p>
     * Tests LONG_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     * @since 1.2
     */
    public void testLongDataTypeSetValue2() throws Exception {
        testSetDataType(Helper.LONG_TYPE, "insert into Submission(submission_id) values (?)", null);
    }

    /**
     * <p>
     * Tests INTEGER_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     * @since 1.2
     */
    public void testIntegerDataTypeGetValue() throws Exception {
        testGetDataType(Helper.INTEGER_TYPE, "select 1 from systables WHERE tabid = 1", 1);
    }

    /**
     * <p>
     * Tests INTEGER_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     * @since 1.2
     */
    public void testIntegerDataTypeSetValue() throws Exception {
        testSetDataType(Helper.INTEGER_TYPE, "insert into submission_image(image_id) values (?)", 1);
    }

    /**
     * <p>
     * Tests INTEGER_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     * @since 1.2
     */
    public void testIntegerDataTypeSetValue2() throws Exception {
        testSetDataType(Helper.INTEGER_TYPE, "insert into submission_image(image_id) values (?)", null);
    }

    /**
     * <p>
     * Tests DOUBLE_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoubleDataTypeGetValue() throws Exception {
        testGetDataType(Helper.DOUBLE_TYPE, "select 1.0 from systables WHERE tabid = 1", new Double(1.0));
    }

    /**
     * <p>
     * Tests DOUBLE_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDoubleDataTypeSetValue() throws Exception {
        testSetDataType(Helper.DOUBLE_TYPE, "insert into Submission(submission_id) values (?)", new Double(1));
    }

    /**
     * <p>
     * Tests DOUBLE_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     * @since 1.2
     */
    public void testDoubleDataTypeSetValue2() throws Exception {
        testSetDataType(Helper.DOUBLE_TYPE, "insert into Submission(submission_id) values (?)", null);
    }

    /**
     * <p>
     * Tests BOOLEAN_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testBooleanDataTypeGetValue() throws Exception {
        testGetDataType(Helper.BOOLEAN_TYPE, "select 1 from systables WHERE tabid = 1", Boolean.TRUE);
    }

    /**
     * <p>
     * Tests BOOLEAN_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testBooleanDataTypeSetValue() throws Exception {
        testSetDataType(Helper.BOOLEAN_TYPE, "insert into Submission(submission_id) values (?)", Boolean.TRUE);
    }

    /**
     * <p>
     * Tests BOOLEAN_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testBooleanDataTypeSetValueNull() throws Exception {
        testSetDataType(Helper.BOOLEAN_TYPE, "insert into Submission(submission_id) values (?)", null);
    }

    /**
     * <p>
     * Tests DATE_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDateDataTypeGetValue() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        testGetDataType(Helper.DATE_TYPE, "select date('01/07/01') from systables WHERE tabid = 1", simpleDateFormat
                .parse("01-07-2001"));
    }

    /**
     * <p>
     * Tests DATE_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     * @since 1.2
     */
    public void testDateDataTypeSetValueValid() throws Exception {
        testSetDataType(Helper.DATE_TYPE, "insert into Submission(submission_id) values (?)", new Date());
    }

    /**
     * <p>
     * Tests DATE_TYPE data type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     * @since 1.2
     */
    public void testDateDataTypeSetValueNull() throws Exception {
        testSetDataType(Helper.DATE_TYPE, "insert into Submission(submission_id) values (?)", null);
    }

    /**
     * <p>
     * Tests the <code>createConnection(DBConnectionFactory, String, boolean, boolean)</code> method.
     * </p>
     * <p>
     * if the connection factory is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testCreateConnection_connectionFactory_null() throws Exception {
        try {
            Helper.createConnection(null, "name", true, true, LOGGER);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>createConnection(DBConnectionFactory, String, boolean, boolean)</code> method.
     * </p>
     * <p>
     * if the connection name is null, create the connection using default connection name.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testCreateConnection_connectionName_null() throws Exception {
        Connection conn = Helper.createConnection(connectionFactory, null, true, true, LOGGER);

        assertNotNull("connection should be created.", conn);

        // close the connection
        conn.close();
    }

    /**
     * <p>
     * Tests the <code>createConnection(DBConnectionFactory, String, boolean, boolean)</code> method.
     * </p>
     * <p>
     * if the connection name is specified, create the connection using given connection name.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testCreateConnection_connectionName_specified() throws Exception {
        Connection conn = Helper.createConnection(connectionFactory, "informix_connection", true, true, LOGGER);

        assertNotNull("connection should be created.", conn);

        // close the connection
        conn.close();
    }

    /**
     * <p>
     * Tests the <code>createConnection(DBConnectionFactory, String, boolean, boolean)</code> method.
     * </p>
     * <p>
     * if the connection name is specified but does not exist, should throw PersistenceException.
     * </p>
     *
     * @since 1.2
     */
    public void testCreateConnection_connectionName_notExist() {
        try {
            Helper.createConnection(connectionFactory, "informix_connection1", true, true, LOGGER);

            fail("expect PersistenceException");
        } catch (com.topcoder.management.deliverable.persistence.PersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>closeConnection(Connection)</code> method.
     * </p>
     * <p>
     * If the connection is null, should do nothing.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testCloseConnection_null() throws Exception {
        Helper.closeConnection(null);
    }

    /**
     * <p>
     * Tests the <code>closeConnection(Connection)</code> method.
     * </p>
     * <p>
     * If the connection is valid, the connection should be closed.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testCloseConnection_valid() throws Exception {
        Connection conn = Helper.createConnection(connectionFactory, "informix_connection", true, true, LOGGER);

        Helper.closeConnection(conn);

        assertTrue("the connection should be closed.", conn.isClosed());
    }

    /**
     * <p>
     * Tests <code>doQuery(Connection, String, DataType[], Object[], DataType[])</code> method.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     * @since 1.2
     */
    public void testDoQueryAccuracy1() throws Exception {
        // query the table
        Object[][] rows = Helper.doQuery(Helper.createConnection(connectionFactory, null, true, true, LOGGER),
                "SELECT submission_status_id, create_user, create_date, "
                        + "modify_user, modify_date, name, description FROM submission_status_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, LOGGER);

        assertEquals("0 results", 0, rows.length);
    }

    /**
     * <p>
     * Tests <code>doQuery(Connection, String, DataType[], Object[], DataType[])</code> method.
     * </p>
     * <p>
     * If the number of columns is incorrect, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     * @since 1.2
     */
    public void testDoQueryAccuracy2() throws Exception {
        try {
            // query the table
            Helper.doQuery(Helper.createConnection(connectionFactory, null, true, true, LOGGER),
                    "SELECT submission_status_id, create_user, create_date, "
                            + "modify_user, modify_date, name, description FROM submission_status_lu",
                    new DataType[] {}, new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE,
                        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE}, LOGGER);

        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>doQuery(Connection, String, DataType[], Object[], DataType[])</code> method.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     * @since 1.2
     */
    public void testDoQueryAccuracy3() throws Exception {
        // query the table
        Helper.doQuery(Helper.createConnection(connectionFactory, null, true, true, LOGGER), "SELECT 1 FROM systables",
                new DataType[] {}, new Object[] {}, new DataType[] {Helper.LONG_TYPE}, LOGGER);
    }

    /**
     * <p>
     * Tests <code>doQuery(DBConnectionFactory, String, String, DataType[], Object[], DataType[])</code> method.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     * @since 1.2
     */
    public void testDoQuery2Accuracy() throws Exception {
        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
            Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};
        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null,
                "SELECT submission_status_id, create_user, create_date, modify_user, modify_date, name, "
                        + "description FROM submission_status_lu WHERE submission_status_id = ?",
                new DataType[] {Helper.LONG_TYPE}, new Object[] {1l}, columnTypes, LOGGER);

        assertEquals("0 results", 0, rows.length);
    }

    /**
     * <p>
     * Tests <code>logException(Log, T)</code> method.
     * </p>
     * <p>
     * If the logger is null, no logging is done.
     * </p>
     *
     * @since 1.2
     */
    public void testLogException_null() {
        Helper.logException(null, new IOException());

        // no logging is done
    }

    /**
     * <p>
     * Tests <code>logException(Log, T)</code> method.
     * </p>
     * <p>
     * If the logger is not null, logging should be done.
     * </p>
     *
     * @since 1.2
     */
    public void testLogException_notNull() {
        Helper.logException(LogFactory.getLog("Unit Test"), new IOException());

        // logging should be done
    }

    /**
     * <p>
     * Helper method for testing DataType setValue method.
     * </p>
     *
     * @param dataType
     *            data type used for testing
     * @param query
     *            used for testing
     * @param val
     *            value to set
     * @throws Exception
     *             if any error occurs
     */
    private void testSetDataType(Helper.DataType dataType, String query, Object val) throws Exception {
        PreparedStatement preparedStatement = createPreparedStatement(query);

        dataType.setValue(preparedStatement, 1, val);

        assertEquals("Invalid parameter count.", 1, preparedStatement.getParameterMetaData().getParameterCount());
    }

    /**
     * <p>
     * Helper method for testing DataType getValue method.
     * </p>
     *
     * @param dataType
     *            data type for testing
     * @param query
     *            query used for testing
     * @param expectedValue
     *            expected value
     * @throws Exception
     *             if any error occurs
     */
    private void testGetDataType(Helper.DataType dataType, String query, Object expectedValue) throws Exception {
        ResultSet rs = null;

        try {
            rs = createResultSet(query);

            rs.next();
            Object result = dataType.getValue(rs, 1);

            assertEquals("Invalid value was returned by DataType.", expectedValue, result);
        } finally {
            closeResultSet(rs);
        }
    }

    /**
     * <p>
     * Creates PreparedStatement.
     * </p>
     *
     * @param query
     *            query string which will be used for creating prepared statement
     * @return prepared statement created for query
     * @throws Exception
     *             if any error occurs
     */
    private PreparedStatement createPreparedStatement(String query) throws Exception {
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();

            return connection.prepareStatement(query);
        } catch (DBConnectionException e) {
            TestHelper.close(connection);

            throw e;
        } catch (SQLException e) {
            TestHelper.close(connection);

            throw e;
        }
    }

    /**
     * <p>
     * Creates result set from passed query string.
     * </p>
     *
     * @param query
     *            query string which will be used to create result set
     * @return result set
     * @throws Exception
     *             if any error occurs
     */
    private ResultSet createResultSet(String query) throws Exception {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectionFactory.createConnection();
            statement = connection.createStatement();

            return statement.executeQuery(query);
        } catch (SQLException e) {

            TestHelper.close(statement);
            TestHelper.close(connection);

            throw e;
        } catch (DBConnectionException e) {

            TestHelper.close(statement);
            TestHelper.close(connection);

            throw e;
        }
    }

    /**
     * <p>
     * Closes Statement.
     * </p>
     *
     * @param statement
     *            statement to close
     */
    private static void closeStatement(Statement statement) {
        Connection connection = null;

        if (statement != null) {
            try {
                connection = statement.getConnection();
            } catch (SQLException e) {
                // ignore exception
            }
        }

        TestHelper.close(statement);
        TestHelper.close(connection);
    }

    /**
     * <p>
     * Closes result set.
     * </p>
     *
     * @param resultSet
     *            result set to close
     */
    private static void closeResultSet(ResultSet resultSet) {
        Statement statement = null;

        if (resultSet != null) {
            try {
                statement = resultSet.getStatement();
            } catch (SQLException e) {
                // ignore exception
            }
        }

        TestHelper.close(resultSet);
        closeStatement(statement);
    }
}
