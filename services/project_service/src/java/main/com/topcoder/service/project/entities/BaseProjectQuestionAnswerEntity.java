/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.entities;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * <p>
 * This class functions as the base of the ProjectQuestionAnswerEntity
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe.
 * </p>
 * 
 * @author leo_lol
 * @version 1.0
 * @since 1.0
 */
@MappedSuperclass
public abstract class BaseProjectQuestionAnswerEntity implements Serializable {
    /**
     * <p>
     * serial ID.
     * </p>
     */
    private static final long serialVersionUID = -7922939025256448454L;
    
    /**
     * Identifier of the entity.
     */
    @Id
    private long id;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    protected BaseProjectQuestionAnswerEntity() {
        // empty implementation.
    }

    /**
     * <p>
     * Getter of id field.
     * </p>
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Setter of id field.
     * </p>
     * 
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Override this method to make user entities behave correctly along with
     * Hibernate.
     * </p>
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    /**
     * <p>
     * Override this method to make user entities behave correctly along with
     * Hibernate.
     * </p>
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BaseProjectQuestionAnswerEntity)) {
            return false;
        }
        BaseProjectQuestionAnswerEntity other = (BaseProjectQuestionAnswerEntity) obj;
        if (id != other.id) {
            return false;
        }
        
        if(!getClass().equals(other.getClass())) {
            return false;
        }
        
        return true;
    }

}
