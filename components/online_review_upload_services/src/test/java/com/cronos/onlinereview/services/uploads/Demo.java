/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices;
import com.cronos.onlinereview.services.uploads.impl.DefaultUploadServices;

/**
 * <p>
 * Component demo showing the usage of the component.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * <p>
     * Integrates all tests in this class.
     * </p>
     *
     * @return Test suite of all tests of <code>Demo</code>.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfigs("config.xml");
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
        TestHelper.releaseConfigs();
        clearFiles();
    }

    /**
     * A typical usage scenario involves the full use of the methods of internal interface, for example from a Web
     * application.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDemo1() throws Exception {
        // Create the implementation
        UploadServices services = new DefaultUploadServices();
        // Implementation created

        // In this demo it will show the different phases of a project
        // The project has the id=20, the user has the id=600.
        // The names of submission will b e different depend from phase

        // upload the submission
        long idSubmission = services.uploadSubmission(30, 600, "submissionMyComponent");
        // submission uploaded and the id of submission is returned

        // Upload the final fixe
        long idFinalFix = services.uploadFinalFix(30, 600, "finalFixMyComponent");
        // final fix uploaded and the id of final fix is returned

        // Upload the test case
        long idTestCases = services.uploadTestCases(30, 600, "testCaseMyComponent");
        // test cases uploaded and the id of test cases is returned

        // change the status of submission previous uploaded to the status of id=1
        services.setSubmissionStatus(idSubmission, 1, "" + 30);
        // the submission status has changed

    }

    /**
     * The typical usage of Axis is to generate the WSDL with Java2WSDL library. After this the WSDL will be
     * generated and published. The client which will want use the web services will use the Axis client. The Axis
     * client is generated with WSDL2Java library. At this point the interface equivalent to UploadExternalServices
     * will be generate and the client will use the web services without explicit SOAP call, all mechanism will be
     * completely transparent.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDemo2() throws Exception {
        // Create the implementation
        UploadExternalServices services = new DefaultUploadExternalServices();
        // Implementation created

        // In this demo it will show the different phases of a project
        // The project has the id=20, the user has the id=600.
        // The names of submission will b e different depend from phase

        // Create the DataHandler
        FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
        DataHandler dataHandler = new DataHandler(dataSource);
        // DataHandler created

        // upload the submission
        long idSubmission = services.uploadSubmission(30, 600, "submissionMyComponent", dataHandler);
        // submission uploaded and the id of submission is returned

        // upload the final fix
        long idFinalFix = services.uploadFinalFix(30, 600, "finalFixMyComponent", dataHandler);
        // final fix uploaded and the id of final fix is returned

        // upload the test cases
        long idTestCases = services.uploadTestCases(30, 100, "testCaseMyComponent", dataHandler);
        // test cases uploaded and the id of test cases is returned

        // change the status of submission previous uploaded to the status of id=1
        services.setSubmissionStatus(idSubmission, 1, "" + 30);
        // the submission status has changed
    }

    /**
     * Normal SOAP Call.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDemo3() throws Exception {
        Long projectId = new Long(TestHelper.PROJECT_ID);
        Long userId = new Long(TestHelper.USER_ID);
        FileDataSource dataSource = new FileDataSource(TestHelper.TEST_FILES + "sample.jar");
        DataHandler testCase = new DataHandler(dataSource);

        // Create the service
        Service service = new Service();

        // Create the caller and set the endpoint
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(TestHelper.END_POINT));

        // Set the operation
        call.setOperationName(new QName("http://onlinereview.topcoder.com", "uploadTestCases"));

        QName qName = new QName("DataHandler");
        call.addParameter("op1", XMLType.XSD_LONG, ParameterMode.IN);
        call.addParameter("op2", XMLType.XSD_LONG, ParameterMode.IN);
        call.addParameter("op3", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter(qName, XMLType.MIME_DATA_HANDLER, ParameterMode.IN);

        call.setReturnType(XMLType.XSD_LONG);

        // Invoke the webservice.
        long id = (Long) call.invoke(new Object[] {projectId, userId, "sample_accuracy.jar", testCase });
    }

    /**
     * Clear all the files created using tesing.
     */
    private void clearFiles() {
        File file = new File("test_files/upload");
        File[] files = file.listFiles();
        for (File delFile : files) {
            delFile.delete();
        }
    }
}
