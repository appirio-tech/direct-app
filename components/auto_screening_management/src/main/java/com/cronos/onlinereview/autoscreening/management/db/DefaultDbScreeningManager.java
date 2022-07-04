/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.cronos.onlinereview.autoscreening.management.Helper;
import com.cronos.onlinereview.autoscreening.management.PersistenceException;
import com.cronos.onlinereview.autoscreening.management.ResponseSeverity;
import com.cronos.onlinereview.autoscreening.management.ScreeningResponse;
import com.cronos.onlinereview.autoscreening.management.ScreeningResult;
import com.cronos.onlinereview.autoscreening.management.ScreeningStatus;
import com.cronos.onlinereview.autoscreening.management.ScreeningTask;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskAlreadyExistsException;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskDoesNotExistException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorImpl;

/**
 * <p>
 * The default implementation of the screening manager that uses Informix database as the
 * persistence for the screening tasks.
 * </p>
 * <p>
 * This class uses the ID Generator component to generate unique ids for the initiation of screening
 * tasks.
 * </p>
 * <p>
 * The setup requirement of this screening manager is to have an unchanged pending status in the
 * screening_status_lu table (for the initiation of screening tasks too). Other things such as
 * screening statuses and response severities are not cached, since the other component (using the
 * same persistence) may update these dynamically. All are retrieved on request.
 * </p>
 * <p>
 * This class is made thread safe by using JDBC transactions. That means enclosing all the JDBC
 * operations done within a method in a transaction. It will be then the responsibility of the
 * database to ensure concurrent access. The component will be able to run even in a distributed
 * environment.
 * </p>
 *
 * <p>
 * Changes in v1.1 (Cockpit Spec Review Backend Service Update v1.0):
 * - added flag so that container transaction demarcation can be used.
 * </p>
 *
 * @author colau, haozhangr, pulky
 * @version 1.1
 */
public class DefaultDbScreeningManager extends DbScreeningManager {
    /**
     * This is a sql script used to retrieve the pending status id.
     */
    private static final String SELECT_SSID = "SELECT screening_status_id FROM screening_status_lu WHERE name = ?";

    /**
     * This is a sql script used to check whether a screening task was already initiated with an
     * upload.
     */
    private static final String SELECT_UPLOADID = "SELECT upload_id FROM screening_task WHERE upload_id = ?";

    /**
     * This is a sql script used to insert the screening task into the table.
     */
    private static final String INSERT_TASK = "INSERT INTO screening_task (screening_task_id, "
            + "upload_id, screening_status_id, create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * This is a sql script used to retrieve the screening tasks and their associated screening
     * status with uploads. This is an uncompleated sql script and will be finished later.
     *
     */
    private static final String SELECT_TASK = "SELECT a.screening_task_id, a.upload_id, "
            + "a.screening_status_id, a.screener_id, a.start_timestamp, a.create_user, a.create_date, "
            + "a.modify_user, a.modify_date, b.name FROM screening_task AS a LEFT JOIN screening_status_lu "
            + "AS b ON a.screening_status_id = b.screening_status_id WHERE a.upload_id IN ";

    /**
     * This is a sql script used to retrieve the screening results and its associated screening
     * response and response severity with a screening task id.
     */
    private static final String SELECT_RESULT = "SELECT a.screening_result_id, a.screening_response_id, "
            + "a.dynamic_response_text, b.response_severity_id, b.response_code, b.response_text, c.name "
            + "FROM screening_result AS a LEFT JOIN screening_response_lu AS b ON "
            + "a.screening_response_id = b.screening_response_id LEFT JOIN response_severity_lu AS c "
            + "ON b.response_severity_id = c.response_severity_id WHERE a.screening_task_id = ?";

    /**
     * The first index when set parameters in prepared statement.
     */
    private static final int FIRST_INDEX = 1;

    /**
     * The second index when set parameters in prepared statement.
     */
    private static final int SECOND_INDEX = 2;

