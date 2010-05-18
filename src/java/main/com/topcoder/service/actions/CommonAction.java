package com.topcoder.service.actions;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.service.configs.ConfigUtils;

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
