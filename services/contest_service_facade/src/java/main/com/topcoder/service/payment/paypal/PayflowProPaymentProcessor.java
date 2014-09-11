/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.payment.paypal;

import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentProcessor;
import com.topcoder.service.payment.PaymentResult;

import org.jboss.logging.Logger;

import paypal.payflow.BaseTransaction;
import paypal.payflow.BillTo;
import paypal.payflow.CardTender;
import paypal.payflow.ClientInfo;
import paypal.payflow.Context;
import paypal.payflow.CreditCard;
import paypal.payflow.Currency;
import paypal.payflow.FraudResponse;
import paypal.payflow.Invoice;
import paypal.payflow.PayflowConnectionData;
import paypal.payflow.PayflowConstants;
import paypal.payflow.PayflowUtility;
import paypal.payflow.Response;
import paypal.payflow.SDKProperties;
import paypal.payflow.SaleTransaction;
import paypal.payflow.VoidTransaction;
import paypal.payflow.TransactionResponse;
import paypal.payflow.UserInfo;

import java.text.DecimalFormat;

import java.util.Locale;


/**
 * This class uses PayFlow payment system to process Paypal payments. Update in
 * version 1.1 Add the logException method to log and throw the payment
 * exception. Add the log for public methods
 *
 * @author Margarita, squarY
 * @version 1.1
 * @since BUGR-1239
 */
public class PayflowProPaymentProcessor implements PaymentProcessor {
    /**
     * This variable stores such info as username, password, partner and vendor
     * info.
     */
    private final UserInfo userInfo;

    /**
     * Logger instance.
     */
    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * Constructs the payment processor with necessary authentication info.
     *
     * @param hostAddress
     *            Host address.
     * @param user
     *            PayFlow username.
     * @param partner
     *            PayFlow partner name.
     * @param vendor
     *            PayFlow vendor name.
     * @param password
     *            PayFlow password.
     */
    public PayflowProPaymentProcessor(String hostAddress, String user,
        String partner, String vendor, String password) {
        logger.debug("payflowProPaymentProcessor");
        logger.debug("hostAddress=" + hostAddress);
        logger.debug("user=" + user);
        logger.debug("vendor=" + vendor);
        logger.debug("password=" + password);
        logger.debug("user=" + user);

        userInfo = new UserInfo(user, vendor, partner, password);

        SDKProperties.setHostAddress(hostAddress);
        SDKProperties.setHostPort(443);
        SDKProperties.setTimeOut(20);
        logger.debug("Exit payflowProPaymentProcessor");
    }

    /**
     * An implementation method of the <code>PaymentProcessor</code> interface.
     *
     * @param payment
     *            the payment data that need to be processed.
     * @return a <code>PaymentResult</code> the payment result. In this case
     *         transaction id from payflow.
     * @throws PaymentException
     *             on payment failure, it captures Error message and Error code
     *             for the failure case.
     */
    public PaymentResult process(PaymentData payment) throws PaymentException {
        logger.debug("process (" + payment.getType().name() + ")");

        CreditCardPaymentData cardPaymentData = (CreditCardPaymentData) payment;

        // Create the Payflow Connection data object with the required
        // connection details.
        // The PAYFLOW_HOST and CERT_PATH are properties defined within
        // SDKProperties.
        PayflowConnectionData connection = new PayflowConnectionData();

        // Create a new Invoice data object with the Amount, Billing Address
        // etc. details.
        Invoice inv = new Invoice();
        Double amount = new Double(cardPaymentData.getAmount());
        DecimalFormat twoDForm = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.US); // set US locale! BUGR-1398
        twoDForm.applyLocalizedPattern("#.##");
        amount = Double.valueOf(twoDForm.format(amount));

        // Set amount
        Currency amt = new Currency(amount, "USD");
        inv.setAmt(amt);
        inv.setComment1(cardPaymentData.getComment1());
        inv.setComment2(cardPaymentData.getComment2());

        // Set the Billing Address details.
        BillTo bill = new BillTo();
        bill.setStreet(cardPaymentData.getAddress());
        bill.setZip(cardPaymentData.getZipCode());
        bill.setFirstName(cardPaymentData.getFirstName());
        bill.setLastName(cardPaymentData.getLastName());
        bill.setCity(cardPaymentData.getCity());
        bill.setBillToCountry(cardPaymentData.getCountry());
        bill.setState(cardPaymentData.getState());
        bill.setEmail(cardPaymentData.getEmail());
        inv.setBillTo(bill);

