/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.accuracytests;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;

import com.topcoder.search.builder.filter.Filter;


/**
 * Mock ResourceManager for testing purpose.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockResourceManager implements ResourceManager {
    /**
     * A static state variable for the mock.
     */
    private static int state = 0;

    /**
     * Used for testing purpose.
     */
    private Resource updateResource;

    /**
     * Used for testing purpose.
     */
    private String updateResourceUserId;

    /**
     * Not implemented.
     *
     * @param resource resource
     * @param operator operator
     */
    public void updateResource(Resource resource, String operator) {
        updateResource = resource;
        updateResourceUserId = operator;
    }

    /**
     * Not implemented.
     *
     * @param resource resource
     * @param operator operator
     */
    public void removeResource(Resource resource, String operator) {
    }

    /**
     * Not implemented.
     *
     * @param resources resources
     * @param project project id
     * @param operator operator
     */
    public void updateResources(Resource[] resources, long project, String operator) {
    }

    /**
     * Gets resource.
     *
     * @param id id
     *
     * @return null of resource.
     */
    public Resource getResource(long id) {
        if (getState() == 1) {
            Resource resource = new Resource();
            resource.setId(1);

            return resource;
        }

        return null;
    }

    /**
     * Retruns an array of resource.
     *
     * @param filter filter
     *
     * @return resources.
     */
    public Resource[] searchResources(Filter filter) {
        if (getState() == 0) {
            Resource[] resources = new Resource[1];
            resources[0] = new Resource();
            resources[0].setId(1);

            return resources;
        }

        return new Resource[0];
    }

    /**
     * Not implemented.
     *
     * @param resourceRole resource role
     * @param operator operator
     */
    public void updateResourceRole(ResourceRole resourceRole, String operator) {
    }

    /**
     * Not implemented.
     *
     * @param resourceRole resource role
     * @param operator operator
     */
    public void removeResourceRole(ResourceRole resourceRole, String operator) {
    }

    /**
     * Returns a predefined set of roles.
     *
     * @return resource roles.
     */
    public ResourceRole[] getAllResourceRoles() {
        if ((getState() == 0) || (getState() == 1)) {
            // modified in version 1.1
            ResourceRole[] roles = new ResourceRole[5];

            for (int i = 0; i < roles.length; i++) {
                roles[i] = new ResourceRole(i + 1);
            }

            roles[0].setName("Submitter");
            roles[1].setName("Specification Submitter");
            roles[2].setName("Accuracy Reviewer");
            roles[3].setName("Failure Reviewer");
            roles[4].setName("Stress Reviewer");

            return roles;
        }

        return new ResourceRole[0];
    }

    /**
     * Not implemented.
     *
     * @param filter filter
     *
     * @return always null.
     */
    public ResourceRole[] searchResourceRoles(Filter filter) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param users users
     * @param project project id
     * @param notificationType notification type
     * @param operator operator
     */
    public void addNotifications(long[] users, long project, long notificationType, String operator) {
    }

    /**
     * Not implemented.
     *
     * @param users users
     * @param project project id
     * @param notificationType notification type
     * @param operator operator
     */
    public void removeNotifications(long[] users, long project, long notificationType,
        String operator) {
    }

    /**
     * Not implemented.
     *
     * @param project project id
     * @param notificationType notification type
     *
     * @return always null.
     */
    public long[] getNotifications(long project, long notificationType) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param filter filter
     *
     * @return always null.
     */
    public Notification[] searchNotifications(Filter filter) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param notificationType notification type
     * @param operator operator
     */
    public void updateNotificationType(NotificationType notificationType, String operator) {
    }

    /**
     * Not implemented.
     *
     * @param notificationType notification type
     * @param operator operator
     */
    public void removeNotificationType(NotificationType notificationType, String operator) {
    }

    /**
     * Not implemented.
     *
     * @param filter filter
     *
     * @return always null.
     */
    public NotificationType[] searchNotificationTypes(Filter filter) {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return always null.
     */
    public NotificationType[] getAllNotificationTypes() {
        return null;
    }

    /**
     * Sets the state.
     *
     * @param state the state to set
     */
    public static void setState(int state) {
        MockResourceManager.state = state;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public static int getState() {
        return state;
    }

    /**
     * Gets the updated resource.
     *
     * @return the updated resource
     */
    public Resource getUpdatedResource() {
        return updateResource;
    }

    /**
     * Gets the updated resource user id.
     *
     * @return the updated user id
     */
    public String getUpdateResourceUserId() {
        return updateResourceUserId;
    }
}
