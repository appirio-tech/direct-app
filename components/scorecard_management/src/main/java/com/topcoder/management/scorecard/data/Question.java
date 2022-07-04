/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;


/**
 * <p>
 * A simple container of several data fields that represents a question, the fourth and final level in the
 * scorecard model hierarchy.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is NOT thread safe.
 * </p>
 *
 * @see         QuestionType
 * @see         Section
 *
 * @author      aubergineanode, UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "question", propOrder = {"description", "guideline", "questionType", "uploadDocument", "uploadRequired"})
public class Question extends WeightedScorecardStructure {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Fields

    /**
     * The sentinel question type, used to indicate that the question type has not yet been set through the
     * constructor or the setQuestionType method (or has been reset by the resetQuestionType method).
     */
    private static final QuestionType SENTINEL_QUESTION_TYPE = null;

    /**
     * The sentinel description, used to indicate that the description has not yet been set through the
     * constructor or the setDescription method (or has been reset by the resetDescription method).
     */
    private static final String SENTINEL_DESCRIPTION = null;

    /**
     * The sentinel guideline, used to indicate that the guideline has not yet been set through the constructor
     * or the setGuideline method (or has been reset by the resetGuideline method).
     */
    private static final String SENTINEL_GUIDELINE = null;

    /**
     * The sentinel upload document switch, used as the default value for the upload document switch.
     */
    private static final boolean SENTINEL_UPLOAD_DOCUMENT = false;

    /**
     * The sentinel upload required switch, used as the default value for the upload required switch.
     */
    private static final boolean SENTINEL_UPLOAD_REQUIRED = false;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Fields

    /**
     * <p>
     * The general type of the question.
     * </p>
     *
     * <p>
     * A null value is used to indicate that no question type is associated with this Question.
     * There are no restrictions of what this field can be set to.
     * </p>
     */
    private QuestionType questionType = SENTINEL_QUESTION_TYPE;

    /**
     * <p>
     * The textual description of the question.
     * </p>
     *
     * <p>
     * A null value is used to indicate that no description is associated with this Question. There
     * are no restrictions of what this field can be set to (it may be the empty string, a blank string, etc.).
     * </p>
     */
    private String description = SENTINEL_DESCRIPTION;

    /**
     * <p>
     * The scoring guidelines for the question.
     * </p>
     *
     * <p>
     * A null value is used to indicate that no guideline is associated with this Question. There
     * are no restrictions of what this field can be set to (it may be the empty string, a blank string, etc.).
     * </p>
     */
    private String guideline = SENTINEL_GUIDELINE;

    /**
     * <p>
     * Whether or not the question allows a document to be uploaded.
     * </p>
     *
     * <p>
     * This field must be true if the uploadRequired field is true.
     * </p>
     */
    private boolean uploadDocument = SENTINEL_UPLOAD_DOCUMENT;

    /**
     * <p>
     * Whether or not the question requires a document to be uploaded.
     * </p>
     *
     * <p>
     * If this field is true, then the uploadDocument field must also be true.
     * </p>
     */
    private boolean uploadRequired = SENTINEL_UPLOAD_REQUIRED;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    /**
     * Creates a new Question using the default values.
     */
    public Question() {
        super();
    }

    /**
     * Creates a new Question using the specified identifier, and a default name, weight, question type,
     * description, guideline, upload document, and upload required.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero.
     */
    public Question(long id) {
        super(id);
    }

    /**
     * Creates a new Question using the specified identifier and name, and a default weight, question type,
     * description, guideline, upload document, and upload required.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   name
     *          The scorecard structure's name to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the name is null.
     */
    public Question(long id, String name) {
        super(id, name);
    }

    /**
     * Creates a new Question using the specified identifier and weight, and a default name, question type,
     * description, guideline, upload document, and upload required.
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
    public Question(long id, float weight) {
        super(id, weight);
    }

    /**
     * Creates a new Question using the specified identifier, weight, name, and a default question type,
     * description, guideline, upload document, and upload required.
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
    public Question(long id, String name, float weight) {
        super(id, name, weight);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - questionType Accessors and Mutators

    /**
     * Sets the question's type.
     *
     * @param   questionType
     *          The question's new type.
     */
    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    /**
     * Resets the question's type to an "undefined" value (which is null).
     */
    public void resetQuestionType() {
        setQuestionType(SENTINEL_QUESTION_TYPE);
    }

    /**
     * Gets the question's type.
     *
     * @return  The question's type.
     */
    public QuestionType getQuestionType() {
        return questionType;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - description Accessors and Mutators

    /**
     * Sets the question's description.
     *
     * @param   description
     *          The question's new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Resets the question's description to an "undefined" value (which is null).
     */
    public void resetDescription() {
        setDescription(SENTINEL_DESCRIPTION);
    }

    /**
     * Gets the question's description.
     *
     * @return  The question's description.
     */
    public String getDescription() {
        return description;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - guideline Accessors and Mutators

    /**
     * Sets the question's scoring guideline.
     *
     * @param   guideline
     *          The question's new scoring guideline.
     */
    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }

    /**
     * Resets the question's scoring guideline to an "undefined" value (which is null).
     */
    public void resetGuideline() {
        setGuideline(SENTINEL_GUIDELINE);
    }

    /**
     * Gets the question's scoring guideline.
     *
     * @return  The question's scoring guideline.
     */
    public String getGuideline() {
        return guideline;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - uploadDocument Accessors and Mutators

    /**
     * Sets the question's upload document switch.
     *
     * @param   uploadDocument
     *          The question's new upload document switch.
     *
     * @throws  IllegalStateException
     *          The uploadDocument is false, but the current upload required switch is true.
     */
    public void setUploadDocument(boolean uploadDocument) {
        this.uploadDocument = uploadDocument;
    }

    /**
     * Resets the question's upload document switch to false.
     *
     * @throws  IllegalStateException
     *          The current upload required switch is true.
     */
    public void resetUploadDocument() {
        setUploadDocument(SENTINEL_UPLOAD_DOCUMENT);
    }

    /**
     * Gets the question's upload document switch.
     *
     * @return  true if document attachments are allowed; false otherwise.
     */
    public boolean isUploadDocument() {
        return uploadDocument;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - uploadRequired Accessors and Mutators

    /**
     * Sets the question's upload required switch.
     *
     * @param   uploadRequired
     *          The question's new upload required switch.
     */
    public void setUploadRequired(boolean uploadRequired) {
        this.uploadRequired = uploadRequired;

        if (uploadRequired) {
            this.uploadDocument = true;
        }
    }

    /**
     * Resets the question's upload required switch to false.
     */
    public void resetUploadRequired() {
        setUploadRequired(SENTINEL_UPLOAD_REQUIRED);
    }

    /**
     * Gets the question's upload required switch.
     *
     * @return  true if document attachments are required; false otherwise.
     */
    public boolean isUploadRequired() {
        return uploadRequired;
    }
}
