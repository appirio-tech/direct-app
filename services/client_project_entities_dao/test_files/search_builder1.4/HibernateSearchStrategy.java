/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.search.builder.hibernate;

import com.topcoder.search.builder.PersistenceOperationException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderHelper;
import com.topcoder.search.builder.SearchContext;
import com.topcoder.search.builder.SearchFragmentBuilder;
import com.topcoder.search.builder.SearchStrategy;
import com.topcoder.search.builder.UnrecognizedFilterException;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.util.classassociations.ClassAssociator;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * This is a Search Strategy that is tuned for searching a database via Hibernate's HQL.
 * </p>
 * <p>
 * It is responsible for building the necessary HQL search string appropriate to the
 * filters provided and executing the HQL against the database using a Session. This is
 * done with the help of the <code>SearchFragmentBuilder</code> implementations that are
 * provided in this package. Each <code>SearchFragmentBuilder</code> is responsible for
 * building the HQL for a specific filter, and a ClassAssociator is used to associate the
 * FragmentBuilders with the filters (making the filter - Fragment mapping easier).
 * </p>
 * <p>
 * A Query instance is used, and the search parameters are bound to the Query after the
 * entire HQL String has been generated.
 * </p>
 * <p>
 * <b>Sample code:</b>
 *
 * <pre>
 * HibernateSearchStrategy hss = new HibernateSearchStrategy(&quot;namespace&quot;);
 * Map aliasMap = new HashMap();
 * aliasMap.put(&quot;The age&quot;, &quot;p.age&quot;);
 * aliasMap.put(&quot;The company name&quot;, &quot;c.name&quot;);
 * hss.search(&quot;FROM Person as p join p.company as c WHERE&quot;, new EqualToFilter(
 *                 &quot;The age&quot;, new Integer(23)), new ArrayList(), aliasMap);
 * </pre>
 *
 * <b>Sample config:</b>
 *
 * <pre>
 *      &lt;CMConfig&gt; &lt;Config name=&quot;HibernateSearchStrategy&quot;&gt;
 *     &lt;!-- Property for defining the path of file from which Hibernate is configured. --&gt;
 *     &lt;Property name=&quot;hibernateConfigFilePath&quot;&gt;
 *     &lt;Value&gt;sampleHibernateConfig.cfg.xml&lt;/Value&gt;
 *     &lt;/Property&gt; &lt;Property name=&quot;searchFragmentFactoryNamespace&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.hibernate.factory&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;!-- Property for defining the mapping of Filter to corresponding FragmentBuilder --&gt;
 *     &lt;Property name=&quot;searchFragmentBuilders&quot;&gt;
 *     &lt;Property name=&quot;first&quot;&gt; &lt;Property name=&quot;targetFilter&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.filter.AndFilter&lt;/Value&gt;&lt;/Property&gt;
 *     &lt;Property name=&quot;className&quot;&gt;
 *     &lt;Value &gt;com.topcoder.search.builder.hibernate.AndFragmentBuilder&lt;/Value&gt;
 *     &lt;/Property&gt; &lt;/Property&gt; &lt;Property name=&quot;second&quot;&gt;
 *     &lt;Property name=&quot;targetFilter&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.filter.OrFilter&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;className&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.hibernate.OrFragmentBuilder&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;third&quot;&gt;
 *     &lt;Property name=&quot;targetFilter&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.filter.LikeFilter&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;className&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.hibernate.LikeFragmentBuilder&lt;/Value&gt;
 *     &lt;/Property&gt; &lt;/Property&gt; &lt;Property name=&quot;fourth&quot;&gt;
 *     &lt;Property name=&quot;targetFilter&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.filter.NotFilter&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;className&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.hibernate.NotFragmentBuilder&lt;/Value&gt;
 *     &lt;/Property&gt; &lt;/Property&gt; &lt;Property name=&quot;fifth&quot;&gt;
 *     &lt;Property name=&quot;targetFilter&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.filter.EqualToFilter&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;className&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.hibernate.EqualsFragmentBuilder&lt;/Value&gt;
 *     &lt;/Property&gt; &lt;/Property&gt; &lt;Property name=&quot;sixth&quot;&gt;
 *     &lt;Property name=&quot;targetFilter&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.filter.InFilter&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;className&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.hibernate.InFragmentBuilder&lt;/Value&gt;
 *     &lt;/Property&gt; &lt;/Property&gt;
 *     &lt;Property name=&quot;eighth&quot;&gt;
 *     &lt;Property name=&quot;targetFilter&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.filter.NullFilter&lt;/Value&gt;
 *     &lt;/Property&gt; &lt;Property
 *     name=&quot;className&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.hibernate.NullFragmentBuilder&lt;/Value&gt;
 *     &lt;/Property&gt; &lt;/Property&gt; &lt;Property name=&quot;ninth&quot;&gt;
 *     &lt;Property name=&quot;targetFilter&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.filter.GreaterThanFilter&lt;/Value&gt; &lt;/Property&gt;
 *     &lt;Property name=&quot;className&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.hibernate.RangeFragmentBuilder&lt;/Value&gt; &lt;/Property&gt;
 *     &lt;/Property&gt; &lt;Property name=&quot;tenth&quot;&gt; &lt;Property name=&quot;targetFilter&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter&lt;/Value&gt;
 *     &lt;/Property&gt; &lt;Property name=&quot;className&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.hibernate.RangeFragmentBuilder&lt;/Value&gt; &lt;/Property&gt;
 *     &lt;/Property&gt; &lt;Property name=&quot;eleventh&quot;&gt; &lt;Property name=&quot;targetFilter&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.filter.BetweenFilter&lt;/Value&gt; &lt;/Property&gt;
 *     &lt;Property name=&quot;className&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.hibernate.RangeFragmentBuilder&lt;/Value&gt;
 *     &lt;/Property&gt; &lt;/Property&gt; &lt;Property name=&quot;twelfth&quot;&gt;
 *     &lt;Property name=&quot;targetFilter&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.filter.LessThanOrEqualToFilter&lt;/Value&gt; &lt;/Property&gt;
 *     &lt;Property name=&quot;className&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.hibernate.RangeFragmentBuilder&lt;/Value&gt; &lt;/Property&gt;
 *     &lt;/Property&gt; &lt;Property name=&quot;thirteenth&quot;&gt; &lt;Property name=&quot;targetFilter&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.filter.LessThanFilter&lt;/Value&gt; &lt;/Property&gt;
 *     &lt;Property name=&quot;className&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.hibernate.RangeFragmentBuilder&lt;/Value&gt;
 *     &lt;/Property&gt; &lt;/Property&gt; &lt;/Property&gt;
 *     &lt;/Config&gt;
 *     &lt;Config name=&quot;com.topcoder.search.builder.hibernate.factory&quot;&gt;
 *     &lt;/Config&gt;
 *     &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Thread Safety: This class is thread safe. The state is maintained in a separate
 * SearchContext class, allowing concurrent calls to be supported. The search method
 * synchronizes on the session (obtained from Sessionfactory) to provide for thread
 * safety.
 * </p>
 *
 * @author kurtrips, myxgyy
 * @version 1.4
 * @since 1.4
 */
