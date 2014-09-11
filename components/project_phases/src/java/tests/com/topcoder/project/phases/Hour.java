/**
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved.
 *
 * TCS Project Phases 1.0 (Unit Test)
 *
 * @ Hour.java
 */
package com.topcoder.project.phases;

import java.util.Date;

/**
 * <p>This is a simply extension of Date to provide easy creation of hours.</p>
 *
 * @author TCSDEVELOPER
 *
 * @version 1.0
 */
class Hour extends Date {

    /**
     * <p>Creates with the number of hours.</p>
     *
     * @param hour the number of hours.
     */
    Hour(long hour) {
        super(hour * 60 * 60 * 1000);
    }

}
