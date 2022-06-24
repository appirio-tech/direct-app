/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.search;

import com.topcoder.management.resource.Helper;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;

/**
 * <p>
 * The ResourceRoleFilterBuilder class supports building filters for searching
 * for ResourceRoles.
 * </p>
 *
 * <p>
 * This class consists of 2 parts. The first part consists of
 * static Strings that name the fields that are available for searching. All
 * ResourceManager implementations should use SearchBundles that are configured
 * to use these names. The second part of this class consists of convenience
 * methods to create common filters based on these field names.
 * </p>
 *
 * <p>
 * This class has only final static fields/methods, so mutability is not an
 * issue. This class is thread-safe.
 * </p>
 *
 * @author aubergineanode, kinfkong
 * @version 1.0
 */
public class ResourceRoleFilterBuilder {

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the resource
     * role id.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createResourceRoleIdFilter method.
     * </p>
     *
     */
    public static final String RESOURCE_ROLE_ID_FIELD_NAME = "resource_role_id";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters
     * can be built in order to filter on the resource role name.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createNameFilter method.
     * </p>
     */
    public static final String NAME_FIELD_NAME = "name";

    /**
     * <p>
     * The name of the field under which SearchBuilder
     * Filters can be built in order to filter on the phase type id of the
     * resource role.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in
     * the createPhaseTypeIdFilter and createNoPhaseTypeFilter methods.
     * </p>
     */
    public static final String PHASE_TYPE_ID_FIELD_NAME = "phase_type_id";

    /**
     * <p>
     * Costructor of ResourceRoleFilterBuilder. It is set to be private prevent instantiation.
     * </p>
     */
    private ResourceRoleFilterBuilder() {
        // do nothing
    }

    /**
     * <p>
     * Creates a filter that selects resource roles which have the given id.
     * </p>
     *
     * @param resourceRoleId The resource role id to filter on
     *
     * @return A filter for selecting resource roles which have the given id
     *
     * @throws IllegalArgumentException If resourceRoleId is &lt;= 0
     */
    public static Filter createResourceRoleIdFilter(long resourceRoleId) {
        Helper.checkLongPositive(resourceRoleId, "resourceRoleId");
        return new EqualToFilter(RESOURCE_ROLE_ID_FIELD_NAME, new Long(resourceRoleId));
    }

    /**
     * <p>
     * Creates a filter that selects resource roles which have
     * the given name.
     * </p>
     * @param name The resource role name to filter on
     *
     * @return A filter for selecting resource roles which have the given name.
     *
     * @throws IllegalArgumentException If name is null
     */
    public static Filter createNameFilter(String name) {
        Helper.checkNull(name, "name");
        return new EqualToFilter(NAME_FIELD_NAME, name);
    }

    /**
     * <p>
     * Creates a filter that selects resource roles which are related to the given phase type.
     * </p>
     *
     * @param phaseTypeId The phase type id to create the filter with
     *
     * @return A filter for selecting resource roles associated with the given phase type id
     *
     * @throws IllegalArgumentException If phaseTypeId is &lt;= 0
     */
    public static Filter createPhaseTypeIdFilter(long phaseTypeId) {
        Helper.checkLongPositive(phaseTypeId, "phaseTypeId");
        return new EqualToFilter(PHASE_TYPE_ID_FIELD_NAME, new Long(phaseTypeId));
    }

    /**
     * <p>
     * Creates a filter that selects resources which do not have an associated phase.
     * </p>
     *
     * @return A filter for selecting ResourceRoles that are not associated with a phase type
     */
    public static Filter createNoPhaseTypeFilter() {
        return new NullFilter(PHASE_TYPE_ID_FIELD_NAME);
    }
}
