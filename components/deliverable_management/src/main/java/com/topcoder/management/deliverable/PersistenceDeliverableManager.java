/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.topcoder.management.deliverable.logging.LogMessage;
import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;
import com.topcoder.util.sql.databaseabstraction.NullColumnValueException;
import java.util.Date;
import java.util.Map.Entry;

/**
 * <p>
 * The PersistenceDeliverableManager class implements the DeliverableManager interface. It ties together a persistence
 * mechanism and two Search Builder instances (for searching for various types of data). The methods in this class use
 * a SearchBundle to execute a search and then use the persistence to load an object for each of the ids found from
 * the search.
 * </p>
 * * <p>
 * Here is the sample code for using this component.
 *
 * <pre>
 * // Set up the SearchBundleManager.
 * SearchBundleManager searchBundleManager = new SearchBundleManager(SEARCH_BUILDER_NAMESPACE);
 *
 * // 4.3.3 Create deliverable persistence and manager.
 *
 * DeliverablePersistence deliverablePersistence = new SqlDeliverablePersistence(new DBConnectionFactoryImpl(
 *         DB_CONNECTION_NAMESPACE));
 *
 * // The checker is used when deliverable instances
 * // are retrieved
 * Map&lt;String, DeliverableChecker&gt; checker = new HashMap&lt;String, DeliverableChecker&gt;();
 * checker.put(&quot;name1&quot;, new MockDeliverableChecker());
 * checker.put(&quot;name2&quot;, new MockDeliverableChecker());
 *
 * DeliverableManager deliverableManager = new PersistenceDeliverableManager(deliverablePersistence, checker,
 *         searchBundleManager.getSearchBundle(&quot;Deliverable Search Bundle&quot;), searchBundleManager
 *                 .getSearchBundle(&quot;Deliverable With Submission Search Bundle&quot;));
 *
 * // Search for deliverables (see 4.3.5)
 * </pre>
 *
 * </p>
 * <p>
 * <em>Changes in 1.1:</em>
 * <ul>
 * <li>Changed <code>logger</code> attribute name to upper case to meet Java and TopCoder standards.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and hence thread-safe.
 * </p>
 *
 * <p>
 * Version 1.1.1 (Specification Review Part 1 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #searchDeliverablesHelper(Filter, Boolean, int)} method not to call
 *         {@link Deliverable#setCompletionDate(Date)} method as it is responsibility of {@link DeliverableChecker} to
 *         do that. Also the return statement for the method is updated to properly convert list of deliverables to
 *         array.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * <em>Changes in 1.2:</em>
 * <ul>
 * <li>generic types are used to eliminate manually type check.</li>
 * <li>checkAndGetCustomResultSetValidDeliverable and checkDeliverableCheckers helper methods are moved here.</li>
 * </ul>
 * </p>
 *
 * @author aubergineanode, singlewood, saarixx, sparemax, isv
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.2
 */
public class PersistenceDeliverableManager implements DeliverableManager {

    /**
     * The name under which the deliverable search bundle should appear in the SearchBundleManager,
     * if the SearchBundleManager constructor is used.
     */
    public static final String DELIVERABLE_SEARCH_BUNDLE_NAME = "Deliverable Search Bundle";

    /**
     * The name under which the deliverable with submissions search bundle should appear in the
     * SearchBundleManager, if the SearchBundleManager constructor is used.
     */
    public static final String DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE_NAME
        = "Deliverable With Submission Search Bundle";

    /**
     * <p>
     * Represents the number 3.
     * </p>
     */
    private static final int NUM_3 = 3;

    /**
     * <p>
     * Represents the number 4.
     * </p>
     */
    private static final int NUM_4 = 4;


    /**
     * <p>
     * Logger instance using the class name as category.
     * </p>
     *
     * <p>
     * <em>Changes in 1.1:</em>
     * <ul>
     * <li>Changed attribute name to upper case to meet Java and TopCoder standards.</li>
     * </ul>
     * </p>
     */
    private static final Log LOGGER = LogManager.getLog(PersistenceDeliverableManager.class.getName());

    /**
     * The persistence store for Deliverables. This field is set in the constructor, is immutable,
     * and can never be null.
     */
    private final DeliverablePersistence persistence;

    /**
     * The search bundle that is used when searching for deliverables. This field is set in the
     * constructor, is immutable, and can never be null.
     */
    private final SearchBundle deliverableSearchBundle;

    /**
     * The checkers that are used to determine whether a deliverable is complete or not. This field is set in the
     * constructor and can never be null.
     * <p>
     * Change in 1.2: Added generic parameters.
     * </p>
     */
    private final Map<String, DeliverableChecker> deliverableCheckers = new HashMap<String, DeliverableChecker>();

