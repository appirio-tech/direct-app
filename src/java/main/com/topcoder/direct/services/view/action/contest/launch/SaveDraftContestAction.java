/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.admin.AdminServiceFacadeException;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.payment.PaymentType;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.pipeline.CapacityData;
import com.topcoder.service.pipeline.CompetitionType;
import com.topcoder.service.project.CompetionType;
import com.topcoder.service.project.CompetitionPrize;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestMultiRoundInformationData;
import com.topcoder.service.studio.ContestSpecificationsData;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.MilestonePrizeData;
import com.topcoder.service.studio.PrizeData;

/**
 * <p>
 * This action permits to create or update a contest. The contest could be a studio or a software competition, based
 * on competition type. The action is an update based on the the project id or contest id presence. This class action
 * contains also in the prepare method another action: it permits to write the sub entities of the software and/or
 * studio competition so two http round trips will be provided in this action.
 * </p>
 * <p>
 * Notes, to configure this action properly, we need use parameters intercepor twice. One before calling the prepare
 * interceptor, one after the prepare interceptor, as we need projectId and contestId be ready when calling prepare
 * interceptor. the config is like following:<br>
 *
 * <pre>
 * &lt;!-- Calls the params interceptor twice, allowing you to
 *        pre-load data for the second time parameters are set --&gt;
 *   &lt;action name=&quot;saveDraftContest&quot;
 *              class=&quot;com.topcoder.service.actions.SaveDraftContestAction&quot;&gt;
 *       &lt;!- this should be different from params by setting projectId and contestId only --&gt;
 *       &lt;interceptor-ref name=&quot;paramsPrepare&quot;/&gt;
 *       &lt;interceptor-ref name=&quot;prepare&quot;/&gt;
 *       &lt;interceptor-ref name=&quot;basicStack&quot;/&gt;
 *       &lt;result name=&quot;success&quot;&gt;good_result.ftl&lt;/result&gt;
 *   &lt;/action&gt;
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread
 * safety is not required (instead in Struts 1 the thread safety is required because the action instances are reused).
 * This class is mutable and stateful: it's not thread safe.
 * </p>
 * <p>
 * Change note for 1.1 Direct Launch Contest Studio Assembly 1.0: 1. Add some fields for holding parameters for
 * contest create/update 2. Adjust some of methods such as XML date conversion.
 * </p>
 * <p>
 * Version 1.2 - Direct - View/Edit/Activate Studio Contests Assembly Change Note - Adds studio contest activation
 * function
 * </p>
 *
 * @author fabrizyo, FireIce, TCSDEVELOPER
 * @version 1.2
 */
public class SaveDraftContestAction extends ContestAction {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = -2592669182689444374L;

    /**
     * <p>
     * Default prizes to be used if they are not provided.
     * </p>
     */
    private static final List<CompetitionPrize> DEFAULT_STUDIO_PRIZES = new ArrayList<CompetitionPrize>();

    static {
        CompetitionPrize prize1 = new CompetitionPrize();
        prize1.setPlace(1);
        prize1.setAmount(350);

        CompetitionPrize prize2 = new CompetitionPrize();
        prize2.setPlace(2);
        prize2.setAmount(70);

        DEFAULT_STUDIO_PRIZES.add(prize1);
        DEFAULT_STUDIO_PRIZES.add(prize2);
    }

    /**
     * <p>
     * This is the start date of the contest.
     * </p>
     */
    private Date startDate = new Date();

    /**
     * <p>
     * This is the end date of the contest.
     * </p>
     */
    private Date endDate = new Date();

    /**
     * <p>
     * Milestone date of the contest if any.
     * </p>
     */
    private Date milestoneDate = new Date();

    /**
     * <p>
     * This is the name of contest.
     * </p>
     * <p>
     * It's mapped to the similar name field (type) in related Competition instance. It must be not null, String, max
     * 255 chars, non empty. It's changed by the setter and returned by the getter.
     * </p>
     */
    private String contestName;

    /**
     * <p>
     * This is the id of project of contest.
     * </p>
     * <p>
     * It's used to update the contest: if it's present then it's an update otherwise is a create. It can be 0 (it
     * means not present) or greater than 0 if it's present. It's changed by the setter and returned by the getter.
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * This is the id of contest.
     * </p>
     * <p>
     * It's used to update the contest: if it's present then it's an update otherwise is a create. It can be 0 (it
     * means not present) or greater than 0 if it's present. It's changed by the setter and returned by the getter.
     * </p>
     */
    private long contestId;

    /**
     * <p>
     * This is the tc direct project id.
     * </p>
     * <p>
     * It's used to create or update the contest. It's changed by the setter and returned by the getter.
     * </p>
     */
    private long tcDirectProjectId;

    /**
     * <p>
     * This is the category of competition.
     * </p>
     * <p>
     * It's changed by the setter and returned by the getter. It can be null or empty.
     * </p>
     */
    private String category;

    /**
     * <p>
     * This is the review payment of competition.
     * </p>
     * <p>
     * It's changed by the setter and returned by the getter. it can be greater or equal than 0.
     * </p>
     */
    private double reviewPayment;

    /**
     * <p>
     * This is the specification review payment of competition.
     * </p>
     * <p>
     * It's changed by the setter and returned by the getter. it can be greater or equal than 0.
     * </p>
     */
    private double specificationReviewPayment;

    /**
     * <p>
     * This one indicates if the contest has wiki specification.
     * </p>
     * <p>
     * All values are possible. It's changed by the setter and returned by the getter.
     * </p>
     */
    private boolean hasWikiSpecification;

