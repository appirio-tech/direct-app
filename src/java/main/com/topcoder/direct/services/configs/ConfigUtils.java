/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import com.topcoder.direct.services.view.dto.contest.ReviewType;
import com.topcoder.direct.services.view.util.challenge.CostCalculationService;
import com.topcoder.management.payment.calculator.impl.DefaultProjectPaymentCalculator;
import com.topcoder.project.phases.PhaseType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;

/**
 * <p>
 * Utils class for handling various configuration files.
 * </p>
 * <p>
 * Version 1.1 - TC Direct - Software Contest Creation Update Assembly 1.0 change notes:
 * - Add the logic to load copilot fees from the configuration file copilotFeeds.xml
 * </p>
 *
 * <p>
 * Version 1.1 - TC Direct Replatforming Release 1 Change note
 * <ul>
 * <li>Remove studioContestTypes filed and getter method for it.</li>
 * <li>Update {@link #init()} method to don't initialize studioContestTypes from files.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 - TC Cockpit Bug Tracking R1 Contest Tracking Assembly 1.0 change notes:
 * - Add the logic to load issue tracking configs from configuration file IssueTrackingConfig.xml
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Cockpit - Marathon Match Contest Detail Page)
 * - Add static configuration field algorithmSubtypeContestFees
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TopCoder Direct VM Instances Management) changes:
 * - Add static configuration field {@link #cloudVMServiceAccessErrorConfig}
 * </p>
 *
 * <p>
 * Version 1.5 (BUGR-10708 Update Cockpit Copilot Fee Calculation)
 * - Update {@link #init()} to get copilot fee value from DefaultProjectPaymentCalculator
 * </p>
 * 
 * <p>
 * Version 1.6 - Topcoder - Remove JIRA Issues Related Functionality In Direct App v1.0
 * - remove JIRA related functionality
 * </p>
 *
 * @author BeBetter, Veve, jiajizhou86, Veve, TCCoder
 * @version 1.6
 */
public final class ConfigUtils {
    /**
     * <p>
     * Overview object to hold studio overview information.
     * </P>
     */
    private static Overview overview;;

    /**
     * <p>
     * studio subtype overview information.
     * </p>
     */
    private static List<StudioSubtypeOverview> studioOverviews;

    /**
     * <p>
     * Contest fees.
     * </p>
     */
    private static ContestFees contestFees;

    /**
     * <p>
     * Studio subtype contest fees.
     * </p>
     */
    private static List<StudioSubtypeContestFee> studioSubtypeContestFees;


    /**
     * <p>
     * Algorithm subtype contest fees
     * </p>
     *
     * @since 1.3
     */
    private static List<AlgorithmSubtypeContestFee> algorithmSubtypeContestFees;

    /**
     * <p>
     * File types.
     * </p>
     */
    private static FileTypes fileTypes;

    /**
     * <p>
     * Software contest fees.
     * </p>
     */
    private static Map<String, ContestFee> softwareContestFees;

    /**
     * <p>
     * Copilot fees.
     * </p>
     *
     * @since 1.1
     */
    private static Map<String, CopilotFee> copilotFees;

    /**
     * <p>
     * Cloud VM Service Access Error configuration.
     * </p>
     *
     * @since 1.4
     */
    private static CloudVMServiceAccessErrorConfig cloudVMServiceAccessErrorConfig;

    /**
     * The challenge fee configuration object, it contains separate configuration for
     * - development
     * - design
     * - data
     */
    private static ChallengeFeeConfiguration challengeFeeConfiguration;


