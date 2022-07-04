/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.permission;

/**
 * <p>
 * Bean class to hold permissions for project and their contests.
 * </p>
 *
 * @version 1.0
 */
public class CommonProjectPermissionData {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6992488651979864257L;

    /**
     * Represents the contest id.
     */
    private Long contestId;

    /**
     * Represents the project id.
     */
    private Long projectId;

    /**
     * Represents the project name.
     */
    private String pname;

    /**
     * Represents the contest name.
     */
    private String cname;
    /**
     * Represents the pread attribute of the CommonProjectPermissionData entity.
     * It's set and accessed in the set/get methods. It can be any value. The
     * default value is null.
     */
    private Integer pread;
    /**
     * Represents the pwrite attribute of the CommonProjectPermissionData
     * entity. It's set and accessed in the set/get methods. It can be any
     * value. The default value is null.
     */
    private Integer pwrite;
    /**
     * Represents the pfull attribute of the CommonProjectPermissionData entity.
     * It's set and accessed in the set/get methods. It can be any value. The
     * default value is null.
     */
    private Integer pfull;
    /**
     * Represents the cread attribute of the CommonProjectPermissionData entity.
     * It's set and accessed in the set/get methods. It can be any value. The
     * default value is null.
     */
    private Integer cread;
    /**
     * Represents the cwrite attribute of the CommonProjectPermissionData
     * entity. It's set and accessed in the set/get methods. It can be any
     * value. The default value is null.
     */
    private Integer cwrite;
    /**
     * Represents the cfull attribute of the CommonProjectPermissionData entity.
     * It's set and accessed in the set/get methods. It can be any value. The
     * default value is null.
     */
    private Integer cfull;
    /**
     * Represents the studio attribute of the CommonProjectPermissionData
     * entity. The default value is false.
     */
    private boolean studio;

    /**
     * Get cfull
     * <p/>
     * Impl note: Get the namesake variable.
     *
     * @return the cfull attribute of the CommonProjectPermissionData entity
     */
    public Integer getCfull() {
        return cfull;
    }

    /**
     * Set cfull
     * <p/>
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     *
     * @param cfull the cfull attribute to set to the CommonProjectPermissionData
     *              entity
     */
    public void setCfull(Integer cfull) {
        this.cfull = cfull;
    }

    /**
     * Get cname
     * <p/>
     * Impl note: Get the namesake variable.
     *
     * @return the cname attribute of the CommonProjectPermissionData entity
     */
    public String getCname() {
        return cname;
    }

    /**
     * Set cname
     * <p/>
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     *
     * @param cname the cname attribute to set to the CommonProjectPermissionData
     *              entity
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * Get contest id
     * <p/>
     * Impl note: Get the namesake variable.
     *
     * @return the contest id attribute of the CommonProjectPermissionData
     *         entity
     */
    public Long getContestId() {
        return contestId;
    }

    /**
     * Set contest id
     * <p/>
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     *
     * @param contestId the contest id attribute to set to the
     *                  CommonProjectPermissionData entity
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    /**
     * Get cread
     * <p/>
     * Impl note: Get the namesake variable.
     *
     * @return the cread attribute of the CommonProjectPermissionData entity
     */
    public Integer getCread() {
        return cread;
    }

    /**
     * Set cread
     * <p/>
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     *
     * @param cread the cread attribute to set to the CommonProjectPermissionData
     *              entity
     */
    public void setCread(Integer cread) {
        this.cread = cread;
    }

    /**
     * Get cwrite
     * <p/>
     * Impl note: Get the namesake variable.
     *
     * @return the cwrite attribute of the CommonProjectPermissionData entity
     */
    public Integer getCwrite() {
        return cwrite;
    }

    /**
     * Set cwrite
     * <p/>
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     *
     * @param cwrite the cwrite attribute to set to the CommonProjectPermissionData
     *               entity
     */
    public void setCwrite(Integer cwrite) {
        this.cwrite = cwrite;
    }

    /**
     * Get pfull
     * <p/>
     * Impl note: Get the namesake variable.
     *
     * @return the pfull attribute of the CommonProjectPermissionData entity
     */
    public Integer getPfull() {
        return pfull;
    }

    /**
     * Set pfull
     * <p/>
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     *
     * @param pfull the pfull attribute to set to the CommonProjectPermissionData
     *              entity
     */
    public void setPfull(Integer pfull) {
        this.pfull = pfull;
    }

    /**
     * Get pname
     * <p/>
     * Impl note: Get the namesake variable.
     *
     * @return the pname attribute of the CommonProjectPermissionData entity
     */
    public String getPname() {
        return pname;
    }

    /**
     * Set pname
     * <p/>
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     *
     * @param pname the pname attribute to set to the CommonProjectPermissionData
     *              entity
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * Get pread
     * <p/>
     * Impl note: Get the namesake variable.
     *
     * @return the pread attribute of the CommonProjectPermissionData entity
     */
    public Integer getPread() {
        return pread;
    }

    /**
     * Set pread
     * <p/>
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     *
     * @param pread the pread attribute to set to the CommonProjectPermissionData
     *              entity
     */
    public void setPread(Integer pread) {
        this.pread = pread;
    }

    /**
     * Get project id
     * <p/>
     * Impl note: Get the namesake variable.
     *
     * @return the project id attribute of the CommonProjectPermissionData
     *         entity
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Set project id
     * <p/>
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     *
     * @param projectId the project id attribute to set to the
     *                  CommonProjectPermissionData entity
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Get pwrite
     * <p/>
     * Impl note: Get the namesake variable.
     *
     * @return the pwrite attribute of the CommonProjectPermissionData entity
     */
    public Integer getPwrite() {
        return pwrite;
    }

    /**
     * Set pwrite
     * <p/>
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     *
     * @param pwrite the pwrite attribute to set to the CommonProjectPermissionData
     *               entity
     */
    public void setPwrite(Integer pwrite) {
        this.pwrite = pwrite;
    }

    /**
     * Set studio
     * <p/>
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     *
     * @param studio the studio attribute to set to the CommonProjectPermissionData
     *               entity
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * Is studio
     * <p/>
     *
     * @return the studio attribute of the CommonProjectPermissionData entity
     */
    public boolean isStudio() {
        return this.studio;
    }

    /**
     * The Empty constructor for CommonProjectPermissionData.
     */
    public CommonProjectPermissionData() {

    }
}
