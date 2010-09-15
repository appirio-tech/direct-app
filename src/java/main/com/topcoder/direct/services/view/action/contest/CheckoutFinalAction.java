/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.UserTransaction;
import javax.servlet.http.HttpServletRequest;

import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.StudioContestSubmissionsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.payment.PaymentType;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.project.CompetitionPrize;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.CompletedContestData;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.SubmissionPaymentData;

/**
 * <p>
 * A <code>Struts</code> action to be used for handling requests for save rank of final submissions for
 * <code>Studio</code> contest and process payment.
 * </p>
 * 
 * @author flexme
 * @since Submission Viewer Release 3 assembly
 * @version 1.0
 */
public class CheckoutFinalAction extends StudioOrSoftwareContestAction {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -943504346090290977L;

    /**
     * <p>
     * A <code>String</code> providing the ranking of the milestone submissions.
     * </p>
     */
    private String ranks;

    /**
     * <p>
     * A <code>String</code> providing the submissions id of Additional Purchases.
     * </p>
     */
    private String additionalPurchases;

    /**
     * <p>
     * A <code>long</code> providing the id of the billing account.
     * </p>
     */
    private long billingProjectId;

    /**
     * <p>
     * A <code>List</code> providing the placement submissions.
     * </p>
     */
    private List<SubmissionData> placePayments;

    /**
     * <p>
     * A <code>List</code> providing the additional purchase submissions.
     * </p>
     */
    private List<SubmissionData> additionalPayments;

    /**
     * <p>
     * A <code>List</code> providing the placement prizes.
     * </p>
     */
    private List<Double> placePrizes;

    /**
     * <p>
     * A <code>double</code> providing the total payments.
     * </p>
     */
    private double totalPayments;

    /**
     * <p>
     * A <code>StudioContestSubmissionsDTO</code> providing the view data for displaying by <code>Studio Contest
     * Submissions</code> view.
     * </p>
     */
    private StudioContestSubmissionsDTO viewData;

    /**
     * <p>
     * A <code>SessionData</code> providing interface to current session.
     * </p>
     */
    private SessionData sessionData;

    /**
     * <p>
     * Gets the current session associated with the incoming request from client.
     * </p>
     * 
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData() {
        return this.sessionData;
    }

    /**
     * <p>
     * Gets the data to be displayed by view mapped to this action.
     * </p>
     * 
     * @return a <code>StudioContestSubmissionsDTO</code> providing the collector for data to be rendered by the view
     *         mapped to this action.
     */
    public StudioContestSubmissionsDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>
     * Gets the placement submissions.
     * </p>
     * 
     * @return A <code>List</code> providing the placement submissions.
     */
    public List<SubmissionData> getPlacePayments() {
        return this.placePayments;
    }

    /**
     * <p>
     * Gets the additional purchase submissions.
     * </p>
     * 
     * @return A <code>List</code> providing the additional purchase submissions.
     */
    public List<SubmissionData> getAdditionalPayments() {
        return this.additionalPayments;
    }

    /**
     * <p>
     * Gets the placement prizes.
     * </p>
     * 
     * @return A <code>List</code> providing the placement prizes.
     */
    public List<Double> getPlacePrizes() {
        return this.placePrizes;
    }

    /**
     * <p>
     * Gets the total payments.
     * </p>
     * 
     * @return A <code>double</code> providing the total payments.
     */
    public double getTotalPayments() {
        return this.totalPayments;
    }

    /**
     * <p>
     * Constructs new <code>CheckoutFinalAction</code> instance. This implementation does nothing.
     * </p>
     */
    public CheckoutFinalAction() {
        this.viewData = new StudioContestSubmissionsDTO();
        this.placePayments = new ArrayList<SubmissionData>();
        this.additionalPayments = new ArrayList<SubmissionData>();
        this.placePrizes = new ArrayList<Double>();
        this.totalPayments = 0;
    }

