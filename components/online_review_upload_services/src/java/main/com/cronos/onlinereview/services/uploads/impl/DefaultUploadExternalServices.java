/*
 * Copyright (C) 2007-2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.impl;

import com.cronos.onlinereview.services.uploads.ConfigurationException;
import com.cronos.onlinereview.services.uploads.InvalidProjectException;
import com.cronos.onlinereview.services.uploads.InvalidProjectPhaseException;
import com.cronos.onlinereview.services.uploads.InvalidSubmissionException;
import com.cronos.onlinereview.services.uploads.InvalidSubmissionStatusException;
import com.cronos.onlinereview.services.uploads.InvalidUserException;
import com.cronos.onlinereview.services.uploads.PersistenceException;
import com.cronos.onlinereview.services.uploads.UploadExternalServices;
import com.cronos.onlinereview.services.uploads.UploadServices;
import com.cronos.onlinereview.services.uploads.UploadServicesException;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.resource.Resource;
import com.topcoder.util.generator.guid.UUID;
import com.topcoder.util.generator.guid.UUIDType;
import com.topcoder.util.generator.guid.UUIDUtility;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.LogFactory;

import javax.activation.DataHandler;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.Set;

/**
 * <p>
 * This is the default implementation of <code>UploadExternalServices</code> interface. For all upload* methods
 * it saves the file into fileStorageLocation and then delegates to UploadServices implementation. The
 * setSubmission delegate directly to <code>UploadServices</code> instance.
 * </p>
 * <p>
 * A sample configuration file that can be used is given below.
 * 
 * <pre>
 *  &lt;Config name=&quot;com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices&quot;&gt;
 *      &lt;Property name=&quot;objectFactoryNamespace&quot;&gt;
 *          &lt;Value&gt;myObjectFactory&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;uploadServicesIdentifier&quot;&gt;
 *          &lt;Value&gt;uploadServices&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;filenamePattern&quot;&gt;
 *          &lt;Value&gt;submission-{0}-{1}&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;fileStorageLocation&quot;&gt;
 *          &lt;Value&gt;test_files/upload&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Config&gt;
 * </pre>
 * </p>
 *
 * <p>
 * Changes in version 1.1: Added
 * <code>{@link DefaultUploadExternalServices#uploadSpecification(long, long, String, DataHandler)}</code> method.
 * </p>
 * <p>
 * Version 1.1.1 (Manage Copilot Postings Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #addReviewer(long, long)} method.</li>
 *   </ol>
 * </p>
 * <p>
 * Thread safe: The thread safety is completely relied to the uploadServices implementation because it's impossible
 * to change the other variables
 * </p>
 *
 * @author fabrizyo, saarixx, cyberjag, TCSDEVELOPER
 * @version 1.1.1
 * @since 1.0
 */
public class DefaultUploadExternalServices implements UploadExternalServices {

    /**
     * <p>
     * Represents the default namespace for this class used to load the configuration with
     * <code>ConfigManager</code>.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = DefaultUploadExternalServices.class.getName();

    /**
     * <p>
     * Represents the default pattern to construct the entire filename to store the file. {0} represents the UUID
     * generated, {1} represents the filename as argument.
     * </p>
     */
    public static final String DEFAULT_FILENAME_PATTERN = "{0}_{1}";

    /**
     * <p>
     * Represents the logger to log all operations, exceptions, etc. It is initialized statically.
     * </p>
     */
    private static final com.topcoder.util.log.Log LOG = com.topcoder.util.log.LogManager
            .getLog(DefaultUploadExternalServices.class.getName());

    /**
     * <p>
     * Represents the buffer size used for input stream reading.
     * </p>
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * <p>
     * Represents the internal <code>UploadServices</code> to delegate the calls. In upload* methods the filename
     * is generated before passing to this services. It is defined in constructor and cannot be <code>null</code>.
     * </p>
     */
    private final UploadServices uploadServices;

    /**
     * <p>
     * Represents the pattern to construct the filename. It's used with
     * <code>MessageFormat.format(filenamePattern, uuidGenerated, filename)</code>. It is defined in constructor
     * and cannot be <code>null</code> or empty.
     * </p>
     */
    private final String filenamePattern;

