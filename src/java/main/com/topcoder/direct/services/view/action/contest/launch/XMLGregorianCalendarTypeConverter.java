/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.conversion.TypeConversionException;
import com.opensymphony.xwork2.conversion.impl.XWorkBasicConverter;

/**
 * <p>
 * This is the converter for the {@link XMLGregorianCalendar} type. This data type is intensively used in the project
 * and contest entities. So it's necessary to convert the data from and to this data type.
 * </p>
 * <p>
 * <code>Thread Safety</code>: this class is thread safe because it's stateless and the parent class is thread safe.
 * </p>
 *
 * @author fabrizyo, FireIce
 * @version 1.0
 */
public class XMLGregorianCalendarTypeConverter extends StrutsTypeConverter {

    /**
     * <p>
     * Represents the minute factor.
     * </p>
     */
    private static final int MINUTE_FACTOR = 60 * 1000;

    /**
     * <p>
     * Represents the <code>XWorkBasicConverter</code> to convert Date from string and convert Date to string.
     * </p>
     */
    private XWorkBasicConverter basicConverter = new XWorkBasicConverter();

    /**
     * <p>
     * Creates a <code>XMLGregorianCalendarTypeConverter</code> instance.
     * </p>
     */
    public XMLGregorianCalendarTypeConverter() {
    }

    /**
     * <p>
     * Convert the string to a {@link XMLGregorianCalendar}.
     * </p>
     *
     * @param context
     *            the context
     * @param values
     *            the values
     * @param clazz
     *            the class used to convert to
     * @return the converted object
     * @throws TypeConversionException
     *             if any error occurs
     */
    @SuppressWarnings("unchecked")
    public Object convertFromString(Map context, String[] values, Class clazz) {
        if (null == values) {
            throw new TypeConversionException("The values array should not be null");
        } else if (1 != values.length) {
            throw new TypeConversionException("The values array should have one element extactly.");
        } else if (null == values[0]) {
            throw new TypeConversionException("The element of values array should not be null.");
        }
        try {
            Date date = (Date) basicConverter.convertValue(context, values[0], null, "names", values[0], Date.class);

            // possible null, if unable to convert.
            if (null == date) {
                throw new TypeConversionException("Can not convert \'" + values[0] + "' to Date type");
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();

            XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendarDate(year, month, day,
                    calendar.getTimeZone().getOffset(date.getTime()) / MINUTE_FACTOR);
            return xmlGregorianCalendar;
        } catch (XWorkException e) {
            throw new TypeConversionException("Fail to convert the string to date type.", e);
        } catch (DatatypeConfigurationException e) {
            throw new TypeConversionException("Fail to create the data type factory.", e);
        }
    }

    /**
     * <p>
     * Convert the {@link XMLGregorianCalendarTypeConverter} to a string representation.
     * </p>
     *
     * @param context
     *            the context
     * @param object
     *            the object value to convert
     * @return the string representation
     * @throws TypeConversionException
     *             if any error occurs
     */
    @SuppressWarnings("unchecked")
    public String convertToString(Map context, Object object) {
        if (object instanceof XMLGregorianCalendar) {
            GregorianCalendar gregorianCalendar = ((XMLGregorianCalendar) object).toGregorianCalendar();

            // get the date
            Date date = gregorianCalendar.getTime();

            // convert the string representation.
            return (String) basicConverter.convertValue(context, date, String.class);
        } else {
            throw new TypeConversionException("The converted object is not XMLGregorianCalendar type");
        }
    }
}
