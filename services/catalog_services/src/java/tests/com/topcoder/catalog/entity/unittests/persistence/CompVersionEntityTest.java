/*
 * Copyright (C) 2007-2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.TestHelper;

/**
 * <p>This test checks all attributes of CompVersion entity correspond database's columns.</p>
 * <p>Before each test the {@link #setUp} method is called. This method saves all necessary foreign
 * entities to database and the entity itself.</p>
 * <p>If the entity saved successfully, it indicates that all constraints are satisfied.</p>
 * <p>After that each method tries to find the entity by a field value pointed in where clause.</p>
 * <p>If the list is empty, then the test fails. It guarantees that each entity attribute mapped to
 * a separate database column (no values in all entities tests are duplicated).</p>
 *
 * @author Retunsky, KingStone
 * @version 1.1
 */
public class CompVersionEntityTest extends BaseCompVersionEntityTest {

    /**
     * <p>Persists entity instance to the database before each test case.</p>
     *
     * @throws Exception to jUnit
     */
    protected void setUp() throws Exception {
        super.setUp(); // always call super method
        persistEntity(true); // pointed true to indicate that all foreign entities are to be stored as well
    }

    /**
     * <p>Test removing.</p>
     */
    public void testRemove() {
        final EntityManager em = TestHelper.getEntityManager();
        final EntityTransaction tx = TestHelper.getEntityTransaction();
        final CompVersion entity = getEntity();

        tx.begin();
        em.remove(em.getReference(CompVersion.class, entity.getId()));
        tx.commit();
        final Set<CompVersion> items = getEntities("select e from CompVersion e");
        assertTrue(
            "There should be no entities after removing", items.isEmpty());
    }

    /**
     * <p>Test <code>comments</code> attribute stored properly.</p>
     */
    public void testComments() {
        final Set<CompVersion> items = getEntities("select e from CompVersion e where comments=?", COMMENTS);
        assertFalse(
            "Not found entities by the following query: select e from CompVersion e where comments='"
                + COMMENTS + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>component</code> relation.</p>
     */
    public void testComponentRelation() {
        final Set<CompVersion> items =
            getEntities("select e from CompVersion e join e.component f where f.description=?",
                BaseComponentEntityTest.DESCRIPTION);
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompVersion e join e.component f where f.description='"
                + BaseComponentEntityTest.DESCRIPTION + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>forum</code> relation.</p>
     * <p>Tries to find a default instance which saved in {@link #setUp} method and checks <code>forum</code> field.</p>
     */
    public void testCompForumRelation() {
        final CompVersion compVersion = TestHelper.getEntityManager().find(CompVersion.class, getEntity().getId());
        assertNotNull("There should be forum", compVersion.getForum());
    }

    /**
     * <p>Test <code>forum</code> relation.</p>
     * <p>Tries to find a default instance which saved in {@link #setUp} method and checks <code>link</code> field.</p>
     */
    public void testCompLinkRelation() {
        final CompVersion compVersion = TestHelper.getEntityManager().find(CompVersion.class, getEntity().getId());
        assertNotNull("There should be link", compVersion.getLink());
    }

    /**
     * <p>Test <code>forum</code> join.</p>
     */
    public void testCompForumJoin() {
        final Set<CompVersion> items = getEntities("select e from CompVersion e where e.forum.jiveCategoryId=?",
            getEntity().getForum().getJiveCategoryId());
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompVersion e join e.forum f where f.jiveCategoryId='"
                + getEntity().getForum().getJiveCategoryId() + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>link</code> join.</p>
     */
    public void testCompLinkJoin() {
        final Set<CompVersion> items = getEntities("select e from CompVersion e join e.link f where f.link=?",
            BaseCompLinkEntityTest.LINK);
        assertFalse(
            "Not found entities by the following query: select e from CompVersion e join e.link f where f.link='"
                + BaseCompLinkEntityTest.LINK + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>phase</code> relation.</p>
     */
    public void testPhaseRelation() {
        final Set<CompVersion> items = getEntities("select e from CompVersion e join e.phase f where f.description=?",
            getEntity().getPhase().getDescription());
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompVersion e join e.phase f where f.description='"
                + getEntity().getPhase().getDescription() + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>technologies</code> relation.</p>
     */
    public void testTechnologiesRelation() {
        final CompVersion compVersion = TestHelper.getEntityManager().find(CompVersion.class, getEntity().getId());
        assertEquals("Wrong number of technologies", 1, compVersion.getTechnologies().size());
    }

    /**
     * <p>Test <code>compVersionDate</code> relation.</p>
     */
    public void testVersionRelation() {
        final CompVersion version =
            (CompVersion) TestHelper.getEntityManager().createQuery("select e from CompVersion e").getSingleResult();
        assertNotNull(
            "There should be version dates", version.getVersionDates());
        assertEquals(
            "There should be two version dates", 2, version.getVersionDates().size());
    }

    /**
     * <p>Test <code>phasePrice</code> attribute stored properly.</p>
     */
    public void testPhasePrice() {
        final Set<CompVersion> items = getEntities("select e from CompVersion e where phasePrice=?", PHASEPRICE);
        assertFalse(
            "Not found entities by the following query: select e from CompVersion e where phasePrice='"
                + PHASEPRICE + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>phaseTime</code> attribute stored properly.</p>
     */
    public void testPhaseTime() {
        final Set<CompVersion> items = getEntities("select e from CompVersion e where phaseTime=?", PHASETIME);
        assertFalse(
            "Not found entities by the following query: select e from CompVersion e where phaseTime='"
                + PHASETIME + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>suspended</code> attribute stored properly.</p>
     */
    public void testSuspended() {
        final Set<CompVersion> items = getEntities("select e from CompVersion e where suspended=?", SUSPENDED);
        assertFalse(
            "Not found entities by the following query: select e from CompVersion e where suspended='"
                + SUSPENDED + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>version</code> attribute stored properly.</p>
     */
    public void testVersion() {
        final Set<CompVersion> items = getEntities("select e from CompVersion e where version=?", VERSION);
        assertFalse(
            "Not found entities by the following query: select e from CompVersion e where version='"
                + VERSION + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>versionText</code> attribute stored properly.</p>
     */
    public void testVersionText() {
        final Set<CompVersion> items = getEntities("select e from CompVersion e where versionText=?", VERSIONTEXT);
        assertFalse(
            "Not found entities by the following query: select e from CompVersion e where versionText='"
                + VERSIONTEXT + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>documentation</code> should be retrieved correctly.</p>
     * @since 1.1
     */
    public void testDocumentation() {
        // create documentation in database
        CompDocumentation compDocumentation = BaseCompDocumentationEntityTest.createCompDocumentation();
        //  persistence documentation
        final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
        try {
            entityTransaction.begin();
            BaseCompDocumentationEntityTest.persistCompDocumentationForeignObjects(compDocumentation, false);
            persistEntity(compDocumentation);
            entityTransaction.commit();
        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        } finally {
            TestHelper.getEntityManager().clear();
        }

        // verify that documentation should be retrieved correctly
        final Set<CompVersion> items = getEntities("select e from CompVersion e where id=?",
                compDocumentation.getCompVersion().getId());
        assertEquals("documentation should be retrieved correctly.", 1, items.iterator().next()
                .getDocumentation().size());
        CompDocumentation document = items.iterator().next().getDocumentation().get(0);
        assertEquals("document stored is invalid.", BaseCompDocumentationEntityTest.DOCUMENT_NAME,
                document.getDocumentName());
    }
}
