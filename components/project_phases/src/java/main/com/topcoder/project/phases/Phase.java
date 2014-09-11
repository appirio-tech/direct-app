/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import com.topcoder.date.workdays.WorkdaysUnitOfTime;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.Date;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Represent the phase making up of the project. Any phase can only belong to one project and has a non-negative length
 * in milliseconds. The phase also has the id, type and status attributes.
 * </p>
 * <p>
 * A phase could depend on a collection of other phases. The relationship between dependency and dependent could be
 * specified by the <code>Dependency</code> class, that dependent phase will start/end after the dependency phase
 * starts/ends with a lag time.
 * </p>
 * <p>
 * Phase could be attached a fixed start timestamp. Fixed start time is interpreted as "start no early than". It's the
 * earliest point when a phase can start.
 * </p>
 * <p>
 * Phase could also be attached scheduled start/end timestamps and actual start/end timestamps. Scheduled start/end
 * timestamps are the original plan for the project timeline. Actual start/end timestamps are available for the phase
 * that's already started/ended. They can override the start/end time calculated otherwise.
 * </p>
 * <p>
 * The phase start and end date could be calculated based on the dependencies and the timestamps.
 * </p>
 * <p>
 * Thread Safety: This class is mutable. so it's not thread safe.
 * </p>
 *
 * @author oldbig, littlebull
 * @version 2.0
 */
 @XmlType(name = "phase", namespace = "com.topcoder.project.phases")
public class Phase extends AttributableObject {
    /**
     * Represents the milliseconds value of one minute.
     */
    private static final long MINUTE_MS = 60000L;

    /**
     * Represents a set of dependency of this phase. It could be accessed in the add/remove/clear/get methods.
     */
    private Set<Dependency> dependencies = new HashSet<Dependency>();

    /**
     * Represents the project instance this phase belong to, it is initialized in the constructor and never changed
     * afterward.
     */
    private Project project;

    /**
     * Represents the length of the phase in milliseconds, it will be initialized in the constructor, and set in the
     * setter method afterward.
     */
    private long length;

    /**
     * Represents the phase id. Initialized in the constructor and could be accessed via getter and setter method. The
     * value could not be negative .
     */
    private long id;

    /**
     * Represents the phase type. Initialized in the constructor and could be accessed via getter and setter method. The
     * value could be null.
     */
    private PhaseType phaseType;

    /**
     * Represents the phase status. Initialized in the constructor and could be accessed via getter and setter method.
     * The value could be null.
     */
    private PhaseStatus phaseStatus;

    /**
     * Fixed start time is interpreted as "start no early than". It's the earliest point when a phase can start.
     * Initialized in the constructor and could be accessed via getter and setter method. The value could be null.
     */
    private Date fixedStartDate;

    /**
     * Scheduled start timestamp is the original plan for the project start timeline. Initialized in the constructor and
     * could be accessed via getter and setter method. The value could be null.
     */
    private Date scheduledStartDate;

    /**
     * Scheduled end timestamp is the original plan for the project end timeline. Initialized in the constructor and
     * could be accessed via getter and setter method. The value could be null.
     */
    private Date scheduledEndDate;

    /**
     * Actual start timestamp is available for the phase that's already started. It can override the start time
     * calculated. Initialized in the constructor and could be accessed via getter and setter method. The value could be
     * null.
     */
    private Date actualStartDate;

    /**
     * Actual end timestamp is available for the phase that's already ended. It can override the end time calculated.
     * Initialized in the constructor and could be accessed via getter and setter method. The value could be null.
     */
    private Date actualEndDate;

    /**
     * Represents the cached end date of this phase. It will be reset when calculating the phase start/end date after
     * any part of the project was changed.
     */
    private Date cachedEndDate;

    /**
     * Represents the cached start date of this phase. It will be reset when calculating the phase start/end date after
     * any part of the project was changed.
     */
    private Date cachedStartDate;


	
	/**
     * Create a new instance of Phase with the project this phase belong to, and length in milliseconds of this phase.
     * Phase will be add to the project automatically.
     *
     * @param project
     *            the project this phase belong to
     * @param length
     *            the length in milliseconds of the phase
     * @throws IllegalArgumentException
     *             if <code>project</code> is null or <code>length</code> is negative
     */
    public Phase() {
		this.project = null;

    }



