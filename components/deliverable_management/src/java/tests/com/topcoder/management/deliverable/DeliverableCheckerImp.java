/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;


/**
 * This is a test class for NamedDeliverableStructure.
 * It extends NamedDeliverableStructure and no method is overridden.
 *
 * @author singlewood
 * @version 1.0
 *
 */
public class DeliverableCheckerImp implements DeliverableChecker {

    /**
     *  Create a new DeliverableCheckerImp.
     */
    public DeliverableCheckerImp() {
        // no operation.
    }

    /**
     * Checks the given deliverable to see if it is complete.
     *
     * @param deliverable The deliverable to check
     * @throws IllegalArgumentException If deliverable is null
     * @throws DeliverableCheckingException If there is an error when determining whether a
     *             Deliverable has been completed or not
     */
    public void check(Deliverable deliverable) throws DeliverableCheckingException {

    }

}
