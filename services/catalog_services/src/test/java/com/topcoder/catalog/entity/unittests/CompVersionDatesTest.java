/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.CompVersionDates;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Status;
import junit.framework.TestCase;

import java.util.Date;

/**
 * <p>Unit test case for {@link CompVersionDates}.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class CompVersionDatesTest extends TestCase {
    /**
     * <p>Represents CompVersionDates instance for testing.</p>
     */
    private CompVersionDates compVersionDates;

    /**
     * <p>Creates a new instance of CompVersionDates.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        compVersionDates = new CompVersionDates();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        compVersionDates = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>CompVersionDates()</code> constructor.</p>
     */
    public void testCompVersionDates() {
        assertNotNull("Unable to instantiate CompVersionDates", compVersionDates);
    }

    /**
     * <p>Tests <code>getId()</code> and
     * <code>getId()</code> methods for accuracy.</p>
     */
    public void testGetSetId() {
        assertNull("Incorrect default id value", compVersionDates.getId());
        Long id = 1L;
        // set a id
        compVersionDates.setId(id);
        assertEquals("Incorrect id value after setting a new one",
            id, compVersionDates.getId());
    }

    /**
     * <p>Tests <code>setId(null)</code>.</p>
     */
    public void testSetIdAllowsNull() {
        // set null
        try {
            compVersionDates.setId(null);
            assertNull("Field 'id' should contain 'null' value",
                compVersionDates.getId());
        } catch (IllegalArgumentException e) {
            fail("Field 'id' should allow null values");
        }
    }


    /**
     * <p>Tests <code>getAggregationCompleteDate()</code> and
     * <code>setAggregationCompleteDate()</code> methods for accuracy.</p>
     */
    public void testGetSetAggregationCompleteDate() {
        assertNull("Incorrect default aggregationCompleteDate value", compVersionDates.getAggregationCompleteDate());
        Date value = new Date(1169499600000L) /*2007-01-23*/;
        // set a aggregationCompleteDate
        compVersionDates.setAggregationCompleteDate(value);
        assertEquals("Incorrect aggregationCompleteDate value after setting a new one",
            value, compVersionDates.getAggregationCompleteDate());
    }

    /**
     * <p>Tests <code>setAggregationCompleteDate(null)</code>.</p>
     */
    public void testAggregationCompleteDateAllowsNull() {
        // set a aggregationCompleteDate
        // set null
        try {
            compVersionDates.setAggregationCompleteDate(null);
            assertNull("Field 'aggregationCompleteDate' should contain 'null' value",
                compVersionDates.getAggregationCompleteDate());
        } catch (IllegalArgumentException e) {
            fail("Field 'aggregationCompleteDate' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getAggregationCompleteDateComment()</code> and
     * <code>setAggregationCompleteDateComment()</code> methods for accuracy.</p>
     */
    public void testGetSetAggregationCompleteDateComment() {
        assertNull("Incorrect default aggregationCompleteDateComment value",
            compVersionDates.getAggregationCompleteDateComment());
        String value = "23";
        // set a aggregationCompleteDateComment
        compVersionDates.setAggregationCompleteDateComment(value);
        assertEquals("Incorrect aggregationCompleteDateComment value after setting a new one",
            value, compVersionDates.getAggregationCompleteDateComment());
    }

    /**
     * <p>Tests <code>setAggregationCompleteDateComment(null)</code>.</p>
     */
    public void testAggregationCompleteDateCommentAllowsNull() {
        // set a aggregationCompleteDateComment
        // set null
        try {
            compVersionDates.setAggregationCompleteDateComment(null);
            assertNull("Field 'aggregationCompleteDateComment' should contain 'null' value",
                compVersionDates.getAggregationCompleteDateComment());
        } catch (IllegalArgumentException e) {
            fail("Field 'aggregationCompleteDateComment' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getEstimatedDevDate()</code> and
     * <code>setEstimatedDevDate()</code> methods for accuracy.</p>
     */
    public void testGetSetEstimatedDevDate() {
        assertNull("Incorrect default estimatedDevDate value", compVersionDates.getEstimatedDevDate());
        Date value = new Date(1169672400000L) /*2007-01-25*/;
        // set a estimatedDevDate
        compVersionDates.setEstimatedDevDate(value);
        assertEquals("Incorrect estimatedDevDate value after setting a new one",
            value, compVersionDates.getEstimatedDevDate());
    }

    /**
     * <p>Tests <code>setEstimatedDevDate(null)</code>.</p>
     */
    public void testEstimatedDevDateAllowsNull() {
        // set a estimatedDevDate
        // set null
        try {
            compVersionDates.setEstimatedDevDate(null);
            assertNull("Field 'estimatedDevDate' should contain 'null' value",
                compVersionDates.getEstimatedDevDate());
        } catch (IllegalArgumentException e) {
            fail("Field 'estimatedDevDate' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getFinalSubmissionDate()</code> and
     * <code>setFinalSubmissionDate()</code> methods for accuracy.</p>
     */
    public void testGetSetFinalSubmissionDate() {
        assertNull("Incorrect default finalSubmissionDate value", compVersionDates.getFinalSubmissionDate());
        Date value = new Date(1169758800000L) /*2007-01-26*/;
        // set a finalSubmissionDate
        compVersionDates.setFinalSubmissionDate(value);
        assertEquals("Incorrect finalSubmissionDate value after setting a new one",
            value, compVersionDates.getFinalSubmissionDate());
    }

    /**
     * <p>Tests <code>setFinalSubmissionDate(null)</code>.</p>
     */
    public void testFinalSubmissionDateAllowsNull() {
        // set a finalSubmissionDate
        // set null
        try {
            compVersionDates.setFinalSubmissionDate(null);
            assertNull("Field 'finalSubmissionDate' should contain 'null' value",
                compVersionDates.getFinalSubmissionDate());
        } catch (IllegalArgumentException e) {
            fail("Field 'finalSubmissionDate' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getFinalSubmissionDateComment()</code> and
     * <code>setFinalSubmissionDateComment()</code> methods for accuracy.</p>
     */
    public void testGetSetFinalSubmissionDateComment() {
        assertNull("Incorrect default finalSubmissionDateComment value",
            compVersionDates.getFinalSubmissionDateComment());
        String value = "26";
        // set a finalSubmissionDateComment
        compVersionDates.setFinalSubmissionDateComment(value);
        assertEquals("Incorrect finalSubmissionDateComment value after setting a new one",
            value, compVersionDates.getFinalSubmissionDateComment());
    }

    /**
     * <p>Tests <code>setFinalSubmissionDateComment(null)</code>.</p>
     */
    public void testFinalSubmissionDateCommentAllowsNull() {
        // set a finalSubmissionDateComment
        // set null
        try {
            compVersionDates.setFinalSubmissionDateComment(null);
            assertNull("Field 'finalSubmissionDateComment' should contain 'null' value",
                compVersionDates.getFinalSubmissionDateComment());
        } catch (IllegalArgumentException e) {
            fail("Field 'finalSubmissionDateComment' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getInitialSubmissionDate()</code> and
     * <code>setInitialSubmissionDate()</code> methods for accuracy.</p>
     */
    public void testGetSetInitialSubmissionDate() {
        assertNull("Incorrect default initialSubmissionDate value", compVersionDates.getInitialSubmissionDate());
        Date value = new Date(1169931600000L) /*2007-01-28*/;
        // set a initialSubmissionDate
        compVersionDates.setInitialSubmissionDate(value);
        assertEquals("Incorrect initialSubmissionDate value after setting a new one",
            value, compVersionDates.getInitialSubmissionDate());
    }

    /**
     * <p>Tests <code>setInitialSubmissionDate(null)</code>.</p>
     */
    public void testInitialSubmissionDateAllowsNull() {
        // set a initialSubmissionDate
        // set null
        try {
            compVersionDates.setInitialSubmissionDate(null);
            assertNull("Field 'initialSubmissionDate' should contain 'null' value",
                compVersionDates.getInitialSubmissionDate());
        } catch (IllegalArgumentException e) {
            fail("Field 'initialSubmissionDate' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getInitialSubmissionDateComment()</code> and
     * <code>setInitialSubmissionDateComment()</code> methods for accuracy.</p>
     */
    public void testGetSetInitialSubmissionDateComment() {
        assertNull("Incorrect default initialSubmissionDateComment value",
            compVersionDates.getInitialSubmissionDateComment());
        String value = "28";
        // set a initialSubmissionDateComment
        compVersionDates.setInitialSubmissionDateComment(value);
        assertEquals("Incorrect initialSubmissionDateComment value after setting a new one",
            value, compVersionDates.getInitialSubmissionDateComment());
    }

    /**
     * <p>Tests <code>setInitialSubmissionDateComment(null)</code>.</p>
     */
    public void testInitialSubmissionDateCommentAllowsNull() {
        // set a initialSubmissionDateComment
        // set null
        try {
            compVersionDates.setInitialSubmissionDateComment(null);
            assertNull("Field 'initialSubmissionDateComment' should contain 'null' value",
                compVersionDates.getInitialSubmissionDateComment());
        } catch (IllegalArgumentException e) {
            fail("Field 'initialSubmissionDateComment' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getLevelId()</code> and
     * <code>setLevelId()</code> methods for accuracy.</p>
     */
    public void testGetSetLevelId() {
        assertNull("Incorrect default levelId value", compVersionDates.getLevelId());
        Long value = 124554051614L;
        // set a levelId
        compVersionDates.setLevelId(value);
        assertEquals("Incorrect levelId value after setting a new one",
            value, compVersionDates.getLevelId());
    }

    /**
     * <p>Tests <code>setLevelId(null)</code>.</p>
     */
    public void testLevelIdAllowsNull() {
        // set a levelId
        // set null
        try {
            compVersionDates.setLevelId(null);
            assertNull("Field 'levelId' should contain 'null' value",
                compVersionDates.getLevelId());
        } catch (IllegalArgumentException e) {
            fail("Field 'levelId' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getPhase()</code> and
     * <code>setPhase()</code> methods for accuracy.</p>
     */
    public void testGetSetPhase() {
        assertNull("Incorrect default phase value", compVersionDates.getPhase());
        Phase value = new Phase();
        // set a phase
        compVersionDates.setPhase(value);
        assertEquals("Incorrect phase value after setting a new one",
            value, compVersionDates.getPhase());
    }

    /**
     * <p>Tests <code>setPhase(null)</code>.</p>
     */
    public void testPhaseAllowsNull() {
        // set a phase
        // set null
        try {
            compVersionDates.setPhase(null);
            assertNull("Field 'phase' should contain 'null' value",
                compVersionDates.getPhase());
        } catch (IllegalArgumentException e) {
            fail("Field 'phase' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getPhaseCompleteDate()</code> and
     * <code>setPhaseCompleteDate()</code> methods for accuracy.</p>
     */
    public void testGetSetPhaseCompleteDate() {
        assertNull("Incorrect default phaseCompleteDate value", compVersionDates.getPhaseCompleteDate());
        Date value = new Date(1170277200000L) /*2007-02-01*/;
        // set a phaseCompleteDate
        compVersionDates.setPhaseCompleteDate(value);
        assertEquals("Incorrect phaseCompleteDate value after setting a new one",
            value, compVersionDates.getPhaseCompleteDate());
    }

    /**
     * <p>Tests <code>setPhaseCompleteDate(null)</code>.</p>
     */
    public void testPhaseCompleteDateAllowsNull() {
        // set a phaseCompleteDate
        // set null
        try {
            compVersionDates.setPhaseCompleteDate(null);
            assertNull("Field 'phaseCompleteDate' should contain 'null' value",
                compVersionDates.getPhaseCompleteDate());
        } catch (IllegalArgumentException e) {
            fail("Field 'phaseCompleteDate' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getPhaseCompleteDateComment()</code> and
     * <code>setPhaseCompleteDateComment()</code> methods for accuracy.</p>
     */
    public void testGetSetPhaseCompleteDateComment() {
        assertNull("Incorrect default phaseCompleteDateComment value", compVersionDates.getPhaseCompleteDateComment());
        String value = "32";
        // set a phaseCompleteDateComment
        compVersionDates.setPhaseCompleteDateComment(value);
        assertEquals("Incorrect phaseCompleteDateComment value after setting a new one",
            value, compVersionDates.getPhaseCompleteDateComment());
    }

    /**
     * <p>Tests <code>setPhaseCompleteDateComment(null)</code>.</p>
     */
    public void testPhaseCompleteDateCommentAllowsNull() {
        // set a phaseCompleteDateComment
        // set null
        try {
            compVersionDates.setPhaseCompleteDateComment(null);
            assertNull("Field 'phaseCompleteDateComment' should contain 'null' value",
                compVersionDates.getPhaseCompleteDateComment());
        } catch (IllegalArgumentException e) {
            fail("Field 'phaseCompleteDateComment' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getPostingDate()</code> and
     * <code>setPostingDate()</code> methods for accuracy.</p>
     */
    public void testGetSetPostingDate() {
        assertNull("Incorrect default postingDate value", compVersionDates.getPostingDate());
        Date value = new Date(1170450000000L) /*2007-02-03*/;
        // set a postingDate
        compVersionDates.setPostingDate(value);
        assertEquals("Incorrect postingDate value after setting a new one",
            value, compVersionDates.getPostingDate());
    }

    /**
     * <p>Tests <code>setPostingDate(null)</code>.</p>
     */
    public void testPostingDateAllowsNull() {
        // set a postingDate
        // set null
        try {
            compVersionDates.setPostingDate(null);
            assertNull("Field 'postingDate' should contain 'null' value",
                compVersionDates.getPostingDate());
        } catch (IllegalArgumentException e) {
            fail("Field 'postingDate' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getPrice()</code> and
     * <code>setPrice()</code> methods for accuracy.</p>
     */
    public void testGetSetPrice() {
        assertEquals("Incorrect default price value", 0.0d, compVersionDates.getPrice());
        double value = 34.5;
        // set a price
        compVersionDates.setPrice(value);
        assertEquals("Incorrect price value after setting a new one",
            value, compVersionDates.getPrice());
    }

    /**
     * <p>Tests <code>getProductionDate()</code> and
     * <code>setProductionDate()</code> methods for accuracy.</p>
     */
    public void testGetSetProductionDate() {
        assertNull("Incorrect default productionDate value", compVersionDates.getProductionDate());
        Date value = new Date(1170622800000L) /*2007-02-05*/;
        // set a productionDate
        compVersionDates.setProductionDate(value);
        assertEquals("Incorrect productionDate value after setting a new one",
            value, compVersionDates.getProductionDate());
    }

    /**
     * <p>Tests <code>setProductionDate(null)</code>.</p>
     */
    public void testProductionDateAllowsNull() {
        // set a productionDate
        // set null
        try {
            compVersionDates.setProductionDate(null);
            assertNull("Field 'productionDate' should contain 'null' value",
                compVersionDates.getProductionDate());
        } catch (IllegalArgumentException e) {
            fail("Field 'productionDate' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getProductionDateComment()</code> and
     * <code>setProductionDateComment()</code> methods for accuracy.</p>
     */
    public void testGetSetProductionDateComment() {
        assertNull("Incorrect default productionDateComment value", compVersionDates.getProductionDateComment());
        String value = "36";
        // set a productionDateComment
        compVersionDates.setProductionDateComment(value);
        assertEquals("Incorrect productionDateComment value after setting a new one",
            value, compVersionDates.getProductionDateComment());
    }

    /**
     * <p>Tests <code>setProductionDateComment(null)</code>.</p>
     */
    public void testProductionDateCommentAllowsNull() {
        // set a productionDateComment
        // set null
        try {
            compVersionDates.setProductionDateComment(null);
            assertNull("Field 'productionDateComment' should contain 'null' value",
                compVersionDates.getProductionDateComment());
        } catch (IllegalArgumentException e) {
            fail("Field 'productionDateComment' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getReviewCompleteDate()</code> and
     * <code>setReviewCompleteDate()</code> methods for accuracy.</p>
     */
    public void testGetSetReviewCompleteDate() {
        assertNull("Incorrect default reviewCompleteDate value", compVersionDates.getReviewCompleteDate());
        Date value = new Date(1170795600000L) /*2007-02-07*/;
        // set a reviewCompleteDate
        compVersionDates.setReviewCompleteDate(value);
        assertEquals("Incorrect reviewCompleteDate value after setting a new one",
            value, compVersionDates.getReviewCompleteDate());
    }

    /**
     * <p>Tests <code>setReviewCompleteDate(null)</code>.</p>
     */
    public void testReviewCompleteDateAllowsNull() {
        // set a reviewCompleteDate
        // set null
        try {
            compVersionDates.setReviewCompleteDate(null);
            assertNull("Field 'reviewCompleteDate' should contain 'null' value",
                compVersionDates.getReviewCompleteDate());
        } catch (IllegalArgumentException e) {
            fail("Field 'reviewCompleteDate' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getReviewCompleteDateComment()</code> and
     * <code>setReviewCompleteDateComment()</code> methods for accuracy.</p>
     */
    public void testGetSetReviewCompleteDateComment() {
        assertNull("Incorrect default reviewCompleteDateComment value",
            compVersionDates.getReviewCompleteDateComment());
        String value = "38";
        // set a reviewCompleteDateComment
        compVersionDates.setReviewCompleteDateComment(value);
        assertEquals("Incorrect reviewCompleteDateComment value after setting a new one",
            value, compVersionDates.getReviewCompleteDateComment());
    }

    /**
     * <p>Tests <code>setReviewCompleteDateComment(null)</code>.</p>
     */
    public void testReviewCompleteDateCommentAllowsNull() {
        // set a reviewCompleteDateComment
        // set null
        try {
            compVersionDates.setReviewCompleteDateComment(null);
            assertNull("Field 'reviewCompleteDateComment' should contain 'null' value",
                compVersionDates.getReviewCompleteDateComment());
        } catch (IllegalArgumentException e) {
            fail("Field 'reviewCompleteDateComment' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getScreeningCompleteDate()</code> and
     * <code>setScreeningCompleteDate()</code> methods for accuracy.</p>
     */
    public void testGetSetScreeningCompleteDate() {
        assertNull("Incorrect default screeningCompleteDate value", compVersionDates.getScreeningCompleteDate());
        Date value = new Date(1170968400000L) /*2007-02-09*/;
        // set a screeningCompleteDate
        compVersionDates.setScreeningCompleteDate(value);
        assertEquals("Incorrect screeningCompleteDate value after setting a new one",
            value, compVersionDates.getScreeningCompleteDate());
    }

    /**
     * <p>Tests <code>setScreeningCompleteDate(null)</code>.</p>
     */
    public void testScreeningCompleteDateAllowsNull() {
        // set a screeningCompleteDate
        // set null
        try {
            compVersionDates.setScreeningCompleteDate(null);
            assertNull("Field 'screeningCompleteDate' should contain 'null' value",
                compVersionDates.getScreeningCompleteDate());
        } catch (IllegalArgumentException e) {
            fail("Field 'screeningCompleteDate' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getScreeningCompleteDateComment()</code> and
     * <code>setScreeningCompleteDateComment()</code> methods for accuracy.</p>
     */
    public void testGetSetScreeningCompleteDateComment() {
        assertNull("Incorrect default screeningCompleteDateComment value",
            compVersionDates.getScreeningCompleteDateComment());
        String value = "40";
        // set a screeningCompleteDateComment
        compVersionDates.setScreeningCompleteDateComment(value);
        assertEquals("Incorrect screeningCompleteDateComment value after setting a new one",
            value, compVersionDates.getScreeningCompleteDateComment());
    }

    /**
     * <p>Tests <code>setScreeningCompleteDateComment(null)</code>.</p>
     */
    public void testScreeningCompleteDateCommentAllowsNull() {
        // set a screeningCompleteDateComment
        // set null
        try {
            compVersionDates.setScreeningCompleteDateComment(null);
            assertNull("Field 'screeningCompleteDateComment' should contain 'null' value",
                compVersionDates.getScreeningCompleteDateComment());
        } catch (IllegalArgumentException e) {
            fail("Field 'screeningCompleteDateComment' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getStatus()</code> and
     * <code>setStatus()</code> methods for accuracy.</p>
     */
    public void testGetSetStatus() {
        assertNull("Incorrect default status value", compVersionDates.getStatus());
        Status value = Status.ACTIVE;
        // set a status
        compVersionDates.setStatus(value);
        assertEquals("Incorrect status value after setting a new one",
            value, compVersionDates.getStatus());
    }

    /**
     * <p>Tests <code>setStatus(null)</code>.</p>
     */
    public void testStatusAllowsNull() {
        // set a status
        // set null
        try {
            compVersionDates.setStatus(null);
            assertNull("Field 'status' should contain 'null' value",
                compVersionDates.getStatus());
        } catch (IllegalArgumentException e) {
            fail("Field 'status' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getTotalSubmissions()</code> and
     * <code>setTotalSubmissions()</code> methods for accuracy.</p>
     */
    public void testGetSetTotalSubmissions() {
        assertEquals("Incorrect default totalSubmissions value", 0, compVersionDates.getTotalSubmissions());
        int value = 42;
        // set a totalSubmissions
        compVersionDates.setTotalSubmissions(value);
        assertEquals("Incorrect totalSubmissions value after setting a new one",
            value, compVersionDates.getTotalSubmissions());
    }

    /**
     * <p>Tests <code>getWinnerAnnouncedDate()</code> and
     * <code>setWinnerAnnouncedDate()</code> methods for accuracy.</p>
     */
    public void testGetSetWinnerAnnouncedDate() {
        assertNull("Incorrect default winnerAnnouncedDate value", compVersionDates.getWinnerAnnouncedDate());
        Date value = new Date(1171314000000L) /*2007-02-13*/;
        // set a winnerAnnouncedDate
        compVersionDates.setWinnerAnnouncedDate(value);
        assertEquals("Incorrect winnerAnnouncedDate value after setting a new one",
            value, compVersionDates.getWinnerAnnouncedDate());
    }

    /**
     * <p>Tests <code>setWinnerAnnouncedDate(null)</code>.</p>
     */
    public void testWinnerAnnouncedDateAllowsNull() {
        // set a winnerAnnouncedDate
        // set null
        try {
            compVersionDates.setWinnerAnnouncedDate(null);
            assertNull("Field 'winnerAnnouncedDate' should contain 'null' value",
                compVersionDates.getWinnerAnnouncedDate());
        } catch (IllegalArgumentException e) {
            fail("Field 'winnerAnnouncedDate' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getWinnerAnnouncedDateComment()</code> and
     * <code>setWinnerAnnouncedDateComment()</code> methods for accuracy.</p>
     */
    public void testGetSetWinnerAnnouncedDateComment() {
        assertNull("Incorrect default winnerAnnouncedDateComment value",
            compVersionDates.getWinnerAnnouncedDateComment());
        String value = "44";
        // set a winnerAnnouncedDateComment
        compVersionDates.setWinnerAnnouncedDateComment(value);
        assertEquals("Incorrect winnerAnnouncedDateComment value after setting a new one",
            value, compVersionDates.getWinnerAnnouncedDateComment());
    }

    /**
     * <p>Tests <code>setWinnerAnnouncedDateComment(null)</code>.</p>
     */
    public void testWinnerAnnouncedDateCommentAllowsNull() {
        // set a winnerAnnouncedDateComment
        // set null
        try {
            compVersionDates.setWinnerAnnouncedDateComment(null);
            assertNull("Field 'winnerAnnouncedDateComment' should contain 'null' value",
                compVersionDates.getWinnerAnnouncedDateComment());
        } catch (IllegalArgumentException e) {
            fail("Field 'winnerAnnouncedDateComment' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getCompVersion()</code> and
     * <code>setCompVersion()</code> methods for accuracy.</p>
     */
    public void testGetSetVersion() {
        assertNull("Incorrect default version value", compVersionDates.getCompVersion());
        CompVersion value = new CompVersion();
        // set a version
        compVersionDates.setCompVersion(value);
        assertEquals("Incorrect version value after setting a new one",
            value, compVersionDates.getCompVersion());
    }

    /**
     * <p>Tests <code>setCompVersion(null)</code>.</p>
     */
    public void testVersionAllowsNull() {
        // set a version
        // set null
        try {
            compVersionDates.setCompVersion(null);
            assertNull("Field 'version' should contain 'null' value",
                compVersionDates.getCompVersion());
        } catch (IllegalArgumentException e) {
            fail("Field 'version' should allow null values");
        }
    }
}
