/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * <p>
 * A simple container of several data fields, along with an ordered list of groups that together represents a
 * scorecard, the root level in the scorecard model hierarchy.
 * </p>
 *
 * <p>
 * Changes in v1.1 (Cockpit Spec Review Backend Service Update v1.0):
 * - fixed @XmlType's name.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is NOT thread safe.
 * </p>
 *
 * @see         Group
 * @see         ScorecardEditor
 * @see         ScorecardStatus
 * @see         ScorecardType
 *
 * @author      aubergineanode, UFP2161, pulky
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scorecard", propOrder = {"category", "creationTimestamp", "creationUser", "groups", "inUse", "maxScore",
        "minScore", "modificationTimestamp", "modificationUser", "scorecardStatus", "scorecardType", "version"})
public class Scorecard extends NamedScorecardStructure {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Fields

    /**
     * The default tolerance to use when checking to see if the weights sum up to 100.
     */
    public static final float DEFAULT_WEIGHT_SUM_TOLERANCE = 1e-9f;

    /**
     * The sentinel scorecard status, used to indicate that the scorecard status has not yet been set through the
     * constructor or the setScorecardStatus method (or has been reset by the resetScorecardStatus
     * method).
     */
    private static final ScorecardStatus SENTINEL_SCORECARD_STATUS = null;

    /**
     * The sentinel scorecard type, used to indicate that the scorecard type has not yet been set through the
     * constructor or the setScorecardType method (or has been reset by the resetScorecardType
     * method).
     */
    private static final ScorecardType SENTINEL_SCORECARD_TYPE = null;

    /**
     * The sentinel category, used to indicate that the category has not yet been set through the constructor or
     * the setCategory method (or has been reset by the resetCategory method).
     */
    private static final long SENTINEL_CATEGORY = -1;

    /**
     * The sentinel version, used to indicate that the version has not yet been set through the constructor or
     * the setVersion method (or has been reset by the resetVersion method).
     */
    private static final String SENTINEL_VERSION = null;

    /**
     * The sentinel minimum score, used to indicate that the minimum score has not yet been set through the
     * constructor or the setMinScore method (or has been reset by the resetMinScore method).
     */
    private static final float SENTINEL_MIN_SCORE = Float.NaN;

    /**
     * The sentinel maximum score, used to indicate that the maximum score has not yet been set through the
     * constructor or the setMaxScore method (or has been reset by the resetMaxScore method).
     */
    private static final float SENTINEL_MAX_SCORE = Float.NaN;

    /**
     * The sentinel in use switch, used as the default value for the in use switch.
     */
    private static final boolean SENTINEL_IN_USE = false;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Fields

    /**
     * <p>
     * The general status of the scorecard.
     * </p>
     *
     * <p>
     * A value of SENTINEL_SCORECARD_STATUS indicates that this field has not yet been initialized. There
     * are no restrictions of what this field can be set to.
     * </p>
     */
    private ScorecardStatus scorecardStatus = SENTINEL_SCORECARD_STATUS;

    /**
     * <p>
     * The general type that the scorecard belongs to.
     * </p>
     *
     * <p>
     * A value of SENTINEL_SCORECARD_TYPE indicates that this field has not yet been initialized. There
     * are no restrictions of what this field can be set to.
     * </p>
     */
    private ScorecardType scorecardType = SENTINEL_SCORECARD_TYPE;

    /**
     * <p>
     * The category identifier of the scorecard.
     * </p>
     *
     * <p>
     * A value of SENTINEL_CATEGORY indicates that this field has not yet been initialized. A positive
     * value indicates that the client has assigned an identifier. A negative value (other than the sentinel
     * value) is disallowed.
     * </p>
     */
    private long category = SENTINEL_CATEGORY;

    /**
     * <p>
     * The version of the scorecard.
     * </p>
     *
     * <p>
     * A value of SENTINEL_VERSION indicates that this field has not yet been initialized. There are no
     * restrictions of what this field can be set to.
     * </p>
     */
    private String version = SENTINEL_VERSION;

