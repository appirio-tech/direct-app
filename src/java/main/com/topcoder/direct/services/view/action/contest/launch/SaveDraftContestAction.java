/*
 * Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompUploadedFile;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.configs.CopilotFee;
import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionFileStore;
import com.topcoder.management.project.FileType;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectStudioSpecification;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.contest.SoftwareContestPaymentResult;
import com.topcoder.service.payment.PaymentType;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.pipeline.CompetitionType;
import com.topcoder.service.project.SoftwareCompetition;


/**
 * <p>
 * This action permits to create or update a contest. The contest could be a studio or a software competition, based on
 * competition type. The action is an update based on the the project id or contest id presence. This class action
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
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread safety
 * is not required (instead in Struts 1 the thread safety is required because the action instances are reused). This
 * class is mutable and stateful: it's not thread safe.
 * </p>
 * <p>
 * Change note for 1.1 Direct Launch Contest Studio Assembly 1.0: 1. Add some fields for holding parameters for contest
 * create/update 2. Adjust some of methods such as XML date conversion.
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
 * Version 1.4 - TC Direct Replatforming Release 1: Replatforming this class to create Studio contest
 * the same as the software contest. And add milestone support for both studio contest and software contest.
 * </p>
 * 
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
 * <p>
 * Version 1.6 - TC Cockpit Post a Copilot Assembly 1 Change Note
 * <ul>
 * <li>Sets the forum url.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.1 - Release Assembly - TopCoder Cockpit Start A New Project Revamp R2 Change Note
 * <ul>
 * <li>
 *     Change methods populateCompetition, getAssetDTOForNewSoftware, initializeCompetition so that
 *     they can be used by subclass (action) which extends SaveDraftContestAction.
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.2 (Release Assembly - TC Cockpit Contest Edit and Upload Update Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #getSoftwareResult(SoftwareCompetition)} method to include details on contest documents.</li>
 *   </ol>
 * </p>
 *
 * @author fabrizyo, FireIce, Veve, isv
 * @version 1.6.2
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
     * </p>
     * 
     * @since TC Direct Replatforming Release 1
     */
    private boolean hasMulti = false;

    /**
     * <p>
     * Milestone date of the contest if any.
     * </p>
     */
    private Date milestoneDate = new Date();

    /**
     * <p>
     * End date of the contest. Only available for the studio contest.
     * </p>
     */
    private XMLGregorianCalendar endDate = null;

    /**
     * <p>
     * This is the id of project of contest.
     * </p>
     * <p>
     * It's used to update the contest: if it's present then it's an update otherwise is a create. It can be 0 (it means
     * not present) or greater than 0 if it's present. It's changed by the setter and returned by the getter.
     * </p>
     */
    private long projectId;

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
     * This is the competition type used to determine the type of competition (Studio or not) to update or create.
     * </p>
     * <p>
     * If it's studio then we need also the studio type otherwise the field is mapped to the type field in the
     * Competition instance. It can't be null when the logic is performed, the possible values are all enum values. It's
     * changed by the setter and returned by the getter.
     * </p>
     */
    private CompetitionType competitionType;

    /**
     * <p>
     * This is asset dto of the software competition.
     * </p>
     * <p>
     * It's used only by the software competition. It's changed by the setter and the prepare method and returned by the
     * getter. It can't be null when the prizes change. It will be created/retrieved in the prepare method to permit to
     * update the technologies and categories. Some setters and getters delegate to it, if it's a software competition.
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
     * <code>softwareCompetition</code> to hold the software competition.
     * </p>
     */
    private SoftwareCompetition softwareCompetition;

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
     * For development software contest. It might indicate the design component which the current design component will
     * be derived from.
     * </p>
     */
    private long selectedDesignId = -1;

    /**
     * <p>
     * For studio contest. It contains the file types.
     * </p>
     * 
     * @since TC Direct Replatforming Release 1
     */
    private List<String> fileTypes;

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
     * @throws IllegalStateException
     *             if the contest service facade is not set.
     * @throws Exception
     *             if any error occurs
     * @see ContestServiceFacade#getContest(TCSubject, long)
     * @see ContestServiceFacade#getSoftwareContestByProjectId(TCSubject, long)
     * @see ContestServiceFacade#updateContest(TCSubject, StudioCompetition)
     * @see ContestServiceFacade#updateSoftwareContest(TCSubject, SoftwareCompetition, long)
     * @see ContestServiceFacade#createContest(TCSubject, StudioCompetition, long)
     * @see ContestServiceFacade#createSoftwareContest(TCSubject, SoftwareCompetition, long)
     */
    protected void executeAction() throws Exception {
        ContestServiceFacade contestServiceFacade = getContestServiceFacadeWithISE();

        // get the TCSubject from session
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

        if (!hasMulti) {
            milestoneDate = null;
        }
        if (projectId > 0) {
            softwareCompetition.setProjectHeaderReason("user update");
            populateCompetition(softwareCompetition);

            // update the software competition copilot resource first
            updateSoftwareCompetitionCopilotResource();

            if (isActivation(softwareCompetition)) {
                softwareCompetition = activateSoftwareCompeition(softwareCompetition);
            } else {
                softwareCompetition = contestServiceFacade.updateSoftwareContest(tcSubject, softwareCompetition,
                        tcDirectProjectId, milestoneDate, endDate == null ? null : endDate.toGregorianCalendar().getTime());
            }
            setResult(getSoftwareResult(softwareCompetition));
        } else {
            // creation of the software competition
            softwareCompetition = new SoftwareCompetition();
            softwareCompetition.setAssetDTO(getAssetDTOForNewSoftware());
            softwareCompetition.setProjectHeader(projectHeader);
            initializeCompetition(softwareCompetition);
            populateCompetition(softwareCompetition);

            if (competitionType == CompetitionType.STUDIO || competitionType == CompetitionType.SOFTWARE) {
                if (isActivation(softwareCompetition)) {
                    softwareCompetition = activateSoftwareCompeition(softwareCompetition);
                } else {
                    softwareCompetition = contestServiceFacade.createSoftwareContest(tcSubject, softwareCompetition,
                            tcDirectProjectId, milestoneDate, endDate == null ? null : endDate.toGregorianCalendar().getTime());
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
     * Populates the values of SoftwareCompetition object with parameters of this action.
     * </p>
     * 
     * @param softwareCompetition the SoftwareCompetition object
     * @throws ContestServiceException if any error occurs
     * @since TC Direct Replatforming Release 1
     */
    @SuppressWarnings("all")
    protected void populateCompetition(SoftwareCompetition softwareCompetition) throws ContestServiceException {
        AssetDTO assetDTO = softwareCompetition.getAssetDTO();
        if (assetDTO.getDetailedDescription() == null) {
            assetDTO.setDetailedDescription("NA");
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

        // Set prizes
        List<Prize> prizes = projectHeader.getPrizes();
        List<Prize> newPrizes = new ArrayList<Prize>();
        if (prizes != null) {
            for (Prize prize : prizes) {
                if (prize.getPrizeAmount() > 0) {
                    newPrizes.add(prize);
                }
            }
            softwareCompetition.getProjectHeader().setPrizes(newPrizes);
        }
        
        if (DirectUtils.isStudio(softwareCompetition)) {
            populateStudioCompetition(softwareCompetition);
        } else {
            populateSoftwareCompetition(softwareCompetition);
        }
    }

    /**
     * <p>Convert a list string of file types to a list of FileType objects.</p>
     * 
     * @param fileTypes the List of String represents the names of the file types
     * @return the List of FileType objects
     * @throws ContestServiceException if any error occurs
     * @since TC Direct Replatforming Release 1
     */
    private List<FileType> getFileTypes(List<String> fileTypes) throws ContestServiceException {
        List<FileType> newFileTypes = new ArrayList<FileType>();
        FileType[] allFileTypes = getContestServiceFacadeWithISE().getAllFileTypes();
        if (fileTypes != null) {
            Map<Long, FileType> idToFileType = new HashMap<Long, FileType>();
            for (FileType type : allFileTypes) {
                idToFileType.put(type.getId(), type);
            }
            for (String fileType : fileTypes) {
                try {
                    long id = Long.parseLong(fileType);
                    if (idToFileType.containsKey(id)) {
                        newFileTypes.add(idToFileType.get(id));
                } else {
                }
                } catch (NumberFormatException e) {
                    FileType type = new FileType();
                    type.setExtension("-");
                    type.setDescription(fileType);
                    newFileTypes.add(type);
                }
            }
        }
        return newFileTypes;
    }

    /**
     * <p>
     * Gets assetDTO for new software contest.
     * </p>
     * 
     * @return the assetDTO for new software contest
     * @throws Exception
     *             if any error occurs
     */
    protected AssetDTO getAssetDTOForNewSoftware() throws Exception {
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
     * @param softwareCompetition the SoftwareCompetition object
     * @since TC Direct Replatforming Release 1
     *
     * <p>
     * Version 1.4 change notes:
     * Add the resource info for copilot if a copilot is selected.
     * </p>
     *
     * @param softwareCompetition software competition
     * @since Direct Launch Software Contests Assembly
     */
    protected void initializeCompetition(SoftwareCompetition softwareCompetition) {
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

        // project catalogs
        assetDTOTemp.setCategories(new ArrayList<Category>());
        
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

        if (!DirectUtils.isStudio(softwareCompetition)) {
            initializeSoftwareCompetition(softwareCompetition);
        } else {
            initializeStudioCompetition(softwareCompetition);
        }
    }

    /**
     * <p>
     * Initializes the software competition object for studio contest.
     * </p>
     * 
     * @param studioCompetition the SoftwareCompetition object
     * @since TC Direct Replatforming Release 1
     */
    private void initializeStudioCompetition(SoftwareCompetition studioCompetition) {
        // asset DTO
        AssetDTO assetDTOTemp = softwareCompetition.getAssetDTO();
        assetDTOTemp.setRootCategory(getReferenceDataBean().getNotSetCatalog());
        assetDTOTemp.getCategories().add(getReferenceDataBean().getNotSetCategory());
        
        if (softwareCompetition.getProjectHeader().getProjectStudioSpecification() == null) {
            softwareCompetition.getProjectHeader().setProjectStudioSpecification(new ProjectStudioSpecification());
        }
        ProjectStudioSpecification projectStudioSpecification = softwareCompetition.getProjectHeader().getProjectStudioSpecification();
        if (projectStudioSpecification.getBrandingGuidelines() == null) {
            projectStudioSpecification.setBrandingGuidelines("");
        }
        if (projectStudioSpecification.getColors() == null) {
            projectStudioSpecification.setColors("");
        }
        if (projectStudioSpecification.getDislikedDesignWebSites() == null) {
            projectStudioSpecification.setDislikedDesignWebSites("");
        }
        if (projectStudioSpecification.getFonts() == null) {
            projectStudioSpecification.setFonts("");
        }
        if (projectStudioSpecification.getGoals() == null) {
            projectStudioSpecification.setGoals("");
        }
        if (projectStudioSpecification.getLayoutAndSize() == null) {
            projectStudioSpecification.setLayoutAndSize("");
        }
        if (projectStudioSpecification.getOtherInstructions() == null) {
            projectStudioSpecification.setOtherInstructions("");
        }
        if (projectStudioSpecification.getRoundOneIntroduction() == null) {
            projectStudioSpecification.setRoundOneIntroduction("");
        }
        if (projectStudioSpecification.getRoundTwoIntroduction() == null) {
            projectStudioSpecification.setRoundTwoIntroduction("");
        }
        if (projectStudioSpecification.getTargetAudience() == null) {
            projectStudioSpecification.setTargetAudience("");
        }
        if (projectStudioSpecification.getWinningCriteria() == null) {
            projectStudioSpecification.setWinningCriteria("");
        }
        
        if (softwareCompetition.getProjectHeader().getPrizes() == null) {
            softwareCompetition.getProjectHeader().setPrizes(new ArrayList<Prize>());
        }
    }
    
    /**
     * <p>
     * Initializes the software competition object for software contest.
     * </p>
     * 
     * @param softwareCompetition
     *            software competition
     * @since Direct Launch Software Contests Assembly
     */
    private void initializeSoftwareCompetition(SoftwareCompetition softwareCompetition) {
        // asset DTO
        AssetDTO assetDTOTemp = softwareCompetition.getAssetDTO();
        
        // project header
        com.topcoder.management.project.Project projectHeaderTemp = softwareCompetition.getProjectHeader();

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
            softwareCompetition
                    .getDevelopmentProjectHeader()
                    .getProperties()
                    .put(PROJECT_HEADER_FIRST_PLACE_COST,
                            projectHeaderTemp.getProperties().get(PROJECT_HEADER_FIRST_PLACE_COST));
        }
    }

    /**
     * <p>
     * Determine if the project is dev or design.
     * </p>
     * 
     * @param projectHeader
     *            the project
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
     * @param projectHeader
     *            the project
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
     * @param projectHeader
     *            the software competition project
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
     * Activates the software competition.
     * </p>
     * 
     * @param softwareCompetition
     *            the software competition data
     * @return the software competition. it will contain project id if it is newly created
     * @throws PermissionServiceException
     *             if any permission error
     * @throws Exception
     *             if any error occurs
     */
    private SoftwareCompetition activateSoftwareCompeition(SoftwareCompetition softwareCompetition) throws Exception {
        softwareCompetition.getAssetDTO().setCompComments(getCurrentUser().getUserId() + "");

        // set the direct project name of software competition
        DirectUtils.setSoftwareCompetitionDirectProjectName(softwareCompetition, getProjects());
		
		SoftwareContestPaymentResult result = getContestServiceFacade().processContestPurchaseOrderSale(
                getCurrentUser(), softwareCompetition, getPaymentData(softwareCompetition), milestoneDate, endDate == null ? null : endDate.toGregorianCalendar().getTime());

        // return result.getSoftwareCompetition();
        // retrieve the software contest again, seems the contest sale is not updated
        return getContestServiceFacade().getSoftwareContestByProjectId(
                DirectStrutsActionsHelper.getTCSubjectFromSession(),
                result.getSoftwareCompetition().getProjectHeader().getId());
    }

    /**
     * <p>
     * Determines if it is activation request for software competition.
     * </p>
     * 
     * @param softwareCompetition
     *            the software competition
     * @return true if it is activation request
     * @throws DirectException
     *             if no billing project is defined
     */
    private boolean isActivation(SoftwareCompetition softwareCompetition) throws DirectException {
        if (activationFlag
                && Long.parseLong(softwareCompetition.getProjectHeader().getProperties()
                        .get(PROJECT_PROPERTIES_KEY_BILLING_PROJECT_ID)) <= 0) {
            throw new DirectException("no billing project is selected.");
        }

        return activationFlag;
    }

    /**
     * <p>
     * Gets the payment data for purchase/activation.
     * </p>
     * 
     * @param softwareCompetition
     *            the software competition
     * @return the payment data
     * @throws Exception
     *             if any error occurs when do purchasing
     */
    private TCPurhcaseOrderPaymentData getPaymentData(SoftwareCompetition softwareCompetition) throws Exception {
        long billingProjectId = Long.parseLong(softwareCompetition.getProjectHeader().getProperties()
                .get(PROJECT_PROPERTIES_KEY_BILLING_PROJECT_ID));
        return getPaymentData(billingProjectId);
    }

    /**
     * <p>
     * Gets the payment data for purchase/activation.
     * </p>
     * 
     * @param billingProjectId
     *            the billing project id
     * @return the payment data
     * @throws Exception
     *             if any error occurs when do purchasing
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
     * @param softwareCompetition
     *            Software competition object
     * @return the result map
     */
    private Map<String, Object> getSoftwareResult(SoftwareCompetition softwareCompetition) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("projectId", softwareCompetition.getProjectHeader().getId());
        result.put("endDate", DirectUtils.getDateString(DirectUtils.getEndDate(softwareCompetition)));
        result.put("paidFee", DirectUtils.getPaidFee(softwareCompetition));
        result.put("projectStatus", softwareCompetition.getProjectHeader().getProjectStatus());
        result.put("hasSpecReview", DirectUtils.hasSpecReview(softwareCompetition));
        result.put("isSpecReviewStarted", DirectUtils.isSpecReviewStarted(softwareCompetition));
        result.put("contestId", softwareCompetition.getProjectHeader().getId());
        
        String forumUrl = DataProvider.getContestDashboardData(
                softwareCompetition.getProjectHeader().getId(), false, false)
                .getForumURL();
        result.put("forumUrl", forumUrl);

        List<CompDocumentation> documentation = softwareCompetition.getAssetDTO().getDocumentation();
        result.put("documents", documentation);

        return result;
    }

    /**
     * <p>
     * Override the parent method to retrieve or create the prize list, the asset dto and the contest data. This method
     * will be called in a http round trip (another round trip different from the execute method) to init the html
     * client lists. This method is obviously re-called in the following round-trip, when the execute method is
     * performed but at the point it will does nothing.
     * </p>
     * 
     * @throws IllegalStateException
     *             if the contest service facade is not set yet
     * @throws Exception
     *             if any problem occurs.
     */
    @Override
    public void prepare() throws Exception {
        // call super
        super.prepare();

        ContestServiceFacade contestServiceFacade = getContestServiceFacadeWithISE();
        
        // if both projectId
        if (projectId > 0) {
            softwareCompetition = contestServiceFacade.getSoftwareContestByProjectId(
                    DirectStrutsActionsHelper.getTCSubjectFromSession(), projectId);
            assetDTO = softwareCompetition.getAssetDTO();
            projectHeader = softwareCompetition.getProjectHeader();
        }

        if (null == softwareCompetition) {
            assetDTO = new AssetDTO();
            projectHeader = new com.topcoder.management.project.Project();
        }
        projectHeader.setPrizes(null);
		projectHeader.setProjectCopilotTypes(null);
        projectHeader.setCopilotContestExtraInfos(null);
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
     * @param competitionType
     *            the competition type to set, can't be null considering the validation.
     */
    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }

    /**
     * <p>
     * Sets the flat indicating whether the contest is multi round or not.
     * 
     * @param hasMulti the flag indicating whether the contest is multi round or not.
     * @since TC Direct Replatforming Release 1
     */
    public void setHasMulti(boolean hasMulti) {
        this.hasMulti = hasMulti;
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
     * @param milestoneDate
     *            the milestone date
     */
    public void setMilestoneDate(Date milestoneDate) {
        this.milestoneDate = milestoneDate;
    }

    /**
     * <p>
     * End date of the contest.
     * </p>
     * 
     * @return End date of the contest.
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * End date of the contest.
     * </p>
     * 
     * @param endDate End date of the contest.
     */
    public void setEndDate(XMLGregorianCalendar endDate) {
        this.endDate = endDate;
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
     * @param assetDTO
     *            the asset dto
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
     * @param projectHeader
     *            the project header object
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
     * @param projectId
     *            the project id to set
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
     * @param tcDirectProjectId
     *            the tc direct project id to set
     */
    // @FieldExpressionValidator(message = "The tcDirectProjectId should be positive", key =
    // "i18n.SaveDraftContestAction.tcDirectProjectIdRequiredSetPositive", expression = "tcDirectProjectId >= 1")
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
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
     * @param shortDescription
     *            the short description of the asset. Should not be null or empty when updating or creating an asset.
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
     * @param detailedDescription
     *            the detailed description of the asset.
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
     * @param functionalDescription
     *            the functional description of the asset. Should not be null or empty when updating or creating an
     *            asset.
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
     * @param docUploadIds
     *            upload ids for documents
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
     * @param docCompIds
     *            the docCompIds to set
     */
    public void setDocCompIds(List<String> docCompIds) {
        this.docCompIds = docCompIds;
    }

    /**
     * <p>
     * Gets the technologies for development contest.
     * </p>
     * 
     * @return the technologies for development contest.
     */
    public List<String> getTechnologies() {
        return technologies;
    }

    /**
     * <p>
     * Sets the technologies for development contest.
     * </p>
     * 
     * @param technologies the technologies for development contest.
     */
    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }

    /**
     * Gets the root category id.
     * 
     * @return the root category id.
     */
    public long getRootCategoryId() {
        return rootCategoryId;
    }

    /**
     * Sets the root category id.
     * 
     * @param rootCategoryId the root category id.
     */
    public void setRootCategoryId(long rootCategoryId) {
        this.rootCategoryId = rootCategoryId;
    }

    /**
     * Gets the categories.
     * 
     * @return the categories
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * Sets the categories.
     * 
     * @param categories the categories
     */
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
     * @param activationFlag
     *            the activation flag
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
     * @param autoCreateDev
     *            the autoCreateDev to set
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
     * @param selectedDesignId
     *            the selectedDesignId to set
     */
    public void setSelectedDesignId(long selectedDesignId) {
        this.selectedDesignId = selectedDesignId;
    }

    /**
     * Sets the file types for studio contest.
     * 
     * @param fileTypes the file types for studio contest
     * @since TC Direct Replatforming Release 1
     */
    public void setFileTypes(List<String> fileTypes) {
        this.fileTypes = fileTypes;
    }

    /**
     * <p>
     * Populates the values of SoftwareCompetition object with parameters of this action.
     * </p>
     * 
     * @param studioCompetition
     *            the SoftwareCompetition object
     * @since TC Direct Replatforming Release 1
     */
    private void populateStudioCompetition(SoftwareCompetition studioCompetition) throws ContestServiceException {
        studioCompetition.getProjectHeader().setProjectFileTypes(getFileTypes(fileTypes));
    }

    /**
     * <p>
     * Populates the values of SoftwareCompetition object with parameters of this action.
     * </p>
     * 
     * @param softwareCompetition
     *            the SoftwareCompetition object
     */
    @SuppressWarnings("all")
    private void populateSoftwareCompetition(SoftwareCompetition softwareCompetition) {
        // sync in properties
        AssetDTO assetDTO = softwareCompetition.getAssetDTO();
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
    }

    /**
     * <p>
     * Gets the contest service facade, throw <code>IllegalStateException</code> if it is null.
     * </p>
     * 
     * @return the contest service facade
     * @throws IllegalStateException
     *             if the contest service facade is null
     */
    private ContestServiceFacade getContestServiceFacadeWithISE() {
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        if (null == contestServiceFacade) {
            throw new IllegalStateException("The contest service facade is not set.");
        }
        return contestServiceFacade;
    }
}
