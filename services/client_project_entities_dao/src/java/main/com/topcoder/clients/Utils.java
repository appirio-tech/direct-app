/*
 * Copyright (C) 2009 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients;

import java.util.List;

import com.topcoder.clients.model.ClientInvoiceUpload;
import com.topcoder.clients.model.CustomerPlatformFee;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.ProjectContestFeePercentage;

/**
 * <p>
 * This is utility class. It provides some methods to be shared across.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class does not contain any changeable fields, so is thread safe.
 * </p>
 * 
 * <p>
 * Version 1.1: (Module Assembly - Contest Fee Based on % of Member Cost Admin Part)
 *  Added method {@link #toString(ProjectContestFeePercentage)}.
 * </p>
 * 
 * <p>
 * Version 1.2: (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
 *  Added methods {@link #toString(CustomerPlatformFee)} and {@link #toString(List)}.
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Cockpit - Billing Management)
 * - Add method {@link #toString(com.topcoder.clients.model.ClientInvoiceUpload)}
 * - Add method {@link #invoiceUploadListToString(java.util.List)}
 * </p>
 * 
 * @author minhu, TCSASSEMBLER
 * @version 1.3
 * @since Configurable Contest Fees v1.0 Assembly
 */
public final class Utils {
    /**
     * Prevents this class to be created.
     */
    private Utils() {
        // do nothing
    }

    /**
     * Checks to make sure the name has no wildcard characers: % or *.
     * 
     * @param name
     *        the search name
     * @throws IllegalArgumentException
     *         if the name contains wildcard characters: % or *
     */
    public static void checkSearchName(String name) {
        // name can not contains wildcard characters % or *
        if (name != null && (name.contains("%") || name.contains("*"))) {
            throw new IllegalArgumentException("the name should not contain wildcard % or *");
        }
    }

    /**
     * Gets the string representation for the given ProjectContestFeePercentage instance.
     * 
     * @param contestFeePercentage the ProjectContestFeePercentage instance
     * @return the string representation
     * @since 1.1
     */
    public static String toString(ProjectContestFeePercentage contestFeePercentage) {
        StringBuilder builder = new StringBuilder();
        if (contestFeePercentage == null) {
            builder.append("contestFeePercentage: null");
        } else {
            builder.append("contestFeePercentage:{id:").append(contestFeePercentage.getId());
            builder.append(",contestFeePercentage:").append(contestFeePercentage.getContestFeePercentage());
            builder.append(",createUser:").append(contestFeePercentage.getCreateUser());
            builder.append(",createDate:").append(contestFeePercentage.getCreateDate());
            builder.append(",modifyUser:").append(contestFeePercentage.getModifyUser());
            builder.append(",modifyDate:").append(contestFeePercentage.getModifyDate());
            builder.append(",projectId:").append(contestFeePercentage.getProjectId());
            builder.append(",active:").append(contestFeePercentage.isActive());
            builder.append("}");
        }        
        return builder.toString();
    }

    /**
     * Gets the string representation for the given CustomerPlatformFee instance.
     * 
     * @param customerPlatformFee the CustomerPlatformFee instance
     * @return the string representation
     * @since 1.2
     */
    public static String toString(CustomerPlatformFee customerPlatformFee) {
        StringBuilder builder = new StringBuilder();
        if (customerPlatformFee == null) {
            builder.append("customerPlatformFee: null");
        } else {
            builder.append("customerPlatformFee:{id:").append(customerPlatformFee.getId());
            builder.append(",amount:").append(customerPlatformFee.getAmount());
            builder.append(",createUser:").append(customerPlatformFee.getCreateUser());
            builder.append(",createDate:").append(customerPlatformFee.getCreateDate());
            builder.append(",modifyUser:").append(customerPlatformFee.getModifyUser());
            builder.append(",modifyDate:").append(customerPlatformFee.getModifyDate());
            builder.append(",clientId:").append(customerPlatformFee.getClientId());
            builder.append(",paymentDate:").append(customerPlatformFee.getPaymentDate());
            builder.append("}");
        }        
        return builder.toString();
    }

