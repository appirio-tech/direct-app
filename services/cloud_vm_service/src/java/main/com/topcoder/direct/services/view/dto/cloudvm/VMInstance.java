/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

import java.util.Date;

/**
 * This class represents the VM instance for the Amazon EC2. It contains the Amazon instance id, instance status,
 * associated image id, tc member handle, SVN branch, contest id and contest type.
 *
 * Thread-safety: Mutable and not thread-safe.
 * 
 * Version 1.1 : Module Assembly - Cloud VM Service Notus Cloud Integration version 1.0
 * Added  {@link #toString()} implementation
 * 
 * @author Standlove, kanakarajank
 * @version 1.1
 */
public class VMInstance extends AbstractIdEntity {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 5743291495081523L;

    /**
     * Represents the Amazon instance id. It has getter & setter. It can be any value.
     */
    private String awsInstanceId;

    /**
     * Represents the image reference id. It has getter & setter. It can be any value.
     */
    private long vmImageId;

    /**
     * Represents the tc member handle. It has getter & setter. It can be any value.
     */
    private String tcMemberHandle;

    /**
     * Represents the SVN branch. It has getter & setter. It can be any value.
     */
    private String svnBranch;

    /**
     * Represents the contest id. It has getter & setter. It can be any value.
     */
    private long contestId;

    /**
     * Represents the contest name. It has getter & setter. It can be any value.
     */
    private String contestName;

    /**
     * Represents the contest type id. It has getter & setter. It can be any value.
     */
    private long contestTypeId;

    /**
     * Represents the VM account user id. It has getter & setter. It can be any value.
     */
    private long vmAccountUserId;

    /**
     * Represents the usage id. It has getter & setter. It can be any value.
     */
    private long usageId;
    
    /**
     * Represents VM public IP address.
     * @since BUGR-3932
     */
    private String publicIP;

    /**
     * Is set to true if VM is terminated
     * @since BUGR-3930
     */
    private boolean terminated;

    /**
     * Represents the creation time. It has getter & setter. It can be any value.
     */
    private Date creationTime = new Date();

    /**
     * Represents user data properties in the form:
     *  key1=value1 
     *  key2=value2
     *  
     *  @since BUGR-3931 
     */
    private String userData;
    
    /**
     * Empty constructor.
     */
    public VMInstance() {
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getAwsInstanceId() {
        return awsInstanceId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param awsInstanceId value to set
     */
    public void setAwsInstanceId(String awsInstanceId) {
        this.awsInstanceId = awsInstanceId;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public long getVmImageId() {
        return vmImageId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param vmImageId value to set
     */
    public void setVmImageId(long vmImageId) {
        this.vmImageId = vmImageId;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param contestId value to set
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param contestId value to set
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getTcMemberHandle() {
        return tcMemberHandle;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param tcMemberHandle value to set
     */
    public void setTcMemberHandle(String tcMemberHandle) {
        this.tcMemberHandle = tcMemberHandle;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getSvnBranch() {
        return svnBranch;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param svnBranch value to set
     */
    public void setSvnBranch(String svnBranch) {
        this.svnBranch = svnBranch;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public long getContestTypeId() {
        return contestTypeId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param contestTypeId value to set
     */
    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public long getVmAccountUserId() {
        return vmAccountUserId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param vmAccountUserId value to set
     */
    public void setVmAccountUserId(long vmAccountUserId) {
        this.vmAccountUserId = vmAccountUserId;
    }

      /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public long getUsageId() {
        return usageId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param usageId value to set
     */
    public void setUsageId(long usageId) {
        this.usageId = usageId;
    }

    
    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     * @since BUGR-3930
     */
    public boolean isTerminated() {
        return terminated;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param terminated value to set
     * @since BUGR-3930
     */
    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }
    
    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     * @since BUGR-3931
     */
    public String getUserData() {
        return userData;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param userData value to set
     * @since BUGR-3931
     */
    public void setUserData(String userData) {
        this.userData = userData;
    }
    
    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     * @since BUGR-3932
     */
    public String getPublicIP() {
        return publicIP;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param accountId value to set
     * @since BUGR-3932
     */
    public void setPublicIP(String publicIP) {
        this.publicIP = publicIP;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param creationTime value to set
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VMInstance [awsInstanceId=" + awsInstanceId + ", vmImageId=" + vmImageId
				+ ", tcMemberHandle=" + tcMemberHandle + ", svnBranch=" + svnBranch
				+ ", contestId=" + contestId + ", contestName=" + contestName + ", contestTypeId="
				+ contestTypeId + ", vmAccountUserId=" + vmAccountUserId + ", usageId=" + usageId
				+ ", publicIP=" + publicIP + ", terminated=" + terminated + ", creationTime="
				+ creationTime + ", userData=" + userData + "]";
	}
}
