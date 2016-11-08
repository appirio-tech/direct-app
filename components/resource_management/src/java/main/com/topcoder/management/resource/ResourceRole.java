/*
 * Copyright (C) 2007 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;


/**
 * <p>
 * The ResourceRole class is the second modeling class in this component. It
 * represents a type of resource and is used to tag instances of the Resource
 * class as playing a certain role. The ResourceRole class is simply a container
 * for a few basic data fields. All data fields in this class are mutable and
 * have get and set methods.
 * </p>
 *
 * <p>
 * This class is mutable because its base class is mutable. Hence it is not
 * thread-safe.
 * </p>
 *
 * <p>
 * Version 1.1 TC Direct Replatforming Release 3 Changes:
 * - Added {@link #RESOURCE_ROLE_CHECKPOINT_REVIEWER_ID} constant.
 * </p>
 *
 * <p>
 * Version 1.2 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #RESOURCE_ROLE_FINAL_REVIEWER_ID} constant.</li>
 *     <li>Added {@link #RESOURCE_ROLE_FINAL_REVIEWER_NAME} constant.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (First2Finish - TC Cockpit Auto Assign Reviewer Update)
 * <ul>
 *     <li>Added {@link #RESOURCE_ROLE_ITERATIVE_REVIEWER_ID}</li>
 * </ul>
 * </p>
 *
 * @author aubergineanode, kinfkong, isv, GreatKevin
 * @version 1.3
 */
public class ResourceRole extends AuditableResourceStructure {

    /**
     * <p>
     * The value that the id field will have (and that the getId
     * method will return) when the id field has not been set in the constructor
     * or through the setId method.
     * </p>
     *
     * This field is public, static, and final.
     */
    public static final long UNSET_ID = -1;


    /**
     * Private constant specifying resource role manager id
     *
     */
    public static final long RESOURCE_ROLE_MANAGER_ID = 13;


    /**
     * Private constant specifying resource role manager name
     *
     */
    public static final String RESOURCE_ROLE_MANAGER_NAME = "Manger";

    /**
     * Private constant specifying resource role manager desc
     *
     */
    public static final String RESOURCE_ROLE_MANAGER_DESC = "Manger";


    /**
     * Private constant specifying resource role observer id
     *
     */
    public static final long RESOURCE_ROLE_OBSERVER_ID = 12;

    /**
     * Private constant specifying resource role manager name
     *
     */
    public static final String RESOURCE_ROLE_OBSERVER_NAME = "Observer";

    /**
     * Private constant specifying resource role manager desc
     *
     */
    public static final String RESOURCE_ROLE_OBSERVER_DESC = "Observer";

    /**
     * Private constant specifying resource role observer id
     *
     */
    public static final long RESOURCE_ROLE_COPILOT_ID = 14;

    /**
     * Private constant specifying resource role observer id
     *
     */
    public static final long RESOURCE_ROLE_CLIENT_MANAGER_ID = 15;

    /**
     * Private constant specifying resource role manager name
     *
     */
    public static final String RESOURCE_ROLE_CLIENT_MANAGER_NAME = "Client Manager";

    /**
     * Private constant specifying resource role manager desc
     *
     */
    public static final String RESOURCE_ROLE_CLIENT_MANAGER_DESC = "Client Manager";

    /**
     * Private constant specifying resource role reviewer id
     *
     */
    public static final long RESOURCE_ROLE_REVIEWER_ID = 4;

    /**
     * Private constant specifying resource role Reviewer name
     *
     */
    public static final String RESOURCE_ROLE_REVIEWER_NAME = "Reviewer";

    /**
     * Private constant specifying resource role Checkpoint Reviewer id.
     * 
     * @since 1.1
     */
    public static final long RESOURCE_ROLE_CHECKPOINT_REVIEWER_ID = 20L;

    /**
     * Private constant specifying resource role Iterative Reviewer id.
     *
     * @since 1.3
     */
    public static final long RESOURCE_ROLE_ITERATIVE_REVIEWER_ID = 21L;

     /**
     * Private constant specifying resource role primary screener id
     *
     */
    public static final long RESOURCE_ROLE_PRIMARY_SCREENER_ID = 2;


    /**
     * Private constant specifying resource role Primary Screener name
     *
     */
    public static final String RESOURCE_ROLE_PRIMARY_SCREENER_NAME = "Primary Screener";
	
	/**
     * Private constant specifying resource role Approver id
     *
     */
    public static final long RESOURCE_ROLE_APPROVER_ID = 10;


    /**
     * Private constant specifying resource role Approver name
     *
     */
    public static final String RESOURCE_ROLE_APPROVER_NAME = "Approver";


