/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dto;

import java.util.List;

import com.topcoder.direct.services.project.metadata.entities.HelperEntities;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 * Composite filter which implements Direct Project Filter interface.
 * This class support composite operator AND also OR.
 * </p>
 *
 * <p>
 *  Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author Standlove, CaDenza
 * @version 1.0
 */
public class CompositeFilter implements DirectProjectFilter {
    /**
     * The collection of direct project filter.
     */
    private List<DirectProjectFilter> projectFilters;

    /**
     * The supported composite operator AND also OR.
     */
    private CompositeOperator compositeOperator;

    /**
     * Default constructor.
     */
    public CompositeFilter() {
        // Do nothing.
    }

    /**
     * Retrieves collection of direct project filter.
     *
     * @return collection of direct project filter.
     */
    public List<DirectProjectFilter> getProjectFilters() {
        return projectFilters;
    }

    /**
     * Setter for collection direct project filter.
     *
     * @param projectFilters the collection of direct project filter.
     */
    public void setProjectFilters(List<DirectProjectFilter> projectFilters) {
        this.projectFilters = projectFilters;
    }

    /**
     * Retrieves supported composite operator.
     *
     * @return composite operator.
     */
    public CompositeOperator getCompositeOperator() {
        return compositeOperator;
    }

    /**
     * Setter for composite operator.
     *
     * @param compositeOperator the supported composite operator.
     */
    public void setCompositeOperator(CompositeOperator compositeOperator) {
        this.compositeOperator = compositeOperator;
    }

    /**
     * Converts the entity to a JSON string that can be used for logging.
     * @return the JSON string with entity data (not null)
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        HelperEntities.setDirectProjectFilterList(jsonObject, "projectFilters", projectFilters);
        if (compositeOperator == null) {
            jsonObject.setNull("compositeOperator");
        } else {
            jsonObject.setString("compositeOperator", compositeOperator.toString());
        }
        return jsonObject.toJSONString();
    }
}

