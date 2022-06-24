/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * A bean entity that defines a position in a team, i.e. a placeholder for team members. It may be filled by a
 * resource. If not, it may also be a published position. A position also has an id, name, description, payment
 * percentage and a set of custom properties. The custom properties are key-value pairs. The key must be a
 * non-null/empty String of maximum 255 characters, and the value must be a serializable java bean. It is serializable
 * so it can be used in a remote environment.
 * </p>
 * <p>
 * It is expected that all positions in a team must not exceed their cumulative payment percentages of 100%.
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
public class TeamPosition implements Serializable {

    /**
     * <p>
     * Represents the position description.
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
     * <p>
     * Represents a flag whether this position has been filled. Basically, the position is filled if memberResourceId
     * is set.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * Default is false for empty constructor.
     * </p>
     */
    private boolean filled = false;

    /**
     * <p>
     * Represents the ID of the member resource that is taking this position.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * It can be changed to -1 using its setter. Default is -1 for empty constructor.
     * </p>
     */
    private long memberResourceId = -1;

    /**
     * <p>
     * Represents the percentage of the payment this position will pay.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * It will be a number between 0 and 100, inclusive. Default is -1 for empty constructor.
     * </p>
     */
    private int paymentPercentage = -1;

    /**
     * <p>
     * Represents the name of this position.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * It can be any value, including null/empty. Default is -1 for empty constructor.
     * </p>
     */
    private String name = null;

    /**
     * <p>
     * Represents the ID of this position.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * Once set, it will not be negative. Default is -1 for empty constructor.
     * </p>
     */
    private long positionId = -1;

    /**
     * <p>
     * Represents a flag as to whether this position has been published and available.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed with the getter.
     * </p>
     * <p>
     * Default is false for empty constructor.
     * </p>
     */
    private boolean published = false;

    /**
     * <p>
     * Represents a map of custom properties of this position.
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
     * Empty constructor.
     */
    public TeamPosition() {
        // Does nothing.
    }

    /**
     * Full constructor. This convenience constructor allows for setting all values in one go.
     * @param description
     *            the position description
     * @param filled
     *            a flag whether this position has been filled
     * @param memberResourceId
     *            the ID of the member resource that is taking this position. Can be -1 if not yet set.
     * @param paymentPercentage
     *            the percentage of the payment this position will pay
     * @param name
     *            the name of this position
     * @param positionId
     *            the ID of this position
     * @param published
     *            a flag as to whether this position has been published and available
     * @throws IllegalArgumentException
     *             If positionId is negative, or if paymentPercentage is not between 0 and 100, inclusive, or if
     *             memberResourceId is below -1.
     */
    public TeamPosition(String description, boolean filled, long memberResourceId, int paymentPercentage,
        String name, long positionId, boolean published) {
        setDescription(description);
        setFilled(filled);
        setMemberResourceId(memberResourceId);
        setPaymentPercentage(paymentPercentage);
        setName(name);
        setPositionId(positionId);
        setPublished(published);
    }

    /**
     * Gets the description field value.
     * @return The description field value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description field value.
     * @param description
     *            The description field value
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the filled field value.
     * @return The filled field value
     */
    public boolean getFilled() {
        return filled;
    }

    /**
     * Sets the filled field value.
     * @param filled
     *            The filled field value
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    /**
     * Gets the memberResourceId field value.
     * @return The memberResourceId field value
     */
    public long getMemberResourceId() {
        return memberResourceId;
    }

    /**
     * Sets the memberResourceId field value. Can be set to -1 to indicate the removal of the resource form the
     * position.
     * @param memberResourceId
     *            The memberResourceId field value
     * @throws IllegalArgumentException
     *             If memberResourceId is below -1
     */
    public void setMemberResourceId(long memberResourceId) {
        if (memberResourceId < -1) {
            // Check illegal parameter
            throw new IllegalArgumentException("memberResourceId cannot be below -1");
        }
        this.memberResourceId = memberResourceId;
    }

    /**
     * Gets the paymentPercentage field value.
     * @return The paymentPercentage field value
     */
    public int getPaymentPercentage() {
        return paymentPercentage;
    }

    /**
     * Sets the paymentPercentage field value.
     * @param paymentPercentage
     *            The paymentPercentage field value
     * @throws IllegalArgumentException
     *             If paymentPercentage is not between 0 and 100, inclusive
     */
    public void setPaymentPercentage(int paymentPercentage) {
        if (paymentPercentage < 0 || paymentPercentage > Helper.HUNDRED) {
            // Check illegal parameter
            throw new IllegalArgumentException("paymentPercentage must be between 0 and 100, inclusive");
        }
        this.paymentPercentage = paymentPercentage;
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
     * Gets the positionId field value.
     * @return The positionId field value
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * Sets the positionId field value.
     * @param positionId
     *            The positionId field value
     * @throws IllegalArgumentException
     *             If positionId is negative
     */
    public void setPositionId(long positionId) {
        Helper.assertNonNegative(positionId, "positionId");
        this.positionId = positionId;
    }

    /**
     * Gets the published field value.
     * @return The published field value
     */
    public boolean getPublished() {
        return published;
    }

    /**
     * Sets the published field value.
     * @param published
     *            The published field value
     */
    public void setPublished(boolean published) {
        this.published = published;
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
     * Gets a custom property from the customProperties map. Value will be null if key is not present.
     * @return A Serializable value of the property. it will be null if key is not present.
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
}
