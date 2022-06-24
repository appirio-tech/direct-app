/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import java.util.List;

import com.topcoder.clients.model.BillingAccount;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.FeeAuditRecord;

/**
 * Defines contract methods to manage contest fees for billing accounts. It defines CRUD operations on contest fee for a
 * billing and create/update default contest fees.
 *
 * <p>
 * Version 1.1 (Release Assembly - Project Contest Fees Management Update 1 Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #search(boolean, int, int, String, String)} method.</li>
 *     <li>Added {@link #getBillingAccountSize(boolean, boolean)} method.</li>
 *   </ol>
 * </p>
 * 
 * Thread safety: The implementation should be reasonable thread safe.
 * 
 * @author winstips, isv
 * @version 1.1
 */
public interface ProjectContestFeeDAO {
    /**
     * This method is responsible for creating contest fees.
     * 
     * @Param contestFees - a list of ContestFeeDetails instances
     * 
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    void save(List<ProjectContestFee> contestFees) throws ContestFeePersistenceException;

    /**
     * This method is responsible for updating contest fee given instance of ContestFeeDetails and project id.
     * 
     * 
     * @Param contestFeeDetails - denotes instance of ContestFeeDetails. ContestFeeDetails.fee should be positive double
     *        and contestType should be valid contest type
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    void update(ProjectContestFee contestFeeDetails) throws ContestFeePersistenceException;

    /**
     * This method is responsible for retrieving billing account given billing account id.
     * 
     * @Param projectId:long - denotes billing account id. should be positive
     * @Return instance of BillingAccount
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    BillingAccount getBillingAccount(long projectId) throws ContestFeePersistenceException;

    /**
     * This method is responsible for deleting contest fee given contest type and project identifier.
     * 
     * @Param contestFeeId - denotes contest fee id.
     */
    void delete(long contestFeeId);
    
    /**
     * Clean up the contest fees for the project.
     * 
     * @param projectId the project id.
     */
    void cleanUpContestFees(long projectId);

    /**
     * This method is responsible for retrieving all billing accounts given billing account id.
     * 
     * @Param pageNumber - denotes the page number. should be positive and > 0.
     * @Param pageSize - denotes no of items to display per page.should be positive (>0)
     * @Param sortColumn - denotes the name of the one of the member of BillingAccount entity which will be used for
     *        sorting resultant billing entities
     * @Param sortingOrder- denotes the SortOrder string. It can be ASC or DSC value.
     * @return returns instance of search result<BillingAccount>
     */
    List<BillingAccount> search(int pageSize, int pageNumber, String sortColumn, String sortOrder);

    /**
     * <p>Gets the list of billing accounts either having or not having contest fees set.</p>
     * 
     * @param contestFeesSet <code>true</code> if accounts having contest fees set are to be returned only; 
     *        <code>false</code> if accounts having no contest fees set are to be returned only.
     * @param pageNumber - denotes the page number. should be positive and > 0.
     * @param pageSize - denotes no of items to display per page.should be positive (>0)
     * @param sortColumn - denotes the name of the one of the member of BillingAccount entity which will be used for
     *        sorting resultant billing entities
     * @param sortOrder - denotes the SortOrder string. It can be ASC or DSC value.
     * @return returns instance of search result<BillingAccount>
     * @since 1.1
     */
    List<BillingAccount> search(boolean contestFeesSet, int pageSize, int pageNumber, String sortColumn, 
                                String sortOrder);

    /**
     * contract responsible for persisting audit record given instance of FeeAuditRecord.
     * 
     * @Param record - instance of FeeAuditRecord. should not be null.
     * @Return long - audit entity identifier
     */
    long audit(FeeAuditRecord record);

    /**
     * responsible for retrieving contest fee given contest typeId and project id.
     * 
     * @param contestTypeId
     *            - denotes the contest type identifier
     * @param projectId
     *            - denotes the project identifier
     * @return found contest fee value.
     */
    double get(long contestTypeId, long projectId);

    /**
     * Get total BillingAccount record number.
     * 
     * @return total BillingAccount record number.
     */
    int getBillingAccountSize();
    
    /**
     * <p>Gets the total number of billing accounts matching the specified criteria.</p>
     * 
     * @param active <code>true</code> if active billing accounts are to be counted; <code>false</code> if inactive.
     * @param contestFeesSet <code>true</code> if billing accounts with contest fees set are to be counted;
     *        <code>false</code> otherwise.
     * @return an <code>int</code> providing the total number of billing accounts.
     * @since 1.1
     */
    int getBillingAccountSize(boolean active, boolean contestFeesSet);

    /**
     * Get all contest types in database.
     * 
     * @return all contest types.
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    //Map<String, ContestType> getContesetTypes() throws ContestFeePersistenceException;
}
