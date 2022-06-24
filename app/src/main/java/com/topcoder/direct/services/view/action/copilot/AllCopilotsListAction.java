/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.copilot;

import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TCSDEVELOPER
 * @version 1.0 (Manage Copilot Postings assembly)
 */
public class AllCopilotsListAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>CopilotProfileDAO</code> providing the DAO for copilot profiles..</p>
     */
    private CopilotProfileDAO copilotProfileDAO;

    /**
     * <p>Constructs new <code>AllCopilotsListAction</code> instance. This implementation does nothing.</p>
     */
    public AllCopilotsListAction() {
    }

    /**
     * <p>Gets the DAO for copilot profiles..</p>
     *
     * @return a <code>CopilotProfileDAO</code> providing the DAO for copilot profiles..
     */
    public CopilotProfileDAO getCopilotProfileDAO() {
        return this.copilotProfileDAO;
    }

    /**
     * <p>Sets the DAO for copilot profiles..</p>
     *
     * @param copilotProfileDAO a <code>CopilotProfileDAO</code> providing the DAO for copilot profiles..
     */
    public void setCopilotProfileDAO(CopilotProfileDAO copilotProfileDAO) {
        this.copilotProfileDAO = copilotProfileDAO;
    }

    /**
     * <p>Handles the incoming request. Gets the list of all existing copilot profiles and binds them to request.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        List<CopilotProfile> profiles = getCopilotProfileDAO().retrieveAll();
        Map<Long, CopilotProfile> profilesMap = new HashMap<Long, CopilotProfile>();
        for (CopilotProfile profile : profiles) {
            profilesMap.put(profile.getUserId(), profile);
        }
        DirectUtils.getServletRequest().setAttribute("copilotProfilesMap", profilesMap);
    }
}
