/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.accuracytests;

import java.util.HashMap;
import java.util.Map;

/**
 * An entity class representing a database row record.
 * <p>
 * It just a simple wrapper of <code>Map</code>. The key is the column name, and the value is the colum
 * value in the specified row.
 * </p>
 *
 * @author mayi
 * @version 1.0
 */
public class DBRecord {
    /**
     * A map of the values. The key is name of column, and the value is corresponding column value in
     * specified row.
     */
    private Map values = new HashMap();

    /**
     * Default constructor.
     */
    public DBRecord() {
        // empty
    }

    /**
     * Add a key-value pair to this record.
     *
     * @param key the column name
     * @param value the column value in the row
     */
    public void addValue(String key, Object value) {
        values.put(key, value);
    }

    /**
     * Get a value for the given <code>key</code>.
     *
     * @param key the column name
     * @return the corresponding value.
     */
    public Object getValue(String key) {
        return values.get(key);
    }
}
