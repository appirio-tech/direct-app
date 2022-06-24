/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import com.topcoder.direct.services.view.util.DirectUtils;

/**
 * <p>
 * Bean processor for <code>XMLGregorianCalendar</code>.
 * </p>
 *
 * @author TCSDEVEDLOPER
 * @version 1.0
 * @since Direct - View/Edit/Activate Studio Contests Assembly
 */
public class XMLGregorianCalendarBeanProcessor implements JsonBeanProcessor {

    /**
     * <p>
     * Processes the bean.
     * </p>
     *
     * @param bean the bean to be processed.
     * @param jsonConfig the configuration when processing. it will be ignored
     * @return json object for the bean
     * @throws IllegalArgumentException if the bean is not of type <code>StudioCompetition</code>
     */
    public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
        if (!(bean instanceof XMLGregorianCalendar)) {
            throw new IllegalArgumentException("bean type should be XMLGregorianCalendar.");
        }

        return new JSONObject().element("date", getDateString((XMLGregorianCalendar) bean));
    }

    /**
     * <p>
     * Gets date string from xml date.
     * </p>
     *
     * @param xmlDate <code>XMLGregorianCalendar</code> object
     * @return the date string
     */
    private String getDateString(XMLGregorianCalendar xmlDate) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return dateFormat.format(DirectUtils.getDate(xmlDate));
    }

}