    /**
     * <p>
     * Represents the path where the files will be stored. It is defined in constructor and cannot be
     * <code>null</code> or empty.
     * </p>
     */
    private final String fileStorageLocation;

    /**
     * <p>
     * Creates <code>DefaultUploadExternalServices</code> using the configuration with default namespace.
     * </p>
     *
     * @throws ConfigurationException If any error occurs during accessing configuration.
     *                                If bad configuration is detected.
     * @since 1.0
     */
    public DefaultUploadExternalServices() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Creates <code>DefaultUploadExternalServices</code> using the configuration with specified namespace.
     * </p>
     *
     * @param namespace the namespace to load configuration
     *
     * @throws ConfigurationException   If any error occurs during accessing configuration.
     *                                  If bad configuration is detected.
     * @throws IllegalArgumentException if namespace is <code>null</code> or trim to empty
     * @since 1.0
     */
    public DefaultUploadExternalServices(String namespace) throws ConfigurationException {
        Helper.checkString(namespace, "namespace", LOG);
        UploadServices services = (UploadServices) Helper.createObject(namespace, "uploadServicesIdentifier",
                "DefaultUploadServices", LOG, UploadServices.class, null);
        if (services == null) {
            services = new DefaultUploadServices();
        }
        this.uploadServices = services;
        LOG.log(Level.INFO, "UploadServices created using ObjectFactory");
        this.filenamePattern = Helper.readProperty(namespace, "filenamePattern", DEFAULT_FILENAME_PATTERN, LOG,
                false);
        this.fileStorageLocation = Helper.readProperty(namespace, "fileStorageLocation", null, LOG, true);
    }

