/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.ejb;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.project.service.ejb.ProjectServicesBean;

import com.topcoder.search.builder.SearchBuilderConfigurationException;

import com.topcoder.security.TCSubject;

import com.topcoder.service.review.specification.SpecificationReview;
import com.topcoder.service.review.specification.SpecificationReviewServiceConfigurationException;
import com.topcoder.service.review.specification.SpecificationReviewServiceException;
import com.topcoder.service.review.specification.SpecificationReviewStatus;

import java.io.File;

import java.lang.reflect.InvocationTargetException;

import java.sql.Connection;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Unit tests for class <code>{@link SpecificationReviewServiceBean}</code>.
 *
 * @author myxgyy
 * @version 1.0
 */
public class SpecificationReviewServiceBeanTests extends BaseTest {
    /**
     * Represents the mock specification submission.
     */
    private static final String MOCK_SPECIFICATION_FILE = "test_files" + File.separatorChar
        + "mock_submission" + File.separatorChar + "mock_rs.rtf";

    /**
     * The <code>{@link SpecificationReviewServiceBean}</code> instance to test.
     */
    private SpecificationReviewServiceBean bean;

    /**
     * The <code>TCSubject</code> instance used for testing.
     */
    private TCSubject tcSubject = new TCSubject(null, 1001L);

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        super.setUp();
        bean = new SpecificationReviewServiceBean();
        setField(SpecificationReviewServiceBean.class, bean, "loggerName",
            "specification_review_service_log");
        setField(SpecificationReviewServiceBean.class, bean, "projectServices",
            new ProjectServicesBean());
        setField(SpecificationReviewServiceBean.class, bean, "mockSubmissionFilePath",
            "test_files/mock_submission/");
        setField(SpecificationReviewServiceBean.class, bean, "mockSubmissionFileName", "mock_rs.rtf");
        setField(SpecificationReviewServiceBean.class, bean, "searchBundleManageNamespace",
            "com.topcoder.search.builder.Upload_Resource_Search");
        setField(SpecificationReviewServiceBean.class, bean, "reviewManagerClassName",
            "com.topcoder.management.review.DefaultReviewManager");
        setField(SpecificationReviewServiceBean.class, bean, "scorecardManagerClassName",
            "com.topcoder.management.scorecard.ScorecardManagerImpl");
        setField(SpecificationReviewServiceBean.class, bean, "uploadExternalServicesClassName",
            "com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices");
        setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryClassName",
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryNamespace",
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        bean.initialize();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests <code>initialize()</code> methods. Log and all managers should be created.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize1() throws Exception {
        assertNotNull("log field should not be null", getField(bean, "log"));
        assertNotNull("uploadManager field should not be null", getField(bean, "uploadManager"));
        assertNotNull("reviewManager field should not be null", getField(bean, "reviewManager"));
        assertNotNull("scorecardManager field should not be null", getField(bean, "scorecardManager"));
        assertNotNull("resourceManager field should not be null", getField(bean, "resourceManager"));
        assertNotNull("uploadExternalServices field should not be null", getField(bean,
            "uploadExternalServices"));
        assertNotNull("dbConnectionFactory field should not be null", getField(bean,
            "dbConnectionFactory"));
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>loggerName</code> field is empty,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize2() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "loggerName", "");
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>connectionName</code> field is empty,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize3() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "connectionName", "");
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>mockSubmissionFileName</code> field is empty,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize4() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "mockSubmissionFileName", "");
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>mockSubmissionFilePath</code> field is empty,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize5() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "mockSubmissionFilePath", "");
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>searchBundleManageNamespace</code> field is empty,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize6() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "searchBundleManageNamespace", "");
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>reviewManagerClassName</code> field is empty,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize7() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "reviewManagerClassName", "");
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>scorecardManagerClassName</code> field is empty,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize8() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "scorecardManagerClassName", "");
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>uploadExternalServicesClassName</code> field is empty,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize9() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "uploadExternalServicesClassName", "");
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>dbConnectionFactoryClassName</code> field is empty,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize10() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryClassName", "");
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>searchBundleManageNamespace</code> is unknown,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize11() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "searchBundleManageNamespace",
                "invalid");
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof SearchBuilderConfigurationException);
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>searchBundleManageNamespace</code> is null,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize12() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "searchBundleManageNamespace",
                null);
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>searchBundleManageNamespace</code> used to create searchBundleManager
     * doesn't contain SearchBundle of required names to create uploadManager,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize13() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "searchBundleManageNamespace",
                "com.topcoder.management.review.persistence.InformixSearchBundle");
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>searchBundleManageNamespace</code> used to create searchBundleManager
     * doesn't contain SearchBundle named &quot;Resource Search Bundle&quot; to create
     * resourceManager, <code>SpecificationReviewServiceConfigurationException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize14() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "searchBundleManageNamespace",
                "com.topcoder.search.builder.failure");
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>projectServices</code> is null,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize15() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "projectServices", null);
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>mockSubmissionFileName</code> is null,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize16() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "mockSubmissionFileName", null);
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>mockSubmissionFilePath</code> is null,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize17() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "mockSubmissionFilePath", null);
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>searchBundleManageNamespace</code> is null,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize18() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "searchBundleManageNamespace", null);
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>reviewManagerClassName</code> is null,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize19() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "reviewManagerClassName", null);
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>scorecardManagerClassName</code> is null,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize20() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "scorecardManagerClassName", null);
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>uploadExternalServicesClassName</code> is null,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize21() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "uploadExternalServicesClassName", null);
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <code>initialize()</code> methods.
     * </p>
     * <p>
     * The <code>dbConnectionFactoryClassName</code> is null,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize22() throws Exception {
        try {
            setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryClassName", null);
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>reviewManagerClassName</code> field is not found,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize23() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "reviewManagerClassName", "not found");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof ClassNotFoundException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>reviewManagerClassName</code> field can not cast
     * to <code>ReviewManager</code>,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize24() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "reviewManagerClassName",
            "java.lang.Exception");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof ClassCastException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>reviewManagerClassName</code> field has no
     * constructor with <code>String</code> argument,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize25() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "reviewManagerClassName",
            "com.topcoder.service.review.specification.SpecificationReview");
        setField(SpecificationReviewServiceBean.class, bean, "reviewManagerNamespace",
            "com.topcoder.service.review.specification.SpecificationReview");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof NoSuchMethodException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>reviewManagerClassName</code> field has private
     * constructor, <code>SpecificationReviewServiceConfigurationException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize26() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "reviewManagerClassName",
            "com.topcoder.util.errorhandling.ExceptionUtils");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof IllegalAccessException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>reviewManagerClassName</code> field is an abstract
     * class, <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize27() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "reviewManagerClassName",
            "java.text.Format");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof InstantiationException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * Calling constructor of class represented by <code>reviewManagerClassName</code>
     * with specified argument failed,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize28() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "reviewManagerNamespace",
            "com.topcoder.invalid.invalid");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof InvocationTargetException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>scorecardManagerClassName</code> field is not
     * found, <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize29() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "scorecardManagerClassName", "not found");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof ClassNotFoundException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>scorecardManagerClassName</code> field can not
     * cast to <code>ReviewManager</code>,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize30() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "scorecardManagerClassName",
            "java.lang.Exception");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof ClassCastException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>scorecardManagerClassName</code> field has no
     * constructor with <code>String</code> argument,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize31() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "scorecardManagerClassName",
            "com.topcoder.service.review.specification.SpecificationReview");
        setField(SpecificationReviewServiceBean.class, bean, "scorecardManagerNamespace",
            "com.topcoder.service.review.specification.SpecificationReview");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof NoSuchMethodException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>scorecardManagerClassName</code> field has private
     * constructor, <code>SpecificationReviewServiceConfigurationException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize32() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "scorecardManagerClassName",
            "com.topcoder.util.errorhandling.ExceptionUtils");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof IllegalAccessException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>scorecardManagerClassName</code> field is an
     * abstract class, <code>SpecificationReviewServiceConfigurationException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize33() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "scorecardManagerClassName",
            "java.text.Format");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof InstantiationException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * Calling constructor of class represented by <code>scorecardManagerClassName</code>
     * with specified argument failed,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize34() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "scorecardManagerNamespace",
            "com.topcoder.invalid.invalid");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof InvocationTargetException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>uploadExternalServicesClassName</code> field is
     * not found, <code>SpecificationReviewServiceConfigurationException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize35() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "uploadExternalServicesClassName",
            "not found");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof ClassNotFoundException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>uploadExternalServicesClassName</code> field can
     * not cast to <code>ReviewManager</code>,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize36() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "uploadExternalServicesClassName",
            "java.lang.Exception");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof ClassCastException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>uploadExternalServicesClassName</code> field has
     * no constructor with <code>String</code> argument,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize37() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "uploadExternalServicesClassName",
            "com.topcoder.service.review.specification.SpecificationReview");
        setField(SpecificationReviewServiceBean.class, bean, "uploadExternalServicesNamespace",
            "com.topcoder.service.review.specification.SpecificationReview");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof NoSuchMethodException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>uploadExternalServicesClassName</code> field has
     * private constructor, <code>SpecificationReviewServiceConfigurationException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize38() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "uploadExternalServicesClassName",
            "com.topcoder.util.errorhandling.ExceptionUtils");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof IllegalAccessException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>uploadExternalServicesClassName</code> field is an
     * abstract class, <code>SpecificationReviewServiceConfigurationException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize39() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "uploadExternalServicesClassName",
            "java.text.Format");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof InstantiationException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * Calling constructor of class represented by
     * <code>uploadExternalServicesClassName</code> with specified argument failed,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize40() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "uploadExternalServicesNamespace",
            "com.topcoder.invalid.invalid");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof InvocationTargetException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>dbConnectionFactoryClassName</code> field is not
     * found, <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize41() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryClassName", "not found");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof ClassNotFoundException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>dbConnectionFactoryClassName</code> field can not
     * cast to <code>ReviewManager</code>,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize42() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryClassName",
            "java.lang.Exception");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof ClassCastException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>dbConnectionFactoryClassName</code> field has no
     * constructor with <code>String</code> argument,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize43() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryClassName",
            "com.topcoder.service.review.specification.SpecificationReview");
        setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryNamespace",
            "com.topcoder.service.review.specification.SpecificationReview");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof NoSuchMethodException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>dbConnectionFactoryClassName</code> field has
     * private constructor, <code>SpecificationReviewServiceConfigurationException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize44() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryClassName",
            "com.topcoder.util.errorhandling.ExceptionUtils");
        setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryNamespace", null);

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof IllegalAccessException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * The class represented by <code>dbConnectionFactoryClassName</code> field is an
     * abstract class, <code>SpecificationReviewServiceConfigurationException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize45() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryClassName",
            "java.text.Format");
        setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryNamespace", null);

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof InstantiationException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link SpecificationReviewServiceBean#initialize()}
     * method.
     * </p>
     * <p>
     * Calling constructor of class represented by
     * <code>dbConnectionFactoryClassName</code> with specified argument failed,
     * <code>SpecificationReviewServiceConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testInitialize46() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "dbConnectionFactoryNamespace",
            "com.topcoder.invalid.invalid");

        try {
            bean.initialize();
            fail("should have thrown SpecificationReviewServiceConfigurationException");
        } catch (SpecificationReviewServiceConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof InvocationTargetException);
        }
    }

    /**
     * <p>
     * Failure test case {@link
     * SpecificationReviewServiceBean#getSpecificationReview(TCSubject, long)} method.
     * </p>
     * <p>
     * The underlying database does not contains required tables,
     * <code>SpecificationReviewServiceException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReview() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "connectionName", "failure_connection");
        try {
            bean.getSpecificationReview(tcSubject, 1);
            fail("should have thrown SpecificationReviewServiceException");
        } catch (SpecificationReviewServiceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case {@link
     * SpecificationReviewServiceBean#getOpenSpecificationReviewPositions(TCSubject)}
     * method.
     * </p>
     * <p>
     * The underlying database does not have required tables,
     * <code>SpecificationReviewServiceException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getOpenSpecificationReviewPositions() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "connectionName", "failure_connection");
        try {
            bean.getOpenSpecificationReviewPositions(tcSubject);
            fail("should have thrown SpecificationReviewServiceException");
        } catch (SpecificationReviewServiceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#scheduleSpecificationReview(TCSubject,
     * long, Date)} method.
     * </p>
     * <p>
     * Tests with review start date is past date. Verify the specification review has been
     * scheduled.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_scheduleSpecificationReview1() throws Exception {
        Date date = new Date(System.currentTimeMillis() - 10000);
        bean.scheduleSpecificationReview(tcSubject, 1, date);

        // verify the mock submission content
        File uploaded = new File(UPLOAD_DIRECTORY).listFiles()[0];
        assertEquals("Failed to submit specification", new File(MOCK_SPECIFICATION_FILE).length(),
            uploaded.length());
        verifyPhase(date, true);
    }

    /**
     * Verify the project has been updated by phase manager, and phase status should be scheduled.
     *
     * @param date the given start date.
     * @param before whether the start date is past or future.
     */
    private void verifyPhase(Date date, boolean before) {
        // get the updated pahses
        Map<String, Project> phases = MockPhaseManager.getPhases();
        Project project = phases.get("1001");

        // verify the start date
        if (before) {
            if (date != null) {
                assertTrue("start date wrong",
                    project.getStartDate().getTime() > date.getTime());
            } else {
                // less than 10 seconds
                assertTrue("start date wrong",
                    System.currentTimeMillis() - project.getStartDate().getTime() < 10000);
            }
        } else {
            assertTrue("start date wrong",
                project.getStartDate().getTime() == date.getTime());
        }

        // find specification submission phase
        Phase specificationSubmissionPhase = null;
        for (Phase phase : project.getAllPhases()) {
            PhaseType phaseType = phase.getPhaseType();
            if ("Specification Submission".equals(phaseType.getName())) {
                specificationSubmissionPhase = phase;
                break;
            }
        }
        assertNotNull("should get the specification submission phase", specificationSubmissionPhase);
        assertTrue("status should be SCHEDULED", PhaseStatus.SCHEDULED == specificationSubmissionPhase
            .getPhaseStatus());
    }

    /**
     * <p>
     * Accuracy test case for {@link
     * SpecificationReviewServiceBean#scheduleSpecificationReview(TCSubject, long, Date)}
     * method.
     * </p>
     * <p>
     * Tests with null review start date. Verify the specification review has been
     * scheduled.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_scheduleSpecificationReview2() throws Exception {
        bean.scheduleSpecificationReview(tcSubject, 1, null);

        // verify the mock submission content
        File uploaded = new File(UPLOAD_DIRECTORY).listFiles()[0];
        assertEquals("Failed to submit specification", new File(MOCK_SPECIFICATION_FILE).length(),
            uploaded.length());
        verifyPhase(null, true);
    }

    /**
     * <p>
     * Accuracy test case for {@link
     * SpecificationReviewServiceBean#scheduleSpecificationReview(TCSubject, long, Date)}
     * method.
     * </p>
     * <p>
     * Tests with review start date is future date. Verify the specification review has
     * been scheduled.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_scheduleSpecificationReview3() throws Exception {
        Date date = new Date(System.currentTimeMillis() + 10000);
        bean.scheduleSpecificationReview(tcSubject, 1, date);

        // verify the mock submission content
        File uploaded = new File(UPLOAD_DIRECTORY).listFiles()[0];
        assertEquals("Failed to submit specification", new File(MOCK_SPECIFICATION_FILE).length(),
            uploaded.length());
        verifyPhase(date, false);
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#scheduleSpecificationReview(TCSubject,
     * long, Date)} method.
     * </p>
     * <p>
     * The tcSubject argument is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_scheduleSpecificationReview4() throws Exception {
        try {
            bean.scheduleSpecificationReview(null, 1, new Date());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#scheduleSpecificationReview(TCSubject,
     * long, Date)} method.
     * </p>
     * <p>
     * The projectId argument is negative, <code>IllegalArgumentException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_scheduleSpecificationReview5() throws Exception {
        try {
            bean.scheduleSpecificationReview(tcSubject, -1, new Date());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#scheduleSpecificationReview(TCSubject,
     * long, Date)} method.
     * </p>
     * <p>
     * The projectId argument is zero, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_scheduleSpecificationReview6() throws Exception {
        try {
            bean.scheduleSpecificationReview(tcSubject, 0, new Date());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link
     * SpecificationReviewServiceBean#scheduleSpecificationReview(TCSubject, long, Date)}
     * method.
     * </p>
     * <p>
     * There is no phase with name &quot;Specification Submission&quot;,
     * <code>SpecificationReviewServiceException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_scheduleSpecificationReview7() throws Exception {
        try {
            bean.scheduleSpecificationReview(tcSubject, 5, new Date());
            fail("should have thrown SpecificationReviewServiceException");
        } catch (SpecificationReviewServiceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link
     * SpecificationReviewServiceBean#scheduleSpecificationReview(TCSubject, long, Date)}
     * method.
     * </p>
     * <p>
     * The given projectId will cause the mock phase manager throw exception,
     * <code>SpecificationReviewServiceException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_scheduleSpecificationReview8() throws Exception {
        try {
            bean.scheduleSpecificationReview(tcSubject, 1000, new Date());
            fail("should have thrown SpecificationReviewServiceException");
        } catch (SpecificationReviewServiceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for {@link
     * SpecificationReviewServiceBean#scheduleSpecificationReview(TCSubject, long, Date)}
     * method.
     * </p>
     * <p>
     * The given userId in tcSubject will cause mock phase manage to throw exception when
     * updating phases, <code>SpecificationReviewServiceException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_scheduleSpecificationReview9() throws Exception {
        tcSubject.setUserId(9999);

        try {
            bean.scheduleSpecificationReview(tcSubject, 1, new Date());
            fail("should have thrown SpecificationReviewServiceException");
        } catch (SpecificationReviewServiceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsString(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * Verify the specification file has been uploaded to specified path.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsString1() throws Exception {
        bean.submitSpecificationAsString(tcSubject, 1, "some content");

        // verify the content
        File uploaded = new File(UPLOAD_DIRECTORY).listFiles()[0];
        assertEquals("Failed to submit specification", "some content".getBytes().length, uploaded
            .length());
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsString(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The tcSubject argument is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsString2() throws Exception {
        try {
            bean.submitSpecificationAsString(null, 1, "content");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsString(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The projectId argument is negative, <code>IllegalArgumentException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsString3() throws Exception {
        try {
            bean.submitSpecificationAsString(tcSubject, -1, "content");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsString(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The projectId argument is zero, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsString4() throws Exception {
        try {
            bean.submitSpecificationAsString(tcSubject, 0, "content");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // check log
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsString(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The filename argument is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsString5() throws Exception {
        try {
            bean.submitSpecificationAsString(tcSubject, 1, null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // check log
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsString(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The filename argument is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsString6() throws Exception {
        try {
            bean.submitSpecificationAsString(tcSubject, 1, "");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsString(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The filename argument is trimmed empty, <code>IllegalArgumentException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsString7() throws Exception {
        try {
            bean.submitSpecificationAsString(tcSubject, 1, "   ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsFile(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * Verify the specification file has been uploaded to specified path.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsFile1() throws Exception {
        bean.submitSpecificationAsFile(tcSubject, 1, MOCK_SPECIFICATION_FILE);

        // verify the content
        File uploaded = new File(UPLOAD_DIRECTORY).listFiles()[0];
        assertEquals("Failed to submit specification", new File(MOCK_SPECIFICATION_FILE).length(),
            uploaded.length());
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsFile(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The tcSubject argument is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsFile2() throws Exception {
        try {
            bean.submitSpecificationAsFile(null, 1, MOCK_SPECIFICATION_FILE);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsFile(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The projectId argument is negative, <code>IllegalArgumentException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsFile3() throws Exception {
        try {
            bean.submitSpecificationAsFile(tcSubject, -1, MOCK_SPECIFICATION_FILE);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsFile(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The projectId argument is zero, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsFile4() throws Exception {
        try {
            bean.submitSpecificationAsFile(tcSubject, 0, MOCK_SPECIFICATION_FILE);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsFile(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The filename argument is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsFile5() throws Exception {
        try {
            bean.submitSpecificationAsFile(tcSubject, 1, null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsFile(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The filename argument is empty, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsFile6() throws Exception {
        try {
            bean.submitSpecificationAsFile(tcSubject, 1, "");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsFile(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The filename argument is trimmed empty, <code>IllegalArgumentException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsFile7() throws Exception {
        try {
            bean.submitSpecificationAsFile(tcSubject, 1, "   ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // check log
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#submitSpecificationAsFile(TCSubject,
     * long, String)} method.
     * </p>
     * <p>
     * The underlying upload service will throw exception,
     * <code>SpecificationReviewServiceException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_submitSpecificationAsFile8() throws Exception {
        try {
            bean.submitSpecificationAsFile(tcSubject, 1000, MOCK_SPECIFICATION_FILE);
            fail("should have thrown SpecificationReviewServiceException");
        } catch (SpecificationReviewServiceException e) {
            // check log
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReview(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * There is no specification submission exists, <code>null</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReview1() throws Exception {
        SpecificationReview review = bean.getSpecificationReview(tcSubject, 1001L);
        assertNull("should be null", review);
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReview(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * There is no specification review exists, <code>null</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReview2() throws Exception {
        Connection conn = getConnection();
        Project project = setupPhasesForSpec(conn);
        // insert submission to database
        setupSubmissionForReview(conn);

        SpecificationReview review = bean.getSpecificationReview(tcSubject, project.getId());
        assertNull("should be null", review);
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReview(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * Both review and scorecard exist, SpecificationReview instance with review and
     * scorecard should be retrieved.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReview3() throws Exception {
        Connection conn = getConnection();
        Project project = setupPhasesForSpec(conn);
        setupSubmissionForReview(conn);

        // insert a review
        Resource reviewer = createResource(5, 102L, 1, 18);
        super.insertResources(conn, new Resource[] {reviewer});

        // insert review and scorecard
        Scorecard scorecard = createScorecard(1, 1, 2, 6, "name", "1.0", 75.0f, 100.0f);
        Review review = createReview(11, reviewer.getId(), 1, scorecard.getId(), false, 90.0f);
        this.insertScorecards(conn, new Scorecard[] {scorecard});
        this.insertReviews(conn, new Review[] {review});

        SpecificationReview result = bean.getSpecificationReview(tcSubject, project.getId());

        // verify scorecard
        assertEquals("scorecard max score should be 100.0f", 100, 0f, result.getScorecard()
            .getMaxScore());
        assertEquals("scorecard min score be 75.0f", 75.0f, result.getScorecard().getMinScore());
        assertEquals("scorecard name should be 'name'", "name", result.getScorecard().getName());
        assertEquals("scorecard version should be '1.0'", "1.0", result.getScorecard().getVersion());

        // verify review
        assertEquals("review score should be 90.0f", 90.0f, result.getReview().getScore());
        assertFalse("review is not committed", result.getReview().isCommitted());
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReview(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * The tcSubject argument is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReview4() throws Exception {
        try {
            bean.getSpecificationReview(null, 1);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReview(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * The projectId argument is negative, <code>IllegalArgumentException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReview5() throws Exception {
        try {
            bean.getSpecificationReview(tcSubject, -1);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReview(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * The projectId argument is zero, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReview6() throws Exception {
        try {
            bean.getSpecificationReview(tcSubject, 0);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case {@link
     * SpecificationReviewServiceBean#getSpecificationReview(TCSubject, long)} method.
     * </p>
     * <p>
     * Mulitiple specification submissions exist,
     * <code>SpecificationReviewServiceException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReview8() throws Exception {
        Connection conn = getConnection();
        Project project = setupPhasesForSpec(conn);

        // insert two submission
        Resource resource1 = createResource(4, 101L, 1, 17);
        Resource resource2 = createResource(5, 101L, 1, 17);
        insertResources(conn, new Resource[] {resource1, resource2});

        Upload upload1 = createUpload(1, project.getId(), 4, 1, 1, "parameter");
        Upload upload2 = createUpload(2, project.getId(), 5, 1, 1, "parameter");
        insertUploads(conn, new Upload[] {upload1, upload2});

        Submission submission1 = createSubmission(1, upload1.getId(), 1, 2);
        Submission submission2 = createSubmission(2, upload2.getId(), 1, 2);
        insertSubmissions(conn, new Submission[] {submission1, submission2});

        try {
            bean.getSpecificationReview(tcSubject, project.getId());
            fail("should have thrown SpecificationReviewServiceException");
        } catch (SpecificationReviewServiceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case {@link
     * SpecificationReviewServiceBean#getSpecificationReview(TCSubject, long)} method.
     * </p>
     * <p>
     * Mulitiple specification reviews exist,
     * <code>SpecificationReviewServiceException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReview9() throws Exception {
        Connection conn = getConnection();
        Project project = setupPhasesForSpec(conn);
        setupSubmissionForReview(conn);

        // insert a review
        Resource reviewer1 = createResource(5, 102L, 1, 18);
        Resource reviewer2 = createResource(6, 102L, 1, 18);
        super.insertResources(conn, new Resource[] {reviewer1, reviewer2});

        // insert review and scorecard
        Scorecard scorecard = createScorecard(1, 1, 2, 6, "name", "1.0", 75.0f, 100.0f);
        Review review1 = createReview(11, reviewer1.getId(), 1, scorecard.getId(), false, 90.0f);
        Review review2 = createReview(12, reviewer2.getId(), 1, scorecard.getId(), true, 90.0f);
        this.insertScorecards(conn, new Scorecard[] {scorecard});
        this.insertReviews(conn, new Review[] {review1, review2});

        try {
            bean.getSpecificationReview(tcSubject, project.getId());
            fail("should have thrown SpecificationReviewServiceException");
        } catch (SpecificationReviewServiceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case {@link
     * SpecificationReviewServiceBean#getSpecificationReview(TCSubject, long)} method.
     * </p>
     * <p>
     * Fails to create database connection,
     * <code>SpecificationReviewServiceException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReview10() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "connectionName", "unknown");

        try {
            bean.getSpecificationReview(tcSubject, 1);
            fail("should have thrown SpecificationReviewServiceException");
        } catch (SpecificationReviewServiceException e) {
            assertTrue("check inner cause", e.getCause() instanceof DBConnectionException);
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReviewStatus(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * The phase with open status is specification submission, <code>{@link
     * SpecificationReviewStatus#WAITING_FOR_FIXES}</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReviewStatus1() throws Exception {
        SpecificationReviewStatus status = bean.getSpecificationReviewStatus(tcSubject, 1L);
        assertTrue("status should be 'WAITING_FOR_FIXES'",
            SpecificationReviewStatus.WAITING_FOR_FIXES == status);
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReviewStatus(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * The phase with open status is specification review, <code>{@link
     * SpecificationReviewStatus#PENDING_REVIEW}</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReviewStatus2() throws Exception {
        SpecificationReviewStatus status = bean.getSpecificationReviewStatus(tcSubject, 2L);
        assertTrue("status should be 'PENDING_REVIEW'",
            SpecificationReviewStatus.PENDING_REVIEW == status);
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReviewStatus(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * The phase with open status is neither specification submission nor specification
     * review, <code>null</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReviewStatus3() throws Exception {
        SpecificationReviewStatus status = bean.getSpecificationReviewStatus(tcSubject, 3L);
        assertNull("status should be null", status);
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReviewStatus(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * There is no phase with OPEN status, <code>null</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReviewStatus4() throws Exception {
        SpecificationReviewStatus status = bean.getSpecificationReviewStatus(tcSubject, 4L);
        assertNull("status should be null", status);
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReviewStatus(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * The tcSubject argument is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReviewStatus5() throws Exception {
        try {
            bean.getSpecificationReviewStatus(null, 1);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // check log
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReviewStatus(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * The projectId argument is negative, <code>IllegalArgumentException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReviewStatus6() throws Exception {
        try {
            bean.getSpecificationReviewStatus(tcSubject, -1);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // check log
        }
    }

    /**
     * <p>
     * Tests {@link SpecificationReviewServiceBean#getSpecificationReviewStatus(TCSubject,
     * long)} method.
     * </p>
     * <p>
     * The projectId argument is zero, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getSpecificationReviewStatus7() throws Exception {
        try {
            bean.getSpecificationReviewStatus(tcSubject, 0);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // check log
        }
    }

    /**
     * <p>
     * Tests {@link
     * SpecificationReviewServiceBean#getOpenSpecificationReviewPositions(TCSubject)}
     * method.
     * </p>
     * <p>
     * All returned projects have no specification reviewer set.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getOpenSpecificationReviewPositions1() throws Exception {
        List<Long> result = bean.getOpenSpecificationReviewPositions(tcSubject);
        assertEquals("should have three ids", 3, result.size());
        assertTrue("should contain id 1", result.contains(new Long(1)));
        assertTrue("should contain id 2", result.contains(new Long(2)));
        assertTrue("should contain id 3", result.contains(new Long(3)));
    }

    /**
     * <p>
     * Tests {@link
     * SpecificationReviewServiceBean#getOpenSpecificationReviewPositions(TCSubject)}
     * method.
     * </p>
     * <p>
     * Project with id one has specification reviewer set, the other two project ids
     * should retrieved.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getOpenSpecificationReviewPositions2() throws Exception {
        Connection conn = getConnection();
        setupPhasesForSpec(conn);
        setupSubmissionForReview(conn);

        // insert a review
        Resource reviewer = createResource(5, 102L, 1, 18);
        super.insertResources(conn, new Resource[] {reviewer});
        insertResourceInfo(conn, reviewer.getId(), 1, "wishingbone");

        List<Long> result = bean.getOpenSpecificationReviewPositions(tcSubject);
        assertEquals("should have two ids", 2, result.size());
        assertTrue("should contain id 2", result.contains(new Long(2)));
        assertTrue("should contain id 3", result.contains(new Long(3)));
    }

    /**
     * <p>
     * Tests {@link
     * SpecificationReviewServiceBean#getOpenSpecificationReviewPositions(TCSubject)}
     * method.
     * </p>
     * <p>
     * The given argument is null, <code>IllegalArgumentException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getOpenSpecificationReviewPositions3() throws Exception {
        try {
            bean.getOpenSpecificationReviewPositions(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests {@link
     * SpecificationReviewServiceBean#getOpenSpecificationReviewPositions(TCSubject)}
     * method.
     * </p>
     * <p>
     * The connection name is unknown, <code>SpecificationReviewServiceException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getOpenSpecificationReviewPositions4() throws Exception {
        setField(SpecificationReviewServiceBean.class, bean, "connectionName", "unknown");
        try {
            bean.getOpenSpecificationReviewPositions(tcSubject);
            fail("should have thrown SpecificationReviewServiceException");
        } catch (SpecificationReviewServiceException e) {
            assertTrue("check inner cause", e.getCause() instanceof DBConnectionException);
        }
    }
}
