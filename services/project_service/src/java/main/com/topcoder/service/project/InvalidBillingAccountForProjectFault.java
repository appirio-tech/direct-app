/*
* Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
*/
package com.topcoder.service.project;

/**
 * <p>
 * The fault is thrown when the billing account to add to the project does not have the same client as the existing
 * billing accounts of the project.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InvalidBillingAccountForProjectFault extends ProjectServiceFault {

    /**
     * <p>
     * Constructs this fault with an error message.
     * </p>
     *
     * @param message The error message describing this fault. Possibly null/empty.
     */
    public InvalidBillingAccountForProjectFault(String message) {
        super(message);
    }

}