        // Create a new Payment Device - Credit Card data object.
        // The input parameters are Credit Card No. and Expiry Date for the
        // Credit Card.
        // Should be mmyy
        String expiry = String.format("%1$02d%2$02d",
                Integer.parseInt(cardPaymentData.getCardExpiryMonth()),
                Integer.parseInt(cardPaymentData.getCardExpiryYear()
                                                .substring(cardPaymentData.getCardExpiryYear()
                                                                          .length() -
                        2)));
        System.out.println(expiry);

        CreditCard cc = new CreditCard(cardPaymentData.getCardNumber(), expiry);
        cc.setCvv2(cardPaymentData.getCsc()); // BUGR-1398
                                              // Create a new Tender - Card Tender data object.

        CardTender card = new CardTender(cc);

        // Create a new Sale Transaction.
        BaseTransaction trans = new SaleTransaction(userInfo, connection, inv,
                card, PayflowUtility.getRequestId()); // BUGR-1398
        trans.setVerbosity("MEDIUM"); // BUGR-1398
                                      // Submit the Transaction

        Response resp = trans.submitTransaction();

        // Display the transaction response parameters.
        if (resp != null) {
            // Get the Transaction Response parameters.
            TransactionResponse trxnResponse = resp.getTransactionResponse();

            // Create a new Client Information data object.
            ClientInfo clInfo = new ClientInfo();

            // Set the ClientInfo object of the transaction object.
            trans.setClientInfo(clInfo);

            System.out.println("--" + PayflowUtility.getStatus(resp));

            if (trxnResponse != null) {
                System.out.println("RESULT = " + trxnResponse.getResult());
                System.out.println("PNREF = " + trxnResponse.getPnref());
                System.out.println("RESPMSG = " + trxnResponse.getRespMsg());
                System.out.println("AUTHCODE = " + trxnResponse.getAuthCode());
                System.out.println("AVSADDR = " + trxnResponse.getAvsAddr());
                System.out.println("AVSZIP = " + trxnResponse.getAvsZip());
                System.out.println("IAVS = " + trxnResponse.getIavs());
                System.out.println("CVV2MATCH = " +
                    trxnResponse.getCvv2Match());
                System.out.println("DUPLICATE = " +
                    trxnResponse.getDuplicate());

                if (trxnResponse.getResult() == 0) { // success

                    PaymentResult paymentResult = new PaymentResult();
                    paymentResult.setReferenceNumber(trxnResponse.getPnref());
                    logger.debug("Exit process (" + payment.getType().name() +
                        ")");

                    return paymentResult;
                }
            } else {
                throw logException(new PaymentException(
                        "No transaction response"));
            }

            // Get the Fraud Response parameters.
            FraudResponse fraudResp = resp.getFraudResponse();

            if (fraudResp != null) {
                System.out.println("PREFPSMSG = " + fraudResp.getPreFpsMsg());
                System.out.println("POSTFPSMSG = " + fraudResp.getPostFpsMsg());
            }

            // Get the Transaction Context and check for any contained SDK
            // specific errors (optional code).
            Context transCtx = resp.getContext();

            if ((transCtx != null) && (transCtx.getErrorCount() > 0)) {
                System.out.println("\nTransaction Errors = " +
                    transCtx.toString());
            }

            throw logException(new PaymentException(trxnResponse.getRespMsg()));
        }

