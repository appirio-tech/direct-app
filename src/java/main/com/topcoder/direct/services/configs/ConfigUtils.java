/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import java.util.List;

import javax.xml.bind.JAXBContext;

/**
 * <p>
 * Utils class for handling various configuration files.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class ConfigUtils {
    private static StudioContestTypes studioContestTypes;

    private static Overview overview;

    private static List<StudioSubtypeOverview> studioOverviews;

    private static ContestFees contestFees;

    private static List<StudioSubtypeContestFee> studioSubtypeContestFees;

    private static FileTypes fileTypes;

    static {
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ConfigUtils() {
        //do nothing
    }

    private static void init() throws Exception {
        JAXBContext studioTypesJaxbContext = JAXBContext.newInstance(StudioContestTypes.class);

        studioContestTypes = (StudioContestTypes) studioTypesJaxbContext.createUnmarshaller().unmarshal(
            ConfigUtils.class.getResourceAsStream("/contestTypes.xml"));

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

        for (ContestFee contestFee : contestFees.getContestFees()) {
            if ("STUDIO".equals(contestFee.getId())) {
                studioSubtypeContestFees = contestFee.getStudioSubtypeContestFees();
            }
        }

        if (studioSubtypeContestFees == null || studioSubtypeContestFees.size() == 0) {
            throw new RuntimeException("no studio subtype contest fee is defined in contestFees.xml");
        }

        JAXBContext fileTypesJaxbContext = JAXBContext.newInstance(FileTypes.class);
        fileTypes = (FileTypes) fileTypesJaxbContext.createUnmarshaller().unmarshal(
            ConfigUtils.class.getResourceAsStream("/fileTypes.xml"));
    }

    public static List<StudioContestType> getStudioContestTypes() {
        return studioContestTypes.getContestTypes();
    }

    public static Overview getOverview() {
        return overview;
    }

    public static List<StudioSubtypeOverview> getStudioOverviews() {
        return studioOverviews;
    }

    public static StudioSubtypeOverview getStudioOverview(long contestTypeId) {
        for (StudioSubtypeOverview overview : getStudioOverviews()) {
            if (overview.getId() == contestTypeId) {
                return overview;
            }
        }

        return null;
    }

    public static ContestFees getContestFees() {
        return contestFees;
    }

    public static List<StudioSubtypeContestFee> getStudioContestFees() {
        return studioSubtypeContestFees;
    }

    public static FileTypes getFileTypes() {
        return fileTypes;
    }
}
