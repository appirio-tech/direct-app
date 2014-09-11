/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.admin.scorecards

import com.topcoder.scorecard.security.AuthenticationHelper

import grails.converters.JSON
import grails.util.GrailsNameUtils
import net.sf.json.JSONObject

import org.springframework.web.servlet.ModelAndView

/**
 * This class is the <code>Controller</code> for <code>Scorecard</code> domain.
 *
 * <p>
 * Version 1.1 change notes:
 *   <ol>
 *     <li>Added <code>ajaxContent</code>, <code>find</code> closures to this controller.</li>
 *     <li>Updated <code>show</code>, <code>edit</code>, <code>save</code> closures.</li>
 *   </ol>
 * </p>
 * 
 * <p>
 * Version 1.2 change notes:
 *   <ol>
 *     <li>Added <code>logout</code> closure. Will remove user Direct Cookie and invalidate current http session.
 *   </ol>
 * </p>
 * 
 * <p>
 * Version 1.3 change notes:
 *   <ol>
 *     <li>Added <code>clone</code> closure.
 *   </ol>
 * </p>
 * @author TCSASSEMBER, pvmagacho, winstys
 * @version 1.3
 */
class ScorecardController {

	static allowedMethods = [save: "POST"]

	/**
	 * The message source instance which will be injected by Grails framework.
	 */
	def messageSource

	/**
	 * The <code>ScorecardService</code> instance which will be injected by Grails framework.
	 */
	def scorecardService

	def index = {
		redirect(action: "find", params: params)
	}

	/**
	 * The view to search scorecards.
	 */
	def find = { [] }

	/**
	 * Returns the scorecard data which will be used by AJAX call in UI. 
	 */
	def ajaxContent = {
		def scorecardInstance = Scorecard.get(params.id)
		if (!scorecardInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scorecard.label', default: 'Scorecard'), params.id])}"
		}
		else {
			[scorecardInstance: scorecardInstance]
		}
	}

	/**
	 * Search action to return the JSON result of matched scorecards.
	 */
	def search = {
		// the json result
		def result = [ success: true ]
		try {
			// the HQL query
			def hql = new StringBuilder("from Scorecard where 1 = :one")
			// the HQL query parameters
			def positionalParams = [one : 1];

			// sn - scorecard name
			if (!GrailsNameUtils.isBlank(params.sn)) {
				// socrecard name is case-insensitive
				hql << " and lower(scorecardName) like :scorecardName"
				positionalParams.scorecardName = '%' + params.sn.trim().toLowerCase() + '%'
			}

			// convert string type list to long type list
			def convertToLongList = {pname, lv ->
				def resv = [];
				lv.each {val ->
					if (!val.isLong()) {
						throw new IllegalArgumentException(pname + " must be numbers.");
					}
					resv.add val.toLong()
				}
				resv
			}

			// cn - contest name, ignore for now

			// suf - scorecard usage frequency, ignore for now

			// spt - project type
			if (params.spt) {
				hql << " and projectCategory.projectType.id in (:projectTypeIds)"
				positionalParams.projectTypeIds = convertToLongList('spt', params.list('spt'))
			}

			// sc - category
			if (params.sc) {
				hql << " and projectCategory.id in (:projectCategoryIds)"
				positionalParams.projectCategoryIds = convertToLongList('sc', params.list('sc'))
			}

			// st - scorecard type
			if (params.st) {
				hql << " and scorecardType.id in (:scorecardTypeIds)"
				positionalParams.scorecardTypeIds = convertToLongList('st', params.list('st'))
			}

			// ss - status
			if (params.ss) {
				if (!params.ss.isLong()) {
					throw new IllegalArgumentException("ss must be a number.");
				}
				hql << " and scorecardStatus.id=:scorecardStatus"
				positionalParams.scorecardStatus = params.long('ss')
			}

			// get the total count of the searching result
			result.totalCount = Scorecard.findAll(hql.toString(), positionalParams).size

			// Pagination & Sort Params
			def queryParams = [offset: 0L];
			if (params.offset) {
				queryParams.offset = params.long('offset')
			}
			if (params.max) {
				queryParams.max = params.long('max')
			}
			if (!GrailsNameUtils.isBlank(params.sortby)) {
				// prevent sql injection
				def sortby = params.sortby.trim().replaceAll("[^a-zA-Z\\.]", "")
				def asc = params.boolean('asc')
				hql << " order by " << sortby << (asc ? " asc" : " desc")
			}

			result.data = Scorecard.findAll(hql.toString(), positionalParams, queryParams)
			HashMap jsonMap = new HashMap();
			
			// Set the editable of scorecards.
			jsonMap.data = result.data.collect {scorecard ->
				return [id: scorecard.id, scorecardName: scorecard.scorecardName, scorecardVersion: scorecard.scorecardVersion, scorecardType: scorecard.scorecardType.id,
						projectCategory: scorecard.projectCategory.id, scorecardStatus:scorecard.scorecardStatus.id, editable: Util.checkScorecardEditable(scorecard)]
			}
			jsonMap.count = result.data.size
			jsonMap.totalCount = result.totalCount;
			jsonMap.success = true;
			render text: jsonMap as JSON, contentType: 'application/json', status: 200
		} catch (Exception e) {
			log.error(e)
			result.success = false
			result.message = e.getMessage()
			render text: result as JSON, contentType: 'application/json', status: 200
		}
	}

	/**
	 * The the entrie scorecard.
	 */
	def save = {
		def result = [success: true]
		try {
			def scorecardData = JSONObject.fromObject(params.scorecard)

			result.id = scorecardService.saveScorecard(messageSource, scorecardData, request)
			result.data = "${message(code: 'default.updated.message', args: [message(code: 'scorecard.label', default: 'Scorecard'), params.id])}"
		} catch (Exception e) {
			log.error(e)
			result.success = false
			result.message = e.getMessage()
		}

		render text: result as JSON, contentType: 'application/json', status: 200
	}

	private getScorecard(id) {
		def scorecardInstance = Scorecard.get(id)
		if (!scorecardInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'scorecard.label', default: 'Scorecard'), id])}"
			redirect(action: "find")
		}
		else {
			return scorecardInstance
		}
	}
	
	/**
	 * Render the scorecard.
	 */
	def show = {
		return [scorecardInstance: getScorecard(params.id)]
	}

	/**
	 * Edit the scorecard
	 */
	def edit = {
		return [scorecardInstance: getScorecard(params.id)]
	}

	/**
	 * Clone the scorecard
	 */
	def clone = {
		return [scorecardInstance: getScorecard(params.id)]
	}
	
	def create = {

	}

	/**
	 * Logout from scorecard tool.
	 */
	def logout = {
		// Invalidate session
		request.session.invalidate()

		AuthenticationHelper.logout(request, response)	

		// Redirect to logout
		log.info "session invalidated. Redirecting to login."
	  redirect(url: '/direct/home-redirect.jsp')
  }
}

