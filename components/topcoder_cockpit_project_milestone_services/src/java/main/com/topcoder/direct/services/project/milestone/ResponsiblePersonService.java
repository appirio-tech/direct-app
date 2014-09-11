/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone;

import java.util.List;

import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;

/**
 * <p>
 * This interface defines the service contract for the retrieval of responsible people in a given direct project.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implementations are expected to be effectively thread-safe.
 * </p>
 *
 * <p>
 *     Update in version 1.1
 *     <li>Add {@link #deleteResponsiblePerson(long)}</li>
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.1
 */
public interface ResponsiblePersonService {
    /**
     * This method gets all responsible people for the given project ID. If none found, returns an empty list.
     *
     * @param projectId
     *            the ID of the direct project
     * @return the responsible people
     * @throws ProjectMilestoneManagementException
     *             If there are any errors during the execution of this method
     */
    public List<ResponsiblePerson> getAllResponsiblePeople(long projectId)
        throws ProjectMilestoneManagementException;

    /**
     * Deletes the responsible person with the given responsible person id.
     *
     * @param responsiblePersonId the id of the responsible person.
     * @throws ProjectMilestoneManagementException If there are any errors during the execution of this method
     * @since 1.1
     */
    public void deleteResponsiblePerson(long responsiblePersonId) throws ProjectMilestoneManagementException;
}
