/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.topcoder.direct.services.configs.ServerConfiguration;
import com.topcoder.direct.services.view.dto.contest.GroupMember;
import com.topcoder.direct.services.view.dto.my.RestResult;
import com.topcoder.direct.services.view.util.DirectProperties;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.struts2.ServletActionContext;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.net.URI;
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
     * List of source group id
     */
    private List<Long> groupIds;

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
            List<? extends Map<String,String>> membersGroup = getData();
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
     *
     * @return List of userHandlePair
     * @throws Exception if any exception occur
     */
    @SuppressWarnings("unchecked")
    private List<? extends Map<String,String>> getData() throws Exception {
        CacheClient cc = null;
        List<? extends Map<String,String>> data = null;
        SortedCacheAddress cacheAddress = new SortedCacheAddress("group_member", MaxAge.FIVE_MINUTES);
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
            Set<Long> groupMembers = getGroupMembers();
            data = DirectUtils.getUsersFromId(groupMembers.toArray(new Long[groupMembers.size()]));
            try{
                cc.set(cacheAddress, data, MaxAge.FIVE_MINUTES);
            } catch (Exception e) {
                logger.error("Failed to put group member into cache ", e);
            }
        }

        return data;
    }

    /**
     * Get all group member user id from API
     *
     * @return set of userId
     * @throws Exception if any exception occur
     */
    private Set<Long> getGroupMembers() throws Exception{
        Set<Long> members = new HashSet<Long>();
        // this will be increased, for inner groups
        LinkedList<Long> gids = new LinkedList<Long>(groupIds);
        Set<Long> gidProcessed = new HashSet<Long>();
        boolean finished = false;
        while (!finished) {
            ListIterator<Long> iter = gids.listIterator();
            while (iter.hasNext()) {
                Long gid = iter.next();
                if (!gidProcessed.contains(gid)) {
                    logger.info("processing gid: " + gid);
                    RestResult<GroupMember> result = getGroupMemberByGid(gid);
                    if (result != null) {
                        for (GroupMember gm : result.getContent()) {
                            if (gm.isGroup()) {
                                if (!gids.contains(gm.getMemberId())) {
                                    iter.add(gm.getMemberId());
                                }
                                logger.info(" inner group: " + gm.getMemberId());
                            } else {
                                members.add(gm.getMemberId());
                            }
                        }
                    }

                    gidProcessed.add(gid);
                }
            }

            if (gids.size() == gidProcessed.size()) {
                finished = true;
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
    private RestResult<GroupMember> getGroupMemberByGid(Long gid) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JsonNode jsonNode = null;
        try{
            URI groupApiEndpointUri = new URI(String.format(groupApiEndpoint, gid));
            HttpGet request = new HttpGet(groupApiEndpointUri);
            String jwtToken = (String)ServletActionContext.getServletContext().getAttribute(DirectProperties.TOKEN_ATTR);

            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
            request.addHeader(HttpHeaders.ACCEPT, "application/json");
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                logger.error("Failed to get Group Member for " + gid + " - " + response.getStatusLine().toString());
                return null;
            }

            jsonNode = objectMapper.readTree(entity.getContent());

        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return objectMapper.readValue(jsonNode.get("result"),
                objectMapper.getTypeFactory().constructParametricType(RestResult.class, GroupMember.class));
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
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