public class HibernateSearchStrategy implements SearchStrategy {
    /**
     * A String represent the 'hibenateConfigFilePath' property in the configuration.
     */
    private static final String HIBERNATE_CONFIG_FILE_PATH = "hibernateConfigFilePath";

    /**
     * A String represent the ','.
     */
    private static final String COMMA = ",";

    /**
     * A String represent the 'select'.
     */
    private static final String SELECT = "select";

    /**
     * A String represent the 'from'.
     */
    private static final String FROM = "from";

    /**
     * <p>
     * This variable represents the SessionFactory instance to be used for creating the
     * Session instance which in turn is used for executing the HQL query.
     * </p>
     * <p>
     * It is initially null. It cannot be set to null value. It is set in constructor and
     * is never changed afterwards.
     * </p>
     * <p>
     * It can be created either from inside this class using the Configuration class or
     * can be passed into the initialization constructor from an external source. If
     * created internally, then this instance is closed in the finalize method of this
     * class.
     * </p>
     * <p>
     * It is available to deriving classes using the namesake getter method. It is used by
     * the search method.
     * </p>
     */
    private final SessionFactory sessionFactory;

    /**
     * <p>
     * This is the classAssociator that will be mapping the Filter classes to their
     * respective SearchFragmentBuilders.
     * </p>
     * <p>
     * The keys are non-null Filter classes, and the values are non-null
     * SearchFragmentBuilders.
     * </p>
     * <p>
     * It is initialized in the constructor, and not changed/modified afterwards. It is
     * used for providing the SearchFragmentBuilders lookup in the SearchContext in
     * buildSearchContext method.
     * </p>
     */
    private final ClassAssociator fragmentBuilders;

