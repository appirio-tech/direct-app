/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * The Resource class is the main modeling class in this component. It
 * represents any arbitrary resource. The Resource class is simply a container
 * for a few basic data fields. All data fields in this class are mutable and
 * have get and set methods.
 * </p>
 *
 * <p>
 * This class is mutable because its base class is mutable. Hence it is not
 * thread-safe.
 * </p>
 *
 * <p>Changes is version 1.1: Multiple submissions can be associated with a resource.
 * The submission field variable is a Set and it can be read and modified using the
 * provided utility methods.</p>
 *
 * @author aubergineanode, kinfkong, Rica, Xuchen
 * @version 1.1
 * @since 1.0
 */
public class Resource extends AuditableResourceStructure {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * The value that the id field will have (and that the getId method will return)
     * when the id field has not been set in the constructor or through the setId method.
     * </p>
     *
     * This field is public, static, and final.
     *
     */
    public static final long UNSET_ID = -1;

    /**
     * <p>
     * The id of the Resource.  This field is set in the constructor and is
     * mutable.
     * </p>
     *
     * <p>
     * The value must always be &gt; 0 or UNSET_ID. The default value is
     * UNSET_ID. Once set to a value besides UNSET_ID, the id can not be set to
     * another value. This allows the class to have a Java Bean API but still
     * have an essentially unchangeable id. This field is set in the constructor
     * and setId method and is retrieved through the getId method.
     * </p>
     */
    private long id = UNSET_ID;

    /**
     * <p>
     * The role of the resource, as given by a ResourceRole instance.
     * This field is set in the constructor and is mutable.
     * </p>
     *
     * <p>
     * The value can be null or non-null. The default value is null, which indicates that
     * the role has not been set. This field is set in the setResourceRole method and
     * retrieved through the getResourceRole method.
     * </p>
     */
    private ResourceRole resourceRole;

    /**
     * <p>
     * The identifier of the project that the resource belongs to. The
     * value can be null or non-null.
     * </p>
     *
     * <p>
     * This field is mutable and its default is null. Null indicates that the
     * resource is not associated with a project. When non-null, the value of
     * this field will be &gt; 0.
     * This field is set in the setProject method and retrieved through the
     * getProject method.
     * </p>
     */
    private Long project = null;

    /**
     * <p>
     * The phase that the resource is associated with. The value can be
     * null or non-null.
     * </p>
     *
     * <p>
     * This field is mutable and its default value is null.
     * Null indicates that no phase is associated with the resource. When
     * non-null, the value of this field will be &gt; 0. This field is set in the
     * setPhase method and retrieved through the getPhase method.
     * </p>
     *
     */
    private Long phase = null;

    /**
     * <p>A set of submissions that the resource is associated with. It is initialized
     * with an empty Set and never changed afterwards. The contents can be changed.</p>
     *
     * <p>If no submissions are set yet, the submissions Set should be empty. Null values
     * and duplicates cannot be added to the submissions Set. Elements having the value
     * &lt;=0 cannot be added to the set.</p>
     *
     * <p>This field is added in version 1.1.</p>
     * 
     * Updated the type to generics for Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private final Set<Long> submissions = new HashSet<Long>();

    /**
     * <p>
     * The map of properties that are associated with this resource.
     * The keys of the map are all required to be non-null String instances (no
     * other condition is enforced).
     * </p>
     *
     * <p>
     * This comment is obsolete now --
     * The values in the map can be of any type.
     * However, persistence mechanisms are only required to support saving the
     * toString() string of each value. This field is immutable, but its
     * contents can be manipulated through the setProperty method. The values in
     * this map can be retrieved through the getProperty and getAllProperties
     * methods.
     * </p>
     * 
     * Updated for Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     * Now the value will be of type String only, as persistence is supporting only String values to be saved.
     * For web-services also only concrete types can be transferred.
     */
    private Map<String, String> properties = new HashMap<String, String>();

    /**
     * <p>
     * The user name of such a person if the the resource identifies a person
     * (architect, reviewer, salesperson, manager, copilot,...).
     * </p>
     * Added for the Pipeline Conversion Assembly v1.0.
     */
    private String name;

    /**
     * <p>
     * The id of the user associated with the resource. The value can be null or non-null.
     * </p>
     *
     * <p>
     * This field is mutable and its default is null. Null indicates that the
     * resource is not associated with a user. When non-null, the value of
     * this field will be &gt; 0.
     * This field is set in the setUserId method and retrieved through the getUserId method.
     * </p>
     */
    private Long userId = null;

