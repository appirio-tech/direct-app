/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project.planner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * The DTO for transferring the project planner data. Used by project planner import / export.
 * </p>
 *
 * @author GreatKevin
 * @version 1.0 (Module Assembly - TopCoder Cockpit Project Planner)
 */
public class ProjectPlannerTransferDTO implements Serializable {

    /**
     * The bug race number.
     */
    private int bugRaceNumber;

    /**
     * The bug race prize
     */
    private double bugRacePrize;

    /**
     * The contests in the project plan.
     */
    private List<ProjectPlannerContestRow> contests = new ArrayList<ProjectPlannerContestRow>();

    /**
     * Gets the bug race number.
     *
     * @return the bug race number.
     */
    public int getBugRaceNumber() {
        return bugRaceNumber;
    }

    /**
     * Sets the bug race number.
     *
     * @param bugRaceNumber the bug race number.
     */
    public void setBugRaceNumber(int bugRaceNumber) {
        this.bugRaceNumber = bugRaceNumber;
    }

    /**
     * Gets the bug race prize.
     *
     * @return the bug race prize.
     */
    public double getBugRacePrize() {
        return bugRacePrize;
    }

    /**
     * Sets the bug race prize.
     *
     * @param bugRacePrize the bug race prize.
     */
    public void setBugRacePrize(double bugRacePrize) {
        this.bugRacePrize = bugRacePrize;
    }

    /**
     * Gets the contests in the project plan.
     *
     * @return the contests in the project plan.
     */
    public List<ProjectPlannerContestRow> getContests() {
        return contests;
    }

    /**
     * Sets the contests in the project plan.
     *
     * @param contests the contests in the project plan.
     */
    public void setContests(List<ProjectPlannerContestRow> contests) {
        this.contests = contests;
    }

    /**
     * This class represents a contest in the project plan.
     */
    public static class ProjectPlannerContestRow implements Serializable {
        /**
         * The index of the contest.
         */
        private int index;

        /**
         * The name of the contest.
         */
        private String name;

        /**
         * The type of the contest.
         */
        private String type;

        /**
         * The followed contest index id.
         */
        private String follow;

        /**
         * The contest index ids this contest depends on.
         */
        private List<Integer> dependsOn;

        /**
         * The time start.
         */
        private int timeStart;

        /**
         * The time length.
         */
        private int timeLength;

        /**
         * The time end.
         */
        private int timeEnd;

        /**
         * The start date in string.
         */
        private String startStr;

        /**
         * The end date in string.
         */
        private String endStr;

        /**
         * Gets the contest index.
         *
         * @return the contest index.
         */
        public int getIndex() {
            return index;
        }

        /**
         * Sets the contest index.
         *
         * @param index the contest index.
         */
        public void setIndex(int index) {
            this.index = index;
        }

        /**
         * Gets the contest name.
         *
         * @return the contest name.
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the contest name.
         *
         * @param name the contest name.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Gets the contest type.
         *
         * @return the contest type.
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the contest type.
         *
         * @param type the contest type.
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * Gets the followed contest index id.
         *
         * @return the followed contest index id.
         */
        public String getFollow() {
            return follow;
        }

        /**
         * Sets the followed contest index id.
         *
         * @param follow the followed contest index id.
         */
        public void setFollow(String follow) {
            this.follow = follow;
        }

        /**
         * Gets the contest index ids this contest depends on.
         *
         * @return the contest index ids this contest depends on.
         */
        public List<Integer> getDependsOn() {
            return dependsOn;
        }

        /**
         * Sets the contest index ids this contest depends on.
         *
         * @param dependsOn the contest index ids this contest depends on.
         */
        public void setDependsOn(List<Integer> dependsOn) {
            this.dependsOn = dependsOn;
        }

        /**
         * Gets the time start.
         *
         * @return the time start.
         */
        public int getTimeStart() {
            return timeStart;
        }

        /**
         * Sets the time start.
         *
         * @param timeStart the time start.
         */
        public void setTimeStart(int timeStart) {
            this.timeStart = timeStart;
        }

        /**
         * Gets the time length.
         *
         * @return the time length.
         */
        public int getTimeLength() {
            return timeLength;
        }

        /**
         * Sets the time length.
         *
         * @param timeLength the time length.
         */
        public void setTimeLength(int timeLength) {
            this.timeLength = timeLength;
        }

        /**
         * Gets the time end.
         *
         * @return
         */
        public int getTimeEnd() {
            return timeEnd;
        }

        /**
         * Sets the time end.
         *
         * @param timeEnd the time end.
         */
        public void setTimeEnd(int timeEnd) {
            this.timeEnd = timeEnd;
        }

        /**
         * Gets the time start string.
         *
         * @return the time start string.
         */
        public String getStartStr() {
            return startStr;
        }

        /**
         * Sets the time start string.
         *
         * @param startStr the time start string.
         */
        public void setStartStr(String startStr) {
            this.startStr = startStr;
        }

        /**
         * Gets the time end string.
         *
         * @return the time end string.
         */
        public String getEndStr() {
            return endStr;
        }

        /**
         * Sets the time end string.
         *
         * @param endStr the time end string.
         */
        public void setEndStr(String endStr) {
            this.endStr = endStr;
        }
    }
}