    /**
     * <p>
     * This is the notes of competition.
     * </p>
     * <p>
     * It's changed by the setter and returned by the getter. It can be null or empty.
     * </p>
     */
    private String notes;

    /**
     * <p>
     * This is the digital run points competition.
     * </p>
     * <p>
     * It's changed by the setter and returned by the getter. it can be greater or equal than 0.
     * </p>
     */
    private double drPoints;

    /**
     * <p>
     * This is the competition type used to determine the type of competition (Studio or not) to update or create.
     * </p>
     * <p>
     * If it's studio then we need also the studio type otherwise the field is mapped to the type field in the
     * Competition instance. It can't be null when the logic is performed, the possible values are all enum values.
     * It's changed by the setter and returned by the getter.
     * </p>
     */
    private CompetitionType competitionType;

    /**
     * <p>
     * these are the prizes of competition.
     * </p>
     * <p>
     * It's changed by the setter and returned by the getter. It can't be null when the prizes change.
     * </p>
     */
    private List<CompetitionPrize> prizes;

    /**
     * <p>
     * This is asset dto of the software competition.
     * </p>
     * <p>
     * It's used only by the software competition. It's changed by the setter and the prepare method and returned by
     * the getter. It can't be null when the prizes change. It will be created/retrieved in the prepare method to
     * permit to update the technologies and categories. Some setters and getters delegate to it, if it's a software
     * competition.
     * </p>
     */
    private AssetDTO assetDTO;

    /**
     * <p>
     * This is contest data of the studio software competition.
     * </p>
     * <p>
     * It's used only by the studio competition. It's changed by the setter and the prepare method and returned by the
     * getter. It can't be null when the prizes change. It contains also the MilestonePrizeData,
     * ContestMultiRoundInformationData, MediumData or ContestSpecificationsData. These instances will be created in
     * the prepare method so they can be filled directly (with the same mechanism of list of entities) in the setters.
     * </p>
     */
    private ContestData contestData;

    /**
     * <p>
     * <code>studioCompetition</code> to hold the object retrieved before parameters are populated.
     * </p>
     */
    private StudioCompetition studioCompetition;

    /**
     * <p>
     * Milestone prize amount.
     * </p>
     */
    private double milestonePrizeAmount;

    /**
     * <p>
     * Milestone prize number of submissions.
     * </p>
     */
    private int milestonePrizeNumberOfSubmissions;

    /**
     * <p>
     * Id list for document uploads.
     * </p>
     */
    private List<String> docUploadIds;

    /**
     * <p>
     * The flag to mark it is intended to be activated.
     * </p>
     */
    private boolean activationFlag;

