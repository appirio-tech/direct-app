/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;


/**
 * <p>
 * The NotificationType class represents a type of notification. It is orthogonal to the Resource and
 * ResourceRole classes. This class is simply a container for a few basic data
 * fields. All data fields in this class are mutable and have get and set
 * methods.
 * </p>
 *
 * <p>
 * This class is mutable because its base class is mutable. Hence it is not
 * thread-safe.
 * </p>
 *
 * @author aubergineanode, kinfkong
 * @version 1.0
 */
public class NotificationType extends AuditableResourceStructure {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;


    /**
     * <p>
     * The value that the id field will have (and that the getId method will return) when the
     * id field has not been set in the constructor or through the setId method.
     * </p>
     *
     * This field is public, static, and final.
     */
    public static final long UNSET_ID = -1;

    public static final long TIMELINE_NOTIFICATION = 1;

    /**
     * <p>
     * The id of the NotificationType. This field is set in the constructor
     * and is mutable.
     * </p>
     *
     * <p>
     * The value must always be &gt; 0 or UNSET_ID. The default value is UNSET_ID.
     * Once set to a value besides UNSET_ID, the id can not be set to another value.
     * This allows the class to have a Java Bean API but still have an essentially
     * unchangeable id. This field is set in the constructor and setId method and is
     * retrieved through the getId method.
     * </p>
     *
     */
    private long id;

    /**
     * <p>
     * The name of the NotificationType. This field is set in the constructor and is mutable.
     * Any value is allowed for this field.
     * </p>
     *
     * <p>
     * It can be null or non-null, and any value is allowed when non-null (empty
     * string, all whitespace, etc). The default value of this field is null,
     * which indicates that the name has not yet been set (or has been unset).
     * This field is set in the constructor and the setName method and is
     * retrieved through the getName method.
     * </p>
     *
     */
    private String name;

    /**
     * <p>
     * The description of the NotificationType. This field is set in the constructor and is
     * mutable. Any value is allowed for this field.
     * </p>
     *
     * <p>
     * It can be null or non-null, and any value is allowed when non-null (empty
     * string, all whitespace, etc). The default value of this field is null,
     * which indicates that the name has not yet been set (or has been unset).
     * This field is set in the constructor and setDescription method and is
     * retrieved through the getDescription method.
     * </p>
     *
     */
    private String description = null;

    /**
     * <p>
     * Creates a new NotificationType, initializing all fields to the default values.
     * </p>
     *
     * <p>
     * That means:
     * this.id = UNSET_ID;
     * this.name = null;
     * </p>
     */
    public NotificationType() {
        // set the fields to default values
        this.id = UNSET_ID;
        this.name = null;
    }

    /**
     * <p>
     * Create a new NotificationType.  The name field  and description is initialized to the default value.
     * </p>
     *
     * @param id The id for the notification type
     *
     * @throws IllegalArgumentException If id is &lt;= 0
     */
    public NotificationType(long id) {
        Helper.checkLongPositive(id, "id");
        this.id = id;

        // set to default
        this.name = null;

    }

    /**
     * <p>
     * Creates a new NotificationType, initializing id and name fields to the given values.
     * The description field is set to default.
     * </p>
     *
     * @param id The id for the notification type
     * @param name The name for the notification type
     *
     * @throws IllegalArgumentException If id is <= 0 or the name is null
     */
    public NotificationType(long id, String name) {
        Helper.checkLongPositive(id, "id");
        Helper.checkNull(name, "name");

        this.id = id;
        this.name = name;
    }

    /**
     * <p>
     * Sets the id of the notification type. The setId method only allows
     * the id to be set once. After that the id value is locked in and can not
     * be changed.
     * </p>
     *
     * @param id The id for the notification type
     *
     * @throws IllegalArgumentException If id is &lt;= 0
     * @throws IdAlreadySetException If the id has already been set (i.e. the id field is not UNSET_ID)
     */
    public void setId(long id) {
        Helper.checkLongPositive(id, "id");
        if (this.id != UNSET_ID) {
            throw new IdAlreadySetException("The id has been set to be:" + id + " and cannot be re-set.");
        }
        this.id = id;
    }

    /**
     * <p>
     * Gets the id of the notification type. The return is either
     * UNSET_ID_VALUE or &gt; 0.
     * </p>
     *
     * @return The id of the notification type
     */
    public long getId() {
        return this.id;
    }

    /**
     * <p>
     * Sets the description of the notification type. Any value,
     * null or non-null is allowed.
     * </p>
     *
     * @param description The description for the notification type
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Gets the description of the notification type. This
     * method may return null or any other string value.
     * </p>
     *
     * @return The description of the notification type
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Sets the name of the notification type. Any value, null or
     * non-null is allowed.
     * </p>
     *
     * @param name The name for the notification type.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Gets the name of the notification type. This method may return
     * null or any other string value.
     * </p>
     *
     * @return The name of the notification type
     */
    public String getName() {
        return this.name;
    }
}
