/*
 * Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.security;

import com.topcoder.direct.services.configs.ServerConfiguration;
import com.topcoder.direct.services.view.action.LoginAction;
import com.topcoder.direct.services.view.form.LoginForm;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DirectProperties;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.jwt.DirectJWTSigner;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.util.DBMS;
import com.topcoder.shared.util.logging.Logger;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.SimpleResponse;
import com.topcoder.web.common.security.LightAuthentication;
import com.topcoder.web.common.security.SSOCookieService;
import com.topcoder.web.common.security.SSOCookieServiceImpl;
import com.topcoder.web.common.security.SessionPersistor;
import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>A mock processor to be used for handling requests for user authentication to application.</p>
 * <p/>
 * <p>This processor expects the actions of {@link com.topcoder.direct.services.view.action.LoginAction} type to be passed to it. It gets the submitted user
 * credentials from action's form and uses them to authenticate user using config file.</p>
 * <p/>
 * <p>
 * Version 1.1 (System Assembly - Direct Topcoder Scorecard Tool Integration) changes notes:
 * <ul>
 * <li>Set cookie to be used by scorecard application.</li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * Version 1.2 (BUG TCCC-5802) Change notes:
 * <ul>
 * <li>Remove direct_sso cookie and its related logic.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.3 (TopCoder Direct - JWT token generation)
 * <ul>
 *     <li>Updated {@link #processRequest(com.topcoder.direct.services.view.action.LoginAction)} to
 *     add JWT cookie</li>
 * </ul>
 * </p>
 *
 * @author pvmagacho, ecnu_haozi, Veve
 * @version 1.3
 */
public class MockLoginProcessor implements RequestProcessor<LoginAction> {

    /**
     * The users x ID map.
     */
    private static final Map<String, Long> usersMap = new HashMap<String, Long>();

    /**
     * The users x password map.
     */
    private static final Map<String, String> passwordsMap = new HashMap<String, String>();

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(MockLoginProcessor.class);

    /**
     * The Options for JWT generation.
     *
     * @since 1.3
     */
    private static final DirectJWTSigner.Options JWT_OPTIONS;

    static {
        JWT_OPTIONS = new DirectJWTSigner.Options();
        JWT_OPTIONS.setExpirySeconds(DirectProperties.JWT_EXPIRATION_SECONDS);
        JWT_OPTIONS.setIssuedAt(true);
    }

    /**
     * <p>Constructs new <code>MockLoginProcessor</code> instance. This implementation does nothing.</p>
     *
     * @throws IllegalStateException if any problem to instantiate the login processor
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
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(LoginAction action) {
        LoginForm form = action.getFormData();
        String username = form.getUsername();
        String password = form.getPassword();

        if ((username == null || (username != null && username.trim().length() == 0))
                && (password == null || (password != null && password.trim().length() == 0))) {
            action.setResultCode(LoginAction.RC_EMPTY_CREDENTIALS);
            return;
        }

        TCSubject tcSubject = null;
        if (passwordsMap.containsKey(username) && password.equalsIgnoreCase(passwordsMap.get(username))) {
            Long userId = usersMap.get(username);

            try {
                tcSubject = getTCSubject(userId);
            } catch (Exception e) {
                log.error("Fail to retrieve the TCSubject for the user with id - " + userId);
            }
        }

        if (tcSubject == null) {
            // not authenticated
            log.error("User " + username + " failed to authenticate successfully due to invalid credentials");
            action.setResultCode(LoginAction.RC_INVALID_CREDENTIALS);
        } else {
            action.getSessionData().setCurrentUser(tcSubject);
            action.getSessionData().setCurrentUserHandle(username);
            action.setResultCode(LoginAction.RC_SUCCESS);
            log.info("User " + username + "  has been authenticated successfully");

            try {
                // added by System Assembly - Direct Topcoder Scorecard Tool Integration (TC SSO cookie)
                LightAuthentication auth = new LightAuthentication(
                        new SessionPersistor(ServletActionContext.getRequest().getSession()),
                        new SimpleRequest(ServletActionContext.getRequest()),
                        new SimpleResponse(ServletActionContext.getResponse()));
                auth.login(new SimpleUser(tcSubject.getUserId(), username, password), action.getFormData().isRemember());

                //We need to set SSO cookie, BUT password in MockXmlAuthenticator.xml must be same with
                //the one in database
                SSOCookieService ssoCookieService = new SSOCookieServiceImpl();
                ssoCookieService.setSSOCookie(ServletActionContext.getResponse(), tcSubject.getUserId(),
                        action.getFormData().isRemember());

                // generate the jwt cookie
                DirectJWTSigner jwtSigner = new DirectJWTSigner(DirectProperties.JWT_V3_SECRET);

                Map<String, Object> claims = new HashMap<String, Object>();
                claims.put("iss", "https://" + DirectProperties.DOMAIN_AUTH0);
                claims.put("sub", "ad|" + tcSubject.getUserId());
                claims.put("aud", DirectProperties.CLIENT_ID_AUTH0);

                String sign = jwtSigner.sign(claims, JWT_OPTIONS);
                log.info("SIgned JWT: " + sign);
                // add session cookie, use -1 for expiration time
                DirectUtils.addDirectCookie(ServletActionContext.getResponse(),
                        ServerConfiguration.JWT_V3_COOKIE_KEY, sign, -1);
            } catch (Exception e) {
                log.error("User " + username + " could not set cookie", e);
                log.error(e.getMessage() + e.getCause());
                log.error(e.getStackTrace());
                action.setResultCode(LoginAction.RC_INVALID_CREDENTIALS);
            }
        }
    }

    /**
     * <p>
     * Gets the TCSubject instance for the given user.
     * </p>
     *
     * @param userId a <code>long</code> providing the ID of a user.
     * @return a <code>TCSubject</code> instance for the given user.
     * @throws Exception if any problem to retrieve the security roles
     */
    public static TCSubject getTCSubject(long userId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("security_roles");
        request.setProperty("uid", String.valueOf(userId));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("security_roles");

        final int recordNum = resultContainer.size();

        Set<TCPrincipal> principals = new HashSet<TCPrincipal>();
        for (int i = 0; i < recordNum; i++) {
            long roleId = resultContainer.getLongItem(i, "role_id");
            String description = resultContainer.getStringItem(i, "description");

            principals.add(new RolePrincipal(description, roleId));
        }

        return new TCSubject(principals, userId);
    }
}
