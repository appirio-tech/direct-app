package com.topcoder.management.project;

import java.io.Serializable;

/**
 * This class represents a billing project configuration type from the
 * persistence. Each billing project configuration must associated with a
 * billing project configuration type. Billing project configuration type are
 * stored in 'client_billing_config_type_lu' table, billing project
 * configuration in 'client_billing_config' table. A Billing project
 * configuration type instance contains id, name and description. This class is
 * used in ProjectManager#getAllProjectPropertyTypes method to return project
 * property types from the persistence. This class implements Serializable
 * interface to support serialization.
 * 
 * <p>
 * Version 1.1 BUGR-8788 (TC Cockpit - New Client Billing Config Type) Changes note:
 *  <ul>
 *      <li>Add {@link #CCA_REQUIRED} configuration key for "CCA Required"</li>
 *  <ul>
 * </p>
 * 
 * @author hohosky
 * @version 1.1
 * 
 */
public class BillingProjectConfigType implements Serializable {

    /**
     * Represents the "Approval Required" billing project configuration key.
     */
    public static final String APPROVAL_REQUIRED_KEY = "Approval Required";

    /**
     * Represents the "Post-Mortem Required" billing project configuration key.
     */
    public static final String POST_MORTEM_REQUIRED_KEY = "Post-Mortem Required";

    /**
     * Represents the "Approval Required" billing project configuration.
     */
    public static final BillingProjectConfigType APPROVAL_REQUIRED = new BillingProjectConfigType(1, "Approval Required", "Approval Required");

    /**
     * Represents the "Post-Mortem Required" billing project configuration key.
     */
    public static final BillingProjectConfigType POST_MORTEM_REQUIRED = new BillingProjectConfigType(2, "Post-Mortem Required",  "Post-Mortem Required");

    /**
     * Represents the "Reliability Bonus Eligible" billing project configuration key.
     */
    public static final BillingProjectConfigType RELIABILITY_BONUS_ELIGIBLE = new BillingProjectConfigType(3, "Reliability Bonus Eligible",  "Reliability Bonus Eligible");

    /**
     * Represents the "Reliability Bonus Eligible" billing project configuration key.
     */
    public static final BillingProjectConfigType MEMBER_PAYMENT_ELIGIBLE = new BillingProjectConfigType(4, "Member Payments Eligible",  "Member Payments Eligible");

    /**
     * Represents the "Send Winner Emails" billing project configuration key.
     */
    public static final BillingProjectConfigType SEND_WINNER_EMAILS = new BillingProjectConfigType(5, "Send Winner Emails",  "Send Winner Emails");


    /**
     * Represents the "Spec Review Required" billing project configuration key.
     */
    public static final BillingProjectConfigType SPEC_REVIEW_REQUIRED = new BillingProjectConfigType(6, "Spec Review Required",  "Spec Review Required");


    /**
     * Represents the "Track Late Deliverables" billing project configuration key.
     */
    public static final BillingProjectConfigType TRACK_LATE_DELIVERABLES = new BillingProjectConfigType(7, "Track Late Deliverables",  "Track Late Deliverables");

    /**
     * Represents the "CCA Required" billing project configuration key.
     * 
     * @since 1.1
     */
    public static final BillingProjectConfigType CCA_REQUIRED = new BillingProjectConfigType(8, "CCA Required",  "CCA Required");

    


    /**
     * Represents the id of this instance. Only values greater than zero is
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private long id = 0;

    /**
     * Represents the name of this instance. Null or empty values are not
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private String name = null;

    /**
     * Represents the description of this instance. Null value is not allowed.
     * This variable is initialized in the constructor and can be accessed in
     * the corresponding getter/setter method.
     */
    private String description = null;

    /**
     * Create a new BillingProjectConfigType instance with the given id and
     * name. The two fields are required for an instance to be persisted.
     * 
     * @param id
     *            The billing project configuration type id.
     * @param name
     *            The billing project configuration type name.
     * @throws IllegalArgumentException
     *             If id is less than or equals to zero, or name is null or
     *             empty string.
     */
    public BillingProjectConfigType(long id, String name) {
	this(id, name, "");
    }

    /**
     * Create a new BillingProjectConfigType instance with the given id, name
     * and description. The first two fields are required for an instance to be
     * persisted.
     * 
     * @param id
     *            The billing project configuration type id.
     * @param name
     *            The billing project configuration type name.
     * @param description
     *            The billing project configuration type description.
     * @throws IllegalArgumentException
     *             If any id is less than or equals to zero, or name is null or
     *             empty string, or description is null.
     */
    public BillingProjectConfigType(long id, String name, String description) {
	setId(id);
	setName(name);
	setDescription(description);
    }

    public BillingProjectConfigType() {

    }

    /**
     * Sets the id for this billing project configuration type type instance.
     * Only positive values are allowed.
     * 
     * @param id
     *            The id of this billing project configuration type instance.
     * @throws IllegalArgumentException
     *             If billing project configuration type id is less than or
     *             equals to zero.
     */
    public void setId(long id) {
	Helper.checkNumberPositive(id, "id");
	this.id = id;
    }

    /**
     * Gets the id of this billing project configuration type instance.
     * 
     * @return the id of this billing project configuration type instance.
     */
    public long getId() {
	return id;
    }

    /**
     * Sets the name for this billing project configuration type instance. Null
     * or empty values are not allowed.
     * 
     * @param name
     *            The name of this billing project configuration type instance.
     * @throws IllegalArgumentException
     *             If billing project configuration type name is null or empty
     *             string.
     */
    public void setName(String name) {
	Helper.checkStringNotNullOrEmpty(name, "name");
	this.name = name;
    }

    /**
     * Gets the name of this billing project configuration type instance.
     * 
     * @return the name of this billing project configuration type instance.
     */
    public String getName() {
	return name;
    }

    /**
     * Sets the description for this billing project configuration type
     * instance. Null value are not allowed.
     * 
     * @param description
     *            The description of this billing project configuration type
     *            instance.
     * @throws IllegalArgumentException
     *             If billing project configuration type description is null.
     */
    public void setDescription(String description) {
	Helper.checkObjectNotNull(description, description);
	this.description = description;
    }

    /**
     * Gets the description of this billing project configuration type instance.
     * 
     * @return the description of this billing project configuration type
     *         instance.
     */
    public String getDescription() {
	return description;
    }

}
