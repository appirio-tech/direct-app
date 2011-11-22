/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

/**
 * The Double converter to converter the double and String.
 * 
 * Thread safety: The class is mutable and not thread safe. But it'll not caused thread safety issue if used under
 * Spring container.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public class DoubleConverter extends StrutsTypeConverter {
    /**
     * Default Constructor.
     */
    public DoubleConverter() {
    }

    /**
     * Convert String to double value.
     * 
     * @param context
     *            - the context map.
     * @param values
     *            - the values to convert.
     * @param toClass
     *            - the convert to class.
     * @return the converted value
     */
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (Double.class == toClass) {
            String doubleStr = values[0];
            try {
                Double d = Double.parseDouble(doubleStr);
                return d;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return doubleStr;
        }
        return 0;
    }

    /**
     * Convert double value to String.
     * 
     * @param context
     *            - the context map.
     * @param o
     *            - the value to convert.
     * @return the converted value
     */
    public String convertToString(Map context, Object o) {
        return o.toString();
    }
}