    /**
     * <p>
     * Creates <code>DefaultUploadExternalServices</code> with the specified property.
     * </p>
     *
     * @param uploadServices      the services to delegate all calls
     * @param filenamePattern     the pattern to construct the filename for passing to uploadServices:
     *                            may be <code>null</code>
     *                            (means that is used the default value) or empty
     * @param fileStorageLocation the location to store the files is used
     *
     * @throws IllegalArgumentException if uploadServices or fileStorageLocation are <code>null</code>,
     *                                  if fileStorageLocation is empty
     * @since 1.0
     */
    public DefaultUploadExternalServices(UploadServices uploadServices, String filenamePattern,
                                         String fileStorageLocation) {
        Helper.checkNull(uploadServices, "uploadServices", LOG);
        Helper.checkString(fileStorageLocation, "fileStorageLocation", LOG);

        this.uploadServices = uploadServices;
        this.filenamePattern = (filenamePattern != null && filenamePattern.trim().length() != 0) ? filenamePattern
                : DEFAULT_FILENAME_PATTERN;
        this.fileStorageLocation = fileStorageLocation;
    }

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     * <p>
     * If the project allows multiple submissions for users, it will add the new submission and return. If multiple
     * submission are not allowed for the project, firstly it will add the new submission, secondly mark previous
     * submissions as deleted and then return.
     * </p>
     *
     * @param projectId  the project's id
     * @param userId     the user's id
     * @param filename   the file name to use
     * @param submission the submission file data
     *
     * @return the id of the new submission
     *
     * @throws RemoteException          if an internal exception occurs (wrap it)
     * @throws UploadServicesException  if any error related to UploadService occurs
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @since 1.0
     */
    public long uploadSubmission(long projectId, long userId, String filename, DataHandler submission)
        throws RemoteException, UploadServicesException {
        LOG.log(Level.DEBUG,
                "Entered DefaultUploadExternalServices#uploadSubmission(long, long, String, DataHandler)");
        Helper.checkId(projectId, "projectId", LOG);
        Helper.checkId(userId, "userId", LOG);
        Helper.checkString(filename, "filename", LOG);
        Helper.checkNull(submission, "submission", LOG);

        File newFile = createNewFile(filename, submission);
        String filenameGenerated = newFile.getName();
        Helper.logFormat(LOG, Level.INFO, "Submission file created {0}", new Object[]{newFile.getAbsolutePath()});
        try {
            // delegate to the similar method in uploadServices without DataHandler passing only
            // filenameGenerated
            return uploadServices.uploadSubmission(projectId, userId, filenameGenerated);
            // if there is an exception during this processing then the file previous written must be deleted
            // delete the file temp generated with Axis
        } catch (UploadServicesException e) {
            newFile.delete();
            throw e;
        } finally {
            if (submission.getName() != null) {
                new File(submission.getName()).delete();
            }
            Helper.logFormat(LOG, Level.DEBUG,
                    "Exited DefaultUploadExternalServices#uploadSubmission(long, long, String, DataHandler)");
        }
    }

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     *
     * @param projectId the project's id
     * @param userId    the user's id
     * @param filename  the file name to use
     * @param finalFix  the final fix file data
     *
     * @return the id of the created final fix submission
     *
     * @throws UploadServicesException  if any error related to UploadService occurs
     * @throws RemoteException          if an internal exception occurs (wrap it)
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @since 1.0
     */
    public long uploadFinalFix(long projectId, long userId, String filename, DataHandler finalFix)
        throws RemoteException, UploadServicesException {
        Helper.logFormat(LOG, Level.DEBUG,
                "Entered DefaultUploadExternalServices#uploadFinalFix(long, long, String, DataHandler)");
        Helper.checkId(projectId, "projectId", LOG);
        Helper.checkId(userId, "userId", LOG);
        Helper.checkString(filename, "filename", LOG);
        Helper.checkNull(finalFix, "finalFix", LOG);

        File newFile = createNewFile(filename, finalFix);
        String filenameGenerated = newFile.getName();
        Helper.logFormat(LOG, Level.INFO, "Final fix file created {0}", new Object[]{filenameGenerated});
        try {
            // delegate to the similar method in uploadServices without DataHandler passing only
            // filenameGenerated
            return uploadServices.uploadFinalFix(projectId, userId, filenameGenerated);
            // if there is an exception during this processing then the file previous written must be deleted
            // delete the file temp generated with Axis
        } catch (UploadServicesException e) {
            newFile.delete();
            throw e;
        } finally {
            if (finalFix.getName() != null) {
                new File(finalFix.getName()).delete();
            }
            Helper.logFormat(LOG, Level.DEBUG,
                    "Exited DefaultUploadExternalServices#uploadFinalFix(long, long, String, DataHandler)");
        }
    }

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     * 
     * @param projectId
     *            the project's id
     * @param userId
     *            the user's id
     * @param filename
     *            the file name to use
     * @param testCases
     *            the test cases data
     * @return the id of the created test cases submission
     * @throws UploadServicesException
     *             if any error related to UploadService occurs
     * @throws RemoteException
     *             if an internal exception occurs (wrap it)
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     */
    public long uploadTestCases(long projectId, long userId, String filename, DataHandler testCases)
            throws RemoteException, UploadServicesException {
        Helper.logFormat(LOG, Level.DEBUG,
                "Entered DefaultUploadExternalServices#uploadTestCases(long, long, String, DataHandler)");
        Helper.checkId(projectId, "projectId", LOG);
        Helper.checkId(userId, "userId", LOG);
        Helper.checkString(filename, "filename", LOG);
        Helper.checkNull(testCases, "testCases", LOG);

        File newFile = createNewFile(filename, testCases);
        String filenameGenerated = newFile.getName();
        Helper.logFormat(LOG, Level.INFO, "Test case file created {0}", new Object[] {filenameGenerated});
        try {
            // delegate to the similar method in uploadServices without DataHandler passing only
            // filenameGenerated
            return uploadServices.uploadTestCases(projectId, userId, filenameGenerated);
            // if there is an exception during this processing then the file previous written must be deleted
            // delete the file temp generated with Axis
        } catch (UploadServicesException e) {
            newFile.delete();
            throw e;
        } finally {
        	if (testCases.getName() != null) {
                new File(testCases.getName()).delete();
        	}
            Helper.logFormat(LOG, Level.DEBUG,
                    "Exited DefaultUploadExternalServices#uploadTestCases(long, long, String, DataHandler)");
        }
    }

