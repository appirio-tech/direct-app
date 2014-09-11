/*
 * Copyright (C)2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import com.topcoder.object.auditor.AuditDAOException;
import com.topcoder.object.auditor.Auditor;

/**
 * the mock Auditor for testing.(do nothing).
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAuditor extends Auditor {
    /**
     * mock method do nothing.
     * @param oldData
     *            the old data before modification
     * @param newData
     *            the new data after modification
     * @param user
     *            the user name
     * @param message
     *            the message for the audit
     * @throws IllegalStateException
     *             if the auditDAO is null
     * @throws IllegalArgumentException
     *             if any argument is null or any string argument is empty
     * @throws AuditDAOException
     *             if the AuditDAO implementation fails to perform the operation
     * @see AuditEntryImpl
     * @see AuditDAO#audit(AuditEntry)
     */
    public void logModification(Object oldData, Object newData, String user, String message) throws AuditDAOException {
    }

}
