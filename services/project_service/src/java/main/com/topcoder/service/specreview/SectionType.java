/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents the section type for a spec review. Section examples are: Contest Overview, Contest Details etc.
 * 
 * @author snow01
 * @version 1.0
 * @since Cockpit Launch Contest - Inline Spec Review Part 2
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sectionType", propOrder = { "sectionTypeId", "name", "studio" })
public class SectionType implements Serializable {

    /**
     * Default Serial Version Id.
     */
    private static final long serialVersionUID = 1L;

    /** The section type id. */
    private long sectionTypeId;

    /** The name. */
    private String name;

    /** The studio. */
    private boolean studio;

    /**
     * Instantiates a new section type.
     */
    public SectionType() {

    }

    /**
     * Gets the section type id.
     * 
     * @return the section type id
     */
    public long getSectionTypeId() {
        return sectionTypeId;
    }

    /**
     * Sets the section type id.
     * 
     * @param sectionTypeId
     *            the new section type id
     */
    public void setSectionTypeId(long sectionTypeId) {
        this.sectionTypeId = sectionTypeId;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if is studio.
     * 
     * @return true, if is studio
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * Sets the studio.
     * 
     * @param studio
     *            the new studio
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }
}
