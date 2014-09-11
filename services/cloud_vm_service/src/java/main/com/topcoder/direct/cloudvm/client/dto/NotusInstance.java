package com.topcoder.direct.cloudvm.client.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * This class represents the data of the Notus Cloud Instance.  
 * This is created by the Jackson serializer. 
 * 
 * Thread-safety: Mutable and not thread-safe.
 *
 * @author kanakarajank
 * @version 1.0
 *
 */
@JsonIgnoreProperties({ "software","productCodes", "primaryIP", "volumes", "secondaryIP", "root-only", "messageForward", "diskSize"})
public class NotusInstance {
	
	/**
	 * Represents the launch time
	 */
	private long launchTime;
	
	/**
	 * Represents the IP
	 */
	private String ip;
	
	/**
	 * Represents the requestID
	 */
	private String requestId;
	
	/**
	 * Represents the id of this instance at the Notus side
	 */
	private String id;
	
	/**
	 * Represents the keyName used for creating this instance
	 */
	private String keyName;
	
	/**
	 * Represents the name of the instance.
	 */
	private String name;
	
	/**
	 * Represents the requested name of this instance.
	 */
	private String requestName;
	
	/**
	 * Represent the instanceType
	 */
	private String instanceType;
	
	/**
	 * Represents the owner of this instance.
	 */
	private String owner;
	
	/**
	 * Represents the status of this image. 
	 */
	private int  status;
	
	/**
	 * Represents the hostname of this image.
	 */
	private String hostname;
	
	/**
	 * Represents the location of this image
	 */
	private String location;
	
	/**
	 * Represents the imageId of the instance.
	 */
	private String imageId;
	
	/**
	 * Represents the expirationtime
	 */
	private String expirationTime;

    @JsonIgnore
    private String primaryIP;

	/**
	 * Represents the software units 
	 */
	@JsonIgnore
	private List<String> software;

	/**
	 * Represents the product codes.
	 * This will be ignored by the JSON for deserializer.
	 */
	@JsonIgnore
	private List<String> productCodes;
	
	
	/**
	 * Getter for launchTime.
	 * @return the launchTime
	 */
	public long getLaunchTime() {
		return launchTime;
	}



	/**
	 * Setter for launchTime.
	 * @param launchTime the launchTime to set
	 */
	public void setLaunchTime(long launchTime) {
		this.launchTime = launchTime;
	}



	/**
	 * Getter for ip.
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}



	/**
	 * Setter for ip.
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}



	/**
	 * Getter for requestId.
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}



	/**
	 * Setter for requestId.
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



	/**
	 * Getter for the keyName.
	 * @return the keyName
	 */
	public String getKeyName() {
		return keyName;
	}



	/**
	 * Setter for the keyName.
	 * @param keyName the keyName to set
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}



	/**
	 * Getter for name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * Setter for name.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * Getter for requestName.
	 * @return the requestName
	 */
	public String getRequestName() {
		return requestName;
	}



	/**
	 * Setter for requestName.
	 * @param requestName the requestName to set
	 */
	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}



	/**
	 * Getter for instanceType.
	 * @return the instanceType
	 */
	public String getInstanceType() {
		return instanceType;
	}



	/**
	 * Setter for the instanceType.
	 * @param instanceType the instanceType to set
	 */
	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}



	/**
	 * Getter for the owner.
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}



	/**
	 * Setter for the owner.
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}



	/**
	 * Getter for the status.
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}



	/**
	 * Getter for hostname.
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}



	/**
	 * Setter for hostName.
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}



	/**
	 * Getter for location.
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}



	/**
	 * Setter for location.
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}



	/**
	 * Getter for imageId.
	 * @return the imageId
	 */
	public String getImageId() {
		return imageId;
	}



	/**
	 * Setter for imageId.
	 * @param imageId the imageId to set
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}



	/**
	 * Getter for expirationTime.
	 * @return the expirationTime
	 */
	public String getExpirationTime() {
		return expirationTime;
	}



	/**
	 * Setter for expirationTime.
	 * @param expirationTime the expirationTime to set
	 */
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}



	/**
	 * Getter for the softwares.
	 * @return the software
	 */
	public List<String> getSoftware() {
		return software;
	}



	/**
	 * Setter for the softwares
	 * @param software the software to set
	 */
	public void setSoftware(List<String> software) {
		this.software = software;
	}



	/**
	 * Getter for producct codes.
	 * @return the productCodes
	 */
	public List<String> getProductCodes() {
		return productCodes;
	}



	/**
	 * Setter for productCodes.
	 * @param productCodes the productCodes to set
	 */
	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}

    public String getPrimaryIP() {
        return primaryIP;
    }

    public void setPrimaryIP(String primaryIP) {
        this.primaryIP = primaryIP;
    }

    /**
	 * Represents the toString() 
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return "NotusInstance [launchTime=" + launchTime + ", ip=" + ip
				+ ", requestId=" + requestId + ", id=" + id + ", keyName="
				+ keyName + ", name=" + name + ", requestName=" + requestName
				+ ", instanceType=" + instanceType + ", owner=" + owner
				+ ", software=" + software + ", productCodes=" + productCodes
				+ ", hostName=" + hostname + ", location=" + location
				+ ", imageId=" + imageId + ", expirationTime=" + expirationTime
				+ "]";
	}
}
