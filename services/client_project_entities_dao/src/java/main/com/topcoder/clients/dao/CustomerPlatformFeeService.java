/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import java.util.Date;
import java.util.List;

import com.topcoder.clients.model.CustomerPlatformFee;

/**
 * Defines contract methods to manage platform fee for customer.
 * It defines CRUD operations on platform fee for a customer.
 * Thread safety: The implementation should be reasonable thread safe.
 * 
 * @author minhu
 * @version 1.0 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
 */
public interface CustomerPlatformFeeService {
    /**
     * This method is responsible for creating customer platform fee.
     * 
     * @param customerPlatformFee - a CustomerPlatformFee instance to save
     * @throws IllegalArgumentException
     *         if customerPlatformFee is null or its id is not zero
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any exception
     */
    void create(CustomerPlatformFee customerPlatformFee) throws CustomerPlatformFeePersistenceException;

    /**
     * This method is responsible for retrieving customer platform fee given id.
     * 
     * @param id - denotes the entity id, should be positive
     * @return instance of CustomerPlatformFee or null if not found
     * @throws IllegalArgumentException
     *         if id is not positive
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any exception
     */
    CustomerPlatformFee get(long id) throws CustomerPlatformFeePersistenceException;

    /**
     * This method is responsible for retrieving the list of all customer platform fees.
     * 
     * @return the list of all customer platform fees
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any exception
     */
    List<CustomerPlatformFee> getAll() throws CustomerPlatformFeePersistenceException;

    /**
     * This method is responsible for retrieving customer platform fee given client id and payment date.
     * 
     * @param clientId - denotes client id, should be positive
     * @param paymentDate - denotes payment date, should be non-null
     * @return instance of CustomerPlatformFee or null if not found
     * @throws IllegalArgumentException
     *         if clientId is not positive, or paymentDate is null
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any exception
     */
    CustomerPlatformFee getByClientIdAndPaymentDate(long clientId, Date paymentDate)
        throws CustomerPlatformFeePersistenceException;

    /**
     * This method is responsible for updating customer platform fee given
     * instance of CustomerPlatformFee.
     * 
     * @param customerPlatformFee - a CustomerPlatformFee instance to update
     * @throws IllegalArgumentException
     *         if customerPlatformFee is null or its id is not positive
     * @throws CustomerPlatformFeeEntityNotFoundException
     *         if the platform fee to update is not found in DB
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any other exception
     */
    void update(CustomerPlatformFee customerPlatformFee)
        throws CustomerPlatformFeeEntityNotFoundException, CustomerPlatformFeePersistenceException;

    /**
     * This method is responsible for deleting customer platform fee given its id.
     * 
     * @param id - denotes customer platform fee id
     * @throws IllegalArgumentException
     *         if id is not positive
     * @throws CustomerPlatformFeeEntityNotFoundException
     *         if the platform fee to update is not found in DB
     * @throws CustomerPlatformFeePersistenceException
     *         if there is any other exception
     */
    void delete(long id) throws CustomerPlatformFeeEntityNotFoundException,
        CustomerPlatformFeePersistenceException;
}
