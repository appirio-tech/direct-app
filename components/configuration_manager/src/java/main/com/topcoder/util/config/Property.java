/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) Property.java
 *
 * 1.0  05/07/2003
 */
package com.topcoder.util.config;

import java.util.Vector;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.io.Serializable;

/**
 * An element of configuration data representing some property. A property may
 * have single or multiple values and zero or more nested subproperties. A
 * property is described with name uniquely identifying it among other
 * properties. While <code>Property</code> has public <i>get</i>-interface
 * it's <i>set</i>-interface is hidden from user. The content of existing
 * property can be manipulated only with use of appropriate
 * <code>ConfigManager's</code> methods. However, this class provides a set
 * of public constructors sufficient to create a property with given name and
 * value or array of values.
 *
 * <p>The <code>Property</code> object serves as a "root"-context for all
 * properties nested directly or indirectly within it. The methods manipulating
 * with property names accept a compound name pointing to any property nested
 * within this <code>Property</code>. A compound name is a dot-separated <code>
 * String</code> each part of which represents a name of a single level
 * property. For example : a property name "countries.USA.currency" points to
 * property named "currency" that is nested within property named "USA" that
 * is nested within property named ""countries".
 *
 * @author  isv
 * @author  WishingBone
 * @version 1.0  05/07/2003
 * @since   Configuration Manager 2.1
 */
public class Property implements Cloneable, Serializable {

    /**
     * A name of this property
     */
    private String name = null;

    /**
     * A List of string comments associated with this element. To leverage the
     * memory usage the memory space for comments is allocated only when there
     * is a first attempt to add some comment to this list.
     */
    private List comments = null;

    /**
     * A list of properties directly nested within this property.
     */
    private List subproperties = null;

    /**
     * A <code>List</code> of mulpile <code>String</code> values associated
     * with this <code>Property</code>
     */
    private List values = null;

    /**
     * A single value associated with this <code>Property</code>
     */
    private String value = null;

    /**
     * The separator associated with this property.
     */
    private char separator = '=';

    /**
     * Gets the separator associated with this property.
     *
     * @return the separator associated with this property.
     */
    char getSeparator() {
        return separator;
    }

    /**
     * Sets the separator associated with this property.
     */
    void setSeparator(char separator) {
        this.separator = separator;
    }

