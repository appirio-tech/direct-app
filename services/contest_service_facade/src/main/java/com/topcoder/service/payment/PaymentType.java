/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.payment;


/**
 * <p>
 * An ENUM which captures different type of payments to be processed. Current
 * implementation just supports two payment types viz: a) PayPal Credit Card. b)
 * TC Purchase order.
 * </p>
 *
 * @author shailendra_80
 */
public enum PaymentType {
    /**
     * Pay Pal Credit Card
     */
    PayPalCreditCard, 
    /**
     * TopCoder Pre-Approved Purchase Order.
     */
    TCPurchaseOrder;
}
