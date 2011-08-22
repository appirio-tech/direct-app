/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.specreview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.HtmlUtils;

import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.review.comment.specification.SpecReviewComment;
import com.topcoder.service.review.comment.specification.SpecReviewCommentService;
import com.topcoder.service.review.comment.specification.UserComment;
import com.topcoder.service.review.specification.SpecificationReview;
import com.topcoder.service.review.specification.SpecificationReviewService;
import com.topcoder.service.review.specification.SpecificationReviewStatus;
import com.topcoder.service.user.UserService;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This action is responsible for handling the request to view a specification
 * review. It extends <code>SpecificationReviewAction</code>.
 * </p>
 * 
 * <p>
 * <code>SpecificationReviewService</code> and
 * <code>SpecReviewCommentService</code> are used to perform the request. The
 * result data is aggregated into
 * <code>ViewSpecificationReviewActionResultData</code>.
 * </p>
 * 
 * <p>
 * <strong>An example of how to configure action in struts.xml:</strong>
 * 
 * <pre>
 * &lt;action name="viewSpecificationReviewAction" class="viewSpecificationReviewAction"&gt;
 *     &lt;result name="input"&gt;/input.jsp&lt;/result&gt;
 *     &lt;result name="success"&gt;/displaySpecReviewComments.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 * 
 * </p>
 * <p>Version 1.1 change notes:
 * <ul>
 *  <li>Update {@link #execute()} to use the projectId instead of contestId</li>
 * </ul>
 * </p>

 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe. This should
 * be fine since different instances of this class should be created to serve
 * different user requests and will not be shared across user threads (this will
 * be taken care of by Struts 2 framework). The business services used by this
 * component are reasonably thread safe and are responsible for handling
 * transactions. Hence this class can be considered as being effectively thread
 * safe.
 * </p>
 * 
 * @author caru, TCSDEVELOPER, morehappiness
 * @version 1.1
 * @since 1.0
 */
public class ViewSpecificationReviewAction extends SpecificationReviewAction {

    /**
     * <p>
     * Represents the specification review service used to retrieve the
     * specification review and its status.
     * </p>
     * 
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and
     * accessed by getter. Used in <code>execute</code> method.
     * </p>
     */
    private SpecificationReviewService specificationReviewService;

    /**
     * <p>
     * Represents the specification review comment service used to retrieve the
     * specification review comments.
     * </p>
     * 
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and
     * accessed by getter. Used in <code>execute</code> method.
     * </p>
     */
    private SpecReviewCommentService specReviewCommentService;

    /**
     * <p>
     * A <code>ViewSpecificationReviewActionResultData</code> providing the view
     * data for displaying by <code>Specification Review</code> view.
     * </p>
     */
    private ViewSpecificationReviewActionResultData viewData;

    /**
     * <p>
     * A <code>SessionData</code> providing interface to current session.
     * </p>
     */
    private SessionData sessionData;

    /**
     * <p>
     * Represents the user service used to retrieve the user handle.
     * </p>
     * 
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and
     * accessed by getter. Used in <code>execute</code> method.
     * </p>
     */
    private UserService userService;

    /**
     * <p>
     * Represents the contest service facade used to retrieve contest start date.
     * <p>
     *
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and accessed by getter. Used in
     * <code>execute</code> method.
     * </p>
     */
    private ContestServiceFacade contestServiceFacade;
    
    /**
     * Default constructor, creates new instance.
     */
    public ViewSpecificationReviewAction() {
    }

    /**
     * <p>
     * Executes the request to view a specification review.
     * </p>
     * 
     * <p>
     * No exception is thrown by this method. If any exception occurs, the
     * exception is wrapped in
     * <code>ViewSpecificationReviewActionException</code> with a proper
     * message, stored in the model using the <code>setException</code> method,
     * and <code>Action.ERROR</code> is returned.
     * </p>
     * 
     * <p>
     * If any required field hasn't been injected then
     * <code>ViewSpecificationReviewActionException</code> will be stored in the
     * model and <code>Action.ERROR</code> is returned.
     * </p>
     * 
     * @return the action result String, will either be
     *         <code>Action.SUCCESS</code> or <code>Action.ERROR</code>
     *         depending on whether method was successful
     */
    public String execute() {
        try {
            // make sure required fields are present and valid
            if (specReviewCommentService == null) {
                return Helper
                        .handleActionError(
                                this,
                                new ViewSpecificationReviewActionException(
                                        "specReviewCommentService has not been injected."));
            }

            if (specificationReviewService == null) {
                return Helper
                        .handleActionError(
                                this,
                                new ViewSpecificationReviewActionException(
                                        "specificationReviewService has not been injected."));
            }

            // fetch the specification review, status and comments
            List<SpecificationReview> specificationReviews = specificationReviewService
                    .getAllSpecificationReviews(getTCSubject(), getProjectId());

            SpecificationReviewStatus specificationReviewStatus = specificationReviewService
                    .getSpecificationReviewStatus(getTCSubject(),
                            getProjectId());
            
            List<SpecReviewComment> specReviewComments = specReviewCommentService
                    .getSpecReviewComments(getTCSubject(), getProjectId(),
                            isStudio());

            Map<Long, List<SpecComment>> specComments = new HashMap<Long, List<SpecComment>>();
            
            Map<Long, Boolean> lastResponses = new HashMap<Long, Boolean>();
            
            for (SpecificationReview specificationReview : specificationReviews) {
                for (Item item : specificationReview.getReview().getItems()) {
                    if (!specComments.containsKey(item.getQuestion())) {
                        specComments.put(item.getQuestion(),
                                new ArrayList<SpecComment>());
                    }
                                        
                    for (Comment comment : item.getAllComments()) {
                        SpecComment specComment = new SpecComment();
                        specComment.setCommentType(SpecCommentType.REVIEWER_COMMENT);
                        specComment.setComment(comment.getComment());
                        specComment.setReviewerCommentType(comment.getCommentType().getName());
                        specComment.setCommentDate(specificationReview.getReview().getCreationTimestamp());
                        specComment.setCommentBy(specificationReview.getCreationUserHandle());
                        
                        specComments.get(item.getQuestion()).add(specComment);
                        
                        lastResponses.put(item.getQuestion(), isMaxScore(item.getAnswer()));
                    }
                }                   
            }
            
            SpecificationReview specificationReview = null;
            if (!specificationReviews.isEmpty()) {
                specificationReview = specificationReviews.get(specificationReviews.size() - 1);    
            
                for (Group group : specificationReview.getScorecard().getAllGroups()) {
                    for (Section section : group.getAllSections()) {
                        for (Question question : section.getAllQuestions()) {
                            question.setDescription(StringUtils.replaceChars(
                                    HtmlUtils.htmlEscape(question.getDescription()),
                                    '\n', ' '));
                            question.setGuideline(StringUtils.replaceChars(
                                    HtmlUtils.htmlEscape(question.getGuideline()),
                                    '\n', ' '));  
                        }
                    }
                }
            }
            

            if (specReviewComments != null) {
                for (SpecReviewComment specReviewComment : specReviewComments) {
                    for (UserComment userComment : specReviewComment.getComments()) {
                        SpecComment specComment = new SpecComment();
                        
                        specComment.setCommentType(SpecCommentType.USER_COMMENT);
                        specComment.setComment(userComment.getComment());
                        specComment.setCommentDate(userComment.getCommentDate());
                        specComment.setCommentBy(userComment.getCommentBy());
                        specComment.setCommentId(userComment.getCommentId());
                        
                        specComments.get(specReviewComment.getQuestionId()).add(specComment);                
                    }
                }    
            }    
            
            for (Entry<Long, List<SpecComment>> comments : specComments.entrySet()) {
                Collections.sort(comments.getValue());
            }

            // load the specification review, status and comments to the model
            ViewSpecificationReviewActionResultData result = new ViewSpecificationReviewActionResultData();
            result.setSpecificationReview(specificationReview);
            result.setShowProgress(true);
            result.setSpecificationReviewStatus(specificationReviewStatus);
            result.setSpecReviewComments(specReviewComments);
            result.setSpecComments(specComments);
            result.setResponses(lastResponses);
            
            // for normal request flow prepare various data to be displayed to
            // user
            // Set contest stats
            ContestStatsDTO contestStats = DirectUtils.getContestStats(
                    getTCSubject(), getProjectId(), isStudio());
            result.setContestStats(contestStats);

            // Get current session
            HttpServletRequest request = DirectUtils.getServletRequest();
            sessionData = new SessionData(request.getSession());
            // Set current project contests
            List<TypedContestBriefDTO> contests = DataProvider
                    .getProjectTypedContests(getTCSubject().getUserId(),
                            contestStats.getContest().getProject().getId());
            sessionData.setCurrentProjectContests(contests);

            // Set current project context based on selected contest
            this.sessionData.setCurrentProjectContext(contestStats.getContest().getProject());
            this.sessionData.setCurrentSelectDirectProjectID(contestStats.getContest().getProject().getId());
            
            // check whether spec review finished
            result.setShowSpecReview(true);
            
            setViewData(result);
            DirectUtils.setDashboardData(getTCSubject(), getProjectId(), viewData,
                    contestServiceFacade, isSoftware());

             // Set projects data
            List<ProjectBriefDTO> projects = DataProvider.getUserProjects(getTCSubject().getUserId());
            UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
            userProjectsDTO.setProjects(projects);
            getViewData().setUserProjects(userProjectsDTO);
            
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();

            return Helper
                    .handleActionError(
                            this,
                            new ViewSpecificationReviewActionException(
                                    "An error occurred in execute method for ViewSpecificationReviewAction.",
                                    e));
        }
    }

    /**
     * Getter for specification review service.
     * 
     * @return the specification review service
     */
    public SpecificationReviewService getSpecificationReviewService() {
        return specificationReviewService;
    }

    /**
     * Setter for specification review service.
     * 
     * @param specificationReviewService
     *            the specification review service
     * @throws IllegalArgumentException
     *             if argument is null
     */
    public void setSpecificationReviewService(
            SpecificationReviewService specificationReviewService) {
        ExceptionUtils.checkNull(specificationReviewService, null, null,
                "specificationReviewService cannot be null.");
        this.specificationReviewService = specificationReviewService;
    }

    /**
     * Getter for specification review comment service.
     * 
     * @return the specification review comment service
     */
    public SpecReviewCommentService getSpecReviewCommentService() {
        return specReviewCommentService;
    }

    /**
     * Setter for specification review comment service.
     * 
     * @param specReviewCommentService
     *            the specification review comment service
     * @throws IllegalArgumentException
     *             if argument is null
     */
    public void setSpecReviewCommentService(
            SpecReviewCommentService specReviewCommentService) {
        ExceptionUtils.checkNull(specReviewCommentService, null, null,
                "specReviewCommentService cannot be null.");
        this.specReviewCommentService = specReviewCommentService;
    }

    /**
     * Retrieve the viewData field.
     * 
     * @return the viewData
     */
    public ViewSpecificationReviewActionResultData getViewData() {
        return viewData;
    }

    /**
     * Set the viewData field.
     * 
     * @param viewData
     *            the viewData to set
     */
    public void setViewData(ViewSpecificationReviewActionResultData viewData) {
        this.viewData = viewData;
    }

    /**
     * Retrieve the sessionData field.
     * 
     * @return the sessionData
     */
    public SessionData getSessionData() {
        return sessionData;
    }

    /**
     * Set the sessionData field.
     * 
     * @param sessionData
     *            the sessionData to set
     */
    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }

    /**
     * Retrieve the user service field.
     * 
     * @return the user service
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Set the user service field.
     * 
     * @param userService
     *            the user service to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Check whether passed answer is maximum possible score.
     * 
     * @param answer the answer to check
     * @return whetehr answer is maximum possible
     */
    private static Boolean isMaxScore(String answer) {
    	// Yes/No questions have maximum answer "1"
    	if (answer == null || "0".equals(answer)) {
    		return false;
    	} else if ("1".equals(answer)) {
        	return true;
        }
        // other max questions are N/N
        int pos = answer.indexOf('/');
        if (answer.substring(0, pos).equals(answer.substring(pos+1))) {
        	return true;
        }
        return false;
    }

    /**
     * <p>
     * Determines if it is software contest or not.
     * </p>
     *
     * @return true if it is software contest
     * @since 1.1
     */
    public boolean isSoftware() {
        return !isStudio();
    }

    /**
     * Gets the contestServiceFacade field.
     * 
     * @return the contestServiceFacade
     * @since 1.1
     */
    public ContestServiceFacade getContestServiceFacade() {
        return contestServiceFacade;
    }

    /**
     * Sets the contestServiceFacade field.
     *
     * @param contestServiceFacade the contestServiceFacade to set
     * @since 1.1
     */
    public void setContestServiceFacade(ContestServiceFacade contestServiceFacade) {
        this.contestServiceFacade = contestServiceFacade;
    }
}
