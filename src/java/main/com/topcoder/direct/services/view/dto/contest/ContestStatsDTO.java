/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.topcoder.service.project.CompetitionPrize;
import com.topcoder.service.studio.MilestonePrizeData;

/**
 * <p>A DTO providing the statistics on requested contest.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ContestStatsDTO implements Serializable {

    /**
     * <p>An interface to be implemented by the parties interested in getting the statistics on desired contest.</p>
     */
    public static interface Aware {

        /**
         * <p>Gets the ID of contest to get statistics for.</p>
         *
         * @return a <code>long</code> providing the ID of contest to get statistics for.
         */
        long getContestId();

        /**
         * <p>Sets the statistics on requested contest.</p>
         *
         * @param stats a <code>ContestStatsDTO</code> providing the details on statistics for requested contest.
         */
        void setContestStats(ContestStatsDTO stats);

    }

    /**
     * <p>A <code>ContestBriefDTO</code> providing the basic details for contest.</p>
     */
    private ContestBriefDTO contest;

    /**
     * <p>A <code>Date</code> providing the start date and time for contest.</p>
     */
    private Date startTime;

    /**
     * <p>A <code>Date</code> providing the end date and time for contest.</p>
     */
    private Date endTime;

    /**
     * <p>A <code>int</code> providing the number of submissions for contest.</p>
     */
    private int submissionsNumber;

    /**
     * <p>A <code>int</code> providing the number of registrants for contest.</p>
     */
    private int registrantsNumber;
    
    /**
     * <p>A <code>int</code> providing the number of forum posts for contest.</p> 
     */
    private int forumPostsNumber;
	
	/**
     * <p>A <code>long</code> providing the id of the contest forum.</p> 
     */
    private long forumId;
	
	private List<CompetitionPrize> prizes;
	
	private MilestonePrizeData milestonePrizes;
	
	private Double adminFees;
	
	private Boolean isStudio;
	
	private String paymentReferenceId;
	
	/**
	 * <p>A <code>String</code> providing the SVN module of the contest.</p>
	 */
	private String svn;

    /**
     * <p>Constructs new <code>ContestStatsDTO</code> instance. This implementation does nothing.</p>
     */
    public ContestStatsDTO() {
		this.isStudio = true;
    }

    /**
     * <p>Gets the number of submissions for contest.</p>
     *
     * @return a <code>int</code> providing the number of submissions for contest.
     */
    public int getSubmissionsNumber() {
        return this.submissionsNumber;
    }

    /**
     * <p>Sets the number of submissions for contest.</p>
     *
     * @param submissionsNumber a <code>int</code> providing the number of submissions for contest.
     */
    public void setSubmissionsNumber(int submissionsNumber) {
        this.submissionsNumber = submissionsNumber;
    }

    /**
     * <p>Gets the number of registrants for contest.</p>
     *
     * @return a <code>int</code> providing the number of registrants for contest.
     */
    public int getRegistrantsNumber() {
        return this.registrantsNumber;
    }

    /**
     * <p>Sets the number of registrants for contest.</p>
     *
     * @param registrantsNumber a <code>int</code> providing the number of registrants for contest.
     */
    public void setRegistrantsNumber(int registrantsNumber) {
        this.registrantsNumber = registrantsNumber;
    }

    /**
     * <p>Gets the number of forum posts for contest.</p>
     *
     * @return a <code>int</code> providing the number of forum posts for contest.
     */
    public int getForumPostsNumber() {
        return this.forumPostsNumber;
    }

    /**
     * <p>Sets the number of forum posts for contest.</p>
     *
     * @param forumPostsNumber a <code>int</code> providing the number of forum posts for contest.
     */
    public void setForumPostsNumber(int forumPostsNumber) {
        this.forumPostsNumber = forumPostsNumber;
    }
	
	/**
     * <p>Gets the id of forum for contest.</p>
     *
     * @return a <code>long</code> providing the id of forum for contest.
     */
    public long getForumId() {
        return this.forumId;
    }

    /**
     * <p>Sets the id of forum for contest.</p>
     *
     * @param forumId a <code>long</code> providing the id of forum for contest.
     */
    public void setForumId(long forumId) {
        this.forumId = forumId;
    }
	
    /**
     * <p>Gets the end date and time for contest.</p>
     *
     * @return a <code>Date</code> providing the end date and time for contest.
     */
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * <p>Sets the end date and time for contest.</p>
     *
     * @param endTime a <code>Date</code> providing the end date and time for contest.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    /**
     * <p>Gets the start date and time for contest.</p>
     *
     * @return a <code>Date</code> providing the start date and time for contest.
     */
    public Date getStartTime() {
        return this.startTime;
    }

    /**
     * <p>Sets the start date and time for contest.</p>
     *
     * @param startTime a <code>Date</code> providing the start date and time for contest.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * <p>Gets the basic details for contest.</p>
     *
     * @return a <code>ContestBriefDTO</code> providing the basic details for contest.
     */
    public ContestBriefDTO getContest() {
        return this.contest;
    }

    /**
     * <p>Sets the basic details for contest.</p>
     *
     * @param contest a <code>ContestBriefDTO</code> providing the basic details for contest.
     */
    public void setContest(ContestBriefDTO contest) {
        this.contest = contest;
    }
	
	public Boolean getIsStudio() {
		return isStudio;
	}
	
	public void setIsStudio(Boolean isStudio) {
		this.isStudio = isStudio;
	}
	
	public void setPrizes(List<CompetitionPrize> prizes){
		this.prizes = prizes;
	}
	
	public List<CompetitionPrize> getPrizes(){
		return prizes;
	}
	
	public void setAdminFees(Double adminFees){
		this.adminFees = adminFees;
	}
	
	public Double getAdminFees(){
		if(adminFees == null){
			return 0.0;
		}
		return adminFees;
	}
	
	public void setMilestonePrizes(MilestonePrizeData milestonePrizes){
		this.milestonePrizes = milestonePrizes;
	}
	
	public MilestonePrizeData getMilestonePrizes(){
		return milestonePrizes;
	}
	
	public Double getTotalFees(){
		Double totalMilestone = 0.0;
		if(getMilestonePrizes() != null){
			totalMilestone = getMilestonePrizes().getAmount() * getMilestonePrizes().getNumberOfSubmissions();
		}
		return getTotalMainPrizes() + totalMilestone  + getAdminFees();
	}
	
	public Double getTotalMainPrizes(){
		Double totalMainPrizes = 0.0;
		for (int i=0; i < prizes.size(); i++ ) {
		   totalMainPrizes += prizes.get(i).getAmount() ;
		}
		return totalMainPrizes;
	}
	
	public void setPaymentReferenceId(String paymentReferenceId){
		this.paymentReferenceId = paymentReferenceId;
	}
	
	public String getPaymentReferenceId(){
		return paymentReferenceId;
	}
	
	/**
	 * Gets the svn module.
	 *
	 * @return the svn module.
	 */
	public String getSvn() {
	    return svn;
	}
	
	/**
	 * Sets the svn module.
	 *
	 * @param svn the svn module.
	 */
	public void setSvn(String svn) {
	    this.svn = svn;
	}
}