    /**
     * <p>A <code>long</code> providing the ID for <code>Final Reviewer</code> role.</p>
     * 
     * @since 1.2
     */
    public static final long RESOURCE_ROLE_FINAL_REVIEWER_ID = 9;


    /**
     * <p>A <code>String</code> providing the name for <code>Final Reviewer</code> role.</p>
     * 
     * @since 1.2
     */
    public static final String RESOURCE_ROLE_FINAL_REVIEWER_NAME = "Final Reviewer";


    /**
     * <code>resource role id</code> for submitter
     * @since 1.4
     */
    public static final long RESOURCE_ROLE_SUBMITTER = 1L;

    /**
     * <p>
     * The id of the ResourceRole. This field is set in the constructor and
     * is mutable.
     * </p>
     *
     * <p>
     * The value must always be &gt; 0 or UNSET_ID. The default value is
     * UNSET_ID. Once set to a value besides UNSET_ID, the id can not be set to
     * another value. This allows the class to have a Java Bean API but still
     * have an essentially unchangeable id. This field is set in the constructor
     * and setId method and is retrieved through the getId method.
     * </p>
     *
     */
    private long id;

    /**
     * <p>
     * The name of the ResourceRole. This field is set in the constructor
     * and is mutable.
     * </p>
     *
     * <p>
     * Any value is allowed for this field. It can be null or
     * non-null, and any value is allowed when non-null (empty string, all
     * whitespace, etc). The default value of this field is null, which
     * indicates that the name has not yet been set (or has been unset). This
     * field is set in the constructor and setName method and is retrieved
     * through the getName method.
     * </p>
     */
    private String name;

    /**
     * <p>
     * The description of the ResourceRole. This field is set in the constructor and is mutable.
     * </p>
     *
     * <p>
     * Any value is allowed for this field. It can be null or non-null, and any value is
     * allowed when non-null (empty string, all whitespace, etc). The default value of this
     * field is null, which indicates that the name has not yet been set (or has been unset).
     * This field is set in the constructor and setDescription method and is
     * retrieved through the getDescription method.
     * </p>
     */
    private String description = null;

    /**
     * <p>
     * The identifier of the phase type for this ResourceRole. The
     * value can be null or non-null.
     * </p>
     *
     * <p>
     * This field is mutable and its default
     * value is null. Null indicates that no phase is associated with the
     * resource. When non-null, the value of this field will be &gt; 0. This field
     * is set in the setPhaseType method and retrieved through the getPhaseType method.
     * </p>
     */
    private Long phaseType;

    /**
     * <p>
     * Creates a new ResourceRole, initializing all fields to the default values.
     * </p>
     */
    public ResourceRole() {
        // set to default
        this.id = UNSET_ID;
        this.name = null;
        this.phaseType = null;
    }

    /**
     * <p>
     * Creates a new ResourceRole. The name and phaseType fields are initialized to the default value.
     * </p>
     * @param id The id for the resource role
     */
    public ResourceRole(long id) {
        this.id = id;
        // set to default
        this.name = null;
        this.phaseType = null;
    }

    /**
     * <p>
     * Creates a new ResourceRole, initializing all fields to the default values.
     * </p>
     *
     * @param id The id for the resource role
     * @param name The name for the resource role
     * @param phaseType The identifier of the phase type
     */
    public ResourceRole(long id, String name, long phaseType) {
        // set the values
        this.id = id;
        this.name = name;
        this.phaseType = new Long(phaseType);
    }

    /**
     * <p>
     * Sets the id of the resource role. The setId method only allows the
     * id to be set once. After that the id value is locked in and can not be
     * changed.
     * </p>
     *
     * @param id The id for the resource role
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the id of the resource role. The return is either UNSET_ID or &gt 0.
     * </p>
     *
     * @return The id of the resource role
     */
    public long getId() {
        return this.id;
    }

    /**
     * <p>
     * Sets the name of the resource role. Any value, null or non-null
     * is allowed
     * </p>
     *
     * @param name The name for the resource role.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Gets the name of the resource role. This method may return null
     * or any other string value.
     * </p>
     *
     * @return The name of the resource role
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * Sets the description of the resource role. Any value,
     * null or non-null is allowed
     * </p>
     *
     * @param description The description for the resource role.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Gets the description of the resource role. This method
     * may return null or any other string value.
     * </p>
     *
     * @return The description of the resource role
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Sets the phase type identifier for the ResourceRole.
     * </p>
     *
     * @param phaseType The identifier of the phase type, can be null
     */
    public void setPhaseType(Long phaseType) {
        this.phaseType = phaseType;
    }

    /**
     * <p>
     * Gets the phase type identifier for the ResourceRole. The
     * value will either be &gt; 0 or null.
     * </p>
     *
     * @return The phase type identifier
     */
    public Long getPhaseType() {
        return this.phaseType;
    }
}
