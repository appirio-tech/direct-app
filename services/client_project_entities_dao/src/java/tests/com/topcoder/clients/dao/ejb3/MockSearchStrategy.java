/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.topcoder.search.builder.SearchStrategy;
import com.topcoder.search.builder.filter.Filter;


/**
 * <p>
 * This is a Search Strategy that is tuned for searching a database via Hibernate's HQL.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class MockSearchStrategy implements SearchStrategy {

    /**
     * Creates a new <code>HibernateSearchStrategy</code> using the default namespace.
     */
    public MockSearchStrategy() {
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
     */
    @SuppressWarnings("unchecked")
    public Object search(String context, Filter filter, List returnFields,
        Map aliasMap) {
        return new ArrayList();
    }
}
