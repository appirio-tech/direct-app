/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.fees.services;

import java.util.List;

import com.topcoder.accounting.fees.entities.BillingAccount;
import com.topcoder.accounting.fees.entities.ContestFeeDetails;
import com.topcoder.accounting.fees.entities.FeeAuditRecord;
import com.topcoder.accounting.fees.entities.SearchResult;

/**
 * Defines contract methods to manage contest fees for billing accounts. It defines CRUD operations on contest fee for a
 * billing and create/update default contest fees.
 * 
 * Thread safety: The implementation should be reasonable thread safe.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public interface ContestFeeService {
	/**
     * This method is responsible for creating contest fees.
     * 
     * @Param contestFees - a list of ContestFeeDetails instances
     * 
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    void save(List<ContestFeeDetails> contestFees) throws ContestFeeServiceException;

    /**
     * This method is responsible for updating contest fee given instance of ContestFeeDetails and project id.
     * 
     * @Param contestFeeDetails:ContestFeeDetails - denotes instance of ContestFeeDetails. ContestFeeDetails.fee should
     *        be positive double and contestType should be valid contest type
     * @throws ContestFeeServiceException
     *             if there is any exception.
     */
    void update(ContestFeeDetails contestFeeDetails) throws ContestFeeServiceException;

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
