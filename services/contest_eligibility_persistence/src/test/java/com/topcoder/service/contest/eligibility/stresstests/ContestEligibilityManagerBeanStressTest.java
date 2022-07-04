package com.topcoder.service.contest.eligibility.stresstests;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.GroupContestEligibility;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityManagerBean;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
/**
 * The stress tests for this component.
 *
 * @author KLW
 * @version 1.0
 */
public class ContestEligibilityManagerBeanStressTest extends TestCase {
    /**
     * The persistence unit name for testing.
     */
    private static final String PERSISTENCE_UNIT_NAME = "ContestEligibilityPersistence";
    /**
     * The entity manager instance for testing.
     */
    private EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)
            .createEntityManager();
    /**
     * The manager bean to test.
     */
    private ContestEligibilityManagerBean contestEligibilityManager;

    /**
     * the contest id for test.
     */
    private long contestId = 100;

    /**
     * the run time count.
     */
    private int count = 5;

    /**
     * Sets up the test environment.
     * 
     * @throws Exception
     *             throw all exception if any error.
     */
    @Before
    public void setUp() throws Exception {
        contestEligibilityManager = new ContestEligibilityManagerBean();
        Field field = ContestEligibilityManagerBean.class.getDeclaredField("logger");
        field.setAccessible(true);
        Log logger  = LogManager.getLog("StressLogger");;
        field.set(contestEligibilityManager, logger);
        field = ContestEligibilityManagerBean.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(contestEligibilityManager, entityManager);
        clearDB(entityManager);
    }

    /**
     * Tears down the test environment.
     */
    @After
    public void tearDown() {
        contestEligibilityManager = null;
        clearDB(entityManager);
    }

    /**
     * Clear the database.
     * 
     * @param entityManager
     */
    private void clearDB(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("delete from group_contest_eligibility").executeUpdate();
        entityManager.createNativeQuery("delete from contest_eligibility").executeUpdate();
        entityManager.getTransaction().commit();
    }

    /**
     * The stress test for retrieve entities from database.
     * @throws Exception throw all exception if any error.
     */
    @Test
    public void testGetContestEligibility() throws Exception {
        for (int i = 0; i < count; i++) {
            // it should return empty list
            List<ContestEligibility> list = contestEligibilityManager.getContestEligibility(contestId, true);
            assertEquals("The list size is invalid.", 0, list.size());
            // add some entity to the database.
            entityManager.getTransaction().begin();
            // the first entity.
            GroupContestEligibility entity1 = new GroupContestEligibility();
            entity1.setContestId(contestId);
            entity1.setStudio(true);
            entity1.setGroupId(1);
            contestEligibilityManager.create(entity1);
            // the second entity.
            GroupContestEligibility entity2 = new GroupContestEligibility();
            entity2.setContestId(contestId);
            entity2.setStudio(true);
            entity2.setGroupId(2);
            contestEligibilityManager.create(entity2);
            // the third entity.
            GroupContestEligibility entity3 = new GroupContestEligibility();
            entity3.setContestId(contestId);
            entity3.setStudio(false);
            entity3.setGroupId(3);
            contestEligibilityManager.create(entity3);
            entityManager.getTransaction().commit();
            // after create this entities, we get it back.
            list = contestEligibilityManager.getContestEligibility(contestId, true);
            // entity1 and entity 2 should be retrieved.
            assertEquals("The list size is invalid.", 2, list.size());
            clearDB(entityManager);
        }
    }

    /**
     * The stress test for creating entities.
     * @throws Exception throw all exception if any error.
     */
    @Test
    public void testCreate() throws Exception{
        entityManager.getTransaction().begin();
        for (int i = 0; i < count; i++) {
            GroupContestEligibility entity1 = new GroupContestEligibility();
            entity1.setContestId(contestId);
            entity1.setStudio(true);
            entity1.setGroupId(1);
            contestEligibilityManager.create(entity1);
            GroupContestEligibility entity2 = new GroupContestEligibility();
            entity2.setContestId(contestId);
            entity2.setStudio(true);
            entity2.setGroupId(2);
            contestEligibilityManager.create(entity2);
            GroupContestEligibility entity3 = new GroupContestEligibility();
            entity3.setContestId(contestId);
            entity3.setStudio(false);
            entity3.setGroupId(3);
            contestEligibilityManager.create(entity3);
        }
        entityManager.getTransaction().commit();
        List<ContestEligibility> list = contestEligibilityManager.getContestEligibility(contestId, true);
        assertEquals("The list size is invalid.", count*2, list.size());
        list = contestEligibilityManager.getContestEligibility(contestId, false);
        assertEquals("The list size is invalid.", count, list.size());
    }

    /**
     * Stress test for remove entity.
     * @throws Exception throw all exception if any error.
     */
    @Test
    public void testRemove() throws Exception{
        entityManager.getTransaction().begin();
        for (int i = 0; i < count; i++) {
            GroupContestEligibility entity1 = new GroupContestEligibility();
            entity1.setContestId(contestId);
            entity1.setStudio(true);
            entity1.setGroupId(1);
            contestEligibilityManager.create(entity1);
            GroupContestEligibility entity2 = new GroupContestEligibility();
            entity2.setContestId(contestId);
            entity2.setStudio(true);
            entity2.setGroupId(2);
            contestEligibilityManager.create(entity2);
            GroupContestEligibility entity3 = new GroupContestEligibility();
            entity3.setContestId(contestId);
            entity3.setStudio(false);
            entity3.setGroupId(3);
            contestEligibilityManager.create(entity3);
        }
        entityManager.getTransaction().commit();
        List<ContestEligibility> list = contestEligibilityManager.getContestEligibility(contestId, true);
        entityManager.getTransaction().begin();
        for(ContestEligibility entity : list){
            contestEligibilityManager.remove(entity);
        }
        list = contestEligibilityManager.getContestEligibility(contestId, false);
        for(ContestEligibility entity : list){
            contestEligibilityManager.remove(entity);
        }
        entityManager.getTransaction().commit();
        list = contestEligibilityManager.getContestEligibility(contestId, true);
        assertEquals("The list size is invalid.", 0, list.size());
    }

    /**
     * The stress test for save the entities.
     * @throws Exception throw all exception if any error.
     */
    @Test
    public void testSave() throws Exception{
        for (int i = 0; i < count; i++) {
            entityManager.getTransaction().begin();
            GroupContestEligibility entity1 = new GroupContestEligibility();
            entity1.setContestId(contestId);
            entity1.setStudio(true);
            entity1.setGroupId(1);
            contestEligibilityManager.create(entity1);
            GroupContestEligibility entity2 = new GroupContestEligibility();
            entity2.setContestId(contestId);
            entity2.setStudio(true);
            entity2.setGroupId(2);
            contestEligibilityManager.create(entity2);
            GroupContestEligibility entity3 = new GroupContestEligibility();
            entity3.setContestId(contestId);
            entity3.setStudio(false);
            entity3.setGroupId(3);
            contestEligibilityManager.create(entity3);
            entityManager.getTransaction().commit();
            //start to save.
            entity1.setDeleted(true);
            entity2.setGroupId(3);
            entity3.setStudio(true);
            GroupContestEligibility entity4 = new GroupContestEligibility();
            entity4.setContestId(contestId);
            entity4.setStudio(true);
            entity4.setGroupId(4);
            List<ContestEligibility> list = new ArrayList<ContestEligibility>();
            list.add(entity1);
            list.add(entity2);
            list.add(entity3);
            list.add(entity4);
            entityManager.getTransaction().begin();
            list = contestEligibilityManager.save(list);
            entityManager.getTransaction().commit();
            //for the result list: entity4 is added, entity1 is deleted, entity2 ant entity3 is updated.
            assertEquals("The list should should be 3.",3, list.size());
        }
    }
}
