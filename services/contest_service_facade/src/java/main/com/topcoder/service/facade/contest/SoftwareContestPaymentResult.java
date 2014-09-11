package com.topcoder.service.facade.contest;

import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.project.SoftwareCompetition;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * This class contains payment result and software competition data. Its
 * instances are created in reply to processing contest payment.
 *
 * @author Margarita
 * @version 1.0
 * @since BUGR-1682
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "softwareContestPaymentResult", propOrder =  {
    "paymentResult", "softwareCompetition"}
)
public class SoftwareContestPaymentResult implements Serializable {
    /**
     * Generated field.
     */
    private static final long serialVersionUID = 1213000777032191633L;

    /**
     * Payment result instance.
     */
    private PaymentResult paymentResult;

    /**
     * Software competition data for particular contest.
     */
    private SoftwareCompetition softwareCompetition;

    /**
     * Empty constructor.
     */
    public SoftwareContestPaymentResult() {
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

    /**
     * Returns the software competition.
     *
     * @return the software competition
     */
    public SoftwareCompetition getSoftwareCompetition() {
        return softwareCompetition;
    }

    /**
     * Sets the new value of software competition
     *
     * @param softwareCompetition
     *            software competition
     */
    public void setSoftwareCompetition(SoftwareCompetition softwareCompetition) {
        this.softwareCompetition = softwareCompetition;
    }
}
