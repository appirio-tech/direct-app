/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.facade.contest.SoftwareContestPaymentResult;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.payment.PaymentType;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;

/**
 * <p>
 * This action will make a payment for the user using an existing billing account.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure bean in applicationContext.xml for Spring:</strong>
 *
 * <pre>
 * &lt;bean id=&quot;payByBillingAccountAction&quot;
 *     class=&quot;com.topcoder.service.actions.PayByBillingAccountAction&quot;&gt;
 *     &lt;property name=&quot;contestServiceFacade&quot; ref=&quot;contestServiceFacade&quot;&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <strong>An example of how to configure action in struts.xml:</strong>
 *
 * <pre>
 * &lt;action name=&quot;payByBillingAccountAction&quot; class=&quot;payByBillingAccountAction&quot;&gt;
 *     &lt;result name=&quot;input&quot;&gt;/main.jsp&lt;/result&gt;
 *     &lt;result name=&quot;success&quot;&gt;/main.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> The class is not thread safe because it's mutable by the setters and the values of this class
 * will change based on the request parameters. It's not required to be thread safe because in Struts 2 the actions
 * (different from Struts 1) are created for every request.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public class PayByBillingAccountAction extends PayContestAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.payByBillingAccountAction.";

    /**
     * <p>
     * Represents the DTO used by this action to save the payment.
     * </p>
     *
     * <p>
     * All setters and getters delegate to the related setters and getters of this DTO class. It's not null during the
     * setters' logic because it's constructed in the prepare method, which is called before any of the setters. It
     * can't be null.
     * </p>
     */
    private TCPurhcaseOrderPaymentData tcPurchaseOrderPaymentData;

    /**
     * Default constructor, creates new instance.
     */
    public PayByBillingAccountAction() {
    }

    /**
     * Prepares the action for use, initializing any necessary fields.
     */
    public void prepare() throws Exception {
        super.prepare();
        tcPurchaseOrderPaymentData = new TCPurhcaseOrderPaymentData();
    }

    /**
     * Processes the payment using the billing account and returns the payment result.
     *
     * @return the payment result after payment has been processed
     *
     * @throws Exception if some error occurs during method execution
     */
    protected PaymentResult processPayment() throws Exception {
        // get the studio competition or software competition, process the payment using purchase order data,
        // and return payment result
        if (isStudioCompetition()) {
            StudioCompetition studioCompetition = getContestServiceFacade().getContest(null, getContestId());
            ContestPaymentResult result = getContestServiceFacade().processContestPurchaseOrderPayment(null,
                studioCompetition, tcPurchaseOrderPaymentData);
            return result.getPaymentResult();
        } else {
            SoftwareCompetition softwareCompetition = getContestServiceFacade().getSoftwareContestByProjectId(null,
                getProjectId());
            SoftwareContestPaymentResult result = getContestServiceFacade().processContestPurchaseOrderSale(null,
                softwareCompetition, tcPurchaseOrderPaymentData);
            return result.getPaymentResult();
        }
    }

    /**
     * Method to send email when payment by billing account is finished, but it does nothing in this implementation.
     *
     * @param paymentResult the payment result
     */
    protected void sendEmail(PaymentResult paymentResult) {
        // do nothing
    }

    /**
     * Getter for the purchase order number.
     *
     * @return the purchase order number
     */
    public String getPoNumber() {
        return tcPurchaseOrderPaymentData.getPoNumber();
    }

    /**
     * Setter for the purchase order number. Struts 2 validation is used to check that the argument is between 1 and
     * 255 characters inclusive.
     *
     * @param poNumber the purchase order number
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "poNumberRange", fieldName = "poNumber", expression = "poNumber != null && poNumber.trim().length() >= 1 && poNumber.trim().length() <= 255", message = "poNumber cannot be null and must be between 1 and 255 characters")
    public void setPoNumber(String poNumber) {
        tcPurchaseOrderPaymentData.setPoNumber(poNumber);
    }

    /**
     * Getter for the client ID.
     *
     * @return the client ID
     */
    public long getClientId() {
        return tcPurchaseOrderPaymentData.getClientId();
    }

    /**
     * Setter for the client ID. Struts 2 validation is used to check that the argument is greater than 0.
     *
     * @param clientId the client ID
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "clientIdGreaterThanZero", fieldName = "clientId", expression = "clientId > 0", message = ActionHelper.GREATER_THAN_ZERO)
    public void setClientId(long clientId) {
        tcPurchaseOrderPaymentData.setClientId(clientId);
    }

    /**
     * Getter for the project ID.
     *
     * @return the project ID
     */
    @Override
    public long getProjectId() {
        return tcPurchaseOrderPaymentData.getProjectId();
    }

    /**
     * Setter for the project ID. Struts 2 validation is used to check that the argument is greater than 0.
     *
     * @param projectId the project ID
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "projectIdGreaterThanZero", fieldName = "projectId", expression = "projectId > 0", message = ActionHelper.GREATER_THAN_ZERO)
    @Override
    public void setProjectId(long projectId) {
        tcPurchaseOrderPaymentData.setProjectId(projectId);
    }

    /**
     * Getter for the payment type.
     *
     * @return the payment type
     */
    public PaymentType getType() {
        return tcPurchaseOrderPaymentData.getType();
    }

    /**
     * Setter for the payment type. Struts 2 validation is used to check that the argument is not null.
     *
     * @param type the payment type
     */
    @RequiredFieldValidator(key = KEY_PREFIX + "typeRequired", fieldName = "type", message = ActionHelper.CANNOT_BE_NULL)
    public void setType(PaymentType type) {
        tcPurchaseOrderPaymentData.setType(type);
    }
}
