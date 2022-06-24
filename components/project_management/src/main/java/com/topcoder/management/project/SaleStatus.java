/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;


/**
 * <p>
 * Represents the entity class for db table <i>sale_status_lu</i>.
 * </p>
 * 
 * <p>
 * Currently the three possible statuses are PAID, UNPAID and MARKED_FOR_PURCHASE.
 * </p>
 * 
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author PE
 * @version 1.0
 *
 * @since Module Contest Service Software Contest Sales Assembly
 */
@SuppressWarnings("serial")
public class SaleStatus implements Serializable {
    /** Represents the entity id. */
    private Long saleStatusId;

    /** Represents the description of Status. */
    private String description;

    /**
     * Default constructor.
     */
    public SaleStatus() {
        // empty
    }

    /**
     * Returns the saleStatusId.
     *
     * @return the saleStatusId.
     */
    public Long getSaleStatusId() {
        return saleStatusId;
    }

    /**
     * Updates the saleStatusId with the specified value.
     *
     * @param saleStatusId the saleStatusId to set.
     */
    public void setSaleStatusId(Long saleStatusId) {
        this.saleStatusId = saleStatusId;
    }

    /**
     * Returns the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description with the specified value.
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj the {@code Object} to compare to this one
     *
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SaleStatus) {
            if (getSaleStatusId() == null) {
                return (((SaleStatus) obj).getSaleStatusId() == null);
            }

            return getSaleStatusId().equals(((SaleStatus) obj).getSaleStatusId());
        }

        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's {@link #equals(Object)}}
     * method.
     *
     * @return a hash code for this {@code SaleStatus}
     */
    @Override
    public int hashCode() {
        return (saleStatusId == null) ? 0 : saleStatusId.hashCode();
    }
}