    /**
     * <p>
     * The minimum score that is achievable on the scorecard.
     * </p>
     *
     * <p>
     * A value of SENTINEL_MIN_SCORE indicates that this field has not yet been initialized. When
     * initialized, this field must be set to a real non-infinite number, and must be less than or equal to
     * maxScore (if maxScore is set).
     * </p>
     */
    private float minScore = SENTINEL_MIN_SCORE;

    /**
     * <p>
     * The maximum score that is achievable on the scorecard.
     * </p>
     *
     * <p>
     * A value of SENTINEL_MAX_SCORE indicates that this field has not yet been initialized. When
     * initialized, this field must be set to a real non-infinite number, and must be greater than or equal to
     * minScore (if minScore is set).
     * </p>
     */
    private float maxScore = SENTINEL_MAX_SCORE;

    /**
     * <p>
     * The list of Group instances that make up this Scorecard.
     * </p>
     *
     * <p>
     * All items in this list are required to be non-null Group instances, and no duplicates (by reference
     * equality) are allowed.
     * </p>
     */
    private final List groups = new ArrayList();

    /**
     * Whether or not this scorecard is currently in active use.
     */
    private boolean inUse = SENTINEL_IN_USE;

    /**
     * <p>
     * The name of the user that was responsible for creating the scorecard.
     * </p>
     *
     * <p>
     * A null value indicates that this field has not yet been initialized yet. There are no restrictions
     * as to what values this field may be set to.
     * </p>
     */
    private String creationUser = null;

    /**
     * <p>
     * The date/time when the scorecard was created.
     * </p>
     *
     * <p>
     * A null value indicates that this field has not yet been initialized yet. There are no restrictions
     * as to what values this field may be set to.
     * </p>
     */
    private Date creationTimestamp = null;

    /**
     * <p>
     * The name of the user that was responsible for the last modification to the scorecard.
     * </p>
     *
     * <p>
     * A null value indicates that this field has not yet been initialized yet. There are no restrictions
     * as to what values this field may be set to.
     * </p>
     */
    private String modificationUser = null;

    /**
     * <p>
     * The date/time when the scorecard was last modified.
     * </p>
     *
     * <p>
     * A null value indicates that this field has not yet been initialized yet. There are no restrictions
     * as to what values this field may be set to.
     * </p>
     */
    private Date modificationTimestamp = null;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    /**
     * Creates a new Scorecard using the default values.
     */
    public Scorecard() {
        super();
    }

    /**
     * Creates a new Scorecard using the specified identifier.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero.
     */
    public Scorecard(long id) {
        super(id);
    }

