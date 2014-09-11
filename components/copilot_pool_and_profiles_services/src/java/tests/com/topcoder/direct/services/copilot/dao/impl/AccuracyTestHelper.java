/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProfileInfo;
import com.topcoder.direct.services.copilot.model.CopilotProfileInfoType;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfo;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfoType;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;
import com.topcoder.direct.services.copilot.model.PlannedContest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;

/**
 * Accuracy test help class
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class AccuracyTestHelper {
    /**
     * <p>Persist all required by {@link CopilotProject} entities.</p>
     *
     * @param hibernateTemplate hibernate template to use
     * @param copilotProfile    copilot profile to save
     * @param copilotProject    copilot project to save
     */
    public static void persistCopilotProjectDependencies(HibernateTemplate hibernateTemplate, CopilotProfile
            copilotProfile, CopilotProject copilotProject) {
        hibernateTemplate.save(copilotProfile.getStatus());
        hibernateTemplate.save(copilotProfile);

        copilotProject.setCopilotProfileId(copilotProfile.getId());
        hibernateTemplate.save(copilotProject.getStatus());
        hibernateTemplate.save(copilotProject.getCopilotType());
    }

    /**
     * <p>Creates a test {@link CopilotProfile} instance filled with dummy data.</p>
     *
     * @return {@link CopilotProfile} instance filled with dummy data
     */
    public static CopilotProfile createCopilotProfile() {

        CopilotProfileStatus copilotProfileStatus = createCopilotProfileStatus();

        CopilotProfile copilotProfile = new CopilotProfile();
        copilotProfile.setActivationDate(new Date());
        copilotProfile.setReliability(10F);
        copilotProfile.setShowCopilotEarnings(true);
        copilotProfile.setSuspensionCount(1);
        copilotProfile.setUserId(10);
        copilotProfile.setStatus(copilotProfileStatus);

        copilotProfile.setProfileInfos(new HashSet<CopilotProfileInfo>());

        copilotProfile.setCreateDate(new Date());
        copilotProfile.setModifyDate(new Date());
        copilotProfile.setCreateUser("user");
        copilotProfile.setModifyUser("user");

        return copilotProfile;
    }

    /**
     * <p>Creates a test {@link CopilotProfileStatus} instance filled with dummy data.</p>
     *
     * @return {@link CopilotProfileStatus} instance filled with dummy data
     */
    public static CopilotProfileStatus createCopilotProfileStatus() {
        CopilotProfileStatus copilotProfileStatus = new CopilotProfileStatus();
        copilotProfileStatus.setName("test");
        copilotProfileStatus.setCreateDate(new Date());
        copilotProfileStatus.setModifyDate(new Date());
        copilotProfileStatus.setCreateUser("user");
        copilotProfileStatus.setModifyUser("user");
        return copilotProfileStatus;
    }

    /**
     * <p>Creates a test {@link CopilotProject} instance filled with dummy data.</p>
     *
     * @return {@link CopilotProject} instance filled with dummy data
     */
    public static CopilotProject createCopilotProject() {

        CopilotProject copilotProject = new CopilotProject();

        copilotProject.setName("testProject");
        copilotProject.setCompletionDate(new Date());

        copilotProject.setCustomerFeedback("customerFeedback");
        copilotProject.setCustomerRating(0.5F);
        copilotProject.setPmFeedback("pmFeedback");
        copilotProject.setPmRating(0.1F);
        copilotProject.setPrivateProject(true);
        copilotProject.setTcDirectProjectId(3L);
        copilotProject.setProjectInfos(new HashSet<CopilotProjectInfo>());

        copilotProject.setStatus(createCopilotProjectStatus());
        copilotProject.setCopilotType(createCopilotType());

        copilotProject.setCreateDate(new Date());
        copilotProject.setModifyDate(new Date());
        copilotProject.setCreateUser("user");
        copilotProject.setModifyUser("user");

        return copilotProject;
    }

    /**
     * <p>Creates a test {@link CopilotProjectStatus} instance filled with dummy data.</p>
     *
     * @return {@link CopilotProjectStatus} instance filled with dummy data
     */
    public static CopilotProjectStatus createCopilotProjectStatus() {

        CopilotProjectStatus copilotProjectStatus = new CopilotProjectStatus();

        copilotProjectStatus.setName("testCopilotStatus");
        copilotProjectStatus.setCreateDate(new Date());
        copilotProjectStatus.setModifyDate(new Date());
        copilotProjectStatus.setCreateUser("user");
        copilotProjectStatus.setModifyUser("user");

        return copilotProjectStatus;
    }

    /**
     * <p>Creates a test {@link CopilotType} instance filled with dummy data.</p>
     *
     * @return {@link CopilotType} instance filled with dummy data
     */
    public static CopilotType createCopilotType() {

        CopilotType copilotType = new CopilotType();

        copilotType.setName("testCopilotType");
        copilotType.setCreateDate(new Date());
        copilotType.setModifyDate(new Date());
        copilotType.setCreateUser("user");
        copilotType.setModifyUser("user");

        return copilotType;
    }
}
