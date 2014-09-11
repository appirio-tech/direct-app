/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * This entity represents the relations between client and project.
 *
 * @version 1.0
 */
@Entity
@Table(name = "client_project")
@IdClass(ClientProject.ClientProjectPK.class)
public class ClientProject implements Serializable {
    /**
     * Represents the client id.
     */
    @Id
    private long clientId;
    
    /**
     * Represents the project id.
     */
    @Id
    private long projectId;
    
    /**
     * Empty constructor.
     */
    public ClientProject() {
    
    }
    
    /**
     * Gets the client id.
     * @return the client id.
     */
    public long getClientId() {
        return clientId;
    }
    
    /**
     * Sets the client id.
     * @param clientId the client id.
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
    
    /**
     * Sets the project id.
     * @param projectId the project id.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
    
    /**
     * Gets the project id.
     * @return the project id.
     */
    public long getProjectId() {
        return projectId;
    }
    
    class ClientProjectPK implements Serializable {
        /**
         * Represents the client id.
         */
        @Column(name = "client_id")
        private long clientId;
        
        /**
         * Represents the project id.
         */
        @Column(name = "project_id")
        private long projectId;
        
        /**
         * Empty constructor.
         */
        public ClientProjectPK() {
        
        }
        
        /**
         * Calculate the hash code.
         * @return the hash code.
         */
        public int hashCode() {
            return (int) (clientId + projectId);
        }
        
        /**
         * Checks whether two instance are equal.
         * @return true if they are equal, false otherwise.
         */
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ClientProjectPK other = (ClientProjectPK) obj;
            return clientId == other.clientId && projectId == other.projectId;
        }
        
        /**
         * Gets the client id.
         * @return the client id.
         */
        public long getClientId() {
            return clientId;
        }
        
        /**
         * Sets the client id.
         * @param clientId the client id.
         */
        public void setClientId(long clientId) {
            this.clientId = clientId;
        }
        
        /**
         * Sets the project id.
         * @param projectId the project id.
         */
        public void setProjectId(long projectId) {
            this.projectId = projectId;
        }
        
        /**
         * Gets the project id.
         * @return the project id.
         */
        public long getProjectId() {
            return projectId;
        }
    }
}

