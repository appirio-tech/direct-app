/*
 * Copyright (C) 2006-2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.validation.ValidationException;

import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.datavalidator.IntegerValidator;
import com.topcoder.util.datavalidator.LongValidator;
import com.topcoder.util.datavalidator.StringValidator;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

import java.lang.reflect.InvocationTargetException;

import java.util.HashMap;
import java.util.Map;


/**
 * This is the manager class of this component. It loads persistence implementation using settings in the
 * configuration namespace. Then using the persistence implementation to create/update/retrieve/search scorecards.
 * This is the main class of the component, which is used by client to perform the above scorecards operations. The
 * default configuration namespace for this class is: &quot;com.topcoder.management.scorecard&quot;. It can accept a
 * custom namespace as well. Apart from the persistence settings, it also initialize a SearchBundle instance to use in
 * scorecards searching and a ScorecardValidator instance to validate scorecards.<br>
 * Thread Safety: The implementation is not thread safe in that two threads running the same method will use the same
 * statement and could overwrite each other's work.
 *
 * In version 1.0.2, a new method named getDefaultScorecardsIDInfo is added
 * to retrieve the scorecard type ids and scorecard ids for a specific category from default scorecards table.
 *
 * @author tuenm
 * @author zhuzeyuan
 * @author George1
 * @author Angen
 *
 * @version 1.0.2
 */
public class ScorecardManagerImpl implements ScorecardManager {
    /**
     * The default namespace of this component. It will be used in the default constructor.
     */
    public static final String NAMESPACE = "com.topcoder.management.scorecard";

    /**
     * Represents the property name for the class name of the implementation of ScorecardPersistence.
     */
    private static final String PERSISTENCE_CLASS = "PersistenceClass";

    /**
     * Represents the property name for the namespace of the implementation of ScorecardPersistence.
     */
    private static final String PERSISTENCE_NAMESPACE = "PersistenceNamespace";

    /**
     * Represents the property name for the class name of the implementation of ScorecardValidator.
     */
    private static final String VALIDATOR_CLASS = "ValidatorClass";

    /**
     * Represents the property name for the namespace of the implementation of ScorecardValidator.
     */
    private static final String VALIDATOR_NAMESPACE = "ValidatorNamespace";

    /**
     * Represents the property name for the namespace of the implementation of SearchBuilder.
     */
    private static final String SEARCHBUILDER_NAMESPACE = "SearchBuilderNamespace";

    /**
     * The maximal length for ScorecardStatusName.
     */
    private static final int SCORECARD_STATUS_NAME_MAXLENGTH = 64;

    /**
     * The maximal length for ScorecardTypeName.
     */
    private static final int SCORECARD_TYPE_NAME_MAXLENGTH = 64;

    /**
     * The maximal length for ScorecardName.
     */
    private static final int SCORECARD_NAME_MAXLENGTH = 64;

    /**
     * The maximal length for ScorecardVersion.
     */
    private static final int SCORECARD_VERSION_MAXLENGTH = 16;

    /**
     * The persistence instance. It is initialized in the constructor and never changed after that. It is used in the
     * create/update/retrieve/search scorecard methods. It can never be null.
     */
    private final ScorecardPersistence persistence;

    /**
     * The search bundle instance. It is initialized in the constructor and never changed after that. It is used in
     * the search scorecard method. It can never be null.
     */
    private final SearchBundle searchBundle;

    /**
     * The scorecard validator instance. It is initialized in the constructor and never changed after that. It is used
     * to validate scorecards before create/update them. It can never be null.
     */
    private final ScorecardValidator validator;

