/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Represents the AggregateDataModel entity. It holds the data and action attributes.
 * The data attribute is a map which stores data for the model (key is a String, and
 * value is an Object). The action attribute holds the action name.
 * </p>
 *
 * <p>
 * Methods are provided in the class to add and remove data from the model, as well
 * as getting a shallow copy of the entire model and setting and getting the action
 * name.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class AggregateDataModel {

    /**
     * <p>
     * Represents the data attribute of the AggregateDataModel entity. It's a map which uses
     * String for its key and Object for its value.
     * </p>
     *
     * <p>
     * It's set and accessed in the set/get methods. It will be initialized as empty map in the constructor,
     * and the key of the map must be non-null/non-empty string.
     * </p>
     */
    private final Map<String, Object> data;

    /**
     * <p>
     * Represents the action attribute of the AggregateDataModel entity.
     * </p>
     *
     * <p>
     * It's set and accessed in the set/get methods. It can be any value.
     * The default value is null.
     * </p>
     */
    private String action;

    /**
     * Default constructor, constructs an instance of this class and initializes the data map.
     */
    public AggregateDataModel() {
        data = new HashMap<String, Object>();
    }

    /**
     * Returns the object in the data map with the given key, or null if key not found in data map.
     *
     * @param key the key to use when getting the object from the data map
     *
     * @return the object in the data map with the given key, or null if key not found in data map
     *
     * @throws IllegalArgumentException if key is null/empty string
     */
    public Object getData(String key) {
        Helper.checkNotNullOrEmpty(key, "key");
        return data.get(key);
    }

    /**
     * Stores the object in the data map using the given key. If an object already exists in the data map
     * for the given key, it is replaced.
     *
     * @param key the key to use when storing the object in the data map
     * @param object the object to set in the data map
     *
     * @throws IllegalArgumentException if key is null/empty string
     */
    public void setData(String key, Object object) {
        Helper.checkNotNullOrEmpty(key, "key");
        data.put(key, object);
    }

    /**
     * Getter for the data map. It returns a shallow copy of the data map.
     *
     * @return a shallow copy of the data map
     */
    public Map<String, Object> getDataAsMap() {
        // return a shallow copy of the data map
        return new HashMap<String, Object>(data);
    }

    /**
     * Getter for the action.
     *
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Setter for the action. This set method does not perform any check on the argument.
     *
     * @param action the action to set in the aggregate data model
     */
    public void setAction(String action) {
        this.action = action;
    }
}
