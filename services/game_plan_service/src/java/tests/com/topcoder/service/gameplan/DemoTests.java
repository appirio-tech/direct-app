/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.gameplan;

import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.gameplan.ejb.GamePlanServiceRemote;
import com.topcoder.service.util.gameplan.SoftwareProjectData;
import com.topcoder.service.util.gameplan.StudioProjectData;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanData;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The demo of this component.
 *
 * @author saarixx, FireIce
 * @version 1.0
 */
public class DemoTests {
    /**
     * This is demo for this component. For other entities and entity dao are almost the same as Project, the usage of
     * other entities is not showed.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDemo() throws Exception {
        // Get game plan service
        Context context = new InitialContext();
        GamePlanService gamePlanService =
                (GamePlanServiceRemote) context.lookup("GamePlanServiceBean/remote");
        // Create TCSubject instance for admin user
        Set<RolePrincipal> principals = new HashSet<RolePrincipal>();
        RolePrincipal adminRole = new RolePrincipal("Cockpit Administrator", 1);
        principals.add(adminRole);
        TCSubject tcSubject1 = new TCSubject(principals, 12345);
        // Retrieve game plan data for all existing TC Direct projects
        List<TCDirectProjectGamePlanData> gamePlanDataList =
                gamePlanService.retrieveGamePlanData(tcSubject1);
        for (TCDirectProjectGamePlanData gamePlanData : gamePlanDataList) {
            // Get data for software projects
            List<SoftwareProjectData> softwareProjects = gamePlanData.getSoftwareProjects();
            // Get data for studio projects
            List<StudioProjectData> studioProjects = gamePlanData.getStudioProjects();
            // Process projects data
            // ...
        }
        // Create TCSubject instance for non-admin user
        principals = new HashSet<RolePrincipal>();
        TCSubject tcSubject2 = new TCSubject(principals, 23456);
        // Retrieve game plan data for all TC Direct projects associated with user with ID=23456
        gamePlanDataList = gamePlanService.retrieveGamePlanData(tcSubject2);

        // Retrieve the game plan data for the specific TC Direct Project associated with the current user.
        TCDirectProjectGamePlanData gamePlanData = gamePlanService.retrieveGamePlanData(tcSubject2, 1L);
    }

}
