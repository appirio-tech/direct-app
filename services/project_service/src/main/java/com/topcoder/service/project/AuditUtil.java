/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.topcoder.service.project.entities.BaseAudit;

/**
 * The utility class for auditing function.
 *
 * @author TCSASSEMBER
 * @version 1.0
 *
 * @since Release Assembly - TopCoder Direct Project Audit v1.0
 */
public final class AuditUtil {
    /**
     * Private Constructor.
     */
    private AuditUtil() {
    }

    /**
     * Audit the create actions.
     * 
     * @param <T> the type of the audit entity.
     * @param clazz the class of audit entity.
     * @param audits the <code>List</code> in which the audit entities should be stored.
     * @param recordId the id of the record which we are going to audit.
     * @param userId the user id of the operator.
     * @param fieldNames the field names to be audit.
     * @param fieldValues the field values of the field names to be audit. 
     */
    public static<T extends BaseAudit> void auditCreateAction(Class<T> clazz, List<T> audits, long recordId,
        long userId, String[] fieldNames, Object[] fieldValues) {
        Object[] oldFieldValues = new Object[fieldNames.length];
        addAudits(clazz, audits, BaseAudit.CREATE_AUDIT_ACTION_ID, recordId, userId, fieldNames,
            oldFieldValues, fieldValues, true);
    }

    /**
     * Audit the delete actions.
     *
     * @param <T> the type of the audit entity.
     * @param clazz the class of audit entity.
     * @param audits the <code>List</code> in which the audit entities should be stored.
     * @param recordId the id of the record which we are going to audit.
     * @param userId the user id of the operator.
     * @param fieldNames the field names to be audit.
     * @param fieldValues the field values of the field names to be audit. 
     */
    public static<T extends BaseAudit> void auditDeleteAction(Class<T> clazz, List<T> audits, long recordId,
        long userId, String[] fieldNames, Object[] fieldValues) {
        Object[] newFieldValues = new Object[fieldNames.length];
        addAudits(clazz, audits, BaseAudit.DELETE_AUDIT_ACTION_ID, recordId, userId, fieldNames,
            fieldValues, newFieldValues, true);
    }

    /**
     * Audit the update actions.
     *
     * @param <T> the type of the audit entity.
     * @param clazz the class of audit entity.
     * @param audits the <code>List</code> in which the audit entities should be stored.
     * @param recordId the id of the record which we are going to audit.
     * @param userId the user id of the operator.
     * @param fieldNames the field names to be audit.
     * @param oldFieldValues the original field values of the field names to be audit. 
     * @param newFieldValues the new field values of the field names to be audit.
     */
    public static<T extends BaseAudit> void auditUpdateAction(Class<T> clazz, List<T> audits, long recordId,
        long userId, String[] fieldNames, Object[] oldFieldValues, Object[] newFieldValues) {
        addAudits(clazz, audits, BaseAudit.UPDATE_AUDIT_ACTION_ID, recordId, userId,
            fieldNames, oldFieldValues, newFieldValues, false);
    }

    /**
     * Persist the audit records.
     *
     * @param <T> the type of the audit entity.
     * @param audits the audit records.
     * @param entityManager the <code>EntityManager</code> instance to be used for persisting.
     */
    public static<T extends BaseAudit> void persistAudits(List<T> audits, EntityManager entityManager) {
        for (BaseAudit audit : audits) {
            entityManager.persist(audit);
        }
    }

    /**
     * Added the audit records to the target list which should be audit.
     *
     * @param <T> the type of the audit entity.
     * @param clazz the class of audit entity.
     * @param audits the target list.
     * @param auditActionTypeId the id of the audit action type.
     * @param recordId the id of the record which we are going to audit.
     * @param userId the user id of the operator.
     * @param fieldNames the field names to be audit.
     * @param oldFieldValues the original field values of the field names to be audit. 
     * @param newFieldValues the new field values of the field names to be audit.
     * @param auditAll whether to audit all the fields (including the unchanged fields).
     */
    private static<T extends BaseAudit> void addAudits(Class<T> clazz, List<T> audits, int auditActionTypeId,
        long recordId, long userId, String[] fieldNames, Object[] oldFieldValues, Object[] newFieldValues,
        boolean auditAll) {
        try {
            for (int i = 0; i < fieldNames.length; i++) {
                if (auditAll || !equal(oldFieldValues[i], newFieldValues[i])) {
                    // only audit the changed fields
                    T audit = clazz.newInstance();
                    audit.setActionTypeId(auditActionTypeId);
                    audit.setRecordId(recordId);
                    audit.setFieldName(fieldNames[i]);
                    if (oldFieldValues[i] != null) {
                        audit.setOldValue(oldFieldValues[i].toString());
                    }
                    if (newFieldValues[i] != null) {
                        audit.setNewValue(newFieldValues[i].toString());
                    }
                    audit.setTimestamp(new Date());
                    audit.setActionUserId(userId);
                    audits.add(audit);
                }
            }
        } catch (InstantiationException e) {
            // should never happen
        } catch (IllegalAccessException e) {
            // should never happen
        }
    }

    /**
     * Checks whether two values are equal.
     * 
     * @param oldValue the old value.
     * @param newValue the new value.
     * @return true if two values are equal, false otherwise.
     */
    private static boolean equal(Object oldValue, Object newValue) {
        if (oldValue == null && newValue == null) {
            return true;
        }
        if (oldValue != null && oldValue.equals(newValue)
            || newValue != null && newValue.equals(oldValue)) {
            return true;
        }
        return false;
    }
}
