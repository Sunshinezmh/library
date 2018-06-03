package com.dmsdbj.library.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_newbook")
public class TNewbook extends AbstractAuditingEntity implements Serializable{


    @Column(name = "start_time", table = "t_newbook", nullable = false)
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time", table = "t_newbook", nullable = false)
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name = "isbn", table = "t_newbook", nullable = false, length = 13)
    @Basic(optional = false)
    private String isbn;


    @Column(name = "reason", table = "t_newbook", nullable = false)
    @Basic(optional = false)
    private String reason;

    @Column(name = "remark", table = "t_newbook")
    @Basic
    private String remark;

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}