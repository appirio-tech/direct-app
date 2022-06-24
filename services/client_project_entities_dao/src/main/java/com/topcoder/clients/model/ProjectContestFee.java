/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents project contest fee.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class contains only mutable fields so
 * therefore it is not thread safe.
 * </p>
 * <p>
 * Version 1.1 update for the Studio Contest Types - Release Assembly 1.0:
 * Remove the contest_type column, remove the sub_type column.
 * Adding is_studio and contest_type_id column.
 * Here contest_type_id column expected to refer to studio_oltp:contest_type_lu.contest_type_id for Studio contests
 * and refer to tcs_catalog:project_category_lu.project_category_id for non-Studion projects
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - Project Contest Fee Management Update 1 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #contestTypeDescription} property.</li>
 *   </ol>
 * </p>
 * 
 * @author isv
 * @version 1.1
 * @since Configurable Contest Fees v1.0 Assembly
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectContestFee")
@Entity
@Table(name = "project_contest_fee")
public class ProjectContestFee extends ProjectContestFeeAudit {
  /**
   * <p>
   * Generated serial id.
   * </p>
   */
  private static final long serialVersionUID = -3947166443467240318L;

  /**
   * <p>
   * The id field.
   * </p>
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_contest_fee_seq")
  @SequenceGenerator(name = "project_contest_fee_seq", sequenceName = "project_contest_fee_seq", allocationSize = 5)
  @Column(name = "project_contest_fee_id")
  private long id;

  /**
   * <p>
   * The projectId field.
   * </p>
   */
  @Column(name = "project_id")
  private long projectId;

  /**
   * <p>
   * The contest_type_id field.
   * </p>
   */
  @Column(name = "contest_type_id")
  private long contestTypeId;

  /**
   * <p>
   * Represents the is_studio column. It is not null.
   * </p>
   */
  @Column(name = "is_studio")
  private boolean studio;

    /**
     * <p>A <code>String</code> providing the description of contest type (non-persistent).</p>
     * 
     * @since 1.1
     */
    @Transient
    private String contestTypeDescription;

    /**
     * Returns the value of studio.
     * @return the studio
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * Set the value to  studio field.
     * @param studio the studio to set
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
   * <p>
   * The contestFee field.
   * </p>
   */
  @Column(name = "contest_fee")
  private double contestFee;

  /**
   * <p>
   * Sets the <code>id</code> field value.
   * </p>
   *
   * @param id
   *            the value to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * <p>
   * Gets the <code>id</code> field value.
   * </p>
   *
   * @return the <code>id</code> field value
   */
  public long getId() {
    return this.id;
  }

  /**
   * <p>
   * Sets the <code>projectId</code> field value.
   * </p>
   *
   * @param projectId
   *            the value to set
   */
  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  /**
   * <p>
   * Gets the <code>projectId</code> field value.
   * </p>
   *
   * @return the <code>projectId</code> field value
   */
  public long getProjectId() {
    return this.projectId;
  }

  /**
   * @deprecated use setContestTypeId
   * <p>
   * Sets the <code>contestTypeId</code> field value.
   * </p>
   *
   * @param contestTypeId
   *            the value to set
   */
  public void setContestType(long contestTypeId) {
    this.contestTypeId = contestTypeId;
  }

  /**
   * @deprecated use getContestTypeId
   * <p>
   * Gets the <code>contestTypeId</code> field value.
   * </p>
   *
   * @return the <code>contestTypeId</code> field value.
   */
  public long getContestType() {
    return this.contestTypeId;
  }
  
  /**
   * <p>
   * Sets the <code>contestTypeId</code> field value.
   * </p>
   *
   * @param contestTypeId
   *            the value to set
   */
  public void setContestTypeId(long contestTypeId) {
    this.contestTypeId = contestTypeId;
  }


  /**
   * <p>
   * Gets the <code>contestTypeId</code> field value.
   * </p>
   *
   * @return the <code>contestTypeId</code> field value
   */
  public long getContestTypeId() {
    return this.contestTypeId;
  }

  /**
   * <p>
   * Sets the <code>contestFee</code> field value.
   * </p>
   *
   * @param contestFee
   *            the value to set
   */
  public void setContestFee(double contestFee) {
    this.contestFee = contestFee;
  }

  /**
   * <p>
   * Gets the <code>contestFee</code> field value.
   * </p>
   *
   * @return the <code>contestFee</code> field value
   */
  public double getContestFee() {
    return this.contestFee;
  }

    /**
     * <p>Gets the description of contest type (non-persistent).</p>
     *
     * @return a <code>String</code> providing the description of contest type (non-persistent).
     * @since 1.1
     */
    public String getContestTypeDescription() {
        return this.contestTypeDescription;
    }

    /**
     * <p>Sets the description of contest type (non-persistent).</p>
     *
     * @param contestTypeDescription a <code>String</code> providing the description of contest type (non-persistent).
     * @since 1.1
     */
    public void setContestTypeDescription(String contestTypeDescription) {
        this.contestTypeDescription = contestTypeDescription;
    }
}
