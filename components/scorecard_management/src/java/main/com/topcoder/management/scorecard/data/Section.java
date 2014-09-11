/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * <p>
 * A simple container of several data fields, along with an ordered list of questions that represents a section,
 * the third level in the scorecard model hierarchy.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is NOT thread safe.
 * </p>
 *
 * @see         Group
 * @see         Question
 *
 * @author      aubergineanode, UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "section", propOrder = {"questions"})
public class Section extends WeightedScorecardStructure {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Fields

    /**
     * <p>
     * The list of Question instances that make up this Section.
     * </p>
     *
     * <p>
     * All items in this list are required to be non-null Question instances, and no duplicates (by
     * reference equality) are allowed.
     * </p>
     */
    private final List questions = new ArrayList();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    /**
     * Creates a new Section using the default values.
     */
    public Section() {
        super();
    }

    /**
     * Creates a new Section using the specified identifier, and a default name, weight, and questions
     * list.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero.
     */
    public Section(long id) {
        super(id);
    }

    /**
     * Creates a new Section using the specified identifier and name, and a default weight, and questions
     * list.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   name
     *          The scorecard structure's name to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the name is null.
     */
    public Section(long id, String name) {
        super(id, name);
    }

    /**
     * Creates a new Section using the specified identifier and weight, and a default name and questions
     * list.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   weight
     *          The scorecard structure's weight to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the weight is less than 0, greater than 100,
     *          or equal to Float.NaN.
     */
    public Section(long id, float weight) {
        super(id, weight);
    }

    /**
     * Creates a new Section using the specified identifier, weight, name, and a default questions list.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   name
     *          The scorecard structure's name to initialize with.
     * @param   weight
     *          The scorecard structure's weight to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the weight is less than 0, greater than 100,
     *          or equal to Float.NaN, or the name is null.
     */
    public Section(long id, String name, float weight) {
        super(id, name, weight);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - questions Add Mutators

    /**
     * <p>
     * Adds the specified question to this Section.
     * </p>
     *
     * <p>
     * If the specified question already exists in this Section, this method results in no operation being
     * done. Otherwise, the question is appended to the end of the list of questions in this Section.
     * </p>
     *
     * @param   question
     *          The question to be added to this section.
     *
     * @throws  IllegalArgumentException
     *          The question is null.
     */
    public void addQuestion(Question question) {
        Util.checkNotNull(question, "question");
        addQuestionInternal(question);
    }

    /**
     * <p>
     * Adds the specified list of questions to this Section.
     * </p>
     *
     * <p>
     * All duplicate questions (either found inside the array, or against this section's list of questions) will
     * be ignored, while all other questions in the specified list will be appended to the end of the list of
     * questions in this Section.
     * </p>
     *
     * @param   questions
     *          The list of questions to be added to this section.
     *
     * @throws  IllegalArgumentException
     *          The questions is null or contains null entries.
     */
    public void addQuestions(Question[] questions) {
        Util.checkArrayNotNull(questions, "questions");

        for (int i = 0; i < questions.length; ++i) {
            addQuestionInternal(questions[i]);
        }
    }

    /**
     * Inserts the specified question into this Section at the specified index.
     *
     * @param   question
     *          The question to be inserted to this section.
     * @param   index
     *          The index where the question should be inserted into.
     *
     * @throws  IllegalArgumentException
     *          The question is null.
     * @throws  IndexOutOfBoundsException
     *          The index is negative or greater than the number of questions.
     */
    public void insertQuestion(Question question, int index) {
        Util.checkNotNull(question, "question");

        if (!questions.contains(question)) {
            questions.add(index, question);
        }
    }

    /**
     * <p>
     * Adds the specified non-null question to this Section.
     * </p>
     *
     * <p>
     * This is mainly used to avoid checking for nulls again during the addQuestions method.
     * </p>
     *
     * @param   question
     *          The question to be added to this section.
     */
    private void addQuestionInternal(Question question) {
        if (!questions.contains(question)) {
            questions.add(question);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - questions Remove Mutators

    /**
     * <p>
     * Removes the specified question from this Section.
     * </p>
     *
     * <p>
     * If the specified question does not exist in this section (by reference equality), this method results in no
     * operation being done. Otherwise, the specified question will be removed from the list of questions in this
     * Section.
     * </p>
     *
     * @param   question
     *          The question to remove from this section.
     *
     * @throws  IllegalArgumentException
     *          The question is null.
     */
    public void removeQuestion(Question question) {
        Util.checkNotNull(question, "question");
        questions.remove(question);
    }

    /**
     * Removes the question at the specified index from this Section.
     *
     * @param   index
     *          The index of the question to be removed.
     *
     * @throws  IndexOutOfBoundsException
     *          The index is negative or greater than or equal to the number of questions.
     */
    public void removeQuestion(int index) {
        questions.remove(index);
    }

    /**
     * <p>
     * Removes the specified list of questions from this Section.
     * </p>
     *
     * <p>
     * Any question not found in this section will be ignored, while all other questions in the specified list will
     * be removed from list of questions in this Section.
     * </p>
     *
     * @param   questions
     *          The list of questions to be removed from this section.
     *
     * @throws  IllegalArgumentException
     *          The questions is null or contains null entries.
     */
    public void removeQuestions(Question[] questions) {
        Util.checkArrayNotNull(questions, "questions");
        this.questions.removeAll(Arrays.asList(questions));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - questions Clear Mutator

    /**
     * Clears all questions associated with this Section.
     */
    public void clearQuestions() {
        questions.clear();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - questions Get Accessors

    /**
     * Gets the Question at the specified position in this Section.
     *
     * @param   questionIndex
     *          The index of the question to retrieve.
     *
     * @return  The Question at the specified position in this Section.
     *
     * @throws  IndexOutOfBoundsException
     *          The questionIndex is less than 0, or greater than or equal to the number of questions.
     */
    public Question getQuestion(int questionIndex) {
        return (Question) questions.get(questionIndex);
    }
    
	/**
     * Gets a list of all the questions associated with this Section.
     *
     * @return  A list of all the questions associated with this Section.
     */
	public List getQuestions() {
		return questions;
	}

	/**
     * Gets a list of all the questions associated with this Section.
     *
     * @return  A list of all the questions associated with this Section.
     */
    public Question[] getAllQuestions() {
        return (Question[]) questions.toArray(new Question[questions.size()]);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - questions Miscellaneous Accessors and Mutators

    /**
     * Gets the number of questions currently associated with this Section.
     *
     * @return  The number of questions currently associated with this Section.
     */
    public int getNumberOfQuestions() {
        return questions.size();
    }

    /**
     * Checks that the sum of the weights of the questions list is 100 plus/minus the tolerance.
     *
     * @param   tolerance
     *          The absolute amount that the sum of weights can at most differ from 100.
     *
     * @return  true if the question weights sum to 100 plus/minus the tolerance; false
     *          otherwise.
     *
     * @throws  IllegalArgumentException
     *          The tolerance is negative, Float.POSITIVE_INFINITY, or Float.NaN.
     */
    public boolean checkWeights(float tolerance) {
        return Util.checkWeights(questions, tolerance);
    }
}
