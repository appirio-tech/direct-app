/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util.jira;

import com.atlassian.jira.rpc.soap.client.*;
import com.atlassian.jira_soapclient.SOAPSession;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.view.dto.TcJiraIssue;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>The class provides a wrapper for the JiraSoapService. And it provides various methods to get issue data using
 * JiraSoapService.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (TC Cockpit Bug Tracking R1 Contest Tracking assembly)
 */
public class JiraRpcServiceWrapper {

    /**
     * The soap session between client and Jira RPC soap service.
     */
    private static SOAPSession soapSession;

    /**
     * The map used to store the mapping of resolution id to resolution name.
     */
    private static Map<String, String> RESOLUTION_NAMES;

    /**
     * The map used to store the mapping of issue status id to issue status name.
     */
    private static Map<String, String> ISSUE_STATUS_NAMES;

    /**
     * The static field used to count the number of authentication failure. The value will be reset to 0 after a
     * successful authentication.
     */
    private static int retryAttemptCount = 0;


    /**
     * Private constructor prevents from initialization.
     */
    private JiraRpcServiceWrapper() {
        // do nothing
    }

    /**
     * Initialize the soap session before calling the methods of RPC service.
     *
     * @throws Exception if any error occurs.
     */
    private static void initializeSoapSession() throws Exception {

        // Get configurations
		String jiraServiceUrl = ConfigUtils.getIssueTrackingConfig().getJiraRpcURL();
        String user = ConfigUtils.getIssueTrackingConfig().getUser();
        String password = ConfigUtils.getIssueTrackingConfig().getPassword();

		// create a soap session from the configured tc jira service soap url
		soapSession = new SOAPSession(new URL(jiraServiceUrl));
		soapSession.connect(user, password);

		// the JIRA SOAP Service and authentication token are used to make authentication calls
		JiraSoapService jiraSoapService = soapSession.getJiraSoapService();
		String authToken = soapSession.getAuthenticationToken();

        // initialize mappings of resolution names if needed
        if (RESOLUTION_NAMES == null) {
            RemoteResolution[] resolutions = jiraSoapService.getResolutions(authToken);
            RESOLUTION_NAMES = new HashMap<String, String>();
            for (RemoteResolution r : resolutions) {
                RESOLUTION_NAMES.put(r.getId(),r.getName());
            }
        }

        // initialize mappings of status names if needed
        if (ISSUE_STATUS_NAMES == null) {
            RemoteStatus[] statuses = jiraSoapService.getStatuses(authToken);
            ISSUE_STATUS_NAMES = new HashMap<String, String>();
            for (RemoteStatus s : statuses) {
                ISSUE_STATUS_NAMES.put(s.getId(), s.getName());
            }
        }

    }


    /**
     * Gets all the issues for specified contest id.
     *
     * @param contestId the id of the contest.
     * @param isStudio the flag to indicate whether the contest is a studio contest.
     * @return the list of all the issues of the contest.
     * @throws Exception if any error occurs.
     */
    public static List<TcJiraIssue> getIssuesForContest(long contestId, boolean isStudio) throws Exception {
        // if soap session is not established, initialize a soap session first
        if (soapSession == null) {
            initializeSoapSession();
        }

        // throw IllegalArgumentException when the contest id is not positive
        if (contestId <= 0 ) {
            throw new IllegalArgumentException("contest id should be positive.");
        }

        // build the JQL query first
        String softwareQuery = ConfigUtils.getIssueTrackingConfig().getSoftwareContestJQLQuery();
        String studioQuery = ConfigUtils.getIssueTrackingConfig().getStudioContestJQLQuery();
        String jqlQuery = (isStudio ? studioQuery : softwareQuery) + contestId;

        // List to store the final result
        final List<TcJiraIssue> result = new ArrayList<TcJiraIssue>();

        try {
            JiraSoapService service = soapSession.getJiraSoapService();
            String token = soapSession.getAuthenticationToken();

            List<Long> includeIssueTypeIds = ConfigUtils.getIssueTrackingConfig().getIncludedIssueTypeIds();

            // gets issues from the RPC service
            RemoteIssue[] issues = service.getIssuesFromJqlSearch(token, jqlQuery, ConfigUtils.getIssueTrackingConfig().getMaxResultNumber());

            for (RemoteIssue issue : issues) {

                TcJiraIssue tcJiraIssue = new TcJiraIssue((issue));

                tcJiraIssue.setResolutionName(RESOLUTION_NAMES.get(tcJiraIssue.getResolutionId()));
                tcJiraIssue.setStatusName(ISSUE_STATUS_NAMES.get(tcJiraIssue.getStatusId()));

                // check issue type
                if (includeIssueTypeIds.contains(Long.parseLong(issue.getType()))) {
                    result.add(tcJiraIssue);
                }
            }

        } catch (RemoteAuthenticationException authEx) {
            // got authentication exception, try authentication again until max error reached
            if (retryAttemptCount < ConfigUtils.getIssueTrackingConfig().getMaxAuthRetry()) {
                retryAttemptCount++;
                initializeSoapSession();
                return getIssuesForContest(contestId, isStudio);
            }
            else {
                // wrap into JiraRpcServiceAuthenticationException and rethrow
                throw new JiraRpcServiceAuthenticationException("Failed to authenticate with Jira RPC Service.", authEx);
            }

        } catch (Exception ex) {
            // all the other exception will be wrapped into JiraRpcServiceProcessingException
            throw new JiraRpcServiceProcessingException("Error occurs when calling Jira RPC Service.", ex);
        }

        // successfully get data, reset retry attempts
        retryAttemptCount = 0;
        return result;
    }
}