    /**
     * Constructs a new <code>Property</code> with given <code>name</code> and
     * empty lists of nested properties and associated values.
     *
     * @param  name a name of this property uniquely identifying it among
     *         others
     * @throws IllegalArgumentException if given <code>name</code> is <code>
     *         empty</code> or is a <code>compound name</code>
     * @throws NullPointerException if given <code>name</code> is <code>null
     *         </code>
     */
    public Property(String name) {
        if (name == null) {
            throw new NullPointerException("parameter name is null");
        }
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("parameter name is empty");
        }
        if (name.indexOf('.') != -1) {
            throw new IllegalArgumentException("parameter name is a compound name");
        }
        this.name = name;
        subproperties = new ArrayList();
        values = new ArrayList();
    }

    /**
     * Constructs a new <code>Property</code> with given <code>name</code> and
     * given value.
     *
     * @param  name a name of this property uniquely identifying it among
     *         others
     * @param  value a value of this property
     * @throws IllegalArgumentException if given <code>name</code> is <code>
     *         empty</code> or is a compound name
     * @throws NullPointerException if any of given <code>name</code> or <code>
     *         value<code> is <code>null</code>
     */
    public Property(String name, String value) {
        this(name);
        if (value == null) {
            throw new NullPointerException("parameter value is null");
        }
        this.value = value;
    }

    /**
     * Constructs a new <code>Property</code> with given <code>name</code> and
     * given list of associated values.
     *
     * @param  name a name of newly created property uniquely identifying it
     *         among others
     * @param  values a list of values associated with this property
     * @throws IllegalArgumentException if given <code>name</code> is <code>
     *         empty</code> or is a <code>compound name</code>
     * @throws NullPointerException if any of given <code>name</code> or <code>
     *         values<code> is <code>null</code>
     */
    public Property(String name, String[] values) {
        this(name);
        if (values == null) {
            throw new NullPointerException("paramter values is null");
        }
        for (int i = 0; i < values.length; ++i) {
            if (values[i] == null) {
                throw new NullPointerException("parameter values contains a null entry");
            }
        }
        if (values.length == 1) {
            value = values[0];
        } else {
            for (int i = 0; i < values.length; ++i) {
                this.values.add(values[i]);
            }
        }
    }

    /**
     * Constructs a special "root" property with empty name. Such "root"
     * property should be used by <code>ConfigProperties</code> as an
     * entry-point to whole properties hierarchy tree. Using such property
     * simplifies the manipulation with properties tree from owning <code>
     * ConfigProperties</code> objects.
     */
    Property() {
        name = "";
        subproperties = new ArrayList();
        values = new ArrayList();
    }

    /**
     * Adds property specified with given name and value to list of actual
     * properties nested directly within this property. Creates a
     * <code>Property</code> object with given name holding given value.
     *
     * @param  name a name of added property
     * @param  value a value of added property
     * @throws DuplicatePropertyException if property with given <code>name
     *         </code> already exists within this <code>Property</code>
     * @throws IllegalArgumentException if given <code>name</code> is <code>
     *         empty</code>
     * @throws NullPointerException if any of given <code>name</code> or
     *         <code>value<code> is <code>null</code>
     */
    void addProperty(String name, String value) throws DuplicatePropertyException {
        if (name == null) {
            throw new NullPointerException("parameter name is null");
        }
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("parameter name is empty");
        }
        if (value == null) {
            throw new NullPointerException("parameter value is null");
        }

        synchronized (this) {
        	if (find(name) != null) {
        		throw new DuplicatePropertyException("property " + name + " already exists");
        	}
        	create(name).setValue(value);
        }
    }

    /**
     * Adds property specified with given name and values to list of
     * properties nested directly within this property. Creates a
     * <code>Property</code> object from given name holding given values.
     *
     * @param  name a name of added property
     * @param  values a values of added property
     * @throws DuplicatePropertyException if property with given <code>name
     *         </code> already exists within this <code>Property</code>
     * @throws IllegalArgumentException if given <code>name</code> is <code>
     *         empty</code>
     * @throws NullPointerException if any of given <code>name</code> or
     *         <code>values<code> is <code>null</code>
     */
    void addProperty(String name, String[] values) throws DuplicatePropertyException {
        if (name == null) {
            throw new NullPointerException("parameter name is null");
        }
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("parameter name is empty");
        }
        if (values == null) {
            throw new NullPointerException("parameter values is null");
        }
        if (values.length == 0) {
            throw new IllegalArgumentException("parameter values contains no entry");
        }
        for (int i = 0; i < values.length; ++i) {
            if (values[i] == null) {
                throw new NullPointerException("parameter values contains null entry");
            }
        }

        synchronized (this) {
        	if (find(name) != null) {
        		throw new DuplicatePropertyException("property " + name + " already exists");
        	}
        	create(name).setValues(values);
        }
    }

    /**
     * Adds specified <code>Property</code> to list of properties directly
     * nested within this <code>Property</code>
     *
     * @param  property a <code>Property</code> to add
     * @throws DuplicatePropertyException if property with given <code>name
     *         </code> already exists within this <code>Property</code>
     * @throws NullPointerException if given <code>property</code> is <code>
     *         null</code>
     */
    void addProperty(Property property) throws DuplicatePropertyException {
        if (property == null) {
            throw new NullPointerException("parameter property is null");
        }

        synchronized (this) {
        	for (Iterator itr = subproperties.iterator(); itr.hasNext();) {
        		if (((Property) itr.next()).name.equals(property.name)) {
        			throw new DuplicatePropertyException("property " + name + " already exists");
        		}
        	}

        	subproperties.add(property);
        }
    }

    /**
     * Adds specified value to list of values associated with this property.
     *
     * @param  value a value to add to property
     * @throws NullPointerException if given <code>value</code> is <code>null
     *         </code>
     */
    void addValue(String value) {
        if (value == null) {
            throw new NullPointerException("parameter value is null");
        }

        synchronized (this) {
        	if (this.value != null) {
        		values.add(this.value);
        		values.add(value);
        		this.value = null;
        	} else if (values.size() == 0) {
        		this.value = value;
        	} else {
        		values.add(value);
        	}
        }
    }

    /**
     * Removes property specified with given name from list of configuration
     * elements nested within this property. The name maybe a compound name
     * pointing to property indirectly nested within this property.
     *
     * @param  name a name of nested property to remove from this property
     * @throws IllegalArgumentException if given <code>name</code> is
     *         <code>empty</code>
     * @throws NullPointerException if given <code>subPropertyName</code> is
     *         <code>null</code>
     */
    void removeProperty(String name) {
        if (name == null) {
            throw new NullPointerException("parameter name is null");
        }
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("parameter name is empty");
        }

        synchronized (this) {
	        // locate property
	        int lastPos = name.lastIndexOf('.');
	        Property property;
	        if (lastPos == -1) {
	            property = this;
	        } else {
	            property = find(name.substring(0, lastPos));
	            if (property == null) {
	                return;
	            }
	        }

	        // remove it
	        name = name.substring(lastPos + 1);
	        for (Iterator itr = property.subproperties.iterator(); itr.hasNext();) {
	            if (((Property) itr.next()).name.equals(name)) {
	                itr.remove();
	            }
	        }
        }
    }

    /**
     * Removes value specified with given value from list of values directly
     * nested within this property.
     *
     * @param  value a value to remove from this property
     * @throws NullPointerException if given <code>value</code> is <code>null
     *         </code>
     */
    void removeValue(String value) {
        if (value == null) {
            throw new NullPointerException("parameter value is null");
        }

        synchronized (this) {
	        if (this.value != null) {
	            if (this.value.equals(value)) {
	                this.value = null;
	            }
	        } else {
	            values.remove(value);
	            if (values.size() == 1) {
	                this.value = (String) values.get(0);
	                values.clear();
	            }
	        }
        }
    }

    /**
     * Sets the value of this <code>Property</code>. Any existing values are
     * removed.
     *
     * @param  value a new value of this <code>Property</code>
     * @throws NullPointerException if given <code>value</code> is <code>null
     *         </code>
     */
    void setValue(String value) {
        if (value == null) {
            throw new NullPointerException("parameter value is null");
        }

        synchronized (this) {
        	this.value = value;
        	values.clear();
        }
    }

    /**
     * Associates given values with this <code>Property</code>. Any existing
     * values are removed.
     *
     * @param  values a new values of this <code>Property</code>
     * @throws IllegalArgumentException if given array is a zero-length array
     * @throws NullPointerException if given <code>values</code> is <code>null
     *         </code>
     */
    void setValues(String[] values) {
        if (values == null) {
            throw new NullPointerException("parameter values is null");
        }
        if (values.length == 0) {
            throw new IllegalArgumentException("parameter values contains no entry");
        }
        for (int i = 0; i < values.length; ++i) {
            if (values[i] == null) {
                throw new NullPointerException("parameter values contains null entry");
            }
        }

        synchronized (this) {
	        value = null;
	        this.values.clear();
	        if (values.length == 1) {
	            value = values[0];
	        } else {
	            for (int i = 0; i < values.length; ++i) {
	                this.values.add(values[i]);
	            }
	        }
        }
    }

    /**
     * Sets the value of nested property specified by given <code>key</code>
     * to given <code>value</code>. A <code>key</code> represents a compound
     * name pointing to property nested directly or indirectly within this
     * <code>Property</code>.
     * If nested property specified by given <code>key</code> does not exist
     * than creates a new one(and possibly the whole properties chain) and
     * puts it into this <code>Property</code>.
     *
     * @param  key a compound name of nested property
     * @param  value a value of requested property
     * @throws IllegalArgumentException if any of given <code>key</code> or
     *         <code>value</code> is <code>empty</code> or <code>key</code>
     *         is not a valid property name
     * @throws NullPointerException if any of given <code>key</code> or <code>
     *         value</code> is <code>null</code>
     */
    void setProperty(String key, String value) {
        if (key == null) {
            throw new NullPointerException("parameter key is null");
        }
        if (key.trim().length() == 0) {
            throw new IllegalArgumentException("parameter key is empty");
        }
        if (value == null) {
            throw new NullPointerException("parameter value is null");
        }

        synchronized (this) {
        	Property property = find(key);
        	if (property == null) {
        		property = create(key);
        	}
        	property.setValue(value);
        }
    }

    /**
     * Sets the value of nested property specified by given <code>key</code>
     * to given <code>values</code>. A <code>key</code> represents a compound
     * name pointing to property nested directly or indirectly within this
     * <code>Property</code>.
     * If nested property specified by given <code>key</code> does not exist
     * than creates a new one(and possibly the whole properties chain) and
     * puts it into this <code>Property</code>.
     *
     * @param  key a compound name of nested property
     * @param  values an array of values to associate with this Property
     * @throws IllegalArgumentException if given <code>key</code> is empty or
     *         <code>values</code> is <code>zero-length</code> array or <code>
     *         key</code> is not a valid property name
     * @throws NullPointerException if any of given <code>key</code> or <code>
     *         values</code> is <code>null</code>
     */
    void setProperty(String key, String[] values) {
        if (key == null) {
            throw new NullPointerException("parameter key is null");
        }
        if (key.trim().length() == 0) {
            throw new IllegalArgumentException("parameter key is empty");
        }
        if (values == null) {
            throw new NullPointerException("parameter values is null");
        }
        if (values.length == 0) {
            throw new IllegalArgumentException("parameter values contains no entry");
        }
        for (int i = 0; i < values.length; ++i) {
            if (values[i] == null) {
                throw new NullPointerException("parameter values contains null entry");
            }
        }

        synchronized (this) {
	        Property property = find(key);
	        if (property == null) {
	            property = create(key);
	        }
	        property.setValues(values);
        }
    }

    /**
     * Checks whether this property contains nested property with given name or
     * not. The name maybe a compound name pointing to property indirectly
     * nested within property nested within this property.
     *
     * @param  property a name of nested property to check for
     * @return <code>true</code> if this property contains property with given
     *         name
     * @throws NullPointerException if given <code>property</code> is <code>
     *         null</code>
     */
    public boolean containsProperty(String property) {
        if (property == null) {
            throw new NullPointerException("parameter property is null");
        }
        if (property.trim().length() == 0) {
            return false;
        }

        return find(property) != null;
    }

    /**
     * Checks whether this property contains given value or not.
     *
     * @param  value a value of this property to check for
     * @return <code>true</code> if this property contains given value
     * @throws NullPointerException if given <code>value</code> is <code>
     *         null</code>
     */
    public boolean containsValue(String value) {
        if (value == null) {
            throw new NullPointerException("parameter value is null");
        }

        synchronized (this) {
	        if (this.value != null) {
	            return this.value.equals(value);
	        } else {
	            return values.contains(value);
	        }
        }
    }

    /**
     * Gets the value of this <code>Property</code>. If this <code>Property
     * </code> contains several values returns the first one.
     *
     * @return a value of this <code>Property</code> or <code>null</code> if
     *         this <code>Property</code> does not contain any value
     */
    public String getValue() {
    	synchronized (this) {
	        if (value != null) {
	            return value;
	        } else if (values.size() > 0) {
	            return (String) values.get(0);
	        } else {
	            return null;
	        }
    	}
    }

    /**
     * Gets the value of this <code>Property</code>. If this <code>Property
     * </code> contains several values returns the first one.
     *
     * @param  name a name of property directly or indirectly nested within
     *         this <code>Property</code>
     * @return a value of this <code>Property</code> or <code>null</code> if
     *         this <code>Property</code> does not contain any value
     * @throws NullPointerException if given <code>name</code> is <code>null
     *         </code>
     * @throws IllegalArgumentException if given <code>name</code> is <code>
     *         empty</code>
     */
    public String getValue(String name) {
        if (name == null) {
            throw new NullPointerException("parameter name is null");
        }
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("parameter name is empty");
        }

        Property property = find(name);
        if (property == null) {
            return null;
        } else {
            return property.getValue();
        }
    }

    /**
     * Gets the value of this <code>Property</code>. If this <code>Property
     * </code> contains several values returns the first one.
     *
     * @param  name a name of property directly nested within this <code>
     *         Property</code>
     * @return a value of this <code>Property</code> or <code>null</code> if
     *         this <code>Property</code> does not contain any value
     * @throws NullPointerException if given <code>name</code> is <code>null
     *         </code>
     * @throws IllegalArgumentException if given <code>name</code> is <code>
     *         empty</code>
     */
    public String[] getValues(String name) {
        if (name == null) {
            throw new NullPointerException("parameter name is null");
        }
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("parameter name is empty");
        }

        Property property = find(name);
        if (property == null) {
            return null;
        } else {
            return property.getValues();
        }
    }

    /**
     * Gets the values associated with this <code>Property</code> as <code>
     * String</code> array.
     *
     * @return an array of string values associated with this <code>Property
     *         </code> or <code>null</code> if this <code>Property</code> does
     *         not contain any value
     */
    public String[] getValues() {
    	synchronized (this) {
	        if (value != null) {
	            return new String[] {value};
	        } else if (values.size() > 0) {
	            return (String[]) values.toArray(new String[values.size()]);
	        } else {
	            return null;
	        }
    	}
    }

    /**
     * Gets the <code>Property</code> objects specified by given name nested
     * directly or indirectly within this <code>Property</code>.
     *
     * @param  name a compound name pointing to requested property
     * @return a <code>Property</code> object corresponding to given name or
     *         <code>null</code> if property with given <code>name</code> does
     *         not exist
     * @throws NullPointerException if given <code>name</code> is <code>null
     *         </code>
     */
    public Property getProperty(String name) {
        if (name == null) {
            throw new NullPointerException("parameter name is null");
        }
        return find(name);
    }

    /**
     * Gets the list of properties directly nested within this <code>Property
     * </code>. Returned <code>List</code> is independent of this <code>
     * Property</code>, i.e. the changes made to this <code>List</code> are not
     * reflected in list of properties directly nested within this <code>
     * Property</code>.
     *
     * @return a <code>List</code> of properties directly nested within this
     *         <code>Property</code>
     */
    public List list() {
        synchronized (this) {
            return new ArrayList(subproperties);
        }
    }

    /**
     * Gets the enumeration through the names of properties directly nested
     * within this <code>Property</code>
     *
     * @return an <code>Enumeration</code> containing the names of properties
     *         directly nested within this <code>Property</code>
     */
    public Enumeration propertyNames() {
        synchronized (this) {
            Vector names = new Vector();
            for (Iterator itr = subproperties.iterator(); itr.hasNext();) {
                names.add(((Property) itr.next()).name);
            }
            return names.elements();
        }
    }

    /**
     * Gets the property contained within this property specified by given name.
     * The specified name maybe a compound name. In such case this method will
     * try to find a deepest nested configuration element specified by this name.
     *
     * @param  name a name of required element
     * @return a <code>Property</code> with given name if any exists within
     *         this property; <code>null</code> otherwise
     */
    Property find(String name) {
        synchronized (this) {
            // tokenize
            StringTokenizer tokenizer = new StringTokenizer(name, ".");
            Property property = this;
            while (tokenizer.hasMoreTokens()) {
                name = tokenizer.nextToken();
                boolean found = false;
                // locate property
                for (Iterator itr = property.subproperties.iterator(); itr.hasNext();) {
                    Property nextProp = (Property) itr.next();
                    if (nextProp.name.equals(name)) {
                        property = nextProp;
                        found = true;
                        break;
                    }
                }
                // if not found, then the target property does not exist
                if (!found) {
                    return null;
                }
            }
            return property;
        }
    }

    /**
     * Creates a <code>Property</code> specified by given name directly or
     * indirectly nested within this <code>Property</code> if it does not exist;
     * otherwise simply returns an existing <code>Property</code> with given
     * <code>name</code>.
     *
     * @param  name a name of <code>Property</code>
     * @return a <code>Property</code> with given name, either newly created or
     *         already existing
     * @throws NullPointerException if given <code>name</code> is <code>null
     *         </code>
     * @throws IllegalArgumentException if given <code>name</code> is <code>
     *         empty</code>
     */
    private Property create(String name) {
        if (name == null) {
            throw new NullPointerException("parameter name is null");
        }
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("parameter name is empty");
        }

        synchronized (this) {
            // tokenize
            StringTokenizer tokenizer = new StringTokenizer(name, ".");
            Property property = this;
            while (tokenizer.hasMoreTokens()) {
                name = tokenizer.nextToken();
                boolean found = false;
                // locate property
                for (Iterator itr = property.subproperties.iterator(); itr.hasNext();) {
                    Property nextProp = (Property) itr.next();
                    if (nextProp.name.equals(name)) {
                        property = nextProp;
                        found = true;
                        break;
                    }
                }
                // if not found, create!
                if (!found) {
                    Property newProp = new Property(name);
                    try {
                        property.addProperty(newProp);
                    } catch (DuplicatePropertyException dpe) {
                        // should not occur
                    }
                    property = newProp;
                }
            }
            return property;
        }
    }

    /**
     * Gets the clone copy of this <code>Property</code>
     *
     * @return a clone of this <code>Property</code>
     */
    public Object clone() {
        synchronized (this) {
            Property property = new Property();
            property.name = name;
            property.value = value;
            property.values = new ArrayList(values);
            property.subproperties = new ArrayList();
            for (Iterator itr = subproperties.iterator(); itr.hasNext();) {
                property.subproperties.add(((Property) itr.next()).clone());
            }
            if (comments != null) {
                property.comments = new ArrayList(comments);
            }
            return property;
        }
    }

    /**
     * Adds given comment to list of comments associated with this property.
     * Allocates a memory space for comment's list first if it is not already
     * allocated.
     *
     * @param  comment a string comment to associate with this property
     * @throws NullPointerException if given <code>comment</code> is <code>null
     *         </code>
     */
    public void addComment(String comment) {
        if (comment == null) {
            throw new NullPointerException("parameter comment is null");
        }

        synchronized (this) {
            if (comments == null) {
                comments = new ArrayList();
            }
            comments.add(comment);
        }
    }

    /**
     * Associates given <code>comments</code> with this <code>Property</code>.
     * Any previous commenst are removed.
     *
     * @param  comments a new comments associated with this <code>Property
     *         </code>
     * @throws IllegalArgumentException if given <code>comments</code> contains
     *         a non String instance
     */
    public void setComments(List comments) {
        synchronized (this) {
            if (comments == null) {
                this.comments = null;
            } else {
                for (Iterator itr = comments.iterator(); itr.hasNext();) {
                    if (!(itr.next() instanceof String)) {
                        throw new IllegalArgumentException("parameter comments contains non String entry");
                    }
                }
                this.comments = new ArrayList(comments);
            }
        }
    }

    /**
     * Gets the comments associated with this property.
     *
     * @return a <code>List</code> containing string comments associated with
     *         this property or <code>null</code> if no comments were associated
     *         with this property
     */
    public List getComments() {
        synchronized (this) {
            if (comments == null) {
                return null;
            } else {
                return new ArrayList(comments);
            }
        }
    }

    /**
     * Gets the name of this property uniquely identifying it among
     * other properties.
     *
     * @return a name of this property
     */
    public String getName() {
        return name;
    }

    /**
     * Compares this <code>Property</code> to the specified object. The
     * result is <code>true</code> if and only if the argument is not <code>null
     * </code> and is a <code>Property</code> object and both objects
     * elements have equal names.
     *
     * @param  o the object to compare this <code>Property</code> against
     * @return <code>true</code> if the property names are equal or
     *         their hash codes are equal; <code>false</code> otherwise
     */
    public boolean equals(Object o) {
        return (o instanceof Property) && name.equals(((Property) o).name);
    }

    /**
     * Returns a hash code for this property. The hash code for a
     * <code>Property</code> object is equal to hash code of it's name.
     *
     * @return a hash code value for this <code>Property</code>
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Gets the list of values associated with property specified by given key.
     *
     * @param  key a property to get values for
     * @param  defaultValue value to return if no values are associated with
     *         given property
     * @return a string array containing the values associated with property
     *         specified by given <code>key</code>. If no values are associated
     *         with given property returns an array containing the <code>
     *         defaultValue</code>
     * @throws IllegalArgumentException if given <code>key</code> is empty
     * @throws NullPointerException if any of given <code>key</code> is <code>null</code>
     */
    String[] getProperties(String key, String defaultValue) {
        if (key == null) {
            throw new NullPointerException("parameter key is null");
        }
        if (key.trim().length() == 0) {
            throw new IllegalArgumentException("parameter key is empty");
        }
        if (defaultValue == null) {
            throw new NullPointerException("parameter defaultValue is null");
        }

        String[] values = getValues(key);
        if (values != null) {
            return values;
        } else {
            return new String[] {defaultValue};
        }
    }

}
