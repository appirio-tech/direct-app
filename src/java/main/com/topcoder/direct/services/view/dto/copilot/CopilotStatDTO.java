/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.copilot;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * The statistics of the copilot.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Cockpit Copilot Posting Registrants Analysis)
 */
public class CopilotStatDTO implements Serializable {
    /**
     * The skill rule id of wireframe copilot skill.
     */
    public static long COPILOT_SKILL_WIREFRAME = 55L;

    /**
     * The skill rule id of desktop UI copilot skill.
     */
    public static long COPILOT_SKILL_DESKTOP_UI = 56L;

    /**
     * The skill rule id of mobile UI copilot skill.
     */
    public static long COPILOT_SKILL_MOBILE_UI = 57L;

    /**
     * The skill rule id of web UI copilot skill.
     */
    public static long COPILOT_SKILL_WEB_UI = 58L;

    /**
     * The skill rule id of marketing/branding/presentation copilot skill.
     */
    public static long COPILOT_SKILL_PRESENTATION = 59L;

    /**
     * The skill rule id of UI development copilot skill.
     */
    public static long COPILOT_SKILL_UI_DEV = 60L;

    /**
     * The skill rule id of architecture and design copilot skill.
     */
    public static long COPILOT_SKILL_ARCHITECTURE_AND_DESIGN = 61L;

    /**
     * The skill rule id of component development copilot skill.
     */
    public static long COPILOT_SKILL_COMPONENT_DEV = 62L;

    /**
     * The skill rule id of assembly copilot skill.
     */
    public static long COPILOT_SKILL_ASSEMBLY = 63L;

    /**
     * The skill rule id of idea generation copilot skill.
     */
    public static long COPILOT_SKILL_IDEA_GENERATION = 64L;

    /**
     * The skill rule id of conceptualization copilot skill.
     */
    public static long COPILOT_SKILL_CONCEPTUALIZATION = 65L;

    /**
     * The skill rule id of test scenarios copilot skill.
     */
    public static long COPILOT_SKILL_TEST_SCENARIOS = 66L;

    /**
     * The skill rule id of bug hunt copilot skill.
     */
    public static long COPILOT_SKILL_BUG_HUNT = 67L;

    /**
     * The skill rule id of big data copilot skill.
     */
    public static long COPILOT_SKILL_BIG_DATA = 68L;

    /**
     * The user id of the copilot
     */
    private long userId;

    /**
     * The handle of the copilot.
     */
    private String handle;

    /**
     * The country of the copilot.
     */
    private String country;

    /**
     * The timezone of the copilot.
     */
    private String timeZone;

    /**
     * Whether the copilot is a studio copilot.
     */
    private boolean isStudioCopilot;

    /**
     * Whether the copilot is a software copilot.
     */
    private boolean isSoftwareCopilot;

    /**
     * The fulfillment of the copilot.
     */
    private double fulfillment;

    /**
     * The current contests the copilot is working on.
     */
    private int currentContests;

    /**
     * The current projects the copilot is working on.
     */
    private int currentProjects;

    /**
     * The matched experiences of the copilot.
     */
    private List<Experience> matchedExperience;

    /**
     * The other experiences of the copilot.
     */
    private List<Experience> otherExperience;

    /**
     * The image path of the copilot.
     */
    private String imagePath;

    /**
     * The copilot skills set which contains the skill rule ids.
     */
    private Set<Long> copilotSkills = new HashSet<Long>();


    /**
     * Gets the user id.
     *
     * @return the user id.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the user id.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the user handle.
     *
     * @return the user handle.
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the user handle.
     *
     * @param handle the user handle.
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Gets the image path.
     *
     * @return the image path.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the image path.
     *
     * @param imagePath the image path.
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Gets the country.
     *
     * @return the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country.
     *
     * @param country the country.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the timezone.
     *
     * @return the time zone.
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the timezone.
     *
     * @param timeZone the timezone.
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Whether the copilot is the studio copilots.
     *
     * @return Whether the copilot is the studio copilots.
     */
    public boolean isStudioCopilot() {
        return isStudioCopilot;
    }

