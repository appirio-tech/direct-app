/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This is a PrizeType entity which represents the project prize types. Added in version 1.2.
 * </p>
 * <p>
 * <strong>Thread-Safety:</strong> This class is mutable and not thread safe. But it will be used as entity so it'll not
 * cause any thread safe problem.
 * </p>
 *
 * @author flytoj2ee, TCSDEVELOPER
 * @version 1.2
 * @since 1.2
 */
@SuppressWarnings("serial")
public class PrizeType implements Serializable {

    public static final PrizeType CHECKPOINT_PRIZE = new PrizeType(14, "Checkpoint Prize");
    public static final PrizeType CONTEST_PRIZE = new PrizeType(15, "Contest Prize");


    /**
     * Represents the id of PrizeType. The default value is 0. It's changeable. It should be positive value.
     * It's accessed in setter and getter.
     */
    private long id;

    /**
     * Represents the description of PrizeType. The default value is null. It's changeable. It could not be null or
     * empty. It's accessed in setter and getter.
     */
    private String description;

    /**
     * Empty constructor.
     */
    public PrizeType() {
    }


    /**
     * constructor.
     */
    public PrizeType(long id, String desc) {
        setId(id);
        setDescription(desc);
    }

    /**
     * Returns the value of id attribute.
     *
     * @return the value of id.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the given value to id attribute.
     *
     * @param id
     *            the given value to set.
     * @throws IllegalArgumentException
     *             if the id parameter is non-positive
     */
    public void setId(long id) {
        Helper.checkNumberPositive(id, "id");
        this.id = id;
    }

    /**
     * Returns the value of description attribute.
     *
     * @return the value of description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the given value to description attribute.
     *
     * @param description
     *            the given value to set.
     * @throws IllegalArgumentException
     *             if given parameter is null or empty.
     */
    public void setDescription(String description) {
        Helper.checkStringNotNullOrEmpty(description, "description");
        this.description = description;
    }
}
