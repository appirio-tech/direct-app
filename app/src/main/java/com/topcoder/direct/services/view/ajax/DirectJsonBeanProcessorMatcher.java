/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax;

import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.json.processors.JsonBeanProcessorMatcher;

/**
 * <p>
 * Bean matcher to handle <code>XMLGregorianCalendar</code>.
 * </p>
 *
 * @author TCSDEVEDLOPER
 * @version 1.0
 * @since Direct - View/Edit/Activate Studio Contests Assembly
 */
public class DirectJsonBeanProcessorMatcher extends JsonBeanProcessorMatcher {

    /**
     * <p>
     * Get matches. It will use default matcher for matching no associated with <code>XMLGregorianCalendar</code>.
     * </p>
     *
     * @param target the target of the class
     * @param set the set of available classes
     * @return the matched result
     */
    @Override
    public Object getMatch(Class target, Set set) {
        Class result = (Class) JsonBeanProcessorMatcher.DEFAULT.getMatch(target, set);
        if (result == null && XMLGregorianCalendar.class.isAssignableFrom(target)) {
            return XMLGregorianCalendar.class;
        } else {
            return result;
        }
    }

}
