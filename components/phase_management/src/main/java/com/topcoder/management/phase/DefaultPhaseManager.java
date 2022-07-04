/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.management.phase.validation.DefaultPhaseValidator;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import com.topcoder.util.errorhandling.BaseException;

import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>Default implementation of the <code>PhaseManager</code> interface. <code>DefaultPhaseManager</code> is not
 * thread safe since it is mutable and its state (data such as handlers) can be compromised through race condition
 * issues. To make this thread-safe we would have to ensure that all the methods that use the internal handlers map
 * have their access synchronized.
 *
 * <p>The purpose of this class is to have a facade which will allow a user to manage phase information (backed by
 * a data store). Phases can be started, ended, or cancelled. The logic to check the feasibility of the status
 * change as well as to move the status is pluggable through the {@link PhaseHandler PhaseHandler} registration API
 * ({@link #registerHandler registerHandler}, {@link #unregisterHandler unregisterHandler}, etc.).  Applications
 * can provide the plug-ins on a per phase type/operation basis if extra logic needs to be integrated.</p>
 *
 * <p>In addition, a phase validator can be provided that will ensure that all phases that are subject to
 * persistent storage operations are validated before they are persisted. This is a pluggable option.</p>
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 */

public class DefaultPhaseManager implements PhaseManager {

    /**
     * A comparator that compares to {@link Phase Phase} instances. This class is thread safe by virtue of not
     * having any state.
     *
     * @author RachaelLCook
     * @version 1.0
     */
    private class PhaseComparator implements Comparator {
        /**
         * Returns -1, 0, or 1 if the {@link Phase#getId ID} of the first Phase argument is less than, equal to, or
         * greater than the ID of the second <code>Phase</code>, respectively.
         *
         * @param o1 the first <code>Phase</code> to compare
         * @param o2 the second <code>Phase</code> to compare
         * @throws ClassCastException if either argument is not a Phase instance
         * @return -1, 0, or 1 if the ID of the first <code>Phase</code> argument is less than, equal to, or greater
         *   than the ID of the second <code>Phase</code>, respectively
         */
        public int compare(Object o1, Object o2) {
            long p1 = ((Phase) o1).getId();
            long p2 = ((Phase) o2).getId();

            if (p1 < p2) {
                return -1;
            } else if (p1 > p2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Mapping from {@link HandlerRegistryInfo HandlerRegistryInfo} keys to {@link PhaseHandler PhaseHandler}
     * values. This is used to look up phase handlers when performing operations.
     *
     * @see registerHandler
     * @see unregisterHandler
     */
    private final Map handlers = new HashMap();

    /**
     * The phase persistence. This member is initialized by the constructor and does not change over the life of the
     * object.
     */
    private final PhasePersistence persistence;

    /**
     * The ID generator. This member is initialized by the constructor and does not change over the life of the object.
     */
    private final IDGenerator idGenerator;

    /**
     * The phase validator.
     * @see #getPhaseValidator
     * @see #setPhaseValidator
     */
    private PhaseValidator phaseValidator = new DefaultPhaseValidator();

    /**
     * Creates a new manager configured based on the specified configuration namespace. The configuration parameters
     * are as follows.
     * <ul>
     *   <li><code>PhasePersistence.className</code> - the name of the class handling phase persistence (this class
     *     must have a constructor that accepts a single <code>String</code> argument)</li>
     *   <li><code>PhaseValidator.className</code> - the name of the class handling phase validation
     *       (<i>optional</i>)</li>
     *   <li><code>Idgenerator.className</code> - the class handling ID generation</li>
     *   <li><code>Idgenerator.sequenceName</code> - the name of the ID sequence</li>
     *   <ul><code>Handlers</code> - the handlers to be registerred
     *      <li><code>handler[x]</code> - the handler[x]</li>
     *   </ul>
     *   <ul>handler[x]
     *      <li><code>phaseType</code> - the type of the phase to handle<li>
     *      <li><code>operation</code> - the operation of the handler to perform, must be one of start, end, cancel
     *      <li><code>handlerDef</code> - the handler definition name in configuration of Object Factory
     *   </ul>
     * </ul>
     *
     * @throws ConfigurationException if any required configuration parameter is missing, or if any of the supplied
     *   parameters are invalid
     * @throws IllegalArgumentException if the argument is <code>null</code> or the empty string
     * @param namespace the configuration namespace
     */
    public  DefaultPhaseManager(String namespace) throws ConfigurationException {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace must be a non-null string");
        }

        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace must be a non-empty string");
        }

        ConfigManager manager = ConfigManager.getInstance();

        try {
            String persistenceClass = manager.getString(namespace, "PhasePersistence.className");
            if (persistenceClass == null) {
                throw new ConfigurationException("PhasePersistence.className does not exist");
            }

            try {
                Class pclass = Class.forName(persistenceClass);
                Constructor ctor = pclass.getConstructor(new Class[] {String.class});
                this.persistence = (PhasePersistence) ctor.newInstance(new Object[] {namespace});
            } catch (ClassNotFoundException ex) {
                throw new ConfigurationException("no such class: " + persistenceClass, ex);
            } catch (NoSuchMethodException ex) {
                throw new ConfigurationException("class " + persistenceClass
                                                 + " does not have the correct constructor", ex);
            } catch (IllegalAccessException ex) {
                throw new ConfigurationException("failed to instantiate persistence: " + persistenceClass, ex);
            } catch (InstantiationException ex) {
                throw new ConfigurationException("error instantiating class " + persistenceClass, ex);
            } catch (InvocationTargetException ex) {
                throw new ConfigurationException("error instantiating class " + persistenceClass, ex);
            } catch (ClassCastException ex) {
                throw new ConfigurationException("error casting persistence class " + persistenceClass, ex);
            }

            String validatorClass = manager.getString(namespace, "PhaseValidator.className");
            if (validatorClass != null) {
                try {
                    Class vclass = Class.forName(validatorClass);
                    this.phaseValidator = (PhaseValidator) vclass.newInstance();
                } catch (ClassNotFoundException ex) {
                    throw new ConfigurationException("no such class: " + validatorClass, ex);
                } catch (IllegalAccessException ex) {
                    throw new ConfigurationException("failed to instantiate ID generator: " + validatorClass, ex);
                } catch (InstantiationException ex) {
                    throw new ConfigurationException("error instantiating class " + validatorClass, ex);
                } catch (ClassCastException ex) {
                    throw new ConfigurationException("error casting validator class " + validatorClass, ex);
                }
            }

            String idSequence = manager.getString(namespace, "Idgenerator.sequenceName");
            String idClass = manager.getString(namespace, "Idgenerator.className");

            this.idGenerator = instantiateIDGenerator(idSequence, idClass);

            // load the handlers

            // create all the handlers defined in the configuration file
            // this is an optional property
            String[] handlerNames = manager.getStringArray(namespace, "Handlers");
            if (handlerNames != null && handlerNames.length > 0) {

                // first get the namespace for object factory
                // it's optional and if it's missed, the default one is the same with the current namespace
                String factoryNamespace = manager.getString(namespace, "ObjectFactoryNamespace");
                if (factoryNamespace == null || factoryNamespace.trim().length() == 0) {
                    throw new ConfigurationException("The namespace for object factory is missing.");
                }

                // create the object factory
                ObjectFactory factory = new ObjectFactory(
                        new ConfigManagerSpecificationFactory(factoryNamespace), ObjectFactory.BOTH);

                // for each handler defined, get its registry info and handler object
                // then register it
                // both info and handler key are required
                for (int i = 0; i < handlerNames.length; i++) {
                    // get registry info
                    PhaseType type = (PhaseType) factory.createObject(
                            this.getRequiredValue(namespace, handlerNames[i] + ".phaseType"));

                    PhaseOperationEnum op = this.getOperation(
                        this.getRequiredValue(namespace, handlerNames[i] + ".operation"));
                    if (op == null) {
                        throw new ConfigurationException("There is an error in the configurations,"
                            + "operation is not set or set incorrectly. It should be one of start, end and cancel.");
                    }

                    // get handler object
                    PhaseHandler handler =
                        (PhaseHandler) factory.createObject(
                                this.getRequiredValue(namespace, handlerNames[i] + ".handler"));

                    // register the handler
                    this.registerHandler(handler, type, op);
                }
            }
        } catch (UnknownNamespaceException ex) {
            throw new ConfigurationException("no such namespace '" + namespace + "'", ex);
        } catch (SpecificationConfigurationException ex) {
            throw new ConfigurationException("bad specification in configurations", ex);
        } catch (IllegalReferenceException ex) {
            throw new ConfigurationException("illegal references", ex);
        } catch (InvalidClassSpecificationException ex) {
            throw new ConfigurationException("invalid class specification", ex);
        }
    }

    /**
     * Get operation by name.
     *
     * @param name the name of the operation
     * @return the enum value
     */
    private PhaseOperationEnum getOperation(String name) {
        if (PhaseOperationEnum.START.getName().equals(name)) {
            return PhaseOperationEnum.START;
        } else if (PhaseOperationEnum.END.getName().equals(name)) {
            return PhaseOperationEnum.END;
        } else if (PhaseOperationEnum.CANCEL.getName().equals(name)) {
            return PhaseOperationEnum.CANCEL;
        } else {
            return null;
        }
    }

    /**
     * Get a required value from configuration manager. If the value is missed
     * (null/empty after trimmed), ConfigurationException will be thrown.
     *
     * @param namespace
     *            the namespace
     * @param name
     *            the name of the property
     * @return the value
     * @throws UnknownNamespaceException
     *             if the namespace doesn't exist
     * @throws ConfigurationException
     *             if the property missed
     */
    private String getRequiredValue(String namespace, String name)
        throws UnknownNamespaceException, ConfigurationException {
        ConfigManager manager = ConfigManager.getInstance();
        String value = manager.getString(namespace, name);
        if (value == null || value.trim().length() == 0) {
            throw new ConfigurationException("The '" + name + "' is required.");
        }
        return value;
    }
    /**
     * Creates a new <code>DefaultPhaseManager</code> with the specified persistent storage and ID generator.
     *
     * @throws IllegalArgumentException if either argument is <code>null</code>
     *
     * @param persistence the persistent storage manager for this phase manager
     * @param idGenerator the ID generator for this phase manager
     */
    public  DefaultPhaseManager(PhasePersistence persistence, IDGenerator idGenerator) {
        if (persistence == null) {
            throw new IllegalArgumentException("persistence must be non-null");
        }

        if (idGenerator == null) {
            throw new IllegalArgumentException("idGenerator must be non-null");
        }

        this.persistence = persistence;
        this.idGenerator = idGenerator;
    }

    /**
     * Helper function to instantiate the ID generator and wrap the exceptions.
     *
     * @param idSequence the ID sequence
     * @param idClass the ID class
     * @return the ID generator
     * @throws ConfigurationException if ID generator cannot be instantiated for any reason
     */
    private IDGenerator instantiateIDGenerator(String idSequence, String idClass) throws ConfigurationException {
        if (idSequence == null) {
            throw new ConfigurationException("Idgenerator.sequenceName does not exist");
        }

        if (idClass == null) {
            throw new ConfigurationException("Idgenerator.className does not exist");
        }

        try {
            return IDGeneratorFactory.getIDGenerator(idSequence, idClass);
        } catch (BaseException ex) {
            throw new ConfigurationException("failed to instantiate ID generator: " + idClass, ex);
        } catch (IllegalAccessException ex) {
            throw new ConfigurationException("failed to instantiate ID generator: " + idClass, ex);
        } catch (IllegalStateException ex) {
            throw new ConfigurationException("failed to instantiate ID generator: " + idClass, ex);
        } catch (ClassNotFoundException ex) {
            throw new ConfigurationException("failed to instantiate ID generator: " + idClass, ex);
        } catch (NoSuchMethodException ex) {
            throw new ConfigurationException("class " + idClass + " does not have the correct constructor", ex);
        } catch (InvocationTargetException ex) {
            throw new ConfigurationException("error instantiating class " + idClass, ex);
        } catch (InstantiationException ex) {
            throw new ConfigurationException("class " + idClass + " does not have the correct constructor", ex);
        } catch (ClassCastException ex) {
            throw new ConfigurationException("error casting ID generator class " + idClass, ex);
        }
    }

    /**
     * Synchronizes the current state of the specified project with persistent storage. This method first validates
     * all of the phases in the project, then generates IDs for any new phases. Finally, the phases of the
     * specified input project are compared to the phases already in the database. If any new phases are
     * encountered, they are added to the persistent store via {@link PhasePersistence#createPhases
     * createPhases}. If any phases are missing from the input, they are deleted using {@link
     * PhasePersistence#deletePhases deletePhases}. All other phases are updated using {@link
     * PhasePersistence#updatePhases updatePhases}.
     *
     * @throws IllegalArgumentException if either argument is <code>null</code> or the empty string
     * @throws PhaseManagementException if a phase fails validation, or if an error occurs while persisting the updates
     *   or generating the IDs
     *
     * @param project project for which to update phases
     * @param operator the operator performing the action
     */
    public void updatePhases(Project project, String operator) throws PhaseManagementException {
        checkUpdatePhasesArguments(project, operator);

        Phase[] phases = project.getAllPhases();
        PhaseValidator validator = this.getPhaseValidator();

        // first, validate all the phases if a validator exists
        if (validator != null) {
            for (int i = 0; i < phases.length; ++i) {
                try {
                    validator.validate(phases[i]);
                } catch (PhaseValidationException ex) {
                    throw new PhaseManagementException("validation failure for phase " + phases[i].getId(), ex);
                }
            }
        }

        try {
            // next, set the ID for any phases that need it
            for (int i = 0; i < phases.length; ++i) {
                if (persistence.isNewPhase(phases[i])) {
                    phases[i].setId(this.idGenerator.getNextID());
                }
            }

            // separate the phases into three batches: additions, deletions, and updates
            TreeSet delete = new TreeSet(new PhaseComparator());
            TreeSet add = new TreeSet(new PhaseComparator());
            TreeSet update = new TreeSet(new PhaseComparator());

            // initially, mark all of the currently existing phases for deletion
            Project existingProject = getPhases(project.getId());
            if (existingProject != null) {
                Phase[] existingPhases = existingProject.getAllPhases();
                for (int i = 0; i < existingPhases.length; ++i) {
                    delete.add(existingPhases[i]);
                }
            }

            // for each phase in the input project, determine whether it's a new phase or an existing phase
            // if it's an existing phase, remove it from the set of phases to delete
            for (int i = 0; i < phases.length; ++i) {
                if (delete.remove(phases[i])) {
                    // the phase already exists, so update it
                    update.add(phases[i]);
                } else {
                    // the phase doesn't already exist, so add it
                    add.add(phases[i]);
                }
            }

            // anything left over in the delete set at this point does not exist in the input project

            // finally, perform the creations, updates, and deletions
            if (add.size() > 0) {
                persistence.createPhases((Phase[]) add.toArray(new Phase[0]), operator);
            }
            if (update.size() > 0) {
                persistence.updatePhases((Phase[]) update.toArray(new Phase[0]), operator);
            }
            if (delete.size() > 0) {
                persistence.deletePhases((Phase[]) delete.toArray(new Phase[0]));
            }
        } catch (IDGenerationException ex) {
            throw new PhaseManagementException("cannot generate phase IDs", ex);
        } catch (PhasePersistenceException ex) {
            throw new PhaseManagementException("phase persistence error", ex);
        }
    }

    /**
     * Validates the arguments to <code>updatePhases</code>. A non-exceptional return indicates success.
     *
     * @param project project for which to update phases
     * @param operator the operator performing the action
     * @throws IllegalArgumentException if either argument is <code>null</code> or the empty string
     */
    private void checkUpdatePhasesArguments(Project project, String operator) {
        if (project == null) {
            throw new IllegalArgumentException("project must be non-null");
        }

        if (operator == null) {
            throw new IllegalArgumentException("operator must be non-null");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator must be non-empty");
        }
    }

    /**
     * Returns the <code>Project</code> corresponding to the specified ID. If no such project exists, returns
     * <code>null</code>.
     *
     * @throws PhaseManagementException if an error occurred querying the project from the persistent store
     *
     * @param project id of the project to fetch
     * @return the project corresponding to the specified ID, or <code>null</code> if no such project exists
     */
    public Project getPhases(long project) throws PhaseManagementException {
        try {
            return persistence.getProjectPhases(project);
        } catch (PhasePersistenceException ex) {
            throw new PhaseManagementException("phase persistence error", ex);
        }
    }

    /**
     * Similar to {@link #getPhases(long) getPhases(long)}, except this method queries multiple projects in one call.
     * Indices in the returned array correspond to indices in the input array. If a specified project cannot be
     * found, a <code>null</code> will be returned in the corresponding array position.
     *
     * @throws PhaseManagementException if an error occurred querying the projects from the persistent store
     * @throws IllegalArgumentException if <code>projects</code> is <code>null</code>
     *
     * @param projects the project IDs to look up
     * @return the <code>Project</code> instances corresponding to the specified project IDs
     */
    public Project[] getPhases(long[] projects) throws PhaseManagementException {
        if (projects == null) {
            throw new IllegalArgumentException("arguments to DefaultPhaseManager#getPhases must be non-null");
        }

        try {
            return persistence.getProjectPhases(projects);
        } catch (PhasePersistenceException ex) {
            throw new PhaseManagementException("phase persistence error", ex);
        }
    }

    /**
     * Returns an array of all phase types by calling the {@link PhasePersistence#getAllPhaseStatuses
     * getAllPhaseTypes} method of this manager's configured persistence object.
     *
     * @throws PhaseManagementException if an error occurred retrieiving the types from persistent storage
     *
     * @return an array of all the phase types
     */
    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
        try {
            return persistence.getAllPhaseTypes();
        } catch (PhasePersistenceException ex) {
            throw new PhaseManagementException("phase persistence error", ex);
        }
    }

    /**
     * Returns an array of all phase statuses by calling the {@link PhasePersistence#getAllPhaseStatuses
     * getAllPhaseStatuses} method of this manager's configured persistence object.
     *
     * @throws PhaseManagementException if an error occurred retrieiving the statuses from persistent storage
     *
     * @return an array of all the phase statuses
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
        try {
            return persistence.getAllPhaseStatuses();
        } catch (PhasePersistenceException ex) {
            throw new PhaseManagementException("phase persistence error", ex);
        }
    }

    /**
     * Determines whether it is possible to start the specified phase. If a {@link PhaseHandler phase handler} has
     * been registered for the start operation of the given phase type, its {@link PhaseHandler#canPerform
     * canPerform} method will be called to determine whether the phase can be started. If no hander is registered,
     * this method returns <code>true</code> if the phase's {@link Phase#calcStartDate start date} is less than or
     * equal to the current date.
     *
     * @throws IllegalArgumentException if phase is <code>null</code>
     * @throws PhaseHandlingException propagated from the phase handler (if any)
     *
     * @param phase phase to test for starting
     * @return <code>true</code> if the specified phase can be started; <code>false</code> otherwise
     */
    public boolean canStart(Phase phase) throws PhaseHandlingException {
        if (phase == null) {
            throw new IllegalArgumentException("null argument to DefaultPhaseManager#canStart");
        }

        PhaseHandler handler = getPhaseHandler(phase, PhaseOperationEnum.START);
        if (handler != null) {
            return handler.canPerform(phase);
        }

        return phase.calcStartDate().compareTo(new Date()) <= 0;
    }

    /**
     * Starts the specified phase. If a {@link PhaseHandler phase handler} is set for the start operation of the
     * phase's type, the handler's {@link PhaseHandler#perform perform} method is invoked first. Next, the phase's
     * status is set to {@link PhaseStatusEnum#OPEN OPEN} and the phase's actual start date is set to the current
     * date. Finally, the changes are persisted by delegating to the configured phase persistence object.
     *
     * @throws PhaseManagementException if an error occurs while persisting the change or in the phase handler
     * @throws IllegalArgumentException if either argument is <code>null</code> or an empty string
     *
     * @param phase the phase to start
     * @param operator the operator starting the phase
     */
    public void start(Phase phase, String operator) throws PhaseManagementException {
        if (phase == null) {
            throw new IllegalArgumentException("phase must be non-null");
        }

        if (operator == null) {
            throw new IllegalArgumentException("operator must be non-null");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator must be non-empty");
        }

        if (phase.getPhaseType() != null) {
            PhaseHandler handler = getPhaseHandler(phase, PhaseOperationEnum.START);
            if (handler != null) {
                handler.perform(phase, operator);
            }
        }

        phase.setPhaseStatus(new PhaseStatus(PhaseStatusEnum.OPEN.getId(), PhaseStatusEnum.OPEN.getName()));
        phase.setActualStartDate(new Date());

        Phase[] allPhases = phase.getProject().getAllPhases();
        recalculateScheduledDates(allPhases);

        try {
            persistence.updatePhases(allPhases, operator);
        } catch (PhasePersistenceException ex) {
            throw new PhaseManagementException("phase persistence error", ex);
        }
    }

    /**
     * Determines whether it is possible to end the specified phase. If a {@link PhaseHandler phase handler} has
     * been registered for the end operation of the given phase type, its {@link PhaseHandler#canPerform
     * canPerform} method will be called to determine whether the phase can be ended. If no hander is registered,
     * this method returns <code>true</code> if the phase's {@link Phase#calcEndDate end date} is less than or
     * equal to the current date.
     *
     * @throws IllegalArgumentException if phase is <code>null</code>
     * @throws PhaseHandlingException propagated from the phase handler (if any)
     *
     * @param phase phase to test for ending
     * @return <code>true</code> if the specified phase can be ended; <code>false</code> otherwise
     */
    public boolean canEnd(Phase phase) throws PhaseHandlingException {
        if (phase == null) {
            throw new IllegalArgumentException("null argument to DefaultPhaseManager#canEnd");
        }

        PhaseHandler handler = getPhaseHandler(phase, PhaseOperationEnum.END);
        if (handler != null) {
            return handler.canPerform(phase);
        }

        return phase.calcEndDate().compareTo(new Date()) <= 0;
    }

    /**
     * Ends the specified phase. If a {@link PhaseHandler phase handler} is set for the end operation of the
     * phase's type, the handler's {@link PhaseHandler#perform perform} method is invoked first. Next, the phase's
     * status is set to {@link PhaseStatusEnum#CLOSED CLOSED} and the phase's actual end date is set to the current
     * date. Finally, the changes are persisted by delegating to the configured phase persistence object.
     *
     * @throws PhaseManagementException if an error occurs while persisting the change or in the phase handler
     * @throws IllegalArgumentException if either argument is <code>null</code> or an empty string
     *
     * @param phase the phase to end
     * @param operator the operator ending the phase
     */
    public void end(Phase phase, String operator) throws PhaseManagementException {
        if (phase == null) {
            throw new IllegalArgumentException("phase must be non-null");
        }

        if (operator == null) {
            throw new IllegalArgumentException("operator must be non-null");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator must be non-empty");
        }

        if (phase.getPhaseType() != null) {
            PhaseHandler handler = getPhaseHandler(phase, PhaseOperationEnum.END);
            if (handler != null) {
                handler.perform(phase, operator);
            }
        }

        phase.setPhaseStatus(new PhaseStatus(PhaseStatusEnum.CLOSED.getId(), PhaseStatusEnum.CLOSED.getName()));
        phase.setActualEndDate(new Date());

        Phase[] allPhases = phase.getProject().getAllPhases();
        recalculateScheduledDates(allPhases);

        try {
            persistence.updatePhases(allPhases, operator);
        } catch (PhasePersistenceException ex) {
            throw new PhaseManagementException("phase persistence error", ex);
        }
    }

    /**
     * Determines whether it is possible to cancel the specified phase. If a {@link PhaseHandler phase handler} has
     * been registered for the cancel operation of the given phase type, its {@link PhaseHandler#canPerform
     * canPerform} method will be called to determine whether the phase can be cancelled. If no hander is
     * registered, this method returns <code>true</code>.
     *
     * @throws IllegalArgumentException if phase is <code>null</code>
     * @throws PhaseHandlingException propagated from the phase handler (if any)
     *
     * @param phase phase to test for cancellation
     * @return <code>true</code> if the phase can be cancelled; <code>false</code> otherwise
     */
    public boolean canCancel(Phase phase) throws PhaseHandlingException {
        if (phase == null) {
            throw new IllegalArgumentException("null argument to DefaultPhaseManager#canCancel");
        }

        PhaseHandler handler = getPhaseHandler(phase, PhaseOperationEnum.CANCEL);
        if (handler != null) {
            return handler.canPerform(phase);
        }

        return true;
    }

    /**
     * Cancels the specified phase. If a {@link PhaseHandler phase handler} is set for the cancel operation of the
     * phase's type, the handler's {@link PhaseHandler#perform perform} method is invoked first. Next, the phase's
     * status is set to {@link PhaseStatusEnum#CLOSED CLOSED} and the phase's actual end date is set to the current
     * date. Finally, the changes are persisted by delegating to the configured phase persistence object.
     *
     * @throws PhaseManagementException if an error occurs while persisting the change or in the phase handler
     * @throws IllegalArgumentException if either argument is <code>null</code> or an empty string
     *
     * @param phase the phase to cancel
     * @param operator the operator cancelling the phase
     */
    public void cancel(Phase phase, String operator) throws PhaseManagementException {
        if (phase == null) {
            throw new IllegalArgumentException("phase must be non-null");
        }

        if (operator == null) {
            throw new IllegalArgumentException("operator must be non-null");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator must be non-empty");
        }

        if (phase.getPhaseType() != null) {
            PhaseHandler handler = getPhaseHandler(phase, PhaseOperationEnum.CANCEL);
            if (handler != null) {
                handler.perform(phase, operator);
            }
        }

        phase.setPhaseStatus(new PhaseStatus(PhaseStatusEnum.CLOSED.getId(), PhaseStatusEnum.CLOSED.getName()));
        phase.setActualEndDate(new Date());

        Phase[] allPhases = phase.getProject().getAllPhases();
        recalculateScheduledDates(allPhases);

        try {
            persistence.updatePhases(allPhases, operator);
        } catch (PhasePersistenceException ex) {
            throw new PhaseManagementException("phase persistence error", ex);
        }
    }

    /**
     * <p>Registers a custom handler for the specified phase type and operation. If present, handlers override the
     * default behavior for determining whether a given operation can be performed on a given phase. If a handler
     * already exists for the specified type/operation combination, it will be replaced by the specified handler.</p>
     *
     * <p>Note that <code>type</code> is stored in the registry by reference (rather than copied) so the caller should
     * take care not to subsequently modify the type. Doing so may cause the registry to become inconsistent.</p>
     *
     * @throws IllegalArgumentException if any argument is null
     *
     * @param handler the handler
     * @param type the phase type to associate with the handler
     * @param operation the operation to associate with the handler
     */
    public void registerHandler(PhaseHandler handler, PhaseType type, PhaseOperationEnum operation) {
        if (handler == null) {
            throw new IllegalArgumentException("handler must be non-null");
        }
        if (type == null) {
            throw new IllegalArgumentException("type must be non-null");
        }
        if (operation == null) {
            throw new IllegalArgumentException("operation must be non-null");
        }

        handlers.put(new HandlerRegistryInfo(type, operation), handler);
    }

    /**
     * Unregisters the handler (if any) associated with the specified phase type and operation and returns a refernce
     * to the handler. Returns <code>null</code> if no handler is associated with the specified type/operation
     * combination.
     *
     * @throws IllegalArgumentException if either argument is <code>null</code>
     *
     * @param type the phase type associated with the handler to unregister
     * @param operation the operation associated with the handler to unregister
     * @return the previously registered handler, or <code>null</code> if no handler was registered
     */
    public PhaseHandler unregisterHandler(PhaseType type, PhaseOperationEnum operation) {
        if (type == null) {
            throw new IllegalArgumentException("type must be non-null");
        }
        if (operation == null) {
            throw new IllegalArgumentException("operation must be non-null");
        }

        return (PhaseHandler) handlers.remove(new HandlerRegistryInfo(type, operation));
    }

    /**
     * Returns an array of all the currently registered phase handlers. If a handler is registered more than one
     * (for different phase/operation combinations), it will appear only once in the array.
     *
     * @return all of the currently registered phase handlers
     */
    public PhaseHandler[] getAllHandlers() {
        // copying the values into a set will remove duplicate values
        Set allHandlers = new HashSet(handlers.values());
        return (PhaseHandler[]) allHandlers.toArray(new PhaseHandler[0]);
    }

    /**
     * Returns the phase type(s) and operation(s) associated with the specified handler in the handler
     * registry. Returns an empty array if the handler is not registered.
     *
     * @throws IllegalArgumentException if <code>handler</code> is <code>null</code>
     * @param handler handler of interest
     * @return the registration entries associated with the handler
     */
    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler must not be null");
        }

        final HashSet hri = new HashSet();
        for (Iterator it = handlers.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == handler) {
                hri.add(entry.getKey());
            }
        }

        return (HandlerRegistryInfo[]) hri.toArray(new HandlerRegistryInfo[0]);
    }

    /**
     * Sets the current phase validator for this manager.
     *
     * @throws IllegalArgumentException if the validator is null
     *
     * @param phaseValidator the validator to use for this manager
     */
    public void setPhaseValidator(PhaseValidator phaseValidator) {
        if (phaseValidator == null) {
            throw new IllegalArgumentException("phase validator cannot be set to null");
        }

        this.phaseValidator = phaseValidator;
    }

    /**
     * Returns the current phase validator. If no phase validator has been configured or set for this manager, an
     * instance of {@link DefaultPhaseValidator DefaultPhaseValidator} will be used instead.
     *
     * @return the current phase validator
     */
    public PhaseValidator getPhaseValidator() {
        return this.phaseValidator;
    }

    /**
     * This method selects all the dependencies for phases.
     * @param conn the database connection.
     * @param phases the map of already retrieved phases.
     * @param projectIds all the project ids.
     * @throws PhaseManagementException if database error occurs.
     */
    public void fillDependencies(Map phases, long[] projectIds)
        throws  PhaseManagementException 
    {
         try {
            persistence.fillDependencies(phases, projectIds);
        } catch (PhasePersistenceException ex) {
            throw new PhaseManagementException("phase persistence error", ex);
        }
    }

    /**
     * Returns the phaes handler associated with the specified phase and operation, or <code>null</code> if no such
     * handler exists.
     *
     * @param phase the phase
     * @param operation the phase operation
     * @return the phaes handler associated with the specified phase and operation
     */
    private PhaseHandler getPhaseHandler(Phase phase, PhaseOperationEnum operation) {
        HandlerRegistryInfo hri = new HandlerRegistryInfo(phase.getPhaseType(), operation);
        return (PhaseHandler) handlers.get(hri);
    }

    /**
     * Recalculate scheduled start date and end date for all phases when a phase is moved.
     *
     * @param allPhases all the phases for the project.
     */
    private void recalculateScheduledDates(Phase[] allPhases) {
        for (int i = 0; i < allPhases.length; ++i) {
            Phase phase = allPhases[i];
            phase.setScheduledStartDate(phase.calcStartDate());
            phase.setScheduledEndDate(phase.calcEndDate());
        }
    }
}
