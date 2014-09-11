/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.entity.StatusUserType;
import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

/**
 * <p>Unit test case for {@link StatusUserType}.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class StatusUserTypeTest extends TestCase {
    /**
     * <p>Represents StatusUserType instance for testing.</p>
     */
    private StatusUserType statusUserType;

    /**
     * <p>Creates a new instance of StatusUserType.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        statusUserType = new StatusUserType();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        statusUserType = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>StatusUserType()</code> constructor.</p>
     */
    public void testStatusUserType() {
        assertNotNull("Unable to instantiate StatusUserType", statusUserType);
    }

    /**
     * <p>Tests <code>sqlTypes()</code> method.</p>
     */
    public void testSqlTypes() {
        final int[] types = statusUserType.sqlTypes();
        assertEquals("There should be the only one type", 1, types.length);
        assertEquals("There should be Types.INTEGER type", Types.INTEGER, types[0]);

    }

    /**
     * <p>Tests <code>setParameterValues()</code> method.</p>
     */
    public void testSetParameterValues() {
        statusUserType.setParameterValues(null);
        // method does nothing, so check no exceptions thrown at least
    }

    /**
     * <p>Tests <code>returnedClass()</code> method.</p>
     */
    public void testReturnedClass() {
        assertEquals("Should support Status.class", Status.class, statusUserType.returnedClass());
    }

    /**
     * <p>Tests <code>replace()</code> method.</p>
     */
    public void testReplace() {
        assertEquals("Should return the same object", Status.ACTIVE,
            statusUserType.replace(Status.ACTIVE, null, null));
    }

    /**
     * <p>Tests <code>disassemble()</code> method.</p>
     */
    public void testDisassemble() {
        assertEquals("Should return the same object", Status.ACTIVE,
            statusUserType.disassemble(Status.ACTIVE));
    }

    /**
     * <p>Tests <code>assemble()</code> method.</p>
     */
    public void testAssemble() {
        assertEquals("Should return the same object", Status.ACTIVE,
            statusUserType.assemble(Status.ACTIVE, null));
    }

    /**
     * <p>Tests <code>assemble()</code> method.</p>
     */
    public void testDeepCopy() {
        assertEquals("Should return the same object", Status.ACTIVE,
            statusUserType.deepCopy(Status.ACTIVE));
    }

    /**
     * <p>Tests <code>isMutable()</code> method.</p>
     */
    public void testIsMutable() {
        assertEquals("Should indicate that class is immutable", false,
            statusUserType.isMutable());
    }

    /**
     * <p>Tests <code>hashCode()</code> method.</p>
     */
    public void testHashCode() {
        assertEquals("Should return original hashCode", Status.ACTIVE.hashCode(),
            statusUserType.hashCode(Status.ACTIVE));
    }

    /**
     * <p>Tests <code>hashCode()</code> method for null argument.</p>
     */
    public void testHashCodeNull() {
        assertEquals("Should return 0", 0, statusUserType.hashCode(null));
    }

    /**
     * <p>Tests <code>equals()</code> method for identical objects.</p>
     */
    public void testEqualsIdentical() {
        assertTrue("Object are equal", statusUserType.equals(Status.ACTIVE, Status.ACTIVE));
    }

    /**
     * <p>Tests <code>equals()</code> method when first param is null.</p>
     */
    public void testEqualsFirstNull() {
        assertFalse("Object are not equal", statusUserType.equals(null, Status.ACTIVE));
    }

    /**
     * <p>Tests <code>equals()</code> method when second param is null.</p>
     */
    public void testEqualsSecondNull() {
        assertFalse("Object are not equal", statusUserType.equals(Status.ACTIVE, null));
    }

    /**
     * <p>Tests <code>equals()</code> method when both params are null.</p>
     */
    public void testEqualsBothNull() {
        assertTrue("Object are equal", statusUserType.equals(null, null));
    }

    /**
     * <p>Tests <code>equals()</code> method when params are not null and not identical.</p>
     */
    public void testEqualsNotIdentical() {
        assertFalse("Object are not equal", statusUserType.equals(Status.ACTIVE, Status.NEW_POST));
    }

    /**
     * <p>Tests <code>nullSafeGet()</code> for non-null object.</p>
     *
     * @throws Exception to jUnit.
     */
    @SuppressWarnings("unchecked")
    public void testNullSafeGetNonNull() throws Exception {
        final String[] columnNames = {"name"};
        final IMocksControl control = EasyMock.createControl();
        ResultSet rs = control.createMock(ResultSet.class);
        control.checkOrder(true);
        rs.getInt(columnNames[0]);
        control.andReturn(Status.DUPLICATE.getStatusId());
        rs.wasNull();
        control.andReturn(false);
        control.replay();
        final Object retrieved = statusUserType.nullSafeGet(rs, columnNames, null);
        assertTrue("There should be exact object retrieved", retrieved == Status.DUPLICATE);
    }

    /**
     * <p>Tests <code>nullSafeGet()</code> for null object.</p>
     *
     * @throws Exception to jUnit.
     */
    @SuppressWarnings("unchecked")
    public void testNullSafeGetNull() throws Exception {
        final String[] columnNames = {"name"};
        final IMocksControl control = EasyMock.createControl();
        ResultSet rs = control.createMock(ResultSet.class);
        control.checkOrder(true);
        rs.getInt(columnNames[0]);
        control.andReturn(0);
        rs.wasNull();
        control.andReturn(true);
        control.replay();
        final Object retrieved = statusUserType.nullSafeGet(rs, columnNames, null);
        assertNull("There should be exact object retrieved", retrieved);
    }

    /**
     * <p>Tests <code>nullSafeSet()</code> for null object.</p>
     *
     * @throws Exception to jUnit.
     */
    @SuppressWarnings("unchecked")
    public void testNullSafeSetNull() throws Exception {
        final IMocksControl control = EasyMock.createControl();
        PreparedStatement ps = control.createMock(PreparedStatement.class);
        ps.setNull(EasyMock.eq(1), EasyMock.eq(Types.INTEGER));
        control.replay();
        statusUserType.nullSafeSet(ps, null, 1);
        control.verify();
    }

    /**
     * <p>Tests <code>nullSafeSet()</code> for non-null object.</p>
     *
     * @throws Exception to jUnit.
     */
    @SuppressWarnings("unchecked")
    public void testNullSafeSetNonNull() throws Exception {
        final IMocksControl control = EasyMock.createControl();
        PreparedStatement ps = control.createMock(PreparedStatement.class);
        ps.setInt(EasyMock.eq(1), EasyMock.eq(Status.ACTIVE.getStatusId()));
        control.replay();
        statusUserType.nullSafeSet(ps, Status.ACTIVE, 1);
        control.verify();
    }
}