    /**
     * The third index when set parameters in prepared statement.
     */
    private static final int THIRD_INDEX = 3;

    /**
     * The fourth index when set parameters in prepared statement.
     */
    private static final int FOURTH_INDEX = 4;

    /**
     * The fifth index when set parameters in prepared statement.
     */
    private static final int FIFTH_INDEX = 5;

    /**
     * The sixth index when set parameters in prepared statement.
     */
    private static final int SIXTH_INDEX = 6;

    /**
     * The sevennth index when set parameters in prepared statement.
     */
    private static final int SEVENTH_INDEX = 7;

    /**
     * The eithth index when set parameters in prepared statement.
     */
    private static final int EIGHTH_INDEX = 8;

    /**
     * The ninth index when set parameters in prepared statement.
     */
    private static final int NINTH_INDEX = 9;

    /**
     * The tenth index when set parameters in prepared statement.
     */
    private static final int TENTH_INDEX = 10;

    /**
     * <p>
     * Represents the id generator used to generate unique ids for the initiation of screening
     * tasks.
     * </p>
     * <p>
     * It is initialized in the constructor and not changed afterwards. It cannot be null. It is
     * accessed in the initiateScreening() method.
     * </p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>
     * Represents the id of the pending screening status.
     * </p>
     * <p>
     * It is initially set to -1, but should be positive once it's lazily set in the
     * initiateScreening() method. It is not changed after being set. It is accessed in the
     * initiateScreening() method too.
     * </p>
     *
     *
     */
    private long pendingStatusId = -1;

    /**
     * <p>
     * Constructor that accepts the given arguments. Default connection producer name will be used
     * to obtain connections.
     * </p>
     *
     *
     *
     * @param connectionFactory
     *            the connection factory to use
     * @param idGenerator
     *            the id generator to use
     * @throws IllegalArgumentException
     *             if connectionFactory or idGenerator is null
     */
    public DefaultDbScreeningManager(DBConnectionFactoryImpl connectionFactory,
            IDGeneratorImpl idGenerator) {
        super(connectionFactory);
        Helper.checkNull(idGenerator, "idGenerator");
        this.idGenerator = idGenerator;
    }

    /**
     * <p>
     * Constructor that accepts the given arguments.
     * </p>
     *
     *
     *
     * @param connectionFactory
     *            the connection factory to use
     * @param connectionName
     *            the connection producer name
     * @param idGenerator
     *            the id generator to use
     * @throws IllegalArgumentException
     *             if connectionFactory or idGenerator is null, or connectionName is null or empty
     *             String
     */
    public DefaultDbScreeningManager(DBConnectionFactoryImpl connectionFactory,
            String connectionName, IDGeneratorImpl idGenerator) {
        super(connectionFactory, connectionName);
        Helper.checkNull(idGenerator, "idGenerator");
        this.idGenerator = idGenerator;
    }

    /**
     * <p>
     * Initiates a screening task with the specified upload and operator. The task should be set to
     * pending once it's initiated. The screener and start timestamp of the task should be left
     * unspecified since it's not determined yet.
     * </p>
     *
     *
     * @param upload
     *            the upload identifier
     * @param operator
     *            the creation user of the screening task
     * @throws IllegalArgumentException
     *             if upload is non-positive, or operator is null or empty String
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskAlreadyExistsException
     *             if the upload has been used to initiate a screening task
     */
    public void initiateScreening(long upload, String operator)
        throws ScreeningTaskAlreadyExistsException, PersistenceException {
        Helper.checkNonPositive(upload, "upload");
        Helper.checkString(operator, "operator");

        Connection conn = createConnection();

        /*
         * insert a row into the screening_task table.
         */
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            synchronized (this) { /* lock pendingStatusId */
                if (pendingStatusId == -1) {
                    pendingStatusId = getPendingScreeningStatusId(conn);
                }
            } /* unlock pendingStatusId */
            checkTask(upload, conn);

            long id = idGenerator.getNextID();
            // get and set the prepareStatement
            ps = conn.prepareStatement(INSERT_TASK);
            ps.setLong(FIRST_INDEX, id);
            ps.setLong(SECOND_INDEX, upload);
            ps.setLong(THIRD_INDEX, pendingStatusId);
            ps.setString(FOURTH_INDEX, operator);
            ps.setTimestamp(FIFTH_INDEX, new Timestamp(System.currentTimeMillis()));
            ps.setString(SIXTH_INDEX, operator);
            ps.setTimestamp(SEVENTH_INDEX, new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();
            if (useManualCommit) {
                conn.commit();
            }
        } catch (SQLException e) {
            rollback(conn);
            throw new PersistenceException("Failed to insert a row into the screening_task table.",
                    e);
        } catch (IDGenerationException e) {
            throw new PersistenceException("Failed to get id from id idGenerator.", e);
        } finally {
            doClose(conn, ps, rs);
        }
    }

