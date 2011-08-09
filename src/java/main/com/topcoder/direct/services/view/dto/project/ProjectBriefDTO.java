/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import java.io.Serializable;

/**
 * <p>A DTO class providing the brief details on single project.</p>
 *
 * <p>Version 1.1 (Release Assembly - Cockpit Customer Right Sidebar and Active Contests Coordination version 1.0) change notes:
 * - Add properties customerId and customerName.
 * </p>
 *
 * @author isv, GreatKevin
 * @version 1.1
 */
public class ProjectBriefDTO implements Serializable {

    /**
     * <p>A <code>long</code> providing the project ID.</p>
     */
    private long id;

    /**
     * <p>A <code>String</code> providing the project name.</p>
     */
    private String name;

    /**
     * The customer id of the project.
     *
     * @since 1.1
     */
    private long customerId;

    /**
     * The customer name of the project.
     *
     * @since 1.1
     */
    private String customerName;

    /**
     * <p>Constructs new <code>ProjectBriefDTO</code> instance. This implementation does nothing.</p>
     */
    public ProjectBriefDTO() {
    }

    /**
     * <p>Gets the project ID.</p>
     *
     * @return a <code>long</code> providing the project ID.
     */
    public long getId() {
        return id;
    }

    /**
     * <p>Sets the project ID.</p>
     *
     * @param id a <code>long</code> providing the project ID.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>Gets the project name.</p>
     *
     * @return a <code>String</code> providing the project name.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets the project name.</p>
     *
     * @param name a <code>String</code> providing the project name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Gets the id of the customer</p>
     *
     * @return the id of the customer.
     * @since 1.1
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * <p>Sets the id of the customer</p>
     *
     * @param customerId the id of the customer
     * @since 1.1
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * <p>Gets the name of the customer</p>
     *
     * @return the name of the customer.
     * @since 1.1
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * <p>Sets the name of the customer</p>
     *
     * @param customerName the name of the customer.
     * @since 1.1
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