    /**
     * Create a new instance of Phase with the project this phase belong to, and length in milliseconds of this phase.
     * Phase will be add to the project automatically.
     *
     * @param project
     *            the project this phase belong to
     * @param length
     *            the length in milliseconds of the phase
     * @throws IllegalArgumentException
     *             if <code>project</code> is null or <code>length</code> is negative
     */
    public Phase(Project project, long length) {
        ProjectPhaseHelper.checkObjectNotNull(project, "project");
        ProjectPhaseHelper.checkLongNotNegative(length, "length");

        this.project = project;
        this.length = length;

        // Add the phase to the project.
        this.project.addPhase(this);

        // Project#addPhase has already set the changed flag to true
        // so we don't need call notifyChange() method here
    }

    /**
     * Return the project this phase belong to.
     *
     * @return the project this phase belongs to
     */
    public Project getProject() {
        return project;
    }

	/**
     * Return the project this phase belong to.
     *
     * @return the project this phase belongs to
     */
    public void setProject(Project project) {
        this.project = project;
    }


    /**
     * Return the length of the phase in milliseconds.
     *
     * @return the length of phase in milliseconds
     */
    public long getLength() {
        return length;
    }

    /**
     * Set the length of the phase in milliseconds.
     *
     * @param length
     *            the length of phase in milliseconds
     * @throws IllegalArgumentException
     *             if the <code>length</code> is negative
     */
    public void setLength(long length) {
        ProjectPhaseHelper.checkLongNotNegative(length, "length");

        this.length = length;
        notifyChange();
    }

    /**
     * Gets the phase id.
     *
     * @return the phase id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the phase id.
     *
     * @param id
     *            the phase id
     * @throws IllegalArgumentException
     *             if <code>id</code> is negative
     */
    public void setId(long id) {
        ProjectPhaseHelper.checkLongNotNegative(id, "id");

        this.id = id;
    }

    /**
     * Gets the phase type.
     *
     * @return the phase type
     */
    public PhaseType getPhaseType() {
        return phaseType;
    }

    /**
     * Sets the phase type. The type could be null.
     *
     * @param type
     *            the phase type
     */
    public void setPhaseType(PhaseType type) {
        this.phaseType = type;
    }

    /**
     * Gets the phase status.
     *
     * @return the phase status
     */
    public PhaseStatus getPhaseStatus() {
        return phaseStatus;
    }

    /**
     * Sets the phase status. The status could be null.
     *
     * @param status
     *            the phase status
     */
    public void setPhaseStatus(PhaseStatus status) {
        this.phaseStatus = status;
    }

    /**
     * Return the fixed start date of the phase. Fixed start time is interpreted as "start no early than". It's the
     * earliest point when a phase can start. This date is optional. This date is optional. This method will return a
     * copy of <code>fixedStartDate</code> variable so that it will not be modified from outside.
     *
     * @return the fixed start date of the phase
     */
    public Date getFixedStartDate() {
        return (fixedStartDate == null) ? null : new Date(fixedStartDate.getTime());
    }

    /**
     * Sets the fixed start date of the phase. Fixed start time is interpreted as "start no early than". It's the
     * earliest point when a phase can start.
     *
     * @param fixedStartDate
     *            the fixed start date of the phase
     */
    public void setFixedStartDate(Date fixedStartDate) {
        this.fixedStartDate = (fixedStartDate == null) ? null : new Date(fixedStartDate.getTime());
        notifyChange();
    }

    /**
     * Gets the scheduled start date of this phase. Scheduled start timestamp is the original plan for the project start
     * timeline. This date is optional. This method will return a copy of <code>scheduledStartDate</code> variable so
     * that it will not be modified from outside.
     *
     * @return the scheduled start date of this phase.
     */
    public Date getScheduledStartDate() {
        return (scheduledStartDate == null) ? null : new Date(scheduledStartDate.getTime());
    }

    /**
     * Sets the scheduled start date of this phase. Scheduled start timestamp is the original plan for the project start
     * timeline. Could be null, but cannot be after <code>scheduledEndDate</code>.
     *
     * @param scheduledStartDate
     *            the scheduled start date of this phase.
     * @throws IllegalArgumentException
     *             if <code>scheduledEndDate</code> and <code>scheduledStartDate</code> are not null, but
     *             <code>scheduledEndDate</code> is before <code>scheduledStartDate</code>
     */
    public void setScheduledStartDate(Date scheduledStartDate) {
        ProjectPhaseHelper.checkDateNotBefore(scheduledEndDate, scheduledStartDate, "scheduledEndDate",
                "scheduledStartDate");
        this.scheduledStartDate = (scheduledStartDate == null) ? null : new Date(scheduledStartDate.getTime());
    }

