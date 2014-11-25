package com.topcoder.direct.services.view.action.dashboard;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.user.UserServiceFacade;

public class SyncUserAction extends BaseDirectStrutsAction {

    /**
     * Represents the handle of the user to be sync.
     */
    private String handle;
    
    /**
     * Sets the handle of the user to sync.
     * 
     * @param handle the handle to set
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Empty constructor.
     */
    public SyncUserAction() {
        
    }
    
    /**
     * Process the incoming request.
     * 
     * @throws Exception if any error occurs
     */
    protected void executeAction() throws Exception {
	
        TCSubject user = DirectUtils.getTCSubjectFromSession();
        if (!DirectUtils.isTcStaff(user)) {
            throw new DirectException("Have no permission to sync user");
        }
        
        UserServiceFacade userServiceFacade = getUserServiceFacade();
        boolean syncJIRA = true;
        boolean syncWIKI = true;
        try {
            userServiceFacade.syncJiraUser(user, handle);
        } catch (Exception e) {
		    System.out.println("----------------syncJiraUser-----------------------------e-"+e);
            syncJIRA = false;
        }
        try {
            userServiceFacade.getConfluenceUser(user, handle);
        } catch (Exception e) {
			System.out.println("--------getConfluenceUser-------------------------------------e-"+e);
            syncWIKI = false;
        }
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        result.put("syncJIRA", syncJIRA);
        result.put("syncWIKI", syncWIKI);
        setResult(result);
    }
}
