/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.LazyInitializationException;

import com.topcoder.direct.services.project.metadata.entities.dao.IdentifiableEntity;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectFilter;
import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 * Helper class for the component.
 * It provides useful common constants and methods for all the classes package view.
 * </p>
 * <p>
 * Thread Safety: This class has no state, and thus it is thread safe.
 * </p>
 * @author Standlove, CaDenza
 * @version 1.0
 */
public final class HelperEntities {

    /**
     * Represents date format to convert Date instance into String representation.
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * This helper class cannot be instantiated.
     */
    private HelperEntities() {
        // Do nothing.
    }

    /**
     * Converts the given Date instance to string.
     * @param date the date to be formatted
     * @return the formatted date (not null)
     */
    private static String formatDate(Date date) {
        return (new SimpleDateFormat(DATE_FORMAT)).format(date);
    }

    /**
     * Sets a String field into given JSON object.
     * @param jsonObject the JSON object
     * @param key the field key
     * @param value the field value to be set
     */
    public static void setString(JSONObject jsonObject, String key, String value) {
        if (value == null) {
            jsonObject.setNull(key);
            return;
        }

        jsonObject.setString(key, value);
    }

    /**
     * Sets a Date field into given JSON object.
     * @param jsonObject the JSON object
     * @param key the field key
     * @param value the field value to be set
     */
    public static void setDate(JSONObject jsonObject, String key, Date value) {
        if (value == null) {
            jsonObject.setNull(key);
            return;
        }

        jsonObject.setString(key, formatDate(value));
    }

    /**
     * Sets a IdentifiableEntity field into given JSON object.
     * @param jsonObject the JSON object
     * @param key the field key
     * @param value the field value to be set
     */
    public static void setIdentifiableEntity(JSONObject jsonObject, String key, IdentifiableEntity value) {
        if (value == null) {
            jsonObject.setNull(key);
            return;
        }

        try {
            jsonObject.setNestedObject(key, value.toJSONObject(true));
        } catch (LazyInitializationException e) {
            jsonObject.setNull(key);
        }
    }

    /**
     * Sets a list of IdentifiableEntity into given JSON object.
     * @param jsonObject the JSON object
     * @param key the field key
     * @param value the field value to be set
     * @param <T> the type of elements maintained by the value list
     */
    public static <T extends IdentifiableEntity> void setLoggableEntityList(JSONObject jsonObject,
            String key, List<T> value) {
        if (value == null) {
            jsonObject.setNull(key);
            return;
        }

        JSONArray jsonArray = new JSONArray();
        for (T element : value) {
            if (element == null) {
                jsonArray.addNull();
            } else {
                try {
                    jsonArray.addJSONObject(element.toJSONObject(true));
                } catch (LazyInitializationException e) {
                    jsonArray.addNull();
                }
            }
        }
        jsonObject.setArray(key, jsonArray);
    }

    /**
     * Sets a list of DirectProjectFilter into given JSON object.
     * @param jsonObject the JSON object
     * @param key the field key
     * @param value the field value to be set
     * @param <T> the type of elements maintained by the value list
     */
    public static <T extends DirectProjectFilter> void setDirectProjectFilterList(JSONObject jsonObject,
            String key, List<T> value) {
        if (value == null) {
            jsonObject.setNull(key);
            return;
        }

        JSONArray jsonArray = new JSONArray();
        for (T element : value) {
            if (element == null) {
                jsonArray.addNull();
            } else {
                jsonArray.addString(element.toJSONString());
            }
        }
        jsonObject.setArray(key, jsonArray);
    }
}
