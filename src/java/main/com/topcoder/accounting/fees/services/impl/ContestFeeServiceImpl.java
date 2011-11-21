/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.fees.services.impl;

import java.util.List;
import java.util.Map;

import com.topcoder.accounting.fees.entities.BillingAccount;
import com.topcoder.accounting.fees.entities.ContestFeeDetails;
import com.topcoder.accounting.fees.entities.ContestType;
import com.topcoder.accounting.fees.entities.FeeAuditRecord;
import com.topcoder.accounting.fees.entities.SearchResult;
import com.topcoder.accounting.fees.persistence.ContestFeePersistence;
import com.topcoder.accounting.fees.services.ContestFeeConfigurationException;
import com.topcoder.accounting.fees.services.ContestFeeService;
import com.topcoder.accounting.fees.services.ContestFeeServiceException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * Implements contract methods to manage contest fees for billing accounts. it defines CRUD operations on contest fee
 * for a billing and create/update default contest fees.
 * 
 * Thread safety: The class is mutable and not thread safe. But it'll not caused thread safety issue if used under
 * Spring container.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public class ContestFeeServiceImpl implements ContestFeeService {
    /**
     * 
     * Instance of Logger used to perform logging operations. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Log logger;
    /**
     * Instance of DataAccess used to perform db operations. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private ContestFeePersistence persistence;

    /**
     * Default Constructor.
     */
    public ContestFeeServiceImpl() {
    }

    /**
     * This method is responsible for creating contest fee given instance of ContestFeeDetails and project id.
     * 
     * @Param contestFeeDetails - denotes instance of ContestFeeDetails. ContestFeeDetails.fee should be positive double
     *        and contestType should be valid contest type
     * @Return Contest fee identifier
     * 
     * @throws ContestFeeServiceException
     *             if there is any exception.
     */
    public long create(ContestFeeDetails contestFeeDetails) throws ContestFeeServiceException {
        return this.persistence.create(contestFeeDetails);
    }

    /**
     * This method is responsible for updating contest fee given instance of ContestFeeDetails and project id.
     * 
     * @Param contestFeeDetails:ContestFeeDetails - denotes instance of ContestFeeDetails. ContestFeeDetails.fee should
     *        be positive double and contestType should be valid contest type
     * @throws ContestFeeServiceException
     *             if there is any exception.
     */
    public void update(ContestFeeDetails contestFeeDetails) throws ContestFeeServiceException {
        this.persistence.update(contestFeeDetails);
    }

    /**
     * This method is responsible for deleting contest fee given contest type and project identifier.
     * 
     * @Param contestFeeId - denotes contest fee id.
     */
    public void delete(long contestFeeId) {
        this.persistence.delete(contestFeeId);

    }

    /**
     * This method is responsible for retrieving billing account given billing account id.
     * 
     * @Param projectId - denotes billing account id. should be positive
     * @Return instance of BillingAccount
     * @throws ContestFeeServiceException
     *             if there is any exception.
     */
    public BillingAccount getBillingAccount(long projectId) throws ContestFeeServiceException {
        BillingAccount account = persistence.getBillingAccount(projectId);
        // populate type names
        Map<String, ContestType> types = persistence.getContesetTypes();
        if (account.getContestFees() != null) {
            for (ContestFeeDetails details : account.getContestFees()) {
                String id = Long.toString(details.getContestTypeId());
                ContestType type = types.get(id);
                if (type != null && type.getDescription() != null && type.getDescription().trim().length() != 0) {
                    details.setContestTypeDescription(type.getDescription());
                } else {
                    details.setContestTypeDescription(id);
                }
            }
        }

        return account;
    }

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
    public SearchResult<BillingAccount> search(int pageSize, int pageNumber, String sortColumn, String sortOrder) {
        List<BillingAccount> accounts = this.persistence.search(pageSize, pageNumber, sortColumn, sortOrder);
        SearchResult<BillingAccount> searchResult = new SearchResult<BillingAccount>();
        searchResult.setPageNumber(pageNumber);
        searchResult.setPageSize(pageSize);
        searchResult.setTotal(persistence.getBillingAccountSize());
        searchResult.setTotalPageCount((int) Math.ceil(accounts.size() / pageSize)); // note upper bound
        searchResult.setItems(accounts);
        return searchResult;
    }

    /**
     * Responsible for retrieving contest fee given contest typeId and project id.
     * 
     * @param contestTypeId
     *            - denotes the contest type identifier
     * @param projectId
     *            - denotes the project identifier
     */
    public double get(long contestTypeId, long projectId) {
        return this.persistence.get(contestTypeId, projectId);
    }

    /**
     * Contract responsible for persisting audit record given instance of FeeAuditRecord.
     * 
     * @Param record - instance of FeeAuditRecord. should not be null.
     * @Return long - audit entity identifier
     */
    public long audit(FeeAuditRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("The record instance should not be null.");
        }
        return persistence.audit(record);
    }

    /**
     * Returns the logger field value.
     * 
     * @return logger field value.
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * Sets the corresponding member field
     * 
     * @param loggerName
     *            - the given name to set.
     */
    public void setLoggerName(String loggerName) {
        if (loggerName == null) {
            this.logger = null;
        } else {
            this.logger = LogManager.getLog(loggerName);
        }
    }

    /**
     * Returns the persistence field value.
     * 
     * @return persistence field value.
     */
    public ContestFeePersistence getPersistence() {
        return persistence;
    }

    /**
     * Sets the given value to persistence field.
     * 
     * @param persistence
     *            - the given value to set.
     */
    public void setPersistence(ContestFeePersistence persistence) {
        this.persistence = persistence;
    }

    /**
     * Check parameters.
     * 
     * @throw ContestFeeConfigurationException if this.logger or this.persistence is null
     */
    public void postConstruct() {
        if (logger == null || persistence == null) {
            throw new ContestFeeConfigurationException("The class requires logger instance and persistence instance.");
        }
    }

}
