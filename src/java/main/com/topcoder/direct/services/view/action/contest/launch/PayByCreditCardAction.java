/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.facade.contest.SoftwareContestPaymentResult;
import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;

/**
 * <p>
 * This action will make a payment for the user using a credit card.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure bean in applicationContext.xml for Spring:</strong>
 *
 * <pre>
 * &lt;bean id=&quot;payByCreditCardAction&quot;
 *     class=&quot;com.topcoder.service.actions.PayByCreditCardAction&quot;&gt;
 *     &lt;property name=&quot;contestServiceFacade&quot; ref=&quot;contestServiceFacade&quot;&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <strong>An example of how to configure action in struts.xml:</strong>
 *
 * <pre>
 * &lt;action name=&quot;payByCreditCardAction&quot; class=&quot;payByCreditCardAction&quot;&gt;
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
 * <p>
 * Version 1.1 - Direct - View/Edit/Activate Studio Contests Assembly Change Note
 * -  Adjust the execute to throw <code>Exception</code>
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class PayByCreditCardAction extends PayContestAction {

    /**
     * Represents the expected number of digits in American Express credit card security code.
     */
    private static final int AMEX_CSC_DIGITS = 4;

    /**
     * Represents the expected number of digits in non American Express credit card security code.
     */
    private static final int NON_AMEX_CSC_DIGITS = 3;

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.payByCreditCardAction.";

    /**
     * Represents the number of months in a year.
     */
    private static final int MONTHS_PER_YEAR = 12;

    /**
     * <p>
     * Represents the DTO used by this action to save the credit card payment.
     * </p>
     *
     * <p>
     * All setters and getters delegate to the related setters and getters of this DTO class. It's not null during the
     * setters' logic because it's constructed in the prepare method, which is called before any of the setters. It
     * can't be null.
     * </p>
     */
    private CreditCardPaymentData creditCardPaymentData;

    /**
     * Default constructor, creates new instance.
     */
    public PayByCreditCardAction() {
    }

    /**
     * Prepares the action for use, initializing any necessary fields.
     */
    public void prepare() throws Exception {
        super.prepare();
        creditCardPaymentData = new CreditCardPaymentData();
    }

    /**
     * Processes the payment using the credit card and returns the payment result.
     *
     * @return the payment result after payment has been processed
     *
     * @throws Exception if some error occurs during method execution
     */
    protected PaymentResult processPayment() throws Exception {
        // get the HTTP session
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        // set IP address of client and session id
        creditCardPaymentData.setIpAddress(request.getRemoteAddr());
        creditCardPaymentData.setSessionId(session.getId());

        // get the studio competition or software competition, process the payment using credit card data,
        // and return payment result
        if (isStudioCompetition()) {
            StudioCompetition studioCompetition = getContestServiceFacade().getContest(null, getContestId());
            ContestPaymentResult result = getContestServiceFacade().processContestCreditCardPayment(null,
                studioCompetition, creditCardPaymentData);
            return result.getPaymentResult();
        } else {
            SoftwareCompetition softwareCompetition = getContestServiceFacade().getSoftwareContestByProjectId(null,
                getProjectId());
            SoftwareContestPaymentResult result = getContestServiceFacade().processContestCreditCardSale(null,
                softwareCompetition, creditCardPaymentData);
            return result.getPaymentResult();
        }
    }

    /**
     * Method to send email when payment by credit card is finished, but it does nothing in this implementation.
     *
     * @param paymentResult the payment result
     */
    protected void sendEmail(PaymentResult paymentResult) {
        // do nothing
    }

    /**
     * Overrides the parent method to allow for field validations. The validations performed are as follows:
     * <ul>
     * <li>If country is US, then state must be exactly 2 characters</li>
     * <li>If card type is American Express, the card security code must be 4 digits, otherwise, it must be 3 digits</li>
     * <li>The credit card must not have already expired</li>
     * </ul>
     *
     * @return SUCCESS if there are validation errors, or the result of parent <code>execute</code> method otherwise
     * @throws Exception if any error occurs
     */
    @Override
    public String execute() throws Exception {
        // if country is US, then state is required and must be 2 characters
        if (getCountry().trim().equalsIgnoreCase("US")) {
            // make sure state is 2 characters
            if (getState() == null || getState().trim().length() != 2) {
                addFieldError("state", "For country = US, state is required and must be 2 characters");
            }
        }

        // validate security code for credit card
        if (!getCardType().trim().equalsIgnoreCase("American Express")) {
            if (getCsc().trim().length() != NON_AMEX_CSC_DIGITS) {
                addFieldError("csc", "For non American Express, the card security code must be "
                    + NON_AMEX_CSC_DIGITS + " digits");
            }
        } else {
            if (getCsc().trim().length() != AMEX_CSC_DIGITS) {
                addFieldError("csc", "For American Express, the card security code must be " + AMEX_CSC_DIGITS
                    + " digits");
            }
        }

        // make sure credit card hasn't expired
        int year = Integer.valueOf(getCardExpiryYear());
        int month = Integer.valueOf(getCardExpiryMonth());

        Calendar cal = Calendar.getInstance();
        int curYear = cal.get(Calendar.YEAR);
        int curMonth = cal.get(Calendar.MONTH) + 1;

        // note that we only have to check case where year is equal to current year,
        // because the setYear validation already checks to make sure entered year is
        // greater than or equal to current year
        if (year == curYear && month < curMonth) {
            addFieldError("cardExpiryYear", "The credit card has expired");
        }

        // if there are field errors, don't execute the action and just return SUCCESS, otherwise,
        // execute action and return its result
        return hasFieldErrors() ? SUCCESS : super.execute();
    }

    /**
     * Getter for the card number.
     *
     * @return the card number
     */
    public String getCardNumber() {
        return creditCardPaymentData.getCardNumber();
    }

    /**
     * Setter for the card number. Struts 2 validation is used to check that the argument is not null or empty.
     *
     * @param cardNumber the card number
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "cardNumberNotNullOrEmpty", fieldName = "cardNumber", expression = "cardNumber != null && cardNumber.trim().length() != 0", message = ActionHelper.CANNOT_BE_NULL_OR_EMPTY)
    public void setCardNumber(String cardNumber) {
        creditCardPaymentData.setCardNumber(cardNumber);
    }

    /**
     * Getter for the card type.
     *
     * @return the card type
     */
    public String getCardType() {
        return creditCardPaymentData.getCardType();
    }

    /**
     * Setter for the card type. Struts 2 validation is used to check that the argument is either Visa, MasterCard or
     * American Express.
     *
     * @param cardType the card type
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "cardTypeValues", fieldName = "cardType", expression = "cardType != null && "
        + "(cardType.trim().equalsIgnoreCase(\"Visa\") || cardType.trim().equalsIgnoreCase(\"MasterCard\") || "
        + "cardType.trim().equalsIgnoreCase(\"American Express\"))", message = "cardType must be Visa, MasterCard or American Express")
    public void setCardType(String cardType) {
        creditCardPaymentData.setCardType(cardType);
    }

    /**
     * Getter for the month the card expires.
     *
     * @return the month the card expires
     */
    public String getCardExpiryMonth() {
        return creditCardPaymentData.getCardExpiryMonth();
    }

    /**
     * Setter for the month the card expires. The argument is validated to ensure it can be parsed to a valid integer
     * between 1 and 12 inclusive.
     *
     * @param cardExpiryMonth the month the card expires
     */
    public void setCardExpiryMonth(String cardExpiryMonth) {
        // parse the month to an integer and make sure it is valid
        int month = 0;
        try {
            month = Integer.parseInt(cardExpiryMonth);

            if (!(month >= 1 && month <= MONTHS_PER_YEAR)) {
                addFieldError("cardExpiryMonth", "cardExpiryMonth must be between 1 and 12");
            } else {
                creditCardPaymentData.setCardExpiryMonth(cardExpiryMonth);
            }
        } catch (NumberFormatException e) {
            addFieldError("cardExpiryMonth", "cardExpiryMonth must be a valid integer month");
        }
    }

    /**
     * Getter for the year the card expires.
     *
     * @return the year the card expires
     */
    public String getCardExpiryYear() {
        return creditCardPaymentData.getCardExpiryYear();
    }

    /**
     * Setter for the year the card expires. The argument is validated to ensure it can be parsed to a valid integer
     * and that the year is greater than or equal to current year.
     *
     * @param cardExpiryYear the year the card expires
     */
    public void setCardExpiryYear(String cardExpiryYear) {
        // parse the year to an integer and make sure it is >= current year
        int year = 0;
        try {
            year = Integer.parseInt(cardExpiryYear);

            if (year < Calendar.getInstance().get(Calendar.YEAR)) {
                addFieldError("cardExpiryYear", "cardExpiryYear must be >= current year");
            } else {
                creditCardPaymentData.setCardExpiryYear(cardExpiryYear);
            }
        } catch (NumberFormatException e) {
            addFieldError("cardExpiryYear", "cardExpiryYear must be a valid integer year");
        }
    }

    /**
     * Getter for the first name of payer.
     *
     * @return the first name of payer
     */
    public String getFirstName() {
        return creditCardPaymentData.getFirstName();
    }

    /**
     * Setter for the first name of payer. Struts 2 validation is used to check that the argument is not null or
     * empty.
     *
     * @param firstName the first name of payer
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "firstNameNotNullOrEmpty", fieldName = "firstName", expression = "firstName != null && firstName.trim().length() != 0", message = ActionHelper.CANNOT_BE_NULL_OR_EMPTY)
    public void setFirstName(String firstName) {
        creditCardPaymentData.setFirstName(firstName);
    }

    /**
     * Getter for the last name of payer.
     *
     * @return the last name of payer
     */
    public String getLastName() {
        return creditCardPaymentData.getLastName();
    }

    /**
     * Setter for the last name of payer. Struts 2 validation is used to check that the argument is not null or empty.
     *
     * @param lastName the last name of payer
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "lastNameNotNullOrEmpty", fieldName = "lastName", expression = "lastName != null && lastName.trim().length() != 0", message = ActionHelper.CANNOT_BE_NULL_OR_EMPTY)
    public void setLastName(String lastName) {
        creditCardPaymentData.setLastName(lastName);
    }

    /**
     * Getter for the zip code of payer.
     *
     * @return the zip code of payer
     */
    public String getZipCode() {
        return creditCardPaymentData.getZipCode();
    }

    /**
     * Setter for the zip code of payer. Struts 2 validation is used to check that the argument is between 1 and 10
     * digits inclusive.
     *
     * @param zipCode the zip code of payer
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "zipCodeNotNullOrEmpty", fieldName = "zipCode", expression = "zipCode != null && zipCode.trim().length() != 0", message = ActionHelper.CANNOT_BE_NULL_OR_EMPTY, shortCircuit = true)
    @RegexFieldValidator(key = KEY_PREFIX + "zipCodeValid", fieldName = "zipCode", expression = "[0-9]{1,10}", message = "zipCode is required and must be between 1 and 10 digits")
    public void setZipCode(String zipCode) {
        creditCardPaymentData.setZipCode(zipCode);
    }

    /**
     * Getter for the address of payer.
     *
     * @return the address of payer
     */
    public String getAddress() {
        return creditCardPaymentData.getAddress();
    }

    /**
     * Setter for the address of payer. Struts 2 validation is used to check that the argument is between 1 and 100
     * digits inclusive.
     *
     * @param address the address of payer
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "addressRange", fieldName = "address", expression = "address != null && address.trim().length() >= 1 && address.trim().length() <= 100", message = ActionHelper.BETWEEN_1_AND_100_CHARS)
    public void setAddress(String address) {
        creditCardPaymentData.setAddress(address);
    }

    /**
     * Getter for the city of payer.
     *
     * @return the city of payer
     */
    public String getCity() {
        return creditCardPaymentData.getCity();
    }

    /**
     * Setter for the city of payer. Struts 2 validation is used to check that the argument is between 1 and 100
     * characters inclusive.
     *
     * @param city the city of payer
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "cityRange", fieldName = "city", expression = "city != null && city.trim().length() >= 1 && city.trim().length() <= 100", message = ActionHelper.BETWEEN_1_AND_100_CHARS)
    public void setCity(String city) {
        creditCardPaymentData.setCity(city);
    }

    /**
     * Getter for the state of payer.
     *
     * @return the state of payer
     */
    public String getState() {
        return creditCardPaymentData.getState();
    }

    /**
     * /** Setter for the state of payer.
     *
     * @param state the state of payer
     */
    public void setState(String state) {
        creditCardPaymentData.setState(state);
    }

    /**
     * Getter for the country of payer.
     *
     * @return the country of payer
     */
    public String getCountry() {
        return creditCardPaymentData.getCountry();
    }

    /**
     * Setter for the country of payer. Struts 2 validation is used to check that the argument is between 1 and 100
     * characters inclusive.
     *
     * @param country the country of payer
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "countryRange", fieldName = "country", expression = "country != null && country.trim().length() >= 1 && country.trim().length() <= 100", message = ActionHelper.BETWEEN_1_AND_100_CHARS)
    public void setCountry(String country) {
        creditCardPaymentData.setCountry(country);
    }

    /**
     * Getter for the phone of payer.
     *
     * @return the phone of payer
     */
    public String getPhone() {
        return creditCardPaymentData.getPhone();
    }

    /**
     * Setter for the phone of payer. Struts 2 validation is used to check that the argument exactly matches this
     * format: <code>(xxx) xxx-xxxx</code> where <code>x</code> is a digit.
     *
     * @param phone the phone of payer
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "phoneNotNullOrEmpty", fieldName = "phone", expression = "phone != null && phone.trim().length() != 0", message = ActionHelper.CANNOT_BE_NULL_OR_EMPTY, shortCircuit = true)
    @RegexFieldValidator(key = KEY_PREFIX + "phoneValid", fieldName = "phone", expression = "^\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}$", message = "phone must be in \"(xxx) xxx-xxxx\" format")
    public void setPhone(String phone) {
        creditCardPaymentData.setPhone(phone);
    }

    /**
     * Getter for the email of payer.
     *
     * @return the email of payer
     */
    public String getEmail() {
        return creditCardPaymentData.getEmail();
    }

    /**
     * Setter for the email of payer. Struts 2 validation is used to check that the argument is a valid non empty
     * email address between 1 and 50 characters inclusive.
     *
     * @param email the email of payer
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "emailRange", fieldName = "email", expression = "email != null && email.trim().length() >= 1 && email.trim().length() <= 50", message = "email must not be null or empty and must be <= 50 characters", shortCircuit = true)
    @EmailValidator(key = KEY_PREFIX + "emailValid", fieldName = "email", message = "email must be a valid email address")
    public void setEmail(String email) {
        creditCardPaymentData.setEmail(email);
    }

    /**
     * Getter for the amount of payment.
     *
     * @return the amount of payment
     */
    public String getAmount() {
        return creditCardPaymentData.getAmount();
    }

    /**
     * Setter for the amount of payment.
     *
     * @param amount the amount of payment
     */
    public void setAmount(String amount) {
        creditCardPaymentData.setAmount(amount);
    }

    /**
     * Getter for the card security code.
     *
     * @return the card security code
     */
    public String getCsc() {
        return creditCardPaymentData.getCsc();
    }

    /**
     * Setter for the card security code. Struts 2 validation is used to check that the argument contains only digits
     * and has length of 3 or 4.
     *
     * @param csc the card security code
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "cscValid", fieldName = "csc", expression = "csc != null && csc.trim().matches(\"^[0-9]{3,4}$\")", message = "card security code is required and must consist of 3 or 4 digits")
    public void setCsc(String csc) {
        creditCardPaymentData.setCsc(csc);
    }
}
