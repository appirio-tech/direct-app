/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompClient;
import com.topcoder.catalog.entity.CompUser;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.entity.TestHelper;
import static com.topcoder.catalog.entity.TestHelper.getEntityManager;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>This is a base class for ComponentEntity JPA tests.</p>
 * <p>It provide basic operations for concrete implementation
 * {@link ComponentEntityTest}.</p>
 * <p>It creates an entity instance with default fields values (including foreign entities).</p>
 * <p>Stores all necessary foreign entities to the database to prepare environment for Component
 * entity testing.</p>
 * <p>After all test's cases are performed the method <code>tearDown</code> cleans up database
 * and provides tests isolation.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public abstract class BaseComponentEntityTest extends CommonEntityTest<Component> {
    /**
     * Default Component.description value.
     */
    public static final String DESCRIPTION = "9";
    /**
     * Default Component.functionalDesc value.
     */
    public static final String FUNCTIONALDESC = "10";
    /**
     * Default Component.name value.
     */
    public static final String NAME = "11";
    /**
     * Default Component.shortDesc value.
     */
    public static final String SHORTDESC = "12";
    /**
     * Default Component.status value.
     */
    public static final Status STATUS = Status.APPROVED;


    /**
     * <p>Constructor. No additional actions to the super constructor.</p>
     */
    protected BaseComponentEntityTest() {
    }

    /**
     * <p>Create a Component entity instance with default constant values defined above.</p>
     *
     * @return a Component entity instance
     */
    static Component createComponent() {
        final Component component = new Component();
        component.setDescription(DESCRIPTION);
        component.setFunctionalDesc(FUNCTIONALDESC);
        component.setName(NAME);
        component.setShortDesc(SHORTDESC);
        component.setStatus(STATUS);
        final CompVersion version = BaseCompVersionEntityTest.createCompVersion(component);
        component.setCurrentVersion(version);
        component.setRootCategory(getEntityManager().find(Category.class, 2L));

        component.setCategories(Arrays.asList(
            component.getRootCategory(),
            component.getRootCategory().getParentCategory()));

        final Set<CompClient> clients = populateClients(component);
        component.setClients(clients);

        final Set<CompUser> users = populateUsers(component);
        component.setUsers(users);

        //this raise some problem, it will update the version to 0.
        //component.setVersions(Arrays.asList(version));

        return component;
    }

    /**
     * <p>Persists Component foreign entities to store. Is called before saving Component itself.</p>
     *
     * @param component           entity instance
     * @param separateTransaction <ul><li><code>true</code> - persist objects in a separate transaction</li>
     *                            <li><code>false</code> - already in a transaction context</li></ul>
     */
    protected static void persistComponentForeignObjects(
        final Component component, boolean separateTransaction) {
        if (separateTransaction) { // within its own transaction
            final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
            try {
                entityTransaction.begin();
                persistForeignObjects(component);
                entityTransaction.commit();
            } catch (PersistenceException e) {
                if (entityTransaction.isActive()) {
                    entityTransaction.rollback();
                }
                throw e; // re-throw the exception
            } finally {
                getEntityManager().clear(); // clears persistence context (caches etc.)
            }
        } else {
            persistForeignObjects(component); // or relying on current context
        }
    }

    /**
     * <p>Creates a default entity.</p>
     *
     * @return instance with test parameters
     */
    protected final Component createEntity() {
        return createComponent();
    }

    /**
     * <p>Stores the {@link #entity} to database.</p>
     *
     * @param saveForeignObjects indicates whether save the entity only, or all foreign entities as well
     *                           <code>true</code> - save all foreign entities
     *                           <code>false</code> - save only the entity itself
     */
    protected void persistEntity(boolean saveForeignObjects) {
        final Component component = getEntity();

        final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
        try {
            entityTransaction.begin();

            if (saveForeignObjects) {
                persistComponentForeignObjects(component, false);
            }

            persistEntity(component);

            entityTransaction.commit();
        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        } finally {
            getEntityManager().clear();
        } // clears persistence context (caches et*c.)
    }

    /**
     * <p>Persists Component foreign entities to store. Is called before saving Component itself.</p>
     *
     * @param component entity instance
     */
    private static void persistForeignObjects(
        final Component component) {
        if (component.getCurrentVersion() != null) {
            BaseCompVersionEntityTest
                .persistCompVersionForeignObjects(component.getCurrentVersion(), false);
            persistEntity(component.getCurrentVersion());
        }
    }

    /**
     * <p>Creates a set of users (using predefined ones by test_data.sql).</p>
     *
     * @param component a component to populate users
     * @return set of users
     */
    private static Set<CompUser> populateUsers(Component component) {
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
    private static Set<CompClient> populateClients(Component component) {
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
}
