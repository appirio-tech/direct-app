package com.topcoder.direct.services.view.action.contest.launch;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.direct.services.configs.ConfigUtils;

public class CommonAction extends BaseDirectStrutsAction {

    @Override
    protected void executeAction() throws Exception {
        // do nothing
    }

    public String getStudioConfigs() {
        Map<String,Object> configs = new HashMap<String, Object>();
        configs.put("overview", ConfigUtils.getStudioOverviews());
        configs.put("fees", ConfigUtils.getStudioContestFees());
        configs.put("fileTypes", ConfigUtils.getFileTypes().getFileTypes());
        setResult(configs);
        return SUCCESS;
    }
}
