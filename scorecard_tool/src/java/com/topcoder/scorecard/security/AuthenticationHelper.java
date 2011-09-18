/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.scorecard.security;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.security.Resource;
import com.topcoder.shared.security.SimpleResource;
import com.topcoder.shared.security.User;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.SimpleResponse;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;

/**
 * <p>
 * Helper class to be used to authenticate user and user's role.
 * <p>
 *
 * @author pvmagacho
 * @version 1.0
 * @since System Assembly - Direct TopCoder Scorecard Tool Integration
 */
public class AuthenticationHelper {

	/**
	 * Scorecard Tool cookie.
	 */
	public static final String SSO_COOKIE = "direct_sso";
	
	/**
	 * <p>Represents the name of the scorecard administrator role.</p>
	 */
	private static final String SCORECARD_ADMIN_ROLE = "Scorecard Administrator";
	
	/**
     * <p>A <code>Logger</code> to be used for logging the events encountered during user authentication.</p>
     */
    private static final Logger logger = Logger.getLogger(AuthenticationHelper.class);

    /**
     * Private constructor.
     */
    private AuthenticationHelper() {
    	// empty
    }
    
    /**
     * Creates a new <code>TCSubject</code> instance from user identifier.
     *
     * @param userId the user login identifier
     * @return a new <code>TCSubject</code> instance
     * @throws Exception if any error occurs
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

            logger.debug("Adding role" + description);
            principals.add(new RolePrincipal(description, roleId));
        }

        return new TCSubject(principals, userId);
	}

	/**
	 * Check for scorecard administrator role
	 * 
	 * @param tcSubject the user to be checked
	 * @return true if user has scorecard administrator role, false otherwise
	 */
	@SuppressWarnings("unchecked")
	public static boolean isScorecardAdmin(TCSubject tcSubject) {
		Set<RolePrincipal> roles = tcSubject.getPrincipals();
		if (roles != null) {		
			for (RolePrincipal role : roles) {
				if (role.getName().equalsIgnoreCase(SCORECARD_ADMIN_ROLE)) {	
					return true;		
				}		
			}		
		}
		return false;
	}
	
	/**
	 * Get the current user from DIRECT SSO or TC SSO cookie. 
	 * 
	 * @param request the servlet http request
	 * @param response the servlet http response
	 * @return the current logged user, null otherwise
	 * @throws Exception if any error occurs
	 */
	public static User getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Entering method getCurrentUser");
		// check user in DIRECT SSO cookie (logged from Direct web site)
		User user = getUser(request, response, new SimpleResource(SSO_COOKIE));
		
		if (user == null || user.isAnonymous()) {
			logger.warn("DIRECT SSO cookie not found. Trying TC SSO cookie");
			
			// check user in TC SSO cookie (logged from TC web site)
			user = getUser(request, response, BasicAuthentication.MAIN_SITE);

			if (user == null || user.isAnonymous()) {
				logger.error("No user was found.");
				return null;
			}				
		}
		
		return user;		
	}
	
	
	/**
	 * Removes DIRECT SSO cookie from session.
	 * 
	 * @param request the servlet http request
	 * @param response the servlet http response
	 * @throws Exception if any error occurs
	 */
	public static void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BasicAuthentication auth = new BasicAuthentication(new SessionPersistor(request.getSession()), new SimpleRequest(request),
				new SimpleResponse(response), new SimpleResource(SSO_COOKIE), DBMS.JTS_OLTP_DATASOURCE_NAME);
		auth.logout();
	}
	
	/**
	 * Get user from cookie.
	 * 
	 * @param request the servlet http request
	 * @param response the servlet http response
	 * @param cookie the cookie to use to check user 
	 * @return the current logged user, null otherwise
	 * @throws Exception if any error occurs
	 */
	private static User getUser(HttpServletRequest request, HttpServletResponse response, Resource cookie) throws Exception {
		BasicAuthentication auth = new BasicAuthentication(new SessionPersistor(request.getSession()), new SimpleRequest(request), 
				new SimpleResponse(response), cookie, DBMS.JTS_OLTP_DATASOURCE_NAME);
		return auth.checkCookie();
	}
}
