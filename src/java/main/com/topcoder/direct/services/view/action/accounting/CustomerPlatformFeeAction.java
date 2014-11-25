/*
 * Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.topcoder.clients.dao.CustomerPlatformFeeConfigurationException;
import com.topcoder.clients.dao.CustomerPlatformFeePersistenceException;
import com.topcoder.clients.dao.CustomerPlatformFeeService;
import com.topcoder.clients.model.CustomerPlatformFee;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * <p>
 * The action class for customer platform fee management.
 * </p>
 *
 * <p>
 * Version 1.1 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 * - Removes the statements to populate the right sidebar direct projects and project contests. It's changed to
 * load these data via ajax instead after the page finishes loading.
 * </p>
 * 
 * <p>Thread safety: Not an issue for struts2 action class.</p>
 * 
 * @author minhu, Veve
 * @version 1.1
 */
public class CustomerPlatformFeeAction extends BaseDirectStrutsAction {
    /**
     * Instance of Logger used to perform logging operations.
     */
    private Log logger;

    /**
     * Represents the instance of CustomerPlatformFee.
     */
    private CustomerPlatformFee fee;
    
    /**
     * Represents the map from client id to client name.
     */
    private Map<Long, String> clients;

    /**
     * Instance of CustomerPlatformFeeService used to perform persistence operations.
     * It is injected through spring IoC.
     */
    private CustomerPlatformFeeService customerPlatformFeeService;
    
    /**
     * The view data contains user projects information.
     */
    private CommonDTO viewData;

    /**
     * <p>
     * Constructs new <code>BaseContestFeeAction</code> instance.
     * </p>
     */
    public CustomerPlatformFeeAction() {
    }

    /**
     * Gets the list of customer platform fee instances.
     * 
     * @throws Exception if an unexpected error occurs
     */
    @Override
    protected void executeAction() throws Exception {
        logger.log(Level.DEBUG, "Enter CustomerPlatformFeeAction#executeAction()");
        List<CustomerPlatformFee> fees = getCustomerPlatformFeeService().getAll();
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        for (CustomerPlatformFee tfee : fees) {
            Map<String, Object> data = new HashMap<String, Object>();
            res.add(data);
            data.put("id", tfee.getId());
            data.put("clientName", clients.get(tfee.getClientId()));
            data.put("paymentDate", tfee.getPaymentDate());
            data.put("amount", String.format("%.2f", tfee.getAmount()));
            data.put("modifyDate", DirectUtils.getDateString(tfee.getModifyDate()));
        }            
        retrieveViewData();
        setResult(res);
        logger.log(Level.DEBUG, "Exit CustomerPlatformFeeAction#executeAction()");
    }

    /**
     * <p>
     * Enters create customer platform fee page. An empty CustomerPlatformFee instance will be created.
     * </p>
     *
     * @return always SUCCESS
     * @throws Exception if any error occurs
     */
    public String enterCreateCustomerPlatformFee() throws Exception {
        logger.log(Level.DEBUG, "Enter CustomerPlatformFeeAction#enterCreateCustomerPlatformFee()");

        fee = new CustomerPlatformFee(); 
        retrieveViewData();
        String res = SUCCESS;
        logger.log(Level.DEBUG, "Exit CustomerPlatformFeeAction#enterCreateCustomerPlatformFee() with " + res);
        return res;
    }

