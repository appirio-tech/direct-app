/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.util.Arrays;
import java.util.Date;

import com.topcoder.management.project.FileType;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.PrizeType;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectStudioSpecification;
import com.topcoder.management.project.ProjectType;

/**
 * <p>
 * This is the test help class used to aggregate some common utilities methods.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.2
 */
public final class TestHelper {
    /**
     * Private constructor.
     */
    private TestHelper() {
    }

    /**
     * Creates file type instance according to the given fields values.
     *
     * @param description
     *            the description field value to set
     * @param extension
     *            the extension field value to set
     * @param imageFile
     *            the imageFile field value to set
     * @param bundledFile
     *            the bundledFile field value to set
     * @param sort
     *            the sort field value to set
     * @return the created instance
     */
    static FileType createFileType(String description, String extension, boolean imageFile, boolean bundledFile,
        int sort) {
        FileType fileType = new FileType();

        fileType.setBundledFile(true);
        fileType.setCreationTimestamp(new Date());
        fileType.setCreationUser("user");
        fileType.setDescription(description);
        fileType.setExtension(extension);
        fileType.setImageFile(true);
        fileType.setModificationTimestamp(new Date());
        fileType.setModificationUser("root");
        fileType.setSort(sort);

        return fileType;
    }

    /**
     * Creates prize instance according to the given fields values.
     *
     * @param place
     *            the place field value to set
     * @param prizeAmount
     *            the prizeAmount field value to set
     * @param numberOfSubmissions
     *            the numberOfSubmissions field value to set
     * @return the created instance
     */
    static Prize createPrize(int place, double prizeAmount, int numberOfSubmissions) {
        Prize prize = new Prize();
        prize.setPlace(place);
        prize.setPrizeAmount(prizeAmount);
        prize.setNumberOfSubmissions(numberOfSubmissions);
        PrizeType prizeType = new PrizeType();
        prizeType.setId(1);
        prizeType.setDescription("Component Design Prize");
        prize.setPrizeType(prizeType);
        return prize;
    }

    /**
     * Creates project studio specification instance according to the given fields values.
     *
     * @param goals
     *            the goals field value to set
     * @param targetAudience
     *            the targetAudience field value to set
     * @param roundOneIntroduction
     *            the roundOneIntroduction field value to set
     * @return the created instance
     */
    static ProjectStudioSpecification createProjectStudioSpecification(String goals, String targetAudience,
        String roundOneIntroduction) {
        ProjectStudioSpecification spec = new ProjectStudioSpecification();
        spec.setBrandingGuidelines("brandingGuidelines");
        spec.setColors("colors");
        spec.setDislikedDesignWebSites("dislikedDesignWebSites");
        spec.setFonts("fonts");
        spec.setLayoutAndSize("layoutAndSize");
        spec.setOtherInstructions("otherInstructions");
        spec.setRoundOneIntroduction(roundOneIntroduction);
        spec.setRoundTwoIntroduction("roundTwoIntroduction");
        spec.setSubmittersLockedBetweenRounds(true);
        spec.setTargetAudience(targetAudience);
        spec.setWinningCriteria("winningCriteria");
        spec.setGoals(goals);
        return spec;
    }

    /**
     * Get a sample Project object to test, with project id = 0, project category = .Net, project type = Topcoder,
     * project status = Active.
     *
     * @return a sample Project object
     */
    static Project getSampleProject1() {
        // create a ProjectStatus object
        ProjectStatus status = new ProjectStatus(1, "Active");

        // create a ProjectType object
        ProjectType type = new ProjectType(1, "Topcoder");

        // create a ProjectCategory object
        ProjectCategory category = new ProjectCategory(1, ".Net", type);

        // create the sample project object
        Project project = new Project(category, status);

        // set the properties
        project.setProperty("property 1", "value 1");
        project.setProperty("property 2", "value 2");

        Prize prize = createPrize(1, 600.00, 5);
        project.setPrizes(Arrays.asList(new Prize[]{prize}));

        FileType fileType = createFileType("description 1", "extension 1", true, false, 1);

        project.setProjectFileTypes(Arrays.asList(new FileType[]{fileType}));
        project.setProjectStudioSpecification(createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1"));

        project.setTcDirectProjectId(1);

        return project;
    }
}