	/**
     * <p>
     * Creates a new Resource, initializing all constructor set fields to the default values.
     * </p>
     */
    public Resource() {
        // empty
    }

    /**
     * <p>
     * Creates a new Resource, setting the id field to the given value and the other
     * field is set to the default.
     * </p>
     *
     * @param id The id of the resource
     * @throws IllegalArgumentException If id is &lt;= 0
     */
    public Resource(long id) {
        setId(id);
    }

    /**
     * <p>
     * Creates a new Resource, setting all fields to the given values.
     * </p>
     *
     * @param id The id of the resource
     * @param resourceRole The role of the resource
     */
    public Resource(long id, ResourceRole resourceRole) {
        this.resourceRole = resourceRole;
        setId(id);
    }

    /**
     * <p>
     * Sets the id of the resource. The setId method only allows the id
     * to be set once. After that the id value is locked in and can not be
     * changed.
     * </p>
     *
     * @param id The id for the resource
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the id of the resource. The return is either UNSET_ID  or &gt; 0.
     * </p>
     *
     * @return The id of the resource
     */
    public long getId() {
        return this.id;
    }

    /**
     * <p>
     * Sets the role of the resource. The argument value can be
     * null or non-null.
     * </p>
     *
     * @param resourceRole The role of the resource
     */
    public void setResourceRole(ResourceRole resourceRole) {
        this.resourceRole = resourceRole;
    }

    /**
     * <p>
     * Gets the role of the resource. The return can be null or
     * non-null.
     * </p>
     *
     * @return The role of the resource.
     */
    public ResourceRole getResourceRole() {
        return this.resourceRole;
    }

    /**
     * <p>
     * Sets the project that the resource is associated with. The
     * value can be null or non-null. If non-null, it must be &gt; 0.
     * </p>
     *
     * @param project The identifier of the project the resource is associate with (or null)
     */
    public void setProject(Long project) {
        this.project = project;
    }

    /**
     * <p>
     * Gets the project that the resource is associated with. The
     * return will either be null or &gt 0.
     * </p>
     *
     * @return The identifier of the project the resource is associated with
     */
    public Long getProject() {
        return this.project;
    }

    /**
     * <p>
     * Sets the phase that the resource is associated with. The value
     * can be null or non-null. If non-null, it must be &gt; 0.
     * </p>
     *
     * @param phase The identifier of the phase the resource is associated with (or null)
     */
    public void setPhase(Long phase) {
        this.phase = phase;
    }

    /**
     * <p>
     * Gets the identifier of the phase that the resource is
     * associated with. The return will either be null or &gt; 0.
     * </p>
     *
     * @return The identifier of the phase the resource is associated with
     */
    public Long getPhase() {
        return this.phase;
    }

    /**
     * <p>Sets the submissions that the resource is associated with.</p>
     *
     * <p>In version 1.1, this method is set with an array of submissions instead of a single one.</p>
     *
     * @param submissions the array ids of submissions to be set
     *
     * @throws IllegalArgumentException if the submissions array is null, or any of the submissions entries
     * has value &lt;= 0, or any of the submissions entries is null
     *
     * @since 1.1
     */
    public void setSubmissions(Long[] submissions) {
        

        // clear original submissions set first
        this.submissions.clear();

        // add each one to set
        for (int i = 0; i < submissions.length; i++) {
            Helper.checkLongPositive(submissions[i], "element of submissions");

            this.submissions.add(submissions[i]);
        }
    }

    /**
     * <p>Returns the submissions set formatted as an array of Long objects. If no submissions are set yet an
     * empty array will be returned.</p>
     *
     * <p>In version 1.1, it retrieves an array of submissions instead of a single one.</p>
     *
     * @return the array ids of associated submissions
     * @since 1.1
     */
    public Long[] getSubmissions() {
        return (Long[]) submissions.toArray(new Long[submissions.size()]);
    }

    /**
     * <p>Adds a submission id to be associated with this resource.</p>
     *
     * @param submission the id of the submission to be added
     * @throws IllegalArgumentException if the argument is null, or given submission is a wrapper of a value &lt;= 0
     * @since 1.1
     */
    public void addSubmission(Long submission) {
        

        submissions.add(submission);
    }

