/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.io.Serializable;

/**
 * <p>
 * This abstract class is a base class for all deliverable structures that have long integer identifier. It is a simple
 * JavaBean that provides getter and setter for private ID field.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.2
 * @since 1.2
 */
public abstract class IdentifiableDeliverableStructure implements Serializable {
    /**
     * <p>
     * The value that the id field will have (and that the getId method will return) when the id field has not been set
     * in the constructor or through the setId method.
     * </p>
     */
    public static final long UNSET_ID = -1;

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 4151954659598955210L;


    /**
     * <p>
     * The id of the deliverable structure. This field is set in the constructor and is mutable. The value must always
     * be greater than 0 or UNSET_ID. The default value is UNSET_ID.
     * </p>
     * <p>
     * Once set to a value besides UNSET_ID, the id can not be set to another value. This allows the class (subclasses
     * actually, since this class is abstract) to have a Java Bean API but still have an essentially unchangeable id.
     * </p>
     */
    private long id;

    /**
     * Creates an <code>IdentifiableDeliverableStructure</code> instance.
     */
    protected IdentifiableDeliverableStructure() {
        id = UNSET_ID;
    }

    /**
     * Creates an <code>IdentifiableDeliverableStructure</code> instance with the given id.
     *
     * @param id
     *            The id of the structure
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    protected IdentifiableDeliverableStructure(long id) {
        DeliverableHelper.checkGreaterThanZero(id, "id", null);
        this.id = id;
    }

    /**
     * Sets the id of the structure. The setId method only allows the id to be set once (if it was not set in the
     * constructor). After that the id value is locked in.
     *
     * @param id
     *            The id for the structure
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws IdAlreadySetException
     *             If the id has already been set (i.e. the id field is not UNSET_ID)
     */
    public void setId(long id) {
        DeliverableHelper.checkGreaterThanZero(id, "id", null);

        if (this.id == UNSET_ID) {
            // id has not been set yet.
            this.id = id;
        } else {
            // id has been locked.
            throw new IdAlreadySetException("id has already been set.");
        }
    }

    /**
     * Gets the id of the structure. The return is either UNSET_ID or greater than 0.
     *
     * @return The id of the structure
     */
    public long getId() {
        return id;
    }
}
