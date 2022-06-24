/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import com.topcoder.catalog.entity.Category;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>This class represents the search criteria that is used when finding assets.</p>
 * Here is how they are used:
 * <ul>
 *  <li><tt>userId</tt>: If a <code>userId</code> is specified, the asset must meet one or of these conditions:
 *   <ol>
 *    <li>In the AssetDTO, users list must contain a user with that user id</li>
 *    <li>In the AssetDTO, clients list must contain a client id which is associated with the user id
 *        (via <code>CompUser</code> entity)</li>
 *    </ol>
 *  </li>
 * <li><tt>clientId</tt>: in the AssetDTO, clients list must contain the client id.</li>
 * <li>List of <tt>categories</tt>: given a list of category ids, the root category of the asset must be
 * one of those.</li>
 * <li>the AssetDTO <tt>name</tt>: seeks for partial case insensitive matches.</li>
 * <li><tt>descriptions</tt>: it must search in all the description fields,
 *   seeks for partial case insensitive matches</li>
 * <li>the <tt>compCreateBeforeDate</tt>: seeks for components create before.</li>
 * <li>the <tt>compCreateAfterDate</tt>: seeks for components create after.</li>
 * <li>the <tt>compVersionCreateBeforeDate</tt>: seeks components create before.</li>
 * <li>the <tt>compVersionCreateAfterDate</tt>: seeks components create after.</li>
 * </ul>
 * Example of finding assets in the persistence by a given criteria:
 * <pre>
 *      List&lt;AssetDTO&gt; assets = remote.findAssets(
 *          new SearchCriteria(null, null, null, "catalog", null, null, null, null, null), true);
 * </pre>
 * Suppose we have in catalog Catalog Service and Catalog Entities assets. The list will contain them both.
 * <p><strong>Thread safety: </strong></p> <p>This class is immutable and thread safe.</p>
 * <p>
 * version 1.1 add 4 new criteria(component create after/before date and component version create after/before date).
 * </p>
 * @author caru, Retunsky, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class SearchCriteria implements Serializable {
    /**
     * <p>This field represents an id of a user to seek assets.</p>
     * <p>The initial value is assigned in the constructor. Access is performed via its getter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private final Long userId;
    /**
     * <p>This field represents an id of a client to seek assets.</p>
     * <p>The initial value is assigned in the constructor. Access is performed via its getter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private final Long clientId;
    /**
     * <p>This field represents root categories of a component to seek assets.</p>
     * <p>The initial value is assigned in the constructor. Access is performed via its getter.</p>
     * <p>The acceptance region: any non-empty <code>List</code> without null elements or <code>null</code>.</p>
     */
    private final List<Category> categories;
    /**
     * <p>This field represents a part of the name of a component to seek assets.</p>
     * <p>The initial value is assigned in the constructor. Access is performed via its getter.</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     */
    private final String name;
    /**
     * <p>This field represents a part of any description of a component.</p>
     * <p>The initial value is assigned in the constructor. Access is performed via its getter.</p>
     * <p>The acceptance region: any <code>String</code> value or <code>null</code>.</p>
     */
    private final String description;

    /**
     * <p>This field represents date before components created.</p>
     * <p>The initial value is assigned in the constructor. Access is performed via its getter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <code>null</code>.</p>
     * @since 1.1
     */
    private final Date compCreateBeforeDate;

    /**
     * <p>This field represents date after components created.</p>
     * <p>The initial value is assigned in the constructor. Access is performed via its getter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <code>null</code>.</p>
     * @since 1.1
     */
    private final Date compCreateAfterDate;

    /**
     * <p>This field represents date before component versions created.</p>
     * <p>The initial value is assigned in the constructor. Access is performed via its getter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <code>null</code>.</p>
     * @since 1.1
     */
    private final Date compVersionCreateBeforeDate;

    /**
     * <p>This field represents date after component versions created.</p>
     * <p>The initial value is assigned in the constructor. Access is performed via its getter.</p>
     * <p>The acceptance region: any <code>Date</code> value or <code>null</code>.</p>
     * @since 1.1
     */
    private final Date compVersionCreateAfterDate;


    /**
     * <p>Create a new instance with specified values for fields.</p>
     *
     * @param userId an id of a user
     * @param clientId an id of a client
     * @param categories root categories of a component
     * @param name  a part of the name of a component
     * @param description a part of any description of a component
     * @param compCreateAfterDate date after components created
     * @param compCreateBeforeDate date before components created
     * @param compVersionCreateAfterDate date after component versions created
     * @param compVersionCreateBeforeDate date before component versions created
     * @throws IllegalArgumentException if all search criteria are null or empty (if Strings)
     *                       categories contains null elements or is an empty list.
     */
    public SearchCriteria(Long userId, Long clientId, List<Category> categories, String name,
            String description, Date compCreateAfterDate, Date compCreateBeforeDate,
            Date compVersionCreateAfterDate, Date compVersionCreateBeforeDate) {
        if ((description == null || description.trim().length() == 0)
                && (name == null || name.trim().length() == 0) && categories == null
                && clientId == null && userId == null && compCreateBeforeDate == null
                && compCreateAfterDate == null && compVersionCreateBeforeDate == null
                && compVersionCreateAfterDate == null) {
            throw new IllegalArgumentException("There should be at least one non-null parameter");
        }
        if (categories != null && (categories.isEmpty() || categories.indexOf(null) >= 0)) {
            throw new IllegalArgumentException("Categories list cannot be empty or contain null values");
        }
        // sign fields
        this.description = description;
        this.name = name;
        this.categories = categories == null ? null : new ArrayList<Category>(categories);
        this.clientId = clientId;
        this.userId = userId;
        this.compCreateBeforeDate = compCreateBeforeDate;
        this.compCreateAfterDate = compCreateAfterDate;
        this.compVersionCreateBeforeDate = compVersionCreateBeforeDate;
        this.compVersionCreateAfterDate = compVersionCreateAfterDate;
    }

    /**
     * <p>Retrieves an id of a user to seek assets.</p>
     *
     * @return an id of a user to seek assets
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * <p>Retrieves an id of a client to seek assets.</p>
     *
     * @return an id of a client to seek assets
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * <p>Retrieves root categories to seek assets.</p>
     *
     * @return root categories to seek assets
     */
    public List<Category> getCategories() {
        return categories == null ? null : new ArrayList<Category>(categories);
    }

    /**
     * <p>Retrieves a part of the name of a component to seek assets.</p>
     *
     * @return a part of the name of a component to seek assets
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Retrieves a part of any description of a component to seek assets.</p>
     *
     * @return a part of any description of a component to seek assets
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Retrieves date before components created to seek assets.</p>
     *
     * @return date before components created to seek assets
     * @since 1.1
     */
    public Date getCompCreateBeforeDate() {
        return compCreateBeforeDate;
    }

    /**
     * <p>Retrieves date after components created to seek assets.</p>
     *
     * @return date after components created to seek assets
     * @since 1.1
     */
    public Date getCompCreateAfterDate() {
        return compCreateAfterDate;
    }

    /**
     * <p>Retrieves date before component versions created to seek assets.</p>
     *
     * @return date before component versions created to seek assets
     * @since 1.1
     */
    public Date getCompVersionCreateBeforeDate() {
        return compVersionCreateBeforeDate;
    }

    /**
     * <p>Retrieves date after component versions created to seek assets.</p>
     *
     * @return date after component versions created to seek assets
     * @since 1.1
     */
    public Date getCompVersionCreateAfterDate() {
        return compVersionCreateAfterDate;
    }
}



