/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.ppt.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.topcoder.ppt.util.Util;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.security.SimpleResource;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.util.logging.Logger;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.SimpleResponse;
import com.topcoder.web.common.security.LightAuthentication;
import com.topcoder.web.common.security.SessionPersistor;

/**
 * <p>A mock implementation to be used for user authentication. It gets the submitted user
 * credentials from action's form and uses them to authenticate user using config file.</p>
 *
 * <p>
 * Version 1.1 (BUG TCCC-5802) Change notes:
 *  <ul>
 *   <li>Remove direct_sso cookie and its related logic.</li>
 *  </ul>
 * </p>
 *
 * @author flexme, ecnu_haozi
 * @version 1.1
 */
public class MockLoginProcessor implements LoginProcessor {
    /**
     * The users x ID map.
     */
    private static final Map<String, Long> usersMap = new HashMap<String, Long>();

    /**
     * The users x password map.
     */
    private static final Map<String, String> passwordsMap = new HashMap<String, String>();

    /**
     * A <code>Logger</code> to be used for logging the events encountered while processing the requests.
     */
    private static final Logger LOGGER = Logger.getLogger(MockLoginProcessor.class);
    
    /**
     * Construct a new <code>MockLoginProcessor</code>. It will read user data from a configuration file.
     */
    public MockLoginProcessor() {
        InputStream input = null;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            input = getClass().getClassLoader().getResourceAsStream("MockXmlAuthenticator.xml");
            if (input == null) {
                throw new IllegalStateException("The config file - MockXmlAuthenticator.xml can not be found.");
            }
            Document doc = docBuilder.parse(input);
            NodeList mappings = doc.getElementsByTagName("user");
            for (int i = 0; i < mappings.getLength(); i++) {
                Node node = mappings.item(i);
                NamedNodeMap attributes = node.getAttributes();

                String userName = attributes.getNamedItem("name").getNodeValue();
                String password = attributes.getNamedItem("password").getNodeValue();
                String id = attributes.getNamedItem("id").getNodeValue();

                usersMap.put(userName, Long.parseLong(id));
                passwordsMap.put(userName, password);
            }
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Invalid id", ex);
        } catch (ParserConfigurationException ex) {
            throw new IllegalStateException("Unable to read XML file", ex);
        } catch (SAXException ex) {
            throw new IllegalStateException("Unable to read XML file", ex);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to read XML file", ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * Perform authentication for a user.
     * 
     * @param username the handle of the user.
     * @param password the password of the user.
     * @param rememberMe a flag indicates whether the login state of the user should be remembered. 
     * @return true if the login is successful, false otherwise. 
     */
    public boolean login(String username, String password, boolean rememberMe) {
        TCSubject tcSubject = null;
        if (passwordsMap.containsKey(username) && password.equalsIgnoreCase(passwordsMap.get(username))) {
            Long userId = usersMap.get(username);

            try {
                tcSubject = Util.getTCSubject(userId);
            } catch(Exception e) {
                LOGGER.error("Fail to retrieve the TCSubject for the user with id - " + userId);
            }
        }

        if (tcSubject == null) {
            // not authenticated
            LOGGER.error("User " + username + " failed to authenticate successfully due to invalid credentials");
            return false;
        } else {
            LOGGER.info("User " + username + "  has been authenticated successfully");

            try {
                LightAuthentication auth = new LightAuthentication(
                            new SessionPersistor(ServletActionContext.getRequest().getSession()),
                            new SimpleRequest(ServletActionContext.getRequest()),
                            new SimpleResponse(ServletActionContext.getResponse()));
                auth.login(new SimpleUser(tcSubject.getUserId(), username, password), rememberMe);
                
                HttpSession session = ServletActionContext.getRequest().getSession();
                session.setAttribute("user", tcSubject);
                session.setAttribute("userHandle", username);
                return true;
            } catch (Exception e) {
                LOGGER.error("User " + username + " could not set cookie"); 
                return false;
            }
        }
    }
}