    /**
     * <p>
     * Creates a <code>SaveDraftContestAction</code> instance.
     * </p>
     */
    public SaveDraftContestAction() {
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     * <p>
     * Save or update the contest based on the value of contest or project id.
     * </p>
     *
     * @throws IllegalStateException if the contest service facade is not set.
     * @throws Exception if any error occurs
     * @see ContestServiceFacade#getContest(TCSubject, long)
     * @see ContestServiceFacade#getSoftwareContestByProjectId(TCSubject, long)
     * @see ContestServiceFacade#updateContest(TCSubject, StudioCompetition)
     * @see ContestServiceFacade#updateSoftwareContest(TCSubject, SoftwareCompetition, long)
     * @see ContestServiceFacade#createContest(TCSubject, StudioCompetition, long)
     * @see ContestServiceFacade#createSoftwareContest(TCSubject, SoftwareCompetition, long)
     */
    protected void executeAction() throws Exception {
        ContestServiceFacade contestServiceFacade = getContestServiceFacadeWithISE();

        XMLGregorianCalendar startTime = newXMLGregorianCalendar(startDate);
        XMLGregorianCalendar endTime = newXMLGregorianCalendar(endDate);

        // get the TCSubject from session
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

        if (projectId > 0) {
            // update of the software competition
            SoftwareCompetition softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(tcSubject,
                projectId);

            // set contest name
            assetDTO.setName(contestName);

            softwareCompetition.setStartTime(startTime);
            softwareCompetition.setEndTime(endTime);
            populateSoftwareCompetition(softwareCompetition);

            setResult(contestServiceFacade.updateSoftwareContest(tcSubject, softwareCompetition, tcDirectProjectId));
        } else if (contestId > 0) {
            // update of the studio competition
            if (studioCompetition == null) {
                studioCompetition = getContestServiceFacade().getContest(tcSubject, contestId);
            }

            studioCompetition.setStartTime(startTime);
            studioCompetition.setEndTime(endTime);
            populateStudioCompetition(studioCompetition);

            if (isActivation(studioCompetition)) {
                studioCompetition = activateStudioCompeition(studioCompetition);
            } else {
                contestServiceFacade.updateContest(tcSubject, studioCompetition);
            }
            setResult(getStudioResult(studioCompetition));
        } else {
            if (competitionType == CompetitionType.STUDIO) {
                if (StringUtils.isBlank(contestData.getContestDescriptionAndRequirements())) {
                    contestData.setContestDescriptionAndRequirements("");
                }

                // creation of the studio competition
                StudioCompetition studioCompetition = new StudioCompetition(contestData);

                studioCompetition.setStartTime(startTime);
                studioCompetition.setEndTime(endTime);
                populateStudioCompetition(studioCompetition);

                if (isActivation(studioCompetition)) {
                    studioCompetition = activateStudioCompeition(studioCompetition);
                } else {
                    studioCompetition = contestServiceFacade.createContest(tcSubject, studioCompetition,
                        tcDirectProjectId);
                }

                // add preloaded documents
                if (docUploadIds != null && docUploadIds.size() > 0) {
                    for (String docUploadId : docUploadIds) {
                        contestServiceFacade.addDocumentToContest(tcSubject, Long.parseLong(docUploadId),
                            studioCompetition.getContestData().getContestId());
                    }
                }

                setResult(getStudioResult(studioCompetition));
            } else if (competitionType == CompetitionType.SOFTWARE) {
                // creation of the software competition

                SoftwareCompetition softwareCompetition = new SoftwareCompetition();

                // set contest name
                assetDTO.setName(contestName);
                softwareCompetition.setAssetDTO(assetDTO);
                populateSoftwareCompetition(softwareCompetition);
                softwareCompetition.setStartTime(startTime);
                softwareCompetition.setEndTime(endTime);

                setResult(contestServiceFacade.createSoftwareContest(tcSubject, softwareCompetition,
                    tcDirectProjectId));
            } else {
                // the competition type is unknown, add error field instead of exception
                // to make the action robust.
                addFieldError("competitionType", "The competition type is uknown");
            }
        }
    }

    /**
     * <p>
     * Activates the studio competition.
     * </p>
     *
     * @param studioCompetition the studio competition data
     * @return the studio competition. it will contain contest id if it is newly created
     * @throws PermissionServiceException if any permission error
     * @throws Exception if any error occurs
     */
    private StudioCompetition activateStudioCompeition(StudioCompetition studioCompetition)
        throws PermissionServiceException, Exception {
        ContestPaymentResult result = getContestServiceFacade().processContestPurchaseOrderPayment(getCurrentUser(),
            studioCompetition, getPaymentData(studioCompetition));
        return new StudioCompetition(result.getContestData());
    }

    /**
     * <p>
     * Determines if it is activation request.
     * </p>
     *
     * @param studioCompetition the studio competition
     * @return true if it is activation request
     * @throws DirectException if no billing project is defined
     */
    private boolean isActivation(StudioCompetition studioCompetition) throws DirectException {
        if (activationFlag && studioCompetition.getContestData().getBillingProject() <= 0) {
            throw new DirectException("no billing project is selected.");
        }

        return activationFlag;
    }

    /**
     * <p>
     * Gets the payment data for purchase/activation.
     * </p>
     *
     * @param studioCompetition the studio competition
     * @return the payment data
     * @throws Exception if any error occurs when do purchasing
     */
    private TCPurhcaseOrderPaymentData getPaymentData(StudioCompetition studioCompetition) throws Exception {
        TCPurhcaseOrderPaymentData paymentData = new TCPurhcaseOrderPaymentData();

        // retrieve all client projects with the current user
        List<Project> projects = getProjectServiceFacade().getClientProjectsByUser(
            DirectStrutsActionsHelper.getTCSubjectFromSession());
        long billingProjectId = studioCompetition.getContestData().getBillingProject();
        for (Project project : projects) {
            if (project.getId() == billingProjectId) {
                paymentData.setProjectId(project.getId());
                paymentData.setProjectName(project.getName());
                paymentData.setClientId(project.getClient().getId());
                paymentData.setClientName(project.getClient().getName());
                paymentData.setPoNumber(project.getPOBoxNumber());
                paymentData.setType(PaymentType.TCPurchaseOrder);
            }
        }


        if (paymentData.getProjectId() <= 0 && billingProjectId > 0)
        {
                paymentData.setProjectId(billingProjectId);
                paymentData.setType(PaymentType.TCPurchaseOrder);
        }

        if (paymentData.getProjectId() <= 0) {
            throw new DirectException("no project is found for billing project id : " + billingProjectId);
        }

        return paymentData;
    }

    /**
     * <p>
     * Creates a result map so it could be serialized by JSON serializer.
     * </p>
     *
     * @param studioCompetition Studio competition object
     * @return the result map
     */
    private Map<String, Object> getStudioResult(StudioCompetition studioCompetition) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("contestId", studioCompetition.getContestData().getContestId());
        result.put("contestAdministrationFee", studioCompetition.getContestData().getContestAdministrationFee());
        return result;
    }

    /**
     * <p>
     * Override the parent method to retrieve or create the prize list, the asset dto and the contest data. This
     * method will be called in a http round trip (another round trip different from the execute method) to init the
     * html client lists. This method is obviously re-called in the following round-trip, when the execute method is
     * performed but at the point it will does nothing.
     * </p>
     *
     * @throws IllegalStateException if the contest service facade is not set yet
     * @throws Exception if any problem occurs.
     */
    @Override
    public void prepare() throws Exception {
        // call super
        super.prepare();

        ContestServiceFacade contestServiceFacade = getContestServiceFacadeWithISE();
        // if both projectId
        if (projectId > 0) {
            SoftwareCompetition softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(
                DirectStrutsActionsHelper.getTCSubjectFromSession(), projectId);
            prizes = softwareCompetition.getPrizes();

            assetDTO = softwareCompetition.getAssetDTO();
        }

        if (contestId > 0) {
            studioCompetition = contestServiceFacade.getContest(DirectStrutsActionsHelper.getTCSubjectFromSession(),
                contestId);
            contestData = studioCompetition.getContestData();
        }

        prizes = null;

        if (null == assetDTO) {
            assetDTO = new AssetDTO();
        }

        if (null == contestData) {
            contestData = new ContestData();

            contestData.setMilestonePrizeData(new MilestonePrizeData());
            contestData.setMedia(new ArrayList<MediumData>());
            contestData.setMultiRoundData(new ContestMultiRoundInformationData());
            contestData.setSpecifications(new ContestSpecificationsData());
        }
    }

