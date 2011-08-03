/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.management.project.ProjectType;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A DTO providing the details on projects associated with current user.</p>
 *
 * <p>
 *     Change notes - version 1.1 (Release Assembly - Cockpit Customer Right Sidebar and Active Contests Coordination)
 *     <li>Add getters for project customers and the json representation of user projects</li>
 * </p>
 *
 * @author isv, GreatKevin
 * @version 1.1
 */
public class UserProjectsDTO implements Serializable {

    /**
     * <p>An interface to be implemented by the parties interested in getting the details on projects associated with
     * current user.</p>
     */
    public static interface Aware {

        /**
         * <p>Sets the details on user projects.</p>
         *
         * @param userProjects a <code>UserProjectsDTO</code> providing details on user projects.
         */
        public void setUserProjects(UserProjectsDTO userProjects);
    }

    /**
     * <p>A <code>List</code> of projects associated with the user.</p>
     */
    private List<ProjectBriefDTO> projects;

    /**
     * <p>Constructs new <code>UserProjectsDTO</code> instance. This implementation does nothing.</p>
     */
    public UserProjectsDTO() {
    }

    /**
     * <p>Gets the list of user projects.</p>
     *
     * @return a <code>List</code> of projects associated with the user.
     */
    public List<ProjectBriefDTO> getProjects() {
        return projects;
    }

    /**
     * <p>Sets the list of user projects.</p>
     *
     * @param projects a <code>List</code> of projects associated with the user.
     */
    public void setProjects(List<ProjectBriefDTO> projects) {
        this.projects = projects;
    }

    /**
     * Gets the customers of the user projects.
     *
     * @return the customers of the user projects.
     * @since 1.1
     */
    public Map<Long, String> getProjectsCustomers() {
        Map<Long, String> customers = new HashMap<Long, String>();

        // iterate the projects to find out all the customers
        for (ProjectBriefDTO project : getProjects()) {
            if (!customers.containsKey(project.getCustomerId())) {
                customers.put(project.getCustomerId(), project.getCustomerName());
            }
        }

        return customers;
    }

    /**
     * Gets the json representation of the user projects.
     *
     * @return the json string.
     * @since 1.1
     */
    public String getJsonResult() {
        Map<String, Map<String, Object>> data = new HashMap<String, Map<String, Object>>();
        Map<Long, String> customers = getProjectsCustomers();
        Map<String, Object> noCustomerData = new HashMap<String, Object>();
        // The map to store the projects without customer data
        noCustomerData.put("id", "none");
        Map<String, String> noCustomerProjects = new HashMap<String, String>();

        // iterate project to classify the project by its customer
        for (ProjectBriefDTO project : getProjects()) {

            if(project.getCustomerName() == null || project.getCustomerName().trim().length() == 0) {
                // the project does not have customer
                noCustomerProjects.put(String.valueOf(project.getId()), project.getName());
                continue;
            }

            if (!data.containsKey(project.getCustomerName())) {
                Map<String, Object> innerData = new HashMap<String, Object>();
                innerData.put("id", String.valueOf(project.getCustomerId()));
                innerData.put("projects", new HashMap<String, String>());
                data.put(project.getCustomerName(), innerData);
            }

            ((Map<String, String>) data.get(project.getCustomerName()).get("projects")).put(String.valueOf(project.getId()), project.getName());
        }

        // put the projects without customer into the map
        noCustomerData.put("projects", noCustomerProjects);
        data.put("No Customer", noCustomerData);

        // generate the json from the result map
        JSON json = JSONSerializer.toJSON(data);

        return json.toString();
    }

}
