/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.ejb;

import java.lang.reflect.Field;
import java.util.Date;

import org.easymock.EasyMock;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.project.service.ejb.ProjectServicesBean;
import com.topcoder.security.TCSubject;
import com.topcoder.service.review.specification.SpecificationReviewServiceConfigurationException;
import com.topcoder.service.review.specification.SpecificationReviewServiceException;
import com.topcoder.service.review.specification.ejb.SpecificationReviewServiceBean;

import junit.framework.TestCase;

/**
 * Failure test for SpecificationReviewServiceBean class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SpecificationReviewServiceBeanFailureTest extends TestCase {
    /**
     * The instance of SpecificationReviewServiceBean used in test.
     */
    private SpecificationReviewServiceBean instance;

    /**
     * The instance of TCSubject used in test.
     */
    private TCSubject subject = new TCSubject(null, 1001L);

    /**
     * The specification file.
     */
    private final String specFile = "test_files/failure/spec.rtf";

    /**
     * Set up for each test.
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void setUp() throws Exception {
        instance = new SpecificationReviewServiceBean();
        setField("loggerName", "failuretest");
        setField("projectServices", new ProjectServicesBean());
        setField("mockSubmissionFilePath", "test_files/failure");
        setField("mockSubmissionFileName", "spec.rtf");
        setField("searchBundleManageNamespace", "com.topcoder.search.builder.Upload_Resource_Search");
        setField("reviewManagerClassName", "com.topcoder.management.review.DefaultReviewManager");
        setField("scorecardManagerClassName", "com.topcoder.management.scorecard.ScorecardManagerImpl");
        setField("uploadExternalServicesClassName",
            "com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices");
        setField("dbConnectionFactoryClassName", "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        setField("dbConnectionFactoryNamespace", "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        instance.initialize();
    }

    /**
     * Sets the field value to instance.
     *
     * @param fieldName
     *            The field name.
     * @param value
     *            The field value.
     *
     * @throws Exception
     *             to JUnit.
     */
    private void setField(String fieldName, Object value) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
        field.setAccessible(false);
    }

    /**
     * Test initialize(). When loggerName is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_LoggerNameIsEmpty() throws Exception {
        setField("loggerName", " ");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When loggerName is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ConnectionNamesIsEmpty() throws Exception {
        setField("connectionName", " ");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When mockSubmissionFileName is null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_MockSubmissionFileNameIsNull() throws Exception {
        setField("mockSubmissionFileName", null);
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When mockSubmissionFileName is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_MockSubmissionFileNameIsEmpty() throws Exception {
        setField("mockSubmissionFileName", " ");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * searchBundleManageNamespace Test initialize(). When mockSubmissionFileName is null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_SearchBundleManageNamespaceIsNull() throws Exception {
        setField("searchBundleManageNamespace", null);
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When searchBundleManageNamespace is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_SearchBundleManageNamespaceIsEmpty() throws Exception {
        setField("searchBundleManageNamespace", " ");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When searchBundleManageNamespace is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_SearchBundleManageNamespaceIsInvalid() throws Exception {
        setField("searchBundleManageNamespace", "invalid");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * searchBundleManageNamespace Test initialize(). When reviewManagerClassName is null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ReviewManagerClassNameIsNull() throws Exception {
        setField("reviewManagerClassName", null);
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When reviewManagerClassName is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ReviewManagerClassNameIsEmpty() throws Exception {
        setField("reviewManagerClassName", " ");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When reviewManagerClassName is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ReviewManagerClassNameIsInvalid1() throws Exception {
        setField("reviewManagerClassName", "invalid");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When reviewManagerClassName is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ReviewManagerClassNameIsInvalid2() throws Exception {
        setField("reviewManagerClassName", "java.lang.String");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When reviewManagerNamespace is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ReviewManagerNamespaceIsEmpty() throws Exception {
        setField("reviewManagerNamespace", " ");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When reviewManagerNamespace is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ReviewManagerNamespaceIsInvalid1() throws Exception {
        setField("reviewManagerNamespace", "invalid");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * searchBundleManageNamespace Test initialize(). When scorecardManagerClassName is null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ScorecardManagerClassNameIsNull() throws Exception {
        setField("scorecardManagerClassName", null);
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When scorecardManagerClassName is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ScorecardManagerClassNameIsEmpty() throws Exception {
        setField("scorecardManagerClassName", " ");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When scorecardManagerClassName is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ScorecardManagerClassNameIsInvalid1() throws Exception {
        setField("scorecardManagerClassName", "invalid");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When scorecardManagerClassName is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ScorecardManagerClassNameIsInvalid2() throws Exception {
        setField("scorecardManagerClassName", "java.lang.String");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When scorecardManagerNamespace is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ScorecardManagerNamespaceIsEmpty() throws Exception {
        setField("scorecardManagerNamespace", " ");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When scorecardManagerNamespace is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_ScorecardManagerNamespaceIsInvalid1() throws Exception {
        setField("scorecardManagerNamespace", "invalid");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * searchBundleManageNamespace Test initialize(). When uploadExternalServicesClassName is null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_UploadExternalServicesClassNameIsNull() throws Exception {
        setField("uploadExternalServicesClassName", null);
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When uploadExternalServicesClassName is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_UploadExternalServicesClassNameIsEmpty() throws Exception {
        setField("uploadExternalServicesClassName", " ");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When uploadExternalServicesClassName is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_UploadExternalServicesClassNameIsInvalid1() throws Exception {
        setField("uploadExternalServicesClassName", "invalid");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When uploadExternalServicesClassName is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_UploadExternalServicesClassNameIsInvalid2() throws Exception {
        setField("uploadExternalServicesClassName", "java.lang.String");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When uploadExternalServicesNamespace is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_UploadExternalServicesNamespaceIsEmpty() throws Exception {
        setField("uploadExternalServicesNamespace", " ");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When uploadExternalServicesNamespace is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_UploadExternalServicesNamespaceIsInvalid1() throws Exception {
        setField("uploadExternalServicesNamespace", "invalid");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * searchBundleManageNamespace Test initialize(). When dbConnectionFactoryClassName is null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_DbConnectionFactoryClassNameIsNull() throws Exception {
        setField("dbConnectionFactoryClassName", null);
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When dbConnectionFactoryClassName is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_DbConnectionFactoryClassNameIsEmpty() throws Exception {
        setField("dbConnectionFactoryClassName", " ");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When dbConnectionFactoryClassName is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_DbConnectionFactoryClassNameIsInvalid1() throws Exception {
        setField("dbConnectionFactoryClassName", "invalid");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When dbConnectionFactoryClassName is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_DbConnectionFactoryClassNameIsInvalid2() throws Exception {
        setField("dbConnectionFactoryClassName", "java.lang.String");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When dbConnectionFactoryNamespace is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_DbConnectionFactoryNamespaceIsEmpty() throws Exception {
        setField("dbConnectionFactoryNamespace", " ");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test initialize(). When dbConnectionFactoryNamespace is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize_DbConnectionFactoryNamespaceIsInvalid1() throws Exception {
        setField("dbConnectionFactoryNamespace", "invalid");
        try {
            instance.initialize();
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // OK
        }
    }

    /**
     * Test scheduleSpecificationReview(TCSubject tcSubject, long projectId, Date reviewStartDate). When tcSubject is
     * null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testScheduleSpecificationReview_TCSubjectIsNull() throws Exception {
        try {
            instance.scheduleSpecificationReview(null, 1, new Date());
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test scheduleSpecificationReview(TCSubject tcSubject, long projectId, Date reviewStartDate). When projectId is
     * zero.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testScheduleSpecificationReview_ProjectIdIsZero() throws Exception {
        try {
            instance.scheduleSpecificationReview(subject, 0, new Date());
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test scheduleSpecificationReview(TCSubject tcSubject, long projectId, Date reviewStartDate). When the service has
     * error.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testScheduleSpecificationReview_ServiceError() throws Exception {
        ProjectServices service = EasyMock.createMock(ProjectServices.class);
        service.getFullProjectData(1);
        EasyMock.expectLastCall().andThrow(new ProjectServicesException(""));
        EasyMock.replay(service);

        setField("projectServices", service);

        try {
            instance.scheduleSpecificationReview(subject, 1, new Date());
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceException e) {
            // pass
        }
    }

    /**
     * Test submitSpecificationAsFile(TCSubject tcSubject, long projectId, String filename). When tcSubject is null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmitSpecificationAsFile_TCSubjectIsNull() throws Exception {
        try {
            instance.submitSpecificationAsFile(null, 1, specFile);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test submitSpecificationAsFile(TCSubject tcSubject, long projectId, String filename). When projectId is zero.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmitSpecificationAsFile_TCSubjectIsZero() throws Exception {
        try {
            instance.submitSpecificationAsFile(subject, 0, specFile);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test submitSpecificationAsFile(TCSubject tcSubject, long projectId, String filename). When filename is null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmitSpecificationAsFile_FilenameIsNull() throws Exception {
        try {
            instance.submitSpecificationAsFile(subject, 1, null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test submitSpecificationAsFile(TCSubject tcSubject, long projectId, String filename). When filename is empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmitSpecificationAsFile_FilenameIsEmpty() throws Exception {
        try {
            instance.submitSpecificationAsFile(subject, 1, "  ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test submitSpecificationAsFile(TCSubject tcSubject, long projectId, String filename). When service throw
     * exception.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmitSpecificationAsFile_ServiceError() throws Exception {
        try {
            instance.submitSpecificationAsFile(subject, 111, specFile);
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceException e) {
            // OK
        }
    }

    /**
     * Failure test submitSpecificationAsString(TCSubject tcSubject, long projectId, String content). When tcSubject is
     * null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmitSpecificationAsString_TCSubjectIsNull() throws Exception {
        try {
            instance.submitSpecificationAsString(null, 1, "test");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test submitSpecificationAsString(TCSubject tcSubject, long projectId, String content). When tcSubject is
     * null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmitSpecificationAsString3_ProjectIdIsZero() throws Exception {
        try {
            instance.submitSpecificationAsString(subject, 0, "content");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test submitSpecificationAsString(TCSubject tcSubject, long projectId, String content). When content is
     * null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmitSpecificationAsString3_ContentIsNull() throws Exception {
        try {
            instance.submitSpecificationAsString(subject, 1, null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test submitSpecificationAsString(TCSubject tcSubject, long projectId, String content). When content is
     * empty.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSubmitSpecificationAsString3_ContentIsEmpty() throws Exception {
        try {
            instance.submitSpecificationAsString(subject, 1, "  ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test getSpecificationReview(TCSubject tcSubject, long projectId). When tcSubject is null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetSpecificationReviewStatus_SubjectIsNull() throws Exception {
        try {
            instance.getSpecificationReview(null, 1);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test getSpecificationReview(TCSubject tcSubject, long projectId). When projectId is zero.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetSpecificationReview_ProjectIdIsZero() throws Exception {
        try {
            instance.getSpecificationReview(subject, 0);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test getSpecificationReview(TCSubject tcSubject, long projectId). When connection name is invalid.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetSpecificationReview_InvalidConnection() throws Exception {
        setField("connectionName", "invalid");
        try {
            instance.getSpecificationReview(subject, 1);
            fail("Cannot go here");
        } catch (SpecificationReviewServiceException e) {
            // pass
        }
    }

    /**
     * Failure test getSpecificationReviewStatus(TCSubject tcSubject, long projectId). When subject is null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetSpecificationReviewStatus_TCSubjectIsNull() throws Exception {
        try {
            instance.getSpecificationReviewStatus(null, 1);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test getSpecificationReviewStatus(TCSubject tcSubject, long projectId). When projectId is zero.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetSpecificationReviewStatus_ProjectIdIsZero() throws Exception {
        try {
            instance.getSpecificationReviewStatus(subject, 0);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test getSpecificationReviewStatus(TCSubject tcSubject, long projectId). When projectId is zero.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetSpecificationReviewStatus_ServiceError() throws Exception {
        ProjectServices service = EasyMock.createMock(ProjectServices.class);
        service.getFullProjectData(1);
        EasyMock.expectLastCall().andThrow(new ProjectServicesException(""));
        EasyMock.replay(service);

        setField("projectServices", service);
        try {
            instance.getSpecificationReviewStatus(subject, 1);
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceException e) {
            // OK
        }
    }

    /**
     * Failure test getOpenSpecificationReviewPositions(TCSubject). When subject is null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetOpenSpecificationReviewPositions_SubjectIsNull() throws Exception {
        try {
            instance.getOpenSpecificationReviewPositions(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test getOpenSpecificationReviewPositions(TCSubject). When error occurred when connect to database.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetOpenSpecificationReviewPositions_InvalidConnection() throws Exception {
        setField("connectionName", "unknown");
        try {
            instance.getOpenSpecificationReviewPositions(subject);
            fail("Cannot go here.");
        } catch (SpecificationReviewServiceException e) {
            // OK
        }
    }
}