    /**
     * <p>
     * This variable represents whether the SessionFactory being used by this class is
     * from an external source or is created inside the component.
     * </p>
     * <p>
     * Its initial value is false. It is set by the constructor and is never changed
     * afterwards. It is used by the finalize method to check if the sessionFactory needs
     * to be closed.
     * </p>
     */
    private boolean isExternalSessionFactory = false;

    /**
     * Creates a new <code>HibernateSearchStrategy</code> using the default namespace.
     *
     * @throws SearchBuilderConfigurationException
     *             if a configuration property that is required is not found, or if a
     *             configuration property does not make sense.
     */
    public HibernateSearchStrategy() throws SearchBuilderConfigurationException {
        this(HibernateSearchStrategy.class.getName());
    }

    /**
     * Creates the <code>HibernateSearchStrategy</code> from the given namespace.
     *
     * @param namespace
     *            The namespace in configuration from which to create the
     *            HibernateSearchStrategy.
     * @throws IllegalArgumentException
     *             If nameSpace is null or empty.
     * @throws SearchBuilderConfigurationException
     *             if a configuration property that is required is not found, or if a
     *             configuration property does not make sense.
     */
    public HibernateSearchStrategy(String namespace)
        throws SearchBuilderConfigurationException {
        if (namespace == null) {
            throw new IllegalArgumentException(
                "The namespace should not be null.");
        }

        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException(
                "The namespace should not be empty.");
        }

        this.fragmentBuilders = SearchBuilderHelper.loadClassAssociator(namespace);

        String filePath = HibernateHelper.getConfigPropertyValue(namespace,
                HIBERNATE_CONFIG_FILE_PATH, false);