    /**
     * Gets the string representation of <code>ClientInvoiceUpload</code>
     *
     * @param clientInvoiceUpload the <code>ClientInvoiceUpload</code> instance
     * @return the string representation of <code>ClientInvoiceUpload</code>.
     * @since 1.3
     */
    public static String toString(ClientInvoiceUpload clientInvoiceUpload) {
        StringBuilder builder = new StringBuilder();
        if (clientInvoiceUpload == null) {
            builder.append("clientInvoiceUpload: null");
        } else {
            builder.append("clientInvoiceUpload:{id:").append(clientInvoiceUpload.getId());
            builder.append(",description:").append(clientInvoiceUpload.getDescription());
            builder.append(",fileName:").append(clientInvoiceUpload.getFileName());
            builder.append(",clientId:").append(clientInvoiceUpload.getClientId());
            builder.append(",invoiceUploadDate:").append(clientInvoiceUpload.getInvoiceUploadDate());
            builder.append(",modifyUser:").append(clientInvoiceUpload.getModifyUser());
            builder.append(",modifyDate:").append(clientInvoiceUpload.getModifyDate());
            builder.append(",createUser:").append(clientInvoiceUpload.getCreateUser());
            builder.append(",createDate:").append(clientInvoiceUpload.getCreateDate());
            builder.append("}");
        }
        return builder.toString();
    }

    /**
     * Gets the string representation of a list of <code>ClientInvoiceUpload</code>
     *
     * @param clientInvoiceUploads a list of <code>ClientInvoiceUpload</code>
     * @return the string representation of a list of <code>ClientInvoiceUpload</code>
     * @since 1.3
     */
    public static String invoiceUploadListToString(List<ClientInvoiceUpload> clientInvoiceUploads) {
        StringBuilder builder = new StringBuilder();
        if (clientInvoiceUploads == null) {
            builder.append("clientInvoiceUploads:null");
        } else {
            builder.append("clientInvoiceUploads:[");
            boolean first = true;
            for (ClientInvoiceUpload clientInvoiceUpload : clientInvoiceUploads) {
                if (!first) {
                    builder.append(",");
                }
                builder.append("{id:").append(clientInvoiceUpload.getId());
                builder.append(",description:").append(clientInvoiceUpload.getDescription());
                builder.append(",fileName:").append(clientInvoiceUpload.getFileName());
                builder.append(",clientId:").append(clientInvoiceUpload.getClientId());
                builder.append(",invoiceUploadDate:").append(clientInvoiceUpload.getInvoiceUploadDate());
                builder.append(",modifyUser:").append(clientInvoiceUpload.getModifyUser());
                builder.append(",modifyDate:").append(clientInvoiceUpload.getModifyDate());
                builder.append(",createUser:").append(clientInvoiceUpload.getCreateUser());
                builder.append(",createDate:").append(clientInvoiceUpload.getCreateDate());
                builder.append("}");
                first = false;
            }
            builder.append("]");
        }
        return builder.toString();
    }

    /**
     * Gets the string representation for the given CustomerPlatformFee instance.
     * 
     * @param customerPlatformFees the list of CustomerPlatformFee instances
     * @return the string representation
     * @since 1.2
     */
    public static String toString(List<CustomerPlatformFee> customerPlatformFees) {
        StringBuilder builder = new StringBuilder();
        if (customerPlatformFees == null) {
            builder.append("customerPlatformFees:null");
        } else {
            builder.append("customerPlatformFees:[");
            boolean first = true;
            for (CustomerPlatformFee customerPlatformFee : customerPlatformFees) {
                if (!first) {
                    builder.append(",");
                }
                builder.append("{id:").append(customerPlatformFee.getId());
                builder.append(",amount:").append(customerPlatformFee.getAmount());
                builder.append(",createUser:").append(customerPlatformFee.getCreateUser());
                builder.append(",createDate:").append(customerPlatformFee.getCreateDate());
                builder.append(",modifyUser:").append(customerPlatformFee.getModifyUser());
                builder.append(",modifyDate:").append(customerPlatformFee.getModifyDate());
                builder.append(",clientId:").append(customerPlatformFee.getClientId());
                builder.append(",paymentDate:").append(customerPlatformFee.getPaymentDate());
                builder.append("}");
                first = false;
            }
            builder.append("]");
        }        
        return builder.toString();
    }

    /**
     * Checks contest fees parameter.
     * 
     * @param contestFees
     *        contest fees parameter. it could be null.
     * @param projectId
     *        the project id
     * @throws IllegalArgumentException
     *         if the associated project id is not equal to the one given or
     *         the <code>contestFees</code> contains null element
     * @since Configurable Contest Fees v1.0 Assembly
     */
    public static void checkContestFees(List<ProjectContestFee> contestFees, long projectId) {
        if (contestFees == null) {
            return;
        }

        for (ProjectContestFee fee : contestFees) {
            if (fee == null) {
                throw new IllegalArgumentException("contestFees should not contain null element.");
            }

            if (fee.getProjectId() != projectId) {
                throw new IllegalArgumentException("contestFee in the list should belong to project of id "
                    + projectId);
            }
        }
    }
}
