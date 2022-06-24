/*
 * Copyright (C) 2007-2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.CompLink;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.CompVersionDates;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Technology;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Unit test case for {@link CompVersion}.</p>
 *
 * @author Retunsky, KingStone
 * @version 1.1
 */
public class CompVersionTest extends TestCase {
    /**
     * <p>Represents CompVersion instance for testing.</p>
     */
    private CompVersion compVersion;

    /**
     * <p>Creates a new instance of CompVersion.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        compVersion = new CompVersion();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        compVersion = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>CompVersion()</code> constructor.</p>
     */
    public void testCompVersion() {
        assertNotNull("Unable to instantiate CompVersion", compVersion);
    }

    /**
     * <p>Tests <code>getId()</code> and
     * <code>getId()</code> methods for accuracy.</p>
     */
    public void testGetSetId() {
        assertNull("Incorrect default id value", compVersion.getId());
        Long id = 1L;
        // set a id
        compVersion.setId(id);
        assertEquals("Incorrect id value after setting a new one",
            id, compVersion.getId());
    }

    /**
     * <p>Tests <code>setId(null)</code>.</p>
     */
    public void testSetIdAllowsNull() {
        // set null
        try {
            compVersion.setId(null);
            assertNull("Field 'id' should contain 'null' value",
                compVersion.getId());
        } catch (IllegalArgumentException e) {
            fail("Field 'id' should allow null values");
        }
    }


    /**
     * <p>Tests <code>getComments()</code> and
     * <code>setComments()</code> methods for accuracy.</p>
     */
    public void testGetSetComments() {
        assertNull("Incorrect default comments value", compVersion.getComments());
        String value = "16";
        // set a comments
        compVersion.setComments(value);
        assertEquals("Incorrect comments value after setting a new one",
            value, compVersion.getComments());
    }