    /**
     * <p>Removes the submission id from the submissions set associated with this resource.</p>
     *
     * @param submission the submission id to be removed
     * @throws IllegalArgumentException if the argument is null, or given submission is a wrapper of a value &lt;= 0
     * @since 1.1
     */
    public void removeSubmission(Long submission) {
        
        submissions.remove(submission);
    }

    /**
     * <p>Checks whether given submission id is associated with this resource.</p>
     *
     * @param submission the submission id to check
     * @return true if given submission id is associated with this resource; false otherwise
     * @throws IllegalArgumentException if the argument is null, or given submission is a wrapper of a value &lt;= 0
     * @since 1.1
     */
    public boolean containsSubmission(Long submission) {
        

        return submissions.contains(submission);
    }

    /**
     * <p>Checks whether there is some submission associated with this resource.</p>
     *
     * @return true if there is some submission associated with this resource; false otherwise
     * @since 1.1
     */
    public boolean hasSubmissions() {
        return !submissions.isEmpty();
    }

    /**
     * <p>Returns the number of submissions associated with this resource.</p>
     *
     * @return the number of submissions associated with this resource.
     * @since 1.1
     */
    public int countSubmissions() {
        return submissions.size();
    }

    /**
     * <p>Removes all submission ids associated with this resource.</p>
     * @since 1.1
     */
    public void clearSubmissions() {
        submissions.clear();
    }

    /**
     * <p>
     * Sets a property associated with the resource. If there is
     * already a value associated with the name, it will be replaced.
     * </p>
     *
     * @param name The name of the property to set.
     * @param value The property value to associate with the name.
     *
     * @throws IllegalArgumentException If name is null.
     */
    public void setProperty(String name, String value) {
        Helper.checkNull(name, "name");
        // set it in the properties
        this.properties.put(name, value);
        if (value == null) {
            this.properties.remove(name);
        } else {
            this.properties.put(name, value);
        }
    }

    /**
     * <p>
     * Tells if a property has been set for this Resource. Returns
     * true if there is an entry in the properties map for name.
     * </p>
     *
     * @param name The name of the property
     *
     * @return True if there is a value associated with the property.
     *
     * @throws IllegalArgumentException If name is null.
     */
    public boolean hasProperty(String name) {
        Helper.checkNull(name, "name");
        // check if the name exists
        return this.properties.containsKey(name);
    }

    /**
     * <p>
     * Gets the property value for the given name. Return the entry
     * in the properties map for name.
     * </p>
     *
     * @param name The name of the property.
     *
     * @return The value associated with the property name. Null if no value is associated with the property.
     *
     * @throws IllegalArgumentException If name is null.
     */
    public String getProperty(String name) {
        Helper.checkNull(name, "name");
        // find it in the properties
        return this.properties.get(name);
    }

    /**
     * <p>
     * Gets all the properties that are associated with the
     * Resource. The returned map has all non-null String keys. The values can
     * be any type, but none of them will be null.
     * </p>
     *
     * @return The map of properties associated with the resource.
     */
    public Map<String, String> getAllProperties() {
        // we need not to remove the null values or null keys explicitly, because
        // the properties is only accessed by the method: setProperties, and
        // the null key and null value is not allowed in that method.

        // copy the properties, since map is mutable
        return new HashMap<String, String>(this.properties);
    }

    /**
     * <p>
     * Gets the properties for this resource.
     * </p>
     * 
     * @return the properties
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public Map<String, String> getProperties() {
        return this.properties;
    }

    /**
     * <p>
     * Sets the properties for this resource.
     * </p>
     * 
     * @param properties the properties to set
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
    /**
	 * Returns the value of name.
	 * @return the name of resource
	 * @since Pipeline Conversion Assembly v1.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the value to  name field.
	 * @param name the name of resource
	 * @param Pipeline Conversion Assembly v1.0
	 */
	public void setName(String name) {
		this.name = name;
	}

     /**
     * <p>
     * Sets the user ID that the resource is associated with. The
     * value can be null or non-null. If non-null, it must be &gt; 0.
     * </p>
     *
     * @param userId The identifier of the user the resource is associate with (or null)
     *
     * @throws IllegalArgumentException If project is a wrapper of a value &lt= 0
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * <p>
     * Gets the user ID that the resource is associated with. The return will either be null or &gt 0.
     * </p>
     *
     * @return The identifier of the user the resource is associated with
     */
    public Long getUserId() {
        return this.userId;
    }
}
