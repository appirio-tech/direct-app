/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.topcoder.direct.services.project.task.EntityNotFoundException;
import com.topcoder.direct.services.project.task.PermissionException;
import com.topcoder.direct.services.project.task.PersistenceException;
import com.topcoder.direct.services.project.task.TaskManagementConfigurationException;
import com.topcoder.direct.services.project.task.model.ContestDTO;
import com.topcoder.direct.services.project.task.model.MilestoneDTO;
import com.topcoder.direct.services.project.task.model.Task;
import com.topcoder.direct.services.project.task.model.TaskAttachment;
import com.topcoder.direct.services.project.task.model.TaskList;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This is a helper class using in the package: <code>com.topcoder.direct.services.project.task.impl</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
final class ServiceHelper {

    /**
     * <p>
     * Selects the number of the tasks for the project id.
     * </p>
     */
    private static final String QUERY_PROJECT_NUM_STATEMENT = "SELECT COUNT(*) FROM tc_direct_project p WHERE p.project_id = ?";

    /**
     * <p>
     * Selects project name from project_id.
     * </p>
     */
    private static final String QUERY_PROJECT_NAME_BY_ID = "SELECT name from tc_direct_project p WHERE p.project_id = ?";

    /**
     * <p>
     * Represents the entrance message.
     * </p>
     */
    private static final String MESSAGE_ENTRANCE = "Entering method %1$s.";

    /**
     * <p>
     * Represents the exit message.
     * </p>
     */
    private static final String MESSAGE_EXIT = "Exiting method %1$s.";

    /**
     * <p>
     * Represents the error message.
     * </p>
     */
    private static final String MESSAGE_ERROR = "Error in method %1$s. Details:";

    /**
     * <p>
     * Represents the list of entity types to print out in the log.
     * </p>
     */
    private static final Class<?>[] POJOS = new Class<?>[] {
        TaskList.class,
        Task.class,
        ContestDTO.class,
        MilestoneDTO.class,
        UserDTO.class,
        TaskAttachment.class
    };

    /**
     * <p>
     * Prevents to create the instance and it has a private modifier.
     * </p>
     */
    private ServiceHelper() {
        // does nothing
    }

