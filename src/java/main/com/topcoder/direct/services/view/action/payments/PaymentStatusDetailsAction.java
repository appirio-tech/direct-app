/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.payments;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.view.dto.payments.PaymentStatusDTO;
import com.topcoder.direct.services.payments.entities.PaymentsByStatus;
import com.topcoder.direct.services.payments.entities.PaymentsByStatusResult;
import com.topcoder.direct.services.payments.ConfigurationException;
import com.topcoder.direct.services.payments.PaymentsService;
import com.topcoder.direct.services.payments.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * <p>This action is used to fetch payments status details data.</p>
 * <p/>
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * <p>
 * Version 1.1 (BUGR-8670 - TopCoder Direct Member Payments Dashboard Update) Change notes:
 *   <ol>
 *     <li>Added field {@link #EARLIEST_START_DATE }.</li>
 *     <li>Updated method {@link #executeAction()}.</li>
 *   </ol>
 * </p>
 *
 * @author TCSASSEMBLER, notpad
 * @version 1.1
 */
public class PaymentStatusDetailsAction extends BaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = PaymentStatusDetailsAction.class.getName();

    /**
     * The days to separate payments status.
     */
    private List<Integer> paymentsByStatusByDays;

    /**
     * The payments service.
     */
    private PaymentsService paymentsService;

    /**
     * The earliest start date.
     */
    private static final String EARLIEST_START_DATE = "2012-01-01"; 
     
    /**
     * Default constructor.
     */
    public PaymentStatusDetailsAction() {
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
            List<PaymentStatusDTO> results = new ArrayList<PaymentStatusDTO>();

            if (paymentsByStatusByDays.size() > 0) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = df.parse(EARLIEST_START_DATE);
                Date endDate = getDateWithDiffDay(-(paymentsByStatusByDays.get(0) + 1));
                startDate = new DateTime(startDate).withTime(0, 0, 0, 0).toDate();
                endDate = new DateTime(endDate).withTime(23, 59, 59, 59).toDate();
                PaymentStatusDTO dto = new PaymentStatusDTO();
                dto.setStartDate(startDate);
                dto.setEndDate(endDate);
                dto.setEndDateStr(paymentsByStatusByDays.get(0) + 1);
                results.add(dto);
            }
            
            // create payment status DTOs by date range
            for (int i = 0; i < paymentsByStatusByDays.size() - 1; i++) {
                Date startDate = getDateWithDiffDay(-paymentsByStatusByDays.get(i));
                Date endDate = getDateWithDiffDay(-(paymentsByStatusByDays.get(i + 1) + 1));

                startDate = new DateTime(startDate).withTime(0, 0, 0, 0).toDate();
                endDate = new DateTime(endDate).withTime(23, 59, 59, 59).toDate();

                PaymentStatusDTO dto = new PaymentStatusDTO();
                dto.setStartDate(startDate);
                dto.setEndDate(endDate);
                dto.setStartDateStr(paymentsByStatusByDays.get(i));
                dto.setEndDateStr(paymentsByStatusByDays.get(i + 1) + 1);
                results.add(dto);
            }

            Date startDate = results.get(0).getStartDate();
            Date endDate = results.get(results.size() - 1).getEndDate();

            // fetch and aggregate data
            List<PaymentsByStatusResult> statusResults = paymentsService.getPaymentsByStatus(startDate, endDate);
            aggregatePaymentsByStatus(statusResults, results);
            setResult(results);

            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new PaymentActionException("Fail to get payment status details", e));
        }
    }

    /**
     * Aggregate payments by status.
     *
     * @param statusResults the status result
     * @param DTOs          the DTOs to hold result data
     */
    private void aggregatePaymentsByStatus(List<PaymentsByStatusResult> statusResults, List<PaymentStatusDTO> DTOs) {
        // there should always be a PaymentsByStatusResult in the list,
        // check anyway
        if (statusResults != null && statusResults.size() > 0) {
            PaymentsByStatusResult paymentsByStatusResult = statusResults.get(0);

            for (PaymentsByStatus paymentsByStatus : paymentsByStatusResult.getPaymentsByStatus()) {
                long updatedTime = paymentsByStatus.getStatusUpdateDate().getTime();
                PaymentStatusDTO paymentStatusDTO = DTOs.get(0);
                boolean find = false;
                for (PaymentStatusDTO dto : DTOs) {
                    if (updatedTime >= dto.getStartDate().getTime()
                            && updatedTime <= dto.getEndDate().getTime()) {
                        paymentStatusDTO = dto;
                        find = true;
                        break;
                    }
                }

                if (!find) {
                    // should never happen
                    continue;
                }

                paymentStatusDTO.addOwedOrAccruingPayments(paymentsByStatus.getOwedOrAccruingPayments());
                paymentStatusDTO.addOnHoldPayments(paymentsByStatus.getOnHoldPayments());
                paymentStatusDTO.addPaidPayments(paymentsByStatus.getPaidPayments());
                paymentStatusDTO.addPaymentsEnteredIntoPaymentSystem(
                        paymentsByStatus.getPaymentsEnteredIntoPaymentSystem());
            }
        }
    }

    /**
     * Check configurations.
     *
     * @throws ConfigurationException if paymentsService or paymentsByStatusByDays are not set properly
     *                                or logger is null.
     */
    @PostConstruct
    public void checkConfiguration() {
        super.checkConfiguration();
        ValidationUtility.checkNotNull(paymentsService, "paymentsService", ConfigurationException.class);
        ValidationUtility.checkNotNullNorEmpty(paymentsByStatusByDays, "paymentsByStatusByDays",
                ConfigurationException.class);
        // there should at least 1 days
        ValidationUtility.checkGreaterThan(paymentsByStatusByDays.size(), 1, true,
                "the size of paymentsByStatusByDays", ConfigurationException.class);

        for (int day : paymentsByStatusByDays) {
            ValidationUtility.checkGreaterThan(day, -1, true,
                    "day", ConfigurationException.class);
        }
    }

    /**
     * Get the payments service.
     *
     * @return the payments service
     */
    public PaymentsService getPaymentsService() {
        return paymentsService;
    }

    /**
     * Set the payments service.
     *
     * @param paymentsService the payments service to set
     */
    public void setPaymentsService(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    /**
     * Set days used to separate payments status.
     *
     * @param paymentsByStatusByDaysStr the days string.
     * @throws ConfigurationException if paymentsByStatusByDaysStr is invalid
     */
    public void setPaymentsByStatusByDays(String paymentsByStatusByDaysStr) {
        if (paymentsByStatusByDaysStr != null) {
            paymentsByStatusByDays = new LinkedList<Integer>();
            String[] days = paymentsByStatusByDaysStr.split(",");
            for (String day : days) {
                if (StringUtils.isNotEmpty(day)) {
                    try {
                        paymentsByStatusByDays.add(Integer.parseInt(day));
                    } catch (NumberFormatException e) {
                        throw new ConfigurationException("fail to set paymentsByStatusByDays for invalid input.",
                                e);
                    }
                }
            }
            Collections.sort(paymentsByStatusByDays, Collections.reverseOrder());

            // add current day
            paymentsByStatusByDays.add(-1);
        }

    }
}