    /**
     * Gets the scheduled end date of this phase. Scheduled start timestamp is the original plan for the project start
     * timeline. This date is optional. This method will return a copy of <code>scheduledEndDate</code> variable so
     * that it will not be modified from outside.
     *
     * @return the scheduled end date of this phase.
     */
    public Date getScheduledEndDate() {
        return (scheduledEndDate == null) ? null : new Date(scheduledEndDate.getTime());
    }

    /**
     * Sets the scheduled end date of this phase. Scheduled end timestamp is the original plan for the project end
     * timeline. Could be null, but cannot be before <code>scheduledStartDate</code>.
     *
     * @param scheduledEndDate
     *            the scheduled end date of this phase.
     * @throws IllegalArgumentException
     *             if <code>scheduledEndDate</code> and <code>scheduledStartDate</code> are not null, but
     *             <code>scheduledEndDate</code> is before <code>scheduledStartDate</code>
     */
    public void setScheduledEndDate(Date scheduledEndDate) {
        ProjectPhaseHelper.checkDateNotBefore(scheduledEndDate, scheduledStartDate, "scheduledEndDate",
                "scheduledStartDate");
        this.scheduledEndDate = (scheduledEndDate == null) ? null : new Date(scheduledEndDate.getTime());
    }

    /**
     * Gets the actual start date of this phase. This date is optional. This method will return a copy of
     * <code>actualStartDate</code> variable so that it will not be modified from outside.
     *
     * @return the actual start date of this phase.
     */
    public Date getActualStartDate() {
        return (actualStartDate == null) ? null : new Date(actualStartDate.getTime());
    }

    /**
     * Sets the actual start date of this phase. Could be null, but cannot be after <code>actualEndDate</code>.
     *
     * @param actualStartDate
     *            the actual start date of this phase.
     * @throws IllegalArgumentException
     *             if <code>actualEndDate</code> and <code>actualStartDate</code> are not null, but
     *             <code>actualEndDate</code> is before <code>actualStartDate</code>
     */
    public void setActualStartDate(Date actualStartDate) {
        ProjectPhaseHelper.checkDateNotBefore(actualEndDate, actualStartDate, "actualEndDate", "actualStartDate");
        this.actualStartDate = (actualStartDate == null) ? null : new Date(actualStartDate.getTime());
        notifyChange();
    }

    /**
     * Gets the actual end date of this phase. This method will return a copy of <code>actualEndDate</code> variable
     * so that it will not be modified from outside.
     *
     * @return the actual end date of this phase.
     */
    public Date getActualEndDate() {
        return (actualEndDate == null) ? null : new Date(actualEndDate.getTime());
    }

    /**
     * Sets the actual end date of this phase. Could be null, but cannot be before <code>actualStartDate</code>.
     *
     * @param actualEndDate
     *            the actual end date of this phase.
     * @throws IllegalArgumentException
     *             if <code>actualEndDate</code> and <code>actualStartDate</code> are not null, but
     *             <code>actualEndDate</code> is before <code>actualStartDate</code>
     */
    public void setActualEndDate(Date actualEndDate) {
        ProjectPhaseHelper.checkDateNotBefore(actualEndDate, actualStartDate, "actualEndDate", "actualStartDate");
        this.actualEndDate = (actualEndDate == null) ? null : new Date(actualEndDate.getTime());
        notifyChange();
    }

    /**
     * Sets the cached start date of this phase.
     *
     * @param cachedStartDate
     *            the cached start date
     */
    void setCachedStartDate(Date cachedStartDate) {
        this.cachedStartDate = (cachedStartDate == null) ? null : new Date(cachedStartDate.getTime());
    }

    /**
     * Sets the cached end date of this phase.
     *
     * @param cachedEndDate
     *            the cached end date
     */
    void setCachedEndDate(Date cachedEndDate) {
        this.cachedEndDate = (cachedEndDate == null) ? null : new Date(cachedEndDate.getTime());
    }

