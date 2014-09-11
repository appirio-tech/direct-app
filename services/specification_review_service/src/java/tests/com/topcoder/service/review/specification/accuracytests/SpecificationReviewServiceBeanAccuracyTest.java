/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.accuracytests;

import java.util.Date;
import java.util.List;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.Project;
import com.topcoder.project.service.ejb.ProjectServicesBean;
import com.topcoder.security.TCSubject;
import com.topcoder.service.review.specification.SpecificationReviewStatus;
import com.topcoder.service.review.specification.ejb.MockPhaseManager;
import com.topcoder.service.review.specification.ejb.SpecificationReviewServiceBean;
import com.topcoder.service.review.specification.ejb.SpecificationReviewServiceLocal;
import com.topcoder.service.review.specification.ejb.SpecificationReviewServiceRemote;

/**
 * <p>
 * Accuracy tests for class <code>SpecificationReviewServiceBean</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SpecificationReviewServiceBeanAccuracyTest extends AccuracyBaseTest {

    /**
     * The <code>SpecificationReviewServiceBean</code> instance for accuracy test.
     */
    private MockSpecificationReviewServiceBean service;

    /**
     * <p>
     * Set the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        service = new MockSpecificationReviewServiceBean();
        setPrivateField(SpecificationReviewServiceBean.class, service, "loggerName",
            "specification_review_service_log");
        setPrivateField(SpecificationReviewServiceBean.class, service, "projectServices",
            new ProjectServicesBean());
        setPrivateField(SpecificationReviewServiceBean.class, service, "mockSubmissionFilePath",
            "test_files/mock_submission/");
        setPrivateField(SpecificationReviewServiceBean.class, service, "mockSubmissionFileName",
            "mock_rs.rtf");
        setPrivateField(SpecificationReviewServiceBean.class, service, "searchBundleManageNamespace",
            "com.topcoder.search.builder.Upload_Resource_Search");
        setPrivateField(SpecificationReviewServiceBean.class, service, "reviewManagerClassName",
            "com.topcoder.management.review.DefaultReviewManager");
        setPrivateField(SpecificationReviewServiceBean.class, service, "scorecardManagerClassName",
            "com.topcoder.management.scorecard.ScorecardManagerImpl");
        setPrivateField(SpecificationReviewServiceBean.class, service,
            "uploadExternalServicesClassName",
            "com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices");
        setPrivateField(SpecificationReviewServiceBean.class, service, "dbConnectionFactoryClassName",
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        setPrivateField(SpecificationReviewServiceBean.class, service, "dbConnectionFactoryNamespace",
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        service.initialize();
    }

    /**
     * <p>
     * Clear the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test for the constructor.
     * </p>
     */
    public void testConstructor1() {
        SpecificationReviewServiceBean local = new SpecificationReviewServiceBean();
        assertNotNull("The instance should be created successfully", local);
        assertTrue(local instanceof SpecificationReviewServiceLocal);
        assertTrue(local instanceof SpecificationReviewServiceRemote);
    }

    /**
     * <p>
     * Accuracy test for the constructor.
     * </p>
     */
    public void testConstructor2() {
        SpecificationReviewServiceBean local = new SpecificationReviewServiceBean();

        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "projectServices"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "mockSubmissionFileName"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "mockSubmissionFilePath"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local,
            "searchBundleManageNamespace"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "reviewManagerClassName"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local,
            "scorecardManagerClassName"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local,
            "uploadExternalServicesClassName"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local,
            "dbConnectionFactoryClassName"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "connectionName"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "reviewManagerNamespace"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local,
            "scorecardManagerNamespace"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local,
            "uploadExternalServicesNamespace"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local,
            "dbConnectionFactoryNamespace"));

        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "reviewManager"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "uploadManager"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "log"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "dbConnectionFactory"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "scorecardManager"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "resourceManager"));
        assertNull(getPrivateField(SpecificationReviewServiceBean.class, local, "uploadExternalServices"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>initialize()</code>.
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testInitialize() throws Exception {
        SpecificationReviewServiceBean local = service;

        assertNotNull(getPrivateField(SpecificationReviewServiceBean.class, local, "reviewManager"));
        assertNotNull(getPrivateField(SpecificationReviewServiceBean.class, local, "uploadManager"));
        assertNotNull(getPrivateField(SpecificationReviewServiceBean.class, local, "log"));
        assertNotNull(getPrivateField(SpecificationReviewServiceBean.class, local, "dbConnectionFactory"));
        assertNotNull(getPrivateField(SpecificationReviewServiceBean.class, local, "scorecardManager"));
        assertNotNull(getPrivateField(SpecificationReviewServiceBean.class, local, "resourceManager"));
        assertNotNull(getPrivateField(SpecificationReviewServiceBean.class, local,
            "uploadExternalServices"));
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>scheduleSpecificationReview(TCSubject tcSubject, long projectId, Date reviewStartDate)</code>
     * .
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testScheduleSpecificationReview1() throws Exception {
        Date date = new Date();
        TCSubject sub = new TCSubject(null, 1L);

        try {
            service.scheduleSpecificationReview(sub, 1, date);
        } catch (Exception e) {
            fail("No exception should be thrown!");
        }
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>scheduleSpecificationReview(TCSubject tcSubject, long projectId, Date reviewStartDate)</code>
     * .
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testScheduleSpecificationReview2() throws Exception {
        Date date = new Date();
        long id = 1L;
        TCSubject sub = new TCSubject(null, id);

        service.scheduleSpecificationReview(sub, 1, date);

        Project project = MockPhaseManager.getPhases().get("" + id);
        assertNotNull(project);

        Phase submissionPhase = getSubmissionPhase(project);
        assertNotNull(submissionPhase);
        assertEquals(PhaseStatus.SCHEDULED, submissionPhase.getPhaseStatus());
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>scheduleSpecificationReview(TCSubject tcSubject, long projectId, Date reviewStartDate)</code>
     * .
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testScheduleSpecificationReview3() throws Exception {
        Date date = new Date(System.currentTimeMillis() + DAY);
        long id = 1L;
        TCSubject sub = new TCSubject(null, id);

        service.scheduleSpecificationReview(sub, 1, date);

        Project project = MockPhaseManager.getPhases().get("" + id);
        assertNotNull(project);

        Phase submissionPhase = getSubmissionPhase(project);
        assertNotNull(submissionPhase);
        assertEquals(PhaseStatus.SCHEDULED, submissionPhase.getPhaseStatus());
    }

    /**
     * <p>
     * Accuracy test for the method <code>submitSpecificationAsString(TCSubject,
     * long, String)</code>.
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testSubmitSpecificationAsString() throws Exception {
        TCSubject sub = new TCSubject(null, 1L);

        try {
            service.submitSpecificationAsString(sub, 1, "String Submission");
        } catch (Exception e) {
            fail("No exception should be thrown!");
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>submitSpecificationAsFile(TCSubject,
     * long, String)</code>.
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testSubmitSpecificationAsFile() throws Exception {
        TCSubject sub = new TCSubject(null, 1L);

        try {
            service.submitSpecificationAsFile(sub, 1L, "test_files/accuracytests/test.file");
        } catch (Exception e) {
            fail("No exception should be thrown!");
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSpecificationReview(TCSubject,
     * long)</code>.
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testGetSpecificationReview() throws Exception {
        TCSubject sub = new TCSubject(null, 1L);

        try {
            assertNull(service.getSpecificationReview(sub, 1001L));
        } catch (Exception e) {
            fail("No exception should be thrown!");
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSpecificationReviewStatus(TCSubject,
     * long)</code>.
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testGetSpecificationReviewStatus1() throws Exception {
        TCSubject sub = new TCSubject(null, 1L);
        SpecificationReviewStatus status = service.getSpecificationReviewStatus(sub, 1L);
        assertNotNull(status);
        assertEquals(SpecificationReviewStatus.WAITING_FOR_FIXES, status);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSpecificationReviewStatus(TCSubject,
     * long)</code>.
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testGetSpecificationReviewStatus2() throws Exception {
        TCSubject sub = new TCSubject(null, 1L);
        long magicId = 1234L;
        SpecificationReviewStatus status = service.getSpecificationReviewStatus(sub, magicId);
        assertNull(status);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSpecificationReview(TCSubject,
     * long)</code>.
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testGetSpecificationReview1() throws Exception {
        TCSubject sub = new TCSubject(null, 1L);
        assertNull(service.getSpecificationReview(sub, 1001L));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getOpenSpecificationReviewPositions(TCSubject)</code>
     * .
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testGetOpenSpecificationReviewPositions() throws Exception {
        TCSubject sub = new TCSubject(null, 1L);
        List<Long> positions = service.getOpenSpecificationReviewPositions(sub);
        assertNotNull(positions);
        assertTrue(positions.size() > 0);
    }

    /**
     * Gets the submission phase from the project.
     *
     * @param project the project
     * @return the submission phase
     */
    private Phase getSubmissionPhase(Project project) {
        for (Phase phase : project.getAllPhases()) {
            if (phase.getPhaseType().getName().equals("Specification Submission")) {
                return phase;
            }
        }
        return null;
    }
}