    /**
     * <p>
     * Enters update customer platform fee page. It populates the CustomerPlatformFee instance to update.
     * </p>
     *
     * @throws CustomerPlatformFeeValidationException
     *      if the CustomerPlatformFee instance to update is null or doesn't have positive id,
     *      or doesn't exist in DB
     * @throws CustomerPlatformFeePersistenceException
     *      if any error occurs from the CustomerPlatformFeeService instance
     * @throws Exception
     *      if any other unexpected error occurred
     * @return always SUCCESS
     */
    public String enterUpdateCustomerPlatformFee() throws Exception {
        logger.log(Level.DEBUG, "Enter CustomerPlatformFeeAction#enterUpdateCustomerPlatformFee()");
        
        if (fee == null || fee.getId() <= 0) {
            logAndThrow(new CustomerPlatformFeeValidationException(
                "The CustomerPlatformFee instance to update should be non-null and have positive id."));
        }
        try {
            fee = getCustomerPlatformFeeService().get(fee.getId());
        } catch (CustomerPlatformFeePersistenceException e) {
            logAndThrow(e);
        }        
        if (fee == null) {
            logAndThrow(new CustomerPlatformFeeValidationException(
                "The CustomerPlatformFee instance to update doesn't exist."));
        }            
        retrieveViewData();
              
        String res = SUCCESS;
        logger.log(Level.DEBUG, "Exit CustomerPlatformFeeAction#enterUpdateCustomerPlatformFee() with " + res);
        return res;
    }

    /**
     * <p>
     * Checks if there's already a customer platform fee record with the same client id and same month/year.
     * </p>
     *
     * @return always SUCCESS
     */
    public String checkRecordExist() {
        logger.log(Level.DEBUG, "Enter CustomerPlatformFeeAction#checkRecordExist()");

        try {
            // check the parameters
            if (!checkParameters()) {
                StringBuilder builder = new StringBuilder();
                boolean first = true;
                for (String error : getActionErrors()) {
                    if (!first)builder.append("\r\n");
                    builder.append(error);   
                    first = false;                 
                }
                for (List<String> errors : getFieldErrors().values()) {
                    for (String error : errors) {
                        if (!first)builder.append("\r\n");
                        builder.append(error);
                        first = false; 
                    }
                }
                clearErrors();
                throw new CustomerPlatformFeeValidationException(builder.toString());                
            }
        
            // for create or update (to a new month/year)
            // check if the record already exist with the same client id and same month/year payment date
            boolean checkExist = (fee.getId() <= 0);
            if (!checkExist) {
                CustomerPlatformFee oldFee = getCustomerPlatformFeeService().get(fee.getId());     
                if (oldFee == null) {
                    throw new CustomerPlatformFeeValidationException(
                        "The CustomerPlatformFee instance to update doesn't exist.");
                } else {
                    Calendar oldCal = Calendar.getInstance();
                    oldCal.setTime(oldFee.getPaymentDate());
                    Calendar newCal = Calendar.getInstance();
                    newCal.setTime(fee.getPaymentDate());
                    checkExist = (oldCal.get(Calendar.YEAR) != newCal.get(Calendar.YEAR) ||
                        oldCal.get(Calendar.MONTH) != newCal.get(Calendar.MONTH));
                }
            }
            
            if (checkExist) {
                checkExist = getCustomerPlatformFeeService().getByClientIdAndPaymentDate(
                    fee.getClientId(), fee.getPaymentDate()) != null;
            }
            
            Map<String, Boolean> res = new HashMap<String, Boolean>();
            res.put("exist", checkExist);
            setResult(res);
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
            setResult(e);
        }
        
        logger.log(Level.DEBUG, "Exit CustomerPlatformFeeAction#checkRecordExist() with " + SUCCESS);
        return SUCCESS;
    }

    /**
     * <p>
     * Creates/updates the customer platform fee.
     * </p>
     *
     * @return SUCCESS if there's no error, otherwise ERROR
     */
    public String createUpdateCustomerPlatformFee() throws Exception {
        logger.log(Level.DEBUG, "Enter CustomerPlatformFeeAction#createUpdateCustomerPlatformFee()");

        String res = SUCCESS;
        try {
            if (!checkParameters()) {
                res = ERROR;        
            } else {
                // create or update the entity
                Date now = new Date();
                String user = getCurrentUser().getUserId() + "";
                fee.setModifyDate(now);
                fee.setModifyUser(user);
                if (fee.getId() == 0) {
                    fee.setCreateDate(now);
                    fee.setCreateUser(user);
                    getCustomerPlatformFeeService().create(fee);
                } else {
                    getCustomerPlatformFeeService().update(fee);
                }
            }            
            retrieveViewData();
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
            addActionError(e.getMessage());
            res = ERROR;
        }
        
        logger.log(Level.DEBUG, "Exit CustomerPlatformFeeAction#createUpdateCustomerPlatformFee() with " + res);
        return res;
    }

