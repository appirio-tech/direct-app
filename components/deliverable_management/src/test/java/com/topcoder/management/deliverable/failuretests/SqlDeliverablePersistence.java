/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;


/**
 * <p>The SqlDeliverablePersistence class implements the DeliverablePersistence
 * interface, in order to persist to the database structure given in the
 * deliverable_management.sql script. This class does not cache a Connection to
 * the database. Instead a new Connection is used on every method call.
 * PreparedStatements should be used to execute the SQL statements.</p>
 *
 * <p>This class is immutable and thread-safe in the sense that multiple threads
 * can not corrupt its internal data structures. However, the results if used
 * from multiple threads can be unpredictable as the database is changed from
 * different threads. This can equally well occur when the component is used on
 * multiple machines or multiple instances are used, so this is not a
 * thread-safety concern.</p>
 *
 * @author  assistant
 * @version  1.0
 */
public class SqlDeliverablePersistence implements DeliverablePersistence {

    /**
     * connectionName: The name of the connection producer to use when a
     * connection to the database is retrieved from the DBConnectionFactory.
     * This field is immutable and can be null or non-null. When non-null, no
     * restrictions are applied to the field. When this field is null, the
     * createConnection() method is used to get a connection. When it is
     * non-null, the createConnection(String) method is used to get a connection.
     * This field is not exposed by this class, and is used whenever a connection
     * to the database is needed (i.e. in every method).
     */
    private final String connectionName;

    /**
     * connectionFactory: The connection factory to use when a connection to the
     * database is needed. This field is immutable and must be non-null. This
     * field is not exposed by this class and is used whenever a connection to
     * the database is needed (i.e. in every method).
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * SqlDeliverablePersistence constructor: Creates a new
     * SqlDeliverablePersistence. The connectionName field is set to null.
     *
     * @param  connectionFactory  The connection factory to use for getting
     *                            connections to the database.
     *
     * @throws  IllegalArgumentException  If connectionFactory is null.
     */
    public SqlDeliverablePersistence(DBConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.connectionName = null;
    }

    /**
     * SqlDeliverablePersistence constructor: Creates a new
     * SqlDeliverablePersistence. All fields are set to the given values.
     *
     * @param  connectionFactory  The connection factory to use for getting
     *                            connections to the database.
     * @param  connectionName  The name of the connection to use. Can be null.
     *
     * @throws  IllegalArgumentException  If connectionFactory is null.
     */
    public SqlDeliverablePersistence(DBConnectionFactory connectionFactory,
        String connectionName) {
        this.connectionFactory = null;
        this.connectionName = null;
    }

    /**
     * <p>loadDeliverables: Loads the deliverables associated with the given
     * deliverable id. There may be more than one deliverable returned if the
     * deliverable is a "per submission" deliverable. Hence the need for an
     * array return type. If there is no matching deliverable in the
     * persistence, an empty array should be returned.</p>
     *
     * <p>Exception Handling: Any SqlException or DBConnectionException should
     * be wrapped in a DeliverablePersistenceException.</p>
     *
     * <p>Implementation: Open a database connection. Execute the deliverable
     * table INSERT statement in CS section 1.3.2.2 If no rows are selected,
     * return null If the per_submission column of the returned result set is
     * true, then run the second query given in CS section 1.3.2.2 to select all
     * deliverable_id, submission_id pairs for the given deliverableId for each
     * such pair, call the loadDeliverable(long, long) method Return the results
     * of all these loadDeliverable calls in an array Else Create a new non "per
     * submission" Deliverable using the data in the row in the result set End
     * if Close the database connection return the created Deliverable</p>
     *
     * @param  deliverableId  The id of the deliverable
     *
     * @return  The matching deliverable (possibly expanded by matching with
     *          each active submission, if it is a "per submission"
     *          deliverable), or an empty array.
     *
     * @throws  IllegalArgumentException  If deliverableId is <= 0
     */
    public Deliverable[] loadDeliverables(long deliverableId, long resourceId, long phaseId) {
        return null;
    }

    /**
     * <p>loadDeliverable: Loads the deliverable associated with the given
     * submission. The deliverable must be a "per submission" deliverable and
     * the given submission must be "Active". If this is not the case, null is
     * returned.</p>
     *
     * <p>Exception Handling: Any SqlException or DBConnectionException should
     * be wrapped in a DeliverablePersistenceException.</p>
     *
     * <p>Implementation: Open a database connection. Execute the deliverable
     * table INSERT statement in CS section 1.3.2.1 If no rows are selected,
     * return null Create a new Deliverable using the data in the row in the
     * result set Close the database connection return the created Deliverable
     * </p>
     *
     * @param  deliverableId  The id of the deliverable
     * @param  submissionId  The id of the submission the deliverable should be
     *                       associated with
     *
     * @return  The deliverable, or null if there is no deliverable for the
     *          given id, or submission is not an 'Active' submission
     *
     * @throws  IllegalArgumentException  If deliverableId is <= 0
     * @throws  DeliverablePersistenceException  If there is an error reading the
     *                                          persistence data
     */
    public Deliverable loadDeliverable(long deliverableId, long resourceId, long phaseId, long submissionId) {
        return null;
    }

    /**
     * <p>loadDeliverables: Loads all Deliverables with the given ids from
     * persistence. May return a 0-length array.</p>
     *
     * <p>Exception Handling: Any SqlException or DBConnectionException should
     * be wrapped in a DeliverablePersistenceException.</p>
     *
     * <p>Implementation: This method behaves exactly as per the
     * loadDeliverables(long) method, except that all rows are selected in one
     * ResultSet and the loadDeliverables(long) steps are run for each row in
     * the ResultSet.</p>
     *
     * @param  deliverableIds  The ids of deliverables to load
     *
     * @return  The loaded deliverables
     *
     * @throws  IllegalArgumentException  if any id is <= 0
     * @throws  DeliverablePersistenceException  if there is an error reading
     *                                           the persistence data
     */
    public com.topcoder.management.deliverable.Deliverable[] loadDeliverables(
        long[] deliverableIds, long[] resourceIds, long[] phaseIds) {

        // your code here
        return null;
    }

    /**
     * <p>loadDeliverables: Loads the deliverables associated with the given
     * submissions. The deliverables must be "per submission" deliverables and
     * the given submissions must be "Active". Pairs of ids not meeting this
     * requirement will not be returned.</p>
     *
     * <p>Exception Handling: Any SqlException or DBConnectionException should
     * be wrapped in a DeliverablePersistenceException.</p>
     *
     * <p>Implementation: This method behaves exactly as per the
     * loadDeliverables(long, long) method, except that all rows are selected in
     * one ResultSet and the loadDeliverables(long, long) steps are run for each
     * row in the ResultSet.</p>
     *
     * @param  deliverableIds  The ids of deliverables to load
     * @param  submissionIds  The ids ofthe submission for each deliverable
     *
     * @return  The loaded deliverables
     *
     * @throws  IllegalArgumentException  If the two arguments do not have the
     *                                    same number of elements
     * @throws  IllegalArgumentException  if any id (in either array) is <= 0
     * @throws  DeliverablePersistenceException  if there is an error reading
     *                                           the persistence data
     */
    public com.topcoder.management.deliverable.Deliverable[] loadDeliverables(
        long[] deliverableIds, long[] resourceIds, long[] phaseIds, long[] submissionIds) {

        // your code here
        return null;
    }
}
