/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * A simple container of several data fields, along with an ordered list of sections that represents a group,
 * the second level in the scorecard model hierarchy.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is NOT thread safe.
 * </p>
 *
 * @see         Scorecard
 * @see         Section
 *
 * @author      aubergineanode, UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "group", propOrder = {"sections"})
public class Group extends WeightedScorecardStructure {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Fields

    /**
     * <p>
     * The list of Section instances that make up this Group.
     * </p>
     *
     * <p>
     * All items in this list are required to be non-null Section instances, and no duplicates (by
     * reference equality) are allowed.
     * </p>
     */
    private final List sections = new ArrayList();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    /**
     * Creates a new Group using the default values.
     */
    public Group() {
        super();
    }

    /**
     * Creates a new Group using the specified identifier, and a default name, weight, and sections list.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero.
     */
    public Group(long id) {
        super(id);
    }

    /**
     * Creates a new Group using the specified identifier and name, and a default weight, and sections
     * list.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   name
     *          The scorecard structure's name to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the name is null.
     */
    public Group(long id, String name) {
        super(id, name);
    }

    /**
     * Creates a new Group using the specified identifier and weight, and a default name and sections list.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   weight
     *          The scorecard structure's weight to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the weight is less than 0, greater than 100,
     *          or equal to Float.NaN.
     */
    public Group(long id, float weight) {
        super(id, weight);
    }

    /**
     * Creates a new Group using the specified identifier, weight, name, and a default sections list.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   name
     *          The scorecard structure's name to initialize with.
     * @param   weight
     *          The scorecard structure's weight to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the weight is less than 0, greater than 100,
     *          or equal to Float.NaN, or the name is null.
     */
    public Group(long id, String name, float weight) {
        super(id, name, weight);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - sections Add Mutators

    /**
     * <p>
     * Adds the specified section to this Group.
     * </p>
     *
     * <p>
     * If the specified section already exists in this Group, this method results in no operation being done.
     * Otherwise, the section is appended to the end of the list of sections in this Group.
     * </p>
     *
     * @param   section
     *          The section to be added to this group.
     *
     * @throws  IllegalArgumentException
     *          The section is null.
     */
    public void addSection(Section section) {
        Util.checkNotNull(section, "section");
        addSectionInternal(section);
    }

    /**
     * <p>
     * Adds the specified list of sections to this Group.
     * </p>
     *
     * <p>
     * All duplicate sections (either found inside the array, or against this groups's list of sections) will be
     * ignored, while all other sections in the specified list will be appended to the end of the list of sections
     * in this Group.
     * </p>
     *
     * @param   sections
     *          The list of sections to be added to this group.
     *
     * @throws  IllegalArgumentException
     *          The sections is null or contains null entries.
     */
    public void addSections(Section[] sections) {
        Util.checkArrayNotNull(sections, "sections");

        for (int i = 0; i < sections.length; ++i) {
            addSectionInternal(sections[i]);
        }
    }

    /**
     * Inserts the specified section into this Group at the specified index.
     *
     * @param   section
     *          The section to be inserted to this group.
     * @param   index
     *          The index where the section should be inserted into.
     *
     * @throws  IllegalArgumentException
     *          The section is null.
     * @throws  IndexOutOfBoundsException
     *          The index is negative or greater than the number of sections.
     */
    public void insertSection(Section section, int index) {
        Util.checkNotNull(section, "section");

        if (!sections.contains(section)) {
            sections.add(index, section);
        }
    }

    /**
     * <p>
     * Adds the specified non-null section to this Group.
     * </p>
     *
     * <p>
     * This is mainly used to avoid checking for nulls again during the addSections method.
     * </p>
     *
     * @param   section
     *          The section to be added to this group.
     */
    private void addSectionInternal(Section section) {
        if (!sections.contains(section)) {
            sections.add(section);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - sections Remove Mutators

    /**
     * <p>
     * Removes the specified section from this Group.
     * </p>
     *
     * <p>
     * If the specified section does not exist in this group (by reference equality), this method results in no
     * operation being done. Otherwise, the specified section will be removed from the list of sections in this
     * Group.
     * </p>
     *
     * @param   section
     *          The section to remove from this group.
     *
     * @throws  IllegalArgumentException
     *          The section is null.
     */
    public void removeSection(Section section) {
        Util.checkNotNull(section, "section");
        sections.remove(section);
    }

    /**
     * Removes the section at the specified index from this Group.
     *
     * @param   index
     *          The index of the section to be removed.
     *
     * @throws  IndexOutOfBoundsException
     *          The index is negative or greater than or equal to the number of sections.
     */
    public void removeSection(int index) {
        sections.remove(index);
    }

    /**
     * <p>
     * Removes the specified list of sections from this Group.
     * </p>
     *
     * <p>
     * Any section not found in this group will be ignored, while all other sections in the specified list will be
     * removed from list of sections in this Group.
     * </p>
     *
     * @param   sections
     *          The list of sections to be removed from this group.
     *
     * @throws  IllegalArgumentException
     *          The sections is null or contains null entries.
     */
    public void removeSections(Section[] sections) {
        Util.checkArrayNotNull(sections, "sections");
        this.sections.removeAll(Arrays.asList(sections));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - sections Clear Mutator

    /**
     * Clears all sections associated with this Group.
     */
    public void clearSections() {
        sections.clear();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - sections Get Accessors
    
    /**
     * Gets the Section at the specified position in this Group.
     *
     * @param   sectionIndex
     *          The index of the section to retrieve.
     *
     * @return  The Section at the specified position in this Group.
     *
     * @throws  IndexOutOfBoundsException
     *          The sectionIndex is less than 0, or greater than or equal to the number of questions.
     */
    public Section getSection(int sectionIndex) {
        return (Section) sections.get(sectionIndex);
    }

    /**
     * Gets the list of all sections associated with this Group.
     * 
     * @return A list of all the sections associated with this Group.
     */
    public List getSections() {
		return sections;
	}

	/**
     * Gets a list of all the sections associated with this Group.
     *
     * @return  A list of all the sections associated with this Group.
     */
    public Section[] getAllSections() {
        return (Section[]) sections.toArray(new Section[sections.size()]);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - sections Miscellaneous Accessors and Mutators

    /**
     * Gets the number of sections currently associated with this Group.
     *
     * @return  The number of sections currently associated with this Group.
     */
    public int getNumberOfSections() {
        return sections.size();
    }

    /**
     * Checks that the sum of the weights of the sections list is 100 plus/minus the tolerance, and that
     * each section's questions' weights sum to 100 plus/minus the tolerance.
     *
     * @param   tolerance
     *          The absolute amount that the sum of weights can at most differ from 100.
     *
     * @return  true if the section weights sum to 100 plus/minus the tolerance, and each section's
     *          questions' weights sum to 100 plus/minus the tolerance; false otherwise.
     *
     * @throws  IllegalArgumentException
     *          The tolerance is negative, Float.POSITIVE_INFINITY, or Float.NaN.
     */
    public boolean checkWeights(float tolerance) {
        // Sections' weights do not sum up to 100 plus/minus the tolerance.
        if (!Util.checkWeights(sections, tolerance)) {
            return false;
        }

        // Check that each section's questions' weights sum up to 100 plus/minus the tolerance.
        Iterator itr = sections.iterator();

        while (itr.hasNext()) {
            if (!((Section) itr.next()).checkWeights(tolerance)) {
                return false;
            }
        }

        // All sum of weights check out.
        return true;
    }
}
