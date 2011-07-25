/*
 * Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompUploadedFile;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.configs.CopilotFee;
import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionFileStore;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.admin.AdminServiceFacadeException;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.contest.SoftwareContestPaymentResult;
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

import com.topcoder.service.studio.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;


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
 * <p>
 * Version 1.3 - Direct Launch Software Contests Assembly Change Note
 * <ul>
 * <li>Adds the functions for launching software contest</li>
 * <li>Adds the functions for activating software contest</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.4 - TC Direct - Software Contest Creation Update Change Note
 * <ul>
 * <li>Adds the properties: contestCopilotId and contestCopilotName</li>
 * <li>Adds the logic to put copilot resource data into software competition to create</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.5 - Release Assembly - Direct Improvements Assembly Release 3 Change Note
 * <ul>
 * <li>Sets the direct project name of studio competition and software competition </li>
 * </ul>
 * </p>
 *
 * @author fabrizyo, FireIce, TCSDEVELOPER
 * @version 1.5
 */
public class SaveDraftContestAction extends ContestAction {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = -2592669182689444374L;

    /**
     * Date format for resource creation date.
     *
     * @since 1.4
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM.dd.yyyy hh:mm a", Locale.US);

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
     * Constant for active project status.
     * </p>
     *
     * @since Direct Launch Software Contests Assembly
     */
    private static final ProjectStatus PROJECT_STATUS_ACTIVE = new ProjectStatus(1, "ACTIVE");

    /**
     * <p>
     * Constant for root catalog id property header.
     * </p>
     */
    private static final String PROJECT_HEADER_FIRST_PLACE_COST = "First Place Cost";

    /**
     * <p>
     * Constant for resource role of manager.
     * </p>
     *
     * @since Direct Launch Software Contests Assembly
     */
    private static final long RESOURCE_ROLE_MANAGER = 13L;

    /**
     * <p>
     * Constant for key of resource info: handle.
     * </p>
     *
     * @since 1.4
     */
    private static final String RESOURCE_INFO_HANDLE = "Handle";

    /**
     * <p>
     * Constant for key of resource info : payment.
     * </p>
     * @since 1.4
     */
    private static final String RESOURCE_INFO_PAYMENT = "Payment";

    /**
     * <p>
     * Constant for key of resource info: payment status.
     * </p>
     * @since 1.4
     */
    private static final String RESOURCE_INFO_PAYMENT_STATUS = "Payment Status";

    /**
     * <p>
     * Constant for key of resource info: user id.
     * </p>
     * @since 1.4
     */
    private static final String RESOURCE_INFO_USER_ID = "External Reference ID";

    /**
     * <p>
     * Constant for key of resource info: registration date.
     * </p>
     * @since 1.4
     */
    private static final String RESOURCE_INFO_REGISTRATION_DATE = "Registration Date";

    /**
     * <p>
     * Constant value for payment status "not paid".
     * </p>
     * @since  1.4
     */
    private static final String NOT_PAID_PAYMENT_STATUS_VALUE = "No";

    /**
     * <p>
     * Constant for design project category.
     * </p>
     *
     * @since Direct Launch Software Contests Assembly
     */
    private static final long PROJECT_CATEGORY_DESIGN = 1;

    /**
     * <p>
     * Constant for dev project category.
     * </p>
     *
     * @since Direct Launch Software Contests Assembly
     */
    private static final long PROJECT_CATEGORY_DEV = 2;

    /**
     * <p>
     * The constant for concept category.
     * </p>
     */
    private static final long PROJECT_CATEGORY_CONCEPT = 23;

    /**
     * <p>
     * The constant for spec category.
     * </p>
     */
    private static final long PROJECT_CATEGORY_SPEC = 6;

    /**
     * <p>
     * Constant for billing project id key.
     * </p>
     *
     * @since Direct Launch Software Contests Assembly
     */
    private static final String PROJECT_PROPERTIES_KEY_BILLING_PROJECT_ID = "Billing Project";

