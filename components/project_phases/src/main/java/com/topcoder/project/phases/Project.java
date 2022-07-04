/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.DefaultWorkdays;

import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * <p>
 * This is the main class of this component, each application or component can be represented by a <code>Project</code>
 * instance which is composed of a collection of phases depending on each other.
 * </p>
 * <p>
 * For example, an application may be composed of design phase, design review phase, development phase, development
 * review phase, and deployment phase. The phases (except the first one) can only be started if the former one is
 * finished, each phase has a start date and length to finish, at the same time, the project has a start date so
 * that we can know when will the project be finished.
 * </p>
 * <p>
 * All the phases added to the project and dependent should belong to the same project.
 * </p>
 * <p>
 * Thread Safety: This class is mutable. so it's not thread safe.
 * </p>
 *
 * @author oldbig, littlebull
 * @version 2.0
 */
@XmlType(name = "project", namespace = "com.topcoder.project.phases")
public class Project extends AttributableObject {
    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents a set of phases owned by this project. This set could be accessed by the create/get/clear/remove
     * methods.
     * Updated the type of phases for Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     * Other corresponding instances has also been updated.
     */
    private Set<Phase> phases = new HashSet<Phase>();

    /**
     * Represents the instance of <code>Workdays</code> to calculate the end date for the phase in the project. It is
     * initialized in the constructor, and never changed later.
     */ 
    private DefaultWorkdays workdays;

    /**
     * Represents the start date of the project, it is initialized in the constructor, and changed in the setter method.
     */
    private Date startDate;

    /**
     * Represents the project id. Initialized in the constructor and could be accessed via getter and setter method. The
     * value could not be negative.
     */
    private long id;

    /**
     * The changed flag indicates whether the project and its phases are modified after last start/end date calculation.
     * The default value is true. It will be set to false in <code>calculateProjectDate</code>.
     */
    private boolean changed = true;

    /**
     * Create a new instance with the start date of the project and the <code>Workdays</code> instance. There is no
     * phases in the project initially.
     *
     * @param startDate
     *            the start date of the project
     * @param workdays
     *            the <code>Workdays</code> instance to calculate the end date for phases
     */
    public Project(Date startDate, DefaultWorkdays workdays) {
        this.workdays = (DefaultWorkdays)workdays;
        setStartDate(startDate);
    }

    /**
     * Create a new instance with the start date of the project and the <code>Workdays</code> instance. There is no
     * phases in the project initially.
     *
     */
    public Project() {
        this.workdays = null;
    }

    /**
     * Return the <code>Workdays</code> instance.
     *
     * @return the <code>Workdays</code> instance
     */
    public DefaultWorkdays getWorkdays() {
        return workdays;
    }
	public void setWorkdays(DefaultWorkdays workdays)
	{
		this.workdays = (DefaultWorkdays)workdays;
	}

    /**
     * Return the <code>startDate</code> of the project, this method will return a copy of <code>startDate</code>
     * variable so that it will not be modified from outside.
     *
     * @return the start date of this project
     */
    public Date getStartDate() {
        return startDate == null ? null : new Date(startDate.getTime());
    }

    /**
     * Set the <code>startDate</code> of the project.
     *
     * @param startDate
     *            the start date of this project
     */
    public void setStartDate(Date startDate) {
        if (startDate == null) {
            if (this.startDate != null) {
                this.startDate = null; 
                changed = true; 
            }  
        } else if (!startDate.equals(this.startDate)) {
            this.startDate = new Date(startDate.getTime());
            changed = true;
        }
    }

    /**
     * Gets the project id.
     *
     * @return the project id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the project id.
     *
     * @param id
     *            the project id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the changed flag.
     *
     * @return the changed flag
     */
    boolean isChanged() {
        return changed;
    }

    /**
     * Set the changed flag.
     *
     * @param changed
     *            the changed flag
     */
    void setChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * Add the phase into the project. This method is called by the constructor of <code>Phase</code>, so the phase
     * will automatically add to the project.
     *
     * @param phase
     *            the phase to add into the project.
     * @throws IllegalArgumentException
     *             if the <code>phase</code> is null or phase's project is not this one
     */
    public void addPhase(Phase phase) {
        ProjectPhaseHelper.checkObjectNotNull(phase, "phase");
//        if (phase.getProject().getId() != getId()) {
//            throw new IllegalArgumentException("The given phase is not belong to this project.");
//        }

        if (!phases.contains(phase)) {
            phases.add(phase);
            changed = true;
        }
    }

