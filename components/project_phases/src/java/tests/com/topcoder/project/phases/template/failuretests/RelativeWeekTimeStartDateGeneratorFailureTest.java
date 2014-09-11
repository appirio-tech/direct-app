package com.topcoder.project.phases.template.failuretests;

import junit.framework.TestCase;

import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator;

public class RelativeWeekTimeStartDateGeneratorFailureTest extends TestCase {

    private static final String NAMESPACE = "com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator";

    private int dayOfWeek = 1;

    private int hour = 0;

    private int minute = 0;

    private int second = 0;

    private int weekOffset = 0;

    protected void setUp() throws Exception {
        FailureTestsHelper.loadAllConfig();
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringNamespaceNull()
            throws Exception {
        try {
            new RelativeWeekTimeStartDateGenerator(null);
            fail("IllegalArgumentException must be thrown since namespace is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringNamespaceEmpty()
            throws Exception {
        try {
            new RelativeWeekTimeStartDateGenerator("");
            fail("IllegalArgumentException must be thrown since namespace is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringNamespaceEmptyTrimmed()
            throws Exception {
        try {
            new RelativeWeekTimeStartDateGenerator("    ");
            fail("IllegalArgumentException must be thrown since namespace is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWeekOffsetMissed() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 1);
            fail("IllegalArgumentException must be thrown since required property week_offset is missed");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWeekDayOfWeekMissed() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 2);
            fail("IllegalArgumentException must be thrown since required property day_of_week is missed");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWeekHourMissed() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 3);
            fail("IllegalArgumentException must be thrown since required property hour is missed");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWeekMinuteMissed() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 4);
            fail("IllegalArgumentException must be thrown since required property minute is missed");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWeekSecondMissed() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 5);
            fail("IllegalArgumentException must be thrown since required property second is missed");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWeekOffsetNotNumber() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 6);
            fail("IllegalArgumentException must be thrown since required property week_offset has wrong format");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWeekDayOfWeekNotNumber() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 7);
            fail("IllegalArgumentException must be thrown since required property day_of_week has wrong format");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWeekHourNotNumber() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 8);
            fail("IllegalArgumentException must be thrown since required property hour has wrong format");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWeekMinuteNotNumber() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 9);
            fail("IllegalArgumentException must be thrown since required property minute has wrong format");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWeekSecondNotNumber() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 10);
            fail("IllegalArgumentException must be thrown since required property second has wrong format");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWrongDayOfWeek1() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 11);
            fail("ConfigurationException must be thrown since dayOfWeek is less than 1");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWrongDayOfWeek2() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 12);
            fail("ConfigurationException must be thrown since dayOfWeek is greater than 7");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWrongHour1() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 13);
            fail("ConfigurationException must be thrown since hour is less than 0");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWrongHour2() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 14);
            fail("ConfigurationException must be thrown since hour is greater than 23");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWrongMinute1() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 15);
            fail("ConfigurationException must be thrown since minute is less than 0");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWrongMinute2() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 16);
            fail("ConfigurationException must be thrown since minute is greater than 59");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWrongSecond1() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 17);
            fail("ConfigurationException must be thrown since second is less than 0");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(String)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorStringWrongSecond2() {
        try {
            new RelativeWeekTimeStartDateGenerator(NAMESPACE + 18);
            fail("ConfigurationException must be thrown since second is greater than 59");
        } catch (ConfigurationException e) {
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(int,
     * int, int, int, int)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorIntIntIntIntIntWrongDayOfWeek1() {
        try {
            new RelativeWeekTimeStartDateGenerator(0, hour, minute, second,
                    weekOffset);
            fail("IllegalArgumentException must be thrown since dayOfWeek is less than 1");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(int,
     * int, int, int, int)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorIntIntIntIntIntWrongDayOfWeek2() {
        try {
            new RelativeWeekTimeStartDateGenerator(8, hour, minute, second,
                    weekOffset);
            fail("IllegalArgumentException must be thrown since dayOfWeek is greater than 7");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(int,
     * int, int, int, int)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorIntIntIntIntIntWrongHour1() {
        try {
            new RelativeWeekTimeStartDateGenerator(dayOfWeek, -1, minute,
                    second, weekOffset);
            fail("IllegalArgumentException must be thrown since hour is less than 0");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(int,
     * int, int, int, int)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorIntIntIntIntIntWrongHour2() {
        try {
            new RelativeWeekTimeStartDateGenerator(dayOfWeek, 25, minute,
                    second, weekOffset);
            fail("IllegalArgumentException must be thrown since hour is greater than 24");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(int,
     * int, int, int, int)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorIntIntIntIntIntWrongMinute1() {
        try {
            new RelativeWeekTimeStartDateGenerator(dayOfWeek, hour, -1, second,
                    weekOffset);
            fail("IllegalArgumentException must be thrown since minute is less than 0");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(int,
     * int, int, int, int)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorIntIntIntIntIntWrongMinute2() {
        try {
            new RelativeWeekTimeStartDateGenerator(dayOfWeek, hour, 60, second,
                    weekOffset);
            fail("IllegalArgumentException must be thrown since minute is greater than 59");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(int,
     * int, int, int, int)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorIntIntIntIntIntWrongSecond1() {
        try {
            new RelativeWeekTimeStartDateGenerator(dayOfWeek, hour, minute, -1,
                    weekOffset);
            fail("IllegalArgumentException must be thrown since second is less than 0");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /*
     * Test method for
     * 'com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator.RelativeWeekTimeStartDateGenerator(int,
     * int, int, int, int)'
     */
    public final void testRelativeWeekTimeStartDateGeneratorIntIntIntIntIntWrongSecond2() {
        try {
            new RelativeWeekTimeStartDateGenerator(dayOfWeek, hour, minute, 60,
                    weekOffset);
            fail("IllegalArgumentException must be thrown since second is greater than 59");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    protected void tearDown() throws Exception {
        FailureTestsHelper.clearTestConfig();
    }
}