    /**
     * <p>
     * Gets the ranking of the milestone submissions.
     * </p>
     * 
     * @return A <code>String</code> providing the ranking of the milestone submissions.
     */
    public String getRanks() {
        return ranks;
    }

    /**
     * <p>
     * Sets the ranking of the milestone submissions.
     * </p>
     * 
     * @param ranks
     *            A <code>String</code> providing the ranking of the milestone submissions.
     */
    public void setRanks(String ranks) {
        this.ranks = ranks;
    }

    /**
     * <p>
     * Gets the submissions id of Additional Purchases.
     * </p>
     * 
     * @return A <code>String</code> providing the submissions id of Additional Purchases.
     */
    public String getAdditionalPurchases() {
        return additionalPurchases;
    }

    /**
     * <p>
     * Sets the submissions id of Additional Purchases.
     * </p>
     * 
     * @param additionalPurchases
     *            A <code>String</code> providing the submissions id of Additional Purchases.
     */
    public void setAdditionalPurchases(String additionalPurchases) {
        this.additionalPurchases = additionalPurchases;
    }

    /**
     * Gets the id of the billing account.
     * 
     * @return A <code>long</code> providing the id of the billing account.
     */
    public long getBillingProjectId() {
        return billingProjectId;
    }

    /**
     * Sets the id of the billing account.
     * 
     * @param projectId
     *            A <code>long</code> providing the id of the billing account.
     */
    public void setBillingProjectId(long billingProjectId) {
        this.billingProjectId = billingProjectId;
    }

