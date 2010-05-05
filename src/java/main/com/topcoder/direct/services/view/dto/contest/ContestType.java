/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>An enumeration over the possible contest types.</p>
 *
 * @author isv
 * @version 1.0
 */
public enum ContestType {

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Wireframe</code> contest type.</p>
     */
    WIREFRAME("Wireframe", "wf"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Web Design</code> contest type.</p>
     */
    WEB_DESIGN("Web Page Design", "w"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Web Design</code> contest type.</p>
     */
    WEB_ELEMENTS("Web Elements", "We"),


    /**
     * <p>A <code>ContestType</code> corresponding to <code>Logo Design</code> contest type.</p>
     */
    LOGO_DESIGN("Logo Design", "L"),

   /**
     * <p>A <code>ContestType</code> corresponding to <code>Icon</code> contest type.</p>
     */
    ICONS("Icon", "ic"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Idea Generation</code> contest type.</p>
     */
    IDEA_GENERATION("Idea Generation", "ig"),


    /**
     * <p>A <code>ContestType</code> corresponding to <code>Print Design</code> contest type.</p>
     */
    PRINT_DESIGN("Print Design", "Pr"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>PowerPoint Presentation</code> contest type.</p>
     */
    POWERPOINT_PRESENTATION("PowerPoint Presentation", "Pw"),


    /**
     * <p>A <code>ContestType</code> corresponding to <code>Application Front-End Design</code> contest type.</p>
     */
    APP_FRONT_END_DESIGN("Application Front End Design", "ap"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Front-End Flash</code> contest type.</p>
     */
    FRONT_END_FLASH("Front-End Flash", "F"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Banners/Icons</code> contest type.</p>
     */
    BANNERS_ICONS("Banners/Icons", "bi"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Widget or Mobile Screen Design</code> contest type.</p>
     */
    WIDGET_MOBILE_SCREEN_DESIGN("Widget or Mobile Screen Design", "wi"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Other</code> contest type.</p>
     */
    OTHER("Other", "O"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>UI Prototype</code> contest type.</p>
     */
    UI_PROTOTYPE("UI Prototypes", "ui"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Specification</code> contest type.</p>
     */
    SPECIFICATION("Specification", "S"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Architecture</code> contest type.</p>
     */
    ARCHITECTURE("Architecture", "ar"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Component Design</code> contest type.</p>
     */
    COMPONENT_DESIGN("Component Design", "Cd"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Component Dev</code> contest type.</p>
     */
    COMPONENT_DEVELOPMENT("Component Dev", "Cv"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Assembly</code> contest type.</p>
     */
    ASSEMBLY("Assembly Competition", "As"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Test Scenarios</code> contest type.</p>
     */
    TEST_SCENARIOS("Test Scenarios", "Ts"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Test Suites</code> contest type.</p>
     */
    TEST_SUITES("Test Suites", "Tu"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>RIA Build</code> contest type.</p>
     */
    RIA_BUILD("RIA Build", "Rb"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>RIA Component</code> contest type.</p>
     */
    RIA_COMPONENT("RIA Component", "Rc"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Bug Races</code> contest type.</p>
     */
    BUG_RACES("Bug Races", "Br"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Algorithm</code> contest type.</p>
     */
    ALGORITHM("Algorithm", "al"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Marathon</code> contest type.</p>
     */
    MARATHON("Marathon", "M"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Design</code> contest type.</p>
     */
    DESIGN("Design", "Ds"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Development</code> contest type.</p>
     */
    DEVELOPMENT("Development", "Dv"),

    /**
     * <p>A <code>ContestType</code> corresponding to <code>Marathon</code> contest type.</p>
     */
    CONCEPTUALIZATION("Conceptualization", "c");

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
     * <p>Constructs new <code>ContestType</code> instance with specified properties.</p>
     *
     * @param name   a <code>String</code> providing the type name. Such a name serves as a textual presentation of the
     *               type.
     * @param letter a <code>String</code> providing the type letter. Such a name serves as a identifier of the type.
     */
    private ContestType(String name, String letter) {
        this.name = name;
        this.letter = letter;
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
     * <p>Gets the <code>ContestType</code> instance matching the specified name.</p>
     *
     * @param name a <code>String</code> providing the name for requested contest type.
     * @return an <code>ContestType</code> matching the specified name or <code>null</code> if there is none.
     */
    public static ContestType forName(String name) {
        ContestType[] types = ContestType.values();
        for (int i = 0; i < types.length; i++) {
            ContestType type = types[i];
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return WIREFRAME;
    }
}
