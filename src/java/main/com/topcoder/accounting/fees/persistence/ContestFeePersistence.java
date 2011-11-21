/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.fees.persistence;

import java.util.List;
import java.util.Map;

import com.topcoder.accounting.fees.entities.BillingAccount;
import com.topcoder.accounting.fees.entities.ContestFeeDetails;
import com.topcoder.accounting.fees.entities.ContestType;
import com.topcoder.accounting.fees.entities.FeeAuditRecord;
import com.topcoder.accounting.fees.services.ContestFeePersistenceException;
import com.topcoder.direct.services.configs.ContestFee;

/**
 * Defines contract methods to manage contest fees for billing accounts. It defines CRUD operations on contest fee for a
 * billing and create/update default contest fees.
 * 
 * 
 * Thread safety: The implementation should be reasonable thread safe.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public interface ContestFeePersistence {
    /**
     * This method is responsible for creating contest fee given instance of ContestFeeDetails and project id.
     * 
     * @Param contestFeeDetails - denotes instance of ContestFeeDetails. ContestFeeDetails.fee should be positive double
     *        and contestType should be valid contest type
     * 
     * @Return Contest fee identifier
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    long create(ContestFeeDetails contestFeeDetails) throws ContestFeePersistenceException;

    /**
     * This method is responsible for updating contest fee given instance of ContestFeeDetails and project id.
     * 
     * 
     * @Param contestFeeDetails - denotes instance of ContestFeeDetails. ContestFeeDetails.fee should be positive double
     *        and contestType should be valid contest type
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    void update(ContestFeeDetails contestFeeDetails) throws ContestFeePersistenceException;

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
     * Get all contest types in database.
     * 
     * @return all contest types.
     * @throws ContestFeePersistenceException
     *             if there is any exception.
     */
    Map<String, ContestType> getContesetTypes() throws ContestFeePersistenceException;
}
