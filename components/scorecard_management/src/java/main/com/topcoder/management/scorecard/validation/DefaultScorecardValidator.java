/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.validation;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.Section;

/**
 * This is the default implementation of the ScorecardValidator interface to provide scorecard validation functions.
 *
 * Thread safety: This class is immutable and thread safe.<br>
 * @author tuenm, zhuzeyuan
 * @version 1.0.1
 */
public class DefaultScorecardValidator implements com.topcoder.management.scorecard.ScorecardValidator {

    /**
     * The precision error to be used while comparing two floating numbers.
     */
    private static final float EPS = 1e-9f;

    /**
     * The floating number for the total weight of a whole scorecard/group/section. This number should be 100.
     */
    private static final float ONE_HUNDRED = 100f;

    /**
     * The maximal length for the version string.
     */
    private static final int VERSION_MAXLENGTH = 16;

    /**
     * The maximal length for a name string.
     */
    private static final int NAME_MAXLENGTH = 64;

    /**
     * Create a new instance of DefaultScorecardValidator. This class does not have any configuration settings. But
     * the namespace parameter is provided to comply with the contract defined in ScorecardValidator interface.
     * @param namespace
     *            The namespace to load configuration settings.
     */
    public DefaultScorecardValidator(String namespace) {
    }

    /**
     * Check if the object is null.
     * @param obj
     *            the object to check.
     * @param name
     *            the object's name
     * @throws ValidationException
     *             if the object is null.
     */
    private void assertObjectNotNull(Object obj, String name) throws ValidationException {
        if (obj == null) {
            throw new ValidationException("The object " + name + " must not be null.");
        }
    }

    /**
     * Check if the float number is between 0 and 100, inclusive.
     * @param number
     *            the float to check.
     * @param name
     *            the float's name
     * @throws ValidationException
     *             if the float is out of range [0,100].
     */
    private void assertFloatBetween0And100(float number, String name) throws ValidationException {
        if (number < -EPS || number > ONE_HUNDRED + EPS) {
            throw new ValidationException("The floating number " + name + " must be between 0 and 100, inclusive.");
        }
    }

    /**
     * Check if the name string is empty or longer than allowance.
     * @param s
     *            the string to check.
     * @param name
     *            the string's name
     * @throws ValidationException
     *             if the string is null, empty or longer than allowance.
     */
    private void checkNameString(String s, String name) throws ValidationException {
        assertObjectNotNull(s, name);
        if (s.trim().length() == 0) {
            throw new ValidationException("The string " + name + " must not be empty.");
        }
        if (s.length() > NAME_MAXLENGTH) {
            throw new ValidationException("Length of the string " + name + " must be smaller than or equal to "
                + NAME_MAXLENGTH);
        }
    }

    /**
     * Check if the version string is illegal.
     * @param version
     *            the version string to check.
     * @throws ValidationException
     *             if the version string is illegal.
     */
    private void checkVersion(String version) throws ValidationException {
        assertObjectNotNull(version, "version");
        if (version.length() > VERSION_MAXLENGTH) {
            throw new ValidationException("Length of the version string must be smaller than or equal to 16.");
        }
        for (int i = 0; i < version.length(); i++) {
            if (!Character.isDigit(version.charAt(i)) && version.charAt(i) != '.') {
                throw new ValidationException("Every single char in version string must be a digit or a dot.");
            }
        }
        if (version.length() == 0) {
            throw new ValidationException("Version string must contain at least one numeric character.");
        }
        if (version.charAt(0) == '.' || version.charAt(version.length() - 1) == '.') {
            throw new ValidationException(
                "The dot character cannot be the first or the last character in the version string.");
        }
    }

    /**
     * Validate the given group and its sub items base on some specific rules. This method will throw
     * ValidationException on the first item that is not follow the a rule. For the set of rules, see class
     * documentation of <code>validateScorecard</code>. This class is used as a helper method for
     * <code>validateScorecard</code>.<br>
     * @param group
     *            The group to validate.
     * @throws ValidationException
     *             if validation fails.
     */
    private void validateGroup(Group group) throws ValidationException {
        assertObjectNotNull(group, "group");

        // While getting weight from the group/section/question, IllegalStateException might be thrown while the value
        // is not set properly.
        try {
            // To validate the property fields for the group itself.
            checkNameString(group.getName(), "group_name");
            assertFloatBetween0And100(group.getWeight(), "group_weight");

            // Fectch all the sections from this group, and validate one by one.
            Section[] sections = group.getAllSections();
            assertObjectNotNull(sections, "all_sections");

            for (int j = 0; j < sections.length; j++) {
                assertObjectNotNull(sections[j], "section");

                // To validate the property fields for the section itself.
                checkNameString(sections[j].getName(), "section_name");
                assertFloatBetween0And100(sections[j].getWeight(), "section_weight");

                // Fectch all the questions from this section, and validate one by one.
                Question[] questions = sections[j].getAllQuestions();
                assertObjectNotNull(questions, "all_questions");

                for (int k = 0; k < questions.length; k++) {
                    // To validate the property fields for this question itself.
                    assertObjectNotNull(questions[k], "question");
                    assertObjectNotNull(questions[k].getDescription(), "question_description");

                    if (questions[k].getDescription().trim().length() == 0) {
                        throw new ValidationException("description must not be empty.");
                    }
                    assertFloatBetween0And100(questions[k].getWeight(), "question_weight");
                }
            }
        } catch (IllegalStateException ise) {
            throw new ValidationException("error occurs while accessing", ise);
        }
    }

    /**
     * Validate the given scorecard and its sub items base on some specific rules. This method will throw
     * ValidationException on the first item that is not follow the a rule.For the set of rules, see class
     * documentation of this class.<br>
     * @param scorecard
     *            The scorecard to validate.
     * @throws IllegalArgumentException
     *             if the given scorecard is null.
     * @throws ValidationException
     *             if validation fails.
     */
    public void validateScorecard(Scorecard scorecard) throws ValidationException {
        if (scorecard == null) {
            throw new IllegalArgumentException("scorecard must not be null.");
        }

        // If the id is illegal, it will throw IllegalStateException while getting.
        try {
            // To validate the property fields for the scorecard itself.
            assertObjectNotNull(scorecard.getScorecardStatus(), "scorecard_status");
            assertObjectNotNull(scorecard.getScorecardType(), "scorecard_type");
            scorecard.getCategory();
            checkNameString(scorecard.getName(), "name");
            checkVersion(scorecard.getVersion());

            // The minScore cannot be smaller than 0, while the maxScore cannot be smaller than or equal to the
            // minScore.
            if (scorecard.getMinScore() < -EPS) {
                throw new ValidationException("min_score must be greater than or equal to zero.");
            }
            if (scorecard.getMaxScore() < scorecard.getMinScore() + EPS) {
                throw new ValidationException("max_score must be greater than min_score.");
            }
        } catch (IllegalStateException ise) {
            throw new ValidationException("error occurs when validating scorecard", ise);
        }

        // Fetch all the groups from this scorecard, and validate one by one.
        Group[] groups = scorecard.getAllGroups();
        assertObjectNotNull(groups, "all_groups");

        // Check the sum of weights for scorecard/group/section.
        if (!scorecard.checkWeights()) {
            throw new ValidationException("all total weight must equal to 100.");
        }
        for (int i = 0; i < groups.length; i++) {
            validateGroup(groups[i]);
        }
    }
}