    /**
     * <p>Tests <code>setComments(null)</code>.</p>
     */
    public void testCommentsAllowsNull() {
        // set a comments
        // set null
        try {
            compVersion.setComments(null);
            assertNull("Field 'comments' should contain 'null' value",
                compVersion.getComments());
        } catch (IllegalArgumentException e) {
            fail("Field 'comments' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getComponent()</code> and
     * <code>setComponent()</code> methods for accuracy.</p>
     */
    public void testGetSetComponent() {
        assertNull("Incorrect default component value", compVersion.getComponent());
        Component value = new Component();
        // set a component
        compVersion.setComponent(value);
        assertEquals("Incorrect component value after setting a new one",
            value, compVersion.getComponent());
    }

    /**
     * <p>Tests <code>setComponent(null)</code>.</p>
     */
    public void testComponentAllowsNull() {
        // set a component
        // set null
        try {
            compVersion.setComponent(null);
            assertNull("Field 'component' should contain 'null' value",
                compVersion.getComponent());
        } catch (IllegalArgumentException e) {
            fail("Field 'component' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getForum()</code> and
     * <code>setForum()</code> methods for accuracy.</p>
     */
    public void testGetSetForum() {
        assertNull("Incorrect default forum value", compVersion.getForum());
        CompForum value = new CompForum();
        // set a forum
        compVersion.setForum(value);
        assertEquals("Incorrect forum value after setting a new one",
            value, compVersion.getForum());
    }

    /**
     * <p>Tests <code>setForum(null)</code>.</p>
     */
    public void testForumAllowsNull() {
        // set a forum
        // set null
        try {
            compVersion.setForum(null);
            assertNull("Field 'forum' should contain 'null' value",
                compVersion.getForum());
        } catch (IllegalArgumentException e) {
            fail("Field 'forum' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getLink()</code> and
     * <code>setLink()</code> methods for accuracy.</p>
     */
    public void testGetSetLink() {
        assertNull("Incorrect default link value", compVersion.getLink());
        CompLink value = new CompLink();
        // set a link
        compVersion.setLink(value);
        assertEquals("Incorrect link value after setting a new one",
            value, compVersion.getLink());
    }

    /**
     * <p>Tests <code>setLink(null)</code>.</p>
     */
    public void testLinkAllowsNull() {
        // set a link
        // set null
        try {
            compVersion.setLink(null);
            assertNull("Field 'link' should contain 'null' value",
                compVersion.getLink());
        } catch (IllegalArgumentException e) {
            fail("Field 'link' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getPhase()</code> and
     * <code>setPhase()</code> methods for accuracy.</p>
     */
    public void testGetSetPhase() {
        assertNull("Incorrect default phase value", compVersion.getPhase());
        Phase value = new Phase();
        // set a phase
        compVersion.setPhase(value);
        assertEquals("Incorrect phase value after setting a new one",
            value, compVersion.getPhase());
    }

    /**
     * <p>Tests <code>setPhase(null)</code>.</p>
     */
    public void testPhaseAllowsNull() {
        // set a phase
        // set null
        try {
            compVersion.setPhase(null);
            assertNull("Field 'phase' should contain 'null' value",
                compVersion.getPhase());
        } catch (IllegalArgumentException e) {
            fail("Field 'phase' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getPhasePrice()</code> and
     * <code>setPhasePrice()</code> methods for accuracy.</p>
     */
    public void testGetSetPhasePrice() {
        assertEquals("Incorrect default phasePrice value", 0.0d, compVersion.getPhasePrice());
        double value = 17.5;
        // set a phasePrice
        compVersion.setPhasePrice(value);
        assertEquals("Incorrect phasePrice value after setting a new one",
            value, compVersion.getPhasePrice());
    }

    /**
     * <p>Tests <code>getPhaseTime()</code> and
     * <code>setPhaseTime()</code> methods for accuracy.</p>
     */
    public void testGetSetPhaseTime() {
        assertNull("Incorrect default phaseTime value", compVersion.getPhaseTime());
        Date value = new Date(1169154000000L) /*2007-01-19*/;
        // set a phaseTime
        compVersion.setPhaseTime(value);
        assertEquals("Incorrect phaseTime value after setting a new one",
            value, compVersion.getPhaseTime());
    }

    /**
     * <p>Tests <code>setPhaseTime(null)</code>.</p>
     */
    public void testPhaseTimeAllowsNull() {
        // set a phaseTime
        // set null
        try {
            compVersion.setPhaseTime(null);
            assertNull("Field 'phaseTime' should contain 'null' value",
                compVersion.getPhaseTime());
        } catch (IllegalArgumentException e) {
            fail("Field 'phaseTime' should allow null values");
        }
    }

    /**
     * <p>Tests <code>setSuspended()</code> and
     * <code>setSuspended()</code> methods for accuracy.</p>
     */
    public void testGetSetSuspended() {
        assertFalse("Incorrect default suspended value", compVersion.isSuspended());
        boolean value = true;
        // set a suspended
        compVersion.setSuspended(value);
        assertEquals("Incorrect suspended value after setting a new one",
            value, compVersion.isSuspended());
    }

    /**
     * <p>Tests <code>getTechnologies()</code> and
     * <code>setTechnologies()</code> methods for accuracy.</p>
     */
    public void testGetSetTechnologies() {
        assertNull("Incorrect default technologies value", compVersion.getTechnologies());
        List<Technology> value = new ArrayList<Technology>();
        // set a technologies
        compVersion.setTechnologies(value);
        assertEquals("Incorrect technologies value after setting a new one",
            value, compVersion.getTechnologies());
    }

    /**
     * <p>Tests <code>setTechnologies(null)</code>.</p>
     */
    public void testTechnologiesAllowsNull() {
        // set a technologies
        // set null
        try {
            compVersion.setTechnologies(null);
            assertNull("Field 'technologies' should contain 'null' value",
                compVersion.getTechnologies());
        } catch (IllegalArgumentException e) {
            fail("Field 'technologies' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getCompVersion()</code> and
     * <code>setCompVersion()</code> methods for accuracy.</p>
     */
    public void testGetSetVersion() {
        assertNull("Incorrect default version value", compVersion.getVersion());
        Long value = 81604378644L;
        // set a version
        compVersion.setVersion(value);
        assertEquals("Incorrect version value after setting a new one",
            value, compVersion.getVersion());
    }

    /**
     * <p>Tests <code>setCompVersion(null)</code>.</p>
     */
    public void testVersionAllowsNull() {
        // set a version
        // set null
        try {
            compVersion.setVersion(null);
            assertNull("Field 'version' should contain 'null' value",
                compVersion.getVersion());
        } catch (IllegalArgumentException e) {
            fail("Field 'version' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getVersionDates()</code> and
     * <code>setVersionDates()</code> methods for accuracy.</p>
     */
    public void testGetSetVersionDates() {
        assertNull("Incorrect default versionDates value", compVersion.getVersionDates());
        Map<Long, CompVersionDates> value = new HashMap<Long, CompVersionDates>();
        // set a versionDates
        compVersion.setVersionDates(value);
        assertEquals("Incorrect versionDates value after setting a new one",
            value, compVersion.getVersionDates());
    }

    /**
     * <p>Tests <code>setVersionDates(null)</code>.</p>
     */
    public void testVersionDatesAllowsNull() {
        // set a versionDates
        // set null
        try {
            compVersion.setVersionDates(null);
            assertNull("Field 'versionDates' should contain 'null' value",
                compVersion.getVersionDates());
        } catch (IllegalArgumentException e) {
            fail("Field 'versionDates' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getVersionText()</code> and
     * <code>setVersionText()</code> methods for accuracy.</p>
     */
    public void testGetSetVersionText() {
        assertNull("Incorrect default versionText value", compVersion.getVersionText());
        String value = "21";
        // set a versionText
        compVersion.setVersionText(value);
        assertEquals("Incorrect versionText value after setting a new one",
            value, compVersion.getVersionText());
    }

    /**
     * <p>Tests <code>setVersionText(null)</code>.</p>
     */
    public void testVersionTextAllowsNull() {
        // set a versionText
        // set null
        try {
            compVersion.setVersionText(null);
            assertNull("Field 'versionText' should contain 'null' value",
                compVersion.getVersionText());
        } catch (IllegalArgumentException e) {
            fail("Field 'versionText' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getDocumentation()</code> and
     * <code>setDocumentation()</code> methods for accuracy.</p>
     * @since 1.1
     */
    public void testGetSetDocumentation() {
        assertNull("Incorrect default documentation value", compVersion.getDocumentation());
        List<CompDocumentation> value = new ArrayList<CompDocumentation>();
        value.add(new CompDocumentation());
        // set a Documentation
        compVersion.setDocumentation(value);
        assertEquals("Incorrect documentation value after setting a new one",
            value, compVersion.getDocumentation());
        // empty list is valid
        value.clear();
        try {
            compVersion.setDocumentation(value);
            // ok
        } catch (IllegalArgumentException e) {
            fail("setDocumentation should accept empty list.");
        }
    }

    /**
     * <p>Tests <code>setDocumentation(null)</code>.</p>
     * @since 1.1
     */
    public void testDocumentationAllowsNull() {
        // set a Documentation
        // set null
        try {
            compVersion.setDocumentation(null);
            assertNull("Field 'documentation' should contain 'null' value",
                compVersion.getDocumentation());
        } catch (IllegalArgumentException e) {
            fail("Field 'documentation' should allow null values");
        }
    }
}
