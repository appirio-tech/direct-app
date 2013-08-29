/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.conversion.TypeConversionException;
import com.opensymphony.xwork2.conversion.impl.XWorkBasicConverter;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ContainerProvider;
import com.opensymphony.xwork2.config.impl.DefaultConfiguration;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.topcoder.direct.services.view.util.DirectUtils;

/**
 * <p>
 * This is the converter for the {@link XMLGregorianCalendar} type. This data type is intensively used in the project
 * and contest entities. So it's necessary to convert the data from and to this data type.
 * </p>
 *
 * <p>
 * Version 1.1 - Direct Launch Software Contests Assembly Change Note
 * <ul>
 * <li>Uses DirectUtils to convert the util date to xml date.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 - Module Assembly - TC Direct Struts 2 Upgrade Change Note
 * <ul>
 * <li>Remove useless MINUTE_FACTOR constant</li>
 * <li>Update the initialization for XWorkBasicConverter type instance due to changes in Struts 2.3.15.1</li>
 * </ul>
 * </p>
 * <p>
 * <code>Thread Safety</code>: this class is thread safe because it's stateless and the parent class is thread safe.
 * </p>
 *
 * @author fabrizyo, FireIce, TCASSEMBLER
 * @version 1.2
 */
public class XMLGregorianCalendarTypeConverter extends StrutsTypeConverter {

    /**
     * <p>
     * Represents the <code>XWorkBasicConverter</code> to convert Date from string and convert Date to string.
     * </p>
     */
    private XWorkBasicConverter basicConverter;

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
     * @param context the context
     * @param values the values
     * @param clazz the class used to convert to
     * @return the converted object
     * @throws TypeConversionException if any error occurs
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
            Date date = (Date) getXWorkBasicConverter().convertValue(context, values[0], null, "names", values[0], Date.class);

            // possible null, if unable to convert.
            if (null == date) {
                throw new TypeConversionException("Can not convert \'" + values[0] + "' to Date type");
            }

            return DirectUtils.newXMLGregorianCalendar(date);
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
     * @param context the context
     * @param object the object value to convert
     * @return the string representation
     * @throws TypeConversionException if any error occurs
     */
    @SuppressWarnings("unchecked")
    public String convertToString(Map context, Object object) {
        if (object instanceof XMLGregorianCalendar) {
            GregorianCalendar gregorianCalendar = ((XMLGregorianCalendar) object).toGregorianCalendar();

            // get the date
            Date date = gregorianCalendar.getTime();

            // convert the string representation.
            return (String) getXWorkBasicConverter().convertValue(context, date, String.class);
        } else {
            throw new TypeConversionException("The converted object is not XMLGregorianCalendar type");
        }
    }


    /**
     * Initialize the XWorkBasicConverter instance as needed, and only once.

     * @return the intialized XWorkBasicConverter instance
     */
    private synchronized XWorkBasicConverter getXWorkBasicConverter() {
        if (basicConverter == null) {
            Configuration config = new DefaultConfiguration();
            List<ContainerProvider> providers = new ArrayList<ContainerProvider>();
            providers.add(new XWorkConfigurationProvider());
            config.reloadContainer(providers);
            ObjectFactory objectFactory = new ObjectFactory();
            objectFactory.setContainer(config.getContainer());
            basicConverter = new XWorkBasicConverter();
            basicConverter.setObjectFactory(objectFactory);
        }
        return basicConverter;
    }
}
