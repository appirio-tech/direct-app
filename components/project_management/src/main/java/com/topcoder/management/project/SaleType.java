/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;


/**
 * <p>
 * Represents the entity class for db table <i>sale_type_lu</i>.
 * </p>
 * 
 * <p>
 * Currently supported types are: Paypal and TC Purchase order.
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
public class SaleType implements Serializable {
    /** Represents the sale type id. */
    private Long saleTypeId;

    /** Represents the description of sale type. */
    private String description;

    /**
     * Default constructor.
     */
    public SaleType() {
        // empty
    }

    /**
     * Returns the saleType.
     *
     * @return the saleType.
     */
    public Long getSaleTypeId() {
        return saleTypeId;
    }

    /**
     * Updates the saleType with the specified value.
     *
     * @param saleType the saleType to set.
     */
    public void setSaleTypeId(Long saleType) {
        this.saleTypeId = saleType;
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
        if (obj instanceof SaleType) {
            if (getSaleTypeId() == null) {
                return (((SaleType) obj).getSaleTypeId() == null);
            }

            return getSaleTypeId().equals(((SaleType) obj).getSaleTypeId());
        }

        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's {@link #equals(Object)}
     * method.
     *
     * @return a hash code for this {@code SaleType}
     */
    @Override
    public int hashCode() {
        return (saleTypeId == null) ? 0 : saleTypeId.hashCode();
    }
}
