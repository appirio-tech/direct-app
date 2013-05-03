/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.direct.services.payments.ConfigurationException;
import com.topcoder.direct.services.payments.PayPalService;
import com.topcoder.direct.services.payments.ServiceException;

/**
 * <p>
 * This class retrieves paypal balance amount.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard Backend Assembly)
 * @since 1.0
 */
public class PayPalServiceImpl extends BaseService implements PayPalService {

	/**
	 * Stands for the name of this class.
	 */
	private static final String CLASS_NAME = "PayPalServiceImpl";

	/**
	 * Stands for the PayPal service base URL.
	 */
	private String baseUrl;
	
	/**
	 * Stands for the PayPal service username.
	 */
	private String username;
	
	/**
	 * Stands for the PayPal service password.
	 */
	private String password;
	
	/**
	 * Stands for the PayPal service signature.
	 */
	private String signature;
	
	/**
	 * Stands for the charset used in sending request and parsing response.
	 */
	private static final String CHARSET = "UTF-8";

    @PostConstruct
    protected void checkConfiguration() throws ConfigurationException {
        if (null == baseUrl) {
            throw new ConfigurationException("Payoneer configuration is not correct. The 'baseUrl' is null");
        }
        if (null == username) {
            throw new ConfigurationException("Payoneer configuration is not correct. The 'username' is null");
        }
        if (null == password) {
            throw new ConfigurationException("Payoneer configuration is not correct. The 'password' is null");
        }
        if (null == signature) {
            throw new ConfigurationException("Payoneer configuration is not correct. The 'signature' is null");
        }
    }

	/**
	 * Get balance amount.
	 * 
	 * @return
	 * 			balance amount
	 * @throws ServiceException
	 * 			if any error occurs
	 */
	public double getBalanceAmount() throws ServiceException {
		final String logSignature = CLASS_NAME + "#getBalanceAmount";
		LoggingWrapperUtility.logEntrance(getLogger(), logSignature, new String[]{}, new Object[]{});
		
		try {
			String responseBody = getResponseBody();

	        double balance = parseResponseBody(responseBody);

	        LoggingWrapperUtility.logExit(getLogger(), logSignature, new Object[]{balance});
	        return balance;
		} catch (Exception e) {
			throw LoggingWrapperUtility.logException(getLogger(), logSignature, 
					new ServiceException("Error occurs while getting PayPal balance.", e));
		}
	}

	/**
	 * Send request and get response body.
	 * 
	 * @return
	 * 			response text
	 * @throws ServiceException
	 * 			if any error occurs
	 */
	private String getResponseBody() throws ServiceException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) 
					(new URL(baseUrl)).openConnection();
			String requestBody="METHOD=GetBalance" +
	                "&VERSION=94.0" +
	                "&PWD=" + URLEncoder.encode(password, CHARSET) +
	                "&USER=" + URLEncoder.encode(username, CHARSET) +
	                "&SIGNATURE=" + URLEncoder.encode(signature, CHARSET) +
	                "&RETURNALLCURRENCIES=0";

			connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        connection.setRequestProperty("Content-Length", "" + Integer.toString(requestBody.getBytes().length));
	        connection.setRequestProperty("Content-Language", "en-US");

	        connection.setRequestProperty("X-PAYPAL-SECURITY-USERID", username);
	        connection.setRequestProperty("X-PAYPAL-SECURITY-PASSWORD", password);
	        connection.setRequestProperty("X-PAYPAL-SECURITY-SIGNATURE", signature);

	        connection.setRequestProperty("X-PAYPAL-REQUEST-DATA-FORMAT", "NV");
	        connection.setRequestProperty("X-PAYPAL-RESPONSE-DATA-FORMAT", "NV");

	        connection.setUseCaches(false);
	        connection.setDoInput(true);
	        connection.setDoOutput(true);

	        //Send request
	        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
	        wr.writeBytes(requestBody);
	        wr.flush();
	        wr.close();

	        //read response with BufferedReader
	        BufferedReader br  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder sb = new StringBuilder();

	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }

	        return sb.toString();
		} catch (Exception e) {
			throw new ServiceException("Error occurs while sending request.", e);
		} finally {
			if(null != connection) {
				connection.disconnect();
			}
		}
	}
	
	/**
	 * Get balance amount by response text.
	 * 
	 * @param responseBody
	 * 				response text
	 * @return
	 * 				balance amount
	 * @throws ServiceException
	 * 				if any error occurs
	 */
	private double parseResponseBody(String responseBody) throws ServiceException {
		try {
			String[] parts = responseBody.split("&");
	        Map<String, String> ret = new HashMap<String,String>();
	        for (String part : parts) {
	            int index = part.indexOf("=");            
	            if (index == -1) {
	            	throw new ServiceException("Response text format is not right.");
	            }
	            ret.put(URLDecoder.decode(part.substring(0, index), CHARSET),
	                URLDecoder.decode(part.substring(index + 1), CHARSET));
	        }
	
	        String ack = ret.get("ACK");
	        if (!"Success".equalsIgnoreCase(ack) && !"SuccessWithWarning".equalsIgnoreCase(ack)) {
	        	throw new ServiceException("PayPal returns incorrect status code.");
	        }

	        String currencyCode = ret.get("L_CURRENCYCODE0");
	        if (!"USD".equalsIgnoreCase(currencyCode)) {
	        	throw new ServiceException("PayPal balance is not USD.");
	        }

	        String balance = ret.get("L_AMT0");
	        return Double.parseDouble(balance);
		} catch (Exception e) {
			throw new ServiceException("Cannot parse response.", e);
		}
	}
 	
	/**
	 * <p>
	 * Getter of baseUrl field.
	 * </p>
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * <p>
	 * Setter of baseUrl field.
	 * </p>
	 * @param baseUrl the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * <p>
	 * Getter of username field.
	 * </p>
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * <p>
	 * Setter of username field.
	 * </p>
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * <p>
	 * Getter of password field.
	 * </p>
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <p>
	 * Setter of password field.
	 * </p>
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * <p>
	 * Getter of signature field.
	 * </p>
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * <p>
	 * Setter of signature field.
	 * </p>
	 * @param signature the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}
