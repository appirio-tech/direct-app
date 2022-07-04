/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * <p>
 * Provides the ability to edit the properties of a Scorecard, while automatically updating the
 * modification user and timestamp whenever a property is edited.
 * </p>
 *
 * <p>
 * This class allows the client using this component to associate a user with an editing session and then avoid
 * the hassle of having the manually call the setModificationUser and setModificationTimestamp
 * methods whenever changes are made.
 * </p>
 *
 * <p>
 * This class simply parallels the set/reset/add/remove/clear/insert methods of the Scorecard class,
 * which proxies the call to the Scorecard instance, and then invokes the setModificationUser and
 * setModificationTimestamp methods.
 * </p>
 *
 * <p>
 * Changes in v1.1 (Cockpit Spec Review Backend Service Update v1.0):
 * - removed "final" from user and scorecard attributes
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is NOT thread safe.
 * </p>
 *
 * @see         Scorecard
 *
 * @author      aubergineanode, UFP2161, pulky
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scorecardEditor", propOrder = {"scorecard", "user"})
public class ScorecardEditor {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Fields

    /**
     * <p>
     * The scorecard that is currently being edited.
     * </p>
     */
    private Scorecard scorecard;

    /**
     * <p>
     * The user that is assigned as the modification user of the scorecard on each mutator call.
     * </p>
     */
    private String user;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors


    /**
     * Creates a new ScorecardEditor.
     */
    public ScorecardEditor() {
    }

    /**
     * Creates a new ScorecardEditor using the specified user and a new Scorecard instance.
     *
     * @param   user
     *          The name of the user to set as the creation user of the scorecard (as well as the modification
     *          user if changes are made).
     *
     * @throws  IllegalArgumentException
     *          The user is null.
     */
    public ScorecardEditor(String user) {
        this(new Scorecard(), user);

        scorecard.setCreationUser(user);
        scorecard.setCreationTimestamp(new Date());

        setModificationInfo();
    }

