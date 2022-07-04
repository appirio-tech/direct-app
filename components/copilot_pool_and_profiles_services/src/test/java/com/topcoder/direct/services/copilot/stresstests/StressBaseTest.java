/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.stresstests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProfileInfo;
import com.topcoder.direct.services.copilot.model.CopilotProfileInfoType;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfo;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfoType;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;
import com.topcoder.direct.services.copilot.model.PlannedContest;

/**
 * <p>
 * Stress Base tests.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class StressBaseTest extends TestCase {

    /**
     * <p>Represent the stress test time.</p>
     */
    protected static final int STRESS_TEST_TIME=10;

    /**
     * <p>Represents instance of {@link HibernateTemplate} used for testing.</p>
     */
    protected HibernateTemplate hibernateTemplate;

    /**
     * <p>Sets instance of {@link SessionFactory} used by this test case.</p>
     *
     * @param sessionFactory the session factory to set
     */
    @Autowired
    protected void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * <p>Retrieves instance of {@link SessionFactory} used by this test case.</p>
     *
     * @return the hibernate session factory
     */
    public SessionFactory getSessionFactory() {
        return hibernateTemplate != null ? hibernateTemplate.getSessionFactory() : null;
    }

    /**
     * <p>Creates a test {@link CopilotProfile} instance filled with dummy data.</p>
     *
     * @return {@link CopilotProfile} instance filled with dummy data
     */
    protected static CopilotProfile createCopilotProfile() {

        CopilotProfileStatus copilotProfileStatus = createCopilotProfileStatus();

        CopilotProfile copilotProfile = new CopilotProfile();
        copilotProfile.setActivationDate(new Date());
        copilotProfile.setReliability(25F);
        copilotProfile.setShowCopilotEarnings(true);
        copilotProfile.setSuspensionCount(1000);
        copilotProfile.setUserId(1000);
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
    protected static CopilotProfileStatus createCopilotProfileStatus() {
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
    protected static CopilotProject createCopilotProject() {

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
    protected static CopilotProjectStatus createCopilotProjectStatus() {

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
    protected static CopilotType createCopilotType() {

        CopilotType copilotType = new CopilotType();

        copilotType.setName("testCopilotType");
        copilotType.setCreateDate(new Date());
        copilotType.setModifyDate(new Date());
        copilotType.setCreateUser("user");
        copilotType.setModifyUser("user");

        return copilotType;
    }

    /**
     * <p>Creates a test {@link CopilotProfileInfo} instance filled with dummy data.</p>
     *
     * @return {@link CopilotProfileInfo} instance filled with dummy data
     */
    protected static CopilotProfileInfo createCopilotProfileInfo() {

        CopilotProfileInfo copilotProfileInfo = new CopilotProfileInfo();

        copilotProfileInfo.setValue("value");
        copilotProfileInfo.setModifyUser("modifyUser");
        copilotProfileInfo.setCreateDate(new Date());
        copilotProfileInfo.setModifyDate(new Date());
        copilotProfileInfo.setCreateUser("user");
        copilotProfileInfo.setModifyUser("user");

        return copilotProfileInfo;
    }

    /**
     * <p>Creates a test {@link CopilotProfileInfoType} instance filled with dummy data.</p>
     *
     * @return {@link CopilotProfileInfoType} instance filled with dummy data
     */
    protected static CopilotProfileInfoType createCopilotProfileInfoType() {

        CopilotProfileInfoType copilotProfileInfoType = new CopilotProfileInfoType();

        copilotProfileInfoType.setName("copilotProfileInfoType");
        copilotProfileInfoType.setCreateDate(new Date());
        copilotProfileInfoType.setModifyDate(new Date());
        copilotProfileInfoType.setCreateUser("user");
        copilotProfileInfoType.setModifyUser("user");

        return copilotProfileInfoType;
    }

    /**
     * <p>Creates a test {@link CopilotProjectInfo} instance filled with dummy data.</p>
     *
     * @return {@link CopilotProjectInfo} instance filled with dummy data
     */
    protected static CopilotProjectInfo createCopilotProjectInfo() {

        CopilotProjectInfo copilotProjectInfo = new CopilotProjectInfo();

        copilotProjectInfo.setValue("value");
        copilotProjectInfo.setModifyUser("modifyUser");
        copilotProjectInfo.setCreateDate(new Date());
        copilotProjectInfo.setModifyDate(new Date());
        copilotProjectInfo.setCreateUser("user");
        copilotProjectInfo.setModifyUser("user");

        return copilotProjectInfo;
    }

    /**
     * <p>Creates a test {@link CopilotProjectInfoType} instance filled with dummy data.</p>
     *
     * @return {@link CopilotProjectInfoType} instance filled with dummy data
     */
    protected static CopilotProjectInfoType createCopilotProjectInfoType() {

        CopilotProjectInfoType copilotProjectInfoType = new CopilotProjectInfoType();

        copilotProjectInfoType.setName("copilotProjectInfoType");
        copilotProjectInfoType.setCreateDate(new Date());
        copilotProjectInfoType.setModifyDate(new Date());
        copilotProjectInfoType.setCreateUser("user");
        copilotProjectInfoType.setModifyUser("user");

        return copilotProjectInfoType;
    }

    /**
     * <p>Creates a test {@link PlannedContest} instance filled with dummy data.</p>
     *
     * @return {@link PlannedContest} instance filled with dummy data
     */
    protected static PlannedContest createPlannedContest() {

        PlannedContest plannedContest = new PlannedContest();

        plannedContest.setName("plannedContest");
        plannedContest.setStartDate(new Date());
        plannedContest.setEndDate(new Date());
        plannedContest.setCreateDate(new Date());
        plannedContest.setModifyDate(new Date());
        plannedContest.setCreateUser("user");
        plannedContest.setModifyUser("user");

        return plannedContest;
    }

    /**
     * <p>Creates a test instance of {@link CopilotProjectPlan} filled with dummy data.</p>
     *
     * @return {@link CopilotProjectPlan} filled with dummy data
     */
    protected static CopilotProjectPlan createCopilotProjectPlan() {

        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        copilotProjectPlan.setCopilotProjectId(1000L);
        copilotProjectPlan.setPlannedBugRaces(500);
        copilotProjectPlan.setPlannedDuration(1000);
        copilotProjectPlan.setPlannedContests(new HashSet<PlannedContest>());

        copilotProjectPlan.setCreateDate(new Date());
        copilotProjectPlan.setModifyDate(new Date());
        copilotProjectPlan.setCreateUser("user");
        copilotProjectPlan.setModifyUser("user");

        return copilotProjectPlan;
    }

    /**
     * <p>Executes batch sql statements stored in file specified by passed parameter.</p>
     *
     * @param sessionFactory hibernate session factory to use
     * @param fileName       path to file containing sql statements
     *
     * @throws java.io.IOException if any error occurs while reading the file
     * @throws org.hibernate.HibernateException
     *                             if any error occurs during executing sql statements
     */
    protected static void executeBatch(SessionFactory sessionFactory, String fileName) throws IOException {

        BufferedReader input = null;

        try {
            input = new BufferedReader(new FileReader(fileName));
            Session session = sessionFactory.getCurrentSession();
            String line;
            while ((line = input.readLine()) != null) {

                if (line.trim().length() != 0) {
                    session.createSQLQuery(line).executeUpdate();
                }
            }

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // ignores exception
                }
            }
        }
    }

}
