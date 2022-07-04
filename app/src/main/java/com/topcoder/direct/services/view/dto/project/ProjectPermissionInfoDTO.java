/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import java.io.Serializable;

/**
 * <p>
 * This class represents the project permission info displayed in the project overview page.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class ProjectPermissionInfoDTO implements Serializable {

    /**
     * The number of users with report project permission.
     */
    private int reportPermissionNumber;

    /**
     * The number of users with read project permission.
     */
    private int readPermissionNumber;

    /**
     * The number of users with write project permission.
     * @since 1.2
     */
    private int writePermissionNumber;

    /**
     * The number of users with full project permission.
     * @since 1.2
     */
    private int fullPermissionNumber;

    /**
     * Gets the number of users with report project permission.
     *
     * @return the number of users with report project permission.
     */
    public int getReportPermissionNumber() {
        return reportPermissionNumber;
    }

    /**
     * Sets the number of users with report project permission.
     *
     * @param reportPermissionNumber the number of users with report project permission.
     */
    public void setReportPermissionNumber(int reportPermissionNumber) {
        this.reportPermissionNumber = reportPermissionNumber;
    }

    /**
     * Gets the number of users with read project permission.
     *
     * @return the number of users with read project permission.
     */
    public int getReadPermissionNumber() {
        return readPermissionNumber;
    }

    /**
     * Sets the number of users with read project permission.
     *
     * @param readPermissionNumber the number of users with read project permission.
     */
    public void setReadPermissionNumber(int readPermissionNumber) {
        this.readPermissionNumber = readPermissionNumber;
    }

    /**
     * Gets the number of users with write project permission.
     *
     * @return the number of users with write project permission.
     */
    public int getWritePermissionNumber() {
        return writePermissionNumber;
    }

    /**
     * Sets the number of users with write project permission.
     *
     * @param writePermissionNumber the number of users with write project permission.
     */
    public void setWritePermissionNumber(int writePermissionNumber) {
        this.writePermissionNumber = writePermissionNumber;
    }

    /**
     * Gets the number of users with full project permission.
     *
     * @return the number of users with full project permission.
     */
    public int getFullPermissionNumber() {
        return fullPermissionNumber;
    }

    /**
     * Sets the number of users with full project permission.
     *
     * @param fullPermissionNumber the number of users with full project permission.
     */
    public void setFullPermissionNumber(int fullPermissionNumber) {
        this.fullPermissionNumber = fullPermissionNumber;
    }

    /**
     * Gets the total number of users with permission.
     *
     * @return the total number of users with permission.
     */
    public int getTotalPermissionNumber() {
        return this.reportPermissionNumber + this.readPermissionNumber
                + this.writePermissionNumber + this.fullPermissionNumber;
    }
}
