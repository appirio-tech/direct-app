/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import javax.persistence.Persistence;
import javax.persistence.Query;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.GroupContestEligibility;

/**
 * <p>
 * GroupEligibilityValidator is used to validate whether the user is in the group of contestEligibility.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is thread safe since it is immutable.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GroupEligibilityValidator implements ContestEligibilityValidator {

    /**
     * The persistence unit name is used to create entity manager by entity manager factory.Default value is
     * persistenceUnitName. You can change it by using the second constructor.
     */
    private String persistenceUnitName = "persistenceUnitName";

    /**
     * Default constructor.
     */
    public GroupEligibilityValidator() {
        // does nothing
    }

    /**
     * Creates an instance of GroupEligibilityValidator with the specified persistence unit name.
     *
     * @param persistenceUnitName
     *            the specified persistenceUnitName
     * @throws IllegalArgumentException
     *             if the persistenceUnitName is null or empty
     */
    public GroupEligibilityValidator(String persistenceUnitName) {
        if (persistenceUnitName == null || persistenceUnitName.trim().length() == 0) {
            throw new IllegalArgumentException(
                "Argument persistenceUnitName should be non-empty and non-null string value.");
        }
        this.persistenceUnitName = persistenceUnitName;
    }

    /**
     * Validate the given contestEligibility.
     *
     * @param userId
     *            the user id
     * @param contestEligibility
     *            the contestEligibility to validate
     * @return true if the user is eligible to join,otherwise false
     * @throws IllegalArgumentException
     *             if contestEligibility is null or it is not a GroupContestEligibility instance
     */
    public boolean validate(long userId, ContestEligibility contestEligibility) {

        // might be replaced by real checking web service,so let us make it simple.
        if (!(contestEligibility instanceof GroupContestEligibility)) {
            throw new IllegalArgumentException(
                "The contestEligibility should be a non-null GroupContestEligibility instance.");
        }
        final Query query =
            Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager()
                .createNativeQuery(
                    "select * from user_group_xref where security_status_id = 1 and login_id=:userId and group_id=:groupId");
        query.setParameter("userId", userId);
        query.setParameter("groupId", ((GroupContestEligibility) contestEligibility).getGroupId());
        return query.getResultList().size() > 0;
    }
}
