package com.topcoder.management.project;

import java.io.Serializable;

/**
 * This class represents a configuration of the client billing project. It has
 * two properties, one is BillingProjectConfigType, the other is the value of
 * String type.
 * 
 * @author hohosky
 * @version 1.0
 */
public class BillingProjectConfiguration implements Serializable {
    
    /**
     * Represents the type of the billing project config.
     */
    private BillingProjectConfigType type;
    
    /**
     * Represents the value of the billing project config.
     */
    private String value;
    
    /**
     * Creates a BillingProjectConfiguration instance with type and value.
     * 
     * @param type the type of the billing project configuration.
     * @param value the value of the billing project configuration.
     */
    public BillingProjectConfiguration(BillingProjectConfigType type, String value) {
        this.type = type;
        this.value = value;
    }
    
    /**
     * Creates a BillingProjectConfiguration instance.
     */
    public BillingProjectConfiguration() {
	    this(null, "");
    }
    
    /**
     * Gets the type of the billing project configuration.
     * 
     * @return type of the billing project configuration.
     */
    public BillingProjectConfigType getType() {
	return this.type;
    }
    
    /**
     * Sets the type of the billing project configuration.
     * 
     * @param type the type of billing project configuration.
     */
    public void setType(BillingProjectConfigType type) {
	this.type = type;
    }
    
    /**
     * Gets the value of the billing project configuration.
     * 
     * @return the value of billing project configuration.
     */
    public String getValue() {
	return this.value;
    }
    
    /**
     * Sets the value of the billing project configuration.
     * 
     * @param value the value of the billing project configuration.
     */
    public void setValue(String value) {
	this.value = value;
    }
}
