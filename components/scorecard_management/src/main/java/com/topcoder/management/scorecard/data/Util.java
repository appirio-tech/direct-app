/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import java.util.Iterator;
import java.util.List;

/**
 * Contains a bunch of static utility methods, mainly for argument checking and such.
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
final class Util {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Fields

    /**
     * All lists of weighted scorecard structure should sum to this value.
     */
    private static final float TARGET_SUM_WEIGHT = 100.0f;


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    /**
     * Prevents instantiation.
     */
    private Util() {
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods - Argument Checking

    /**
     * Checks to make sure the specified array is not null, and that it contains no nulls.
     *
     * @param   argument
     *          The array argument to check.
     * @param   argumentName
     *          The name of the argument to use in the error message.
     *
     * @throws  IllegalArgumentException
     *          The argument is null or contains a null.
     */
    static void checkArrayNotNull(Object[] argument, String argumentName) {
        checkNotNull(argument, argumentName);

        for (int i = 0; i < argument.length; ++i) {
            if (argument[i] == null) {
                throw new IllegalArgumentException("The " + argumentName + "[" + i + "] cannot be null.");
            }
        }
    }

    /**
     * Checks to make sure the specified long is a positive integer.
     *
     * @param   argument
     *          The long argument to check.
     * @param   argumentName
     *          The name of the argument to use in the error message.
     *
     * @throws  IllegalArgumentException
     *          The argument is less than or equal to zero.
     */
    static void checkNotNonPositive(long argument, String argumentName) {
        if (argument <= 0) {
            throw new IllegalArgumentException("The " + argumentName + " cannot be non-positive.");
        }
    }

    /**
     * Checks to make sure the specified object is not null.
     *
     * @param   argument
     *          The object argument to check.
     * @param   argumentName
     *          The name of the argument to use in the error message.
     *
     * @throws  IllegalArgumentException
     *          The argument is null.
     */
    static void checkNotNull(Object argument, String argumentName) {
        if (argument == null) {
            throw new IllegalArgumentException("The " + argumentName + " cannot be null.");
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods - Weight Sum Checking

    /**
     * Checks that the weights of the specified list of WeightedScorecardStructure sum up to 100, plus/minus
     * the tolerance.
     *
     * @param   weightedScorecardStructures
     *          The list of WeightedScorecardStructure instances to check.
     * @param   tolerance
     *          The tolerance for floating point inaccuracy.
     *
     * @return  true if the weights in the list sum up to 100 plus/minus the tolerance; false
     *          otherwise.
     *
     * @throws  IllegalArgumentException
     *          The weightedScorecardStructures is null, or the tolerance is negative,
     *          Float.POSITIVE_INFINITY, or Float.NaN.
     */
    static boolean checkWeights(List weightedScorecardStructures, float tolerance) {
        // Sanity check method call.
        checkNotNull(weightedScorecardStructures, "weightedScorecardStructures");

        if ((tolerance < 0) || (tolerance == Float.POSITIVE_INFINITY) || Float.isNaN(tolerance)) {
            throw new IllegalArgumentException("The tolerance must be a positive real number.");
        }

        // Sum up the weights of each structure in the list.
        float sum = 0.0f;
        Iterator itr = weightedScorecardStructures.iterator();

        while (itr.hasNext()) {
            sum += ((WeightedScorecardStructure) itr.next()).getWeight();
        }

        // Check the sum against 100 plus/minus the tolerance.
        return (Math.abs(sum - TARGET_SUM_WEIGHT) < tolerance);
    }
}
