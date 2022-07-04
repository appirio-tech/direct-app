/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * The AuditableResourceStructure is the base class for the modeling classes in
 * this component. It holds the information about when the structure was created
 * and updated. This class simply holds the four data fields needed for this
 * auditing information and exposes both getters and setters for these fields.
 * </p>
 *
 * <p>
 * All the methods in this class do is get/set the underlying fields.
 * </p>
 *
 * <p>
 * This class is highly mutable, hence it is not thread safe. All fields can be
 * changed.
 * </p>
 *
 * @author aubergineanode, kinfkong
 * @version 1.0
 */
public abstract class AuditableResourceStructure implements Serializable {

    /**
     * <p>
     * creationUser: The name of the user that was responsible for creating the
     * resource structure. This field is mutable, and can be null or non-null.
     * </p>
     *
     * <p>
     * A null value indicates that this field has not been set in the through the
     * setCreationUser method.
     * </p>
     *
     * <p>
     * When non-null, no value is considered invalid for
     * this field - the user may be the empty string, all whitespace, etc.
     * </p>
     *
     * <p>
     * Although most applications will probably not change the creation user
     * once it is set, this class does allow this field to be changed. This
     * field is set through the setCreationUser method and retrieved through the
     * getCreationUser method.
     * </p>
     *
     * @serial
     */
    private String creationUser = null;

    /**
     * <p>
     * creationTimestamp: The date/time that the resource structure was created.
     * This field is mutable, and can be null or non-null.
     * </p>
     *
     * <p>
     * A null value indicates that this field has not been through the setCreationTimestamp
     * method.
     * </p>
     *
     * <p>
     * Null value is considered invalid for this field.
     * </p>
     *
     *  <p>
     * Although most applications will probably not change the creation timestamp once it is
     * set, this class does allow this field to be changed. This field is set
     * through the setCreationTimestamp method and retrieved through the
     * getCreationTimestamp method.
     * </p>
     *
     * @serialData
     */
    private Date creationTimestamp = null;

    /**
     * <p>
     * modificationUser: The name of the user that was responsible for the last
     * modification to the resource structure. This field is mutable, and can be
     * null or non-null.
     * </p>
     *
     * <p>
     * A null value indicates that this field has not been set
     * through the setModificationUser method.
     * </p>
     *
     * <p>
     * When non-null, no value is considered invalid for this field - the user may be the empty string, all
     * whitespace, etc.
     * </p>
     *
     *  <p>
     * This field is set through the setModificationUser method
     * and retrieved through the getModificationUser method. This field is not
     * automatically updated when changes are made to this class.
     * </p>
     *
     * @serial
     */
    private String modificationUser = null;

    /**
     * <p>
     * modificationTimestamp: The date/time that the resource structure was last
     * modified. This field is initialized to null, is mutable, and can be null
     * or non-null.
     * </p>
     *
     * <p>
     * A null value indicates that this field has not been set
     * through the setModificationTimestamp method. No value is considered
     * invalid for this field.
     * </p>
     *
     * <p>
     * This field is set through the setModificationTimestamp method and retrieved through the
     * getModificationTimestamp method. This field is not automatically updated
     * when any setModificationTimestamp method is called.
     * </p>
     *
     * @serialData
     */
    private Date modificationTimestamp = null;

    /**
     * <p>
     * Creates a new AuditableResourceStructure.
     * </p>
     *
     * <p>
     * This constructor does nothing.
     * </p>
     */
    protected AuditableResourceStructure() {
        // do nothing
    }

    /**
     * <p>
     * Sets the user that is responsible for the creation of the resource structure.
     * </p>
     *
     * <p>
     * The value can be null or non-null, and no value is considered invalid.
     * </p>
     *
     * @param creationUser The user that created the resource structure.
     */
    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    /**
     * <p>
     * Gets the user responsible for creating the resource
     * structure.
     * </p>
     *
     * <p>
     * The return may be null which indicates that the creation user has not been set.
     * </p>
     *
     * @return The user that created the resource structure
     */
    public String getCreationUser() {
        return this.creationUser;
    }

    /**
     * <p>
     * Sets the date/time at which the resource structure was created.
     * </p>
     *
     * <p>
     * This value may be null or non-null and no value is considered invalid.
     * </p>
     *
     * @param creationTimestamp The date/time the resource structure was created.
     */
    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * <p>
     * Gets the date/time the resource structure was
     * created.
     * </p>
     *
     * <p>
     * The return may be null, indicating that the creation date/time has not been set.
     * </p>
     *
     * @return The date/time the resource structure was created.
     */
    public Date getCreationTimestamp() {
        return this.creationTimestamp;
    }

    /**
     * <p>
     * Sets the user that is responsible for the last modification to the the resource structure.
     * </p>
     *
     * <p>
     * The value can be null or non-null, and no value is considered invalid.
     * </p>
     *
     * @param modificationUser The user responsible for the last resource structure modification, can be null
     */
    public void setModificationUser(String modificationUser) {
        this.modificationUser = modificationUser;
    }

    /**
     * <p>
     * Gets the user responsible for last modifying the resource structure.
     * </p>
     *
     * <p>
     * The return may be null which indicates that the modification user has not been set.
     * </p>
     *
     * @return The user that last modified the resource structure
     */
    public String getModificationUser() {
        return this.modificationUser;
    }

    /**
     * <p>
     * Sets the date/time at which the resource structure was last modified.
     * </p>
     *
     * <p>
     * This value may be null or non-null and no value is considered invalid.
     * </p>
     *
     * @param modificationTimestamp The date/time the resource structure was last modified
     */
    public void setModificationTimestamp(Date modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }

    /**
     * <p>
     * Gets the date/time the resource structure was last modified.
     * </p>
     *
     * <p>
     * The return may be null, indicating that the modification date/time has not been set.
     * </p>
     *
     * @return The date/time the resource structure was last modified
     */
    public Date getModificationTimestamp() {
        return this.modificationTimestamp;
    }
}
