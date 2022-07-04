/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * A bean entity that defines the team. It provides such team data as the team's id, name, description, finalization
 * status and the id of the associated project. It has the id and payment percentage of the team captain. It also has
 * the set of custom properties for the team. The custom properties are key-value pairs. The key must be a
 * non-null/empty String of maximum 255 characters, and the value must be a serializable java bean. It is serializable
 * so it can be used in a remote environment.
 * </p>
 * <p>
 * This entity follows java bean conventions for defining setters and getters for these properties, as well as the
 * minimum empty constructor. A full constructor is also provided for convenient usage.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class TeamHeader implements Serializable {

    /**
     * <p>
     * Represents the name of this team.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * It can be any value, including null/empty. Default is null for empty constructor.
     * </p>
     */
    private String name = null;

    /**
     * <p>
     * Represents a flag whether the time details are finalized.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * Default is false for empty constructor.
     * </p>
     */
    private boolean finalized = false;

    /**
     * <p>
     * Represents the ID of the project this team is participating in.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * Once set, it will not be negative. Default is -1 for empty constructor.
     * </p>
     */
    private long projectID = -1;

    /**
     * <p>
     * Represents the ID of this team.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * Once set, it will not be negative. Default is -1 for empty constructor.
     * </p>
     */
    private long teamID = -1;

    /**
     * <p>
     * Represents the ID of the resource that represents the captain.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * Once set, it will not be negative. Default is -1 for empty constructor.
     * </p>
     */
    private long captainResourceId = -1;

    /**
     * <p>
     * Represents the percentage of the payment the resource that represents the captain is assigned.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * It will be a number between 0 and 100, inclusive. Default is -1 for empty constructor.
     * </p>
     */
    private int captainPaymentPercentage = -1;

    /**
     * <p>
     * Represents a map of custom properties of this team.
     * </p>
     * <p>
     * The map is created on instantiation to a non-null Map, and this reference will never be null. It is accessed
     * via the setProperty, getProperty, and getAllproperties methods. The last method call results in a shallow copy
     * of this map being returned. It contains String keys, and java.io.Serializable values.
     * </p>
     * <p>
     * The map can be empty. It will never contain null/empty keys, and the values will never be null as well. The
     * keys will also be never than 255 characters long.
     * </p>
     */
    private final Map customProperties = new HashMap();

    /**
     * <p>
     * Represents the team description.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * It can be any value, including null/empty. Default is null for empty constructor.
     * </p>
     */
    private String description = null;

    /**
     * Empty constructor.
     */
    public TeamHeader() {
        // Does nothing.
    }

    /**
     * Full constructor. This convenience constructor allows for setting all values in one go.
     * @param name
     *            the name of this team
     * @param finalized
     *            a flag whether the time details are finalized
     * @param projectId
     *            the ID of the project this team is participating in
     * @param teamId
     *            the ID of this team
     * @param captainResourceId
     *            the ID of the resource that represents the captain
     * @param captainPaymentPercentage
     *            the percentage of the payment the resource that represents the captain is assigned
     * @param description
     *            the team description
     * @throws IllegalArgumentException
     *             If projectId, teamId, or captainResourceId is negative, or if captainPaymentPercentage is not
     *             between 0 and 100, inclusive
     */
    public TeamHeader(String name, boolean finalized, long projectId, long teamId, long captainResourceId,
        int captainPaymentPercentage, String description) {
        setName(name);
        setFinalized(finalized);
        setProjectId(projectId);
        setTeamId(teamId);
        setCaptainResourceId(captainResourceId);
        setCaptainPaymentPercentage(captainPaymentPercentage);
        setDescription(description);
    }

    /**
     * Gets the name field value.
     * @return The name field value
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name field value.
     * @param name
     *            The name field value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the finalized field value.
     * @return The finalized field value
     */
    public boolean getFinalized() {
        return finalized;
    }

    /**
     * Sets the finalized field value.
     * @param finalized
     *            The finalized field value
     */
    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }

    /**
     * Gets the projectId field value.
     * @return The projectId field value
     */
    public long getProjectId() {
        return projectID;
    }

    /**
     * Sets the projectId field value.
     * @param projectId
     *            The projectId field value
     * @throws IllegalArgumentException
     *             If projectId is negative
     */
    public void setProjectId(long projectId) {
        Helper.assertNonNegative(projectId, "projectId");
        this.projectID = projectId;
    }

    /**
     * Gets the teamId field value.
     * @return The teamId field value
     */
    public long getTeamId() {
        return teamID;
    }

    /**
     * Sets the teamId field value.
     * @param teamId
     *            The teamId field value
     * @throws IllegalArgumentException
     *             If teamId is negative
     */
    public void setTeamId(long teamId) {
        Helper.assertNonNegative(teamId, "teamId");
        this.teamID = teamId;
    }

    /**
     * Gets the captainResourceId field value.
     * @return The captainResourceId field value
     */
    public long getCaptainResourceId() {
        return captainResourceId;
    }

    /**
     * Sets the captainResourceId field value.
     * @param captainResourceId
     *            The captainResourceId field value
     * @throws IllegalArgumentException
     *             If captainResourceId is negative
     */
    public void setCaptainResourceId(long captainResourceId) {
        Helper.assertNonNegative(captainResourceId, "captainResourceId");
        this.captainResourceId = captainResourceId;
    }

    /**
     * Gets the captainPaymentPercentage field value.
     * @return The captainPaymentPercentage field value
     */
    public int getCaptainPaymentPercentage() {
        return captainPaymentPercentage;
    }

    /**
     * Sets the captainPaymentPercentage field value.
     * @param captainPaymentPercentage
     *            The captainPaymentPercentage field value
     * @throws IllegalArgumentException
     *             If captainPaymentPercentage is not between 0 and 100, inclusive
     */
    public void setCaptainPaymentPercentage(int captainPaymentPercentage) {
        if (captainPaymentPercentage < 0 || captainPaymentPercentage > Helper.HUNDRED) {
            // Check illegal parameter
            throw new IllegalArgumentException("captainPaymentPercentage must be between 0 and 100, inclusive");
        }
        this.captainPaymentPercentage = captainPaymentPercentage;
    }

    /**
     * Puts a custom property in the customProperties map. A null value removes the property from the map if it is
     * present.
     * @param key
     *            A String key of the property
     * @param value
     *            A Serializable value of the property, or null if this is a remove action
     * @throws IllegalArgumentException
     *             If key is null/empty or longer than 255 characters
     */
    public void setProperty(String key, Serializable value) {
        Helper.assertStringNotNullOrEmpty_ShorterThan256(key, "key");
        if (value != null) {
            customProperties.put(key, value);
        } else {
            customProperties.remove(key);
        }
    }

    /**
     * Gets a custom property from the customProperties map. Value will not be null.
     * @return A Serializable value of the property. it will not be null
     * @param key
     *            A String key of the property whose value is to be retrieved from the customProperties map.
     * @throws IllegalArgumentException
     *             If key is null/empty or longer than 255 characters
     */
    public Serializable getProperty(String key) {
        Helper.assertStringNotNullOrEmpty_ShorterThan256(key, "key");
        return (Serializable) customProperties.get(key);
    }

    /**
     * Returns a shallow copy of the customProperties map.
     * @return a shallow copy of the customProperties map.
     */
    public Map getAllProperties() {
        return new HashMap(customProperties);
    }

    /**
     * <p>
     * Gets the description field value.
     * </p>
     * @return The description field value
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets the description field value.
     * </p>
     * @param description
     *            the description field value.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the finalized field value.
     * @return The finalized field value
     */
    public boolean isFinalized() {
        return getFinalized();
    }
}
