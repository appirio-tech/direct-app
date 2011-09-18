/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import com.topcoder.security.TCSubject
import com.topcoder.shared.security.User

import com.topcoder.scorecard.security.AuthenticationHelper

/**
 * <p>
 * This class is the <code>Filter</code> for <code>Scorecard</code> domain. It will filter all incoming requests and make the
 * necessary Authentication.
 * </p>
 *
 * @author pvmagacho
 * @version 1.0
 * @since System Assembly - Direct TopCoder Scorecard Tool Integration
 */
class ScorecardFilters {

	def filters = {
		login(controller:'*', action:'logout', invert:true) {
			before = {
				// check if DIRECT_USER attribute is set
				TCSubject tcSubject = request.session['DIRECT_USER']
				if (!tcSubject) {
					log.debug("Creating DIRECT_USER")

					// Get current user from cookie
					User user = AuthenticationHelper.getCurrentUser(request, response)
					if (!user) {
						log.error "User is not authenticated. Redirecting to Login page."
						redirect(url: '/direct/home.action')
						return false
					}

					// Get TCSubject instance for user
					tcSubject = AuthenticationHelper.getTCSubject(user.id)
					request.session['DIRECT_USER'] = tcSubject
				}

				// current user have scorecard administrator role
				Boolean hasDirectRole = request.session['SCORECARD_ROLE']
				if (hasDirectRole == null) {
					hasDirectRole = AuthenticationHelper.isScorecardAdmin(tcSubject)
					request.session['SCORECARD_ROLE'] = hasDirectRole
					log.info "User " + tcSubject.userid + " has scorecard administrator role : " + hasDirectRole
				}

				if (!hasDirectRole) {
					log.error "User does not have scorecard admin role. Redirecting to Dashboard page."
					redirect(url: '/direct/dashboardActive.action')
					return false
				}

				request.setAttribute('userId', tcSubject.userid)
			}
		}
	}
}
