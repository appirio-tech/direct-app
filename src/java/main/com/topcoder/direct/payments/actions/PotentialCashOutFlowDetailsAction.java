/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.actions;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.payments.DTO.PotentialCashOutFlowDTO;
import com.topcoder.direct.payments.entities.CashOutflowPotential;
import com.topcoder.direct.payments.entities.CashOutflowPotentialResult;
import com.topcoder.direct.payments.entities.PaymentMethod;
import com.topcoder.direct.payments.services.ConfigurationException;
import com.topcoder.direct.payments.services.PaymentsService;
import com.topcoder.direct.payments.services.ServiceException;
import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>This action is used to fetch potential cash out details data.</p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version  1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 */
public class PotentialCashOutFlowDetailsAction extends BaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = PotentialCashOutFlowDetailsAction.class.getName();

    /**
     * The day ranges.
     */
    private static final List<Integer> DAY_RANGES;

    /**
     * The name of paypal method.
     */
    private static final String PAYPAL_METHOD_NAME = "Paypal";

    /**
     * The name of payoneer method.
     */
    private static final String PAYONEER_METHOD_NAME = "Payoneer";

    /**
     * The name of western union method.
     */
    private static final String WESTERN_UNION_METHOD_NAME = "Western Union";

    /**
     * The time of a day.
     */
    private static final long DAY = 1000 * 60 * 60 * 24;

    /**
     * Initiate day ranges.
     */
    static {
        DAY_RANGES = new ArrayList<Integer>();
        DAY_RANGES.add(0);
        DAY_RANGES.add(7);
        DAY_RANGES.add(15);
        DAY_RANGES.add(30);
    }

    /**
     * The payments service.
     */
    private PaymentsService paymentsService;

    /**
     * The paypal payment method id.
     */
    private int paypalPaymentMethodId;

    /**
     * The payoneer payment method id.
     */
    private int payoneerPaymentMethodId;

    /**
     * The western payment method id.
     */
    private int westernPaymentMethodId;

    /**
     * Default constructor.
     */
    public void PotentialCashOutFlowDetailsAction() {
    }

    /**
     * Handle the request, fetch data and set to result.
     *
     * @throws PaymentActionException if any exception occurs while fetching data by service.
     */
    public void executeAction() throws PaymentActionException {
        final String signature = CLASS_NAME + ".executeAction()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);

        try {
            // set start date and end date
            int minDateDiff = Integer.MAX_VALUE, maxDateDiff = Integer.MIN_VALUE;

            for (int diff : DAY_RANGES) {
                minDateDiff = Math.min(minDateDiff, diff);
                maxDateDiff = Math.max(maxDateDiff, diff);
            }

            Date startDate = getDateWithDiffDay(minDateDiff);
            Date endDate = getDateWithDiffDay(maxDateDiff);

            // fetch data
            Map<Integer, CashOutflowPotentialResult> cashOutflowPotentialResults =
                    paymentsService.getPotentialMemberPayments(startDate, endDate,
                            new int[] { paypalPaymentMethodId, payoneerPaymentMethodId, westernPaymentMethodId});

            List<PotentialCashOutFlowDTO> potentialCashOutFlowDTOs = new ArrayList<PotentialCashOutFlowDTO>();

            List<Integer> methods = new ArrayList<Integer>(cashOutflowPotentialResults.keySet());
            Collections.sort(methods);

            // aggregate potential payments data by method and date interval
            DateTime startTime = new DateTime(startDate).withTime(0, 0, 0, 0);
            for (int methodId : methods) {
                PotentialCashOutFlowDTO dto = null;
                if (methodId == PaymentMethod.PAYPAL.getPaymentMethodId()) {
                    dto = new PotentialCashOutFlowDTO(PAYPAL_METHOD_NAME);
                } else if (methodId == PaymentMethod.PAYONEER.getPaymentMethodId()) {
                    dto = new PotentialCashOutFlowDTO((PAYONEER_METHOD_NAME));
                } else if (methodId == PaymentMethod.WESTERN_UNION.getPaymentMethodId()) {
                    dto = new PotentialCashOutFlowDTO(WESTERN_UNION_METHOD_NAME);
                }
                // should never happen, check anyway
                if (dto == null) {
                    throw LoggingWrapperUtility.logException(getLogger(), signature,
                            new PaymentActionException("Unknown payment method id"));
                }

                double[] potentialPayments = new double[DAY_RANGES.size()];

                CashOutflowPotentialResult result = cashOutflowPotentialResults.get(methodId);
                for (CashOutflowPotential potential : result.getItems()) {
                    int diffDay = Days.daysBetween(startTime,
                            new DateTime(potential.getDueDate())).getDays();

                    for (int i = 0; i < DAY_RANGES.size(); i++) {
                        if (diffDay <= DAY_RANGES.get(i)) {
                            potentialPayments[i] += potential.getPayments();
                        }
                    }
                }
                dto.setCashAmounts(potentialPayments);
                potentialCashOutFlowDTOs.add(dto);
            }

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("days", DAY_RANGES);
            data.put("payments", potentialCashOutFlowDTOs);

            setResult(data);
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (ServiceException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new PaymentActionException("Fail to get potential payment details", e));
        }


    }

    /**
     * Check configurations.
     *
     * @throws ConfigurationException if payments service of method id is not set properly
     *           or logger is null.
     */
    @PostConstruct
    public void checkConfiguration() {
        super.checkConfiguration();
        ValidationUtility.checkNotNull(paymentsService, "paymentsService", ConfigurationException.class);
        ValidationUtility.checkPositive(paypalPaymentMethodId, "paypalPaymentMethodId",
                ConfigurationException.class);
        ValidationUtility.checkPositive(payoneerPaymentMethodId, "payoneerPaymentMethodId",
                ConfigurationException.class);
        ValidationUtility.checkPositive(westernPaymentMethodId, "westernPaymentMethodId",
                ConfigurationException.class);
    }

    /**
     * Set the payments service.
     *
     * @param paymentsService the payments service to set.
     */
    public void setPaymentsService(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    /**
     * Set the paypal payment method id.
     *
     * @param paypalPaymentMethodId the method id to set
     */
    public void setPaypalPaymentMethodId(int paypalPaymentMethodId) {
        this.paypalPaymentMethodId = paypalPaymentMethodId;
    }

    /**
     * Set the payoneer payment method id.
     *
     * @param payoneerPaymentMethodId the method id to set
     */
    public void setPayoneerPaymentMethodId(int payoneerPaymentMethodId) {
        this.payoneerPaymentMethodId = payoneerPaymentMethodId;
    }

    /**
     * Set the western payment method id.
     *
     * @param westernPaymentMethodId the method id to set
     */
    public void setWesternPaymentMethodId(int westernPaymentMethodId) {
        this.westernPaymentMethodId = westernPaymentMethodId;
    }
}

