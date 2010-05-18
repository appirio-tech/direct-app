/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.util.List;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.ContestData;

/**
 * <p>
 * This action will return all documents of the contest by getting the contest itself. Studio documents are in
 * UploadedDocument instances. Only studio competitions can be used for this action.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure bean in applicationContext.xml for Spring:</strong>
 * <pre>
 * &lt;bean id="getDocumentsContestAction"
 *     class="com.topcoder.service.actions.GetDocumentsContestAction"&gt;
 *     &lt;property name="contestServiceFacade" ref="contestServiceFacade"&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="getDocumentsContestAction" class="getDocumentsContestAction"&gt;
 *     &lt;interceptor-ref name="demoTCSStack" /&gt;
 *     &lt;result name="input"&gt;/getDocuments.jsp&lt;/result&gt;
 *     &lt;result name="success"&gt;/success.jsp?type=getDocuments&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> The class is not thread safe because it's mutable by the setters and the values of this
 * class will change based on the request parameters. It's not required to be thread safe because in Struts 2 the
 * actions (different from Struts 1) are created for every request.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public class GetDocumentsContestAction extends StudioOrSoftwareContestAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.getDocumentsContestAction.";

    /**
     * Default constructor, creates new instance.
     */
    public GetDocumentsContestAction() {
    }

    /**
     * <p>
     * Executes the action. Retrieves and sets the documents to the model.
     * </p>
     *
     * <p>
     * Struts 2 validations are used to ensure that either project ID or contest ID are set, but not both.
     * </p>
     *
     * @throws Exception if some error occurs during method execution
     * @throws IllegalStateException if <code>contestServiceFacade</code> hasn't been injected
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "projectIdXorContestId",
        fieldName = "contestIdAndprojectId", expression = "((projectId != 0) ^ (contestId != 0)) == 1",
        message = "Either projectId or contestId must be set")
    public void executeAction() throws Exception {
        ActionHelper.checkInjectedFieldNull(getContestServiceFacade(), "contestServiceFacade");

        if (isStudioCompetition()) {
            StudioCompetition studioCompetition = getContestServiceFacade().getContest(null, getContestId());
            ContestData contestData = studioCompetition.getContestData();
            performLogic(contestData == null ? null : contestData.getDocumentationUploads());
        } else {
            SoftwareCompetition softwareCompetition = getContestServiceFacade().getSoftwareContestByProjectId(
                null, getProjectId());
            performLogic(softwareCompetition.getAssetDTO().getDocumentation());
        }
    }

    /**
     * Add the competition documents to the model, unless competition documents is null or contains
     * null elements.
     *
     * @param competitionDocuments the competition documents to add to the model
     *
     * @throws Exception if some error occurs during method execution
     */
    protected void performLogic(List<?> competitionDocuments) throws Exception {
        if (competitionDocuments != null) {
            // make sure no elements are null
            boolean nullElementFound = false;
            for (int i = 0; i < competitionDocuments.size(); ++i) {
                if (competitionDocuments.get(i) == null) {
                    nullElementFound = true;
                    break;
                }
            }

            // if it contains null elements then don't set result
            if (!nullElementFound) {
                setResult(competitionDocuments);
            }
        }
    }
}
