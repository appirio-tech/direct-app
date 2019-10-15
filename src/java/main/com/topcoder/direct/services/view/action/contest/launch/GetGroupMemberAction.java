/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.dto.contest.GroupMember;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SortedCacheAddress;
import com.topcoder.web.common.cache.CacheClient;
import com.topcoder.web.common.cache.CacheClientFactory;
import com.topcoder.web.common.cache.MaxAge;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * This action handle group member search for given list of groups and substringof user's handle
 *
 * @version 1.0
 * 
 * Version 1.1 (Topcoder - Integrate Direct with Groups V5)
 * <ul>
 *     <li>Refactor projectGroup to comply with v5</li>
 * </ul>
 * @author TCSCODER
 * @version 1.1
 */
class GetGroupMemberAction extends ContestAction {
    /**
     * logging
     */
    private static final Logger logger = Logger.getLogger(GetGroupMemberAction.class);

    /**
     * Default limit of result list
     */
    private static final int DEFAULT_LIMIT = 20;

    /**
     * Url parameter for perPage
     */
    private static final String PER_PAGE = "perPage";

    /**
     * Url parameter for page
     */
    private static final String PAGE = "page";

    /**
     * Response header for X-Total-Pages
     */
    private static final String X_TOTAL_PAGE = "X-Total-Pages";

    /**
     * Default value url parameter perPage
     */
    private static final String PER_PAGE_VALUE = "1000";

    /**
     * List of source group id
     */
    private List<String> groupIds;

    /**
     * Group api endpoint
     */
    private String groupApiEndpoint;

    /**
     * Substring of user;s handle from query
     */
    private String handle;

    /**
     * Json object mapper
     */
    protected static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Handle incoming request
     *
     * @throws Exception if any exception occur
     */
    @Override
    protected void executeAction() throws Exception {
        try {
            List<? extends Map<String,String>> membersGroup = getData(groupIds);
            CollectionUtils.filter(membersGroup, new Predicate() {
                @Override
                public boolean evaluate(Object o) {
                    return ((Map<String,String>) o).get("handle").toLowerCase().contains(handle.toLowerCase());
                }
            });
            setResult(membersGroup.subList(0, Math.min(membersGroup.size(), DEFAULT_LIMIT)));
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }
    }

    /**
     * Get list of user id and handle. It will try to get from cache first.
     * @param groupIds list of group ids
     * @return List of userHandlePair
     * @throws Exception if any exception occur
     */
    @SuppressWarnings("unchecked")
    private List<? extends Map<String,String>> getData(List<String> groupIds) throws Exception {
        CacheClient cc = null;
        List<? extends Map<String,String>> data = null;
        SortedCacheAddress cacheAddress = new SortedCacheAddress("group_member", MaxAge.HOUR);
        cacheAddress.addAll(groupIds);
        try{
            cc = CacheClientFactory.create();
            data = (List<? extends Map<String,String>>)cc.get(cacheAddress);
            logger.info("cache address " + cacheAddress.getKey());
            logger.info("data from cache: " + data.size());
        } catch (Exception e){
            logger.info("Can't get group member from cache");
        }

        if (data == null){
            Set<Long> groupMembers = getGroupMembers(groupIds);
            data = DirectUtils.getUsersFromId(groupMembers.toArray(new Long[groupMembers.size()]));
            try{
                cc.set(cacheAddress, data, MaxAge.HOUR);
            } catch (Exception e) {
                logger.error("Failed to put group member into cache ", e);
            }
        }

        return data;
    }

    /**
     * Get all group member user id from API
     * @param groupIds list of group ids
     * @return set of userId
     * @throws Exception if any exception occur
     */
    private Set<Long> getGroupMembers(List<String> groupIds) throws Exception{
        Set<Long> members = new HashSet<Long>();
        // this will be increased, for inner groups
        LinkedList<String> gids = new LinkedList<String>(groupIds);
        Set<String> gidProcessed = new HashSet<String>();
        
        CacheClient cc = null;
        boolean finished = false;
        while (!finished) {
            ListIterator<String> iter = gids.listIterator();
            finished = true;
            while (iter.hasNext()) {
                String gid = iter.next();
                if (!gidProcessed.contains(gid)) {
                    logger.info("processing gid: " + gid);
                    List<GroupMember> result = getGroupMemberByGid(gid);

                    for (GroupMember gm : result) {
                        if (gm.isGroup()) {
                            if (!gids.contains(gm.getMemberId())) {
                                iter.add(gm.getMemberId());
                                finished = false;
                            }
                            logger.info(" inner group: " +  gm.getMemberId());
                        } else {
                            members.add(Long.valueOf(gm.getMemberId()));
                        }
                    }
                    gidProcessed.add(gid);
                }
            }
        }
        return members;
    }


    /**
     * Query group member API
     *
     * @param gid group id to get its members
     * @return a RestResult of groupMember
     * @throws Exception if any exception occur
     */
    private List<GroupMember> getGroupMemberByGid(String gid) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        List<GroupMember> result = new ArrayList<GroupMember>();
        int page = 1;
        JsonNode jsonNode = null;
        try{
            boolean finished = false;
            URIBuilder groupApiEndpointUri = new URIBuilder(String.format(groupApiEndpoint, gid));
            while (!finished) {
                groupApiEndpointUri.setParameter(PER_PAGE, PER_PAGE_VALUE);
                groupApiEndpointUri.setParameter(PAGE, String.valueOf(page));
                HttpGet request = new HttpGet(groupApiEndpointUri.build());
                String jwtToken = getSessionData().getToken();

                request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
                request.addHeader(HttpHeaders.ACCEPT, "application/json");
                HttpResponse response = httpClient.execute(request);
                HttpEntity entity = response.getEntity();

                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    logger.error("Failed to get Group Member for " + gid + " - " + response.getStatusLine().toString());
                    throw new DirectException("Fail to get group member from group api");
                }

                jsonNode = objectMapper.readTree(entity.getContent());
                List<GroupMember> groupMembers = objectMapper.readValue(jsonNode.path("result"),
                        new TypeReference<List<GroupMember>>(){});
                for (GroupMember groupMember : groupMembers) {
                    result.add(groupMember);
                }
                String totalPage = DirectUtils.getHeader(response.getAllHeaders(), X_TOTAL_PAGE);
                finished = (page == Integer.valueOf(totalPage));
                page++;
            }
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return result;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    public String getGroupApiEndpoint() {
        return groupApiEndpoint;
    }

    public void setGroupApiEndpoint(String groupApiEndpoint) {
        this.groupApiEndpoint = groupApiEndpoint;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
}
