/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot;

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
 * <p>Defines helper methods used by the test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {

    /**
     * <p>Creates new instance of <code>TestHelper</code> class.</p>
     */
    private TestHelper() {
        // empty constructor
    }

    /**
     * <p>Retrieves value of specified field using reflection from passed object instance.</p>
     *
     * @param clazz     object class
     * @param object    object to use
     * @param fieldName name of the field to retrieve
     *
     * @return the field value
     *
     * @throws Exception if any error occurs
     */
    public static Object getFieldValue(Class clazz, Object object, String fieldName) throws Exception {

        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
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
    public static void executeBatch(SessionFactory sessionFactory, String fileName) throws IOException {

        BufferedReader input = null;
        String line;

        try {
            input = new BufferedReader(new FileReader(fileName));
            Session session = sessionFactory.getCurrentSession();
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
     * <p>Assert that passed {@link CopilotProfile} are exactly the same.</p>
     *
     * @param copilotProfile copilot profile to check
     * @param result         copilot profile to check
     */
    public static void assertCopilotProfile(CopilotProfile copilotProfile, CopilotProfile result) {
        Assert.assertEquals("Invalid CopilotProfile id", copilotProfile.getId(), result.getId());
        Assert.assertEquals("Invalid CopilotProfile status", copilotProfile.getStatus(), result.getStatus());
        Assert.assertEquals("Invalid CopilotProfile activation date", copilotProfile.getActivationDate(),
                result.getActivationDate());
        Assert.assertEquals("Invalid CopilotProfile create user", copilotProfile.getCreateUser(),
                result.getCreateUser());
        Assert.assertEquals("Invalid CopilotProfile modify user", copilotProfile.getModifyUser(),
                result.getModifyUser());
        Assert.assertEquals("Invalid CopilotProfile reliability", copilotProfile.getReliability(),
                result.getReliability(), 0F);
        Assert.assertEquals("Invalid CopilotProfile suspension count", copilotProfile.getSuspensionCount(),
                result.getSuspensionCount());
        Assert.assertEquals("Invalid CopilotProfile user id", copilotProfile.getUserId(), result.getUserId());

        Assert.assertEquals("Invalid CopilotProfileInfo count", copilotProfile.getProfileInfos().size(),
                result.getProfileInfos().size());
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

    /**
     * <p>Creates a test {@link CopilotProfileInfo} instance filled with dummy data.</p>
     *
     * @return {@link CopilotProfileInfo} instance filled with dummy data
     */
    public static CopilotProfileInfo createCopilotProfileInfo() {

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
    public static CopilotProfileInfoType createCopilotProfileInfoType() {

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
    public static CopilotProjectInfo createCopilotProjectInfo() {

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
    public static CopilotProjectInfoType createCopilotProjectInfoType() {

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
    public static PlannedContest createPlannedContest() {

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
}
