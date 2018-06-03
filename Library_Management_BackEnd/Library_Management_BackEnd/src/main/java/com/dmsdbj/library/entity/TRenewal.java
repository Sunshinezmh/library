package com.dmsdbj.library.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_renewal")
public class TRenewal extends AbstractAuditingEntity implements Serializable{


    @Column(name = "borrow_id", table = "t_renewal", nullable = false, length = 22)
    @Basic(optional = false)
    private String borrowId;

    @Column(name = "start_time", table = "t_renewal", nullable = false)
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "xujie_time", table = "t_renewal")
    @Basic
    private Integer xujieTime;

    @Column(name = "remark", table = "t_renewal")
    @Basic
    private String remark;

    public String getBorrowId() {
        return this.borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getXujieTime() {
        return this.xujieTime;
    }

    public void setXujieTime(Integer xujieTime) {
        this.xujieTime = xujieTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}