    static {
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>
     * Private ctor. Does nothing.
     * </p>
     */
    private ConfigUtils() {
        // do nothing
    }

    /**
     * <p>
     * Initialize the configuration objects.
     * </p>
     * <p> version 1.1 changes - add load of copilot fees</p>
     * <p> version 1.2 changes - add load of issue tracking configuration</p>
     */
    private static void init() throws Exception {
        // load configuration using JAXB
        JAXBContext overviewJaxbContext = JAXBContext.newInstance(Overview.class);

        overview = (Overview) overviewJaxbContext.createUnmarshaller().unmarshal(
                ConfigUtils.class.getResourceAsStream("/overview.xml"));

        for (ContestOverview contestOverview : overview.getContestOverviews()) {
            if ("STUDIO".equals(contestOverview.getId())) {
                studioOverviews = contestOverview.getStudioSubtypeOverviews();
            }
        }

        if (studioOverviews == null || studioOverviews.size() == 0) {
            throw new RuntimeException("no studio overview is defined in overview.xml");
        }

        JAXBContext contestFeesJaxbContext = JAXBContext.newInstance(ContestFees.class);
        contestFees = (ContestFees) contestFeesJaxbContext.createUnmarshaller().unmarshal(
                ConfigUtils.class.getResourceAsStream("/contestFees.xml"));

        softwareContestFees = new HashMap<String, ContestFee>();

        for (ContestFee contestFee : contestFees.getContestFees()) {
            if (contestFee.isStudioFee()) {
                studioSubtypeContestFees = contestFee.getStudioSubtypeContestFees();
            } else if (contestFee.isAlgorithmFee()) {
                algorithmSubtypeContestFees = contestFee.getAlgorithmSubtypeContestFees();
            } else {
                softwareContestFees.put(contestFee.getContestTypeId() + "", contestFee);
            }
        }

        if (studioSubtypeContestFees == null || studioSubtypeContestFees.size() == 0) {
            throw new RuntimeException("no studio subtype contest fee is defined in contestFees.xml");
        }

        JAXBContext fileTypesJaxbContext = JAXBContext.newInstance(FileTypes.class);
        fileTypes = (FileTypes) fileTypesJaxbContext.createUnmarshaller().unmarshal(
                ConfigUtils.class.getResourceAsStream("/fileTypes.xml"));

        // load the copilot fees from the configuration copilotFees.xml
        DefaultProjectPaymentCalculator calculator = new DefaultProjectPaymentCalculator();

        JAXBContext copilotFeesJaxbContext = JAXBContext.newInstance(CopilotFees.class);
        CopilotFees parsedFees = (CopilotFees) copilotFeesJaxbContext.createUnmarshaller().unmarshal(
                ConfigUtils.class.getResourceAsStream("/copilotFees.xml"));
        copilotFees = new HashMap<String, CopilotFee>();

        // put copilot fee into the map
        for(CopilotFee copilotFee : parsedFees.getCopilotFees()) {
            copilotFees.put(String.valueOf(copilotFee.getContestTypeId()), copilotFee);

            // gets the copilot fee from default project payment calculator to overrides if exists

            BigDecimal copilotPayment = calculator.getDefaultPayment(copilotFee.getContestTypeId(),
                    DefaultProjectPaymentCalculator.COPILOT_RESOURCE_ROLE_ID, BigDecimal.ZERO, 1);
            if(copilotPayment != null) {
                copilotFee.setCopilotFee(copilotPayment.doubleValue());
            }
        }

        // load cloud vm access error configuration
        JAXBContext vmServiceErrorMessageJaxbContext = JAXBContext.newInstance(CloudVMServiceAccessErrorConfig.class);
        cloudVMServiceAccessErrorConfig = (CloudVMServiceAccessErrorConfig) vmServiceErrorMessageJaxbContext
                .createUnmarshaller()
                .unmarshal(ConfigUtils.class.getResourceAsStream("/CloudVMAccessErrorConfig.xml"));
    }

    /**
     * <p>
     * Gets overview information.
     * </p>
     *
     * @return overview
     */
    public static Overview getOverview() {
        return overview;
    }

    /**
     * <p>
     * Get studio overviews.
     * </p>
     *
     * @return studio overviews
     */
    public static List<StudioSubtypeOverview> getStudioOverviews() {
        return studioOverviews;
    }

    /**
     * <p>
     * Get studio overview.
     * </p>
     *
     * @return studio subtype overview
     */
    public static StudioSubtypeOverview getStudioOverview(long contestTypeId) {
        for (StudioSubtypeOverview overview : getStudioOverviews()) {
            if (overview.getId() == contestTypeId) {
                return overview;
            }
        }

        return null;
    }


    public static ChallengeFeeConfiguration getChallengeFeeConfiguration() {
        if (challengeFeeConfiguration == null) {
            setupChallengeFeeConfiguration();
        }
        return challengeFeeConfiguration;
    }

    private static void setupChallengeFeeConfiguration() {

        try {
            CostCalculationService service = CostCalculationService.getInstance();

            if (challengeFeeConfiguration == null) {
                for (StudioSubtypeContestFee studioSubtypeContestFee : studioSubtypeContestFees) {
                    double totalReviewCost = 0;
                    Map<String, BigDecimal> phasesPayment = service.getPhasesPayment(0, studioSubtypeContestFee.getId(),
                            ReviewType.COMMUNITY, new BigDecimal(studioSubtypeContestFee.getFirstPlaceCost()));

                    for (Map.Entry<String, BigDecimal> entry : phasesPayment.entrySet()) {
                        if (!(entry.getKey().equalsIgnoreCase(CostCalculationService.TOTAL_RESULT_KEY) ||
                                entry.getKey().equalsIgnoreCase(PhaseType.SPECIFICATION_REVIEW_PHASE.getName()))) {
                            totalReviewCost += entry.getValue().doubleValue();
                        }
                    }

                    studioSubtypeContestFee.setReviewCost(totalReviewCost);
                    studioSubtypeContestFee.setSpecReviewCost(
                            (phasesPayment.get(PhaseType.SPECIFICATION_REVIEW_PHASE.getName()) ==
                                    null) ? 0 : phasesPayment.get(
                                    PhaseType.SPECIFICATION_REVIEW_PHASE.getName()).doubleValue());
                }


                for (ContestFee softwareEntry : softwareContestFees.values()) {

                    List<ContestCostBillingLevel> contestCostBillingLevels = softwareEntry.getContestCost().getContestCostBillingLevels();

                    for (ContestCostBillingLevel contestCostBillingLevel : contestCostBillingLevels) {
                        Map<String, BigDecimal> phasesPayment = service.getPhasesPayment(0,
                                softwareEntry.getContestTypeId(),
                                ReviewType.COMMUNITY, new BigDecimal(contestCostBillingLevel.getFirstPlaceCost()));
                        double totalReviewCost = 0;
                        for (Map.Entry<String, BigDecimal> entry : phasesPayment.entrySet()) {
                            if (!(entry.getKey().equalsIgnoreCase(CostCalculationService.TOTAL_RESULT_KEY) ||
                                    entry.getKey().equalsIgnoreCase(PhaseType.SPECIFICATION_REVIEW_PHASE.getName()))) {
                                totalReviewCost += entry.getValue().doubleValue();
                            }
                        }
                        contestCostBillingLevel.setReviewBoardCost(totalReviewCost);
                    }
                }

                challengeFeeConfiguration = new ChallengeFeeConfiguration();
                challengeFeeConfiguration.setDevelopment(softwareContestFees);
                challengeFeeConfiguration.setDesign(studioSubtypeContestFees);
                challengeFeeConfiguration.setData(algorithmSubtypeContestFees);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error when setting up challenge fee configuration", e);
        }
    }

    /**
     * <p>
     * Gets software contest fees.
     * </p>
     *
     * @return software contest fees
     */
    public static Map<String, ContestFee> getSoftwareContestFees() {
        return getChallengeFeeConfiguration().getDevelopment();
    }

    /**
     * <p>
     * Gets studio subtype contest fees.
     * </p>
     *
     * @return studio subtype contest fees
     */
    public static List<StudioSubtypeContestFee> getStudioContestFees() {
        return getChallengeFeeConfiguration().getDesign();
    }

    /**
     * <p>
     * Gets algorithm subtype contest fees.
     * </p>
     *
     * @return algorithm subtype contest fees.
     * @since 1.3
     */
    public static List<AlgorithmSubtypeContestFee> getAlgorithmSubtypeContestFees() {
        return getChallengeFeeConfiguration().getData();
    }

    /**
     * <p>
     * Gets file types.
     * </p>
     *
     * @return file types
     */
    public static FileTypes getFileTypes() {
        return fileTypes;
    }

    /**
     * <p>
     * Gets copilot fees.
     * </p>
     *
     * @return the copilot fees.
     * @since 1.1
     */
    public static Map<String, CopilotFee> getCopilotFees() {
        return copilotFees;
    }

    /**
     * <p>
     * Gets the configuration for cloud vm service access error messages.
     * </p>
     *
     * @retrn the configuration for cloud vm service access error messages.
     * @since 1.4
     */
    public static CloudVMServiceAccessErrorConfig getCloudVMServiceAccessErrorConfig() {
        return cloudVMServiceAccessErrorConfig;
    }

    public static class ChallengeFeeConfiguration {

        private List<StudioSubtypeContestFee> design;

        private List<AlgorithmSubtypeContestFee> data;

        private Map<String, ContestFee> development;

        public List<StudioSubtypeContestFee> getDesign() {
            return design;
        }

        public void setDesign(List<StudioSubtypeContestFee> design) {
            this.design = design;
        }

        public List<AlgorithmSubtypeContestFee> getData() {
            return data;
        }

        public void setData(List<AlgorithmSubtypeContestFee> data) {
            this.data = data;
        }

        public Map<String, ContestFee> getDevelopment() {
            return development;
        }

        public void setDevelopment(Map<String, ContestFee> development) {
            this.development = development;
        }
    }
}