        try {
            this.sessionFactory = (filePath == null)
                ? new AnnotationConfiguration().buildSessionFactory()
                : new AnnotationConfiguration().configure(filePath).buildSessionFactory();
        } catch (HibernateException ex) {
            throw new SearchBuilderConfigurationException("Initial SessionFactory creation failed.",
                ex);
        }
    }

    /**
     * Initialization constructor for this class.
     *
     * @param fragmentBuilders
     *            The ClassAssociator to use for this class.
     * @param sessionFactory
     *            The sessionFactory to set for this class.
     * @throws IllegalArgumentException
     *             If either parameter is null.
     */
    public HibernateSearchStrategy(SessionFactory sessionFactory,
        ClassAssociator fragmentBuilders) {
        if (sessionFactory == null) {
            throw new IllegalArgumentException(
                "The sessionFactory should not be null.");
        }

        if (fragmentBuilders == null) {
            throw new IllegalArgumentException(
                "The fragmentBuilders should not be null.");
        }

        this.sessionFactory = sessionFactory;
        this.fragmentBuilders = fragmentBuilders;
        isExternalSessionFactory = true;
    }

    /**
     * Searches the underlying persistence using Hibernate with the provided context,
     * filter and constraints the returnFields.
     *
     * @return If it is a FROM hibernate query, then returns a List of objects. If it is a
     *         SELECT hibernate query, then returns a List of multiple objects and/or
     *         properties as an array of type Object[]. Basically this is a list of object
     *         arrays.
     * @param context
     *            The search context. This would be an HQL statement for ex. "from Cat" or
     *            "select name, age from Cat" Note that Cat here is actually a name of OR
     *            mapped class. Hibernate is responsible for finding the underlying
     *            persistence table. Similarly name, age are properties of the Cat class.
     *            Hibernate is responsible for finding the underlying persistence columns.
     * @param filter
     *            The filter to use. This is typically used for providing the WHERE clause
     *            of the resultant query. An example could be an Equals filter with name =
     *            'age' and value = 10. Again, age is property of class Cat, not a
     *            database column. Another example could be an Equals filter with name =
     *            'cat.mother.name' and value = 'Momma'. Here mother is a property of Cat
     *            class with type as Cat and we are trying to find all cats such that
     *            their mother's name is Mommy.
     * @param returnFields
     *            The set of properties to return. This will be used only if it is a
     *            SELECT query. In case of a FROM query, this list is ignored.
     * @param aliasMap
     *            a map of strings, holding the alternate names of properties as keys and
     *            their actual property names as the respective values. Exceptions:
     * @throws UnrecognizedFilterException
     *             propagated from SearchFragmentBuilder.
     * @throws IllegalArgumentException
     *             if context or filter is null or if context is an empty String or if
     *             returnFields contains null/non-String contents or aliasMap contains
     *             null/empty String arguments.
     * @throws PersistenceOperationException
     *             to wrap any exception thrown by Hibernate.
     */
    public Object search(String context, Filter filter, List returnFields,
        Map aliasMap)
        throws UnrecognizedFilterException, PersistenceOperationException {
        if (context == null) {
            throw new IllegalArgumentException(
                "The context should not be null.");
        }

        if (context.trim().length() == 0) {
            throw new IllegalArgumentException(
                "The context should not be empty.");
        }

        if (filter == null) {
            throw new IllegalArgumentException(
                "The context should not be null.");
        }

        SearchBuilderHelper.checkList(returnFields, "returnFields", String.class);
        SearchBuilderHelper.checkaliasMap(aliasMap, "aliasMap");

        SearchContext searchContext = buildSearchContext(context, filter,
                returnFields, aliasMap);

        Session session;

        try {
            session = this.sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            throw new PersistenceOperationException(
                "Persistence error occurred: " + e.getMessage(), e);
        }

        synchronized (session) {
            try {
                // since the query need transaction supported
                if (!session.getTransaction().isActive()) {
                    session.beginTransaction();
                }


                Query query = session.createQuery(searchContext.getSearchString()
                                                               .toString());

                for (int i = 0;
                        i < searchContext.getBindableParameters().size();
                        i++) {
                    query.setParameter(i,
                        searchContext.getBindableParameters().get(i));
                }

                List ret = query.list();
                session.getTransaction().commit();

                return ret;
            } catch (HibernateException e) {
                rollBack(session);
                throw new PersistenceOperationException(
                    "Persistence error occurred: " + e.getMessage(), e);
            } finally {
                closeSession(session);
            }
        }
    }

    /**
     * <p>
     * Close the given session.
     * </p>
     *
     * @param session
     *            the session to be closed.
     * @throws PersistenceOperationException
     *             to wrap any exception thrown by Hibernate.
     */
    private static void closeSession(Session session)
        throws PersistenceOperationException {
        if ((session != null) && session.isOpen()) {
            try {
                session.close();
            } catch (HibernateException e) {
                throw new PersistenceOperationException(
                    "Error occurred when closing the session: " +
                    e.getMessage(), e);
            }

            session = null;
        }
    }

    /**
     * <p>
     * Roll back the transaction of the session.
     * </p>
     *
     * @param session
     *            the session to be roll back.
     * @throws PersistenceOperationException
     *             to wrap any exception thrown by Hibernate.
     */
    private static void rollBack(Session session)
        throws PersistenceOperationException {
        try {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } catch (HibernateException e) {
            throw new PersistenceOperationException(
                "Error occurred when closing the session: " + e.getMessage(), e);
        }
    }

    /**
     * This is a protected method that can be used to build the SearchContext for the
     * search. It may be overridden by subclasses to provide additional or different
     * methods of building up the search context. In this Strategy, the SearchContext is
     * used to hold the state of processing the Filters. The search String is built
     * separately from the binding parameters.
     *
     * @return A SearchContext object that is used to hold the state of building the
     *         search.
     * @param context
     *            The search context. This would be an HQL statement, for ex. "from Cat"
     *            or "select name, age from Cat" Note that Cat here is actually a name of
     *            OR mapped class. Hibernate is responsible for finding the underlying
     *            persistence table. Similarly name, age are properties of the Cat class.
     *            Hibernate is responsible for finding the underlying persistence columns.
     * @param filter
     *            The filter to use. This is typically used for providing the WHERE clause
     *            of the resultant query. An example could be an Equals filter with name =
     *            'age' and value = 10. Again, age is property of class Cat, not a
     *            database column. Another example could be an Equals filter with name =
     *            'cat.mother.name' and value = 'Momma'. Here mother is a property of Cat
     *            class with type as Cat and we are trying to find all cats such that
     *            their mother's name is Momma.
     * @param returnFields
     *            The set of properties to return. This will be used only if it is a
     *            SELECT query. In case of a FROM query, this list is ignored.
     * @param aliasMap
     *            a map of strings, holding the alternate names of properties as keys and
     *            their actual property names as the respective values. Exceptions:
     * @throws UnrecognizedFilterException
     *             Propagated from SearchFragmentBuilder.
     * @throws IllegalArgumentException
     *             If context is invalid HQL string. An HQL is considered invalid if it
     *             does not start with "SELECT" or "FROM". If it starts with "SELECT" it
     *             must also contain a "FROM".
     */
    protected SearchContext buildSearchContext(String context, Filter filter,
        List returnFields, Map aliasMap) throws UnrecognizedFilterException {
        SearchContext searchContext = new SearchContext(fragmentBuilders,
                aliasMap);
        searchContext.getSearchString()
                     .append(includeReturnFieldsInSearchString(context,
                returnFields, aliasMap));

        SearchFragmentBuilder builder = searchContext.getFragmentBuilder(filter);

        if (builder == null) {
            throw new UnrecognizedFilterException("No SearchFragmentBuilder can be retrieved.",
                filter);
        }

        builder.buildSearch(filter, searchContext);

        return searchContext;
    }

    /**
     * Return the sessionFactory variable.
     *
     * @return the sessionFactory variable.
     */
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * This is the destructor. It is used for disposing off the sessionFactory that was
     * created during the constructor. However if an external sessionFactory was used in
     * the constructor, then we do not close the sessionFactory because it may be used
     * outside this component.
     */
    protected void finalize() {
        if ((sessionFactory != null) && (!isExternalSessionFactory)) {
            sessionFactory.close();
        }
    }

    /**
     * This method is used to place the returnFields in the Select HQL query. Ultimately
     * the fields of the query will include both of those in the list and the context
     * string.
     *
     * @param aliasMap
     *            The map of aliases
     * @param context
     *            The initial HQL query.
     * @param fields
     *            The return fields to be added to the select query.
     * @return The modified HQL select query.
     * @throws IllegalArgumentException
     *             If the context string is not in correct format for a HQL query.
     */
    private String includeReturnFieldsInSearchString(String context,
        List fields, Map aliasMap) {
        String lowerCase = context.toLowerCase();

        // find the 'select' token
        int indexSelect = lowerCase.indexOf(SELECT);

        // find the 'from' token
        int indexFrom = lowerCase.indexOf(FROM);

        // check the context String
        if (!lowerCase.startsWith(SELECT) && !lowerCase.startsWith(FROM)) {
            throw new IllegalArgumentException(
                "The context should start with \"SELECT\" or \"FROM\".");
        }

        if (lowerCase.startsWith(SELECT) && (indexFrom < 0)) {
            throw new IllegalArgumentException(
                "If context starts with \"SELECT\" it must also contain a \"FROM\".");
        }

        if (lowerCase.startsWith(FROM)) {
            return context + " ";
        }

        String[] existingFieldsArray = context.substring(indexSelect +
                SELECT.length(), indexFrom).trim().split(COMMA);

        String newFields = getFields(fields, aliasMap);
        String existingFields = getFields(Arrays.asList(existingFieldsArray),
                aliasMap);

        if (newFields.length() == 0) {
            // just append nothing
            newFields = "";
        } else {
            if (existingFields.length() != 0) {
                // should have a comma first
                newFields = COMMA + newFields;
            }
        }

        return "SELECT " + existingFields + newFields + " FROM "
            + context.substring(indexFrom + FROM.length()).trim() + " ";
    }

    /**
     * Build the fields String for existing fields and new fields.
     *
     * @param fields
     *            The fields to be added to the select query
     * @param aliasMap
     *            The map of aliases
     * @return the fields string.
     */
    private static String getFields(List fields, Map aliasMap) {
        StringBuffer ret = new StringBuffer();

        for (int i = 0; i < fields.size(); i++) {
            String value = ((String) fields.get(i)).trim();

            if (aliasMap.containsKey(value)) {
                ret.append(aliasMap.get(value));
            } else {
                ret.append(value);
            }

            if (i < (fields.size() - 1)) {
                ret.append(COMMA);
            }
        }

        return ret.toString();
    }
}
