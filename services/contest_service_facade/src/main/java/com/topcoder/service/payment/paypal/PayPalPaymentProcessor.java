/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.payment.paypal;

import com.paypal.sdk.exceptions.PayPalException;
import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.profiles.ProfileFactory;
import com.paypal.sdk.services.CallerServices;

import com.paypal.soap.api.AckCodeType;
import com.paypal.soap.api.AddressType;
import com.paypal.soap.api.BasicAmountType;
import com.paypal.soap.api.CountryCodeType;
import com.paypal.soap.api.CreditCardDetailsType;
import com.paypal.soap.api.CreditCardTypeType;
import com.paypal.soap.api.CurrencyCodeType;
import com.paypal.soap.api.DoDirectPaymentRequestDetailsType;
import com.paypal.soap.api.DoDirectPaymentRequestType;
import com.paypal.soap.api.DoDirectPaymentResponseType;
import com.paypal.soap.api.PayerInfoType;
import com.paypal.soap.api.PaymentActionCodeType;
import com.paypal.soap.api.PaymentDetailsType;
import com.paypal.soap.api.PersonNameType;

import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentProcessor;
import com.topcoder.service.payment.PaymentResult;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * <p>
 * This implementation class processes the payment (<code>PaymentData</code>)
 * through PayPal SOAP APIs
 * </p>
 *
 * @author TCSDEVELOPER
 */
public class PayPalPaymentProcessor implements PaymentProcessor {
    /**
     * A instance to PayPal SOAP API caller services.
     */
    private CallerServices paypalServicesCaller;

    /**
     * <p>
     * It relies on PostConstruct mechanism to initialize the necessary
     * resources.
     * </p>
     */
    public PayPalPaymentProcessor(String apiUserName, String apiPassword,
        String apiSignature, String apiEnvironment) throws PaymentException {
        try {
            Logger.getLogger(this.getClass())
                  .debug("PayPal API Username: " + apiUserName);
            Logger.getLogger(this.getClass())
                  .debug("PayPal API Password: " + apiPassword);
            Logger.getLogger(this.getClass())
                  .debug("PayPal API Signature: " + apiSignature);
            Logger.getLogger(this.getClass())
                  .debug("PayPal API Environment: " + apiEnvironment);

            paypalServicesCaller = new CallerServices();

            APIProfile profile = ProfileFactory.createSignatureAPIProfile();

            // a test API profile for this assembly.
            profile.setAPIUsername(apiUserName);
            profile.setAPIPassword(apiPassword);
            profile.setSignature(apiSignature);
            profile.setEnvironment(apiEnvironment);
            paypalServicesCaller.setAPIProfile(profile);
        } catch (PayPalException e) {
            throw new PaymentException(
                "Error in initializing PayPalPaymentProcessor:" +
                e.getMessage(), e);
        }
    }

    /**
     * <p>
     * An implementation method of the <code>PaymentProcessor</code> interface.
     * This method simply delegates the responsibility to internal
     * doDirectPayment(..) method. On successful payment, internal method
     * returns PayPal transaction id. This method wraps the transaction id in
     * PaymentResult and return it to the caller.
     * </p>
     *
     * @param payment
     *            the payment data that need to be processed.
     * @return a <code>PaymentResult</code> the payment result. In this case
     *         transaction id from paypal.
     * @throws PaymentException
     *             on payment failure, it captures Error message and Error code
     *             for the failure case.
     */
    public PaymentResult process(PaymentData payment) throws PaymentException {
        try {
            String transactionId = doDirectPayment((CreditCardPaymentData) payment);
            PaymentResult result = new PaymentResult();
            result.setReferenceNumber(transactionId);

            return result;
        } catch (PayPalException e) {
            throw new PaymentException("Error in processing payment through PayPalPaymentProcessor",
                e);
        }
    }

