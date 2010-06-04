/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax.serializers;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.json.JSON;
import net.sf.json.JSONException;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import com.topcoder.direct.services.view.ajax.AJAXDataSerializationException;
import com.topcoder.direct.services.view.ajax.AJAXDataSerializer;
import com.topcoder.direct.services.view.ajax.DirectJsonBeanProcessorMatcher;
import com.topcoder.direct.services.view.ajax.Helper;
import com.topcoder.direct.services.view.ajax.StudioCompetitionBeanProcessor;
import com.topcoder.direct.services.view.ajax.XMLGregorianCalendarBeanProcessor;
import com.topcoder.service.project.StudioCompetition;

/**
 * <p>
 * This implementation provides a JSON serialization of a Java Object. There are two possible outcomes: a successful
 * result or an error result.
 * </p>
 *
 * <p>
 * <Strong>Thread-Safety:</Strong> This class is mutable and thus NOT thread safe.
 * </p>
 *
 * <p>
 * Version 1.1 - Direct - View/Edit/Activate Studio Contests Assembly Change Note
 * - add JSON serialization configuration.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class JSONDataSerializer implements AJAXDataSerializer {
    /**
     * <p>
     * Represents a empty string.
     * </p>
     */
    private static final String EMPTY_STRING = "";

    /**
     * <p>
     * Represent the string of ${result} token.
     * </p>
     */
    private static final String RESULT_TOKEN_REG = "$[result]";

    /**
     * <p>
     * Represent the string of ${error} token.
     * </p>
     */
    private static final String ERROR_TOKEN_REG = "$[error]";

    /**
     * <p>
     * Represent the string of ${action} token.
     * </p>
     */
    private static final String ACTION_TOKEN_REG = "$[action]";

    /**
     * <p>
     * This some specific data that could be used to dynamically populate the two format templates (jsonResultTemplate
     * and/or jsonErrorTemplate).
     * </p>
     *
     * <p>
     * Can not be set to null but can be left as null (meaning not set). Keys: cannot be null/empty, Values: cannot be
     * null.
     * </p>
     */
    private Map<String, String> formatData;

    /**
     * <p>
     * This is a configurable JSON format for the result string coming from this serializer. Can not be set to null or
     * empty string.
     * </p>
     */
    private String jsonResultTemplate = "{\"result\": {\"name\": \"$[action]\",\"return\": $[result]}}";

    /**
     * <p>
     * This is a configurable JSON format for the error string coming from this serializer.Can not be set to null or
     * empty string.
     * </p>
     */
    private String jsonErrorTemplate = "{\"error\": {\"name\": \"$[action]\",\"errorMessage\": \"$[error]\"}}";

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public JSONDataSerializer() {
    }

    /**
     * <p>
     * Serialize the given data to string representation(typically a JSON string). We delegate everything to JSON
     * library except for one aspect which is the specialized serialization of the Exception class.
     * </p>
     *
     * @param data the data to be serialized
     * @param actionName the name of the action that is serializing the data (null is allowed)
     * @return The string representation serialized from given data object
     *
     * @throws IllegalArgumentException If the actionName is an empty string.
     * @throws AJAXDataSerializationException if there were issues with serialization. NOTE: We do not worry about not
     *             replaced or not-found tokens.
     */
    public String serializeData(String actionName, Object data) throws AJAXDataSerializationException {
        if (actionName != null && actionName.trim().length() == 0) {
            throw new IllegalArgumentException("The actionName can't be empty.");
        }
        String jsonReturnString;
        if (data == null) {
            jsonReturnString = jsonResultTemplate.replace(RESULT_TOKEN_REG, "\"\"");
        } else if (data instanceof Exception) {
            // if the data is an exception
            String error = ((Exception) data).getMessage();
            jsonReturnString = jsonErrorTemplate.replace(ERROR_TOKEN_REG, error == null ? EMPTY_STRING : error);
        } else {
            // other cases
            try {
                JsonConfig config = new JsonConfig();
                config.setJsonBeanProcessorMatcher(new DirectJsonBeanProcessorMatcher());
                config.registerJsonBeanProcessor(XMLGregorianCalendar.class, new XMLGregorianCalendarBeanProcessor());
                config.registerJsonBeanProcessor(StudioCompetition.class, new StudioCompetitionBeanProcessor());
                JSON json = JSONSerializer.toJSON(data, config);
                jsonReturnString = jsonResultTemplate.replace(RESULT_TOKEN_REG, json.toString());
            } catch (JSONException e) {
                throw new AJAXDataSerializationException("Can't serialize the data to JSON object.", e);
            }
        }
        // process the generated string to substitute any other tokens in the template
        if (formatData != null) {
            // NOTE : the ${tokenName} like string should never be in the result string or the error message.
            for (Entry<String, String> entry : formatData.entrySet()) {
                jsonReturnString = jsonReturnString.replace("$[" + entry.getKey() + "]", entry.getValue());
            }
        }
        // if the actionName is null, empty string is used
        jsonReturnString = jsonReturnString.replace(ACTION_TOKEN_REG, actionName == null ? EMPTY_STRING : actionName);
        return jsonReturnString;
    }

    /**
     * <p>
     * Setter for the formatData field.
     * </p>
     *
     * @param data data to be used with the formatted templates. It contains keys as substitution tokens (to be
     *            matched in the templates) and the values are the actual values to be inserted into the formatted
     *            string.
     *
     * @throws IllegalArgumentException If given data is null, or the keys contain null/empty element, or the values
     *             contain null
     */
    public void setFormatData(Map<String, String> data) {
        Helper.checkNull(data, "data");
        for (Entry<String, String> entry : data.entrySet()) {
            Helper.checkString(entry.getKey(), "the key in data");
            Helper.checkNull(entry.getValue(), "the value in data");
        }
        this.formatData = data;
    }

    /**
     * <p>
     * Setter for the jsonResultTemplate field.
     * </p>
     *
     * @param resultTemplate The JSON template string to be used
     * @throws IllegalArgumentException If given input is null/empty
     */
    public void setJsonResultTemplate(String resultTemplate) {
        Helper.checkString(resultTemplate, "resultTemplate");
        this.jsonResultTemplate = resultTemplate;
    }

    /**
     * <p>
     * Setter for the jsonErrorTemplate field.
     * </p>
     *
     * @param errorTemplate The JSON template string to be used
     * @throws IllegalArgumentException If given input is null/empty
     */
    public void setJsonErrorTemplate(String errorTemplate) {
        Helper.checkString(errorTemplate, "errorTemplate");
        this.jsonErrorTemplate = errorTemplate;
    }

    /**
     * <p>
     * Getter for the formatData field.
     * </p>
     *
     * @return the formatData.
     */
    public Map<String, String> getFormatData() {
        return formatData;
    }

    /**
     * <p>
     * Getter for the jsonErrorTemplate field.
     * </p>
     *
     * @return the jsonErrorTemplate.
     */
    public String getJsonErrorTemplate() {
        return this.jsonErrorTemplate;
    }

    /**
     * <p>
     * Getter for the jsonResultTemplate field.
     * </p>
     *
     * @return the jsonResultTemplate
     */
    public String getJsonResultTemplate() {
        return jsonResultTemplate;
    }

}
