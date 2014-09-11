/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import java.util.List;

import com.topcoder.clients.model.BillingAccount;
import com.topcoder.clients.model.FeeAuditRecord;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.SearchResult;

/**
 * Defines contract methods to manage contest fees for billing accounts. It defines CRUD operations on contest fee for a
 * billing and create/update default contest fees.
 *
 * <p>
 * Version 1.1 (Release Assembly - Project Contest Fees Management Update 1 Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #search(boolean, int, int, String, String)} method.</li>
 *   </ol>
 * </p>
 * 
 * Thread safety: The implementation should be reasonable thread safe.
 * 
 * @author winstips, isv
 * @version 1.1
 */
public interface ProjectContestFeeService {
	/**
     * This method is responsible for creating contest fees.
     * 
     * @Param contestFees - a list of ProjectContestFee instances
     * 
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    void save(List<ProjectContestFee> contestFees) throws ContestFeeServiceException;

    /**
     * This method is responsible for updating contest fee given instance of ProjectContestFee and project id.
     * 
     * @Param contestFeeDetails:ProjectContestFee - denotes instance of ProjectContestFee. ProjectContestFee.fee should
     *        be positive double and contestType should be valid contest type
     * @throws ContestFeeServiceException
     *             if there is any exception.
     */
    void update(ProjectContestFee contestFeeDetails) throws ContestFeeServiceException;

    /**
     * This method is responsible for retrieving billing account given billing account id.
     * 
     * @Param projectId - denotes billing account id. should be positive
     * @Return instance of BillingAccount
     * @throws ContestFeeServiceException
     *             if there is any exception.
     */
    BillingAccount getBillingAccount(long projectId) throws ContestFeeServiceException;

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
     * @Param pageNumber:int - denotes the page number. should be positive and > 0.
     * @Param pageSize:int - denotes no of items to display per page.should be positive (>0)
     * @Param sortColumn:String - denotes the name of the one of the member of BillingAccount entity which will be used
     *        for sorting resultant billing entities
     * @Param sortingOrder:String- denotes the SortOrder string. It can be ASC or DSC value.
     * @return returns instance of search result<BillingAccount>
     */
    SearchResult<BillingAccount> search(int pageSize, int pageNumber, String sortColumn, String sortOrder);

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
    SearchResult<BillingAccount> search(boolean contestFeesSet, int pageSize, int pageNumber, String sortColumn, 
                                        String sortOrder);

    /**
     * Contract responsible for persisting audit record given instance of FeeAuditRecord.
     * 
     * @Param record:FeeAuditRecord - instance of FeeAuditRecord. should not be null.
     * @Return long - audit entity identifier
     */
    long audit(FeeAuditRecord record);

    /**
     * Responsible for retrieving contest fee given contest typeId and project id.
     * 
     * @param contestTypeId
     *            - denotes the contest type identifier
     * @param projectId
     *            - denotes the project identifier
     */
    double get(long contestTypeId, long projectId);

}