    /**
     * Add a dependency to this phase, if the given dependency already exists in the dependency list, nothing will
     * happen. Both dependency and dependent phases are expected to be in current project.
     * <p>
     * For the sake of efficiency, this method will not detect the loop dependency. Loop dependency will be detected in
     * <code>calcEndDate()</code> and <code>calcStartDate()</code> method.
     * </p>
     *
     * @param dependency
     *            the dependency to add
     * @throws IllegalArgumentException
     *             if the argument is null, or the <code>dependency</code> does not exist in the project, or
     *             <code>dependent</code> is not this phase
     */
    public void addDependency(Dependency dependency) {
        checkDependency(dependency);

        if (!dependencies.contains(dependency)) {
            dependencies.add(dependency);
            notifyChange();
        }
    }

    /**
     * Check the dependency. This method is used by <code>addDependency</code> and <code>removeDependency</code> to
     * avoid code redundancy.
     *
     * @param dependency
     *            the dependency to check
     * @throws IllegalArgumentException
     *             if the argument is null, or the <code>dependency</code> does not exist in the project, or
     *             <code>dependent</code> is not this phase
     */
    private void checkDependency(Dependency dependency) {
        ProjectPhaseHelper.checkObjectNotNull(dependency, "dependency");

        if (!project.containsPhase(dependency.getDependency())) {
            throw new IllegalArgumentException("The dependency does not exist in this project.");
        }

        if (dependency.getDependent() != this) {
            throw new IllegalArgumentException("The dependent is not this phase.");
        }
    }

    /**
     * Remove the dependency from the dependency set. If the given phase does not exist in the dependency list, nothing
     * will happen. After the removal, this phase will not depend on the removed one any more.
     *
     * @param dependency
     *            the dependency to remove
     * @throws IllegalArgumentException
     *             if the argument is null, or the <code>dependency</code> does not exist in the project, or
     *             <code>dependent</code> is not this phase
     */
    public void removeDependency(Dependency dependency) {
        checkDependency(dependency);

        if (dependencies.contains(dependency)) {
            dependencies.remove(dependency);
            notifyChange();
        }
    }

    /**
     * Clear all the dependency phases from the dependency set. This phase will not depend on any phase in the project
     * any more.
     */
    public void clearDependencies() {
        dependencies.clear();
        notifyChange();
    }

	/**
     * getter
     * any more.
     */
    public Set<Dependency> getDependencies() {
        return this.dependencies;
    }

	/**
     * getter
     * any more.
     */
    public void setDependencies(Set<Dependency> dependencies) {
		if (dependencies != null)
		{
			 this.dependencies = dependencies;
		}
    }

    /**
     * Return an array of all <code>Dependency</code> instances of this phase.
     *
     * @return an array of all <code>Dependency</code> instances of this phase
     */
    public Dependency[] getAllDependencies() {
        return (Dependency[]) dependencies.toArray(new Dependency[dependencies.size()]);
    }

    /**
     * Calculate the end date of this phase.
     *
     * @return the end date of the phase
     * @throws CyclicDependencyException
     *             if cyclic dependency exists
     */
    public Date calcEndDate() {
        // Calculate the state/end date of all phases, the phase end date will be cached
        project.calculateProjectDate();

        return cachedEndDate;
    }

    /**
     * Calculate the start date of this phase.
     *
     * @return the start date of the phase
     * @throws CyclicDependencyException
     *             if cyclic dependency exists
     */
    public Date calcStartDate() {
        // Calculate the state/end date of all phases, the phase start date will be cached
        project.calculateProjectDate();

        return cachedStartDate;
    }