    /**
     * Sets Whether the copilot is the studio copilots.
     *
     * @param studioCopilot Whether the copilot is the studio copilots.
     */
    public void setStudioCopilot(boolean studioCopilot) {
        isStudioCopilot = studioCopilot;
    }

    /**
     * Gets whether the copilot is the software copilots.
     *
     * @return whether the copilot is the software copilots.
     */
    public boolean isSoftwareCopilot() {
        return isSoftwareCopilot;
    }

    /**
     * Sets whether the copilot is the software copilots.
     *
     * @param softwareCopilot whether the copilot is the software copilots.
     */
    public void setSoftwareCopilot(boolean softwareCopilot) {
        isSoftwareCopilot = softwareCopilot;
    }

    /**
     * Gets the fulfillment.
     *
     * @return the fulfillment.
     */
    public double getFulfillment() {
        return fulfillment;
    }

    /**
     * Sets the fulfillment
     *
     * @param fulfillment the fulfillment.
     */
    public void setFulfillment(double fulfillment) {
        this.fulfillment = fulfillment;
    }

    /**
     * Gets the current contests.
     *
     * @return the current contests.
     */
    public int getCurrentContests() {
        return currentContests;
    }

    /**
     * Sets the current contests.
     *
     * @param currentContests the current contests.
     */
    public void setCurrentContests(int currentContests) {
        this.currentContests = currentContests;
    }

    /**
     * Gets the current projects.
     *
     * @return the current projects.
     */
    public int getCurrentProjects() {
        return currentProjects;
    }

    /**
     * Sets the current projects.
     *
     * @param currentProjects the current projects.
     */
    public void setCurrentProjects(int currentProjects) {
        this.currentProjects = currentProjects;
    }

    /**
     * Gets the matched experiences.
     *
     * @return the matched experiences.
     */
    public List<Experience> getMatchedExperience() {
        return matchedExperience;
    }

    /**
     * Sets the matched experiences.
     *
     * @param matchedExperience the matched experiences.
     */
    public void setMatchedExperience(List<Experience> matchedExperience) {
        this.matchedExperience = matchedExperience;
    }

    /**
     * Gets other experiences.
     *
     * @return the other experiences.
     */
    public List<Experience> getOtherExperience() {
        return otherExperience;
    }

    /**
     * Sets the other experiences.
     *
     * @param otherExperience the other experiences.
     */
    public void setOtherExperience(List<Experience> otherExperience) {
        this.otherExperience = otherExperience;
    }

    /**
     * Gets whether the copilot has the UI group skill.
     *
     * @return whether the copilot has the UI group skill.
     */
    public boolean isHasUISkill() {
        return copilotSkills.contains(COPILOT_SKILL_WIREFRAME)
                && copilotSkills.contains(COPILOT_SKILL_DESKTOP_UI)
                && copilotSkills.contains(COPILOT_SKILL_MOBILE_UI)
                && copilotSkills.contains(COPILOT_SKILL_WEB_UI)
                && copilotSkills.contains(COPILOT_SKILL_PRESENTATION);
    }

    /**
     * Gets whether the copilot has the UI group skill.
     *
     * @return whether the copilot has the UI group skill.
     */
    public boolean isHasImplementationSkill() {
        return copilotSkills.contains(COPILOT_SKILL_ASSEMBLY)
                && copilotSkills.contains(COPILOT_SKILL_COMPONENT_DEV)
                && copilotSkills.contains(COPILOT_SKILL_UI_DEV)
                && copilotSkills.contains(COPILOT_SKILL_ARCHITECTURE_AND_DESIGN);
    }

    /**
     * Gets whether the copilot has the business analysis group skill.
     *
     * @return whether the copilot has the business analysis group skill.
     */
    public boolean isHasBusinessAnalysisSkill() {
        return copilotSkills.contains(COPILOT_SKILL_CONCEPTUALIZATION)
                && copilotSkills.contains(COPILOT_SKILL_IDEA_GENERATION);
    }