    /**
     * <p>Adds a new specification upload for a user in a particular project. This
     * submission always overwrite the previous ones.</p>
     *
     * @param projectId     the project's id
     * @param userId        the user's id.
     * @param filename      the file name to use.
     * @param specification the data handler to be used
     *
     * @return the id of the created specification submission.
     *
     * @throws IllegalArgumentException if any id is < 0 or any argument is null or trim to empty
     * @throws RemoteException          if an internal exception occurs (wrap it)
     * @throws UploadServicesException  if any error related to UploadServices occurs
     * @since 1.1
     */
    public long uploadSpecification(long projectId, long userId, String filename, DataHandler specification)
        throws RemoteException, UploadServicesException {

        // log method entry
        LOG.log(Level.DEBUG,
                "Entered DefaultUploadExternalServices#uploadSpecification(long, long, String, DataHandler)");

        // validate input parameters
        Helper.checkId(projectId, "projectId", LOG);
        Helper.checkId(userId, "userId", LOG);
        Helper.checkString(filename, "filename", LOG);
        Helper.checkNull(specification, "specification", LOG);

        // generate unique tmp file and write the content of the specification into it
        File newFile = createNewFile(filename, specification);
        String filenameGenerated = newFile.getName();
        Helper.logFormat(LOG, Level.INFO, "Specification file created {0}", new Object[]{newFile.getAbsolutePath()});
        try {
            // delegates to the similar method in uploadServices without DataHandler passing only
            // generated filename
            return uploadServices.uploadSpecification(projectId, userId, filenameGenerated);
        } catch (UploadServicesException e) {
            // if there is an exception during this processing then the file previous written must be deleted
            // delete the file temp generated with Axis
            newFile.delete();
            throw e;
        } finally {
            // finally if the data handler has associated file then delete it
            if (specification.getName() != null) {
                new File(specification.getName()).delete();
            }
            // log method exit
            Helper.logFormat(LOG, Level.DEBUG,
                    "Exited DefaultUploadExternalServices#uploadSpecification(long, long, String, DataHandler)");
        }
    }

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     * 
     * @param submissionId
     *            the submission's id
     * @param submissionStatusId
     *            the submission status id
     * @param operator
     *            the operator which execute the operation
     * @throws RemoteException
     *             if an internal exception occurs
     * @throws InvalidSubmissionException
     *             if the submission does not exist
     * @throws InvalidSubmissionStatusException
     *                                    if the submission status does not exist
     * @throws PersistenceException       if some error occurs in persistence layer
     * @throws RemoteException            if an internal exception occurs (wrap it)
     * @throws IllegalArgumentException   if any id is &lt; 0 or if operator is null or trim to empty
     * @since 1.0
     */
    public void setSubmissionStatus(long submissionId, long submissionStatusId, String operator)
        throws RemoteException, InvalidSubmissionException, InvalidSubmissionStatusException,
        PersistenceException {
        Helper.logFormat(LOG, Level.DEBUG,
                "Entered DefaultUploadExternalServices#setSubmissionStatus(long, long, String)");
        try {
            uploadServices.setSubmissionStatus(submissionId, submissionStatusId, operator);
        } finally {
            Helper.logFormat(LOG, Level.DEBUG,
                    "Exited DefaultUploadExternalServices#setSubmissionStatus(long, long, String)");
        }
    }