    /**
     * Remove the phase from the phases set, all its dependent phases will be removed automatically.
     * <p>
     * <b>Note:</b> This method also detects whether cyclic dependency is exist or not.
     * </p>
     *
     * @param phase
     *            the phase to remove from project
     * @throws CyclicDependencyException
     *             if cyclic dependency exists
     * @throws IllegalArgumentException
     *             if the <code>phase</code> is null or does not exist in this project
     */
    public void removePhase(Phase phase) {
        ProjectPhaseHelper.checkObjectNotNull(phase, "phase");
        if (!phases.contains(phase)) {
            throw new IllegalArgumentException("The phase is not exist.");
        }

        // Get all phases should be removed
        // including the given phase and all its dependent phases
        Set<Phase> removedPhases = getAllRemovedPhases(phase);

        phases.removeAll(removedPhases);
        changed = true;
    }

    /**
     * Get all phases should be removed, including the given phase and all its dependent phases. This is a DFS without
     * recursion. The complexity of algorithm is O(N + E), N is the number of phases, E is the number of dependencies.
     * <p>
     * <b>Note:</b> This method also detects whether cyclic dependency is exist or not.
     * </p>
     *
     * @param phase
     *            the phase to remove from project
     * @return all phases should be removed
     * @throws CyclicDependencyException
     *             if cyclic dependency exists
     */
    private Set<Phase> getAllRemovedPhases(Phase phase) {
        // This method has too many statements, 38 lines(max allowed is 30)
        // however we has no good reason to divide this method into several parts;)

        // We need two sets to store the removed phases and visited phases during DFS
        Set<Phase> removedPhases = new HashSet<Phase>();
        Set<Phase> visitedPhases = new HashSet<Phase>();
        removedPhases.add(phase);
        visitedPhases.add(phase);

        // We need three stacks to store the phase node, dependencies array and index during DFS
        Stack<Phase> stack = new Stack<Phase>();
        Stack<Dependency[]> arrayStack = new Stack<Dependency[]>();
        Stack<Integer> indexStack = new Stack<Integer>();
        stack.ensureCapacity(phases.size());
        arrayStack.ensureCapacity(phases.size());
        indexStack.ensureCapacity(phases.size());

        // Iterate all phases of this project
        for (Iterator<Phase> itr = phases.iterator(); itr.hasNext();) {
            Phase nextPhase = (Phase) itr.next();

            // It's important not to process visited nodes.
            if (visitedPhases.contains(nextPhase)) {
                continue;
            }

            // DFS starts here.
            visitedPhases.add(nextPhase);
            stack.push(nextPhase);
            arrayStack.push(nextPhase.getAllDependencies());
            indexStack.push(new Integer(0));

            do {
                // Get the current dependencies array and index
                Dependency[] currentDependencies = (Dependency[]) arrayStack.peek();
                int currentIndex = ((Integer) indexStack.peek()).intValue();

                // See if there are more dependencies to look at
                if (currentIndex < currentDependencies.length) {
                    Phase dependency = currentDependencies[currentIndex].getDependency();

                    // Store the next index (currentIndex + 1) into stack
                    indexStack.pop();
                    indexStack.push(new Integer(currentIndex + 1));

                    // If dependency phase is not processed, we should take a look at it first
                    if (!visitedPhases.contains(dependency)) {
                        // Push new item into stack
                        visitedPhases.add(dependency);
                        stack.push(dependency);
                        arrayStack.push(dependency.getAllDependencies());
                        indexStack.push(new Integer(0));
                    } else {
                        // If the dependency exist in the phase node stack,
                        // means there exist a cycle
                        if (stack.contains(dependency)) {
                            throw new CyclicDependencyException("Cyclic dependency detected.");
                        }

                        // One of its dependency phase is removed!
                        // That means the whole stack is bad.
                        // Just think of it as a dependency chain.
                        // We wipe the stack out straight.
                        if (removedPhases.contains(dependency)) {
                            do {
                                removedPhases.add(stack.pop());
                            } while (!stack.empty());

                            // Break DFS to check the next phase of this project
                            arrayStack.clear();
                            indexStack.clear();
                            break;
                        }
                    }
                } else {
                    // No more dependencies to look at.
                    // That tells us we can keep the node.
                    stack.pop();
                    arrayStack.pop();
                    indexStack.pop();
                }
            } while (!stack.empty());

            // DFS ends here.
        }

        // OK, now return the removed phases set
        return removedPhases;
    }

    /**
     * Clear all the phases from the set.
     */
    public void clearPhases() {
        phases.clear();
        changed = true;
    }