    /**
     * The search bundle that is used when searching for deliverables combined with submissions.
     * This field is set in the constructor, is immutable, and can never be null.
     */
    private final SearchBundle deliverableWithSubmissionsSearchBundle;

    /**
     * Creates a new PersistenceDeliverableManager, initializing all fields to the given values. The deliverableCheckers
     * map is cloned.
     * <p>
     * Change in 1.2: Added generic parameters to deliverableCheckers type.
     * </p>
     *
     * @param persistence
     *            The persistence for Deliverables
     * @param deliverableCheckers
     *            The checkers to run on the deliverables to see if each is complete.
     * @param deliverableSearchBundle
     *            The search bundle for deliverables
     * @param submissionSearchBundle
     *            The search bundle for deliverables with submissions
     * @throws IllegalArgumentException
     *             If any argument is null, or deliverableCheckers contains null key or value.
     */
    public PersistenceDeliverableManager(DeliverablePersistence persistence,
        Map<String, DeliverableChecker> deliverableCheckers, SearchBundle deliverableSearchBundle,
        SearchBundle submissionSearchBundle) {
        DeliverableHelper.checkObjectNotNull(persistence, "persistence", LOGGER);
        DeliverableHelper.checkObjectNotNull(deliverableCheckers, "deliverableCheckers", LOGGER);
        DeliverableHelper.checkObjectNotNullFullDesp(deliverableSearchBundle,
                "deliverableSearchBundle is null, or searchBundleManager doesn't contain"
                        + " SearchBundle of required names DELIVERABLE_SEARCH_BUNDLE_NAME", LOGGER);
        DeliverableHelper.checkObjectNotNullFullDesp(submissionSearchBundle,
                "submissionSearchBundle is null, or searchBundleManager doesn't contain"
                        + " SearchBundle of required names DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE_NAME", LOGGER);

        // Check if deliverableCheckers is a Map of non-null String - DeliverableChecker
        checkDeliverableCheckers(deliverableCheckers);

        // Set the searchable fields of SearchBundle.
        DeliverableHelper.setSearchableFields(deliverableSearchBundle, DeliverableHelper.DELIVERABLE_SEARCH_BUNDLE);
        DeliverableHelper.setSearchableFields(submissionSearchBundle,
                DeliverableHelper.DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE);

        LOGGER.log(Level.INFO,
                "Instantiate PersistenceDeliverableManager with DeliverablePersistence, deliverableCheckers map,"
                        + "deliverableSearchBundle and submissionSearchBundle.");
        this.persistence = persistence;
        this.deliverableCheckers.putAll(deliverableCheckers);
        this.deliverableSearchBundle = deliverableSearchBundle;
        this.deliverableWithSubmissionsSearchBundle = submissionSearchBundle;
    }

    /**
     * Creates a new PersistenceDeliverableManager.
     *<p>
     * Change in 1.2: Added generic parameters to deliverableCheckers type.
     * </p>
     *
     * @param persistence
     *            The persistence for Deliverables
     * @param deliverableCheckers
     *            The checkers to run on the deliverables to see if each is complete. Map from String -
     *            DeliverableChecker.
     * @param searchBundleManager
     *            The manager containing the various SearchBundles needed
     * @throws IllegalArgumentException
     *             If any argument is null
     * @throws IllegalArgumentException
     *             If any search bundle is not available under the required names, or deliverableCheckers contains null
     *             key or value.
     */
    public PersistenceDeliverableManager(DeliverablePersistence persistence,
        Map<String, DeliverableChecker> deliverableCheckers, SearchBundleManager searchBundleManager) {
        // Check if searchBundleManager is null, if not, get the SearchBundles, then delegate to the first constructor.
        this(persistence, deliverableCheckers, (DeliverableHelper.checkObjectNotNull(searchBundleManager,
                "searchBundleManager", LOGGER)) ? searchBundleManager.getSearchBundle(DELIVERABLE_SEARCH_BUNDLE_NAME)
                : null, searchBundleManager.getSearchBundle(DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE_NAME));
    }

    /**
     * <p>
     * Searches the deliverables in the persistence store using the given filter. The filter can be
     * formed using the field names and utility methods in DeliverableFilterBuilder. The return will
     * always be a non-null (possibly 0 item) array. This method is designed to be used with
     * submission id filters, and returns only deliverables that are "per submission".
     * </p>
     *
     * @return The Deliverables meeting the filter and complete conditions
     * @param filter The filter to use
     * @param complete True to include only those deliverables that have been completed, false to
     *            include only those deliverables that are not complete, null to include both
     *            complete and incomplete deliverables
     * @throws IllegalArgumentException If filter is null
     * @throws DeliverablePersistenceException If there is an error reading the persistence
     * @throws SearchBuilderException If there is an error executing the filter
     * @throws SearchBuilderConfigurationException If the manager is not properly configured with a
     *             correct SearchBundle for searching
     * @throws DeliverableCheckingException If there is an error when determining whether a
     *             Deliverable has been completed or not
     */
    public Deliverable[] searchDeliverablesWithSubmissionFilter(Filter filter, Boolean complete)
        throws DeliverablePersistenceException, SearchBuilderException, DeliverableCheckingException {
        // Delegate to searchDeliverablesHelper. 4 indicates that there are four columns in the
        // CustomResultSet.
        LOGGER.log(Level.INFO, "search deliverables with submission filter.");
        return searchDeliverablesHelper(filter, complete, NUM_4);
    }

