/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import static com.topcoder.catalog.entity.TestHelper.getEntityManager;
import static com.topcoder.catalog.entity.TestHelper.getEntityTransaction;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.CompLink;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.CompVersionDates;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Technology;

/**
 * <p>This is a base class for CompVersionEntity JPA tests.</p>
 * <p>It provide basic operations for concrete implementation
 * {@link CompVersionEntityTest}.</p>
 * <p>It creates an entity instance with default fields values (including foreign entities).</p>
 * <p>Stores all necessary foreign entities to the database to prepare environment for CompVersion
 * entity testing.</p>
 * <p>After all test's cases are performed the method <code>tearDown</code> cleans up database
 * and provides tests isolation.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public abstract class BaseCompVersionEntityTest extends CommonEntityTest<CompVersion> {
    /**
     * Default CompVersion.comments value.
     */
    public static final String COMMENTS = "16";
    /**
     * Default CompVersion.phasePrice value.
     */
    public static final double PHASEPRICE = 17.5;
    /**
     * Default CompVersion.phaseTime value.
     */
    public static final Date PHASETIME = new Date(1169154000000L) /*2007-01-19*/;
    /**
     * Default CompVersion.suspended value.
     */
    public static final boolean SUSPENDED = true;
    /**
     * Default CompVersion.version value.
     */
    public static final Long VERSION = 56L;
    /**
     * Default CompVersion.versionText value.
     */
    public static final String VERSIONTEXT = "7.2.0026";


    /**
     * <p>Constructor. No additional actions to the super constructor.</p>
     */
    protected BaseCompVersionEntityTest() {
    }

    /**
     * <p>Create a CompVersion entity instance with default constant values defined above.</p>
     *
     * @return a CompVersion entity instance
     */
    static CompVersion createCompVersion() {
        final Component component = new Component();
        component.setDescription(BaseComponentEntityTest.DESCRIPTION);
        component.setFunctionalDesc(BaseComponentEntityTest.FUNCTIONALDESC);
        component.setName(BaseComponentEntityTest.NAME);
        component.setShortDesc(BaseComponentEntityTest.SHORTDESC);
        component.setStatus(BaseComponentEntityTest.STATUS);
        component.setRootCategory(getEntityManager().find(Category.class, 1L));
        return createCompVersion(component);
    }

    /**
     * <p>Create a CompVersion entity instance with default constant values defined above.</p>
     *
     * @param component the component for which this version is actual
     * @return a CompVersion entity instance
     */
    static CompVersion createCompVersion(Component component) {
        final CompVersion compVersion = new CompVersion();
        compVersion.setComments(COMMENTS);
        compVersion.setPhasePrice(PHASEPRICE);
        compVersion.setPhaseTime(PHASETIME);
        compVersion.setSuspended(SUSPENDED);
        compVersion.setVersion(VERSION);
        compVersion.setVersionText(VERSIONTEXT);
        final CompForum compForum = BaseCompForumEntityTest.createCompForum();
        compForum.setCompVersion(compVersion);
        compVersion.setForum(compForum);
        final CompLink compLink = BaseCompLinkEntityTest.createCompLink();
        compLink.setCompVersion(compVersion);
        compVersion.setLink(compLink);
        compVersion.setPhase(getEntityManager().find(Phase.class, 1L));
        final Map<Long, CompVersionDates> dates = populateVersionDates(compVersion);
        compVersion.setVersionDates(dates);
        component.setCurrentVersion(compVersion);
        compVersion.setTechnologies(Arrays.asList(BaseTechnologyEntityTest.createTechnology()));

        compVersion.setComponent(component);

        return compVersion;
    }

    /**
     * <p>Populates <code>versionDates</code>.</p>
     *
     * @param compVersion parent compVersion
     * @return map of phase-to-compVersionDate
     */
    static Map<Long, CompVersionDates> populateVersionDates(CompVersion compVersion) {
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
     * <p>Persists CompVersion foreign entities to store. Is called before saving CompVersion itself.</p>
     *
     * @param compVersion         entity instance
     * @param separateTransaction <ul><li><code>true</code> - persist objects in a separate transaction</li>
     *                            <li><code>false</code> - already in a transaction context</li></ul>
     */
    protected static void persistCompVersionForeignObjects(
        final CompVersion compVersion, boolean separateTransaction) {
        if (separateTransaction) { // within its own transaction
            final EntityTransaction entityTransaction = getEntityTransaction();
            try {
                entityTransaction.begin();
                persistForeignObjects(compVersion);
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
            persistForeignObjects(compVersion); // or relying on current context
        }
    }

    /**
     * <p>Creates a default entity.</p>
     *
     * @return instance with test parameters
     */
    protected final CompVersion createEntity() {
        return createCompVersion();
    }

    /**
     * <p>Stores the {@link #entity} to database.</p>
     *
     * @param saveForeignObjects indicates whether save the entity only, or all foreign entities as well
     *                           <code>true</code> - save all foreign entities
     *                           <code>false</code> - save only the entity itself
     */
    protected void persistEntity(boolean saveForeignObjects) {
        final EntityTransaction entityTransaction = getEntityTransaction();
        try {
            entityTransaction.begin();
            if (saveForeignObjects) {
                persistCompVersionForeignObjects(getEntity(), false);
            }
            persistEntity(getEntity());
            final Component component = getEntity().getComponent();
            if (component != null) {
                persistEntity(component);
            }
            entityTransaction.commit();
        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        } finally {
            getEntityManager().clear();
        } // clears persistence context (caches etc.)
    }

    /**
     * <p>Persists CompVersion foreign entities to store. Is called before saving CompVersion itself.</p>
     *
     * @param compVersion entity instance
     */
    private static void persistForeignObjects(
        final CompVersion compVersion) {
        if (compVersion.getLink() != null) {
            compVersion.getLink().setCompVersion(compVersion);
        }
        if (compVersion.getForum() != null) {
            compVersion.getForum().setCompVersion(compVersion);
        }
        if (compVersion.getTechnologies() != null) {
            for (Technology technology : compVersion.getTechnologies()) {
                persistEntity(technology);
            }
        }
    }
}