    /**
     * <p>
     * This internal method processes the given
     * <code>CreditCardPaymentData</code> through PayPal SOAP SDK API.
     * Implementation simply sets various bean properties, as required by
     * DoDirectPayment PayPal API and then calls the API.
     * </p>
     *
     * @param paymentData
     *            the given <code>CreditCardPaymentData</code> that need to be
     *            processed.
     * @return on success it returns the PayPal transaction id.
     * @throws PayPalException
     *             on some paypal internal api related exceptions/errors.
     * @throws PaymentException
     *             on payment failure, it captures Error message and Error code
     *             for the failure case.
     */
    private String doDirectPayment(CreditCardPaymentData paymentData)
        throws PayPalException, PaymentException {
        DoDirectPaymentRequestType request = new DoDirectPaymentRequestType();
        DoDirectPaymentRequestDetailsType details = new DoDirectPaymentRequestDetailsType();

        // determine the card type.
        CreditCardTypeType cardType = null;

        if (paymentData.getCardType().equalsIgnoreCase("Visa")) {
            cardType = CreditCardTypeType.Visa;
        } else if (paymentData.getCardType().equalsIgnoreCase("MasterCard")) {
            cardType = CreditCardTypeType.MasterCard;
        } else if (paymentData.getCardType().equalsIgnoreCase("Amex")) {
            cardType = CreditCardTypeType.Amex;
        }

        System.out.println("-----card number --" + paymentData.getCardNumber());

        // set card number, expiry month & year.
        CreditCardDetailsType creditCard = new CreditCardDetailsType();
        creditCard.setCreditCardNumber(paymentData.getCardNumber());
        creditCard.setCreditCardType(cardType);
        creditCard.setExpMonth(new Integer(paymentData.getCardExpiryMonth()));
        creditCard.setExpYear(new Integer(paymentData.getCardExpiryYear()));

        // set payer info
        PayerInfoType cardOwner = new PayerInfoType();

        // right now the country is hard-coded (as discussed in forum thread)
        cardOwner.setPayerCountry(CountryCodeType.US);

        // set payer address.
        AddressType address = new AddressType();
        address.setPostalCode(paymentData.getZipCode());
        address.setStateOrProvince(paymentData.getState());
        address.setStreet1(paymentData.getAddress());
        address.setCountryName(paymentData.getCountry());

        // right now the country is hard-coded (as discussed in forum thread)
        address.setCountry(CountryCodeType.US);
        address.setCityName(paymentData.getCity());
        address.setPhone(paymentData.getPhone());
        cardOwner.setAddress(address);

        // set payer name etc.
        PersonNameType payerName = new PersonNameType();
        payerName.setFirstName(paymentData.getFirstName());
        payerName.setLastName(paymentData.getLastName());
        cardOwner.setPayerName(payerName);

        creditCard.setCardOwner(cardOwner);
        details.setCreditCard(creditCard);

        // set payer ip address and session id etc.
        details.setIPAddress(paymentData.getIpAddress());

        if (paymentData.getSessionId() != null) {
            details.setMerchantSessionId(paymentData.getSessionId());
        }

        // mark this payment as final.
        details.setPaymentAction(PaymentActionCodeType.Sale);

        // capture the payment details - amount, current etc.
        PaymentDetailsType paymentDetails = new PaymentDetailsType();

        BasicAmountType orderTotal = new BasicAmountType();
        orderTotal.setCurrencyID(CurrencyCodeType.USD);
        orderTotal.set_value(paymentData.getAmount());
        paymentDetails.setOrderTotal(orderTotal);

        details.setPaymentDetails(paymentDetails);
        request.setDoDirectPaymentRequestDetails(details);

        // process the payment through paypal soap api.
        DoDirectPaymentResponseType response = (DoDirectPaymentResponseType) paypalServicesCaller.call("DoDirectPayment",
                request);

        // if response is successful then return the transaction id.
        if (response.getAck().equals(AckCodeType.Success) ||
                response.getAck().equals(AckCodeType.SuccessWithWarning)) {
            return response.getTransactionID();
        } else {
            // if response is failure then throw the PaymentException.
            // response code and response long message from paypal is used in
            // the exception.
            throw new PaymentException(response.getErrors()[0].getErrorCode()
                                                              .toString(),
                response.getErrors()[0].getLongMessage());
        }
    }
}
