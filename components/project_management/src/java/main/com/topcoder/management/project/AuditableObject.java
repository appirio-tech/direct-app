/*
 * Copyright (C) 2006 - 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;
import java.util.Date;

/**
 * This abstract class contains properties for an auditable object such as
 * creation date, modification date, creation user, modification user. Project
 * class extends this class to inherit these properties. Future classes that
 * need audit properties also can extends from this.
 *
 * @author tuenm, iamajia, flytoj2ee, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class AuditableObject implements Serializable {
    /**
     * Represents the creation user of this instance. Null and empty values are
     * allowed. The value of this field is set by the persistence implementor,
     * not by component users. This variable can be accessed in the
     * corresponding getter/setter method.
     */
    private String creationUser = null;

    /**
     * Represents the modification user of this instance. Null and empty values
     * are allowed. The value of this field is set by the persistence
     * implementor, not by component users. This variable can be accessed in the
     * corresponding getter/setter method.
     */
    private String modificationUser = null;

    /**
     * Represents the creation time stamp of this instance. Null value is
     * allowed. The value of this field is set by the persistence implementor,
     * not by component users. This variable can be accessed in the
     * corresponding getter/setter method.
     */
    private Date creationTimestamp = null;

    /**
     * Represents the time stamp of this instance. Null value is allowed. The
     * value of this field is set by the persistence implementor, not by
     * component users. This variable can be accessed in the corresponding
     * getter/setter method.
     */
    private Date modificationTimestamp = null;

    /**
     * <p>
     * Create a new AuditableObject. This is an empty constructor.
     * </p>
     */
    protected AuditableObject() {
    }

    /**
     * Sets the creation user for this project instance. This method is not
     * supposed to set by the component user. It is used by persistence
     * implementors during loading the project instance from the persistence.
     *
     * @param creationUser
     *            The creation user of this project instance.
     * @throws IllegalArgumentException
     *            If creationUser is null or empty string.
     */
    public void setCreationUser(String creationUser) {
        Helper.checkStringNotNullOrEmpty(creationUser, "creationUser");
        this.creationUser = creationUser;
    }

    /**
     * Gets the creation user for this project instance. This method can be used
     * by component user to see who created the project. It is not used by
     * persistence implementors, they use 'operator' parameter instead.
     *
     * @return The creation user of this project instance.
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * Sets the creation time stamp for this project instance. This method is
     * not supposed to set by the component user. It is used by persistence
     * implementors during loading the project instance from the persistence.
     *
     * @param creationTimestamp
     *            The creation time stamp of this project instance.
     * @throws IllegalArgumentException
     *             If any parameter is null.
     */
    public void setCreationTimestamp(Date creationTimestamp) {
        Helper.checkObjectNotNull(creationTimestamp, "creationTimestamp");
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * Gets the creation time stamp for this project instance. This method can
     * be used by component user to see who created the project. It is not used
     * by persistence implementors, they use the system time when the project is
     * being updated instead.
     *
     * @return The creation time stamp of this project instance.
     */
    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * Sets the modification user for this project instance. This method is not
     * supposed to set by the component user. It is used by persistence
     * implementors during loading the project instance from the persistence.
     *
     * @param modificationUser
     *            The modification user of this project instance.
     * @throws IllegalArgumentException
     *            If modificationUser is null or empty string.
     */
    public void setModificationUser(String modificationUser) {
        Helper.checkStringNotNullOrEmpty(modificationUser, "modificationUser");
        this.modificationUser = modificationUser;
    }

    /**
     * Gets the modification user for this project instance. This method can be
     * used by component user to see who created the project. It is not used by
     * persistence implementors, they use 'operator' parameter instead.
     *
     * @return The modification user of this project instance.
     */
    public String getModificationUser() {
        return modificationUser;
    }

    /**
     * Sets the modification time stamp for this project instance. This method
     * is not supposed to set by the component user. It is used by persistence
     * implementors during loading the project instance from the persistence.
     *
     * @param modificationTimestamp
     *            The modification time stamp of this project instance.
     * @throws IllegalArgumentException
     *             If any parameter is null.
     */
    public void setModificationTimestamp(Date modificationTimestamp) {
        Helper.checkObjectNotNull(modificationTimestamp, "modificationTimestamp");
        this.modificationTimestamp = modificationTimestamp;
    }

    /**
     * Gets the modification time stamp for this project instance. This method
     * can be used by component user to see who created the project. It is not
     * used by persistence implementors, they use the system time when the
     * project is being updated instead.
     *
     * @return The modification time stamp of this project instance.
     */
    public Date getModificationTimestamp() {
        return modificationTimestamp;
    }
}
