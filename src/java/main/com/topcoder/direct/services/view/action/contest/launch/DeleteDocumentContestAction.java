/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.service.studio.UploadedDocument;

/**
 * <p>
 * This action will delete the document. It will first remove the document from the contest then delete the
 * document itself. This is done using the ContestServiceFacade's <code>removeDocumentFromContest</code> and
 * <code>removeDocument methods</code>, respectively.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure bean in applicationContext.xml for Spring:</strong>
 * <pre>
 * &lt;bean id="deleteDocumentContestAction"
 *      class="com.topcoder.service.actions.DeleteDocumentContestAction"&gt;
 *      &lt;property name="contestServiceFacade" ref="contestServiceFacade"&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="deleteDocumentContestAction" class="deleteDocumentContestAction"&gt;
 *     &lt;interceptor-ref name="demoTCSStack" /&gt;
 *     &lt;result name="input"&gt;/deleteDocument.jsp&lt;/result&gt;
 *     &lt;result name="success"&gt;/success.jsp?type=deleteDocument&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> In Struts 2 framework the action is constructed for every request so the thread safety
 * is not required (instead in Struts 1 the thread safety is required because the action instances are reused). This
 * class is mutable and stateful, so it's not thread safe.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public class DeleteDocumentContestAction extends ContestAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.deleteDocumentContestAction.";

    /**
     * <p>
     * Represents the id of the document of the contest. It's used to delete the document.
     * </p>
     *
     * <p>
     * It must be greater than 0. It's changed by the setter and returned by the getter.
     * </p>
     */
    private long documentId;

    /**
     * <p>
     * This is the id of the contest. It's used to delete the attachment to the document.
     * <p>
     *
     * <p>
     * It must be greater than 0. It's changed by the setter and returned by the getter.
     * </p>
     */
    private long contestId;

    /**
     * Default constructor, creates new instance.
     */
    public DeleteDocumentContestAction() {
    }

    /**
     * Executes the action. It will first remove the document from the contest then delete the document itself.
     *
     * @throws Exception if some error occurs during method execution
     * @throws IllegalStateException if <code>contestServiceFacade</code> hasn't been injected
     */
    public void executeAction() throws Exception {
        ActionHelper.checkInjectedFieldNull(getContestServiceFacade(), "contestServiceFacade");
        UploadedDocument uploadedDocument = new UploadedDocument();
        uploadedDocument.setContestId(contestId);
        uploadedDocument.setDocumentId(documentId);
        getContestServiceFacade().removeDocumentFromContest(null, uploadedDocument);
        getContestServiceFacade().removeDocument(null, documentId);
    }

    /**
     * Getter for the document ID.
     *
     * @return the document ID
     */
    public long getDocumentId() {
        return documentId;
    }

    /**
     * Setter for the document ID. Struts 2 validation is used to check that the argument is greater than 0.
     *
     * @param documentId the document ID
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "documentIdGreaterThanZero",
        fieldName = "documentId", expression = "documentId > 0",
        message = ActionHelper.GREATER_THAN_ZERO)
    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    /**
     * Getter for the contest ID.
     *
     * @return the contest ID
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Setter for the contest ID. Struts 2 validation is used to check that the argument is greater than 0.
     *
     * @param contestId the contest ID
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "contestIdGreaterThanZero",
        fieldName = "contestId", expression = "contestId > 0",
        message = ActionHelper.GREATER_THAN_ZERO)
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }
}