    /**
     * <p>
     * searchDeliverables: Searches the deliverables in the persistence store using the given
     * filter. The filter can be formed using the field names and utility methods in
     * DeliverableFilterBuilder. The return will always be a non-null (possibly 0 item) array. This
     * method should not be used with submission id filters.
     * </p>
     *
     * @return The Deliverables meeting the Filter and complete conditions
     * @param filter The filter to use
     * @param complete True to include only those deliverables that have been completed, false to
     *            include only those deliverables that are not complete, null to include both
     *            complete and incomplete deliverables
     * @throws IllegalArgumentException If filter is null
     * @throws DeliverablePersistenceException If there is an error reading the persistence store
     * @throws SearchBuilderException If there is an error executing the filter
     * @throws SearchBuilderConfigurationException If the manager is not properly configured with a
     *             correct SearchBundle for searching
     * @throws DeliverableCheckingException If there is an error when determining whether a
     *             Deliverable has been completed or not
     */
    public Deliverable[] searchDeliverables(Filter filter, Boolean complete)
        throws DeliverablePersistenceException, SearchBuilderException, DeliverableCheckingException {
        // Delegate to searchDeliverablesHelper. 3 indicates that there are three columns in the
        // CustomResultSet.
        LOGGER.log(Level.INFO, "Search deliverables filter.");
        return searchDeliverablesHelper(filter, complete, NUM_3);
    }

    /**
     * <p>
     * Help method for methods: searchDeliverables and searchDeliverablesWithSubmissionFilter.
     * Searches the deliverables in the persistence store using the given filter. The filter can be
     * formed using the field names and utility methods in DeliverableFilterBuilder. The return will
     * always be a non-null (possibly 0 item) array. The String operation is used for distinguish
     * call from two methods.
     * </p>
     *
     * <p>
     * Changes in version 1.2:
     * <ul>
     * <li>add generic type support.</li>
     * <li>checkAndGetCustomResultSetValidDeliverable method is moved in this class.</li>
     * </ul>
     * </p>
     *
     * @param filter The filter to use
     * @param complete True to include only those deliverables that have been completed, false to
     *            include only those deliverables that are not complete, null to include both
     *            complete and incomplete deliverables
     * @param operation the identifier for the caller, 3 is searchDeliverables, 4 is
     *            searchDeliverablesWithSubmissionFilter
     * @return The Deliverables meeting the Filter and complete conditions
     * @throws DeliverablePersistenceException If there is an error reading the persistence store
     * @throws SearchBuilderException If there is an error executing the filter
     * @throws SearchBuilderConfigurationException If the manager is not properly configured with a
     *             correct SearchBundle for searching
     * @throws DeliverableCheckingException If there is an error when determining whether a
     *             Deliverable has been completed or not
     */
    private Deliverable[] searchDeliverablesHelper(Filter filter, Boolean complete, int operation)
        throws SearchBuilderException, DeliverablePersistenceException, DeliverableCheckingException {
        DeliverableHelper.checkObjectNotNull(filter, "filter", LOGGER);

        // Get object with given filter from corresponding search bundle.
        Object obj;
        if (operation == NUM_3) {
            obj = deliverableSearchBundle.search(filter);
        } else {
            obj = deliverableWithSubmissionsSearchBundle.search(filter);
        }

        // Check if the return object is a CustomResultSet, and it's record count is correct too.
        // And retrieve long[][] from correct CustomResultSet.
        long[][] array = checkAndGetCustomResultSetValidDeliverable(obj, operation);

        // Create a List for temporary storage.
        List<Deliverable> list = new ArrayList<Deliverable>();

        LOGGER.log(Level.INFO, "Deliverable Id Array: " + array.length + ", " + array[0].length);

        // Get Deliverable[] from persistence layer.
        Deliverable[] deliverableArray;
        if (operation == NUM_3) {
            deliverableArray = persistence.loadDeliverables(array[0], array[1], array[2]);
        } else {
            deliverableArray = persistence.loadDeliverables(array[0], array[1], array[2], array[NUM_3]);
        }

        // For each deliverable in deliverableArray
        for (int i = 0, n = deliverableArray.length; i < n; i++) {

            LOGGER.log(Level.INFO, "Loading Deliverable Array");

            Deliverable deliverable = deliverableArray[i];

            // Get the name of deliverable. This can not be null, if it is, throw a
            // DeliverablePersistenceException.
            String name = deliverable.getName();
            DeliverableHelper.checkObjectNotNullFullDesp(name, "name in the deliverable can't be null.", LOGGER);

            // Look up the deliverable checker with the name.
            DeliverableChecker deliverableChecker = (DeliverableChecker) deliverableCheckers.get(name);

            // If DeliverableChecker is found for the name, run the retrieved DeliverableChecker
            // on the deliverable. Otherwise do not check the deliverable.
            if (deliverableChecker != null) {
                deliverableChecker.check(deliverable);
            }

            // If the isCompleted property of the deliverable matches the complete argument
            // (when complete is not null), then add the deliverable to the return array.
            // When complete is null, the deliverable is always added to the return array.
            if ((complete == null) || (complete.booleanValue() == deliverable.isComplete())) {

                LOGGER.log(Level.INFO, "Adding Deliverable To List");
                list.add(deliverable);
            }
        }

        // Convert List to Deliverable[]
        return list.toArray(new Deliverable[list.size()]);
    }