    /**
     * Calculate the end date of this phase.
     * <p>
     * A recursion algorithm is provided. The time complexity is O(E), where E is the number of dependencies.
     * </p>
     *
     * @param visited
     *            the visited phases.
     * @param startDateCache
     *            the cached phase start date
     * @param endDateCache
     *            the cached phase end date
     * @return the end date of the phase
     * @throws CyclicDependencyException
     *             if cyclic dependency exists
     */
    Date calcEndDate(Set visited, Map startDateCache, Map endDateCache) {
        // Detect cyclic dependency
        if (visited.contains(this)) {
            throw new CyclicDependencyException("Cycle dependency detected.");
        }

        // if actual end time exists, return it
        if (actualEndDate != null) {
            return actualEndDate;
        }

        // if the end date is cached, return it
        if (endDateCache.containsKey(this)) {
            return (Date) endDateCache.get(this);
        }

        // calculate the phase start date plus phase length, the phase can't end before this time.
        Date latest = addDate(calcStartDate(visited, startDateCache, endDateCache), length);

        // add this phase to the visited phases set
        visited.add(this);

        // for each end dependency, calculate the dependency's start/end time plus lag time
        for (Iterator itr = dependencies.iterator(); itr.hasNext();) {
            Dependency dependency = (Dependency) itr.next();
            if (!dependency.isDependentStart()) {
                Date dependencyDate = null;
                if (dependency.isDependencyStart()) {
                    // dependent ends after dependency starts
                    dependencyDate = addDate(dependency.getDependency().calcStartDate(visited, startDateCache,
                            endDateCache), dependency.getLagTime());
                } else {
                    // dependent ends after dependency ends
                    dependencyDate = addDate(dependency.getDependency().calcEndDate(visited, startDateCache,
                            endDateCache), dependency.getLagTime());
                }
                // update the latest time
                if (dependencyDate.getTime() > latest.getTime()) {
                    latest = dependencyDate;
                }
            }
        }

        // remove this phase from the visited phases set
        visited.remove(this);

        // cache the end date for this phase
        endDateCache.put(this, latest);

        return latest;
    }

    /**
     * Calculate the start date of this phase.
     * <p>
     * A recursion algorithm is provided. The time complexity is O(E), where E is the number of dependencies.
     * </p>
     *
     * @param visited
     *            the visited phases.
     * @param startDateCache
     *            the cached phase start date
     * @param endDateCache
     *            the cached phase end date
     * @return the end date of the phase
     * @throws CyclicDependencyException
     *             if cyclic dependency exists
     */
    Date calcStartDate(Set visited, Map startDateCache, Map endDateCache) {
        // Detect cyclic dependency
        if (visited.contains(this)) {
            throw new CyclicDependencyException("Cycle dependency detected.");
        }

        // if actual start time exists, return it
        if (actualStartDate != null) {
            return new Date(actualStartDate.getTime());
        }

        // if the start date is cached, return it
        if (startDateCache.containsKey(this)) {
            return (Date) startDateCache.get(this);
        }

        // get the project start time, the phase can't start before this time.
        Date latest = project.getStartDate();

        // if the fixed start date exists, update the latest date if necessary
        if (fixedStartDate != null && fixedStartDate.getTime() > latest.getTime()) {
            latest = fixedStartDate;
        }

        // add this phase to the visited phases set
        visited.add(this);

        // for each start dependency, calculate the dependency's start/end time plus lag time
        for (Iterator itr = dependencies.iterator(); itr.hasNext();) {
            Dependency dependency = (Dependency) itr.next();
            if (dependency.isDependentStart()) {
                Date dependencyDate = null;
                if (dependency.isDependencyStart()) {
                    // dependent starts after dependency starts
                    dependencyDate = addDate(dependency.getDependency().calcStartDate(visited, startDateCache,
                            endDateCache), dependency.getLagTime());
                } else {
                    // dependent starts after dependency ends
                    dependencyDate = addDate(dependency.getDependency().calcEndDate(visited, startDateCache,
                            endDateCache), dependency.getLagTime());
                }
                // update the latest time
                if (dependencyDate.getTime() > latest.getTime()) {
                    latest = dependencyDate;
                }
            }
        }

        // remove this phase from the visited phases set
        visited.remove(this);

        // cache the start date for this phase
        startDateCache.put(this, latest);

        return latest;
    }

    /**
     * Calculate date with Workdays Component.
     *
     * @param date
     *            the date to perform the addition to
     * @param length
     *            the amount of time to add, in milliseconds
     * @return the Date result of adding the amount of time to the date taking into consideration the workdays
     *         definition.
     */
    private Date addDate(Date date, long length) {
        return project.getWorkdays().add(date, WorkdaysUnitOfTime.MINUTES, (int) (length / MINUTE_MS));
    }

    /**
     * Notifies that this phase is changed.
     */
    void notifyChange() {
		if (project != null)
		{
			project.setChanged(true);
		}
    }
}
