/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for pipeline info DTO
 * </p>
 *  <p>
 *  Version 1.1 - Direct Pipeline Integration Assembly
 *  - Added {@link #copilots} property with respective accessor/mutator methods.
 *  </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 * 
 * @author TCSASSEMBLER, isv
 * @version 1.1
 * @since Cockpit Pipeline Release Assembly 1 v1.0
 */
public class CommonPipelineData implements Serializable, Comparable<CommonPipelineData>{

    /** Generated serial version id. */
    private static final long serialVersionUID = 1154431097193493915L;

    /** Represents the client project name. */
    private String cpname;


    /** Represents the contest id. */
    private Long contestId;

    /** Represents the project id. */
    private Long projectId;

    /** Represents the project name. */
    private String pname;

    /** Represents the contest name. */
    private String cname;

    /** Represents the contest version. */
    private String cversion;

    /** Represents the start date. */
    private XMLGregorianCalendar startDate;

    /** Represents the end date. */
    private XMLGregorianCalendar endDate;

    /** The duration start time. */
    private XMLGregorianCalendar durationStartTime;

    /** The duration end time. */
    private XMLGregorianCalendar durationEndTime;

    /** The duration in hours. */
    private long durationInHrs;

    /** Represents the status name. */
    private String sname;

    /** Represents the type of contest. */
    private String contestType;

    /** Represents the category of contest. */
    private String contestCategory;

    /** The create time. */
    private XMLGregorianCalendar createTime;

    /** The modify time. */
    private XMLGregorianCalendar modifyTime;

    /** The client name. */
    private String clientName;

    /** The total prize. */
    private Double totalPrize;

    /** The contest fee. */
    private Double contestFee;


    /** The manager. */
    private String manager;

	  /** The was reposted. */
    private Boolean wasReposted;
    /** Represents the permission for contest. */
    private String cperm;

    /** Represents the permission for project. */
    private String pperm;

    /**
     * <p>A <code>String[]</code> providing the list of copilots assigned to contest.</p>
     * 
     * @since 1.1
     */
    private String[] copilots;

    
    /** Represents the contest type id. */
    private Long contestTypeId;


    /** The is studio */
    private Boolean isStudio;

	
   /** Represents the client id. */
    private Long clientId;
	
    /**
     * Returns the client project name.
     * 
     * @return the client project name.
     */
    public String getCpname() {
        return cpname;
    }

    /**
     * Updates the client project name with the specified value.
     * 
     * @param pname
     *            the client project name to set.
     */
    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    /**
     * Returns the contest name.
     * 
     * @return the contest name.
     */
    public String getCname() {
        return cname;
    }

    /**
     * Updates the contest name with the specified value.
     * 
     * @param cname
     *            the contest name to set.
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * Returns the contestId.
     * 
     * @return the contestId.
     */
    public Long getContestId() {
        return contestId;
    }

    /**
     * Updates the contestId with the specified value.
     * 
     * @param contestId
     *            the contestId to set.
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    /**
     * Returns the project name.
     * 
     * @return the project name.
     */
    public String getPname() {
        return pname;
    }

    /**
     * Updates the project name with the specified value.
     * 
     * @param pname
     *            the project name to set.
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * Returns the status name.
     * 
     * @return the status name.
     */
    public String getSname() {
        return sname;
    }

    /**
     * Updates the sname with the specified value.
     * 
     * @param sname
     *            the status name to set.
     */
    public void setSname(String sname) {
        this.sname = sname;
    }

    /**
     * <p>
     * Gets the type of contest.
     * </p>
     * 
     * @return the contestType
     */
    public String getContestType() {
        return this.contestType;
    }

    /**
     * <p>
     * Sets the type of contest.
     * </p>
     * 
     * @param contestType
     *            the contestType to set
     */
    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    /**
     * Gets the project id.
     * 
     * @return the project id
     */
    public Long getProjectId() {
        return this.projectId;
    }

    /**
     * Sets the project id.
     * 
     * @param projectId
     *            the new project id
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the cversion.
     * 
     * @return the cversion
     */
    public String getCversion() {
        return this.cversion;
    }

    /**
     * Sets the cversion.
     * 
     * @param cversion
     *            the new cversion
     */
    public void setCversion(String cversion) {
        this.cversion = cversion;
    }

    /**
     * Gets the contest category.
     * 
     * @return the contest category
     */
    public String getContestCategory() {
        return this.contestCategory;
    }

    /**
     * Sets the contest category.
     * 
     * @param contestCategory
     *            the new contest category
     */
    public void setContestCategory(String contestCategory) {
        this.contestCategory = contestCategory;
    }

    /**
     * Gets the creates the time.
     * 
     * @return the creates the time
     */
    public XMLGregorianCalendar getCreateTime() {
        return this.createTime;
    }

    /**
     * Sets the creates the time.
     * 
     * @param createTime
     *            the new creates the time
     */
    public void setCreateTime(XMLGregorianCalendar createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets the modify time.
     * 
     * @return the modify time
     */
    public XMLGregorianCalendar getModifyTime() {
        return this.modifyTime;
    }

    /**
     * Sets the modify time.
     * 
     * @param modifyTime
     *            the new modify time
     */
    public void setModifyTime(XMLGregorianCalendar modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * Gets the client name.
     * 
     * @return the client name
     */
    public String getClientName() {
        return this.clientName;
    }

    /**
     * Sets the client name.
     * 
     * @param clientName
     *            the new client name
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }


    /**
     * Gets the total prize.
     * 
     * @return the total prize
     */
    public Double getTotalPrize() {
        return this.totalPrize;
    }

    /**
     * Sets the total prize.
     * 
     * @param totalPrize
     *            the new total prize
     */
    public void setTotalPrize(Double totalPrize) {
        this.totalPrize = totalPrize;
    }

    /**
     * Gets the contest fee.
     * 
     * @return the contest fee
     */
    public Double getContestFee() {
        return this.contestFee;
    }

    /**
     * Sets the contest fee.
     * 
     * @param contestFee
     *            the new contest fee
     */
    public void setContestFee(Double contestFee) {
        this.contestFee = contestFee;
    }

   
    /**
     * Gets the manager.
     * 
     * @return the manager
     */
    public String getManager() {
        return this.manager;
    }

    /**
     * Sets the manager.
     * 
     * @param manager
     *            the new manager
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

	   /**
     * Gets the was reposted.
     * 
     * @return the was reposted
     */
    public Boolean getWasReposted() {
        return this.wasReposted;
    }

    /**
     * Sets the was reposted.
     * 
     * @param wasReposted
     *            the new was reposted
     */
    public void setWasReposted(Boolean wasReposted) {
        this.wasReposted = wasReposted;
    }


    /**
     * Gets the cperm.
     * 
     * @return the cperm
     */
    public String getCperm() {
        return this.cperm;
    }

    /**
     * Sets the cperm.
     * 
     * @param cperm
     *            the new cperm
     */
    public void setCperm(String cperm) {
        this.cperm = cperm;
    }

    /**
     * Gets the pperm.
     * 
     * @return the pperm
     */
    public String getPperm() {
        return this.pperm;
    }

    /**
     * Sets the pperm.
     * 
     * @param pperm
     *            the new pperm
     */
    public void setPperm(String pperm) {
        this.pperm = pperm;
    }

    /**
     * Gets the duration in hrs.
     * 
     * @return the duration in hrs
     */
    public long getDurationInHrs() {
        return this.durationInHrs;
    }

    /**
     * Sets the duration in hrs.
     * 
     * @param durationInHrs
     *            the new duration in hrs
     */
    public void setDurationInHrs(long durationInHrs) {
        this.durationInHrs = durationInHrs;
    }

    /**
     * Gets the start date.
     * 
     * @return the start date
     */
    public XMLGregorianCalendar getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the start date.
     * 
     * @param startDate
     *            the new start date
     */
    public void setStartDate(XMLGregorianCalendar startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     * 
     * @return the end date
     */
    public XMLGregorianCalendar getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the end date.
     * 
     * @param endDate
     *            the new end date
     */
    public void setEndDate(XMLGregorianCalendar endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the duration start time.
     * 
     * @return the duration start time
     */
    public XMLGregorianCalendar getDurationStartTime() {
        return this.durationStartTime;
    }

    /**
     * Sets the duration start time.
     * 
     * @param durationStartTime
     *            the new duration start time
     */
    public void setDurationStartTime(XMLGregorianCalendar durationStartTime) {
        this.durationStartTime = durationStartTime;
    }

    /**
     * Gets the duration end time.
     * 
     * @return the duration end time
     */
    public XMLGregorianCalendar getDurationEndTime() {
        return this.durationEndTime;
    }

    /**
     * Sets the duration end time.
     * 
     * @param durationEndTime
     *            the new duration end time
     */
    public void setDurationEndTime(XMLGregorianCalendar durationEndTime) {
        this.durationEndTime = durationEndTime;
    }

    public int compareTo(CommonPipelineData o) {
        int ret = this.startDate.compare(o.startDate);
        if (ret == 0) {
            return this.contestId.compareTo(o.contestId);
        }
        
        return ret;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.contestId == null) ? 0 : this.contestId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommonPipelineData other = (CommonPipelineData) obj;
        if (this.contestId == null) {
            if (other.contestId != null)
                return false;
        } else if (!this.contestId.equals(other.contestId))
            return false;
        return true;
    }
    
    /**
     * <p>Gets the list of copilots assigned to contest.</p>
     *
     * @return a <code>String[]</code> providing the list of copilots assigned to contest.
     * @since 1.1
     */
    public String[] getCopilots() {
        return this.copilots;
    }

    /**
     * <p>Sets the list of copilots assigned to contest.</p>
     *
     * @param copilots a <code>String[]</code> providing the list of copilots assigned to contest.
     * @since 1.1
     */
    public void setCopilots(String[] copilots) {
        this.copilots = copilots;
    }


     /**
     * Returns the contestTypeId.
     * 
     * @return the contestTypeId.
     */
    public Long getContestTypeId() {
        return contestTypeId;
    }

    /**
     * Updates the contestTypeId with the specified value.
     * 
     * @param contestTypeId
     *            the contestId to set.
     */
    public void setContestTypeId(Long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }


    /**
     * Gets the  isStudio.
     * 
     * @return the isStudio
     */
    public Boolean isStudio() {
        return this.isStudio;
    }

    /**
     * Sets the was isStudio.
     * 
     * @param isStudio
     *            the isStudio
     */
    public void setIsStudio(Boolean isStudio) {
        this.isStudio = isStudio;
    }
	
	/**
     * Returns the clientId.
     * 
     * @return the clientId.
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * Updates the clientId with the specified value.
     * 
     * @param clientId
     *            the contestId to set.
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
