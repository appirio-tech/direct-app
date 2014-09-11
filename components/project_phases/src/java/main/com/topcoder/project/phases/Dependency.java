/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The <code>Dependency</code> class represents the relationship between two phases. This class specifies whether the
 * dependent phase will start/end after the dependency phase starts/ends with a lag time. The lag time is accurate to
 * milliseconds. There are four different relationship of the dependent phase and dependency phase:
 * <ul>
 * <li>if <code>dependencyStart</code> is true, <code>dependentStart</code> is true, then the dependent phase will
 * starts after the dependency phase starts</li>
 * <li>if <code>dependencyStart</code> is true, <code>dependentStart</code> is false, then the dependent phase will
 * ends after the dependency phase starts</li>
 * <li>if <code>dependencyStart</code> is false, <code>dependentStart</code> is true, then the dependent phase will
 * starts after the dependency phase ends</li>
 * <li>if <code>dependencyStart</code> is false, <code>dependentStart</code> is false, then the dependent phase
 * will ends after the dependency phase ends</li>
 * </ul>
 * This class is serializable.
 * </p>
 * <p>
 * Thread Safety: This class is mutable. so it's not thread safe.
 * </p>
 *
 * @author oldbig, littlebull
 * @version 2.0
 */
@XmlType(name = "dependency", namespace = "com.topcoder.project.phases")
public class Dependency implements Serializable {
    /**
     * The dependency phase. Initialized in the constructor and could be accessed via getter and setter method. Cannot
     * be null.
     */
    private Phase dependency;

    /**
     * The dependent phase. Initialized in the constructor and could be accessed via getter and setter method. Cannot be
     * null.
     */
    private Phase dependent;

    /**
     * The dependency start/end flag. If the value is true, the dependent phase will start/end after the dependency
     * phase starts. If the value is false, the dependent phase will start/end after the dependency phase ends.
     * Initialized in the constructor and could be accessed via getter and setter method.
     */
    private boolean dependencyStart;

    /**
     * The dependent start/end flag. If the value is true, the dependent phase will start after the dependency phase
     * starts/ends. If the value is false, the dependent phase will end after the dependency phase starts/ends.
     * Initialized in the constructor and could be accessed via getter and setter method.
     */
    private boolean dependentStart;

    /**
     * The lag time in milliseconds. The dependent phase will start/end after the dependency phase starts/ends with this
     * lag time. Negative lag time is allowed. Initialized in the constructor and could be accessed via getter and
     * setter method.
     */
    private long lagTime;

    /**
     * Creates a <code>Dependency</code> instance with given dependency, dependent, dependency start/end flag,
     * dependent start/end flag and the lag time.
     *
     * @param dependency
     *            the dependency phase
     * @param dependent
     *            the dependent phase
     * @param dependencyStart
     *            the dependency start/end flag
     * @param dependentStart
     *            the dependent start/end flag
     * @param lagTime
     *            the lag time in milliseconds
     * @throws IllegalArgumentException
     *             if <code>dependency</code> or <code>dependent</code> is null, or <code>dependency</code> and
     *             <code>dependent</code> are same instance
     */
    public Dependency(Phase dependency, Phase dependent, boolean dependencyStart, boolean dependentStart,
        long lagTime) {
        ProjectPhaseHelper.checkObjectNotNull(dependency, "dependency");
        ProjectPhaseHelper.checkObjectNotNull(dependent, "dependent");
        ProjectPhaseHelper.checkPhaseNotSameInstance(dependency, dependent);

        this.dependency = dependency;
        this.dependent = dependent;
        this.dependencyStart = dependencyStart;
        this.dependentStart = dependentStart;
        this.lagTime = lagTime;
    }


	public Dependency() {

        this.dependency = null;
        this.dependent = null;
        this.dependencyStart = false;
        this.dependentStart = false;
        this.lagTime = 0;
    }

    /**
     * Sets the dependency phase.
     *
     * @param dependency
     *            the dependency phase
     * @throws IllegalArgumentException
     *             if argument is null, or <code>dependency</code> and <code>dependent</code> are same instance
     */
    public void setDependency(Phase dependency) {
        ProjectPhaseHelper.checkObjectNotNull(dependency, "dependency");
        ProjectPhaseHelper.checkPhaseNotSameInstance(dependency, dependent);

        this.dependency = dependency;
        this.dependent.notifyChange();
    }

    /**
     * Gets the dependency phase.
     *
     * @return the dependency phase
     */
    public Phase getDependency() {
        return dependency;
    }

    /**
     * Sets the dependent phase.
     *
     * @param dependent
     *            the dependent phase
     * @throws IllegalArgumentException
     *             if argument is null, or <code>dependency</code> and <code>dependent</code> are same instance
     */
    public void setDependent(Phase dependent) {
        ProjectPhaseHelper.checkObjectNotNull(dependent, "dependent");
        ProjectPhaseHelper.checkPhaseNotSameInstance(dependency, dependent);

        this.dependent = dependent;
        this.dependent.notifyChange();
    }

    /**
     * Gets the dependent phase.
     *
     * @return the dependent phase
     */
    public Phase getDependent() {
        return dependent;
    }

    /**
     * Sets the dependency start/end flag. If the value is true, the dependent phase will start/end after the dependency
     * phase starts. If the value is false, the dependent phase will start/end after the dependency phase ends.
     *
     * @param dependencyStart
     *            the dependency start/end flag
     */
    public void setDependencyStart(boolean dependencyStart) {
        this.dependencyStart = dependencyStart;
        dependent.notifyChange();
    }

    /**
     * Returns the dependency start/end flag. If the value is true, the dependent phase will start/end after the
     * dependency phase starts. If the value is false, the dependent phase will start/end after the dependency phase
     * ends.
     *
     * @return the dependency start/end flag
     */
    public boolean isDependencyStart() {
        return dependencyStart;
    }

    /**
     * Sets the dependent start/end flag. If the value is true, the dependent phase will start after the dependency
     * phase starts/ends. If the value is false, the dependent phase will end after the dependency phase starts/ends.
     *
     * @param dependentStart
     *            the dependent start/end flag
     */
    public void setDependentStart(boolean dependentStart) {
        this.dependentStart = dependentStart;
        dependent.notifyChange();
    }

    /**
     * Returns the dependent start/end flag. If the value is true, the dependent phase will start after the dependency
     * phase starts/ends. If the value is false, the dependent phase will end after the dependency phase starts/ends.
     *
     * @return the dependent start/end flag
     */
    public boolean isDependentStart() {
        return dependentStart;
    }

    /**
     * Sets the lag time in milliseconds. The dependent phase will start/end after the dependency phase starts/ends with
     * this lag time.
     *
     * @param lagTime
     *            the lag time in milliseconds
     */
    public void setLagTime(long lagTime) {
        this.lagTime = lagTime;
        dependent.notifyChange();
    }

    /**
     * Gets the lag time in milliseconds. The dependent phase will start/end after the dependency phase starts/ends with
     * this lag time.
     *
     * @return the lag time in milliseconds
     */
    public long getLagTime() {
        return lagTime;
    }
}
