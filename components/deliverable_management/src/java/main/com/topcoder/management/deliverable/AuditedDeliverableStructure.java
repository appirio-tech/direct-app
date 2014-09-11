/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * The AuditedDeliverableStructure is the base class for the modeling classes in this component. It holds the
 * information about when the structure was created and updated. This class simply holds the four data fields needed for
 * this auditing information (as well as the id of the item) and exposes both getters and setters for these fields.
 * </p>
 * <p>
 * The only thing to take note of when developing this class is that the setId method throws the IdAlreadySetException.
 * </p>
 * <p>
 * Changes in version 1.2:
 * <ul>
 * <li>ID property was extracted into IdentifiableDeliverableStructure that is now extended by this class.</li>
 * </ul>
 * </p>
 * <p>
 * Thread Safety: This class is highly mutable. All fields can be changed.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.2
 */
public abstract class AuditedDeliverableStructure extends IdentifiableDeliverableStructure {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -1702303867607453865L;

    /**
     * The name of the user that was responsible for creating the
     * AuditedDeliverableStructure. This field is mutable, and can be null or non-null. A null value
     * indicates that this field has not been set in the through the setCreationUser method. When
     * non-null, no value is considered invalid for this field - the user may be the empty string,
     * all whitespace, etc. Although most applications will probably not change the creation user
     * once it is set, this class does allow this field to be changed.
     */
    private String creationUser = null;

    /**
     * The date/time that the AuditedDeliverableStructure was created. This field
     * is mutable, and can be null or non-null. A null value indicates that this field has not been
     * through the setCreationTimestamp method. No value is considered invalid for this field.
     * Although most applications will probably not change the creation timestamp once it is set,
     * this class does allow this field to be changed. This field is set through the
     * setCreationTimestamp method and retrieved through the getCreationTimestamp method.
     */
    private Date creationTimestamp = null;

    /**
     * The name of the user that was responsible for the last modification to the
     * AuditedDeliverableStructure. This field is mutable, and can be null or non-null. A null value
     * indicates that this field has not been set through the setModificationUser method. When
     * non-null, no value is considered invalid for this field - the user may be the empty string,
     * all whitespace, etc. This field is set through the setModificationUser method and retrieved
     * through the getModificationUser method. This field is not automatically updated when changes
     * are made to this class.
     */
    private String modificationUser = null;

    /**
     * The date/time that the AuditedDeliverableStructure was last modified.
     * This field is initialized to null, is mutable, and can be null or non-null. A null value
     * indicates that this field has not been set through the setModificationTimestamp method. No
     * value is considered invalid for this field.
     */
    private Date modificationTimestamp = null;

    /**
     * Creates a new AuditedDeliverableStructure.
     */
    protected AuditedDeliverableStructure() {
    }

    /**
     * Creates a new AuditedDeliverableStructure.
     *
     * @param id The id of the structure
     * @throws IllegalArgumentException If id is <= 0
     */
    protected AuditedDeliverableStructure(long id) {
        super(id);
    }

    /**
     * Sets the user that is responsible for the creation of the AuditedDeliverableStructure. The
     * value can be null or non-null, and no value is considered invalid.
     *
     * @param creationUser The user that created the AuditedDeliverableStructure.
     */
    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    /**
     * Gets the user responsible for creating the AuditedDeliverableStructure. The return may be
     * null which indicates that the creation user has not been set.
     *
     * @return The user that created the AuditedDeliverableStructure
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * Sets the date/time at which the AuditedDeliverableStructure was created. This value may be
     * null or non-null and no value is considered invalid.
     *
     * @param creationTimestamp The date/time the AuditedDeliverableStructure was created.
     */
    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * Gets the date/time the AuditedDeliverableStructure was created. The return may be null,
     * indicating that the creation date/time has not been set.
     *
     * @return The date/time the AuditedDeliverableStructure was created.
     */
    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * Sets the user that is responsible for the last modification to the AuditedDeliverableStructure.
     * The value can be null or non-null, and no value is considered invalid.
     *
     * @param modificationUser The user responsible for the last AuditedDeliverableStructure
     *            modification, can be null
     */
    public void setModificationUser(String modificationUser) {
        this.modificationUser = modificationUser;
    }

    /**
     * Gets the user responsible for last modifying the AuditedDeliverableStructure. The return may
     * be null which indicates that the modification user has not been set.
     *
     * @return The user that last modified the AuditedDeliverableStructure
     */
    public String getModificationUser() {
        return modificationUser;
    }

    /**
     * Sets the date/time at which the AuditedDeliverableStructure was last modified. This value may
     * be null or non-null and no value is considered invalid.
     *
     * @param modificationTimestamp The date/time the AuditedDeliverableStructure was last modified
     */
    public void setModificationTimestamp(Date modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }

    /**
     * Gets the date/time the AuditedDeliverableStructure was last modified. The return may be null,
     * indicating that the modification date/time has not been set.
     *
     * @return The date/time the AuditedDeliverableStructure was last modified
     */
    public Date getModificationTimestamp() {
        return modificationTimestamp;
    }

    /**
     * Tells whether all the required fields of this structure have values set.
     *
     * @return True if all fields required for persistence are present
     */
    public boolean isValidToPersist() {
        return ((creationUser != null)
                    && (creationTimestamp != null)
                    && (modificationUser != null)
                    && (modificationTimestamp != null))
                    && (creationTimestamp.compareTo(modificationTimestamp) <= 0);
    }

}
