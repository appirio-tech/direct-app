/*
 * Copyright (C) 2007-2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import static com.topcoder.catalog.entity.TestHelper.getEntityManager;
import com.topcoder.catalog.entity.unittests.persistence.BaseCompVersionDatesEntityTest;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Tests the Demo provided in the CS 4.3.</p>
 *
 * @author caru, Retunsky, KingStone
 * @version 1.1
 */
public class DemoTest extends TestCase {
    /**
     * <p>The <p>EntityManager</p> instance for testing.</p>
     */
    private EntityManager entityManager;
    /**
     * <p>Transaction context for testing.</p>
     */
    private EntityTransaction entityTransaction;

    /**
     * <p>Sets up testing environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.bindIdGenerator();
        TestHelper.clearTables(); // clear data before a test
        entityManager = TestHelper.getEntityManager();
        entityTransaction = TestHelper.getEntityTransaction();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.close(); // close entity manager and transaction
        TestHelper.clearTables(); // clear data
        TestHelper.unbindIdGenerator();
        entityManager = null;
        entityTransaction = null;
        super.tearDown();
    }

    /**
     * <p>Tests "Persisting entities", "Finding entities" and "Remove entities" paragraphs.</p>
     *
     * @throws Exception to jUnit
     */
    public void testEntities() throws Exception {
        entityTransaction.begin();
        Phase phase = new Phase();
        phase.setDescription("screening");
        entityManager.persist(phase);
        entityTransaction.commit();
        // now check the demo works:
        entityManager.clear(); // clear caches
        // find entity
        final Long phaseId = phase.getId();
        Phase found = entityManager.find(Phase.class, phaseId);
        // check the object was found
        assertNotNull("There should be object in the database", found);
        assertEquals("Invalid description", phase.getDescription(), found.getDescription());
        // now delete the object
        entityTransaction.begin();
        entityManager.remove(entityManager.getReference(Phase.class, phaseId));
        entityTransaction.commit();
        // and check it has been deleted
        entityManager.clear();
        found = entityManager.find(Phase.class, phaseId);
        // check the object was found
        assertNull("There should be no object in the database", found);
    }

    /**
     * <p>Tests "Create queries" and "Obtaining named queries" paragraphs.</p>
     *
     * @throws Exception to jUnit
     */
    public void testQueries() throws Exception {
        entityTransaction.begin();
        Phase phase1 = new Phase();
        phase1.setDescription("phase1");
        Phase phase2 = new Phase();
        phase2.setDescription("phase2");
        entityManager.persist(phase1);
        entityManager.persist(phase2);
        entityTransaction.commit();
        final Long phase1Id = phase1.getId();
        // now check the demo works:
        entityManager.clear(); // clear caches
        // find entity

        Query query = entityManager.createQuery("select p from Phase p where p.id=:id");
        query.setParameter("id", phase1Id);
        Phase found = (Phase) query.getSingleResult();
        // check the object was found
        assertEquals("Invalid description", phase1.getDescription(), found.getDescription());

        query = entityManager.createNamedQuery("getAllPhases");

        @SuppressWarnings("unchecked")
        final List<Phase> phases = query.getResultList();
        // check the result
        assertEquals("There should be all created phases", 2, phases.size());
    }

    /**
     * <p>Tests "Create components and component versions" and "Removing components" paragraphs.</p>
     *
     * @throws Exception to jUnit
     */
    public void testComponents() throws Exception {
        TestHelper.prepareTables(); // insert test data
        // create component
        final Component component = new Component();
        component.setDescription("Component description");
        component.setFunctionalDesc("Functional description");
        component.setName("Component name");
        component.setShortDesc("Short Desc.");
        component.setStatus(Status.NEW_POST);
        // create version
        final CompVersion version = createComponentVersion();
        version.setComponent(component);

        // set the current version
        component.setCurrentVersion(version);

        // set the root category (categories are in the database
        component.setRootCategory(getEntityManager().find(Category.class, 2L));

        // assign categories which this component belongs to
        component.setCategories(Arrays.asList(
            getEntityManager().find(Category.class, 2L),
            getEntityManager().find(Category.class, 3L)));

        // assign component clients
        final Set<CompClient> clients = populateClients(component);
        component.setClients(clients);

        // assign component users
        final Set<CompUser> users = populateUsers(component);
        component.setUsers(users);

        // start the transaction
        entityTransaction.begin();
        // persist the version first
        entityManager.persist(version);
        // persist the component, this will update version as well to bind it to the component
        entityManager.persist(component);
        // commit the transaction
        entityTransaction.commit();
        // now check is has been saved
        entityManager.clear();
        final Component checkEntity = entityManager.find(Component.class, component.getId());
        assertNotNull("Component is not stored", checkEntity);
        assertFalse("Component should have categories", checkEntity.getCategories().isEmpty());
        assertNotNull("Component should have root category", checkEntity.getRootCategory());
        assertFalse("Component should have versions", checkEntity.getVersions().isEmpty());
        assertNotNull("Component should have current version", checkEntity.getCurrentVersion());
        assertFalse("Component should have clients", checkEntity.getClients().isEmpty());
        assertFalse("Component should have users", checkEntity.getUsers().isEmpty());
        removeComponent(component);
    }