    /**
     * Gets the logger instance.
     * 
     * @return the logger
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * Sets the logger name.
     * 
     * @param loggerName the logger name used
     */
    public void setLoggerName(String loggerName) {
        if (loggerName == null) {
            this.logger = null;
        } else {
            this.logger = LogManager.getLog(loggerName);
        }
    }
    
    /**
     * Gets the map from client id to client name.
     * 
     * @return the map from client id to client name
     */
    public Map<Long, String> getClients() {
        return clients;
    }
    
    /**
     * Sets the map from client id to client name.
     * 
     * @param clients the map from client id to client name
     */
    public void setClients(Map<Long, String> clients) {
        this.clients = clients;
    }

    /**
     * Gets the customer platform fee instance.
     * 
     * @return the customer platform fee instance
     */
    public CustomerPlatformFee getFee() {
        return fee;
    }

    /**
     * Sets the customer platform fee instance.
     * 
     * @param fee the customer platform fee instance to set
     */
    public void setFee(CustomerPlatformFee fee) {
        this.fee = fee;
    }

    /**
     * Gets the CustomerPlatformFeeService instance.
     * 
     * @return the CustomerPlatformFeeService instance
     */
    public CustomerPlatformFeeService getCustomerPlatformFeeService() {
        return customerPlatformFeeService;
    }

    /**
     * Sets the CustomerPlatformFeeService instance.
     * 
     * @param customerPlatformFeeService the CustomerPlatformFeeService instance to set
     */
    public void setCustomerPlatformFeeService(CustomerPlatformFeeService customerPlatformFeeService) {
        this.customerPlatformFeeService = customerPlatformFeeService;
    }

    /**
     * Check the parameters.
     * 
     * @throws CustomerPlatformFeeConfigurationException if this.logger or this.customerPlatformFeeService is null
     */
    @PostConstruct
    public void postConstruct() {
        if (logger == null || customerPlatformFeeService == null) {
            throw new CustomerPlatformFeeConfigurationException(
                "This class requires logger instance and customerPlatformFeeService instance.");
        }
    }
    
    /**
     * Gets the view data which contains user projects information.
     * 
     * @return the view data
     */
    public CommonDTO getViewData() {
        return viewData;
    }
    
    /**
     * Retrieves the view data which contains user projects information.
     * 
     * @throws Exception if any error occurred
     */
    private void retrieveViewData() throws Exception {
        viewData = new CommonDTO();
    }
    
    /**
     * Logs and throws the exception.
     * 
     * @param <T> the exception type 
     * @param ex the exception instance
     * @throws T the exception thrown
     */
    private <T extends Exception> void logAndThrow(T ex) throws T {
        logger.log(Level.ERROR, ex);
        throw ex;        
    }
    
    /**
     * Checks the parameters used in method createUpdateCustomerPlatformFee and checkRecordExist.
     * 
     * @return true if there's no error, false otherwise
     */
    private boolean checkParameters() {
        boolean res = true;
        if (fee == null) {
            res = false;
            addActionError("The CustomerPlatformFee instance should not be null.");
        } else {
            if (fee.getClientId() <= 0) {
                res = false;
                addFieldError("fee.clientId", "Client id should be positive.");
            }
            if (fee.getPaymentDate() == null) {
                res = false;
                addFieldError("fee.paymentDate", "Payment date should be set.");
            }
            if (fee.getAmount() < 0) {
                res = false;
                addFieldError("fee.amount", "Amount should not be negative.");
            }
        }
        return res;
    }
}
