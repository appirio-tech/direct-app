/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.randomstringimg.RandomStringImage;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for captcha image.</p>
 *
 * @author flexme
 * @version 1.0
 */
public class CaptchaImageAction extends ActionSupport {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 3139042178154621860L;

    /**
     * Represents the configuration file path for the random string image generator. It 
     * will be injected by Spring.
     */
    private String configFile;
    
    /**
     * Represents the stream holding the image content.
     */
    private ByteArrayInputStream byteInputStream;
    
    /**
     * Sets the configuration file path for the random string image generator.
     * 
     * @param configFile the configuration file path for the random string image generator.
     */
    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    /**
     * Construct a new <code>CaptchaImageAction</code> instance.
     */
    public CaptchaImageAction() {
        
    }
    
    /**
     * /**
     * Handles the incoming request. It will generate the random string image.
     *
     * @return {@link #SUCCESS} always.
     * @throws Exception if any error occurs
     */
    public String execute() throws Exception {
        RandomStringImage rsi = new RandomStringImage(configFile);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ServletActionContext.getRequest().getSession().setAttribute("captcha_str", rsi.generateRandomFromDictionaries(bs));
        bs.close();
        
        byteInputStream = new ByteArrayInputStream(bs.toByteArray());
        return SUCCESS;
    }
    
    /**
     * Gets the stream holding the image content.
     * 
     * @return the stream holding the image content.
     */
    public InputStream getInputStream() {
        return byteInputStream;
    }
}
