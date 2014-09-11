/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents the VM instance data. It contains the vm instance, status, and VM Manager's handle.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * Version 1.1 : Module Assembly - Cloud VM Service Notus Cloud Integration version 1.0
 * Added  {@link #toString()} implementation
 * 
 * @author Standlove, kanakarajank
 * @version 1.1
 */
public class VMInstanceData implements Serializable {
	
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    /**
     * Represents the vm instance status. It has getter & setter. It can be any value.
     */
    private VMInstanceStatus status;

    /**
     * Represents the VM instance. It has getter & setter. It can be any value.
     */
    private VMInstance instance;

    /**
     * Represents the VM manager's handle. It has getter & setter. It can be any value.
     */
    private String managerHandle;
    
    /**
     * Stores contest name for readability.
     * @since BUGR-3232
     */
    private String contestName;
    
    /**
     * Stores tc image name for readability.
     * @since BUGR-3232
     */
    private String vmImageTcName;

    /**
     * Stores account name for readability.
     */
    private String accountName;

    /**
     * Stores vm creation time for readability.
     */
    private String vmCreationTime;
    
    /**
     * vm usage for readability.
     */         
    private String usage;
    
    /**
     * Empty constructor.
     */
    public VMInstanceData() {
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public VMInstanceStatus getStatus() {
        return status;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param status value to set
     */
    public void setStatus(VMInstanceStatus status) {
        this.status = status;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public VMInstance getInstance() {
        return instance;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param instance value to set
     */
    public void setInstance(VMInstance instance) {
        this.instance = instance;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getManagerHandle() {
        return managerHandle;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param managerHandle value to set
     */
    public void setManagerHandle(String managerHandle) {
        this.managerHandle = managerHandle;
    }
   
    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     * @since BUGR-3932
     */
    public String getContestName() {
        return contestName;
    }
    
    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param managerHandle value to set
     * @since BUGR-3932
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }
    
    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     * @since BUGR-3932
     */
    public String getVmImageTcName() {
        return vmImageTcName;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param managerHandle value to set
     * @since BUGR-3932
     */
    public void setVmImageTcName(String vmImageTcName) {
        this.vmImageTcName = vmImageTcName;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param accountName value to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getVmCreationTime() {
        return vmCreationTime;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param vmCreationTime value to set
     */
    public void setVmCreationTime(String vmCreationTime) {
        this.vmCreationTime = vmCreationTime;
    }
    
    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param vmCreationTime value to set
     */
    public void setVmCreationTime(Date vmCreationTime) {
        this.vmCreationTime = DATE_FORMATTER.format(vmCreationTime);
    }
    
    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param usage value to set
     */
    public void setUsage(String usage) {
        this.usage = usage;
    }

    
    /**
     * Fills contest name and vm image TC name.
     * 
     * @param instance
     * @param vmImage
     * @param usage
     */
    public void fillData(VMInstance instance, VMImage vmImage, VMUsage usage){
    	setVmImageTcName(vmImage.getTcName());
        setAccountName(vmImage.getVmAccount().getAccountName());
        setVmCreationTime( DATE_FORMATTER.format(instance.getCreationTime()));
        setUsage(usage.getName());
        setContestName(instance.getContestName());
    }

    /**
     * Returns the vmCreationTime in Date Format
     * 
     * @return	
     *      vmCreationTime in <code>Date<code>Format
     */
    public Date getCreationDate(){
    	if (null != vmCreationTime ){
    		try{
    			return DATE_FORMATTER.parse(vmCreationTime);
    		} catch (ParseException e){
    			// Do Nothing.
    		}
    	}
    	return null;
    }
    
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VMInstanceData [status=" + status + ", instance=" + instance + ", managerHandle="
				+ managerHandle + ", contestName=" + contestName + ", vmImageTcName="
				+ vmImageTcName + ", accountName=" + accountName + ", vmCreationTime="
				+ vmCreationTime + ", usage=" + usage + "]";
	}
}

