/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

/**
 * This class is a utility class for this application.
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
class Util {
    /**
     * Checks whether the <code>Scorecard</code> is editable.
     *
     * @param scorecard the <code>Scorecard</code> instance
     * @return true if the scorecard is editable, false otherwise
     */
    public static boolean checkScorecardEditable(Scorecard scorecard) {
        def r = Review.findByScorecard(scorecard, [max:1]);
        r == null
    }
}
