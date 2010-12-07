/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.dao.LookupDAO;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.project.service.ScorecardReviewData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.user.UserServiceException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for selecting copilot for contest.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (Direct Manage Copilot Postings assembly)
 */
public class SelectCopilotAction extends BaseDirectStrutsAction {

    /**
     * <p>The lookup DAO.</p>
     */
    private LookupDAO lookupDAO;

    /**
     * <p>The copilot project DAO.</p>
     */
    private CopilotProjectDAO copilotProjectDAO;

    /**
     * <p>A <code>long</code> providing the ID of a project.</p>
     */
    private long tcDirectProjectId;

    /**
     * <p>A <code>long</code> providing the ID of a project.</p>
     */
    private long projectId;

    /**
     * <p>A <code>long</code> providing the ID for copilot profile.</p>
     */
    private long profileId;

    /**
     * <p>A <code>int</code> providing the placement for copilot.</p>
     */
    private int placement;

    /**
     * <p>A <code>long</code> providing the submission ID.</p>
     */
    private long submissionId;

    /**
     * <p>Constructs new <code>SelectCopilotAction</code> instance. This implementation does nothing.</p>
     */
    public SelectCopilotAction() {
    }

    /**
     * <p>
     * Handles the incoming request. Update copilot projects.
     * </p>
     *
     * @throws Exception
     *             if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        insertCopilotProject();

        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        TCSubject currentUser = getCurrentUser();
        
        // Find the Reviewer resource for current user; if there is none then create one
        Resource reviewer = contestServiceFacade.addReviewer(currentUser, getProjectId(), currentUser.getUserId());
        
        // Find a review for specified resource and submission and if not exists then create one
        Submission[] submissions = contestServiceFacade.getSoftwareProjectSubmissions(getProjectId());
        int placement = getPlacement();
        ScorecardReviewData reviewData 
            = contestServiceFacade.getReview(getProjectId(), reviewer.getId(), getSubmissionId());
        if ((reviewData.getReview() == null) || (reviewData.getReview().getSubmission() != getSubmissionId())) {
            createReview(reviewer, getSubmissionId(), placement, reviewData.getScorecard());
        }
        
        // Fill scorecards for non-winning submissions if necessary
        if (placement == 2) {
            for (int i = 0; i < submissions.length; i++) {
                Submission submission = submissions[i];
                reviewData = contestServiceFacade.getReview(getProjectId(), reviewer.getId(), submission.getId());
                if ((reviewData.getReview() == null) || (reviewData.getReview().getSubmission() != submission.getId())) {
                    createReview(reviewer, submission.getId(), 3, reviewData.getScorecard());
                }
            }
        }
    }

    /**
     * <p>Creates review for specified submission based on specified scorecard.</p>
     * 
     * @param reviewer a <code>long</code> providing the reviewer ID.
     * @param submissionId a <code>long</code> providing the submission ID.
     * @param placement an <code>int</code> providing the placement.
     * @param scorecard a <code>Scorecard</code> providing the details for scorecard.
     * @throws ReviewManagementException if an unexpected error occurs.
     */
    private void createReview(Resource reviewer, long submissionId, int placement, Scorecard scorecard) 
        throws ReviewManagementException {
        Review review = new Review();
        review.setAuthor(reviewer.getId());
        review.setCommitted(true);
        review.setCreationUser(String.valueOf(reviewer.getId()));
        review.setCreationTimestamp(new Date());
        review.setModificationUser(String.valueOf(reviewer.getId()));
        review.setModificationTimestamp(new Date());
        if (placement == 1) {
            review.setInitialScore(100F);
            review.setScore(100F);
        } else if (placement == 2) {
            review.setInitialScore(80F);
            review.setScore(80F);
        } else {
            review.setInitialScore(10F);
            review.setScore(10F);
        }
        review.setSubmission(submissionId);
        review.setScorecard(scorecard.getId());

        List<Item> items = new ArrayList<Item>();
        Group[] groups = scorecard.getAllGroups();
        for (int i = 0; i < groups.length; i++) {
            Group group = groups[i];
            Section[] allSections = group.getAllSections();
            for (int j = 0; j < allSections.length; j++) {
                Section section = allSections[j];
                Question[] questions = section.getAllQuestions();
                for (int k = 0; k < questions.length; k++) {
                    Question question = questions[k];
                    Item item = new Item();
                    if (placement == 1) {
                        item.setAnswer("10");
                    } else if (placement == 2) {
                        item.setAnswer("8");
                    } else {
                        item.setAnswer("1");
                    }
                    item.setQuestion(question.getId());
                    items.add(item);
                }
            }
        }

        review.setItems(items);

        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        contestServiceFacade.createReview(review);
    }

    /**
     * <p>Gets the reosurce with <code>Reviewer</code> role.</p>
     *  
     * @param currentUser a <code>TCSubject</code> representing the current user. 
     * @param contest a <code>SoftwareCompetition</code> providing details for Copilot Posting contest. 
     * @return a <code>Resource</code> providing the dat for resource with <code>Reviewer</code> role.  
     */
    private Resource getReviewerResource(TCSubject currentUser, SoftwareCompetition contest) {
        Resource[] resources = contest.getResources();
        for (int i = 0; i < resources.length; i++) {
            Resource resource = resources[i];
            if (resource.getResourceRole().getId() == 4) { // If Reviewer role
                String extRefId = resource.getProperty("External Reference ID");
                if (String.valueOf(currentUser.getUserId()).equals(extRefId)) {
                    return resource;
                }
            }
        }
        return null;
    }