    /**
     * Creates a new Scorecard using the specified identifier and name.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   name
     *          The scorecard structure's name to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the name is null.
     */
    public Scorecard(long id, String name) {
        super(id, name);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - scorecardStatus Accessors and Mutators

    /**
     * Sets the status of this Scorecard.
     *
     * @param   scorecardStatus
     *          The scorecard's new status.
     */
    public void setScorecardStatus(ScorecardStatus scorecardStatus) {
        this.scorecardStatus = scorecardStatus;
    }

    /**
     * Resets the status of this Scorecard to null.
     */
    public void resetScorecardStatus() {
        setScorecardStatus(SENTINEL_SCORECARD_STATUS);
    }

    /**
     * Gets the status of this Scorecard.
     *
     * @return  This scorecard's status.
     */
    public ScorecardStatus getScorecardStatus() {
        return scorecardStatus;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - scorecardType Accessors and Mutators

    /**
     * Sets the type of this Scorecard.
     *
     * @param   scorecardType
     *          The scorecard's new type.
     */
    public void setScorecardType(ScorecardType scorecardType) {
        this.scorecardType = scorecardType;
    }

    /**
     * Resets the type of this Scorecard to null.
     */
    public void resetScorecardType() {
        setScorecardType(SENTINEL_SCORECARD_TYPE);
    }

    /**
     * Gets the type of this Scorecard.
     *
     * @return  This scorecard's type.
     */
    public ScorecardType getScorecardType() {
        return scorecardType;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - category Accessors and Mutators

    /**
     * Sets the category that this Scorecard belongs to.
     *
     * @param   category
     *          The scorecard's new category identifier.
     *
     * @throws  IllegalArgumentException
     *          The category is less than or equal to zero.
     */
    public void setCategory(long category) {
        this.category = category;
    }

    /**
     * Resets the category of this Scorecard to an "undefined" value (which is -1).
     */
    public void resetCategory() {
        category = SENTINEL_CATEGORY;
    }

    /**
     * Gets the category of this Scorecard.
     *
     * @return  This scorecard's category.
     *
     * @throws  IllegalStateException
     *          The scorecard's category is not yet set.
     */
    public long getCategory() {
        if (category == SENTINEL_CATEGORY) {
            throw new IllegalStateException("The category has not been set yet.");
        }

        return category;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - version Accessors and Mutators

    /**
     * Sets the version of this Scorecard.
     *
     * @param   version
     *          The scorecard's new version.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Resets the version of this Scorecard to null.
     */
    public void resetVersion() {
        setVersion(SENTINEL_VERSION);
    }

    /**
     * Gets the version of this Scorecard.
     *
     * @return  This scorecard's version.
     */
    public String getVersion() {
        return version;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - minScore Accessors and Mutators

    /**
     * Sets the minimum score that is achievable on this Scorecard.
     *
     * @param   minScore
     *          The new minimum score that is achievable.
     *
     * @throws  IllegalArgumentException
     *          The minScore is Float.NaN, Float.POSITIVE_INFINITY, or
     *          Float.NEGATIVE_INFINITY.
     * @throws  IllegalStateException
     *          The minScore is greater than the current maximum score (if set).
     */
    public void setMinScore(float minScore) {
        this.minScore = minScore;
    }

    /**
     * Resets the minimum score that is achievable on this Scorecard to an "undefined" value (which is
     * Float.NaN).
     */
    public void resetMinScore() {
        minScore = SENTINEL_MIN_SCORE;
    }

    /**
     * Gets the minimum score that is achievable on this Scorecard.
     *
     * @return  This scorecard's minimum achievable score.
     *
     * @throws  IllegalStateException
     *          The minimum score is not initialized.
     */
    public float getMinScore() {
        if (Float.isNaN(minScore)) {
            throw new IllegalStateException("The minimum score has not yet been initialized.");
        }

        return minScore;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - maxScore Accessors and Mutators

    /**
     * Sets the maximum score that is achievable on this Scorecard.
     *
     * @param   maxScore
     *          The new maximum score that is achievable.
     *
     * @throws  IllegalArgumentException
     *          The maxScore is Float.NaN, Float.POSITIVE_INFINITY, or
     *          Float.NEGATIVE_INFINITY.
     * @throws  IllegalStateException
     *          The maxScore is less than the current minimum score (if set).
     */
    public void setMaxScore(float maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * Resets the maximum score that is achievable on this Scorecard to an "undefined" value (which is
     * Float.NaN).
     */
    public void resetMaxScore() {
        maxScore = SENTINEL_MAX_SCORE;
    }

    /**
     * Gets the maximum score that is achievable on this Scorecard.
     *
     * @return  This scorecard's maximum achievable score.
     *
     * @throws  IllegalStateException
     *          The maximum score is not initialized.
     */
    public float getMaxScore() {
        if (Float.isNaN(maxScore)) {
            throw new IllegalStateException("The maximum score has not yet been initialized.");
        }

        return maxScore;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - group Add Mutators

    /**
     * <p>
     * Adds the group section to this Scorecard.
     * </p>
     *
     * <p>
     * If the specified group already exists in this Scorecard, this method results in no operation being
     * done. Otherwise, the group is appended to the end of the list of groups in this Scorecard.
     * </p>
     *
     * @param   group
     *          The group to be added to this scorecard.
     *
     * @throws  IllegalArgumentException
     *          The group is null.
     */
    public void addGroup(Group group) {
        Util.checkNotNull(group, "group");
        addGroupInternal(group);
    }

    /**
     * <p>
     * Adds the specified list of groups to this Scorecard.
     * </p>
     *
     * <p>
     * All duplicate groups (either found inside the array, or against this scorecard's list of groups) will be
     * ignored, while all other groups in the specified list will be appended to the end of the list of groups
     * in this Scorecard.
     * </p>
     *
     * @param   groups
     *          The list of groups to be added to this scorecard.
     *
     * @throws  IllegalArgumentException
     *          The groups is null or contains null entries.
     */
    public void addGroups(Group[] groups) {
        Util.checkArrayNotNull(groups, "groups");

        for (int i = 0; i < groups.length; ++i) {
            addGroupInternal(groups[i]);
        }
    }

    /**
     * Inserts the specified group into this Scorecard at the specified index.
     *
     * @param   group
     *          The group to be inserted to this scorecard.
     * @param   index
     *          The index where the group should be inserted into.
     *
     * @throws  IllegalArgumentException
     *          The group is null
     * @throws  IndexOutOfBoundsException
     *          The index is negative or greater than the number of groups.
     */
    public void insertGroup(Group group, int index) {
        Util.checkNotNull(group, "group");

        if (!groups.contains(group)) {
            groups.add(index, group);
        }
    }

    /**
     * <p>
     * Adds the specified non-null group to this Scorecard.
     * </p>
     *
     * <p>
     * This is mainly used to avoid checking for nulls again during the addGroups method.
     * </p>
     *
     * @param   group
     *          The group to be added to this scorecard.
     */
    private void addGroupInternal(Group group) {
        if (!groups.contains(group)) {
            groups.add(group);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - group Remove Mutators

    /**
     * <p>
     * Removes the specified group from this Scorecard.
     * </p>
     *
     * <p>
     * If the specified group does not exist in this scorecard (by reference equality), this method results in no
     * operation being done. Otherwise, the specified group will be removed from the list of groups in this
     * Scorecard.
     * </p>
     *
     * @param   group
     *          The group to remove from this scorecard.
     *
     * @throws  IllegalArgumentException
     *          The group is null.
     */
    public void removeGroup(Group group) {
        Util.checkNotNull(group, "group");
        groups.remove(group);
    }

    /**
     * Removes the group at the specified index from this Scorecard.
     *
     * @param   index
     *          The index of the group to be removed.
     *
     * @throws  IndexOutOfBoundsException
     *          The index is negative or greater than or equal to the number of groups.
     */
    public void removeGroup(int index) {
        groups.remove(index);
    }

    /**
     * <p>
     * Removes the specified list of groups from this Scorecard.
     * </p>
     *
     * <p>
     * Any group not found in this scorecard will be ignored, while all other groups in the specified list will
     * be removed from list of groups in this Scorecard.
     * </p>
     *
     * @param   groups
     *          The list of groups to be removed from this scorecard.
     *
     * @throws  IllegalArgumentException
     *          The groups is null or contains null entries.
     */
    public void removeGroups(Group[] groups) {
        Util.checkArrayNotNull(groups, "groups");
        this.groups.removeAll(Arrays.asList(groups));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - group Clear Mutators

    /**
     * Clears all groups associated with this Scorecard.
     */
    public void clearGroups() {
        groups.clear();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - group Get Accessors

    /**
     * Gets the Group at the specified position in this Scorecard.
     *
     * @param   groupIndex
     *          The index of the group to retrieve.
     *
     * @return  The Group at the specified position in this Scorecard.
     *
     * @throws  IndexOutOfBoundsException
     *          The groupIndex is less than 0, or greater than or equal to the number of groups.
     */
    public Group getGroup(int groupIndex) {
        return (Group) groups.get(groupIndex);
    }

    /**
     * Gets a list of all the groups associated with this Scorecard.
     *
     * @return  A list of all the groups associated with this Scorecard.
     */
    public Group[] getAllGroups() {
        return (Group[]) groups.toArray(new Group[groups.size()]);
    }

    /**
     * Gets a list of all the groups associated with this Scorecard.
     *
     * @return  A list of all the groups associated with this Scorecard.
     */
    public List getGroups() {
        return groups;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - group Miscellaneous Accessors Mutators

    /**
     * Gets the number of groups currently associated with this Scorecard.
     *
     * @return  The number of groups currently associated with this Scorecard.
     */
    public int getNumberOfGroups() {
        return groups.size();
    }

    /**
     * Checks that the sum of the weights of the groups list is 100 plus/minus the
     * DEFAULT_WEIGHT_SUM_TOLERANCE, and that each group's sections' weights sum to 100 plus/minus the
     * DEFAULT_WEIGHT_SUM_TOLERANCE.
     *
     * @return  true if the group weights sum to 100 plus/minus the DEFAULT_WEIGHT_SUM_TOLERANCE,
     *          and each group's sections' weights sum to 100 plus/minus the DEFAULT_WEIGHT_SUM_TOLERANCE;
     *          false otherwise.
     */
    public boolean checkWeights() {
        return checkWeights(DEFAULT_WEIGHT_SUM_TOLERANCE);
    }

    /**
     * Checks that the sum of the weights of the groups list is 100 plus/minus the tolerance, and that
     * each group's sections' weights sum to 100 plus/minus the tolerance.
     *
     * @param   tolerance
     *          The absolute amount that the sum of weights can at most differ from 100.
     *
     * @return  true if the group weights sum to 100 plus/minus the tolerance, and each group's
     *          sections' weights sum to 100 plus/minus the tolerance; false otherwise.
     *
     * @throws  IllegalArgumentException
     *          The tolerance is negative, Float.POSITIVE_INFINITY, or Float.NaN.
     */
    public boolean checkWeights(float tolerance) {
        // Groups' weights do not sum up to 100 plus/minus the tolerance.
        if (!Util.checkWeights(groups, tolerance)) {
            return false;
        }

        // Check that each groups's sections' weights sum up to 100 plus/minus the tolerance.
        Iterator itr = groups.iterator();

        while (itr.hasNext()) {
            if (!((Group) itr.next()).checkWeights(tolerance)) {
                return false;
            }
        }

        // All sum of weights check out.
        return true;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - inUse Accessors and Mutators

    /**
     * Sets whether or not this Scorecard is currently active.
     *
     * @param   inUse
     *          The new in use switch for this scorecard.
     */
    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    /**
     * Resets the in use switch to false.
     */
    public void resetInUse() {
        setInUse(SENTINEL_IN_USE);
    }

    /**
     * Gets whether or not this Scorecard is currently active.
     *
     * @return  true if this scorecard is currently in use; false otherwise.
     */
    public boolean isInUse() {
        return inUse;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - creationUser Accessors and Mutators

    /**
     * Sets the user that created this Scorecard.
     *
     * @param   creationUser
     *          The new user that created this scorecard.
     */
    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    /**
     * Gets the user that created this Scorecard.
     *
     * @return  The user that created this Scorecard.
     */
    public String getCreationUser() {
        return creationUser;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - creationTimestamp Accessors and Mutators

    /**
     * Sets the date/time when this Scorecard was created.
     *
     * @param   creationTimestamp
     *          The new date/time when this scorecard was created.
     */
    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * Gets the date/time when this Scorecard was created.
     *
     * @return  The date/time when this Scorecard was created.
     */
    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - modificationUser Accessors and Mutators

    /**
     * Sets the user that last modified this Scorecard.
     *
     * @param   modificationUser
     *          The new user that last modified this scorecard.
     */
    public void setModificationUser(String modificationUser) {
        this.modificationUser = modificationUser;
    }

    /**
     * Gets the user that last modified this Scorecard.
     *
     * @return  The user that last modified this Scorecard.
     */
    public String getModificationUser() {
        return modificationUser;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - modificationTimestamp Accessors and Mutators

    /**
     * Sets the date/time when this Scorecard was last modified.
     *
     * @param   modificationTimestamp
     *          The new date/time when this scorecard was last modified.
     */
    public void setModificationTimestamp(Date modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }

    /**
     * Gets the date/time when this Scorecard was last modified.
     *
     * @return  The date/time when this Scorecard was last modified.
     */
    public Date getModificationTimestamp() {
        return modificationTimestamp;
    }
}
