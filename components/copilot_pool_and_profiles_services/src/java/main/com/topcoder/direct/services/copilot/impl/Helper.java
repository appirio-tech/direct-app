/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import java.util.Date;
import java.util.List;

import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.dto.CopilotPoolMember;
import com.topcoder.direct.services.copilot.dto.CopilotProfileDTO;
import com.topcoder.direct.services.copilot.dto.CopilotProjectDTO;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * Helper class for the component.
 * <p>
 * Thread safe: This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class Helper {

    /**
     * Private constructor.
     */
    private Helper() {
        // does nothing
    }

    /**
     * Logs the entrance of a method.
     *
     * @param logger the logger used to do the logging
     * @param method the name of the method
     * @param entryTime the time of method entry
     */
    static void logMethodEntrance(String method, Log logger, Date entryTime) {
        if (logger == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Entering method: ");
        sb.append(method);
        sb.append(" at ");
        sb.append(entryTime);
        logger.log(Level.DEBUG, sb.toString());
    }

    /**
     * Logs the exit of a method.
     *
     * @param method the name of the method
     * @param logger the logger used to do the logging
     * @param entryTime the time of method entry
     */
    static void logMethodExit(String method, Log logger, Date entryTime) {
        if (logger == null) {
            return;
        }
        Date exitDate = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append("Exiting method: ");
        sb.append(method);
        sb.append(" at ");
        sb.append(exitDate);
        sb.append(" ,duration time is ");
        sb.append(exitDate.getTime() - entryTime.getTime());
        sb.append(" ms");
        logger.log(Level.DEBUG, sb.toString());
    }

    /**
     * Logs the error.
     *
     * @param <T> the generic error type
     * @param error the exception error
     * @param method the method which the error occurred
     * @param logger the logger used to do the logging
     * @return the exception error
     */
    static <T extends Exception> T logError(T error, String method, Log logger) {
        if (logger == null) {
            return error;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Error occurred in method: ");
        sb.append(method);
        sb.append(", Details: ");
        sb.append(error.getMessage());
        logger.log(Level.ERROR, error, sb.toString());
        return error;
    }

    /**
     * Logs input arguments.
     *
     * @param logger the Logger instance
     * @param paramNames the names of the parameters
     * @param paramValues the values of the parameters
     */
    static void logInputArguments(Log logger, String[] paramNames, Object[] paramValues) {
        if (logger == null) {
            return;
        }
        StringBuilder sb = new StringBuilder("Input arguments:[");
        for (int i = 0; i < paramNames.length; i++) {
            sb.append(paramNames[i]);
            sb.append(":");
            sb.append(buildObjectLoggingInfo(paramValues[i]));
            if (i != paramNames.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        logger.log(Level.DEBUG, sb.toString());
    }

    /**
     * Build part of object string.
     *
     * @param obj the object
     * @return string representation of objects
     */
    private static String buildPartOfObjectString(Object obj) {
        StringBuilder sb = new StringBuilder();
        if (obj instanceof IdentifiableEntity) {
            sb.append("[class name:");
            sb.append(obj.getClass().getSimpleName());
            sb.append(", id:");
            sb.append(((IdentifiableEntity) obj).getId());
            sb.append("]");
            return sb.toString();
        }
        if (obj instanceof CopilotProfileDTO) {
            sb.append("[class name:CopilotProfileDTO,");
            sb.append("earnings:");
            sb.append(((CopilotProfileDTO) obj).getEarnings());
            sb.append("]");
            return sb.toString();
        }
        if (obj instanceof CopilotProjectDTO) {
            CopilotProjectDTO copilotProjectDTO = (CopilotProjectDTO) obj;
            sb.append("[class CopilotProjectDTO,");
            sb.append("totalPlannedContests:");
            sb.append(copilotProjectDTO.getTotalPlannedContests());
            sb.append(", totalFailedContests:");
            sb.append(copilotProjectDTO.getTotalFailedContests());
            sb.append(", totalRealBugRaces:");
            sb.append(copilotProjectDTO.getTotalRealBugRaces());
            sb.append(", totalRealContests:");
            sb.append(copilotProjectDTO.getTotalRealContests());
            sb.append(", totalRepostedContests:");
            sb.append(copilotProjectDTO.getTotalRepostedContests());
            sb.append("]");
            return sb.toString();
        }
        return sb.toString();
    }

    /**
     * Build object logging String information.
     *
     * @param obj the logged object.
     * @return the built string value
     */
    @SuppressWarnings("unchecked")
    private static String buildObjectLoggingInfo(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(buildPartOfObjectString(obj));
        if (obj instanceof CopilotPoolMember) {
            CopilotPoolMember copilotPoolMember = (CopilotPoolMember) obj;
            sb.append("[class name:CopilotPoolMember");
            sb.append(", currentContests:");
            sb.append(copilotPoolMember.getCurrentContests());
            sb.append(", currentProjects:");
            sb.append(copilotPoolMember.getCurrentProjects());
            sb.append(", totalBugRaces:");
            sb.append(copilotPoolMember.getTotalBugRaces());
            sb.append(", totalContests:");
            sb.append(copilotPoolMember.getTotalContests());
            sb.append(", totalFailedContests:");
            sb.append(copilotPoolMember.getTotalFailedContests());
            sb.append(", totalProjects:");
            sb.append(copilotPoolMember.getTotalProjects());
            sb.append(", totalRepostedContests:");
            sb.append(copilotPoolMember.getTotalRepostedContests());
            sb.append("]");
            return sb.toString();
        }
        if (obj instanceof List) {
            List list = (List) obj;
            sb.append("[list:[");
            for (int i = 0; i < list.size(); i++) {
                sb.append(buildObjectLoggingInfo(list.get(i)));
                if (i != list.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]]");
        }
        // for other simple object or null
        sb.append(obj);
        return sb.toString();
    }

    /**
     * Logs the return value.
     *
     * @param logger the Logger instance
     * @param returnValue the return value
     */
    static void logReturnValue(Log logger, Object returnValue) {
        if (logger == null) {
            return;
        }
        logger.log(Level.DEBUG, "return value:" + buildObjectLoggingInfo(returnValue));
    }

    /**
     * Checks whether the given Object is null and logs the IAE if it is null.
     *
     * @param arg the argument to check
     * @param name the name of the argument
     * @param method the method which the error occurred
     * @param logger the logger used to do the logging
     * @throws IllegalArgumentException if the given Object is null
     */
    static void checkNullWithLogging(Object arg, String name, String method, Log logger) {
        if (arg == null) {
            throw logError(new IllegalArgumentException(name + " should not be null."), method, logger);
        }
    }

    /**
     * Checks whether the given Object is null.
     *
     * @param arg the argument to check
     * @param name the name of the argument
     * @throws IllegalArgumentException if the given Object is null
     */
    static void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * Checks whether the given argument is positive.
     *
     * @param method the method which the error occurred
     * @param logger the logger used to do the logging
     * @param arg the argument to check
     * @param name the name of the argument to check
     * @throws IllegalArgumentException if the given argument is not positive
     */
    static void checkPositive(long arg, String name, String method, Log logger) {
        if (arg <= 0) {
            throw logError(new IllegalArgumentException(name + " should be positive."), method, logger);
        }
    }

    /**
     * Checks whether the given Object is null.
     *
     * @param arg the argument to check
     * @param name the name of the argument
     * @throws CopilotServiceInitializationException if the given Object is null
     */
    static void checkNullForInjectedValue(Object arg, String name) {
        if (arg == null) {
            throw new CopilotServiceInitializationException(name + " should not be null.");
        }
    }
}