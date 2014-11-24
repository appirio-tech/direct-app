/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.service.user.User;

import java.util.List;

/**
 * <p>A <code>Struts</code> action for handling requests for searching the users matching the provided criteria.</p>
 *
 * @author isv
 * @version 1.0 (Direct Permissions Setting Back-end and Integration Assembly 1.0)
 */
public class SearchUserAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>String</code> providing the search criteria for looking up for users.</p>
     */
    private String searchText;

    /**
     * <p>Constructs new <code>SearchUserAction</code> instance. This implementation does nothing.</p>
     */
    public SearchUserAction() {
    }

    /**
     * <p>Handles the incoming request. Gets the list of users matching the specified criteria and binds them to
     * request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        List<User> users = getUserService().searchUser(getSearchText());
        setResult(users);
    }

    /**
     * <p>Gets the search criteria for looking up for users.</p>
     *
     * @return a <code>String</code> providing the search criteria for looking up for users.
     */
    public String getSearchText() {
        return this.searchText;
    }

    /**
     * <p>Sets the search criteria for looking up for users.</p>
     *
     * @param searchText a <code>String</code> providing the search criteria for looking up for users.
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
