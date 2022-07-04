/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) PropertyTestCase.java
 *
 * 2.1  02/01/2004
 */
package com.topcoder.util.config;

import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Tests the behavior of Property.
 *
 * @author  WishingBone
 * @version 2.1
 */
public class PropertyTestCase extends TestCase {

    /**
     * Tests create root Property.
     */
    public void testCreateRootProperty() {
        Property property = new Property();
        assertNotNull(property);
        // no further testing necessary
    }

    /**
     * Tests create Property with name.
     */
    public void testCreatePropertyName() {
        Property property = new Property("testprop");
        assertNotNull(property);
        assertTrue(property.getName().equals("testprop"));
        assertNull(property.getValue());
        assertTrue(property.list().size() == 0);
        // name is null
        try {
            new Property(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // name is empty
        try {
            new Property("   ");
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // compound name
        try {
            new Property("test.prop");
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Tests create Property with name and value.
     */
    public void testCreatePropertyNameValue() {
        Property property = new Property("testprop", "testvalue");
        assertNotNull(property);
        assertTrue(property.getName().equals("testprop"));
        assertTrue(property.getValue().equals("testvalue"));
        assertTrue(property.list().size() == 0);
        // name is null
        try {
            new Property(null, "testvalue");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // value is null
        try {
            new Property("testprop", (String) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // name is empty
        try {
            new Property("   ", "testvalue");
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // compound name
        try {
            new Property("test.prop", "testvalue");
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Tests create Property with name and values.
     */
    public void testCreatePropertyNameValues() {
        Property property = new Property("testprop", new String[] {"value1", "value2"});
        assertNotNull(property);
        assertTrue(property.getName().equals("testprop"));
        String[] values = property.getValues();
        assertTrue(values.length == 2);
        assertTrue(values[0].equals("value1"));
        assertTrue(values[1].equals("value2"));
        assertTrue(property.list().size() == 0);
        // name is null
        try {
            new Property(null, new String[] {"value1", "value2"});
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // values is null
        try {
            new Property("testprop", (String[]) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // values contains null entry
        try {
            new Property("testprop", new String[] {"testvalue", null});
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // name is empty
        try {
            new Property("   ", new String[] {"value1", "value2"});
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // compound name
        try {
            new Property("test.prop", new String[] {"value1", "value2"});
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Tests getName().
     */
    public void testGetName() {
        Property property = new Property("testprop");
        assertTrue(property.getName().equals("testprop"));
    }

    /**
     * Tests addProperty(property).
     *
     * @throws Exception to JUnit.
     */
    public void testAddPropertyProperty() throws Exception {
        Property parent = new Property("parent");
        Property sub = new Property("sub");
        parent.addProperty(sub);
        assertTrue(parent.containsProperty("sub"));
        // add null
        try {
            parent.addProperty(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // add duplicate
        try {
            parent.addProperty(sub);
            fail("Should have thrown DuplicatePropertyException");
        } catch (DuplicatePropertyException dpe) {
        }
    }

    /**
     * Tests addProperty(name, value).
     *
     * @throws Exception to JUnit.
     */
    public void testAddPropertyNameValue() throws Exception {
        Property parent = new Property("parent");
        parent.addProperty("sub", "value");
        assertTrue(parent.containsProperty("sub"));
        Property sub = (Property) parent.list().get(0);
        assertTrue(sub.getValue().equals("value"));
        // name is null
        try {
            parent.addProperty(null, "testvalue");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // value is null
        try {
            parent.addProperty("testprop", (String) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // name is empty
        try {
            parent.addProperty("   ", "testvalue");
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // add duplicate
        try {
            parent.addProperty("sub", "value");
            fail("Should have thrown DuplicatePropertyException");
        } catch (DuplicatePropertyException dpe) {
        }
    }

    /**
     * Tests addProperty(name, values).
     *
     * @throws Exception to JUnit.
     */
    public void testAddPropertyNameValues() throws Exception {
        Property parent = new Property("parent");
        parent.addProperty("sub", new String[] {"value1", "value2"});
        assertTrue(parent.containsProperty("sub"));
        Property sub = (Property) parent.list().get(0);
        String[] values = sub.getValues();
        assertTrue(values.length == 2);
        assertTrue(values[0].equals("value1"));
        assertTrue(values[1].equals("value2"));
        // name is null
        try {
            parent.addProperty(null, new String[] {"value1", "value2"});
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // values is null
        try {
            parent.addProperty("testprop", (String[]) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // values contains null entry
        try {
            parent.addProperty("testprop", new String[] {"testvalue", null});
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // name is empty
        try {
            parent.addProperty("   ", new String[] {"value1", "value2"});
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // add duplicate
        try {
            parent.addProperty("sub", new String[] {"value1", "value2"});
            fail("Should have thrown DuplicatePropertyException");
        } catch (DuplicatePropertyException dpe) {
        }
    }

    /**
     * Tests addValue(value).
     */
    public void testAddValueValue() {
        Property property = new Property("testprop");
        assertNull(property.getValue());
        property.addValue("value1");
        property.addValue("value2");
        String[] values = property.getValues();
        assertTrue(values.length == 2);
        assertTrue(values[0].equals("value1"));
        assertTrue(values[1].equals("value2"));
        // value is null
        try {
            property.addValue(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Tests removeValue(value).
     */
    public void testRemoveValueValue() {
        Property property = new Property("testprop", new String[] {"value1", "value2"});
        property.removeValue("value1");
        assertTrue(property.getValues().length == 1);
        assertTrue(property.getValue().equals("value2"));
        // remove value that does not exist
        property.removeValue("non-exist");
        assertTrue(property.getValues().length == 1);
        assertTrue(property.getValue().equals("value2"));
        // value is null
        try {
            property.removeValue(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Tests containsValue(value).
     */   
    public void testContainsValueValue() {
        Property property = new Property("testprop", new String[] {"value1", "value2"});
        assertTrue(property.containsValue("value1"));
        assertTrue(property.containsValue("value2"));
        assertFalse(property.containsValue("non-exist"));
        // value is null
        try {
            property.containsValue(null);
            fail("Should have thrwon NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Tests getValue().
     */
    public void testGetValue() {
        // single
        Property property = new Property("testprop", "value1");
        assertTrue(property.getValue().equals("value1"));
        // multiple, return the first
        property.addValue("value2");
        assertTrue(property.getValue().equals("value1"));
        // empty, return null
        property.removeValue("value1");
        property.removeValue("value2");
        assertNull(property.getValue());
    }

    /**
     * Tests getValues().
     */
    public void testGetValues() {
        // single
        Property property = new Property("testprop", "value1");
        String[] values = property.getValues();
        assertTrue(values.length == 1);
        assertTrue(values[0].equals("value1"));
        // multiple
        property.addValue("value2");
        values = property.getValues();
        assertTrue(values.length == 2);
        assertTrue(values[0].equals("value1"));
        assertTrue(values[1].equals("value2"));
        // empty, return null
        property.removeValue("value1");
        property.removeValue("value2");
        assertNull(property.getValues());
    }

    /**
     * Tests setValue(value).
     */
    public void testSetValueValue() {
        Property property = new Property("testprop", "value1");
        property.setValue("value2");
        assertTrue(property.getValue().equals("value2"));
        // clears multiple value
        property.addValue("value3");
        property.setValue("value2");
        assertTrue(property.getValues().length == 1);
        assertTrue(property.getValue().equals("value2"));
        // value is null
        try {
            property.setValue(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Tests setValues(values).
     */
    public void testSetValuesValues() {
        Property property = new Property("testprop", "value1");
        property.setValues(new String[] {"value2", "value3"});
        assertTrue(property.getValues().length == 2);
        assertTrue(property.getValues()[0].equals("value2"));
        assertTrue(property.getValues()[1].equals("value3"));
        // values is null
        try {
            property.setValues(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // no value
        try {
            property.setValues(new String[0]);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // values contains null entry
        try {
            property.setValues(new String[] {"value", null});
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Tests addComment(comment).
     */
    public void testAddCommentComment() {
        Property property = new Property("testprop");
        property.addComment("comment1");
        property.addComment("comment2");
        List comments = property.getComments();
        assertTrue(comments.size() == 2);
        assertTrue(comments.get(0).equals("comment1"));
        assertTrue(comments.get(1).equals("comment2"));
        // comment is null
        try {
            property.addComment(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Tests setComments(comments).
     */
    public void testSetCommentsComments() {
        Property property = new Property("testprop");
        List comments = new ArrayList();
        comments.add("comment1");
        comments.add("comment2");
        property.setComments(comments);
        comments.clear();
        comments = property.getComments();
        assertTrue(comments.size() == 2);
        assertTrue(comments.get(0).equals("comment1"));
        assertTrue(comments.get(1).equals("comment2"));
        // can set null, means clear
        property.setComments(null);
        assertNull(property.getComments());
        // contains non String instance
        comments.clear();
        comments.add(new Object());
        try {
            property.setComments(comments);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Tests getComments().
     */
    public void testGetComments() {
        // defaultly, return null
        Property property = new Property("testprop");
        assertNull(property.getComments());
        property.addComment("comment1");
        property.addComment("comment2");
        List comments = property.getComments();
        assertTrue(comments.size() == 2);
        assertTrue(comments.get(0).equals("comment1"));
        assertTrue(comments.get(1).equals("comment2"));
    }

    /**
     * Tests list().
     *
     * @throws Exception to JUnit.
     */
    public void testList() throws Exception {
        Property property = new Property("testprop");
        property.addProperty("sub1", "value1");
        property.addProperty("sub2", "value2");
        List subs = property.list();
        assertTrue(subs.size() == 2);
        Property sub = (Property) subs.get(0);
        assertTrue(sub.getName().equals("sub1"));
        assertTrue(sub.getValue().equals("value1"));
        sub = (Property) subs.get(1);
        assertTrue(sub.getName().equals("sub2"));
        assertTrue(sub.getValue().equals("value2"));
    }

    /**
     * Tests propertyNames().
     *
     * @throws Exception to JUnit.
     */
    public void testPropertyNames() throws Exception {
        Property property = new Property("testprop");
        property.addProperty("sub1", "value1");
        property.addProperty("sub2", "value2");
        Enumeration enu = property.propertyNames();
        assertTrue(enu.hasMoreElements());
        assertTrue(enu.nextElement().equals("sub1"));
        assertTrue(enu.hasMoreElements());
        assertTrue(enu.nextElement().equals("sub2"));
        assertFalse(enu.hasMoreElements());
    }

    /**
     * Tests find(name).
     */
    public void testFindName() {
        Property root = new Property();
        root.setProperty("a.b.c", "value");
        Property a = root.find("a");
        Property b = root.find("a.b");
        Property c = root.find("a.b.c");
        assertNotNull(a);
        assertNotNull(b);
        assertNotNull(c);
        assertNull(root.find("b.c"));
        assertNull(root.find("a.b.d"));
    }

    /**
     * Tests getProperty(name).
     */
    public void testGetPropertyName() {
        Property root = new Property();
        root.setProperty("a.b.c", "value");
        Property a = root.getProperty("a");
        Property b = root.getProperty("a.b");
        Property c = root.getProperty("a.b.c");
        assertNotNull(a);
        assertNotNull(b);
        assertNotNull(c);
        assertNull(root.getProperty("b.c"));
        assertNull(root.getProperty("a.b.d"));
        // name is null
        try {
            root.getProperty(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Tests getValue(name).
     */
    public void testGetValueName() {
        Property root = new Property();
        root.setProperty("a", "value1");
        root.setProperty("a.b.c", "value2");
        assertTrue(root.getValue("a").equals("value1"));
        assertTrue(root.getValue("a.b.c").equals("value2"));
        assertNull(root.getValue("a.b"));
        assertNull(root.getValue("b.c"));
        // name is null
        try {
            root.getValue(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // name is empty
        try {
            root.getValue("   ");
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Tests getValues(name).
     */
    public void testGetValuesName() {
        Property root = new Property();
        root.setProperty("a", "value1");
        root.setProperty("a.b.c", new String[] {"value2", "value3"});
        String[] values = root.getValues("a");
        assertTrue(values.length == 1);
        assertTrue(values[0].equals("value1"));
        values = root.getValues("a.b.c");
        assertTrue(values.length == 2);
        assertTrue(values[0].equals("value2"));
        assertTrue(values[1].equals("value3"));
        assertNull(root.getValues("a.b"));
        assertNull(root.getValues("b.c"));
        // name is null
        try {
            root.getValues(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // name is empty
        try {
            root.getValues("   ");
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Tests removeProperty(name).
     */
    public void testRemovePropertyName() {
        Property root = new Property();
        root.setProperty("a.b.c", "value");
        root.removeProperty("a.b.c");
        assertNull(root.getProperty("a.b.c"));
        assertNotNull(root.getProperty("a.b"));
        // remove all
        root.removeProperty("a");
        assertNull(root.getProperty("a"));
        assertNull(root.getProperty("a.b"));
        // name is null
        try {
            root.removeProperty(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // name is empty
        try {
            root.removeProperty("   ");
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Tests setProperty(name, value).
     */
    public void testSetPropertyNameValue() {
        Property root = new Property();
        root.setProperty("a.b.c", "value");
        // construct the whole chain
        assertNotNull(root.getProperty("a"));
        assertNotNull(root.getProperty("a.b"));
        assertNotNull(root.getProperty("a.b.c"));
        assertTrue(root.getValue("a.b.c").equals("value"));
        // name is null
        try {
            root.setProperty(null, "value");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // name is empty
        try {
            root.setProperty("   ", "value");
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // value is null
        try {
            root.setProperty("d", (String) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Tests setProperty(name, values).
     */
    public void testSetPropertyNameValues() {
        Property root = new Property();
        root.setProperty("a.b.c", new String[] {"value1", "value2"});
        // construct the whole chain
        assertNotNull(root.getProperty("a"));
        assertNotNull(root.getProperty("a.b"));
        assertNotNull(root.getProperty("a.b.c"));
        assertTrue(root.getValues("a.b.c").length == 2);
        assertTrue(root.getValues("a.b.c")[0].equals("value1"));
        assertTrue(root.getValues("a.b.c")[1].equals("value2"));
        // name is null
        try {
            root.setProperty(null, new String[] {"value1", "value2"});
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // name is empty
        try {
            root.setProperty("   ", new String[] {"value1", "value2"});
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // value is null
        try {
            root.setProperty("d", (String[]) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Tests getProperties(key, defaultValue).
     */
    public void testGetPropertiesKeyDefaultValue() {
        Property root = new Property();
        root.setProperty("a.b.c", new String[] {"value1", "value2"});
        String[] values = root.getProperties("a.b.c", "default");
        assertTrue(values.length == 2);
        assertTrue(values[0].equals("value1"));
        assertTrue(values[1].equals("value2"));
        values = root.getProperties("a.b", "default");
        assertTrue(values.length == 1);
        assertTrue(values[0].equals("default"));
        values = root.getProperties("non-exist", "default");
        assertTrue(values.length == 1);
        assertTrue(values[0].equals("default"));
        // key is null
        try {
            root.getProperties(null, "default");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is empty
        try {
            root.getProperties("   ", "default");
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // defaultValue is null
        try {
            root.getProperties("a.b.c", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Test clone().
     */
    public void testClone() {
        Property root = new Property();
        root.setProperty("a.b.c", "value");
        Property copy = (Property) root.clone();
        // it is not shallow copy
        assertNotNull(copy.getProperty("a"));
        assertNotNull(copy.getProperty("a.b"));
        assertNotNull(copy.getProperty("a.b.c"));
        assertTrue(root.getProperty("a") != copy.getProperty("a"));
        assertTrue(root.getProperty("a.b") != copy.getProperty("a.b"));
        assertTrue(root.getProperty("a.b.c") != copy.getProperty("a.b.c"));
    }

    /**
     * Tests equals(obj).
     */
    public void testEquals() {
        Property p1 = new Property("testprop", "value1");
        Property p2 = new Property("testprop", "value2");
        Property p3 = new Property("otherprop", "value3");
        assertTrue(p1.equals(p2));
        assertFalse(p1.equals(p3));
        assertFalse(p1.equals(new Object()));
        assertFalse(p1.equals(null));
    }

    /**
     * Tests hashCode().
     */
    public void testHashCode() {
        Property p1 = new Property("testprop", "value1");
        Property p2 = new Property("testprop", "value2");
        assertTrue(p1.hashCode() == p2.hashCode());
    }

}
