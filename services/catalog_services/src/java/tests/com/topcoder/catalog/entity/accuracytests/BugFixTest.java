/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * <p>
 * Test cases for the bug fix of <code>CompVersion</code>.
 * </p>
 * 
 * @author icyriver
 * @version 1.1
 */
public class BugFixTest extends BaseTest {
    /**
     * <p>
     * Test suite of <code>BugFixTest</code>.
     * </p>
     *
     * @return Test suite of <code>BugFixTest</code>.
     */
    public static Test suite() {
        return new TestSuite(BugFixTest.class);
    }

    /**
     * Test the <code>setDependencies</code> and <code>getDependencies</code>
     * methods.
     *
     * @throws Exception to junit.
     */
    public void testCompVersion_GetSetDependencies() throws Exception {
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

        manager.persist(version);

        transaction.commit();

        CompVersion v1 = getCompVersions(1);
        CompVersion v2 = getCompVersions(2);
        List list = new ArrayList();
        list.add(v1);
        list.add(v2);

        version.setDependencies(list);

        CompVersion find = manager.find(CompVersion.class, version.getId());

        assertEquals("Equal to 2.", 2, find.getDependencies().size());
    }

    /**
     * Helper method used to get a instance of CompVersion.
     *
     * @return an instance of CompVersion.
     * @param num the number used to create the CompVersion.
     * @throws Exception to junit
     */
    private static CompVersion getCompVersions(long num)
        throws Exception {
        CompVersion version = new CompVersion();
        version.setComments("version1.0");
        version.setVersionText("text");
        version.setVersion(new Long(num + 3));

        Phase phase = new Phase();
        phase.setId(new Long(num));
        phase.setDescription("");

        version.setPhase(phase);
        version.setPhaseTime(new Date());
        version.setPhasePrice(111.0);

        return version;
    }
}