    /**
     * <p>
     * The constant value to represents the id of resource is unset.
     * </p>
     *
     * @since 1.4
     */
    private static final long UNSET_RESOURCE_ID = -1;

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
     * This is the copilot user id for the software contest.
     * </p>
     * @since 1.4
     */
    private long contestCopilotId;

    /**
     * <p>
     * This is the copilot name for the software contest.
     * <p>
     *
     * @since 1.4
     */
    private String contestCopilotName;

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
     * This is project object of the software competition.
     * </p>
     *
     * @since Direct Launch Software Contests Assembly
     */
    private com.topcoder.management.project.Project projectHeader;

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
     * <code>softwareCompetition</code> to hold the software competition.
     * </p>
     */
    private SoftwareCompetition softwareCompetition;

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
     * Id list for document uploads. For software, it indicates uploadDocument ids.
     * </p>
     */
    private List<String> docUploadIds;

    /**
     * <p>
     * Id list for compDocument uploads. It is for software competition.
     * </p>
     */
    private List<String> docCompIds;

    /**
     * <p>
     * Id list for technologies.
     * </p>
     */
    private List<String> technologies;

    /**
     * <p>
     * Root category id.
     * </p>
     */
    private long rootCategoryId;

    /**
     * <p>
     * Category ids.
     * </p>
     */
    private List<String> categories;

    /**
     * <p>
     * Auto create dev flag.
     * </p>
     */
    private boolean autoCreateDev = false;

    /**
     * <p>
     * The flag to mark it is intended to be activated.
     * </p>
     */
    private boolean activationFlag;

    /**
     * <p>
     * For development software contest. It might indicate the design component which the current design component
     * will be derived from.
     * </p>
     */
    private long selectedDesignId = -1;

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
            softwareCompetition.setProjectHeaderReason("user update");
            populateSoftwareCompetition(softwareCompetition);

            // update the software competition copilot resource first
            updateSoftwareCompetitionCopilotResource();