    /**
     * Create a new instance of ScorecardManagerImpl using the default configuration namespace. First it load the
     * 'PersistenceClass' and 'PersistenceNamespace' properties to initialize the persistence plug-in implementation.
     * The 'PersistenceNamespace' is optional and if it does not present, value of 'PersistenceClass' property will be
     * used. Then it load the 'SearchBuilderNamespace' property to initialize SearchBuilder component.<br>
     * Please refer to Component Specification 1.3.8 how to initialize the SearchBuilder.
     * @throws ConfigurationException
     *             if error occurrs while loading configuration settings, or required configuration parameter is
     *             missing.
     */
    public ScorecardManagerImpl() throws ConfigurationException {
        // Use the default configuration namespace as the specified one.
        this(NAMESPACE);
    }

    /**
     * Create a new instance of ScorecardManagerImpl using the given configuration namespace. First it load the
     * 'PersistenceClass' and 'PersistenceNamespace' properties to initialize the persistence plug-in implementation.
     * The 'PersistenceNamespace' is optional and if it does not present, value of 'PersistenceClass' property will be
     * used. Then it load the 'SearchBuilderNamespace' property to initialize SearchBuilder component.
     * @param ns
     *            The namespace to load configuration settings from.
     * @throws IllegalArgumentException
     *             if the input is null or empty string.
     * @throws ConfigurationException
     *             if error occurrs while loading configuration settings, or required configuration parameter is
     *             missing.
     */
    public ScorecardManagerImpl(String ns) throws ConfigurationException {
        Helper.assertStringNotEmpty(ns, "namespace");

        // Get the instance of ConfigManager
        ConfigManager cm = ConfigManager.getInstance();

        try {
            // Create an instance from the class name and the namespace for persistence.
            persistence = (ScorecardPersistence) createObjectWithNamespace(cm.getString(
                        ns, PERSISTENCE_CLASS),
                    cm.getString(ns, PERSISTENCE_NAMESPACE),
                    "ScorecardPersistence");

            // Create an instance from the class name and the namespace for validator.
            validator = (ScorecardValidator) createObjectWithNamespace(cm.getString(
                        ns, VALIDATOR_CLASS),
                    cm.getString(ns, VALIDATOR_NAMESPACE), "ScorecardValidator");

            // Create a SearchBundleManager using the fetched SearchBuidlerNamespace.
            SearchBundleManager manager;

            try {
                manager = new SearchBundleManager(cm.getString(ns,
                            SEARCHBUILDER_NAMESPACE));
            } catch (SearchBuilderConfigurationException e) {
                throw new ConfigurationException("error while linking the SearchBundleManager",
                    e);
            } catch (NullPointerException e) {
                throw new ConfigurationException("namespace property for SearchBundleManager cannot be found",
                    e);
            }

            // Get the searchBundle from the manager.
            searchBundle = manager.getSearchBundle("ScorecardSearchBundle");

            if (searchBundle == null) {
                throw new ConfigurationException(
                    "searchBundle cannot be built correctly");
            }

            // Create a validationMap, and map the items to be checked with desired validator.
            // Please refer to Component Specification 1.3.8 for the details.
            Map validationMap = new HashMap();
            validationMap.put(ScorecardSearchBundle.SCORECARD_STATUS_ID,
                LongValidator.isPositive());
            validationMap.put(ScorecardSearchBundle.SCORECARD_TYPE_ID,
                LongValidator.isPositive());
            validationMap.put(ScorecardSearchBundle.PROJECT_CATEGORY_ID,
                LongValidator.isPositive());
            validationMap.put(ScorecardSearchBundle.PROJECT_ID,
                LongValidator.isPositive());
            validationMap.put(ScorecardSearchBundle.SCORECARD_STATUS_NAME,
                StringValidator.hasLength(IntegerValidator.lessThanOrEqualTo(
                        SCORECARD_STATUS_NAME_MAXLENGTH)));
            validationMap.put(ScorecardSearchBundle.SCORECARD_TYPE_NAME,
                StringValidator.hasLength(IntegerValidator.lessThanOrEqualTo(
                        SCORECARD_TYPE_NAME_MAXLENGTH)));
            validationMap.put(ScorecardSearchBundle.SCORECARD_NAME,
                StringValidator.hasLength(IntegerValidator.lessThanOrEqualTo(
                        SCORECARD_NAME_MAXLENGTH)));
            validationMap.put(ScorecardSearchBundle.SCORECARD_VERSION,
                StringValidator.hasLength(IntegerValidator.lessThanOrEqualTo(
                        SCORECARD_VERSION_MAXLENGTH)));

            // Set the SearchableFields with this validationMap.
            searchBundle.setSearchableFields(validationMap);
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("namespace '" + ns +
                "' is unknown.", e);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("the configuration values are invalid.",
                e);
        } catch (ClassCastException e) {
            throw new ConfigurationException("unable to create the object.", e);
        }
    }