    /**
     * <p>Removes component from the database (according to Demo in CS 4.3).</p>
     *
     * @param component the component to remove
     */
    private void removeComponent(Component component) {
        final CompVersion version = component.getCurrentVersion();
        // and remove
        entityTransaction.begin(); // start transaction
        version.setComponent(null); // clear reference to component
        component.setVersions(null); // remove to versions associations
        component.setUsers(null); // remove to users associations
        component.setCategories(null); // remove to categories associations
        component.setClients(null); // remove to clients associations
        entityManager.merge(version); // update version without
        entityManager.remove(entityManager.getReference(Component.class, component.getId())); // remove component
        entityManager.remove(entityManager.getReference(CompVersion.class, version.getId())); // remove version
        entityTransaction.commit(); // commit finally
        // check the entity is removed
        assertNull(
            "There should be no entities after removing",
            entityManager.find(Component.class, component.getId()));
    }

    /**
     * <p>Creates component version for test.</p>
     *
     * @return a component version
     * @throws Exception to jUnit
     */
    private CompVersion createComponentVersion() throws Exception {
        final CompVersion version = new CompVersion();
        version.setComments("Version comments");
        version.setPhasePrice(500);
        version.setPhaseTime(parseDate("2007/12/21"));
        version.setSuspended(false);
        version.setVersion(1L);
        version.setVersionText("1.0");
        // create forum
        final CompForum compForum = new CompForum();
        compForum.setCompVersion(version);
        version.setForum(compForum); // assign to the version
        // create link
        final CompLink compLink = new CompLink();
        compLink.setLink("some svnlink");
        compLink.setCompVersion(version);
        version.setLink(compLink); // assign to the version
        // create documentation
        // note: newly added in version 1.1
        final CompDocumentation compDocumentation = new CompDocumentation();
        compDocumentation.setDocumentName("my doc");
        compDocumentation.setDocumentTypeId(300L);
        compDocumentation.setUrl("software.topcoder.com");
        compDocumentation.setCompVersion(version);
        List<CompDocumentation> documentation = new ArrayList<CompDocumentation>();
        documentation.add(compDocumentation);
        version.setDocumentation(documentation); // assign to the version

        // assign phase, which is already in the database
        version.setPhase(getEntityManager().find(Phase.class, 1L));

        // populate version phase dates
        final Map<Long, CompVersionDates> dates = populateVersionDates(version);
        version.setVersionDates(dates);
        return version;
    }

    /**
     * <p>Populates <code>versionDates</code>.</p>
     *
     * @param compVersion parent compVersion
     * @return map of phase-to-compVersionDate
     */
    private Map<Long, CompVersionDates> populateVersionDates(CompVersion compVersion) {
        final Map<Long, CompVersionDates> dates = new HashMap<Long, CompVersionDates>();
        CompVersionDates compVersionDates;

        compVersionDates = BaseCompVersionDatesEntityTest.createCompVersionDates();
        compVersionDates.setCompVersion(compVersion);
        compVersionDates.setPhase(getEntityManager().find(Phase.class, 1L));
        dates.put(1L, compVersionDates);

        compVersionDates = BaseCompVersionDatesEntityTest.createCompVersionDates();
        compVersionDates.setCompVersion(compVersion);
        compVersionDates.setPhase(getEntityManager().find(Phase.class, 2L));
        dates.put(2L, compVersionDates);

        return dates;
    }

    /**
     * <p>Creates a set of users (using predefined ones by test_data.sql).</p>
     *
     * @param component a component to populate users
     * @return set of users
     */
    private Set<CompUser> populateUsers(Component component) {
        final Set<CompUser> users = new HashSet<CompUser>();

        final CompUser user1 = new CompUser();
        user1.setComponent(component);
        user1.setUserId(1L);
        users.add(user1);

        final CompUser user2 = new CompUser();
        user2.setComponent(component);
        user2.setUserId(2L);
        users.add(user2);
        return users;
    }

    /**
     * <p>Creates a set of clients (using predefined ones by test_data.sql).</p>
     *
     * @param component a component to populate clients
     * @return set of clients
     */
    private Set<CompClient> populateClients(Component component) {
        final Set<CompClient> clients = new HashSet<CompClient>();

        final CompClient client1 = new CompClient();
        client1.setClientId(1L);
        client1.setComponent(component);

        final CompClient client2 = new CompClient();
        client2.setClientId(2L);
        client2.setComponent(component);

        final CompClient client3 = new CompClient();
        client3.setClientId(3L);
        client3.setComponent(component);

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        return clients;
    }

    /**
     * <p>Parses a date by its date string representation.</p>
     *
     * @param dateAsString string representation of a date (in 'yyyy/MM/dd' format)
     * @return Date object parsed by SimpleDateFormat
     * @throws Exception to jUnit
     */
    private Date parseDate(String dateAsString) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        return df.parse(dateAsString);
    }

}
