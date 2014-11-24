/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.util.DataProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This common action provides ajax supports for all the reports in Cockpit. In the version 1.0,
 * it provides method to get group by options for a selected customer and method to get group values for
 * a selected group by option.
 *
 * <p>
 *  Version 1.1 (Release Assembly - TC Direct Cockpit Release Six) changes:
 *  <li>
 *      Add properties {@link #billingAccountId} and {@link #invoiceNumber} and their getter and setter.
 *  </li>
 * </p>
 *
 * @author GreatKevin
 * @version 1.1
 */
public class ReportCommonAction extends BaseDirectStrutsAction {

    /**
     * The id of the customer used to get group by options.
     */
    private long customerId;

    /**
     * The id of the group key used to get group values options.
     */
    private long groupKeyId;

    /**
     * The billing account id.
     *
     * @since 1.1
     */
    private long billingAccountId;

    /**
     * The invoice number.
     *
     * @since 1.1
     */
    private String invoiceNumber;

    /**
     * Gets the customer id.
     *
     * @return the customer id.
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer id.
     *
     * @param customerId the customer id.
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the group by key id.
     *
     * @return the group key id
     */
    public long getGroupKeyId() {
        return groupKeyId;
    }

    /**
     * Sets the group key id.
     *
     * @param groupKeyId the group key id.
     */
    public void setGroupKeyId(long groupKeyId) {
        this.groupKeyId = groupKeyId;
    }

    /**
     * Gets the billing account id.
     *
     * @return the billing account id.
     * @since 1.1
     */
    public long getBillingAccountId() {
        return billingAccountId;
    }

    /**
     * Sets the billing account id.
     *
     * @param billingAccountId the billing account id.
     * @since 1.1
     */
    public void setBillingAccountId(long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    /**
     * Gets the invoice number.
     *
     * @return the invoice number.
     * @since 1.1
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Sets the invoice number.
     *
     * @param invoiceNumber the invoice number.
     * @since 1.1
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * Do nothing because this action provides ajax supports.
     *
     * @throws Exception if there is error (will not happen).
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing
    }

    /**
     * Gets the group by options with the customer id in the request.
     *
     * @return the struts result string.
     */
    public String getGroupByOptionsForCustomer() {
        // Map to store the ajax result
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        try {

            final List<DirectProjectMetadataKey> clientProjectMetadataKeys = getMetadataKeyService().getClientProjectMetadataKeys(getCustomerId(), true);

            Collections.sort(clientProjectMetadataKeys, new DirectProjectMetadataKeyComparable(true));

            for (DirectProjectMetadataKey key : clientProjectMetadataKeys) {
                Map<String, String> keyResult = new HashMap<String, String>();

                keyResult.put("id", String.valueOf(key.getId()));
                keyResult.put("name", key.getName());

                result.add(keyResult);
            }

            setResult(result);

        } catch (Throwable e) {

            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }
        }

        return SUCCESS;
    }

    /**
     * Gets the group by values with the group key id in the request.
     *
     * @return the struts result string.
     */
    public String getGroupValuesForGroupBy() {

        try {

            final List<DirectProjectMetadata> projectMetadataByKey = getMetadataService().getProjectMetadataByKey(getGroupKeyId());

            Set<String> valuesSet = new LinkedHashSet<String>();

            for (DirectProjectMetadata metadata : projectMetadataByKey) {
                valuesSet.add(metadata.getMetadataValue());
            }

            // Map to store the ajax result
            List<String> result = new ArrayList<String>(valuesSet);

            Collections.sort(result);

            setResult(result);

        } catch (Throwable e) {

            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }
        }

        return SUCCESS;
    }

    public String getInvoiceNumbersForBillingAccount() {
        try {

            final List<String> invoiceNumbers = DataProvider.getInvoiceNumbersFromBilling(getBillingAccountId());

            setResult(invoiceNumbers);

        } catch (Throwable e) {

            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }
        }

        return SUCCESS;
    }

    public String getProjectsForInvoiceNumberAndBillingAccount() {
        try {
            Map<String, String> result = new LinkedHashMap<String, String>();

            final List<IdNamePair> projectsForInvoiceNumberAndBilling =
                    DataProvider.getProjectsForInvoiceNumberAndBilling(getInvoiceNumber(), getBillingAccountId());

            for(IdNamePair item : projectsForInvoiceNumberAndBilling) {
                result.put(String.valueOf(item.getId()), item.getName());
            }

            setResult(result);

        } catch (Throwable e) {

            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e.getMessage());
            }
        }

        return SUCCESS;
    }

    /**
     * <p>
     * This class for comparing of DirectProjectMetadataKey.
     * </p>
     * <p>
     * <b>Thread Safety:</b> It's thread safe.
     * </p>
     *
     * @author TCSASSEMBLER
     * @version 1.0
     */
    private class DirectProjectMetadataKeyComparable implements Comparator<DirectProjectMetadataKey> {
        /**
         * <p>
         * The integer to decide order.
         * </p>
         */
        private final int order;

        /**
         * Constructor.
         *
         * @param ascending the flag of ascending
         */
        public DirectProjectMetadataKeyComparable(boolean ascending) {
            order = ascending ? 1 : -1;
        }

        /**
         * Compare the key by name
         *
         * @param k1 the key 1
         * @param k2 the key 2
         * @return 1 is larger, -1 is smaller, 0 equal
         */
        public int compare(DirectProjectMetadataKey k1, DirectProjectMetadataKey k2) {
            String keyName1 = k1.getName();
            String keyName2 = k2.getName();
            return order * keyName1.compareTo(keyName2);
        }

    }
}