    /**
     * Create an instance of the specified implementation of an object using reflection.
     * @param className
     *            The class name of the implementation of the desired object.
     * @param namespace
     *            The namespace of the implementation of the desired object. Can be null(ignored) if it is the same as
     *            className.
     * @param objectName
     *            The name of the object to be created.
     * @return the instance of an object
     * @throws ConfigurationException
     *             if any error occurs when creating the instance.
     * @throws IllegalArgumentException
     *             if the given className is null or empty.
     */
    private Object createObjectWithNamespace(String className,
        String namespace, String objectName) throws ConfigurationException {
        Helper.assertStringNotEmpty(className, "className");

        // If the namespace is not specified in the configuration, use its className instead.
        if (namespace == null) {
            namespace = className;
        }

        try {
            // Get the class object.
            Class klass = Class.forName(className);

            // Create the instance using the specified namespace.
            return klass.getConstructor(new Class[] { String.class })
                        .newInstance(new Object[] { namespace });
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("unable to create the " +
                objectName + " object.", e);
        } catch (InstantiationException e) {
            throw new ConfigurationException("unable to create the " +
                objectName + " object.", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("unable to create the " +
                objectName + " object.", e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException("unable to create the " +
                objectName + " object.", e);
        } catch (InvocationTargetException e) {
            throw new ConfigurationException("unable to create the " +
                objectName + " object.", e);
        }
    }

    /**
     * Create the scorecard in the database using the given scorecard instance. The scorecard instance can include sub
     * items such as groups, sections and questions. Those sub items will be persisted as well. The operator parameter
     * is used as the creation/modification user of the scorecard and its subitems. The creation date and modification
     * date will be the current date time when the scorecard is created. The given scorecard instance will be
     * validated before persisting.
     * @param scorecard
     *            The scorecard instance to be created in the database.
     * @param operator
     *            The creation user of this scorecard.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     * @throws ValidationException
     *             if error occurred while validating the scorecard instance.
     */
    public void createScorecard(Scorecard scorecard, String operator)
        throws PersistenceException, ValidationException {
        // Validate the scorecard first, and then pass it to the persistence.
        validator.validateScorecard(scorecard);
        persistence.createScorecard(scorecard, operator);
    }

    /**
     * Update the given scorecard instance into the database. The scorecard instance can include sub items such as
     * groups, sections and questions. Those sub items will be updated as well. If sub items are removed from the
     * scorecard, they will be deleted from the persistence. Likewise, if new sub items are added, they will be
     * created in the persistence. The operator parameter is used as the modification user of the scorecard and its
     * subitems. The modification date will be the current date time when the scorecard is updated. The given
     * scorecard instance will be validated before persisting.
     * @param scorecard
     *            The scorecard instance to be updated into the database.
     * @param operator
     *            The modification user of this scorecard.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     * @throws ValidationException
     *             if error occurred while validating the scorecard instance.
     */
    public void updateScorecard(Scorecard scorecard, String operator)
        throws PersistenceException, ValidationException {
        // Validate the scorecard first, and then pass it to the persistence.
        validator.validateScorecard(scorecard);
        persistence.updateScorecard(scorecard, operator);
    }

    /**
     * Retrieves the scorecard instance from the persistence given its id. The scorecard instance is retrieved with
     * its sub items, such as group, section and questions.
     * @return The scorecard instance.
     * @param id
     *            The id of the scorecard to be retrieved.
     * @throws IllegalArgumentException
     *             if the input id is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Scorecard getScorecard(long id) throws PersistenceException {
        Helper.assertIntegerGreaterThanZero(id, "id");

        return persistence.getScorecard(id, true);
    }

    /**
     * Search scorecard instances using the given filter parameter. The filter parameter decides the condition of
     * searching. This method use the Search Builder component to perform searching. The search condition can be the
     * combination of any of the followings:<br> - Scorecard name<br> - Scorecard version<br> - Scorecard type id<br> -
     * Scorecard type name<br> - Scorecard status id<br> - Scorecard status name<br> - Project category id that the
     * scorecard linked to.<br> - Project id that the scorecard linked to<br>
     * The filter is created using the ScorecardSearchBundle class. This class provide method to create filter of the
     * above condition and any combination of them.
     * @return An array of scorecard instance as the search result.
     * @param filter
     *            The filter to search for scorecards.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub items.
     * @throws IllegalArgumentException
     *             if the filter is null.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Scorecard[] searchScorecards(Filter filter, boolean complete)
        throws PersistenceException {
        Helper.assertObjectNotNull(filter, "filter");

        try {
            // Use the SearchBundle instance to search for ids
            CustomResultSet result = (CustomResultSet) searchBundle.search(filter);

            // If no search result found
            if (result.getRecordCount() == 0) {
                return new Scorecard[0];
            }

            // Use the ScorecardPersistence instance to get Scorecard instance array
            return persistence.getScorecards(result, complete);
        } catch (SearchBuilderException sbe) {
            throw new PersistenceException("Error exist while searching with searchBundle",
                sbe);
        } catch (ClassCastException cce) {
            throw new PersistenceException("Error exist while class casting",
                cce);
        }
    }

    /**
     * Retrieves all the scorecard types from the persistence.
     * @return An array of scorecard type instances.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ScorecardType[] getAllScorecardTypes() throws PersistenceException {
        return persistence.getAllScorecardTypes();
    }

    /**
     * Retrieves all the question types from the persistence.
     * @return An array of question type instances.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public QuestionType[] getAllQuestionTypes() throws PersistenceException {
        return persistence.getAllQuestionTypes();
    }

    /**
     * Retrieves all the scorecard statuses from the persistence.
     * @return An array of scorecard status instances.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ScorecardStatus[] getAllScorecardStatuses()
        throws PersistenceException {
        return persistence.getAllScorecardStatuses();
    }

    /**
     * <p>
     * Retrieves an array of the scorecard instances from the persistence given their ids. The scorecard instances can
     * be retrieved with or without its sub items, depends on the 'complete' parameter.
     * </p>
     * @param ids
     *            The array of ids of the scorecards to be retrieved.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub items.
     * @return An array of scorecard instances.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     * @throws IllegalArgumentException
     *             if the ids is less than or equal to zero. Or the input array is null or empty.
     */
    public Scorecard[] getScorecards(long[] ids, boolean complete)
        throws PersistenceException {
        return persistence.getScorecards(ids, complete);
    }

    /**
     * Retrieves the scorecard type ids and scorecard ids for a specific category
     * from default scorecards table.
     * This method takes as a parameter projectCategoryId, gets the
     * id information of scorecards for it, and returns an array of ScorecardIDInfo instances, each one
     * containing the scorecard type id and the scorecard id.
     *
     * @param projectCategoryId the id of the project category.
     *
     * @return the ScorecardIDInfo instances array, each one containing the scorecard type id and the scorecard id.
     *
     * @throws PersistenceException if error occurred while accessing the persistence.
     * @throws IllegalArgumentException if the projectCategoryId is less than or equal to zero.
     */
    public ScorecardIDInfo[] getDefaultScorecardsIDInfo(long projectCategoryId)
        throws PersistenceException {
        return persistence.getDefaultScorecardsIDInfo(projectCategoryId);
    }
}
