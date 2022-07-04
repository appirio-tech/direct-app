/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.accuracytests;

import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.CompLink;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.CompVersionDates;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.entity.Technology;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Accuracy test cases for entity <code>CompVersion </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestCompVersionAccuracy extends BaseTest {
    /**
     * Test if the entity can be created.
     *
     * @throws Exception to junit
     */
    public void testCreate() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        CompVersion version = new CompVersion();
        version.setComments("version1.0");
        version.setVersionText("text");
        version.setVersion(new Long(3));

        Phase phase = new Phase();
        phase.setId(new Long(0));
        phase.setDescription("");

        version.setPhase(phase);
        version.setPhaseTime(new Date());
        version.setPhasePrice(111.0);

        manager.persist(version);

        transaction.commit();

        CompVersion ret = manager.find(CompVersion.class, version.getId());
        assertNotNull("the CompVersion entity should be created.", ret);
        assertEquals("Equal is expected.", 3, ret.getVersion().longValue());
    }

    /**
     * Test if the entity can be updated.
     *
     * @throws Exception to junit.
     */
    public void testUpdate_1() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        CompVersion version = new CompVersion();
        version.setComments("version1.0");
        version.setVersionText("text");
        version.setVersion(new Long(3));

        Phase phase = new Phase();
        phase.setId(new Long(0));
        phase.setDescription("");

        version.setPhase(phase);
        version.setPhaseTime(new Date());
        version.setPhasePrice(111.0);

        manager.persist(version);

        transaction.commit();

        CompVersion ret = manager.find(CompVersion.class, version.getId());
        assertNotNull("the CompVersion entity should be created.", ret);
        assertEquals("Equal is expected.", 3, ret.getVersion().longValue());

        version.setVersion(new Long(4));
        version.setComments("c");
        version.setSuspended(false);

        ret = manager.find(CompVersion.class, version.getId());
        assertNotNull("the CompVersion entity should be created.", ret);
        assertEquals("Equal is expected.", 4, ret.getVersion().longValue());
        assertEquals("Equal to c", "c", ret.getComments());

        assertFalse("False is expected.", ret.isSuspended());
        assertNull("Null is expected.", ret.getForum());
        assertNull("Null is expected.", ret.getComponent());
        assertNull("Null is expected.", ret.getLink());

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        transaction.commit();
    }

    /**
     * Test if the entity can be updated.
     * 
     * <p>
     * The CompLink will be udpated.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testUpdate_2() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        CompVersion version = new CompVersion();
        version.setComments("version1.0");
        version.setVersionText("text");
        version.setVersion(new Long(3));

        Phase phase = new Phase();
        phase.setId(new Long(0));
        phase.setDescription("");

        version.setPhase(phase);
        version.setPhaseTime(new Date());
        version.setPhasePrice(111.0);

        manager.persist(version);

        transaction.commit();

        CompVersion ret = manager.find(CompVersion.class, version.getId());
        assertNotNull("the CompVersion entity should be created.", ret);
        assertEquals("Equal is expected.", 3, ret.getVersion().longValue());

        CompLink link = new CompLink();
        link.setCompVersion(version);
        link.setLink("www.topcoder.com");

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        /*
         * Persist the CompLink into the database.
         */
        manager.persist(link);

        transaction.commit();

        version.setLink(link);

        ret = manager.find(CompVersion.class, version.getId());

        assertEquals("Equal is expected.", "www.topcoder.com", ret.getLink().getLink());

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        transaction.commit();
    }

    /**
     * Test if the entity can be updated.
     * 
     * <p>
     * The CompForum will be udpated.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testUpdate_3() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        CompVersion version = new CompVersion();
        version.setComments("version1.0");
        version.setVersionText("text");
        version.setVersion(new Long(3));

        Phase phase = new Phase();
        phase.setId(new Long(0));
        phase.setDescription("");

        version.setPhase(phase);
        version.setPhaseTime(new Date());
        version.setPhasePrice(111.0);

        manager.persist(version);

        transaction.commit();

        CompVersion ret = manager.find(CompVersion.class, version.getId());
        assertNotNull("the CompVersion entity should be created.", ret);
        assertEquals("Equal is expected.", 3, ret.getVersion().longValue());

        CompForum forum = new CompForum();
        forum.setCompVersion(version);

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        /*
         * Persist the CompForum into the database.
         */
        manager.persist(forum);

        transaction.commit();

        version.setForum(forum);

        ret = manager.find(CompVersion.class, version.getId());

        assertNotNull("The comp forum should be set.", version.getForum());

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        transaction.commit();
    }

    /**
     * Test if the entity can be updated.
     * 
     * <p>
     * The Technology should be updated.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testUpdate_4() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        CompVersion version = new CompVersion();
        version.setComments("version1.0");
        version.setVersionText("text");
        version.setVersion(new Long(3));

        Phase phase = new Phase();
        phase.setId(new Long(0));
        phase.setDescription("");

        version.setPhase(phase);
        version.setPhaseTime(new Date());
        version.setPhasePrice(111.0);

        manager.persist(version);

        transaction.commit();

        CompVersion ret = manager.find(CompVersion.class, version.getId());
        assertNotNull("the CompVersion entity should be created.", ret);
        assertEquals("Equal is expected.", 3, ret.getVersion().longValue());

        Technology t = new Technology();
        t.setDescription("tech");
        t.setName("name1");
        t.setStatus(Status.ACTIVE);

        Technology t2 = new Technology();
        t2.setDescription("tech2");
        t2.setName("name2");
        t2.setStatus(Status.ACTIVE);

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        /*
         * Persist the Technology into the database.
         */
        manager.persist(t);
        manager.persist(t2);

        transaction.commit();

        List list = new ArrayList();
        list.add(t);
        list.add(t2);

        version.setTechnologies(list);

        ret = manager.find(CompVersion.class, version.getId());

        assertEquals("Equal to 2.", 2, ret.getTechnologies().size());

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        transaction.commit();
    }

    /**
     * Test if the entity can be updated.
     * 
     * <p>
     * The Component will be udpated.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testUpdate_5() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        CompVersion version = new CompVersion();
        version.setComments("version1.0");
        version.setVersionText("text");
        version.setVersion(new Long(3));

        Phase phase = new Phase();
        phase.setId(new Long(0));
        phase.setDescription("");

        version.setPhase(phase);
        version.setPhaseTime(new Date());
        version.setPhasePrice(111.0);

        manager.persist(version);

        transaction.commit();

        CompVersion ret = manager.find(CompVersion.class, version.getId());
        assertNotNull("the CompVersion entity should be created.", ret);
        assertEquals("Equal is expected.", 3, ret.getVersion().longValue());

        Component c = new Component();
        c.setDescription("c");
        c.setStatus(Status.APPROVED);
        c.setFunctionalDesc("f");

        c.setCurrentVersion(version);
        c.setName("name");

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        /*
         * Persist the Component into the database.
         */
        manager.persist(c);

        transaction.commit();

        version.setComponent(c);

        ret = manager.find(CompVersion.class, version.getId());

        assertNotNull("The component should be set.", version.getComponent());

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        transaction.commit();
    }

    /**
     * Test if the entity can be updated.
     * 
     * <p>
     * The VersionDates will be udpated.
     * </p>
     *
     * @throws Exception to junit.
     */
    public void testUpdate_6() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        CompVersion version = new CompVersion();
        version.setComments("version1.0");
        version.setVersionText("text");
        version.setVersion(new Long(3));

        Phase phase = new Phase();
        phase.setId(new Long(0));
        phase.setDescription("");

        version.setPhase(phase);
        version.setPhaseTime(new Date());
        version.setPhasePrice(111.0);

        manager.persist(version);

        transaction.commit();

        CompVersion ret = manager.find(CompVersion.class, version.getId());
        assertNotNull("the CompVersion entity should be created.", ret);
        assertEquals("Equal is expected.", 3, ret.getVersion().longValue());

        // create CompVersionDates.
        CompVersionDates dates = new CompVersionDates();
        dates.setAggregationCompleteDate(new Date());
        dates.setAggregationCompleteDateComment("aggregationComplate");
        dates.setCompVersion(version);

        dates.setEstimatedDevDate(new Date());
        dates.setFinalSubmissionDate(new Date());
        dates.setFinalSubmissionDateComment("final");
        dates.setInitialSubmissionDate(new Date());
        dates.setInitialSubmissionDateComment("initSubmission");
        dates.setLevelId(new Long(1));
        dates.setPhase(phase);
        dates.setPhaseCompleteDate(new Date());
        dates.setPostingDate(new Date());
        dates.setPhaseCompleteDateComment("phaseCompleted");

        dates.setWinnerAnnouncedDateComment("winner");
        dates.setWinnerAnnouncedDate(new Date());
        dates.setTotalSubmissions(10);
        dates.setStatus(Status.ACTIVE);
        dates.setScreeningCompleteDateComment("screen");
        dates.setScreeningCompleteDate(new Date());
        dates.setReviewCompleteDate(new Date());
        dates.setReviewCompleteDateComment("comment");

        dates.setPrice(11.2);

        Map map = new HashMap();
        map.put(dates.getPhase().getId(), dates);

        if (transaction.isActive() == false) {
            transaction.begin();
        }

        /*
         * Persist the CompVersionDate into the database.
         */
        manager.merge(version);

        transaction.commit();

        version.setVersionDates(map);

        ret = manager.find(CompVersion.class, version.getId());

        assertEquals("Equal to 1.", 1, version.getVersionDates().size());
    }
}
