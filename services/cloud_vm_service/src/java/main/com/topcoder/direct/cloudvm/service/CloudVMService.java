/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.cloudvm.service;

import com.topcoder.direct.services.view.dto.cloudvm.VMUsage;
import com.topcoder.direct.services.view.dto.cloudvm.VMContestType;
import com.topcoder.direct.services.view.dto.cloudvm.VMImage;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstance;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceData;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceStatus;
import com.topcoder.security.TCSubject;

import java.util.List;

/**
 * This interface defines the contract to launch VM instance, terminate VM instance, get VM instances, get VM images and
 * get VM contest types. It will exposed as web service.
 *
 * <p>
 * Changes in version 1.1 (TopCoder Direct Contest VM Instances Management) :
 * <ul>
 *     <li>added {@link #getVMInstancesForContest(com.topcoder.security.TCSubject, long)}  method.</li>
 *     <li>added {@link #terminateContestVMInstance(com.topcoder.security.TCSubject, long, long)} method.</li>
 *     <li>added {@link #isVMCreator(com.topcoder.security.TCSubject, long)} method.</li>
 * </ul>
 * </p>
 * <p>
 * Thread-safety: handled by the container.
 * </p>
 *
 * <p>
 * Version 1.2 - Release Assembly - TopCoder Direct VM Instances Management
 * <ul>
 *     <li>added {@link #getVMInstancesForContests(com.topcoder.security.TCSubject, java.util.List)} method.</li>
 * </ul>
 * </p>
 *
 * @author Standlove, gentva, jiajizhou86
 * @version 1.2
 */
public interface CloudVMService {
    /**
     * Launch VM instance.
     *
     * @param tcSubject the currently logged-in user info.
     * @param instance  the VM instance to launch.
     * @return the launched VM instance.
     * @throws IllegalArgumentException if any argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMInstanceData> launchVMInstance(TCSubject tcSubject, VMInstance instance) throws CloudVMServiceException;

    /**
     * Terminate VM instance for the given contest.
     *
     * @param tcSubject  the currently logged-in user info.
     * @param instanceId the VM instance to terminate.
     * @param contestId the contest id that the VM instance belong to.
     * @return the changed instance status
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     * @since 1.1
     */
    public VMInstanceStatus terminateContestVMInstance(TCSubject tcSubject, long instanceId, long contestId) throws CloudVMServiceException;

    /**
     * Terminate VM instance.
     *
     * @param tcSubject  the currently logged-in user info.
     * @param instanceId the VM instance to terminate.
     * @return the changed instance status
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public VMInstanceStatus terminateVMInstance(TCSubject tcSubject, long instanceId) throws CloudVMServiceException;

    /**
     * Get VM instances for the given user.
     *
     * @param tcSubject the currently logged-in user info.
     * @return the vm instances.
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMInstanceData> getVMInstances(TCSubject tcSubject) throws CloudVMServiceException;

    /**
     * Get VM instances for the given contest.
     *
     * @param tcSubject the currently logged-in user info.
     * @param contestId the specified contest id
     * @return the vm instances.
     * @throws IllegalArgumentException if the tcSubject argument is null or contestId is negative or null..
     * @throws CloudVMServiceException  if any error occurs.
     * @since 1.1
     */
    public List<VMInstanceData> getVMInstancesForContest(TCSubject tcSubject, long contestId) throws CloudVMServiceException;

    /**
     * Get VM instances for the given contests.
     *
     * @param tcSubject the currently logged-in user info.
     * @param contestIds the specified contest ids.
     * @return the vm instances
     * @throws IllegalArgumentException if the tcSubject argument is null or contestIds is null or empty.
     * @throws CloudVMServiceException  if any error occurs.
     * @since 1.2
     */
    public List<VMInstanceData> getVMInstancesForContests(TCSubject tcSubject, List<Long> contestIds)
            throws CloudVMServiceException;

    /**
     * Get VM images.
     *
     * @param tcSubject the currently logged-in user info.
     * @return the vm images.
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMImage> getVMImages(TCSubject tcSubject) throws CloudVMServiceException;

    /**
     * Get VM contest types.
     *
     * @param tcSubject the currently logged-in user info.
     * @return the vm contest types.
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMContestType> getVMContestTypes(TCSubject tcSubject) throws CloudVMServiceException;

    /**
     * Get VM usages.
     *
     * @param tcSubject the currently logged-in user info.
     * @return the vm usages.
     * @throws IllegalArgumentException if the tcSubject argument is null.
     * @throws CloudVMServiceException  if any error occurs.
     */
    public List<VMUsage> getVMUsages(TCSubject tcSubject) throws CloudVMServiceException;

    /**
     * Checks whether the VM instance is created by the current user.
     *
     * @param tcSubject the currently logged-in user info.
     * @param instanceId  the vm instance id.
     * @return true if the vm instance is created by the current logged-in user.
     * @throws CloudVMServiceException if any error occurs.
     * @since 1.1
     */
    public boolean  isVMCreator(TCSubject tcSubject, long instanceId) throws  CloudVMServiceException;
}

