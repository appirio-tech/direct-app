/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

/**
 * This class represents the VM account for the Amazon EC2. It contains the access key id and security access key.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * Version 1.1 : Module Assembly - Cloud VM Service Notus Cloud Integration version 1.0
 * Added  {@link #toString()} implementation
 *  
 * @author Standlove, kanakarajank
 * @version 1.1
 */
public class VMAccount extends AbstractIdEntity {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 2742320281043253L;

    /**
     * Represents the account name. It has getter & setter. It can be any value.
     */
    private String accountName;

    /**
     * Represents the access key id. It has getter & setter. It can be any value.
     */
    private String awsAccessKeyId;

    /**
     * Represents the security access key. It has getter & setter. It can be any value.
     */
    private String awsSecurityAccessKey;

    /**
     * Empty constructor.
     */
    public VMAccount() {
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param awsAccessKeyId
     */
    public void setAwsAccessKeyId(String awsAccessKeyId) {
        this.awsAccessKeyId = awsAccessKeyId;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getAwsSecurityAccessKey() {
        return awsSecurityAccessKey;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param awsSecurityAccessKey
     */
    public void setAwsSecurityAccessKey(String awsSecurityAccessKey) {
        this.awsSecurityAccessKey = awsSecurityAccessKey;
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
     * @param accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VMAccount [accountName=" + accountName + ", awsAccessKeyId=" + awsAccessKeyId + "]";
	}
}

