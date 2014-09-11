package com.cronos.onlinereview.services.uploads;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

/**
 * This class is a sample client which demonstrates the web service method invocation using a SOAP call.
 * 
 * @author evilisneo
 * @version 1.0
 */
public class UploadServiceClient {
    /**
     * The end location of the SOAP call, which contains the method to be executed.
     */
    private final static String END_POINT = "http://63.118.154.189/review/services/UploadService";

    /**
     * Uploads the submission using the SOAP call.
     * 
     * @param projectId
     *            the project id
     * @param ownerId
     *            the owner/user id
     * @param filename
     *            the file name of the submission
     * @throws ServiceException
     *             if any while creating the SOAP call.
     * @throws MalformedURLException
     *             if any while creating the SOAP call.
     * @throws RemoteException
     *             if any while executing.
     */
    public void uploadSubmission(long projectId, long ownerId, String filename) throws ServiceException,
            MalformedURLException, RemoteException {

        // Create the data for the attached file.
        DataHandler dhSource = new DataHandler(new FileDataSource(filename));

        Service service = new Service();
        Call call = (Call) service.createCall();

        call.setTargetEndpointAddress(new URL(END_POINT));
        QName qnameAttachment = new QName("urn:EchoAttachmentsService", "DataHandler");

        call.setOperationName(new QName("urn:UploadService", "uploadSubmission"));
        call.addParameter("projectId", XMLType.XSD_LONG, ParameterMode.IN);
        call.addParameter("ownerId", XMLType.XSD_LONG, ParameterMode.IN);
        call.addParameter("filename", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter(qnameAttachment, XMLType.MIME_DATA_HANDLER, ParameterMode.IN); // Add the file.
        call.setReturnType(XMLType.XSD_LONG);
        
        // call.
        try {
            Object ret = call.invoke(new Object[] {new Long(projectId), new Long(ownerId), filename, dhSource});
            System.out.println("return: " + ret);
        } catch (RemoteException e) {
            System.out.println("RE Class Name: " + e.getClass().getSimpleName());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Adds the given user as a submitter to the given project id using the SOAP call.
     * 
     * @param projectId
     *            the project id
     * @param userId
     *            the user id
     * @throws ServiceException
     *             if any while creating the SOAP call.
     * @throws MalformedURLException
     *             if any while creating the SOAP call.
     * @throws RemoteException
     *             if any while executing.
     */
    public void addSubmitter(long projectId, long userId) throws ServiceException,
            MalformedURLException, RemoteException {

        Service service = new Service();
        Call call = (Call) service.createCall();

        call.setTargetEndpointAddress(new URL(END_POINT));

        call.setOperationName(new QName("urn:UploadService", "addSubmitter"));
        call.addParameter("projectId", XMLType.XSD_LONG, ParameterMode.IN);
        call.addParameter("userId", XMLType.XSD_LONG, ParameterMode.IN);
        call.setReturnType(XMLType.XSD_LONG);
        
        // call.
        try {
            Object ret = call.invoke(new Object[] {new Long(projectId), new Long(userId)});
            System.out.println("return: " + ret);
        } catch (RemoteException e) {
            System.out.println("RE Class Name: " + e.getClass().getSimpleName());
            e.printStackTrace();
            throw e;
        }
    }
    /**
     * The starter.
     * 
     * @param args
     *            not used.
     */
    public static void main(String[] args) {
        UploadServiceClient client = new UploadServiceClient();
        try {
            client.uploadSubmission(38101922, 157409, "sample.jar");
            client.addSubmitter(38101922, 101431);
            System.out.println("Second run. This will return the previously entered resource id.");
            client.addSubmitter(38101922, 101431);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