    /**
     * Check if the upload already initiated a screening task in the screening_task table.
     *
     * @param upload
     *            upload task to check
     * @param conn
     *            the database connection
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskAlreadyExistsException
     *             if the upload has been used to initiate a screening task
     */
    private void checkTask(long upload, Connection conn)
        throws ScreeningTaskAlreadyExistsException, PersistenceException {
        // the database resource to use
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // get record from screening_task
            ps = conn.prepareStatement(SELECT_UPLOADID);
            ps.setLong(1, upload);
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                throw new ScreeningTaskAlreadyExistsException(upload);
            }
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Failed to check if the upload already initiated a screening task.", e);
        } finally {
            doClose(null, ps, rs);
        }
    }

    /**
     * This method gets id of the pending screening status from the screening_status_lu table.
     *
     * @param conn
     *            the database connection
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @return the id of the pending screening status
     */
    private long getPendingScreeningStatusId(Connection conn) throws PersistenceException {
        // the database resource to use
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(SELECT_SSID);
            ps.setString(1, ScreeningStatus.PENDING);
            ps.executeQuery();
            rs = ps.getResultSet();
            if (!rs.next()) {
                throw new PersistenceException(
                        "There is no id exist in database which is associated to "
                                + ScreeningStatus.PENDING + ".");
            }
            // return the id of the pending screening status
            return rs.getLong(FIRST_INDEX);
        } catch (SQLException e) {
            throw new PersistenceException("Failed to get id of the pending screening status.", e);
        } finally {
            doClose(null, ps, rs);
        }
    }

    /**
     * <p>
     * Gets the screening task with the single upload, with the screening results included as
     * details.
     * </p>
     *
     *
     * @param upload
     *            the upload identifier
     * @return the screening task with details
     * @throws IllegalArgumentException
     *             if upload is non-positive
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskDoesNotExistException
     *             if the upload was not initiated a screening task
     */
    public ScreeningTask getScreeningDetails(long upload) throws PersistenceException,
        ScreeningTaskDoesNotExistException {
        Helper.checkNonPositive(upload, "upload");

        // get database connection
        Connection conn = createConnection();

        try {
            // get task and screening results
            ScreeningTask task = getScreeningTasks(new long[] {upload}, false, conn)[0];
            getScreeningResults(task, conn);
            return task;
        } finally {
            doClose(conn, null, null);
        }
    }

    /**
     * This method gets the screening results from database and add them to the screening task.
     * Connection will not be closed in this method.
     *
     * @param task
     *            the task to add results to
     * @param conn the database connection
     * @throws PersistenceException
     *             if any error occurs in persistence
     */
    private void getScreeningResults(ScreeningTask task, Connection conn)
        throws PersistenceException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // prepare the database resource
            ps = conn.prepareStatement(SELECT_RESULT);
            ps.setLong(FIRST_INDEX, task.getId());
            rs = ps.executeQuery();

            // get results from database
            while (rs.next()) {
                // create the ScreeningResult, ScreeningResponse and ResponseSeverity
                // objects and set ResponseSeverity to ScreeningResponse, ScreeningResponse
                // to ScreeningResult
                ResponseSeverity responseSeverity = new ResponseSeverity();
                ScreeningResponse screeningResponse = new ScreeningResponse();
                ScreeningResult screeningResult = new ScreeningResult();
                screeningResponse.setResponseSeverity(responseSeverity);
                screeningResult.setScreeningResponse(screeningResponse);

                // populate the attributes retrieved from database
                screeningResult.setId(rs.getLong(FIRST_INDEX));
                screeningResponse.setId(rs.getLong(SECOND_INDEX));
                screeningResult.setDynamicText(rs.getString(THIRD_INDEX));
                responseSeverity.setId(rs.getLong(FOURTH_INDEX));
                screeningResponse.setResponseCode(rs.getString(FIFTH_INDEX));
                screeningResponse.setResponseText(rs.getString(SIXTH_INDEX));
                responseSeverity.setName(rs.getString(SEVENTH_INDEX));

                // add result to task
                task.addScreeningResult(screeningResult);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Failed to get screening results from the database.", e);
        } finally {
            doClose(null, ps, rs);
        }
    }

    /**
     * <p>
     * Gets the screening tasks with the multiple uploads, without the screening results included.
     * Each screening task will be returned in the position corresponding to the respective upload
     * in the array.
     * </p>
     * <p>
     * If any of the uploads was not initiated a screening task, the default behavior would throw a
     * ScreeningTaskDoesNotExistException.
     * </p>
     *
     *
     *
     * @param uploads
     *            the upload identifiers
     * @return the screening tasks without details
     * @throws IllegalArgumentException
     *             if uploads is null, or any of the uploads is non-positive, or uploads contains
     *             duplicated elements
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskDoesNotExistException
     *             if any of the uploads was not initiated a screening task
     */
    public ScreeningTask[] getScreeningTasks(long[] uploads) throws PersistenceException,
        ScreeningTaskDoesNotExistException {
        return getScreeningTasks(uploads, false);
    }

    /**
     * <p>
     * Gets the screening tasks with the multiple uploads, without the screening results included.
     * Each screening task will be returned in the position corresponding to the respective upload
     * in the array.
     * </p>
     * <p>
     * If any of the uploads was not initiated a screening task, the behavior depends on the
     * allowNotExist flag. If it is true, null will be set in the corresponding position in the
     * array. If it is false, a ScreeningTaskDoesNotExistException will be thrown.
     * </p>
     *
     *
     * @param uploads
     *            the upload identifiers
     * @param allowNonExist
     *            whether it allows upload with no screening task initiated
     * @return the screening tasks without details
     * @throws IllegalArgumentException
     *             if uploads is null, or any of the uploads is non-positive, or uploads contains
     *             duplicated elements
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskDoesNotExistException
     *             if any of the uploads was not initiated a screening task, and allowNotExist is
     *             false
     */
    public ScreeningTask[] getScreeningTasks(long[] uploads, boolean allowNonExist)
        throws PersistenceException, ScreeningTaskDoesNotExistException {
        Helper.checkNull(uploads, "uploads");
        // check uploads for non-positive element and duplicated
        Set set = new HashSet();
        for (int i = 0; i < uploads.length; i++) {
            Helper.checkNonPositive(uploads[i], "The element of uploads indexed " + i);
            if (set.contains(new Long(uploads[i]))) {
                throw new IllegalArgumentException(
                        "There are duplicated elements exist in 'uploads'.");
            }
            set.add(new Long(uploads[i]));
        }

        // get database connection
        Connection conn = createConnection();

        try {
            return getScreeningTasks(uploads, allowNonExist, conn);
        } finally {
            doClose(conn, null, null);
        }
    }

    /**
     * <p>
     * Gets the screening tasks with the multiple uploads, without the screening results included.
     * Each screening task will be returned in the position corresponding to the respective upload
     * in the array.
     * </p>
     * <p>
     * The Connetion will not be closed in this method.
     * </p>
     *
     * @param uploads
     *            the upload identifiers
     * @param allowNonExist
     *            whether it allows upload with no screening task initiated
     * @param conn
     *            the database connection
     * @return the screening tasks without details
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskDoesNotExistException
     *             if any of the uploads was not initiated a screening task, and allowNotExist is
     *             false
     */
    private ScreeningTask[] getScreeningTasks(long[] uploads, boolean allowNonExist, Connection conn)
        throws PersistenceException, ScreeningTaskDoesNotExistException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // complete the SELECT_TASK sql script.
            StringBuffer buffer = new StringBuffer(SELECT_TASK);
            buffer.append("(");
            for (int i = 0; i < uploads.length - 1; i++) {
                buffer.append(uploads[i] + ",");
            }
            buffer.append(uploads[uploads.length - 1] + ")");

            // prepare sql statement and execute it
            ps = conn.prepareStatement(buffer.toString());
            rs = ps.executeQuery();
            // mapping uploads[i] to i
            Map map = new HashMap();
            for (int i = 0; i < uploads.length; i++) {
                map.put(new Long(uploads[i]), new Integer(i));
            }
            ScreeningTask[] tasks = new ScreeningTask[uploads.length];
            int taskNum = 0;
            // retrieve rows from database
            while (rs.next()) {
                // create ScreeningTask and ScreeningStatus, and set ScreeningStatus to
                // ScreeningTask
                ScreeningTask task = new ScreeningTask();
                ScreeningStatus screeningStatus = new ScreeningStatus();
                task.setScreeningStatus(screeningStatus);

                // populate the attributes retrieved from database
                task.setId(rs.getLong(FIRST_INDEX));
                task.setUpload(rs.getLong(SECOND_INDEX));
                screeningStatus.setId(rs.getLong(THIRD_INDEX));
                if (rs.getObject(FOURTH_INDEX) != null) {
                    task.setScreener(rs.getLong(FOURTH_INDEX));
                }
                if (rs.getObject(FIFTH_INDEX) != null) {
                    task.setStartTimestamp(new Date(rs.getTimestamp(FIFTH_INDEX).getTime()));
                }
                task.setCreationUser(rs.getString(SIXTH_INDEX));
                task.setCreationTimestamp(new Date(rs.getTimestamp(SEVENTH_INDEX).getTime()));
                task.setModificationUser(rs.getString(EIGHTH_INDEX));
                task.setModificationTimestamp(new Date(rs.getTimestamp(NINTH_INDEX).getTime()));
                screeningStatus.setName(rs.getString(TENTH_INDEX));

                // add task to tasks array at corresponding position
                tasks[((Integer) map.get(new Long(task.getUpload()))).intValue()] = task;
                taskNum++;
            }
            if (!allowNonExist && taskNum != uploads.length) {
                long[] notExistUploads = new long[uploads.length - taskNum];
                int index = 0;
                for (int i = 0; i < tasks.length; i++) {
                    if (tasks[i] == null) {
                        notExistUploads[index++] = uploads[i];
                    }
                }
                throw new ScreeningTaskDoesNotExistException(notExistUploads);
            }
            return tasks;
        } catch (SQLException e) {
            throw new PersistenceException("Failed to get screening tasks from database.", e);
        } finally {
            doClose(null, ps, rs);
        }
    }

    /**
     * This method rollback all the modified data.
     *
     * @param conn
     *            the database connection
     */
    private void rollback(Connection conn) {
        if (useManualCommit) {
            try {
                conn.rollback();
            } catch (SQLException sqle) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Close the resources of the database.
     * </p>
     *
     * @param connection
     *            the Connection to be closed
     * @param statement
     *            the PreparedStatement to be closed.
     * @param resultSet
     *            the ResultSet to be closed
     */
    private void doClose(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        // close the resultSet
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            // ignore to continue close the Connection and Statement
        }

        // close the PreparedStatement
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            // ignore to continue close the Connection
        }

        // close the connection
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }
}