    /**
     * <p>
     * Handles the incoming request. Save the ranking of the final submissions and process payment.
     * </p>
     * 
     * @throws Exception
     *             if an unexpected error occurs.
     */
    @Override
    public void executeAction() throws Exception {
        if (!isStudioCompetition()) {
            return;
        }

        // Get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());

        // Get the submissions which should be ranked and paid
        String[] rankingSubmissions = ranks.split(",");
        // Get the submissions which should be additional purchase
        String[] additionalSubmissions = additionalPurchases.split(",");
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        long contestId = getContestId();
        StudioCompetition studioCompetition = contestServiceFacade.getContest(currentUser, contestId);

        List<SubmissionData> subs = DirectUtils.getStudioContestSubmissions(studioCompetition.getContestData(), ContestRoundType.FINAL,
                currentUser, contestServiceFacade);
        boolean hasCheckout = DirectUtils.getSubmissionsCheckout(subs, ContestRoundType.FINAL);

        CompletedContestData contestData = new CompletedContestData();
        contestData.setContestId(contestId);
        List<SubmissionPaymentData> paymentData = new ArrayList<SubmissionPaymentData>();

        // Get all submissions
        Map<Long, SubmissionData> submissions = new HashMap<Long, SubmissionData>();
        for (SubmissionData sub : subs) {
            submissions.put(sub.getSubmissionId(), sub);
        }
        List<SubmissionData> mileSubmissions = DirectUtils.getStudioContestSubmissions(studioCompetition.getContestData(),
                ContestRoundType.MILESTONE, currentUser, contestServiceFacade);
        for (SubmissionData sub : mileSubmissions) {
            submissions.put(sub.getSubmissionId(), sub);
        }
        UserTransaction ut = DirectUtils.getUserTransaction();
     
        try {
            ut.begin();
            if (!hasCheckout) {
                // only save rank and process payment for placement and milestone when has not been checked out
                // save rank
                if (ranks.length() > 0) {
                    for (int i = 0; i < rankingSubmissions.length; i++) {
                        contestServiceFacade.updateSubmissionUserRank(currentUser, Long.parseLong(rankingSubmissions[i]),
                                i + 1, false);
                    }
                }

                double[] placePayments = new double[studioCompetition.getPrizes().size()];
                for (CompetitionPrize prize : studioCompetition.getPrizes()) {
                    placePayments[prize.getPlace() - 1] = prize.getAmount();
                }

                // place payments
                if (ranks.length() > 0) {
                    for (int i = 0; i < rankingSubmissions.length; i++) {
                        SubmissionPaymentData payment = new SubmissionPaymentData();
                        payment.setAmount(placePayments[i]);
                        payment.setAwardMilestonePrize(false);
                        payment.setRank(i + 1);
                        payment.setId(Long.parseLong(rankingSubmissions[i]));
                        paymentData.add(payment);
                        this.placePayments.add(submissions.get(payment.getId()));
                        this.placePrizes.add(payment.getAmount());
                        totalPayments += payment.getAmount();
                    }
                }

                // milestone payments
                int milestoneNumber = 0;
                if (studioCompetition.getContestData().getMultiRound()) {
                    for (SubmissionData submission : mileSubmissions) {
                        if (submission.isAwardMilestonePrize()) {
                            SubmissionPaymentData payment = new SubmissionPaymentData();
                            payment.setAmount(0);
                            payment.setAwardMilestonePrize(true);
                            payment.setRank(0);
                            payment.setId(submission.getSubmissionId());
                            paymentData.add(payment);
                            milestoneNumber++;
                            totalPayments += payment.getAmount();
                        }
                    }
                    getViewData().setMilestonePrize(studioCompetition.getContestData().getMilestonePrizeData().getAmount());
                }
                getViewData().setMilestoneAwardNumber(milestoneNumber);
            } else {
                getViewData().setMilestoneAwardNumber(0);
            }

            // Additional Purchases
            double additionalPrize = DirectUtils.getAdditionalPrize(studioCompetition);
            if (additionalPurchases.length() > 0) {
                for (int i = 0; i < additionalSubmissions.length; i++) {
                    SubmissionPaymentData payment = new SubmissionPaymentData();
                    payment.setAmount(additionalPrize);
                    payment.setAwardMilestonePrize(false);
                    payment.setRank(0);
                    payment.setId(Long.parseLong(additionalSubmissions[i]));
                    paymentData.add(payment);
                    totalPayments += payment.getAmount();
                    this.additionalPayments.add(submissions.get(payment.getId()));
                }
            }
            contestData.setSubmissions(paymentData.toArray(new SubmissionPaymentData[0]));
            getViewData().setAdditionalPrize(additionalPrize);

            List<Project> billingProjects = getProjectServiceFacade().getClientProjectsByUser(currentUser);
            Project billingAccount = null;
            for (Project project : billingProjects) {
                if (project.getId() == billingProjectId) {
                    billingAccount = project;
                    break;
                }
            }
            if (billingAccount == null) {
                throw new Exception("Can't find billing project for id:[" + billingProjectId + "].");
            }
            TCPurhcaseOrderPaymentData orderPaymentData = new TCPurhcaseOrderPaymentData();
            orderPaymentData.setClientId(billingAccount.getClient().getId());
            orderPaymentData.setClientName(billingAccount.getClient().getName());
            orderPaymentData.setProjectId(billingAccount.getId());
            orderPaymentData.setProjectName(billingAccount.getName());
            orderPaymentData.setType(PaymentType.TCPurchaseOrder);
            orderPaymentData.setPoNumber(billingAccount.getPOBoxNumber());

            // process payment
            contestServiceFacade.processSubmissionPurchaseOrderPayment(currentUser, contestData, orderPaymentData);
        } catch(Exception e) {
           ut.rollback();
        }            

        // Set contest stats
        ContestStatsDTO contestStats = DirectUtils.getContestStats(contestServiceFacade, currentUser, contestId);
        getViewData().setContestStats(contestStats);

        // Set projects data
        List<ProjectBriefDTO> projects = DataProvider.getUserProjects(currentUser.getUserId());
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        getViewData().setUserProjects(userProjectsDTO);

        // Set current project contests
        List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(currentUser.getUserId(),
                contestStats.getContest().getProject().getId());
        getSessionData().setCurrentProjectContests(contests);

        // Set current project context based on selected contest
        getSessionData().setCurrentProjectContext(contestStats.getContest().getProject());
    }
}
