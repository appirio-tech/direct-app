/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard;

import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.validation.ValidationException;

/**
 * This interface defines the contract that scorecard validators should implement. The implementation classes will be
 * used by ScorecardManagerImpl to perform scorecard validation. ScorecardManagerImpl loads the validation
 * implementation from the configuration settings, which allows further validator to plug-in. The implementation
 * classes should have a constructor that receives a namespace string parameter so that they're exchangeable with each
 * other by changing configuration settings in the manager.
 * @author tuenm, zhuzeyuan
 * @version 1.0.1
 */
public interface ScorecardValidator {
    /**
     * Validate the given scorecard and its sub items base on some specific rules. This method will throw
     * ValidationException on the first item that is not follow the a rule. The exception should contains meaningful
     * error message about the validation problem. The set of rules is the logic of implementation classes.
     * @param scorecard
     *            The scorecard to validate.
     * @throws ValidationException
     *             if validation fails.
     */
    public void validateScorecard(Scorecard scorecard) throws ValidationException;
}
