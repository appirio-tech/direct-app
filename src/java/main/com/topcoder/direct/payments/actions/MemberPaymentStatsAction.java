/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.actions;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.payments.DTO.PaymentTrendItem;
import com.topcoder.direct.payments.entities.PaymentMethod;
import com.topcoder.direct.payments.entities.PaymentStatus;
import com.topcoder.direct.payments.entities.PaymentTrend;
import com.topcoder.direct.payments.entities.PaymentTrendSearchCriteria;
import com.topcoder.direct.payments.services.ConfigurationException;
import com.topcoder.direct.payments.services.PaymentStatsService;
import com.topcoder.direct.payments.services.ServiceException;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>This class is the action used to fetch member payment stats.</p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version  1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 */
public class MemberPaymentStatsAction extends BaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = MemberPaymentStatsAction.class.getName();

    /**
     * The month type.
     */
    private static final String MONTH_TYPE = "month";

    /**
     * The week type.
     */
    private static final String WEEK_TYPE = "week";

    /**
     * The day type.
     */
    private static final String DAY_TYPE = "day";

    /**
     * The date format used for month type.
     */
    private static final DateFormat MONTH_FORMAT = new SimpleDateFormat("MMM yyyy");

    /**
     * The date format used for week type and day type.
     */
    private static final DateFormat DAY_FORMAT = new SimpleDateFormat("MMM d, yyyy");

    /**
     * The default date range.
     */
    private static final int DEFAULT_DATE_RANGE = 6;

    /**
     * The payment methods.
     */
    private static final PaymentMethod[] PAYMENT_METHODS = {
            PaymentMethod.PAYPAL,
            PaymentMethod.PAYONEER,
            PaymentMethod.WESTERN_UNION
    };

    /**
     * The payment stats service, used to fetch member payment stats.
     */
    private PaymentStatsService paymentStatsService;

    /**
     * The date type request.
     */
    private String dateType;

    /**
     * The date range request.
     */
    private int dateRange;

    /**
     * Default constructor.
     */
    public MemberPaymentStatsAction() {
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
            if (dateType == null) {
                dateType = MONTH_TYPE;
            }
            if (dateRange <= 0) {
                dateRange = DEFAULT_DATE_RANGE;
            }

            Date startDate;
            Date endDate = new Date();
            DateTime startTime = new DateTime();
            int dateDiff = dateRange - 1;
            if (MONTH_TYPE.equals(dateType)) {
                startTime = startTime.minusMonths(dateDiff).withDayOfMonth(1);
            } else if (WEEK_TYPE.equals(dateType)) {
                startTime = startTime.minusWeeks(dateDiff).withDayOfWeek(1);
            } else {
                startTime = startTime.minusDays(dateDiff);
            }
            startDate = startTime.toDate();

            // fetch create payment trends
            PaymentTrendSearchCriteria paymentsTrendsCriteria = new PaymentTrendSearchCriteria();
            paymentsTrendsCriteria.setStartDate(startDate);
            paymentsTrendsCriteria.setEndDate(endDate);
            paymentsTrendsCriteria.setPaymentStatusId(PaymentStatus.OWED.getPaymentStatusId());
            List<PaymentTrend> createPaymentTrends = paymentStatsService.
                    getPaymentsTrends(paymentsTrendsCriteria);

            // fetch paid payment trends
            paymentsTrendsCriteria.setPaymentStatusId(PaymentStatus.PAID.getPaymentStatusId());
            List<PaymentTrend> paidPaymentTrends = paymentStatsService.
                    getPaymentsTrends(paymentsTrendsCriteria);

            Map<Integer, List<PaymentTrendItem>> map = new HashMap<Integer, List<PaymentTrendItem>>();

            if (createPaymentTrends.size() == 0 && paidPaymentTrends.size() == 0) {
                throw LoggingWrapperUtility.logException(getLogger(), signature,
                        new PaymentActionException("There is no trend data."));
            }

            // aggregate payment trends
            for (PaymentMethod method : PAYMENT_METHODS) {
                List<PaymentTrendItem> items = new ArrayList<PaymentTrendItem>();
                for (int i = 0; i < dateRange; i++) {
                    items.add(new PaymentTrendItem());
                }
                map.put(method.getPaymentMethodId(), items);
            }
            aggregatePaymentTrends(map, createPaymentTrends, false);
            aggregatePaymentTrends(map, paidPaymentTrends, true);

            // set timestamps
            Map<String, Object> result = new HashMap<String, Object>();
            for (PaymentMethod method : PAYMENT_METHODS) {
                List<PaymentTrendItem> items = map.get(method.getPaymentMethodId());
                Collections.reverse(items);

                startTime = new DateTime(startDate);
                for (PaymentTrendItem item : items) {
                    if (MONTH_TYPE.equals(dateType)) {
                        item.setTimeStamp(MONTH_FORMAT.format(startTime.toDate()));
                        startTime = startTime.plusMonths(1);
                    } else if (WEEK_TYPE.equals(dateType)) {
                        item.setTimeStamp(DAY_FORMAT.format(startTime.toDate()));
                        startTime = startTime.plusWeeks(1);
                    } else if (DAY_TYPE.equals(dateType)){
                        item.setTimeStamp(DAY_FORMAT.format(startTime.toDate()));
                        startTime = startTime.plusDays(1);
                    }
                }

                result.put(method.toString(), map.get(method.getPaymentMethodId()));
            }
            setResult(result);

            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (ServiceException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new PaymentActionException("Fail to get member payment stats", e));
        }
    }

    /**
     * Aggregate the payment trends data.
     *
     * @param map the map to store aggregated result.
     * @param trends the source trend data
     * @param isPaid whether is paid payment
     * @throws PaymentActionException if data is incorrect.
     */
    private void aggregatePaymentTrends(Map<Integer, List<PaymentTrendItem>> map,
                                        List<PaymentTrend> trends,
                                        boolean isPaid) throws PaymentActionException {
        for (PaymentTrend trend : trends) {
            if (!map.containsKey(trend.getPaymentMethodId())) {
                throw new PaymentActionException("the payment method id is not set");
            }

            int diff;
            DateTime processDate = new DateTime(trend.getCreatedOrPaidDate());
            if (MONTH_TYPE.equals(dateType)) {
                diff = Months.monthsBetween(
                        processDate.withDayOfMonth(1).withTime(0, 0, 0, 0),
                        DateTime.now()).getMonths();
            } else if (WEEK_TYPE.equals(dateType)) {
                diff = Weeks.weeksBetween(processDate.withDayOfWeek(1).withTime(0, 0, 0, 0),
                        DateTime.now()).getWeeks();
            } else {
                diff = Days.daysBetween(processDate.withTime(0, 0, 0, 0),
                        DateTime.now()).getDays();
            }

            List<PaymentTrendItem> items = map.get(trend.getPaymentMethodId());
            if (diff < 0 || diff > items.size()) {
                throw new PaymentActionException("the date is out of range");
            }

            if (isPaid) {
                items.get(diff).addPaid(trend.getPayments());
            } else {
                items.get(diff).addCreated(trend.getPayments());
            }
        }
    }

    /**
     * Check configurations.
     *
     * @throws ConfigurationException if paymentStatsService is null or logger is null
     */
    @PostConstruct
    public void checkConfiguration() {
        super.checkConfiguration();
        ValidationUtility.checkNotNull(paymentStatsService, "paymentStatsService",
                ConfigurationException.class);
    }

    /**
     * Get payment stats service.
     *
     * @return the service
     */
    public PaymentStatsService getPaymentStatsService() {
        return paymentStatsService;
    }

    /**
     * Set the payment stats service.
     *
     * @param paymentStatsService the service to set
     */
    public void setPaymentStatsService(PaymentStatsService paymentStatsService) {
        this.paymentStatsService = paymentStatsService;
    }

    /**
     * Get the date type.
     *
     * @return the date type.
     */
    public String getDateType() {
        return dateType;
    }

    /**
     * Set the date type.
     *
     * @param dateType the date type to set.
     */
    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    /**
     * Get the date range.
     *
     * @return the date range.
     */
    public int getDateRange() {
        return dateRange;
    }

    /**
     * Set the date range.
     *
     * @param dateRange the date range.
     */
    public void setDateRange(int dateRange) {
        this.dateRange = dateRange;
    }
}

