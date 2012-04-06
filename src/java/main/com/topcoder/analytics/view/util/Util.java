/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.util;

import java.util.HashSet;
import java.util.Set;

import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.DBMS;

/**
 * <p>Common utility class.</p>
 * 
 * @author flexme
 * @version 1.0
 */
public final class Util {
    /**
     * A private constructor.
     */
    private Util() {
        
    }
    
    /**
     * Gets the TCSubject instance for the given user.
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