    /**
     * <p>Insert a copilot project record.</p>
     *
     * @throws UserServiceException if any exception occurs when retireving user handle.
     * @throws CopilotDAOException if any exception occurs when performing DB operation.
     */
    private void insertCopilotProject()
            throws UserServiceException, CopilotDAOException {
        CopilotProject copilotProject = new CopilotProject();

        // populate actual values
        copilotProject.setTcDirectProjectId(getTcDirectProjectId());
        copilotProject.setCopilotProfileId(getProfileId());
        copilotProject.setCreateUser("" + DirectStrutsActionsHelper.getTCSubjectFromSession()
                .getUserId());
        copilotProject.setCreateDate(new Date());
        copilotProject.setModifyUser(copilotProject.getCreateUser());
        copilotProject.setModifyDate(new Date());

        // populate copilot type
        for (CopilotType copilotType : lookupDAO.getAllCopilotTypes()) {
            if (copilotType.getId() == 1L) {
                copilotProject.setCopilotType(copilotType);
            }
        }
        for (CopilotProjectStatus copilotProjectStatus : lookupDAO.getAllCopilotProjectStatuses()) {
            if (copilotProjectStatus.getId() == 1L) {
                copilotProject.setStatus(copilotProjectStatus);
            }
        }
        copilotProject.setPrivateProject(false);

        // insert into DB
        copilotProjectDAO.create(copilotProject);

    }


    /**
     * Retrieves the lookupDAO field.
     *
     * @return the lookupDAO
     */
    public LookupDAO getLookupDAO() {
        return lookupDAO;
    }

    /**
     * Sets the lookupDAO field.
     *
     * @param lookupDAO the lookupDAO to set
     */
    public void setLookupDAO(LookupDAO lookupDAO) {
        this.lookupDAO = lookupDAO;
    }

    /**
     * Retrieves the copilotProjectDAO field.
     *
     * @return the copilotProjectDAO
     */
    public CopilotProjectDAO getCopilotProjectDAO() {
        return copilotProjectDAO;
    }

    /**
     * Sets the copilotProjectDAO field.
     *
     * @param copilotProjectDAO
     *            the copilotProjectDAO to set
     */
    public void setCopilotProjectDAO(CopilotProjectDAO copilotProjectDAO) {
        this.copilotProjectDAO = copilotProjectDAO;
    }

    /**
     * <p>Gets the placement for copilot.</p>
     *
     * @return a <code>int</code> providing the placement for copilot.
     */
    public int getPlacement() {
        return this.placement;
    }

    /**
     * <p>Sets the placement for copilot.</p>
     *
     * @param placement a <code>int</code> providing the placement for copilot.
     */
    public void setPlacement(int placement) {
        this.placement = placement;
    }

    /**
     * <p>Gets the ID for copilot profile.</p>
     *
     * @return a <code>long</code> providing the ID for copilot profile.
     */
    public long getProfileId() {
        return this.profileId;
    }

    /**
     * <p>Sets the ID for copilot profile.</p>
     *
     * @param profileId a <code>long</code> providing the ID for copilot profile.
     */
    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    /**
     * <p>Gets the ID of a project.</p>
     *
     * @return a <code>long</code> providing the ID of a project.
     */
    public long getTcDirectProjectId() {
        return this.tcDirectProjectId;
    }

    /**
     * <p>Sets the ID of a project.</p>
     *
     * @param tcDirectProjectId a <code>long</code> providing the ID of a project.
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * <p>Gets the submission ID.</p>
     *
     * @return a <code>long</code> providing the submission ID.
     */
    public long getSubmissionId() {
        return this.submissionId;
    }

    /**
     * <p>Sets the submission ID.</p>
     *
     * @param submissionId a <code>long</code> providing the submission ID.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * <p>Gets the ID of a project.</p>
     *
     * @return a <code>long</code> providing the ID of a project.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the ID of a project.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
    
    public String toString(Review review) {
        if (review != null) {
            return "Review{" +
                   "id=" + review.getId() +
                   ", author=" + review.getAuthor() +
                   ", submission=" + review.getSubmission() +
                   ", scorecard=" + review.getScorecard() +
                   ", committed=" + review.isCommitted() +
                   ", score=" + review.getScore() +
                   ", initialScore=" + review.getInitialScore() +
                   ", items=" + review.getItems() +
                   ", comments=" + review.getComments() +
                   ", creationUser='" + review.getCreationUser() + '\'' +
                   ", creationTimestamp=" + review.getCreationTimestamp() +
                   ", modificationUser='" + review.getModificationUser() + '\'' +
                   ", modificationTimestamp=" + review.getModificationTimestamp() +
                   '}';
        } else {
            return null;
        }
    }
}
