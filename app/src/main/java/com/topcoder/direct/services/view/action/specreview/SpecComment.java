package com.topcoder.direct.services.view.action.specreview;

import java.util.Date;

public class SpecComment implements Comparable<SpecComment>{
    private SpecCommentType commentType;
    
    private String reviewerCommentType;
    
    private String commentBy;
    
    private Date commentDate;
    
    private String comment;
    
    private long commentId;

    /**
     * Gets the commentType field.
     *
     * @return the commentType
     */
    public SpecCommentType getCommentType() {
        return commentType;
    }

    /**
     * Sets the commentType field.
     *
     * @param commentType the commentType to set
     */
    public void setCommentType(SpecCommentType commentType) {
        this.commentType = commentType;
    }

    /**
     * Gets the reviewerCommentType field.
     *
     * @return the reviewerCommentType
     */
    public String getReviewerCommentType() {
        return reviewerCommentType;
    }

    /**
     * Sets the reviewerCommentType field.
     *
     * @param reviewerCommentType the reviewerCommentType to set
     */
    public void setReviewerCommentType(String reviewerCommentType) {
        this.reviewerCommentType = reviewerCommentType;
    }

    /**
     * Gets the commentBy field.
     *
     * @return the commentBy
     */
    public String getCommentBy() {
        return commentBy;
    }

    /**
     * Sets the commentBy field.
     *
     * @param commentBy the commentBy to set
     */
    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    /**
     * Gets the commentDate field.
     *
     * @return the commentDate
     */
    public Date getCommentDate() {
        return commentDate;
    }

    /**
     * Sets the commentDate field.
     *
     * @param commentDate the commentDate to set
     */
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * Gets the comment field.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment field.
     *
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the commentId field.
     *
     * @return the commentId
     */
    public long getCommentId() {
        return commentId;
    }

    /**
     * Sets the commentId field.
     *
     * @param commentId the commentId to set
     */
    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public int compareTo(SpecComment o) {
        return commentDate.compareTo(o.getCommentDate());
    }
    
}
