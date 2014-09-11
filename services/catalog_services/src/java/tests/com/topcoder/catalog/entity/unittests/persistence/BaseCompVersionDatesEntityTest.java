/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.CompVersionDates;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.entity.TestHelper;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>This is a base class for CompVersionDatesEntity JPA tests.</p>
 * <p>It provide basic operations for concrete implementation
 * {@link CompVersionDatesEntityTest}.</p>
 * <p>It creates an entity instance with default fields values (including foreign entities).</p>
 * <p>Stores all necessary foreign entities to the database to prepare environment for CompVersionDates
 * entity testing.</p>
 * <p>After all test's cases are performed the method <code>tearDown</code> cleans up database
 * and provides tests isolation.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public abstract class BaseCompVersionDatesEntityTest extends CommonEntityTest<CompVersionDates> {
    /**
     * Default CompVersionDates.aggregationCompleteDate value.
     */
    public static final Date AGGREGATIONCOMPLETEDATE = parseDate("2007-01-23");
    /**
     * Default CompVersionDates.aggregationCompleteDateComment value.
     */
    public static final String AGGREGATIONCOMPLETEDATECOMMENT = "23";
    /**
     * Default CompVersionDates.estimatedDevDate value.
     */
    public static final Date ESTIMATEDDEVDATE = parseDate("2007-01-25");
    /**
     * Default CompVersionDates.finalSubmissionDate value.
     */
    public static final Date FINALSUBMISSIONDATE = parseDate("2007-01-26");
    /**
     * Default CompVersionDates.finalSubmissionDateComment value.
     */
    public static final String FINALSUBMISSIONDATECOMMENT = "26";
    /**
     * Default CompVersionDates.initialSubmissionDate value.
     */
    public static final Date INITIALSUBMISSIONDATE = parseDate("2007-01-28");
    /**
     * Default CompVersionDates.initialSubmissionDateComment value.
     */
    public static final String INITIALSUBMISSIONDATECOMMENT = "28";
    /**
     * Default CompVersionDates.levelId value.
     */
    public static final Long LEVELID = 124554051614L;
    /**
     * Default CompVersionDates.phaseCompleteDate value.
     */
    public static final Date PHASECOMPLETEDATE = parseDate("2007-02-01");
    /**
     * Default CompVersionDates.phaseCompleteDateComment value.
     */
    public static final String PHASECOMPLETEDATECOMMENT = "32";
    /**
     * Default CompVersionDates.postingDate value.
     */
    public static final Date POSTINGDATE = parseDate("2007-02-03");
    /**
     * Default CompVersionDates.price value.
     */
    public static final double PRICE = 34.5;
    /**
     * Default CompVersionDates.productionDate value.
     */
    public static final Date PRODUCTIONDATE = parseDate("2007-02-05");
    /**
     * Default CompVersionDates.productionDateComment value.
     */
    public static final String PRODUCTIONDATECOMMENT = "36";
    /**
     * Default CompVersionDates.reviewCompleteDate value.
     */
    public static final Date REVIEWCOMPLETEDATE = parseDate("2007-02-07");
    /**
     * Default CompVersionDates.reviewCompleteDateComment value.
     */
    public static final String REVIEWCOMPLETEDATECOMMENT = "38";
    /**
     * Default CompVersionDates.screeningCompleteDate value.
     */
    public static final Date SCREENINGCOMPLETEDATE = parseDate("2007-02-09");
    /**
     * Default CompVersionDates.screeningCompleteDateComment value.
     */
    public static final String SCREENINGCOMPLETEDATECOMMENT = "40";
    /**
     * Default CompVersionDates.status value.
     */
    public static final Status STATUS = Status.ACTIVE;
    /**
     * Default CompVersionDates.totalSubmissions value.
     */
    public static final int TOTALSUBMISSIONS = 42;
    /**
     * Default CompVersionDates.winnerAnnouncedDate value.
     */
    public static final Date WINNERANNOUNCEDDATE = parseDate("2007-02-13");
    /**
     * Default CompVersionDates.winnerAnnouncedDateComment value.
     */
    public static final String WINNERANNOUNCEDDATECOMMENT = "44";


    /**
     * <p>Constructor. No additional actions to the super constructor.</p>
     */
    protected BaseCompVersionDatesEntityTest() {
    }

    /**
     * <p>Create a CompVersionDates entity instance with default constant values defined above.</p>
     *
     * @return a CompVersionDates entity instance
     */
    public static CompVersionDates createCompVersionDates() {
        final CompVersionDates compVersionDates = new CompVersionDates();
        compVersionDates.setAggregationCompleteDate(AGGREGATIONCOMPLETEDATE);
        compVersionDates.setAggregationCompleteDateComment(AGGREGATIONCOMPLETEDATECOMMENT);
        compVersionDates.setEstimatedDevDate(ESTIMATEDDEVDATE);
        compVersionDates.setFinalSubmissionDate(FINALSUBMISSIONDATE);
        compVersionDates.setFinalSubmissionDateComment(FINALSUBMISSIONDATECOMMENT);
        compVersionDates.setInitialSubmissionDate(INITIALSUBMISSIONDATE);
        compVersionDates.setInitialSubmissionDateComment(INITIALSUBMISSIONDATECOMMENT);
        compVersionDates.setLevelId(LEVELID);
        compVersionDates.setPhaseCompleteDate(PHASECOMPLETEDATE);
        compVersionDates.setPhaseCompleteDateComment(PHASECOMPLETEDATECOMMENT);
        compVersionDates.setPostingDate(POSTINGDATE);
        compVersionDates.setPrice(PRICE);
        compVersionDates.setProductionDate(PRODUCTIONDATE);
        compVersionDates.setProductionDateComment(PRODUCTIONDATECOMMENT);
        compVersionDates.setReviewCompleteDate(REVIEWCOMPLETEDATE);
        compVersionDates.setReviewCompleteDateComment(REVIEWCOMPLETEDATECOMMENT);
        compVersionDates.setScreeningCompleteDate(SCREENINGCOMPLETEDATE);
        compVersionDates.setScreeningCompleteDateComment(SCREENINGCOMPLETEDATECOMMENT);
        compVersionDates.setStatus(STATUS);
        compVersionDates.setTotalSubmissions(TOTALSUBMISSIONS);
        compVersionDates.setWinnerAnnouncedDate(WINNERANNOUNCEDDATE);
        compVersionDates.setWinnerAnnouncedDateComment(WINNERANNOUNCEDDATECOMMENT);
        compVersionDates.setPhase(BasePhaseEntityTest.createPhase());

        return compVersionDates;
    }

    /**
     * <p>Persists CompVersionDates foreign entities to store. Is called before saving CompVersionDates itself.</p>
     *
     * @param compVersionDates    entity instance
     * @param separateTransaction <ul><li><code>true</code> - persist objects in a separate transaction</li>
     *                            <li><code>false</code> - already in a transaction context</li></ul>
     */
    protected static void persistCompVersionDatesForeignObjects(
        final CompVersionDates compVersionDates, boolean separateTransaction) {
        if (separateTransaction) { // within its own transaction
            final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
            try {
                entityTransaction.begin();
                persistForeignObjects(compVersionDates);
                entityTransaction.commit();
            } catch (PersistenceException e) {
                if (entityTransaction.isActive()) {
                    entityTransaction.rollback();
                }
                throw e; // re-throw the exception
            } finally {
                TestHelper.getEntityManager().clear(); // clears persistence context (caches etc.)
            }
        } else {
            persistForeignObjects(compVersionDates); // or relying on current context
        }
    }

    /**
     * <p>Creates a default entity.</p>
     *
     * @return instance with test parameters
     */
    protected final CompVersionDates createEntity() {
        return createCompVersionDates();
    }

    /**
     * <p>Stores the {@link #entity} to database.</p>
     *
     * @param saveForeignObjects indicates whether save the entity only, or all foreign entities as well
     *                           <code>true</code> - save all foreign entities
     *                           <code>false</code> - save only the entity itself
     */
    protected void persistEntity(boolean saveForeignObjects) {
        final EntityTransaction entityTransaction = TestHelper.getEntityTransaction();
        try {
            entityTransaction.begin();
            if (saveForeignObjects) {
                persistCompVersionDatesForeignObjects(getEntity(), false);
            }
            entityTransaction.commit();
        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        } finally {
            TestHelper.getEntityManager().clear();
        } // clears persistence context (caches etc.)
    }

    /**
     * <p>Persists CompVersionDates foreign entities to store. Is called before saving CompVersionDates itself.</p>
     *
     * @param compVersionDates entity instance
     */
    private static void persistForeignObjects(
        final CompVersionDates compVersionDates) {
        final Phase phase = compVersionDates.getPhase();
        persistEntity(phase);
        final CompVersion compVersion = BaseCompVersionEntityTest.createCompVersion();
        compVersion.setComponent(null); // no component dependency for testing
        final Map<Long, CompVersionDates> dates = new HashMap<Long, CompVersionDates>();
        dates.put(phase.getId(), compVersionDates);
        compVersion.setVersionDates(dates);
        BaseCompVersionEntityTest.persistCompVersionForeignObjects(compVersion, false);
        persistEntity(compVersion);
    }

    /**
     * <p>Parses a date by its date string representation.</p>
     *
     * @param dateAsString string representation of a date (in 'yyyy/MM/dd' format)
     * @return Date object parsed by SimpleDateFormat
     */
    private static Date parseDate(String dateAsString) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            return df.parse(dateAsString);
        } catch (ParseException e) {
            // make compiler happy
            return null;
        }
    }
}