            if (isActivation(softwareCompetition)) {
                softwareCompetition = activateSoftwareCompeition(softwareCompetition);
            } else {
                softwareCompetition = contestServiceFacade.updateSoftwareContest(tcSubject, softwareCompetition,
                    tcDirectProjectId);
            }
            setResult(getSoftwareResult(softwareCompetition));
        } else if (contestId > 0) {
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
                studioCompetition = new StudioCompetition(contestData);

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
                softwareCompetition = new SoftwareCompetition();
                softwareCompetition.setAssetDTO(getAssetDTOForNewSoftware());
                softwareCompetition.setProjectHeader(projectHeader);

                initializeSoftwareCompetition(softwareCompetition);

                populateSoftwareCompetition(softwareCompetition);

                if (isActivation(softwareCompetition)) {
                    softwareCompetition = activateSoftwareCompeition(softwareCompetition);
                } else {
                    softwareCompetition = contestServiceFacade.createSoftwareContest(tcSubject, softwareCompetition,
                        tcDirectProjectId);
                }
                setResult(getSoftwareResult(softwareCompetition));
            } else {
                // the competition type is unknown, add error field instead of exception
                // to make the action robust.
                addFieldError("competitionType", "The competition type is unknown");
            }
        }
    }

    /**
     * Updates the copilot resource during software competition update.
     */
    private void updateSoftwareCompetitionCopilotResource() {
        // get the resources before updating
        Resource[] rs = softwareCompetition.getProjectResources();

        String currentCopilotId = null;
        Resource currentCopilot = null;

        // upload copilot if needed
        if (getContestCopilotId() > 0 && getContestCopilotName() != null && getContestCopilotName().trim().length() != 0) {
            // add copilot resource to project resources
            currentCopilot = getCopilotResource();
            // set project id
            currentCopilot.setProject(projectId);
            currentCopilotId = currentCopilot.getProperty(RESOURCE_INFO_USER_ID);
        }

        Map<String, Resource> oldCopilots = new HashMap<String, Resource>();
        List<Resource> updatedResources = new ArrayList<Resource>();

        for (Resource r : rs) {
            if (r.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID) {
                // the existing copilots before update
                oldCopilots.put(r.getProperty(RESOURCE_INFO_USER_ID), r);
            } else {
                // put non-copilot into updatedResources first
                updatedResources.add(r);
            }
        }

        if (!oldCopilots.containsKey(currentCopilotId)) {
            // copilot to update is not one of the old copilots
            if (currentCopilot != null) {
                updatedResources.add(currentCopilot);
            }

            softwareCompetition.setProjectResources(updatedResources.toArray(new Resource[updatedResources.size()]));
        } else {
            // copilot to update is one of the old copilots, update payment because the payment info could be changed
            Resource r = oldCopilots.get(currentCopilotId);
            r.setProperty(RESOURCE_INFO_PAYMENT, currentCopilot.getProperty(RESOURCE_INFO_PAYMENT));
        }
    }

    /**
     * <p>
     * Gets assetDTO for new software contest.
     * </p>
     *
     * @return the assetDTO for new software contest
     * @throws Exception if any error occurs
     */
    private AssetDTO getAssetDTOForNewSoftware() throws Exception {
        if (selectedDesignId <= 0) {
            return assetDTO;
        } else {
            SoftwareCompetition designCompetition = getContestServiceFacade().getSoftwareContestByProjectId(
                DirectStrutsActionsHelper.getTCSubjectFromSession(), selectedDesignId);
            AssetDTO designAssetDTO = designCompetition.getAssetDTO();
            designAssetDTO.setProductionDate(assetDTO.getProductionDate());
            return designAssetDTO;
        }
    }

    /**
     * <p>
     * Initializes the software competition object.
     * </p>
     *
     * <p>
     * Version 1.4 change notes:
     * Add the resource info for copilot if a copilot is selected.
     * </p>
     *
     * @param softwareCompetition software competition
     * @since Direct Launch Software Contests Assembly
     */
    private void initializeSoftwareCompetition(SoftwareCompetition softwareCompetition) {
        // asset DTO
        AssetDTO assetDTOTemp = softwareCompetition.getAssetDTO();
        if (assetDTOTemp.getVersionNumber() == null) {
            assetDTOTemp.setVersionNumber(1L);
            assetDTOTemp.setVersionText("1.0");
            assetDTOTemp.setDocumentation(new ArrayList<CompDocumentation>());
            assetDTOTemp.setDependencies(new ArrayList<Long>());
            assetDTOTemp.setShortDescription("NA");
            assetDTOTemp.setTechnologies(new ArrayList<Technology>());
        }

        // project header
        com.topcoder.management.project.Project projectHeaderTemp = softwareCompetition.getProjectHeader();
        if (projectHeaderTemp.getProjectCategory().getDescription() == null) {
            projectHeaderTemp.getProjectCategory().setDescription("");
        }
        if (projectHeaderTemp.getProjectCategory().getProjectType().getDescription() == null) {
            projectHeaderTemp.getProjectCategory().getProjectType().setDescription("");
        }
        projectHeaderTemp.setProjectStatus(PROJECT_STATUS_ACTIVE);

        // process project resources

        // if has a valid copilot id and copilot name is not empty
        if (getContestCopilotId() > 0 && getContestCopilotName() != null && getContestCopilotName().trim().length() != 0) {
            // add copilot resource to project resources
            softwareCompetition.setProjectResources(new Resource[] {getUserResource(), getCopilotResource()});
        } else {
            softwareCompetition.setProjectResources(new Resource[] {getUserResource()});
        };

        // project phases
        softwareCompetition.setProjectPhases(new com.topcoder.project.phases.Project());

        // project catalogs
        assetDTOTemp.setCategories(new ArrayList<Category>());
        if (isDevOrDesign(projectHeaderTemp)) {
            assetDTOTemp.setRootCategory(getReferenceDataBean().getNotSetCatalog());
            assetDTOTemp.getCategories().add(getReferenceDataBean().getNotSetCategory());
        } else {
            assetDTOTemp.setRootCategory(getReferenceDataBean().getApplicationCatalog());
            assetDTOTemp.getCategories().add(getReferenceDataBean().getBusinessLayerApplicationCategory());
        }

        if (isDesign(projectHeaderTemp) && this.autoCreateDev) {
            softwareCompetition.setDevelopmentProjectHeader(new com.topcoder.management.project.Project());
            softwareCompetition.getDevelopmentProjectHeader().setProperties(new HashMap<String, String>());
            softwareCompetition.getDevelopmentProjectHeader().getProperties().put(PROJECT_HEADER_FIRST_PLACE_COST,
                projectHeaderTemp.getProperties().get(PROJECT_HEADER_FIRST_PLACE_COST));
        }
    }

    /**
     * <p>
     * Determine if the project is dev or design.
     * </p>
     *
     * @param projectHeader the project
     * @return true if it is dev or design
     */
    private boolean isDevOrDesign(com.topcoder.management.project.Project projectHeader) {
        long projectCategoryId = projectHeader.getProjectCategory().getId();
        return (projectCategoryId == PROJECT_CATEGORY_DESIGN || projectCategoryId == PROJECT_CATEGORY_DEV);
    }

    /**
     * <p>
     * Determine if the project is design.
     * </p>
     *
     * @param projectHeader the project
     * @return true if it is design
     */
    private boolean isDesign(com.topcoder.management.project.Project projectHeader) {
        long projectCategoryId = projectHeader.getProjectCategory().getId();
        return (projectCategoryId == PROJECT_CATEGORY_DESIGN);
    }

    /**
     * <p>
     * Determines if it needs technology.
     * </p>
     *
     * @param projectHeader the software competition project
     * @return true if it needs technology
     */
    private boolean isTechnologyContest(com.topcoder.management.project.Project projectHeader) {
        long projectCategoryId = projectHeader.getProjectCategory().getId();
        return !(PROJECT_CATEGORY_CONCEPT == projectCategoryId || PROJECT_CATEGORY_SPEC == projectCategoryId);
    }

    /**
     * <p>
     * Creates the user resource.
     * </p>
     *
     * @return the resource
     */
    private Resource getUserResource() {
        Resource resource = new Resource();
        // unset id
        resource.setId(UNSET_RESOURCE_ID);
        resource.setResourceRole(new ResourceRole(RESOURCE_ROLE_MANAGER));
        resource.setProperty(RESOURCE_INFO_HANDLE + "", DirectStrutsActionsHelper.getUserHandle());
        resource.setSubmissions(new Long[] {});
        return resource;
    }

    /**
     * <p>
     * Creates the copilot resource. The copilot resource has the user id, handle, payment and payment status populated.
     * </p>
     *
     * @return the resource copilot with user id, handle, payment and payment status populated.
     * @since 1.4
     */
    private Resource getCopilotResource() {
        Resource resource = new Resource();
        // unset id
        resource.setId(UNSET_RESOURCE_ID);
        resource.setResourceRole(new ResourceRole(ResourceRole.RESOURCE_ROLE_COPILOT_ID));
        resource.setProperty(String.valueOf(RESOURCE_INFO_HANDLE), getContestCopilotName());
        resource.setProperty(String.valueOf(RESOURCE_INFO_USER_ID), String.valueOf(getContestCopilotId()));

        // get the copilot fee from the configuration
        CopilotFee copilotFee = ConfigUtils.getCopilotFees().get(String.valueOf(getProjectHeader().getProjectCategory().getId()));

        // user zero by default in case there is no fee configured
        double feeValue = 0.0;

        if (copilotFee != null) {
            feeValue = copilotFee.getCopilotFee();
        }

        // set payment information
        resource.setProperty(String.valueOf(RESOURCE_INFO_PAYMENT), String.valueOf(feeValue));
        // set payment status to "not paid"
        resource.setProperty(String.valueOf(RESOURCE_INFO_PAYMENT_STATUS), NOT_PAID_PAYMENT_STATUS_VALUE);
        // set registration date to now
        resource.setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));

        return resource;
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
    private StudioCompetition activateStudioCompeition(StudioCompetition studioCompetition) throws Exception {
        
		// set the direct project name of the studio competition
        DirectUtils.setStudioCompetitionDirectProjectName(studioCompetition, getProjects());

		ContestPaymentResult result = getContestServiceFacade().processContestPurchaseOrderPayment(getCurrentUser(),
            studioCompetition, getPaymentData(studioCompetition));
        return new StudioCompetition(result.getContestData());
    }

    /**
     * <p>
     * Activates the software competition.
     * </p>
     *
     * @param softwareCompetition the software competition data
     * @return the software competition. it will contain project id if it is newly created
     * @throws PermissionServiceException if any permission error
     * @throws Exception if any error occurs
     */
    private SoftwareCompetition activateSoftwareCompeition(SoftwareCompetition softwareCompetition) throws Exception {
        softwareCompetition.getAssetDTO().setCompComments(getCurrentUser().getUserId() + "");

        // set the direct project name of software competition
        DirectUtils.setSoftwareCompetitionDirectProjectName(softwareCompetition, getProjects());
		
		SoftwareContestPaymentResult result = getContestServiceFacade().processContestPurchaseOrderSale(
            getCurrentUser(), softwareCompetition, getPaymentData(softwareCompetition));

        // return result.getSoftwareCompetition();
        // retrieve the software contest again, seems the contest sale is not updated
        return getContestServiceFacade().getSoftwareContestByProjectId(
            DirectStrutsActionsHelper.getTCSubjectFromSession(),
            result.getSoftwareCompetition().getProjectHeader().getId());
    }

    /**
     * <p>
     * Determines if it is activation request for studio competition.
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
     * Determines if it is activation request for software competition.
     * </p>
     *
     * @param softwareCompetition the software competition
     * @return true if it is activation request
     * @throws DirectException if no billing project is defined
     */
    private boolean isActivation(SoftwareCompetition softwareCompetition) throws DirectException {
        if (activationFlag
            && Long.parseLong(softwareCompetition.getProjectHeader().getProperties().get(
                PROJECT_PROPERTIES_KEY_BILLING_PROJECT_ID)) <= 0) {
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
        long billingProjectId = studioCompetition.getContestData().getBillingProject();
        return getPaymentData(billingProjectId);
    }

    /**
     * <p>
     * Gets the payment data for purchase/activation.
     * </p>
     *
     * @param softwareCompetition the software competition
     * @return the payment data
     * @throws Exception if any error occurs when do purchasing
     */
    private TCPurhcaseOrderPaymentData getPaymentData(SoftwareCompetition softwareCompetition) throws Exception {
        long billingProjectId = Long.parseLong(softwareCompetition.getProjectHeader().getProperties().get(
                PROJECT_PROPERTIES_KEY_BILLING_PROJECT_ID));
        return getPaymentData(billingProjectId);
    }

    /**
     * <p>
     * Gets the payment data for purchase/activation.
     * </p>
     *
     * @param billingProjectId the billing project id
     * @return the payment data
     * @throws Exception if any error occurs when do purchasing
     */
    private TCPurhcaseOrderPaymentData getPaymentData(long billingProjectId) throws Exception {
        TCPurhcaseOrderPaymentData paymentData = new TCPurhcaseOrderPaymentData();

        // retrieve all client projects with the current user
        List<Project> projects = getProjectServiceFacade().getClientProjectsByUser(
            DirectStrutsActionsHelper.getTCSubjectFromSession());
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

        if (paymentData.getProjectId() <= 0 && billingProjectId > 0) {
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
     * Creates a result map so it could be serialized by JSON serializer.
     * </p>
     *
     * @param softwareCompetition Software competition object
     * @return the result map
     */
    private Map<String, Object> getSoftwareResult(SoftwareCompetition softwareCompetition) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("projectId", softwareCompetition.getProjectHeader().getId());
        result.put("endDate", DirectUtils.getDateString(DirectUtils.getEndDate(softwareCompetition)));
        result.put("paidFee", DirectUtils.getPaidFee(softwareCompetition));
        result.put("projectStatus", softwareCompetition.getProjectHeader().getProjectStatus());
        result.put("hasSpecReview", DirectUtils.hasSpecReview(softwareCompetition));
        result.put("isSpecReviewStarted", DirectUtils.isSpecReviewStarted(softwareCompetition));
        result.put("contestId", softwareCompetition.getProjectHeader().getId());
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
            softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(DirectStrutsActionsHelper
                .getTCSubjectFromSession(), projectId);
            prizes = softwareCompetition.getPrizes();
            assetDTO = softwareCompetition.getAssetDTO();
            projectHeader = softwareCompetition.getProjectHeader();
        }

        if (contestId > 0) {
            studioCompetition = contestServiceFacade.getContest(DirectStrutsActionsHelper.getTCSubjectFromSession(),
                contestId);
            contestData = studioCompetition.getContestData();
        }

        prizes = null;

        if (null == softwareCompetition) {
            assetDTO = new AssetDTO();
            projectHeader = new com.topcoder.management.project.Project();
        }

        if (null == studioCompetition) {
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

    /**
     * <p>
     * Gets mile stone date.
     * </p>
     *
     * @return the mile stone date
     */
    public Date getMilestoneDate() {
        return milestoneDate;
    }

    /**
     * <p>
     * Sets the mile stone date.
     * </p>
     *
     * @param milestoneDate the milestone date
     */
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

    /**
     * <p>
     * Get the contest data.
     * </p>
     *
     * @return the contest data
     */
    public ContestData getContestData() {
        return contestData;
    }

    /**
     * <p>
     * Set the contest data.
     * </p>
     *
     * @param contestData the contest data
     */
    public void setContestData(ContestData contestData) {
        this.contestData = contestData;
    }

    /**
     * <p>
     * Gets the asset dto.
     * </p>
     *
     * @return asset dto
     */
    public AssetDTO getAssetDTO() {
        return assetDTO;
    }

    /**
     * <p>
     * Sets the asset dto.
     * </p>
     *
     * @param assetDTO the asset dto
     */
    public void setAssetDTO(AssetDTO assetDTO) {
        this.assetDTO = assetDTO;
    }

    /**
     * <p>
     * Gets the project header object.
     * </p>
     *
     * @return the project header object
     * @since Direct Launch Software Contests Assembly
     */
    public com.topcoder.management.project.Project getProjectHeader() {
        return projectHeader;
    }

    /**
     * <p>
     * Sets the project header object.
     * </p>
     *
     * @param projectHeader the project header object
     * @since Direct Launch Software Contests Assembly
     */
    public void setProjectHeader(com.topcoder.management.project.Project projectHeader) {
        this.projectHeader = projectHeader;
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
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <P>
     * Gets the software contest copilot id.
     * </p>
     * @return the user id of the copilot
     * @since  1.4
     */
    public long getContestCopilotId() {
        return contestCopilotId;
    }

    /**
     * <p>
     * Sets the copilot user id for the software contest.
     * </p>
     *
     * @param contestCopilotId the copilot user id.
     * @since 1.4
     */
    public void setContestCopilotId(long contestCopilotId) {
        this.contestCopilotId = contestCopilotId;
    }

    /**
     * Gets the contest copilot name.
     *
     * @return the contest copilot name.
     * @since 1.4
     */
    public String getContestCopilotName() {
        return contestCopilotName;
    }

    /**
     * Sets the contest copilot name.
     *
     * @param contestCopilotName the contest copilot name
     * @since 1.4
     */
    public void setContestCopilotName(String contestCopilotName) {
        this.contestCopilotName = contestCopilotName;
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
     * Gets compDocument ids.
     * </p>
     *
     * @return the docCompIds the ids
     */
    public List<String> getDocCompIds() {
        return docCompIds;
    }

    /**
     * <p>
     * Sets the docCompIds field.
     * </p>
     *
     * @param docCompIds the docCompIds to set
     */
    public void setDocCompIds(List<String> docCompIds) {
        this.docCompIds = docCompIds;
    }

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }

    public long getRootCategoryId() {
        return rootCategoryId;
    }

    public void setRootCategoryId(long rootCategoryId) {
        this.rootCategoryId = rootCategoryId;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
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
     * is auto create dev.
     * </p>
     *
     * @return the autoCreateDev
     */
    public boolean isAutoCreateDev() {
        return autoCreateDev;
    }

    /**
     * <p>
     * Sets the autoCreateDev flag.
     * </p>
     *
     * @param autoCreateDev the autoCreateDev to set
     */
    public void setAutoCreateDev(boolean autoCreateDev) {
        this.autoCreateDev = autoCreateDev;
    }

    /**
     * <p>
     * Gets the selectedDesignId value.
     * </p>
     *
     * @return the selectedDesignId
     */
    public long getSelectedDesignId() {
        return selectedDesignId;
    }

    /**
     * <p>
     * Sets the selectedDesignId value.
     * </p>
     *
     * @param selectedDesignId the selectedDesignId to set
     */
    public void setSelectedDesignId(long selectedDesignId) {
        this.selectedDesignId = selectedDesignId;
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
        return DirectUtils.newXMLGregorianCalendar(date);
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
        double contestFee = getProjectStudioContestFee(contestData.getBillingProject(), contestData
            .getContestTypeId());
        if (contestFee > 0) {
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
    @SuppressWarnings("all")
    private void populateSoftwareCompetition(SoftwareCompetition softwareCompetition) {
        AssetDTO assetDTO = softwareCompetition.getAssetDTO();
        if (assetDTO.getDetailedDescription() == null) {
            assetDTO.setDetailedDescription("NA");
        }

        // sync in properties
        com.topcoder.management.project.Project projectHeader = softwareCompetition.getProjectHeader();
        projectHeader.getProperties().put("Root Catalog ID", assetDTO.getRootCategory().getId() + "");

        if (isDevOrDesign(projectHeader)) {
            // refresh categories
            if (rootCategoryId > 0 && getReferenceDataBean().getCategoryMap().get(rootCategoryId) != null) {
                assetDTO.setRootCategory(getReferenceDataBean().getCategoryMap().get(rootCategoryId));
            }

            if (categories != null && categories.size() > 0) {
                List<Category> dtoCategories = new ArrayList<Category>();
                for (String categoryId : categories) {
                    dtoCategories.add(getReferenceDataBean().getCategoryMap().get(Long.parseLong(categoryId)));
                }
                assetDTO.setCategories(dtoCategories);
            }
        }

        if (isTechnologyContest(projectHeader)) {
            // refresh technologies
            if (technologies != null && technologies.size() > 0) {
                List<Technology> dtoTechs = new ArrayList<Technology>();
                for (String techId : technologies) {
                    dtoTechs.add(getReferenceDataBean().getTechnologyMap().get(Long.parseLong(techId)));
                }
                assetDTO.setTechnologies(dtoTechs);
            }
        }

        // handle upload documents
        SessionFileStore fileStore = new SessionFileStore(DirectUtils.getServletRequest().getSession(true));
        List<CompUploadedFile> files = new ArrayList<CompUploadedFile>();
        if (docUploadIds != null && docUploadIds.size() > 0) {
            for (String docUploadId : docUploadIds) {
                CompUploadedFile file = fileStore.getFile(Long.parseLong(docUploadId));
                if (file != null) {
                    files.add(file);
                }
            }
        }
        assetDTO.setCompUploadedFiles(files);

        // handle existing documents
        if (assetDTO.getDocumentation() != null) {
            assetDTO.setDocumentation((List) CollectionUtils.select(assetDTO.getDocumentation(), new Predicate() {
                public boolean evaluate(Object object) {
                    return docCompIds != null && docCompIds.contains(((CompDocumentation) object).getId());
                }
            }));
        }
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