    /**
     * <p>
     * Checks state of object.
     * </p>
     *
     * @param isInvalid
     *            the state of object.
     * @param message
     *            the error message.
     *
     * @throws TaskManagementConfigurationException
     *             if isInvalid is <code>true</code>
     */
    static void checkState(boolean isInvalid, String message) {
        if (isInvalid) {
            throw new TaskManagementConfigurationException(message);
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be <code>null</code>.
     * </p>
     *
     * @param logger
     *            the logger object.
     * @param signature
     *            the signature of the method to be logged.
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     *
     * @throws IllegalArgumentException
     *             if the value of the variable is <code>null</code>.
     */
    static void checkNull(Log logger, String signature, Object value, String name) {
        if (value == null) {
            // Log exception
            throw logException(logger, signature, new IllegalArgumentException("'" + name
                + "' should not be null."));
        }
    }



    /**
     * <p>
     * Validates the value of a string. The value can not be <code>null</code> or an empty string.
     * </p>
     *
     * @param logger
     *            the logger object (not null).
     * @param signature
     *            the signature of the method to be logged.
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     *
     * @throws IllegalArgumentException
     *             if the given string is <code>null</code> or an empty string.
     */
    static void checkNullOrEmpty(Log logger, String signature, String value, String name) {
        checkNull(logger, signature, value, name);

        if (value.trim().length() == 0) {
            // Log exception
            throw logException(logger, signature, new IllegalArgumentException("'" + name
                + "' should not be an empty string."));
        }
    }


    /**
     * <p>
     * Logs for entrance into public methods at <code>DEBUG</code> level.
     * </p>
     *
     * @param logger
     *            the logger object (not null).
     * @param signature
     *            the signature of the method to be logged.
     * @param paramNames
     *            the names of parameters to log .
     * @param params
     *            the values of parameters to log.
     */
    static void logEntrance(Log logger, String signature, String[] paramNames, Object[] params) {
        if (logger != null) {
            // Do logging
            logger.log(Level.DEBUG, String.format(MESSAGE_ENTRANCE, signature));
            if (paramNames != null) {
                // Log parameters
                logParameters(logger, paramNames, params);
            }
        }
    }

    /**
     * <p>
     * Logs for exit from public methods at <code>DEBUG</code> level.
     * </p>
     *
     * @param logger
     *            the logger object (not null).
     * @param signature
     *            the signature of the method to be logged.
     *
     * @param value
     *            the return value to log.
     * @param lazyFetch
     *            check if this is lazy fetch.
     * @param <T>
     *            the type of the return value.
     * @return the return object.
     */
    static <T> T logExit(Log logger, String signature, T value, boolean lazyFetch) {
        if (logger != null) {
            // Do logging
            logger.log(Level.DEBUG, String.format(MESSAGE_EXIT, signature));
            // Log return value
            logger.log(Level.DEBUG, "The return value: " + toString(value, lazyFetch));

        }
        return value;
    }

    /**
     * <p>
     * Logs for exit from public methods at <code>DEBUG</code> level.
     * </p>
     *
     * @param logger
     *            the logger object (not null).
     * @param signature
     *            the signature of the method to be logged.
     *
     * @param value
     *            the return value to log.
     * @param <T>
     *            the type of the return value.
     * @return the return object.
     */
    static <T> T logExit(Log logger, String signature, T value) {
        if (logger != null) {
            // Do logging
            logger.log(Level.DEBUG, String.format(MESSAGE_EXIT, signature));
            // Log return value
            logger.log(Level.DEBUG, "The return value: " + toString(value, false));

        }
        return value;
    }

    /**
     * <p>
     * Logs for exit from public methods at <code>DEBUG</code> level. The calling method does not have a return value.
     * </p>
     *
     * @param logger
     *            the logger object (not null).
     * @param signature
     *            the signature of the method to be logged.
     */
    static void logExit(Log logger, String signature) {
        if (logger != null) {
            // Do logging
            logger.log(Level.DEBUG, String.format(MESSAGE_EXIT, signature));
        }
    }

    /**
     * <p>
     * Logs the given exception and message at <code>ERROR</code> level. The calling method has a return value.
     * </p>
     *
     * @param <T>
     *            the exception type.
     * @param logger
     *            the logger object).
     * @param signature
     *            the signature of the method to log.
     * @param e
     *            the exception to log.
     *
     * @return the passed in exception.
     */
    static <T extends Throwable> T logException(Log logger, String signature, T e) {
        if (logger != null) {
            String errorMessage = String.format(MESSAGE_ERROR, signature);

            // Do logging
            logger.log(Level.ERROR, e, errorMessage);
        }
        return e;
    }

    /**
     * <p>
     * Logs the parameters at <code>DEBUG</code> level.
     * </p>
     *
     *
     * @param logger
     *            the logger object (not <code>null</code>).
     * @param paramNames
     *            the names of parameters to log (not <code>null</code>).
     * @param params
     *            the values of parameters to log (not <code>null</code>).
     */
    private static void logParameters(Log logger, String[] paramNames, Object[] params) {
        StringBuffer sb = new StringBuffer("The parameters: {");

        for (int i = 0; i < params.length; i++) {

            if (i > 0) {
                // Append a comma
                sb.append(", ");
            }

            sb.append(paramNames[i]).append(":").append(toString(params[i], false));
        }
        sb.append("}.");

        // Do logging
        logger.log(Level.DEBUG, sb.toString());
    }

    /**
     * Create the string of parameter object.
     *
     * @param paramObj
     *            the parameter object
     * @param lazyFetch
     *            specifies if this is a lazy fetch object.
     * @return the string of parameter object
     */
    private static String toString(Object paramObj, boolean lazyFetch) {
        StringBuilder sb = new StringBuilder();
        if (paramObj == null) {
            return "null";
        }
        if (Arrays.asList(POJOS).contains(paramObj.getClass())) {
            // print out each field
            printPOJO(paramObj, lazyFetch, sb);
            return sb.toString();
        }
        if (paramObj instanceof List<?>) {
            List<?> list = (List<?>) paramObj;
            sb.append("[");
            for (Object obj : list) {
                sb.append(toString(obj, lazyFetch)).append(",");
            }
            sb.append("]");
            return sb.toString();
        }
        if (paramObj instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) paramObj;
            for (Object key : map.keySet()) {
                Object value = map.get(key);
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append("{").append(toString(key, lazyFetch)).append(":").append(toString(value, lazyFetch))
                    .append("}");
            }
            return sb.toString();
        }

        return paramObj.toString();
    }

    /**
     * <p>
     * Prints out the pojos to the log.
     * </p>
     * @param paramObj the object to print.
     * @param lazyFetch if lazy fetch, the tasks are not printed.
     * @param sb to build the log.
     */
    private static void printPOJO(Object paramObj, boolean lazyFetch, StringBuilder sb) {
        Method[] methods = paramObj.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") || method.getName().startsWith("is")) {
                if (lazyFetch && paramObj instanceof TaskList && method.getName().equals("getTasks")) {
                    // ignore the lazy fetch
                    continue;
                }
                if (method.getName().equals("getClass")) {
                    continue;
                }
                Object value = null;
                try {
                    value = method.invoke(paramObj);
                } catch (IllegalArgumentException e) {
                    // ignore
                } catch (IllegalAccessException e) {
                    // ignore
                } catch (InvocationTargetException e) {
                    // ignore
                }
                String fieldName = method.getName();
                if (fieldName.startsWith("get")) {
                    fieldName = fieldName.substring("get".length());
                }
                if (fieldName.startsWith("is")) {
                    fieldName = fieldName.substring("is".length());
                }
                fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                // print the value
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append("{").append(fieldName).append(":").append(toString(value, lazyFetch)).append("}");
            }
        }
    }

    /**
     * <p>
     * Query the list result from the query statement.
     * </p>
     * @param log the logger for logging.
     * @param methodName the name of the method for logging.
     * @param entityManager the entityManager.
     * @param statement the statement to retrieve the list.
     * @param parameters the parameters to set into the statement.
     * @param <T> the type of the object.
     * @return the list of the query result.
     */
    @SuppressWarnings("unchecked")
    static <T> List<T> queryListResult(Log log, String methodName,
        EntityManager entityManager, String statement, Object[] parameters) {
        Query query = createQuery(entityManager, statement, parameters);
        return query.getResultList();
    }

    /**
     * <p>
     * Queries the single result.
     * </P>
     * @param log the logger for logging.
     * @param methodName the name of the method for logging.
     * @param entityManager the entity manager to access the database.
     * @param statement the statement to query the result.
     * @param parameters the parameters to set into the statement.
     * @param clazz the type of the entity.
     * @param <T> the type of the object.
     * @return the entity, or null if not exist.
     * @throws PersistenceException if failed to get the object.
     */
    static <T> T querySingleResult(Log log, String methodName, EntityManager entityManager,
        String statement, Object[] parameters, Class<T> clazz) throws PersistenceException {
        try {
            Query query = createQuery(entityManager, statement, parameters);
            return clazz.cast(query.getSingleResult());
        } catch (ClassCastException e) {
            throw ServiceHelper.logException(
                log, methodName, new PersistenceException("The retrieving object is not in correct type.", e));
        } catch (NoResultException e) {
            // return null if the record not found
            return null;
        } catch (NonUniqueResultException  e) {
            throw ServiceHelper.logException(
                log, methodName, new PersistenceException("The retrieving object is not unique.", e));
        }

    }

    /**
     * <p>
     * Query the number count of the entities for a query statement.
     * </p>
     * @param log the logger for logging.
     * @param methodName the name of the method for logging.
     * @param entityManager the entity manager to access the database.
     * @param statement the query statement.
     * @param parameters the parameters to set into the statement.
     * @return the number that retrieved.
     */
    static int queryRecordNum(Log log, String methodName,
        EntityManager entityManager, String statement, Object[] parameters) {
        Query query = createQuery(entityManager, statement, parameters);
        return ((Long) query.getSingleResult()).intValue();
    }

    /**
     * <p>
     * Creates the native query.
     * </p>
     * @param entityManager the entity manager to access the database.
     * @param statement the statement to retrieve the result.
     * @param parameters the parameters to set into the statement.
     * @return the created query.
     */
    private static Query createNativeQuery(EntityManager entityManager, String statement, Object[] parameters) {
        Query query = entityManager.createNativeQuery(statement);
        setParameters(query, parameters);
        return query;
    }

    /**
     * <p>
     * Creates the hibernate query.
     * </p>
     * @param entityManager the entity manager to access the database.
     * @param statement the statement to retrieve the result.
     * @param parameters the parameters to set into the statement.
     * @return the created query.
     */
    private static Query createQuery(EntityManager entityManager, String statement, Object[] parameters) {
        Query query = entityManager.createQuery(statement);
        setParameters(query, parameters);
        return query;
    }

    /**
     * <p>
     * Sets the parameters to the query.
     * </p>
     * @param query the query to set the parameters.
     * @param parameters the parameter value to set.
     */
    private static void setParameters(Query query, Object[] parameters) {
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                query.setParameter(i + 1, parameters[i]);
            }
        }

    }

    /**
     * <p>
     * Checks if the project exists.
     * </p>
     * @param log the logger for logging.
     * @param methodName the name of the method for logging.
     * @param entityManager the entity manager to access the database.
     * @param projectId the id of the project.
     * @throws EntityNotFoundException if the project not found.
     */
    static void existsProject(Log log, final String methodName, EntityManager entityManager, long projectId)
        throws EntityNotFoundException {
        Query query = createNativeQuery(entityManager, QUERY_PROJECT_NUM_STATEMENT, new Object[] {projectId});
        int resultNum = ((BigDecimal) query.getSingleResult()).intValue();
        if (resultNum == 0) {
            throw ServiceHelper.logException(log, methodName,
                new EntityNotFoundException("The project does not exist for id:" + projectId));
        }
    }

    /**

     * <p>
     * Get project name by id.
     * </p>
     * @param log the logger for logging.
     * @param methodName the name of the method for logging.
     *
     * @param entityManager the entity manager to access the database.
     * @param projectId the id of the project.
     */
    static String projectNameById(Log log, final String methodName, EntityManager entityManager, long projectId)
        {
        Query query = createNativeQuery(entityManager, QUERY_PROJECT_NAME_BY_ID, new Object[] {projectId});
        String projectName = (String) query.getSingleResult();
        return projectName;
    }

    /**
     * <p>
     * Gets the user handle.
     * </p>
     * @param log the log for logging.
     * @param methodName the name of the name for logging.
     * @param userService the user service to retrieve the user info.
     * @param userId the id of the user.
     * @return the handle of the user.
     * @throws PermissionException if the user does not exist or other error occurs.
     */
    static String getUserHandle(Log log, final String methodName, UserService userService, long userId)
        throws PermissionException {
        String callerHandle;
        try {
            callerHandle = userService.getUserHandle(userId);
        } catch (UserServiceException e) {
            throw ServiceHelper.logException(log, methodName,
                new PermissionException("Failed to get the handle for the userId:" + userId, e));
        }
        return callerHandle;
    }

    /**
     * <p>
     * Performs the flush operation and wrapped the exception.
     * </p>
     * @param log the logger for logging.
     * @param methodName the name of the calling method for logging.
     * @param entityManager the entity manager to access the database.
     * @throws PersistenceException if failed to do the flush.
     */
    static void flush(Log log, final String methodName, EntityManager entityManager)
        throws PersistenceException {
        try {
            // flush it to the database to update the id
            entityManager.flush();
        } catch (javax.persistence.PersistenceException e) {
            // wrap the exception
            throw ServiceHelper.logException(log, methodName, new PersistenceException(
                "Failed to persist the object to database.", e));
        }
    }
}
