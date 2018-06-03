package com.dmsdbj.library.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_review")
public class TReview extends AbstractAuditingEntity implements Serializable{


    @Column(name = "agree_count", table = "t_review")
    @Basic
    private Integer agreeCount;

    @Column(name = "isbn", table = "t_review", nullable = false, length = 13)
    @Basic(optional = false)
    private String isbn;

    @Column(name = "disagree_count", table = "t_review")
    @Basic
    private Integer disagreeCount;

    @Column(name = "PID", table = "t_review", nullable = false, length = 22)
    @Basic(optional = false)
    private String pid;

    @Column(name = "review_content", table = "t_review", nullable = false)
    @Basic(optional = false)
    private String reviewContent;

    @Column(name = "user_id", table = "t_review", nullable = false, length = 22)
    @Basic(optional = false)
    private String userId;


    public Integer getAgreeCount() {
        return this.agreeCount;
    }

    public void setAgreeCount(Integer agreeCount) {
        this.agreeCount = agreeCount;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getDisagreeCount() {
        return this.disagreeCount;
    }

    public void setDisagreeCount(Integer disagreeCount) {
        this.disagreeCount = disagreeCount;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getReviewContent() {
        return this.reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}