    /**
     * <p>
     * Gets the competition type.
     * </p>
     *
     * @return the competition type
     */
    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    /**
     * <p>
     * Set the competition type.
     * </p>
     * <p>
     * Don't perform argument checking by the usual exception. The conversion from enum value string to enum is
     * automatically done by the enum type converter of Struts 2. (for example: "STUDIO" string --> Studio enum).
     * </p>
     *
     * @param competitionType the competition type to set, can't be null considering the validation.
     */
    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }

    /**
     * <p>
     * Gets the start date.
     * </p>
     *
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <p>
     * Set the start date.
     * </p>
     * <p>
     * The default type conversion of Struts 2 correctly converts the SHORT date format described in ARS using the
     * locale, so a custom type conversion is not necessary. Don't perform argument checking by the usual exception.
     * </p>
     *
     * @param startDate the start date to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>
     * Gets the end date.
     * </p>
     *
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * Set the end date.
     * </p>
     * <p>
     * The default type conversion of Struts 2 correctly converts the SHORT date format described in ARS using the
     * locale, so a custom type conversion is not necessary. Don't perform argument checking by the usual exception.
     * </p>
     *
     * @param endDate the end date to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getMilestoneDate() {
        return milestoneDate;
    }

    public void setMilestoneDate(Date milestoneDate) {
        this.milestoneDate = milestoneDate;
    }

    /**
     * <p>
     * Gets the contest name.
     * </p>
     *
     * @return the contest name
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * <p>
     * Set the contest name.
     * </p>
     * <p>
     * Don't perform argument checking by the usual exception.
     * </p>
     *
     * @param contestName the contest name to set
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public ContestData getContestData() {
        return contestData;
    }

    public void setContestData(ContestData contestData) {
        this.contestData = contestData;
    }

    /**
     * <p>
     * Gets the project id.
     * </p>
     *
     * @return the project id
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Set the project id.
     * </p>
     * <p>
     * Don't perform argument checking by the usual exception.
     * </p>
     *
     * @param projectId the project id to set
     */
    // @FieldExpressionValidator(message = "The projectId should be positive", key =
    // "i18n.SaveDraftContestAction.projectIdRequiredSetPositive", expression = "projectId >= 0")
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the tc direct project id.
     * </p>
     *
     * @return the tc direct project id
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }

    /**
     * <p>
     * Set the tc direct project id.
     * </p>
     * <p>
     * Don't perform argument checking by the usual exception.
     * </p>
     *
     * @param tcDirectProjectId the tc direct project id to set
     */
    // @FieldExpressionValidator(message = "The tcDirectProjectId should be positive", key =
    // "i18n.SaveDraftContestAction.tcDirectProjectIdRequiredSetPositive", expression = "tcDirectProjectId >= 1")
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * <p>
     * Gets the contest id.
     * </p>
     *
     * @return the contest id
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>
     * Set the contest id.
     * </p>
     * <p>
     * Don't perform argument checking by the usual exception.
     * </p>
     *
     * @param contestId the contest id to set
     */
    // @FieldExpressionValidator(message = "The contestId should be positive", key =
    // "i18n.SaveDraftContestAction.contestIdRequiredSetPositive", expression = "contestId >= 0")
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Gets the category.
     * </p>
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * <p>
     * Set the category.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * <p>
     * Gets the review payment.
     * </p>
     *
     * @return the review payment
     */
    public double getReviewPayment() {
        return reviewPayment;
    }

    /**
     * <p>
     * Set the review payment.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param reviewPayment the review payment to set
     */
    public void setReviewPayment(double reviewPayment) {
        this.reviewPayment = reviewPayment;
    }

    /**
     * <p>
     * Gets the specification review payment.
     * </p>
     *
     * @return the specification review payment
     */
    public double getSpecificationReviewPayment() {
        return specificationReviewPayment;
    }

    /**
     * <p>
     * Set the specification review payment.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param specificationReviewPayment the specification review payment to set
     */
    public void setSpecificationReviewPayment(double specificationReviewPayment) {
        this.specificationReviewPayment = specificationReviewPayment;
    }

    /**
     * <p>
     * Gets the flag whether has wiki specification.
     * </p>
     *
     * @return the flag whether has wiki specification.
     */
    public boolean isHasWikiSpecification() {
        return hasWikiSpecification;
    }

    /**
     * <p>
     * Set the flag whether has wiki specification.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param hasWikiSpecification the flag whether has wiki specification to set
     */
    public void setHasWikiSpecification(boolean hasWikiSpecification) {
        this.hasWikiSpecification = hasWikiSpecification;
    }

    /**
     * <p>
     * Gets the notes.
     * </p>
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * <p>
     * Set the notes.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * <p>
     * Gets the digital run points for the competition.
     * </p>
     *
     * @return the digital run points for the competition.
     */
    public double getDrPoints() {
        return drPoints;
    }

    /**
     * <p>
     * Sets the digital run points for the competition.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param drPoints the digital run points for the competition.
     */
    public void setDrPoints(double drPoints) {
        this.drPoints = drPoints;
    }

    /**
     * <p>
     * Gets the prizes for the competition.
     * </p>
     *
     * @return the prizes for the competition.
     */
    public List<CompetitionPrize> getPrizes() {
        return prizes;
    }

    /**
     * <p>
     * Sets the prizes for the competition.
     * </p>
     *
     * @param prizes the prizes for the competition.
     */
    public void setPrizes(List<CompetitionPrize> prizes) {
        this.prizes = prizes;
    }

    /**
     * <p>
     * Sets the short description of the asset.
     * </p>
     * <p>
     * The acceptance region: any <code>String</code> value or <code>null</code>.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param shortDescription the short description of the asset. Should not be null or empty when updating or
     *            creating an asset.
     * @see AssetDTO#setShortDescription(String)
     */
    public void setShortDescription(String shortDescription) {
        assetDTO.setShortDescription(shortDescription);
    }

    /**
     * <p>
     * Retrieves the short description of the asset.
     * </p>
     *
     * @return the short description of the asset.
     * @see AssetDTO#getShortDescription()
     */
    public String getShortDescription() {
        return assetDTO.getShortDescription();
    }

    /**
     * <p>
     * Sets the detailed description of the asset.
     * </p>
     * <p>
     * The acceptance region: any <code>String</code> value or <code>null</code>.
     * <p>
     * Validation: no validation
     * </p>
     * </p>
     *
     * @param detailedDescription the detailed description of the asset.
     * @see AssetDTO#setDetailedDescription(String)
     */
    public void setDetailedDescription(String detailedDescription) {
        assetDTO.setDetailedDescription(detailedDescription);
    }

    /**
     * <p>
     * Retrieves the detailed description of the asset.
     * </p>
     *
     * @return the detailed description of the asset.
     * @see AssetDTO#getDetailedDescription()
     */
    public String getDetailedDescription() {
        return assetDTO.getDetailedDescription();
    }

    /**
     * <p>
     * Sets the functional description of the asset.
     * </p>
     * <p>
     * The acceptance region: any <code>String</code> value or <code>null</code>.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param functionalDescription the functional description of the asset. Should not be null or empty when updating
     *            or creating an asset.
     * @see AssetDTO#setFunctionalDescription(String)
     */
    public void setFunctionalDescription(String functionalDescription) {
        assetDTO.setFunctionalDescription(functionalDescription);

    }

    /**
     * <p>
     * Retrieves the functional description of the asset.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @return the functional description of the asset.
     * @see AssetDTO#getFunctionalDescription()
     */
    public String getFunctionalDescription() {
        return assetDTO.getFunctionalDescription();
    }

    /**
     * <p>
     * Sets the categories this asset belongs to.
     * </p>
     * <p>
     * The acceptance region: any <code>List</code> value or <code>null</code>.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param categories the categories this asset belongs to. Should not be null, empty or containing nulls when
     *            updating or creating an asset.
     * @see AssetDTO#setCategories(List)
     */
    public void setCategories(List<Category> categories) {
        assetDTO.setCategories(categories);
    }

    /**
     * <p>
     * Retrieves the categories this asset belongs to.
     * </p>
     *
     * @return the categories this asset belongs to.
     * @see AssetDTO#getCategories()
     */
    public List<Category> getCategories() {
        return assetDTO.getCategories();
    }

    /**
     * <p>
     * Sets the technologies of this version of the asset.
     * </p>
     * <p>
     * The acceptance region: any <code>List</code> value or <code>null</code>.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param technologies the technologies of this version of the asset. Should not be null, empty or containing
     *            nulls when updating or creating an asset.
     * @see AssetDTO#setTechnologies(List)
     */
    public void setTechnologies(List<Technology> technologies) {
        assetDTO.setTechnologies(technologies);
    }

    /**
     * <p>
     * Retrieves the technologies of this version of the asset.
     * </p>
     *
     * @return the technologies of this version of the asset.
     * @see AssetDTO#getTechnologies()
     */
    public List<Technology> getTechnologies() {
        return assetDTO.getTechnologies();
    }

    /**
     * <p>
     * Gets prize description.
     * </p>
     *
     * @return the prize description
     * @see ContestData#getPrizeDescription()
     */
    public String getPrizeDescription() {
        return contestData.getPrizeDescription();
    }

    /**
     * <p>
     * Set prize description.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param prizeDescription the prize description to set
     * @see ContestData#setPrizeDescription(String)
     */
    public void setPrizeDescription(String prizeDescription) {
        contestData.setPrizeDescription(prizeDescription);
    }

    /**
     * <p>
     * Gets the maximum submissions.
     * </p>
     *
     * @return the maximum submissions.
     * @see ContestData#getMaximumSubmissions()
     */
    public long getMaximumSubmissions() {
        return contestData.getMaximumSubmissions();
    }

    /**
     * <p>
     * Set the maximum submissions.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param maximumSubmissions the maximum submissions to set
     * @see ContestData#setMaximumSubmissions(long)
     */
    public void setMaximumSubmissions(long maximumSubmissions) {
        contestData.setMaximumSubmissions(maximumSubmissions);
    }

    /**
     * <p>
     * Gets the prizes.
     * </p>
     *
     * @return the prizes.
     * @see ContestData#getPrizes()
     */
    public List<PrizeData> getPrizesData() {
        return contestData.getPrizes();
    }

    /**
     * <p>
     * Set the prizes.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param prizes the prizes to set.
     * @throws IllegalArgumentException if <code>prizes</code> is null.
     */
    public void setPrizesData(List<PrizeData> prizes) {
        DirectStrutsActionsHelper.checkNull(prizes, "prizes");

        contestData.setPrizes(prizes);
    }

    /**
     * <p>
     * Gets the media.
     * </p>
     *
     * @return the media.
     * @see ContestData#getMedia()
     */
    public List<MediumData> getMedia() {
        return contestData.getMedia();
    }

    /**
     * <p>
     * Set the media.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param mediums the media to set.
     * @see ContestData#setMedia(List)
     */
    public void setMedia(List<MediumData> mediums) {
        contestData.setMedia(mediums);
    }

    /**
     * <p>
     * Gets the contest description and requirements.
     * </p>
     *
     * @return the contest description and requirements.
     * @see ContestData#getContestDescriptionAndRequirements()
     */
    public String getContestDescriptionAndRequirements() {
        return contestData.getContestDescriptionAndRequirements();
    }

    /**
     * <p>
     * Set the contest description and requirements.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param contestDescriptionAndRequirements the contest description and requirements to set.
     * @see ContestData#setContestDescriptionAndRequirements(String)
     */
    public void setContestDescriptionAndRequirements(String contestDescriptionAndRequirements) {
        contestData.setContestDescriptionAndRequirements(contestDescriptionAndRequirements);
    }

    /**
     * <p>
     * Gets the required or restricted colors.
     * </p>
     *
     * @return the required or restricted colors.
     * @see ContestData#getRequiredOrRestrictedColors()
     */
    public String getRequiredOrRestrictedColors() {
        return contestData.getRequiredOrRestrictedColors();
    }

    /**
     * <p>
     * Set the required or restricted colors.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param requiredOrRestrictedColors the required or restricted colors to set.
     * @see ContestData#setRequiredOrRestrictedColors(String)
     */
    public void setRequiredOrRestrictedColors(String requiredOrRestrictedColors) {
        contestData.setRequiredOrRestrictedColors(requiredOrRestrictedColors);
    }

    /**
     * <p>
     * Gets the required or restricted fonts.
     * </p>
     *
     * @return the required or restricted fonts
     * @see ContestData#getRequiredOrRestrictedFonts()
     */
    public String getRequiredOrRestrictedFonts() {
        return contestData.getRequiredOrRestrictedFonts();
    }

    /**
     * <p>
     * Set the required or restricted fonts.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param requiredOrRestrictedFonts the required or restricted fonts to set.
     * @see ContestData#setRequiredOrRestrictedFonts(String)
     */
    public void setRequiredOrRestrictedFonts(String requiredOrRestrictedFonts) {
        contestData.setRequiredOrRestrictedFonts(requiredOrRestrictedFonts);
    }

    /**
     * <p>
     * Gets the size requirements.
     * </p>
     *
     * @return the size requirements.
     * @see ContestData#getSizeRequirements()
     */
    public String getSizeRequirements() {
        return contestData.getSizeRequirements();
    }

    /**
     * <p>
     * Set the size requirements.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param sizeRequirements the size requirements to set.
     * @see ContestData#setSizeRequirements(String)
     */
    public void setSizeRequirements(String sizeRequirements) {
        contestData.setSizeRequirements(sizeRequirements);
    }

    /**
     * <p>
     * Gets the other requirements or restrictions.
     * </p>
     *
     * @return the other requirements or restrictions.
     * @see ContestData#getOtherRequirementsOrRestrictions()
     */
    public String getOtherRequirementsOrRestrictions() {
        return contestData.getOtherRequirementsOrRestrictions();
    }

    /**
     * <p>
     * Set the other requirements or restrictions.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param otherRequirementsOrRestrictions the other requirements or restrictions to set.
     * @see ContestData#setOtherRequirementsOrRestrictions(String)
     */
    public void setOtherRequirementsOrRestrictions(String otherRequirementsOrRestrictions) {
        contestData.setOtherRequirementsOrRestrictions(otherRequirementsOrRestrictions);
    }

    /**
     * <p>
     * Gets the final file format.
     * </p>
     *
     * @return the final file format.
     * @see ContestData#getFinalFileFormat()
     */
    public String getFinalFileFormat() {
        return contestData.getFinalFileFormat();
    }

    /**
     * <p>
     * Set the final file format.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param finalFileFormat the final file format to set.
     * @see ContestData#setFinalFileFormat(String)
     */
    public void setFinalFileFormat(String finalFileFormat) {
        contestData.setFinalFileFormat(finalFileFormat);
    }

    /**
     * <p>
     * Gets the other file formats.
     * </p>
     *
     * @return the other file formats.
     * @see ContestData#getOtherFileFormats()
     */
    public String getOtherFileFormats() {
        return contestData.getOtherFileFormats();
    }

    /**
     * <p>
     * Set the other file formats.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param otherFileFormats the other file formats to set.
     * @see ContestData#setOtherFileFormats(String)
     */
    public void setOtherFileFormats(String otherFileFormats) {
        contestData.setOtherFileFormats(otherFileFormats);
    }

    /**
     * <p>
     * Sets the Launch immediately flag.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param launchImmediately the launch immediately flag to set.
     * @see ContestData#setLaunchImmediately(boolean)
     */
    public void setLaunchImmediately(boolean launchImmediately) {
        contestData.setLaunchImmediately(launchImmediately);
    }

    /**
     * <p>
     * Gets the launch immediately flag.
     * </p>
     *
     * @return the launch immediately flag.
     * @see ContestData#isLaunchImmediately()
     */
    public boolean isLaunchImmediately() {
        return contestData.isLaunchImmediately();
    }

    /**
     * <p>
     * Gets the multi-round flag.
     * </p>
     *
     * @return true if the contest is a multi-round format, false otherwise.
     * @see ContestData#isMultiRound()
     */
    public boolean isMultiRound() {
        return contestData.isMultiRound();
    }

    /**
     * <p>
     * Sets the value of the multi-round flag.
     * </p>
     *
     * @param multiRound the new value for the multi-round flag
     * @see ContestData#setMultiRound(boolean)
     */
    public void setMultiRound(boolean multiRound) {
        contestData.setMultiRound(multiRound);
    }

    /**
     * <p>
     * Gets the contest specifications data.
     * </p>
     *
     * @return the contest specifications data.
     * @see ContestData#getSpecifications()
     */
    public ContestSpecificationsData getSpecifications() {
        return contestData.getSpecifications();
    }

    /**
     * <p>
     * Sets the contest specifications data.
     * </p>
     *
     * @param specifications the new value for the contest specifications data.
     * @see ContestData#setSpecifications(ContestSpecificationsData)
     */
    public void setSpecifications(ContestSpecificationsData specifications) {
        contestData.setSpecifications(specifications);
    }

    /**
     * <p>
     * Gets the contest multi-round data.
     * </p>
     *
     * @return the contest multi-round data.
     * @see ContestData#getMultiRoundData()
     */
    public ContestMultiRoundInformationData getMultiRoundData() {
        return contestData.getMultiRoundData();
    }

    /**
     * <p>
     * Sets the contest multi-round data.
     * </p>
     * <p>
     * Validation: no validation
     * </p>
     *
     * @param multiRoundData the contest multi-round data.
     * @see ContestData#setMultiRoundData(ContestMultiRoundInformationData)
     */
    public void setMultiRoundData(ContestMultiRoundInformationData multiRoundData) {
        contestData.setMultiRoundData(multiRoundData);
    }

    /**
     * <p>
     * Gets the milestone prize data.
     * </p>
     *
     * @return the milestone prize data.
     * @see ContestData#getMilestonePrizeData()
     */
    public MilestonePrizeData getMilestonePrizeData() {
        return contestData.getMilestonePrizeData();
    }

    /**
     * <p>
     * Sets the milestone prize data.
     * </p>
     *
     * @param milestonePrizeData the milestone prize data.
     * @see ContestData#setMilestonePrizeData(MilestonePrizeData)
     */
    public void setMilestonePrizeData(MilestonePrizeData milestonePrizeData) {
        contestData.setMilestonePrizeData(milestonePrizeData);
    }

    /**
     * <p>
     * Gets the milestone prize amount.
     * </p>
     *
     * @return the mile stone prize amount
     */
    public double getMilestonePrizeAmount() {
        return milestonePrizeAmount;
    }

    /**
     * <p>
     * Sets the milestone prize amount.
     * </p>
     *
     * @param milestonePrizeAmount the prize amount
     */
    public void setMilestonePrizeAmount(double milestonePrizeAmount) {
        this.milestonePrizeAmount = milestonePrizeAmount;
    }

    /**
     * <p>
     * Gets milestone prize number of submissions.
     * </p>
     *
     * @return the number of submissions for milestone prize
     */
    public int getMilestonePrizeNumberOfSubmissions() {
        return milestonePrizeNumberOfSubmissions;
    }

    /**
     * <p>
     * Sets the milestone prize number of submissions.
     * </p>
     *
     * @param milestonePrizeNumberOfSubmissions the number of submissions for given
     */
    public void setMilestonePrizeNumberOfSubmissions(int milestonePrizeNumberOfSubmissions) {
        this.milestonePrizeNumberOfSubmissions = milestonePrizeNumberOfSubmissions;
    }

    /**
     * <p>
     * Gets the doc upload ids.
     * </p>
     *
     * @return the list of upload ids for docs
     */
    public List<String> getDocUploadIds() {
        return docUploadIds;
    }

    /**
     * <p>
     * Sets the doc upload ids.
     * </p>
     *
     * @param docUploadIds upload ids for documents
     */
    public void setDocUploadIds(List<String> docUploadIds) {
        this.docUploadIds = docUploadIds;
    }

    /**
     * <p>
     * Returns the activation flag.
     * </p>
     *
     * @return the activation flag
     */
    public boolean isActivationFlag() {
        return activationFlag;
    }

    /**
     * <p>
     * Sets the activation flag.
     * </p>
     *
     * @param activationFlag the activation flag
     */
    public void setActivationFlag(boolean activationFlag) {
        this.activationFlag = activationFlag;
    }

    /**
     * <p>
     * Creates the <code>XMLGregorianCalendar</code> from the given date.
     * </p>
     *
     * @param date the date
     * @return the created XMLGregorianCalendar instance
     * @throws DatatypeConfigurationException if fail to create the XMLGregorianCalendar instance
     */
    private static XMLGregorianCalendar newXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
        if (date == null) {
            date = new Date();
        }
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        return datatypeFactory.newXMLGregorianCalendar(gc);
    }

    /**
     * <p>
     * Creates the <code>XMLGregorianCalendar</code> from the given date, hour and minute.
     * </p>
     *
     * @param date the date
     * @param hour the hour
     * @param minute the minute
     * @return the created XMLGregorianCalendar instance
     * @throws DatatypeConfigurationException if fail to create the XMLGregorianCalendar instance
     */
    private static XMLGregorianCalendar newXMLGregorianCalendar(Date date, int hour, int minute)
        throws DatatypeConfigurationException {
        if (date == null) {
            date = new Date();
        }
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.HOUR, hour);
        gc.set(Calendar.MINUTE, minute);

        return datatypeFactory.newXMLGregorianCalendar(gc);
    }

    /**
     * <p>
     * Populates the values of StudioCompetition object with parameters of this action.
     * </p>
     *
     * @param studioCompetition the StudioCompetition object
     * @throws Exception if any error occurs
     */
    private void populateStudioCompetition(StudioCompetition studioCompetition) throws Exception {
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

        // validate to make sure the start time is not full
        boolean startTimeOk = true;
        // validate startTime for capacity
        List<CapacityData> capacityDataList = getPipelineServiceFacade().getCapacityFullDates(tcSubject,
            (int) studioCompetition.getContestData().getContestTypeId(), true);
        capacityDataList = (capacityDataList == null) ? new ArrayList<CapacityData>() : capacityDataList;
        for (CapacityData capacity : capacityDataList) {
            if (isSameDay(studioCompetition.getStartTime(), capacity.getDate())) {
                // capacity contests is an int list
                if (!capacity.getContests().contains((int) contestId)) {
                    startTimeOk = false;
                }
            }
        }
        if (!startTimeOk) {
            throw new Exception("You must change your start date since it's full.");
        }

        studioCompetition.setCategory(category);
        studioCompetition.setReviewPayment(reviewPayment);
        studioCompetition.setSpecificationReviewPayment(specificationReviewPayment);
        studioCompetition.setHasWikiSpecification(hasWikiSpecification);
        studioCompetition.setNotes(notes);
        // studioCompetition.setDrPoints(drPoints);

        // can not set directly through contestData, it always return copy
        if (prizes != null && prizes.size() >= 2) {
            studioCompetition.setPrizes(prizes);
        } else {
            studioCompetition.setPrizes(DEFAULT_STUDIO_PRIZES);
        }
        studioCompetition.setType(CompetionType.STUDIO);


        // milestone date
        ContestData contestData = studioCompetition.getContestData();

        // adjust administration fee
        double contestFee = getProjectStudioContestFee(contestData.getBillingProject(),contestData.getContestTypeId());
        if(contestFee > 0 ) {
            contestData.setContestAdministrationFee(contestFee);
        }

        // make sure the short summary is not null
        if (StringUtils.isBlank(studioCompetition.getShortSummary())) {
            studioCompetition.setShortSummary("");
        }

        if (contestData.getMultiRound()) {
            contestData.getMultiRoundData().setMilestoneDate(newXMLGregorianCalendar(milestoneDate));

            MilestonePrizeData milestonePrizeData = new MilestonePrizeData();
            milestonePrizeData.setAmount(milestonePrizeAmount);
            milestonePrizeData.setNumberOfSubmissions(milestonePrizeNumberOfSubmissions);
            contestData.setMilestonePrizeData(milestonePrizeData);
        } else {
            contestData.setMilestonePrizeData(null);
        }

        if (contestData.getFinalFileFormat() != null) {
            contestData.setFinalFileFormat(contestData.getFinalFileFormat().toLowerCase());
        }

        // set dr point in the end. it is a derivative number before all other prizes/amount are decided.
        studioCompetition.setDrPoints(getDrPoint(studioCompetition));

        // set channel id to 2
        contestData.setContestChannelId(2);
        contestData.setMaximumSubmissions(5);
        contestData.setRequiresPreviewImage(true);
        contestData.setRequiresPreviewFile(false);
    }

    /**
     * <p>
     * Calculates the dr point and set it up.
     * </p>
     *
     * @param studioCompetition studio competition
     * @return the DR point
     */
    private double getDrPoint(StudioCompetition studioCompetition) {
        // prizes not set yet
        if (studioCompetition.getPrizes().size() == 0) {
            return 0;
        }

        double sum = 0;

        ContestData contestData = studioCompetition.getContestData();

        // add all prizes up
        for (PrizeData prizeData : contestData.getPrizes()) {
            sum += prizeData.getAmount();
        }

        if (contestData.getMultiRound()) {
            sum += contestData.getMilestonePrizeData().getAmount()
                * contestData.getMilestonePrizeData().getNumberOfSubmissions();
        }

        return sum * 0.25;
    }

    /**
     * <p>
     * Determine if it is the same day.
     * </p>
     *
     * @param day1 the day 1
     * @param day2 the day 2
     * @return true if the same day
     */
    private boolean isSameDay(XMLGregorianCalendar day1, XMLGregorianCalendar day2) {
        return (day1.getYear() == day2.getYear()) && (day1.getMonth() == day2.getMonth())
            && (day1.getDay() == day2.getDay());
    }

    /**
     * <p>
     * Gets project studio contest fee.
     * </p>
     *
     * @param billingProjectId the billing project id
     * @param studioSubTypeId studio sub type id
     * @return contest fee &gt;0 if it exists
     * @throws AdminServiceFacadeException if any error occurs
     */
    private double getProjectStudioContestFee(long billingProjectId, long studioSubTypeId)
        throws AdminServiceFacadeException {
        if (billingProjectId <= 0) {
            return -1;
        }

        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

        List<ProjectContestFee> contestFees = getAdminServiceFacade().getContestFeesByProject(tcSubject,
            billingProjectId);
        for (ProjectContestFee contestFee : contestFees) {
            if (contestFee.isStudio() && contestFee.getContestTypeId() == studioSubTypeId) {
                return contestFee.getContestFee();
            }
        }

        return -1;
    }

    /**
     * <p>
     * Populates the values of SoftwareCompetition object with parameters of this action.
     * </p>
     *
     * @param softwareCompetition the SoftwareCompetition object
     */
    private void populateSoftwareCompetition(SoftwareCompetition softwareCompetition) {
        softwareCompetition.setReviewPayment(reviewPayment);
        softwareCompetition.setSpecificationReviewPayment(specificationReviewPayment);
        softwareCompetition.setHasWikiSpecification(hasWikiSpecification);
        softwareCompetition.setNotes(notes);
        softwareCompetition.setDrPoints(drPoints);
    }

    /**
     * <p>
     * Gets the contest service facade, throw <code>IllegalStateException</code> if it is null.
     * </p>
     *
     * @return the contest service facade
     * @throws IllegalStateException if the contest service facade is null
     */
    private ContestServiceFacade getContestServiceFacadeWithISE() {
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        if (null == contestServiceFacade) {
            throw new IllegalStateException("The contest service facade is not set.");
        }
        return contestServiceFacade;
    }
}
