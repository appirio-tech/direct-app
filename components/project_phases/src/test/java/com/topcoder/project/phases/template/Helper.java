/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import junit.framework.Assert;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * Helper class to simplify the unit tests.
 * </p>
 * @author flying2hk
 * @version 1.1
 * @since 1.0
 */
public final class Helper {
    /**
     * <p>
     * Private constructor.
     * </p>
     *
     */
    private Helper() {
    }

    /**
     * <p>
     * Load the given configuration file into the Configuration Manager.
     * </p>
     * @param file configuration file
     * @throws ConfigManagerException to JUnit
     */
    public static void loadConfig(String file) throws ConfigManagerException {
        ConfigManager.getInstance().add(new File(file).getAbsolutePath());
    }

    /**
     * <p>
     * Clear all namespaces in the Configuration Manager.
     * </p>
     * @throws UnknownNamespaceException to JUnit
     */
    public static void clearConfig() throws UnknownNamespaceException {
        ConfigManager mgr = ConfigManager.getInstance();
        Iterator itr = mgr.getAllNamespaces();

        while (itr.hasNext()) {
            String ns = (String) itr.next();

            mgr.removeNamespace(ns);
        }
    }

    /**
     * <p>
     * Check the given phases against the sample phases, the sample phases are defined
     * in test_files/template/TCSTemplate_Design.xml.
     * </p>
     * @param phases the phases to check
     */
    public static void checkSamplePhases(Phase[] phases) {
        Assert.assertEquals("Incorrect number of phases.", 10, phases.length);
        for (int i = 0; i < phases.length; i++) {
            Phase phase = phases[i];
            PhaseType type = phase.getPhaseType();
            long typeId = type.getId();
            Dependency dependency = null;
            switch ((int) typeId) {
            case 1:
                Assert.assertEquals("Incorrect type name.", "Submission", type.getName());
                Assert.assertEquals("Incorrect length.", 604800000, phase.getLength());
                Assert.assertEquals("Incorrect number of dependencies.", 0, phase.getAllDependencies().length);
                break;
            case 2:
                Assert.assertEquals("Incorrect type name.", "Screening", type.getName());
                Assert.assertEquals("Incorrect length.", 86400000, phase.getLength());
                Assert.assertEquals("Incorrect number of dependencies.", 1, phase.getAllDependencies().length);
                dependency = phase.getAllDependencies()[0];
                Assert.assertEquals("Incorrect lag time.", 0, dependency.getLagTime());
                Assert.assertEquals("Incorrect dependent.", phase, dependency.getDependent());
                Assert.assertEquals("Incorrect dependency.", "Submission",
                        dependency.getDependency().getPhaseType().getName());
                Assert.assertFalse("Incorrect isDependencyStart.", dependency.isDependencyStart());
                Assert.assertTrue("Incorrect isDependentStart.", dependency.isDependentStart());
                break;
            case 3:
                Assert.assertEquals("Incorrect type name.", "Review", type.getName());
                Assert.assertEquals("Incorrect length.", 259200000, phase.getLength());
                Assert.assertEquals("Incorrect number of dependencies.", 1, phase.getAllDependencies().length);
                dependency = phase.getAllDependencies()[0];
                Assert.assertEquals("Incorrect lag time.", 0, dependency.getLagTime());
                Assert.assertEquals("Incorrect dependent.", phase, dependency.getDependent());
                Assert.assertEquals("Incorrect dependency.", "Screening",
                        dependency.getDependency().getPhaseType().getName());
                Assert.assertFalse("Incorrect isDependencyStart.", dependency.isDependencyStart());
                Assert.assertTrue("Incorrect isDependentStart.", dependency.isDependentStart());
                break;
            case 4:
                Assert.assertEquals("Incorrect type name.", "Appeals", type.getName());
                Assert.assertEquals("Incorrect length.", 86400000, phase.getLength());
                Assert.assertEquals("Incorrect number of dependencies.", 1, phase.getAllDependencies().length);
                dependency = phase.getAllDependencies()[0];
                Assert.assertEquals("Incorrect lag time.", 0, dependency.getLagTime());
                Assert.assertEquals("Incorrect dependent.", phase, dependency.getDependent());
                Assert.assertEquals("Incorrect dependency.", "Review",
                        dependency.getDependency().getPhaseType().getName());
                Assert.assertFalse("Incorrect isDependencyStart.", dependency.isDependencyStart());
                Assert.assertTrue("Incorrect isDependentStart.", dependency.isDependentStart());
                break;
            case 5:
                Assert.assertEquals("Incorrect type name.", "Appeals Response", type.getName());
                Assert.assertEquals("Incorrect length.", 86400000, phase.getLength());
                Assert.assertEquals("Incorrect number of dependencies.", 1, phase.getAllDependencies().length);
                dependency = phase.getAllDependencies()[0];
                Assert.assertEquals("Incorrect lag time.", 0, dependency.getLagTime());
                Assert.assertEquals("Incorrect dependent.", phase, dependency.getDependent());
                Assert.assertEquals("Incorrect dependency.", "Appeals",
                        dependency.getDependency().getPhaseType().getName());
                Assert.assertFalse("Incorrect isDependencyStart.", dependency.isDependencyStart());
                Assert.assertTrue("Incorrect isDependentStart.", dependency.isDependentStart());
                break;
            case 6:
                Assert.assertEquals("Incorrect type name.", "Aggregation", type.getName());
                Assert.assertEquals("Incorrect length.", 43200000, phase.getLength());
                Assert.assertEquals("Incorrect number of dependencies.", 1, phase.getAllDependencies().length);
                dependency = phase.getAllDependencies()[0];
                Assert.assertEquals("Incorrect lag time.", 0, dependency.getLagTime());
                Assert.assertEquals("Incorrect dependent.", phase, dependency.getDependent());
                Assert.assertEquals("Incorrect dependency.", "Appeals Response",
                        dependency.getDependency().getPhaseType().getName());
                Assert.assertFalse("Incorrect isDependencyStart.", dependency.isDependencyStart());
                Assert.assertTrue("Incorrect isDependentStart.", dependency.isDependentStart());
                break;
            case 7:
                Assert.assertEquals("Incorrect type name.", "Aggregation Review", type.getName());
                Assert.assertEquals("Incorrect length.", 43200000, phase.getLength());
                Assert.assertEquals("Incorrect number of dependencies.", 1, phase.getAllDependencies().length);
                dependency = phase.getAllDependencies()[0];
                Assert.assertEquals("Incorrect lag time.", 0, dependency.getLagTime());
                Assert.assertEquals("Incorrect dependent.", phase, dependency.getDependent());
                Assert.assertEquals("Incorrect dependency.", "Aggregation",
                        dependency.getDependency().getPhaseType().getName());
                Assert.assertFalse("Incorrect isDependencyStart.", dependency.isDependencyStart());
                Assert.assertTrue("Incorrect isDependentStart.", dependency.isDependentStart());
                break;
            case 8:
                Assert.assertEquals("Incorrect type name.", "Final Fixes", type.getName());
                Assert.assertEquals("Incorrect length.", 172800000, phase.getLength());
                Assert.assertEquals("Incorrect number of dependencies.", 1, phase.getAllDependencies().length);
                dependency = phase.getAllDependencies()[0];
                Assert.assertEquals("Incorrect lag time.", 0, dependency.getLagTime());
                Assert.assertEquals("Incorrect dependent.", phase, dependency.getDependent());
                Assert.assertEquals("Incorrect dependency.", "Aggregation Review",
                        dependency.getDependency().getPhaseType().getName());
                Assert.assertFalse("Incorrect isDependencyStart.", dependency.isDependencyStart());
                Assert.assertTrue("Incorrect isDependentStart.", dependency.isDependentStart());
                break;
            case 9:
                Assert.assertEquals("Incorrect type name.", "Final Review", type.getName());
                Assert.assertEquals("Incorrect length.", 86400000, phase.getLength());
                Assert.assertEquals("Incorrect number of dependencies.", 1, phase.getAllDependencies().length);
                dependency = phase.getAllDependencies()[0];
                Assert.assertEquals("Incorrect lag time.", 0, dependency.getLagTime());
                Assert.assertEquals("Incorrect dependent.", phase, dependency.getDependent());
                Assert.assertEquals("Incorrect dependency.", "Final Fixes",
                        dependency.getDependency().getPhaseType().getName());
                Assert.assertFalse("Incorrect isDependencyStart.", dependency.isDependencyStart());
                Assert.assertTrue("Incorrect isDependentStart.", dependency.isDependentStart());
                break;
            case 10:
                Assert.assertEquals("Incorrect type name.", "Component Preparation", type.getName());
                Assert.assertEquals("Incorrect length.", 86400000, phase.getLength());
                Assert.assertEquals("Incorrect number of dependencies.", 1, phase.getAllDependencies().length);
                dependency = phase.getAllDependencies()[0];
                Assert.assertEquals("Incorrect lag time.", 0, dependency.getLagTime());
                Assert.assertEquals("Incorrect dependent.", phase, dependency.getDependent());
                Assert.assertEquals("Incorrect dependency.", "Final Review",
                        dependency.getDependency().getPhaseType().getName());
                Assert.assertFalse("Incorrect isDependencyStart.", dependency.isDependencyStart());
                Assert.assertTrue("Incorrect isDependentStart.", dependency.isDependentStart());
                break;
            default:
                break;
            }
        }
    }

    /**
     * <p>
     * Check the generated start date against the default configuration:
     * 9:00 AM next Thursday.
     * </p>
     * @param date the date to check
     */
    public static void checkGeneratedStartDate(Date date) {
        // initialize a Calendar with the start date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // verify the week
       // Assert.assertEquals("Incorrect week.", Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) + 1, cal
                //.get(Calendar.WEEK_OF_YEAR));
        // verify day of week
        Assert.assertEquals("Incorrect day of week.", Calendar.THURSDAY, cal.get(Calendar.DAY_OF_WEEK));
        // verify hour
        Assert.assertEquals("Incorrect hour.", 9, cal.get(Calendar.HOUR_OF_DAY));
        // verify minute
        Assert.assertEquals("Incorrect minute.", 0, cal.get(Calendar.MINUTE));
        // verify second
        Assert.assertEquals("Incorrect second.", 0, cal.get(Calendar.SECOND));
    }

}