    /**
     * Check if the object is a CustomResultSet. If true, then check if the CustomResultSet's record count equals count.
     * If all ok, it will return a long[][] array that retrieved from CustomResultSet.
     *
     * @param obj
     *            the object to check
     * @param maxColumn
     *            the max column number
     * @return long[][] from CustomResultSet
     * @throws SearchBuilderConfigurationException
     *             if obj is not a CustomResultSet, or it's record count is not correct.
     * @since 1.2
     */
    private static long[][] checkAndGetCustomResultSetValidDeliverable(Object obj, int maxColumn)
        throws SearchBuilderConfigurationException {

        if (!(obj instanceof CustomResultSet)) {
            LOGGER.log(Level.ERROR, "the object get from SearchBundle is not a CustomResultSet");
            throw new SearchBuilderConfigurationException("the object get from SearchBundle"
                    + " is not a CustomResultSet");
        }

        CustomResultSet customerResult = (CustomResultSet) obj;

        LOGGER.log(Level.INFO, "CustomResultSet Records: " + customerResult.getRecordCount());

        // Create a long array for temporary storage.
        long[][] res = new long[maxColumn][customerResult.getRecordCount()];

        try {
            // Retrieved long[][] from CustomResultSet. If the expected data is not present,
            // according to the implementation of CustomerResult.getInt(), ClassCastException
            // will be thrown. Note that all columns and rows of CustomResultSet are 1-indexed.
            for (int i = 0; customerResult.next(); i++) {
                for (int j = 0; j < maxColumn; ++j) {
                    res[j][i] = customerResult.getLong(j + 1);
                }
            }
        } catch (ClassCastException e) {
            LOGGER.log(Level.ERROR, "the CustomResultSet get from SearchBundle"
                    + " contains non-long values, or the expected data is missing.\n"
                    + LogMessage.getExceptionStackTrace(e));
            throw new SearchBuilderConfigurationException("the CustomResultSet get from SearchBundle"
                    + " contains non-long values, or the expected data is missing.", e);
        } catch (InvalidCursorStateException e) {
            LOGGER.log(Level.ERROR, "error occurs when fetching the current row. \n"
                    + LogMessage.getExceptionStackTrace(e));
            throw new SearchBuilderConfigurationException("error occurs when fetching the current row.", e);
        } catch (NullColumnValueException e) {
            LOGGER.log(Level.ERROR, "NullColumnValueException. \n"
                    + LogMessage.getExceptionStackTrace(e));
            throw new SearchBuilderConfigurationException("error occurs when fetching the current row.", e);
        }

        return res;
    }

    /**
     * Check if deliverableCheckers don't have null key or value..
     * <p>
     * Changes in version 1.2: since generic type is used, we don't need to explicitly check types.
     * </p>
     *
     * @param deliverableCheckers
     *            the map to check
     * @throws IllegalArgumentException
     *             If deliverableCheckers contains null key/value
     * @since 1.2
     */
    private static void checkDeliverableCheckers(Map<String, DeliverableChecker> deliverableCheckers) {
        for (Iterator<Entry<String, DeliverableChecker>> iter = deliverableCheckers.entrySet().iterator(); iter
                .hasNext();) {
            Entry<String, DeliverableChecker> element = (Entry<String, DeliverableChecker>) iter.next();

            if (element.getKey() == null) {
                throw new IllegalArgumentException("deliverableCheckers contains null String key.");
            }

            if (element.getValue() == null) {
                throw new IllegalArgumentException("deliverableCheckers contains null DeliverableChecker Objects");
            }
        }
    }
}