    /**
     * Creates a new file with a unique name and copies the stream from the <code>DataHandler</code> to the new
     * file created.
     *
     * @param filename    the filename to use
     * @param dataHandler the <code>DataHandler</code> to get the <code>InputStream</code>
     *
     * @return the new file
     *
     * @throws RemoteException if an internal exception occurs
     * @since 1.0
     */
    private File createNewFile(String filename, DataHandler dataHandler) throws RemoteException {
        // generate the filename to storage the file
        String filenameGenerated;
        File newFile;
        do {
            UUID uuidGenerated = UUIDUtility.getNextUUID(UUIDType.TYPEINT32);
            filenameGenerated = MessageFormat.format(filenamePattern, uuidGenerated, filename);
            filenameGenerated = fileStorageLocation + File.separator + filenameGenerated;
            newFile = new File(filenameGenerated);
        } while (newFile.exists());

        // write the content of submission into file
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = dataHandler.getDataSource().getInputStream();
            outputStream = new FileOutputStream(newFile);
            // write all bytes from input to output
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            Helper.logFormat(LOG, Level.ERROR, e,
                    "Failed to read/write from the submission data source stream to new file", new Object[]{});
            throw new RemoteException("Failed to read/write from the submission data source stream to new file", e);
        } finally {

            close(inputStream);
            close(outputStream);
        }
        return newFile;
    }

    /**
     * Adds the given user as a new submitter to the given project id. If the user is already added returns the the
     * id.
     *
     * @param projectId the project to which the user needs to be added
     * @param userId    the user to be added
     *
     * @return the added resource id
     *
     * @throws InvalidProjectException      if the project id is unknown
     * @throws InvalidUserException         if the user id is unknown
     * @throws InvalidProjectPhaseException if the phase of the project is not Registration.
     * @throws UploadServicesException      if any error occurs from UploadServices
     * @throws IllegalArgumentException     if any id is &lt; 0
     * @since 1.0
     */
    public long addSubmitter(long projectId, long userId) throws RemoteException, UploadServicesException {
        Helper.logFormat(LOG, Level.DEBUG,
                "Entered DefaultUploadExternalServices#addSubmitter(long, long)");
        try {
            return uploadServices.addSubmitter(projectId, userId);
        } finally {
            Helper.logFormat(LOG, Level.DEBUG,
                    "Exited DefaultUploadExternalServices#addSubmitter(long, long)");
        }
    }

    /**
     * Adds the given user as a new reviewer to the given project id.
     *
     * @param projectId the project to which the user needs to be added
     * @param userId    the user to be added
     * @return the added resource id
     * @throws InvalidProjectException      if the project id is unknown
     * @throws InvalidUserException         if the user id is unknown
     * @throws InvalidProjectPhaseException if the phase of the project is not Registration.
     * @throws UploadServicesException      if any error occurs from UploadServices
     * @throws PhaseManagementException if an unexpected error occurs.
     * @throws IllegalArgumentException     if any id is &lt; 0
     * @since 1.1.1
     */
    public Resource addReviewer(long projectId, long userId) throws UploadServicesException, PhaseManagementException {
        Helper.logFormat(LOG, Level.DEBUG, "Entered DefaultUploadExternalServices#addReviewer(long, long)");
        try {
            return uploadServices.addReviewer(projectId, userId);
        } finally {
            Helper.logFormat(LOG, Level.DEBUG, "Exited DefaultUploadExternalServices#addReviewer(long, long)");
        }
    }

     /**
     * Adds the given user as a primary screener to the given project id.
     *
     * @param projectId the project to which the user needs to be added
     * @param userId    the user to be added
     * @return the added resource id
     * @throws InvalidProjectException      if the project id is unknown
     * @throws InvalidUserException         if the user id is unknown
     * @throws InvalidProjectPhaseException if the phase of the project is not Registration.
     * @throws UploadServicesException      if any error occurs from UploadServices
     * @throws PhaseManagementException if an unexpected error occurs.
     * @throws IllegalArgumentException     if any id is &lt; 0
     * @since 1.1.1
     */
    public Resource addPrimaryScreener(long projectId, long userId) throws UploadServicesException, PhaseManagementException{
        Helper.logFormat(LOG, Level.DEBUG, "Entered DefaultUploadExternalServices#addPrimaryScreener(long, long)");
        try {
            return uploadServices.addPrimaryScreener(projectId, userId);
        } finally {
            Helper.logFormat(LOG, Level.DEBUG, "Exited DefaultUploadExternalServices#addPrimaryScreener(long, long)");
        }
    }

    /**
     * <p>Closes passed stream ignoring any exception that may occur during this operation.</p>
     *
     * @param closeable instance of {@link java.io.Closeable} object
     */
    private static void close(Closeable closeable) {

        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            // ignores exception
        }
    }

    /**
     * Remove all submitters for a given project
     *
     * @param projectId the project id
     * @param operator  user whos added
     * @return
     * @throws InvalidProjectException
     * @throws UploadServicesException
     * @throws InvalidUserException
     * @throws InvalidProjectPhaseException
     * @since 1.1.2
     */
    public Set<Long> removeAllSubmitters(long projectId, String operator) throws UploadServicesException{
        Helper.logFormat(LOG, Level.DEBUG, "Entered DefaultUploadExternalServices#removeAllSubmitters(long, string)");
        try {
            return uploadServices.removeAllSubmitters(projectId, operator);
        } finally {
            Helper.logFormat(LOG, Level.DEBUG, "Exited DefaultUploadExternalServices#removeAllSubmitters(long, string)");
        }
    }
}
