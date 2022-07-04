/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.io.Serializable;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * The class to provide a extensible way for including custom attributes for both <code>Project</code> and
 * <code>Phase</code> classes. The attributes are stored in key-value pairs, the type of key and value can vary in
 * different situations, so they are both stored in Serializable object type for best extensibility. This class is
 * serializable.
 * <p>
 * <b>Thread Safety:</b> This class is not thread safe. Since all the accesses to the attributes map are not
 * synchronized.
 * </p>
 *
 * @author oldbig, littlebull
 * @version 2.0
 */
@XmlSeeAlso({Phase.class,Project.class})
public abstract class AttributableObject implements Serializable {
    /**
     * Represents the map to store the custom attributes. This variable is initialized in the constructor to an
     * empty <code>HashMap</code> and accessed in those add/get/remove/clear methods.
     */
    private Map attributes;

    /**
     * Create a new instance of <code>AttributableObject</code>.
     */
    public AttributableObject() {
        attributes = new HashMap();
    }

    /**
     * Return the corresponding value to the specified key, null is returned if there is no corresponding value.
     *
     * @return the corresponding value to the specified key, null is returned if there is no corresponding value.
     * @param key
     *            the key of the value to return
     * @throws IllegalArgumentException
     *             if the given <code>key</code> is null
     */
    public Serializable getAttribute(Serializable key) {
        ProjectPhaseHelper.checkObjectNotNull(key, "key");

        return (Serializable) attributes.get(key);
    }

    /**
     * Return a unmodifiable shallow copy of inner attributes map.
     *
     * @return a unmodifiable shallow copy of inner attributes map.
     */
    public Map getAttributes() {
        return attributes;
    }

	 /**
     * Return a unmodifiable shallow copy of inner attributes map.
     *
     * @return a unmodifiable shallow copy of inner attributes map.
     */
    public void setAttributes(Map attributes) {
		if (attributes != null)
		{
			 this.attributes = attributes;
		}
       
    }

    /**
     * Add the key-value pair into the attributes map, if the key is already exist, the old value will be overridden by
     * the new value.
     *
     * @param key
     *            the key of the attribute to add, cannot be null
     * @param value
     *            the value of the attribute to add, cannot be null
     * @throws IllegalArgumentException
     *             if the given <code>key</code> or <code>value</code> is null
     */
    public void setAttribute(Serializable key, Serializable value) {
        ProjectPhaseHelper.checkObjectNotNull(key, "key");
        ProjectPhaseHelper.checkObjectNotNull(value, "value");

        attributes.put(key, value);
    }

    /**
     * Remove key-value pair from the attributes map, if the key does not exist, nothing will happen. The removed value
     * will be returned if the key exists, null will be returned if the key does not exist.
     *
     * @return the removed value to the key if exist, null otherwise
     * @param key
     *            the key to remove
     * @throws IllegalArgumentException
     *             if the given <code>key</code> is null
     */
    public Serializable removeAttribute(Serializable key) {
        ProjectPhaseHelper.checkObjectNotNull(key, "key");

        return (Serializable) attributes.remove(key);
    }

    /**
     * Clear all the attributes from the map.
     */
    public void clearAttributes() {
        attributes.clear();
    }
}
