/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.payments;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.payments.entities.PaymentHistory;
import com.topcoder.direct.services.payments.entities.PaymentHistoryCriteria;
import com.topcoder.direct.services.payments.entities.PaymentHistoryResult;
import com.topcoder.direct.services.payments.ConfigurationException;
import com.topcoder.direct.services.payments.PaymentsService;
import com.topcoder.direct.services.payments.ServiceException;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>This action is used to fetch payments history data.</p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version  1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 */
public class PaymentsHistoryAction extends BaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = PaymentsHistoryAction.class.getName();

    /**
     * The month of history data to fetch.
     */
    private static final int HISTORY_MONTH = 6;

    /**
     * The month format.
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MMM. yyyy");

    /**
     * The date format used for current month.
     */
    private static final DateFormat NUMBER_DATE_FORMAT = new SimpleDateFormat("M.d.yyyy");

    /**
     * The payments service.
     */
    private PaymentsService paymentsService;

    /**
     * Default constructor.
     */
    public PaymentsHistoryAction() {
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
            Date endDate = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, -(HISTORY_MONTH - 1));
            Date startDate = calendar.getTime();

            // create criteria and fetch payment history data
            PaymentHistoryCriteria criteria = new PaymentHistoryCriteria();
            criteria.setStartDate(startDate);
            criteria.setEndDate(endDate);
            List<PaymentHistoryResult> paymentHistoryResults = paymentsService.getPaymentHistory(criteria);

            Map<String, Object> result = new HashMap<String, Object>();

            // create column names
            List<String> columns = new ArrayList<String>();
            List<Date> monthList = new ArrayList<Date>();
            Date now = new Date();
            calendar.setTime(now);
            if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
                columns.add(NUMBER_DATE_FORMAT.format(now));
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                columns.add(NUMBER_DATE_FORMAT.format(calendar.getTime()) + " - " + NUMBER_DATE_FORMAT.format(now));
            }
            monthList.add(now);

            for (int i = 1; i < HISTORY_MONTH; i++) {
                calendar.setTime(now);
                calendar.add(Calendar.MONTH, -i);
                columns.add(DATE_FORMAT.format(calendar.getTime()));
                monthList.add(calendar.getTime());
            }
            result.put("columns", columns);

            // set data to corresponding month cell
            for (PaymentHistoryResult historyResult : paymentHistoryResults) {
                Map<String, Double> paymentAmount = new HashMap<String, Double>();

                for (PaymentHistory history : historyResult.getItems()) {
                    for (int i = 0; i < monthList.size(); i++) {
                        if (isTheSameMonth(history.getMonthDate(), monthList.get(i))) {
                            paymentAmount.put(String.valueOf(i), history.getAmount());
                            break;
                        }
                    }
                }
                result.put(historyResult.getPaymentMethod().toString(), paymentAmount);
            }
            setResult(result);
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (ServiceException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new PaymentActionException("Fail to get payment history details", e));
        }
    }

    /**
     * Check whether date 1 and date 2 are in the same month.
     *
     * @param date1 date 1
     * @param date2 date 2
     * @return the check result
     */
    private boolean isTheSameMonth(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        calendar.setTime(date2);
        return year == calendar.get(Calendar.YEAR) && month == calendar.get(Calendar.MONTH);
    }

    /**
     * Check configurations.
     *
     * @throws ConfigurationException if payments service is null or logger is null.
     */
    @PostConstruct
    public void checkConfiguration() {
        super.checkConfiguration();
        ValidationUtility.checkNotNull(paymentsService, "paymentsService", ConfigurationException.class);
    }

    /**
     * Get the payments service.
     *
     * @return the payments service.
     */
    public PaymentsService getPaymentsService() {
        return paymentsService;
    }

    /**
     * Set the payments service.
     *
     * @param paymentsService the payments service to set.
     */
    public void setPaymentsService(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }
}

