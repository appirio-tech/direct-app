/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.accuracytests;

import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.CompVersionDates;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Status;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Accuracy test cases for Entity <code>Technology</code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestCompVersionDatesAccuracy extends BaseTest {
    /**
     * Test if the entity can be created.
     *
     * @throws Exception to junit.
     */
    public void testCreate() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        Phase phase = manager.find(Phase.class, new Long(0));

        // first get a CompVersion entity.
        CompVersion compVersion = manager.find(CompVersion.class, new Long(0));

        CompVersionDates dates = new CompVersionDates();
        dates.setAggregationCompleteDate(new Date());
        dates.setAggregationCompleteDateComment("aggregationComplate");
        dates.setCompVersion(compVersion);

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

        compVersion.getVersionDates().put(phase.getId(), dates);

        manager.merge(compVersion);

        transaction.commit();

        // find the entity.
        CompVersionDates ret = manager.find(CompVersionDates.class, compVersion.getVersionDates().get(phase.getId()).getId());

        assertNotNull("The CompVersionDates entity should be persisted.", ret);

        // verify some field.
        assertEquals("Equal is expected", "screen", ret.getScreeningCompleteDateComment());
        assertEquals("Equal is expected.", "comment", dates.getReviewCompleteDateComment());
        assertEquals("Equal to 10.", 10, dates.getTotalSubmissions());
        assertEquals("Equal to status.active.", Status.ACTIVE, dates.getStatus());
        assertEquals("Equal is expected.", new Long(1), dates.getLevelId());
    }

    /**
     * Test if the entity CompVersionDates can be updated.
     *
     * @throws Exception to junit.
     */
    public void testUpdate() throws Exception {
        EntityManager manager = this.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        DBUtil.initDB();
        if (transaction.isActive() == false) {
            transaction.begin();
        }


        Phase phase = manager.find(Phase.class, new Long(0));

        // first get a CompVersion entity.
        CompVersion compVersion = manager.find(CompVersion.class, new Long(0));

        CompVersionDates dates = new CompVersionDates();
        dates.setAggregationCompleteDate(new Date());
        dates.setAggregationCompleteDateComment("aggregationComplate");
        dates.setCompVersion(compVersion);

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

        compVersion.getVersionDates().put(phase.getId(), dates);

        manager.merge(compVersion);

        transaction.commit();

        // find the entity.
        dates = compVersion.getVersionDates().get(phase.getId());
        final Long datesId = dates.getId();
        CompVersionDates ret = manager.find(CompVersionDates.class, datesId);

        assertNotNull("The CompVersionDates entity should be persisted.", ret);

        // verify some field.
        assertEquals("Equal is expected", "screen", ret.getScreeningCompleteDateComment());
        assertEquals("Equal is expected.", "comment", dates.getReviewCompleteDateComment());
        assertEquals("Equal to 10.", 10, dates.getTotalSubmissions());
        assertEquals("Equal to status.active.", Status.ACTIVE, dates.getStatus());
        assertEquals("Equal is expected.", new Long(1), dates.getLevelId());

        // update productionDateComment.
        dates.setProductionDateComment("update");
        ret = manager.find(CompVersionDates.class, datesId);
        assertEquals("Equal is expected.", "update", ret.getProductionDateComment());

        // update price.
        dates.setPrice(1111.0);
        ret = manager.find(CompVersionDates.class, datesId);
        assertEquals("Equal is expected.", 1111.0, ret.getPrice());

        // update the aggregationCompleletDate.
        dates.setAggregationCompleteDate(new Date(111100000));
        ret = manager.find(CompVersionDates.class, datesId);
        assertEquals("Equal is expected.", new Date(111100000), ret.getAggregationCompleteDate());

        // update aggregation completeDate comment
        dates.setAggregationCompleteDateComment("new comment");
        ret = manager.find(CompVersionDates.class, datesId);
        assertEquals("Equal is expected.", "new comment", ret.getAggregationCompleteDateComment());

        // update the level id.
        dates.setLevelId(new Long(11));
        ret = manager.find(CompVersionDates.class, datesId);
        assertEquals("Equal is expected.", 11, ret.getLevelId().longValue());

        // update the status.
        dates.setStatus(Status.DECLINED);
        dates.setScreeningCompleteDate(new Date(111111111));
        dates.setScreeningCompleteDateComment("screen complete");
        ret = manager.find(CompVersionDates.class, datesId);

        assertEquals("Equal is expected.", Status.DECLINED, ret.getStatus());
        assertEquals("Equal is expected.", "screen complete", ret.getScreeningCompleteDateComment());
        assertEquals("Equal is expected.", new Date(111111111), ret.getScreeningCompleteDate());

        // update the EstimatedDevDate
        dates.setEstimatedDevDate(new Date(1111111));
        ret = manager.find(CompVersionDates.class, datesId);

        assertEquals("Equal is expected.", new Date(1111111), ret.getEstimatedDevDate());

        // update FinalSubmissionDate information.
        dates.setFinalSubmissionDate(new Date());
        dates.setFinalSubmissionDateComment("final update");
        ret = manager.find(CompVersionDates.class, datesId);
        assertEquals("Equal is expected.", "final update", ret.getFinalSubmissionDateComment());

        // update total submission
        dates.setTotalSubmissions(111);
        ret = manager.find(CompVersionDates.class, datesId);
        assertEquals("Equal is expected.", 111, ret.getTotalSubmissions());

        dates.setWinnerAnnouncedDate(new Date());
        dates.setWinnerAnnouncedDateComment("winner update");
        ret = manager.find(CompVersionDates.class, datesId);

        assertEquals("Equal is expected.", "winner update", ret.getWinnerAnnouncedDateComment());
        
        
        if (transaction.isActive() == false) {
            transaction.begin();
        }
        transaction.commit();
    }
}
