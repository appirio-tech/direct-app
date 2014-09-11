package com.topcoder.management.project;

/**
 * <p>
 * Bean class to hold permissions for project and their contests.
 * </p>
 * 
 * @author TCSASSEMBLER
 * 
 * @since TCCC-1329
 * @version 1.0
 */
public class SimpleProjectPermissionData {

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

    private Integer pread;

    private Integer pwrite;

    private Integer pfull;

    private Integer cread;

    private Integer cwrite;

    private Integer cfull;

	private boolean studio;

    public Integer getCfull() {
        return cfull;
    }

    public void setCfull(Integer cfull) {
        this.cfull = cfull;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Long getContestId() {
        return contestId;
    }

    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    public Integer getCread() {
        return cread;
    }

    public void setCread(Integer cread) {
        this.cread = cread;
    }

    public Integer getCwrite() {
        return cwrite;
    }

    public void setCwrite(Integer cwrite) {
        this.cwrite = cwrite;
    }

    public Integer getPfull() {
        return pfull;
    }

    public void setPfull(Integer pfull) {
        this.pfull = pfull;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getPread() {
        return pread;
    }

    public void setPread(Integer pread) {
        this.pread = pread;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getPwrite() {
        return pwrite;
    }

    public void setPwrite(Integer pwrite) {
        this.pwrite = pwrite;
    }

	public void setStudio(boolean studio) {
        this.studio = studio;
    }
    
    public boolean isStudio() {
        return this.studio;
    }
}
