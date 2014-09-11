/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.dao.CopilotProjectPlanDAO;
import com.topcoder.direct.services.copilot.dao.LookupDAO;
import com.topcoder.direct.services.copilot.dao.UtilityDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;
import com.topcoder.direct.services.copilot.model.PlannedContest;
import junit.framework.JUnit4TestAdapter;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Demonstrates usage of this component.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:demoApplicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class Demo {

    /**
     * <p>Represents the instance of {@link HibernateTemplate}.</p>
     */
    private HibernateTemplate hibernateTemplate;

    /**
     * <p>Represents the instance of {@link CopilotProjectPlanDAO}.</p>
     */
    @Autowired
    private CopilotProjectPlanDAO copilotProjectPlanDAO;

    /**
     * <p>Represents the instance of {@link CopilotProfileDAO}.</p>
     */
    @Autowired
    private CopilotProfileDAO copilotProfileDAO;

    /**
     * <p>Represents the instance of {@link CopilotProjectDAO}.</p>
     */
    @Autowired
    private CopilotProjectDAO copilotProjectDAO;

    /**
     * <p>Represents the instance of {@link UtilityDAO}.</p>
     */
    @Autowired
    private UtilityDAO utilityDAO;

    /**
     * <p>Represents the instance of {@link LookupDAO}.</p>
     */
    @Autowired
    private LookupDAO lookupDAO;

    /**
     * <p>Sets {@link SessionFactory} for this test case.</p>
     *
     * @param sessionFactory session factory to set
     */
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
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
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(Demo.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        TestHelper.executeBatch(getSessionFactory(), "test_files/demo_delete.sql");
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @After
    public void tearDown() throws Exception {

        TestHelper.executeBatch(getSessionFactory(), "test_files/demo_delete.sql");
    }

    /**
     * <p>Demonstrates usage of this component.</p>
     *
     * @throws CopilotDAOException if any error occurs
     */
    @Test
    public void demoCopilotProjectPlanDAO() throws CopilotDAOException {

        // Crates and saves required dependant entities in other to test saving CopilotProjectPlan
        CopilotProfile copilotProfile = TestHelper.createCopilotProfile();
        CopilotProject copilotProject = TestHelper.createCopilotProject();

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);
        hibernateTemplate.save(copilotProject);

        // Note: The CopilotProjectPlanDAO has been already injected into this demo so no need to retrieve it from
        // application context
        // Despite using autowiring you can alternatively use the application context.
        // Retrieves CopilotProjectPlanDAO from the Spring application context
        // ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // CopilotProjectPlanDAO copilotProjectPlanDAO =
        //   (CopilotProjectPlanDAO) context.getBean("copilotProjectPlanDAO");

        // Creates copilot project plan
        CopilotProjectPlan copilotProjectPlan = createCopilotProjectPlan(copilotProject.getId());
        Set<PlannedContest> plannedContests = new HashSet<PlannedContest>();
        PlannedContest plannedContest = new PlannedContest();
        plannedContest.setName("Sample Design Contest");
        plannedContest.setDescription("This is a sample design contest");
        plannedContest.setProjectCategoryId(1);
        plannedContest.setStartDate(new Date(new Date().getTime() + 24 * 3600 * 1000));
        plannedContest.setEndDate(new Date(new Date().getTime() + 216 * 3600 * 1000));
        plannedContest.setCreateUser("user");
        plannedContest.setModifyUser("user");
        plannedContest.setCreateDate(new Date());
        plannedContest.setModifyDate(new Date());
        plannedContests.add(plannedContest);
        copilotProjectPlan.setPlannedContests(plannedContests);

        long copilotProjectPlanId = copilotProjectPlanDAO.create(copilotProjectPlan);

        // Updates the planned project duration
        copilotProjectPlan.setPlannedDuration(10);

        copilotProjectPlanDAO.update(copilotProjectPlan);

        // Retrieves the copilot project plan by its ID
        copilotProjectPlan = copilotProjectPlanDAO.retrieve(copilotProjectPlanId);
        assertCopilotProjectPlan(copilotProject, copilotProjectPlan, copilotProjectPlanId);

        // Retrieve the copilot project plan by copilot project ID
        copilotProjectPlan = copilotProjectPlanDAO.getCopilotProjectPlan(copilotProject.getId());
        Assert.assertEquals("Incorrect id.", copilotProjectPlanId, copilotProjectPlan.getId());

        // Delete the copilot project plan by copilot project ID
        copilotProjectPlanDAO.delete(copilotProjectPlanId);

        // Retrieves all copilot project plans
        List<CopilotProjectPlan> copilotProjectPlans = copilotProjectPlanDAO.retrieveAll();
        Assert.assertEquals("One plan were expected.", 0, copilotProjectPlans.size());
    }

    /**
     * <p>Demonstrates usage of this component.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void demoCopilotDAOs() throws Exception {

        // Note: all the dao used by this test method are already injected by Spring into this test case

        // inserts into database some sample data
        CopilotProfile profile = TestHelper.createCopilotProfile();
        CopilotProject project = TestHelper.createCopilotProject();
        setUpDatabase(profile, project);

        // Retrieves the copilot profile for copilot user with ID=1
        CopilotProfile copilotProfile = copilotProfileDAO.getCopilotProfile(profile.getUserId());
        Assert.assertNotNull("Null was returned.", copilotProfile);

        // Retrieves the copilot projects for copilot with profile ID=1
        List<CopilotProject> copilotProjects = copilotProjectDAO.getCopilotProjects(profile.getId());
        Assert.assertNotNull("Null was returned.", copilotProjects);

        // Retrieves all copilot profile statuses
        List<CopilotProfileStatus> copilotProfileStatuses = lookupDAO.getAllCopilotProfileStatuses();
        Assert.assertEquals("Two results were expected.", 2, copilotProfileStatuses.size());

        // Retrieves all copilot project statuses
        List<CopilotProjectStatus> copilotProjectStatuses = lookupDAO.getAllCopilotProjectStatuses();
        Assert.assertEquals("Two results were expected.", 2, copilotProjectStatuses.size());

        // Retrieves all copilot types
        List<CopilotType> copilotTypes = lookupDAO.getAllCopilotTypes();
        Assert.assertEquals("Two results were expected.", 2, copilotTypes.size());

        // Retrieves the bug count for contest with ID=1001
        int contestBugCount = utilityDAO.getContestBugCount(1001);
        Assert.assertEquals("One result was expected.", 1, contestBugCount);

        // Retrieves the copilot earnings for copilot user with ID=1
        double copilotEarnings = utilityDAO.getCopilotEarnings(1);
        Assert.assertEquals("Incorrect copilot earnings 500 was expected.", 500D, copilotEarnings, 0D);

        // Retrieves the latest bug resolution date for contest with ID=1
        Date contestLatestBugResolutionDate = utilityDAO.getContestLatestBugResolutionDate(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals("Method returned invalid date.", simpleDateFormat.parse("2010-09-02 12:00:00"),
                contestLatestBugResolutionDate);

        // Retrieves the IDs of OR contests for copilot with user ID=1
        // and TC direct project with ID=101
        long[] contestIds = utilityDAO.getCopilotProjectContests(1, 101);
        Assert.assertEquals("One result was expected.", 1, contestIds.length);
        Assert.assertEquals("Incorrect project id.", 1, contestIds[0]);
    }

    /**
     * <p>Asserts that retrieved {@link CopilotProjectPlan} has exactly the same values as expected.</p>
     *
     * @param copilotProject copilot project to use
     * @param copilotProjectPlan copilot project to assert
     * @param copilotProjectPlanId copilot project plan id to assert
     */
    private void assertCopilotProjectPlan(CopilotProject copilotProject, CopilotProjectPlan copilotProjectPlan,
                                          long copilotProjectPlanId) {
        Assert.assertEquals("Incorrect id.", copilotProjectPlanId, copilotProjectPlan.getId());
        Assert.assertEquals("Incorrect plannedDuration.", 10, copilotProjectPlan.getPlannedDuration());
        Assert.assertEquals("Incorrect plannedBugRaces.", 2, copilotProjectPlan.getPlannedBugRaces());
        Assert.assertEquals("Incorrect copilotProjectId.", copilotProject.getId(),
                copilotProjectPlan.getCopilotProjectId());
        Assert.assertEquals("Incorrect copilotProjectId.", 1, copilotProjectPlan.getPlannedContests().size());
    }

    /**
     * <p>Creates {@link CopilotProjectPlan} for demonstration purpose.</p>
     *
     * @param copilotProjectId copilotProjectId to set
     *
     * @return created {@link CopilotProjectPlan} instance
     */
    private CopilotProjectPlan createCopilotProjectPlan(long copilotProjectId) {
        CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
        copilotProjectPlan.setPlannedDuration(14);
        copilotProjectPlan.setPlannedBugRaces(2);
        copilotProjectPlan.setCopilotProjectId(copilotProjectId);
        copilotProjectPlan.setCreateUser("user");
        copilotProjectPlan.setModifyUser("user");
        copilotProjectPlan.setCreateDate(new Date());
        copilotProjectPlan.setModifyDate(new Date());
        return copilotProjectPlan;
    }

    /**
     * <p>Sets up database.</p>
     *
     * @param copilotProfile copilotProfile to save in database
     * @param copilotProject copilotProject to save in database
     *
     * @throws Exception if any error occurs
     */
    private void setUpDatabase(CopilotProfile copilotProfile, CopilotProject copilotProject) throws Exception {

        TestHelper.persistCopilotProjectDependencies(hibernateTemplate, copilotProfile, copilotProject);
        hibernateTemplate.save(copilotProject);

        CopilotProfileStatus copilotProfileStatus = TestHelper.createCopilotProfileStatus();
        hibernateTemplate.save(copilotProfileStatus);

        CopilotProjectStatus copilotProjectStatus = TestHelper.createCopilotProjectStatus();
        hibernateTemplate.save(copilotProjectStatus);

        CopilotType copilotType = TestHelper.createCopilotType();
        hibernateTemplate.save(copilotType);

        TestHelper.executeBatch(getSessionFactory(), "test_files/demo_insert.sql");
    }
}
