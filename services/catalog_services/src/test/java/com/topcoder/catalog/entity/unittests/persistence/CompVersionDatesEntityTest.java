/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import com.topcoder.catalog.entity.CompVersionDates;
import com.topcoder.catalog.entity.TestHelper;

import java.util.Set;

/**
 * <p>This test checks all attributes of CompVersionDates entity correspond database's columns.</p>
 * <p>Before each test the {@link #setUp} method is called. This method saves all necessary foreign
 * entities to database and the entity itself.</p>
 * <p>If the entity saved successfully, it indicates that all constraints are satisfied.</p>
 * <p>After that each method tries to find the entity by a field value pointed in where clause.</p>
 * <p>If the list is empty, then the test fails. It guarantees that each entity attribute mapped to
 * a separate database column (no values in all entities tests are duplicated).</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class CompVersionDatesEntityTest extends BaseCompVersionDatesEntityTest {

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
     * <p>Test <code>aggregationCompleteDate</code> attribute stored properly.</p>
     */
    public void testAggregationCompleteDate() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where aggregationCompleteDate=?", AGGREGATIONCOMPLETEDATE);
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompVersionDates e where aggregationCompleteDate='"
                + AGGREGATIONCOMPLETEDATE + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>aggregationCompleteDateComment</code> attribute stored properly.</p>
     */
    public void testAggregationCompleteDateComment() {
        final Set<CompVersionDates> items = getEntities(
            "select e from CompVersionDates e where aggregationCompleteDateComment=?", AGGREGATIONCOMPLETEDATECOMMENT);
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompVersionDates e where aggregationCompleteDateComment='"
                + AGGREGATIONCOMPLETEDATECOMMENT + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>estimatedDevDate</code> attribute stored properly.</p>
     */
    public void testEstimatedDevDate() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where estimatedDevDate=?", ESTIMATEDDEVDATE);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where estimatedDevDate='"
                + ESTIMATEDDEVDATE + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>finalSubmissionDate</code> attribute stored properly.</p>
     */
    public void testFinalSubmissionDate() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where finalSubmissionDate=?", FINALSUBMISSIONDATE);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where finalSubmissionDate='"
                + FINALSUBMISSIONDATE + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>finalSubmissionDateComment</code> attribute stored properly.</p>
     */
    public void testFinalSubmissionDateComment() {
        final Set<CompVersionDates> items = getEntities(
            "select e from CompVersionDates e where finalSubmissionDateComment=?", FINALSUBMISSIONDATECOMMENT);
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompVersionDates e where finalSubmissionDateComment='"
                + FINALSUBMISSIONDATECOMMENT + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>initialSubmissionDate</code> attribute stored properly.</p>
     */
    public void testInitialSubmissionDate() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where initialSubmissionDate=?", INITIALSUBMISSIONDATE);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where initialSubmissionDate='"
                + INITIALSUBMISSIONDATE + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>initialSubmissionDateComment</code> attribute stored properly.</p>
     */
    public void testInitialSubmissionDateComment() {
        final Set<CompVersionDates> items = getEntities(
            "select e from CompVersionDates e where initialSubmissionDateComment=?", INITIALSUBMISSIONDATECOMMENT);
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompVersionDates e where initialSubmissionDateComment='"
                + INITIALSUBMISSIONDATECOMMENT + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>levelId</code> attribute stored properly.</p>
     */
    public void testLevelId() {
        final Set<CompVersionDates> items = getEntities("select e from CompVersionDates e where levelId=?", LEVELID);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where levelId='"
                + LEVELID + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>phase</code> relation.</p>
     */
    public void testPhaseRelation() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e join e.phase f where f.description=?",
                BasePhaseEntityTest.DESCRIPTION);
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompVersionDates e join e.phase f where f.description='"
                + BasePhaseEntityTest.DESCRIPTION + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>phaseCompleteDate</code> attribute stored properly.</p>
     */
    public void testPhaseCompleteDate() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where phaseCompleteDate=?", PHASECOMPLETEDATE);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where phaseCompleteDate='"
                + PHASECOMPLETEDATE + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>phaseCompleteDateComment</code> attribute stored properly.</p>
     */
    public void testPhaseCompleteDateComment() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where phaseCompleteDateComment=?", PHASECOMPLETEDATECOMMENT);
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompVersionDates e where phaseCompleteDateComment='"
                + PHASECOMPLETEDATECOMMENT + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>postingDate</code> attribute stored properly.</p>
     */
    public void testPostingDate() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where postingDate=?", POSTINGDATE);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where postingDate='"
                + POSTINGDATE + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>price</code> attribute stored properly.</p>
     */
    public void testPrice() {
        final Set<CompVersionDates> items = getEntities("select e from CompVersionDates e where price=?", PRICE);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where price='"
                + PRICE + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>productionDate</code> attribute stored properly.</p>
     */
    public void testProductionDate() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where productionDate=?", PRODUCTIONDATE);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where productionDate='"
                + PRODUCTIONDATE + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>productionDateComment</code> attribute stored properly.</p>
     */
    public void testProductionDateComment() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where productionDateComment=?", PRODUCTIONDATECOMMENT);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where productionDateComment='"
                + PRODUCTIONDATECOMMENT + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>reviewCompleteDate</code> attribute stored properly.</p>
     */
    public void testReviewCompleteDate() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where reviewCompleteDate=?", REVIEWCOMPLETEDATE);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where reviewCompleteDate='"
                + REVIEWCOMPLETEDATE + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>reviewCompleteDateComment</code> attribute stored properly.</p>
     */
    public void testReviewCompleteDateComment() {
        final Set<CompVersionDates> items = getEntities(
            "select e from CompVersionDates e where reviewCompleteDateComment=?", REVIEWCOMPLETEDATECOMMENT);
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompVersionDates e where reviewCompleteDateComment='"
                + REVIEWCOMPLETEDATECOMMENT + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>screeningCompleteDate</code> attribute stored properly.</p>
     */
    public void testScreeningCompleteDate() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where screeningCompleteDate=?", SCREENINGCOMPLETEDATE);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where screeningCompleteDate='"
                + SCREENINGCOMPLETEDATE + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>screeningCompleteDateComment</code> attribute stored properly.</p>
     */
    public void testScreeningCompleteDateComment() {
        final Set<CompVersionDates> items = getEntities(
            "select e from CompVersionDates e where screeningCompleteDateComment=?", SCREENINGCOMPLETEDATECOMMENT);
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompVersionDates e where screeningCompleteDateComment='"
                + SCREENINGCOMPLETEDATECOMMENT + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>status</code> attribute stored properly.</p>
     */
    public void testStatus() {
        final Set<CompVersionDates> items = getEntities("select e from CompVersionDates e where status=?", STATUS);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where status='"
                + STATUS + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>totalSubmissions</code> attribute stored properly.</p>
     */
    public void testTotalSubmissions() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where totalSubmissions=?", TOTALSUBMISSIONS);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where totalSubmissions='"
                + TOTALSUBMISSIONS + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>winnerAnnouncedDate</code> attribute stored properly.</p>
     */
    public void testWinnerAnnouncedDate() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e where winnerAnnouncedDate=?", WINNERANNOUNCEDDATE);
        assertFalse(
            "Not found entities by the following query: select e from CompVersionDates e where winnerAnnouncedDate='"
                + WINNERANNOUNCEDDATE + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>winnerAnnouncedDateComment</code> attribute stored properly.</p>
     */
    public void testWinnerAnnouncedDateComment() {
        final Set<CompVersionDates> items = getEntities(
            "select e from CompVersionDates e where winnerAnnouncedDateComment=?", WINNERANNOUNCEDDATECOMMENT);
        assertFalse(
            "Not found entities by the following query:"
                + " select e from CompVersionDates e where winnerAnnouncedDateComment='"
                + WINNERANNOUNCEDDATECOMMENT + "'", items.isEmpty());
    }

    /**
     * <p>Test <code>version</code> relation.</p>
     */
    public void testCompVersionRelation() {
        final CompVersionDates item = TestHelper.getEntityManager().find(CompVersionDates.class, getEntity().getId());
        assertNotNull("'version' field should contain non null value", item.getCompVersion());
    }

    /**
     * <p>Test <code>version</code> relation.</p>
     */
    public void testCompVersionJoin() {
        final Set<CompVersionDates> items =
            getEntities("select e from CompVersionDates e join e.compVersion f where f.version=?",
                BaseCompVersionEntityTest.VERSION);
        assertFalse(
            "Not found entities by the following query: "
                + "select e from CompVersionDates e join e.compVersion f where f.version="
                + BaseCompVersionEntityTest.VERSION + "'", items.isEmpty());
    }
}