    /**
     * <p>
     * Sets the phases.
     * </p>
     * 
     * @param phases the phases set.
     * Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
	public void setPhases(Set<Phase> phases)
	{
		if (phases != null)
		{
			this.phases = phases;
		}
	}

	/**
	 * <p>
	 * Gets the phases.
	 * </p>
	 * 
	 * @return the phases set.
	 * Flex Cockpit Launch Contest - Integrate Software Contests v1.0
	 */
	public Set<Phase> getPhases()
	{
		return this.phases;
	}

    /**
     * Return an array of all phases in this project. The phases are sorted by <code>PhaseDateComparator</code>.
     *
     * @return an array of all phases in this project
     */
    public Phase[] getAllPhases() {
        return getAllPhases(new PhaseDateComparator());
    }

    /**
     * Return an array of phases sorted by the given comparator.
     *
     * @param comparator
     *            the comparator instance to sort the phases
     * @return an array of sorted phases
     * @throws IllegalArgumentException
     *             if the argument is null
     * @throws ClassCastException
     *             if the <code>comparator</code> cannot compare the phases
     */
    public Phase[] getAllPhases(Comparator comparator) {
        ProjectPhaseHelper.checkObjectNotNull(comparator, "comparator");

        Phase[] results = (Phase[]) phases.toArray(new Phase[phases.size()]);
        Arrays.sort(results, comparator);
        return results;
    }

    /**
     * Return an array of initial phases which does not depend on the other phases in the project. For example, in the
     * project, phase D depends on phase C, phase C depends on phase B and A, then phase A and B should be returned.
     * This method will calculate the in-degree of each phase in the graph (project) first, and return all the phases
     * whose in-degree is zero.
     *
     * @return an array of initial phases which does not depend on the other phases in the project.
     */
    public Phase[] getInitialPhases() {
        Set<Phase> initialPhases = new HashSet<Phase>();

        for (Iterator<Phase> itr = phases.iterator(); itr.hasNext();) {
            Phase phase = (Phase) itr.next();
            if (phase.getAllDependencies().length == 0) {
                initialPhases.add(phase);
            }
        }

        return (Phase[]) initialPhases.toArray(new Phase[initialPhases.size()]);
    }

    /**
     * Return true if the specified phase is belong to current project.
     * <p>
     * <b>Note:</b> This method is added, because the <tt>Phase.addDependency()</tt> and
     * <tt>Phase.removeDependency()</tt> methods should check whether the dependency phase is belonging to the current
     * project. This method is package private. This is under the permission of designer, please see
     * https://software.topcoder.com/forum/c_forum_message.jsp?f=22404142&r=22995652
     * </p>
     *
     * @param phase the phase is to be tested
     * @return true if the specified phase is belong to current project, false otherwise
     */
    boolean containsPhase(Phase phase) {
        // I think no check of phase should be done, if the phase is null, simply return false.

        return phases.contains(phase);
    }

    /**
     * Return the end date of the project, project end date is the latest end date of all phases.
     *
     * @return the end date of the project.
     * @throws CyclicDependencyException
     *             if cyclic dependency exists
     */
    public Date calcEndDate() {
        // Project end date can not before project start date
        Date projectEndDate = this.startDate;

        for (Iterator<Phase> itr = phases.iterator(); itr.hasNext();) {
            Date phaseEndDate = ((Phase) itr.next()).calcEndDate();
            if (phaseEndDate.getTime() > projectEndDate.getTime()) {
                projectEndDate = phaseEndDate;
            }
        }

        // If no phase defined, the project startDate will be returned. So we need to make the copy.
        return new Date(projectEndDate.getTime());
    }

    /**
     * Calculate the state/end date of all phases and cache the date.
     *
     * @throws CyclicDependencyException
     *             if cyclic dependency exists
     */
    void calculateProjectDate() {
        if (!changed) {
            // if change flag is false, simply return
            return;
        }

        // Otherwise calculate the start and end date for each phase, and cache the date
        Map startDateCache = new HashMap();
        Map endDateCache = new HashMap();
        for (Iterator<Phase> itr = phases.iterator(); itr.hasNext();) {
            Phase phase = (Phase) itr.next();
            phase.setCachedStartDate(phase.calcStartDate(new HashSet<Phase>(), startDateCache, endDateCache));
            phase.setCachedEndDate(phase.calcEndDate(new HashSet<Phase>(), startDateCache, endDateCache));
        }

        // Set changed flag to false
        changed = false;
    }
}
