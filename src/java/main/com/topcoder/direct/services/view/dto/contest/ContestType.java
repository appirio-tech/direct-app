/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>An enumeration over the possible contest types.</p>
 *
 * <p>
 * Version 1.1 (Direct Pipeline Integration Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Removed unused constructor.</li>
 *     <li>Added grouping for contest types.</li>
 *   </ol>
 * </p>

 * @author isv, TCSDEVELOPER
 * @version 1.1
 */
public enum ContestType {

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Wireframe</code> contest type.</p>
     */
    WIREFRAME("Wireframes", "wf", 25, true),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Web Design</code> contest type.</p>
     */
    WEB_DESIGN("Web Design", "w", 1, true),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Web Design</code> contest type.</p>
     */
    WEB_ELEMENTS("Web Elements", "We", 4, true),


    /**
     * <p>A <code>ContestType</code> corresponding to <code>Logo Design</code> contest type.</p>
     */
    LOGO_DESIGN("Logo Design", "L", 3, true),

   /**
     * <p>A <code>ContestType</code> corresponding to <code>Icon</code> contest type.</p>
     */
    ICONS("Icon", "ic", 14, true),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Idea Generation</code> contest type.</p>
     */
    IDEA_GENERATION("Idea Generation", "ig", 26, true),


    /**
     * <p>A <code>ContestType</code> corresponding to <code>Print Design</code> contest type.</p>
     */
    PRINT_DESIGN("Print Design", "Pr", 12, true),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>PowerPoint Presentation</code> contest type.</p>
     */
    POWERPOINT_PRESENTATION("PowerPoint Presentation", "Pw", 21, true),


    /**
     * <p>A <code>ContestType</code> corresponding to <code>Application Front-End Design</code> contest type.</p>
     */
    APP_FRONT_END_DESIGN("Application Front-End Design", "ap", 5, true),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Front-End Flash</code> contest type.</p>
     */
    FRONT_END_FLASH("Front-End Flash", "F", 8, true),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Banners/Icons</code> contest type.</p>
     */
    BANNERS_ICONS("Banners/Icons", "bi", 4, true), 

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Widget or Mobile Screen Design</code> contest type.</p>
     */
    WIDGET_MOBILE_SCREEN_DESIGN("Widget or Mobile Screen Design", "wi", 6, true), 

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Other</code> contest type.</p>
     */
    OTHER("Other", "O", 18, true),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>UI Prototype</code> contest type.</p>
     */
    UI_PROTOTYPE("UI Prototype Competition", "ui", 19),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Specification</code> contest type.</p>
     */
    SPECIFICATION("Specification", "S", 6),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Architecture</code> contest type.</p>
     */
    ARCHITECTURE("Architecture", "ar", 7),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Component Design</code> contest type.</p>
     */
    COMPONENT_DESIGN("Design", "Cd", 1),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Component Dev</code> contest type.</p>
     */
    COMPONENT_DEVELOPMENT("Development", "Cv", 2),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Assembly</code> contest type.</p>
     */
    ASSEMBLY("Assembly Competition", "As", 14),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Test Scenarios</code> contest type.</p>
     */
    TEST_SCENARIOS("Test Scenarios", "Ts", 26),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Test Suites</code> contest type.</p>
     */
    TEST_SUITES("Test Suites", "Tu", 13),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>RIA Build</code> contest type.</p>
     */
    RIA_BUILD("RIA Build Competition", "Rb", 24),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>RIA Component</code> contest type.</p>
     */
    RIA_COMPONENT("RIA Component Competition", "Rc", 25),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Bug Races</code> contest type.</p>
     */
    BUG_RACES("Bug Races", "Br", 0), // TODO unknown

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Algorithm</code> contest type.</p>
     */
    ALGORITHM("Algorithm", "al", 0), // TODO unknown

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Marathon</code> contest type.</p>
     */
    MARATHON("Marathon", "M", 0), // TODO unknown

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Design</code> contest type.</p>
     */
    DESIGN("Design", "Ds", 1), // TODO duplicate

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Development</code> contest type.</p>
     */
    DEVELOPMENT("Development", "Dv", 2), // TODO duplicate

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Conceptualization</code> contest type.</p>
     */
    CONCEPTUALIZATION("Conceptualization", "c", 23),

    /**
     * <p>A <code>ContestType</code> corresponding to group of <code>Studio</code> contest type.</p>
     *
     * @since 1.1
     */
    STUDIO("Studio", true, new ContestType[] {WIREFRAME, WEB_DESIGN});

    /**
     * <p>A <code>String</code> providing the activity name. Such a name serves as a textual presentation of the
     * activity.</p>
     */
    private String name;

    /**
     * <p>A <code>String</code> providing the activity shortName. Such a short name serves as an ID of the activity.</p>
     */
    private String letter;

    /**
     * Contest type id.
     * @since BUGR-3913
     */
    private long id;
    
    /**
     * Whether the contest is studio type.
     * @since BUGR-3913
     */
    private boolean isStudio;

    /**
     * <p>A <code>ContestType[]</code> providing the types grouped into a single group.</p>
     *
     * @since 1.1
     */
    private ContestType[] types;

    /**
     * <p>Constructs new <code>ContestType</code> instance with specified properties.</p>
     *
     * @param name a <code>String</code> providing the type name. Such a name serves as a textual presentation of the
     *             type.
     * @param letter a <code>String</code> providing the type letter. Such a name serves as a identifier of the type.
     * @param id a <code>long</code> providing the ID of the type.
     * @param isStudio <code>true</code> if this contest type is <code>Studio</code> contest type; <code>false</code>
     *        otherwise.
     */
    private ContestType(String name, String letter, long id, boolean isStudio) {
        this.name = name;
        this.letter = letter;
        this.id = id;
        this.isStudio = isStudio;
    }
    
    /**
     * <p>Constructs new <code>ContestType</code> instance with specified properties.</p>
     *
     * @param name a <code>String</code> providing the type name. Such a name serves as a textual presentation of the
     *             type.
     * @param letter a <code>String</code> providing the type letter. Such a name serves as a identifier of the type.
     * @param id a <code>long</code> providing the ID of the type.
     */
    private ContestType(String name, String letter, long id) {
        this(name, letter, id, false);
    }

    /**
     * <p>Constructs new <code>ContestType</code> instance with specified properties.</p>
     *
     * @param name a <code>String</code> providing the type name. Such a name serves as a textual presentation of the
     *             type.
     * @param isStudio <code>true</code> if this contest type is <code>Studio</code> contest type; <code>false</code>
     *        otherwise.
     * @param groupedTypes a <code>ContestType[]</code> providing the contest types grouped into a single group.
     * @since 1.1
     */
    private ContestType(String name, boolean isStudio, ContestType[] groupedTypes) {
        this(name, null, 0, isStudio);
        this.types = groupedTypes;
    }

    /**
     * <p>Gets the <code>ContestType</code> instance matching the specified name.</p>
     *
     * @param id a <code>Long</code> providing the contest type id for requested contest type.
     * @param isStudio whether the contest is studio contest
     * @return an <code>ContestType</code> matching the specified name or <code>null</code> if there is none.
     * @since BUGR-3913
     */
    public static ContestType forIdAndFlag(long id, boolean isStudio) {
        ContestType[] types = ContestType.values();
        for (int i = 0; i < types.length; i++) {
            ContestType type = types[i];
            if (type.id == id && type.isStudio == isStudio) {
                return type;
            }
        }
        return WIREFRAME;
    }

    /**
     * <p>Gets the name of this type.</p>
     *
     * @return a <code>String</code> providing the type name. Such a name serves as a textual presentation of the
     *         type.
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>Gets the letter of this type.</p>
     *
     * @return a <code>String</code> providing the type letter. Such a name serves as a identifier of the type.
     */
    public String getLetter() {
        return this.letter.toLowerCase();
    }

    /**
     * <p>Gets the id of this type.</p>
     *
     * @return a <code>long</code> providing the type id. 
     * @since BUGR-3913
     */
    public long getId() {
        return id;
    }

    /**
     * <p>Gets the studio flag of this type.</p>
     *
     * @return a <code>boolean</code> providing the studio flag. 
     * @since BUGR-3913
     */
    public boolean isStudio() {
        return isStudio;
    }

    /**
     * <p>Gets the types grouped into a single group. This method should be called only if {@link #isGroup()} method
     * returns <code>true</code>.</p>
     *
     * @return a <code>ContestType[]</code> providing the types grouped into a single group.
     * @since 1.1
     */
    public ContestType[] getTypes() {
        return this.types;
    }

    /**
     * <p>Checks if this enum item represents rather a group of contest types instead of a single type. If this method
     * returns <code>true</code> then {@link #getTypes()} will return the list of grouped types.</p>
     *
     * @return <code>true</code> if this enum item represents a group of contest types; <code>false</code> otherwise.
     */
    public boolean isGroup() {
        return getTypes() != null;
    }
}
