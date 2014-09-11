package com.topcoder.service.facade.contest;

import com.topcoder.service.payment.PaymentResult;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * This class contains payment result and contest data. Its instances are
 * created in reply to processing contest payment.
 *
 * <p>
 * Version 1.0.1 (TCCC-3658) Change notes:
 *   <ol>
 *     <li>Removed dependencies to studio components</li>
 *   </ol>
 * </p>
 * @author Margarita, lmmortal
 * @version 1.0.1
 * @since BUGR-1494
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestPaymentResult", propOrder =  {
    "paymentResult", "contestData"}
)
public class ContestPaymentResult implements Serializable {
    /**
     * Generated field.
     */
    private static final long serialVersionUID = -1232655979944585284L;

    /**
     * Payment result instance.
     */
    private PaymentResult paymentResult;

    /**
     * Empty constructor.
     */
    public ContestPaymentResult() {
    }

    /**
     * Returns the payment result.
     *
     * @return payment result
     */
    public PaymentResult getPaymentResult() {
        return paymentResult;
    }

    /**
     * Sets the new value of payment result.
     *
     * @param paymentResult
     *            the new value
     */
    public void setPaymentResult(PaymentResult paymentResult) {
        this.paymentResult = paymentResult;
    }
}
