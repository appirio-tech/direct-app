/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.impl;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.direct.services.payments.ConfigurationException;
import com.topcoder.direct.services.payments.PayoneerService;
import com.topcoder.direct.services.payments.ServiceException;

/**
 * <p>
 * This class retrieves Payoneer balance amount.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public class PayoneerServiceImpl extends BaseService implements PayoneerService {

    /**
     * Stands for the name of this class.
     */
    private static final String CLASS_NAME = "PayoneerServiceImpl";

    /**
     * Stands for Payoneer base api URL.
     */
    private String baseApiUrl;

    /**
     * Stands for Payoneer username.
     */
    private String username;

    /**
     * Stands for the Payoneer password.
     */
    private String password;

    /**
     * Stands for the Payoneer partner id.
     */
    private String partnerId;

    /**
     * <p>
     * This method is responsible for checking aggregate members are initialized
     * or not. If any member is null, then it will throw ConfigugationException
     * </p>
     */
    @PostConstruct
    protected void checkConfiguration()
        throws ConfigurationException {
        if (null == baseApiUrl) {
            throw new ConfigurationException("Payoneer configuration is not correct. The 'baseApiUrl' is null");
        }
        if (null == username) {
            throw new ConfigurationException("Payoneer configuration is not correct. The 'username' is null");
        }
        if (null == password) {
            throw new ConfigurationException("Payoneer configuration is not correct. The 'password' is null");
        }
        if (null == partnerId) {
            throw new ConfigurationException("Payoneer configuration is not correct. The 'partnerId' is null");
        }
    }

    /**
     * Get Payoneer balance amount.
     * 
     * @return balance amount
     * @throws ServiceException
     *             if any error occurs
     */
    public double getBalanceAmount()
        throws ServiceException {
        final String signature = CLASS_NAME + "#getBalanceAmount";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {}, new Object[] {});
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("mname", "GetAccountDetails");
            parameters.put("p1", username);
            parameters.put("p2", password);
            parameters.put("p3", partnerId);

            Document response = getXMLResponse(baseApiUrl, parameters);

            // Code node means something went wrong.
            NodeList codeNode = response.getElementsByTagName("Code");
            if (codeNode.getLength() > 0) {
                NodeList errorNode = response.getElementsByTagName("Error");
                String error = errorNode.getLength() > 0 ? errorNode.item(0).getFirstChild().getNodeValue() : null;
                throw new ServiceException("Payoneer service reported an error : " + error);
            }

            // The AccountBalance node contains the balance amount.
            NodeList balanceNode = response.getElementsByTagName("AccountBalance");
            if (balanceNode.getLength() > 0) {
                String value = balanceNode.item(0).getFirstChild().getNodeValue();
                if (value != null) {
                    LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {value});
                    return Double.parseDouble(value);
                }
            }
            return 0;
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new ServiceException(
                "Error while retrieving Payoneer balance amount.", e));
        }
    }

    /**
     * <p>
     * This is a private helper method that queries the Payoneer API with the
     * specified parameters and returns the response.
     * </p>
     */
    private Document getXMLResponse(String baseApiUrl, Map<String, String> parameters)
        throws Exception {
        HttpURLConnection connection = null;
        try {
            StringBuilder builder = new StringBuilder();
            for (String key : parameters.keySet()) {
                if (builder.length() > 0) {
                    builder.append("&");
                }
                builder.append(key + "=" + parameters.get(key));
            }
            String urlParameters = builder.toString();

            // Create connection
            connection = (HttpURLConnection) (new URL(baseApiUrl)).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            // Get Response
            Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(connection.getInputStream());
            response.getDocumentElement().normalize();
            return response;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * <p>
     * Getter of baseApiUrl field.
     * </p>
     * 
     * @return the baseApiUrl
     */
    public String getBaseApiUrl() {
        return baseApiUrl;
    }

    /**
     * <p>
     * Setter of baseApiUrl field.
     * </p>
     * 
     * @param baseApiUrl
     *            the baseApiUrl to set
     */
    public void setBaseApiUrl(String baseApiUrl) {
        this.baseApiUrl = baseApiUrl;
    }

    /**
     * <p>
     * Getter of username field.
     * </p>
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>
     * Setter of username field.
     * </p>
     * 
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>
     * Getter of password field.
     * </p>
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>
     * Setter of password field.
     * </p>
     * 
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>
     * Getter of partnerId field.
     * </p>
     * 
     * @return the partnerId
     */
    public String getPartnerId() {
        return partnerId;
    }

    /**
     * <p>
     * Setter of partnerId field.
     * </p>
     * 
     * @param partnerId
     *            the partnerId to set
     */
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

}
