/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * <p>This class introduced to store and to retrieve <code>Status</code> enumeration instances.</p>
 * <p>For more information look at the <a href="http://www.hibernate.org/272.html">official Hibernate site</a>.</p>
 * <p><strong>Thread safety: </strong></p> <p>This class is immutable and thread safe.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class StatusUserType implements UserType, ParameterizedType {
    /**
     * <p>The SQL types constant which is to be returned by {@link #sqlTypes()} method.</p>
     */
    private static final int[] SQL_TYPES = {Types.INTEGER};

    /**
     * <p>Method does nothing as no parameters expected.</p>
     *
     * @param params ignored
     */
    public void setParameterValues(Properties params) {
    }

    /**
     * Returns supported sqlTypes for this custom type.
     *
     * @return array including the only type <code>Type.INTEGER</code>
     * @see #SQL_TYPES
     */
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    /**
     * <p>Returns supported Java classes. Only {@link com.topcoder.catalog.entity.Status} is supported.</p>
     *
     * @return <code>com.topcoder.catalog.entity.Status.class</code>
     */
    public Class returnedClass() {
        return Status.class;
    }

    /**
     * <p>Converts database value to <code>Status</code> instance.</p>
     *
     * @param resultSet the result set returned by query
     * @param names     names of the columns in the query
     * @param owner     ignored
     * @return if there is non-null value in corresponding column,
     *         then <code>Status</code> instance or <code>null</code> otherwise
     * @throws SQLException to Hibernate from the underlying JDBC level
     */
    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
        throws SQLException {
        int code = resultSet.getInt(names[0]);
        Object result = null;
        if (!resultSet.wasNull()) {
            result = Status.valueOf(code);
        }
        return result;
    }

    /**
     * <p>Sets parameter value corresponding to <code>value</code>.</p>
     * <p>Enumeration value converted to integer, if it's not null and instance of <code>Status</code>.</p>
     * <p>Otherwise null is set.</p>
     *
     * @param preparedStatement the statement to set the parameter
     * @param value             the value to store
     * @param index             the index in the statement
     * @throws SQLException to Hibernate from the underlying JDBC level
     */
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index)
        throws SQLException {
        if (!(value instanceof Status)) {
            preparedStatement.setNull(index, Types.INTEGER);
        } else {
            preparedStatement.setInt(index, ((Status) value).getStatusId());
        }
    }

    /**
     * <p>Returns the same object.</p>
     *
     * @param value object to copy
     * @return the given parameter (as no copy of an enum could exist)
     */
    public Object deepCopy(Object value) {
        return value;
    }

    /**
     * <p>Returns <code>false</code>, as instances of <p>Status</p> are not mutable.</p>
     *
     * @return <code>false</code> always.
     */
    public boolean isMutable() {
        return false;
    }

    /**
     * <p>Transform the object into its cache representation.</p>
     *
     * @param cached the object to be cached
     * @param owner  the owner of the cached object
     * @return object passed in <code>cached</code> parameter
     */
    public Object assemble(Serializable cached, Object owner) {
        return cached;
    }

    /**
     * <p>Transform the object into its cache representation.</p>
     *
     * @param value the given object
     * @return object passed in the <code>value</code> parameter
     */
    public Serializable disassemble(Object value) {
        return (Serializable) value;
    }

    /**
     * <p>Simply returns the first parameter as instances of Status are immutable.</p>
     *
     * @param original the value from the detached entity being merged
     * @param target   the value in the managed entity
     * @param owner    the owner of the cached object
     * @return the first parameter
     */
    public Object replace(Object original, Object target, Object owner) {
        return original;
    }

    /**
     * <p>Get a hashcode for the instance, consistent with persistence "equality".</p>
     *
     * @param x object to calculate hash code
     * @return hash code of the object, or '0' if the given parameter is null
     */
    public int hashCode(Object x) {
        return x == null ? 0 : x.hashCode();
    }

    /**
     * <p>Enums are equal if and only if they are identical.</p>
     *
     * @param x first object to check for equality
     * @param y second object to check for equality
     * @return <code>true</code> if they are identical, <code>false</code> otherwise
     */
    public boolean equals(Object x, Object y) {
        return x == y;
    }
}