    /**
     * Creates a new ScorecardEditor using the specified scorecard and user.
     *
     * @param   scorecard
     *          The scorecard to be edited by the specified user.
     * @param   user
     *          The name of the user to set as the modification user of the scorecard if changes are made.
     *
     * @throws  IllegalArgumentException
     *          The scorecard or user is null.
     */
    public ScorecardEditor(Scorecard scorecard, String user) {
        Util.checkNotNull(scorecard, "scorecard");
        Util.checkNotNull(user, "user");

        this.scorecard = scorecard;
        this.user = user;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - scorecard#id Mutators

    /**
     * Sets the identifier of the scorecard being edited.
     *
     * @param   id
     *          The new identifier of the scorecard being edited.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero.
     */
    public void setId(long id) {
        scorecard.setId(id);
        setModificationInfo();
    }

    /**
     * Resets the identifier of the scorecard being edited to an "undefined" value (which is -1).
     */
    public void resetId() {
        scorecard.resetId();
        setModificationInfo();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - scorecard#name Mutators

    /**
     * Sets the name of the scorecard being edited.
     *
     * @param   name
     *          The new name of the scorecard being edited.
     */
    public void setName(String name) {
        scorecard.setName(name);
        setModificationInfo();
    }

    /**
     * Resets the name of the scorecard being edited to an "undefined" value (which is null).
     */
    public void resetName() {
        scorecard.resetName();
        setModificationInfo();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - scorecard#scorecardStatus Mutators

    /**
     * Sets the status of the scorecard being edited.
     *
     * @param   scorecardStatus
     *          The new status of the scorecard being edited.
     */
    public void setScorecardStatus(ScorecardStatus scorecardStatus) {
        scorecard.setScorecardStatus(scorecardStatus);
        setModificationInfo();
    }

    /**
     * Resets the status of the scorecard being edited to an "undefined" value (which is null).
     */
    public void resetScorecardStatus() {
        scorecard.resetScorecardStatus();
        setModificationInfo();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - scorecard#scorecardType Mutators

    /**
     * Sets the type of the scorecard being edited.
     *
     * @param   scorecardType
     *          The new type of the scorecard being edited.
     */
    public void setScorecardType(ScorecardType scorecardType) {
        scorecard.setScorecardType(scorecardType);
        setModificationInfo();
    }

    /**
     * Resets the type of the scorecard being edited to an "undefined" value (which is null).
     */
    public void resetScorecardType() {
        scorecard.resetScorecardType();
        setModificationInfo();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - scorecard#category Mutators

    /**
     * Sets the category of the scorecard being edited.
     *
     * @param   category
     *          The new category of the scorecard being edited.
     *
     * @throws  IllegalArgumentException
     *          The category is less than or equal to zero.
     */
    public void setCategory(long category) {
        scorecard.setCategory(category);
        setModificationInfo();
    }

    /**
     * Resets the category of the scorecard being edited to an "undefined" value (which is -1).
     */
    public void resetCategory() {
        scorecard.resetCategory();
        setModificationInfo();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - scorecard#version Mutators

    /**
     * Sets the version string of the scorecard being edited.
     *
     * @param   version
     *          The new version string of the scorecard being edited.
     */
    public void setVersion(String version) {
        scorecard.setVersion(version);
        setModificationInfo();
    }

    /**
     * Resets the version string of the scorecard being edited to an "undefined" value (which is null).
     */
    public void resetVersion() {
        scorecard.resetVersion();
        setModificationInfo();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - scorecard#minScore Mutators

    /**
     * Sets the minimum score of the scorecard being edited.
     *
     * @param   minScore
     *          The new minimum score of the scorecard being edited.
     *
     * @throws  IllegalArgumentException
     *          The minScore is Float.NaN, Float.POSITIVE_INFINITY, or
     *          Float.NEGATIVE_INFINITY.
     * @throws  IllegalStateException
     *          The minScore is greater than the current maximum score (if set).
     */
    public void setMinScore(float minScore) {
        scorecard.setMinScore(minScore);
        setModificationInfo();
    }

    /**
     * Resets the minimum score of the scorecard being edited to an "undefined" value (which is Float.NaN).
     */
    public void resetMinScore() {
        scorecard.resetMinScore();
        setModificationInfo();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - scorecard#maxScore Mutators

    /**
     * Sets the maximum score of the scorecard being edited.
     *
     * @param   maxScore
     *          The new maximum score of the scorecard being edited.
     *
     * @throws  IllegalArgumentException
     *          The maxScore is Float.NaN, Float.POSITIVE_INFINITY, or
     *          Float.NEGATIVE_INFINITY.
     * @throws  IllegalStateException
     *          The maxScore is less than the current minimum score (if set).
     */
    public void setMaxScore(float maxScore) {
        scorecard.setMaxScore(maxScore);
        setModificationInfo();
    }

    /**
     * Resets the maximum score of the scorecard being edited to an "undefined" value (which is Float.NaN).
     */
    public void resetMaxScore() {
        scorecard.resetMaxScore();
        setModificationInfo();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - scorecard#groups Mutators

    /**
     * Adds the group to the scorecard being edited.
     *
     * @param   group
     *          The group that should be added to the scorecard being edited.
     *
     * @throws  IllegalArgumentException
     *          The group is null.
     */
    public void addGroup(Group group) {
        scorecard.addGroup(group);
        setModificationInfo();
    }

    /**
     * Adds the list of groups to the scorecard being edited.
     *
     * @param   groups
     *          The list of groups that should be added to the scorecard being edited.
     *
     * @throws  IllegalArgumentException
     *          The groups is null or contains a null.
     */
    public void addGroups(Group[] groups) {
        scorecard.addGroups(groups);
        setModificationInfo();
    }

    /**
     * Inserts the group at the given index to the scorecard being edited.
     *
     * @param   group
     *          The group to be inserted at the specified index.
     * @param   index
     *          The index to insert the group at.
     *
     * @throws  IllegalArgumentException
     *          The group is null.
     * @throws  IndexOutOfBoundsException
     *          The index is negative, or greater than the number of groups.
     */
    public void insertGroup(Group group, int index) {
        scorecard.insertGroup(group, index);
        setModificationInfo();
    }

    /**
     * Removes the group from the scorecard being edited.
     *
     * @param   group
     *          The group to be removed from the scorecard being edited.
     *
     * @throws  IllegalArgumentException
     *          The group is null.
     */
    public void removeGroup(Group group) {
        scorecard.removeGroup(group);
        setModificationInfo();
    }

    /**
     * Removes the group at the given index from the scorecard being edited.
     *
     * @param   groupIndex
     *          The index of the group to be removed from the scorecard being edited.
     *
     * @throws  IndexOutOfBoundsException
     *          The groupIndex is negative or greater than or equal to the number of groups.
     */
    public void removeGroup(int groupIndex) {
        scorecard.removeGroup(groupIndex);
        setModificationInfo();
    }

    /**
     * Removes the list of groups from the scorecard being edited.
     *
     * @param   groups
     *          The list of groups to be removed from the scorecard being edited.
     *
     * @throws  IllegalArgumentException
     *          The groups is null or contains a null.
     */
    public void removeGroups(Group[] groups) {
        scorecard.removeGroups(groups);
        setModificationInfo();
    }

    /**
     * Clears all groups from the scorecard being edited.
     */
    public void clearGroups() {
        scorecard.clearGroups();
        setModificationInfo();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - scorecard#inUse Mutators

    /**
     * Sets whether or not the scorecard being edited is active or not.
     *
     * @param   inUse
     *          Whether or not the scorecard being edited is active or not.
     */
    public void setInUse(boolean inUse) {
        scorecard.setInUse(inUse);
        setModificationInfo();
    }

    /**
     * Resets the scorecard's in use switch to false.
     */
    public void resetInUse() {
        scorecard.resetInUse();
        setModificationInfo();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - Miscellaneous Accessors

    /**
     * Gets the scorecard that is being edited.
     *
     * @return  The scorecard that is being edited.
     */
    public Scorecard getScorecard() {
        return scorecard;
    }

    /**
     * Gets the user that is set as the modification user each time one of the mutators is called.
     *
     * @return  The user that is set as the modification user each time one of the mutators is called.
     */
    public String getUser() {
        return user;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - Miscellaneous Mutators

    /**
     * <p>
     * Sets the modification user and timestamp on the scorecard being edited to the user field and the current
     * date/time, respectively.
     * </p>
     *
     * <p>
     * This method is called at the end of each set/reset mutator.
     * </p>
     */
    private void setModificationInfo() {
        scorecard.setModificationUser(user);
        scorecard.setModificationTimestamp(new Date());
    }
}
