/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import com.topcoder.shared.util.ApplicationServer
import com.topcoder.web.common.tag.HandleTag

/**
 * This class is the <code>Tag Lib</code> for <code>Scorecard</code> pages.
 *
 * @author pvmagacho
 * @version 1.0
 * @since System Assembly - Direct TopCoder Scorecard Tool Integration
 */
class ScorecardTagLib {
	
	private static final String[] lightStyles = ["coderTextOrange", "coderTextWhite", "coderTextGray", "coderTextGreen", "coderTextBlue",
		"coderTextYellow", "coderTextRed"];

	private static final String[] darkStyles = ["coderTextOrange", "coderTextBlack", "coderTextGray", "coderTextGreen", "coderTextBlue",
		"coderTextYellow", "coderTextRed"]
	
	/**
	 * Creates the member link.
	 */
	def memberLink = {attrs, body ->
		out << new HandleTag().getLink(attrs['coderId'], "", "", null, null, lightStyles, darkStyles, false)		
	}
}