        throw logException(new PaymentException("No response"));
    }

    /**
     * <p>
     * Void a previous payment. On success it should
     * return <code>PaymentResult</code> On failure it throws
     * <code>PaymentException</code>
     * </p>
     *
     * @param referenceNumber
     *            the referenceNumber of the to be voided payment
     * @return a <code>PaymentResult</code> the payment result.
     * @throws PaymentException
     *             on payment failure. It captures Error message and Error code
     *             for the failure case.
     */
    public PaymentResult voidPayment(String referenceNumber) throws PaymentException {
        logger.debug("void (" + referenceNumber + ")");

        

        // Create the Payflow Connection data object with the required
        // connection details.
        // The PAYFLOW_HOST and CERT_PATH are properties defined within
        // SDKProperties.
        PayflowConnectionData connection = new PayflowConnectionData();

        
        // Create a new Sale Transaction.
        BaseTransaction trans = new VoidTransaction(referenceNumber, userInfo, connection, PayflowUtility.getRequestId()); 
        trans.setVerbosity("MEDIUM"); 

        Response resp = trans.submitTransaction();

        // Display the transaction response parameters.
        if (resp != null) {
            // Get the Transaction Response parameters.
            TransactionResponse trxnResponse = resp.getTransactionResponse();

            // Create a new Client Information data object.
            ClientInfo clInfo = new ClientInfo();

            // Set the ClientInfo object of the transaction object.
            trans.setClientInfo(clInfo);

            System.out.println("--" + PayflowUtility.getStatus(resp));

            if (trxnResponse != null) {
                System.out.println("RESULT = " + trxnResponse.getResult());
                System.out.println("PNREF = " + trxnResponse.getPnref());
                System.out.println("RESPMSG = " + trxnResponse.getRespMsg());
                System.out.println("AUTHCODE = " + trxnResponse.getAuthCode());
                System.out.println("AVSADDR = " + trxnResponse.getAvsAddr());
                System.out.println("AVSZIP = " + trxnResponse.getAvsZip());
                System.out.println("IAVS = " + trxnResponse.getIavs());
                System.out.println("CVV2MATCH = " +
                    trxnResponse.getCvv2Match());
                System.out.println("DUPLICATE = " +
                    trxnResponse.getDuplicate());

                if (trxnResponse.getResult() == 0) { // success

                    PaymentResult paymentResult = new PaymentResult();
                    paymentResult.setReferenceNumber(trxnResponse.getPnref());
                    logger.debug("Exit process (" + referenceNumber + ")");

                    return paymentResult;
                }
            } else {
                throw logException(new PaymentException(
                        "No transaction response"));
            }

            // Get the Fraud Response parameters.
            FraudResponse fraudResp = resp.getFraudResponse();

            if (fraudResp != null) {
                System.out.println("PREFPSMSG = " + fraudResp.getPreFpsMsg());
                System.out.println("POSTFPSMSG = " + fraudResp.getPostFpsMsg());
            }

            // Get the Transaction Context and check for any contained SDK
            // specific errors (optional code).
            Context transCtx = resp.getContext();

            if ((transCtx != null) && (transCtx.getErrorCount() > 0)) {
                System.out.println("\nTransaction Errors = " +
                    transCtx.toString());
            }

            throw logException(new PaymentException(trxnResponse.getRespMsg()));
        }

        throw logException(new PaymentException("No response"));
    }

    /**
     * Main method. Contains info for testing purposes.
     *
     * @param args
     * @throws PaymentException
     */
    public static void main(String[] args) throws PaymentException {
        PayflowProPaymentProcessor proc = new PayflowProPaymentProcessor("pilot-payflowpro.paypal.com",
                "tcTestAccount2", "PayPal", "tcTestAccount2", "password123");

        SDKProperties.setLogFileName("payflow_java.log");
        SDKProperties.setLoggingLevel(PayflowConstants.SEVERITY_WARN);
        SDKProperties.setMaxLogFileSize(100000);

        CreditCardPaymentData c = new CreditCardPaymentData();
        c.setCardNumber("5555555555554444"); // use any other to be declined
        c.setAmount("199.00"); // use more than 1000 to be declined
        c.setCardExpiryMonth("08"); // use past date to be declined
        c.setCardExpiryYear("09");
        c.setFirstName("John");
        c.setLastName("McClaine");
        c.setAddress("24285 Elm");
        c.setZipCode("00382");
        c.setCsc("422"); // use 1-300 to succeed and 301-600 to fail csc
                         // BUGR-1398

        System.out.print("reference number=" +
            proc.process(c).getReferenceNumber());
    }

    /**
     * Log the exception
     *
     * @param e
     *            the payment exception
     * @return payment exception
     * @since 1.1
     */
    private PaymentException logException(PaymentException e) {
        logger.error(e.getMessage(), e);

        return e;
    }
}