    /**
     * Gets whether the copilot has the analytic group skill.
     *
     * @return whether the copilot has the analytic group skill.
     */
    public boolean isHasAnalyticsSkill() {
        return copilotSkills.contains(COPILOT_SKILL_BIG_DATA);
    }

    /**
     * Gets whether the copilot has the testing group skill.
     *
     * @return whether the copilot has the testing group skill.
     */
    public boolean isHasTestingSkill() {
        return copilotSkills.contains(COPILOT_SKILL_BUG_HUNT)
                && copilotSkills.contains(COPILOT_SKILL_TEST_SCENARIOS);
    }

    /**
     * Gets the fulfillment in integer which is rounded.
     *
     * @return the rounded to integer copilot fulfillment
     */
    public int getRoundedFulfillment() {
        return Math.round((float) getFulfillment());
    }

    /**
     * Gets the copilot rating color.
     *
     * @return the copilot rating color.
     */
    public String getFulfillmentColor() {
        int value = getRoundedFulfillment();
        if (value < 70) {
            return "Grey";
        } else if (value >= 70 && value < 80) {
            return "Green";
        } else if (value >= 80 && value < 90) {
            return "Blue";
        } else if (value >= 90 && value < 95) {
            return "Yellow";
        } else {
            return "Red";
        }
    }

    /**
     * Adds the copilot skill rule id to the copilot skills set.
     *
     * @param copilotSkillId the copilot skill rule id.
     */
    public void addCopilotSkill(long copilotSkillId) {
        this.copilotSkills.add(copilotSkillId);
    }

    /**
     * The copilot experience of certain project type and category.
     */
    public static class Experience {
        /**
         * The project type name.
         */
        private String projectType;

        /**
         * The project type id.
         */
        private long projectTypeId;

        /**
         * The project category name.
         */
        private String projectCategory;

        /**
         * The project category id.
         */
        private long projectCategoryId;

        /**
         * The active project number.
         */
        private long activeProjectNumber;

        /**
         * The completed project number.
         */
        private long completedProjectNumber;

        /**
         * Gets the project type.
         *
         * @return the project type.
         */
        public String getProjectType() {
            return projectType;
        }

        /**
         * Sets the project type.
         *
         * @param projectType the project type.
         */
        public void setProjectType(String projectType) {
            this.projectType = projectType;
        }

        /**
         * Gets the project type id.
         *
         * @return the project type id.
         */
        public long getProjectTypeId() {
            return projectTypeId;
        }

        /**
         * Sets the project type id.
         *
         * @param projectTypeId the project type id.
         */
        public void setProjectTypeId(long projectTypeId) {
            this.projectTypeId = projectTypeId;
        }

        /**
         * Gets the project category name.
         *
         * @return the project category name.
         */
        public String getProjectCategory() {
            return projectCategory;
        }

        /**
         * Sets the project category name.
         *
         * @param projectCategory the project category name.
         */
        public void setProjectCategory(String projectCategory) {
            this.projectCategory = projectCategory;
        }

        /**
         * Gets the project category id.
         *
         * @return the project category id.
         */
        public long getProjectCategoryId() {
            return projectCategoryId;
        }

        /**
         * Sets the project category id.
         *
         * @param projectCategoryId the project category id.
         */
        public void setProjectCategoryId(long projectCategoryId) {
            this.projectCategoryId = projectCategoryId;
        }

        /**
         * Gets the active project number.
         *
         * @return the active project number.
         */
        public long getActiveProjectNumber() {
            return activeProjectNumber;
        }

        /**
         * Sets the active project number.
         *
         * @param activeProjectNumber the active project number.
         */
        public void setActiveProjectNumber(long activeProjectNumber) {
            this.activeProjectNumber = activeProjectNumber;
        }

        /**
         * Gets the completed project number.
         *
         * @return the completed project number.
         */
        public long getCompletedProjectNumber() {
            return completedProjectNumber;
        }

        /**
         * Sets the completed project number.
         *
         * @param completedProjectNumber the completed project number.
         */
        public void setCompletedProjectNumber(long completedProjectNumber) {
            this.completedProjectNumber = completedProjectNumber;
        }
    }
}
