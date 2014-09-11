/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

import java.util.List;

/**
 * This class represents the VM image for the Amazon EC2. It contains the Amazon image id, configured security group,
 * instance type, availability zone, key pair name and user data.
 *
 * Thread-safety: Mutable and not thread-safe.
 * 
 * Version 1.1 : Module Assembly - Cloud VM Service Notus Cloud Integration version 1.0
 * Added  {@link #toString()} implementation and a new Field <code>VMProvider<code> representing
 * the provider of this image.
 * 
 * @author Standlove, kanakarajank
 * @version 1.1
 */
public class VMImage extends AbstractIdEntity {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 121048731307324L;

    /**
     * Represents the Amazon image id. It has getter & setter. It can be any value.
     */
    private String awsImageId;

    /**
     * Represents the security group. It has getter & setter. It can be any value.
     */
    private VMSecurityGroup securityGroup;

    /**
     * Represents the instance type. It has getter & setter. It can be any value.
     */
    private VMInstanceType instanceType;

    /**
     * Represents the availability zone. It has getter & setter. It can be any value.
     */
    private VMAvailabilityZone availabilityZone;

    /**
     * Represents the key pair name. It has getter & setter. It can be any value.
     */
    private VMKeyPair keyPair;

    /**
     * Represents the vm account for launching this VM Image. It has getter & setter. It can be any value.
     */
    private VMAccount vmAccount;

    /**
     * Represents the user data mapping. It has getter & setter. It can be any value.
     */
    private List<VMUserData> userData;

    /**
     * Represents TopCoder image name.
     * @since BUGR-3932
     */
    private String tcName;
    
    /**
     * Represents the provider of this instance.It has getter & setter.
     */
    private VMProvider vmProvider;
    
    /**
     * Empty constructor.
     */
    public VMImage() {
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getAwsImageId() {
        return awsImageId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param awsImageId value to set
     */
    public void setAwsImageId(String awsImageId) {
        this.awsImageId = awsImageId;
    }

    /**
     * @return field value
     */
    public VMSecurityGroup getSecurityGroup() {
        return securityGroup;
    }

    /**
     * @param securityGroup value to set
     */
    public void setSecurityGroup(VMSecurityGroup securityGroup) {
        this.securityGroup = securityGroup;
    }

    /**
     * @return field value
     */
    public VMInstanceType getInstanceType() {
        return instanceType;
    }

    /**
     * @param instanceType value to set
     */
    public void setInstanceType(VMInstanceType instanceType) {
        this.instanceType = instanceType;
    }

    /**
     * @return field value
     */
    public VMAvailabilityZone getAvailabilityZone() {
        return availabilityZone;
    }

    /**
     * @param availabilityZone value to set
     */
    public void setAvailabilityZone(VMAvailabilityZone availabilityZone) {
        this.availabilityZone = availabilityZone;
    }

    /**
     * @return field value
     */
    public VMKeyPair getKeyPair() {
        return keyPair;
    }

    /**
     * @param keyPair value to set
     */
    public void setKeyPair(VMKeyPair keyPair) {
        this.keyPair = keyPair;
    }

    /**
     * @return field value
     */
    public List<VMUserData> getUserData() {
        return userData;
    }

    /**
     * @param userData value to set
     */
    public void setUserData(List<VMUserData> userData) {
        this.userData = userData;
    }

    /**
     * 
     * @return field value
     * @since BUGR-3932
     */
    public String getTcName() {
        return tcName;
    }
    /**
     * 
     * @param tcName the new value to set
     * @since BUGR-3932
     */
    public void setTcName(String tcName) {
        this.tcName = tcName;
    }
    
    /**
     * @return field value
     */
    public VMAccount getVmAccount() {
        return vmAccount;
    }

    /**
     * @param vmAccount value to set
     */
    public void setVmAccount(VMAccount vmAccount) {
        this.vmAccount = vmAccount;
    }

	/**
	 * @return the vmProvider
	 */
	public VMProvider getVmProvider() {
		return vmProvider;
	}

	/**
	 * @param vmProvider the vmProvider to set
	 */
	public void setVmProvider(VMProvider vmProvider) {
		this.vmProvider = vmProvider;
	}
}

